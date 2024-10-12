package cn.sdqingyun.smartpark.module.bus.dal.mysql.org;

import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.AdjustBillListVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillAdjustDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
/**
 * 机构账单调整 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface OrgBillAdjustMapper extends BaseMapperX<OrgBillAdjustDO> {


    /**
     *
     * @param page
     * @param adjustBillListVO
     * @return
     */
    IPage<AdjustBillListVO> getAdjustBillList(Page<?> page, @Param("param") AdjustBillListVO adjustBillListVO);

}