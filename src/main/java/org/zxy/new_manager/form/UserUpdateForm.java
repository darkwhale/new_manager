package org.zxy.new_manager.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserUpdateForm {

    @NotEmpty
    private String apartment;

    @NotEmpty
    private String personName;
}
