package com.transaction.datastructure;

import static java.lang.Double.MAX_VALUE;
import static java.lang.Double.MIN_VALUE;
import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.transaction.model.Statistics;
import com.transaction.model.Transaction;

public class CurrentBufferTest {

  private Buffer buffer;

  @Before
  public void setUp() throws Exception {
    buffer = new CircularBuffer(60);
  }

  private void addTransactionsWithinTimeline(long currentTime) {
    final Random random = new Random();
    double minAmount = MAX_VALUE, maxAmount = MIN_VALUE;
    double sum = 0;
    for (int i = 0; i < 59; i++) {
      double amount = random.nextDouble();
      sum += amount;
      minAmount = Double.min(minAmount, amount);
      maxAmount = Double.max(maxAmount, amount);
      buffer.addTransaction(new Transaction(amount, currentTime));
      assertStats(i, currentTime, minAmount, maxAmount, sum);
    }
  }

  private void assertStats(int index, long currentTime, double minAmount, double maxAmount,
      double sum) {
    assertEquals(60, buffer.thresholdSize());
    Statistics statistics = buffer.getStatistics(currentTime);
    System.out.println(statistics);
    assertEquals(index + 1, statistics.getCount());
    assertEquals(minAmount, statistics.getMin(), 0.01);
    assertEquals(maxAmount, statistics.getMax(), 0.01);
    assertEquals(sum, statistics.getSum(), 0.01);
    assertEquals(sum / (index + 1d), statistics.getAvg(), 0.01);
  }

  @Test
  public void testMultipleTransaction_returnsStatistics() {
    addTransactionsWithinTimeline(System.currentTimeMillis());
    addTransactionsWithinTimeline(System.currentTimeMillis() + 100);// with resetting sliding window
  }
}
