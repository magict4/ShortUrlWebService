package me.jiarui.shorturl.service.impl;

import com.google.common.primitives.UnsignedInteger;
import me.jiarui.shorturl.service.Base62Converter;
import org.springframework.stereotype.Service;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class Base62ConverterImp implements Base62Converter {

    private final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private final UnsignedInteger BASE = UnsignedInteger.fromIntBits(ALPHABET.length());

    public String fromBase10(UnsignedInteger num) {
        checkNotNull(num);
        StringBuilder sb = new StringBuilder();
        do {
            UnsignedInteger rem = num.mod(BASE);
            sb.append(ALPHABET.charAt(rem.intValue()));
            num = num.dividedBy(BASE);
        }
        while (num.compareTo(UnsignedInteger.ZERO) > 0);
        return sb.reverse().toString();
    }
}