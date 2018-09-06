package com.edianniu.pscp.cms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.edianniu.pscp.cms.commons.ResultCode;
import com.edianniu.pscp.cms.entity.AppCheckUpdateEntity;
import com.edianniu.pscp.cms.service.AppCheckUpdateService;
import com.edianniu.pscp.cms.utils.DateUtils;
import com.edianniu.pscp.cms.utils.Md5Utils;
import com.edianniu.pscp.cms.utils.PageUtils;
import com.edianniu.pscp.cms.utils.R;
import com.edianniu.pscp.mis.bean.FileUploadResult;
import com.edianniu.pscp.mis.service.dubbo.FileUploadService;

/**
 * 自更新管理
 * 
 * @author wangcheng.li
 * @email wangcheng.li@edianniu.com
 * @date 2017-07-03 10:08:40
 */
@Controller
@RequestMapping("appcheckupdate")
public class AppCheckUpdateController extends AbstractController{
	private static final Logger logger = LoggerFactory.getLogger(AppCheckUpdateController.class);
	@Autowired
	private AppCheckUpdateService appCheckUpdateService;
	@Autowired
	private FileUploadService fileUploadService;
	@Value(value = "${apk.download.url}")
    private String apkDownloadUrl;
	@RequestMapping("/appcheckupdate.html")
	public String list() {
		return "cp/appcheckupdate.html";
	}

	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("appcheckupdate:list")
	public R list(Integer page, Integer limit, Integer appType,Integer updateType,Integer status,String startTime,String endTime) {
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("appType", appType);
		map.put("updateType", updateType);
		map.put("status", status);
		if(StringUtils.isNotBlank(startTime))
		map.put("startTime", DateUtils.parse(startTime+" 00:00:00", DateUtils.DATE_TIME_PATTERN));
		if(StringUtils.isNoneBlank(endTime))
		map.put("endTime", DateUtils.parse(endTime+" 23:59:59", DateUtils.DATE_TIME_PATTERN));
		// 查询列表数据
		List<AppCheckUpdateEntity> appCheckUpdateList = appCheckUpdateService
				.queryList(map);
		int total = appCheckUpdateService.queryTotal(map);

		PageUtils pageUtil = new PageUtils(appCheckUpdateList, total, limit,
				page);

		return R.ok().put("page", pageUtil);
	}

	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("appcheckupdate:info")
	public R info(@PathVariable("id") Long id) {
		AppCheckUpdateEntity appCheckUpdate = appCheckUpdateService
				.queryObject(id);

		return R.ok().put("appCheckUpdate", appCheckUpdate);
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("appcheckupdate:save")
	public R save(@RequestBody AppCheckUpdateEntity appCheckUpdate) {
		Long appLatestVer = appCheckUpdate.getAppLatestVer();
		// Integer最大值为2的31次方减1
		Integer  max = (1 << 31) - 1; 
		if (appLatestVer > max) {
			return R.error("内部版本号不能超过" + max);
		}
		// 获取之前最大内部版本号
		Integer maxAppLatestVer = appCheckUpdateService.getMaxAppLatestVer(appCheckUpdate.getAppPkg());
		maxAppLatestVer = (maxAppLatestVer == null ? 0 : maxAppLatestVer);
		if (appLatestVer <= maxAppLatestVer) {
			return R.error("内部版本号不能低于" + (maxAppLatestVer + 1));
		}

		appCheckUpdate.setStatus(1);
		appCheckUpdate.setAppPkg(appCheckUpdate.getAppPkg().trim().toLowerCase());
		appCheckUpdateService.save(appCheckUpdate);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("appcheckupdate:update")
	public R update(@RequestBody AppCheckUpdateEntity appCheckUpdate) {
		appCheckUpdateService.update(appCheckUpdate);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/changeStatus")
	@RequiresPermissions("appcheckupdate:changeStatus")
	public R changeStatus(@RequestBody AppCheckUpdateEntity appCheckUpdate) {
		AppCheckUpdateEntity bean = appCheckUpdateService
				.queryObject(appCheckUpdate.getId());
		BeanUtils.copyProperties(bean, appCheckUpdate);
		if (bean.getStatus().intValue() == 0) {
			appCheckUpdate.setStatus(1);
		}
		if (bean.getStatus().intValue() == 1) {
			appCheckUpdate.setStatus(0);
		}
		appCheckUpdateService.update(appCheckUpdate);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("appcheckupdate:delete")
	public R delete(@RequestBody Long[] ids) {
		appCheckUpdateService.deleteBatch(ids);

		return R.ok();
	}

	/**
	 * apk上传
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/apkUpload", method = RequestMethod.POST)
	@ResponseBody()
	public R apkUpload(HttpServletRequest request, ModelMap model) {
		FileUploadResult result=new FileUploadResult();
		Long size=0L;
		String md5=null;
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartRequest.getFile("file");
			if(multipartFile==null){
				return R.error("file为空");
			}
			System.out.println(multipartFile.getSize());
			size=multipartFile.getSize();
			byte[] content=multipartFile.getBytes();
			md5=Md5Utils.MD5(content);
			result=fileUploadService.upload(multipartFile.getOriginalFilename(),
					content);
		} catch (Exception e) {
			logger.error("apkUpload:{}",e);
			result.set(ResultCode.ERROR, "系统异常");
		}
		if(!result.isSuccess()){
			return R.error(result.getResultMessage());
		}
		return R.ok().put("fileId", result.getFileId())
				.put("fileUrl", apkDownloadUrl+result.getFileId())
				.put("apkSize", size/1024)
				.put("apkMd5", md5);

	}

}
