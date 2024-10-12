package cn.sdqingyun.smartpark.module.bus.service.energy;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.util.MyBatisUtils;
import cn.sdqingyun.smartpark.framework.security.core.util.SecurityFrameworkUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyPreRechargeRecordListPageVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.EnergyPreRechargeRecordMapper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;

import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyPreRechargeRecordPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyPreRechargeRecordSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyPreRechargeRecordDO;

import java.util.List;

/**
 * 水电表预充值记录 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class EnergyPreRechargeRecordServiceImpl implements EnergyPreRechargeRecordService {

    @Resource
    private EnergyPreRechargeRecordMapper Mapper;

    @Override
    public Long create(EnergyPreRechargeRecordSaveReqVO createReqVO) {
        // 插入
        EnergyPreRechargeRecordDO energyPreRechargeRecordDO = BeanUtils.toBean(createReqVO, EnergyPreRechargeRecordDO.class);
        Mapper.insert(energyPreRechargeRecordDO);
        // 返回
        return energyPreRechargeRecordDO.getId();
    }

    @Override
    public void update(EnergyPreRechargeRecordSaveReqVO updateReqVO) {

        // 更新
        EnergyPreRechargeRecordDO updateObj = BeanUtils.toBean(updateReqVO, EnergyPreRechargeRecordDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }

    @Override
    public EnergyPreRechargeRecordDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<EnergyPreRechargeRecordListPageVO> getPage(EnergyPreRechargeRecordListPageVO pageReqVO) {
        pageReqVO.setTenantId(SecurityFrameworkUtils.getLoginUser().getTenantId());
        IPage<EnergyPreRechargeRecordListPageVO> listPage = Mapper.getListPage(MyBatisUtils.buildPage(pageReqVO), pageReqVO);
        return new PageResult<>(listPage.getRecords(), listPage.getTotal());
    }

}