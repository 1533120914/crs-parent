package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.common.exception.CustomException;
import org.example.common.response.ResponseData;
import org.example.common.response.ResponseEnum;
import org.example.entity.Account;
import org.example.entity.Admin;
import org.example.entity.Salesman;
import org.example.entity.User;
import org.example.mapper.AdminMapper;
import org.example.mapper.SalesmanMapper;
import org.example.mapper.UserMapper;
import org.example.service.AccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AccountServiceImpl implements AccountService {
    @Resource
    AdminMapper adminMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    SalesmanMapper salesmanMapper;

    @Override
    public ResponseData login(Account account) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("username", account.getUsername());
        Account one = null;
        switch (account.getIdentity()) {
            case 1:
                one = adminMapper.selectOne(qw);
                break;
            case 2:
                one = salesmanMapper.selectOne(qw);
                break;
            case 3:
                one = userMapper.selectOne(qw);
                break;
        }
        if( one == null ) {
            throw new CustomException(ResponseEnum.USER_NOT_EXIST);
        }else {
            if (!one.getPassword().equals(account.getPassword())) {
                throw new CustomException(ResponseEnum.PASSWORD_INVALID);
            }else {
                account.setId(one.getId());
                account.setAvatar(one.getAvatar());
                return ResponseData.success(account);
            }
        }
    }

    @Override
    public ResponseData modifyPassword(Account account,String oldPassword,String newPassword) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("id", account.getId());
        Account one = null;
        switch (account.getIdentity()) {
            case 1:
                one = adminMapper.selectOne(qw);
                break;
            case 2:
                one = salesmanMapper.selectOne(qw);
                break;
            case 3:
                one = userMapper.selectOne(qw);
                break;
        }
        // 先判断原密码是否正确
        if(!one.getPassword().equals(oldPassword)) {
            // 原密码错误直接抛出异常
            throw new CustomException(ResponseEnum.OLD_PASSWORD_INVALID);
        }else {
            // 原密码正确更新密码
            switch (account.getIdentity()) {
                case 1:
                    Admin admin = new Admin();
                    admin.setId(account.getId());
                    admin.setPassword(newPassword);
                    adminMapper.updateById(admin);
                    break;
                case 2:
                    Salesman salesman = new Salesman();
                    salesman.setId(account.getId());
                    salesman.setPassword(newPassword);
                    salesmanMapper.updateById(salesman);
                    break;
                case 3:
                    User user = new User();
                    user.setId(account.getId());
                    user.setPassword(newPassword);
                    userMapper.updateById(user);
                    break;
            }
        }
        return ResponseData.success();
    }
}
