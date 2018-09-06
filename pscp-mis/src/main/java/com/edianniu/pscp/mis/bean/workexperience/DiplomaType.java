package com.edianniu.pscp.mis.bean.workexperience;

/**
 * 学历类型
 * ClassName: DiplomaType
 * Author: tandingbo
 * CreateTime: 2017-04-20 10:51
 */
public enum DiplomaType {
    OTHER("其他", 0),
    MIDDLE_SCHOOL("初中", 1),
    HIGH_SCHOOL("高中", 2),
    JUNIOR_COLLEGE("大专", 3),
    UNDERGRADUATE("本科", 4);

    private final String name;
    private final Integer value;

    DiplomaType(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public static String getName(Integer value) {
        DiplomaType[] values = DiplomaType.values();
        for (DiplomaType diplomaType : values) {
            if (value.equals(diplomaType.getValue())) {
                return diplomaType.getName();
            }
        }
        return "";
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }
}
