package com.edianniu.pscp.sps.bean.socialworkorder.resume;

/**
 * ClassName: DiplomaType
 * Author: tandingbo
 * CreateTime: 2017-05-24 15:04
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
        if (value == null) {
            return "";
        }

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
