package cn.sdqingyun.smartpark.module.bus.dal.mysql.contract;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractOperateLogPageReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractOperateLogDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 合同操作日志 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface ContractOperateLogMapper extends BaseMapperX<ContractOperateLogDO> {


}