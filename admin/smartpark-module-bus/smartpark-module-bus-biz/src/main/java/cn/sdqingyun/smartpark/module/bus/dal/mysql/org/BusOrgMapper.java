package cn.sdqingyun.smartpark.module.bus.dal.mysql.org;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.BusOrgDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.*;

/**
 * 机构 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface BusOrgMapper extends BaseMapperX<BusOrgDO> {

    default PageResult<BusOrgDO> selectPage(BusOrgPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BusOrgDO>()
                .likeIfPresent(BusOrgDO::getName, reqVO.getName())
                .eqIfPresent(BusOrgDO::getAdminUid, reqVO.getAdminUid())
                .eqIfPresent(BusOrgDO::getCompany, reqVO.getCompany())
                .eqIfPresent(BusOrgDO::getProvinceId, reqVO.getProvinceId())
                .eqIfPresent(BusOrgDO::getCityId, reqVO.getCityId())
                .likeIfPresent(BusOrgDO::getDistrictName, reqVO.getDistrictName())
                .eqIfPresent(BusOrgDO::getInfo, reqVO.getInfo())
                .eqIfPresent(BusOrgDO::getLogo, reqVO.getLogo())
                .eqIfPresent(BusOrgDO::getSquareLogo, reqVO.getSquareLogo())
                .eqIfPresent(BusOrgDO::getTotal, reqVO.getTotal())
                .eqIfPresent(BusOrgDO::getTel, reqVO.getTel())
                .eqIfPresent(BusOrgDO::getAddress, reqVO.getAddress())
                .eqIfPresent(BusOrgDO::getType, reqVO.getType())
                .eqIfPresent(BusOrgDO::getDomainPrefix, reqVO.getDomainPrefix())
                .eqIfPresent(BusOrgDO::getIsOem, reqVO.getIsOem())
                .eqIfPresent(BusOrgDO::getWechatNumber, reqVO.getWechatNumber())
                .eqIfPresent(BusOrgDO::getOrgPrefix, reqVO.getOrgPrefix())
                .eqIfPresent(BusOrgDO::getJoinCode, reqVO.getJoinCode())
                .eqIfPresent(BusOrgDO::getStatus, reqVO.getStatus())
                .eqIfPresent(BusOrgDO::getDictionary, reqVO.getDictionary())
                .eqIfPresent(BusOrgDO::getModule, reqVO.getModule())
                .eqIfPresent(BusOrgDO::getVillageType, reqVO.getVillageType())
                .eqIfPresent(BusOrgDO::getOverdueDay, reqVO.getOverdueDay())
                .eqIfPresent(BusOrgDO::getClueTel, reqVO.getClueTel())
                .betweenIfPresent(BusOrgDO::getClueTime, reqVO.getClueTime())
                .eqIfPresent(BusOrgDO::getRegFrom, reqVO.getRegFrom())
                .eqIfPresent(BusOrgDO::getMaxUseArea, reqVO.getMaxUseArea())
                .eqIfPresent(BusOrgDO::getMaxUseRoom, reqVO.getMaxUseRoom())
                .betweenIfPresent(BusOrgDO::getLastLoginTime, reqVO.getLastLoginTime())
                .betweenIfPresent(BusOrgDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(BusOrgDO::getId));
    }

}