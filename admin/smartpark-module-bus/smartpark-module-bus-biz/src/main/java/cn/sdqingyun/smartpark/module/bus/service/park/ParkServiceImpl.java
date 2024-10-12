package cn.sdqingyun.smartpark.module.bus.service.park;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo.ParkPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo.ParkSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.park.ParkDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.park.ParkMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PARK_NOT_EXISTS;

/**
 * 停车场（一个项目可以有多个场） Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class ParkServiceImpl implements ParkService {

    @Resource
    private ParkMapper Mapper;

    @Override
    public Long create(ParkSaveReqVO createReqVO) {
        // 插入
        ParkDO parkDO = BeanUtils.toBean(createReqVO, ParkDO.class);
        Mapper.insert(parkDO);
        // 返回
        return parkDO.getId();
    }

    @Override
    public void update(ParkSaveReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        ParkDO updateObj = BeanUtils.toBean(updateReqVO, ParkDO.class);
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
            throw exception(PARK_NOT_EXISTS);
        }
    }

    @Override
    public ParkDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<ParkDO> getPage(ParkPageReqVO pageReqVO) {
        LambdaQueryWrapperX<ParkDO> queryWrapperX = new LambdaQueryWrapperX<>();
        if (pageReqVO.getVillageId() != null) {
            queryWrapperX.eq(ParkDO::getVillageId, pageReqVO.getVillageId());
        }
        queryWrapperX.orderByDesc(ParkDO::getCreateTime);
        return Mapper.selectPage(pageReqVO, queryWrapperX);
    }

    @Override
    public Boolean isCheckName(Long villageId, String parkName, Long id) {
        LambdaQueryWrapperX<ParkDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ParkDO::getVillageId, villageId);
        queryWrapperX.eq(ParkDO::getParkName, parkName);
        if (id != null) {
            queryWrapperX.apply(" id !='" + id + "'");
        }
        List<ParkDO> parkDOS = Mapper.selectList(queryWrapperX);
        if (parkDOS.size() >= 1) {
            return true;
        }
        return false;
    }

}