package org.zxy.new_manager.controller;

import org.springframework.web.bind.annotation.*;
import org.zxy.new_manager.VO.ResponseVO;
import org.zxy.new_manager.VO.UserVO;
import org.zxy.new_manager.config.ApiConst;
import org.zxy.new_manager.dataobject.User;
import org.zxy.new_manager.form.UserCreateForm;
import org.zxy.new_manager.form.UserLoginForm;
import org.zxy.new_manager.form.UserUpdateForm;
import org.zxy.new_manager.service.impl.UserServiceImpl;
import org.zxy.new_manager.utils.ResponseVOUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserServiceImpl userService;

    @PostMapping("/register")
    ResponseVO<User> register(@Valid @RequestBody UserCreateForm userCreateForm) {
        User user = userService.register(userCreateForm);

        return ResponseVOUtil.success(user);
    }

    @PostMapping("/update")
    ResponseVO<User> update(@Valid @RequestBody UserUpdateForm userUpdateForm,
                            HttpSession session) {

        User user = (User) session.getAttribute(ApiConst.General_User);

        User newUser = userService.update(user.getUserId(), userUpdateForm);
        session.setAttribute(ApiConst.General_User, newUser);

        return ResponseVOUtil.success(newUser);

    }

    @GetMapping("/userInfo")
    ResponseVO<UserVO> userInfo(HttpSession session) {
        User user = (User) session.getAttribute(ApiConst.General_User);

        UserVO userVO = userService.converter(user);

        return ResponseVOUtil.success(userVO);
    }

    @PostMapping("/login")
    ResponseVO<User> login(@Valid @RequestBody UserLoginForm userLoginForm,
                           HttpSession session) {
        User user = userService.login(userLoginForm);

        session.setAttribute(ApiConst.General_User, user);

        return ResponseVOUtil.success(user);
    }

    @PostMapping("/logout")
    ResponseVO logout(HttpSession session) {
        session.removeAttribute(ApiConst.General_User);

        return ResponseVOUtil.success();
    }

    @GetMapping("/findByUser")
    ResponseVO<UserVO> findByUser(@RequestParam("userId") String userId) {
        User user = userService.findByUser(userId);

        UserVO userVO = userService.converter(user);

        return ResponseVOUtil.success(userVO);
    }

    @GetMapping("/getAll")
    ResponseVO<List<UserVO>> getAll() {
        List<UserVO> userVOList = userService.getAll();
        return ResponseVOUtil.success(userVOList);
    }
}
