package cn.sdqingyun.smartpark.module.bus.api.property;

import cn.sdqingyun.smartpark.module.bus.service.property.PropertyService;
import cn.sdqingyun.smartpark.module.bus.service.property.PropertyStuffStockService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName PropertyApiImpl
 * @Description 更新审批清单审批状态
 * @Author SUNk
 * @Date 2024/8/14 14:41
 * @ModifyDate 2024/8/14 14:41
 * @Version 1.0
 */
@RestController
@Validated
public class PropertyApiImpl implements PropertyApi{
    @Resource
    private PropertyService propertyService;
    @Resource
    private PropertyStuffStockService propertyStuffStockService;
    @Override
    public void updatePropertyStatus(String id, Integer status) {
        propertyService.updatePropertyStatus( id, status );
    }

    @Override
    public void updatePropertyStuffStatus(String id, Integer status) {
        propertyStuffStockService.updatePropertyStuffStatus( id, status );
    }
}
