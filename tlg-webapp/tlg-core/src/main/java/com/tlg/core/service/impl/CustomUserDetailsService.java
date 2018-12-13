package com.tlg.core.service.impl;

import com.tlg.core.dao.UserDao;
import com.tlg.core.dao.exception.DaoException;
import com.tlg.core.dto.UserDto;
import com.tlg.core.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) {
        User user = null;
        try {
            user = userDao.findByEmail(email);
        } catch (DaoException ex) {
            throw new UsernameNotFoundException("User not found.");
        }

        if (user != null) {
            return new UserDto(user);
        } else {
            throw new UsernameNotFoundException("User not found.");
        }

    }
}
