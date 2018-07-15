package com.transaction.service;

import com.transaction.model.Statistics;
import com.transaction.model.Transaction;

/**
 * A service which is responsible for handling the statistical request and addition of transaction.
 * @author almass
 *
 */
public interface StatsService {

  /**
   * Add a transaction in the chain.
   * @param transaction
   */
  public void addTransaction(final Transaction transaction);

  /**
   * Get the statistics from the time specified.
   * @param timeSince
   * @return
   */
  public Statistics getStatistics(final long timeSince);

  /**
   * Returns the time range within which we collect the statistics.
   * @return
   */
  public int thresholdSize();
}