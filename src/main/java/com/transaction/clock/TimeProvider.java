package com.transaction.clock;

/**
 * An interface which would give us a time value.
 * @author almass
 *
 */
public interface TimeProvider {

  /**
   * Return a long value representing an epoch.
   * @return
   */
  public long getTime();
}