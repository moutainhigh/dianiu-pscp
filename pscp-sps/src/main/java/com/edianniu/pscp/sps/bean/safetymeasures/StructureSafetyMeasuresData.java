package com.edianniu.pscp.sps.bean.safetymeasures;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.sps.bean.safetymeasures.vo.SafetyMeasuresVO;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 构建安全措施信息
 * ClassName: StructureSafetyMeasuresData
 * Author: tandingbo
 * CreateTime: 2017-05-17 17:37
 */
public class StructureSafetyMeasuresData {
    private StructureSafetyMeasuresData() {
    }

    private static String[] names = new String[]{
            "检修设备是否需要办理工作票、动火工作票等手续",
            "检修设备是否断电、挂牌",
            "检修设备是否切断隔离",
            "现场通风是否良好",
            "现场照明是否良好",
            "周围可燃物是否清理干净或隔离",
            "是否对检修人员已讲明现场及被检修设备的状况",
            "业主单位责任人是否对检修人员安全交底",
            "业主单位是否专人在现场协调检修",
            "特殊动火、进入受限空间、高空作业等是否办理相关票证",
            "是否具备作业条件"
//            "需补充的内容（业主单位负责人填写）"
    };
    private static List<SafetyMeasuresVO> safetyMeasuresVOList = new ArrayList<>();

    static {
        if (CollectionUtils.isEmpty(safetyMeasuresVOList)) {
            Long id = 1000L;
            for (String name : names) {
                SafetyMeasuresVO safetyMeasuresVO = new SafetyMeasuresVO();
                safetyMeasuresVO.setId(id);
                safetyMeasuresVO.setName(name);
                safetyMeasuresVOList.add(safetyMeasuresVO);
                id++;
            }
        }
    }

    public static List<SafetyMeasuresVO> getSafetyMeasuresVOList() {
        return safetyMeasuresVOList;
    }

    public static void main(String[] args) {
        List<SafetyMeasuresVO> list = StructureSafetyMeasuresData.getSafetyMeasuresVOList();
        System.out.println(JSON.toJSONString(list));
    }
}
