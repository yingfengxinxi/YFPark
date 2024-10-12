package cn.sdqingyun.smartpark.module.bus.service.park;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo.ParkPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo.ParkSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.park.ParkDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 停车场（一个项目可以有多个场） Service 接口
 *
 * @author 智慧园区
 */
public interface ParkService {

    /**
     * 创建停车场（一个项目可以有多个场）
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid ParkSaveReqVO createReqVO);

    /**
     * 更新停车场（一个项目可以有多个场）
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid ParkSaveReqVO updateReqVO);

    /**
     * 删除停车场（一个项目可以有多个场）
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得停车场（一个项目可以有多个场）
     *
     * @param id 编号
     * @return 停车场（一个项目可以有多个场）
     */
    ParkDO get(Long id);

    /**
     * 获得停车场（一个项目可以有多个场）分页
     *
     * @param pageReqVO 分页查询
     * @return 停车场（一个项目可以有多个场）分页
     */
    PageResult<ParkDO> getPage(ParkPageReqVO pageReqVO);

    /**
     * @param villageId
     * @param parkName
     * @param id
     * @return
     */
    Boolean isCheckName(Long villageId, String parkName, Long id);
}