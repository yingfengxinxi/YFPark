package cn.sdqingyun.smartpark.module.bus.dal.mysql.village;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.UserFieldExtendPageReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.UserFieldExtendDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户扩展信息自定义系统设置 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface UserFieldExtendMapper extends BaseMapperX<UserFieldExtendDO> {

    default PageResult<UserFieldExtendDO> selectPage(UserFieldExtendPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<UserFieldExtendDO>()
                .eqIfPresent(UserFieldExtendDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(UserFieldExtendDO::getVillageId, reqVO.getVillageId())
                .eqIfPresent(UserFieldExtendDO::getSysFieldId, reqVO.getSysFieldId())
                .likeIfPresent(UserFieldExtendDO::getFieldName, reqVO.getFieldName())
                .likeIfPresent(UserFieldExtendDO::getInputName, reqVO.getInputName())
                .eqIfPresent(UserFieldExtendDO::getInputNameCus, reqVO.getInputNameCus())
                .eqIfPresent(UserFieldExtendDO::getInputType, reqVO.getInputType())
                .eqIfPresent(UserFieldExtendDO::getDefaultInputValue, reqVO.getDefaultInputValue())
                .eqIfPresent(UserFieldExtendDO::getDefaultFieldValue, reqVO.getDefaultFieldValue())
                .eqIfPresent(UserFieldExtendDO::getIsEnable, reqVO.getIsEnable())
                .eqIfPresent(UserFieldExtendDO::getIsRequired, reqVO.getIsRequired())
                .eqIfPresent(UserFieldExtendDO::getIsAllowModifie, reqVO.getIsAllowModifie())
                .eqIfPresent(UserFieldExtendDO::getJsonFields, reqVO.getJsonFields())
                .eqIfPresent(UserFieldExtendDO::getSort, reqVO.getSort())
                .betweenIfPresent(UserFieldExtendDO::getCreateTime, reqVO.getCreateTime())
                .orderByAsc( UserFieldExtendDO::getSort )
                .orderByDesc(UserFieldExtendDO::getId));
    }

}