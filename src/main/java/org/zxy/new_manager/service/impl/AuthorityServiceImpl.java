package org.zxy.new_manager.service.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zxy.new_manager.config.ApiConst;
import org.zxy.new_manager.dataobject.Application;
import org.zxy.new_manager.dataobject.Authority;
import org.zxy.new_manager.enums.ResponseEnum;
import org.zxy.new_manager.exception.ApiException;
import org.zxy.new_manager.mapper.ApplicationMapper;
import org.zxy.new_manager.mapper.AuthorityMapper;
import org.zxy.new_manager.service.AuthorityService;
import org.zxy.new_manager.utils.RedisUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Resource
    private AuthorityMapper authorityMapper;

    @Resource
    private ApplicationMapper applicationMapper;

    @Resource
    private RedisUtil redisUtil;

    @Override
    @CacheEvict(cacheNames = "authority", allEntries = true)
    @Transactional
    public Authority create(String userId, Integer applicationId, Long legalTime) {
        Authority authority = authorityMapper.findByUserIdAndApplicationId(userId, applicationId);
        if(authority != null) {
            throw new ApiException(ResponseEnum.AUTHORITY_EXIST);
        }

        // 保存到数据库
        Authority newAuthority = new Authority();
        newAuthority.setUserId(userId);
        newAuthority.setApplicationId(applicationId);
        authorityMapper.save(newAuthority);

        // 保存到redis
        redisUtil.power(redisUtil.makeKey(ApiConst.Authority_prefix, applicationId, userId), legalTime);

        return newAuthority;
    }

    @Override
    @CacheEvict(cacheNames = "authority", allEntries = true)
    @Transactional
    public Authority add(String userId, Integer applicationId, Long legalTime) {

        // 判断application是否存在；
        Application application = applicationMapper.findById(applicationId).orElse(null);
        if (application == null) {
            throw new ApiException(ResponseEnum.APPLICATION_NOT_EXIST);
        }

        Authority authority = authorityMapper.findByUserIdAndApplicationId(userId, applicationId);
        if(authority == null) {
            authority = new Authority();
        }

        // 保存到数据库
        authority.setUserId(userId);
        authority.setApplicationId(applicationId);

        // 清空updateTime
        authority.setUpdateTime(null);
        authorityMapper.save(authority);

        // 保存到redis
        redisUtil.power(redisUtil.makeKey(ApiConst.Authority_prefix, applicationId, userId), legalTime);

        return authority;
    }

    @Override
    @CacheEvict(cacheNames = "authority", allEntries = true)
    @Transactional
    public Authority refresh(Integer authorityId, Long legalTime) {
        Authority authority = authorityMapper.findById(authorityId).orElse(null);
        if (authority == null) {
            throw new ApiException(ResponseEnum.AUTHORITY_NOT_EXIST);
        }

        redisUtil.power(redisUtil.makeKey(
                ApiConst.Authority_prefix,
                authority.getApplicationId(),
                authority.getUserId()),
                legalTime);

        return authority;
    }

    @Override
    @CacheEvict(cacheNames = "authority", allEntries = true)
    @Transactional
    public void deleteByUser(String userId, Integer applicationId) {
        Authority authority = authorityMapper.findByUserIdAndApplicationId(userId, applicationId);
        if(authority == null) {
            throw new ApiException(ResponseEnum.AUTHORITY_NOT_EXIST);
        }

        authorityMapper.delete(authority);

        // 删除redis内容;
        redisUtil.delete(redisUtil.makeKey(ApiConst.Authority_prefix, authority.getApplicationId(), authority.getUserId()));

    }

    @Override
    @CacheEvict(cacheNames = "authority", allEntries = true)
    @Transactional
    public void delete(Integer authorityId) {
        Authority authority = authorityMapper.findById(authorityId).orElse(null);

        if(authority == null) {
            throw new ApiException(ResponseEnum.AUTHORITY_NOT_EXIST);
        }

        // 删除数据库内容；
        authorityMapper.delete(authority);

        // 删除redis内容;
        redisUtil.delete(redisUtil.makeKey(ApiConst.Authority_prefix, authority.getApplicationId(), authority.getUserId()));
    }

    @Override
    @CacheEvict(cacheNames = "authority", allEntries = true)
    public void deleteAllByUser(String userId) {
        List<Authority> authorityList = authorityMapper.findAllByUserId(userId);

        deleteByList(authorityList);

    }

    @Override
    @CacheEvict(cacheNames = "authority", allEntries = true)
    public void deleteAllByApp(Integer applicationId) {
        List<Authority> authorityList = authorityMapper.findAllByApplicationId(applicationId);

        deleteByList(authorityList);
    }

    @Override
    @Cacheable(cacheNames = "authority", key = "123")
    public Map<String, List<Authority>> authorityMap() {
        List<Authority> authorityList = authorityMapper.findAll();

        return authorityList.stream().collect(Collectors.groupingBy(Authority::getUserId));

    }

    private void deleteByList(List<Authority> authorityList) {
        List<String> keyList = authorityList.stream()
                .map(e ->
                        redisUtil.makeKey(ApiConst.Authority_prefix, e.getApplicationId(), e.getUserId()))
                .collect(Collectors.toList());

        // 删除数据库数据；
        authorityMapper.deleteAll(authorityList);

        // 删除redis数据;
        for (String s : keyList) {
            redisUtil.delete(s);
        }
    }
}
