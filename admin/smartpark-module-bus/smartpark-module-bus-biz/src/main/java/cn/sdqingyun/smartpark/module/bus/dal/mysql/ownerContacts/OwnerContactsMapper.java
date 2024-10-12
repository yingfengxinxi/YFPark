package cn.sdqingyun.smartpark.module.bus.dal.mysql.ownerContacts;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.ownerContacts.vo.OwnerContactsPageReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.ownerContacts.OwnerContactsDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 租客联系人 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface OwnerContactsMapper extends BaseMapperX<OwnerContactsDO> {

    default PageResult<OwnerContactsDO> selectPage(OwnerContactsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OwnerContactsDO>()
                .eqIfPresent(OwnerContactsDO::getOrgId, reqVO.getOrgId())
                .likeIfPresent(OwnerContactsDO::getName, reqVO.getName())
                .eqIfPresent(OwnerContactsDO::getPhone, reqVO.getPhone())
                .eqIfPresent(OwnerContactsDO::getEmail, reqVO.getEmail())
                .eqIfPresent(OwnerContactsDO::getOwnerId, reqVO.getOwnerId())
                .eqIfPresent(OwnerContactsDO::getAddress, reqVO.getAddress())
                .eqIfPresent(OwnerContactsDO::getIsDefault, reqVO.getIsDefault())
                .betweenIfPresent(OwnerContactsDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OwnerContactsDO::getId));
    }

}