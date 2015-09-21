package me.jiarui.shorturl.controller;

import com.google.common.base.Optional;
import me.jiarui.shorturl.common.GlobalConfig;
import me.jiarui.shorturl.entity.LongUrl;
import me.jiarui.shorturl.entity.ShortUrlPath;
import me.jiarui.shorturl.entity.UrlMapping;
import me.jiarui.shorturl.service.UrlMappingService;
import me.jiarui.shorturl.view.UrlMappingView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import static com.google.common.base.Preconditions.checkNotNull;

@RestController
@RequestMapping("/")
public class UrlMappingController {
    private final UrlMappingService urlMappingService;

    @Autowired
    public UrlMappingController(@Qualifier("cacheableUrlMappingServiceImpl") UrlMappingService urlMappingService) {
        this.urlMappingService = checkNotNull(urlMappingService);
    }

    @RequestMapping(method = RequestMethod.GET)
    public UrlMappingView getByLongUrl(@RequestParam String url) {
        LongUrl longUrl = new LongUrl(url);
        UrlMapping urlMapping = urlMappingService.getByLongUrl(longUrl);
        return entityToView(urlMapping);
    }


    @RequestMapping(value = "/{shortUrlPath}", method = RequestMethod.GET)
    public UrlMappingView getByShortUrl(@PathVariable("shortUrlPath") String shortUrlPath) {
        Optional<UrlMapping> urlMappingOptional = urlMappingService.getByShortUrlPath(new ShortUrlPath(shortUrlPath));
        if (urlMappingOptional.isPresent()) {
            return entityToView(urlMappingOptional.get());
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String Welcome() {
        return "Welcome " + GlobalConfig.getMachineId();
    }

    private UrlMappingView entityToView(UrlMapping urlMapping) {
        UrlMappingView urlMappingView = new UrlMappingView();
        String shortUrl = GlobalConfig.getUrlScheme() + "://" + GlobalConfig.getDomainName() + "/" + urlMapping.getShortUrlPath().getValue();
        urlMappingView.setShortUrl(shortUrl);
        urlMappingView.setLongUrl(urlMapping.getLongUrl().getValue());
        return urlMappingView;
    }
}