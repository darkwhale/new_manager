package org.zxy.new_manager.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserLoginForm {

    @NotEmpty
    private String account;

    @NotEmpty
    private String password;
}
