package com.serleonsolo.account.api.v1;

import com.serleonsolo.account.model.Account;
import com.serleonsolo.account.model.AccountSearchParameters;
import com.serleonsolo.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@Slf4j
public class AccountManagementController {

    private final AccountService accountService;

    @Contract(pure = true)
    @Autowired
    public AccountManagementController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public Flux<Account> getAccount(@PathVariable String id) {
        log.debug("Get account with id {}", id);

        return accountService.getAccount(id)
                .doOnNext(l -> log.info("account found = " + l));
    }

    @GetMapping
    public Flux<Account> getAccounts(@RequestParam(required = false) String name,
                                     @RequestParam(required = false) String status,
                                     @RequestParam(required = false) String date) {
        log.debug("Get accounts with name {}, status {}, on date {}", name, status, date);

        AccountSearchParameters params = AccountSearchParameters.builder()
                .accountName(name)
                .status(status)
                .date(date)
                .build();

        return accountService.getAccounts(params)
                .doOnNext(l -> log.info("accounts found = " + l));
    }

    @PutMapping
    public Mono<Boolean> updateAccounts(@RequestBody List<Account> accounts) {
        log.debug("Update accounts {}", accounts);
        return accountService.updateAccounts(accounts);
    }
}
