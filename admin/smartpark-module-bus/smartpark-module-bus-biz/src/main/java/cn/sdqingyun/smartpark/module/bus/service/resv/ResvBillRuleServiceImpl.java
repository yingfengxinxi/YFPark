package cn.sdqingyun.smartpark.module.bus.service.resv;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvBillRulePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvBillRuleRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvBillRuleSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.ResvBillRuleDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.ResvPlaceDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.resv.ResvBillRuleMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.resv.ResvPlaceMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.VillageMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.RESV_BILL_RULE_NOT_EXISTS;

/**
 * 预约收费规则 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class ResvBillRuleServiceImpl implements ResvBillRuleService {

    @Resource
    private ResvBillRuleMapper resvBillRuleMapper;
    @Resource
    private VillageMapper villageMapper;
    @Resource
    private ResvPlaceMapper placeMapper;

    @Override
    public Long createResvBillRule(ResvBillRuleSaveReqVO createReqVO) {
        //校验规则名称是否存在
        LambdaQueryWrapperX<ResvBillRuleDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eq(ResvBillRuleDO::getName, createReqVO.getName())
                .eq(ResvBillRuleDO::getVillageId, createReqVO.getVillageId())
                .eq(ResvBillRuleDO::getAppSign, createReqVO.getAppSign());
        if (resvBillRuleMapper.selectCount(wrapperX) > 0) {
            throw new ServiceException(406,"规则名称已经存在、不能重复");
        }

        // 插入
        ResvBillRuleDO resvBillRule = BeanUtils.toBean(createReqVO, ResvBillRuleDO.class);
        resvBillRuleMapper.insert(resvBillRule);
        // 返回
        return resvBillRule.getId();
    }

    @Override
    public void updateResvBillRule(ResvBillRuleSaveReqVO updateReqVO) {
        // 校验存在
        validateResvBillRuleExists(updateReqVO.getId());
        // 更新
        ResvBillRuleDO updateObj = BeanUtils.toBean(updateReqVO, ResvBillRuleDO.class);
        resvBillRuleMapper.updateById(updateObj);
    }

    @Override
    public void deleteResvBillRule(Long id) {
        // 校验存在
        validateResvBillRuleExists(id);

        //判断当前规则是否在使用
        if(placeMapper.selectCount(new LambdaQueryWrapperX<ResvPlaceDO>().eq( ResvPlaceDO::getBillRuleId,id )) > 0){
            throw new ServiceException(406,"该计费规则正在使用中，不允许删除");
        }

        // 删除
        resvBillRuleMapper.deleteById(id);
    }

    private void validateResvBillRuleExists(Long id) {
        if (resvBillRuleMapper.selectById(id) == null) {
            throw exception(RESV_BILL_RULE_NOT_EXISTS);
        }
    }

    @Override
    public ResvBillRuleDO getResvBillRule(Long id) {
        return resvBillRuleMapper.selectById(id);
    }

    @Override
    public PageResult<ResvBillRuleDO> getResvBillRulePage(ResvBillRulePageReqVO pageReqVO) {
        return resvBillRuleMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<ResvBillRuleRespVO> getResvBillRulePageVO(ResvBillRulePageReqVO pageReqVO) {
        PageResult<ResvBillRuleDO> result = resvBillRuleMapper.selectPage( pageReqVO );
        PageResult<ResvBillRuleRespVO> pageResult = BeanUtils.toBean( result, ResvBillRuleRespVO.class );
        if(CollUtil.isNotEmpty( pageResult.getList() )){
            pageResult.getList().forEach( item -> {
                if(item.getVillageId() != null){
                    VillageDO villageDO = villageMapper.selectById( item.getVillageId() );
                    if(villageDO != null && villageDO.getName() != null){
                        item.setVillageName( villageDO.getName() );
                    }
                }
            } );
        }

        return pageResult;
    }
}