package cn.sdqingyun.smartpark.module.system.service.dept;

import cn.sdqingyun.smartpark.framework.common.util.collection.CollectionUtils;
import cn.sdqingyun.smartpark.module.system.controller.admin.dept.vo.dept.DeptListReqVO;
import cn.sdqingyun.smartpark.module.system.controller.admin.dept.vo.dept.DeptSaveReqVO;
import cn.sdqingyun.smartpark.module.system.controller.admin.dept.vo.dept.DeptSimpleRespVO;
import cn.sdqingyun.smartpark.module.system.dal.dataobject.dept.DeptDO;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 部门 Service 接口
 *
 * @author 智慧园区
 */
public interface DeptService {

    /**
     * 创建部门
     *
     * @param createReqVO 部门信息
     * @return 部门编号
     */
    Long createDept(DeptSaveReqVO createReqVO);

    /**
     * 更新部门
     *
     * @param updateReqVO 部门信息
     */
    void updateDept(DeptSaveReqVO updateReqVO);

    /**
     * 删除部门
     *
     * @param id 部门编号
     */
    void deleteDept(Long id);

    /**
     * 获得部门信息
     *
     * @param id 部门编号
     * @return 部门信息
     */
    DeptDO getDept(Long id);

    /**
     * 获得部门信息数组
     *
     * @param ids 部门编号数组
     * @return 部门信息数组
     */
    List<DeptDO> getDeptList(Collection<Long> ids);

    /**
     * 筛选部门列表
     *
     * @param reqVO 筛选条件请求 VO
     * @return 部门列表
     */
    List<DeptDO> getDeptList(DeptListReqVO reqVO);


    List<DeptSimpleRespVO> getDeptUserList(DeptListReqVO reqVO);

    /**
     * 获得指定编号的部门 Map
     *
     * @param ids 部门编号数组
     * @return 部门 Map
     */
    default Map<Long, DeptDO> getDeptMap(Collection<Long> ids) {
        List<DeptDO> list = getDeptList(ids);
        return CollectionUtils.convertMap(list, DeptDO::getId);
    }

    /**
     * 获得指定部门的所有子部门
     *
     * @param id 部门编号
     * @return 子部门列表
     */
    List<DeptDO> getChildDeptList(Long id);

    /**
     * 获得所有子部门，从缓存中
     *
     * @param id 父部门编号
     * @return 子部门列表
     */
    Set<Long> getChildDeptIdListFromCache(Long id);

    /**
     * 校验部门们是否有效。如下情况，视为无效：
     * 1. 部门编号不存在
     * 2. 部门被禁用
     *
     * @param ids 角色编号数组
     */
    void validateDeptList(Collection<Long> ids);

    /**
    * @Author SUNk
    * @Description 根据部门名称获取相关部门
    * @Date 16:35 2024/8/19
    * @Param [name]
    * @return cn.sdqingyun.smartpark.module.system.dal.dataobject.dept.DeptDO
    **/
    DeptDO getDeptByName(String name);
}
