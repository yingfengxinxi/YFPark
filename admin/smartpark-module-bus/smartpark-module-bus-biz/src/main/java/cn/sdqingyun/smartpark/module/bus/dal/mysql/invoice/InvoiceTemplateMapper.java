package cn.sdqingyun.smartpark.module.bus.dal.mysql.invoice;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.invoice.vo.InvoiceTemplatePageReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.invoice.InvoiceTemplateDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 发票模板配置 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface InvoiceTemplateMapper extends BaseMapperX<InvoiceTemplateDO> {

}