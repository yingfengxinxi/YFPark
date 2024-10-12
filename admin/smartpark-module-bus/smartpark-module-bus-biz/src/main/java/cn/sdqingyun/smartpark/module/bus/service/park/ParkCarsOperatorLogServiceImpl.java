package cn.sdqingyun.smartpark.module.bus.service.park;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo.ParkCarsOperatorLogPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo.ParkCarsOperatorLogSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.park.ParkCarOrderDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.park.ParkCarsOperatorLogDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.park.ParkCarOrderMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.park.ParkCarsOperatorLogMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.systemUser.SystemUserMapper;
import com.alibaba.nacos.common.utils.CollectionUtils;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import java.util.List;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PARK_CARS_OPERATOR_LOG_NOT_EXISTS;

/**
 * 车辆操作日志 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class ParkCarsOperatorLogServiceImpl implements ParkCarsOperatorLogService {

    @Resource
    private ParkCarsOperatorLogMapper Mapper;

    @Resource
    private SystemUserMapper systemUserMapper;

    @Resource
    private ParkCarOrderMapper parkCarOrderMapper;

    @Override
    public Long create(ParkCarsOperatorLogSaveReqVO createReqVO) {
        // 插入
        ParkCarsOperatorLogDO parkCarsOperatorLogDO = BeanUtils.toBean(createReqVO, ParkCarsOperatorLogDO.class);
        Mapper.insert(parkCarsOperatorLogDO);
        // 返回
        return parkCarsOperatorLogDO.getId();
    }

    @Override
    public void update(ParkCarsOperatorLogSaveReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        ParkCarsOperatorLogDO updateObj = BeanUtils.toBean(updateReqVO, ParkCarsOperatorLogDO.class);
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
            throw exception(PARK_CARS_OPERATOR_LOG_NOT_EXISTS);
        }
    }

    @Override
    public ParkCarsOperatorLogDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<ParkCarsOperatorLogDO> getPage(ParkCarsOperatorLogPageReqVO pageReqVO) {
        LambdaQueryWrapperX<ParkCarsOperatorLogDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ParkCarsOperatorLogDO::getCarsId, pageReqVO.getCarsId());
        PageResult<ParkCarsOperatorLogDO> parkCarsOperatorLogDOPageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        List<ParkCarsOperatorLogDO> list = parkCarsOperatorLogDOPageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(parkCarsOperatorLogDO -> {
                String creator = parkCarsOperatorLogDO.getCreator();
                String userName = systemUserMapper.getByOperatorIdUserName(Long.valueOf(creator));
                parkCarsOperatorLogDO.setCreatorName(userName);

                if(parkCarsOperatorLogDO.getType().equals("3")){
                    LambdaQueryWrapperX<ParkCarOrderDO>queryWrapperX1=new LambdaQueryWrapperX<>();
                    queryWrapperX1.eq(ParkCarOrderDO::getOrderId,parkCarsOperatorLogDO.getOrderId());
                    ParkCarOrderDO parkCarOrderDO = parkCarOrderMapper.selectOne(queryWrapperX1);
                    String content=parkCarOrderDO.getOrderName()+"通过"+parkCarOrderDO.getPayMethodTxt()+"方式缴费"+parkCarOrderDO.getOrderMoney()+"元";
                    parkCarsOperatorLogDO.setContent(content);
                }
            });
        }
        return parkCarsOperatorLogDOPageResult;
    }

}