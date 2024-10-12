package cn.sdqingyun.smartpark.module.bus.service.tag;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagContractDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.tag.TagContractMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 合同标签 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class TagContractServiceImpl implements TagContractService {

    @Resource
    private TagContractMapper tagContractMapper;

    @Override
    public Long createTagContract(TagContractSaveReqVO createReqVO) {
        // 插入
        TagContractDO tagContract = BeanUtils.toBean(createReqVO, TagContractDO.class);
        tagContractMapper.insert(tagContract);
        // 返回
        return tagContract.getId();
    }

    @Override
    public void updateTagContract(TagContractSaveReqVO updateReqVO) {
        // 校验存在
        validateTagContractExists(updateReqVO.getId());
        // 更新
        TagContractDO updateObj = BeanUtils.toBean(updateReqVO, TagContractDO.class);
        tagContractMapper.updateById(updateObj);
    }

    @Override
    public void deleteTagContract(Long id) {
        // 校验存在
        validateTagContractExists(id);
        // 删除
        tagContractMapper.deleteById(id);
    }

    private void validateTagContractExists(Long id) {
        if (tagContractMapper.selectById(id) == null) {
            throw exception(TAG_CONTRACT_NOT_EXISTS);
        }
    }

    @Override
    public TagContractDO getTagContract(Long id) {
        return tagContractMapper.selectById(id);
    }

    @Override
    public PageResult<TagContractDO> getTagContractPage(TagContractPageReqVO pageReqVO) {
        return tagContractMapper.selectPage(pageReqVO);
    }

}