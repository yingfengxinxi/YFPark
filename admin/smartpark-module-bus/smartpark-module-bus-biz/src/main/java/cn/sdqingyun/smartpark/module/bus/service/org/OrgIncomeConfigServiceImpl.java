package cn.sdqingyun.smartpark.module.bus.service.org;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.tenant.core.context.TenantContextHolder;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgIncomeConfigPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgIncomeConfigSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgIncomeConfigDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgIncomeConfigMapper;
import com.alibaba.nacos.common.utils.CollectionUtils;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.INCOME_CONFG_NOT_EXISTS;

/**
 * 机构收支确认配置 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
@Transactional
public class OrgIncomeConfigServiceImpl implements OrgIncomeConfigService {

    @Resource
    private OrgIncomeConfigMapper Mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(OrgIncomeConfigSaveReqVO createReqVO) {
        // 插入
        OrgIncomeConfigDO orgIncomeConfigDO = BeanUtils.toBean(createReqVO, OrgIncomeConfigDO.class);
        Mapper.deleteByTenantId(TenantContextHolder.getTenantId());

        Mapper.insert(orgIncomeConfigDO);
        // 返回
        return orgIncomeConfigDO.getId();
    }

    @Override
    public OrgIncomeConfigDO getOneInfo() {
        LambdaQueryWrapperX<OrgIncomeConfigDO> queryWrapperX = new LambdaQueryWrapperX<>();
        List<OrgIncomeConfigDO> orgIncomeConfigDOS = Mapper.selectList(queryWrapperX);
        if (CollectionUtils.isNotEmpty(orgIncomeConfigDOS)) {
            return orgIncomeConfigDOS.get(0);
        }
        return new OrgIncomeConfigDO();
    }

}