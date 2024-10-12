package cn.sdqingyun.smartpark.module.bus.service.contract;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractTemplatePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractTemplateSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractTemplateDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillNoticeTemplateDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractTemplateMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.BuildMapper;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.CONTRACT_TEMPLATE_NOT_EXISTS;

/**
 * 机构合同模板配置 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class ContractTemplateServiceImpl implements ContractTemplateService {

    @Resource
    private ContractTemplateMapper Mapper;

    @Resource
    private BuildMapper buildMapper;

    @Override
    public Long create(ContractTemplateSaveReqVO createReqVO) {
        // 插入
        ContractTemplateDO contractTemplateDO = BeanUtils.toBean(createReqVO, ContractTemplateDO.class);
        Mapper.insert(contractTemplateDO);
        // 返回
        return contractTemplateDO.getId();
    }

    @Override
    public void update(ContractTemplateSaveReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        ContractTemplateDO updateObj = BeanUtils.toBean(updateReqVO, ContractTemplateDO.class);
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
            throw exception(CONTRACT_TEMPLATE_NOT_EXISTS);
        }
    }

    @Override
    public ContractTemplateDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<ContractTemplateDO> getPage(ContractTemplatePageReqVO pageReqVO) {
        LambdaQueryWrapperX<ContractTemplateDO> queryWrapperX = new LambdaQueryWrapperX<>();
        if (StringUtils.isNotEmpty(pageReqVO.getScene())) {
            queryWrapperX.eq(ContractTemplateDO::getScene, "retreat");
        }
        queryWrapperX.orderByDesc(ContractTemplateDO::getCreateTime);
        PageResult<ContractTemplateDO> contractTemplateDOPageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        List<ContractTemplateDO> list = contractTemplateDOPageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(contractTemplateDO -> {
                String[] relationBuilds = contractTemplateDO.getRelationBuilds().split(",");
                StringBuilder stringBuilder = new StringBuilder();
                for (String relationBuild : relationBuilds) {
                    BuildDO buildDO = buildMapper.selectById(relationBuild);
                    if (buildDO != null) {
                        stringBuilder.append(buildDO.getBuildName()).append(",");
                    }
                }
                String relationBuildName = stringBuilder.toString();
                if (StringUtils.isNotEmpty(relationBuildName)) {
                    relationBuildName = relationBuildName.substring(0, relationBuildName.length() - 1);
                    contractTemplateDO.setRelationBuildName(relationBuildName);
                }
            });
        }
        return contractTemplateDOPageResult;
    }

    /**
     *
     * @param relationBuild
     * @return
     */
    @Override
    public List<ContractTemplateDO> getRelationBuildIdTemplateList(String relationBuild) {
        LambdaQueryWrapperX<ContractTemplateDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ContractTemplateDO::getScene, "retreat");
        queryWrapperX.apply("REGEXP_LIKE(relation_builds, '(^|,)" + relationBuild + "($|,)')");
        queryWrapperX.orderByDesc(ContractTemplateDO::getCreateTime);
        return Mapper.selectList(queryWrapperX);
    }


    @Override
    public Boolean isCheckName(String name, Long id) {
        LambdaQueryWrapperX<ContractTemplateDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ContractTemplateDO::getTemplateName, name);
        queryWrapperX.eq(ContractTemplateDO::getScene, "retreat");
        if (id != null) {
            queryWrapperX.apply("id !='" + id + "'");
        }
        int size = Mapper.selectList(queryWrapperX).size();
        if (size >= 1) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean isCheckBuild(String buildBind, Long id) {
        LambdaQueryWrapperX<ContractTemplateDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.apply("REGEXP_LIKE(relation_builds, '(^|,)" + buildBind + "($|,)')");
        queryWrapperX.eq(ContractTemplateDO::getScene, "retreat");
        if (id != null) {
            queryWrapperX.apply("id!=" + id + "");
        }
        int size = Mapper.selectList(queryWrapperX).size();
        if (size >= 1) {
            return true;
        }
        return false;
    }


}