package cn.sdqingyun.smartpark.module.bus.dal.mysql.energy;


import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyListPageVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 自定义 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface EnergyMapper extends BaseMapperX<EnergyDO> {


    IPage<EnergyListPageVO> getEnergyListPage(Page<?> page, @Param("param") EnergyListPageVO energyRecordListPageVO);
}