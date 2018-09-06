package com.edianniu.pscp.portal.controller;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.mis.bean.FileUploadResult;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.FileUploadService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.portal.bean.req.FileUploadRequest;
import com.edianniu.pscp.portal.bean.resp.FileUploadResponse;
import com.edianniu.pscp.portal.commons.Constants;
import com.edianniu.pscp.portal.commons.ResultCode;
import com.edianniu.pscp.portal.utils.ImageUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.InputStream;


/**
 * 文件上传接口
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-04-14 18:17:07
 */
@Controller
@RequestMapping("/file")
public class FileUploadController extends AbstractController {
    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("fileUploadService")
    private FileUploadService fileUploadService;

    private String fileDownloadUrl = "http://111.1.17.197:8091/";

    @Value(value = "${file.download.url:}")
    public void setFileDownloadUrl(String fileDownloadUrl) {
        this.fileDownloadUrl = fileDownloadUrl;
    }

    // 文件上传开关-特殊情况下手动开启(默认false-关闭)
    private boolean fileUploadSwitch = false;

    @RequestMapping(value = "upload", method = {RequestMethod.POST, RequestMethod.OPTIONS})
    @ResponseBody()
    @CrossOrigin(value="*")
    public FileUploadResponse base64Upload(@RequestBody FileUploadRequest req) {
        long start = System.currentTimeMillis();
        FileUploadResponse response = new FileUploadResponse();
        if (req == null) {
            response.setResultCode(ResultCode.ERROR);
            response.setResultMessage("参数不能为空");
            return response;
        }
        if (req.getUid() == null) {
            response.setResultCode(ResultCode.ERROR);
            response.setResultMessage("uid 不能为空");
            return response;
        }
        if (StringUtils.isBlank(req.getToken())) {
            response.setResultCode(ResultCode.ERROR);
            response.setResultMessage("token 不能为空");
            return response;
        }
        if (StringUtils.isBlank(req.getFile())) {
            response.setResultCode(ResultCode.ERROR);
            response.setResultMessage("file 不能为空");
            return response;
        }
        Result isLoginResult = userInfoService.isLogin(req.getUid(), req.getToken());
        if (!isLoginResult.isSuccess()) {
            response.setResultCode(isLoginResult.getResultCode());
            response.setResultMessage(isLoginResult.getResultMessage());
            return response;
        }
        try {
            byte[] content = ImageUtils.getBase64ImageBytes(req.getFile());
            // 图片压缩
            if (content != null) {
                if (content.length > 8 * 1024 * 1024) {// 压缩后的图片或者原图图片大于8M，则提示片太大了，请重新选择图片。
                    response.setResultCode(ResultCode.ERROR);
                    response.setResultMessage("图片大小太大了，请重新选择图片");
                    return response;
                }
            }
            
            FileUploadResult result = fileUploadService.upload("upload_" + System.nanoTime() + ".jpg",
                    content, true, null, req.getWatermark().equals(Constants.TAG_YES));
            if (result.isSuccess()) {
                response.setFileId(result.getFileId());
                response.setFileUrl(fileDownloadUrl + result.getFileId());
            } else {
                response.setResultCode(result.getResultCode());
                response.setResultMessage(result.getResultMessage());
            }
            logger.debug("fileUploatResult:{}", JSON.toJSONString(result));
        } catch (Exception e) {
            response.setResultCode(ResultCode.ERROR);
            response.setResultMessage("系统异常");
            logger.error("upload file:{}", e);
        }
        logger.debug("fileUpload 耗时:{}", (System.currentTimeMillis() - start) + " ms");
        return response;
    }

    @RequestMapping(value = "fileupload", method = RequestMethod.GET)
    public ModelAndView fileUploadHtml(ModelAndView modelAndView) {
        modelAndView.setViewName("fileUpload.ftl");
        return modelAndView;
    }

    @RequestMapping(value = "fileupload", method = RequestMethod.POST)
    @ResponseBody()
    public ModelAndView fileUpload(@RequestParam MultipartFile myfile, ModelAndView modelAndView) {
        long start = System.currentTimeMillis();
        if (!fileUploadSwitch) {
            modelAndView.addObject("msg", "上传功能已被限制，返回主页看看其他功能吧！");
            modelAndView.setViewName("fileUpload.ftl");
            return modelAndView;
        }
        try {
            InputStream is = myfile.getInputStream();
            byte[] content = IOUtils.toByteArray(is);
            FileUploadResult result = fileUploadService.upload("upload_" + System.nanoTime() + ".jpg", content,
                    false, null, false);
            if (result.isSuccess()) {
                modelAndView.addObject("fid", result.getFileId());
            } else {
                modelAndView.addObject("msg", result.getResultMessage());
            }
            logger.debug("fileUploatResult:{}", JSON.toJSONString(result));
        } catch (Exception e) {
            modelAndView.addObject("msg", "系统异常");
            logger.error("upload file:{}", e);
        }
        logger.debug("fileUpload 耗时:{}", (System.currentTimeMillis() - start) + " ms");
        modelAndView.setViewName("fileUpload.ftl");
        return modelAndView;
    }

}
