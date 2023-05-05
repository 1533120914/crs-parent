package org.example.entity;

import lombok.Data;

/**
 * 映射前端登录的请求参数的实体类
 */
@Data
public class Account {
    private Integer id; // 用户id
    private String username; // 用户名
    private String password; // 密码
    private Integer identity; // 身份
    private String avatar; // 头像
}
