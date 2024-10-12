package cn.sdqingyun.smartpark.module.bus.dal.mysql.energy;


import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyPreRechargeRecordListPageVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyPreRechargeRecordDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 水电表预充值记录 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface EnergyPreRechargeRecordMapper extends BaseMapperX<EnergyPreRechargeRecordDO> {


    IPage<EnergyPreRechargeRecordListPageVO> getListPage(Page<?> page, @Param("param") EnergyPreRechargeRecordListPageVO energyPreRechargeRecordListPageVO);
}