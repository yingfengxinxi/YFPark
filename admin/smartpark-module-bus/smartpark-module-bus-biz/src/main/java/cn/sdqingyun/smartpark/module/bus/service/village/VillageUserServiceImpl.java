package cn.sdqingyun.smartpark.module.bus.service.village;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.framework.common.enums.CommonStatusEnum;
import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillageUserPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillageUserRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillageUserSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.excel.VillageUserImportExcelVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.owner.OwnerDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageUserDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.owner.OwnerMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.VillageUserMapper;
import cn.sdqingyun.smartpark.module.bus.framework.utils.coverUtils.CoverUtil;
import cn.sdqingyun.smartpark.module.system.api.permission.PermissionApi;
import cn.sdqingyun.smartpark.module.system.api.user.AdminUserApi;
import cn.sdqingyun.smartpark.module.system.api.user.dto.AdminUserRespDTO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.VILLAGE_USER_NOT_EXISTS;

/**
 * 项目用户/租客 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class VillageUserServiceImpl implements VillageUserService {

    @Resource
    private VillageUserMapper villageUserMapper;
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private OwnerMapper ownerMapper;
    @Resource
    private PermissionApi permissionApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createVillageUser(VillageUserSaveReqVO createReqVO) {
        //查询所属租客，如果没有相关租客则报错不允许创建员工
        OwnerDO ownerDO = ownerMapper.selectById( createReqVO.getOwnerId() );
        if (ownerDO == null){
            throw new ServiceException(406, "租客不存在,请检查租客名称是否正确");
        }

        createReqVO.setOrgId( ownerDO.getOrgId() );
        //创建用户
        Long user = createUser( createReqVO );

        // 插入
        VillageUserDO villageUser = BeanUtils.toBean(createReqVO, VillageUserDO.class);
        villageUser.setUserId( user );
        villageUserMapper.insert(villageUser);
        // 返回
        return villageUser.getId();
    }

    private Long createUser(VillageUserSaveReqVO createReqVO){
        //查询用户信息，如果没有进行创建
        CommonResult<AdminUserRespDTO> userByPhone = adminUserApi.getUserByPhone( createReqVO.getPhone() );

        if (userByPhone.isSuccess() && userByPhone.getData() == null){
            //创建用户
            AdminUserRespDTO adminUserRespDTO = new AdminUserRespDTO();
            adminUserRespDTO.setUsername( createReqVO.getPhone() );
            adminUserRespDTO.setMobile( createReqVO.getPhone() );
            adminUserRespDTO.setPassword( createReqVO.getPhone() );
            adminUserRespDTO.setNickname( createReqVO.getName() );
            adminUserRespDTO.setStatus( CommonStatusEnum.ENABLE.getStatus() );
            adminUserRespDTO.setDeptId( createReqVO.getOrgId() );
            CommonResult<Long> longCommonResult = adminUserApi.create( adminUserRespDTO );
            if (longCommonResult.isError()){
                throw new ServiceException(406, "创建用户失败,"+longCommonResult.getMsg());
            }

            //todo :需要修改成配置
            Set<Long> roleIds = new HashSet<>();
            roleIds.add( 164L );
            permissionApi.assignUserRole( longCommonResult.getData(),roleIds  );
            return longCommonResult.getData();
        }else {
            return userByPhone.getData().getId();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateVillageUser(VillageUserSaveReqVO updateReqVO) {
        // 校验存在
        validateVillageUserExists(updateReqVO.getId());

        // 更新
        VillageUserDO updateObj = BeanUtils.toBean(updateReqVO, VillageUserDO.class);
        villageUserMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteVillageUser(Long id) {
        // 校验存在
        validateVillageUserExists(id);
        // 删除
        villageUserMapper.deleteById(id);
    }

    private void validateVillageUserExists(Long id) {
        if (villageUserMapper.selectById(id) == null) {
            throw exception(VILLAGE_USER_NOT_EXISTS);
        }
    }

    @Override
    public VillageUserDO getVillageUser(Long id) {
        return villageUserMapper.selectById(id);
    }

    @Override
    public PageResult<VillageUserDO> getVillageUserPage(VillageUserPageReqVO pageReqVO) {
        return villageUserMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean importExcel(List<VillageUserImportExcelVO> list, HttpServletResponse response){
        AtomicReference<Boolean> flag = new AtomicReference<>( true );
        list.forEach(item -> {
            StringBuilder builder = new StringBuilder();
            if(StringUtils.isBlank(item.getOwnerName())){
                flag.set( false );
                builder.append( "租客名称不能为空/" );
                throw new ServiceException( 406, builder.toString() );
            }
            OwnerDO ownerDO = ownerMapper.selectOne( new LambdaQueryWrapperX<OwnerDO>().eq( OwnerDO::getName, item.getOwnerName() ) );
            if(ownerDO == null){
                flag.set( false );
                builder.append( "租客不存在,请检查租客名称是否正确/" );
                throw new ServiceException( 406, builder.toString() );
            }
            if(StringUtils.isBlank(item.getType())){
                flag.set( false );
                builder.append( "员工身份不能为空/" );
                throw new ServiceException( 406, builder.toString() );
            }
            if(StringUtils.isBlank(item.getName())){
                flag.set( false );
                builder.append( "员工姓名不能为空/" );
                throw new ServiceException( 406, builder.toString() );
            }
            if(villageUserMapper.selectCount(new LambdaQueryWrapperX<VillageUserDO>().eq( VillageUserDO::getName,item.getName() ) ) > 0){
                flag.set( false );
                builder.append( "员工姓名已存在/" );
                throw new ServiceException( 406, builder.toString() );
            }
            if(StringUtils.isBlank(item.getPhone())){
                flag.set( false );
                builder.append( "员工电话不能为空/" );
                throw new ServiceException( 406, builder.toString() );
            }
            if(villageUserMapper.selectCount(new LambdaQueryWrapperX<VillageUserDO>().eq( VillageUserDO::getPhone,item.getPhone() ) ) > 0){
                flag.set( false );
                builder.append( "员工电话已存在/" );
                throw new ServiceException( 406, builder.toString() );
            }
            item.setImportResult( String.valueOf( builder ) );
        });
        if(flag.get()){
            list.forEach(item -> {
                //根据名称查询ID
                item.setType( CoverUtil.coverVillageType( item.getType() ) );
                item.setIdcardType( CoverUtil.coverIdCardType( item.getIdcardType() ) );
                item.setSex( CoverUtil.coverVillageSex( item.getSex() ) );
                VillageUserSaveReqVO villageUserDO = BeanUtils.toBean( item, VillageUserSaveReqVO.class );
                OwnerDO ownerDO = ownerMapper.selectOne( new LambdaQueryWrapperX<OwnerDO>().eq( OwnerDO::getName, item.getOwnerName() ) );
                villageUserDO.setOwnerId( ownerDO.getId() );
                createVillageUser( villageUserDO );
            });
            return true;
        }else{
            try {
                ExcelUtils.write(response, "租客员工列表导入错误.xls", "租客员工列表", VillageUserImportExcelVO.class, list);
            }catch (Exception e){
                throw new ServiceException( 406, "租客员工列表导入错误" );
            }
            return false;
        }
    }

    @Override
    public PageResult<VillageUserRespVO> getVillageUserPageVO(VillageUserPageReqVO pageReqVO) {
        PageResult<VillageUserDO> result = villageUserMapper.selectPage( pageReqVO );
        PageResult<VillageUserRespVO> pageResult = BeanUtils.toBean( result, VillageUserRespVO.class );
        if(pageResult == null || CollUtil.isEmpty( pageResult.getList() )){
            return pageResult;
        }
        for (VillageUserRespVO vill : pageResult.getList()) {
            if(vill.getOwnerId() != null){
                OwnerDO ownerDO = ownerMapper.selectById( vill.getOwnerId() );
                if(ownerDO != null){
                    vill.setOwnerName( ownerDO.getName() );
                }
            }
        }
        return pageResult;
    }

    @Override
    public List<VillageUserDO> getByPhoneOwnerList(String phone) {

        return villageUserMapper.getByPhoneOwnerList(phone);
    }
}