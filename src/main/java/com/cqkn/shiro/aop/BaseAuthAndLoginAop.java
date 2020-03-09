package com.cqkn.shiro.aop;

import com.cqkn.shiro.entity.User;
import com.leyongleshi.commons.exception.LyBaseException;
import com.leyongleshi.commons.web.GeneralResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * @author chengliang
 * @date 2020/3/9 14:15
 */
@Slf4j
public class BaseAuthAndLoginAop {

    private boolean checkToken;

    public BaseAuthAndLoginAop(boolean checkToken) {
        System.out.println("checkToken");
        this.checkToken = checkToken;
    }

    public Object around(ProceedingJoinPoint pjp) throws Throwable{
        System.out.println("aop 拦截成功");

        HttpServletRequest request = null;
        Object result = null;
        long checkTokenTime = -1;
        long controllerTime = -1;
        Throwable ex = null;
        Method method = null;

        try {
            Object target = pjp.getTarget();
            long checkTokenStartTime = System.currentTimeMillis();

            //获取request对象
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            Signature signature = pjp.getSignature();
            MethodSignature ms = (MethodSignature) signature;
            method = ms.getMethod();

            if (checkToken) {
                Login login = method.getAnnotation(Login.class);
                if (login != null){
                    //检查是否登录
                    HttpSession session = request.getSession();
                    User user = (User) session.getAttribute("user");
                    if (user == null){
                        return GeneralResponse.failedResponse("请先登录!");
                    }
                }
            }
            result = pjp.proceed();
            //如果正常登录或者不需要检验登录信息，则记录访问日志等!
        }catch (Throwable e){
            ex = e;
            result = exception(e);
        }

        if (!(result instanceof GeneralResponse)){
            return GeneralResponse.failedResponse("Controller 需要继承至BaseController!");
        }

        if (result instanceof GeneralResponse){
            GeneralResponse response = (GeneralResponse) result;
            if (!"success".equalsIgnoreCase(response.getMsg()) || ex != null) {
                if (ex != null) {
                    log.info(ex.getMessage(),ex);
                } else {
                    log.info(ex.getMessage(),ex);
                }
            }
        }
        return result;
    }


    public GeneralResponse exception(Throwable e) {
        if (!(e instanceof LyBaseException)) {
            e = new LyBaseException("程序异常,正在处理中********", e);
        }
        //这里可以发邮件通知程序员
        GeneralResponse result = GeneralResponse.unknownExceptionResponse(e);
        return result;
    }
}
