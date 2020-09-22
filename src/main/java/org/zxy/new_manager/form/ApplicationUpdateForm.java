package org.zxy.new_manager.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ApplicationUpdateForm {

    @NotNull
    private Integer applicationId;

    @NotEmpty
    private String applicationName;

    @NotEmpty
    private String url;

    @NotEmpty
    private String remark;
}
