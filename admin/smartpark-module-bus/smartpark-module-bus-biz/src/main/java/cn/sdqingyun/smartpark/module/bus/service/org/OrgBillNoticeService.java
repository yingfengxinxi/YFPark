package cn.sdqingyun.smartpark.module.bus.service.org;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.NoticeListVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillNoticePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillNoticeSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillNoticeDO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 收款通知记录 Service 接口
 *
 * @author 智慧园区
 */
public interface OrgBillNoticeService {


    /**
     * @param createReqVO
     * @return
     * @throws Exception
     */
    String batchCreate(@Valid OrgBillNoticeSaveReqVO createReqVO, HttpServletResponse response) throws Exception;

    /**
     * 更新收款通知记录
     *
     * @param updateReqVO 更新信息
     */
    void updateBillNotice(@Valid OrgBillNoticeSaveReqVO updateReqVO);

    /**
     * 删除收款通知记录
     *
     * @param id 编号
     */
    void deleteBillNotice(Long id);

    /**
     * 获得收款通知记录
     *
     * @param id 编号
     * @return 收款通知记录
     */
    OrgBillNoticeDO getBillNotice(Long id);

    /**
     * 获得收款通知记录分页
     *
     * @param noticeListVO 分页查询
     * @return 收款通知记录分页
     */
    PageResult<NoticeListVO> getBillNoticePage(NoticeListVO noticeListVO);

    /**
     * @param noticeListVO
     * @return
     */
    Map<String, Object> getTopNumCount(NoticeListVO noticeListVO);

}