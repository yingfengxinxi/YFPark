package cn.sdqingyun.smartpark.module.bus.service.energy;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.BailingOrgConfigPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.BailingOrgConfigSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.BailingOrgConfigDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.BailingOrgConfigMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import java.util.List;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 智能表参数配置 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class BailingOrgConfigServiceImpl implements BailingOrgConfigService {

    @Resource
    private BailingOrgConfigMapper Mapper;


    @Override
    public Long save(BailingOrgConfigSaveReqVO saveReqVO) {
        // 插入
        BailingOrgConfigDO bailingOrgConfigDO = BeanUtils.toBean(saveReqVO, BailingOrgConfigDO.class);

        LambdaQueryWrapperX<BailingOrgConfigDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(BailingOrgConfigDO::getType, saveReqVO.getType());
        BailingOrgConfigDO bailingOrgConfigDOOld = Mapper.selectOne(queryWrapperX);

        if (bailingOrgConfigDOOld != null) {
            //修改数据
            bailingOrgConfigDO.setId(bailingOrgConfigDOOld.getId());
            Mapper.updateById(bailingOrgConfigDO);
        } else {
            Mapper.insert(bailingOrgConfigDO);
        }
        return bailingOrgConfigDO.getId();
    }

    @Override
    public BailingOrgConfigDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public BailingOrgConfigDO getByTypeInfo(String type) {
        LambdaQueryWrapperX<BailingOrgConfigDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(BailingOrgConfigDO::getType, type);
        return Mapper.selectOne(queryWrapperX);
    }

}