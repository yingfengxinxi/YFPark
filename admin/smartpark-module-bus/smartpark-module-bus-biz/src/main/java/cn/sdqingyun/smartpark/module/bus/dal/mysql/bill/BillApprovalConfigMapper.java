package cn.sdqingyun.smartpark.module.bus.dal.mysql.bill;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.bill.BillApprovalConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 账单业务审批配置 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface BillApprovalConfigMapper extends BaseMapperX<BillApprovalConfigDO> {

}