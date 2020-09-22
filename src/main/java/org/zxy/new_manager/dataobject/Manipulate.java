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
@DynamicUpdate
@DynamicInsert
public class Manipulate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer manipulateId;

    private String userId;

    private Integer applicationId;

    private Date time;

}
