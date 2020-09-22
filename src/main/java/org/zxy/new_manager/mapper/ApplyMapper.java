package org.zxy.new_manager.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zxy.new_manager.dataobject.Apply;

import java.util.List;

public interface ApplyMapper extends JpaRepository<Apply, Integer> {

    List<Apply> findAllByStatus(Integer status);

    List<Apply> findByUserId(String userId);

    Apply findByUserIdAndApplicationIdAndStatus(String userId, Integer applicationId, Integer status);

    List<Apply> findAllByOrderByUserId();
}
