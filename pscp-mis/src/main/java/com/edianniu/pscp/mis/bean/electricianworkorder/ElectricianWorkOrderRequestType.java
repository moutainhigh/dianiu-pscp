package com.edianniu.pscp.mis.bean.electricianworkorder;

import org.apache.commons.lang3.StringUtils;

import com.edianniu.pscp.mis.commons.Constants;

import java.util.HashSet;
import java.util.Set;

/**
 * ClassName: ElectricianWorkOrderRequestType
 * Author: tandingbo
 * CreateTime: 2017-04-13 15:48
 */
public enum ElectricianWorkOrderRequestType {
    UN_CONFIRMED("unconfirmed", "未确认") {
        @Override
        public Integer[] getStatus(boolean isCompany) {
            return new Integer[]{ElectricianWorkOrderStatus.UNCONFIRMED.getValue()};
        }
    },
    UN_FINISHED("unfinished", "未完成") {
        @Override
        public Integer[] getStatus(boolean isCompany) {
            Set<Integer> stateSet = new HashSet<Integer>();
            if (isCompany) {
                stateSet.add(ElectricianWorkOrderStatus.UNCONFIRMED.getValue());
            }
            stateSet.add(ElectricianWorkOrderStatus.CONFIRMED.getValue());
            stateSet.add(ElectricianWorkOrderStatus.EXECUTING.getValue());
            return stateSet.toArray(new Integer[]{});
        }
    },
    FINISHED("finished", "已完成") {
        @Override
        public Integer[] getStatus(boolean isCompany) {
            Set<Integer> stateSet = new HashSet<Integer>();
            stateSet.add(ElectricianWorkOrderStatus.FAIL.getValue());
            stateSet.add(ElectricianWorkOrderStatus.CANCELED.getValue());
            stateSet.add(ElectricianWorkOrderStatus.LIQUIDATED.getValue());
            if (isCompany) {
                stateSet.add(ElectricianWorkOrderStatus.FINISHED.getValue());
            }
            return stateSet.toArray(new Integer[]{});
        }
        
        public Integer[] getStatusByType(Integer type){
        	Set<Integer> stateSet = new HashSet<Integer>();
        	stateSet.add(ElectricianWorkOrderStatus.FAIL.getValue());
            stateSet.add(ElectricianWorkOrderStatus.CANCELED.getValue());
            if (type == Constants.ELECTRICIAN_TYPE_COMPANY_ELECTRICIAN) {
            	stateSet.add(ElectricianWorkOrderStatus.FINISHED.getValue());
            	stateSet.add(ElectricianWorkOrderStatus.LIQUIDATED.getValue());
			}
            if (type == Constants.ELECTRICIAN_TYPE_SOCIAL_ELECTRICIAN) {
            	stateSet.add(ElectricianWorkOrderStatus.LIQUIDATED.getValue());
			}
            if (type == Constants.ELECTRICIAN_TYPE_UNBUNG_ELECTRICIAN) {
            	stateSet.add(ElectricianWorkOrderStatus.FINISHED.getValue());
			}
        	return stateSet.toArray(new Integer[]{});
        }
    },
    UNLIQUIDAT("unliquidat", "待结算") {
        @Override
        public Integer[] getStatus(boolean isCompany) {
            Set<Integer> stateSet = new HashSet<Integer>();
            stateSet.add(ElectricianWorkOrderStatus.FINISHED.getValue());
            stateSet.add(ElectricianWorkOrderStatus.FEE_CONFIRM.getValue());
            return stateSet.toArray(new Integer[]{});
        }
    };

    private final String value;
    private final String name;

    ElectricianWorkOrderRequestType(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public Integer[] getStatus(boolean isCompany) {
        return new Integer[]{};
    }
    
    public Integer[] getStatusByType(Integer type){
    	return new Integer[]{};
    }

    /**
     * 根据值获取Enum对象
     *
     * @param value
     * @return
     */
    public static ElectricianWorkOrderRequestType get(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }

        ElectricianWorkOrderRequestType[] values = ElectricianWorkOrderRequestType.values();
        for (ElectricianWorkOrderRequestType electricianWorkOrderRequestType : values) {
            if (value.equals(electricianWorkOrderRequestType.getValue())) {
                return electricianWorkOrderRequestType;
            }
        }
        return null;
    }

    /**
     * 判断值是否存在
     *
     * @param value
     * @return
     */
    public static Boolean isExist(String value) {
        ElectricianWorkOrderRequestType[] values = ElectricianWorkOrderRequestType.values();
        for (ElectricianWorkOrderRequestType electricianWorkOrderRequestType : values) {
            if (value.equals(electricianWorkOrderRequestType.getValue())) {
                return true;
            }
        }
        return false;
    }

}
