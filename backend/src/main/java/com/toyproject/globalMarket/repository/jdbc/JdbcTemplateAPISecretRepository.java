package com.toyproject.globalMarket.repository.jdbc;

import com.toyproject.globalMarket.entity.APISecretEntity;
import com.toyproject.globalMarket.entity.MarketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.awt.*;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class JdbcTemplateAPISecretRepository {
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplateAPISecretRepository (DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long create(String secret, Long prev){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO api_secret (secret, prev) VALUES(?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con
                    .prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, secret);
            ps.setLong(2, prev);
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


    //C
    //R
    //U selfReference
}
