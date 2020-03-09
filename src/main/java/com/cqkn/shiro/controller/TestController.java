package com.cqkn.shiro.controller;

import com.cqkn.shiro.dao.TestDao;
import com.leyongleshi.commons.web.GeneralResponse;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author chengliang
 * @date 2020/3/9 10:24
 */
@RestController
@RequestMapping("/user")
public class TestController {


    @Autowired
    private TestDao testDao;

    @PostMapping("/get")
    public GeneralResponse<String> getBuyMethod() {
        String test = testDao.test();
        return GeneralResponse.successResponse(test);
    }
}
