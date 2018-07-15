package com.transaction;

import static org.springframework.boot.SpringApplication.run;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * An entry point. 
 * This class is a start up class which is responsible for bootstrapping the whole application.
 */
@SpringBootApplication
public class Bootstrap {

  public static void main(final String[] inputArguments) {
    run(Bootstrap.class, inputArguments);
  }
}