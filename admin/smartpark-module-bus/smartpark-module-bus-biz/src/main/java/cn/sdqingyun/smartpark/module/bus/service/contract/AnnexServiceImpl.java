package cn.sdqingyun.smartpark.module.bus.service.contract;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.AnnexPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.AnnexSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.AnnexDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractAnnexMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.systemUser.SystemUserMapper;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.ANNEX_NOT_EXISTS;

/**
 * 机构合同附件 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class AnnexServiceImpl implements AnnexService {

    @Resource
    private ContractAnnexMapper annexMapper;

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Override
    public Long createAnnex(AnnexSaveReqVO createReqVO) {
        // 插入
        AnnexDO annex = BeanUtils.toBean(createReqVO, AnnexDO.class);
        annexMapper.insert(annex);
        // 返回
        return annex.getId();
    }

    @Override
    public void updateAnnex(AnnexSaveReqVO updateReqVO) {
        // 校验存在
        validateAnnexExists(updateReqVO.getId());
        // 更新
        AnnexDO updateObj = BeanUtils.toBean(updateReqVO, AnnexDO.class);
        annexMapper.updateById(updateObj);
    }

    @Override
    public void deleteAnnex(Long id) {
        // 校验存在
        validateAnnexExists(id);
        // 删除
        annexMapper.deleteById(id);
    }

    private void validateAnnexExists(Long id) {
        if (annexMapper.selectById(id) == null) {
            throw exception(ANNEX_NOT_EXISTS);
        }
    }

    @Override
    public AnnexDO getAnnex(Long id) {
        return annexMapper.selectById(id);
    }

    /**
     *
     * @param pageReqVO 分页查询
     * @return
     */
    @Override
    public PageResult<AnnexDO> getAnnexPage(AnnexPageReqVO pageReqVO) {
        PageResult<AnnexDO> annexDOPageResult = annexMapper.selectPage(pageReqVO);
        List<AnnexDO> list = annexDOPageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.stream().forEach(annexDO -> {
                String creator = annexDO.getCreator();
                String nickname = systemUserMapper.getByOperatorIdUserName(Long.valueOf(creator));
                if (StringUtils.isNotEmpty(nickname)) {
                    annexDO.setCreator(nickname);
                }
            });
        }
        return annexDOPageResult;
    }

    /**
     * @param contractId
     */
    @Override
    public void deleteByContractId(Long contractId) {

        LambdaQueryWrapper<AnnexDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AnnexDO::getContractId, contractId);
        annexMapper.delete(queryWrapper);
    }

    /**
     * @param contractId
     * @return
     */
    @Override
    public List<AnnexDO> getByContractIdAnnexList(Long contractId) {
        LambdaQueryWrapper<AnnexDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AnnexDO::getContractId, contractId);
        return annexMapper.selectList(queryWrapper);
    }

}