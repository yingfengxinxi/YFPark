package cn.sdqingyun.smartpark.module.bus.service.village;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.security.core.util.SecurityFrameworkUtils;
import cn.sdqingyun.smartpark.framework.tenant.core.context.TenantContextHolder;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.VillageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.countNum.CountDataVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.countNum.ProjectOverviewVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.villagebuild.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.BusOrgDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.*;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.*;
import cn.sdqingyun.smartpark.module.bus.service.org.BusOrgService;
import cn.sdqingyun.smartpark.module.system.api.dept.DeptApi;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 社区 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class VillageServiceImpl implements VillageService {

    @Resource
    private VillageMapper villageMapper;
    @Resource
    private BusOrgService orgService;
    @Resource
    private ZoneMapper zoneMapper;
    @Resource
    private BuildMapper buildMapper;
    @Resource
    private LayerMapper layerMapper;
    @Resource
    private RoomMapper roomMapper;
    @Resource
    private DeptApi depApi;
    @Resource
    private VillageCollectionMapper villageCollectionMapper;
    @Resource
    private RoomPriceMapper roomPriceMapper;

    @Override
    public Long createVillage(VillageSaveReqVO createReqVO) {
        // 插入
        VillageDO village = BeanUtils.toBean(createReqVO, VillageDO.class);
        villageMapper.insert(village);
        // 返回
        return village.getId();
    }

    @Override
    public void updateVillage(VillageSaveReqVO updateReqVO) {
        // 校验存在
        validateVillageExists(updateReqVO.getId());
        // 更新
        VillageDO updateObj = BeanUtils.toBean(updateReqVO, VillageDO.class);
        villageMapper.updateById(updateObj);

//        DeptRespDTO deptResp = new DeptRespDTO();
//        deptResp.setName( updateObj.getName() );
//        deptResp.setId( updateObj.getOrgId() );
//        depApi.updateDept( deptResp );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteVillage(Long id) {
        // 校验存在
        VillageDO villageDO = villageMapper.selectById( id );
        if (villageDO == null) {
            throw exception(VILLAGE_NOT_EXISTS);
        }
        Long orgId = villageDO.getOrgId();

        // 删除
        villageMapper.deleteById(id);
        //删除区域
        zoneMapper.delete(new LambdaQueryWrapper<ZoneDO>().eq(ZoneDO::getVillageId, id));
        //删除建筑
//        List<BuildDO> buildDOS = buildMapper.selectList( new LambdaQueryWrapper<BuildDO>().eq( BuildDO::getVillageId, id ) );
//        if(CollUtil.isNotEmpty(buildDOS)){
//            for (BuildDO buildDO : buildDOS) {
//                depApi.deleteDept(buildDO.getOrgId());
//            }
//        }
        buildMapper.delete(new LambdaQueryWrapper<BuildDO>().eq(BuildDO::getVillageId, id));
        //删除楼层
        layerMapper.delete(new LambdaQueryWrapper<LayerDO>().eq(LayerDO::getVillageId, id));
        //删除房间
        roomMapper.delete(new LambdaQueryWrapper<RoomDO>().eq(RoomDO::getVillageId, id));
        //删除部门
//        depApi.deleteDept(orgId);
    }

    private void validateVillageExists(Long id) {
        if (villageMapper.selectById(id) == null) {
            throw exception(VILLAGE_NOT_EXISTS);
        }
    }

    private void validateVillageExistsByName(String name) {
        LambdaQueryWrapper<VillageDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VillageDO::getName, name);
        if (villageMapper.selectOne(wrapper) != null) {
            throw exception(VILLAGE_NOT_EXISTS_NAME);
        }
    }

    @Override
    public VillageDO getVillage(Long id) {
        return villageMapper.selectById(id);
    }

    @Override
    public PageResult<VillageDO> getVillagePage(VillagePageReqVO pageReqVO) {
        return villageMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveVillage(VillageInsertReqVO createReqVO) {
        try {
            //校验项目名称是否存在
            validateVillageExistsByName(createReqVO.getName());

            if (CollUtil.isNotEmpty( createReqVO.getBuildList() ) && createReqVO.getBuildList().size() > 3) {
                throw exception(NUMBER_OF_BUILDINGS);
            }

            //判断园区类型
            if (createReqVO.getType() == null) {
                BusOrgDO orgByCode = orgService.getOrgByCode();
                if (orgByCode == null) {
                    throw exception(BUS_ORG_NOT_EXISTS);
                }
                createReqVO.setType(orgByCode.getType());
            }

            VillageDO village = new VillageDO();
            village.setName(createReqVO.getName());
            village.setShortName(createReqVO.getShortName());
            village.setDescribe(createReqVO.getDescribe());
            village.setLogo(createReqVO.getLogo());
            village.setDistrictTxt(createReqVO.getDistrictTxt());
            village.setAddress(createReqVO.getAddress());
            village.setLng(createReqVO.getLng());
            village.setLat(createReqVO.getLat());
            village.setTagIdArr(createReqVO.getTagIdArr());
            village.setProvinceId(createReqVO.getProvinceId());
            village.setCityId(createReqVO.getCityId());
            village.setDistrictId(createReqVO.getDistrictId());
            village.setStreetId(createReqVO.getStreetId());
            village.setCommunityId(createReqVO.getCommunityId());
            village.setType(createReqVO.getType());
            village.setExtraConfig(createReqVO.getExtraConfig());
            village.setSort(createReqVO.getSort());
            village.setVrLink(createReqVO.getVrLink());
            village.setVideo(createReqVO.getVideo());
            village.setThreeDimensionalFile(createReqVO.getThreeDimensionalFile());
            village.setRoomStatusColor(createReqVO.getRoomStatusColor());
            village.setDimensionalBgImg(createReqVO.getDimensionalBgImg());

            //创建部门信息
//            DeptRespDTO deptRespDTO = new DeptRespDTO();
//            deptRespDTO.setName( createReqVO.getName() );
//            deptRespDTO.setParentId( 0L );
//            deptRespDTO.setStatus( 0 );
//            CommonResult<Long> dept = depApi.createDept( deptRespDTO );
//            if(dept.isError()){
//                throw new ServiceException(406, "项目部门创建失败");
//            }

//            village.setOrgId(dept.getData());

            int insert = villageMapper.insert(village);
            if (insert < 1) {
                throw exception(CREATE_A_PROJECT);
            }

            ZoneDO villageZone = new ZoneDO();
            villageZone.setVillageId(village.getId());
            villageZone.setZoneNumber(1L);
            villageZone.setZoneName("一区");
            villageZone.setSort(1);
            villageZone.setStatus(1);

            if (zoneMapper.insert(villageZone) > 0 && createReqVO.getBuildList() != null) {
                for (BuildList buildItem : createReqVO.getBuildList()) {
                    BuildDO villageBuild = new BuildDO();
                    villageBuild.setVillageId(village.getId());
                    villageBuild.setZoneId(villageZone.getId());
                    villageBuild.setBuildNumber(String.valueOf(buildItem.getNumber()));
                    villageBuild.setBuildName(buildItem.getName());
                    villageBuild.setSort(buildItem.getSort());
                    villageBuild.setStatus(1);

                    //创建部门信息
//                    DeptRespDTO deptResp = new DeptRespDTO();
//                    deptResp.setName( buildItem.getName() );
//                    deptResp.setParentId( dept.getData() );
//                    deptResp.setStatus( 0 );
//                    CommonResult<Long> depts = depApi.createDept( deptResp );
//                    if(depts.isError()){
//                        throw new ServiceException(406, "楼栋部门创建失败");
//                    }
//                    villageBuild.setOrgId(depts.getData());
                    if (buildMapper.insert(villageBuild) > 0 && buildItem.getLayerList() != null) {
                        for (LayerList layerItem : buildItem.getLayerList()) {
                            LayerDO villageLayer = new LayerDO();
                            villageLayer.setVillageId(village.getId());
                            villageLayer.setZoneId(villageZone.getId());
                            villageLayer.setBuildId(villageBuild.getId());
                            villageLayer.setLayerNumber(layerItem.getNumber());
                            villageLayer.setLayerName(layerItem.getName());
                            villageLayer.setSort(layerItem.getSort());
                            villageLayer.setStatus(1);

                            if (layerMapper.insert(villageLayer) > 0 && layerItem.getRoomList() != null) {
                                for (RoomList roomItem : layerItem.getRoomList()) {
                                    RoomDO villageRoom = new RoomDO();
                                    villageRoom.setVillageId(village.getId());
                                    villageRoom.setZoneId(villageZone.getId());
                                    villageRoom.setBuildId(villageBuild.getId());
                                    villageRoom.setLayerId(villageLayer.getId());
                                    villageRoom.setRoomNumber(roomItem.getNumber());
                                    villageRoom.setRoomName(roomItem.getName());
                                    villageRoom.setSort(roomItem.getSort());
                                    villageRoom.setInvitationStatus(1);
                                    villageRoom.setStatus(1);

                                    roomMapper.insert(villageRoom);
                                }
                            }
                        }
                    }
                }
            }

            return "项目创建成功";
        } catch (Exception e) {
            throw new ServiceException(406, "数据处理出错: " + e.getMessage());
        }
    }

    @Override
    public BuildArrRespVO getVillageAndBuild(BuildArrReqVO reqVO) {
        BuildArrRespVO buildArrRespVO = new BuildArrRespVO();
        List<Long> villageIdArr = reqVO.getVillageIdArr();
        List<Long> buildArr = reqVO.getBuildArr();

        // 项目列表
        List<VillageDO> villageList = new ArrayList<>();
        if (CollUtil.isEmpty(villageIdArr) && CollUtil.isEmpty(buildArr)) {
            return buildArrRespVO;
        }

        for (Long aLong : villageIdArr) {
            VillageDO villageDO = villageMapper.selectById( aLong );
            villageList.add( villageDO );
        }

        villageList.sort(Comparator.comparing(VillageDO::getSort).thenComparing(VillageDO::getId).reversed());

        // 楼宇列表
        List<BuildDO> villageBuildList = new ArrayList<>();
        if (CollUtil.isNotEmpty(villageIdArr)) {
            if (villageIdArr.size() == 1) {
                villageBuildList = buildMapper.selectList(new LambdaQueryWrapper<BuildDO>().eq(BuildDO::getVillageId, villageIdArr.get(0)));
            } else {
                villageBuildList = buildMapper.selectList(new LambdaQueryWrapper<BuildDO>().in(BuildDO::getVillageId, villageIdArr));
            }
        }

        if (CollUtil.isNotEmpty(buildArr)) {
            villageBuildList.addAll(buildMapper.selectBatchIds(buildArr));
        }

        villageBuildList = villageBuildList.stream()
                .filter(build -> build.getStatus().equals(1))
                .sorted(Comparator.comparing(BuildDO::getSort).thenComparing(BuildDO::getBuildNumber))
                .collect(Collectors.toList());

        // 不合并数组
        if (!reqVO.isMergeData()) {
            if (CollUtil.isNotEmpty(villageBuildList)) {
                villageIdArr = villageBuildList.stream()
                        .map(BuildDO::getVillageId)
                        .distinct()
                        .collect(Collectors.toList());

                if (villageIdArr.size() == 1) {
                    villageList = List.of(villageMapper.selectById(villageIdArr.get(0)));
                } else {
                    villageList = villageMapper.selectBatchIds(villageIdArr);
                }

                villageList.sort(Comparator.comparing(VillageDO::getSort).thenComparing(VillageDO::getId).reversed());
            }

            buildArrRespVO.setVillageList(BeanUtils.toBean(villageList, VillageRespVO.class));
            buildArrRespVO.setVillageBuildList(BeanUtils.toBean(villageBuildList, BuildRespVO.class));
            return buildArrRespVO;
        }

        // 将楼宇拼接成项目ID为下标的集合中
        List<BuildRespVO> buildRespVOS = BeanUtils.toBean(villageBuildList, BuildRespVO.class);
        Map<Long, List<BuildRespVO>> villageBuildTmpList = buildRespVOS.stream()
                .collect(Collectors.groupingBy(BuildRespVO::getVillageId));

        // 将楼宇拼接到项目中
        List<VillageRespVO> villageRespVOS = BeanUtils.toBean(villageList, VillageRespVO.class);
        for (VillageRespVO village : villageRespVOS) {
            village.setBuildList(villageBuildTmpList.getOrDefault(village.getId(), Collections.emptyList()));
        }

        return buildArrRespVO.setVillageRespVOS(villageRespVOS);
    }

    /**
     *
     * @param parkName
     * @return
     */
    @Override
    public Long getParkNameId(String parkName) {
        LambdaQueryWrapper<VillageDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(VillageDO::getName, parkName);
        List<VillageDO> villageDOS = villageMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(villageDOS)) {
            return villageDOS.get(0).getId();
        }
        return null;
    }

    @Override
    public BuildArrRespVO villageAndBuildList(VillageReqVO villageReqVO) {
        BuildArrRespVO respVO = new BuildArrRespVO();
        LambdaQueryWrapperX<VillageDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eq( VillageDO::getType, villageReqVO.getType() );
        List<VillageDO> dos = villageMapper.selectList( wrapperX );
        if(CollUtil.isEmpty( dos )){
            throw new ServiceException(406, "机构下该项目类型还未绑定任何项目，请先绑定");
        }

        List<VillageRespVO> villageList = new ArrayList<>();
        List<BuildRespVO> villageBuildList = new ArrayList<>();
        List<VillageRespVO> villageRespVOS = new ArrayList<>();
        for (VillageDO villageDO : dos) {
            if(villageDO.getId() == null){
                continue;
            }
            VillageRespVO villageRespVO = BeanUtils.toBean( villageDO, VillageRespVO.class );
            villageList.add( villageRespVO );
            List<BuildDO> buildDOS = buildMapper.selectList( new LambdaQueryWrapper<BuildDO>().eq( BuildDO::getVillageId, villageDO.getId() ) );
            if(CollUtil.isNotEmpty( buildDOS )){
                List<BuildRespVO> buildRespVOS = BeanUtils.toBean( buildDOS, BuildRespVO.class );
                villageRespVO.setBuildList( buildRespVOS );
                villageBuildList.addAll( buildRespVOS );
            }
            villageRespVOS.add( villageRespVO );
        }
        respVO.setVillageList( villageList );
        respVO.setVillageBuildList( villageBuildList );
        respVO.setVillageRespVOS( villageRespVOS );

        //查询建筑集合
        LambdaQueryWrapperX<VillageCollectionDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq( VillageCollectionDO::getUid, SecurityFrameworkUtils.getLoginUserId() )
                                .eq( VillageCollectionDO::getVillageType, villageReqVO.getType() );
        List<VillageCollectionDO> collectionDOS = villageCollectionMapper.selectList( queryWrapperX );
        respVO.setVillageCollectionRespVOS( BeanUtils.toBean( collectionDOS, VillageCollectionRespVO.class) );
        /*查询用户选择的设置---保留代码后期如果做数据权限可以进行修改
        LambdaQueryWrapperX<SpercialSettingDO> queryWrapperX = new LambdaQueryWrapperX<SpercialSettingDO>();
        queryWrapperX.eq( SpercialSettingDO::getUid, SecurityFrameworkUtils.getLoginUserId() )
                        .eq( SpercialSettingDO::getType, UserSettingConstants.USER_BUILD_SET )
                                .eq( SpercialSettingDO::getOrgId, TenantContextHolder.getTenantId() );
        SpercialSettingDO spercialSettingDO = spercialSettingMapper.selectOne( queryWrapperX );
        if(spercialSettingDO == null){
            throw new ServiceException(500, "获取用户配置保存信息失败，请选择楼宇后重试");
        }
        String content = spercialSettingDO.getContent();
        // 使用 Jackson ObjectMapper 解析 JSON 字符串为 Map
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> dataMap = new HashMap<>();
        try {
            dataMap = objectMapper.readValue(content, new TypeReference<Map<String, Object>>() {});
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException(500, "获取用户配置信息失败，请选择楼宇后重试");
        }


        // 创建 BuildArrReqVO 对象并设置数据
        BuildArrReqVO buildArrReqVO = new BuildArrReqVO();

        // 提取并设置 villageId 和 buildId
        List<Long> villageIdList = objectMapper.convertValue(dataMap.get("villageId"), new TypeReference<List<Long>>() {});
        List<Long> buildIdList = objectMapper.convertValue(dataMap.get("buildId"), new TypeReference<List<Long>>() {});
        buildArrReqVO.setVillageIdArr(villageIdList);
        buildArrReqVO.setBuildArr(buildIdList);
        buildArrReqVO.setMergeData(true);
        getVillageAndBuild( buildArrReqVO )
         */

        return respVO;
    }

    @Override
    public CountDataVO projectManage(VillageReqVO villageReqVO) {
        CountDataVO countData = new CountDataVO();
        LambdaQueryWrapperX<VillageDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eq( VillageDO::getOrgId, TenantContextHolder.getTenantId())
                .eq( VillageDO::getType, villageReqVO.getType() );
        List<VillageDO> dos = villageMapper.selectList( wrapperX );
        if (dos.isEmpty()) {
            return countData;
        }

        // 统计数据初始化
        countData.setManagementArea(BigDecimal.ZERO);
        countData.setRentableArea(BigDecimal.ZERO);
        countData.setRoomCount(0);
        countData.setRoomRentableCount(0);
        countData.setRentableScale(BigDecimal.ZERO);
        countData.setMaxUseArea(BigDecimal.ZERO);
        countData.setUseLimitScale(BigDecimal.ZERO);
        countData.setMaxUseRoom(0);
        countData.setUseLimitRoomScale(BigDecimal.ZERO);

        BigDecimal totalManagementArea = BigDecimal.ZERO;
        BigDecimal totalRentableArea = BigDecimal.ZERO;
        int totalRoomCount = 0;
        int totalRoomRentableCount = 0;

        for (VillageDO item : dos) {
            BigDecimal managementArea = item.getManagementArea() != null ? item.getManagementArea() : BigDecimal.ZERO;
            BigDecimal rentableArea = item.getRentableArea() != null ? item.getRentableArea() : BigDecimal.ZERO;

            totalManagementArea = totalManagementArea.add(managementArea);
            totalRentableArea = totalRentableArea.add(rentableArea);
            totalRoomCount += item.getRoomCount() != null ? item.getRoomCount() : 0;
            totalRoomRentableCount += item.getRoomRentableCount() != null ? item.getRoomRentableCount() : 0;
        }

        countData.setManagementArea(formatNumber(totalManagementArea));
        countData.setRentableArea(formatNumber(totalRentableArea));
        countData.setRoomCount(totalRoomCount);
        countData.setRoomRentableCount(totalRoomRentableCount);

        BigDecimal totalRentableScale = totalManagementArea.compareTo(BigDecimal.ZERO) > 0
                ? totalRentableArea.divide(totalManagementArea, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100))
                : BigDecimal.ZERO;

        countData.setRentableScale(formatNumber(totalRentableScale));
        return countData;
    }

    private BigDecimal formatNumber(BigDecimal number) {
        return number.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public ProjectOverviewVO projectOverview(Long id) {
        ProjectOverviewVO projectOverviewVO = new ProjectOverviewVO();
        //查询项目
        VillageDO villageDO = villageMapper.selectOne( new LambdaQueryWrapperX<VillageDO>().eq( VillageDO::getId, id ) );
        if(villageDO == null){
            return projectOverviewVO;
        }
        //查询房间
        List<RoomDO> roomDOS = roomMapper.selectList( new LambdaQueryWrapperX<RoomDO>().eq( RoomDO::getVillageId, villageDO.getId() ) );
        if(CollUtil.isNotEmpty( roomDOS )){
            projectOverviewVO.setHousesNum( roomDOS.size() );
            Integer contractCount = 0;
            BigDecimal managementArea = BigDecimal.ZERO;
            BigDecimal averagePrice = BigDecimal.ZERO;
            BigDecimal builtArea = BigDecimal.ZERO;
            BigDecimal rentedArea = BigDecimal.ZERO;
            BigDecimal rentalRate = BigDecimal.ZERO;
            BigDecimal WaitingArea = BigDecimal.ZERO;
            BigDecimal squareDay = BigDecimal.ZERO;
            for (RoomDO roomDO : roomDOS) {
                if(roomDO.getRoomStatus() != null && roomDO.getRoomStatus() == 20){
                    contractCount += 1;
                    List<RoomPriceDO> roomPriceDOS = roomPriceMapper.selectList( new LambdaQueryWrapperX<RoomPriceDO>().eq( RoomPriceDO::getRoomId, roomDO.getId() ) );
                    if(CollUtil.isNotEmpty( roomPriceDOS )){
                        squareDay = squareDay.add( roomPriceDOS.get( 0 ).getSquareDay() == null? BigDecimal.ZERO : roomPriceDOS.get( 0 ).getSquareDay() );
                    }
                }
                builtArea =  builtArea.add( roomDO.getBuildArea() == null ? BigDecimal.ZERO : roomDO.getBuildArea());
                managementArea = managementArea.add( roomDO.getInsideArea() == null ? BigDecimal.ZERO : roomDO.getInsideArea());
                rentedArea = rentedArea.add( roomDO.getRentalAreaIn() == null ? BigDecimal.ZERO : roomDO.getRentalAreaIn());
                WaitingArea = WaitingArea.add( (roomDO.getRentalArea() == null ? BigDecimal.ZERO : roomDO.getRentalArea()).subtract( roomDO.getRentalAreaIn() == null ? BigDecimal.ZERO : roomDO.getRentalAreaIn() ));

            }

            projectOverviewVO.setContractCount( contractCount );
            projectOverviewVO.setManagementArea( managementArea );
            projectOverviewVO.setBuiltArea( builtArea );
            projectOverviewVO.setWaitingArea( WaitingArea );
            projectOverviewVO.setRentedArea( rentedArea );

            rentalRate = rentedArea.compareTo(BigDecimal.ZERO) > 0
                    ? rentedArea.divide(rentedArea.add( WaitingArea ), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100))
                    : BigDecimal.ZERO;
            projectOverviewVO.setRentalRate( rentalRate );
            averagePrice = squareDay.compareTo(BigDecimal.ZERO) > 0
                    ? squareDay.divide(new BigDecimal(contractCount), 2, BigDecimal.ROUND_HALF_UP)
                    : BigDecimal.ZERO;
            projectOverviewVO.setAveragePrice( averagePrice );
        }

        return projectOverviewVO;
    }
}