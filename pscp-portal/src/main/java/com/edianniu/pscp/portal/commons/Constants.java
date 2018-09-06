package com.edianniu.pscp.portal.commons;

import com.edianniu.pscp.mis.bean.wallet.BankType;

import java.util.ArrayList;
import java.util.List;

public class Constants {


    public static final int TAG_YES = 1;//是

    public static final int TAG_NO = 0;//否

    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final List<BankType> bankTypes = new ArrayList<BankType>();

    static {
        BankType zg = new BankType();
        zg.setId(1001L);
        zg.setName("中国银行");
        bankTypes.add(zg);

        BankType ny = new BankType();
        ny.setId(1002L);
        ny.setName("中国农业银行");
        bankTypes.add(ny);

        BankType gs = new BankType();
        gs.setId(1003L);
        gs.setName("中国工商银行");
        bankTypes.add(gs);

        BankType js = new BankType();
        js.setId(1004L);
        js.setName("中国建设银行");
        bankTypes.add(js);

        BankType ys = new BankType();
        ys.setId(1005L);
        ys.setName("中国邮政储蓄银行");
        bankTypes.add(ys);

        BankType zs = new BankType();
        zs.setId(1006L);
        zs.setName("招商银行");
        bankTypes.add(zs);

        BankType jt = new BankType();
        jt.setId(1007L);
        jt.setName("交通银行");
        bankTypes.add(jt);

        BankType gd = new BankType();
        gd.setId(1008L);
        gd.setName("中国光大银行");
        bankTypes.add(gd);

        BankType pf = new BankType();
        pf.setId(1009L);
        pf.setName("浦发银行");
        bankTypes.add(pf);

        BankType zx = new BankType();
        zx.setId(1010L);
        zx.setName("中信银行");
        bankTypes.add(zx);

        BankType ms = new BankType();
        ms.setId(1011L);
        ms.setName("中国民生银行");
        bankTypes.add(ms);

        BankType xy = new BankType();
        xy.setId(1012L);
        xy.setName("兴业银行");
        bankTypes.add(xy);

        BankType hx = new BankType();
        hx.setId(1013L);
        hx.setName("华夏银行");
        bankTypes.add(hx);

        BankType gf = new BankType();
        gf.setId(1014L);
        gf.setName("广发银行");
        bankTypes.add(gf);

        BankType sh = new BankType();
        sh.setId(1015L);
        sh.setName("上海银行");
        bankTypes.add(sh);

        BankType hz = new BankType();
        hz.setId(1016L);
        hz.setName("杭州银行");
        bankTypes.add(hz);

        BankType nb = new BankType();
        nb.setId(1017L);
        nb.setName("宁波银行");
        bankTypes.add(nb);

        BankType zsh = new BankType();
        zsh.setId(1018L);
        zsh.setName("浙商银行");
        bankTypes.add(zsh);

        BankType apliy = new BankType();
        apliy.setId(2L);
        apliy.setName("支付宝");
        bankTypes.add(apliy);
    }

    public static String parseBankId(Integer bankId) {
        String desc = "";
        if (bankId == null) {
            desc = "银行卡id不能为空";
        } else {
            for (BankType type : bankTypes) {
                if (bankId.intValue() == type.getId().intValue()) {
                    desc = type.getName();
                    break;
                }
            }
        }
        return desc;
    }
}
