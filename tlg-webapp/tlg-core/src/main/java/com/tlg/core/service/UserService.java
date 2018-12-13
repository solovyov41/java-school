package com.tlg.core.service;

import com.tlg.core.entity.User;
import com.tlg.core.entity.enums.Role;
import com.tlg.core.service.exceptions.ServiceException;

import java.util.List;

public interface UserService extends GenericService<User, String> {
    List<User> findAllWithRole(Role role) throws ServiceException;

    List<User> findAllNotAssignedDriverUsers() throws ServiceException;

    User update(String email, User user, boolean isFromAdmin) throws ServiceException;
}
