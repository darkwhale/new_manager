package org.zxy.new_manager.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ManagerForm {

    @NotEmpty
    private String managerId;

    @NotEmpty
    private String password;
}
