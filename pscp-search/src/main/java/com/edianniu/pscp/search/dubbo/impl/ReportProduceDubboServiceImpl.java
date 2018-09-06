package com.edianniu.pscp.search.dubbo.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edianniu.pscp.search.common.Constants;
import com.edianniu.pscp.search.dubbo.ReportProduceDubboService;
import com.edianniu.pscp.search.service.ProduceService;
import com.edianniu.pscp.search.service.meter.DayDetailLogService;
import com.edianniu.pscp.search.service.meter.DayLoadDetailService;
import com.edianniu.pscp.search.service.meter.DayLogService;
import com.edianniu.pscp.search.service.meter.DayVoltageCurrentDetailService;
import com.edianniu.pscp.search.service.meter.DemandDetailService;
import com.edianniu.pscp.search.service.meter.MeterLogService;
import com.edianniu.pscp.search.service.meter.MonthLogService;
import com.edianniu.pscp.search.support.meter.DayDetailLogReqData;
import com.edianniu.pscp.search.support.meter.DayLoadDetailReqData;
import com.edianniu.pscp.search.support.meter.DayLogReqData;
import com.edianniu.pscp.search.support.meter.DayVoltageCurrentDetailReqData;
import com.edianniu.pscp.search.support.meter.DemandDetailReqData;
import com.edianniu.pscp.search.support.meter.MeterLogReqData;
import com.edianniu.pscp.search.support.meter.MonthLogReqData;
import com.edianniu.pscp.search.support.meter.OpResult;
import com.edianniu.pscp.search.util.DateUtils;
import com.edianniu.pscp.search.util.ReflectionUtils;
/**
 * ReportProduceDubboServiceImpl
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月6日 下午2:44:42 
 * @version V1.0
 */
@Service("reportProduceDubboService")
public class ReportProduceDubboServiceImpl implements ReportProduceDubboService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DayLoadDetailService dayLoadDetailService;
	@Autowired
	private MonthLogService monthLogService;
	@Autowired
	private DayLogService meterDayLogService;
	@Autowired
	private DayDetailLogService meterDayDetailLogService;
	@Autowired
	private MeterLogService meterLogService;
	@Autowired
	private DemandDetailService demandDetailService;
	@Autowired
	private DayVoltageCurrentDetailService dayVoltageCurrentDetailService;
	@Override
	public OpResult save(DayLoadDetailReqData reqData) {
		OpResult result = new OpResult();
        try {
            if(reqData.getCompanyId()==null){
            	result.set(Constants.BAD_REQUEST, "客户企业ID不能为空");
                return result;
            }
            if(StringUtils.isBlank(reqData.getCompanyName())){
            	result.set(Constants.BAD_REQUEST, "客户企业名称不能为空");
                return result;
            }
            if(StringUtils.isBlank(reqData.getMeterId())){
            	result.set(Constants.BAD_REQUEST, "meterId不能为空");
                return result;
            }
            if(StringUtils.isBlank(reqData.getDate())){//还需要判断日期的格式
            	result.set(Constants.BAD_REQUEST, "date不能为空");
                return result;
            }
            
            if(StringUtils.isBlank(reqData.getPeriod())){//还需要判断是否数字格式
            	result.set(Constants.BAD_REQUEST, "period不能为空");
                return result;
            }
            if(StringUtils.isBlank(reqData.getTime())){//还需要判断是否数字格式
            	result.set(Constants.BAD_REQUEST, "time不能为空");
                return result;
            }
            if(reqData.getLoad()==null){//还需要判断是否数字格式
            	result.set(Constants.BAD_REQUEST, "load不能为空");
                return result;
            }
            if(reqData.getCreateTime()==null){//
            	result.set(Constants.BAD_REQUEST, "createTime不能为空");
                return result;
            }
            Map<String, Object> param = new HashMap<>();
            ReflectionUtils.beanToMap(reqData, param,"serialVersionUID");
            param.put("reportTime", reqData.getDate());
            IndexResponse response = dayLoadDetailService.save(reqData.getId(), param);
            RestStatus status = response.status();
            // 返回状态有两种情况
            // 第一:该文档id不存在,进行创建写入RestStatus.CREATED
            // 第二,文档id已经存在,进行更新操作返回RestStatus.OK
            if (!RestStatus.OK.equals(status)
                    && !RestStatus.CREATED.equals(status)) {
                result.set(Constants.ERROR_CODE, "添加数据失败");
                logger.error("添加数据失败，IndexResponse返回状态码：{}", status);
            }
        } catch (Exception e) {
            result.set(Constants.ERROR_CODE, "系统异常");
            logger.error("系统异常信息", e);
        }
        return result;
	}

	@Override
	public OpResult save(MonthLogReqData reqData) {
		OpResult result = new OpResult();
        try {
            if(reqData.getCompanyId()==null){
            	result.set(Constants.BAD_REQUEST, "客户企业ID不能为空");
                return result;
            }
            if(StringUtils.isBlank(reqData.getCompanyName())){
            	result.set(Constants.BAD_REQUEST, "客户企业名称不能为空");
                return result;
            }
            if(StringUtils.isBlank(reqData.getMeterId())){
            	result.set(Constants.BAD_REQUEST, "meterId不能为空");
                return result;
            }
            if(StringUtils.isBlank(reqData.getDate())){//还需要判断日期的格式
            	result.set(Constants.BAD_REQUEST, "date不能为空");
                return result;
            }
            
            if(StringUtils.isBlank(reqData.getCycle())){//还需要判断是否数字格式
            	result.set(Constants.BAD_REQUEST, "period不能为空");
                return result;
            }
            if(reqData.getTotalCharge()==null){//还需要判断是否数字格式
            	result.set(Constants.BAD_REQUEST, "time不能为空");
                return result;
            }
            if(reqData.getBasicCharge()==null){//还需要判断是否数字格式
            	result.set(Constants.BAD_REQUEST, "basicCharge不能为空");
                return result;
            }
            if(reqData.getFactorCharge()==null){//还需要判断是否数字格式
            	result.set(Constants.BAD_REQUEST, "factorCharge不能为空");
                return result;
            }
            if(reqData.getApexCharge()==null){//还需要判断是否数字格式
            	result.set(Constants.BAD_REQUEST, "apexCharge不能为空");
                return result;
            }
            if(reqData.getPeakCharge()==null){//还需要判断是否数字格式
            	result.set(Constants.BAD_REQUEST, "peakCharge不能为空");
                return result;
            }
            if(reqData.getFlatCharge()==null){//还需要判断是否数字格式
            	result.set(Constants.BAD_REQUEST, "flatCharge不能为空");
                return result;
            }
            if(reqData.getValleyCharge()==null){//还需要判断是否数字格式
            	result.set(Constants.BAD_REQUEST, "valleyCharge不能为空");
                return result;
            }
            if(reqData.getActivePowerFactor()==null){//还需要判断是否数字格式
            	result.set(Constants.BAD_REQUEST, "activePowerFactor不能为空");
                return result;
            }
            if(reqData.getBasicPrice()==null){//还需要判断是否数字格式
            	result.set(Constants.BAD_REQUEST, "basicPrice不能为空");
                return result;
            }
            if(reqData.getApexPrice()==null){//还需要判断是否数字格式
            	result.set(Constants.BAD_REQUEST, "apexPrice不能为空");
                return result;
            }
            if(reqData.getPeakPrice()==null){//还需要判断是否数字格式
            	result.set(Constants.BAD_REQUEST, "peakPrice不能为空");
                return result;
            }
            if(reqData.getFlatPrice()==null){//还需要判断是否数字格式
            	result.set(Constants.BAD_REQUEST, "flatPrice不能为空");
                return result;
            }
            if(reqData.getValleyPrice()==null){//还需要判断是否数字格式
            	result.set(Constants.BAD_REQUEST, "valleyPrice不能为空");
                return result;
            }
            

            if(reqData.getCreateTime()==null){//
            	result.set(Constants.BAD_REQUEST, "createTime不能为空");
                return result;
            }
            Map<String, Object> param = new HashMap<>();
            ReflectionUtils.beanToMap(reqData, param,"serialVersionUID");
            param.put("reportTime", reqData.getDate());
            IndexResponse response = monthLogService.save(reqData.getId(), param);
            RestStatus status = response.status();
            // 返回状态有两种情况
            // 第一:该文档id不存在,进行创建写入RestStatus.CREATED
            // 第二,文档id已经存在,进行更新操作返回RestStatus.OK
            if (!RestStatus.OK.equals(status)
                    && !RestStatus.CREATED.equals(status)) {
                result.set(Constants.ERROR_CODE, "添加数据失败");
                logger.error("添加数据失败，IndexResponse返回状态码：{}", status);
            }
        } catch (Exception e) {
            result.set(Constants.ERROR_CODE, "系统异常");
            logger.error("系统异常信息", e);
        }
        return result;
	}
	@Override
	public OpResult deleteById(DayLoadDetailReqData reqData) {
		
		return deletedById(dayLoadDetailService,reqData.getId());
	}

	@Override
	public OpResult deleteById(MonthLogReqData reqData) {
		return deletedById(monthLogService,reqData.getId());
	}

	

	@Override
	public OpResult deleteById(DayLogReqData reqData) {
		return deletedById(meterDayLogService,reqData.getId());
	}

	
	private OpResult deletedById(ProduceService produceService,String id) {
		OpResult result = new OpResult();
        try {
            if (StringUtils.isBlank(id)) {
                result.set(Constants.BAD_REQUEST, "ID不能为空");
                return result;
            }
            // 检查数据是否存在
            GetResponse getResponse = produceService.getById(id);
            if (getResponse.isSourceEmpty()) {
                result.set(Constants.ERROR_CODE, "删除数据不存在");
                logger.warn("删除数据不存在,操作标识：{}", id);
                return result;
            }

            // 删除数据
            DeleteResponse response = produceService.deleteById(id);
            if (RestStatus.OK != response.status()) {
                result.set(Constants.ERROR_CODE, "删除数据失败");
                logger.error("删除数据失败", response.toString());
            }
        } catch (Exception e) {
            result.set(Constants.ERROR_CODE, "系统异常");
            logger.error("系统异常信息", e);
        }
        return result;
	}

	@Override
	public OpResult save(DayDetailLogReqData reqData) {
		OpResult result = new OpResult();
        try {
            if(reqData.getCompanyId()==null){
            	result.set(Constants.BAD_REQUEST, "客户企业ID不能为空");
                return result;
            }
            if(StringUtils.isBlank(reqData.getCompanyName())){
            	result.set(Constants.BAD_REQUEST, "客户企业名称不能为空");
                return result;
            }
            if(StringUtils.isBlank(reqData.getMeterId())){
            	result.set(Constants.BAD_REQUEST, "仪表ID不能为空");
                return result;
            }
            if(reqData.getCreateTime()==null){
            	result.set(Constants.BAD_REQUEST, "createTime不能为空");
                return result;
            }
            Map<String, Object> param = new HashMap<>();
            ReflectionUtils.beanToMap(reqData, param,"serialVersionUID");
            param.put("reportTime", reqData.getDate());
            IndexResponse response = meterDayDetailLogService.save(reqData.getId(), param);
            RestStatus status = response.status();
            // 返回状态有两种情况
            // 第一:该文档id不存在,进行创建写入RestStatus.CREATED
            // 第二,文档id已经存在,进行更新操作返回RestStatus.OK
            if (!RestStatus.OK.equals(status)
                    && !RestStatus.CREATED.equals(status)) {
                result.set(Constants.ERROR_CODE, "添加数据失败");
                logger.error("添加数据失败，IndexResponse返回状态码：{}", status);
            }
        } catch (Exception e) {
            result.set(Constants.ERROR_CODE, "系统异常");
            logger.error("系统异常信息", e);
        }
        return result;
	}
	@Override
	public OpResult save(DayLogReqData reqData) {
		OpResult result = new OpResult();
        try {
            
            
            if(reqData.getCompanyId()==null){
            	result.set(Constants.BAD_REQUEST, "客户企业ID不能为空");
                return result;
            }
            if(StringUtils.isBlank(reqData.getCompanyName())){
            	result.set(Constants.BAD_REQUEST, "客户企业名称不能为空");
                return result;
            }
            if(StringUtils.isBlank(reqData.getMeterId())){
            	result.set(Constants.BAD_REQUEST, "仪表ID不能为空");
                return result;
            }
            if(reqData.getCreateTime()==null){
            	result.set(Constants.BAD_REQUEST, "createTime不能为空");
                return result;
            }
            Map<String, Object> param = new HashMap<>();
            ReflectionUtils.beanToMap(reqData, param,"serialVersionUID");
            param.put("reportTime", reqData.getDate());
            param.put("week", DateUtils.dateToDayofweek(reqData.getDate()));
            
            IndexResponse response = meterDayLogService.save(reqData.getId(), param);
            RestStatus status = response.status();
            // 返回状态有两种情况
            // 第一:该文档id不存在,进行创建写入RestStatus.CREATED
            // 第二,文档id已经存在,进行更新操作返回RestStatus.OK
            if (!RestStatus.OK.equals(status)
                    && !RestStatus.CREATED.equals(status)) {
                result.set(Constants.ERROR_CODE, "添加数据失败");
                logger.error("添加数据失败，IndexResponse返回状态码：{}", status);
            }
        } catch (Exception e) {
            result.set(Constants.ERROR_CODE, "系统异常");
            logger.error("系统异常信息", e);
        }
        return result;
	}

	
	@Override
	public OpResult save(MeterLogReqData reqData) {
		OpResult result = new OpResult();
        try {
        	if(reqData.getCompanyId()==null){
            	result.set(Constants.BAD_REQUEST, "客户企业ID不能为空");
                return result;
            }
            if(StringUtils.isBlank(reqData.getCompanyName())){
            	result.set(Constants.BAD_REQUEST, "客户企业名称不能为空");
                return result;
            }
            if(StringUtils.isBlank(reqData.getMeterId())){
            	result.set(Constants.BAD_REQUEST, "仪表ID不能为空");
                return result;
            }
            if(reqData.getTime()==null){//还需要判断日期的格式
            	result.set(Constants.BAD_REQUEST, "time不能为空");
                return result;
            }
            if(reqData.getCreateTime()==null){
            	result.set(Constants.BAD_REQUEST, "createTime不能为空");
                return result;
            }
            GetResponse getResponse=meterLogService.getById(reqData.getId());
            if(getResponse.isExists()){
            	result.set(Constants.EXIST_CODE, "数据已存在了");
                return result;
            }
            Map<String, Object> param = new HashMap<>();
            ReflectionUtils.beanToMap(reqData, param,"serialVersionUID");
            param.put("reportTime", DateUtils.format(new Date(reqData.getTime()), "yyyyMMddHHmmss"));
            IndexResponse response = meterLogService.save(reqData.getId(), param);
            RestStatus status = response.status();
            // 返回状态有两种情况
            // 第一:该文档id不存在,进行创建写入RestStatus.CREATED
            // 第二,文档id已经存在,进行更新操作返回RestStatus.OK
            if (!RestStatus.OK.equals(status)
                    && !RestStatus.CREATED.equals(status)) {
                result.set(Constants.ERROR_CODE, "添加数据失败");
                logger.error("添加数据失败，IndexResponse返回状态码：{}", status);
            }
        } catch (Exception e) {
            result.set(Constants.ERROR_CODE, "系统异常");
            logger.error("系统异常信息", e);
        }
        return result;
	}
	@Override
	public OpResult save(DemandDetailReqData reqData) {
		OpResult result = new OpResult();
        try {
            
        	if(reqData.getCompanyId()==null){
            	result.set(Constants.BAD_REQUEST, "客户企业ID不能为空");
                return result;
            }
            if(StringUtils.isBlank(reqData.getCompanyName())){
            	result.set(Constants.BAD_REQUEST, "客户企业名称不能为空");
                return result;
            }
            if(StringUtils.isBlank(reqData.getMeterId())){
            	result.set(Constants.BAD_REQUEST, "仪表ID不能为空");
                return result;
            }
            if(StringUtils.isBlank(reqData.getDate())){
            	result.set(Constants.BAD_REQUEST, "date不能为空");
                return result;
            }
            if(reqData.getPower()==null){
            	result.set(Constants.BAD_REQUEST, "power不能为空");
                return result;
            }
            if(reqData.getCreateTime()==null){
            	result.set(Constants.BAD_REQUEST, "createTime不能为空");
                return result;
            }
            Map<String, Object> param = new HashMap<>();
            ReflectionUtils.beanToMap(reqData, param,"serialVersionUID");
            param.put("reportTime",reqData.getDate());
            IndexResponse response = demandDetailService.save(reqData.getId(), param);
            RestStatus status = response.status();
            // 返回状态有两种情况
            // 第一:该文档id不存在,进行创建写入RestStatus.CREATED
            // 第二,文档id已经存在,进行更新操作返回RestStatus.OK
            if (!RestStatus.OK.equals(status)
                    && !RestStatus.CREATED.equals(status)) {
                result.set(Constants.ERROR_CODE, "添加数据失败");
                logger.error("添加数据失败，IndexResponse返回状态码：{}", status);
            }
        } catch (Exception e) {
            result.set(Constants.ERROR_CODE, "系统异常");
            logger.error("系统异常信息", e);
        }
        return result;
	}
	@Override
	public OpResult save(DayVoltageCurrentDetailReqData reqData) {
		OpResult result = new OpResult();
        try {
            
        	if(reqData.getCompanyId()==null){
            	result.set(Constants.BAD_REQUEST, "客户企业ID不能为空");
                return result;
            }
            if(StringUtils.isBlank(reqData.getCompanyName())){
            	result.set(Constants.BAD_REQUEST, "客户企业名称不能为空");
                return result;
            }
            if(StringUtils.isBlank(reqData.getMeterId())){
            	result.set(Constants.BAD_REQUEST, "仪表ID不能为空");
                return result;
            }
            if(StringUtils.isBlank(reqData.getDate())){
            	result.set(Constants.BAD_REQUEST, "date不能为空");
                return result;
            }
            if(reqData.getCreateTime()==null){
            	result.set(Constants.BAD_REQUEST, "createTime不能为空");
                return result;
            }
            Map<String, Object> param = new HashMap<>();
            ReflectionUtils.beanToMap(reqData, param,"serialVersionUID");
            IndexResponse response = dayVoltageCurrentDetailService.save(reqData.getId(), param);
            RestStatus status = response.status();
            // 返回状态有两种情况
            // 第一:该文档id不存在,进行创建写入RestStatus.CREATED
            // 第二,文档id已经存在,进行更新操作返回RestStatus.OK
            if (!RestStatus.OK.equals(status)
                    && !RestStatus.CREATED.equals(status)) {
                result.set(Constants.ERROR_CODE, "添加数据失败");
                logger.error("添加数据失败，IndexResponse返回状态码：{}", status);
            }
        } catch (Exception e) {
            result.set(Constants.ERROR_CODE, "系统异常");
            logger.error("系统异常信息", e);
        }
        return result;
	}


	@Override
	public OpResult deleteById(DemandDetailReqData reqData) {
		return deletedById(demandDetailService,reqData.getId());
	}

	
	@Override
	public OpResult deleteById(DayVoltageCurrentDetailReqData reqData) {
		return deletedById(dayVoltageCurrentDetailService,reqData.getId());
	}

	@Override
	public OpResult deleteById(DayDetailLogReqData reqData) {
		return deletedById(meterDayDetailLogService,reqData.getId());
	}

	
	

	

}
