package cn.sdqingyun.smartpark.module.bus.service.workOrder;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.BuildRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderRulePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderRuleRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderRuleSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillNoticeTemplateDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderRuleDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.BuildMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.workOrder.WorkOrderRuleMapper;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import java.util.List;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.error;

/**
 * 工单规则设置 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class WorkOrderRuleServiceImpl implements WorkOrderRuleService {

    @Resource
    private WorkOrderRuleMapper Mapper;

    @Resource
    private BuildMapper buildMapper;

    @Override
    public Long create(WorkOrderRuleSaveReqVO createReqVO) {
        isCheckName(createReqVO);
        updateIsDefault(createReqVO);
        // 插入
        WorkOrderRuleDO workOrderRuleDO = BeanUtils.toBean(createReqVO, WorkOrderRuleDO.class);
        workOrderRuleDO.setIsDelete("1");
        Mapper.insert(workOrderRuleDO);
        // 返回
        return workOrderRuleDO.getId();
    }

    private void updateIsDefault(WorkOrderRuleSaveReqVO createReqVO) {
        if (createReqVO.getIsDefault().equals("1")) {
            LambdaQueryWrapperX<WorkOrderRuleDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.eq(WorkOrderRuleDO::getIsDefault, "1");
            queryWrapperX.eq(WorkOrderRuleDO::getApplication, createReqVO.getApplication());
            if (createReqVO.getId() != null) {
                queryWrapperX.apply("id !='" + createReqVO.getId() + "'");
            }
            List<WorkOrderRuleDO> workOrderRuleList = Mapper.selectList(queryWrapperX);
            if (CollectionUtils.isNotEmpty(workOrderRuleList)) {
                workOrderRuleList.forEach(workOrderRuleDO -> {
                    workOrderRuleDO.setIsDefault("0");
                    Mapper.updateById(workOrderRuleDO);
                });
            }
        }
    }

    private void isCheckName(WorkOrderRuleSaveReqVO workOrderRuleSaveReqVO) {
        LambdaQueryWrapperX<WorkOrderRuleDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(WorkOrderRuleDO::getName, workOrderRuleSaveReqVO.getName());
        queryWrapperX.eq(WorkOrderRuleDO::getApplication, workOrderRuleSaveReqVO.getApplication());
        if (workOrderRuleSaveReqVO.getId() != null) {
            queryWrapperX.apply("id !='" + workOrderRuleSaveReqVO.getId() + "'");
        }
        int size = Mapper.selectList(queryWrapperX).size();
        if (size >= 1) {
            throw new ServiceException(406, "规则名称已存在");
        }

        if (StringUtils.isNotEmpty(workOrderRuleSaveReqVO.getBuildBind())) {
            //楼宇配置规则后不允许再次配置
            String[] buildBinds = workOrderRuleSaveReqVO.getBuildBind().split(",");
            for (String buildBind : buildBinds) {
                LambdaQueryWrapperX<WorkOrderRuleDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
                queryWrapperX1.eq(WorkOrderRuleDO::getApplication, workOrderRuleSaveReqVO.getApplication());
                queryWrapperX1.apply("REGEXP_LIKE(build_bind, '(^|,)" + buildBind + "($|,)')");
                if (workOrderRuleSaveReqVO.getId() != null) {
                    queryWrapperX1.apply("id!=" + workOrderRuleSaveReqVO.getId() + "");
                }
                int checkBuild = Mapper.selectList(queryWrapperX1).size();
                if (checkBuild >= 1) {
                    BuildDO buildDO = buildMapper.selectById(Long.valueOf(buildBind));
                    String buildName = "";
                    if (buildDO != null) {
                        buildName = buildDO.getBuildName();
                    }
                    throw new ServiceException(406, "当前楼宇" + buildName + "已存在规则列表中,请勿重复配置");
                }
            }
        }
    }

    @Override
    public void update(WorkOrderRuleSaveReqVO updateReqVO) {
        isCheckName(updateReqVO);
        updateIsDefault(updateReqVO);
        // 更新
        WorkOrderRuleDO updateObj = BeanUtils.toBean(updateReqVO, WorkOrderRuleDO.class);
        updateObj.setIsDelete("1");
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public WorkOrderRuleDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<WorkOrderRuleRespVO> getPage(WorkOrderRulePageReqVO pageReqVO) {
        LambdaQueryWrapperX<WorkOrderRuleDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(WorkOrderRuleDO::getApplication, pageReqVO.getApplication());
        queryWrapperX.orderByDesc(WorkOrderRuleDO::getCreateTime);
        PageResult<WorkOrderRuleDO> pageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        PageResult<WorkOrderRuleRespVO> workOrderRuleRespVOPageResult = BeanUtils.toBean(pageResult, WorkOrderRuleRespVO.class);
        List<WorkOrderRuleRespVO> list = workOrderRuleRespVOPageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(workOrderRuleRespVO -> {
                if (StringUtils.isNotEmpty(workOrderRuleRespVO.getBuildBind())) {
                    List<String> buildBindList = List.of(workOrderRuleRespVO.getBuildBind().split(","));
                    StringBuilder sb = new StringBuilder();
                    for (String buildId : buildBindList) {
                        BuildDO buildDO = buildMapper.selectById(Long.valueOf(buildId));
                        sb.append(buildDO.getBuildName()).append(",");
                    }
                    String buildName = sb.toString();
                    if (StringUtils.isNotEmpty(buildName)) {
                        workOrderRuleRespVO.setBuildNames(buildName.substring(0, buildName.length() - 1));
                    }
                } else {
                    workOrderRuleRespVO.setBuildNames("全部楼宇");
                }
            });
        }

        return workOrderRuleRespVOPageResult;
    }

}