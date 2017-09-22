/**
 * Copyright (c) 2017, TalkingData and/or its affiliates. All rights reserved.
 */
package net.vicp.sealedbook.controller;

import java.io.IOException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.vicp.sealedbook.common.utils.LogUtils;
import net.vicp.sealedbook.dao.auto.model.SystemFileMo;
import net.vicp.sealedbook.dao.provider.SystemFileProvider;
import net.vicp.sealedbook.utils.FastDFSClient;

/**
 * @author shitianshu on 2017/6/21 下午11:41.
 */
@Controller
public class FileDownloadController {

    private static final Logger LOG = LoggerFactory.getLogger(FileDownloadController.class);

    @Resource
    private SystemFileProvider systemFileProvider;

    @ResponseBody
    @RequestMapping(value = "/load/image/{fileCode}", produces = "image/jpeg;charset=UTF-8")
    public byte[] loadImage(@PathVariable String fileCode) throws IOException {
        if (!StringUtils.hasText(fileCode)) {
            return null;
        }

        SystemFileMo systemFile = systemFileProvider.fetchSystemFileByCode(fileCode);
        LogUtils.debug(LOG, "获得映射关系", "systemFile", systemFile);
        if (null == systemFile) {
            return null;
        }
        return FastDFSClient.fetchFileByteArray(systemFile.getFilePath());
    }

}
