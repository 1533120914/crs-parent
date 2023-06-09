package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Brand {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String brandName;
}
