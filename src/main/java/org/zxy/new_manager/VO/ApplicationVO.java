package org.zxy.new_manager.VO;

import lombok.Data;
import org.zxy.new_manager.dataobject.Api;

import java.io.Serializable;
import java.util.List;

@Data
public class ApplicationVO implements Serializable {

    private static final long serialVersionUID = 6869262940067304638L;

    private Integer applicationId;

    private String applicationName;

    /**
     * 应用根网址
     */
    private String url;

    private String remark;

    private List<Api> apiList;

    public void bypass() {
        url = "";
    }
}
