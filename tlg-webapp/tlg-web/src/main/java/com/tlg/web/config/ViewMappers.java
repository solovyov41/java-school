package com.tlg.web.config;

import com.tlg.core.dto.UserDto;
import com.tlg.core.entity.User;
import com.tlg.core.dto.UserViewDto;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ViewMappers {

    private final ModelMapper modelmapper;

    @Autowired
    public ViewMappers(ModelMapper modelmapper) {
        this.modelmapper = modelmapper;
    }

    @Bean
    public TypeMap<UserDto, UserViewDto> getUserDtoUserViewTypeMap() {

        return modelmapper.createTypeMap(UserDto.class, UserViewDto.class)
                .addMappings(mapping -> mapping.skip(UserViewDto::setPassword))
                .addMappings(mapping -> mapping.skip(UserViewDto::setConfirmPassword));
    }

    @Bean
    public TypeMap<UserViewDto, User> getUserViewUserTypeMap() {
        Condition notNull = ctx -> ctx.getSource() != null;
        return modelmapper.createTypeMap(UserViewDto.class, User.class)
                .addMappings(mapping -> mapping.when(notNull).map(UserViewDto::getAvatar, User::setAvatar));
    }

    @Bean
    public TypeMap<UserDto, User> userDtoUserTypeMap() {
        Condition notNull = ctx -> ctx.getSource() != null;
        return modelmapper.createTypeMap(UserDto.class, User.class)
                .addMappings(mapping -> mapping.when(notNull).map(UserDto::getAvatar, User::setAvatar));
    }

}

