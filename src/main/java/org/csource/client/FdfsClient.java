package org.csource.client;

import org.csource.fastdfs.*;
import org.csource.utils.CommonUtil;
import org.csource.utils.FdfsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class FdfsClient {
    static Logger log = LoggerFactory.getLogger(FdfsClient.class);

    private static final String DEFAULT_CONFIG = "fdfs.properties";

    public FdfsClient() {
        try {
            ClientGlobal.initByProperties(DEFAULT_CONFIG);
            log.info("fdfs config:{}", ClientGlobal.configInfo());
        } catch (Exception e) {
            log.error("fastdfs 初始化配置失敗，请检查" + DEFAULT_CONFIG + "配置", e);
            throw new RuntimeException(e);
        }
    }

    public FdfsClient(String config) {
        try {
            Properties properties = new Properties();
            properties.load(CommonUtil.loadFile(config));
            ClientGlobal.initByProperties(properties);
            log.info("fdfs config:{}", ClientGlobal.configInfo());
        } catch (Exception e) {
            log.error("fastdfs 初始化配置失敗，请检查" + config + "配置", e);
            throw new RuntimeException(e);
        }
    }

    public StorageClient1 getClient() throws Exception {
        return new StorageClient1();
    }

    /**
     * 上传文件
     *
     * @param input
     * @param fileName
     */
    public String upload(String group, InputStream input, String fileName) throws Exception {
        String fileExtName = FdfsUtil.getExtension(fileName);
        return getClient().upload_file1(group, input.available(), new UploadStream(input, input.available()), fileExtName, null);
    }


    /**
     * 断点续传文件api( upload,append,modify)
     */
    public String uploadAppendFile(String group, InputStream input, String fileName) throws Exception {
        String fileExtName = FdfsUtil.getExtension(fileName);
        return getClient().upload_appender_file1(group, input.available(), new UploadStream(input, input.available()), fileExtName, null);

    }

    public void append(InputStream input, String fileId) throws Exception {
        getClient().append_file1(fileId, input.available(), new UploadStream(input, input.available()));
    }

    public void modifyFile(InputStream input, Long offset, String fileId) throws Exception {
        getClient().modify_file1(fileId, offset, input.available(), new UploadStream(input, input.available()));

    }

    /**
     * 断点下载
     */
    public void download(String fileId, Long start, Long len, OutputStream out) throws Exception {
        try {
            getClient().download_file1(fileId, start, len, new DownloadStream(out));
        } finally {
            out.close();
        }
    }

    /**
     * 从文件上传
     */

    public String uploadSlave(String masterFileId, InputStream input, String filePrefixName, String fileName)
            throws Exception {
        String fileExtName = FdfsUtil.getExtension(fileName);
        return getClient().upload_file1(masterFileId, filePrefixName, input.available(), new UploadStream(input, input.available()), fileExtName, null);
    }


    /**
     * 下载文件
     */
    public void download(String fileId, OutputStream out) throws Exception {
        try {
            getClient().download_file1(fileId, new DownloadStream(out));
        } finally {
            out.close();
        }
    }


    /**
     * 删除
     */
    public int delete(String fileId) throws Exception {// 0成功
        return getClient().delete_file1(fileId);
    }


    /**
     * 获取文件信息
     *
     * @param fileId
     * @return
     * @throws Exception
     */
    public FileInfo getFileInfo(String fileId) throws Exception {
        return getClient().get_file_info1(fileId);
    }


}
