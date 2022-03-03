package com.wiredcraft.testing.user.domain.po;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * user
 *
 * @TableName biz_user
 */
@TableName(value = "biz_user")
@Data
public class BizUser implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * user name
     */
    private String name;

    /**
     * user password
     */
    private String pwd;

    /**
     * user date of birth
     */
    private LocalDate dob;

    /**
     * user address
     */
    private String address;

    /**
     * user description
     */
    private String description;

    /**
     * user created date
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * update_at
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateAt;

    /**
     * version
     */
    private Integer version;

    /**
     * is_delete
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}