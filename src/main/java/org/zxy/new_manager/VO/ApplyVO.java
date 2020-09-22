package org.zxy.new_manager.VO;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ApplyVO implements Serializable {

    private static final long serialVersionUID = 4030686229012300317L;

    private Integer applyId;

    private String userId;

    private String personName;

    private String apartment;

    private String account;

    private Integer applicationId;

    private String applicationName;

    private Integer legalTime;

    private Date time;

    private Integer status;
}
