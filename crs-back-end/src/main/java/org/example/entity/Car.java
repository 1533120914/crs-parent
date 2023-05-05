package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
}
