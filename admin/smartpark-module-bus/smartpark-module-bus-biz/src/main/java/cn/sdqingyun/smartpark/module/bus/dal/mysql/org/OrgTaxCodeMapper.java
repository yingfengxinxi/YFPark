package cn.sdqingyun.smartpark.module.bus.dal.mysql.org;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgTaxCodePageReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgTaxCodeDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 税收分类编码配置 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface OrgTaxCodeMapper extends BaseMapperX<OrgTaxCodeDO> {

    default PageResult<OrgTaxCodeDO> selectPage(OrgTaxCodePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OrgTaxCodeDO>()
                .likeIfPresent(OrgTaxCodeDO::getName, reqVO.getName())
                .eqIfPresent(OrgTaxCodeDO::getTaxCode, reqVO.getTaxCode())
                .eqIfPresent(OrgTaxCodeDO::getTaxRate, reqVO.getTaxRate())
                .betweenIfPresent(OrgTaxCodeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OrgTaxCodeDO::getId));
    }

}