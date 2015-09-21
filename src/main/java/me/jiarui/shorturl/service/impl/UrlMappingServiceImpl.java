package me.jiarui.shorturl.service.impl;

import com.google.common.base.Optional;
import me.jiarui.shorturl.entity.LongUrl;
import me.jiarui.shorturl.entity.ShortUrlPath;
import me.jiarui.shorturl.entity.UrlMapping;
import me.jiarui.shorturl.repository.UrlMappingRepository;
import me.jiarui.shorturl.service.ShortUrlPathProvider;
import me.jiarui.shorturl.service.UrlMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by jerry.zhang on 7/6/2015.
 */
@Service("urlMappingServiceImpl")
public class UrlMappingServiceImpl implements UrlMappingService {

    private final ShortUrlPathProvider shortUrlPathProvider;

    private final UrlMappingRepository urlMappingRepository;

    @Autowired
    public UrlMappingServiceImpl(ShortUrlPathProvider shortUrlPathProvider, UrlMappingRepository urlMappingRepository) {
        this.shortUrlPathProvider = checkNotNull(shortUrlPathProvider);
        this.urlMappingRepository = checkNotNull(urlMappingRepository);
    }


    @Override
    public UrlMapping getByLongUrl(LongUrl longUrl) {
        checkNotNull(longUrl);
        Optional<UrlMapping> urlMappingOptional = urlMappingRepository.getByLongUrl(longUrl);
        if (urlMappingOptional.isPresent()) {
            return urlMappingOptional.get();
        } else {
            ShortUrlPath shortUrlPath = shortUrlPathProvider.nextShortUrlPath();
            UrlMapping urlMapping = new UrlMapping(shortUrlPath, longUrl);
            urlMappingRepository.add(urlMapping);
            return urlMapping;
        }
    }

    @Override
    public Optional<UrlMapping> getByShortUrlPath(ShortUrlPath shortUrlPath) {
        return urlMappingRepository.getByShortUrlPath(shortUrlPath);
    }
}
