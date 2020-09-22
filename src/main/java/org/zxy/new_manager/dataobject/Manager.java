package org.zxy.new_manager.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
public class Manager {

    @Id
    private String managerId;

    private String managerName;

    private String password;

    private Date createTime;

    private Date updateTime;
}
