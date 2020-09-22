package org.zxy.new_manager.service;

import org.zxy.new_manager.VO.ApplicationVO;
import org.zxy.new_manager.dataobject.Application;
import org.zxy.new_manager.form.ApplicationForm;
import org.zxy.new_manager.form.ApplicationUpdateForm;

import java.util.List;
import java.util.Map;

public interface ApplicationService {

    Application add(ApplicationForm applicationForm);

    Application delete(Integer applicationId);

    Application update(ApplicationUpdateForm applicationForm);

    Map<Integer, Application> appMap();

    ApplicationVO findByApp(Integer applicationId);

    List<ApplicationVO> getAll();

    String getUrlByApplicationName(String applicationName);

    Integer name2Id(String applicationName);

    Application getById(Integer applicationId);
}
