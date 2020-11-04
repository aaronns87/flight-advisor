package com.aaron.challenge.flightadvisor.users.web;

import com.aaron.challenge.flightadvisor.users.Role;
import com.aaron.challenge.flightadvisor.users.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;

    @Test
    public void postToEntity_userCreate_user() {
        var userCreate = new UserCreate();
        userCreate.setUserName("userName");
        userCreate.setPassword("password");
        userCreate.setFirstName("firstName");
        userCreate.setLastName("lastName");
        userCreate.setRole(Role.USER);

        var user = userMapper.postToEntity(userCreate);

        assertThat(user.getId()).isNotBlank();
        assertThat(user.getUserName()).isEqualTo("userName");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getFirstName()).isEqualTo("firstName");
        assertThat(user.getLastName()).isEqualTo("lastName");
        assertThat(user.getRole()).isEqualTo(Role.USER);
    }

    @Test
    public void putToEntity_userCreate_target_user() {
        var userCreate = new UserCreate();
        userCreate.setUserName("userName");
        userCreate.setPassword("password");
        userCreate.setFirstName("firstName");
        userCreate.setLastName("lastName");
        userCreate.setRole(Role.USER);

        var target = new User();

        var user = userMapper.putToEntity(userCreate, target);

        assertThat(user.getUserName()).isEqualTo("userName");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getFirstName()).isEqualTo("firstName");
        assertThat(user.getLastName()).isEqualTo("lastName");
        assertThat(user.getRole()).isEqualTo(Role.USER);
    }

    @Test
    public void patchToEntity_userUpdate_target_user() {
        var userUpdate = new UserUpdate();
        userUpdate.setUserName("userName");
        userUpdate.setPassword("password");
        userUpdate.setFirstName("firstName");
        userUpdate.setLastName("lastName");
        userUpdate.setRole(Role.USER);

        var target = new User();

        var user = userMapper.patchToEntity(userUpdate, target);

        assertThat(user.getUserName()).isEqualTo("userName");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getFirstName()).isEqualTo("firstName");
        assertThat(user.getLastName()).isEqualTo("lastName");
        assertThat(user.getRole()).isEqualTo(Role.USER);
    }

    @Test
    public void searchToEntity_userSearch_user() {
        var userSearch = new UserSearch();
        userSearch.setUserName("userName");
        userSearch.setFirstName("firstName");
        userSearch.setLastName("lastName");
        userSearch.setRole(Role.USER);

        var user = userMapper.searchToEntity(userSearch);

        assertThat(user.getUserName()).isEqualTo("userName");
        assertThat(user.getFirstName()).isEqualTo("firstName");
        assertThat(user.getLastName()).isEqualTo("lastName");
        assertThat(user.getRole()).isEqualTo(Role.USER);
    }

    @Test
    public void entityToResponse_user_userResponse() {
        var user = new User();
        user.setId("id");
        user.setUserName("userName");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPassword("password");
        user.setRole(Role.USER);

        var userResponse = userMapper.entityToResponse(user);

        assertThat(userResponse.getId()).isEqualTo("id");
        assertThat(userResponse.getUserName()).isEqualTo("userName");
        assertThat(userResponse.getFirstName()).isEqualTo("firstName");
        assertThat(userResponse.getLastName()).isEqualTo("lastName");
        assertThat(userResponse.getRole()).isEqualTo(Role.USER);
    }
}
