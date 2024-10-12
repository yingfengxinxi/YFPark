package cn.sdqingyun.smartpark.module.bus.dal.mysql.village;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.UserIcCardDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;

/**
 * 住户的IC卡 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface UserIcCardMapper extends BaseMapperX<UserIcCardDO> {

    default PageResult<UserIcCardDO> selectPage(UserIcCardPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<UserIcCardDO>()
                .eqIfPresent(UserIcCardDO::getUserId, reqVO.getUserId())
                .eqIfPresent(UserIcCardDO::getOwnerId, reqVO.getOwnerId())
                .eqIfPresent(UserIcCardDO::getIcCard, reqVO.getIcCard())
                .eqIfPresent(UserIcCardDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(UserIcCardDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(UserIcCardDO::getId));
    }

}