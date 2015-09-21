package me.jiarui.shorturl.service.impl;


import me.jiarui.shorturl.entity.ShortUrlPath;
import me.jiarui.shorturl.service.NumToShortUrlPathTransformer;
import me.jiarui.shorturl.service.ShortUrlPathProvider;
import me.jiarui.shorturl.service.UniqueNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by jerry.zhang on 7/6/2015.
 */
@Service
public class ShortUrlPathProviderImpl implements ShortUrlPathProvider {

    private final UniqueNumService uniqueNumService;

    private final NumToShortUrlPathTransformer numToShortUrlPathTransformer;

    @Autowired
    public ShortUrlPathProviderImpl(UniqueNumService uniqueNumService, NumToShortUrlPathTransformer numToShortUrlPathTransformer) {

        this.uniqueNumService = checkNotNull(uniqueNumService);

        this.numToShortUrlPathTransformer = checkNotNull(numToShortUrlPathTransformer);
    }

    @Override
    public ShortUrlPath nextShortUrlPath() {
        int uniqueNum = uniqueNumService.nextNum();

        return this.numToShortUrlPathTransformer.transform(uniqueNum);
    }
}
