package org.example.service;

import org.example.common.response.ResponseData;
import org.example.entity.Account;

public interface AccountService {
    ResponseData login(Account account);
}
