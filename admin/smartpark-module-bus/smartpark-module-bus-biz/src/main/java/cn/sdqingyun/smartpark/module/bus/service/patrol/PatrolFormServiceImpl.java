package cn.sdqingyun.smartpark.module.bus.service.patrol;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolFormPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolFormSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolFormDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.patrol.PatrolFormMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.systemUser.SystemUserMapper;
import com.alibaba.nacos.common.utils.CollectionUtils;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import java.util.List;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PATROL_FORM_NOT_EXISTS;

/**
 * 巡检表单设置 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PatrolFormServiceImpl implements PatrolFormService {

    @Resource
    private PatrolFormMapper Mapper;

    @Resource
    private SystemUserMapper systemUserMapper;

    @Override
    public Long create(PatrolFormSaveReqVO createReqVO) {
        isCheckTitle(createReqVO.getApplication(),createReqVO.getTitle(), createReqVO.getId());
        // 插入
        PatrolFormDO patrolFormDO = BeanUtils.toBean(createReqVO, PatrolFormDO.class);

        if (createReqVO.getIsDefault().equals("1")) {
            LambdaQueryWrapperX<PatrolFormDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.eq(PatrolFormDO::getIsDefault, "1");
            List<PatrolFormDO> patrolFormDOS = Mapper.selectList(queryWrapperX);
            if (CollectionUtils.isNotEmpty(patrolFormDOS)) {
                patrolFormDOS.forEach(patrolFormDO1 -> patrolFormDO1.setIsDefault("0"));
                Mapper.updateBatch(patrolFormDOS);
            }
        }

        Mapper.insert(patrolFormDO);
        // 返回
        return patrolFormDO.getId();
    }

    private void isCheckTitle(String application,String title, Long id) {

        LambdaQueryWrapperX<PatrolFormDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(PatrolFormDO::getTitle, title);
        queryWrapperX.eq(PatrolFormDO::getApplication, application);
        if (id != null) {
            queryWrapperX.apply("id!='" + id + "'");
        }
        int size = Mapper.selectList(queryWrapperX).size();
        if (size >= 1) {
            throw new ServiceException(406, "当前标题已存在，请勿重复添加");
        }

    }

    @Override
    public void update(PatrolFormSaveReqVO updateReqVO) {
        isCheckTitle(updateReqVO.getApplication(),updateReqVO.getTitle(), updateReqVO.getId());
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        PatrolFormDO updateObj = BeanUtils.toBean(updateReqVO, PatrolFormDO.class);
        if (updateObj.getIsDefault().equals("1")) {
            LambdaQueryWrapperX<PatrolFormDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.eq(PatrolFormDO::getIsDefault, "1");
            List<PatrolFormDO> patrolFormDOS = Mapper.selectList(queryWrapperX);
            if (CollectionUtils.isNotEmpty(patrolFormDOS)) {
                patrolFormDOS.forEach(patrolFormDO1 -> patrolFormDO1.setIsDefault("0"));
                Mapper.updateBatch(patrolFormDOS);
            }
        }
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
            throw exception(PATROL_FORM_NOT_EXISTS);
        }
    }

    @Override
    public PatrolFormDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<PatrolFormDO> getPage(PatrolFormPageReqVO pageReqVO) {
        LambdaQueryWrapperX<PatrolFormDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(PatrolFormDO::getApplication,pageReqVO.getApplication());
        PageResult<PatrolFormDO> patrolFormDOPageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        List<PatrolFormDO> list = patrolFormDOPageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(patrolFormDO -> {
                String creator = patrolFormDO.getCreator();
                String userName = systemUserMapper.getByOperatorIdUserName(Long.valueOf(creator));
                patrolFormDO.setCreator(userName);

            });
        }
        return patrolFormDOPageResult;
    }

    @Override
    public void updateIsDefault(PatrolFormSaveReqVO updateReqVO) {
        if (updateReqVO.getIsDefault().equals("1")) {
            LambdaQueryWrapperX<PatrolFormDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.eq(PatrolFormDO::getIsDefault, "1");
            List<PatrolFormDO> patrolFormDOS = Mapper.selectList(queryWrapperX);
            if (CollectionUtils.isNotEmpty(patrolFormDOS)) {
                patrolFormDOS.forEach(patrolFormDO1 -> patrolFormDO1.setIsDefault("0"));
                Mapper.updateBatch(patrolFormDOS);
            }
        }
        PatrolFormDO updateObj = BeanUtils.toBean(updateReqVO, PatrolFormDO.class);
        Mapper.updateById(updateObj);
    }

}