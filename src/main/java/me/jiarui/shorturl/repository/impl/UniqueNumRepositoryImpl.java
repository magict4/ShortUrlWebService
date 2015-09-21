package me.jiarui.shorturl.repository.impl;

import me.jiarui.shorturl.repository.UniqueNumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Sher10ck on 7/20/15.
 */
@Repository("uniqueNumRepository")
public class UniqueNumRepositoryImpl implements UniqueNumRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UniqueNumRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(checkNotNull(dataSource));
    }

    @Override
    public void add(int num) {
        String sql = "INSERT INTO unique_nums (num) VALUES(?)";
        Object[] values = {num};
        int[] types = {Types.INTEGER};
        jdbcTemplate.update(sql, values, types);
    }

    @Override
    public int getCountOfNumsEndingWithSpecificDigits(int endingDigits) {
        String sql = "SELECT COUNT(*) FROM unique_nums WHERE num % 10 = " + endingDigits;
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
