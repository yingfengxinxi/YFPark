package cn.sdqingyun.smartpark.module.bus.service.org;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgSellerPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgSellerSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgSellerDO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.SELLER_NOT_EXISTS;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgSellerMapper;

/**
 * 机构楼宇售方信息(发票设置) Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class OrgSellerServiceImpl implements OrgSellerService {

    @Resource
    private OrgSellerMapper Mapper;

    @Override
    public Long create(OrgSellerSaveReqVO createReqVO) {
        // 插入
        OrgSellerDO orgSellerDO = BeanUtils.toBean(createReqVO, OrgSellerDO.class);
        Mapper.insert(orgSellerDO);
        // 返回
        return orgSellerDO.getId();
    }

    @Override
    public void update(OrgSellerSaveReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        OrgSellerDO updateObj = BeanUtils.toBean(updateReqVO, OrgSellerDO.class);
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
            throw exception(SELLER_NOT_EXISTS);
        }
    }

    @Override
    public OrgSellerDO get(Long id) {
        return Mapper.selectById(id);
    }

    /**
     *
     * @param pageReqVO 分页查询
     * @return
     */
    @Override
    public PageResult<OrgSellerDO> getPage(OrgSellerPageReqVO pageReqVO) {
        LambdaQueryWrapperX<OrgSellerDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.orderByDesc(OrgSellerDO::getCreateTime);
        return Mapper.selectPage(pageReqVO, queryWrapperX);
    }

    @Override
    public Boolean isCheckName(String companyName, Long id) {
        LambdaQueryWrapperX<OrgSellerDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OrgSellerDO::getCompanyName, companyName);
        if (id != null) {
            queryWrapperX.apply(" id!='" + id + "'");
        }
        int size = Mapper.selectList(queryWrapperX).size();
        if (size >= 1) {
            return true;
        }
        return false;
    }

}