package org.zxy.new_manager.data;

import lombok.Data;

import java.io.Serializable;

@Data
public class App implements Serializable {

    private static final long serialVersionUID = 1212962723751115553L;

    private Integer applicationId;

    private String applicationName;

    private String url;

    private String remark;

    private Long legalTime;
}
