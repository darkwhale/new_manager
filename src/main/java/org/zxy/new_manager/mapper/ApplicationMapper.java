package org.zxy.new_manager.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zxy.new_manager.dataobject.Application;

import java.util.List;

public interface ApplicationMapper extends JpaRepository<Application, Integer> {

    List<Application> findAll();

    Application findByApplicationName(String applicationName);

}
