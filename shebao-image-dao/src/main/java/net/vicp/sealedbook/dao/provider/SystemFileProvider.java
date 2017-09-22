/**
 * Copyright (c) 2017, TalkingData and/or its affiliates. All rights reserved.
 */
package net.vicp.sealedbook.dao.provider;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import net.vicp.sealedbook.common.utils.LogUtils;
import net.vicp.sealedbook.dao.auto.criteria.SystemFileExample;
import net.vicp.sealedbook.dao.auto.mapper.SystemFileMapper;
import net.vicp.sealedbook.dao.auto.model.SystemFileMo;

/**
 * @author shitianshu on 2017/6/22 上午12:23.
 */
@Service
public class SystemFileProvider {

    private static final Logger LOG = LoggerFactory.getLogger(SystemFileProvider.class);

    @Resource
    private SystemFileMapper systemFileMapper;

    public void insert(String filePath, String fileCode) {
        SystemFileMo systemFile = new SystemFileMo();
        systemFile.setFileCode(fileCode);
        systemFile.setFilePath(filePath);
        try {
            systemFileMapper.insert(systemFile);
        } catch(Throwable e) {
            LogUtils.error(LOG, e, "文件映射关系写入异常");
        }
    }

    public SystemFileMo fetchSystemFileByCode(String fileCode) {
        SystemFileExample example = new SystemFileExample();
        SystemFileExample.Criteria criteria = example.createCriteria();
        criteria.andFileCodeEqualTo(fileCode);

        List<SystemFileMo> systemFileMoList = systemFileMapper.selectByExample(example);
        if(null == systemFileMoList) {
            return null;
        }
        return systemFileMoList.get(0);
    }
}
