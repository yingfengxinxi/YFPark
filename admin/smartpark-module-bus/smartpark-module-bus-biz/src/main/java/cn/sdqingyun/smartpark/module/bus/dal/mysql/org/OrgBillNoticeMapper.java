package cn.sdqingyun.smartpark.module.bus.dal.mysql.org;


import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.NoticeListVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillNoticeDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 收款通知记录 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface OrgBillNoticeMapper extends BaseMapperX<OrgBillNoticeDO> {

    /**
     *
     * @param page
     * @param noticeListVO
     * @return
     */
    IPage<NoticeListVO> getNoticeListPage(Page<?> page, @Param("param") NoticeListVO noticeListVO);

}