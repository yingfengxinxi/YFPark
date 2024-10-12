package cn.sdqingyun.smartpark.module.bus.service.contract;

import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractSelectedPropertiePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractSelectedPropertieSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractSelectedPropertieDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;

import java.util.List;

/**
 * 合同中选中房源 Service 接口
 *
 * @author 智慧园区
 */
public interface ContractSelectedPropertieService {





    /**
     *
     * @param contractId
     */
    void updateByContractIdStatus(Long contractId);

    /**
     * 删除合同中选中房源
     *
     * @param id 编号
     */
    void deleteSelectedPropertie(Long id);

    /**
     *
     * @param contractId
     */
    void deleteByContractId(Long contractId);

    /**
     * 获得合同中选中房源
     *
     * @param id 编号
     * @return 合同中选中房源
     */
    ContractSelectedPropertieDO getSelectedPropertie(Long id);

    /**
     * 获得合同中选中房源分页
     *
     * @param pageReqVO 分页查询
     * @return 合同中选中房源分页
     */
    PageResult<ContractSelectedPropertieDO> getSelectedPropertiePage(ContractSelectedPropertiePageReqVO pageReqVO);

    /**
     *
     * @param roomId
     * @return
     */
    String getRoomIdArea(Long roomId);

    /**
     *
     * @param contractId
     * @return
     */
    List<ContractSelectedPropertieDO> getContractIdRoomInfoList(Long contractId);
}