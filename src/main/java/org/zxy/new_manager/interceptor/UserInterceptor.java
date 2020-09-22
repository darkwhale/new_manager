package org.zxy.new_manager.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.zxy.new_manager.config.ApiConst;
import org.zxy.new_manager.dataobject.User;
import org.zxy.new_manager.enums.ResponseEnum;
import org.zxy.new_manager.exception.ApiException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        User user = (User) request.getSession().getAttribute(ApiConst.General_User);

        if (user == null) {
            throw new ApiException(ResponseEnum.USER_NOT_LOGIN);
        }

        return true;
    }
}
