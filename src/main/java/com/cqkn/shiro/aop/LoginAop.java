package com.cqkn.shiro.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author chengliang
 * @date 2020/3/9 14:11
 */
@Slf4j
@Aspect
public class LoginAop extends BaseAuthAndLoginAop {


    public LoginAop(boolean checkToken) {
        super(checkToken);
    }

    @Override
    @Around(value = "@annotation(org.springframework.web.bind.annotation.RequestMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("进入aop");
        return super.around(pjp);
    }
}
