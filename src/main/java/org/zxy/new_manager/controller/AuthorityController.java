package org.zxy.new_manager.controller;


import org.springframework.web.bind.annotation.*;
import org.zxy.new_manager.VO.ResponseVO;
import org.zxy.new_manager.dataobject.Authority;
import org.zxy.new_manager.form.AuthorityAddForm;
import org.zxy.new_manager.service.impl.AuthorityServiceImpl;
import org.zxy.new_manager.utils.ResponseVOUtil;

import javax.annotation.Resource;
import javax.validation.Valid;

@RequestMapping("/authority")
@RestController
public class AuthorityController {

    @Resource
    private AuthorityServiceImpl authorityService;

    @PostMapping("/create")
    ResponseVO<Authority> create(@Valid @RequestBody AuthorityAddForm authorityAddForm) {
        Authority authority = authorityService.create(
                authorityAddForm.getUserId(),
                authorityAddForm.getApplicationId(),
                authorityAddForm.getLegalTime()
        );

        return ResponseVOUtil.success(authority);
    }

    @PostMapping("/refresh")
    ResponseVO<Authority> refresh(@RequestParam("authorityId") Integer authorityId,
                                  @RequestParam("legalTime") Long legalTime) {
        Authority authority = authorityService.refresh(authorityId, legalTime);

        return ResponseVOUtil.success(authority);
    }

    @PostMapping("/delete")
    ResponseVO delete(@RequestParam("authorityId") Integer authorityId) {
        authorityService.delete(authorityId);

        return ResponseVOUtil.success();
    }

    @PostMapping("/deleteByUser")
    ResponseVO deleteByUser(@RequestParam("userId") String userId,
                            @RequestParam("applicationId") Integer applicationId) {
        authorityService.deleteByUser(userId, applicationId);

        return ResponseVOUtil.success();
    }


}
