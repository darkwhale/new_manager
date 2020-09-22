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
@DynamicInsert
@DynamicUpdate
public class Application implements Serializable {

    private static final long serialVersionUID = -6939407707614064550L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer applicationId;

    private String applicationName;

    /**
     * 应用根网址
     */
    private String url;

    private String remark;

    private Date createTime;

    private Date updateTime;
}
