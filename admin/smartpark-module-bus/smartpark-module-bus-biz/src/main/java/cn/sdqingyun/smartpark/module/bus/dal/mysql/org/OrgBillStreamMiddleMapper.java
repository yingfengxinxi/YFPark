package cn.sdqingyun.smartpark.module.bus.dal.mysql.org;

import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillStreamMiddlePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.StreamIdMatchingListPageVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillStreamMiddleDO;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

/**
 * 机构流水账单中间表【匹配】 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface OrgBillStreamMiddleMapper extends BaseMapperX<OrgBillStreamMiddleDO> {

    /**
     *
     * @param page
     * @param pageReqVO
     * @return
     */
    IPage<StreamIdMatchingListPageVO>getByStreamIdMatchingListPage(Page<?>page,@Param("param") OrgBillStreamMiddlePageReqVO pageReqVO);

}