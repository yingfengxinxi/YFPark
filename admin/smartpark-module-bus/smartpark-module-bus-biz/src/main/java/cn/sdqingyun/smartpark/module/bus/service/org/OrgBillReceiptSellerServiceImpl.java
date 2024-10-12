package cn.sdqingyun.smartpark.module.bus.service.org;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptSellerPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptSellerSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.BuildRespVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillReceiptSellerDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgBillReceiptSellerMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.BuildMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.RoomMapper;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import java.util.List;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.RECEIPT_SELLER_NOT_EXISTS;

/**
 * 收据收款方信息 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class OrgBillReceiptSellerServiceImpl implements OrgBillReceiptSellerService {

    @Resource
    private OrgBillReceiptSellerMapper Mapper;

    @Resource
    private BuildMapper buildMapper;

    @Override
    public Long create(OrgBillReceiptSellerSaveReqVO createReqVO) {
        // 插入
        OrgBillReceiptSellerDO receiptSellerDO = BeanUtils.toBean(createReqVO, OrgBillReceiptSellerDO.class);
        Mapper.insert(receiptSellerDO);
        // 返回
        return receiptSellerDO.getId();
    }

    @Override
    public void update(OrgBillReceiptSellerSaveReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        OrgBillReceiptSellerDO updateObj = BeanUtils.toBean(updateReqVO, OrgBillReceiptSellerDO.class);
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
            throw exception(RECEIPT_SELLER_NOT_EXISTS);
        }
    }

    @Override
    public OrgBillReceiptSellerDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<OrgBillReceiptSellerDO> getPage(OrgBillReceiptSellerPageReqVO pageReqVO) {
        LambdaQueryWrapperX<OrgBillReceiptSellerDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.orderByDesc(OrgBillReceiptSellerDO::getCreateTime);
        PageResult<OrgBillReceiptSellerDO> orgBillReceiptSellerDOPageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        List<OrgBillReceiptSellerDO> list = orgBillReceiptSellerDOPageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(orgBillReceiptSellerDO -> {
                String[] split = orgBillReceiptSellerDO.getBuildBind().split(",");
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
                    orgBillReceiptSellerDO.setBuildBind(buildName);
                }
            });
        }
        return orgBillReceiptSellerDOPageResult;
    }

    /**
     * @param buildBindList
     * @return
     */
    @Override
    public List<OrgBillReceiptSellerDO> getByBuildsList(List<Long> buildBindList) {

        return Mapper.getByBuildsList(buildBindList);
    }

}