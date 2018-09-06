/**
 *
 */
package com.edianniu.pscp.mis.service.impl;

import com.edianniu.pscp.mis.bean.FileUploadResult;
import com.edianniu.pscp.mis.commons.BusinessConstants;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.util.ImageUtils2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author cyl
 */
@Service
@Repository("fastDfsClient")
public class FastDfsClient {
    private static final Logger logger = LoggerFactory
            .getLogger(FastDfsClient.class);
    private static final String CONF_FILENAME = FastDfsClient.class
            .getClassLoader().getResource("fastdfs.properties").getPath();

    public FastDfsClient() {
        try {
            ClientGlobal.init(CONF_FILENAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FileUploadResult uploadFile(byte[] fileBytes, String extName) {
        return this.uploadFile(fileBytes, extName, null, null);
    }

    public FileUploadResult uploadFile(byte[] fileBytes, String extName, String groupName) {
        return this.uploadFile(fileBytes, extName, groupName, null);
    }

    public FileUploadResult uploadFile(byte[] fileBytes, String extName, Set<Integer> types) {
        return this.uploadFile(fileBytes, extName, null, types);
    }


	private FileUploadResult uploadFile(byte[] fileBytes, String extName, String groupName, Set<Integer> types) {
    	FileUploadResult result = new FileUploadResult();
        String fileId = "";
        TrackerServer trackerServer = null;
        try {
            TrackerClient tracker = new TrackerClient();
            trackerServer = tracker.getConnection();
            StorageClient client = new StorageClient(trackerServer, null);
            String[] filesStrings = null;
            
            /*判断是否需要旋转图片*/
            //fileBytes = ImageUtils.getExifAndTurn(fileBytes);
            fileBytes = ImageUtils2.getExifAndTurn(fileBytes);
            
            if (StringUtils.isNotBlank(groupName)) {
                filesStrings = client.upload_file(groupName, fileBytes, extName, null);
            } else {
                filesStrings = client.upload_file(fileBytes, extName, null);
            }
            if (filesStrings.length > 0) {
                fileId = filesStrings[0] + "/" + filesStrings[1];
            }
            result.setFileId(fileId);

            if (CollectionUtils.isNotEmpty(types)) {
                List<String> fileIds = new ArrayList<>();
                for (Integer type : types) {
                    if (type.equals(1)) {
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        ByteArrayInputStream is = new ByteArrayInputStream(fileBytes);

                        String prefix_name = String.format("_%sx%s", BusinessConstants.PICTURE_COMPRESS_DEFAULT_WIDTH, BusinessConstants.PICTURE_COMPRESS_DEFAULT_HEIGHT);
                        try {
                            /*先等比例缩放，小边缩放至指定长度后， 大边直接裁剪指指定长度*/
                        	//ImageUtils.zoomAndCut2(is, out, BusinessConstants.PICTURE_COMPRESS_DEFAULT_WIDTH, BusinessConstants.PICTURE_COMPRESS_DEFAULT_WIDTH);
                        	ImageUtils2.zoomPic(is, out, BusinessConstants.PICTURE_COMPRESS_DEFAULT_WIDTH, BusinessConstants.PICTURE_COMPRESS_DEFAULT_WIDTH);
                        	if (out.toByteArray().length < 0) {
                                logger.error("Thumbnails images error: file_buff is null");
                                continue;
                            }

                            String[] filesSlaveStrings = client.upload_file(filesStrings[0], filesStrings[1], prefix_name, out.toByteArray(), extName, null);
                            fileIds.add(String.format("%s/%s", filesSlaveStrings[0], filesSlaveStrings[1]));
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            IOUtils.closeQuietly(out);
                        }
                    }
                }
                if (CollectionUtils.isNotEmpty(fileIds)) {
                    result.setSlaveFileIds(fileIds);
                }
            }
            return result;
        } catch (Exception e) {
            logger.error("fastDfs上传错误", e);
            result.setResultCode(ResultCode.ERROR_401);
            result.setResultMessage("fastdfs上传错误:" + e.getMessage());
            return result;
        } finally {
            try {
                if (trackerServer != null) {
                    trackerServer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] downloadFile(String groupName, String filename) {
        TrackerServer trackerServer = null;
        try {
            TrackerClient tracker = new TrackerClient();
            trackerServer = tracker.getConnection();
            StorageClient client = new StorageClient(trackerServer, null);
            return client.download_file(groupName, filename);
        } catch (Exception e) {
            logger.error("fastDfs下载错误", e);
            return null;
        } finally {
            try {
                if (trackerServer != null) {
                    trackerServer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
