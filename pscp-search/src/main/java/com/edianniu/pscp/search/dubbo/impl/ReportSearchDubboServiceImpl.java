package com.edianniu.pscp.search.dubbo.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.get.GetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.edianniu.pscp.search.common.Constants;
import com.edianniu.pscp.search.common.PageResult;
import com.edianniu.pscp.search.dubbo.ReportSearchDubboService;
import com.edianniu.pscp.search.repository.meter.MeterLogRepository;
import com.edianniu.pscp.search.service.meter.DayDetailLogService;
import com.edianniu.pscp.search.service.meter.DayLoadDetailService;
import com.edianniu.pscp.search.service.meter.DayLogService;
import com.edianniu.pscp.search.service.meter.DayVoltageCurrentDetailService;
import com.edianniu.pscp.search.service.meter.DemandDetailService;
import com.edianniu.pscp.search.service.meter.MeterLogService;
import com.edianniu.pscp.search.service.meter.MonthLogService;
import com.edianniu.pscp.search.support.SubEnergyCode;
import com.edianniu.pscp.search.support.meter.AvgListResult;
import com.edianniu.pscp.search.support.meter.AvgOfMetreReqData;
import com.edianniu.pscp.search.support.meter.DayAggregateReqData;
import com.edianniu.pscp.search.support.meter.DayElectricChargeReqData;
import com.edianniu.pscp.search.support.meter.DayElectricReqData;
import com.edianniu.pscp.search.support.meter.DayLoadDetailReqData;
import com.edianniu.pscp.search.support.meter.DayLogReqData;
import com.edianniu.pscp.search.support.meter.DayPowerFactorReqData;
import com.edianniu.pscp.search.support.meter.DayVoltageCurrentDetailReqData;
import com.edianniu.pscp.search.support.meter.DemandDetailReqData;
import com.edianniu.pscp.search.support.meter.MeterLogReqData;
import com.edianniu.pscp.search.support.meter.MonthElectricReqData;
import com.edianniu.pscp.search.support.meter.MonthLogReqData;
import com.edianniu.pscp.search.support.meter.ReportListResult;
import com.edianniu.pscp.search.support.meter.StatListResult;
import com.edianniu.pscp.search.support.meter.SubEnergyReq;
import com.edianniu.pscp.search.support.meter.list.CurrentListReqData;
import com.edianniu.pscp.search.support.meter.list.DayAggregateListReqData;
import com.edianniu.pscp.search.support.meter.list.DayDetailLogListReqData;
import com.edianniu.pscp.search.support.meter.list.DayElectricChargeListReqData;
import com.edianniu.pscp.search.support.meter.list.DayElectricListReqData;
import com.edianniu.pscp.search.support.meter.list.DayLoadDetailListReqData;
import com.edianniu.pscp.search.support.meter.list.DayPowerFactorDetailListReqData;
import com.edianniu.pscp.search.support.meter.list.DayVoltageCurrentDetailListReqData;
import com.edianniu.pscp.search.support.meter.list.DemandDetailListReqData;
import com.edianniu.pscp.search.support.meter.list.DistinctMeterLogListReqData;
import com.edianniu.pscp.search.support.meter.list.ElectricChargeStatReqData;
import com.edianniu.pscp.search.support.meter.list.MonthElectricChargeListReqData;
import com.edianniu.pscp.search.support.meter.list.MonthElectricListReqData;
import com.edianniu.pscp.search.support.meter.list.MonthLogListReqData;
import com.edianniu.pscp.search.support.meter.list.VoltageListReqData;
import com.edianniu.pscp.search.support.meter.vo.AvgOfMeterStat;
import com.edianniu.pscp.search.support.meter.vo.CurrentVO;
import com.edianniu.pscp.search.support.meter.vo.DayAggregateVO;
import com.edianniu.pscp.search.support.meter.vo.DayDetailLogVO;
import com.edianniu.pscp.search.support.meter.vo.DayElectricChargeVO;
import com.edianniu.pscp.search.support.meter.vo.DayElectricVO;
import com.edianniu.pscp.search.support.meter.vo.DayLoadDetailVO;
import com.edianniu.pscp.search.support.meter.vo.DayLogVO;
import com.edianniu.pscp.search.support.meter.vo.DayPowerFactorDetailVO;
import com.edianniu.pscp.search.support.meter.vo.DayPowerFactorVO;
import com.edianniu.pscp.search.support.meter.vo.DayVoltageCurrentDetailVO;
import com.edianniu.pscp.search.support.meter.vo.DemandDetailVO;
import com.edianniu.pscp.search.support.meter.vo.ElectricChargeStat;
import com.edianniu.pscp.search.support.meter.vo.MeterLogVO;
import com.edianniu.pscp.search.support.meter.vo.MonthElectricChargeVO;
import com.edianniu.pscp.search.support.meter.vo.MonthElectricVO;
import com.edianniu.pscp.search.support.meter.vo.MonthLogVO;
import com.edianniu.pscp.search.support.meter.vo.StatLog;
import com.edianniu.pscp.search.support.meter.vo.VoltageVO;
import com.edianniu.pscp.search.support.query.meter.DayDetailLogQuery;
import com.edianniu.pscp.search.support.query.meter.DayLoadDetailQuery;
import com.edianniu.pscp.search.support.query.meter.DayLogQuery;
import com.edianniu.pscp.search.support.query.meter.DayVoltageCurrentDetailQuery;
import com.edianniu.pscp.search.support.query.meter.DemandDetailQuery;
import com.edianniu.pscp.search.support.query.meter.MeterLogQuery;
import com.edianniu.pscp.search.support.query.meter.MonthLogQuery;
import com.edianniu.pscp.search.util.DateUtils;

@Service("reportSearchDubboService")
public class ReportSearchDubboServiceImpl implements ReportSearchDubboService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DayLoadDetailService dayLoadDetailService;
	@Autowired
	private MonthLogService meterMonthLogService;
	@Autowired
	private MeterLogService meterLogService;
	@Autowired
	private DayLogService meterDayLogService;
	@Autowired
	private DayDetailLogService meterDayDetailLogService;
	@Autowired
	private DemandDetailService demandDetailService;
	@Autowired
	private DayVoltageCurrentDetailService dayVoltageCurrentDetailService;

	@Override
	public ReportListResult<DayAggregateVO> search(
			DayAggregateListReqData reqData) {
		ReportListResult<DayAggregateVO> result = new ReportListResult<DayAggregateVO>();
		try {
			// 校验暂时不处理
			DayLogQuery listQuery = new DayLogQuery();
			BeanUtils.copyProperties(reqData, listQuery);
			PageResult<DayLogVO> pageResult = meterDayLogService
					.queryByPage(listQuery);
			List<DayAggregateVO> list = new ArrayList<>();
			if (pageResult.getData() != null) {
				for (DayLogVO dayLogVO : pageResult.getData()) {
					DayAggregateVO dayAggregateVO = new DayAggregateVO();
					BeanUtils.copyProperties(dayLogVO, dayAggregateVO);
					list.add(dayAggregateVO);
				}
			}
			result.setList(list);
			result.setHasNext(pageResult.isHasNext());
			result.setNextOffset(pageResult.getNextOffset());
			result.setTotalCount(pageResult.getTotal());
		} catch (Exception e) {
			result.set(Constants.ERROR_CODE, "系统异常");
			logger.error("系统异常:{}", e);
		}
		return result;

	}

	@Override
	public ReportListResult<DayLoadDetailVO> search(
			DayLoadDetailListReqData reqData) {
		ReportListResult<DayLoadDetailVO> result = new ReportListResult<DayLoadDetailVO>();
		try {
			// 校验暂时不处理
			DayLoadDetailQuery listQuery = new DayLoadDetailQuery();
			BeanUtils.copyProperties(reqData, listQuery);
			PageResult<DayLoadDetailVO> pageResult = dayLoadDetailService
					.queryByPage(listQuery);
			result.setList(pageResult.getData());
			result.setHasNext(pageResult.isHasNext());
			result.setNextOffset(pageResult.getNextOffset());
			result.setTotalCount(pageResult.getTotal());
		} catch (Exception e) {
			result.set(Constants.ERROR_CODE, "系统异常");
			logger.error("系统异常:{}", e);
		}
		return result;
	}

	@Override
	public ReportListResult<MonthElectricChargeVO> search(
			MonthElectricChargeListReqData reqData) {
		ReportListResult<MonthElectricChargeVO> result = new ReportListResult<MonthElectricChargeVO>();
		try {
			// 校验暂时不处理
			MonthLogQuery listQuery = new MonthLogQuery();
			BeanUtils.copyProperties(reqData, listQuery);
			PageResult<MonthLogVO> pageResult = meterMonthLogService
					.queryByPage(listQuery);
			List<MonthElectricChargeVO> list=new ArrayList<>();
			for(MonthLogVO monthLogVO:pageResult.getData()){
				MonthElectricChargeVO vo=new MonthElectricChargeVO();
				BeanUtils.copyProperties(monthLogVO, vo);
				list.add(vo);
			}
			result.setList(list);
			result.setHasNext(pageResult.isHasNext());
			result.setNextOffset(pageResult.getNextOffset());
			result.setTotalCount(pageResult.getTotal());
		} catch (Exception e) {
			result.set(Constants.ERROR_CODE, "系统异常");
			logger.error("系统异常:{}", e);
		}
		return result;
	}
	@Override
	public ReportListResult<MonthLogVO> search(MonthLogListReqData reqData) {
		ReportListResult<MonthLogVO> result = new ReportListResult<MonthLogVO>();
		try {
			// 校验暂时不处理
			MonthLogQuery listQuery = new MonthLogQuery();
			BeanUtils.copyProperties(reqData, listQuery);
			PageResult<MonthLogVO> pageResult = meterMonthLogService
					.queryByPage(listQuery);
			result.setList(pageResult.getData());
			result.setHasNext(pageResult.isHasNext());
			result.setNextOffset(pageResult.getNextOffset());
			result.setTotalCount(pageResult.getTotal());
		} catch (Exception e) {
			result.set(Constants.ERROR_CODE, "系统异常");
			logger.error("系统异常:{}", e);
		}
		return result;
	}

	@Override
	public ReportListResult<DayElectricVO> search(DayElectricListReqData reqData) {
		ReportListResult<DayElectricVO> result = new ReportListResult<DayElectricVO>();
		try {
			// 校验暂时不处理
			DayLogQuery listQuery = new DayLogQuery();
			BeanUtils.copyProperties(reqData, listQuery);
			PageResult<DayLogVO> pageResult = meterDayLogService
					.queryByPage(listQuery);
			List<DayElectricVO> list = new ArrayList<>();
			if (pageResult.getData() != null) {
				for (DayLogVO dayLogVO : pageResult.getData()) {
					DayElectricVO dayElectricVO = new DayElectricVO();
					BeanUtils.copyProperties(dayLogVO, dayElectricVO);
					list.add(dayElectricVO);
				}
			}
			result.setList(list);
			result.setHasNext(pageResult.isHasNext());
			result.setNextOffset(pageResult.getNextOffset());
			result.setTotalCount(pageResult.getTotal());
		} catch (Exception e) {
			result.set(Constants.ERROR_CODE, "系统异常");
			logger.error("系统异常:{}", e);
		}
		return result;
	}

	@Override
	public ReportListResult<MonthElectricVO> search(
			MonthElectricListReqData reqData) {
		ReportListResult<MonthElectricVO> result = new ReportListResult<MonthElectricVO>();
		try {
			// 校验暂时不处理
			MonthLogQuery listQuery = new MonthLogQuery();
			BeanUtils.copyProperties(reqData, listQuery);
			PageResult<MonthLogVO> pageResult = meterMonthLogService
					.queryByPage(listQuery);
			List<MonthLogVO> data = pageResult.getData();
			List<MonthElectricVO> list = new ArrayList<>();
			if (data != null) {
				for (MonthLogVO mecVo : data) {
					MonthElectricVO monthElectricVO = new MonthElectricVO();
					BeanUtils.copyProperties(mecVo, monthElectricVO);
					list.add(monthElectricVO);
				}
			}
			result.setList(list);
			result.setHasNext(pageResult.isHasNext());
			result.setNextOffset(pageResult.getNextOffset());
			result.setTotalCount(pageResult.getTotal());
		} catch (Exception e) {
			result.set(Constants.ERROR_CODE, "系统异常");
			logger.error("系统异常:{}", e);
		}
		return result;
	}

	@Override
	public ReportListResult<VoltageVO> search(VoltageListReqData reqData) {
		ReportListResult<VoltageVO> result = new ReportListResult<VoltageVO>();
		try {
			// 校验暂时不处理
			MeterLogQuery listQuery = new MeterLogQuery();
			BeanUtils.copyProperties(reqData, listQuery);
			PageResult<MeterLogVO> pageResult = meterLogService
					.queryByPage(listQuery);
			List<VoltageVO> list = new ArrayList<>();
			if (pageResult.getData() != null) {
				for (MeterLogVO meterLogVO : pageResult.getData()) {
					VoltageVO voltageVO = new VoltageVO();
					BeanUtils.copyProperties(meterLogVO, voltageVO);
					list.add(voltageVO);
				}
			}
			result.setList(list);
			result.setHasNext(pageResult.isHasNext());
			result.setNextOffset(pageResult.getNextOffset());
			result.setTotalCount(pageResult.getTotal());
		} catch (Exception e) {
			result.set(Constants.ERROR_CODE, "系统异常");
			logger.error("系统异常:{}", e);
		}
		return result;
	}

	@Override
	public ReportListResult<CurrentVO> search(CurrentListReqData reqData) {
		ReportListResult<CurrentVO> result = new ReportListResult<CurrentVO>();
		try {
			// 校验暂时不处理
			MeterLogQuery listQuery = new MeterLogQuery();
			BeanUtils.copyProperties(reqData, listQuery);
			PageResult<MeterLogVO> pageResult = meterLogService
					.queryByPage(listQuery);
			List<CurrentVO> list = new ArrayList<>();
			if (pageResult.getData() != null) {
				for (MeterLogVO meterLogVO : pageResult.getData()) {
					CurrentVO currentVO = new CurrentVO();
					BeanUtils.copyProperties(meterLogVO, currentVO);
					list.add(currentVO);
				}
			}
			result.setList(list);
			result.setHasNext(pageResult.isHasNext());
			result.setNextOffset(pageResult.getNextOffset());
			result.setTotalCount(pageResult.getTotal());
		} catch (Exception e) {
			result.set(Constants.ERROR_CODE, "系统异常");
			logger.error("系统异常:{}", e);
		}
		return result;
	}

	@Override
	public ReportListResult<DayPowerFactorDetailVO> search(
			DayPowerFactorDetailListReqData reqData) {
		ReportListResult<DayPowerFactorDetailVO> result = new ReportListResult<DayPowerFactorDetailVO>();
		try {
			// 校验暂时不处理
			DayDetailLogQuery listQuery = new DayDetailLogQuery();
			BeanUtils.copyProperties(reqData, listQuery);
			PageResult<DayDetailLogVO> pageResult = meterDayDetailLogService
					.queryByPage(listQuery);
			List<DayPowerFactorDetailVO> list = new ArrayList<>();
			if (pageResult.getData() != null) {
				for (DayDetailLogVO ddlVo : pageResult.getData()) {
					DayPowerFactorDetailVO dpfdVo = new DayPowerFactorDetailVO();
					BeanUtils.copyProperties(ddlVo, dpfdVo);
					list.add(dpfdVo);
				}
			}
			result.setList(list);
			result.setHasNext(pageResult.isHasNext());
			result.setNextOffset(pageResult.getNextOffset());
			result.setTotalCount(pageResult.getTotal());
		} catch (Exception e) {
			result.set(Constants.ERROR_CODE, "系统异常");
			logger.error("系统异常:{}", e);
		}
		return result;
	}

	

	@Override
	public ReportListResult<DayElectricChargeVO> search(
			DayElectricChargeListReqData reqData) {
		ReportListResult<DayElectricChargeVO> result = new ReportListResult<DayElectricChargeVO>();
		try {
			// 校验暂时不处理
			DayLogQuery listQuery = new DayLogQuery();
			BeanUtils.copyProperties(reqData, listQuery);
			PageResult<DayLogVO> pageResult = meterDayLogService
					.queryByPage(listQuery);
			List<DayElectricChargeVO> list = new ArrayList<>();
			if (pageResult.getData() != null) {
				for (DayLogVO dayLogVO : pageResult.getData()) {
					DayElectricChargeVO dayElectricChargeVO = new DayElectricChargeVO();
					BeanUtils.copyProperties(dayLogVO, dayElectricChargeVO);
					list.add(dayElectricChargeVO);
				}
			}
			result.setList(list);
			result.setHasNext(pageResult.isHasNext());
			result.setNextOffset(pageResult.getNextOffset());
			result.setTotalCount(pageResult.getTotal());
		} catch (Exception e) {
			result.set(Constants.ERROR_CODE, "系统异常");
			logger.error("系统异常:{}", e);
		}
		return result;
	}

	@Override
	public ReportListResult<DayDetailLogVO> search(
			DayDetailLogListReqData reqData) {
		ReportListResult<DayDetailLogVO> result = new ReportListResult<DayDetailLogVO>();
		try {
			// 校验暂时不处理
			DayDetailLogQuery listQuery = new DayDetailLogQuery();
			BeanUtils.copyProperties(reqData, listQuery);
			PageResult<DayDetailLogVO> pageResult = meterDayDetailLogService
					.queryByPage(listQuery);

			result.setList(pageResult.getData());
			result.setHasNext(pageResult.isHasNext());
			result.setNextOffset(pageResult.getNextOffset());
			result.setTotalCount(pageResult.getTotal());
		} catch (Exception e) {
			result.set(Constants.ERROR_CODE, "系统异常");
			logger.error("系统异常:{}", e);
		}
		return result;
	}

	@Override
	public ReportListResult<DemandDetailVO> search(
			DemandDetailListReqData reqData) {
		ReportListResult<DemandDetailVO> result = new ReportListResult<DemandDetailVO>();
		try {
			// 校验暂时不处理
			DemandDetailQuery listQuery = new DemandDetailQuery();
			BeanUtils.copyProperties(reqData, listQuery);
			PageResult<DemandDetailVO> pageResult = demandDetailService
					.queryByPage(listQuery);

			result.setList(pageResult.getData());
			result.setHasNext(pageResult.isHasNext());
			result.setNextOffset(pageResult.getNextOffset());
			result.setTotalCount(pageResult.getTotal());
		} catch (Exception e) {
			result.set(Constants.ERROR_CODE, "系统异常");
			logger.error("系统异常:{}", e);
		}
		return result;
	}

	@Override
	public ReportListResult<DayVoltageCurrentDetailVO> search(
			DayVoltageCurrentDetailListReqData reqData) {
		ReportListResult<DayVoltageCurrentDetailVO> result = new ReportListResult<DayVoltageCurrentDetailVO>();
		try {
			// 校验暂时不处理
			DayVoltageCurrentDetailQuery listQuery = new DayVoltageCurrentDetailQuery();
			BeanUtils.copyProperties(reqData, listQuery);
			PageResult<DayVoltageCurrentDetailVO> pageResult = dayVoltageCurrentDetailService
					.queryByPage(listQuery);

			result.setList(pageResult.getData());
			result.setHasNext(pageResult.isHasNext());
			result.setNextOffset(pageResult.getNextOffset());
			result.setTotalCount(pageResult.getTotal());
		} catch (Exception e) {
			result.set(Constants.ERROR_CODE, "系统异常");
			logger.error("系统异常:{}", e);
		}
		return result;
	}
	@Override
	public DayLogVO getById(DayLogReqData reqData) {
		GetResponse response = meterDayLogService.getById(reqData.getId());
		if (response.isExists()) {
			try {
				return JSON.parse(response.getSourceAsString(),
						DayLogVO.class);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	@Override
	public DayElectricChargeVO getById(DayElectricChargeReqData reqData) {
		GetResponse response = meterDayLogService.getById(reqData.getId());
		if (response.isExists()) {
			try {
				return JSON.parse(response.getSourceAsString(),
						DayElectricChargeVO.class);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public DayAggregateVO getById(DayAggregateReqData reqData) {
		GetResponse response = meterDayLogService.getById(reqData.getId());
		if (response.isExists()) {
			try {
				return JSON.parse(response.getSourceAsString(),
						DayAggregateVO.class);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public DayLoadDetailVO getById(DayLoadDetailReqData reqData) {
		GetResponse response = dayLoadDetailService.getById(reqData.getId());
		if (response.isExists()) {
			try {
				return JSON.parse(response.getSourceAsString(),
						DayLoadDetailVO.class);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public MonthElectricChargeVO getById(MonthLogReqData reqData) {
		GetResponse response = meterMonthLogService.getById(reqData
				.getId());
		if (response.isExists()) {
			try {
				return JSON.parse(response.getSourceAsString(),
						MonthElectricChargeVO.class);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public MonthElectricVO getById(MonthElectricReqData reqData) {
		GetResponse response = meterMonthLogService.getById(reqData
				.getId());
		if (response.isExists()) {
			try {
				return JSON.parse(response.getSourceAsString(),
						MonthElectricVO.class);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public MonthElectricVO getById(MonthElectricReqData reqData,
			SubEnergyReq subEnergyReq) {
		MonthElectricVO monthElectricVO = getById(reqData);
		if (monthElectricVO != null && subEnergyReq != null
				&& subEnergyReq.isNeedSubEnergy()
				&& subEnergyReq.getMeterIds() != null) {// 分项能耗查询

			Map<String, Double> subEnergyMap = meterMonthLogService
					.getDividedElectric(reqData.getCompanyId(),
							subEnergyReq.getMeterIds(), reqData.getDate());
			if (!subEnergyMap.isEmpty()) {
				monthElectricVO.setAir(subEnergyMap.get(SubEnergyCode.AIR
						.getCode()));
				monthElectricVO.setLighting(subEnergyMap
						.get(SubEnergyCode.LIGHTING.getCode()));
				monthElectricVO.setOther(subEnergyMap.get(SubEnergyCode.OTHER
						.getCode()));
				monthElectricVO.setPower(subEnergyMap.get(SubEnergyCode.POWER
						.getCode()));
				monthElectricVO.setSpecial(subEnergyMap
						.get(SubEnergyCode.SPECIAL.getCode()));

			}
		}
		return monthElectricVO;
	}

	@Override
	public DayElectricVO getById(DayElectricReqData reqData) {
		GetResponse response = meterDayLogService.getById(reqData.getId());
		if (response.isExists()) {
			try {
				return JSON.parse(response.getSourceAsString(),
						DayElectricVO.class);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public MeterLogVO getById(MeterLogReqData reqData) {
		GetResponse response = meterLogService.getById(reqData.getId());
		if (response.isExists()) {
			try {
				return JSON.parse(response.getSourceAsString(),
						MeterLogVO.class);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public DayPowerFactorVO getById(DayPowerFactorReqData reqData) {
		GetResponse response = meterDayLogService.getById(reqData.getId());
		if (response.isExists()) {
			try {
				return JSON.parse(response.getSourceAsString(),
						DayPowerFactorVO.class);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	@Override
	public DemandDetailVO getById(DemandDetailReqData reqData) {
		GetResponse response = demandDetailService.getById(reqData.getId());
		if (response.isExists()) {
			try {
				return JSON.parse(response.getSourceAsString(),
						DemandDetailVO.class);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Double getAvgPower(Long companyId, String meterId, Long startTime,
			Long endTime) {

		return meterLogService.getAvgPower(companyId, meterId, startTime,
				endTime);
	}

	@Override
	public Double getVoltageQualifiedRate(Long companyId, String meterId,
			Long startTime, Long endTime) {
		return meterLogService.getVoltageQualifiedRate(companyId, meterId,
				startTime, endTime);
	}

	@Override
	public Double getMaxPower(Long companyId, String meterId, Long startTime,
			Long endTime) {

		return demandDetailService.getMaxPower(companyId, meterId, startTime,
				endTime);
	}

	@Override
	public ReportListResult<MeterLogVO> searchDistinctByMeterId(
			DistinctMeterLogListReqData reqData) {
		ReportListResult<MeterLogVO> result = new ReportListResult<MeterLogVO>();
		try {
			MeterLogQuery meterLogQuery = new MeterLogQuery();
			BeanUtils.copyProperties(reqData, meterLogQuery);
			PageResult<MeterLogVO> pageResult = meterLogService
					.searchDistinctByMeterId(meterLogQuery);
			result.setList(pageResult.getData());
			result.setHasNext(pageResult.isHasNext());
			result.setNextOffset(pageResult.getNextOffset());
			result.setTotalCount(pageResult.getTotal());
		} catch (Exception e) {
			result.set(Constants.ERROR_CODE, "系统异常");
			logger.error("系统异常:{}", e);
		}
		return result;
	}

	@Override
	public Map<String, Double> getMonthDividedElectric(Long companyId,
			String[] meterIds, String date) {
		return meterMonthLogService.getDividedElectric(companyId,
				meterIds, date);
	}

	@Override
	public DayVoltageCurrentDetailVO getById(
			DayVoltageCurrentDetailReqData reqData) {
		GetResponse response = dayVoltageCurrentDetailService.getById(reqData
				.getId());
		if (response.isExists()) {
			try {
				return JSON.parse(response.getSourceAsString(),
						DayVoltageCurrentDetailVO.class);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}



	@Override
	public StatListResult<ElectricChargeStat> getElectricChargeStats(ElectricChargeStatReqData electricChargeStatReqData) {
		StatListResult<ElectricChargeStat> result=new StatListResult<ElectricChargeStat>();
		try{
			
			String type=electricChargeStatReqData.getType();
			String fromDate=electricChargeStatReqData.getFromDate();
			String toDate=electricChargeStatReqData.getToDate();
			Set<String> meterIds=electricChargeStatReqData.getMeterIds();
			Long companyId=electricChargeStatReqData.getCompanyId();
			String source=electricChargeStatReqData.getSource();
			if(StringUtils.isBlank(source)){
				result.set(Constants.BAD_REQUEST, "source 不能为空!");
				return result;
			}
			source=source.trim();
			if(StringUtils.isBlank(type)){
				result.set(Constants.BAD_REQUEST, "type 不能为空!");
				return result;
			}
			if(!("DAY".equals(type)||"MONTH".equals(type)||"YEAR".equals(type))){
				result.set(Constants.BAD_REQUEST, "type 只能是DAY MONTH YEAR!");
				return result;
			}
			if(StringUtils.isBlank(fromDate)){
				result.set(Constants.BAD_REQUEST, "fromDate 不能为空!");
				return result;
			}
			if(!StringUtils.isNumeric(fromDate)){
				result.set(Constants.BAD_REQUEST, "fromDate 必须为数字!");
				return result;
			}
			String format="";
			if("DAY_LOG".equals(source)){
				format="yyyyMMdd";
				
			}
			else if("MONTH_LOG".equals(source)){
				format="yyyyMM";
			}
			else{
				result.set(Constants.BAD_REQUEST, "source 只支持(MONTH_LOG,DAY_LOG)!");
				return result;
			}
			if(!checkDate(format, fromDate)){
				result.set(Constants.BAD_REQUEST, "fromDate 格式必须为:"+format+" !");
				return result;
			}
			if(StringUtils.isNotBlank(toDate)){
				if(!StringUtils.isNumeric(toDate)){
					result.set(Constants.BAD_REQUEST, "toDate 必须为数字!");
					return result;
				}
				if(!checkDate(format, toDate)){
					result.set(Constants.BAD_REQUEST, "toDate 格式必须为:"+format+" !");
					return result;
				}
			}
			if(companyId==null){
				result.set(Constants.BAD_REQUEST, "companyId 不能为空!");
				return result;
			}
			if(meterIds==null||meterIds.isEmpty()){
				result.set(Constants.BAD_REQUEST, "meterIds 不能为空!");
				return result;
			}
			if(source.equals("DAY_LOG")){
				List<ElectricChargeStat> list=this.meterDayLogService.getDayStats(electricChargeStatReqData);
				result.setList(list);
			}
			else if(source.equals("MONTH_LOG")){
				List<ElectricChargeStat> list=this.meterMonthLogService.getYearMonthStats(electricChargeStatReqData);
				result.setList(list);
			}
			else{
				result.set(Constants.BAD_REQUEST, "source 只支持(MONTH_LOG,DAY_LOG)!");
				return result;
			}
			
		}
		catch(Exception e){
			result.set(Constants.ERROR_CODE, "系统异常");
			logger.error("系统异常:{}", e);
		}
		return result;
	}
	private boolean checkDate(String format, String date) {
		boolean rs=true;
		if(date.length()!=format.length()){
			return false;
		}
		Date time=DateUtils.parse(date,format);
		if(date==null){
			return false;
		}
		if(format.equals("yyyyMMdd")){
			String mm=date.substring(4, 6);
			if(Integer.parseInt(mm)!=(time.getMonth()+1)){
				return false;
			}
			String dd=date.substring(6,8);
			if(Integer.parseInt(dd)!=time.getDate()){
				return false;
			}
		}
		else if(format.equals("yyyyMM")){
			String mm=date.substring(4, 6);
			if(Integer.parseInt(mm)!=(time.getMonth()+1)){
				return false;
			}
		}
		return rs;
	}

	@Autowired
    private MeterLogRepository meterLogRepository;
	@Override
	public List<StatLog> getStatLogs(String fromDate, String toDate,String type,
			Set<String> meterIds, Long companyId) {
		
		return meterLogRepository.getStatLogs(fromDate, toDate, type,meterIds, companyId);

	}
	@Override
	public AvgListResult<AvgOfMeterStat> avgOfMeter(AvgOfMetreReqData req) {
		AvgListResult<AvgOfMeterStat> result = new AvgListResult<AvgOfMeterStat>();
		try {
			Long companyId=req.getCompanyId();
			String fromDate=req.getFromDate().trim();
			Set<String> meterIds=req.getMeterIds();
			if(companyId==null){
				result.set(Constants.BAD_REQUEST, "companyId 不能为空!");
				return result;
			}
			if(StringUtils.isBlank(fromDate)){
				result.set(Constants.BAD_REQUEST, "fromDate不能为空!");
				return result;
			}
			if(!StringUtils.isNumeric(fromDate)){
				result.set(Constants.BAD_REQUEST, "fromDate必须为数字!");
				return result;
			}
			if(!checkDate("yyyyMMdd", fromDate)){
				result.set(Constants.BAD_REQUEST, "fromDate 格式必须为:yyyyMMdd!");
				return result;
			}
			if(meterIds==null||meterIds.isEmpty()){
				result.set(Constants.BAD_REQUEST, "meterIds 不能为空!");
				return result;
			}
			List<AvgOfMeterStat> list = meterDayLogService.avgOfMeter(req);
			result.setList(list);
		} catch (Exception e) {
			result.set(Constants.ERROR_CODE, "系统异常");
			logger.error("系统异常:{}", e);
		}
		return result;

	}
}
