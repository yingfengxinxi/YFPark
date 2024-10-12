package cn.sdqingyun.smartpark.module.bus.service.energy;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.HengHanEnergyCollectorMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.HengHanEnergyCollectorPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.HengHanEnergyCollectorSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.HengHanEnergyCollectorDO;
/**
 * 采集器电表关联表（一对多的关系） Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class HengHanEnergyCollectorServiceImpl implements HengHanEnergyCollectorService {

    @Resource
    private HengHanEnergyCollectorMapper Mapper;

    @Override
    public Long create(HengHanEnergyCollectorSaveReqVO createReqVO) {
        // 插入
        HengHanEnergyCollectorDO hengHanEnergyCollectorDO = BeanUtils.toBean(createReqVO, HengHanEnergyCollectorDO.class);
        Mapper.insert(hengHanEnergyCollectorDO);
        // 返回
        return hengHanEnergyCollectorDO.getId();
    }

    @Override
    public void update(HengHanEnergyCollectorSaveReqVO updateReqVO) {

        // 更新
        HengHanEnergyCollectorDO updateObj = BeanUtils.toBean(updateReqVO, HengHanEnergyCollectorDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public HengHanEnergyCollectorDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<HengHanEnergyCollectorDO> getPage(HengHanEnergyCollectorPageReqVO pageReqVO) {
        LambdaQueryWrapperX<HengHanEnergyCollectorDO>queryWrapperX=new LambdaQueryWrapperX<>();
        return Mapper.selectPage(pageReqVO,queryWrapperX);
    }

}