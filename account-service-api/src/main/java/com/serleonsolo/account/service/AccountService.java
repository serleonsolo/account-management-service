package com.serleonsolo.account.service;

import com.serleonsolo.account.model.Account;
import com.serleonsolo.account.model.AccountSearchParameters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.List;

public interface AccountService {

    Flux<Account> getAccount(String id);

    Flux<Account> getAccounts(AccountSearchParameters params);

    Mono<Boolean> updateAccounts(List<Account> accounts);

}
