package com.example.demo.db;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import bitronix.tm.resource.jdbc.PoolingDataSource;

@Configuration
public class h2DataSourceConfig {

    @Value("${spring.h2.url}")
    private String url;

    @Value("${spring.h2.username}")
    private String username;

    @Value("${spring.h2.password}")
    private String password;

    private int maxPoolSize = 5;

    @Primary
    @Bean(name = "nonXaDataSource", destroyMethod = "close")
    public DataSource getNonXaDataSource() {
        PoolingDataSource poolingDataSource = new PoolingDataSource();
        poolingDataSource.setClassName(bitronix.tm.resource.jdbc.lrc.LrcXADataSource.class.getName());
        poolingDataSource.setUniqueName("nonXaDataSource");
        Properties props = new Properties();
        props.put("driverClassName", org.h2.Driver.class.getName());
        props.put("url", url);
        props.put("user", username);
        props.put("password", password);
        poolingDataSource.setDriverProperties(props);
        poolingDataSource.setAllowLocalTransactions(true);
        poolingDataSource.setTestQuery("select 1");
        poolingDataSource.setMaxPoolSize(this.maxPoolSize);
        poolingDataSource.init();
        return poolingDataSource;
    }
    
    @Bean(name = "xaDataSource", destroyMethod = "close")
    public DataSource getXaDataSource() {
        PoolingDataSource poolingDataSource = new PoolingDataSource();
        poolingDataSource.setClassName(org.h2.jdbcx.JdbcDataSource.class.getName());
        poolingDataSource.setUniqueName("xaDataSource");
        Properties props = new Properties();
        props.put("URL", url);
        props.put("user", username);
        props.put("password", password);
        poolingDataSource.setDriverProperties(props);
        poolingDataSource.setAllowLocalTransactions(true);
        poolingDataSource.setTestQuery("select 1");
        poolingDataSource.setMaxPoolSize(this.maxPoolSize);
        poolingDataSource.init();
        return poolingDataSource;
    }

}
