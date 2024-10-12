package cn.sdqingyun.smartpark.module.system.service.notify;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.QueryWrapperX;
import cn.sdqingyun.smartpark.module.system.controller.admin.notify.vo.message.NotifyMessageMyPageReqVO;
import cn.sdqingyun.smartpark.module.system.controller.admin.notify.vo.message.NotifyMessagePageReqVO;
import cn.sdqingyun.smartpark.module.system.dal.dataobject.notify.NotifyMessageDO;
import cn.sdqingyun.smartpark.module.system.dal.dataobject.notify.NotifyTemplateDO;
import cn.sdqingyun.smartpark.module.system.dal.mysql.notify.NotifyMessageMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 站内信 Service 实现类
 *
 * @author xrcoder
 */
@Service
@Validated
public class NotifyMessageServiceImpl implements NotifyMessageService {

    @Resource
    private NotifyMessageMapper notifyMessageMapper;

    @Override
    public Long createNotifyMessage(Long userId, Integer userType,
                                    NotifyTemplateDO template, String templateContent, Map<String, Object> templateParams) {
        NotifyMessageDO message = new NotifyMessageDO().setUserId(userId).setUserType(userType)
                .setTemplateId(template.getId()).setTemplateCode(template.getCode())
                .setTemplateType(template.getType()).setTemplateNickname(template.getNickname())
                .setTemplateContent(templateContent).setTemplateParams(templateParams).setReadStatus(false);
        notifyMessageMapper.insert(message);
        return message.getId();
    }

    @Override
    public PageResult<NotifyMessageDO> getNotifyMessagePage(NotifyMessagePageReqVO pageReqVO) {
        return notifyMessageMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<NotifyMessageDO> getMyMyNotifyMessagePage(NotifyMessageMyPageReqVO pageReqVO, Long userId, Integer userType) {
        return notifyMessageMapper.selectPage(pageReqVO, userId, userType);
    }

    @Override
    public NotifyMessageDO getNotifyMessage(Long id) {
        return notifyMessageMapper.selectById(id);
    }

    @Override
    public List<NotifyMessageDO> getUnreadNotifyMessageList(NotifyMessagePageReqVO notifyMessagePageReqVO) {

        LambdaQueryWrapperX<NotifyMessageDO>queryWrapperX=new LambdaQueryWrapperX<>();
        queryWrapperX.eq(NotifyMessageDO::getUserId,notifyMessagePageReqVO.getUserId());
        queryWrapperX.eq(NotifyMessageDO::getUserType,notifyMessagePageReqVO.getUserType());
        queryWrapperX.orderByDesc(NotifyMessageDO::getId);
        PageResult<NotifyMessageDO> notifyMessageDOPageResult = notifyMessageMapper.selectPage(notifyMessagePageReqVO, queryWrapperX);
        return notifyMessageDOPageResult.getList();
    }

    @Override
    public Long getUnreadNotifyMessageCount(Long userId, Integer userType) {
        return notifyMessageMapper.selectUnreadCountByUserIdAndUserType(userId, userType);
    }

    @Override
    public int updateNotifyMessageRead(Collection<Long> ids, Long userId, Integer userType) {
        return notifyMessageMapper.updateListRead(ids, userId, userType);
    }

    @Override
    public int updateAllNotifyMessageRead(Long userId, Integer userType) {
        return notifyMessageMapper.updateListRead(userId, userType);
    }

}
