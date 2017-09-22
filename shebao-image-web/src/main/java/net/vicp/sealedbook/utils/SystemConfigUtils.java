/**
 * Copyright (c) 2017, TalkingData and/or its affiliates. All rights reserved.
 */
package net.vicp.sealedbook.utils;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.vicp.sealedbook.common.utils.LogUtils;

/**
 * @author shitianshu on 2017/6/20 下午7:58.
 */
public class SystemConfigUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemConfigUtils.class);

    private final static Properties config;

    static {
        config = new Properties();
        InputStream stream = null;
        try {
            stream = SystemConfigUtils.class.getResourceAsStream("/fastdfs_client.properties");
            config.load(stream);
        } catch (Exception e) {
            LogUtils.error(LOGGER, e, "parse fdfs_client error.");
        } finally {
            if (null != stream) {
                try {
                    stream.close();
                } catch (Exception e) {
                    LogUtils.error(LOGGER, e, "close fdfs_client error.");
                }
            }
        }
    }

    public static Properties fetchFastDFSConfig() {
        return config;
    }

}
