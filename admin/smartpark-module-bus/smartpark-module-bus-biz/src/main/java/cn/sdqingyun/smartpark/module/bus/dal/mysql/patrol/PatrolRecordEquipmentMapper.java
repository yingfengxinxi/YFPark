package cn.sdqingyun.smartpark.module.bus.dal.mysql.patrol;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PostStationStaticVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.WarnRecordStaticVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolRecordEquipmentDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 应用巡检记录 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface PatrolRecordEquipmentMapper extends BaseMapperX<PatrolRecordEquipmentDO> {

    /**
     *
     * @param page
     * @param warnRecordStaticVO
     * @return
     */
    IPage<WarnRecordStaticVO> warnRecordStatic(Page<?> page, @Param("param") WarnRecordStaticVO warnRecordStaticVO);

    /**
     *
     * @param postStationStaticVO
     * @return
     */
    List<PostStationStaticVO> postStationStatic(@Param("param") PostStationStaticVO postStationStaticVO);
}