package cn.sdqingyun.smartpark.module.bus.service.tag;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagContractDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 合同标签 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface TagContractService {

    /**
     * 创建合同标签
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTagContract(@Valid TagContractSaveReqVO createReqVO);

    /**
     * 更新合同标签
     *
     * @param updateReqVO 更新信息
     */
    void updateTagContract(@Valid TagContractSaveReqVO updateReqVO);

    /**
     * 删除合同标签
     *
     * @param id 编号
     */
    void deleteTagContract(Long id);

    /**
     * 获得合同标签
     *
     * @param id 编号
     * @return 合同标签
     */
    TagContractDO getTagContract(Long id);

    /**
     * 获得合同标签分页
     *
     * @param pageReqVO 分页查询
     * @return 合同标签分页
     */
    PageResult<TagContractDO> getTagContractPage(TagContractPageReqVO pageReqVO);

}