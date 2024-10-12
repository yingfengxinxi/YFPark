package cn.sdqingyun.smartpark.module.bus.service.patrol;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolTagPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolTagSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolTagDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.patrol.PatrolTagMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
/**
 * 巡检标签模板 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PatrolTagServiceImpl implements PatrolTagService {

    @Resource
    private PatrolTagMapper Mapper;

    @Override
    public Long create(PatrolTagSaveReqVO createReqVO) {
        // 插入
        PatrolTagDO patrolTagDO = BeanUtils.toBean(createReqVO, PatrolTagDO.class);
        LambdaQueryWrapperX<PatrolTagDO>queryWrapperX=new LambdaQueryWrapperX<>();
        queryWrapperX.eq(PatrolTagDO::getApplication,createReqVO.getApplication());
        Mapper.delete(queryWrapperX);

        Mapper.insert(patrolTagDO);
        // 返回
        return patrolTagDO.getId();
    }

    @Override
    public void update(PatrolTagSaveReqVO updateReqVO) {

        // 更新
        PatrolTagDO updateObj = BeanUtils.toBean(updateReqVO, PatrolTagDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public PatrolTagDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public List<PatrolTagDO> getList(String application) {

        LambdaQueryWrapperX<PatrolTagDO>queryWrapperX=new LambdaQueryWrapperX();
        queryWrapperX.eq(PatrolTagDO::getApplication,application);
        return Mapper.selectList(queryWrapperX);
    }

}