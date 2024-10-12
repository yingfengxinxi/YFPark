package cn.sdqingyun.smartpark.module.bus.service.org;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillStreamMiddlePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillStreamMiddleSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.StreamIdMatchingListPageVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillStreamMiddleDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 机构流水账单中间表【匹配】 Service 接口
 *
 * @author 智慧园区
 */
public interface OrgBillStreamMiddleService {

    /**
     * 更新机构流水账单中间表【匹配】
     *
     * @param updateReqVO 更新信息
     */
    void updateBillStreamMiddle(@Valid OrgBillStreamMiddleSaveReqVO updateReqVO);

    /**
     * 删除机构流水账单中间表【匹配】
     *
     * @param id 编号
     */
    void deleteBillStreamMiddle(Long id);

    /**
     * 获得机构流水账单中间表【匹配】
     *
     * @param id 编号
     * @return 机构流水账单中间表【匹配】
     */
    OrgBillStreamMiddleDO getBillStreamMiddle(Long id);

    /**
     * 获得机构流水账单中间表【匹配】分页
     *
     * @param pageReqVO 分页查询
     * @return 机构流水账单中间表【匹配】分页
     */
    PageResult<StreamIdMatchingListPageVO> getBillStreamMiddlePage(OrgBillStreamMiddlePageReqVO pageReqVO);

    /**
     * @param billId
     * @return
     */
    List<OrgBillStreamMiddleDO> getBillIdStreamMiddleList(Long billId);

    /**
     * @param pageReqVO
     * @return
     */
    PageResult<OrgBillStreamMiddleDO> getBillStreamListPage(OrgBillStreamMiddlePageReqVO pageReqVO);

    /**
     *
     */
    Integer getByStreamIdCount(Long streamId);

    /**
     * @param billIds
     * @param streamId
     * @param matchPrice
     * @param matchDate
     * @throws ParseException
     */
    void addMiddle(List<Long> billIds, Long streamId, BigDecimal matchPrice, String matchDate) throws ParseException;


    /**
     * @param billId
     * @param streamIds
     * @param matchPrice
     * @param matchDate
     */
    void addCollectionMiddle(Long billId, List<Long> streamIds, BigDecimal matchPrice, String matchDate) throws ParseException;

    /**
     * @param id
     * @param cancelMatchDate
     */
    void cancelMatch(Long id, String cancelMatchDate) throws ParseException;

}