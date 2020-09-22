package org.zxy.new_manager.controller;

import org.springframework.web.bind.annotation.*;
import org.zxy.new_manager.VO.ApplicationVO;
import org.zxy.new_manager.VO.ResponseVO;
import org.zxy.new_manager.config.ApiConst;
import org.zxy.new_manager.dataobject.Application;
import org.zxy.new_manager.form.ApplicationForm;
import org.zxy.new_manager.form.ApplicationUpdateForm;
import org.zxy.new_manager.service.impl.ApplicationServiceImpl;
import org.zxy.new_manager.utils.ResponseVOUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/application")
public class ApplicationController {

    @Resource
    private ApplicationServiceImpl applicationService;

    @PostMapping("/add")
    ResponseVO<Application> add(@Valid @RequestBody ApplicationForm applicationForm) {
        Application app = applicationService.add(applicationForm);

        return ResponseVOUtil.success(app);
    }

    @PostMapping("/delete")
    ResponseVO<Application> delete(@RequestParam("applicationId") Integer applicationId) {
        Application application = applicationService.delete(applicationId);

        return ResponseVOUtil.success(application);
    }

    @PostMapping("/update")
    ResponseVO<Application> update(@Valid @RequestBody ApplicationUpdateForm applicationForm) {
        Application application = applicationService.update(applicationForm);

        return ResponseVOUtil.success(application);
    }

    @GetMapping("/findByApp")
    ResponseVO<ApplicationVO> findByApp(@RequestParam("applicationId") Integer applicationId) {
        ApplicationVO applicationVO = applicationService.findByApp(applicationId);

        return ResponseVOUtil.success(applicationVO);
    }

    @GetMapping("/getAll")
    ResponseVO<List<ApplicationVO>> getAll(HttpSession session) {

        List<ApplicationVO> applicationVOList = applicationService.getAll();

        if (session.getAttribute(ApiConst.Manager_User) == null) {
            for (ApplicationVO applicationVO : applicationVOList) {
                applicationVO.bypass();
            }
        }

        return ResponseVOUtil.success(applicationVOList);
    }

}
