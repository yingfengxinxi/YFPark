package cn.sdqingyun.smartpark.module.bus.service.village;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.IccardDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.IccardMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 项目IC卡表（可能会绑定人员，因不同设备需要而定） Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class IccardServiceImpl implements IccardService {

    @Resource
    private IccardMapper iccardMapper;

    @Override
    public Long createIccard(IccardSaveReqVO createReqVO) {
        // 插入
        IccardDO iccard = BeanUtils.toBean(createReqVO, IccardDO.class);
        iccardMapper.insert(iccard);
        // 返回
        return iccard.getId();
    }

    @Override
    public void updateIccard(IccardSaveReqVO updateReqVO) {
        // 校验存在
        validateIccardExists(updateReqVO.getId());
        // 更新
        IccardDO updateObj = BeanUtils.toBean(updateReqVO, IccardDO.class);
        iccardMapper.updateById(updateObj);
    }

    @Override
    public void deleteIccard(Long id) {
        // 校验存在
        validateIccardExists(id);
        // 删除
        iccardMapper.deleteById(id);
    }

    private void validateIccardExists(Long id) {
        if (iccardMapper.selectById(id) == null) {
            throw exception(ICCARD_NOT_EXISTS);
        }
    }

    @Override
    public IccardDO getIccard(Long id) {
        return iccardMapper.selectById(id);
    }

    @Override
    public PageResult<IccardDO> getIccardPage(IccardPageReqVO pageReqVO) {
        return iccardMapper.selectPage(pageReqVO);
    }

}