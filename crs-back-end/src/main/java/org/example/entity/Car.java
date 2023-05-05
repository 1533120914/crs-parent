package org.example.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Car {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String carName;
    private Integer brandId;
    private String carType;
    private Integer seatNum;
    private Double acceCapa;
    private Double oilWear;
    private Double dailyRent;
    private Integer state;
    @TableLogic // 逻辑删除
    @TableField(fill = FieldFill.INSERT)
    private Integer isDelete;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime modifyDate;
}
