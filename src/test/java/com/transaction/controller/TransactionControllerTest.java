package com.transaction.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transaction.Bootstrap;
import com.transaction.clock.TimeProvider;
import com.transaction.model.Transaction;
import com.transaction.service.StatsService;
import com.transaction.validator.Validator;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { Bootstrap.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class })
public class TransactionControllerTest {

  private MockMvc mockMvc;
  @InjectMocks
  private TransactionController transactionController;
  @Mock
  private StatsService statisticsService;
  @Mock
  private Validator validator;
  @Mock
  private TimeProvider timeProvider;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
  }

  public static byte[] convertObjectToJsonBytes(final Object object) throws IOException {
    final ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    return mapper.writeValueAsBytes(object);
  }

  @Test
  public void oldDatedTransaction_returns204() throws Exception {
    mockMvc
        .perform(post("/transactions").contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(convertObjectToJsonBytes(new Transaction(12, 0))))
        .andExpect(status().isNoContent());
  }

  @Test
  public void validTransaction_returns201() throws Exception {
    Mockito.when(validator.isValid(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyInt()))
        .thenReturn(true);
    mockMvc
        .perform(post("/transactions").contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(convertObjectToJsonBytes(new Transaction(12, System.currentTimeMillis()))))
        .andExpect(status().isCreated());
  }

  @Test
  public void getStatistics_returns200() throws Exception {
    mockMvc.perform(get("/statistics")).andExpect(status().isOk());
  }
}