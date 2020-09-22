package org.zxy.new_manager.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.zxy.new_manager.VO.UserVO;
import org.zxy.new_manager.config.ApiConst;
import org.zxy.new_manager.data.App;
import org.zxy.new_manager.dataobject.Application;
import org.zxy.new_manager.dataobject.Authority;
import org.zxy.new_manager.dataobject.User;
import org.zxy.new_manager.enums.ResponseEnum;
import org.zxy.new_manager.exception.ApiException;
import org.zxy.new_manager.form.UserCreateForm;
import org.zxy.new_manager.form.UserLoginForm;
import org.zxy.new_manager.form.UserUpdateForm;
import org.zxy.new_manager.mapper.UserMapper;
import org.zxy.new_manager.service.UserService;
import org.zxy.new_manager.utils.KeyUtil;
import org.zxy.new_manager.utils.RedisUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private AuthorityServiceImpl authorityService;

    @Resource
    private ApplicationServiceImpl applicationService;

    @Resource
    private KeyUtil keyUtil;

    @Resource
    private RedisUtil redisUtil;

    @Value("${apiLength}")
    private Integer apiLength;

    @Override
    @Cacheable(cacheNames = "user", key = "T(String).valueOf(#userId).concat('id')")
    public User findByUser(String userId) {
        User user = userMapper.findById(userId).orElse(null);
        if (user == null) {
            throw new ApiException(ResponseEnum.USER_NOT_EXIST);
        }

        return user;
    }

    @Override
    @Cacheable(cacheNames = "user", key = "#apiId")
    public String api2UserId(String apiId) {
        User user = userMapper.findByApiId(apiId);

        if (user == null) {
            throw new ApiException((ResponseEnum.USER_NOT_EXIST));
        }
        return user.getUserId();
    }

    @Override
    @CacheEvict(cacheNames = "user", allEntries = true)
    public User register(UserCreateForm userCreateForm) {

        User user = userMapper.findByAccount(userCreateForm.getAccount());
        if (user != null) {
            throw new ApiException(ResponseEnum.USER_EXIST);
        }

        User newUser = new User();
        BeanUtils.copyProperties(userCreateForm, newUser);
        newUser.setUserId(keyUtil.genUniqueKey());

        while (true) {
            String apiId = keyUtil.genApiId(apiLength);

            if (userMapper.countByApiId(apiId) == 0) {
                newUser.setApiId(apiId);
                break;
            }
        }

        userMapper.save(newUser);

        return newUser;
    }

    @Override
    public User login(UserLoginForm userLoginForm) {

        User user = userMapper.findByAccountAndPassword(userLoginForm.getAccount(), userLoginForm.getPassword());

        if(user == null) {
            throw new ApiException(ResponseEnum.USER_OR_PASSWORD_ERROR);
        }

        return user;
    }

    @Override
    @CacheEvict(cacheNames = "user", allEntries = true)
    public User update(String userId, UserUpdateForm userUpdateForm) {
        User user = userMapper.findById(userId).orElse(null);

        if (user == null) {
            throw new ApiException(ResponseEnum.USER_NOT_EXIST);
        }

        BeanUtils.copyProperties(userUpdateForm, user);

        userMapper.save(user);

        return user;
    }

    @Override
    public UserVO converter(User user) {
        Map<Integer, Application> appMap = applicationService.appMap();

        Map<String, List<Authority>> authorityMap = authorityService.authorityMap();

        List<App> appList = makeAppList(appMap, authorityMap, user.getUserId());

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);

        userVO.setAppList(appList);

        return userVO;
    }

    @Override
    public List<UserVO> getAll() {
        Map<Integer, Application> appMap = applicationService.appMap();

        Map<String, List<Authority>> authorityMap = authorityService.authorityMap();

        List<User> userList = userList();

        return userList.stream().map(
                e -> {
                    UserVO userVO = new UserVO();
                    BeanUtils.copyProperties(e, userVO);
                    List<App> subAppList = makeAppList(appMap, authorityMap, e.getUserId());
                    userVO.setAppList(subAppList);
                    return userVO;
                }
        ).collect(Collectors.toList());

    }

    @Override
    @Cacheable(cacheNames = "user", key = "123")
    public List<User> userList() {
        return userMapper.findAll();
    }

    private List<App> makeAppList(Map<Integer, Application> appMap, Map<String, List<Authority>> authorityMap, String userId) {
        return authorityMap.getOrDefault(userId, new ArrayList<>()).stream().map(
                e -> {
                    App app = new App();
                    Application subApp = appMap.get(e.getApplicationId());
                    BeanUtils.copyProperties(subApp, app);
                    app.setLegalTime(redisUtil.legalTime(
                            redisUtil.makeKey(
                                    ApiConst.Authority_prefix,
                                    e.getApplicationId(),
                                    e.getUserId())));
                    return app;
                }
        ).collect(Collectors.toList());
    }

}
