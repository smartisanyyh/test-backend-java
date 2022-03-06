package com.wiredcraft.testing.user.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInfoVO {

    private Long id;

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


    /**
     * user longitude 经度
     */
    private Double longitude;


    /**
     * user latitude 纬度
     */
    private Double latitude;

}
