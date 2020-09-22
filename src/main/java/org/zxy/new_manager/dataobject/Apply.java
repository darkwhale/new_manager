package org.zxy.new_manager.dataobject;


import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
public class Apply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer applyId;

    private String userId;

    private Integer applicationId;

    private Integer legalTime;

    private Date time;

    private Integer status;
}
