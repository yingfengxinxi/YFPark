package cn.sdqingyun.smartpark.module.bus.service.owner.impl;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.dict.core.DictFrameworkUtils;
import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.owner.vo.OwnerInfoVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.owner.vo.OwnerPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.owner.vo.OwnerRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.owner.vo.OwnerSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.RoomOwnerListVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.excel.OwnImportExcelVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractSelectedPropertieDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.owner.OwnerDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagHouseDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageUserDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractSelectedPropertieMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.owner.OwnerMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.tag.TagHouseMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.BuildMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.RoomMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.VillageMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.VillageUserMapper;
import cn.sdqingyun.smartpark.module.bus.framework.utils.coverUtils.CoverUtil;
import cn.sdqingyun.smartpark.module.bus.service.owner.OwnerService;
import cn.sdqingyun.smartpark.module.system.api.dept.DeptApi;
import cn.sdqingyun.smartpark.module.system.api.dept.dto.DeptRespDTO;
import cn.sdqingyun.smartpark.module.system.api.permission.PermissionApi;
import cn.sdqingyun.smartpark.module.system.api.user.AdminUserApi;
import cn.sdqingyun.smartpark.module.system.api.user.dto.AdminUserRespDTO;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.OWNER_NOT_EXISTS;

/**
 * 租客信息 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class OwnerServiceImpl implements OwnerService {

    @Resource
    private OwnerMapper Mapper;
    @Resource
    private VillageUserMapper villageUserMapper;
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private VillageMapper villageMapper;
    @Resource
    private BuildMapper buildMapper;

    @Resource
    private ContractMapper contractMapper;

    @Resource
    private ContractSelectedPropertieMapper contractSelectedPropertieMapper;

    @Resource
    private RoomMapper roomMapper;

    @Resource
    private TagHouseMapper tagHouseMapper;
    @Resource
    private DeptApi depApi;
    @Resource
    private PermissionApi permissionApi;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(OwnerSaveReqVO createReqVO) {
        //查询部门信息，如果已有则不允许创建
        CommonResult<DeptRespDTO> deptByName = depApi.getDeptByName( createReqVO.getName() );
        if (deptByName.isSuccess() && deptByName.getData() != null) {
            throw new ServiceException(406, "当前公司名称已存在，不允许再次使用");
        }
        //创建部门信息
        DeptRespDTO deptRespDTO = new DeptRespDTO();
        deptRespDTO.setName( createReqVO.getName() );
        deptRespDTO.setParentId( 0L );
        deptRespDTO.setStatus( 0 );
        CommonResult<Long> dept = depApi.createDept( deptRespDTO );
        if(dept.isError()){
            throw new ServiceException(406, "项目部门创建失败");
        }

        //查询用户信息，如果没有进行创建
        CommonResult<AdminUserRespDTO> userByPhone = adminUserApi.getUserByPhone(createReqVO.getTel());
        Long userId = null;
        if (StringUtils.isEmpty(createReqVO.getContactName()) || StringUtils.isEmpty(createReqVO.getTel())) {
            throw new ServiceException(406, "联系人姓名/电话不能为空");
        }
        if (userByPhone.isSuccess() && userByPhone.getData() == null) {
            //创建用户
            AdminUserRespDTO adminUserRespDTO = new AdminUserRespDTO();
            adminUserRespDTO.setUsername(createReqVO.getTel());
            adminUserRespDTO.setMobile(createReqVO.getTel());
            adminUserRespDTO.setPassword(createReqVO.getTel());
            adminUserRespDTO.setNickname(createReqVO.getContactName());
            adminUserRespDTO.setStatus(1);
            adminUserRespDTO.setDeptId( dept.getData() );
            CommonResult<Long> longCommonResult = adminUserApi.create(adminUserRespDTO);
            if (longCommonResult.isError()) {
                throw new ServiceException(406, "创建用户失败," + longCommonResult.getMsg());
            }
            userId = longCommonResult.getData();
        } else {
            userId = userByPhone.getData().getId();
        }

        //todo :需要修改成配置
        Set<Long> roleIds = new HashSet<>();
        roleIds.add( 163L );
        permissionApi.assignUserRole( userId, roleIds );

        VillageUserDO villageUserDO = new VillageUserDO();
        villageUserDO.setUserId(userId);
        villageUserDO.setName(createReqVO.getContactName());
        villageUserDO.setPhone(createReqVO.getTel());
        villageUserDO.setType(createReqVO.getVillageType());
        villageUserDO.setIdcardType(createReqVO.getIdcardType());
        villageUserDO.setIdcard(createReqVO.getCertificateNumber());
        villageUserDO.setIsDefault(1);
        villageUserDO.setAddress(createReqVO.getAddress());
        villageUserDO.setOrgId( dept.getData() );
        villageUserDO.setStatus( 1 );

        villageUserMapper.insert(villageUserDO);
        // 插入
        OwnerDO ownerDO = BeanUtils.toBean(createReqVO, OwnerDO.class);
        ownerDO.setOrgId( dept.getData() );
        ownerDO.setContactId(villageUserDO.getId());
        Mapper.insert(ownerDO);

        villageUserDO.setOwnerId(ownerDO.getId());
        VillageUserDO villageUser = new VillageUserDO();
        villageUser.setId(villageUserDO.getId());
        villageUser.setOwnerId(ownerDO.getId());
        villageUserMapper.updateById(villageUser);

        // 返回
        return ownerDO.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(OwnerSaveReqVO updateReqVO) {
        // 校验存在
        OwnerDO ownerDO = Mapper.selectById( updateReqVO.getId() );
        if (ownerDO == null) {
            throw exception(OWNER_NOT_EXISTS);
        }

        if(!StringUtils.equals( updateReqVO.getName(),ownerDO.getName() )){
            //修改部门名称
            DeptRespDTO deptRespDTO = new DeptRespDTO();
            deptRespDTO.setName( updateReqVO.getName() );
            deptRespDTO.setId( ownerDO.getOrgId() );
            depApi.updateDept( deptRespDTO );
        }
        // 更新
        OwnerDO updateObj = BeanUtils.toBean(updateReqVO, OwnerDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        OwnerDO ownerDO = Mapper.selectById( id );
        if (ownerDO == null) {
            throw exception(OWNER_NOT_EXISTS);
        }

        if (ownerDO.getIsArchive() == 1) {
            throw new ServiceException(406, "已归档，不能删除");
        }

        if (StringUtils.isNotEmpty( ownerDO.getBuildBind() )) {
            throw new ServiceException(406, "该租客有产生过合同、账单，为了数据完整，无法被删除。");
        }
        // 删除
        Mapper.deleteById(id);
        villageUserMapper.delete(new LambdaQueryWrapperX<VillageUserDO>().eq(VillageUserDO::getOwnerId, id));
    }

    private void validateExists(Long id) {
        if (Mapper.selectById(id) == null) {
            throw exception(OWNER_NOT_EXISTS);
        }
    }

    @Override
    public OwnerDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<OwnerDO> getPage(OwnerPageReqVO pageReqVO) {

        return Mapper.selectPage(pageReqVO);
    }

    @Override
    public List<OwnerDO> getList(OwnerPageReqVO pageReqVO) {

        LambdaQueryWrapperX<OwnerDO> queryWrapperX = new LambdaQueryWrapperX<>();
        if (StringUtils.isNotEmpty(pageReqVO.getName())) {
            queryWrapperX.like(OwnerDO::getName, pageReqVO.getName());
        }
        if (StringUtils.isNotEmpty(pageReqVO.getTel())) {
            queryWrapperX.like(OwnerDO::getTel, pageReqVO.getTel());
        }
        queryWrapperX.orderByDesc(OwnerDO::getCreateTime);
        return Mapper.selectList(queryWrapperX);
    }

    /**
     * @return
     */
    @Override
    public List<OwnerDO> getOwnerList() {
        return Mapper.selectList();
    }

    /**
     * @param ownerName
     * @return
     */
    @Override
    public Long getOwnerNameId(String ownerName) {
        LambdaQueryWrapperX<OwnerDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OwnerDO::getName, ownerName);
        List<OwnerDO> ownerDOS = Mapper.selectList(queryWrapperX);
        if (CollectionUtils.isNotEmpty(ownerDOS)) {
            return ownerDOS.get(0).getId();
        }
        return null;
    }

    @Override
    public Map<String, Object> getCountOwnerMap() {
        Map<String, Object> map = new HashMap<>();
        LambdaQueryWrapperX<OwnerDO> wrapperX = new LambdaQueryWrapperX<>();
        Long selectCount = Mapper.selectCount(wrapperX);
        map.put("total", selectCount);
        wrapperX.eq(OwnerDO::getIsPersonal, 1);
        Long person = Mapper.selectCount(wrapperX);
        map.put("person", person);
        map.put("company", selectCount - person);

        LambdaQueryWrapperX<OwnerDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.isNotNull(OwnerDO::getContactSignId);
        Long contactSign = Mapper.selectCount(queryWrapperX);
        map.put("contactSign", contactSign);
        return map;
    }

    @Override
    public Boolean importExcel(List<OwnImportExcelVO> list, HttpServletResponse response) {
        list.remove(0);
        AtomicReference<Boolean> flag = new AtomicReference<>(true);
        list.forEach(ownImportExcelVO -> {
            StringBuilder builder = new StringBuilder();
            if (StringUtils.isEmpty(ownImportExcelVO.getIsPersonal())) {
                flag.set(false);
                builder.append("租客类型不能为空/");
                throw new ServiceException(406, builder.toString());
            }
            if (StringUtils.isEmpty(ownImportExcelVO.getName())) {
                flag.set(false);
                builder.append("租客名称不能为空/");
                throw new ServiceException(406, builder.toString());
            }
            if (Mapper.selectCount(new LambdaQueryWrapperX<OwnerDO>().eq(OwnerDO::getName, ownImportExcelVO.getName())) > 0) {
                flag.set(false);
                builder.append("租客名称重复/");
                throw new ServiceException(406, builder.toString());
            }
            if (StringUtils.isEmpty(ownImportExcelVO.getContactName())) {
                flag.set(false);
                builder.append("租客联系人不能为空/");
                throw new ServiceException(406, builder.toString());
            }
            if (villageUserMapper.selectCount(new LambdaQueryWrapperX<VillageUserDO>().eq(VillageUserDO::getName, ownImportExcelVO.getContactName())) > 0) {
                flag.set(false);
                builder.append("租客联系人名称重复/");
                throw new ServiceException(406, builder.toString());
            }
            if (StringUtils.isEmpty(ownImportExcelVO.getTel())) {
                flag.set(false);
                builder.append("租客电话不能为空/");
                throw new ServiceException(406, builder.toString());
            }
            if (villageUserMapper.selectCount(new LambdaQueryWrapperX<VillageUserDO>().eq(VillageUserDO::getPhone, ownImportExcelVO.getTel())) > 0) {
                flag.set(false);
                builder.append("租客电话重复/");
                throw new ServiceException(406, builder.toString());
            }
            if (Mapper.selectCount(new LambdaQueryWrapperX<OwnerDO>().eq(OwnerDO::getTenantNo, ownImportExcelVO.getTenantNo())) > 0) {
                flag.set(false);
                builder.append("租客编码重复/");
                throw new ServiceException(406, builder.toString());
            }
            if (villageUserMapper.selectCount(new LambdaQueryWrapperX<VillageUserDO>().eq(VillageUserDO::getIdcard, ownImportExcelVO.getCertificateNumber())) > 0) {
                flag.set(false);
                builder.append("证件号码重复/");
                throw new ServiceException(406, builder.toString());
            }
            ownImportExcelVO.setImportResult(String.valueOf(builder));
        });
        if (flag.get()) {
            list.forEach(ownImportExcelVO -> {
                ownImportExcelVO.setIsPersonal(ownImportExcelVO.getIsPersonal().equals("公司") ? "0" : "1");
                ownImportExcelVO.setIdcardType(CoverUtil.coverIdCardType(ownImportExcelVO.getIdcardType()));
                ownImportExcelVO.setVillageType(CoverUtil.coverVillageType(ownImportExcelVO.getVillageType()));
                create(BeanUtils.toBean(ownImportExcelVO, OwnerSaveReqVO.class));
            });
            return true;
        } else {
            List<OwnImportExcelVO> ownImportExcelVOS = new ArrayList<>();
            OwnImportExcelVO ownImportExcelVO = new OwnImportExcelVO();
            ownImportExcelVO.setIsPersonal("公司/个人（必填）");
            ownImportExcelVO.setName("公司全称/个人姓名（必填）");
            ownImportExcelVO.setContactName("填写联系人名称（必填）");
            ownImportExcelVO.setVillageType("超级管理员");
            ownImportExcelVO.setTel("填写联系人电话（必填）");
            ownImportExcelVO.setIdcardType("证件类型（选填）");
            ownImportExcelVO.setCertificateNumber("填写租客证件号码（选填）");
            ownImportExcelVO.setTenantNo("填写租客编码（选填）");
            ownImportExcelVO.setAddress("填写联系人通讯地址（选填）");
            ownImportExcelVO.setImportResult("非填项，如上传失败请下载反馈文档，该列会予以问题说明");
            ownImportExcelVOS.add(ownImportExcelVO);
            ownImportExcelVOS.addAll(list);
            // 输出
            try {
                ExcelUtils.write(response, "租客列表导入错误.xls", "租客列表", OwnImportExcelVO.class, ownImportExcelVOS);
            } catch (Exception e) {
                throw new ServiceException(406, "租客列表导入错误");
            }
            return false;
        }

    }

    @Override
    public List<Map<String, Object>> getCountOwnerAnnularRing(OwnerSaveReqVO createReqVO) {
        return Mapper.getCountOwnerAnnularRing(createReqVO);
    }

    @Override
    public List<Map<String, Object>> getCountOwnerTagAnnularRing(OwnerSaveReqVO createReqVO) {
        return Mapper.getCountOwnerTagAnnularRing(createReqVO);
    }

    @Override
    public PageResult<OwnerRespVO> getOwnerRespVOPage(OwnerPageReqVO pageReqVO) {
        PageResult<OwnerDO> result = Mapper.selectPage(pageReqVO);
        PageResult<OwnerRespVO> pageResult = BeanUtils.toBean(result, OwnerRespVO.class);
        if (CollectionUtils.isNotEmpty(pageResult.getList())) {
            pageResult.getList().forEach(ownerRespVO -> {
                if (StringUtils.isNotEmpty(ownerRespVO.getBuildBind())) {
                    String[] split = ownerRespVO.getBuildBind().split(",");
                    if (split.length > 0) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (String s : split) {
                            BuildDO buildDO = buildMapper.selectById( s );
                            if (buildDO != null) {
                                stringBuilder.append( buildDO.getBuildName() ).append( "," );
                            }
                        }
                        if (stringBuilder.length() > 0) {
                            ownerRespVO.setBuildBindName( String.valueOf( stringBuilder ).substring( 0, stringBuilder.length() - 1 ) );
                        }
                    }
                }
                if (StringUtils.isNotEmpty(ownerRespVO.getVillageIdList())) {
                    List<String> villageIds = Arrays.asList(ownerRespVO.getVillageIdList().split(","));
                    List<VillageDO> villageDOs = villageMapper.selectBatchIds(villageIds);

                    String villageNames = villageDOs.stream()
                            .map(VillageDO::getName)
                            .collect(Collectors.joining(","));

                    ownerRespVO.setVillageName(villageNames);
                }
                if (ownerRespVO.getContactId() != null) {
                    VillageUserDO villageUserDO = villageUserMapper.selectById(ownerRespVO.getContactId());
                    if (villageUserDO != null) {
                        ownerRespVO.setContactName(villageUserDO.getName());
                    }
                }
            });
        }
        return pageResult;
    }

    @Override
    public List<OwnerDO> getByRoomIdOwnerList(Long roomId) {
        LambdaQueryWrapperX<ContractDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.apply("REGEXP_LIKE(room_number, '(^|,)" + roomId + "($|,)')");
        queryWrapperX.orderByDesc(ContractDO::getCreateTime);
        List<ContractDO> contractDOS = contractMapper.selectList(queryWrapperX);
        if (CollectionUtils.isNotEmpty(contractDOS)) {
            List<Long> ownerIdList = contractDOS.stream().map(ContractDO::getOwnerId).collect(Collectors.toList());
            LambdaQueryWrapperX<OwnerDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
            queryWrapperX1.in(OwnerDO::getId, ownerIdList);
            List<OwnerDO> ownerDOS = Mapper.selectList(queryWrapperX1);
            for (OwnerDO ownerDO : ownerDOS) {
                LambdaQueryWrapperX<ContractDO> queryWrapperX2 = new LambdaQueryWrapperX<>();
                queryWrapperX2.apply("REGEXP_LIKE(room_number, '(^|,)" + roomId + "($|,)')");
                queryWrapperX2.eq(ContractDO::getOwnerId, ownerDO.getId());
                queryWrapperX2.orderByDesc(ContractDO::getCreateTime);
                ContractDO contractDO = contractMapper.selectList(queryWrapperX).get(0);
                ownerDO.setContractId(contractDO.getId());
            }
            return ownerDOS;
        }
        return Lists.newArrayList();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public OwnerInfoVO getByIdOwnerInfo(Long id) {
        OwnerInfoVO ownerInfo = Mapper.getByIdOwnerInfo(id);
        if (ownerInfo != null) {
            String status = ownerInfo.getStatus();
            String dataLabel = DictFrameworkUtils.getDictDataLabel("CONTRACT_STATUS", status);
            ownerInfo.setStatusName(dataLabel);

            LambdaQueryWrapperX<ContractDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.eq(ContractDO::getOwnerId, id);
            List<ContractDO> contractDOS = contractMapper.selectList(queryWrapperX);
            List<Long> contractIdList = contractDOS.stream().map(ContractDO::getId).distinct().collect(Collectors.toList());
            LambdaQueryWrapperX<ContractSelectedPropertieDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
            queryWrapperX1.in(ContractSelectedPropertieDO::getContractId, contractIdList);
            List<ContractSelectedPropertieDO> contractSelectedPropertieDOS = contractSelectedPropertieMapper.selectList(queryWrapperX1);
            List<RoomOwnerListVO> roomOwnerListVOList = Lists.newArrayList();
            if (CollectionUtils.isNotEmpty(contractSelectedPropertieDOS)) {
                contractSelectedPropertieDOS.forEach(contractSelectedPropertieDO -> {
                    RoomOwnerListVO roomOwnerListVO = new RoomOwnerListVO();
                    roomOwnerListVO.setRoomId(contractSelectedPropertieDO.getVillageRoomId());
                    roomOwnerListVO.setContractId(contractSelectedPropertieDO.getContractId());
                    VillageDO villageDO = villageMapper.selectById(contractSelectedPropertieDO.getParkId());
                    if (villageDO != null) {
                        roomOwnerListVO.setParkId(villageDO.getId());
                        roomOwnerListVO.setParkName(villageDO.getName());
                    }

                    BuildDO buildDO = buildMapper.selectById(contractSelectedPropertieDO.getBuildId());
                    if (buildDO != null) {
                        roomOwnerListVO.setBuildName(buildDO.getBuildName());
                        roomOwnerListVO.setBuildId(buildDO.getId());
                    }

                    RoomDO roomDO = roomMapper.selectById(contractSelectedPropertieDO.getVillageRoomId());
                    if (roomDO != null) {
                        String roomName = roomDO.getRoomName();
                        roomOwnerListVO.setRoomName(roomName);
                        String lc = roomName.substring(0, roomName.length() - 2);
                        roomOwnerListVO.setFloor(lc);

                        String tagIdArr = roomDO.getTagIdArr();
                        if (StringUtils.isNotEmpty(tagIdArr)) {
                            tagIdArr = tagIdArr.replaceAll("null", "");
                            tagIdArr = tagIdArr.replaceAll("\\[", "").replaceAll("]", "");
                            StringBuilder sb = new StringBuilder();
                            if (StringUtils.isNotEmpty(tagIdArr)) {
                                String[] split = tagIdArr.split(",");
                                for (String tagHouseId : split) {
                                    TagHouseDO tagHouseDO = tagHouseMapper.selectById(tagHouseId);
                                    sb.append(tagHouseDO.getName()).append(",");
                                }
                            }
                            String tagHouseName = sb.toString();
                            if (StringUtils.isNotEmpty(tagHouseName)) {
                                tagHouseName = tagHouseName.substring(0, tagHouseName.length() - 1);
                                roomOwnerListVO.setTagName(tagHouseName);
                            }
                        } else {
                            roomOwnerListVO.setTagName("");
                        }

                    }
                    roomOwnerListVO.setRentalArea(contractSelectedPropertieDO.getRentalArea());


                    roomOwnerListVOList.add(roomOwnerListVO);
                });
            }

            ownerInfo.setRoomOwnerListVOList(roomOwnerListVOList);
        }
        return ownerInfo;
    }
}