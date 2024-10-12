package cn.sdqingyun.smartpark.module.bus.service.contract;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractDO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.itextpdf.text.DocumentException;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.List;

/**
 * 机构合同 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface ContractService {


    /**
     *
     * @param contractSaveVO
     * @return
     */
    public String contractPdf(ContractSaveVO contractSaveVO) throws IOException, DocumentException;


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


    /**
     *
     * @param contractDO
     */
    void updateById(@Valid ContractDO contractDO);

    /**
     * 删除机构合同
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     *
     * @param id
     */
    void deleteByIdContract(Long id);

    /**
     * 获得机构合同
     *
     * @param id 编号
     * @return 机构合同
     */
    ContractSaveVO get(Long id) throws JsonProcessingException;

    /**
     *
     * @param id
     * @return
     */
    ContractDO selectById(Long id);

    /**
     *
     * @param buildId
     * @return
     */
    List<ContractDO> getByBuildIdContractList(Long buildId);

    List<ContractDO> getExpireContractList();

    /**
     * 获得机构合同分页
     *
     * @param pageReqVO 分页查询
     * @return 机构合同分页
     */
    PageResult<ContractDO> getPage(ContractPageReqVO pageReqVO);

    /**
     *
     * @param list
     */
    void importContractList(List<ContractImportVO> list);
    
    /**
    * @Author SUNk
    * @Description 租赁面积排行
    * @Date 17:44 2024/7/10
    * @Param [contractTopVO]
    * @return java.util.List<cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractTopVO>
    **/
    List<ContractTopVO> getContractTop5(ContractTopVO contractTopVO);
    
    /**
    * @Author SUNk
    * @Description 租金单价排行
    * @Date 18:34 2024/7/10
    * @Param [contractTopVO]
    * @return java.util.List<cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractTopVO>
    **/
    List<ContractTopVO> getContractTop5Price(ContractTopVO contractTopVO);

    /**
     *
     * @param contractId
     * @return
     */
    Boolean rentTermination(Long contractId);

    /**
     *
     * @param contractId
     * @return
     */
    void isCheckLateFee(Long contractId);

    /**
     *
     * @param contractId
     */
    Integer isCheckUpdateRoom(Long contractId);

    /**
     *
     * @param contractToVoidVO
     */
    void toVoid(ContractToVoidVO contractToVoidVO);

    /**
     *
     * @return
     */
    public List<ContractDO> getPendingExecution();

    /**
     *
     * @param id
     * @return
     */
    ContractDO getByRenewalContractIdInfo(Long id);

    /**
     *
     * @param contractNumber
     * @param id
     * @return
     */
    Boolean isCheckContractNumber(String contractNumber, Long id);

    /**
     *
     * @param ownerName
     * @return
     */
    List<OwnerNameContractVO> getOwnerNameContractList(String ownerName);
}