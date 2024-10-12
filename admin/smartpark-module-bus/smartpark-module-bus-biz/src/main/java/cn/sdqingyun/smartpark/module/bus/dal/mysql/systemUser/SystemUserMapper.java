package cn.sdqingyun.smartpark.module.bus.dal.mysql.systemUser;

import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.DeptPostIdUserListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/6/24 9:35
 */
@Mapper
public interface SystemUserMapper {


    /**
     * @param operatorId
     * @return
     */
    String getByOperatorIdUserName(@Param("operatorId") Long operatorId);

    /**
     * @param operatorName
     * @return
     */
    String getByOperatorNameUserId(@Param("operatorName") String operatorName);

    /**
     * @param mobile
     * @return
     */
    String getByMobileUserId(@Param("mobile") String mobile);

    /**
     *
     * @param deptIdList
     * @param postIdList
     * @return
     */
    String getByDeptPostIdListId(@Param("deptIdList") List<String> deptIdList,@Param("postIdList") List<String> postIdList);



    /**
     *
     * @param deptIdList
     * @param postIdList
     * @return
     */
    List<DeptPostIdUserListVO> getDeptPostIdUserList(@Param("deptIdList") List<String> deptIdList, @Param("postIdList") List<String> postIdList);
}
