package org.zxy.new_manager.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ApiUpdateForm {

    @NotNull
    private Integer apiId;

//    @NotNull
//    private Integer applicationId;

    @NotEmpty
    private String apiName;

    @NotEmpty
    private String apiValue;

//    @NotEmpty
    private String remark;
}
