package com.abc.app.engine.security.service;

import java.util.List;

import com.abc.app.engine.security.entity.Password;
import com.abc.app.engine.security.entity.User;

public interface UserService {

	List<User> findUserList(User user);

	long findUserListCount(User user);

	User findUserById(long id);

	long findUserRoleCount(long id);

	User findUserByUsername(String username);

	boolean saveUser(User user);

	boolean deleteUserById(long id);

	boolean updatePasswordById(User user);

	boolean updatePasswordByIds(Password password);

}
