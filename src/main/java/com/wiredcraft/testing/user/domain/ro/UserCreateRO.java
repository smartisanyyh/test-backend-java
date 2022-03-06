package com.wiredcraft.testing.user.domain.ro;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
@Data
public class UserCreateRO {


    /**
     * user name
     */
    @NotBlank(message = "name must not be empty")
    private String name;

    /**
     * user password
     */
    @NotBlank(message = "pwd must not be empty")
    private String pwd;

    /**
     * user date of birth
     */
    @NotNull(message = "user date of birth must not be empty")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate dob;

    /**
     * user address
     */
    @NotBlank(message = "address must not be empty")
    private String address;

    /**
     * user description
     */
    @NotBlank(message = "description must not be empty")
    private String description;


    /**
     * user longitude 经度
     */
    @NotNull(message = "longitude must not be empty")
    private Double longitude;


    /**
     * user latitude 纬度
     */
    @NotNull(message = "latitude must not be empty")
    private Double latitude;

}
