package com.transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transaction.datastructure.Buffer;
import com.transaction.model.Statistics;
import com.transaction.model.Transaction;

/**
 * Calculates real-time transaction statistics.
 * @author almass
 */
@Service
public class StatisticsService implements StatsService {

  private final Buffer buffer;

  @Autowired
  public StatisticsService(final Buffer buffer) {
    this.buffer = buffer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addTransaction(final Transaction transaction) {
    buffer.addTransaction(transaction);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Statistics getStatistics(final long timeSince) {
    return buffer.getStatistics(timeSince);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int thresholdSize() {
    return buffer.thresholdSize();
  }
}