package cn.sdqingyun.smartpark.module.bus.dal.mysql.tag;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.TagTerminationPageReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagTerminationDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 退租原因 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface TagTerminationMapper extends BaseMapperX<TagTerminationDO> {

    default PageResult<TagTerminationDO> selectPage(TagTerminationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TagTerminationDO>()
                .likeIfPresent(TagTerminationDO::getName, reqVO.getName())
                .eqIfPresent(TagTerminationDO::getRemarks, reqVO.getRemarks())
                .eqIfPresent(TagTerminationDO::getColor, reqVO.getColor())
                .eqIfPresent(TagTerminationDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(TagTerminationDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TagTerminationDO::getId));
    }

}