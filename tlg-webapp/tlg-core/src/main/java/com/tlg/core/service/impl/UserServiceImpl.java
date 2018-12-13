package com.tlg.core.service.impl;

import com.tlg.core.dao.UserDao;
import com.tlg.core.dao.exception.DaoException;
import com.tlg.core.dao.exception.error.DaoError;
import com.tlg.core.dto.UserDto;
import com.tlg.core.entity.User;
import com.tlg.core.entity.enums.Role;
import com.tlg.core.service.UserService;
import com.tlg.core.service.exceptions.ServiceException;
import com.tlg.core.service.exceptions.error.ServiceError;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private TypeMap<User, User> typeMapUserUser;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder encoder;

    @Transactional
    @Override
    public User create(User newUser) throws ServiceException {
        newUser.setRole(Role.REGISTERED);
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        try {
            userDao.create(newUser);
            return userDao.findByEmail(newUser.getEmail());
        } catch (DaoException ex) {
            throw new ServiceException(ex, ServiceError.CANT_CREATE, newUser);
        }
    }

    @Transactional
    @Override
    public List<User> findAll() throws ServiceException {
        try {
            return userDao.findAll();
        } catch (DaoException ex) {
            throw new ServiceException(ex, ServiceError.SEARCH_ERROR, "All users");
        }
    }

    @Transactional
    @Override
    public List<User> findAllWithRole(Role role) throws ServiceException {
        try {
            return userDao.findUsersWithRole(role);
        } catch (DaoException ex) {
            throw new ServiceException(ex, ServiceError.SEARCH_ERROR, "Users with role: " + role);
        }
    }

    @Transactional
    @Override
    public List<User> findAllNotAssignedDriverUsers() throws ServiceException {
        try {
            return userDao.findAllNotAssignedDriverUsers();
        } catch (DaoException ex) {
            throw new ServiceException(ex, ServiceError.SEARCH_ERROR, "Users with role: " + Role.DRIVER);
        }
    }

    @Transactional
    @Override
    public User findByIdentifier(String email) throws ServiceException {
        try {
            return userDao.findByEmail(email);
        } catch (DaoException ex) {
            if (ex.getError() == DaoError.NO_RESULT) {
                return null;
            }
            throw new ServiceException(ex, ServiceError.SEARCH_ERROR, "User with email: " + email);
        }
    }

    @Transactional
    @Override
    public User update(String email, User user, boolean isFromAdmin) throws ServiceException {
        User curUser = update(email, user);
        if (!isFromAdmin) {
            updateAuthentication(curUser);
        }
        return curUser;
    }

    @Transactional
    @Override
    public User update(String email, User user) throws ServiceException {
        try {
            User curUser = userDao.findByEmail(email);
            if (user != null) {
                typeMapUserUser.map(user, curUser);
                userDao.update(curUser);
                return curUser;
            }
            return null;
        } catch (DaoException ex) {
            throw new ServiceException(ex, ServiceError.UPDATE_ERROR, "email", email, user);
        }
    }

    @Transactional
    @Override
    public boolean delete(String email) throws ServiceException {
        try {
            User user = userDao.findByEmail(email);
            if (user != null) {
                userDao.delete(user);
                return true;
            }
            return false;
        } catch (DaoException ex) {
            throw new ServiceException(ex, ServiceError.DELETE_ERROR, "email", email, "User");
        }
    }

    private void updateAuthentication(User user) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDto origUser = (UserDto) securityContext.getAuthentication().getPrincipal();
        modelMapper.map(user, origUser);
    }
}
