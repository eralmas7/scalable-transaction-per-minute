package com.transaction.model;

/**
 * Immutable statistics managing count, max/min and sum over the passed values which gets aggregated and returned to the client.
 * @author almass
 */
public final class Statistics {

  private final long count;
  private final double max;
  private final double min;
  private final double sum;

  public Statistics(final long count, final double max, final double min, final double sum) {
    this.count = count;
    this.max = max;
    this.min = min;
    this.sum = sum;
  }

  /**
   * Returns a long specifying the total number of transactions happened in the last 60 seconds.
   * @return
   */
  public long getCount() {
    return count;
  }

  /**
   * Returns a double specifying single highest transaction value in the last 60 seconds.
   * @return
   */
  public double getMax() {
    return max;
  }

  /**
   * Return a double specifying single lowest transaction value in the last 60 seconds.
   * @return
   */
  public double getMin() {
    return min;
  }

  /**
   * Returns a double specifying the total sum of transaction value in the last 60 seconds.
   * @return
   */
  public double getSum() {
    return sum;
  }

  /**
   * Returns a double specifying the average amount of transaction value in the last 60 seconds.
   * @return
   */
  public double getAvg() {
    return sum / (count == 0 ? 1 : count);
  }

  @Override
  public String toString() {
    return "Statistics [count=" + count + ", max=" + max + ", min=" + min + ", sum=" + sum
        + ", avg=" + getAvg() + "]";
  }

}