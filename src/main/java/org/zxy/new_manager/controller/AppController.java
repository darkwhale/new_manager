package org.zxy.new_manager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zxy.new_manager.data.AppInfo;
import org.zxy.new_manager.dataobject.Manipulate;
import org.zxy.new_manager.enums.ResponseEnum;
import org.zxy.new_manager.exception.ApiException;
import org.zxy.new_manager.service.impl.AppServiceImpl;
import org.zxy.new_manager.service.impl.ApplicationServiceImpl;
import org.zxy.new_manager.service.impl.ManipulateServiceImpl;
import org.zxy.new_manager.service.impl.UserServiceImpl;
import org.zxy.new_manager.utils.HttpUtil;
import org.zxy.new_manager.utils.RedisUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequestMapping("/app")
@RestController
public class AppController {

    @Resource
    private AppServiceImpl appService;

    @Resource
    private ManipulateServiceImpl manipulateService;

    @Resource
    private UserServiceImpl userService;

    @Resource
    private ApplicationServiceImpl applicationService;

    @Resource
    private RedisUtil redisUtil;

    @GetMapping("/**")
    Object transmit(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        Map<String, String[]> parameterMap = request.getParameterMap();

        AppInfo appInfo = appService.generateInfo(parameterMap, servletPath);

        String userId = userService.api2UserId(appInfo.getApi());
        Integer applicationId = applicationService.name2Id(appInfo.getApplicationName());

        // 验证权限
        if(!redisUtil.legal(userId, applicationId)) {
            throw new ApiException(ResponseEnum.AUTHORITY_ERROR);
        }

        // 访问记录
        Manipulate manipulate = new Manipulate();
        manipulate.setApplicationId(applicationId);
        manipulate.setUserId(userId);
        manipulateService.save(manipulate);

        return HttpUtil.get(appInfo.getUrl());
    }
}
