package cn.sdqingyun.smartpark.module.bus.dal.mysql.org;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptSellerPageReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillReceiptSellerDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 收据收款方信息 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface OrgBillReceiptSellerMapper extends BaseMapperX<OrgBillReceiptSellerDO> {


    /**
     *
     * @param buildBindList
     * @return
     */
    List<OrgBillReceiptSellerDO> getByBuildsList(List<Long> buildBindList);
}