package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.villagebuild;

import lombok.Data;

import java.util.List;

/**
 * @ClassName ProjectBuild
 * @Description TODO
 * @Author SUNk
 * @Date 2024/6/26 16:26
 * @ModifyDate 2024/6/26 16:26
 * @Version 1.0
 */
@Data
public class ProjectBuild {
    private Long id;
    private String type;
    private List<Long> build;
}
