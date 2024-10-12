package cn.sdqingyun.smartpark.module.bus.service.village;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageDrawDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.VillageDrawMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 项目绘制数据 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class VillageDrawServiceImpl implements VillageDrawService {

    @Resource
    private VillageDrawMapper villageDrawMapper;

    @Override
    public Long createVillageDraw(VillageDrawSaveReqVO createReqVO) {
        // 插入
        VillageDrawDO villageDraw = BeanUtils.toBean(createReqVO, VillageDrawDO.class);
        villageDrawMapper.insert(villageDraw);
        // 返回
        return villageDraw.getId();
    }

    @Override
    public void updateVillageDraw(VillageDrawSaveReqVO updateReqVO) {
        // 校验存在
        validateVillageDrawExists(updateReqVO.getId());
        // 更新
        VillageDrawDO updateObj = BeanUtils.toBean(updateReqVO, VillageDrawDO.class);
        villageDrawMapper.updateById(updateObj);
    }

    @Override
    public void deleteVillageDraw(Long id) {
        // 校验存在
        validateVillageDrawExists(id);
        // 删除
        villageDrawMapper.deleteById(id);
    }

    private void validateVillageDrawExists(Long id) {
        if (villageDrawMapper.selectById(id) == null) {
            throw exception(VILLAGE_DRAW_NOT_EXISTS);
        }
    }

    @Override
    public VillageDrawDO getVillageDraw(Long id) {
        return villageDrawMapper.selectById(id);
    }

    @Override
    public PageResult<VillageDrawDO> getVillageDrawPage(VillageDrawPageReqVO pageReqVO) {
        return villageDrawMapper.selectPage(pageReqVO);
    }

}