package com.transaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.transaction.clock.TimeProvider;
import com.transaction.model.Statistics;
import com.transaction.model.Transaction;
import com.transaction.service.StatsService;
import com.transaction.validator.Validator;

/**
 * Controller which exposes rest end point for transaction and statistics.
 * @author almass
 *
 */
@RestController
public class TransactionController {

  private final StatsService statisticsService;
  private final Validator stalenessValidator;
  private final TimeProvider timeProvider;

  @Autowired
  public TransactionController(final StatsService statisticsService,
      final Validator stalenessValidator, final TimeProvider timeProvider) {
    this.statisticsService = statisticsService;
    this.stalenessValidator = stalenessValidator;
    this.timeProvider = timeProvider;
  }

  @PostMapping("/transactions")
  public ResponseEntity<Void> addTransaction(@RequestBody final Transaction transaction) {
    if (stalenessValidator.isValid(timeProvider.getTime(), transaction.getTimestamp(),
        statisticsService.thresholdSize())) {
      statisticsService.addTransaction(transaction);
      return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @GetMapping("/statistics")
  public ResponseEntity<Statistics> getStatistics() {
    return ResponseEntity.ok(statisticsService.getStatistics(timeProvider.getTime()));
  }
}