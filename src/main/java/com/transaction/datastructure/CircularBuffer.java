package com.transaction.datastructure;

import static java.lang.Double.MAX_VALUE;
import static java.lang.Double.MIN_VALUE;
import static java.lang.Double.compare;
import static java.lang.Double.max;
import static java.lang.Double.min;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.transaction.model.Statistics;
import com.transaction.model.Transaction;
import com.transaction.model.TransactionStats;

/**
 * Circular buffer which is responsible for creating bucket for each seconds of size defined by property ${com.transaction.buffer.size} to maintain statistical information within each seconds.
 * Based on the property we can further increase the performance and granularity by having bucket size based out of millis or 10X millis and so on. 
 * @author almass
 *
 */
@Component
public class CircularBuffer implements Buffer {

  private final static class StatsHolder {

    private final TransactionStats transactionStats;
    private final ReadWriteLock readWriteLocks;

    public StatsHolder(final TransactionStats transactionStats, final ReadWriteLock readWriteLock) {
      this.transactionStats = transactionStats;
      this.readWriteLocks = readWriteLock;
    }

    public Lock getReadLock() {
      return readWriteLocks.readLock();
    }

    public Lock getWriteLock() {
      return readWriteLocks.writeLock();
    }
  }

  private final StatsHolder[] statsHolders;

  /*
   * One time storage of a constant space, which is used to store the statistics. I've used
   * ReadWriteLock assuming readers aren't getting blocked when write operation is being performed.
   * Which means we are making a tradeoff, where one thread is still writing and another thread has
   * requested the stats which means we are eventual consistent in that case.
   */
  public CircularBuffer(
      @Value("${com.transaction.buffer.size:60}") final int transactionBufferSize) {
    this.statsHolders = new StatsHolder[transactionBufferSize];
    for (int i = 0; i < transactionBufferSize; ++i) {
      statsHolders[i] = new StatsHolder(new TransactionStats(), new ReentrantReadWriteLock());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int thresholdSize() {
    return statsHolders.length;
  }

  /**
   * {@inheritDoc}
   * Uses Lock Striping, which means multiple Threads can still pass on the past events which may/may not be needed in current window.
   */
  @Override
  public void addTransaction(final Transaction transaction) {
    final int index = (int) (transaction.getTimestamp() % statsHolders.length);
    try {
      statsHolders[index].getWriteLock().lock();
      final TransactionStats transactionStats = statsHolders[index].transactionStats;
      if (transactionStats.getStartTime() != transaction.getTimestamp()) {
        transactionStats.reset(transaction);
      } else {
        transactionStats.update(transaction);
      }
    } finally {
      statsHolders[index].getWriteLock().unlock();
    }
  }

  private boolean isWithinCurrentWindow(long timeSince, long bucketFirstTransTime) {
    return timeSince - bucketFirstTransTime < statsHolders.length;
  }

  /**
   * {@inheritDoc}
   * Time Complexity - O(1) since we have just 60 buckets and we iterate over 60 buckets only.
   * Space Complexity - O(1) since we are using constant space i.e. 60 array containers only.
   */
  @Override
  public Statistics getStatistics(final long timeSince) {
    long count = 0;
    double sum = 0, min = MAX_VALUE, max = MIN_VALUE;
    for (int index = 0; index < statsHolders.length; ++index) {
      try {
        statsHolders[index].getReadLock().lock();
        final TransactionStats transactionStats = statsHolders[index].transactionStats;
        if (isWithinCurrentWindow(timeSince, transactionStats.getStartTime())) {
          count += transactionStats.getNumberOfRequest();
          min = min(min, transactionStats.getMin());
          max = max(max, transactionStats.getMax());
          sum += transactionStats.getSum();
        }
      } finally {
        statsHolders[index].getReadLock().unlock();
      }
    }
    return new Statistics(count, evaluate(MIN_VALUE, max), evaluate(MAX_VALUE, min), sum);
  }

  private double evaluate(final double first, final double second) {
    return compare(first, second) == 0 ? 0 : second;
  }
}