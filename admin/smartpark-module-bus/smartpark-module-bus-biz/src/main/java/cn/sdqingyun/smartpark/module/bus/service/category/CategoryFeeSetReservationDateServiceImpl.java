package cn.sdqingyun.smartpark.module.bus.service.category;

import cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo.CategoryFeeSetReservationDateSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.category.CategoryFeeSetReservationDateDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.category.CategoryFeeSetReservationDateMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 工单子类费用预约时段设置 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class CategoryFeeSetReservationDateServiceImpl implements CategoryFeeSetReservationDateService {

    @Resource
    private CategoryFeeSetReservationDateMapper Mapper;

    @Override
    public Long create(CategoryFeeSetReservationDateSaveReqVO createReqVO) {
        // 插入
        CategoryFeeSetReservationDateDO categoryFeeSetReservationDateDO = BeanUtils.toBean(createReqVO, CategoryFeeSetReservationDateDO.class);
        Mapper.insert(categoryFeeSetReservationDateDO);
        // 返回
        return categoryFeeSetReservationDateDO.getId();
    }

    @Override
    public void update(CategoryFeeSetReservationDateSaveReqVO updateReqVO) {

        // 更新
        CategoryFeeSetReservationDateDO updateObj = BeanUtils.toBean(updateReqVO, CategoryFeeSetReservationDateDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public CategoryFeeSetReservationDateDO get(Long id) {
        return Mapper.selectById(id);
    }


}