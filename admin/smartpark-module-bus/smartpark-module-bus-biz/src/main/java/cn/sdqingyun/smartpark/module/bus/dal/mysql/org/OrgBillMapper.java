package cn.sdqingyun.smartpark.module.bus.dal.mysql.org;

import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.BillCollectionAllListVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author lvzy
 * @Date 2024/7/15 15:20
 */
@Mapper
public interface OrgBillMapper {

    /**
     * 所有账单列表分页
     *
     * @param billCollectionAllListVO
     * @return
     */
    IPage<BillCollectionAllListVO> getBillCollectionAllListPage(
            Page<?> page,
            @Param("param") BillCollectionAllListVO billCollectionAllListVO);

    /**
     * 逾期账单列表分页
     *
     * @param billCollectionAllListVO
     * @return
     */
    IPage<BillCollectionAllListVO> getBillBeOverdueListPage(
            Page<?> page,
            @Param("param") BillCollectionAllListVO billCollectionAllListVO);
}
