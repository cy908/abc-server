package com.abc.app.engine.security.mapper;

import java.util.List;

import com.abc.app.engine.security.entity.Password;
import com.abc.app.engine.security.entity.User;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

	List<User> findUserList(User user);

	long findUserListCount(User user);

	User findUserById(long id);

	long findUserRoleCount(long id);

	User findUserByUsername(String username);

	long insertUser(User user);

	long updateUserById(User user);

	long deleteUserById(long id);

	long updatePasswordById(User user);

	long updatePasswordByIds(Password password);

}
