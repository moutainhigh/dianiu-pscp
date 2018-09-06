/**
 *
 */
package com.edianniu.pscp.mis.service.dubbo;

import com.edianniu.pscp.mis.bean.FileUploadResult;

import java.util.Set;


/**
 * @author cyl
 */
public interface FileUploadService {
    /**
     * 文件上传
     *
     * @param fileName
     * @param content
     * @return
     */
    public FileUploadResult upload(String fileName, byte[] content);

    /**
     * 文件上传
     *
     * @param fileName  文件名称
     * @param content   文件内容
     * @param thumbnail 缩略图(true：生成缩略图，false:不生成缩略图)
     * @param types     缩略图类型
     * @return
     */
    public FileUploadResult upload(String fileName, byte[] content, boolean thumbnail, Set<Integer> types);

    /**
     * @param fileName  文件名称
     * @param content   文件内容
     * @param thumbnail 缩略图(true：生成缩略图，false:不生成缩略图)
     * @param types     缩略图类型
     * @param watermark 是否水印(true：是，false:否)
     * @return
     */
    public FileUploadResult upload(String fileName, byte[] content, boolean thumbnail, Set<Integer> types, boolean watermark);

    /**
     * 原图地址转换小图地址
     *
     * @param originalFilePath
     * @return
     */
    public String conversionSmallFilePath(String originalFilePath);
}
