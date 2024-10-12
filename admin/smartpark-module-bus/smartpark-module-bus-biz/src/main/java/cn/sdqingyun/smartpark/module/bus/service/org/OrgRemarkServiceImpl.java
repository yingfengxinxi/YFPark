package cn.sdqingyun.smartpark.module.bus.service.org;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgRemarkPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgRemarkSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgRemarkDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgRemarkMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.systemUser.SystemUserMapper;
import com.alibaba.nacos.common.utils.CollectionUtils;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 机构业务备注 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class OrgRemarkServiceImpl implements OrgRemarkService {

    @Resource
    private OrgRemarkMapper Mapper;

    @Resource
    private SystemUserMapper systemUserMapper;

    @Override
    public Long create(OrgRemarkSaveReqVO createReqVO) {
        // 插入
        OrgRemarkDO orgRemarkDO = BeanUtils.toBean(createReqVO, OrgRemarkDO.class);
        Mapper.insert(orgRemarkDO);
        // 返回
        return orgRemarkDO.getId();
    }

    @Override
    public void update(OrgRemarkSaveReqVO updateReqVO) {
        // 更新
        OrgRemarkDO updateObj = BeanUtils.toBean(updateReqVO, OrgRemarkDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public OrgRemarkDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<OrgRemarkDO> getPage(OrgRemarkPageReqVO pageReqVO) {
        LambdaQueryWrapperX<OrgRemarkDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OrgRemarkDO::getBusinessId, pageReqVO.getBusinessId());
        queryWrapperX.eq(OrgRemarkDO::getType, pageReqVO.getType());
        PageResult<OrgRemarkDO> orgRemarkDOPageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        List<OrgRemarkDO> list = orgRemarkDOPageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(orgRemarkDO -> {
                String creator = orgRemarkDO.getCreator();
                String userName = systemUserMapper.getByOperatorIdUserName(Long.valueOf(creator));
                orgRemarkDO.setCreator(userName);
            });
        }
        return orgRemarkDOPageResult;
    }

}