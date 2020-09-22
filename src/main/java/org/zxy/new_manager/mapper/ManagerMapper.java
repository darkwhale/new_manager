package org.zxy.new_manager.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zxy.new_manager.dataobject.Manager;

public interface ManagerMapper extends JpaRepository<Manager, String> {

    Manager findByManagerIdAndPassword(String managerId, String password);
}
