package cn.sdqingyun.smartpark.module.bus.service.resv;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.ResvApplicationDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.resv.ResvApplicationMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 预约应用 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class ResvApplicationServiceImpl implements ResvApplicationService {

    @Resource
    private ResvApplicationMapper resvApplicationMapper;

    @Override
    public Long createResvApplication(ResvApplicationSaveReqVO createReqVO) {
        // 插入
        ResvApplicationDO resvApplication = BeanUtils.toBean(createReqVO, ResvApplicationDO.class);
        resvApplicationMapper.insert(resvApplication);
        // 返回
        return resvApplication.getId();
    }

    @Override
    public void updateResvApplication(ResvApplicationSaveReqVO updateReqVO) {
        // 校验存在
        validateResvApplicationExists(updateReqVO.getId());
        // 更新
        ResvApplicationDO updateObj = BeanUtils.toBean(updateReqVO, ResvApplicationDO.class);
        resvApplicationMapper.updateById(updateObj);
    }

    @Override
    public void deleteResvApplication(Long id) {
        // 校验存在
        validateResvApplicationExists(id);
        // 删除
        resvApplicationMapper.deleteById(id);
    }

    private void validateResvApplicationExists(Long id) {
        if (resvApplicationMapper.selectById(id) == null) {
            throw exception(RESV_APPLICATION_NOT_EXISTS);
        }
    }

    @Override
    public ResvApplicationDO getResvApplication(Long id) {
        return resvApplicationMapper.selectById(id);
    }

    @Override
    public PageResult<ResvApplicationDO> getResvApplicationPage(ResvApplicationPageReqVO pageReqVO) {
        return resvApplicationMapper.selectPage(pageReqVO);
    }

}