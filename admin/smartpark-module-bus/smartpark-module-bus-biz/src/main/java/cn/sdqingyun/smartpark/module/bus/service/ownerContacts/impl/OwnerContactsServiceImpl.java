package cn.sdqingyun.smartpark.module.bus.service.ownerContacts.impl;

import cn.sdqingyun.smartpark.module.bus.controller.admin.ownerContacts.vo.OwnerContactsPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.ownerContacts.vo.OwnerContactsSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.ownerContacts.OwnerContactsDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.ownerContacts.OwnerContactsMapper;
import cn.sdqingyun.smartpark.module.bus.service.ownerContacts.OwnerContactsService;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.CONTACTS_NOT_EXISTS;

/**
 * 租客联系人 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class OwnerContactsServiceImpl implements OwnerContactsService {

    @Resource
    private OwnerContactsMapper contactsMapper;

    @Override
    public Long createContacts(OwnerContactsSaveReqVO createReqVO) {
        // 插入
        OwnerContactsDO contacts = BeanUtils.toBean(createReqVO, OwnerContactsDO.class);
        contactsMapper.insert(contacts);
        // 返回
        return contacts.getId();
    }

    @Override
    public void updateContacts(OwnerContactsSaveReqVO updateReqVO) {
        // 校验存在
        validateContactsExists(updateReqVO.getId());
        // 更新
        OwnerContactsDO updateObj = BeanUtils.toBean(updateReqVO, OwnerContactsDO.class);
        contactsMapper.updateById(updateObj);
    }

    @Override
    public void deleteContacts(Long id) {
        // 校验存在
        validateContactsExists(id);
        // 删除
        contactsMapper.deleteById(id);
    }

    private void validateContactsExists(Long id) {
        if (contactsMapper.selectById(id) == null) {
            throw exception(CONTACTS_NOT_EXISTS);
        }
    }

    @Override
    public OwnerContactsDO getContacts(Long id) {
        return contactsMapper.selectById(id);
    }

    @Override
    public PageResult<OwnerContactsDO> getContactsPage(OwnerContactsPageReqVO pageReqVO) {
        return contactsMapper.selectPage(pageReqVO);
    }

}