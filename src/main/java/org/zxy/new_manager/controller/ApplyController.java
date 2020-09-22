package org.zxy.new_manager.controller;

import org.springframework.web.bind.annotation.*;
import org.zxy.new_manager.VO.ApplyVO;
import org.zxy.new_manager.VO.ResponseVO;
import org.zxy.new_manager.config.ApiConst;
import org.zxy.new_manager.dataobject.Apply;
import org.zxy.new_manager.dataobject.User;
import org.zxy.new_manager.enums.ApplyStatusEnum;
import org.zxy.new_manager.service.impl.ApplyServiceImpl;
import org.zxy.new_manager.utils.ResponseVOUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/apply")
@RestController
public class ApplyController {

    @Resource
    private ApplyServiceImpl applyService;

    @PostMapping("/apply")
    ResponseVO<ApplyVO> apply(@RequestParam("applicationId") Integer applicationId,
                              @RequestParam("legalTime") Integer legalTime,
                              HttpSession session) {
        User user = (User) session.getAttribute(ApiConst.General_User);

        Apply apply = applyService.apply(user.getUserId(), applicationId, legalTime);

        ApplyVO applyVO = applyService.converter(apply);

        return ResponseVOUtil.success(applyVO);
    }

    @PostMapping("/ok")
    ResponseVO<ApplyVO> ok(@RequestParam("applyId") Integer applyId) {
        Apply apply = applyService.ok(applyId);

        ApplyVO applyVO = applyService.converter(apply);

        return ResponseVOUtil.success(applyVO);
    }

    @PostMapping("/denied")
    ResponseVO<ApplyVO> denied(@RequestParam("applyId") Integer applyId) {
        Apply apply = applyService.denied(applyId);

        ApplyVO applyVO = applyService.converter(apply);

        return ResponseVOUtil.success(applyVO);
    }

    @GetMapping("/getApply")
    ResponseVO<List<ApplyVO>> getApply() {
        List<Apply> applyList = applyService.getList(ApplyStatusEnum.WAIT.getCode());

        List<ApplyVO> applyVOList = applyService.converter(applyList);
        return ResponseVOUtil.success(applyVOList);
    }

    @GetMapping("/getOk")
    ResponseVO<List<ApplyVO>> getOk() {
        List<Apply> applyList = applyService.getList(ApplyStatusEnum.OK.getCode());
        List<ApplyVO> applyVOList = applyService.converter(applyList);
        return ResponseVOUtil.success(applyVOList);
    }

    @GetMapping("/getDenied")
    ResponseVO<List<ApplyVO>> getDenied() {
        List<Apply> applyList = applyService.getList(ApplyStatusEnum.DENIED.getCode());
        List<ApplyVO> applyVOList = applyService.converter(applyList);
        return ResponseVOUtil.success(applyVOList);
    }

    @GetMapping("/getAll")
    ResponseVO<List<ApplyVO>> getAll() {
        List<Apply> applyList = applyService.getAll();
        List<ApplyVO> applyVOList = applyService.converter(applyList);
        return ResponseVOUtil.success(applyVOList);
    }

    @GetMapping("/getByUser")
    ResponseVO<List<ApplyVO>> getByUser(HttpSession session) {
        User user = (User) session.getAttribute(ApiConst.General_User);

        List<Apply> applyList = applyService.getList(user.getUserId());
        List<ApplyVO> applyVOList = applyService.converter(applyList);

        return ResponseVOUtil.success(applyVOList);

    }

    @PostMapping("/cancel")
    ResponseVO<ApplyVO> cancel(@RequestParam("applyId") Integer applyId,
                             HttpSession session) {
        User user = (User) session.getAttribute(ApiConst.General_User);

        Apply cancel = applyService.cancel(applyId, user.getUserId());

        ApplyVO applyVO = applyService.converter(cancel);

        return ResponseVOUtil.success(applyVO);
    }

}
