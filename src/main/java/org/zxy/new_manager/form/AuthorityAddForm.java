package org.zxy.new_manager.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AuthorityAddForm {

    @NotNull
    private Integer applicationId;

    @NotNull
    private String userId;

    @NotNull
    private Long legalTime;

}
