package com.cqkn.shiro.config;

import com.leyongleshi.commons.db.DruidClusterDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author chengliang
 * @date 2020/3/9 9:41
 */
@Configuration
public class JdbcConfigration implements EnvironmentAware {

    private Environment env;

    /**
     * 数据源
     */
    @Bean(initMethod = "init", destroyMethod = "close")
    public DruidClusterDataSource druidDataSource() {
        String dbDriver = env.getProperty("db.driver");
        Integer dbInitialSize = env.getProperty("db.initialSize", Integer.class);
        Integer dbMinIdle = env.getProperty("db.minIdle", Integer.class);
        Integer dbMaxActive = env.getProperty("db.maxActive", Integer.class);
        String dbMasterConfig = env.getProperty("db.masterConfig");
        String dbSlaveConfig = env.getProperty("db.slaveConfig");
        DruidClusterDataSource dataSource = new DruidClusterDataSource(dbDriver, dbInitialSize, dbMinIdle, dbMaxActive, dbMasterConfig, dbSlaveConfig);
        return dataSource;
    }

    /**
     * 初始化 jdbcTemplate
     */
    @Bean
    public JdbcTemplate jdbcTemplate(@Autowired DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

    @Override
    public void setEnvironment(Environment environment) {
        env = environment;
    }

}
