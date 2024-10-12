package cn.sdqingyun.smartpark.module.bus.service.energy;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.HardWareConfigPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.HardWareConfigSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.HardWareConfigDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.HardWareConfigMapper;
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
 * 智能硬件配置 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class HardWareConfigServiceImpl implements HardWareConfigService {

    @Resource
    private HardWareConfigMapper Mapper;

    @Override
    public Long create(HardWareConfigSaveReqVO createReqVO) {
        // 插入
        HardWareConfigDO hardWareConfigDO = BeanUtils.toBean(createReqVO, HardWareConfigDO.class);
        Mapper.insert(hardWareConfigDO);
        // 返回
        return hardWareConfigDO.getId();
    }

    @Override
    public void update(HardWareConfigSaveReqVO updateReqVO) {

        // 更新
        HardWareConfigDO updateObj = BeanUtils.toBean(updateReqVO, HardWareConfigDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }



    @Override
    public HardWareConfigDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<HardWareConfigDO> getPage(HardWareConfigPageReqVO pageReqVO) {
        LambdaQueryWrapperX<HardWareConfigDO>queryWrapperX=new LambdaQueryWrapperX<>();
        return Mapper.selectPage(pageReqVO,queryWrapperX);
    }

}