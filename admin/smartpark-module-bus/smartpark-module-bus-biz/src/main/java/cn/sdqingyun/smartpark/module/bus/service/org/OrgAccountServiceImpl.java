package cn.sdqingyun.smartpark.module.bus.service.org;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgAccountPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgAccountRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgAccountSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgAccountDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgAccountMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.BuildMapper;
import com.alibaba.nacos.common.utils.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.ORG_ACCOUNT_DO_NOT_EXISTS;

/**
 * 收支账户配置 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class OrgAccountServiceImpl implements OrgAccountService {

    @Resource
    private OrgAccountMapper Mapper;
    @Resource
    private BuildMapper buildMapper;

    @Override
    public Long create(OrgAccountSaveReqVO createReqVO) {
        // 插入
        OrgAccountDO orgAccountDO = BeanUtils.toBean(createReqVO, OrgAccountDO.class);
        Mapper.insert(orgAccountDO);
        // 返回
        return orgAccountDO.getId();
    }

    @Override
    public void update(OrgAccountSaveReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        OrgAccountDO updateObj = BeanUtils.toBean(updateReqVO, OrgAccountDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {
        // 校验存在
        validateExists(id);
        // 删除
        Mapper.deleteById(id);
    }

    private void validateExists(Long id) {
        if (Mapper.selectById(id) == null) {
            throw exception(ORG_ACCOUNT_DO_NOT_EXISTS);
        }
    }

    @Override
    public OrgAccountDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<OrgAccountDO> getPage(OrgAccountPageReqVO pageReqVO) {

        return Mapper.selectPage(pageReqVO);
    }

    @Override
    public List<OrgAccountDO> getList(String build) {
        LambdaQueryWrapperX<OrgAccountDO> queryWrapperX = new LambdaQueryWrapperX<>();
        if (StringUtils.isNotEmpty(build)) {
            queryWrapperX.apply("REGEXP_LIKE(build, '(^|,)" + build + "($|,)')");
        }
        queryWrapperX.eq(OrgAccountDO::getStatus, "1");
        queryWrapperX.orderByDesc(OrgAccountDO::getCreateTime);
        return Mapper.selectList(queryWrapperX);
    }

    /**
     * @return
     */
    @Override
    public List<OrgAccountDO> getList() {
        LambdaQueryWrapperX<OrgAccountDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OrgAccountDO::getDeleted, "0");
        queryWrapperX.eq(OrgAccountDO::getStatus, "0");
        return Mapper.selectList(queryWrapperX);
    }

    @Override
    public Integer getNameCount(String name, Long id) {
        LambdaQueryWrapperX<OrgAccountDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OrgAccountDO::getDeleted, "0");
        queryWrapperX.eq(OrgAccountDO::getName, name);
        if (id != null) {
            queryWrapperX.apply(" id !='" + id + "'");
        }

        return Mapper.selectList(queryWrapperX).size();
    }

    @Override
    public PageResult<OrgAccountRespVO> getAccountPage(OrgAccountPageReqVO pageReqVO) {
        PageResult<OrgAccountDO> orgAccountDOPageResult = Mapper.selectPage(pageReqVO);
        PageResult<OrgAccountRespVO> orgAccountRespVOPageResult = BeanUtils.toBean(orgAccountDOPageResult, OrgAccountRespVO.class);
        if (CollUtil.isNotEmpty(orgAccountRespVOPageResult.getList())) {
            for (OrgAccountRespVO orgAccountRespVO : orgAccountRespVOPageResult.getList()) {
                if (StringUtils.isNotEmpty(orgAccountRespVO.getBuild())) {
                    String[] split = orgAccountRespVO.getBuild().split(",");
                    StringBuilder builder = new StringBuilder();
                    for (String s : split) {
                        BuildDO buildDO = buildMapper.selectById(Long.parseLong(s.trim()));
                        if (buildDO != null) {
                            builder.append(buildDO.getBuildName()).append(",");
                        }
                    }
                    if (builder.length() > 0) {
                        orgAccountRespVO.setBuildName(String.valueOf(builder).substring(0, String.valueOf(builder).length() - 1));
                    }
                }
            }
        }
        return orgAccountRespVOPageResult;
    }
}