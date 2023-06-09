package org.example.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Salesman extends Account{
    private String sex;
    private String contact;
    private Double sal;
    private Integer isLock;
    private Integer state;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime modifyDate;
}
