package org.zxy.new_manager.VO;

import lombok.Data;
import org.zxy.new_manager.data.App;

import java.util.List;

@Data
public class UserVO {
    private String userId;

    private String apiId;

    private String personName;

    private String account;

    private String apartment;

    private List<App> appList;
}
