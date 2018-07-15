package com.transaction.jbehave;

import static org.junit.Assert.assertEquals;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.transaction.controller.TransactionController;
import com.transaction.model.Statistics;
import com.transaction.model.Transaction;

@Steps
public class AllTransactionSteps {

  @Autowired
  private TransactionController transactionController;
  private Transaction transaction;
  private int status;
  private Statistics statistics;

  @Given("a user tries to add an old transaction into the system")
  public void generateOldDatedTransaction() {
    transaction = new Transaction(0, 0);
  }

  @When("client tries to add amount $amount for time $timestamp")
  public void createTransaction(double amount, long timestamp) {
    transaction = new Transaction(amount, timestamp);
  }

  @Then("user wouldn't get a response but the status would be $status")
  public void userResponse(int status) {
    final ResponseEntity<Void> response = transactionController.addTransaction(transaction);
    assertEquals(status, response.getStatusCodeValue());
  }

  @Given("a user tries to add a new transaction into the system")
  public void generateNewTransaction() {
    transaction = new Transaction(10, System.currentTimeMillis());
  }

  @When("client tries to add amount $amount current time")
  public void addTransaction(double amount) {
    transaction = new Transaction(amount, System.currentTimeMillis());
    status = transactionController.addTransaction(transaction).getStatusCodeValue();
  }

  @Then("user would get the status as $status")
  public void postTransaction(int status) {
    Assert.assertEquals(status, this.status);
  }

  @Given("a user adds the transaction statistics from the System with amount $first, $second with current time.")
  public void generateNewTransaction(double first, double second) {
    transactionController.addTransaction(new Transaction(first, System.currentTimeMillis()));
    transactionController.addTransaction(new Transaction(second, System.currentTimeMillis()));
  }

  @When("Client tries to get the statistics based on current time with a status of $status.")
  public void generateStats(int status) {
    ResponseEntity<Statistics> response = transactionController.getStatistics();
    statistics = response.getBody();
    assertEquals(response.getStatusCodeValue(), status);
  }

  @Then("user expects a total amount as $sum, min as $min, max as $max, average as $average and count as $count")
  public void compareStats(double sum, double min, double max, double average, long count) {
    assertEquals(0, Double.compare(this.statistics.getAvg(), average));
    assertEquals(count, this.statistics.getCount());
    assertEquals(0, Double.compare(this.statistics.getMax(), max));
    assertEquals(0, Double.compare(this.statistics.getMin(), min));
    assertEquals(0, Double.compare(this.statistics.getSum(), sum));
  }
}