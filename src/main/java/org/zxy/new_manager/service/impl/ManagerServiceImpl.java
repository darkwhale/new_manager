package org.zxy.new_manager.service.impl;

import org.springframework.stereotype.Service;
import org.zxy.new_manager.dataobject.Manager;
import org.zxy.new_manager.enums.ResponseEnum;
import org.zxy.new_manager.exception.ApiException;
import org.zxy.new_manager.form.ManagerForm;
import org.zxy.new_manager.mapper.ManagerMapper;
import org.zxy.new_manager.service.ManagerService;

import javax.annotation.Resource;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Resource
    private ManagerMapper managerMapper;

    @Override
    public Manager loin(ManagerForm managerForm) {
        Manager manager = managerMapper.findByManagerIdAndPassword(managerForm.getManagerId(), managerForm.getPassword());

        if(manager == null) {
            throw new ApiException(ResponseEnum.MANAGER_ERROR);
        }

        return manager;
    }
}
