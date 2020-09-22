package org.zxy.new_manager.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zxy.new_manager.dataobject.Api;

import java.util.List;

public interface ApiMapper extends JpaRepository<Api, Integer> {

    List<Api> findAllByApplicationId(Integer applicationId);

    Api findByApplicationIdAndApiValue(Integer applicationId, String apiValue);
}
