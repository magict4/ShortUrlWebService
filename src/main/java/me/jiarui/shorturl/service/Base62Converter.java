package me.jiarui.shorturl.service;

import com.google.common.primitives.UnsignedInteger;

public interface Base62Converter {
    String fromBase10(UnsignedInteger num);

}
