package org.zxy.new_manager.service.impl;

import org.springframework.stereotype.Service;
import org.zxy.new_manager.dataobject.Manipulate;
import org.zxy.new_manager.mapper.ManipulateMapper;
import org.zxy.new_manager.service.ManipulateService;

import javax.annotation.Resource;

@Service
public class ManipulateServiceImpl implements ManipulateService {

    @Resource
    private ManipulateMapper manipulateMapper;

    @Override
    public void save(Manipulate manipulate) {
        manipulateMapper.save(manipulate);
    }
}
