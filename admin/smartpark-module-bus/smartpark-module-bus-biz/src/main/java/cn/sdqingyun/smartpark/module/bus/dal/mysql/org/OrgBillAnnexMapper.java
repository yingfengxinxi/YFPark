package cn.sdqingyun.smartpark.module.bus.dal.mysql.org;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillAnnexPageReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillAnnexDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 机构账单收支流水附件 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface OrgBillAnnexMapper extends BaseMapperX<OrgBillAnnexDO> {


}