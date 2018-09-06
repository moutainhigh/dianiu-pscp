package com.edianniu.pscp.sps.bean.identifications;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.sps.bean.identifications.vo.IdentificationsVO;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: StructureIdentificationsData
 * Author: tandingbo
 * CreateTime: 2017-05-17 18:20
 */
public class StructureIdentificationsData {
    private StructureIdentificationsData() {
    }

    private static String[] names = new String[]{
            "触电", "灼烫", "火灾", "高处坠落", "机械伤害", "物体打击", "起重伤害", "容器爆炸",
            "中毒", "窒息", "粉尘", "噪声", "震动", "高温", "电离辐射"/*, "其它"*/
    };

    private static List<IdentificationsVO> identificationsVOList = new ArrayList<>();

    static {
        if (CollectionUtils.isEmpty(identificationsVOList)) {
            Long id = 1000L;
            for (String name : names) {
                IdentificationsVO identificationsVO = new IdentificationsVO();
                identificationsVO.setId(id);
                identificationsVO.setName(name);
                identificationsVOList.add(identificationsVO);
                id++;
            }
        }
    }

    public static List<IdentificationsVO> getIdentificationsVOList() {
        return identificationsVOList;
    }

    public static void main(String[] args) {
        List<IdentificationsVO> list = StructureIdentificationsData.getIdentificationsVOList();
        System.out.println(JSON.toJSONString(list));
    }
}
