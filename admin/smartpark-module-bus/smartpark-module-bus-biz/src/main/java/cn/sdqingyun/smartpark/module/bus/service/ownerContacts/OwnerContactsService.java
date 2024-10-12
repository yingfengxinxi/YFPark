package cn.sdqingyun.smartpark.module.bus.service.ownerContacts;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.ownerContacts.vo.OwnerContactsPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.ownerContacts.vo.OwnerContactsSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.ownerContacts.OwnerContactsDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 租客联系人 Service 接口
 *
 * @author 智慧园区
 */
public interface OwnerContactsService {

    /**
     * 创建租客联系人
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createContacts(@Valid OwnerContactsSaveReqVO createReqVO);

    /**
     * 更新租客联系人
     *
     * @param updateReqVO 更新信息
     */
    void updateContacts(@Valid OwnerContactsSaveReqVO updateReqVO);

    /**
     * 删除租客联系人
     *
     * @param id 编号
     */
    void deleteContacts(Long id);

    /**
     * 获得租客联系人
     *
     * @param id 编号
     * @return 租客联系人
     */
    OwnerContactsDO getContacts(Long id);

    /**
     * 获得租客联系人分页
     *
     * @param pageReqVO 分页查询
     * @return 租客联系人分页
     */
    PageResult<OwnerContactsDO> getContactsPage(OwnerContactsPageReqVO pageReqVO);

}