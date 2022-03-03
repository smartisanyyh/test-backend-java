package com.wiredcraft.testing.user.domain.ro;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginRO {

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




}
