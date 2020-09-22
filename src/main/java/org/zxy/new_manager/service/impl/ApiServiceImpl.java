package org.zxy.new_manager.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.zxy.new_manager.dataobject.Api;
import org.zxy.new_manager.dataobject.Application;
import org.zxy.new_manager.enums.ResponseEnum;
import org.zxy.new_manager.exception.ApiException;
import org.zxy.new_manager.form.ApiForm;
import org.zxy.new_manager.form.ApiUpdateForm;
import org.zxy.new_manager.mapper.ApiMapper;
import org.zxy.new_manager.mapper.ApplicationMapper;
import org.zxy.new_manager.service.ApiService;

import javax.annotation.Resource;

@Service
public class ApiServiceImpl implements ApiService {

    @Resource
    private ApiMapper apiMapper;

    @Resource
    private ApplicationMapper applicationMapper;

    @Override
    @CacheEvict(cacheNames = "application", allEntries = true)
    public Api add(ApiForm apiForm) {

        // 查询是否存在app
        Application application = applicationMapper.findById(apiForm.getApplicationId()).orElse(null);
        if (application == null) {
            throw new ApiException(ResponseEnum.APPLICATION_NOT_EXIST);
        }

        // 查询是否存在api
        Api api = apiMapper.findByApplicationIdAndApiValue(apiForm.getApplicationId(), apiForm.getApiValue());
        if (api != null) {
            throw new ApiException(ResponseEnum.API_EXIST);
        }

        Api newApi = new Api();
        BeanUtils.copyProperties(apiForm, newApi);

        // 存储数据；
        apiMapper.save(newApi);

        return newApi;
    }

    @Override
    @CacheEvict(cacheNames = "application", allEntries = true)
    public Api delete(Integer apiId) {
        Api api = apiMapper.findById(apiId).orElse(null);

        if (api == null) {
            throw new ApiException(ResponseEnum.API_NOT_EXIST);
        }

        apiMapper.delete(api);

        return api;
    }

    @Override
    @CacheEvict(cacheNames = "application", allEntries = true)
    public Api update(ApiUpdateForm apiUpdateForm) {
        Api api = apiMapper.findById(apiUpdateForm.getApiId()).orElse(null);

        if (api == null) {
            throw new ApiException(ResponseEnum.API_NOT_EXIST);
        }

        BeanUtils.copyProperties(apiUpdateForm, api);

        // 保存数据到数据库；
        apiMapper.save(api);

        return api;
    }

}
