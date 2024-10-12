package cn.sdqingyun.smartpark.module.bus.service.energy;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.HengHanCollectorPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.HengHanCollectorRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.HengHanCollectorSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.HengHanCollectorDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.HengHanCollectorMapper;
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

/**
 * henghan_collector Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class HengHanCollectorServiceImpl implements HengHanCollectorService {

    @Resource
    private HengHanCollectorMapper Mapper;

    @Override
    public Long create(HengHanCollectorSaveReqVO createReqVO) {
        // 插入
        HengHanCollectorDO hengHanCollectorDO = BeanUtils.toBean(createReqVO, HengHanCollectorDO.class);
        Mapper.insert(hengHanCollectorDO);
        // 返回
        return hengHanCollectorDO.getId();
    }

    @Override
    public void update(HengHanCollectorSaveReqVO updateReqVO) {

        // 更新
        HengHanCollectorDO updateObj = BeanUtils.toBean(updateReqVO, HengHanCollectorDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public HengHanCollectorDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<HengHanCollectorRespVO> getPage(HengHanCollectorPageReqVO pageReqVO) {
        LambdaQueryWrapperX<HengHanCollectorDO>queryWrapperX=new LambdaQueryWrapperX<>();
        queryWrapperX.eq(HengHanCollectorDO::getVillageId,pageReqVO.getVillageId());
        if(StringUtils.isNotEmpty(pageReqVO.getCid())){
            queryWrapperX.like(HengHanCollectorDO::getCid,pageReqVO.getCid());
        }
        PageResult<HengHanCollectorDO> pageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        PageResult<HengHanCollectorRespVO> hanCollectorRespVOPageResult = BeanUtils.toBean(pageResult, HengHanCollectorRespVO.class);

        return hanCollectorRespVOPageResult;
    }

}