package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffDepositoryDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 耗材档案信息 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyStuffDepositoryMapper extends BaseMapperX<PropertyStuffDepositoryDO> {

    default PageResult<PropertyStuffDepositoryDO> selectPage(PropertyStuffDepositoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyStuffDepositoryDO>()
                .eqIfPresent(PropertyStuffDepositoryDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyStuffDepositoryDO::getNumber, reqVO.getNumber())
                .likeIfPresent(PropertyStuffDepositoryDO::getName, reqVO.getName())
                .eqIfPresent(PropertyStuffDepositoryDO::getParentId, reqVO.getParentId())
                .eqIfPresent(PropertyStuffDepositoryDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyStuffDepositoryDO::getLevel, reqVO.getLevel())
                .eqIfPresent(PropertyStuffDepositoryDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PropertyStuffDepositoryDO::getSort, reqVO.getSort())
                .eqIfPresent(PropertyStuffDepositoryDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyStuffDepositoryDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(PropertyStuffDepositoryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyStuffDepositoryDO::getId));
    }

    default String getNameById(Long id) {
        PropertyStuffDepositoryDO depositoryDO = selectById( id );
        if (depositoryDO != null) {
            return depositoryDO.getName();
        }
        return null;
    }
}