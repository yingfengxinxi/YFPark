package cn.sdqingyun.smartpark.module.bus.service.energy;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.HengHanEnergyOprMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.HengHanEnergyOprPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.HengHanEnergyOprSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.HengHanEnergyOprDO;
/**
 * 电表操作id关联表、回调用 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class HengHanEnergyOprServiceImpl implements HengHanEnergyOprService {

    @Resource
    private HengHanEnergyOprMapper Mapper;

    @Override
    public Long create(HengHanEnergyOprSaveReqVO createReqVO) {
        // 插入
        HengHanEnergyOprDO hengHanEnergyOprDO = BeanUtils.toBean(createReqVO, HengHanEnergyOprDO.class);
        Mapper.insert(hengHanEnergyOprDO);
        // 返回
        return hengHanEnergyOprDO.getId();
    }

    @Override
    public void update(HengHanEnergyOprSaveReqVO updateReqVO) {

        // 更新
        HengHanEnergyOprDO updateObj = BeanUtils.toBean(updateReqVO, HengHanEnergyOprDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public HengHanEnergyOprDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<HengHanEnergyOprDO> getPage(HengHanEnergyOprPageReqVO pageReqVO) {
        LambdaQueryWrapperX<HengHanEnergyOprDO>queryWrapperX=new LambdaQueryWrapperX<>();
        return Mapper.selectPage(pageReqVO,queryWrapperX);
    }

}