package cn.sdqingyun.smartpark.module.bus.dal.mysql.contract;

import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.OwnerNameContractVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * 机构合同 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface ContractMapper extends BaseMapperX<ContractDO> {

    /**
     *
     * @param id
     */
    void deleteByIdContract(Long id);

    /**
     * @param page
     * @param contractPageReqVO
     * @return
     */
    IPage<ContractDO> getListPage(Page<?> page, @Param("param") ContractPageReqVO contractPageReqVO);

    /**
     *
     * @param ownerName
     * @return
     */
    List<OwnerNameContractVO> getOwnerNameContractList(@Param("ownerName") String ownerName);
}