package org.zxy.new_manager.controller;


import org.springframework.web.bind.annotation.*;
import org.zxy.new_manager.VO.ResponseVO;
import org.zxy.new_manager.dataobject.Api;
import org.zxy.new_manager.form.ApiForm;
import org.zxy.new_manager.form.ApiUpdateForm;
import org.zxy.new_manager.service.impl.ApiServiceImpl;
import org.zxy.new_manager.utils.ResponseVOUtil;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Resource
    private ApiServiceImpl apiService;

    @PostMapping("/add")
    ResponseVO<Api> add(@Valid @RequestBody ApiForm apiForm) {
        Api add = apiService.add(apiForm);

        return ResponseVOUtil.success(add);
    }

    @PostMapping("/delete")
    ResponseVO<Api> delete(@RequestParam("apiId") Integer apiId) {
        Api delete = apiService.delete(apiId);

        return ResponseVOUtil.success(delete);
    }

    @PostMapping("/update")
    ResponseVO<Api> update(@Valid @RequestBody ApiUpdateForm apiUpdateForm) {
        Api update = apiService.update(apiUpdateForm);

        return ResponseVOUtil.success(update);
    }

}
