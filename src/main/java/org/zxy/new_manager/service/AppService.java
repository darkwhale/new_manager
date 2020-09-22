package org.zxy.new_manager.service;


import org.zxy.new_manager.data.AppInfo;

import java.util.Map;

public interface AppService {

    AppInfo generateInfo(Map<String, String[]> paramMap, String servletPath);

}
