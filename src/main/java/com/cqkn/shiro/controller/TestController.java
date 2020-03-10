package com.cqkn.shiro.controller;

import com.cqkn.shiro.aop.Login;
import com.cqkn.shiro.dao.TestDao;
import com.cqkn.shiro.entity.User;
import com.leyongleshi.commons.web.GeneralResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author chengliang
 * @date 2020/3/9 10:24
 */
@RestController
@RequestMapping("/user")
public class TestController {


    @Autowired
    private TestDao testDao;

    @Login
    @GetMapping("/get")
    public GeneralResponse<String> getBuyMethod(HttpServletRequest request) {
        HttpSession session= request.getSession(true);
        User user = (User) session.getAttribute("user");
        if (user == null){
            return GeneralResponse.failedResponse("请先登录");
        }
        String test = testDao.test(user.getPhone());
        return GeneralResponse.successResponse(test);
    }

    @GetMapping("/login")
    public GeneralResponse<String> login(
            User user,HttpSession session){
        Subject subject = SecurityUtils.getSubject();
        try {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getPhone(), user.getPassword());
            subject.login(usernamePasswordToken);
        }catch (Exception e){
            return GeneralResponse.failedResponse("账号霍密码错误");
        }
        session.setAttribute("user",user);
        return GeneralResponse.successResponse("success");
    }

    @Login
    @GetMapping("/logout")
    public GeneralResponse<Boolean> logout(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        session.removeAttribute("user");
        return GeneralResponse.successResponse(true);
    }
}
