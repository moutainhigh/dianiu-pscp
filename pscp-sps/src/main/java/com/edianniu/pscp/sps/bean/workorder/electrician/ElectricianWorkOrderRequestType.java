package com.edianniu.pscp.sps.bean.workorder.electrician;

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * package name: com.edianniu.pscp.sps.bean.workorder.electrician
 * project name: pscp-sps
 * user: tandingbo
 * create time: 2017-05-10 16:49
 */
public enum ElectricianWorkOrderRequestType {
    UN_CONFIRM("unconfirm", "未确认") {
        @Override
        public Integer[] getStatus(boolean isCompany) {
            return new Integer[]{
            	ElectricianWorkOrderStatus.UNCONFIRMED.getValue()
            };
        }
    },
    CONFIRMED("ongoing", "进行中") {
        @Override
        public Integer[] getStatus(boolean isCompany) {
            return new Integer[]{
	            ElectricianWorkOrderStatus.CONFIRMED.getValue(),
	            ElectricianWorkOrderStatus.EXECUTING.getValue()
            };
        }
    },
    STARTED("unsettlement", "待结算") {
        @Override
        public Integer[] getStatus(boolean isCompany) {
            Set<Integer> stateSet = new HashSet<Integer>();
        	stateSet.add(ElectricianWorkOrderStatus.FINISHED.getValue());
            stateSet.add(ElectricianWorkOrderStatus.FEE_CONFIRM.getValue());
            return stateSet.toArray(new Integer[]{});
        }
    },
    COMPLETED("finished", "已完成") {
        @Override
        public Integer[] getStatus(boolean isCompanyEletrician) {
            Set<Integer> stateSet = new HashSet<Integer>();
            stateSet.add(ElectricianWorkOrderStatus.CANCELED.getValue());
            stateSet.add(ElectricianWorkOrderStatus.FAIL.getValue());
            if (isCompanyEletrician) {
            	stateSet.add(ElectricianWorkOrderStatus.FINISHED.getValue());
			} 
            if(! isCompanyEletrician){
            	stateSet.add(ElectricianWorkOrderStatus.LIQUIDATED.getValue());
            }
            
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

    /**
     * 获取value的组合
     *
     * @return
     */
    public static String getValues() {
        StringBuffer sb = new StringBuffer(64);
        for (ElectricianWorkOrderRequestType electricianWorkOrderRequestType : ElectricianWorkOrderRequestType.values()) {
            sb.append(electricianWorkOrderRequestType.getValue()).append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }
}
