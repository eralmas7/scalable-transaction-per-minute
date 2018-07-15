package com.transaction.model;

/**
 * Mutable holder class which would hold the transaction statistics on each and every bucket.
 * @author almass
 *
 */
public class TransactionStats {

  private long startTime;
  private long numberOfRequest;
  private double sum;
  private double min = Double.MAX_VALUE;
  private double max = Double.MIN_VALUE;

  /**
   * Update the statistics based on the given transaction.
   * @param transaction
   */
  public void update(final Transaction transaction) {
    ++numberOfRequest;
    sum += transaction.getAmount();
    min = Double.min(min, transaction.getAmount());
    max = Double.max(max, transaction.getAmount());
  }

  /**
   * Reset the statistics as we have to slide the window based on the time elapsed defined.
   * @param transaction
   */
  public void reset(final Transaction transaction) {
    this.startTime = transaction.getTimestamp();
    this.numberOfRequest = 1;
    this.sum = transaction.getAmount();
    this.min = transaction.getAmount();
    this.max = transaction.getAmount();
  }

  /**
   * Returns the time of a first transaction.
   * @return
   */
  public long getStartTime() {
    return startTime;
  }

  /**
   * Number of request in the time range defined.
   * @return
   */
  public long getNumberOfRequest() {
    return numberOfRequest;
  }

  /**
   * Minimum amount received in the time range defined.
   * @return
   */
  public double getMin() {
    return min;
  }

  /**
   * Maximum amount received in the time range defined.
   * @return
   */
  public double getMax() {
    return max;
  }

  /**
   * Total amount received in the time range defined.
   * @return
   */
  public double getSum() {
    return sum;
  }

}