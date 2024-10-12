package cn.sdqingyun.smartpark.module.bus.service.village;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.ZoneDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.ZoneMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 项目分区 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class ZoneServiceImpl implements ZoneService {

    @Resource
    private ZoneMapper zoneMapper;

    @Override
    public Long createZone(ZoneSaveReqVO createReqVO) {
        // 插入
        ZoneDO zone = BeanUtils.toBean(createReqVO, ZoneDO.class);
        zoneMapper.insert(zone);
        // 返回
        return zone.getId();
    }

    @Override
    public void updateZone(ZoneSaveReqVO updateReqVO) {
        // 校验存在
        validateZoneExists(updateReqVO.getId());
        // 更新
        ZoneDO updateObj = BeanUtils.toBean(updateReqVO, ZoneDO.class);
        zoneMapper.updateById(updateObj);
    }

    @Override
    public void deleteZone(Long id) {
        // 校验存在
        validateZoneExists(id);
        // 删除
        zoneMapper.deleteById(id);
    }

    private void validateZoneExists(Long id) {
        if (zoneMapper.selectById(id) == null) {
            throw exception(ZONE_NOT_EXISTS);
        }
    }

    @Override
    public ZoneDO getZone(Long id) {
        return zoneMapper.selectById(id);
    }

    @Override
    public PageResult<ZoneDO> getZonePage(ZonePageReqVO pageReqVO) {
        return zoneMapper.selectPage(pageReqVO);
    }

}