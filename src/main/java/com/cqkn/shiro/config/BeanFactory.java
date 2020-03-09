package com.cqkn.shiro.config;

import com.cqkn.shiro.aop.BaseAuthAndLoginAop;
import com.cqkn.shiro.aop.LoginAop;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chengliang
 * @date 2020/3/9 14:36
 */
@Configuration
public class BeanFactory {

    @Bean
    public LoginAop baseAuthAndLoginAop(@Value("${check.token}") boolean checkToken){
        LoginAop loginAop = new LoginAop(checkToken);
        return loginAop;
    }
}
