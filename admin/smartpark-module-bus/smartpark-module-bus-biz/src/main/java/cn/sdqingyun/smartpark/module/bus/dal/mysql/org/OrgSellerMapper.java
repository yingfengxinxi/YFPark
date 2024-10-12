package cn.sdqingyun.smartpark.module.bus.dal.mysql.org;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgSellerPageReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgSellerDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 机构楼宇售方信息(发票设置) Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface OrgSellerMapper extends BaseMapperX<OrgSellerDO> {


}