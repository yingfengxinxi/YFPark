package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyOperateLogDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 资产操作日志 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyOperateLogMapper extends BaseMapperX<PropertyOperateLogDO> {

    default PageResult<PropertyOperateLogDO> selectPage(PropertyOperateLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyOperateLogDO>()
                .eqIfPresent(PropertyOperateLogDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyOperateLogDO::getOperateUid, reqVO.getOperateUid())
                .eqIfPresent(PropertyOperateLogDO::getPropertyJson, reqVO.getPropertyJson())
                .eqIfPresent(PropertyOperateLogDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(PropertyOperateLogDO::getOperateType, reqVO.getOperateType())
                .eqIfPresent(PropertyOperateLogDO::getLogType, reqVO.getLogType())
                .eqIfPresent(PropertyOperateLogDO::getOperateUser, reqVO.getOperateUser())
                .betweenIfPresent(PropertyOperateLogDO::getOperateTime, reqVO.getOperateTime())
                .eqIfPresent(PropertyOperateLogDO::getOperateContent, reqVO.getOperateContent())
                .eqIfPresent(PropertyOperateLogDO::getOtherContent, reqVO.getOtherContent())
                .eqIfPresent(PropertyOperateLogDO::getOperate, reqVO.getOperate())
                .betweenIfPresent(PropertyOperateLogDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyOperateLogDO::getId));
    }

}