package org.zxy.new_manager.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zxy.new_manager.VO.ApplyVO;
import org.zxy.new_manager.dataobject.Application;
import org.zxy.new_manager.dataobject.Apply;
import org.zxy.new_manager.dataobject.User;
import org.zxy.new_manager.enums.ApplyStatusEnum;
import org.zxy.new_manager.enums.ResponseEnum;
import org.zxy.new_manager.exception.ApiException;
import org.zxy.new_manager.mapper.ApplyMapper;
import org.zxy.new_manager.service.ApplyService;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplyServiceImpl implements ApplyService {

    @Resource
    private ApplyMapper applyMapper;

    @Resource
    private AuthorityServiceImpl authorityService;

    @Resource
    private UserServiceImpl userService;

    @Resource
    private ApplicationServiceImpl applicationService;

    @Override
    public List<Apply> getAll() {
        return applyMapper.findAll();
    }

    @Override
    public List<Apply> getList(Integer status) {
        return applyMapper.findAllByStatus(status);
    }

    @Override
    public List<ApplyVO> converter(List<Apply> applyList) {
        return applyList.stream().map(this::converter).collect(Collectors.toList());
    }

    @Override
    public ApplyVO converter(Apply apply) {

        User user = userService.findByUser(apply.getUserId());

        Application application = applicationService.getById(apply.getApplicationId());

        ApplyVO applyVO = new ApplyVO();
        BeanUtils.copyProperties(user, applyVO);
        BeanUtils.copyProperties(application, applyVO);
        BeanUtils.copyProperties(apply, applyVO);

        return applyVO;
    }

    @Override
    @Transactional
    public Apply apply(String userId, Integer applicationId, Integer legalTime) {
        Apply apply = applyMapper.findByUserIdAndApplicationIdAndStatus(userId, applicationId, ApplyStatusEnum.WAIT.getCode());

        if (apply != null) {
            apply.setLegalTime(legalTime);
            applyMapper.save(apply);

            return apply;
        }else {
            Apply newApply = new Apply();
            newApply.setUserId(userId);
            newApply.setApplicationId(applicationId);
            newApply.setLegalTime(legalTime);
            applyMapper.save(newApply);

            return newApply;
        }
    }

    @Override
    public Apply cancel(Integer applyId, String userId) {
        Apply apply = applyMapper.findById(applyId).orElse(null);
        if (apply == null || !apply.getUserId().equals(userId)) {
            throw new ApiException(ResponseEnum.APPLY_NOT_EXIST);
        }

        applyMapper.delete(apply);
        return apply;
    }

    @Override
    public List<Apply> getList(String userId) {
        return applyMapper.findByUserId(userId);
    }

    @Override
    @Transactional
    public Apply ok(Integer applyId) {
        Apply apply = applyMapper.findById(applyId).orElse(null);
        if (apply == null) {
            throw new ApiException(ResponseEnum.APPLY_NOT_EXIST);
        }

        // 提交到authority中；
        authorityService.add(apply.getUserId(), apply.getApplicationId(),
                Long.valueOf(apply.getLegalTime()) * 86400);

        // 状态置1；
        apply.setStatus(ApplyStatusEnum.OK.getCode());
        applyMapper.save(apply);

        return apply;
    }

    @Override
    @Transactional
    public Apply denied(Integer applyId) {
        Apply apply = applyMapper.findById(applyId).orElse(null);
        if (apply == null) {
            throw new ApiException(ResponseEnum.APPLY_NOT_EXIST);
        }
        apply.setStatus(ApplyStatusEnum.DENIED.getCode());
        applyMapper.save(apply);

        return apply;
    }
}
