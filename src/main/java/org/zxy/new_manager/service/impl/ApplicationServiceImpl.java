package org.zxy.new_manager.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.zxy.new_manager.VO.ApplicationVO;
import org.zxy.new_manager.dataobject.Api;
import org.zxy.new_manager.dataobject.Application;
import org.zxy.new_manager.enums.ResponseEnum;
import org.zxy.new_manager.exception.ApiException;
import org.zxy.new_manager.form.ApplicationForm;
import org.zxy.new_manager.form.ApplicationUpdateForm;
import org.zxy.new_manager.mapper.ApiMapper;
import org.zxy.new_manager.mapper.ApplicationMapper;
import org.zxy.new_manager.service.ApplicationService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Resource
    private ApplicationMapper applicationMapper;

    @Resource
    private ApiMapper apiMapper;

    @Resource
    private AuthorityServiceImpl authorityService;

    @Override
    @Cacheable(cacheNames = "application", key = "#applicationId")
    public ApplicationVO findByApp(Integer applicationId) {

        Application application = applicationMapper.findById(applicationId).orElse(null);
        if(application == null) {
            throw new ApiException(ResponseEnum.APPLICATION_NOT_EXIST);
        }

        List<Api> apiList = apiMapper.findAllByApplicationId(applicationId);

        ApplicationVO applicationVO = new ApplicationVO();
        BeanUtils.copyProperties(application, applicationVO);
        applicationVO.setApiList(apiList);

        return applicationVO;
    }

    @Override
    @CacheEvict(cacheNames = "application", allEntries = true)
    public Application add(ApplicationForm applicationForm) {
        Application application = applicationMapper.findByApplicationName(applicationForm.getApplicationName());
        if (application != null) {
            throw new ApiException(ResponseEnum.APPLICATION_EXIST);
        }

        Application newApplication = new Application();
        BeanUtils.copyProperties(applicationForm, newApplication);

        // 存储数据；
        applicationMapper.save(newApplication);

        return newApplication;    }

    @Override
    @CacheEvict(cacheNames = "application", allEntries = true)
    public Application delete(Integer applicationId) {
        Application application = applicationMapper.findById(applicationId).orElse(null);
        if (application == null) {
            throw new ApiException(ResponseEnum.APPLICATION_NOT_EXIST);
        }

        applicationMapper.delete(application);

        // 清除api
        List<Api> allByApplicationId = apiMapper.findAllByApplicationId(applicationId);
        apiMapper.deleteAll(allByApplicationId);

        // 清除权限， redis权限
        authorityService.deleteAllByApp(applicationId);


        return application;
    }

    @Override
    @CacheEvict(cacheNames = "application", allEntries = true)
    public Application update(ApplicationUpdateForm applicationUpdateForm) {
        Application application = applicationMapper.findById(applicationUpdateForm.getApplicationId()).orElse(null);
        if (application == null) {
            throw new ApiException(ResponseEnum.APPLICATION_NOT_EXIST);
        }

        BeanUtils.copyProperties(applicationUpdateForm, application);

        // 存储数据
        applicationMapper.save(application);

        return application;
    }

    @Override
    @Cacheable(cacheNames = "application", key = "125")
    public List<ApplicationVO> getAll() {
        List<Api> apiList = apiMapper.findAll();

        Map<Integer, List<Api>> apiMap = apiList.stream()
                .collect(Collectors.groupingBy(Api::getApplicationId));

        List<Application> all = applicationMapper.findAll();

        return all.stream().map(
                e -> {
                    ApplicationVO applicationVO = new ApplicationVO();
                    BeanUtils.copyProperties(e, applicationVO);
                    applicationVO.setApiList(apiMap.getOrDefault(e.getApplicationId(), new ArrayList<>()));
                    return applicationVO;
                }
        ).collect(Collectors.toList());
    }

    @Override
    @Cacheable(cacheNames = "application", key = "T(String).valueOf(#applicationId).concat('en')")
    public Application getById(Integer applicationId) {

        Application application = applicationMapper.findById(applicationId).orElse(null);
        if (application == null) {
            throw new ApiException(ResponseEnum.APPLICATION_NOT_EXIST);
        }
        return application;
    }

    @Override
    @Cacheable(cacheNames = "application", key = "T(String).valueOf(#applicationName).concat('id')")
    public Integer name2Id(String applicationName) {
        Application application = applicationMapper.findByApplicationName(applicationName);
        if(application == null) {
            throw new ApiException(ResponseEnum.APPLICATION_NOT_EXIST);
        }

        return application.getApplicationId();
    }

    @Override
    @Cacheable(cacheNames = "application", key = "#applicationName")
    public String getUrlByApplicationName(String applicationName) {

        Application application = applicationMapper.findByApplicationName(applicationName);

        if (application == null) {
            throw new ApiException(ResponseEnum.APPLICATION_NOT_EXIST);
        }

        return application.getUrl();
    }

    @Override
    @Cacheable(cacheNames = "application", key = "123")
    public Map<Integer, Application> appMap() {
        List<Application> applicationList = applicationMapper.findAll();

        return applicationList.stream().collect(Collectors.toMap(Application::getApplicationId, app -> app));
    }
}
