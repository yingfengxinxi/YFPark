package cn.sdqingyun.smartpark.module.system.api.dept;

import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.util.collection.CollectionUtils;
import cn.sdqingyun.smartpark.module.system.api.dept.dto.DeptRespDTO;
import cn.sdqingyun.smartpark.module.system.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@FeignClient(name = ApiConstants.NAME) // TODO 芋艿：fallbackFactory =
@Tag(name = "RPC 服务 - 部门")
public interface DeptApi {

    String PREFIX = ApiConstants.PREFIX + "/dept";

    @GetMapping(PREFIX + "/get")
    @Operation(summary = "获得部门信息")
    @Parameter(name = "id", description = "部门编号", example = "1024", required = true)
    CommonResult<DeptRespDTO> getDept(@RequestParam("id") Long id);

    @GetMapping(PREFIX + "/list")
    @Operation(summary = "获得部门信息数组")
    @Parameter(name = "ids", description = "部门编号数组", example = "1,2", required = true)
    CommonResult<List<DeptRespDTO>> getDeptList(@RequestParam("ids") Collection<Long> ids);

    @GetMapping(PREFIX + "/valid")
    @Operation(summary = "校验部门是否合法")
    @Parameter(name = "ids", description = "部门编号数组", example = "1,2", required = true)
    CommonResult<Boolean> validateDeptList(@RequestParam("ids") Collection<Long> ids);

    /**
     * 获得指定编号的部门 Map
     *
     * @param ids 部门编号数组
     * @return 部门 Map
     */
    default Map<Long, DeptRespDTO> getDeptMap(Collection<Long> ids) {
        List<DeptRespDTO> list = getDeptList(ids).getCheckedData();
        return CollectionUtils.convertMap(list, DeptRespDTO::getId);
    }

    @GetMapping(PREFIX + "/list-child")
    @Operation(summary = "获得指定部门的所有子部门")
    @Parameter(name = "id", description = "部门编号", example = "1024", required = true)
    CommonResult<List<DeptRespDTO>> getChildDeptList(@RequestParam("id") Long id);

    @PostMapping(PREFIX +"create")
    @Operation(summary = "创建部门")
    CommonResult<Long> createDept(@RequestBody DeptRespDTO deptRespDTO);

    @GetMapping(PREFIX + "/updateDept")
    @Operation(summary = "修改部门名称")
    void updateDept(@RequestBody DeptRespDTO deptRespDTO);

    @GetMapping(PREFIX + "/deleteDept")
    @Operation(summary = "删除部门")
    @Parameter(name = "id", description = "部门编号", example = "1024", required = true)
    void deleteDept(@RequestParam("id") Long id);

    @GetMapping(PREFIX + "/getByName")
    @Operation(summary = "根据部门名称获得部门信息")
    @Parameter(name = "name", description = "部门名称", example = "1024", required = true)
    CommonResult<DeptRespDTO> getDeptByName(@RequestParam("name") String name);
}
