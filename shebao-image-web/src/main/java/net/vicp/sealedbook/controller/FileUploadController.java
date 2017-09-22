/**
 * Copyright (c) 2017, TalkingData and/or its affiliates. All rights reserved.
 */
package net.vicp.sealedbook.controller;

import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import net.vicp.sealedbook.common.utils.LogUtils;
import net.vicp.sealedbook.dao.provider.SystemFileProvider;
import net.vicp.sealedbook.utils.FastDFSClient;

/**
 * @author shitianshu on 2017/6/21 下午11:18.
 */
@Controller
public class FileUploadController {

    private static final Logger LOG = LoggerFactory.getLogger(FileUploadController.class);

    @Resource
    private SystemFileProvider systemFileProvider;

    @ResponseBody
    @RequestMapping(name = "/file/upload", method = RequestMethod.POST)
    private String fildUpload(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {

        LogUtils.info(LOG, "接收到文件", "originalFileName", file.getOriginalFilename());

        String filePath = FastDFSClient.uploadFile(file.getBytes(), file.getOriginalFilename());
        LogUtils.debug(LOG, "文件写入FastDFS", "filePath", filePath);

        if(null == filePath) {
            return "上传失败";
        }
        String fileCode = UUID.randomUUID().toString().replaceAll("-", "");

        systemFileProvider.insert(filePath, fileCode);

        return "http://220.248.55.202:5580/load/image/" + fileCode;
    }

}
