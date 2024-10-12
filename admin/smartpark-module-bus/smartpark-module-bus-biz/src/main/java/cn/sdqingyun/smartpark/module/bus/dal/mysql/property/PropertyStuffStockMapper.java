package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffStockDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 耗材即时库存 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyStuffStockMapper extends BaseMapperX<PropertyStuffStockDO> {


    IPage<StockListPageVO>stockListPage(Page<?> page, @Param("param")StockListPageVO stockListPageVO);

    default PageResult<PropertyStuffStockDO> selectPage(PropertyStuffStockPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyStuffStockDO>()
                .eqIfPresent(PropertyStuffStockDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyStuffStockDO::getStuffId, reqVO.getStuffId())
                .eqIfPresent(PropertyStuffStockDO::getDepositoryId, reqVO.getDepositoryId())
                .eqIfPresent(PropertyStuffStockDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(PropertyStuffStockDO::getUsableNum, reqVO.getUsableNum())
                .eqIfPresent(PropertyStuffStockDO::getFrozenNum, reqVO.getFrozenNum())
                .eqIfPresent(PropertyStuffStockDO::getTotalNum, reqVO.getTotalNum())
                .eqIfPresent(PropertyStuffStockDO::getTotalPrice, reqVO.getTotalPrice())
                .eqIfPresent(PropertyStuffStockDO::getChargePrice, reqVO.getChargePrice())
                .eqIfPresent(PropertyStuffStockDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PropertyStuffStockDO::getExtra, reqVO.getExtra())
                .eqIfPresent(PropertyStuffStockDO::getIsStockUp, reqVO.getIsStockUp())
                .eqIfPresent(PropertyStuffStockDO::getIsStockLower, reqVO.getIsStockLower())
                .eqIfPresent(PropertyStuffStockDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyStuffStockDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(PropertyStuffStockDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyStuffStockDO::getId));
    }

    default PageResult<PropertyStuffStockDO> selectPageVO(PropertyStuffStockPageReqVO reqVO) {
        LambdaQueryWrapperX<PropertyStuffStockDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eqIfPresent(PropertyStuffStockDO::getStuffId, reqVO.getStuffId())
                .eqIfPresent(PropertyStuffStockDO::getDepositoryId, reqVO.getDepositoryId())
                .eqIfPresent(PropertyStuffStockDO::getProcessCode, reqVO.getProcessCode())
                .gt(reqVO.getUsableNumStart() != null,PropertyStuffStockDO::getUsableNum, reqVO.getUsableNumStart())
                .le(reqVO.getUsableNumEnd() != null,PropertyStuffStockDO::getUsableNum, reqVO.getUsableNumEnd())
                .gt(reqVO.getTotalNumStart() != null,PropertyStuffStockDO::getTotalNum, reqVO.getTotalNumStart())
                .le(reqVO.getTotalNumEnd() != null,PropertyStuffStockDO::getTotalNum, reqVO.getTotalNumEnd())
                .orderByDesc(PropertyStuffStockDO::getId);
        return selectPage(reqVO, wrapperX);
    }

}