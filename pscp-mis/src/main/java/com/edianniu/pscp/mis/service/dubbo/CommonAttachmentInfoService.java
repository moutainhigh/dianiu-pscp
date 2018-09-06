package com.edianniu.pscp.mis.service.dubbo;

import com.edianniu.pscp.mis.bean.attachment.common.*;

/**
 * ${comments}
 *
 * @author wangcheng.li
 * @email wangcheng.li@edianniu.com
 * @date 2017-09-19 11:04:25
 */
public interface CommonAttachmentInfoService {
    /**
     * 根据ID获取附件信息
     *
     * @param reqData
     * @return
     */
    QueryOneResult getAttachmentById(QueryOneReqData reqData);

    /**
     * 根据条件获取附件信息
     *
     * @param reqData
     * @return
     */
    QueryListResult queryListAttachment(QueryListReqData reqData);

    /**
     * 批量保存附件信息
     *
     * @param reqData
     * @return
     */
    SaveResult saveBatchEntity(SaveReqData reqData);

    /**
     * 批量删除附件信息
     *
     * @param reqData
     * @return
     */
    DeleteResult deleteBatchEntity(DeleteReqData reqData);

    /**
     * 批量修改附件信息
     *
     * @param reqData
     * @return
     */
    UpdateResult updateBatchEntity(UpdateReqData reqData);
}
