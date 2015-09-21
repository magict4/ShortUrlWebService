package me.jiarui.shorturl.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by jerry.zhang on 7/6/2015.
 */
public class GlobalConfig {
    private static final int maxShortUrlPathLength;
    private static final String urlScheme;
    private static final String domainName;
    private static final int maxMachineNum;
    private static final int machineId;
    private static final String redisHost;
    private static final int redisPort;
    private static final int expireInSeconds;

    static {
        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream in = classLoader.getResourceAsStream("/config.properties");
        try {
            properties.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        maxShortUrlPathLength = Integer.valueOf(properties.getProperty("maxShortUrlPathLength"));
        urlScheme = properties.getProperty("urlScheme");
        domainName = properties.getProperty("domainName");
        maxMachineNum = Integer.valueOf(properties.getProperty("maxMachineNum"));
        machineId = Integer.valueOf(properties.getProperty("machineId"));
        redisHost = properties.getProperty("redisHost");
        redisPort = Integer.valueOf(properties.getProperty("redisPort"));
        expireInSeconds = Integer.valueOf(properties.getProperty("expireInSeconds"));
    }

    public static int getMaxShortUrlPathLength() {
        return maxShortUrlPathLength;
    }

    public static String getUrlScheme() {
        return urlScheme;
    }

    public static String getDomainName() {
        return domainName;
    }

    public static int getMaxMachineNum() {
        return maxMachineNum;
    }

    public static int getMachineId() {
        return machineId;
    }

    public static String getRedisHost() {
        return redisHost;
    }

    public static int getRedisPort() {
        return redisPort;
    }

    public static int getExpireInSeconds() {
        return expireInSeconds;
    }
}
