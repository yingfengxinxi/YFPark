package cn.sdqingyun.smartpark.module.bus.dal.mysql.contract;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.AnnexPageReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.AnnexDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 机构合同附件 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface ContractAnnexMapper extends BaseMapperX<AnnexDO> {

    default PageResult<AnnexDO> selectPage(AnnexPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AnnexDO>()
                .eq(AnnexDO::getContractId, reqVO.getContractId())
                .orderByDesc(AnnexDO::getCreateTime));
    }

    /**
     *
     * @param contractId
     */
    void deleteByContractId(@Param("contractId") Long contractId);
}