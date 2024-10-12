package cn.sdqingyun.smartpark.module.bus.service.org;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgSellerPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgSellerSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgSellerDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 机构楼宇售方信息(发票设置) Service 接口
 *
 * @author 智慧园区
 */
public interface OrgSellerService {

    /**
     * 创建机构楼宇售方信息(发票设置)
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid OrgSellerSaveReqVO createReqVO);

    /**
     * 更新机构楼宇售方信息(发票设置)
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid OrgSellerSaveReqVO updateReqVO);

    /**
     * 删除机构楼宇售方信息(发票设置)
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得机构楼宇售方信息(发票设置)
     *
     * @param id 编号
     * @return 机构楼宇售方信息(发票设置)
     */
    OrgSellerDO get(Long id);

    /**
     * 获得机构楼宇售方信息(发票设置)分页
     *
     * @param pageReqVO 分页查询
     * @return 机构楼宇售方信息(发票设置)分页
     */
    PageResult<OrgSellerDO> getPage(OrgSellerPageReqVO pageReqVO);

    /**
     *
     * @param companyName
     * @param id
     * @return
     */
    Boolean isCheckName(String companyName,Long id);
}