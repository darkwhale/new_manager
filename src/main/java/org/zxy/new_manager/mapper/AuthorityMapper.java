package org.zxy.new_manager.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zxy.new_manager.dataobject.Authority;

import java.util.List;

public interface AuthorityMapper extends JpaRepository<Authority, Integer> {

    Authority findByUserIdAndApplicationId(String userId, Integer appId);

    List<Authority> findAllByApplicationId(Integer applicationId);

    List<Authority> findAllByUserId(String userId);
}
