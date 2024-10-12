package cn.sdqingyun.smartpark.module.bus.service.energy;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.util.date.DateUtils;
import cn.sdqingyun.smartpark.framework.dict.core.DictFrameworkUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyNotCompleteTaskListVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyRespVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.*;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.BuildMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.RoomMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.VillageMapper;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyTaskPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyTaskSaveReqVO;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义抄表任务 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class EnergyTaskServiceImpl implements EnergyTaskService {

    @Resource
    private EnergyTaskMapper Mapper;

    @Resource
    private EnergyNotCompleteTaskListService energyNotCompleteTaskListService;


    @Override
    public Long create(EnergyTaskSaveReqVO createReqVO) {
        // 插入
        EnergyTaskDO energyTaskDO = BeanUtils.toBean(createReqVO, EnergyTaskDO.class);
        Mapper.insert(energyTaskDO);
        // 返回
        return energyTaskDO.getId();
    }

    @Override
    public void update(EnergyTaskSaveReqVO updateReqVO) {

        // 更新
        EnergyTaskDO updateObj = BeanUtils.toBean(updateReqVO, EnergyTaskDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public EnergyTaskDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<EnergyTaskDO> getPage(EnergyTaskPageReqVO pageReqVO) {
        LambdaQueryWrapperX<EnergyTaskDO> queryWrapperX = new LambdaQueryWrapperX<>();
        return Mapper.selectPage(pageReqVO, queryWrapperX);
    }

    @Override
    public List<EnergyNotCompleteTaskListVO> notCompleteTaskList(Long planId) {
        return energyNotCompleteTaskListService.notCompleteTaskList(planId);
    }


}