package cn.sdqingyun.smartpark.module.bus.service.org;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgTaxCodePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgTaxCodeSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgTaxCodeDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 税收分类编码配置 Service 接口
 *
 * @author 智慧园区
 */
public interface OrgTaxCodeService {

    /**
     * 创建税收分类编码配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid OrgTaxCodeSaveReqVO createReqVO);

    /**
     * 更新税收分类编码配置
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid OrgTaxCodeSaveReqVO updateReqVO);

    /**
     * 删除税收分类编码配置
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得税收分类编码配置
     *
     * @param id 编号
     * @return 税收分类编码配置
     */
    OrgTaxCodeDO get(Long id);

    /**
     * 获得税收分类编码配置分页
     *
     * @param pageReqVO 分页查询
     * @return 税收分类编码配置分页
     */
    PageResult<OrgTaxCodeDO> getPage(OrgTaxCodePageReqVO pageReqVO);


    /**
     * @param taxCode
     * @param id
     * @return
     */
    Boolean isCheckTaxCode(String taxCode, Long id);

}