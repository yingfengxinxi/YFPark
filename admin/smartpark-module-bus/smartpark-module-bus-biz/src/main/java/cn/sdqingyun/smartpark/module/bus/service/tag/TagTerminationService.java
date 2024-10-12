package cn.sdqingyun.smartpark.module.bus.service.tag;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagTerminationDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 退租原因标签 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface TagTerminationService {

    /**
     * 创建退租原因标签
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid TagTerminationSaveReqVO createReqVO);

    /**
     * 更新退租原因标签
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid TagTerminationSaveReqVO updateReqVO);

    /**
     * 删除退租原因标签
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得退租原因标签
     *
     * @param id 编号
     * @return 退租原因标签
     */
    TagTerminationDO get(Long id);

    /**
     * 获得退租原因标签分页
     *
     * @param pageReqVO 分页查询
     * @return 退租原因标签分页
     */
    PageResult<TagTerminationDO> getPage(TagTerminationPageReqVO pageReqVO);

}