package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import lombok.Data;
import org.apache.ibatis.annotations.DeleteProvider;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/7/11 17:45
 */
@Data
public class CostTypeChildrenVO {

    private Long value;
    private String label;
    private List<CostTypeChildrenVO> children;

}
