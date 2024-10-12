package cn.sdqingyun.smartpark.module.bus.service.org;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.CostTypeChildrenVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillCostTypePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillCostTypeSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillCostCategoryDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillCostTypeDO;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.COST_TYPE_NOT_EXISTS;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgBillCostTypeMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgBillCostCategoryMapper;

import java.util.List;

/**
 * 账单费用类型 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class OrgBillCostTypeServiceImpl implements OrgBillCostTypeService {

    @Resource
    private OrgBillCostTypeMapper Mapper;

    @Autowired
    private OrgBillCostCategoryMapper billCostCategoryMapper;

    @Override
    public Long create(OrgBillCostTypeSaveReqVO createReqVO) {
        OrgBillCostCategoryDO orgBillCostCategoryDO = billCostCategoryMapper.selectById(createReqVO.getCategoryId());
        createReqVO.setIsBond(orgBillCostCategoryDO.getIsBond());
        createReqVO.setIsRoot(orgBillCostCategoryDO.getIsRoot());
        // 插入
        OrgBillCostTypeDO costTypeDO = BeanUtils.toBean(createReqVO, OrgBillCostTypeDO.class);
        Mapper.insert(costTypeDO);
        // 返回
        return costTypeDO.getId();
    }

    @Override
    public void update(OrgBillCostTypeSaveReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        OrgBillCostTypeDO updateObj = BeanUtils.toBean(updateReqVO, OrgBillCostTypeDO.class);
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
            throw exception(COST_TYPE_NOT_EXISTS);
        }
    }

    @Override
    public OrgBillCostTypeDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<OrgBillCostTypeDO> getPage(OrgBillCostTypePageReqVO pageReqVO) {
        LambdaQueryWrapperX<OrgBillCostTypeDO> queryWrapperX = new LambdaQueryWrapperX<>();
        if (StringUtils.isNotEmpty(pageReqVO.getName())) {
            queryWrapperX.like(OrgBillCostTypeDO::getName, pageReqVO.getName());
        }
        if (pageReqVO.getCategoryId() != null) {
            queryWrapperX.eq(OrgBillCostTypeDO::getCategoryId, pageReqVO.getCategoryId());
        }
        queryWrapperX.orderByAsc(OrgBillCostTypeDO::getSort);
        return Mapper.selectPage(pageReqVO, queryWrapperX);
    }

    @Override
    public Boolean isChackName(String name, Long id, Long categoryId) {
        LambdaQueryWrapperX<OrgBillCostTypeDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OrgBillCostTypeDO::getName, name);
        queryWrapperX.eq(OrgBillCostTypeDO::getCategoryId, categoryId);
        if (id != null) {
            queryWrapperX.apply("id !='" + id + "'");
        }
        int size = Mapper.selectList(queryWrapperX).size();
        if (size >= 1) {
            return true;
        }
        return false;
    }

    /**
     * @return
     */
    public List<CostTypeChildrenVO> getCostTypeChildrenList() {
        List<CostTypeChildrenVO> list = Lists.newArrayList();
        LambdaQueryWrapperX<OrgBillCostCategoryDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.orderByAsc(OrgBillCostCategoryDO::getSort);
        List<OrgBillCostCategoryDO> orgBillCostCategoryList = billCostCategoryMapper.selectList(queryWrapperX);
        if (CollectionUtils.isNotEmpty(orgBillCostCategoryList)) {
            orgBillCostCategoryList.stream().forEach(orgBillCostCategoryDO -> {
                CostTypeChildrenVO costTypeChildrenVO = new CostTypeChildrenVO();
                costTypeChildrenVO.setValue(orgBillCostCategoryDO.getId());
                costTypeChildrenVO.setLabel(orgBillCostCategoryDO.getName());
                LambdaQueryWrapperX<OrgBillCostTypeDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
                queryWrapperX1.eq(OrgBillCostTypeDO::getStatus, "1");
                queryWrapperX1.eq(OrgBillCostTypeDO::getCategoryId, orgBillCostCategoryDO.getId());
                queryWrapperX1.orderByAsc(OrgBillCostTypeDO::getSort);
                List<OrgBillCostTypeDO> costTypeDOList = Mapper.selectList(queryWrapperX1);
                if (CollectionUtils.isNotEmpty(costTypeDOList)) {
                    List<CostTypeChildrenVO> costTypeList = Lists.newArrayList();
                    costTypeDOList.forEach(orgBillCostTypeDO -> {
                        CostTypeChildrenVO costTypeChildrenVO1 = new CostTypeChildrenVO();
                        costTypeChildrenVO1.setValue(orgBillCostTypeDO.getId());
                        costTypeChildrenVO1.setLabel(orgBillCostTypeDO.getName());
                        costTypeList.add(costTypeChildrenVO1);
                    });
                    costTypeChildrenVO.setChildren(costTypeList);
                }
                list.add(costTypeChildrenVO);
            });
        }
        return list;
    }

    @Override
    public List<OrgBillCostTypeDO> getBondList() {
        LambdaQueryWrapperX<OrgBillCostTypeDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OrgBillCostTypeDO::getIsBond, "1");
        queryWrapperX.orderByAsc(OrgBillCostTypeDO::getSort);
        List<OrgBillCostTypeDO> orgBillCostTypeDOS = Mapper.selectList(queryWrapperX);
        return orgBillCostTypeDOS;
    }

}