package org.zxy.new_manager.service;

import org.zxy.new_manager.VO.ApplyVO;
import org.zxy.new_manager.dataobject.Apply;

import java.util.List;

public interface ApplyService {

    Apply apply(String userId, Integer applicationId, Integer legalTime);

    Apply cancel(Integer applyId, String userId);

    Apply ok(Integer applyId);

    Apply denied(Integer applyId);

    List<Apply> getList(Integer status);

    List<Apply> getList(String userId);

    List<Apply> getAll();

    ApplyVO converter(Apply apply);

    List<ApplyVO> converter(List<Apply> applyList);
}
