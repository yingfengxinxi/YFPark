package cn.sdqingyun.smartpark.module.bus.service.resv;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.common.util.qrcode.QRCodeUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvPlaceCategoryPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvPlaceCategorySaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.ResvPlaceCategoryDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.resv.ResvPlaceCategoryMapper;
import cn.sdqingyun.smartpark.module.infra.api.file.FileApi;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.io.FileOutputStream;
import java.io.IOException;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.RESV_PLACE_CATEGORY_NOT_EXISTS;

/**
 * 预约场地分类 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class ResvPlaceCategoryServiceImpl implements ResvPlaceCategoryService {

    @Resource
    private ResvPlaceCategoryMapper resvPlaceCategoryMapper;
    @Resource
    private FileApi fileApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createResvPlaceCategory(ResvPlaceCategorySaveReqVO createReqVO) {
        // 插入
        ResvPlaceCategoryDO resvPlaceCategory = BeanUtils.toBean(createReqVO, ResvPlaceCategoryDO.class);
        resvPlaceCategoryMapper.insert(resvPlaceCategory);

        //生成二维码并保存
        byte[] bytes = QRCodeUtils.generateQRCode( resvPlaceCategory.getId().toString(), 350, 350 );
        String file = fileApi.createFile( bytes );

        //回更文件路径
        resvPlaceCategory.setQrCode( file );
        resvPlaceCategoryMapper.updateById( resvPlaceCategory );

        return resvPlaceCategory.getId();
    }

    @Override
    public void updateResvPlaceCategory(ResvPlaceCategorySaveReqVO updateReqVO) {
        // 校验存在
        validateResvPlaceCategoryExists(updateReqVO.getId());
        // 更新
        ResvPlaceCategoryDO updateObj = BeanUtils.toBean(updateReqVO, ResvPlaceCategoryDO.class);
        resvPlaceCategoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteResvPlaceCategory(Long id) {
        // 校验存在
        validateResvPlaceCategoryExists(id);
        // 删除
        resvPlaceCategoryMapper.deleteById(id);
    }

    private void validateResvPlaceCategoryExists(Long id) {
        if (resvPlaceCategoryMapper.selectById(id) == null) {
            throw exception(RESV_PLACE_CATEGORY_NOT_EXISTS);
        }
    }

    @Override
    public ResvPlaceCategoryDO getResvPlaceCategory(Long id) {
        return resvPlaceCategoryMapper.selectById(id);
    }

    @Override
    public PageResult<ResvPlaceCategoryDO> getResvPlaceCategoryPage(ResvPlaceCategoryPageReqVO pageReqVO) {
        return resvPlaceCategoryMapper.selectPage(pageReqVO);
    }

}