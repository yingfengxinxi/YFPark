package cn.sdqingyun.smartpark.module.bus.service.ownerFiles;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.ownerFiles.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.ownerFiles.OwnerFilesDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.ownerFiles.OwnerFilesMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 租客附件 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class OwnerFilesServiceImpl implements OwnerFilesService {

    @Resource
    private OwnerFilesMapper ownerFilesMapper;

    @Override
    public Long createOwnerFiles(OwnerFilesSaveReqVO createReqVO) {
        // 插入
        OwnerFilesDO ownerFiles = BeanUtils.toBean(createReqVO, OwnerFilesDO.class);
        ownerFilesMapper.insert(ownerFiles);
        // 返回
        return ownerFiles.getId();
    }

    @Override
    public void updateOwnerFiles(OwnerFilesSaveReqVO updateReqVO) {
        // 校验存在
        validateOwnerFilesExists(updateReqVO.getId());
        // 更新
        OwnerFilesDO updateObj = BeanUtils.toBean(updateReqVO, OwnerFilesDO.class);
        ownerFilesMapper.updateById(updateObj);
    }

    @Override
    public void deleteOwnerFiles(Long id) {
        // 校验存在
        validateOwnerFilesExists(id);
        // 删除
        ownerFilesMapper.deleteById(id);
    }

    private void validateOwnerFilesExists(Long id) {
        if (ownerFilesMapper.selectById(id) == null) {
            throw exception(OWNER_FILES_NOT_EXISTS);
        }
    }

    @Override
    public OwnerFilesDO getOwnerFiles(Long id) {
        return ownerFilesMapper.selectById(id);
    }

    @Override
    public PageResult<OwnerFilesDO> getOwnerFilesPage(OwnerFilesPageReqVO pageReqVO) {
        return ownerFilesMapper.selectPage(pageReqVO);
    }

}