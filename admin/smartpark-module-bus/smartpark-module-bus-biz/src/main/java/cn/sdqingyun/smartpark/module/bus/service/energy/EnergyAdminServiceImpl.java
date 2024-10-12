package cn.sdqingyun.smartpark.module.bus.service.energy;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyAdminPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyAdminSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyAdminDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolAdminDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.EnergyAdminMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.systemUser.SystemUserMapper;
import com.alibaba.nacos.common.utils.CollectionUtils;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import java.util.List;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
/**
 * 自定义抄表管理员 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class EnergyAdminServiceImpl implements EnergyAdminService {

    @Resource
    private EnergyAdminMapper Mapper;

    @Resource
    private SystemUserMapper systemUserMapper;

    @Override
    public Long create(EnergyAdminSaveReqVO createReqVO) {
        Boolean checkUid = isCheckUid(createReqVO.getUid(), createReqVO.getId());
        if (checkUid) {
            throw new ServiceException(406, "当前用户已存在");
        }
        // 插入
        EnergyAdminDO energyAdminDO = BeanUtils.toBean(createReqVO, EnergyAdminDO.class);
        Mapper.insert(energyAdminDO);
        // 返回
        return energyAdminDO.getId();
    }
    private Boolean isCheckUid(Long uid, Long id) {

        LambdaQueryWrapperX<EnergyAdminDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(EnergyAdminDO::getUid, uid);
        if (id != null) {
            queryWrapperX.apply(" id !='" + id + "'");
        }
        int size = Mapper.selectList(queryWrapperX).size();
        if (size >= 1) {
            return true;
        }
        return false;
    }
    @Override
    public void update(EnergyAdminSaveReqVO updateReqVO) {
        Boolean checkUid = isCheckUid(updateReqVO.getUid(), updateReqVO.getId());
        if (checkUid) {
            throw new ServiceException(406, "当前用户已存在");
        }
        // 更新
        EnergyAdminDO updateObj = BeanUtils.toBean(updateReqVO, EnergyAdminDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {
        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public EnergyAdminDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<EnergyAdminDO> getPage(EnergyAdminPageReqVO pageReqVO) {
        LambdaQueryWrapperX<EnergyAdminDO>queryWrapperX=new LambdaQueryWrapperX<>();
        PageResult<EnergyAdminDO> energyAdminDOPageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        List<EnergyAdminDO> list = energyAdminDOPageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(patrolAdminDO -> {
                Long uid = patrolAdminDO.getUid();
                String userName = systemUserMapper.getByOperatorIdUserName(uid);
                patrolAdminDO.setName(userName);
            });
        }

        return energyAdminDOPageResult;
    }

}