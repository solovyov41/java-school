package com.tlg.core.dao;

import com.tlg.core.dao.exception.DaoException;
import com.tlg.core.entity.User;
import com.tlg.core.entity.enums.Role;

import java.util.List;

public interface UserDao extends GenericDao<User, Long> {

    User findByEmail(String email) throws DaoException;

    List<User> findUsersWithRole(Role role) throws DaoException;

    List<User> findAllNotAssignedDriverUsers() throws DaoException;

}
