package cn.sdqingyun.smartpark.module.bus.service.org;


import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgIncomeConfigPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgIncomeConfigSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgIncomeConfigDO;
import jakarta.validation.Valid;

/**
 * 机构收支确认配置 Service 接口
 *
 * @author 智慧园区
 */
public interface OrgIncomeConfigService {

    /**
     * 创建机构收支确认配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid OrgIncomeConfigSaveReqVO createReqVO);

    /**
     * 获得机构收支确认配置分页
     *
     * @return 机构收支确认配置分页
     */
    OrgIncomeConfigDO getOneInfo();

}