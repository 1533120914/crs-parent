package org.example.controller;

import org.example.common.response.ResponseData;
import org.example.entity.Account;
import org.example.service.AccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Resource
    AccountService accountService;

    @PostMapping("/login")
    public ResponseData login(Account account) {
        return accountService.login(account);
    }

    @PostMapping("/modify-password")
    public ResponseData modifyPassword(Account account, String oldPassword,String newPassword) {
        return accountService.modifyPassword(account,oldPassword,newPassword);
    }
}
