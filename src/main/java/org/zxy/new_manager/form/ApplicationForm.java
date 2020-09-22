package org.zxy.new_manager.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ApplicationForm {

    @NotEmpty
    private String applicationName;

    @NotEmpty
    private String url;

    @NotEmpty
    private String remark;
}
