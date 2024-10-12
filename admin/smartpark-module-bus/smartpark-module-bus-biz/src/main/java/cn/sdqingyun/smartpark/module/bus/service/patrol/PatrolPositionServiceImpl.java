package cn.sdqingyun.smartpark.module.bus.service.patrol;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolPositionPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolPositionSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolFormDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolPositionDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyLocationDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.patrol.PatrolFormMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.patrol.PatrolPositionMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyLocationMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyMapper;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import java.util.List;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PATROL_POSITION_NOT_EXISTS;

/**
 * 巡检点位数据 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PatrolPositionServiceImpl implements PatrolPositionService {

    @Resource
    private PatrolPositionMapper Mapper;

    @Resource
    private PatrolFormMapper patrolFormMapper;

    @Resource
    private PropertyLocationMapper propertyLocationMapper;

    @Override
    public Long create(PatrolPositionSaveReqVO createReqVO) {
        // 插入
        PatrolPositionDO patrolPositionDO = BeanUtils.toBean(createReqVO, PatrolPositionDO.class);
        Mapper.insert(patrolPositionDO);
        // 返回
        return patrolPositionDO.getId();
    }

    @Override
    public void update(PatrolPositionSaveReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        PatrolPositionDO updateObj = BeanUtils.toBean(updateReqVO, PatrolPositionDO.class);
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
            throw exception(PATROL_POSITION_NOT_EXISTS);
        }
    }

    @Override
    public PatrolPositionDO get(Long id) {
        PatrolPositionDO patrolPositionDO = Mapper.selectById(id);
        if (patrolPositionDO != null) {
            extracted(patrolPositionDO);
        }
        return patrolPositionDO;

    }

    @Override
    public PageResult<PatrolPositionDO> getPage(PatrolPositionPageReqVO pageReqVO) {
        LambdaQueryWrapperX<PatrolPositionDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(PatrolPositionDO::getApplication, pageReqVO.getApplication());
        if (StringUtils.isNotEmpty(pageReqVO.getNumber())) {
            queryWrapperX.like(PatrolPositionDO::getNumber, pageReqVO.getNumber());
        }
        if (StringUtils.isNotEmpty(pageReqVO.getName())) {
            queryWrapperX.like(PatrolPositionDO::getName, pageReqVO.getName());
        }
        if (pageReqVO.getPositionId() !=null) {
            queryWrapperX.eq(PatrolPositionDO::getPositionId, pageReqVO.getPositionId());
        }
        queryWrapperX.orderByDesc(PatrolPositionDO::getCreateTime);
        PageResult<PatrolPositionDO> patrolPositionDOPageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        List<PatrolPositionDO> list = patrolPositionDOPageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(patrolPositionDO -> {
                extracted(patrolPositionDO);
            });
        }
        return patrolPositionDOPageResult;
    }

    private void extracted(PatrolPositionDO patrolPositionDO) {
        String positionParentIds = String.valueOf(patrolPositionDO.getPositionId());
        if (StringUtils.isNotEmpty(patrolPositionDO.getPositionParentIds())) {
            positionParentIds = patrolPositionDO.getPositionParentIds() + "," + positionParentIds;
        }
        String[] positionParentIdArray = positionParentIds.split(",");
        StringBuilder stringBuilder = new StringBuilder();
        for (String positionParentId : positionParentIdArray) {
            PropertyLocationDO propertyLocationDO = propertyLocationMapper.selectById(positionParentId);
            if (propertyLocationDO != null) {
                stringBuilder.append(propertyLocationDO.getName()).append(",");
            }
        }
        String positionParentName = stringBuilder.toString();
        if (StringUtils.isNotEmpty(positionParentName)) {
            positionParentName = positionParentName.substring(0, positionParentName.length() - 1);
            patrolPositionDO.setPositionName(positionParentName);
        }
        String statusName = patrolPositionDO.getStatus().equals("1") ? "启用" : "禁用";
        patrolPositionDO.setStatusName(statusName);
        Long formId = patrolPositionDO.getFormId();
        if (formId != null) {
            PatrolFormDO patrolFormDO = patrolFormMapper.selectById(formId);
            if (patrolFormDO != null) {
                patrolPositionDO.setFormTitle(patrolFormDO.getTitle());
            }
        }
    }

}