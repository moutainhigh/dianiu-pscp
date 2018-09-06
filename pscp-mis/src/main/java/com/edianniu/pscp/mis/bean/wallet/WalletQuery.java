package com.edianniu.pscp.mis.bean.wallet;

import com.edianniu.pscp.mis.commons.BaseQuery;

import java.io.Serializable;

/**
 * @author AbnerElk
 */
public class WalletQuery extends BaseQuery implements Serializable {

    private static final long serialVersionUID = 1L;
    private String sorts;
    private Long uid;

    public String getSorts() {
        return sorts;
    }

    public void setSorts(String sorts) {
        this.sorts = sorts;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
