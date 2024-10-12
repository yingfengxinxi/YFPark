package cn.sdqingyun.smartpark.module.bus.dal.mysql.contract;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractSelectedPropertiePageReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractSelectedPropertieDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 合同中选中房源 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface ContractSelectedPropertieMapper extends BaseMapperX<ContractSelectedPropertieDO> {

    /**
     *
     * @param contractId
     */
    void updateByContractIdStatus(@Param("contractId") Long contractId);

    default PageResult<ContractSelectedPropertieDO> selectPage(ContractSelectedPropertiePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ContractSelectedPropertieDO>()
                .eqIfPresent(ContractSelectedPropertieDO::getContractId, reqVO.getContractId())
                .betweenIfPresent(ContractSelectedPropertieDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(ContractSelectedPropertieDO::getParkId, reqVO.getParkId())
                .eqIfPresent(ContractSelectedPropertieDO::getBuildId, reqVO.getBuildId())
                .eqIfPresent(ContractSelectedPropertieDO::getVillageRoomId, reqVO.getVillageRoomId())
                .eqIfPresent(ContractSelectedPropertieDO::getRentalArea, reqVO.getRentalArea())
                .orderByDesc(ContractSelectedPropertieDO::getId));
    }

    /**
     * @param villageRoomId
     * @return
     */
    String getRoomIdArea(@Param("villageRoomId") Long villageRoomId);

    /**
     *
     * @param contractId
     */
    void deleteByContractId(@Param("contractId") Long contractId);
}