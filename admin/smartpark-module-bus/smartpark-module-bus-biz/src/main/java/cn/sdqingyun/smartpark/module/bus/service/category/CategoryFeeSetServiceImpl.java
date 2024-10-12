package cn.sdqingyun.smartpark.module.bus.service.category;

import cn.sdqingyun.smartpark.framework.common.util.date.DateUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo.CategoryFeeSetPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo.CategoryFeeSetReservationDateSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo.CategoryFeeSetRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo.CategoryFeeSetSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.category.CategoryFeeSetDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.category.CategoryFeeSetReservationDateDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.category.CategoryFeeSetMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.category.CategoryFeeSetReservationDateMapper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 工单子类费用设置 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class CategoryFeeSetServiceImpl implements CategoryFeeSetService {

    @Resource
    private CategoryFeeSetMapper Mapper;

    @Resource
    private CategoryFeeSetReservationDateMapper reservationDateMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(CategoryFeeSetSaveReqVO createReqVO) throws Exception {
        LambdaQueryWrapperX<CategoryFeeSetDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(CategoryFeeSetDO::getSubcatId, createReqVO.getSubcatId());
        CategoryFeeSetDO feeSet = Mapper.selectOne(queryWrapperX);
        // 插入
        CategoryFeeSetDO categoryFeeSetDO = BeanUtils.toBean(createReqVO, CategoryFeeSetDO.class);
        if (feeSet != null) {
            categoryFeeSetDO.setId(feeSet.getId());
            Mapper.updateById(categoryFeeSetDO);
        } else {
            Mapper.insert(categoryFeeSetDO);
        }
        LambdaQueryWrapperX<CategoryFeeSetReservationDateDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
        queryWrapperX1.eq(CategoryFeeSetReservationDateDO::getFeeSetId, categoryFeeSetDO.getId());
        if (StringUtils.equals(categoryFeeSetDO.getIsUse(), "1")) {
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
            CategoryFeeSetReservationDateSaveReqVO reservationDateSave = createReqVO.getReservationDateSave();
            //启用时间段模式
            List<CategoryFeeSetReservationDateDO> categoryFeeSetReservationDateList = reservationDateMapper.selectList(queryWrapperX1);
            if (CollectionUtils.isNotEmpty(categoryFeeSetReservationDateList)) {
                String orderStartTime = reservationDateSave.getOrderStartTime();
                String orderEndTime = reservationDateSave.getOrderEndTime();
                List<String> days = DateUtils.getDays(sim.parse(orderStartTime), sim.parse(orderEndTime));
                for (String orderTime : days) {
                    LambdaQueryWrapperX<CategoryFeeSetReservationDateDO> queryWrapperX2 = new LambdaQueryWrapperX<>();
                    queryWrapperX2.eq(CategoryFeeSetReservationDateDO::getFeeSetId, categoryFeeSetDO.getId());
                    queryWrapperX2.eq(CategoryFeeSetReservationDateDO::getOrderTime, orderTime);
                    CategoryFeeSetReservationDateDO categoryFeeSetReservationDate = reservationDateMapper.selectOne(queryWrapperX2);
                    if (categoryFeeSetReservationDate != null) {
                        CategoryFeeSetReservationDateDO categoryFeeSetReservationDateDO = BeanUtils.toBean(reservationDateSave, CategoryFeeSetReservationDateDO.class);
                        categoryFeeSetReservationDateDO.setOrderTime(sim.parse(orderTime));
                        categoryFeeSetReservationDateDO.setId(categoryFeeSetReservationDate.getId());
                        reservationDateMapper.updateById(categoryFeeSetReservationDateDO);
                    } else {
                        CategoryFeeSetReservationDateDO categoryFeeSetReservationDateDO = BeanUtils.toBean(reservationDateSave, CategoryFeeSetReservationDateDO.class);
                        categoryFeeSetReservationDateDO.setOrderTime(sim.parse(orderTime));
                        categoryFeeSetReservationDateDO.setFeeSetId(categoryFeeSetDO.getId());
                        reservationDateMapper.insert(categoryFeeSetReservationDateDO);
                    }
                }

            } else {
                //新增

                String orderStartTime = reservationDateSave.getOrderStartTime();
                String orderEndTime = reservationDateSave.getOrderEndTime();
                List<String> days = DateUtils.getDays(sim.parse(orderStartTime), sim.parse(orderEndTime));
                for (String orderTime : days) {
                    CategoryFeeSetReservationDateDO categoryFeeSetReservationDateDO = BeanUtils.toBean(reservationDateSave, CategoryFeeSetReservationDateDO.class);
                    categoryFeeSetReservationDateDO.setOrderTime(sim.parse(orderTime));
                    categoryFeeSetReservationDateDO.setFeeSetId(categoryFeeSetDO.getId());
                    reservationDateMapper.insert(categoryFeeSetReservationDateDO);
                }
            }
        } else {
            //删除时段
            reservationDateMapper.delete(queryWrapperX1);
        }


        // 返回
        return categoryFeeSetDO.getId();
    }


    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public CategoryFeeSetDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<CategoryFeeSetDO> getPage(CategoryFeeSetPageReqVO pageReqVO) {
        LambdaQueryWrapperX<CategoryFeeSetDO> queryWrapperX = new LambdaQueryWrapperX<>();
        return Mapper.selectPage(pageReqVO, queryWrapperX);
    }

    @Override
    public CategoryFeeSetRespVO getFeeSet(Long subcatId, String date) {
        LambdaQueryWrapperX<CategoryFeeSetDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(CategoryFeeSetDO::getSubcatId, subcatId);
        CategoryFeeSetDO categoryFeeSetDO = Mapper.selectOne(queryWrapperX);
        CategoryFeeSetRespVO categoryFeeSetRespVO = BeanUtils.toBean(categoryFeeSetDO, CategoryFeeSetRespVO.class);
        if (categoryFeeSetRespVO != null) {
            LambdaQueryWrapperX<CategoryFeeSetReservationDateDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
            queryWrapperX1.eq(CategoryFeeSetReservationDateDO::getFeeSetId, categoryFeeSetRespVO.getId());
            queryWrapperX1.like(CategoryFeeSetReservationDateDO::getOrderTime, date);
            queryWrapperX1.orderByAsc(CategoryFeeSetReservationDateDO::getOrderTime);
            List<CategoryFeeSetReservationDateDO> feeSetReservationDateList = reservationDateMapper.selectList(queryWrapperX1);
            if(CollectionUtils.isNotEmpty(feeSetReservationDateList)){
                categoryFeeSetRespVO.setReservationDateList(feeSetReservationDateList);
            }
        }
        return categoryFeeSetRespVO;
    }


}