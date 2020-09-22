package org.zxy.new_manager.interceptor;


import org.springframework.web.servlet.HandlerInterceptor;
import org.zxy.new_manager.config.ApiConst;
import org.zxy.new_manager.dataobject.Manager;
import org.zxy.new_manager.enums.ResponseEnum;
import org.zxy.new_manager.exception.ApiException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManagerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Manager manager = (Manager) request.getSession().getAttribute(ApiConst.Manager_User);

        if (manager == null) {
            throw new ApiException(ResponseEnum.MANAGER_NOT_LOGIN);
        }

        return true;
    }
}
