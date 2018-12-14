package com.tlg.core.service;

import com.tlg.core.dao.UserDao;
import com.tlg.core.dao.exception.DaoException;
import com.tlg.core.dao.exception.error.DaoError;
import com.tlg.core.entity.User;
import com.tlg.core.entity.enums.Role;
import com.tlg.core.service.exceptions.ServiceException;
import com.tlg.core.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    public static final String NO_SERVICE_EX = "Service exception was not thrown";
    @InjectMocks
    UserServiceImpl us;

    @Mock
    private UserDao userDao;

    @Mock
    private TypeMap<User, User> typeMapUserUser;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder encoder;

    private User tUser;

    @Before
    public void setUp() throws Exception {
        tUser = new User();
        tUser.setEmail("test@mail.com");
        tUser.setPassword("pass");
        tUser.setName("tName");
        tUser.setSurname("tSurname");
        tUser.setRole(Role.DRIVER);
    }

    @Test
    public void userCreate_happyPath() throws DaoException {
        when(encoder.encode(anyString())).thenReturn("encoded");
        doReturn(tUser).when(userDao).findByEmail(tUser.getEmail());

        doAnswer(invocation -> {
            User user = invocation.getArgument(0);

            assertEquals("encoded", user.getPassword());
            assertEquals(Role.REGISTERED, user.getRole());
            return null;
        }).when(userDao).create(tUser);
        try {
            us.create(tUser);
        } catch (ServiceException ex) {
            fail(ex.getMessage());
        }

        verify(userDao, times(1)).create(tUser);
        verify(userDao, times(1)).findByEmail(tUser.getEmail());
    }

    @Test
    public void userCreate_daoThrowEx_expectedServiceEx() throws DaoException {
        doThrow(DaoException.class).when(userDao).findByEmail(anyString());
        try {
            us.create(tUser);
            fail(NO_SERVICE_EX);
        } catch (ServiceException e) {
            //expected
        }
        doThrow(DaoException.class).when(userDao).create(any(User.class));
        try {
            us.create(tUser);
            fail(NO_SERVICE_EX);
        } catch (ServiceException e) {
            //expected
        }
    }

    @Test
    public void userUpdate_happyPath() throws DaoException {
        User existUser = new User();
        doReturn(existUser).when(userDao).findByEmail(tUser.getEmail());
        doAnswer(i -> {
            User to = i.getArgument(1);
            to.setEmail(tUser.getEmail());
            return null;
        }).when(typeMapUserUser).map(any(User.class), any(User.class));
        try {
            assertEquals(tUser.getEmail(), us.update(tUser.getEmail(), existUser).getEmail());
        } catch (ServiceException ex) {
            fail(ex.getMessage());
        }

        verify(userDao, times(1)).findByEmail(tUser.getEmail());
        verify(typeMapUserUser, times(1)).map(tUser, existUser);
        verify(userDao, times(1)).update(tUser);

        String notExistEmail = "notExisted@email.org";
        doReturn(null).when(userDao).findByEmail(notExistEmail);

        try {
            assertNull(us.update(notExistEmail, tUser));
        } catch (ServiceException e) {
            fail(e.getMessage());
        }
        verify(userDao, times(1)).findByEmail(notExistEmail);
    }

    @Test
    public void userUpdate_withNullUser_expectedNull() throws DaoException {
        doReturn(tUser).when(userDao).findByEmail(tUser.getEmail());
        try {
            assertNull(us.update(tUser.getEmail(), null));
        } catch (ServiceException e) {
            fail(e.getMessage());
        }
        verify(userDao, times(1)).findByEmail(tUser.getEmail());
        verify(typeMapUserUser, times(0)).map(any(User.class), any(User.class));
        verify(userDao, times(0)).update(any(User.class));
    }

    @Test
    public void userUpdate_daoThrowEx_expectedServiceEx() throws DaoException {
        doThrow(DaoException.class).when(userDao).update(any(User.class));
        doReturn(tUser).when(userDao).findByEmail(tUser.getEmail());

        try {
            us.update(tUser.getEmail(), tUser);
            fail(NO_SERVICE_EX);
        } catch (ServiceException e) {
            //expected
        }

        doThrow(DaoException.class).when(userDao).findByEmail(anyString());
        try {
            us.update(tUser.getEmail(), tUser);
            fail(NO_SERVICE_EX);
        } catch (ServiceException e) {
            //expected
        }
    }

    @Test
    public void userDelete_happyPath() throws DaoException {
        doReturn(tUser).when(userDao).findByEmail(tUser.getEmail());
        try {
            assertTrue(us.delete(tUser.getEmail()));
        } catch (ServiceException e) {
            fail(e.getMessage());
        }
        String notExistEmail = "notExisted@email.org";
        doThrow(new DaoException(DaoError.NO_RESULT)).when(userDao).findByEmail(notExistEmail);
        try {
            assertFalse(us.delete(notExistEmail));
        } catch (ServiceException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void userDelete_daoThrowEx_expectedServiceEx() throws DaoException {
        doThrow(DaoException.class).when(userDao).delete(any(User.class));
        doReturn(tUser).when(userDao).findByEmail(tUser.getEmail());
        try {
            us.delete(tUser.getEmail());
            fail(NO_SERVICE_EX);
        } catch (ServiceException e) {
            //expected
        }

        doThrow(DaoException.class).when(userDao).findByEmail(anyString());
        try {
            us.delete(tUser.getEmail());
            fail(NO_SERVICE_EX);
        } catch (ServiceException e) {
            //expected
        }
    }

    @Test
    public void usersFind_expectedUsers() throws DaoException {
        List<User> userList = new ArrayList<>(Arrays.asList(tUser));
        doReturn(userList).when(userDao).findAll();
        doReturn(userList).when(userDao).findUsersWithRole(any(Role.class));
        doReturn(userList).when(userDao).findAllNotAssignedDriverUsers();

        doReturn(tUser).when(userDao).findByEmail(tUser.getEmail());
        doReturn(null).when(userDao).findByEmail("notExisted");

        try {
            assertEquals(userList, us.findAll());
            assertEquals(userList, us.findAllWithRole(tUser.getRole()));
            assertEquals(userList, us.findAllNotAssignedDriverUsers());
            assertEquals(tUser, us.findByIdentifier(tUser.getEmail()));
            assertNull(us.findByIdentifier("notExisted"));
        } catch (ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void usersFind_daoThrowEx_expectedServiceEx() throws DaoException {
        doThrow(DaoException.class).when(userDao).findAll();
        doThrow(DaoException.class).when(userDao).findUsersWithRole(any(Role.class));
        doThrow(DaoException.class).when(userDao).findAllNotAssignedDriverUsers();

        doThrow(DaoException.class).when(userDao).findByEmail(anyString());
        try {
            us.findAll();
            fail(NO_SERVICE_EX);
        } catch (ServiceException e) {
            //expected
        }
        try {
            us.findAllWithRole(tUser.getRole());
            fail(NO_SERVICE_EX);
        } catch (ServiceException e) {
            //expected
        }
        try {
            us.findAllNotAssignedDriverUsers();
            fail(NO_SERVICE_EX);
        } catch (ServiceException e) {
            //expected
        }try {
            us.findByIdentifier(tUser.getEmail());
            fail(NO_SERVICE_EX);
        } catch (ServiceException e) {
            //expected
        }

    }
}