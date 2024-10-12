package cn.sdqingyun.smartpark.module.bus.service.org;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptTemplatePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptTemplateSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillReceiptTemplateDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgBillReceiptTemplateMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.BuildMapper;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.RECEIPT_TEMPLATE_NOT_EXISTS;

/**
 * 收据模板 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class OrgBillReceiptTemplateServiceImpl implements OrgBillReceiptTemplateService {

    @Resource
    private OrgBillReceiptTemplateMapper Mapper;

    @Resource
    private BuildMapper buildMapper;

    @Override
    public Long create(OrgBillReceiptTemplateSaveReqVO createReqVO) {
        // 插入
        OrgBillReceiptTemplateDO receiptTemplateDO = BeanUtils.toBean(createReqVO, OrgBillReceiptTemplateDO.class);
        Mapper.insert(receiptTemplateDO);
        // 返回
        return receiptTemplateDO.getId();
    }

    @Override
    public void update(OrgBillReceiptTemplateSaveReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        OrgBillReceiptTemplateDO updateObj = BeanUtils.toBean(updateReqVO, OrgBillReceiptTemplateDO.class);
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
            throw exception(RECEIPT_TEMPLATE_NOT_EXISTS);
        }
    }

    @Override
    public OrgBillReceiptTemplateDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<OrgBillReceiptTemplateDO> getPage(OrgBillReceiptTemplatePageReqVO pageReqVO) {
        LambdaQueryWrapperX<OrgBillReceiptTemplateDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.orderByDesc(OrgBillReceiptTemplateDO::getCreateTime);
        PageResult<OrgBillReceiptTemplateDO> orgBillReceiptTemplateDOPageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        List<OrgBillReceiptTemplateDO> list = orgBillReceiptTemplateDOPageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(orgBillReceiptTemplateDO -> {
                String[] split = orgBillReceiptTemplateDO.getBuildBind().split(",");
                StringBuilder sb = new StringBuilder();
                for (String buildBind : split) {
                    BuildDO buildDO = buildMapper.selectById(Long.valueOf(buildBind));
                    if (buildDO != null) {
                        sb.append(buildDO.getBuildName()).append(",");
                    }

                }
                String buildName = sb.toString();
                if (StringUtils.isNotEmpty(buildName)) {
                    buildName = buildName.substring(0, buildName.length() - 1);
                    orgBillReceiptTemplateDO.setBuildBind(buildName);
                }
            });
        }
        return orgBillReceiptTemplateDOPageResult;
    }

    @Override
    public Boolean isCheckName(String name, Long id) {
        LambdaQueryWrapperX<OrgBillReceiptTemplateDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OrgBillReceiptTemplateDO::getName, name);
        if (id != null) {
            queryWrapperX.apply(" id !='" + id + "'");
        }
        int size = Mapper.selectList(queryWrapperX).size();
        if (size >= 1) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param buildBind
     * @return
     */
    @Override
    public List<OrgBillReceiptTemplateDO> getByBuildsList(Long buildBind) {
        List<Long> buildBindList = Lists.newArrayList();
        buildBindList.add(buildBind);
        return Mapper.getByBuildsList(buildBindList);
    }

}