package cn.sdqingyun.smartpark.module.bus.dal.mysql.org;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptTemplatePageReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillReceiptTemplateDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 收据模板 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface OrgBillReceiptTemplateMapper extends BaseMapperX<OrgBillReceiptTemplateDO> {


    /**
     * @param buildBindList
     * @return
     */
    List<OrgBillReceiptTemplateDO> getByBuildsList(List<Long> buildBindList);

}