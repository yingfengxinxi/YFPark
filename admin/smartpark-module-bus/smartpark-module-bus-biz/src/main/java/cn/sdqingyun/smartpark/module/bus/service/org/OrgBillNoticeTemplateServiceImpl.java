package cn.sdqingyun.smartpark.module.bus.service.org;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillNoticeTemplatePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillNoticeTemplateSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillNoticeTemplateDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillReceiptRuleDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgBillNoticeTemplateMapper;
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
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.BILL_NOTICE_TEMPLATE_NOT_EXISTS;

/**
 * 账单缴费通知单模板 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class OrgBillNoticeTemplateServiceImpl implements OrgBillNoticeTemplateService {

    @Resource
    private OrgBillNoticeTemplateMapper Mapper;

    @Override
    public Long create(OrgBillNoticeTemplateSaveReqVO createReqVO) {
        // 插入
        OrgBillNoticeTemplateDO billNoticeTemplateDO = BeanUtils.toBean(createReqVO, OrgBillNoticeTemplateDO.class);
        Mapper.insert(billNoticeTemplateDO);
        // 返回
        return billNoticeTemplateDO.getId();
    }

    @Override
    public void update(OrgBillNoticeTemplateSaveReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        OrgBillNoticeTemplateDO updateObj = BeanUtils.toBean(updateReqVO, OrgBillNoticeTemplateDO.class);
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
            throw exception(BILL_NOTICE_TEMPLATE_NOT_EXISTS);
        }
    }

    @Override
    public OrgBillNoticeTemplateDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<OrgBillNoticeTemplateDO> getPage(OrgBillNoticeTemplatePageReqVO pageReqVO) {
        LambdaQueryWrapperX<OrgBillNoticeTemplateDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.orderByDesc(OrgBillNoticeTemplateDO::getCreateTime);
        return Mapper.selectPage(pageReqVO, queryWrapperX);
    }

    @Override
    public Boolean isCheckName(String name, Long id) {
        LambdaQueryWrapperX<OrgBillNoticeTemplateDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OrgBillNoticeTemplateDO::getName, name);
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
        LambdaQueryWrapperX<OrgBillNoticeTemplateDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.apply("REGEXP_LIKE(build_bind, '(^|,)" + buildBind + "($|,)')");
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