package me.jiarui.shorturl.service.impl;


import me.jiarui.shorturl.common.GlobalConfig;
import me.jiarui.shorturl.repository.UniqueNumRepository;
import me.jiarui.shorturl.service.UniqueNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by jerry.zhang on 7/6/2015.
 */
@Service
public class UniqueNumServiceImpl implements UniqueNumService {
    private final AtomicInteger atomicInteger;

    private final UniqueNumRepository uniqueNumRepository;

    @Autowired
    public UniqueNumServiceImpl(UniqueNumRepository uniqueNumRepository) {
        this.uniqueNumRepository = checkNotNull(uniqueNumRepository);
        int count = uniqueNumRepository.getCountOfNumsEndingWithSpecificDigits(GlobalConfig.getMachineId());
        this.atomicInteger = new AtomicInteger(count * GlobalConfig.getMaxMachineNum() + GlobalConfig.getMachineId());
    }


    @Override
    public int nextNum() {
        int nextInt = atomicInteger.getAndAdd(GlobalConfig.getMaxMachineNum());
        if (nextInt < 0) {
            throw new ArithmeticException(String.format("expected non negative integer, but actual : %s", nextInt));
        }
        uniqueNumRepository.add(nextInt);
        return nextInt;
    }
}
