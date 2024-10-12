package cn.sdqingyun.smartpark.module.bus.service.village;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.security.core.util.SecurityFrameworkUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillageCollectionPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillageCollectionRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillageCollectionSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.villagebuild.BuildArrReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.villagebuild.BuildArrRespVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageCollectionDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.BuildMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.VillageCollectionMapper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.VILLAGE_COLLECTION_NOT_EXISTS;

/**
 * 项目集合表，方便快速选择 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class VillageCollectionServiceImpl implements VillageCollectionService {

    @Resource
    private VillageCollectionMapper villageCollectionMapper;
    @Resource
    private VillageService villageService;
    @Resource
    private BuildMapper buildMapper;

    @Override
    public Long createVillageCollection(VillageCollectionSaveReqVO createReqVO) {
        // 插入
        VillageCollectionDO villageCollection = BeanUtils.toBean(createReqVO, VillageCollectionDO.class);
        villageCollectionMapper.insert(villageCollection);
        // 返回
        return villageCollection.getId();
    }

    @Override
    public void updateVillageCollection(VillageCollectionSaveReqVO updateReqVO) {
        // 校验存在
        validateVillageCollectionExists(updateReqVO.getId());
        // 更新
        VillageCollectionDO updateObj = BeanUtils.toBean(updateReqVO, VillageCollectionDO.class);
        villageCollectionMapper.updateById(updateObj);
    }

    @Override
    public void deleteVillageCollection(Long id) {
        // 校验存在
        validateVillageCollectionExists(id);
        // 删除
        villageCollectionMapper.deleteById(id);
    }

    private void validateVillageCollectionExists(Long id) {
        if (villageCollectionMapper.selectById(id) == null) {
            throw exception(VILLAGE_COLLECTION_NOT_EXISTS);
        }
    }

    @Override
    public VillageCollectionDO getVillageCollection(Long id) {
        return villageCollectionMapper.selectById(id);
    }

    @Override
    public PageResult<VillageCollectionDO> getVillageCollectionPage(VillageCollectionPageReqVO pageReqVO) {
        return villageCollectionMapper.selectPage(pageReqVO);
    }

    @Override
    public List<VillageCollectionRespVO> getVillageCollectionList(VillageCollectionRespVO reqVO) {
        List<VillageCollectionRespVO> vos = new ArrayList<>();
        // 查询收藏列表
        LambdaQueryWrapperX<VillageCollectionDO> wrapperX = new LambdaQueryWrapperX<VillageCollectionDO>()
                .eqIfPresent( VillageCollectionDO::getUid, SecurityFrameworkUtils.getLoginUserId() )
                .likeIfPresent( VillageCollectionDO::getCollectionName, reqVO.getCollectionName() )
                .eqIfPresent( VillageCollectionDO::getCollectionBuild, reqVO.getCollectionBuild() )
                .eqIfPresent( VillageCollectionDO::getVillageType, reqVO.getVillageType() )
                .orderByDesc( VillageCollectionDO::getId );

        List<VillageCollectionDO> collectionList = villageCollectionMapper.selectList( wrapperX );
        if(CollUtil.isEmpty( collectionList )){
            return vos;
        }
        List<VillageCollectionRespVO> villageCollectionRespVOS = BeanUtils.toBean( collectionList, VillageCollectionRespVO.class );

        for (VillageCollectionRespVO collection : villageCollectionRespVOS) {
            // 从village服务读取项目和楼栋数据
            if(StringUtils.isNoneBlank(collection.getCollectionBuild())){
                List<Long> buildIds = parseBuildIds(collection.getCollectionBuild());
                BuildArrReqVO buildArrReqVO = new BuildArrReqVO();
                buildArrReqVO.setVillageIdArr(new ArrayList<>());
                buildArrReqVO.setBuildArr(buildIds);
                buildArrReqVO.setMergeData( false );
                BuildArrRespVO villageAndBuild = villageService.getVillageAndBuild( buildArrReqVO );
                collection.setVillageAndBuild( villageAndBuild );
            }
        }
        return villageCollectionRespVOS;
    }

    private List<Long> parseBuildIds(String collectionBuild) {
        List<Long> buildIds = new ArrayList<>();
        if (collectionBuild != null && !collectionBuild.isEmpty()) {
            JSONArray jsonArray = JSON.parseArray(collectionBuild);
            for (Object o : jsonArray) {
                buildIds.add( Long.parseLong(o.toString()));
            }
        }
        return buildIds;
    }

    @Override
    public PageResult<VillageCollectionRespVO> getVillageCollectionRespVOPage(VillageCollectionPageReqVO pageReqVO) {
        PageResult<VillageCollectionDO> result = villageCollectionMapper.selectPage( pageReqVO );
        PageResult<VillageCollectionRespVO> pageResult = BeanUtils.toBean( result, VillageCollectionRespVO.class );
        if(CollUtil.isNotEmpty( pageResult.getList() )){
            for (VillageCollectionRespVO respVO : pageResult.getList()) {
                if(StringUtils.isNoneBlank(respVO.getCollectionBuild())){
                    // 解析数组字符串
                    JSONArray jsonArray = JSON.parseArray(respVO.getCollectionBuild());
                    StringBuilder stringBuilder = new StringBuilder();
                    for (Object o : jsonArray) {
                        BuildDO buildDO = buildMapper.selectById( Long.parseLong( o.toString() ) );
                        if (buildDO != null){
                            stringBuilder.append( buildDO.getBuildName() ).append( "," );
                        }
                    }
                    if(stringBuilder != null){
                        respVO.setCollectionBuildName( String.valueOf( stringBuilder ).substring( 0, stringBuilder.length() - 1 ) );
                    }
                }
            }
        }

        return pageResult;
    }
}