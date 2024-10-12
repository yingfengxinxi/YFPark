package cn.sdqingyun.smartpark.module.bus.dal.mysql.village;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.ThirdButtDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;

/**
 * 第三方数据对接（目前用于智慧社区系统，全功能版） Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface ThirdButtMapper extends BaseMapperX<ThirdButtDO> {

    default PageResult<ThirdButtDO> selectPage(ThirdButtPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ThirdButtDO>()
                .eqIfPresent(ThirdButtDO::getBusinessType, reqVO.getBusinessType())
                .eqIfPresent(ThirdButtDO::getBusinessId, reqVO.getBusinessId())
                .eqIfPresent(ThirdButtDO::getSelfId, reqVO.getSelfId())
                .eqIfPresent(ThirdButtDO::getOriginalData, reqVO.getOriginalData())
                .betweenIfPresent(ThirdButtDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ThirdButtDO::getId));
    }

}