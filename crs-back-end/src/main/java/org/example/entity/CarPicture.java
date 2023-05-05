package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class CarPicture {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer carId;
    private String imageUrl;
}
