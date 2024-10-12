package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.common.util.qrcode.QRCodeUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.security.core.LoginUser;
import cn.sdqingyun.smartpark.framework.security.core.util.SecurityFrameworkUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.*;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.*;
import cn.sdqingyun.smartpark.module.bus.enums.OperateTypeEnum;
import cn.sdqingyun.smartpark.module.bus.enums.ProptryAuditStatusEnum;
import cn.sdqingyun.smartpark.module.bus.enums.ProptryCodeEnum;
import cn.sdqingyun.smartpark.module.bus.enums.ProptryStatusEnum;
import cn.sdqingyun.smartpark.module.bus.framework.utils.coverUtils.CodeGetName;
import cn.sdqingyun.smartpark.module.bus.framework.utils.coverUtils.CodeUtil;
import cn.sdqingyun.smartpark.module.infra.api.file.FileApi;
import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.common.utils.UuidUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PROPERTY_HANDOUT_NOT_EXISTS;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PROPERTY_NOT_EXISTS;

/**
 * 资产管理 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
@Slf4j
public class PropertyServiceImpl implements PropertyService {

    @Resource
    private PropertyMapper propertyMapper;
    @Resource
    private PropertyCategoryMapper propertyCategoryMapper;
    @Resource
    private PropertyDepositoryMapper propertyDepositoryMapper;
    @Resource
    private PropertyApproveMapper propertyApproveMapper;
    @Resource
    private PropertyOperateLogMapper propertyOperateLogMapper;
    @Resource
    private PropertyHandoutMapper propertyHandoutMapper;
    @Resource
    private PropertyLendoutMapper propertyLendoutMapper;
    @Resource
    private PropertyChangeMapper propertyChangeMapper;
    @Resource
    private PropertyTransferMapper propertyTransferMapper;
    @Resource
    private PropertyRepairMapper propertyRepairMapper;
    @Resource
    private PropertyHandleMapper propertyHandleMapper;
    @Resource
    private PropertyMaintainMapper propertyMaintainMapper;
    @Resource
    private CodeGetName codeGetName;
    @Resource
    private FileApi fileApi;


    private static final String PROCESS_DEFINITION_KEY = "proress";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createProperty(PropertySaveReqVO createReqVO) {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();

        String propertyNumber = ProptryCodeEnum.ZCQD.getCode() + CodeUtil.generateUniquecode();
        createReqVO.setPropertyNumber(propertyNumber);
        // 插入
        PropertyDO property = BeanUtils.toBean(createReqVO, PropertyDO.class);
        property.setStatus( ProptryStatusEnum.STATUS_0.getCode() );
        propertyMapper.insert(property);

        //生成查询二维码进行保存
        byte[] bytes = QRCodeUtils.generateQRCode( property.getId().toString(), 350, 350 );
        String file = fileApi.createFile( bytes );
        property.setLabelLink( file );
        propertyMapper.updateById( property );


        //创建审批流程实例
        String processInstanceId = UuidUtils.generateUuid();

        //新增资产入库
        PropertyDepositoryDO propertyDepositoryDO = new PropertyDepositoryDO();
        String number = ProptryCodeEnum.ZCRK.getCode() + CodeUtil.generateUniquecode();
        propertyDepositoryDO.setNumber(number);
        propertyDepositoryDO.setStatus(ProptryAuditStatusEnum.STATUS_1.getCode());
        propertyDepositoryDO.setRemark(createReqVO.getRemark());
        propertyDepositoryDO.setCuserUid(loginUserId);
        propertyDepositoryDO.setMuserUid(loginUserId);
        propertyDepositoryDO.setProcessCode(processInstanceId);
        propertyDepositoryMapper.insert(propertyDepositoryDO);

        //新增关联关系表
        PropertyApproveDO approve = new PropertyApproveDO();
        approve.setVillageId(createReqVO.getVillageId());
        approve.setBuildId(createReqVO.getBuildId());
        approve.setRoomId(createReqVO.getRoomId());
        approve.setApproveNumber(processInstanceId);
        approve.setRelationNumber(property.getId().toString());
        approve.setRelationType(OperateTypeEnum.PUT_DEPOSITORY.getCode());
        approve.setStatus(ProptryAuditStatusEnum.STATUS_1.getCode());
        approve.setCuserUid(loginUserId);
        approve.setMuserUid(loginUserId);
        approve.setProcessType(OperateTypeEnum.PUT_DEPOSITORY.getCode());
        approve.setContent("todo");
        approve.setLaunchTime(LocalDateTime.now());

        propertyApproveMapper.insert(approve);

        //新增操作日志
        propertyOperateLogMapper.insert(new PropertyOperateLogDO()
                .setOperateUid(loginUserId)
                .setPropertyJson(property.getId().toString())
                .setProcessCode(processInstanceId)
                .setOperateType(property.getPropertyNumber())
                .setLogType(OperateTypeEnum.PUT_DEPOSITORY.getCode())
                .setOperateUser(SecurityFrameworkUtils.getLoginUserNickname())
                .setOperateTime(LocalDateTime.now())
                .setOperateContent( "单据编号："+property.getPropertyNumber()+OperateTypeEnum.PUT_DEPOSITORY.getDescription() )
        );

        // 返回
        return property.getId();
    }


    @Override
    public void updateProperty(PropertySaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyExists(updateReqVO.getId());
        // 更新
        PropertyDO updateObj = BeanUtils.toBean(updateReqVO, PropertyDO.class);
        propertyMapper.updateById(updateObj);
    }

    @Override
    public void deleteProperty(Long id) {
        // 校验存在
        validatePropertyExists(id);
        // 删除
        propertyMapper.deleteById(id);
    }

    private void validatePropertyExists(Long id) {
        if (propertyMapper.selectById(id) == null) {
            throw exception(PROPERTY_NOT_EXISTS);
        }
    }

    @Override
    public PropertyRespVO getProperty(Long id) {
        PropertyDO propertyDO = propertyMapper.selectById(id);
        if (propertyDO != null) {
            PropertyRespVO propertyRespVO = BeanUtils.toBean(propertyDO, PropertyRespVO.class);
            PropertyCategoryDO propertyCategoryDO = propertyCategoryMapper.selectById(propertyRespVO.getType());
            if (propertyCategoryDO != null) {
                propertyRespVO.setCategoryName(propertyCategoryDO.getName());
            }
            return propertyRespVO;
        }
        return null;
    }

    @Override
    public PageResult<PropertyDO> getPropertyPage(PropertyPageReqVO pageReqVO) {
        return propertyMapper.selectPage(pageReqVO);
    }

    /**
     * 巡检点绑定资产分页
     *
     * @param pageReqVO
     * @return
     */
    @Override
    public PageResult<PropertyRespVO> getBindAssetsPropertyPage(PropertyPageReqVO pageReqVO) {
        LambdaQueryWrapperX<PropertyDO> queryWrapperX = new LambdaQueryWrapperX<>();
        if (StringUtils.isNotEmpty(pageReqVO.getPropertyNumber())) {
            queryWrapperX.like(PropertyDO::getPropertyNumber, pageReqVO.getPropertyNumber());
        }

        if (StringUtils.isNotEmpty(pageReqVO.getTypes())) {
            String[] split = pageReqVO.getTypes().split(",");
            List<String> typeList = Arrays.asList(split);
            queryWrapperX.in(PropertyDO::getType, typeList);
        }

        if (StringUtils.isNotEmpty(pageReqVO.getName())) {
            queryWrapperX.like(PropertyDO::getName, pageReqVO.getName());
        }

        if (pageReqVO.getStatus()!=null) {
            queryWrapperX.eq(PropertyDO::getStatus, pageReqVO.getStatus());
        }

        if (StringUtils.equals(pageReqVO.getIsDeviceCode(),"1")) {
            queryWrapperX.apply("(device_code IS NOT NULL AND device_code != '')");
        }

        if (StringUtils.isNotEmpty(pageReqVO.getPositionName())) {
            queryWrapperX.like(PropertyDO::getPositionName, pageReqVO.getPositionName());
        }
        if (StringUtils.isNotEmpty(pageReqVO.getBrand())) {
            queryWrapperX.like(PropertyDO::getBrand, pageReqVO.getBrand());
        }
        if (StringUtils.isNotEmpty(pageReqVO.getModelName())) {
            queryWrapperX.like(PropertyDO::getModelName, pageReqVO.getModelName());
        }
        queryWrapperX.orderByDesc(PropertyDO::getCreateTime);
        PageResult<PropertyDO> page = propertyMapper.selectPage(pageReqVO, queryWrapperX);
        PageResult<PropertyRespVO> result = BeanUtils.toBean(page, PropertyRespVO.class);
        getCategoryName(result);
        return result;
    }

    @Override
    public PageResult<PropertyRespVO> getPropertyPageVO(PropertyPageReqVO pageReqVO) {
        //处理
        PageResult<PropertyDO> page = propertyMapper.selectPage(pageReqVO);
        PageResult<PropertyRespVO> result = BeanUtils.toBean(page, PropertyRespVO.class);
        //分类名称
        getCategoryName(result);

        return result;
    }

    private void getCategoryName(PageResult<PropertyRespVO> result) {
        if (CollUtil.isNotEmpty(result.getList())) {
            for (PropertyRespVO vo : result.getList()) {
                //查询分类名称
                PropertyCategoryDO propertyCategoryDO = propertyCategoryMapper.selectById(vo.getType());
                if (propertyCategoryDO != null) {
                    vo.setCategoryName(propertyCategoryDO.getName());
                }

                //查询使用人及使用部门名称
                if(vo.getDepartmentId() != null){
                    vo.setDepartmentName( codeGetName.getDeptName(vo.getDepartmentId()) );
                }
                if(vo.getUserId() != null){
                    vo.setUserName( codeGetName.getUserName( vo.getUserId() ));
                }

            }
        }
    }

//    public void updatePropertyStatus(String id, Integer status) {
//        //查询关联表取出资产ID和库存ID进行更新
//        PropertyApproveDO approve = propertyApproveMapper.selectOne(new LambdaQueryWrapper<PropertyApproveDO>().eq(PropertyApproveDO::getApproveNumber, id));
//        if (approve == null) {
//            return;
//        }
//
//        Integer auditStatus = 3;
//        if (status == 2){
//            auditStatus = ProptryAuditStatusEnum.STATUS_2.getCode();
//        }
//
//        switch (approve.getProcessType()){
//            case "put_depository":
//                //转换状态 2审批通过 3审批不通过
//                if (auditStatus == 2) {
//                    //更新资产清单
//                    PropertyDO propertyDO = new PropertyDO();
//                    propertyDO.setStatus(ProptryStatusEnum.STATUS_1.getCode());
//                    propertyDO.setId(Long.valueOf(approve.getRelationNumber()));
//                    propertyMapper.updateById(propertyDO);
//                    //更新库存状态
//                    LambdaUpdateWrapper<PropertyDepositoryDO> wrapper = new LambdaUpdateWrapper<>();
//                    wrapper.eq(PropertyDepositoryDO::getProcessCode, id)
//                            .set(PropertyDepositoryDO::getStatus, auditStatus)
//                            .set(PropertyDepositoryDO::getOperateUid, SecurityFrameworkUtils.getLoginUserId())
//                            .set(PropertyDepositoryDO::getOperateTime, LocalDateTime.now());
//                    propertyDepositoryMapper.update(wrapper);
//                    //更新审批记录表
//                    propertyApproveMapper.updateById( approve.setStatus( auditStatus ) );
//                } else {
//                    propertyMapper.deleteById(Long.valueOf(approve.getRelationNumber()));
//                    //更新库存状态
//                    LambdaUpdateWrapper<PropertyDepositoryDO> wrapper = new LambdaUpdateWrapper<>();
//                    wrapper.eq(PropertyDepositoryDO::getProcessCode, id)
//                            .set(PropertyDepositoryDO::getStatus, auditStatus)
//                            .set(PropertyDepositoryDO::getOperateUid, SecurityFrameworkUtils.getLoginUserId())
//                            .set(PropertyDepositoryDO::getOperateTime, LocalDateTime.now());
//                    propertyDepositoryMapper.update(wrapper);
//                    //更新审批记录表
//                    propertyApproveMapper.updateById( approve.setStatus( auditStatus ) );
//                }
//                break;
//            case "handout_property":
//                //资产派发
//            case "receive_property":
//                //资产领用
//                PropertyHandoutDO handout = propertyHandoutMapper.selectOne(new LambdaQueryWrapper<PropertyHandoutDO>().eq(PropertyHandoutDO::getProcessCode, id));
//                String[] split1 = handout.getPropertyIds().split( "," );
//                if (auditStatus == 2) {
//                    //查询派发单据
//                    //更新资产清单
//                    for (String s : split1) {
//                        PropertyDO propertyDO = new PropertyDO();
//                        propertyDO.setStatus(ProptryStatusEnum.STATUS_2.getCode());
//                        propertyDO.setId(Long.valueOf(s));
//                        propertyMapper.updateById(propertyDO);
//                    }
//
//                    //更新库存状态
//                    propertyHandoutMapper.updateById(handout.setStatus( auditStatus ).setOperateUid( SecurityFrameworkUtils.getLoginUserId() ).setOperateTime( LocalDateTime.now() ));
//                    //更新审批记录表
//                    propertyApproveMapper.updateById( approve.setStatus( auditStatus ) );
//                } else {
//                    for (String s : split1) {
//                        PropertyDO propertyDO = new PropertyDO();
//                        propertyDO.setStatus(ProptryStatusEnum.STATUS_1.getCode());
//                        propertyDO.setId(Long.valueOf(s));
//                        propertyMapper.updateById(propertyDO);
//                    }
//                    //更新库存状态
//                    propertyHandoutMapper.updateById(handout.setStatus( auditStatus ).setOperateUid( SecurityFrameworkUtils.getLoginUserId() ).setOperateTime( LocalDateTime.now() ));
//                    //更新审批记录表
//                    propertyApproveMapper.updateById( approve.setStatus( auditStatus ) );
//                }
//                break;
//            case "return_property":
//                //资产退库
//                PropertyHandoutDO handoutDO = propertyHandoutMapper.selectOne(new LambdaQueryWrapper<PropertyHandoutDO>().eq(PropertyHandoutDO::getProcessCode, id));
//                String[] split2 = handoutDO.getPropertyIds().split( "," );
//                if (auditStatus == 2) {
//                    //更新资产清单
//                    for (String s : split2) {
//                        PropertyDO propertyDO = new PropertyDO();
//                        propertyDO.setStatus(ProptryStatusEnum.STATUS_1.getCode());
//                        propertyDO.setId(Long.valueOf(s));
//                        propertyMapper.updateById(propertyDO);
//                    }
//                    //更新库存状态
//                    propertyHandoutMapper.updateById(handoutDO.setStatus( auditStatus ).setOperateUid( SecurityFrameworkUtils.getLoginUserId() ).setOperateTime( LocalDateTime.now() ));
//                    //更新审批记录表
//                    propertyApproveMapper.updateById( approve.setStatus( auditStatus ) );
//                } else {
//                    for (String s : split2) {
//                        PropertyDO propertyDO = new PropertyDO();
//                        propertyDO.setStatus(ProptryStatusEnum.STATUS_2.getCode());
//                        propertyDO.setId(Long.valueOf(s));
//                        propertyMapper.updateById(propertyDO);
//                    }
//                    //更新库存状态
//                    propertyHandoutMapper.updateById(handoutDO.setStatus( auditStatus ).setOperateUid( SecurityFrameworkUtils.getLoginUserId() ).setOperateTime( LocalDateTime.now() ));
//                    //更新审批记录表
//                    propertyApproveMapper.updateById( approve.setStatus( auditStatus ) );
//                }
//                break;
//            case "lendout_property":
//                //资产借出
//            case "borrow_property":
//                //查新借用单据
//                PropertyLendoutDO propertyLendoutDO = propertyLendoutMapper.selectOne( new LambdaQueryWrapper<PropertyLendoutDO>().eq( PropertyLendoutDO::getProcessCode, id ) );
//                String[] split3 = propertyLendoutDO.getPropertyIds().split( "," );
//                //资产借用
//                if (auditStatus == 2) {
//                    for (String s : split3) {
//                        PropertyDO propertyDO = new PropertyDO();
//                        propertyDO.setStatus(ProptryStatusEnum.STATUS_3.getCode());
//                        propertyDO.setId(Long.valueOf(s));
//                        propertyMapper.updateById(propertyDO);
//                    }
//                    //更新库存状态
//                    propertyLendoutMapper.updateById(propertyLendoutDO.setStatus( auditStatus ).setOperateUid( SecurityFrameworkUtils.getLoginUserId() ).setOperateTime( LocalDateTime.now() ));
//                    //更新审批记录表
//                    propertyApproveMapper.updateById( approve.setStatus( auditStatus ) );
//                } else {
//                    for (String s : split3) {
//                        PropertyDO propertyDO = new PropertyDO();
//                        propertyDO.setStatus(ProptryStatusEnum.STATUS_1.getCode());
//                        propertyDO.setId(Long.valueOf(s));
//                        propertyMapper.updateById(propertyDO);
//                    }
//                    //更新库存状态
//                    propertyLendoutMapper.updateById(propertyLendoutDO.setStatus( auditStatus ).setOperateUid( SecurityFrameworkUtils.getLoginUserId() ).setOperateTime( LocalDateTime.now() ));
//                    //更新审批记录表
//                    propertyApproveMapper.updateById( approve.setStatus( auditStatus ) );
//                }
//                break;
//            case "revert_property":
//                //资产归还
//            case "retreat_property":
//                //资产退还
//                PropertyLendoutDO propertyLendout = propertyLendoutMapper.selectOne( new LambdaQueryWrapper<PropertyLendoutDO>().eq( PropertyLendoutDO::getProcessCode, id ) );
//                String[] split4 = propertyLendout.getPropertyIds().split( "," );
//                if (auditStatus == 2) {
//                    for (String s : split4) {
//                        PropertyDO propertyDO = new PropertyDO();
//                        propertyDO.setStatus(ProptryStatusEnum.STATUS_1.getCode());
//                        propertyDO.setId(Long.valueOf(s));
//                        propertyMapper.updateById(propertyDO);
//                    }
//                    //更新库存状态
//                    propertyLendoutMapper.updateById(propertyLendout.setStatus( auditStatus ).setOperateUid( SecurityFrameworkUtils.getLoginUserId() ).setOperateTime( LocalDateTime.now() ));
//                    //更新审批记录表
//                    propertyApproveMapper.updateById( approve.setStatus( auditStatus ) );
//                } else {
//                    for (String s : split4) {
//                        PropertyDO propertyDO = new PropertyDO();
//                        propertyDO.setStatus(ProptryStatusEnum.STATUS_3.getCode());
//                        propertyDO.setId(Long.valueOf(s));
//                        propertyMapper.updateById(propertyDO);
//                    }
//                    //更新库存状态
//                    propertyLendoutMapper.updateById(propertyLendout.setStatus( auditStatus ).setOperateUid( SecurityFrameworkUtils.getLoginUserId() ).setOperateTime( LocalDateTime.now() ));
//                    //更新审批记录表
//                    propertyApproveMapper.updateById( approve.setStatus( auditStatus ) );
//                }
//                break;
//            case "report_loss_property":
//                //资产报失
//                //修改资产清单状态
//
//                //修改审批状态
//
//                //修改派发单据
//                break;
//            case "transfer_property":
//                //资产调拨
//                PropertyTransferDO transferDO = propertyTransferMapper.selectOne( PropertyTransferDO::getProcessCode, id );
//                String[] split = transferDO.getPropertyIds().split( "," );
//                if (auditStatus == 2) {
//                    //查询调拨单数据
//                    for (String s : split) {
//                        PropertyDO propertyDO = new PropertyDO();
//                        propertyDO.setStatus(ProptryStatusEnum.STATUS_1.getCode());
//                        propertyDO.setId(Long.valueOf(s));
//                        propertyDO.setAdminId( transferDO.getInAdminUid() );
//                        propertyDO.setAdminName( transferDO.getInAdminUidName() );
//                        propertyDO.setPositionId( transferDO.getInLocationId() );
//                        propertyMapper.updateById(propertyDO);
//                    }
//                    //更新资产调拨状态
//                    propertyTransferMapper.updateById(transferDO.setStatus( auditStatus ).setOperateUid( SecurityFrameworkUtils.getLoginUserId() ).setOperateTime( LocalDateTime.now() ));
//                    //更新审批记录表
//                    propertyApproveMapper.updateById( approve.setStatus( auditStatus ) );
//                } else {
//                    for (String s : split) {
//                        PropertyDO propertyDO = new PropertyDO();
//                        propertyDO.setStatus(ProptryStatusEnum.STATUS_1.getCode());
//                        propertyDO.setId(Long.valueOf(s));
//                        propertyMapper.updateById(propertyDO);
//                    }
//                    //更新库存状态
//                    propertyTransferMapper.updateById(transferDO.setStatus( auditStatus ).setOperateUid( SecurityFrameworkUtils.getLoginUserId() ).setOperateTime( LocalDateTime.now() ));
//                    //更新审批记录表
//                    propertyApproveMapper.updateById( approve.setStatus( auditStatus ) );
//                }
//                break;
//            case "change_property":
//                //变更领用人
//                PropertyChangeDO changeDO = propertyChangeMapper.selectOne( PropertyChangeDO::getProcessCode, id );
//                String[] split5 = changeDO.getPropertyIds().split( "," );
//                if (auditStatus == 2) {
//                //更新派发单据 -获取变更信息 -修改派发
//                    for (String s : split5) {
//                        PropertyDO propertyDO = new PropertyDO();
//                        propertyDO.setStatus(ProptryStatusEnum.STATUS_1.getCode());
//                        propertyDO.setId(Long.valueOf(s));
//                        propertyDO.setUserId( changeDO.getAfterUseUid() );
//                        propertyDO.setDepartmentId( changeDO.getAfterUseDepartmentId() );
//                        propertyMapper.updateById(propertyDO);
//                    }
//                PropertyHandoutDO propertyHandoutDO = new PropertyHandoutDO();
//                propertyHandoutDO.setId( changeDO.getMuserUid());
//                propertyHandoutDO.setReceiveUid( changeDO.getAfterUseUid() );
//                propertyHandoutDO.setDepartmentId( changeDO.getAfterUseDepartmentId() );
//                propertyHandoutMapper.updateById( propertyHandoutDO );
//
//                //更新变更状态
//                propertyChangeMapper.updateById(changeDO.setStatus( auditStatus ).setOperateUid( SecurityFrameworkUtils.getLoginUserId() ).setOperateTime( LocalDateTime.now() ));
//                //更新审批记录表
//                propertyApproveMapper.updateById( approve.setStatus( auditStatus ) );
//            } else {
//                for (String s : split5) {
//                    PropertyDO propertyDO = new PropertyDO();
//                    propertyDO.setStatus(ProptryStatusEnum.STATUS_1.getCode());
//                    propertyDO.setId(Long.valueOf(s));
//                    propertyMapper.updateById(propertyDO);
//                }
//                //更新变更状态
//                propertyChangeMapper.updateById(changeDO.setStatus( auditStatus ).setOperateUid( SecurityFrameworkUtils.getLoginUserId() ).setOperateTime( LocalDateTime.now() ));
//                //更新审批记录表
//                propertyApproveMapper.updateById( approve.setStatus( auditStatus ) );
//            }
//                break;
//            case "handover_property":
//                //交接他人
//
//                break;
//            case "repair_property":
//                //资产维修
//            case "report_repair_property":
//                //资产报修
//                PropertyRepairDO propertyRepairDO = propertyRepairMapper.selectOne( PropertyRepairDO::getProcessCode, id );
//                String[] split8 = propertyRepairDO.getPropertyIds().split( "," );
//                if (auditStatus == 2) {
//                    for (String s : split8) {
//                        PropertyDO propertyDO = new PropertyDO();
//                        propertyDO.setStatus(ProptryStatusEnum.STATUS_10.getCode());
//                        propertyDO.setId(Long.valueOf(s));
//                        propertyMapper.updateById(propertyDO);
//                    }
//                    //更新报修状态
//                    propertyRepairMapper.updateById(propertyRepairDO.setStatus( auditStatus ).setOperateUid( SecurityFrameworkUtils.getLoginUserId() ).setOperateTime( LocalDateTime.now() ));
//                    //更新审批记录表
//                    propertyApproveMapper.updateById( approve.setStatus( auditStatus ) );
//                } else {
//                    for (String s : split8) {
//                        PropertyDO propertyDO = new PropertyDO();
//                        propertyDO.setStatus(ProptryStatusEnum.STATUS_1.getCode());
//                        propertyDO.setId(Long.valueOf(s));
//                        propertyMapper.updateById(propertyDO);
//                    }
//                    //更新库存状态
//                    propertyRepairMapper.updateById(propertyRepairDO.setStatus( auditStatus ).setOperateUid( SecurityFrameworkUtils.getLoginUserId() ).setOperateTime( LocalDateTime.now() ));
//                    //更新审批记录表
//                    propertyApproveMapper.updateById( approve.setStatus( auditStatus ) );
//                }
//                break;
//            case "property_maintain":
//                PropertyMaintainDO propertyMaintainDO = propertyMaintainMapper.selectOne( new LambdaQueryWrapperX<PropertyMaintainDO>().eq( PropertyMaintainDO::getProcessCode, id ) );
//                String[] split7 = propertyMaintainDO.getPropertyIds().split( "," );
//                //资产保养
//                if (auditStatus == 2) {
//                    for (String s : split7) {
//                        PropertyDO propertyDO = new PropertyDO();
//                        propertyDO.setStatus(ProptryStatusEnum.STATUS_19.getCode());
//                        propertyDO.setId(Long.valueOf(s));
//                        propertyMapper.updateById(propertyDO);
//                    }
//                    //更新库存状态
//                    propertyMaintainMapper.updateById(propertyMaintainDO.setStatus( auditStatus ).setOperateUid( SecurityFrameworkUtils.getLoginUserId() ).setOperateTime( LocalDateTime.now() ));
//                    //更新审批记录表
//                    propertyApproveMapper.updateById( approve.setStatus( auditStatus ) );
//                } else {
//                    for (String s : split7) {
//                        PropertyDO propertyDO = new PropertyDO();
//                        propertyDO.setStatus(ProptryStatusEnum.STATUS_1.getCode());
//                        propertyDO.setId(Long.valueOf(s));
//                        propertyMapper.updateById(propertyDO);
//                    }
//
//                    //更新库存状态
//                    propertyMaintainMapper.updateById(propertyMaintainDO.setStatus( auditStatus ).setOperateUid( SecurityFrameworkUtils.getLoginUserId() ).setOperateTime( LocalDateTime.now() ));
//                    //更新审批记录表
//                    propertyApproveMapper.updateById( approve.setStatus( auditStatus ) );
//                }
//                break;
//        }
//    }

    @Override
    public void updatePropertyStatus(String id, Integer status) {
        // 查询关联表取出资产ID和库存ID进行更新
        PropertyApproveDO approve = propertyApproveMapper.selectOne(
                new LambdaQueryWrapper<PropertyApproveDO>().eq(PropertyApproveDO::getApproveNumber, id)
        );
        if (approve == null) {
            return;
        }

        Integer auditStatus = status == 2 ? ProptryAuditStatusEnum.STATUS_2.getCode() : ProptryAuditStatusEnum.STATUS_3.getCode();

        // 处理不同类型的审批
        switch (approve.getProcessType()) {
            case "put_depository":
                updatePropertyAndDepository(id, approve, auditStatus, ProptryStatusEnum.STATUS_1.getCode(), auditStatus);
                break;
            case "handout_property":
            case "receive_property":
                updatePropertyAndHandout(id, approve, auditStatus, ProptryStatusEnum.STATUS_2.getCode(), ProptryStatusEnum.STATUS_1.getCode());
                break;
            case "return_property":
                updatePropertyAndHandout(id, approve, auditStatus, ProptryStatusEnum.STATUS_1.getCode(), ProptryStatusEnum.STATUS_2.getCode());
                break;
            case "lendout_property":
            case "borrow_property":
                updatePropertyAndLendout(id, approve, auditStatus, ProptryStatusEnum.STATUS_3.getCode(), ProptryStatusEnum.STATUS_1.getCode());
                break;
            case "revert_property":
            case "retreat_property":
                updatePropertyAndLendout(id, approve, auditStatus, ProptryStatusEnum.STATUS_1.getCode(), ProptryStatusEnum.STATUS_3.getCode());
                break;
            case "transfer_property":
                updatePropertyAndTransfer(id, approve, auditStatus, ProptryStatusEnum.STATUS_1.getCode());
                break;
            case "change_property":
                updatePropertyAndChange(id, approve, auditStatus);
                break;
            case "repair_property":
            case "report_repair_property":
                updatePropertyAndRepair(id, approve, auditStatus, ProptryStatusEnum.STATUS_10.getCode(), ProptryStatusEnum.STATUS_1.getCode());
                break;
            case "property_maintain":
                updatePropertyAndMaintain(id, approve, auditStatus, ProptryStatusEnum.STATUS_19.getCode(), ProptryStatusEnum.STATUS_1.getCode());
                break;
            // case "handover_property":
            // case "report_loss_property":
            //     // 其他审批类型可以根据需求添加
            //     break;
        }
    }

    private void updatePropertyAndDepository(String id, PropertyApproveDO approve, Integer auditStatus, Integer successStatus, Integer failStatus) {
        if (auditStatus == ProptryAuditStatusEnum.STATUS_2.getCode()) {
            PropertyDO propertyDO = new PropertyDO();
            propertyDO.setStatus(successStatus);
            propertyDO.setId(Long.valueOf(approve.getRelationNumber()));
            propertyMapper.updateById(propertyDO);

            updateDepository(id, auditStatus);
        } else {
            propertyMapper.deleteById(Long.valueOf(approve.getRelationNumber()));
            updateDepository(id, failStatus);
        }
        updateApproveRecord(approve, auditStatus);
    }

    private void updatePropertyAndHandout(String id, PropertyApproveDO approve, Integer auditStatus, Integer successStatus, Integer failStatus) {
        log.info( "updatePropertyAndHandout: {},id={}", approve.getRelationNumber(),id );
        LambdaQueryWrapper<PropertyHandoutDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PropertyHandoutDO::getProcessCode, id);
        PropertyHandoutDO handout = propertyHandoutMapper.selectOne(wrapper);
        String[] propertyIds = handout.getPropertyIds().split(",");

        updatePropertyList(propertyIds, auditStatus == ProptryAuditStatusEnum.STATUS_2.getCode() ? successStatus : failStatus);

        propertyHandoutMapper.updateById(handout.setStatus(auditStatus).setOperateUid(SecurityFrameworkUtils.getLoginUserId()).setOperateTime(LocalDateTime.now()));
        updateApproveRecord(approve, auditStatus);
    }

    private void updatePropertyAndLendout(String id, PropertyApproveDO approve, Integer auditStatus, Integer successStatus, Integer failStatus) {
        PropertyLendoutDO lendout = propertyLendoutMapper.selectOne(new LambdaQueryWrapper<PropertyLendoutDO>().eq(PropertyLendoutDO::getProcessCode, id));
        String[] propertyIds = lendout.getPropertyIds().split(",");

        updatePropertyList(propertyIds, auditStatus == ProptryAuditStatusEnum.STATUS_2.getCode() ? successStatus : failStatus);

        propertyLendoutMapper.updateById(lendout.setStatus(auditStatus).setOperateUid(SecurityFrameworkUtils.getLoginUserId()).setOperateTime(LocalDateTime.now()));
        updateApproveRecord(approve, auditStatus);
    }

    private void updatePropertyAndTransfer(String id, PropertyApproveDO approve, Integer auditStatus, Integer successStatus) {
        PropertyTransferDO transfer = propertyTransferMapper.selectOne(new LambdaQueryWrapper<PropertyTransferDO>().eq(PropertyTransferDO::getProcessCode, id));
        String[] propertyIds = transfer.getPropertyIds().split(",");

        for (String propertyId : propertyIds) {
            PropertyDO propertyDO = new PropertyDO();
            propertyDO.setStatus(successStatus);
            propertyDO.setId(Long.valueOf(propertyId));
            propertyDO.setAdminId(transfer.getInAdminUid());
            propertyDO.setAdminName(transfer.getInAdminUidName());
            propertyDO.setPositionId(transfer.getInLocationId());
            propertyMapper.updateById(propertyDO);
        }

        propertyTransferMapper.updateById(transfer.setStatus(auditStatus).setOperateUid(SecurityFrameworkUtils.getLoginUserId()).setOperateTime(LocalDateTime.now()));
        updateApproveRecord(approve, auditStatus);
    }

    private void updatePropertyAndChange(String id, PropertyApproveDO approve, Integer auditStatus) {
        PropertyChangeDO change = propertyChangeMapper.selectOne(new LambdaQueryWrapper<PropertyChangeDO>().eq(PropertyChangeDO::getProcessCode, id));
        String[] propertyIds = change.getPropertyIds().split(",");

        for (String propertyId : propertyIds) {
            PropertyDO propertyDO = new PropertyDO();
            propertyDO.setStatus(ProptryStatusEnum.STATUS_1.getCode());
            propertyDO.setId(Long.valueOf(propertyId));
            propertyDO.setUserId(change.getAfterUseUid());
            propertyDO.setDepartmentId(change.getAfterUseDepartmentId());
            propertyMapper.updateById(propertyDO);
        }

        PropertyHandoutDO handout = new PropertyHandoutDO();
        handout.setId(change.getMuserUid());
        handout.setReceiveUid(change.getAfterUseUid());
        handout.setDepartmentId(change.getAfterUseDepartmentId());
        propertyHandoutMapper.updateById(handout);

        propertyChangeMapper.updateById(change.setStatus(auditStatus).setOperateUid(SecurityFrameworkUtils.getLoginUserId()).setOperateTime(LocalDateTime.now()));
        updateApproveRecord(approve, auditStatus);
    }

    private void updatePropertyAndRepair(String id, PropertyApproveDO approve, Integer auditStatus, Integer successStatus, Integer failStatus) {
        PropertyRepairDO repair = propertyRepairMapper.selectOne(new LambdaQueryWrapper<PropertyRepairDO>().eq(PropertyRepairDO::getProcessCode, id));
        String[] propertyIds = repair.getPropertyIds().split(",");

        updatePropertyList(propertyIds, auditStatus == ProptryAuditStatusEnum.STATUS_2.getCode() ? successStatus : failStatus);

        propertyRepairMapper.updateById(repair.setStatus(auditStatus).setOperateUid(SecurityFrameworkUtils.getLoginUserId()).setOperateTime(LocalDateTime.now()));
        updateApproveRecord(approve, auditStatus);
    }

    private void updatePropertyAndMaintain(String id, PropertyApproveDO approve, Integer auditStatus, Integer successStatus, Integer failStatus) {
        PropertyMaintainDO maintain = propertyMaintainMapper.selectOne(new LambdaQueryWrapperX<PropertyMaintainDO>().eq(PropertyMaintainDO::getProcessCode, id));
        String[] propertyIds = maintain.getPropertyIds().split(",");

        updatePropertyList(propertyIds, auditStatus == ProptryAuditStatusEnum.STATUS_2.getCode() ? successStatus : failStatus);

        propertyMaintainMapper.updateById(maintain.setStatus(auditStatus).setOperateUid(SecurityFrameworkUtils.getLoginUserId()).setOperateTime(LocalDateTime.now()));
        updateApproveRecord(approve, auditStatus);
    }

    private void updatePropertyList(String[] propertyIds, Integer status) {
        for (String propertyId : propertyIds) {
            PropertyDO propertyDO = new PropertyDO();
            propertyDO.setStatus(status);
            propertyDO.setId(Long.valueOf(propertyId));
            propertyMapper.updateById(propertyDO);
        }
    }

    private void updateDepository(String id, Integer status) {
        LambdaUpdateWrapper<PropertyDepositoryDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(PropertyDepositoryDO::getProcessCode, id)
                .set(PropertyDepositoryDO::getStatus, status)
                .set(PropertyDepositoryDO::getOperateUid, SecurityFrameworkUtils.getLoginUserId())
                .set(PropertyDepositoryDO::getOperateTime, LocalDateTime.now());
        propertyDepositoryMapper.update(wrapper);
    }

    private void updateApproveRecord(PropertyApproveDO approve, Integer status) {
        propertyApproveMapper.updateById(approve.setStatus(status));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createHandoutProperty(PropertyHandoutSaveReqVO createReqVO) {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        String[] propertyIds = createReqVO.getPropertyIds().split(",");

        if (createReqVO.getType() == 1) {
            checkAndUpdatePropertyStatus(propertyIds, ProptryStatusEnum.STATUS_1.getCode(), ProptryStatusEnum.STATUS_6.getCode());
        } else if (createReqVO.getType() == 2) {
            for (String id : propertyIds) {
                PropertyDO propertyDO = propertyMapper.selectById(id);
                if (propertyDO == null) {
                    continue;
                }
                Integer status = propertyDO.getStatus();
                if (!Objects.equals(status, ProptryStatusEnum.STATUS_2.getCode()) &&
                        !Objects.equals(status, ProptryStatusEnum.STATUS_18.getCode()) &&
                        !Objects.equals(status, ProptryStatusEnum.STATUS_19.getCode())) {
                    throw new ServiceException(406, "资产状态不符合预期，请检查资产状态");
                }
                propertyMapper.updateById(propertyDO.setStatus(ProptryStatusEnum.STATUS_7.getCode()));
            }
        }

        String propertyNumber = createReqVO.getType() == 1 ?
                ProptryCodeEnum.ZCPF.getCode() : ProptryCodeEnum.ZCTK.getCode();
        propertyNumber += CodeUtil.generateUniquecode();

        PropertyHandoutDO propertyHandoutDO = BeanUtils.toBean(createReqVO, PropertyHandoutDO.class);
        propertyHandoutDO.setNumber(propertyNumber);
        propertyHandoutDO.setStatus(ProptryAuditStatusEnum.STATUS_1.getCode());
        propertyHandoutMapper.insert(propertyHandoutDO);

        String processInstanceId = createProcessInstanceAndSaveApprove(loginUserId, propertyHandoutDO.getId().toString(),
                createReqVO.getType() == 1 ? OperateTypeEnum.HANDOUT_PROPERTY.getCode() : OperateTypeEnum.RETURN_PROPERTY.getCode(),
                propertyHandoutDO.getId());

        propertyHandoutMapper.updateById( propertyHandoutDO.setProcessCode(processInstanceId) );

        createOperateLog(loginUserId, createReqVO.getPropertyIds(), processInstanceId, propertyNumber,
                createReqVO.getType() == 1 ? OperateTypeEnum.HANDOUT_PROPERTY.getCode() : OperateTypeEnum.RETURN_PROPERTY.getCode(),
                createReqVO.getType() == 1 ? OperateTypeEnum.HANDOUT_PROPERTY.getDescription() : OperateTypeEnum.RETURN_PROPERTY.getDescription());

        return propertyHandoutDO.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createLendoutProperty(PropertyLendoutSaveReqVO createReqVO) {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        String[] propertyIds = createReqVO.getPropertyIds().split(",");

        if (createReqVO.getType() == 1) {
            checkAndUpdatePropertyStatus(propertyIds, ProptryStatusEnum.STATUS_1.getCode(), ProptryStatusEnum.STATUS_8.getCode());
        } else if (createReqVO.getType() == 2) {
            checkAndUpdatePropertyStatus(propertyIds, ProptryStatusEnum.STATUS_3.getCode(), ProptryStatusEnum.STATUS_9.getCode());
        }

        String propertyNumber = createReqVO.getType() == 1 ?
                ProptryCodeEnum.ZCJC.getCode() : ProptryCodeEnum.ZCGH.getCode();
        propertyNumber += CodeUtil.generateUniquecode();

        PropertyLendoutDO propertyLendoutDO = BeanUtils.toBean(createReqVO, PropertyLendoutDO.class);
        propertyLendoutDO.setNumber(propertyNumber);
        propertyLendoutDO.setStatus(ProptryAuditStatusEnum.STATUS_1.getCode());
        propertyLendoutMapper.insert(propertyLendoutDO);

        String processInstanceId = createProcessInstanceAndSaveApprove(loginUserId, propertyLendoutDO.getId().toString(),
                createReqVO.getType() == 1 ? OperateTypeEnum.LENDOUT_PROPERTY.getCode() : OperateTypeEnum.REVERT_PROPERTY.getCode(),
                propertyLendoutDO.getId());

        propertyLendoutMapper.updateById( propertyLendoutDO.setProcessCode(processInstanceId) );

        createOperateLog(loginUserId, createReqVO.getPropertyIds(), processInstanceId, propertyNumber,
                createReqVO.getType() == 1 ? OperateTypeEnum.LENDOUT_PROPERTY.getCode() : OperateTypeEnum.REVERT_PROPERTY.getCode(),
                createReqVO.getType() == 1 ? OperateTypeEnum.LENDOUT_PROPERTY.getDescription() : OperateTypeEnum.REVERT_PROPERTY.getDescription());

        return propertyLendoutDO.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createChangeProperty(PropertyChangeSaveReqVO createReqVO) {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        String[] propertyIds = createReqVO.getPropertyIds().split(",");

        if (propertyIds.length == 0) {
            throw exception(PROPERTY_HANDOUT_NOT_EXISTS);
        }

        // 校验资产状态并更新
        checkAndUpdatePropertyStatus(propertyIds, ProptryStatusEnum.STATUS_2.getCode(), ProptryStatusEnum.STATUS_14.getCode());

        String propertyNumber = ProptryCodeEnum.ZCBG.getCode() + CodeUtil.generateUniquecode();
        PropertyChangeDO propertyChangeDO = BeanUtils.toBean(createReqVO, PropertyChangeDO.class);
        propertyChangeDO.setNumber(propertyNumber);
        propertyChangeDO.setStatus(ProptryAuditStatusEnum.STATUS_1.getCode());
        propertyChangeMapper.insert(propertyChangeDO);

        // 创建审批流程并保存关联关系表
        String processInstanceId = createProcessInstanceAndSaveApprove(loginUserId, String.valueOf(propertyChangeDO.getId()), OperateTypeEnum.CHANGE_PROPERTY.getCode(), propertyChangeDO.getId());

        propertyChangeMapper.updateById( propertyChangeDO.setProcessCode(processInstanceId) );
        // 新增操作日志
        createOperateLog(loginUserId, createReqVO.getPropertyIds(), processInstanceId, propertyNumber, OperateTypeEnum.CHANGE_PROPERTY.getCode(), OperateTypeEnum.CHANGE_PROPERTY.getDescription());

        return propertyChangeDO.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createTransferProperty(PropertyTransferSaveReqVO createReqVO) {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        String[] propertyIds = createReqVO.getPropertyIds().split(",");

        if (propertyIds.length == 0) {
            throw exception(PROPERTY_HANDOUT_NOT_EXISTS);
        }

        // 校验资产状态并更新
        checkAndUpdatePropertyStatus(propertyIds, ProptryStatusEnum.STATUS_1.getCode(), ProptryStatusEnum.STATUS_13.getCode());

        String propertyNumber = ProptryCodeEnum.ZCDB.getCode() + CodeUtil.generateUniquecode();
        PropertyTransferDO propertyTransferDO = BeanUtils.toBean(createReqVO, PropertyTransferDO.class);
        propertyTransferDO.setNumber(propertyNumber);
        propertyTransferDO.setStatus(ProptryAuditStatusEnum.STATUS_1.getCode());
        propertyTransferMapper.insert(propertyTransferDO);

        // 创建审批流程并保存关联关系表
        String processInstanceId = createProcessInstanceAndSaveApprove(loginUserId, String.valueOf(propertyTransferDO.getId()), OperateTypeEnum.TRANSFER_PROPERTY.getCode(), propertyTransferDO.getId());

        propertyTransferMapper.updateById( propertyTransferDO.setProcessCode(processInstanceId) );
        // 新增操作日志
        createOperateLog(loginUserId, createReqVO.getPropertyIds(), processInstanceId, propertyNumber, OperateTypeEnum.TRANSFER_PROPERTY.getCode(), OperateTypeEnum.TRANSFER_PROPERTY.getDescription());

        return propertyTransferDO.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createRepairProperty(PropertyRepairSaveReqVO createReqVO) {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        String[] propertyIds = createReqVO.getPropertyIds().split(",");

        if (propertyIds.length == 0) {
            throw exception(PROPERTY_HANDOUT_NOT_EXISTS);
        }

        // 校验资产状态并更新
        checkAndUpdatePropertyStatus(propertyIds, ProptryStatusEnum.STATUS_1.getCode(), ProptryStatusEnum.STATUS_10.getCode()); // 根据实际需求修改

        String propertyNumber = ProptryCodeEnum.ZCWX.getCode() + CodeUtil.generateUniquecode();
        PropertyRepairDO propertyRepairDO = BeanUtils.toBean(createReqVO, PropertyRepairDO.class);
        propertyRepairDO.setNumber(propertyNumber);
        propertyRepairDO.setStatus(ProptryAuditStatusEnum.STATUS_1.getCode());
        propertyRepairMapper.insert(propertyRepairDO);

        // 创建审批流程并保存关联关系表
        String processInstanceId = createProcessInstanceAndSaveApprove(loginUserId, String.valueOf(propertyRepairDO.getId()), OperateTypeEnum.REPAIR_PROPERTY.getCode(), propertyRepairDO.getId());

        propertyRepairMapper.updateById( propertyRepairDO.setProcessCode(processInstanceId) );

        // 新增操作日志
        createOperateLog(loginUserId, createReqVO.getPropertyIds(), processInstanceId, propertyNumber, OperateTypeEnum.REPAIR_PROPERTY.getCode(), OperateTypeEnum.REPAIR_PROPERTY.getDescription());

        return propertyRepairDO.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createHandleProperty(PropertyHandleSaveReqVO createReqVO) {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        String processNumber = createReqVO.getProcessNumber();

        PropertyDO propertyDO = propertyMapper.selectOne(new LambdaQueryWrapperX<PropertyDO>().eq( PropertyDO::getPropertyNumber, processNumber ));
        if (propertyDO == null) {
            throw new ServiceException(406, "资产不存在，请检查资产编号");
        }
        Integer status = propertyDO.getStatus();
        if (!Objects.equals(status, ProptryStatusEnum.STATUS_1.getCode())) {
            throw new ServiceException(406, "资产状态不符合预期，请检查资产状态");
        }
        propertyMapper.updateById(propertyDO.setStatus(ProptryStatusEnum.STATUS_4.getCode()));

        String propertyNumber = ProptryCodeEnum.ZCCL.getCode() + CodeUtil.generateUniquecode();
        PropertyHandleDO propertyHandleDO = BeanUtils.toBean(createReqVO, PropertyHandleDO.class);
        propertyHandleDO.setProcessNumber(propertyNumber);
        propertyHandleDO.setStatus(ProptryAuditStatusEnum.STATUS_2.getCode());
        propertyHandleMapper.insert(propertyHandleDO);

        //新增审批
        PropertyApproveDO approve = new PropertyApproveDO();
        approve.setApproveNumber(propertyNumber);
        approve.setRelationNumber(propertyDO.getId().toString());
        approve.setRelationType(OperateTypeEnum.HANDLE_PROPERTY.getCode());
        approve.setStatus(ProptryAuditStatusEnum.STATUS_2.getCode());
        approve.setCuserUid(loginUserId);
        approve.setMuserUid(loginUserId);
        approve.setProcessType(OperateTypeEnum.HANDLE_PROPERTY.getCode());
        approve.setContent("资产处置");
        approve.setLaunchTime(LocalDateTime.now());
        propertyApproveMapper.insert(approve);
        // 新增操作日志
        createOperateLog(loginUserId, createReqVO.getProcessNumber(), propertyNumber, propertyNumber, OperateTypeEnum.HANDLE_PROPERTY.getCode(), OperateTypeEnum.HANDLE_PROPERTY.getDescription());

        return propertyHandleDO.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createMaintainProperty(PropertyMaintainSaveReqVO createReqVO) {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        String[] propertyIds = createReqVO.getPropertyIds().split(",");

        if (propertyIds.length == 0) {
            throw exception(PROPERTY_HANDOUT_NOT_EXISTS);
        }

        // 校验资产状态并更新
        checkAndUpdatePropertyStatus(propertyIds, ProptryStatusEnum.STATUS_1.getCode(), ProptryStatusEnum.STATUS_18.getCode()); // 根据实际需求修改

        String propertyNumber = ProptryCodeEnum.ZCBY.getCode() + CodeUtil.generateUniquecode();
        PropertyMaintainDO propertyMaintainDO = BeanUtils.toBean(createReqVO, PropertyMaintainDO.class);
        propertyMaintainDO.setNumber(propertyNumber);
        propertyMaintainDO.setStatus(ProptryAuditStatusEnum.STATUS_1.getCode());
        propertyMaintainMapper.insert(propertyMaintainDO);

        // 创建审批流程并保存关联关系表
        String processInstanceId = createProcessInstanceAndSaveApprove(loginUserId, String.valueOf(propertyMaintainDO.getId()), OperateTypeEnum.PROPERTY_MAINTAIN.getCode(), propertyMaintainDO.getId());

        propertyMaintainMapper.updateById( propertyMaintainDO.setProcessCode(processInstanceId) );

        // 新增操作日志
        createOperateLog(loginUserId, createReqVO.getPropertyIds(), processInstanceId, propertyNumber, OperateTypeEnum.PROPERTY_MAINTAIN.getCode(), OperateTypeEnum.PROPERTY_MAINTAIN.getDescription());

        return propertyMaintainDO.getId();
    }

    private void checkAndUpdatePropertyStatus(String[] propertyIds, Integer expectedStatus, Integer newStatus) {
        for (String id : propertyIds) {
            PropertyDO propertyDO = propertyMapper.selectById(id);
            if (propertyDO == null) {
                continue;
            }
            Integer status = propertyDO.getStatus();
            if (!Objects.equals( status, expectedStatus )) {
                throw new ServiceException(406, "资产状态不符合预期，请检查资产状态");
            }
            propertyMapper.updateById(propertyDO.setStatus(newStatus));
        }
    }

    private String createProcessInstanceAndSaveApprove(Long loginUserId, String businessKey, String operateType, Long relationId) {
        String processInstanceId = UuidUtils.generateUuid();

        PropertyApproveDO approve = new PropertyApproveDO();
        approve.setApproveNumber(processInstanceId);
        approve.setRelationNumber(relationId.toString());
        approve.setRelationType(operateType);
        approve.setStatus(ProptryAuditStatusEnum.STATUS_1.getCode());
        approve.setCuserUid(loginUserId);
        approve.setMuserUid(loginUserId);
        approve.setProcessType(operateType);
        approve.setContent("todo");
        approve.setLaunchTime(LocalDateTime.now());

        propertyApproveMapper.insert(approve);

        return processInstanceId;
    }

    private void createOperateLog(Long loginUserId, String propertyIds, String processInstanceId, String propertyNumber, String operateType, String operateName) {
        propertyOperateLogMapper.insert(new PropertyOperateLogDO()
                .setOperateUid(loginUserId)
                .setPropertyJson(propertyIds)
                .setProcessCode(processInstanceId)
                .setOperateType(propertyNumber)
                .setLogType(operateType)
                .setOperateUser(SecurityFrameworkUtils.getLoginUserNickname())
                .setOperateTime(LocalDateTime.now())
                .setOperateContent("单据编号：" + propertyNumber + operateName)
        );
    }

    @Override
    public PageResult<PropertyRespVO> getPropertyPageByApprove(PropertyApprovePageReqVO pageReqVO) {
        //根据审批单编号查询审批记录
        LambdaQueryWrapper<PropertyApproveDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PropertyApproveDO::getApproveNumber, pageReqVO.getApproveNumber())
                .eq(pageReqVO.getRelationType() != null,PropertyApproveDO::getRelationType, pageReqVO.getRelationType());
        List<PropertyApproveDO> propertyApproves = propertyApproveMapper.selectList(wrapper);
        if(CollUtil.isEmpty( propertyApproves )){
            return PageResult.empty();
        }

        List<String> relationNumbers = propertyApproves.stream()
                .map(PropertyApproveDO::getRelationNumber)
                .collect( Collectors.toList());

        PageResult<PropertyDO> propertyDOPageResult = propertyMapper.selectPage( pageReqVO, new LambdaQueryWrapperX<PropertyDO>()
                .in( PropertyDO::getId, relationNumbers )
                .orderByDesc( PropertyDO::getId ) );

        if(propertyDOPageResult == null || CollUtil.isEmpty( propertyDOPageResult.getList() )){
            return PageResult.empty();
        }
        PageResult<PropertyRespVO> result = BeanUtils.toBean( propertyDOPageResult, PropertyRespVO.class );
        for (PropertyRespVO vo : result.getList()) {
            vo.setDepartmentName( codeGetName.getDeptName(vo.getDepartmentId()) );
        }
        return  result;
    }

    @Override
    public Map<String, Object> getAllProperty(Integer type) {
        List<PropertyDO> propertyList = propertyMapper.selectList();
        // 状态集合定义
        Set<Integer> approvalStatuses = new HashSet<>(Arrays.asList(6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19));

        HashMap<String, Object> map = new HashMap<>(16);

        if(type == 1){
            map.put( "1",propertyList.stream()
                    .filter(p -> p.getStatus() == 1)
                    .count() );

            map.put( "2",propertyList.stream()
                    .filter(p -> p.getStatus() == 2)
                    .count() );

            map.put( "3",propertyList.stream()
                    .filter(p -> p.getStatus() == 3)
                    .count() );

            map.put( "4",propertyList.stream()
                    .filter(p -> approvalStatuses.contains(p.getStatus()))
                    .count() );
        }else {
            map.put( "1",propertyList.stream()
                    .filter(p -> p.getStatus() == 1)
                            .map(PropertyDO::getPurchaseAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add) );

            map.put( "2",propertyList.stream()
                    .filter(p -> p.getStatus() == 2)
                    .map(PropertyDO::getPurchaseAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));

            map.put( "3",propertyList.stream()
                    .filter(p -> p.getStatus() == 3)
                    .map(PropertyDO::getPurchaseAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));

            map.put( "4",propertyList.stream()
                    .filter(p -> approvalStatuses.contains(p.getStatus()))
                    .map(PropertyDO::getPurchaseAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
        }

        return map;
    }

    @Override
    public Map<String, Object> getMyProperty() {
        Map<String, Object> map = new HashMap<>();
        //获取当前登录人
        Long loginUser = SecurityFrameworkUtils.getLoginUserId();
        if(loginUser == null){
            throw new ServiceException(406,"当前登录已过期，请重新登陆");
        }

        //获取我的资产数量
        LambdaQueryWrapperX<PropertyDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eq( PropertyDO::getUserId, loginUser );
        map.put( "myPropertyCount", propertyMapper.selectCount(wrapperX) );
        //获取我的申请的数量
        LambdaQueryWrapperX<PropertyApproveDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq( PropertyApproveDO::getCreator, loginUser);
        map.put( "myApproveCount", propertyApproveMapper.selectCount(queryWrapperX) );
        return map;
    }

    @Override
    public List<PropertyRespVO> returnPropertyPage() {
        //获取当前登录人
        Long loginUser = SecurityFrameworkUtils.getLoginUserId();
        if(loginUser == null){
            throw new ServiceException(406,"当前登录已过期，请重新登陆");
        }
        //查询需要归还的分页
        LambdaQueryWrapperX<PropertyLendoutDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eq( PropertyLendoutDO::getType, 1 )
                .eq( PropertyLendoutDO::getLendUid, loginUser )
                .le( PropertyLendoutDO::getExpectRevertTime, LocalDateTime.now().plusDays(30) );
        List<PropertyLendoutDO> dos = propertyLendoutMapper.selectList( wrapperX );
        String concatenatedPropertyIds = dos.stream()
                .map(PropertyLendoutDO::getPropertyIds) // 获取每个对象的propertyIds
                .filter(Objects::nonNull) // 过滤掉null值
                .collect(Collectors.joining(",")); // 使用逗号拼接
        LambdaQueryWrapperX<PropertyDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.in( PropertyDO::getId, concatenatedPropertyIds )
                .eq( PropertyDO::getStatus, 8 )
                .orderByDesc( PropertyDO::getId );
        List<PropertyDO> propertyDOS = propertyMapper.selectList( queryWrapperX );

        List<PropertyRespVO> propertyRespVOS = BeanUtils.toBean( propertyDOS, PropertyRespVO.class );

        //查询分类名称
        if(CollUtil.isNotEmpty( propertyRespVOS )){
            for (PropertyRespVO property : propertyRespVOS) {
                PropertyCategoryDO propertyCategoryDO = propertyCategoryMapper.selectById(property.getType());
                if (propertyCategoryDO != null) {
                    property.setCategoryName(propertyCategoryDO.getName());
                }
            }
        }
        return propertyRespVOS;
    }
}