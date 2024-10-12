package cn.sdqingyun.smartpark.module.bus.service.village;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.BuildMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.RoomMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.VillageMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.LayerDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.LayerMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 项目楼层 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class LayerServiceImpl implements LayerService {

    @Resource
    private LayerMapper layerMapper;
    @Resource
    private RoomMapper roomMapper;
    @Resource
    private VillageMapper villageMapper;
    @Resource
    private BuildMapper buildMapper;

    @Override
    public Long createLayer(LayerSaveReqVO createReqVO) {
        // 插入
        LayerDO layer = BeanUtils.toBean(createReqVO, LayerDO.class);
        layerMapper.insert(layer);
        // 返回
        return layer.getId();
    }

    @Override
    public void updateLayer(LayerSaveReqVO updateReqVO) {
        // 校验存在
        validateLayerExists(updateReqVO.getId());
        // 更新
        LayerDO updateObj = BeanUtils.toBean(updateReqVO, LayerDO.class);
        layerMapper.updateById(updateObj);
    }

    @Override
    public void deleteLayer(Long id) {
        // 校验存在
        validateLayerExists(id);
        // 删除
        layerMapper.deleteById(id);
        //删除房间
        roomMapper.delete(new LambdaQueryWrapper<RoomDO>().eq(RoomDO::getLayerId, id));
    }

    private void validateLayerExists(Long id) {
        if (layerMapper.selectById(id) == null) {
            throw exception(LAYER_NOT_EXISTS);
        }
    }

    @Override
    public LayerDO getLayer(Long id) {
        return layerMapper.selectById(id);
    }

    @Override
    public PageResult<LayerDO> getLayerPage(LayerPageReqVO pageReqVO) {
        return layerMapper.selectPage(pageReqVO);
    }

    @Override
    public List<LayerRespVO> getLayerList(LayerPageReqVO pageReqVO) {
        PageResult<LayerDO> pageResult = layerMapper.selectPage( pageReqVO );
        if(pageResult == null || CollUtil.isEmpty( pageResult.getList() )){
            return new ArrayList<LayerRespVO>();
        }
        List<LayerRespVO> layerRespVOS = BeanUtils.toBean( pageResult.getList(), LayerRespVO.class );
        for (LayerRespVO layerRespVO : layerRespVOS) {
            //查询楼层下是否有房间
            List<RoomDO> roomList = roomMapper.selectList( new LambdaQueryWrapper<RoomDO>().eq( RoomDO::getLayerId, layerRespVO.getId() ) );
            Long aLong = roomMapper.selectCount( new LambdaQueryWrapper<RoomDO>().eq( RoomDO::getLayerId, layerRespVO.getId() )
                    .eq( RoomDO::getStatus, 1 )
                    .eq( RoomDO::getBuildId, layerRespVO.getBuildId() )
                    .eq( RoomDO::getVillageId, layerRespVO.getVillageId() ) );
            if(aLong > 0){
                layerRespVO.setHaveRoom( true );
            }
            //获取楼宇项目名称
            BuildDO buildDO = buildMapper.selectById( layerRespVO.getBuildId() );
            layerRespVO.setBuildName( buildDO == null? "" : buildDO.getBuildName() );
            VillageDO villageDO = villageMapper.selectById( layerRespVO.getVillageId() );
            layerRespVO.setVillageName( villageDO == null? "" : villageDO.getName() );
        }

        return layerRespVOS;
    }
}