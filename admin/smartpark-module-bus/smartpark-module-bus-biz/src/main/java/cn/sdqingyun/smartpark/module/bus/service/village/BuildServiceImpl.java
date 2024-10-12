package cn.sdqingyun.smartpark.module.bus.service.village;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.build.BuildLayerDataVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.build.BuildLayerReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.countNum.CountBuildDataVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.LayerDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractSelectedPropertieMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.BuildMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.LayerMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.RoomMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.VillageMapper;
import cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants;
import cn.sdqingyun.smartpark.module.system.api.dept.DeptApi;
import cn.sdqingyun.smartpark.module.system.api.dept.dto.DeptRespDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.BUILD_NOT_EXISTS;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.VILLAGE_NOT_EXISTS;

/**
 * 项目楼栋 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class BuildServiceImpl implements BuildService {

    @Resource
    private BuildMapper buildMapper;
    @Resource
    private VillageMapper villageMapper;
    @Resource
    private LayerMapper layerMapper;
    @Resource
    private RoomMapper roomMapper;
    @Resource
    private ContractSelectedPropertieMapper contractSelectedPropertieMapper;
    @Resource
    private DeptApi depApi;

    @Override
    public Long createBuild(BuildSaveReqVO createReqVO) {
        // 插入
        BuildDO build = BeanUtils.toBean(createReqVO, BuildDO.class);

        VillageDO villageDO = villageMapper.selectOne( new LambdaQueryWrapperX<VillageDO>().eq( VillageDO::getId, createReqVO.getVillageId() ) );
        if(villageDO == null){
            throw exception(VILLAGE_NOT_EXISTS);
        }
        //创建部门信息
        DeptRespDTO deptResp = new DeptRespDTO();
        deptResp.setName( build.getBuildName() );
        deptResp.setParentId( villageDO.getOrgId() );
        deptResp.setStatus( 0 );
        CommonResult<Long> depts = depApi.createDept( deptResp );
        if(depts.isError()){
            throw new ServiceException(406, "楼栋部门创建失败");
        }
        build.setOrgId(depts.getData());

        buildMapper.insert(build);
        // 返回
        return build.getId();
    }

    @Override
    public void updateBuild(BuildSaveReqVO updateReqVO) {
        // 校验存在
        validateBuildExists(updateReqVO.getId());
        // 更新
        BuildDO updateObj = BeanUtils.toBean(updateReqVO, BuildDO.class);
        buildMapper.updateById(updateObj);

        DeptRespDTO deptResp = new DeptRespDTO();
        deptResp.setName( updateObj.getBuildName() );
        deptResp.setId( updateObj.getOrgId() );
        depApi.updateDept( deptResp );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBuild(Long id) {
        // 校验存在
        BuildDO buildDO = buildMapper.selectById( id );
        if (buildDO == null) {
            throw exception(BUILD_NOT_EXISTS);
        }
        Long orgId = buildDO.getOrgId();
        // 删除
        buildMapper.deleteById(id);
        //删除楼层
        layerMapper.delete(new LambdaQueryWrapper<LayerDO>().eq(LayerDO::getBuildId, id));
        //删除房间
        roomMapper.delete(new LambdaQueryWrapper<RoomDO>().eq(RoomDO::getBuildId, id));
        depApi.deleteDept(orgId);
    }

    private void validateBuildExists(Long id) {
        if (buildMapper.selectById(id) == null) {
            throw exception(BUILD_NOT_EXISTS);
        }
    }

    @Override
    public BuildRespVO getBuild(Long id) {
        BuildDO buildDO = buildMapper.selectById( id );
        BuildRespVO buildRespVO = BeanUtils.toBean( buildDO, BuildRespVO.class );
        if(buildDO != null && buildDO.getId() != null){
            VillageDO villageDO = villageMapper.selectById( buildDO.getVillageId() );
            if(villageDO != null){
                buildRespVO.setVillageName( villageDO.getName() );
            }
        }
        return buildRespVO;
    }

    @Override
    public Long getByBuildNameId(Long villageId, String buildName) {
        LambdaQueryWrapperX<BuildDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(BuildDO::getBuildName, buildName);
        queryWrapperX.eq(BuildDO::getVillageId, villageId);
        List<BuildDO> buildDOS = buildMapper.selectList(queryWrapperX);
        if (CollectionUtils.isNotEmpty(buildDOS)) {
            return buildDOS.get(0).getId();
        }
        return null;
    }

    @Override
    public PageResult<BuildDO> getBuildPage(BuildPageReqVO pageReqVO) {
        return buildMapper.selectPage(pageReqVO);
    }

    @Override
    public CountBuildDataVO projectBuild(BuildReqVO buildReqVO) {
        CountBuildDataVO countData = new CountBuildDataVO();
        List<VillageDO> dos = getVillageInfo( buildReqVO.getVillageId(), buildReqVO.getType() );
        if (dos.isEmpty()) {
            return countData;
        }

        //取出ID转换成数组
        List<Long> villageIds = dos.stream()
                .map(VillageDO::getId)
                .collect(Collectors.toList());

        LambdaQueryWrapperX<BuildDO> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eq( BuildDO::getStatus, "1" )
                .eqIfPresent( BuildDO::getId, buildReqVO.getId() )
                .in( BuildDO::getVillageId, villageIds);
        List<BuildDO> buildDOS = buildMapper.selectList( queryWrapper );
        if(CollUtil.isEmpty(buildDOS)){
            return countData;
        }

        //数据初始化
        countData.setBuildCount( 0 );
        countData.setManagementArea( BigDecimal.ZERO );
        countData.setRoomCount( 0 );
        countData.setRentInArea( BigDecimal.ZERO );
        countData.setRentInPercent( BigDecimal.ZERO );
        countData.setRentInAreaPercent( BigDecimal.ZERO );
        countData.setRentInContract( 0 );
        countData.setRentInRoomPercent( BigDecimal.ZERO );
        countData.setRentInRoom( 0 );
        countData.setToRentArea( BigDecimal.ZERO );
        countData.setToRentRoom( 0 );
        countData.setRevenueTargetYear( BigDecimal.ZERO );


        // 遍历楼宇数据
        for (BuildDO buildDO : buildDOS) {
            // 计算出租率
            BigDecimal managementArea = buildDO.getManagementArea() != null ? buildDO.getManagementArea() : BigDecimal.ZERO;
            BigDecimal rentInArea = buildDO.getRentInArea() != null ? buildDO.getRentInArea() : BigDecimal.ZERO;
            BigDecimal toRentArea = managementArea.subtract(rentInArea);
            Integer toRentRoom = buildDO.getRoomCount() != null ? buildDO.getRoomCount() - buildDO.getRentInRoom() : 0;

            // 计算本年度营收目标
            BigDecimal revenueTargetYear = parseDateTargetYear( buildDO );

            // 统计数据累加
            countData.setManagementArea(countData.getManagementArea().add(managementArea));
            countData.setRoomCount(countData.getRoomCount() + (buildDO.getRoomCount() != null ? buildDO.getRoomCount() : 0));
            countData.setRentInArea(countData.getRentInArea().add(rentInArea));
            countData.setRentInRoom(countData.getRentInRoom() + (buildDO.getRentInRoom() != null ? buildDO.getRentInRoom() : 0));
            countData.setRentInContract(countData.getRentInContract() + (buildDO.getRentInContract() != null ? buildDO.getRentInContract() : 0));
            countData.setToRentArea(countData.getToRentArea().add(toRentArea));
            countData.setToRentRoom(countData.getToRentRoom() + toRentRoom);
            countData.setRevenueTargetYear(countData.getRevenueTargetYear().add(revenueTargetYear));
        }

        // 格式化统计数据
        countData.setBuildCount( buildDOS.size() );
        countData.setManagementArea(formatNumber(countData.getManagementArea()));
        countData.setToRentArea(formatNumber(countData.getToRentArea()));
        countData.setRentInArea(formatNumber(countData.getRentInArea()));
        countData.setRevenueTargetYear(formatNumber(countData.getRevenueTargetYear()));
        countData.setRentInRoomPercent(countData.getRoomCount() > 0
                ? formatNumber(new BigDecimal(countData.getRentInRoom()).divide(new BigDecimal(countData.getRoomCount()), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)))
                : BigDecimal.ZERO);
        countData.setRentInAreaPercent(countData.getManagementArea().compareTo(BigDecimal.ZERO) > 0
                ? formatNumber(countData.getRentInArea().divide(countData.getManagementArea(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)))
                : BigDecimal.ZERO);

        return countData;
    }
    private BigDecimal formatNumber(BigDecimal number) {
        return number.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public BuildLayerDataVO projectProfile(BuildLayerReqVO buildLayerReqVO) {
        BuildLayerDataVO dataVO = new BuildLayerDataVO();
        //根据ID查询建筑信息
        BuildDO nowBuild = buildMapper.selectById( buildLayerReqVO.getId() );
        if (nowBuild == null) {
            throw new ServiceException( ErrorCodeConstants.BUILD_NOT_EXISTS);
        }

        // 项目配置的房间颜色
        List<VillageDO> villageInfo = getVillageInfo( nowBuild.getVillageId(), null );
        if(CollUtil.isNotEmpty( villageInfo )){
            String roomStatusColor = villageInfo.get( 0 ).getRoomStatusColor();
            dataVO.setRoomStatusColor( roomStatusColor );
        }

        //查询楼层
        LambdaQueryWrapperX<LayerDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eq( LayerDO::getBuildId, nowBuild.getId() )
                .eqIfPresent( LayerDO::getId, buildLayerReqVO.getLayerId() )
                .orderByDesc( LayerDO::getSort );
        List<LayerDO> layerList = layerMapper.selectList( wrapperX );
        if (CollUtil.isEmpty(layerList)) {
            return dataVO;
        }

        List<LayerRespVO> layerRespVOS = BeanUtils.toBean( layerList, LayerRespVO.class );
        Iterator<LayerRespVO> iterator = layerRespVOS.iterator();
        while (iterator.hasNext()) {
            LayerRespVO layerRespVO = iterator.next();
            //获取房间列表
            LambdaQueryWrapperX<RoomDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.eq( RoomDO::getBuildId, layerRespVO.getBuildId() )
                    .eq( RoomDO::getLayerId, layerRespVO.getId() )
                    .orderByAsc( RoomDO::getSort );
            List<RoomDO> roomList = roomMapper.selectList( queryWrapperX );
            if(CollUtil.isNotEmpty( roomList ) && roomList.size() > 0){
                List<RoomRespVO> roomRespVOS = BeanUtils.toBean( roomList, RoomRespVO.class );
                BigDecimal rentalAreaAll = BigDecimal.ZERO;
                for (RoomRespVO respVO : roomRespVOS) {
                    VillageDO villageDO = villageMapper.selectById( respVO.getVillageId() );
                    if(villageDO != null){
                        respVO.setVillageName( villageDO.getName() );
                    }
                    BuildDO buildDO = buildMapper.selectById( respVO.getBuildId() );
                    if(buildDO != null){
                        respVO.setBuildName( buildDO.getBuildName() );
                    }
                    LayerDO layerDO = layerMapper.selectById( respVO.getLayerId() );
                    if(layerDO != null){
                        respVO.setLayerName(layerDO.getLayerName());
                    }
                    rentalAreaAll = rentalAreaAll.add( respVO.getRentalArea() == null? BigDecimal.ZERO : respVO.getRentalArea() );
                    //房间信息判断
                    if (respVO.getLeaseEnd() != null && respVO.getContractInfo() != null) {
                        long unixLeaseEnd = respVO.getLeaseEnd().atZone(ZoneId.systemDefault()).toEpochSecond();
                        String leaseEndDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(respVO.getLeaseEnd());

                        long currentTime = System.currentTimeMillis() / 1000;

                        if (unixLeaseEnd < currentTime) {
                            respVO.setShowStatus("7"); // 已过期
                        } else if (unixLeaseEnd < currentTime + 30 * 86400) {
                            respVO.setShowStatus("6"); // 30日过期
                        } else if (unixLeaseEnd < currentTime + 90 * 86400) {
                            respVO.setShowStatus("5"); // 90日过期
                        } else if (respVO.getInvitationStatus() == 1) {
                            respVO.setShowStatus("10"); // 招商状态
                        } else {
                            respVO.setShowStatus("4"); // 已出租
                        }

                        // 设置租户名称
                        respVO.setOwnerName(JSON.parseObject(respVO.getContractInfo()).getString( "signedName" ));

                    } else {
                        respVO.setShowStatus("1"); // 默认空置状态
                    }

                }
                layerRespVO.setRentalAreaAll( rentalAreaAll );
                layerRespVO.setRoomRespVO( roomRespVOS );
            }else {
                iterator.remove();
            }
        }

        dataVO.setLayerRespVOS( layerRespVOS );


//        ObjectMapper objectMapper = new ObjectMapper();
//        List<Map<String, String>> villageRoomColor = new ArrayList<>();
//        villageRoomColor = objectMapper.readValue(roomStatusColor, new TypeReference<List<Map<String, String>>>(){});
//
//
//        Map<String, List<Map<String, String>>> villageRoomColorArr = new HashMap<>();
//        if (villageRoomColor != null) {
//            for (Map<String, String> tmpColorItem : villageRoomColor) {
//                villageRoomColorArr.putIfAbsent(tmpColorItem.get("type"), new ArrayList<>());
//                villageRoomColorArr.get(tmpColorItem.get("type")).add(tmpColorItem);
//            }
//            for (List<Map<String, String>> colorArrItem : villageRoomColorArr.values()) {
//                colorArrItem.sort(Comparator.comparingInt(item -> Integer.parseInt(item.get("limit"))));
//            }
//        }
        return dataVO;
    }

    private BigDecimal parseDateTargetYear(BuildDO nowBuild){
        BigDecimal revenueTargetYear = BigDecimal.ZERO;
        if (nowBuild.getRevenueTarget() != null && nowBuild.getRevenueTarget().length() > 0) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                List<Map<String, Object>> revenueTargets = objectMapper.readValue(nowBuild.getRevenueTarget(), new TypeReference<List<Map<String, Object>>>() {});
                Map<Integer, BigDecimal> tmpTarget = revenueTargets.stream()
                        .collect(Collectors.toMap(
                                rt -> (Integer) rt.get("year"),
                                rt -> new BigDecimal(rt.get("target").toString())
                        ));
                revenueTargetYear = tmpTarget.getOrDefault( LocalDate.now().getYear(), BigDecimal.ZERO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return revenueTargetYear;
    }

    private List<VillageDO> getVillageInfo(Long villageId,String Type) {
        LambdaQueryWrapperX<VillageDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eqIfPresent( VillageDO::getId, villageId )
                .eqIfPresent( VillageDO::getType, Type );
        return  villageMapper.selectList( wrapperX );
    }

    @Override
    public PageResult<BuildRespVO> getBuildVOPage(BuildPageReqVO pageReqVO) {
        PageResult<BuildDO> result = buildMapper.selectPage( pageReqVO );
        PageResult<BuildRespVO> pageResult = BeanUtils.toBean( result, BuildRespVO.class );
        if(CollUtil.isNotEmpty( pageResult.getList() )){
            for (BuildRespVO buildRespVO : pageResult.getList()) {
                if(buildRespVO.getVillageId() != null){
                    buildRespVO.setVillageName( villageMapper.selectById( buildRespVO.getVillageId() ).getName() );
                }
            }
        }
        return pageResult;
    }
}