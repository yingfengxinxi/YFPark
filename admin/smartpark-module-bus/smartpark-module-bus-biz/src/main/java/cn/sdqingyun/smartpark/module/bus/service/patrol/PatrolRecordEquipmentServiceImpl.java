package cn.sdqingyun.smartpark.module.bus.service.patrol;

import cn.sdqingyun.smartpark.framework.mybatis.core.util.MyBatisUtils;
import cn.sdqingyun.smartpark.framework.tenant.core.context.TenantContextHolder;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.BillListVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.GetBillListVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolPlanPositionDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolRecordEquipmentDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.patrol.PatrolPlanPositionMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.patrol.PatrolRecordEquipmentMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.systemUser.SystemUserMapper;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.stream.Collectors;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 应用巡检记录 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PatrolRecordEquipmentServiceImpl implements PatrolRecordEquipmentService {

    @Resource
    private PatrolRecordEquipmentMapper Mapper;


    @Resource
    private PropertyMapper propertyMapper;

    @Resource
    private SystemUserMapper systemUserMapper;

    @Resource
    private PatrolPlanPositionMapper patrolPlanPositionMapper;

    @Override
    public Long create(PatrolRecordEquipmentSaveReqVO createReqVO) {
        // 插入
        PatrolRecordEquipmentDO patrolRecordEquipmentDO = BeanUtils.toBean(createReqVO, PatrolRecordEquipmentDO.class);
        Mapper.insert(patrolRecordEquipmentDO);
        // 返回
        return patrolRecordEquipmentDO.getId();
    }

    @Override
    public void update(PatrolRecordEquipmentSaveReqVO updateReqVO) {

        // 更新
        PatrolRecordEquipmentDO updateObj = BeanUtils.toBean(updateReqVO, PatrolRecordEquipmentDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {
        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public PatrolRecordEquipmentDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<PatrolRecordEquipmentRespVO> getPage(PatrolRecordEquipmentPageReqVO pageReqVO) {
        LambdaQueryWrapper<PatrolRecordEquipmentDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PatrolRecordEquipmentDO::getApplication,pageReqVO.getApplication());
        if (StringUtils.isNotEmpty(pageReqVO.getStatus())) {
            queryWrapper.eq(PatrolRecordEquipmentDO::getStatus, pageReqVO.getStatus());
        }

        if (StringUtils.isNotEmpty(pageReqVO.getAddress())) {
            queryWrapper.like(PatrolRecordEquipmentDO::getAddress, pageReqVO.getAddress());
        }
        queryWrapper.orderByDesc(PatrolRecordEquipmentDO::getCreateTime);
        PageResult<PatrolRecordEquipmentDO> pageResult = Mapper.selectPage(pageReqVO, queryWrapper);
        PageResult<PatrolRecordEquipmentRespVO> respVOPageResult = BeanUtils.toBean(pageResult, PatrolRecordEquipmentRespVO.class);
        List<PatrolRecordEquipmentRespVO> list = respVOPageResult.getList();
        if(CollectionUtils.isNotEmpty(list)){
            list.forEach(patrolRecordEquipmentRespVO -> {
                String propertyList = patrolRecordEquipmentRespVO.getPropertyList();
                PropertyDO propertyDO = propertyMapper.selectById(propertyList);
                if(propertyDO!=null){
                    patrolRecordEquipmentRespVO.setProperty(propertyDO);
                }
                Long uid = patrolRecordEquipmentRespVO.getUid();
                if(uid!=null){
                    String userName = systemUserMapper.getByOperatorIdUserName(uid);
                    patrolRecordEquipmentRespVO.setName(userName);
                }

            });
        }

        return respVOPageResult;

    }

    @Override
    public PageResult<WarnRecordStaticVO> warnRecordStatic(WarnRecordStaticVO warnRecordStaticVO) {

        warnRecordStaticVO.setTenantId(TenantContextHolder.getTenantId());
        warnRecordStaticVO.setStatus("3,4");
        if (StringUtils.isNotEmpty(warnRecordStaticVO.getType()) && !StringUtils.equals(warnRecordStaticVO.getType(), "0")) {
            if (StringUtils.equals(warnRecordStaticVO.getType(), "1")) {
                //已整改
                warnRecordStaticVO.setStatus("4");
            } else {
                //未整改
                warnRecordStaticVO.setStatus("3");
            }
        }

        IPage<WarnRecordStaticVO> warnRecordStaticVOIPage = Mapper.warnRecordStatic(MyBatisUtils.buildPage(warnRecordStaticVO), warnRecordStaticVO);

        return new PageResult<>(warnRecordStaticVOIPage.getRecords(), warnRecordStaticVOIPage.getTotal());
    }


    @Override
    public List<PostStationStaticVO> postStationStatic(PostStationStaticVO postStationStaticVO) {
        postStationStaticVO.setTenantId(TenantContextHolder.getTenantId());
        List<PostStationStaticVO> list = Mapper.postStationStatic(postStationStaticVO);
        List<PostStationStaticVO> newList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(list)) {

            Map<Long, List<PostStationStaticVO>> stationMap = list.stream().collect(Collectors.groupingBy(PostStationStaticVO::getStationId));
            for (Long stationId : stationMap.keySet()) {
                List<PostStationStaticVO> postStationStaticList = stationMap.get(stationId);
                PostStationStaticVO ps = new PostStationStaticVO();
                ps.setShouldTotal(postStationStaticList.size());
                ps.setStationName(list.stream().filter(aa -> aa.getStationId() == stationId).collect(Collectors.toList()).get(0).getStationName());
                //实际巡检次数
                int actualTotal = postStationStaticList.stream().filter(aa -> StringUtils.equals(aa.getStatus(), "2") || StringUtils.equals(aa.getStatus(), "3") || StringUtils.equals(aa.getStatus(), "4") || StringUtils.equals(aa.getStatus(), "5")).collect(Collectors.toList()).size();
                ps.setActualTotal(actualTotal);

                //正常巡检次数
                int normalTotal = postStationStaticList.stream().filter(aa -> StringUtils.equals(aa.getStatus(), "2")).collect(Collectors.toList()).size();
                ps.setNormalTotal(normalTotal);

                //异常巡检次数
                int abnormalTotal = postStationStaticList.stream().filter(aa -> StringUtils.equals(aa.getStatus(), "3") || StringUtils.equals(aa.getStatus(), "4")).collect(Collectors.toList()).size();
                ps.setAbnormalTotal(abnormalTotal);

                //正常巡检次数
                int skipTotal = postStationStaticList.stream().filter(aa -> StringUtils.equals(aa.getStatus(), "5")).collect(Collectors.toList()).size();
                ps.setSkipTotal(skipTotal);
                newList.add(ps);
            }

        }
        return newList;
    }

}