package com.toyproject.globalMarket.repository.jdbc;

import com.toyproject.globalMarket.entity.CategoryNaverEntity;
import com.toyproject.globalMarket.entity.MarketEntity;
import org.checkerframework.checker.units.qual.C;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;

@Repository
public class JdbcTemplateMarketRepository  {
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplateMarketRepository (DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public Long create(String username, String password, Long prev){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO market (username, password, api_secret) VALUES(?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con
                    .prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setLong(3, prev);
            return ps;
        },keyHolder);
        Number ret = keyHolder.getKey().longValue();
        return (Long) ret;
    }

    public String read (Long id){
        String sql = "SELECT u.secret FROM api_secret WHERE u.secret_id = ? LIMIT 1";
        String ret = (String) jdbcTemplate.queryForObject(sql, new Object[] {id}, String.class);
        return ret;
    }

    public Long updatePassword(Long id, String password){
        String sql = "UPDATE market SET password = ? WHERE id = ?";
        jdbcTemplate.update(sql, password, id);
        return id;
    }
    //C
    //R
    //U password
    //D
}
