package org.zxy.new_manager.dataobject;


import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class Api implements Serializable {

    private static final long serialVersionUID = -201103555188277500L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer apiId;

    private Integer applicationId;

    private String apiName;

    private String apiValue;

    private String remark;

    private Date createTime;

    private Date updateTime;
}
