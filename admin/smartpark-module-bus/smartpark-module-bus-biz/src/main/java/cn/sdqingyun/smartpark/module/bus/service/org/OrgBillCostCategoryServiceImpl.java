package cn.sdqingyun.smartpark.module.bus.service.org;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillCostCategoryPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillCostCategorySaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillCostCategoryDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillCostTypeDO;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.COST_CATEGORY_NOT_EXISTS;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgBillCostCategoryMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgBillCostTypeMapper;

import java.util.List;

/**
 * 账单费用分类 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class OrgBillCostCategoryServiceImpl implements OrgBillCostCategoryService {

    @Resource
    private OrgBillCostCategoryMapper Mapper;

    @Resource
    private OrgBillCostTypeMapper billCostTypeMapper;

    @Override
    public Long create(OrgBillCostCategorySaveReqVO createReqVO) {
        createReqVO.setIsRoot("0");
        // 插入
        OrgBillCostCategoryDO orgBillCostCategoryDO = BeanUtils.toBean(createReqVO, OrgBillCostCategoryDO.class);
        Mapper.insert(orgBillCostCategoryDO);
        // 返回
        return orgBillCostCategoryDO.getId();
    }

    @Override
    public void update(OrgBillCostCategorySaveReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        OrgBillCostCategoryDO updateObj = BeanUtils.toBean(updateReqVO, OrgBillCostCategoryDO.class);
        if (Mapper.selectById(updateReqVO.getId()).getIsBond().equals("1")) {
            updateReqVO.setIsBond("1");
        } else {
            if (updateReqVO.getIsBond().equals("1")) {
                //
                LambdaQueryWrapperX<OrgBillCostTypeDO> queryWrapperX = new LambdaQueryWrapperX<>();
                queryWrapperX.eq(OrgBillCostTypeDO::getCategoryId, updateReqVO.getId());
                List<OrgBillCostTypeDO> orgBillCostTypeDOS = billCostTypeMapper.selectList(queryWrapperX);
                if (CollectionUtils.isNotEmpty(orgBillCostTypeDOS)) {
                    orgBillCostTypeDOS.stream().forEach(orgBillCostTypeDO -> {
                        orgBillCostTypeDO.setIsBond("1");
                        billCostTypeMapper.updateById(orgBillCostTypeDO);
                    });
                }
            }
        }
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
            throw exception(COST_CATEGORY_NOT_EXISTS);
        }
    }

    @Override
    public OrgBillCostCategoryDO get(Long id) {
        return Mapper.selectById(id);
    }

    /**
     * @return
     */
    @Override
    public List<OrgBillCostCategoryDO> getList(String name) {
        LambdaQueryWrapperX<OrgBillCostCategoryDO> queryWrapperX = new LambdaQueryWrapperX<>();
        if (StringUtils.isNotEmpty(name)) {
            queryWrapperX.like(OrgBillCostCategoryDO::getName, name);
        }
        queryWrapperX.orderByAsc(OrgBillCostCategoryDO::getSort);
        return Mapper.selectList(queryWrapperX);
    }

    @Override
    public Boolean isCheckName(String name, Long id) {
        LambdaQueryWrapperX<OrgBillCostCategoryDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OrgBillCostCategoryDO::getName, name);
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