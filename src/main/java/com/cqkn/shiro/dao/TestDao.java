package com.cqkn.shiro.dao;

import com.cqkn.shiro.entity.User;
import com.leyongleshi.commons.utils.MapToBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author chengliang
 * @date 2020/3/9 10:08
 */
@Repository
public class TestDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String test(){
        String sql = "select * from shiro_user where phone = ?";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, "15196705987");
        List<User> user = MapToBeanUtils.convertMapToBean(maps,User.class);
        User user1 = user.get(0);
        System.out.println(user1.getPassword());
        return user1.getPhone();

    }
}
