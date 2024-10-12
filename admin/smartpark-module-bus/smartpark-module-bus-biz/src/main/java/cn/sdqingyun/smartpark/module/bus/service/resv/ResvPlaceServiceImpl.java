package cn.sdqingyun.smartpark.module.bus.service.resv;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvPlacePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvPlaceRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvPlaceSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.ResvBillRuleDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.ResvPlaceCategoryDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.ResvPlaceDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.resv.ResvBillRuleMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.resv.ResvPlaceCategoryMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.resv.ResvPlaceMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.RESV_PLACE_NOT_EXISTS;

/**
 * 预约场地 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class ResvPlaceServiceImpl implements ResvPlaceService {

    @Resource
    private ResvPlaceMapper resvPlaceMapper;
    @Resource
    private ResvBillRuleMapper resvBillRuleMapper;
    @Resource
    private ResvPlaceCategoryMapper resvPlaceCategoryMapper;

    @Override
    public Long createResvPlace(ResvPlaceSaveReqVO createReqVO) {
        // 插入
        ResvPlaceDO resvPlace = BeanUtils.toBean(createReqVO, ResvPlaceDO.class);
        resvPlaceMapper.insert(resvPlace);
        // 返回
        return resvPlace.getId();
    }

    @Override
    public void updateResvPlace(ResvPlaceSaveReqVO updateReqVO) {
        // 校验存在
        validateResvPlaceExists(updateReqVO.getId());
        // 更新
        ResvPlaceDO updateObj = BeanUtils.toBean(updateReqVO, ResvPlaceDO.class);
        resvPlaceMapper.updateById(updateObj);
    }

    @Override
    public void deleteResvPlace(Long id) {
        // 校验存在
        validateResvPlaceExists(id);
        // 删除
        resvPlaceMapper.deleteById(id);
    }

    private void validateResvPlaceExists(Long id) {
        if (resvPlaceMapper.selectById(id) == null) {
            throw exception(RESV_PLACE_NOT_EXISTS);
        }
    }

    @Override
    public ResvPlaceDO getResvPlace(Long id) {
        return resvPlaceMapper.selectById(id);
    }

    @Override
    public PageResult<ResvPlaceDO> getResvPlacePage(ResvPlacePageReqVO pageReqVO) {
        return resvPlaceMapper.selectPage(pageReqVO);
    }

    @Override
    public ResvPlaceRespVO getResvPlaceVO(Long id) {
        ResvPlaceDO resvPlaceDO = resvPlaceMapper.selectById( id );
        ResvPlaceRespVO placeRespVO = BeanUtils.toBean( resvPlaceDO, ResvPlaceRespVO.class );
        if( placeRespVO != null){
            if( placeRespVO.getBillRuleId() != null){
                ResvBillRuleDO ruleDO = resvBillRuleMapper.selectById( placeRespVO.getBillRuleId() );
                if( ruleDO != null){
                    placeRespVO.setRuleName( ruleDO.getName() );
                    placeRespVO.setChargeStandard( ruleDO.getChargeStandard() );
                    placeRespVO.setIsMultiTimeCharge( ruleDO.getIsMultiTimeCharge() );
                    placeRespVO.setMultiTimeChargeStandard( ruleDO.getMultiTimeChargeStandard() );
                }
            }
            //查询分类人数
            ResvPlaceCategoryDO resvPlaceCategoryDO = resvPlaceCategoryMapper.selectById( placeRespVO.getCategoryId() );
            if( resvPlaceCategoryDO != null){
                placeRespVO.setCapacity( resvPlaceCategoryDO.getCapacity() );
            }
        }
        return placeRespVO;
    }
}