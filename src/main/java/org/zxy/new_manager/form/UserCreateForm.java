package org.zxy.new_manager.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserCreateForm {

    @NotEmpty
    private String account;

    @NotEmpty
    private String password;

    @NotEmpty
    private String apartment;

    @NotEmpty
    private String personName;

}
