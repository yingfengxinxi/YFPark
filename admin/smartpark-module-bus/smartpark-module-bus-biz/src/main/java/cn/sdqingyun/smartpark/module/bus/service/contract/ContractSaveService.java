package cn.sdqingyun.smartpark.module.bus.service.contract;

import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractSaveVO;
import jakarta.validation.Valid;

/**
 * @Author lvzy
 * @Date 2024/8/12 15:19
 */
public interface ContractSaveService {

    /**
     * 创建机构合同
     *
     * @param contractSaveVO 创建信息
     * @return 编号
     */
    Long create(@Valid ContractSaveVO contractSaveVO);

    /**
     * 更新机构合同
     *
     * @param contractSaveVO 更新信息
     */
    void update(@Valid ContractSaveVO contractSaveVO);
}
