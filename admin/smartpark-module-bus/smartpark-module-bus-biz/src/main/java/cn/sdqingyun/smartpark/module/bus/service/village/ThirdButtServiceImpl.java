package cn.sdqingyun.smartpark.module.bus.service.village;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.ThirdButtDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.ThirdButtMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 第三方数据对接（目前用于智慧社区系统，全功能版） Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class ThirdButtServiceImpl implements ThirdButtService {

    @Resource
    private ThirdButtMapper thirdButtMapper;

    @Override
    public Long createThirdButt(ThirdButtSaveReqVO createReqVO) {
        // 插入
        ThirdButtDO thirdButt = BeanUtils.toBean(createReqVO, ThirdButtDO.class);
        thirdButtMapper.insert(thirdButt);
        // 返回
        return thirdButt.getId();
    }

    @Override
    public void updateThirdButt(ThirdButtSaveReqVO updateReqVO) {
        // 校验存在
        validateThirdButtExists(updateReqVO.getId());
        // 更新
        ThirdButtDO updateObj = BeanUtils.toBean(updateReqVO, ThirdButtDO.class);
        thirdButtMapper.updateById(updateObj);
    }

    @Override
    public void deleteThirdButt(Long id) {
        // 校验存在
        validateThirdButtExists(id);
        // 删除
        thirdButtMapper.deleteById(id);
    }

    private void validateThirdButtExists(Long id) {
        if (thirdButtMapper.selectById(id) == null) {
            throw exception(THIRD_BUTT_NOT_EXISTS);
        }
    }

    @Override
    public ThirdButtDO getThirdButt(Long id) {
        return thirdButtMapper.selectById(id);
    }

    @Override
    public PageResult<ThirdButtDO> getThirdButtPage(ThirdButtPageReqVO pageReqVO) {
        return thirdButtMapper.selectPage(pageReqVO);
    }

}