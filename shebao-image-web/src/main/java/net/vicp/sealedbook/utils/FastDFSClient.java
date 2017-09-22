/**
 * Copyright (c) 2017, TalkingData and/or its affiliates. All rights reserved.
 */
package net.vicp.sealedbook.utils;

import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.vicp.sealedbook.common.utils.LogUtils;

/**
 * @author shitianshu on 2017/6/20 下午7:54.
 */
public class FastDFSClient {

    private static final Logger LOG = LoggerFactory.getLogger(FastDFSClient.class);

    private static StorageClient1 storageClient1 = null;

    // 初始化FastDFS Client
    static {
        try {
            ClientGlobal.initByProperties(SystemConfigUtils.fetchFastDFSConfig());
            TrackerClient trackerClient = new TrackerClient(ClientGlobal.g_tracker_group);
            TrackerServer trackerServer = trackerClient.getConnection();
            if (trackerServer == null) {
                throw new IllegalStateException("getConnection return null");
            }

            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
            if (storageServer == null) {
                throw new IllegalStateException("getStoreStorage return null");
            }

            storageClient1 = new StorageClient1(trackerServer, storageServer);

        } catch (Exception e) {
            LogUtils.error(LOG, e, "init FastDFS Client error.");
        }
    }

    /**
     * 上传文件
     *
     * @param buff 文件对象
     * @param fileName 文件名
     */
    public static String uploadFile(byte[] buff, String fileName) {
        return uploadFile(buff, fileName, null);
    }

    /**
     * 上传文件
     *
     * @param file 文件对象
     * @param fileName 文件名
     * @param metaList 文件元数据
     */
    public static String uploadFile(File file, String fileName, Map<String, String> metaList) {
        try {
            byte[] buff = IOUtils.toByteArray(new FileInputStream(file));
            return uploadFile(buff, fileName, metaList);
        } catch (Exception e) {
            LogUtils.error(LOG, e, "upload file error.");
        }
        return null;
    }

    public static String uploadFile(byte[] buff, String fileName, Map<String, String> metaList) {
        try {
            NameValuePair[] nameValuePairs = null;
            if (metaList != null) {
                nameValuePairs = new NameValuePair[metaList.size()];
                int index = 0;
                for (Iterator<Entry<String, String>> iterator = metaList.entrySet().iterator();
                    iterator.hasNext(); ) {
                    Map.Entry<String, String> entry = iterator.next();
                    String name = entry.getKey();
                    String value = entry.getValue();
                    nameValuePairs[index++] = new NameValuePair(name, value);
                }
            }
            return storageClient1.upload_file1(buff, fetchFileExtension(fileName), nameValuePairs);
        } catch (Exception e) {
            LogUtils.error(LOG, e, "upload file error.");
        }
        return null;
    }

    private static String fetchFileExtension(String fileName) {
        if(!StringUtils.hasText(fileName)) {
            return "";
        }
        int index = fileName.lastIndexOf(".");
        if(-1 == index) {
            return "";
        }
        return fileName.substring(index + 1);
    }

    /**
     * 获取文件元数据
     *
     * @param fileId 文件ID
     */
    public static Map<String, String> getFileMetadata(String fileId) {
        try {
            NameValuePair[] metaList = storageClient1.get_metadata1(fileId);
            if (metaList != null) {
                HashMap<String, String> map = new HashMap<String, String>();
                for (NameValuePair metaItem : metaList) {
                    map.put(metaItem.getName(), metaItem.getValue());
                }
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除文件
     *
     * @param fileId 文件ID
     * @return 删除失败返回-1，否则返回0
     */
    public static int deleteFile(String fileId) {
        try {
            return storageClient1.delete_file1(fileId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static byte[] fetchFileByteArray(String fileId) {
        try {
            byte[] content = storageClient1.download_file1(fileId);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        File file = new File("~/Download/IMG_6548.JPG");
        Map<String, String> metaList = new HashMap<String, String>();
        metaList.put("width", "1024");
        metaList.put("height", "768");
        metaList.put("author", "杨信");
        metaList.put("date", "20161018");
        String fid = FastDFSClient.uploadFile(file, file.getName(), metaList);
        System.out.println("upload local file " + file.getPath() + " ok, fileid=" + fid);
        //上传成功返回的文件ID： group1/M00/00/00/wKgAyVgFk9aAB8hwAA-8Q6_7tHw351.jpg
    }
}
