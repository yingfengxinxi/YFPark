package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 耗材档案信息 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyStuffMapper extends BaseMapperX<PropertyStuffDO> {

    default PageResult<PropertyStuffDO> selectPage(PropertyStuffPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyStuffDO>()
                .eqIfPresent(PropertyStuffDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyStuffDO::getNumber, reqVO.getNumber())
                .likeIfPresent(PropertyStuffDO::getName, reqVO.getName())
                .eqIfPresent(PropertyStuffDO::getCategoryId, reqVO.getCategoryId())
                .eqIfPresent(PropertyStuffDO::getBarCode, reqVO.getBarCode())
                .eqIfPresent(PropertyStuffDO::getBrand, reqVO.getBrand())
                .likeIfPresent(PropertyStuffDO::getModelName, reqVO.getModelName())
                .eqIfPresent(PropertyStuffDO::getMeterUnit, reqVO.getMeterUnit())
                .eqIfPresent(PropertyStuffDO::getQuantityDigit, reqVO.getQuantityDigit())
                .eqIfPresent(PropertyStuffDO::getPriceDigit, reqVO.getPriceDigit())
                .eqIfPresent(PropertyStuffDO::getComputeMethod, reqVO.getComputeMethod())
                .eqIfPresent(PropertyStuffDO::getLockPrice, reqVO.getLockPrice())
                .eqIfPresent(PropertyStuffDO::getPrice, reqVO.getPrice())
                .eqIfPresent(PropertyStuffDO::getSafeStockUp, reqVO.getSafeStockUp())
                .eqIfPresent(PropertyStuffDO::getSafeStockLower, reqVO.getSafeStockLower())
                .eqIfPresent(PropertyStuffDO::getReceiveLimit, reqVO.getReceiveLimit())
                .eqIfPresent(PropertyStuffDO::getHasReturn, reqVO.getHasReturn())
                .eqIfPresent(PropertyStuffDO::getImageUrl, reqVO.getImageUrl())
                .eqIfPresent(PropertyStuffDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyStuffDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PropertyStuffDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyStuffDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(PropertyStuffDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyStuffDO::getId));
    }

    Integer getHightLow(String flag);

}