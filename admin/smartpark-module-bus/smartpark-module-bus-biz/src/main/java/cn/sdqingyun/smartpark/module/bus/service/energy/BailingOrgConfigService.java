package cn.sdqingyun.smartpark.module.bus.service.energy;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.BailingOrgConfigPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.BailingOrgConfigSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.BailingOrgConfigDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 智能表参数配置 Service 接口
 *
 * @author 管理员
 */
public interface BailingOrgConfigService {


    /**
     *
     * @param saveReqVO
     * @return
     */
    Long save(BailingOrgConfigSaveReqVO saveReqVO);


    /**
     * 获得智能表参数配置
     *
     * @param id 编号
     * @return 智能表参数配置
     */
    BailingOrgConfigDO get(Long id);

    /**
     * 获得智能表参数配置
     *
     * @param type 类型
     * @return 智能表参数配置
     */
    BailingOrgConfigDO getByTypeInfo(String type);

}