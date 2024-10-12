package cn.sdqingyun.smartpark.module.bus.service.village;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.IccardDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 项目IC卡表（可能会绑定人员，因不同设备需要而定） Service 接口
 *
 * @author 智慧园区管理员
 */
public interface IccardService {

    /**
     * 创建项目IC卡表（可能会绑定人员，因不同设备需要而定）
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createIccard(@Valid IccardSaveReqVO createReqVO);

    /**
     * 更新项目IC卡表（可能会绑定人员，因不同设备需要而定）
     *
     * @param updateReqVO 更新信息
     */
    void updateIccard(@Valid IccardSaveReqVO updateReqVO);

    /**
     * 删除项目IC卡表（可能会绑定人员，因不同设备需要而定）
     *
     * @param id 编号
     */
    void deleteIccard(Long id);

    /**
     * 获得项目IC卡表（可能会绑定人员，因不同设备需要而定）
     *
     * @param id 编号
     * @return 项目IC卡表（可能会绑定人员，因不同设备需要而定）
     */
    IccardDO getIccard(Long id);

    /**
     * 获得项目IC卡表（可能会绑定人员，因不同设备需要而定）分页
     *
     * @param pageReqVO 分页查询
     * @return 项目IC卡表（可能会绑定人员，因不同设备需要而定）分页
     */
    PageResult<IccardDO> getIccardPage(IccardPageReqVO pageReqVO);

}