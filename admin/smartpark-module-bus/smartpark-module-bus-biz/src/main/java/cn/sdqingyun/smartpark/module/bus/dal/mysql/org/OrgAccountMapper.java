package cn.sdqingyun.smartpark.module.bus.dal.mysql.org;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgAccountPageReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgAccountDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 收支账户配置 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface OrgAccountMapper extends BaseMapperX<OrgAccountDO> {

    default PageResult<OrgAccountDO> selectPage(OrgAccountPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OrgAccountDO>()
                .likeIfPresent(OrgAccountDO::getName, reqVO.getName())
                .eqIfPresent(OrgAccountDO::getCompany, reqVO.getCompany())
                .eqIfPresent(OrgAccountDO::getBank, reqVO.getBank())
                .eqIfPresent(OrgAccountDO::getBankAccount, reqVO.getBankAccount())
                .eqIfPresent(OrgAccountDO::getSubject, reqVO.getSubject())
                .eqIfPresent(OrgAccountDO::getBuild, reqVO.getBuild())
                .eqIfPresent(OrgAccountDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(OrgAccountDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OrgAccountDO::getId));
    }

}