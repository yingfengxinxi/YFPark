package cn.sdqingyun.smartpark.module.bus.service.org;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgTaxCodePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgTaxCodeSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgTaxCodeDO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.TAX_CODE_NOT_EXISTS;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgTaxCodeMapper;

/**
 * 税收分类编码配置 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class OrgTaxCodeServiceImpl implements OrgTaxCodeService {

    @Resource
    private OrgTaxCodeMapper Mapper;

    @Override
    public Long create(OrgTaxCodeSaveReqVO createReqVO) {
        // 插入
        OrgTaxCodeDO orgTaxCodeDO = BeanUtils.toBean(createReqVO, OrgTaxCodeDO.class);
        Mapper.insert(orgTaxCodeDO);
        // 返回
        return orgTaxCodeDO.getId();
    }

    @Override
    public void update(OrgTaxCodeSaveReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        OrgTaxCodeDO updateObj = BeanUtils.toBean(updateReqVO, OrgTaxCodeDO.class);
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
            throw exception(TAX_CODE_NOT_EXISTS);
        }
    }

    @Override
    public OrgTaxCodeDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<OrgTaxCodeDO> getPage(OrgTaxCodePageReqVO pageReqVO) {
        return Mapper.selectPage(pageReqVO);
    }

    /**
     *
     * @param taxCode
     * @param id
     * @return
     */
    @Override
    public Boolean isCheckTaxCode(String taxCode, Long id) {

        LambdaQueryWrapperX<OrgTaxCodeDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OrgTaxCodeDO::getTaxCode, taxCode);
        if (id != null) {
            queryWrapperX.apply("id !='" + id + "'");
        }
        int size = Mapper.selectList(queryWrapperX).size();
        if (size >= 1) {
            return true;
        }
        return false;
    }

}