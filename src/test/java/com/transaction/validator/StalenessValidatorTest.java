package com.transaction.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class StalenessValidatorTest {

  private Validator validator;

  @Before
  public void setUp() throws Exception {
    validator = new StalenessValidator();
  }

  @Test
  public void validInBetwenCurrentAndTransactionTime_returnsTrue() {
    assertTrue(validator.isValid(100, 80, 60));
  }

  @Test
  public void oldTransactionTime_returnsTrue() {
    assertFalse(validator.isValid(100, 10, 60));
  }

  @Test
  public void futureTransactionTime_returnsTrue() {
    assertFalse(validator.isValid(100, 110, 60));
  }

  @Test
  public void onCurrentTransactionTime_returnsTrue() {
    assertTrue(validator.isValid(100, 100, 60));
  }

  @Test
  public void onThresholdTransactionTime_returnsTrue() {
    assertTrue(validator.isValid(100, 40, 60));
  }

  @Test
  public void justCurrentTransactionTime_returnsTrue() {
    assertFalse(validator.isValid(100, 101, 60));
  }

  @Test
  public void justThresholdTransactionTime_returnsTrue() {
    assertFalse(validator.isValid(100, 39, 60));
  }
}
