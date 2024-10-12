package cn.sdqingyun.smartpark.module.bus.service.patrol;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolAdminPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolAdminSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolAdminDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.patrol.PatrolAdminMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.systemUser.SystemUserMapper;
import cn.sdqingyun.smartpark.module.system.api.user.AdminUserApi;
import cn.sdqingyun.smartpark.module.system.api.user.dto.AdminUserRespDTO;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PATROL_ADMIN_NOT_EXISTS;

/**
 * 资产管理子应用管理员配置 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PatrolAdminServiceImpl implements PatrolAdminService {

    @Resource
    private PatrolAdminMapper Mapper;

    @Resource
    private SystemUserMapper systemUserMapper;

    @Override
    public Long create(PatrolAdminSaveReqVO createReqVO) {
        Boolean checkUid = isCheckUid(createReqVO.getApplication(), createReqVO.getUid(), createReqVO.getId());
        if (checkUid) {
            throw new ServiceException(406, "当前用户已存在");
        }
        // 插入
        PatrolAdminDO patrolAdminDO = BeanUtils.toBean(createReqVO, PatrolAdminDO.class);
        Mapper.insert(patrolAdminDO);
        // 返回
        return patrolAdminDO.getId();
    }

    private Boolean isCheckUid(String application, Long uid, Long id) {

        LambdaQueryWrapperX<PatrolAdminDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(PatrolAdminDO::getUid, uid);
        queryWrapperX.eq(PatrolAdminDO::getApplication, application);
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
    public void update(PatrolAdminSaveReqVO updateReqVO) {
        Boolean checkUid = isCheckUid(updateReqVO.getApplication(), updateReqVO.getUid(), updateReqVO.getId());
        if (checkUid) {
            throw new ServiceException(406, "当前用户已存在");
        }
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        PatrolAdminDO updateObj = BeanUtils.toBean(updateReqVO, PatrolAdminDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {
        // 校验存在
        validateExists(id);
        // 删除
        Mapper.deleteById(id);
    }

    private void validateExists(Long id) {
        if (Mapper.selectById(id) == null) {
            throw exception(PATROL_ADMIN_NOT_EXISTS);
        }
    }

    @Override
    public PatrolAdminDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<PatrolAdminDO> getPage(PatrolAdminPageReqVO pageReqVO) {
        LambdaQueryWrapperX<PatrolAdminDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(PatrolAdminDO::getApplication, pageReqVO.getApplication());
        if (StringUtils.isNotEmpty(pageReqVO.getName())) {
            queryWrapperX.like(PatrolAdminDO::getName, pageReqVO.getName());
        }
        queryWrapperX.orderByDesc(PatrolAdminDO::getCreateTime);
        PageResult<PatrolAdminDO> patrolAdminDOPageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        List<PatrolAdminDO> list = patrolAdminDOPageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(patrolAdminDO -> {
                Long uid = patrolAdminDO.getUid();
                String userName = systemUserMapper.getByOperatorIdUserName(uid);
                patrolAdminDO.setName(userName);
            });
        }
        return patrolAdminDOPageResult;
    }
}