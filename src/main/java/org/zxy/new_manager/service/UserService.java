package org.zxy.new_manager.service;

import org.zxy.new_manager.VO.UserVO;
import org.zxy.new_manager.dataobject.User;
import org.zxy.new_manager.form.UserCreateForm;
import org.zxy.new_manager.form.UserLoginForm;
import org.zxy.new_manager.form.UserUpdateForm;

import java.util.List;

public interface UserService {

    String api2UserId(String userId);

    User register(UserCreateForm userCreateForm);

    User login(UserLoginForm userLoginForm);

    User update(String userId, UserUpdateForm userUpdateForm);

    List<UserVO> getAll();

    User findByUser(String userId);

    UserVO converter(User user);

    List<User> userList();
}
