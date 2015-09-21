package me.jiarui.shorturl.service.impl;


// see: http://stackoverflow.com/questions/10299901/how-do-sites-like-goo-gl-or-jsfiddle-generate-their-url-codes/

import me.jiarui.shorturl.service.PermuteIntService;
import org.springframework.stereotype.Service;

@Service
public class PermuteIntServiceImpl implements PermuteIntService {
    @Override
    public int permuteInt(int x) {
        int l1 = (x >> 16) & 65535;
        int r1 = x & 65535;

        for (int i = 0; i < 3; i++) {
            int l2 = r1;
            int r2 = l1 ^ (int) (RoundFunction(r1) * 65535);
            l1 = l2;
            r1 = r2;
        }
        return ((r1 << 16) + l1);
    }

    private double RoundFunction(int x) {
        // Must be a function in the mathematical sense (x=y implies f(x)=f(y))
        // but it doesn't have to be reversible.
        // Must return a value between 0 and 1
        return ((1369 * x + 150889) % 714025) / 714025.0;
    }
}
