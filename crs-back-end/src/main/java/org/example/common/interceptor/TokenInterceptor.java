package org.example.common.interceptor;

import org.example.common.exception.CustomException;
import org.example.common.response.ResponseEnum;
import org.example.common.token.TokenDTO;
import org.example.common.token.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Token校验拦截器
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Resource
    TokenService tokenService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        setHeader(request, response);
        // 从请求报文中找到存储Token令牌的Cookie
        Cookie[] cookies = request.getCookies();
        String token = null;
        if( cookies != null ) {
            for (Cookie cookie : cookies) {
                if ( "token".equals(cookie.getName()) ){
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if( token != null) {
            // 解析如果失败, 就会抛出异常, 这里不对异常进行try...catch, 把异常交给全局异常处理器进行处理
            TokenDTO tokenDTO = tokenService.parseToken(token);
        } else {
            // 如果token为空, 手动抛出异常, 让全局异常处理器进行处理
            throw new CustomException(ResponseEnum.TOKEN_INVALID);
        }
        // 解析成功, 返回true, 表示拦截器对请求进行放行
        return true;
    }

    private void setHeader(HttpServletRequest request, HttpServletResponse response) {
        //跨域的header设置
        response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
        // response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        response.setHeader("Access-Control-Max-Age", "1800");
        //防止乱码，适用于传输JSON数据
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.setStatus(HttpStatus.OK.value());
    }
}
