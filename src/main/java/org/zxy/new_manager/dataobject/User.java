package org.zxy.new_manager.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@DynamicUpdate
@DynamicInsert
public class User implements Serializable {

    private static final long serialVersionUID = -7978260601824120759L;

    @Id
    private String userId;

    private String apiId;

    private String personName;

    private String account;

    private String password;

    private String apartment;

    private Date createTime;

    private Date updateTime;


}
