package org.example.common.token;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Token业务类
 */
@Component
public class TokenService {
    @Value("${config.token.secret}")
    private String secret; //令牌加密和解密的密钥
    @Value("${config.token.expire}")
    private long expire;// 令牌的过期时间
    @Value("${config.token.header}")
    private String header;// 令牌的头部名称

    /**
     * 创建Token令牌的方法
     * @param tokenDTO token实体对象
     * @return token令牌字符串
     */
    public String createToken(TokenDTO tokenDTO) {
        // 在系统当前时间戳上+过期时间*1000,然后构建一个日期对象
        Date date = new Date(System.currentTimeMillis() + expire * 1000);
        // 根据密钥secret,创建一个加密算法对象
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 设置头部
        Map map = new HashMap();
        map.put("Type", "Jwt");
        map.put("alg", "HS256");
        return JWT.create()
                .withHeader(map) // 指定令牌的头部
                .withClaim(header, JSONObject.toJSONString(tokenDTO)) // 指定令牌中的内容
                .withExpiresAt(date) // 指定令牌过期日期
                .sign(algorithm); // 指定签发令牌的算法
    }

    /**
     * 对Token进行解密, 校验
     * @param token token令牌字符串
     * @return token对象
     */
    public TokenDTO parseToken (String token){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        String tokenInfo = jwt.getClaim("token").asString();
        return JSON.parseObject(tokenInfo, TokenDTO.class);
    }
}
