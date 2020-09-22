package org.zxy.new_manager.service.impl;

import org.springframework.stereotype.Service;
import org.zxy.new_manager.config.ApiConst;
import org.zxy.new_manager.data.AppInfo;
import org.zxy.new_manager.enums.ResponseEnum;
import org.zxy.new_manager.exception.ApiException;
import org.zxy.new_manager.service.AppService;
import org.zxy.new_manager.utils.PathUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AppServiceImpl implements AppService {

    @Resource
    private ApplicationServiceImpl applicationService;

    @Override
    public AppInfo generateInfo(Map<String, String[]> paramMap, String servletPath) {
        List<String> paramList = new ArrayList<>();

        String api = null;

        for (Map.Entry<String, String[]> entry: paramMap.entrySet()) {
            // 检测api
            if (entry.getKey().equals(ApiConst.User_Api)) {
                if (entry.getValue().length == 0) {
                    throw new ApiException(ResponseEnum.API_NOT_PRESENT);
                }
                api = entry.getValue()[0];

                continue;
            }
            paramList.add(entry.getKey() + "=" + entry.getValue()[0]);
        }

        if (api == null) {
            throw new ApiException(ResponseEnum.API_NOT_PRESENT);
        }

        PathUtil pathUtil = new PathUtil(servletPath);

        String applicationName = pathUtil.extractApp();
        String applicationApi = pathUtil.extractApi();
        String applicationUrl = applicationService.getUrlByApplicationName(applicationName);

        String url = applicationUrl + "/" + applicationApi + "?" + String.join("&", paramList);

        AppInfo appInfo = new AppInfo();
        appInfo.setApi(api);
        appInfo.setApplicationName(applicationName);
        appInfo.setUrl(url);
        appInfo.setParamStr(applicationApi + "?" + String.join("&", paramList));

        return appInfo;

    }
}
