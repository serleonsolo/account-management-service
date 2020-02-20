package com.serleonsolo.account.api.v1;

import com.serleonsolo.account.model.Account;
import com.serleonsolo.account.model.AccountSearchParameters;
import com.serleonsolo.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class AccountManagementControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountManagementController accountManagementController;

    private AccountSearchParameters accountSearchParameters;
    private List<Account> accounts = new ArrayList<>();

    private final Integer DEFAULT_STATUS = 0;
    private final String DEFAULT_ACCOUNT_NAME = "First";
    private final BigDecimal DEFAULT_BALANCE = new BigDecimal("10.0");

    @Before
    public void init() {
        accountSearchParameters = AccountSearchParameters.builder()
                .status(DEFAULT_STATUS.toString())
                .accountName(DEFAULT_ACCOUNT_NAME)
                .build();

        Account account = Account.builder()
                .status(DEFAULT_STATUS)
                .accountName(DEFAULT_ACCOUNT_NAME)
                .activeFrom(Timestamp.valueOf(LocalDateTime.now()))
                .balance(DEFAULT_BALANCE)
                .build();
        accounts.add(account);
    }

    @Test
    public void testGetAccounts() {
        Mockito.when(accountService.getAccounts(accountSearchParameters))
                .thenReturn(Flux.fromIterable(this.accounts));

        List<Account> accounts = accountService.getAccounts(accountSearchParameters)
                .collectList()
                .block();
        Assert.assertTrue(!CollectionUtils.isEmpty(accounts));

        Flux<Account> account = accountManagementController.getAccounts(DEFAULT_ACCOUNT_NAME,DEFAULT_STATUS.toString(),null);
        StepVerifier.create(account)
                .expectNextMatches(r -> {
                    Assert.assertTrue(r.getBalance().equals(DEFAULT_BALANCE));
                    return true;
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void testGetAccount() {

    }

    @Test
    public void testUpdateAccounts() {

    }
}
