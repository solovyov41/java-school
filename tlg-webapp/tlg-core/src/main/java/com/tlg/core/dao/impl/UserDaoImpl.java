package com.tlg.core.dao.impl;

import com.tlg.core.dao.UserDao;
import com.tlg.core.dao.exception.DaoException;
import com.tlg.core.dao.exception.error.DaoError;
import com.tlg.core.entity.User;
import com.tlg.core.entity.enums.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<User, Long> implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    public UserDaoImpl() {
        super(User.class);
    }

    @Override
    public User findByEmail(String email) throws DaoException {
        try {
            TypedQuery<User> query = entityManager.createNamedQuery("User.findByEmail", User.class);
            query.setParameter("email", email);
            User user = query.getSingleResult();

            logger.info("Found: {}.", user);
            return user;
        } catch (NoResultException ex) {
            logger.warn("No user with email {} was found.", email);
            throw new DaoException(ex, DaoError.NO_RESULT);
        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.QUERY_ERROR);
        }
    }

    @Override
    public List<User> findUsersWithRole(Role role) throws DaoException {
        try {
            TypedQuery<User> query = entityManager.createNamedQuery("User.findAllWithRole", User.class);
            query.setParameter("role", role);
            List<User> users = query.getResultList();

            logger.info("List of users with role {}", role);
            for (User user : users) {
                logger.info("User list::{}", user);
            }
            return users;
        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.QUERY_ERROR);
        }
    }

    @Override
    public List<User> findAllNotAssignedDriverUsers() throws DaoException {
        try {
            TypedQuery<User> query = entityManager.createNamedQuery("User.findAllNotAssignedDriverUsers", User.class);
            query.setParameter("role", Role.DRIVER);
            List<User> users = query.getResultList();

            logger.info("List of not connected users with role {}", Role.DRIVER);
            for (User user : users) {
                logger.info("User list::{}", user);
            }
            return users;
        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.QUERY_ERROR);
        }
    }
}
