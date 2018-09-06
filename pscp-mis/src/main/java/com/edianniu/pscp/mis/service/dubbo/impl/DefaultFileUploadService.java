package com.edianniu.pscp.mis.service.dubbo.impl;

import com.edianniu.pscp.mis.bean.FileUploadResult;
import com.edianniu.pscp.mis.commons.BusinessConstants;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.FileUploadService;
import com.edianniu.pscp.mis.service.impl.FastDfsClient;
import com.edianniu.pscp.mis.util.WatermarkUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
@Repository("fileUploadService")
public class DefaultFileUploadService implements FileUploadService {
    private static final Logger logger = LoggerFactory
            .getLogger(DefaultFileUploadService.class);
    @Autowired
    @Qualifier("fastDfsClient")
    private FastDfsClient fastDfsClient;
    private static String[] extNames = {".png", ".jpeg", ".jpg", ".gif", ".bmp", ".apk"};

    /**
     * 检查文件扩展名
     *
     * @param suffix
     * @return
     */
    private boolean checkFileExtension(String suffix) {
        if (StringUtils.isBlank(suffix)) {
            return false;
        }
        boolean rs = false;
        for (String extName : extNames) {
            if (suffix.trim().toLowerCase().equals(extName)) {
                rs = true;
                break;
            }
        }
        return rs;
    }

    /**
     * 获取支持的文件扩展名的字符串
     *
     * @return
     */
    private String getSupportFileExtensions() {
        StringBuffer sbf = new StringBuffer(64);
        for (String extName : extNames) {
            sbf.append(extName).append(",");
        }
        return sbf.toString().substring(0, sbf.length() - 1);
    }

    @Override
    public FileUploadResult upload(String fileName, byte[] content) {
        return upload(fileName, content, false, null);
    }

    /**
     * 文件上传
     *
     * @param fileName  文件名称
     * @param content   文件内容
     * @param thumbnail 缩略图(true：生成缩略图，false:不生成缩略图)
     * @param types     缩略图类型
     * @return
     */
    @Override
    public FileUploadResult upload(String fileName, byte[] content, boolean thumbnail, Set<Integer> types) {
        return upload(fileName, content, thumbnail, types, false);
    }

    /**
     * @param fileName  文件名称
     * @param content   文件内容
     * @param thumbnail 缩略图(true：生成缩略图，false:不生成缩略图)
     * @param types     缩略图类型
     * @param watermark 是否水印(true：是，false:否)
     * @return
     */
    @Override
    public FileUploadResult upload(String fileName, byte[] content, boolean thumbnail, Set<Integer> types, boolean watermark) {
        FileUploadResult result = new FileUploadResult();
        if (StringUtils.isBlank(fileName)) {
            logger.warn("fileName can not be null");
            result.setResultCode(ResultCode.ERROR_201);
            result.setResultMessage("fileName can not be null");
            return result;
        }
        if (content == null || content.length == 0) {
            logger.warn("content can not be null");
            result.setResultCode(ResultCode.ERROR_202);
            result.setResultMessage("content can not be null");
            return result;
        }
        logger.debug("fileName:{} content-length:{}", fileName, content.length);
        int idx = fileName.lastIndexOf(".");
        String suffix = "png";
        if (idx >= 0) {
            suffix = fileName.substring(idx + 1);
            if (!checkFileExtension(suffix)) {
                result.set(ResultCode.ERROR_203, "上传文件扩展名只支持" + getSupportFileExtensions());
            }
        } else {
            result.set(ResultCode.ERROR_203, "上传文件扩展名有问题");
            return result;
        }

        if (watermark) {
            // 图片添加水印
            content = WatermarkUtils.moreImageMark(content, suffix);
        }

        if (thumbnail) {
            // 生成缩略图
            if (types == null || CollectionUtils.isEmpty(types)) {
                types = new HashSet<>();
                types.add(1);
            }
            return fastDfsClient.uploadFile(content, suffix, types);
        }
        return fastDfsClient.uploadFile(content, suffix);
    }

    /**
     * 原图地址转换小图地址
     * @param originalFilePath
     * @return
     */
    @Override
    public String conversionSmallFilePath(String originalFilePath) {
        if (StringUtils.isBlank(originalFilePath)) {
            return "";
        }

        int idx = originalFilePath.lastIndexOf(".");
        if (idx < 0) {
            return originalFilePath;
        }

        String originalSuffix = originalFilePath.substring(idx);
        if (!checkFileExtension(originalSuffix)) {
            return originalFilePath;
        }

        String suffix = String.format("_%sx%s%s",
                BusinessConstants.PICTURE_COMPRESS_DEFAULT_WIDTH,
                BusinessConstants.PICTURE_COMPRESS_DEFAULT_HEIGHT,
                originalSuffix);

        if (originalFilePath.lastIndexOf(suffix) >= 0) {
            return originalFilePath;
        }

        return originalFilePath.replace(originalSuffix, suffix);
    }

}
