package cn.sdqingyun.smartpark.module.bus.service.park;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo.ParkChargePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo.ParkChargeSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.park.ParkCarsDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.park.ParkChargeDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.park.ParkCarsMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.park.ParkChargeMapper;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import java.util.List;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PARK_CHARGE_NOT_EXISTS;

/**
 * 停车场收费标准 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class ParkChargeServiceImpl implements ParkChargeService {

    @Resource
    private ParkChargeMapper Mapper;

    @Resource
    private ParkCarsMapper parkCarsMapper;

    @Override
    public Long create(ParkChargeSaveReqVO createReqVO) {
        // 插入
        ParkChargeDO parkChargeDO = BeanUtils.toBean(createReqVO, ParkChargeDO.class);
        if (createReqVO.getIsDefault().equals("1")) {
            extracted(parkChargeDO);
        }


        Mapper.insert(parkChargeDO);
        // 返回
        return parkChargeDO.getId();
    }

    private void extracted(ParkChargeDO parkChargeDO) {
        LambdaQueryWrapperX<ParkChargeDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ParkChargeDO::getParkId, parkChargeDO.getParkId());
        queryWrapperX.eq(ParkChargeDO::getVillageId, parkChargeDO.getVillageId());
        queryWrapperX.eq(ParkChargeDO::getIsDefault, "1");
        List<ParkChargeDO> parkChargeDOS = Mapper.selectList(queryWrapperX);
        if (CollectionUtils.isNotEmpty(parkChargeDOS)) {
            parkChargeDOS.forEach(parkChargeDO1 -> {
                parkChargeDO1.setIsDefault("0");
                Mapper.updateById(parkChargeDO1);
            });
        }
    }

    @Override
    public void update(ParkChargeSaveReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        ParkChargeDO updateObj = BeanUtils.toBean(updateReqVO, ParkChargeDO.class);
        extracted(updateObj);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {
        LambdaQueryWrapperX<ParkCarsDO>queryWrapperX=new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ParkCarsDO::getFeeChargeId,id);
        int size = parkCarsMapper.selectList(queryWrapperX).size();
        if(size>=1){
            throw new ServiceException(406,"该收费规则已绑定了车辆，请先取消绑定再删除！");
        }
        // 校验存在
        validateExists(id);
        // 删除
        Mapper.deleteById(id);
    }

    private void validateExists(Long id) {
        if (Mapper.selectById(id) == null) {
            throw exception(PARK_CHARGE_NOT_EXISTS);
        }
    }

    @Override
    public ParkChargeDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<ParkChargeDO> getPage(ParkChargePageReqVO pageReqVO) {
        LambdaQueryWrapperX<ParkChargeDO> queryWrapperX = new LambdaQueryWrapperX<>();
        if (pageReqVO.getVillageId() != null) {
            queryWrapperX.eq(ParkChargeDO::getVillageId, pageReqVO.getVillageId());
        }
        if (pageReqVO.getParkId() != null) {
            queryWrapperX.eq(ParkChargeDO::getParkId, pageReqVO.getParkId());
        }
        if (StringUtils.isNotEmpty(pageReqVO.getChargeName())) {
            queryWrapperX.like(ParkChargeDO::getChargeName, pageReqVO.getChargeName());
        }
        queryWrapperX.orderByDesc(ParkChargeDO::getCreateTime);
        return Mapper.selectPage(pageReqVO, queryWrapperX);
    }

    @Override
    public Boolean isCheckName(Long parkId, Long villageId, String chargeName, Long id) {
        LambdaQueryWrapperX<ParkChargeDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ParkChargeDO::getParkId, parkId);
        queryWrapperX.eq(ParkChargeDO::getVillageId, villageId);
        queryWrapperX.eq(ParkChargeDO::getChargeName, chargeName);
        if (id != null) {
            queryWrapperX.apply("id!='" + id + "'");
        }
        int size = Mapper.selectList(queryWrapperX).size();
        if (size >= 1) {
            return true;
        }
        return false;
    }

}