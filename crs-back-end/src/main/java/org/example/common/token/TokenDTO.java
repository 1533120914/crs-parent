package org.example.common.token;

import lombok.Data;

/**
 * 封装Token令牌的实体类
 */
@Data
public class TokenDTO {
    private Integer id; //用户ID
    private String username; //用户名
    // Other User Info...
}
