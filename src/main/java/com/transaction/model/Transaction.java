package com.transaction.model;

/**
 * Representation of a Transaction - an input from the client.
 * @author almass
 */
public class Transaction {

  private final double amount;
  private final long timestamp;

  public Transaction() {// for jackson
    amount = 0.;
    timestamp = 0l;
  }

  public Transaction(final double amount, final long timestamp) {
    this.amount = amount;
    this.timestamp = timestamp;
  }

  /**
   * Get transaction amount which can be credited or debited.
   *
   * @return Transaction amount.
   */
  public double getAmount() {
    return amount;
  }

  /**
   * Get transaction time in epoch milliseconds in UTC time zone.
   *
   * @return Transaction time.
   */
  public long getTimestamp() {
    return timestamp;
  }

}