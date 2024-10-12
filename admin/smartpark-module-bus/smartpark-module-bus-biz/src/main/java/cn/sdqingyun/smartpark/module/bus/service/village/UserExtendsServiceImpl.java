package cn.sdqingyun.smartpark.module.bus.service.village;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.UserExtendsPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.UserExtendsSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.UserExtendsDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.UserFieldExtendDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.UserExtendsMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.UserFieldExtendMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.USER_EXTENDS_NOT_EXISTS;

/**
 * 用户信息扩展 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class UserExtendsServiceImpl implements UserExtendsService {

    @Resource
    private UserExtendsMapper userExtendsMapper;
    @Resource
    private UserFieldExtendMapper userFieldExtendMapper;

    @Override
    public Long createUserExtends(UserExtendsSaveReqVO createReqVO) {
        // 插入
        UserExtendsDO userExtends = BeanUtils.toBean(createReqVO, UserExtendsDO.class);
        userExtendsMapper.insert(userExtends);
        // 返回
        return userExtends.getId();
    }

    @Override
    public void updateUserExtends(UserExtendsSaveReqVO updateReqVO) {
        // 校验存在
        validateUserExtendsExists(updateReqVO.getId());
        // 更新
        UserExtendsDO updateObj = BeanUtils.toBean(updateReqVO, UserExtendsDO.class);
        userExtendsMapper.updateById(updateObj);
    }

    @Override
    public void deleteUserExtends(Long id) {
        // 校验存在
        validateUserExtendsExists(id);
        // 删除
        userExtendsMapper.deleteById(id);
    }

    private void validateUserExtendsExists(Long id) {
        if (userExtendsMapper.selectById(id) == null) {
            throw exception(USER_EXTENDS_NOT_EXISTS);
        }
    }

    @Override
    public UserExtendsDO getUserExtends(Long id) {
        return userExtendsMapper.selectById(id);
    }

    @Override
    public PageResult<UserExtendsDO> getUserExtendsPage(UserExtendsPageReqVO pageReqVO) {
        return userExtendsMapper.selectPage(pageReqVO);
    }

    @Override
    public Map<String, Object> getCountUserExtends(UserExtendsSaveReqVO createReqVO) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        LambdaQueryWrapperX<UserExtendsDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eqIfPresent(UserExtendsDO::getVillageId, createReqVO.getVillageId());
        List<UserExtendsDO> userExtendsDOS = userExtendsMapper.selectList( wrapperX );
        if(CollUtil.isEmpty( userExtendsDOS) ){
            return null;
        }

        LambdaQueryWrapperX<UserFieldExtendDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq( UserFieldExtendDO::getIsEnable,"1" );
        List<UserFieldExtendDO> fieldExtendDOS = userFieldExtendMapper.selectList( queryWrapperX );
        if(CollUtil.isEmpty( fieldExtendDOS )){
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        for (UserFieldExtendDO fieldExtendDO : fieldExtendDOS) {
            switch (fieldExtendDO.getFieldName()){
                case "houseType":
                    String defaultFieldValue = fieldExtendDO.getDefaultFieldValue();
                    List<Map<String, Object>> mapList = objectMapper.readValue(defaultFieldValue, new TypeReference<List<Map<String, Object>>>() {});
                    Map<Integer, Long> collect = userExtendsDOS.stream().collect( Collectors.groupingBy( UserExtendsDO::getNation, Collectors.counting() ) );
                    for (Map<String, Object> stringObjectMap : mapList) {
                        stringObjectMap.put( "count",collect.get( MapUtils.getInteger( stringObjectMap, "id" ) )==null?0:collect.get( MapUtils.getInteger( stringObjectMap, "id" )) );
                    }
                    map.put( "house_type",mapList );
                    break;
                case "retiree":
                    List<Map<String, Object>> retirees = objectMapper.readValue( fieldExtendDO.getDefaultFieldValue(), new TypeReference<List<Map<String, Object>>>() {
                    } );
                    Map<Integer, Long> retiree = userExtendsDOS.stream().collect( Collectors.groupingBy( UserExtendsDO::getRetiree, Collectors.counting() ) );
                    for (Map<String, Object> stringObjectMap : retirees) {
                        stringObjectMap.put( "count",retiree.get( MapUtils.getInteger( stringObjectMap, "id" ) )==null?0:retiree.get( MapUtils.getInteger( stringObjectMap, "id" )) );
                    }
                    map.put( "retiree",retirees );
                    break;
                case "natureEmployment":
                    List<Map<String, Object>> natureEmployments = objectMapper.readValue( fieldExtendDO.getDefaultFieldValue(), new TypeReference<List<Map<String, Object>>>() {
                    } );
                    Map<Integer, Long> natureEmployment = userExtendsDOS.stream().collect( Collectors.groupingBy( UserExtendsDO::getNatureEmployment, Collectors
                            .counting() ) );
                    for (Map<String, Object> stringObjectMap : natureEmployments) {
                        stringObjectMap.put( "count",natureEmployment.get( MapUtils.getInteger( stringObjectMap, "id" ) )==null?0:natureEmployment.get( MapUtils.getInteger( stringObjectMap, "id" )) );
                    }
                    map.put( "nature_employment",natureEmployments );
                case "communityGroup":
                    List<Map<String, Object>> communityGroups = objectMapper.readValue( fieldExtendDO.getDefaultFieldValue(), new TypeReference<List<Map<String, Object>>>() {
                    } );
                    Map<Integer, Long> communityGroup = userExtendsDOS.stream().collect( Collectors.groupingBy( UserExtendsDO::getNatureEmployment, Collectors
                            .counting() ) );
                    for (Map<String, Object> stringObjectMap : communityGroups) {
                        stringObjectMap.put( "count",communityGroup.get( MapUtils.getInteger( stringObjectMap, "id" ) )==null?0:communityGroup.get( MapUtils.getInteger( stringObjectMap, "id" )) );
                    }
                    map.put( "nature_employment",communityGroups );
                case "foucusChildren":
                    List<Map<String, Object>> foucusChildrens = objectMapper.readValue( fieldExtendDO.getDefaultFieldValue(), new TypeReference<List<Map<String, Object>>>() {
                    } );
                    Map<Integer, Long> foucusChildren = userExtendsDOS.stream().collect( Collectors.groupingBy( UserExtendsDO::getNatureEmployment, Collectors
                            .counting() ) );
                    for (Map<String, Object> stringObjectMap : foucusChildrens) {
                        stringObjectMap.put( "count",foucusChildren.get( MapUtils.getInteger( stringObjectMap, "id" ) )==null?0:foucusChildren.get( MapUtils.getInteger( stringObjectMap, "id" )) );
                    }
                    map.put( "foucus_children",foucusChildrens );
                    break;
                case "isDibaohu":
                    List<Map<String, Object>> isDibaohus = objectMapper.readValue( fieldExtendDO.getDefaultFieldValue(), new TypeReference<List<Map<String, Object>>>() {
                    } );
                    Map<Integer, Long> isDibaohu = userExtendsDOS.stream().collect( Collectors.groupingBy( UserExtendsDO::getNatureEmployment, Collectors
                            .counting() ) );
                    for (Map<String, Object> stringObjectMap : isDibaohus) {
                        stringObjectMap.put( "count",isDibaohu.get( MapUtils.getInteger( stringObjectMap, "id" ) )==null?0:isDibaohu.get( MapUtils.getInteger( stringObjectMap, "id" )) );
                    }
                    map.put( "is_dibaohu",isDibaohus );
                    break;
                case "insuranceType":
                    List<Map<String, Object>> insuranceTypes = objectMapper.readValue( fieldExtendDO.getDefaultFieldValue(), new TypeReference<List<Map<String, Object>>>() {
                    } );
                    Map<Integer, Long> insuranceType = userExtendsDOS.stream().collect( Collectors.groupingBy( UserExtendsDO::getNatureEmployment, Collectors
                            .counting() ) );
                    for (Map<String, Object> stringObjectMap : insuranceTypes) {
                        stringObjectMap.put( "count",insuranceType.get( MapUtils.getInteger( stringObjectMap, "id" ) )==null?0:insuranceType.get( MapUtils.getInteger( stringObjectMap, "id" )) );
                    }
                    map.put( "insurance_type",insuranceTypes );
                    break;
                case "religiousBelief":
                    List<Map<String, Object>> religiousBeliefs = objectMapper.readValue( fieldExtendDO.getDefaultFieldValue(), new TypeReference<List<Map<String, Object>>>() {
                    } );
                    Map<Integer, Long> religiousBelief = userExtendsDOS.stream().collect( Collectors.groupingBy( UserExtendsDO::getNatureEmployment, Collectors
                            .counting() ) );
                    for (Map<String, Object> stringObjectMap : religiousBeliefs) {
                        stringObjectMap.put( "count",religiousBelief.get( MapUtils.getInteger( stringObjectMap, "id" ) )==null?0:religiousBelief.get( MapUtils.getInteger( stringObjectMap, "id" )) );
                    }
                    map.put( "religious_belief",religiousBeliefs );
                    break;
                case "bloodType":
                    List<Map<String, Object>> bloodTypes = objectMapper.readValue( fieldExtendDO.getDefaultFieldValue(), new TypeReference<List<Map<String, Object>>>() {
                    } );
                    Map<Integer, Long> bloodType = userExtendsDOS.stream().collect( Collectors.groupingBy( UserExtendsDO::getNatureEmployment, Collectors
                            .counting() ) );
                    for (Map<String, Object> stringObjectMap : bloodTypes) {
                        stringObjectMap.put( "count",bloodType.get( MapUtils.getInteger( stringObjectMap, "id" ) )==null?0:bloodType.get( MapUtils.getInteger( stringObjectMap, "id" )) );
                    }
                    map.put( "blood_type",bloodTypes );
                    break;
                case "education":
                    List<Map<String, Object>> educations = objectMapper.readValue( fieldExtendDO.getDefaultFieldValue(), new TypeReference<List<Map<String, Object>>>() {
                    } );
                    Map<Integer, Long> education = userExtendsDOS.stream().collect( Collectors.groupingBy( UserExtendsDO::getNatureEmployment, Collectors
                            .counting() ) );
                    for (Map<String, Object> stringObjectMap : educations) {
                        stringObjectMap.put( "count",education.get( MapUtils.getInteger( stringObjectMap, "id" ) )==null?0:education.get( MapUtils.getInteger( stringObjectMap, "id" )) );
                    }
                    map.put( "education",educations );
                    break;
                case "politicalStatus":
                    List<Map<String, Object>> politicalStatuss = objectMapper.readValue( fieldExtendDO.getDefaultFieldValue(), new TypeReference<List<Map<String, Object>>>() {
                    } );
                    Map<Integer, Long> politicalStatus = userExtendsDOS.stream().collect( Collectors.groupingBy( UserExtendsDO::getNatureEmployment, Collectors
                            .counting() ) );
                    for (Map<String, Object> stringObjectMap : politicalStatuss) {
                        stringObjectMap.put( "count",politicalStatus.get( MapUtils.getInteger( stringObjectMap, "id" ) )==null?0:politicalStatus.get( MapUtils.getInteger( stringObjectMap, "id" )) );
                    }
                    map.put( "political_status",politicalStatuss );
                    break;
                case "nation":
                    List<Map<String, Object>> nations = objectMapper.readValue( fieldExtendDO.getDefaultFieldValue(), new TypeReference<List<Map<String, Object>>>() {
                    } );
                    Map<Integer, Long> nation = userExtendsDOS.stream().collect( Collectors.groupingBy( UserExtendsDO::getNatureEmployment, Collectors
                            .counting() ) );
                    for (Map<String, Object> stringObjectMap : nations) {
                        stringObjectMap.put( "count",nation.get( MapUtils.getInteger( stringObjectMap, "id" ) )==null?0:nation.get( MapUtils.getInteger( stringObjectMap, "id" )) );
                    }
                    map.put( "nation",nations );
                    break;
                case "careLevel":
                    List<Map<String, Object>> careLevels = objectMapper.readValue( fieldExtendDO.getDefaultFieldValue(), new TypeReference<List<Map<String, Object>>>() {
                    } );
                    Map<Integer, Long> careLevel = userExtendsDOS.stream().collect( Collectors.groupingBy( UserExtendsDO::getNatureEmployment, Collectors
                            .counting() ) );
                    for (Map<String, Object> stringObjectMap : careLevels) {
                        stringObjectMap.put( "count",careLevel.get( MapUtils.getInteger( stringObjectMap, "id" ) )==null?0:careLevel.get( MapUtils.getInteger( stringObjectMap, "id" )) );
                    }
                    map.put( "care_level",careLevels );
                    break;
                case "maritalStatus":
                    List<Map<String, Object>> maritalStatuss = objectMapper.readValue( fieldExtendDO.getDefaultFieldValue(), new TypeReference<List<Map<String, Object>>>() {
                    } );
                    Map<Integer, Long> maritalStatus = userExtendsDOS.stream().collect( Collectors.groupingBy( UserExtendsDO::getNatureEmployment, Collectors
                            .counting() ) );
                    for (Map<String, Object> stringObjectMap : maritalStatuss) {
                        stringObjectMap.put( "count",maritalStatus.get( MapUtils.getInteger( stringObjectMap, "id" ) )==null?0:maritalStatus.get( MapUtils.getInteger( stringObjectMap, "id" )) );
                    }
                    map.put( "marital_status",maritalStatuss );
                    break;
                case "custody":
                    List<Map<String, Object>> custodys = objectMapper.readValue( fieldExtendDO.getDefaultFieldValue(), new TypeReference<List<Map<String, Object>>>() {
                    } );
                    Map<Integer, Long> custody = userExtendsDOS.stream().collect( Collectors.groupingBy( UserExtendsDO::getNatureEmployment, Collectors
                            .counting() ) );
                    for (Map<String, Object> stringObjectMap : custodys) {
                        stringObjectMap.put( "count",custody.get( MapUtils.getInteger( stringObjectMap, "id" ) )==null?0:custody.get( MapUtils.getInteger( stringObjectMap, "id" )) );
                    }
                    map.put( "custody",custodys );
                    break;
                case "livingConditions":
                    List<Map<String, Object>> livingConditionss = objectMapper.readValue( fieldExtendDO.getDefaultFieldValue(), new TypeReference<List<Map<String, Object>>>() {
                    } );
                    Map<Integer, Long> livingConditions = userExtendsDOS.stream().collect( Collectors.groupingBy( UserExtendsDO::getNatureEmployment, Collectors
                            .counting() ) );
                    for (Map<String, Object> stringObjectMap : livingConditionss) {
                        stringObjectMap.put( "count",livingConditions.get( MapUtils.getInteger( stringObjectMap, "id" ) )==null?0:livingConditions.get( MapUtils.getInteger( stringObjectMap, "id" )) );
                    }
                    map.put( "living_conditions",livingConditionss );
                    break;
                case "serviceType":
                    List<Map<String, Object>> serviceTypes = objectMapper.readValue( fieldExtendDO.getDefaultFieldValue(), new TypeReference<List<Map<String, Object>>>() {
                    } );
                    Map<Integer, Long> serviceType = userExtendsDOS.stream().collect( Collectors.groupingBy( UserExtendsDO::getNatureEmployment, Collectors
                            .counting() ) );
                    for (Map<String, Object> stringObjectMap : serviceTypes) {
                        stringObjectMap.put( "count",serviceType.get( MapUtils.getInteger( stringObjectMap, "id" ) )==null?0:serviceType.get( MapUtils.getInteger( stringObjectMap, "id" )) );
                    }
                    map.put( "custody",serviceTypes );
                    break;
                case "failure":
                    List<Map<String, Object>> failures = objectMapper.readValue( fieldExtendDO.getDefaultFieldValue(), new TypeReference<List<Map<String, Object>>>() {
                    } );
                    Map<Integer, Long> failure = userExtendsDOS.stream().collect( Collectors.groupingBy( UserExtendsDO::getNatureEmployment, Collectors
                            .counting() ) );
                    for (Map<String, Object> stringObjectMap : failures) {
                        stringObjectMap.put( "count",failure.get( MapUtils.getInteger( stringObjectMap, "id" ) )==null?0:failure.get( MapUtils.getInteger( stringObjectMap, "id" )) );
                    }
                    map.put( "failure",failures );
                    break;
                case "chronic":
                    List<Map<String, Object>> chronics = objectMapper.readValue( fieldExtendDO.getDefaultFieldValue(), new TypeReference<List<Map<String, Object>>>() {
                    } );
                    Map<Integer, Long> chronic = userExtendsDOS.stream().collect( Collectors.groupingBy( UserExtendsDO::getNatureEmployment, Collectors
                            .counting() ) );
                    for (Map<String, Object> stringObjectMap : chronics) {
                        stringObjectMap.put( "count",chronic.get( MapUtils.getInteger( stringObjectMap, "id" ) )==null?0:chronic.get( MapUtils.getInteger( stringObjectMap, "id" )) );
                    }
                    map.put( "chronic",chronics );
                    break;
                case "disabled":
                    List<Map<String, Object>> disableds = objectMapper.readValue( fieldExtendDO.getDefaultFieldValue(), new TypeReference<List<Map<String, Object>>>() {
                    } );
                    Map<Integer, Long> disabled = userExtendsDOS.stream().collect( Collectors.groupingBy( UserExtendsDO::getNatureEmployment, Collectors
                            .counting() ) );
                    for (Map<String, Object> stringObjectMap : disableds) {
                        stringObjectMap.put( "count",disabled.get( MapUtils.getInteger( stringObjectMap, "id" ) )==null?0:disabled.get( MapUtils.getInteger( stringObjectMap, "id" )) );
                    }
                    map.put( "chronic",disableds );
                    break;
                default:
                    break;
            }
        }

        return map;
    }

    @Override
    public UserExtendsDO getUserExtendsByUser(Long villageUserId) {
        return userExtendsMapper.selectOne( new LambdaQueryWrapperX<UserExtendsDO>().eq( UserExtendsDO::getVillageUserId, villageUserId) );
    }
}