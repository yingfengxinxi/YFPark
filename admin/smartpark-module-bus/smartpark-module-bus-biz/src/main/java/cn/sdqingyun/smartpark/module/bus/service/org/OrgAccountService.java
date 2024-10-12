package cn.sdqingyun.smartpark.module.bus.service.org;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgAccountPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgAccountRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgAccountSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgAccountDO;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 收支账户配置 Service 接口
 *
 * @author 智慧园区
 */
public interface OrgAccountService {

    /**
     * 创建收支账户配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid OrgAccountSaveReqVO createReqVO);

    /**
     * 更新收支账户配置
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid OrgAccountSaveReqVO updateReqVO);

    /**
     * 删除收支账户配置
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得收支账户配置
     *
     * @param id 编号
     * @return 收支账户配置
     */
    OrgAccountDO get(Long id);

    /**
     * 获得收支账户配置分页
     *
     * @param pageReqVO 分页查询
     * @return 收支账户配置分页
     */
    PageResult<OrgAccountDO> getPage(OrgAccountPageReqVO pageReqVO);


    /**
     *
     * @param build
     * @return
     */
    List<OrgAccountDO> getList(String build);


    /**
     * @param
     * @return
     */
    List<OrgAccountDO> getList();

    /**
     * @param name
     * @param id
     * @return
     */
    Integer getNameCount(String name, Long id);

    PageResult<OrgAccountRespVO> getAccountPage(OrgAccountPageReqVO pageReqVO);
}