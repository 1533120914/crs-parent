package org.example.entity.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class CarParam extends Page {
    // 查询条件1, 汽车名称, 支持模糊查询
    private String carName;
    // 查询条件2, 品牌id
    private Integer brandId;
    // 查询条件3: 最高日租金
    private Double highDailyRent;
    // 查询条件3: 最低日租金
    private Double lowDailyRent;
}
