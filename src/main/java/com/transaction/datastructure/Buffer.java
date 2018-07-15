package com.transaction.datastructure;

import com.transaction.model.Statistics;
import com.transaction.model.Transaction;

/**
 * A specific data structure which would do the heavy lifting part to read the transaction and generate the statistics.
 * @author almass
 *
 */
public interface Buffer {

  /**
   * Returns the threshold size, which is nothing but a bucket size within which we need to accumulate transaction samples.
   * @return
   */
  public int thresholdSize();

  /**
   * Add a transaction.
   * @param transaction
   */
  public void addTransaction(final Transaction transaction);

  /**
   * Returns the statistics.
   * @param timeSince
   * @return
   */
  public Statistics getStatistics(final long timeSince);

}