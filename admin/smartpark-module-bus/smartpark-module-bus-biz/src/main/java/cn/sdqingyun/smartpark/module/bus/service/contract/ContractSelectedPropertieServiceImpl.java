package cn.sdqingyun.smartpark.module.bus.service.contract;

import cn.sdqingyun.smartpark.framework.common.util.bill.Tools;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractSelectedPropertiePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractSelectedPropertieSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractSelectedPropertieDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractSelectedPropertieMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.BuildMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.RoomMapper;
import cn.sdqingyun.smartpark.module.bus.service.village.VillageService;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import java.math.BigDecimal;
import java.util.List;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.SELECTED_PROPERTIE_NOT_EXISTS;

/**
 * 合同中选中房源 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class ContractSelectedPropertieServiceImpl implements ContractSelectedPropertieService {

    @Resource
    private ContractSelectedPropertieMapper selectedPropertieMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private BuildMapper buildMapper;



    /**
     *
     * @param contractId
     */
    @Override
    public void updateByContractIdStatus(Long contractId) {

        selectedPropertieMapper.updateByContractIdStatus(contractId);
    }

    @Override
    public void deleteSelectedPropertie(Long id) {
        // 校验存在
        validateSelectedPropertieExists(id);
        // 删除
        selectedPropertieMapper.deleteById(id);
    }

    @Override
    public void deleteByContractId(Long contractId) {
        LambdaQueryWrapper<ContractSelectedPropertieDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ContractSelectedPropertieDO::getContractId, contractId);
        selectedPropertieMapper.delete(queryWrapper);
    }

    private void validateSelectedPropertieExists(Long id) {
        if (selectedPropertieMapper.selectById(id) == null) {
            throw exception(SELECTED_PROPERTIE_NOT_EXISTS);
        }
    }

    @Override
    public ContractSelectedPropertieDO getSelectedPropertie(Long id) {
        return selectedPropertieMapper.selectById(id);
    }

    @Override
    public PageResult<ContractSelectedPropertieDO> getSelectedPropertiePage(ContractSelectedPropertiePageReqVO pageReqVO) {
        return selectedPropertieMapper.selectPage(pageReqVO);
    }

    @Override
    public String getRoomIdArea(Long villageRoomId) {

        //已租面积
        String roomIdArea = selectedPropertieMapper.getRoomIdArea(villageRoomId);
        BigDecimal insideArea = roomMapper.selectById(villageRoomId).getInsideArea();
        BigDecimal kczArea = insideArea.subtract(new BigDecimal(roomIdArea));
        return Tools.DecimalFormat(kczArea);
    }

    /**
     *
     * @param contractId
     * @return
     */
    @Override
    public List<ContractSelectedPropertieDO> getContractIdRoomInfoList(Long contractId) {
        LambdaQueryWrapper<ContractSelectedPropertieDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ContractSelectedPropertieDO::getContractId, contractId);
        List<ContractSelectedPropertieDO> contractSelectedPropertieDOS = selectedPropertieMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(contractSelectedPropertieDOS)) {
            contractSelectedPropertieDOS.forEach(contractSelectedPropertieDO -> {
                BuildDO buildDO = buildMapper.selectById(contractSelectedPropertieDO.getBuildId());
                if (buildDO != null) {
                    contractSelectedPropertieDO.setBuildName(buildDO.getBuildName());
                }
                RoomDO roomDO = roomMapper.selectById(contractSelectedPropertieDO.getVillageRoomId());
                if (roomDO != null) {
                    String roomName = roomDO.getRoomName();
                    contractSelectedPropertieDO.setRoomName(roomName);
                    String lc = roomName.substring(0, roomName.length() - 2); // 截取左边的数据
                    contractSelectedPropertieDO.setFloor(Integer.valueOf(lc));
                }
            });
        }
        return contractSelectedPropertieDOS;
    }

}