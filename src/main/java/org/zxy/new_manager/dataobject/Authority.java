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
public class Authority implements Serializable {

    private static final long serialVersionUID = 5408027155234850216L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authorityId;

    private String userId;

    private Integer applicationId;

    private Date createTime;

    private Date updateTime;

}
