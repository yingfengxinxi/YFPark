package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.util.MyBatisUtils;
import cn.sdqingyun.smartpark.framework.security.core.util.SecurityFrameworkUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.*;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.*;
import cn.sdqingyun.smartpark.module.bus.enums.ProptryAuditStatusEnum;
import cn.sdqingyun.smartpark.module.bus.enums.ProptryCodeEnum;
import cn.sdqingyun.smartpark.module.bus.framework.utils.coverUtils.CodeUtil;
import com.alibaba.nacos.common.utils.UuidUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PROPERTY_STUFF_STOCK_NOT_EXISTS;

/**
 * 耗材即时库存 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
@Slf4j
public class PropertyStuffStockServiceImpl implements PropertyStuffStockService {

    @Resource
    private PropertyStuffStockMapper propertyStuffStockMapper;
    private static final String PROCESS_DEFINITION_KEY = "proress_stuff";

    @Resource
    private PropertyStuffProcessMapper propertyStuffProcessMapper;
    @Resource
    private PropertyStuffEnterMapper propertyStuffEnterMapper;
    @Resource
    private PropertyStuffHandoutMapper propertyStuffHandoutMapper;
    @Resource
    private PropertyStuffTransferMapper propertyStuffTransferMapper;
    @Resource
    private PropertyStuffHandleMapper propertyStuffHandleMapper;
    @Resource
    private PropertyStuffAdjustMapper propertyStuffAdjustMapper;
    @Resource
    private PropertyStuffDepositoryMapper propertyStuffDepositoryMapper;
    @Resource
    private PropertyStuffMapper propertyStuffMapper;
    @Resource
    private PropertyStuffRetreatMapper propertyStuffRetreatMapper;
    @Resource
    private PropertyStuffCategoryMapper propertyStuffCategoryMapper;
    @Override
    public Long createPropertyStuffStock(PropertyStuffStockSaveReqVO createReqVO) {
        // 插入
        PropertyStuffStockDO propertyStuffStock = BeanUtils.toBean(createReqVO, PropertyStuffStockDO.class);
        propertyStuffStockMapper.insert(propertyStuffStock);
        // 返回
        return propertyStuffStock.getId();
    }

    @Override
    public void updatePropertyStuffStock(PropertyStuffStockSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyStuffStockExists(updateReqVO.getId());
        // 更新
        PropertyStuffStockDO updateObj = BeanUtils.toBean(updateReqVO, PropertyStuffStockDO.class);
        propertyStuffStockMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyStuffStock(Long id) {
        // 校验存在
        validatePropertyStuffStockExists(id);
        // 删除
        propertyStuffStockMapper.deleteById(id);
    }

    private void validatePropertyStuffStockExists(Long id) {
        if (propertyStuffStockMapper.selectById(id) == null) {
            throw exception(PROPERTY_STUFF_STOCK_NOT_EXISTS);
        }
    }

    @Override
    public PropertyStuffStockDO getPropertyStuffStock(Long id) {
        return propertyStuffStockMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyStuffStockDO> getPropertyStuffStockPage(PropertyStuffStockPageReqVO pageReqVO) {
        return propertyStuffStockMapper.selectPage(pageReqVO);
    }

    @Override
    public Long savePropertyStuff(List<PropertyStuffProcessSaveReqVO> list) {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        if (CollUtil.isEmpty(list)) {
            throw new ServiceException(406, "未获取相关耗材数据，请检查后重新保存");
        }

        final String number;
        final String processInstanceId;
        String processType = list.get(0).getProcessType();

        // 根据 processType 设置通用信息
        switch (processType) {
            //耗材入库
            case "stuff_stock_enter":
                number = generateUniqueCode(ProptryCodeEnum.HCRK);
                PropertyStuffEnterDO enterDO = createPropertyStuffEnterDO(list.get(0), loginUserId, number);
                propertyStuffEnterMapper.insert(enterDO);
                processInstanceId = createProcessInstanceAndSetCode(loginUserId, enterDO.getId(), enterDO::setProcessCode);
                propertyStuffEnterMapper.updateById(enterDO);
                break;

            //耗材派发/耗材领用/库存耗材领用
            case "stuff_hand_out":
            case "stuff_receive":
            case "stuff_stock_receive":
                number = generateUniqueCode(ProptryCodeEnum.HCPF);
                PropertyStuffHandoutDO handoutDO = createPropertyStuffHandoutDO(list.get(0), loginUserId, number);
                propertyStuffHandoutMapper.insert(handoutDO);
                processInstanceId = createProcessInstanceAndSetCode(loginUserId, handoutDO.getId(), handoutDO::setProcessCode);
                propertyStuffHandoutMapper.updateById(handoutDO);
                break;

                //耗材退还/耗材退还
            case "stuff_retreat_out":
            case "stuff_return":
                number = generateUniqueCode(ProptryCodeEnum.HCTK);
                PropertyStuffRetreatDO propertyStuffReturnDO = createPropertyStuffRetreatDO( list.get( 0 ), loginUserId, number );
                propertyStuffRetreatMapper.insert(propertyStuffReturnDO);
                processInstanceId = createProcessInstanceAndSetCode(loginUserId, propertyStuffReturnDO.getId(), propertyStuffReturnDO::setProcessCode);
                propertyStuffRetreatMapper.updateById(propertyStuffReturnDO);
                break;

                //耗材调拨
            case "stuff_transfer":
                number = generateUniqueCode(ProptryCodeEnum.HCDB);
                PropertyStuffTransferDO transferDO = createPropertyStuffTransferDO(list.get(0), number);
                propertyStuffTransferMapper.insert(transferDO);
                processInstanceId = createProcessInstanceAndSetCode(loginUserId, transferDO.getId(), transferDO::setProcessCode);
                propertyStuffTransferMapper.updateById(transferDO);
                break;

                //耗材处置
            case "stuff_handle":
                number = generateUniqueCode(ProptryCodeEnum.HCCZ);
                PropertyStuffHandleDO handleDO = createPropertyStuffHandleDO(list.get(0), number);
                propertyStuffHandleMapper.insert(handleDO);
                processInstanceId = createProcessInstanceAndSetCode(loginUserId, handleDO.getId(), handleDO::setProcessCode);
                propertyStuffHandleMapper.updateById(handleDO);
                break;

                //耗材调整
            case "stuff_adjust":
                number = generateUniqueCode(ProptryCodeEnum.HCTZ);
                PropertyStuffAdjustDO adjustDO = createPropertyStuffAdjustDO(list.get(0), number);
                propertyStuffAdjustMapper.insert(adjustDO);
                processInstanceId = createProcessInstanceAndSetCode(loginUserId, adjustDO.getId(), adjustDO::setProcessCode);
                propertyStuffAdjustMapper.updateById(adjustDO);
                break;

            default:
                throw new ServiceException(406, "未知的处理类型: " + processType);
        }

        // 保存耗材处理流程记录

        List<PropertyStuffProcessDO> propertyStuffProcessDOS = list.stream()
                .map(item -> {
                    PropertyStuffProcessDO processDO = BeanUtils.toBean(item, PropertyStuffProcessDO.class);
                    processDO.setProcessNumber(number);
                    processDO.setProcessCode(processInstanceId);
                    return processDO;
                })
                .collect(Collectors.toList());
        propertyStuffProcessMapper.insertBatch(propertyStuffProcessDOS);

        return propertyStuffProcessDOS.get(0).getId();
    }

    private String generateUniqueCode(ProptryCodeEnum codeEnum) {
        return codeEnum.getCode() + CodeUtil.generateUniquecode();
    }

    private String createProcessInstanceAndSetCode(Long loginUserId, Long businessId, Consumer<String> setCodeFunc) {
        String processInstanceId = UuidUtils.generateUuid();
        setCodeFunc.accept(processInstanceId);
        return processInstanceId;
    }

    private PropertyStuffEnterDO createPropertyStuffEnterDO(PropertyStuffProcessSaveReqVO reqVO, Long loginUserId, String number) {
        PropertyStuffEnterDO enterDO = new PropertyStuffEnterDO();
        enterDO.setNumber(number);
        enterDO.setDepositoryId(reqVO.getDepositoryId());
        enterDO.setEnterUid(reqVO.getEnterUid());
        enterDO.setEnterTime(reqVO.getEnterTime() == null ? LocalDateTime.now() : reqVO.getEnterTime());
        enterDO.setSupplier(reqVO.getSupplier());
        enterDO.setTotalPrice(reqVO.getTotalPrice());
        enterDO.setStatus(ProptryAuditStatusEnum.STATUS_1.getCode());
        enterDO.setRemark(reqVO.getExtra());
        enterDO.setCuserUid( reqVO.getCuserUid() );
        enterDO.setMuserUid( reqVO.getMuserUid()  == null ? loginUserId : reqVO.getMuserUid() );
        return enterDO;
    }

    private PropertyStuffHandoutDO createPropertyStuffHandoutDO(PropertyStuffProcessSaveReqVO reqVO, Long loginUserId, String number) {
        PropertyStuffHandoutDO handoutDO = new PropertyStuffHandoutDO();
        handoutDO.setProcessNumber(number);
        handoutDO.setReceiveUid(reqVO.getReceiveUid());
        handoutDO.setDepartmentId(reqVO.getDepartmentId());
        handoutDO.setDepartmentName(reqVO.getDepartmentName());
        handoutDO.setDepositoryId(reqVO.getDepositoryId());
        handoutDO.setHandoutTime(reqVO.getHandoutTime() == null ? LocalDateTime.now() : reqVO.getHandoutTime());
        handoutDO.setStatus(ProptryAuditStatusEnum.STATUS_1.getCode());
        handoutDO.setRemark(reqVO.getExtra());
        handoutDO.setCuserUid( reqVO.getCuserUid() );
        handoutDO.setMuserUid( reqVO.getMuserUid() == null ? loginUserId : reqVO.getMuserUid());
        return handoutDO;
    }

    private PropertyStuffRetreatDO createPropertyStuffRetreatDO(PropertyStuffProcessSaveReqVO reqVO, Long loginUserId, String number) {
        PropertyStuffRetreatDO handoutDO = new PropertyStuffRetreatDO();
        handoutDO.setProcessNumber(number);
        handoutDO.setRetreatUid( reqVO.getRetreatUid() );
        handoutDO.setDepartmentId( reqVO.getDepartmentId() );
        handoutDO.setDepartmentName( reqVO.getDepartmentName() );
        handoutDO.setDepositoryId( reqVO.getDepositoryId() );
        handoutDO.setRetreatDate( reqVO.getRetreatDate() );
        handoutDO.setStatus(ProptryAuditStatusEnum.STATUS_1.getCode());
        handoutDO.setRemark(reqVO.getExtra());
        handoutDO.setCuserUid( reqVO.getCuserUid() );
        handoutDO.setMuserUid(  reqVO.getMuserUid() == null ? loginUserId : reqVO.getMuserUid() );
        return handoutDO;
    }

    private PropertyStuffTransferDO createPropertyStuffTransferDO(PropertyStuffProcessSaveReqVO reqVO, String number) {
        PropertyStuffTransferDO transferDO = new PropertyStuffTransferDO();
        transferDO.setProcessNumber(number);
        transferDO.setOutAdminUid(reqVO.getOutAdminUid());
        transferDO.setInAdminUid(reqVO.getInAdminUid());
        transferDO.setOutDepositoryId(reqVO.getOutDepositoryId());
        transferDO.setInDepositoryId(reqVO.getInDepositoryId());
        transferDO.setTotalNum(reqVO.getTotalNumAj());
        transferDO.setTotalPrice(reqVO.getTotalPriceNum());
        transferDO.setStatus(ProptryAuditStatusEnum.STATUS_1.getCode());
        transferDO.setRemark(reqVO.getExtra());
        transferDO.setCuserUid( reqVO.getCuserUid() );
        transferDO.setMuserUid( reqVO.getMuserUid() == null ? 0 : reqVO.getMuserUid() );
        return transferDO;
    }

    private PropertyStuffHandleDO createPropertyStuffHandleDO(PropertyStuffProcessSaveReqVO reqVO, String number) {
        PropertyStuffHandleDO handleDO = new PropertyStuffHandleDO();
        handleDO.setProcessNumber(number);
        handleDO.setDepartmentId(reqVO.getDepartmentId());
        handleDO.setDepartmentName(reqVO.getDepartmentName());
        handleDO.setDepositoryId(reqVO.getDepositoryId());
        handleDO.setLaunchTime(reqVO.getLaunchTime() == null ? LocalDateTime.now():reqVO.getLaunchTime());
        handleDO.setTotalPrice(reqVO.getTotalPriceNum());
        handleDO.setStatus(ProptryAuditStatusEnum.STATUS_1.getCode());
        handleDO.setRemark(reqVO.getExtra());
        handleDO.setCuserUid( reqVO.getCuserUid() );
        handleDO.setMuserUid( reqVO.getMuserUid() == null ? 0 : reqVO.getMuserUid() );
        return handleDO;
    }

    private PropertyStuffAdjustDO createPropertyStuffAdjustDO(PropertyStuffProcessSaveReqVO reqVO, String number) {
        PropertyStuffAdjustDO adjustDO = new PropertyStuffAdjustDO();
        adjustDO.setProcessNumber(number);
        adjustDO.setDepositoryId(reqVO.getDepositoryId());
        adjustDO.setAdjustTime(reqVO.getAdjustTime() == null?LocalDateTime.now():reqVO.getAdjustTime());
        adjustDO.setStatus(ProptryAuditStatusEnum.STATUS_1.getCode());
        adjustDO.setRemark(reqVO.getExtra());
        adjustDO.setCuserUid( reqVO.getCuserUid() );
        adjustDO.setMuserUid( reqVO.getMuserUid() == null ? 0 : reqVO.getMuserUid() );
        return adjustDO;
    }

    @Override
    public void updatePropertyStuffStatus(String id, Integer status) {
        log.info("updatePropertyStuffStatus------>id={},status={}", id, status);

        // 查询审批关联表
        LambdaQueryWrapper<PropertyStuffProcessDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PropertyStuffProcessDO::getProcessCode, id);
        List<PropertyStuffProcessDO> processDOS = propertyStuffProcessMapper.selectList(wrapper);

        if (CollUtil.isEmpty(processDOS)) {
            return;
        }

        String processType = processDOS.get(0).getProcessType();

        // 统一设置审批状态
        processDOS.forEach(processDO -> processDO.setStatus(status));
        propertyStuffProcessMapper.updateBatch(processDOS);

        // 处理不同类型的流程
        switch (processType) {
            //耗材入库
            case "stuff_stock_enter":
                handleStuffStockEnter(processDOS, status);
                break;

            //耗材派发/耗材领用/库存耗材领用
            case "stuff_hand_out":
            case "stuff_receive":
            case "stuff_stock_receive":
                handleStockReduction(processDOS, status);
                updatePropertyStuffHandoutStatus(processDOS.get(0).getProcessCode(), status);
                break;

            //耗材退还/耗材退还
            case "stuff_retreat_out":
            case "stuff_return":
                handleStockIncrease(processDOS, status);
                updatePropertyStuffReturnStatus(processDOS.get(0).getProcessCode(), status);
                break;

            //耗材调拨
            case "stuff_transfer":
                handleStuffTransfer(processDOS, status);
                updatePropertyStuffTransferStatus(processDOS.get(0).getProcessCode(), status);
                break;

            //耗材处置
            case "stuff_handle":
                handleStockReduction(processDOS, status);
                updatePropertyStuffHandleStatus(processDOS.get(0).getProcessCode(), status);
                break;

            //耗材调整
            case "stuff_adjust":
                adjustStockReduction(processDOS, status);
                updatePropertyStuffAdjustStatus(processDOS.get(0).getProcessCode(), status);
                break;

            default:
                throw new ServiceException(406, "未知的处理类型: " + processType);
        }
    }

    private void  handleStuffStockEnter(List<PropertyStuffProcessDO> processDOS, Integer status) {
        if (status.equals(ProptryAuditStatusEnum.STATUS_2.getCode())) {
            for (PropertyStuffProcessDO property : processDOS) {
                PropertyStuffStockDO stockDO = new PropertyStuffStockDO();
                stockDO.setOrgId( property.getOrgId() );
                stockDO.setStuffId( property.getStuffId() );
                stockDO.setDepositoryId( property.getDepositoryId() );
                stockDO.setProcessCode( property.getProcessCode() );
                stockDO.setFrozenNum( BigDecimal.ZERO );
                stockDO.setUsableNum( property.getNum() );
                stockDO.setTotalNum( property.getNum() );
                stockDO.setTotalPrice( property.getTotalPrice() );
                stockDO.setChargePrice( property.getPrice() );
                stockDO.setRemark( property.getExtra() );
                stockDO.setExtra( property.getExtra() );
                stockDO.setIsStockUp( 0 );
                stockDO.setIsStockLower( 0 );
                stockDO.setCuserUid( property.getCuserUid() );
                stockDO.setMuserUid( property.getMuserUid() );
                propertyStuffStockMapper.insert( stockDO );
            }
        }
        updatePropertyStuffEnterStatus(processDOS.get(0).getProcessCode(), status);
    }

    private void handleStockReduction(List<PropertyStuffProcessDO> processDOS, Integer status) {
        if (status.equals(ProptryAuditStatusEnum.STATUS_2.getCode())) {
            for (PropertyStuffProcessDO processDO : processDOS) {
                //获取即时库存相关信息
                PropertyStuffStockDO stockDO = propertyStuffStockMapper.selectById(processDO.getCuserUid());
                if (stockDO != null) {
                    stockDO.setUsableNum(stockDO.getUsableNum().subtract(processDO.getNum()));
                    stockDO.setTotalNum( stockDO.getTotalNum().subtract(processDO.getNum()) );
                    stockDO.setTotalPrice(stockDO.getTotalPrice().subtract(processDO.getTotalPrice()));
                    propertyStuffStockMapper.updateById(stockDO);
                }
            }
        }
    }

    private void adjustStockReduction(List<PropertyStuffProcessDO> processDOS, Integer status) {
        if (status.equals(ProptryAuditStatusEnum.STATUS_2.getCode())) {
            for (PropertyStuffProcessDO processDO : processDOS) {
                //获取即时库存相关信息
                PropertyStuffStockDO stockDO = propertyStuffStockMapper.selectById(processDO.getCuserUid());
                if (stockDO != null) {
                    stockDO.setChargePrice( processDO.getPrice() );
                    stockDO.setUsableNum(processDO.getNum());
                    stockDO.setTotalNum(processDO.getNum());
                    stockDO.setTotalPrice(processDO.getTotalPrice());
                    propertyStuffStockMapper.updateById(stockDO);
                }
            }
        }
    }

    private void handleStockIncrease(List<PropertyStuffProcessDO> processDOS, Integer status) {
        if (status.equals(ProptryAuditStatusEnum.STATUS_2.getCode())) {
            for (PropertyStuffProcessDO processDO : processDOS) {
                //查询即时库存记录然后将相关库存信息加上
                PropertyStuffStockDO stockDO = propertyStuffStockMapper.selectById(processDO.getCuserUid());
                if (stockDO != null) {
                    stockDO.setUsableNum(stockDO.getUsableNum().add(processDO.getRetreatNum()));
                    stockDO.setTotalNum(stockDO.getTotalNum().add(processDO.getRetreatNum()));
                    stockDO.setTotalPrice(stockDO.getTotalPrice().add(processDO.getTotalPrice()));
                    propertyStuffStockMapper.updateById(stockDO);
                }
            }
        }
    }

    private void handleStuffTransfer(List<PropertyStuffProcessDO> processDOS, Integer status) {
        if (status.equals(ProptryAuditStatusEnum.STATUS_2.getCode())) {
            for (PropertyStuffProcessDO processDO : processDOS) {
                // 处理调出仓库
                PropertyStuffStockDO stockDO = propertyStuffStockMapper.selectById(processDO.getCuserUid());
                if (stockDO != null) {
                    stockDO.setUsableNum(stockDO.getUsableNum().subtract(processDO.getNum()));
                    stockDO.setTotalNum( stockDO.getTotalNum().subtract(processDO.getNum()) );
                    stockDO.setTotalPrice(stockDO.getTotalPrice().subtract(processDO.getTotalPrice()));
                    propertyStuffStockMapper.updateById(stockDO);
                }

                // 处理调入仓库
                PropertyStuffStockDO stockDOIn = propertyStuffStockMapper.selectOne(
                        new LambdaQueryWrapper<PropertyStuffStockDO>()
                                .eq(PropertyStuffStockDO::getDepositoryId, processDO.getInDepositoryId())
                                .eq(PropertyStuffStockDO::getStuffId, processDO.getStuffId())
                );
                if (stockDOIn != null) {
                    stockDOIn.setUsableNum(stockDOIn.getUsableNum().add(processDO.getNum()));
                    stockDO.setTotalNum( stockDO.getTotalNum().add(processDO.getNum()) );
                    stockDOIn.setTotalPrice(stockDOIn.getTotalPrice().add(processDO.getTotalPrice()));
                    propertyStuffStockMapper.updateById(stockDOIn);
                } else {
                    PropertyStuffStockDO newStockDO = BeanUtils.toBean(processDO, PropertyStuffStockDO.class);
                    newStockDO.setId(null);
                    newStockDO.setDepositoryId( processDO.getInDepositoryId() );
                    newStockDO.setProcessCode( processDO.getProcessCode() );
                    newStockDO.setUsableNum( processDO.getNum() );
                    newStockDO.setFrozenNum( BigDecimal.ZERO );
                    newStockDO.setTotalNum( processDO.getNum() );
                    newStockDO.setTotalPrice( processDO.getTotalPrice() );
                    propertyStuffStockMapper.insert(newStockDO);
                }
            }
        }
    }

    // 更新状态的辅助方法
    private void updatePropertyStuffEnterStatus(String processCode, Integer status) {
        LambdaUpdateWrapper<PropertyStuffEnterDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(PropertyStuffEnterDO::getProcessCode, processCode)
                .set(PropertyStuffEnterDO::getStatus, status);
        propertyStuffEnterMapper.update(updateWrapper);
    }

    private void updatePropertyStuffHandoutStatus(String processCode, Integer status) {
        LambdaUpdateWrapper<PropertyStuffHandoutDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(PropertyStuffHandoutDO::getProcessCode, processCode)
                .set(PropertyStuffHandoutDO::getStatus, status);
        propertyStuffHandoutMapper.update(updateWrapper);
    }

    private void updatePropertyStuffReturnStatus(String processCode, Integer status) {
        LambdaUpdateWrapper<PropertyStuffRetreatDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(PropertyStuffRetreatDO::getProcessCode, processCode)
                .set(PropertyStuffRetreatDO::getStatus, status);
        propertyStuffRetreatMapper.update(updateWrapper);
    }

    private void updatePropertyStuffTransferStatus(String processCode, Integer status) {
        LambdaUpdateWrapper<PropertyStuffTransferDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(PropertyStuffTransferDO::getProcessCode, processCode)
                .set(PropertyStuffTransferDO::getStatus, status);
        propertyStuffTransferMapper.update(updateWrapper);
    }

    private void updatePropertyStuffHandleStatus(String processCode, Integer status) {
        LambdaUpdateWrapper<PropertyStuffHandleDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(PropertyStuffHandleDO::getProcessCode, processCode)
                .set(PropertyStuffHandleDO::getStatus, status);
        propertyStuffHandleMapper.update(updateWrapper);
    }

    private void updatePropertyStuffAdjustStatus(String processCode, Integer status) {
        LambdaUpdateWrapper<PropertyStuffAdjustDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(PropertyStuffAdjustDO::getProcessCode, processCode)
                .set(PropertyStuffAdjustDO::getStatus, status);
        propertyStuffAdjustMapper.update(updateWrapper);
    }

    @Override
    public PageResult<PropertyStuffStockRespVO> getPropertyStuffStockPageVO(PropertyStuffStockPageReqVO pageReqVO) {
        PageResult<PropertyStuffStockDO> pageResult = propertyStuffStockMapper.selectPageVO(pageReqVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return PageResult.empty();
        }
        PageResult<PropertyStuffStockRespVO> result = BeanUtils.toBean(pageResult, PropertyStuffStockRespVO.class);
        for (PropertyStuffStockRespVO vo : result.getList()) {
            PropertyStuffDO propertyStuffDO = propertyStuffMapper.selectById( vo.getStuffId() );
            if(propertyStuffDO != null){
                vo.setStuff(BeanUtils.toBean(propertyStuffDO, PropertyStuffRespVO.class));
                vo.setCategoryName(propertyStuffCategoryMapper.selectById(propertyStuffDO.getCategoryId()).getName());
            }

            vo.setDepository(BeanUtils.toBean(propertyStuffDepositoryMapper.selectById(vo.getDepositoryId()), PropertyDepositoryRespVO.class));
        }

        return result;
    }

    /**
     * @param pageReqVO
     * @return
     */
    @Override
    public PageResult<StockListPageVO> stockListPage(StockListPageVO pageReqVO) {
        IPage<StockListPageVO> stockListPageVOIPage = propertyStuffStockMapper.stockListPage(MyBatisUtils.buildPage(pageReqVO), pageReqVO);
        return new PageResult<>(stockListPageVOIPage.getRecords(), stockListPageVOIPage.getTotal());
    }

    @Override
    public PageResult<PropertyStuffStockRespVO> getPropertyStuffPageByApprove(PropertyStuffProcessPageReqVO pageReqVO) {
        //根据审批单编号查询审批记录
        LambdaQueryWrapper<PropertyStuffProcessDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PropertyStuffProcessDO::getProcessCode, pageReqVO.getProcessCode())
                .eq(pageReqVO.getProcessType() != null,PropertyStuffProcessDO::getProcessType, pageReqVO.getProcessType());
        List<PropertyStuffProcessDO> propertyApproves = propertyStuffProcessMapper.selectList(wrapper);
        if(CollUtil.isEmpty( propertyApproves )){
            return PageResult.empty();
        }

        List<Long> relationNumbers = propertyApproves.stream()
                .map(PropertyStuffProcessDO::getCuserUid)
                .collect( Collectors.toList());

        PropertyApprovePageReqVO reqVO = BeanUtils.toBean( pageReqVO, PropertyApprovePageReqVO.class );
        return  BeanUtils.toBean( propertyStuffStockMapper.selectPage(reqVO, new LambdaQueryWrapperX<PropertyStuffStockDO>()
                .in(PropertyStuffStockDO::getId, relationNumbers)
                .orderByDesc(PropertyStuffStockDO::getId)), PropertyStuffStockRespVO.class );
    }
}