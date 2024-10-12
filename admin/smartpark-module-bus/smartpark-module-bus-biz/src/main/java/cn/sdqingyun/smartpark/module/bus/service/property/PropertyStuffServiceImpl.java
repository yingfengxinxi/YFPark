package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.common.util.qrcode.QRCodeUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyStuffMapper;
import cn.sdqingyun.smartpark.module.bus.framework.utils.coverUtils.CodeUtil;
import cn.sdqingyun.smartpark.module.infra.api.file.FileApi;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.Map;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PROPERTY_STUFF_NOT_EXISTS;

/**
 * 耗材档案信息 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyStuffServiceImpl implements PropertyStuffService {

    @Resource
    private PropertyStuffMapper propertyStuffMapper;
    @Resource
    private FileApi fileApi;

    @Override
    public Long createPropertyStuff(PropertyStuffSaveReqVO createReqVO) {
        // 插入
        PropertyStuffDO propertyStuff = BeanUtils.toBean(createReqVO, PropertyStuffDO.class);
        propertyStuff.setNumber( CodeUtil.generateUniquecode() );
        propertyStuffMapper.insert(propertyStuff);

        //生成查询二维码进行保存
        byte[] bytes = QRCodeUtils.generateQRCode( propertyStuff.getId().toString(), 350, 350 );
        String file = fileApi.createFile( bytes );
        propertyStuff.setBarCode( file );
        propertyStuffMapper.updateById( propertyStuff );
        // 返回
        return propertyStuff.getId();
    }

    @Override
    public void updatePropertyStuff(PropertyStuffSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyStuffExists(updateReqVO.getId());
        // 更新
        PropertyStuffDO updateObj = BeanUtils.toBean(updateReqVO, PropertyStuffDO.class);
        propertyStuffMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyStuff(Long id) {
        // 校验存在
        validatePropertyStuffExists(id);
        // 删除
        propertyStuffMapper.deleteById(id);
    }

    private void validatePropertyStuffExists(Long id) {
        if (propertyStuffMapper.selectById(id) == null) {
            throw exception(PROPERTY_STUFF_NOT_EXISTS);
        }
    }

    @Override
    public PropertyStuffDO getPropertyStuff(Long id) {
        return propertyStuffMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyStuffDO> getPropertyStuffPage(PropertyStuffPageReqVO pageReqVO) {
        return propertyStuffMapper.selectPage(pageReqVO);
    }

    @Override
    public Map<String, Object> getHightLow() {
        Map<String, Object> resultMap = new HashMap<>(16);

        // 获取高于安全库存的物料
        resultMap.put("high", propertyStuffMapper.getHightLow( "high" ));

        // 获取低于安全库存的物料
        resultMap.put("low", propertyStuffMapper.getHightLow( "low" ));

        return resultMap;
    }

    @Override
    public PageResult<PropertyStuffRespVO> getPropertyStuffPageVO(PropertyStuffPageReqVO pageReqVO) {
        PageResult<PropertyStuffDO> pageResult = propertyStuffMapper.selectPage( pageReqVO );
        if( pageResult == null || CollectionUtils.isEmpty( pageResult.getList())){
            return PageResult.empty();
        }

        PageResult<PropertyStuffRespVO> result = BeanUtils.toBean( pageResult, PropertyStuffRespVO.class );

        return result;
    }
}