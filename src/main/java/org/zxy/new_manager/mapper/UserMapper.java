package org.zxy.new_manager.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zxy.new_manager.dataobject.User;

public interface UserMapper extends JpaRepository<User, String> {

    User findByAccountAndPassword(String account, String password);

    User findByAccount(String account);

    Integer countByApiId(String apiId);

    User findByApiId(String apiId);
}
