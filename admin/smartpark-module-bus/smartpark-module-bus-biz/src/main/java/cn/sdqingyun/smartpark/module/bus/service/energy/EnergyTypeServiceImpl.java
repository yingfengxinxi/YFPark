package cn.sdqingyun.smartpark.module.bus.service.energy;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyTypePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyTypeSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyTypeDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillCostTypeDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.EnergyTypeMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgBillCostTypeMapper;
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

/**
 * 表种类管理 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class EnergyTypeServiceImpl implements EnergyTypeService {

    @Resource
    private EnergyTypeMapper Mapper;

    @Resource
    private OrgBillCostTypeMapper orgBillCostTypeMapper;

    @Override
    public Long create(EnergyTypeSaveReqVO createReqVO) {
        isCheckName(createReqVO);
        // 插入
        EnergyTypeDO energyTypeDO = BeanUtils.toBean(createReqVO, EnergyTypeDO.class);
        Mapper.insert(energyTypeDO);
        // 返回
        return energyTypeDO.getId();
    }

    private void isCheckName(EnergyTypeSaveReqVO energyTypeSaveReqVO) {
        LambdaQueryWrapperX<EnergyTypeDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(EnergyTypeDO::getName, energyTypeSaveReqVO.getName());
        if (energyTypeSaveReqVO.getId() != null) {
            queryWrapperX.apply("id !='" + energyTypeSaveReqVO.getId() + "'");
        }
        int size = Mapper.selectList(queryWrapperX).size();
        if (size >= 1) {
            throw new ServiceException(406, "名称已存在");
        }
    }

    @Override
    public void update(EnergyTypeSaveReqVO updateReqVO) {
        isCheckName(updateReqVO);
        // 更新
        EnergyTypeDO updateObj = BeanUtils.toBean(updateReqVO, EnergyTypeDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void lockout(EnergyTypeSaveReqVO updateReqVO) {
        EnergyTypeDO updateObj = Mapper.selectById(updateReqVO.getId());
        updateObj.setRemindValue(updateReqVO.getRemindValue());
        updateObj.setIsBroken(updateReqVO.getIsBroken());
        if (StringUtils.isNotEmpty(updateReqVO.getCutType())) {
            updateObj.setCutType(updateReqVO.getCutType());
        }

        if (StringUtils.equals(updateReqVO.getIsBroken(), "1")) {
            if (updateReqVO.getOverdueDay() == null) {
                throw new ServiceException(406, "请输入后付费逾期宽限天数");
            }
            updateObj.setOverdueDay(updateReqVO.getOverdueDay());
        } else {
            updateObj.setOverdueDay(0);
        }
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public EnergyTypeDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<EnergyTypeDO> getPage(EnergyTypePageReqVO pageReqVO) {

        LambdaQueryWrapperX<EnergyTypeDO> queryWrapperX = new LambdaQueryWrapperX<>();
        PageResult<EnergyTypeDO> energyTypeDOPageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        List<EnergyTypeDO> list = energyTypeDOPageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(energyTypeDO -> {
                String costType = energyTypeDO.getCostType();
                OrgBillCostTypeDO orgBillCostTypeDO = orgBillCostTypeMapper.selectById(costType);
                if (orgBillCostTypeDO != null) {
                    energyTypeDO.setCostTypeTxt(orgBillCostTypeDO.getName());
                }
            });
        }
        return energyTypeDOPageResult;
    }

}