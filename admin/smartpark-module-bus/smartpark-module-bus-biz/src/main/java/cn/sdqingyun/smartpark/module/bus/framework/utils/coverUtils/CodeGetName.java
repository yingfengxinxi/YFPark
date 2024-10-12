package cn.sdqingyun.smartpark.module.bus.framework.utils.coverUtils;

import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.module.system.api.dept.DeptApi;
import cn.sdqingyun.smartpark.module.system.api.dept.dto.DeptRespDTO;
import cn.sdqingyun.smartpark.module.system.api.user.AdminUserApi;
import cn.sdqingyun.smartpark.module.system.api.user.dto.AdminUserRespDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @ClassName CodeGetName
 * @Description TODO
 * @Author SUNk
 * @Date 2024/9/13 10:46
 * @ModifyDate 2024/9/13 10:46
 * @Version 1.0
 */
@Component
public class CodeGetName {
    @Resource
    private DeptApi deptApi;
    @Resource
    private AdminUserApi adminUserApi;

    public String getDeptName(Long id){
        CommonResult<DeptRespDTO> dept = deptApi.getDept( id );
        if(dept.isSuccess() && dept.getData() != null){
            return dept.getData().getName();
        }
        return null;
    }

    public String getUserName(Long id){
        CommonResult<AdminUserRespDTO> user = adminUserApi.getUser( id );
        if(user.isSuccess() && user.getData() != null){
            return user.getData().getNickname();
        }
        return null;
    }
}
