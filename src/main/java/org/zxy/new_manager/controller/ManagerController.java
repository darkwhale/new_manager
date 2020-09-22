package org.zxy.new_manager.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zxy.new_manager.VO.ResponseVO;
import org.zxy.new_manager.config.ApiConst;
import org.zxy.new_manager.dataobject.Manager;
import org.zxy.new_manager.dataobject.User;
import org.zxy.new_manager.form.ManagerForm;
import org.zxy.new_manager.service.impl.ManagerServiceImpl;
import org.zxy.new_manager.utils.ResponseVOUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Resource
    private ManagerServiceImpl managerService;

    @PostMapping("/login")
    ResponseVO<Manager> login(@Valid @RequestBody ManagerForm managerForm,
                              HttpSession session) {
        Manager manager = managerService.loin(managerForm);
        session.setAttribute(ApiConst.Manager_User, manager);

        return ResponseVOUtil.success(manager);
    }

    @PostMapping("/logout")
    ResponseVO<User> logout(HttpSession session) {
        session.removeAttribute(ApiConst.Manager_User);

        return ResponseVOUtil.success();
    }

}
