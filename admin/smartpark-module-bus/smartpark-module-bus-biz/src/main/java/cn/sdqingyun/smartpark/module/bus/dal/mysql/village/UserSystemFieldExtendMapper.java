package cn.sdqingyun.smartpark.module.bus.dal.mysql.village;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.UserSystemFieldExtendDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;

/**
 * 用户扩展信息自定义系统设置 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface UserSystemFieldExtendMapper extends BaseMapperX<UserSystemFieldExtendDO> {

    default PageResult<UserSystemFieldExtendDO> selectPage(UserSystemFieldExtendPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<UserSystemFieldExtendDO>()
                .likeIfPresent(UserSystemFieldExtendDO::getFieldName, reqVO.getFieldName())
                .likeIfPresent(UserSystemFieldExtendDO::getInputName, reqVO.getInputName())
                .eqIfPresent(UserSystemFieldExtendDO::getInputNameCus, reqVO.getInputNameCus())
                .eqIfPresent(UserSystemFieldExtendDO::getInputType, reqVO.getInputType())
                .eqIfPresent(UserSystemFieldExtendDO::getDefaultInputValue, reqVO.getDefaultInputValue())
                .eqIfPresent(UserSystemFieldExtendDO::getDefaultFieldValue, reqVO.getDefaultFieldValue())
                .eqIfPresent(UserSystemFieldExtendDO::getIsEnable, reqVO.getIsEnable())
                .eqIfPresent(UserSystemFieldExtendDO::getIsRequired, reqVO.getIsRequired())
                .eqIfPresent(UserSystemFieldExtendDO::getIsAllowModifie, reqVO.getIsAllowModifie())
                .eqIfPresent(UserSystemFieldExtendDO::getJsonFields, reqVO.getJsonFields())
                .eqIfPresent(UserSystemFieldExtendDO::getSort, reqVO.getSort())
                .betweenIfPresent(UserSystemFieldExtendDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(UserSystemFieldExtendDO::getId));
    }

}