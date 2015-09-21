package me.jiarui.shorturl.repository.impl;

import com.google.common.base.Optional;
import me.jiarui.shorturl.dto.UrlMappingDto;
import me.jiarui.shorturl.entity.LongUrl;
import me.jiarui.shorturl.entity.ShortUrlPath;
import me.jiarui.shorturl.entity.UrlMapping;
import me.jiarui.shorturl.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Repository
public class UrlMappingRepositoryImpl implements UrlMappingRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UrlMappingRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(checkNotNull(dataSource));
    }

    @Override
    public Optional<UrlMapping> getByLongUrl(LongUrl longUrl) {
        return queryForObject("long_url", longUrl.getValue());
    }

    @Override
    public Optional<UrlMapping> getByShortUrlPath(ShortUrlPath shortUrlPath) {
        return queryForObject("short_url_path", shortUrlPath.getValue());
    }

    private Optional<UrlMapping> queryForObject(String columnName, String value) {
        String sql = "SELECT short_url_path, long_url FROM url_mappings WHERE " + columnName + " = ?";

        List<UrlMapping> urlMappings = jdbcTemplate.query(sql, new Object[]{value}, new RowMapper<UrlMapping>() {
            @Override
            public UrlMapping mapRow(ResultSet resultSet, int i) throws SQLException {
                UrlMappingDto urlMappingDto = new UrlMappingDto();
                urlMappingDto.setShortUrlPath(resultSet.getString("short_url_path"));
                urlMappingDto.setLongUrl(resultSet.getString("long_url"));
                return dtoToEntity(urlMappingDto);
            }
        });

        if (urlMappings.size() == 0) {
            return Optional.absent();
        }

        if (urlMappings.size() == 1) {
            return Optional.of(urlMappings.get(0));
        }

        throw new IncorrectResultSizeDataAccessException(1);
    }

    @Override
    public void add(UrlMapping urlMapping) {
        UrlMappingDto urlMappingDto = entityToDto(urlMapping);
        String sql = "INSERT INTO url_mappings (short_url_path, long_url) VALUES(?, ?)";
        Object[] values = {urlMappingDto.getShortUrlPath(), urlMappingDto.getLongUrl()};
        int[] types = {Types.VARCHAR, Types.VARCHAR};
        jdbcTemplate.update(sql, values, types);
    }

    private UrlMapping dtoToEntity(UrlMappingDto urlMappingDto) {
        ShortUrlPath shortUrlPath = new ShortUrlPath(urlMappingDto.getShortUrlPath());
        LongUrl longUrl = new LongUrl(urlMappingDto.getLongUrl());
        return new UrlMapping(shortUrlPath, longUrl);
    }

    private UrlMappingDto entityToDto(UrlMapping urlMapping) {
        UrlMappingDto urlMappingDto = new UrlMappingDto();
        urlMappingDto.setShortUrlPath(urlMapping.getShortUrlPath().getValue());
        urlMappingDto.setLongUrl(urlMapping.getLongUrl().getValue());
        return urlMappingDto;
    }
}
