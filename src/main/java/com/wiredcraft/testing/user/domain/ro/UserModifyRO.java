package com.wiredcraft.testing.user.domain.ro;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class UserModifyRO {


    @NotNull(message = "id must not be null")
    private Long id;

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
    @NotNull(message = "dob must not be empty")
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
    private Double longitude;


    /**
     * user latitude 纬度
     */
    private Double latitude;


}
