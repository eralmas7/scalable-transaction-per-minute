package com.transaction.validator;

import org.springframework.stereotype.Component;

/**
 * A validator to validate staleness of a transaction request.
 * @author almass
 *
 */
@Component
public class StalenessValidator implements Validator {

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isValid(final long currentTime, final long transactionTime,
      final int thresholdValue) {
    return transactionTime >= (currentTime - thresholdValue) && transactionTime <= currentTime;
  }

}