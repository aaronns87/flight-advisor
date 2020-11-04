package com.aaron.challenge.flightadvisor.users.web;

import com.aaron.challenge.flightadvisor.config.web.GenericRestMapper;
import com.aaron.challenge.flightadvisor.users.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements GenericRestMapper<User, UserCreate, UserUpdate, UserSearch, UserResponse> {

    @Override
    public User postToEntity(UserCreate userCreate) {
        return User.builder()
                .role(userCreate.getRole())
                .firstName(userCreate.getFirstName())
                .lastName(userCreate.getLastName())
                .userName(userCreate.getUserName())
                .password(userCreate.getPassword())
                .build();
    }

    @Override
    public User putToEntity(UserCreate userCreate, User target) {
        target.setRole(userCreate.getRole());
        target.setFirstName(userCreate.getFirstName());
        target.setLastName(userCreate.getLastName());
        target.setUserName(userCreate.getUserName());
        target.setPassword(userCreate.getPassword());

        return target;
    }

    @Override
    public User patchToEntity(UserUpdate userUpdate, User target) {
        setIfNotNull(userUpdate::getRole, target::setRole);
        setIfNotNull(userUpdate::getFirstName, target::setFirstName);
        setIfNotNull(userUpdate::getLastName, target::setLastName);
        setIfNotNull(userUpdate::getUserName, target::setUserName);
        setIfNotNull(userUpdate::getPassword, target::setPassword);

        return target;
    }

    @Override
    public User searchToEntity(UserSearch userSearch) {
        return User.builder()
                .role(userSearch.getRole())
                .firstName(userSearch.getFirstName())
                .lastName(userSearch.getLastName())
                .userName(userSearch.getUserName())
                .build();
    }

    @Override
    public UserResponse entityToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .role(user.getRole())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userName(user.getUserName())
                .build();
    }
}
