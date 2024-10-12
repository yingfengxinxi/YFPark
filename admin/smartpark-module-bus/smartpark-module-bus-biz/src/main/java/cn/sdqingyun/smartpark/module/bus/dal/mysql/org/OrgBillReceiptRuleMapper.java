package cn.sdqingyun.smartpark.module.bus.dal.mysql.org;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptRulePageReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillReceiptRuleDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 账单收据编号规则 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface OrgBillReceiptRuleMapper extends BaseMapperX<OrgBillReceiptRuleDO> {

    /**
     *
     * @param buildBindList
     * @return
     */
    OrgBillReceiptRuleDO getByBuildsInfo(@Param("buildBindList") List<Long> buildBindList);

    default PageResult<OrgBillReceiptRuleDO> selectPage(OrgBillReceiptRulePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OrgBillReceiptRuleDO>()
                .likeIfPresent(OrgBillReceiptRuleDO::getName, reqVO.getName())
                .eqIfPresent(OrgBillReceiptRuleDO::getPrefix, reqVO.getPrefix())
                .eqIfPresent(OrgBillReceiptRuleDO::getStartNumber, reqVO.getStartNumber())
                .eqIfPresent(OrgBillReceiptRuleDO::getEndNumber, reqVO.getEndNumber())
                .eqIfPresent(OrgBillReceiptRuleDO::getBuildBind, reqVO.getBuildBind())
                .betweenIfPresent(OrgBillReceiptRuleDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OrgBillReceiptRuleDO::getId));
    }

}