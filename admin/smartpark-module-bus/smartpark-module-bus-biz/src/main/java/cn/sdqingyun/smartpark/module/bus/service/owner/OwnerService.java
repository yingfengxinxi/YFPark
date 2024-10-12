package cn.sdqingyun.smartpark.module.bus.service.owner;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.module.bus.controller.admin.owner.vo.OwnerInfoVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.owner.vo.OwnerPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.owner.vo.OwnerRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.owner.vo.OwnerSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.excel.OwnImportExcelVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.owner.OwnerDO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 租客信息 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface OwnerService {

    /**
     * 创建租客信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid OwnerSaveReqVO createReqVO);

    /**
     * 更新租客信息
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid OwnerSaveReqVO updateReqVO);

    /**
     * 删除租客信息
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得租客信息
     *
     * @param id 编号
     * @return 租客信息
     */
    OwnerDO get(Long id);

    /**
     * 获得租客信息分页
     *
     * @param pageReqVO 分页查询
     * @return 租客信息分页
     */
    PageResult<OwnerDO> getPage(OwnerPageReqVO pageReqVO);


    List<OwnerDO> getList(OwnerPageReqVO pageReqVO);


    /**
     * 租客下拉
     *
     * @return
     */
    public List<OwnerDO> getOwnerList();

    /**
     *
     * @param ownerName
     * @return
     */
    public Long getOwnerNameId(String ownerName);

    /**
    * @Author SUNk
    * @Description 获取租客列表信息统计
    * @Date 17:59 2024/7/8
    * @Param []
    * @return java.util.Map<java.lang.String,java.lang.Object>
    **/
    Map<String,Object> getCountOwnerMap();

    /**
    * @Author SUNk
    * @Description 导入租客信息
    * @Date 19:19 2024/7/8
    * @Param [list]
    * @return java.lang.Boolean
    **/
    Boolean importExcel(List<OwnImportExcelVO> list, HttpServletResponse response);

    /**
    * @Author SUNk
    * @Description 租客行业环形图
    * @Date 14:46 2024/7/10
    * @Param [createReqVO]
    * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    List<Map<String, Object>> getCountOwnerAnnularRing(OwnerSaveReqVO createReqVO);

    /**
    * @Author SUNk
    * @Description 租客标签环形图
    * @Date 14:46 2024/7/10
    * @Param [createReqVO]
    * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    List<Map<String, Object>> getCountOwnerTagAnnularRing(OwnerSaveReqVO createReqVO);

    /**
     * 获得租客信息分页
     *
     * @param pageReqVO 分页查询
     * @return 租客信息分页
     */
    PageResult<OwnerRespVO> getOwnerRespVOPage(OwnerPageReqVO pageReqVO);

    /**
     *
     * @param roomId
     * @return
     */
    List<OwnerDO> getByRoomIdOwnerList(Long roomId);

    /**
     *
     * @param id
     * @return
     */
    OwnerInfoVO getByIdOwnerInfo(Long id);
}