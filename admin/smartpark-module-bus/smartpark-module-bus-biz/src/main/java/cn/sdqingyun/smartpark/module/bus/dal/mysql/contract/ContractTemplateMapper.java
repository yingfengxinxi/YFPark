package cn.sdqingyun.smartpark.module.bus.dal.mysql.contract;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractTemplateDO;
import org.apache.ibatis.annotations.Mapper;
/**
 * 机构合同模板配置 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface ContractTemplateMapper extends BaseMapperX<ContractTemplateDO> {

}