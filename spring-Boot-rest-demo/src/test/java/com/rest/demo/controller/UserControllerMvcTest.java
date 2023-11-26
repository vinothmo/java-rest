package com.rest.demo.controller;
import com.rest.demo.controller.helper.JsonHelper;
import com.rest.demo.model.User;
import com.rest.demo.service.UserService;
import com.rest.demo.utility.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class UserControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    public void should_find_all_users_returns_200_nominal_case() throws Exception {
        //Given
        User user = givenUser();
        List<User> users = Collections.singletonList(user);
        when(userService.findAll()).thenReturn(users);
        String expected = JsonHelper.toJson(users).orElse("");
        //When
        mockMvc.perform(get(Constants.USER_URL)
                .contentType(MediaType.APPLICATION_JSON))
                //Then
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    public void should_find_user_by_id_returns_200_nominal_case() throws Exception {
        //Given
        User user = givenUser();
        when(userService.findById(Constants.ID)).thenReturn(user);
        String expected = JsonHelper.toJson(user).orElse("");
        //When
        mockMvc.perform(get(Constants.USER_ID_URL, Constants.ID)
                .contentType(MediaType.APPLICATION_JSON))
                //Then
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    public void should_save_user_returns_200_nominal_case() throws Exception {
        //Given
        User createRequest = givenUserRequest();
        User user = givenUser();
        String createRequestJson = JsonHelper.toJson(createRequest).orElse("");
        String expected = JsonHelper.toJson(user).orElse("");
        //When
        mockMvc.perform(post(Constants.USER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createRequestJson))
                //Then
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    public void should_delete_user_by_id_returns_200_nominal_case() throws Exception {
        //Given
        User user = givenUser();
        when(userService.deleteById(Constants.ID)).thenReturn(user);
        String expected = JsonHelper.toJson(user).orElse("");
        //When
        mockMvc.perform(delete(Constants.USER_ID_URL, Constants.ID)
                .contentType(MediaType.APPLICATION_JSON))
                //Then
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    public void should_update_user_returns_200_nominal_case() throws Exception {
        //Given
        User user = givenUser();
        User userRequest = givenUserRequest();
        when(userService.update(user,user.getId())).thenReturn(user);
        String userRequestJson = JsonHelper.toJson(userRequest).orElse("");
        String expected = JsonHelper.toJson(user).orElse("");
        //When
        mockMvc.perform(put(Constants.USER_ID_URL, Constants.ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userRequestJson))
                //Then
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    private User givenUserRequest() {
        User user = new User();
        user.setName(Constants.NAME);
        user.setEmail(Constants.EMAIL);
        user.setLocation(Constants.LOCATION);
        user.setProfilePicture(Constants.PROFILE_PICTURE);
        return user;
    }


    private User givenUser() {
        User user = new User();
        user.setName(Constants.NAME);
        user.setEmail(Constants.EMAIL);
        user.setLocation(Constants.LOCATION);
        user.setProfilePicture(Constants.PROFILE_PICTURE);
        return user;
    }
}
