package cn.sdqingyun.smartpark.module.bus.dal.mysql.tag;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagContractDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.*;

/**
 * 合同标签 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface TagContractMapper extends BaseMapperX<TagContractDO> {

    default PageResult<TagContractDO> selectPage(TagContractPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TagContractDO>()
                .eqIfPresent(TagContractDO::getOrgId, reqVO.getOrgId())
                .likeIfPresent(TagContractDO::getName, reqVO.getName())
                .eqIfPresent(TagContractDO::getDescVillage, reqVO.getDescVillage())
                .eqIfPresent(TagContractDO::getColor, reqVO.getColor())
                .eqIfPresent(TagContractDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(TagContractDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TagContractDO::getId));
    }

}