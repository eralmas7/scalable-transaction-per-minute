package com.transaction.clock;

import org.springframework.stereotype.Component;

/**
 * A service which would provide current time in milliseconds and could be used to inject and helpful in case of testing.
 * @author almass
 *
 */
@Component
public class CurrentTimeProvider implements TimeProvider {

  /**
   * {@inheritDoc}
   */
  @Override
  public long getTime() {
    return System.currentTimeMillis();
  }
}