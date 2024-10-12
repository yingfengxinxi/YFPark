package cn.sdqingyun.smartpark.module.bus.service.tag;

import cn.sdqingyun.smartpark.module.bus.service.contract.ContractOrderBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagTerminationDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.tag.TagTerminationMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 退租原因标签 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class TagTerminationServiceImpl implements TagTerminationService {

    @Resource
    private TagTerminationMapper tagTerminationMapper;

    @Override
    public Long create(TagTerminationSaveReqVO createReqVO) {
        // 插入
        TagTerminationDO tagTermination = BeanUtils.toBean(createReqVO, TagTerminationDO.class);
        tagTerminationMapper.insert(tagTermination);
        // 返回
        return tagTermination.getId();
    }

    @Override
    public void update(TagTerminationSaveReqVO updateReqVO) {
        // 校验存在
        validateTagTerminationExists(updateReqVO.getId());
        // 更新
        TagTerminationDO updateObj = BeanUtils.toBean(updateReqVO, TagTerminationDO.class);
        tagTerminationMapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {
        // 校验存在
        validateTagTerminationExists(id);
        // 删除
        tagTerminationMapper.deleteById(id);
    }

    private void validateTagTerminationExists(Long id) {
        if (tagTerminationMapper.selectById(id) == null) {
            throw exception(TAG_TERMINATION_NOT_EXISTS);
        }
    }

    @Override
    public TagTerminationDO get(Long id) {
        return tagTerminationMapper.selectById(id);
    }

    @Override
    public PageResult<TagTerminationDO> getPage(TagTerminationPageReqVO pageReqVO) {
        return tagTerminationMapper.selectPage(pageReqVO);
    }

}