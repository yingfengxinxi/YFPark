package cn.sdqingyun.smartpark.module.bus.dal.mysql.village;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageTypeDictDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;

/**
 * 项目类型字典 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface VillageTypeDictMapper extends BaseMapperX<VillageTypeDictDO> {

    default PageResult<VillageTypeDictDO> selectPage(VillageTypeDictPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<VillageTypeDictDO>()
                .eqIfPresent(VillageTypeDictDO::getTypeAlias, reqVO.getTypeAlias())
                .eqIfPresent(VillageTypeDictDO::getWords, reqVO.getWords())
                .eqIfPresent(VillageTypeDictDO::getZhCn, reqVO.getZhCn())
                .eqIfPresent(VillageTypeDictDO::getZhHk, reqVO.getZhHk())
                .eqIfPresent(VillageTypeDictDO::getZhTw, reqVO.getZhTw())
                .eqIfPresent(VillageTypeDictDO::getEn, reqVO.getEn())
                .eqIfPresent(VillageTypeDictDO::getJa, reqVO.getJa())
                .betweenIfPresent(VillageTypeDictDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(VillageTypeDictDO::getId));
    }

}