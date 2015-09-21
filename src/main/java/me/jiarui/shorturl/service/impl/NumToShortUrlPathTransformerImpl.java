package me.jiarui.shorturl.service.impl;

import com.google.common.primitives.UnsignedInteger;
import me.jiarui.shorturl.entity.ShortUrlPath;
import me.jiarui.shorturl.service.Base62Converter;
import me.jiarui.shorturl.service.NumToShortUrlPathTransformer;
import me.jiarui.shorturl.service.PermuteIntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class NumToShortUrlPathTransformerImpl implements NumToShortUrlPathTransformer {
    private final PermuteIntService permuteIntService;

    private final Base62Converter base62Converter;

    @Autowired
    public NumToShortUrlPathTransformerImpl(PermuteIntService permuteIntService, Base62Converter base62Converter) {
        this.permuteIntService = checkNotNull(permuteIntService);
        this.base62Converter = checkNotNull(base62Converter);
    }


    @Override
    public ShortUrlPath transform(int num) {
        UnsignedInteger permutedInt = UnsignedInteger.fromIntBits(permuteIntService.permuteInt(num));

        String shortUrlPath = base62Converter.fromBase10(permutedInt);

        return new ShortUrlPath(shortUrlPath);
    }
}
