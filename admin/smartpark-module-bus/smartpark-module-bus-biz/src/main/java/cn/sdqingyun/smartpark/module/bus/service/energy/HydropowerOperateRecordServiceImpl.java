package cn.sdqingyun.smartpark.module.bus.service.energy;

import cn.sdqingyun.smartpark.framework.dict.core.DictFrameworkUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.HydropowerOperateRecordPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.HydropowerOperateRecordRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.HydropowerOperateRecordSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.HydropowerOperateRecordDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.HydropowerOperateRecordMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.systemUser.SystemUserMapper;
import com.alibaba.nacos.common.utils.CollectionUtils;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import java.util.List;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 水电表开关记录 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class HydropowerOperateRecordServiceImpl implements HydropowerOperateRecordService {

    @Resource
    private HydropowerOperateRecordMapper Mapper;

    @Resource
    private SystemUserMapper systemUserMapper;

    @Override
    public Long create(HydropowerOperateRecordSaveReqVO createReqVO) {
        // 插入
        HydropowerOperateRecordDO hydropowerOperateRecordDO = BeanUtils.toBean(createReqVO, HydropowerOperateRecordDO.class);
        Mapper.insert(hydropowerOperateRecordDO);
        // 返回
        return hydropowerOperateRecordDO.getId();
    }

    @Override
    public void update(HydropowerOperateRecordSaveReqVO updateReqVO) {

        // 更新
        HydropowerOperateRecordDO updateObj = BeanUtils.toBean(updateReqVO, HydropowerOperateRecordDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public HydropowerOperateRecordDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<HydropowerOperateRecordRespVO> getPage(HydropowerOperateRecordPageReqVO pageReqVO) {
        LambdaQueryWrapperX<HydropowerOperateRecordDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(HydropowerOperateRecordDO::getEnergyId, pageReqVO.getEnergyId());
        queryWrapperX.orderByDesc(HydropowerOperateRecordDO::getCreateTime);
        PageResult<HydropowerOperateRecordDO> pageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        PageResult<HydropowerOperateRecordRespVO> hydropowerOperateRecordRespVOPageResult = BeanUtils.toBean(pageResult, HydropowerOperateRecordRespVO.class);
        List<HydropowerOperateRecordRespVO> list = hydropowerOperateRecordRespVOPageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(hydropowerOperateRecordRespVO -> {
                //0关闸、1开闸 2、重置
                String statusName = DictFrameworkUtils.getDictDataLabel("OPERATE_RECORD_STATUS", hydropowerOperateRecordRespVO.getStatus());
                hydropowerOperateRecordRespVO.setStatusName(statusName);

                String userName = systemUserMapper.getByOperatorIdUserName(hydropowerOperateRecordRespVO.getOperateUid());
                hydropowerOperateRecordRespVO.setOperateName(userName);
            });
        }
        return hydropowerOperateRecordRespVOPageResult;
    }

}