package com.wiredcraft.testing.user.domain.ro;

import com.wiredcraft.testing.common.vo.BaseRequest;
import lombok.Data;

@Data
public class UserListRO extends BaseRequest {


    /**
     * user name
     */
    private String name;

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
