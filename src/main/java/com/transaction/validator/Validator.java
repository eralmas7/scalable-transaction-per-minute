package com.transaction.validator;

/**
 * A validator to validate the input from user.
 * @author almass
 */
public interface Validator {

  /**
   * Is time valid within the given threshold?
   * @param currentTime
   * @param transactionTime
   * @param threshold
   * @return
   */
  public boolean isValid(final long currentTime, final long transactionTime, final int threshold);
}