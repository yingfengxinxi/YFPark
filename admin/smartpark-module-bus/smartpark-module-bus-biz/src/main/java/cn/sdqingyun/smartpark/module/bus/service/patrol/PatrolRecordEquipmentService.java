package cn.sdqingyun.smartpark.module.bus.service.patrol;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolRecordEquipmentDO;
import com.alibaba.fastjson.JSONObject;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 应用巡检记录 Service 接口
 *
 * @author 管理员
 */
public interface PatrolRecordEquipmentService {

    /**
     * 创建应用巡检记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid PatrolRecordEquipmentSaveReqVO createReqVO);

    /**
     * 更新应用巡检记录
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid PatrolRecordEquipmentSaveReqVO updateReqVO);

    /**
     * 删除应用巡检记录
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得应用巡检记录
     *
     * @param id 编号
     * @return 应用巡检记录
     */
    PatrolRecordEquipmentDO get(Long id);

    /**
     * 获得应用巡检记录分页
     *
     * @param pageReqVO 分页查询
     * @return 应用巡检记录分页
     */
    PageResult<PatrolRecordEquipmentRespVO> getPage(PatrolRecordEquipmentPageReqVO pageReqVO);

    /**
     *
     * @param warnRecordStaticVO
     * @return
     */
    PageResult<WarnRecordStaticVO> warnRecordStatic(WarnRecordStaticVO warnRecordStaticVO);

    /**
     *
     * @param postStationStaticVO
     * @return
     */
    List<PostStationStaticVO> postStationStatic(PostStationStaticVO postStationStaticVO);
}