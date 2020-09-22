package org.zxy.new_manager.service;

import org.zxy.new_manager.dataobject.Authority;

import java.util.List;
import java.util.Map;

public interface AuthorityService {

    Authority create(String userId, Integer applicationId, Long legalTime);

    Authority add(String userId, Integer applicationId, Long legalTime);

    Authority refresh(Integer authorityId, Long legalTime);

    void delete(Integer authorityId);

    void deleteByUser(String userId, Integer applicationId);

    void deleteAllByUser(String userId);

    void deleteAllByApp(Integer applicationId);

    Map<String, List<Authority>> authorityMap();

}
