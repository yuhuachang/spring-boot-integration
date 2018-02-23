package com.example.demo;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyService {

    @Autowired
    @Qualifier("xaDataSource")
    private DataSource h2DataSource;

    private JdbcTemplate jdbcTemplate;

    private void setup() {
        if (jdbcTemplate == null) {
            jdbcTemplate = new JdbcTemplate(h2DataSource);

            try {
                jdbcTemplate.update("create table test_table ( name varchar(20) primary key, phone varchar(20) )");
                jdbcTemplate.update("insert into test_table (name, phone) values ('John', '123456')");
                jdbcTemplate.update("insert into test_table (name, phone) values ('Marry', '999012')");
            } catch (DataAccessException e) {
                // ignore.
            }
        }
    }

    @Transactional
    public List<ContactInfo> select() {
        setup();
        return jdbcTemplate.query("select * from test_table", new BeanPropertyRowMapper<ContactInfo>(ContactInfo.class));
    }

    @Transactional
    public Response insert(ContactInfo req) {
        setup();
        int updateCount = jdbcTemplate.update("insert into test_table (name, phone) values (?, ?)", req.getName(), req.getPhone());
        
        Response response = new Response();
        response.setMessage("updateCount = " + updateCount);
        return response;
    }
    
    @Transactional
    public Response update(String name, ContactInfo req) {
        setup();
        int updateCount = jdbcTemplate.update("update test_table set name = ?, phone = ? where name = ?", req.getName(), req.getPhone(), name);
        
        Response response = new Response();
        response.setMessage("updateCount = " + updateCount);
        return response;
    }
    
    @Transactional
    public Response delete(String name) {
        setup();
        int updateCount = jdbcTemplate.update("delete from test_table where name = ?", name);
        
        Response response = new Response();
        response.setMessage("updateCount = " + updateCount);
        return response;
    }
}
