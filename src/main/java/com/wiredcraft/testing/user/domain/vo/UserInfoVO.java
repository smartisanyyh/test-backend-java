package com.wiredcraft.testing.user.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInfoVO {

    private Integer id;

    /**
     * user name
     */
    private String name;


    /**
     * user date of birth
     */
    private LocalDateTime dob;

    /**
     * user address
     */
    private String address;

    /**
     * user description
     */
    private String description;

}
