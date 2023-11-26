package com.rest.demo.controller;

import com.rest.demo.DemoApplication;
import com.rest.demo.controller.helper.HttpHelper;
import com.rest.demo.model.User;
import com.rest.demo.utility.Constants;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

    private static final String USER_URL = "http://localhost:%s/users";
    private static final String USER_ID_URL = "http://localhost:%s/users/%s";

    private static final int ID = 3;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void test_1_should_save_user() {
        //Given
        String url = String.format(USER_URL, port);
        User createRequest = givenUserRequest();
        HttpEntity<User> request = HttpHelper.getHttpEntity(createRequest);
        //When
        ResponseEntity<User> response = testRestTemplate.exchange(url, HttpMethod.POST, request, User.class);
        //Then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getId(), Constants.ID);
        assertEquals(response.getBody().getProfilePicture(), Constants.PROFILE_PICTURE);
        assertEquals(response.getBody().getName(), Constants.NAME);
        assertEquals(response.getBody().getLocation(), Constants.LOCATION);
        assertEquals(response.getBody().getEmail(), Constants.EMAIL);
    }

    @Test
    public void test_2_should_find_user_by_id() {
        //Given
        String url = String.format(USER_ID_URL, port, ID);
        HttpEntity<String> request = HttpHelper.getHttpEntity();
        //When
        ResponseEntity<User> response = testRestTemplate.exchange(url, HttpMethod.GET, request, User.class);
        //Then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getId(), Constants.ID);
        assertEquals(response.getBody().getProfilePicture(), Constants.PROFILE_PICTURE);
        assertEquals(response.getBody().getName(), Constants.NAME);
        assertEquals(response.getBody().getLocation(), Constants.LOCATION);
        assertEquals(response.getBody().getEmail(), Constants.EMAIL);
    }


    @Test
    public void test_4_should_update_user() {
        //Given
        String url = String.format(USER_ID_URL, port, ID);
        User userRequest = givenUserRequest();
        HttpEntity<User> request = HttpHelper.getHttpEntity(userRequest);
        //When
        ResponseEntity<User> response = testRestTemplate.exchange(url, HttpMethod.PUT, request, User.class);
        //Then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertEquals(Constants.ID, response.getBody().getId());
        assertEquals(Constants.PROFILE_PICTURE, response.getBody().getProfilePicture());
        assertEquals(Constants.NAME, response.getBody().getName());
        assertEquals(Constants.EMAIL, response.getBody().getEmail());
        assertEquals(Constants.LOCATION, response.getBody().getLocation());
    }

    @Test
    public void test_5_should_delete_user_by_id() {
        //Given
        String url = String.format(USER_ID_URL, port, ID);
        HttpEntity<String> request = HttpHelper.getHttpEntity();
        //When
        ResponseEntity<User> response = testRestTemplate.exchange(url, HttpMethod.DELETE, request, User.class);
        //Then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertEquals(Constants.ID, response.getBody().getId());
        assertEquals(Constants.PROFILE_PICTURE, response.getBody().getProfilePicture());
        assertEquals(Constants.NAME, response.getBody().getName());
        assertEquals(Constants.EMAIL, response.getBody().getEmail());
        assertEquals(Constants.LOCATION, response.getBody().getLocation());
    }

    private User givenUserRequest() {
        User userRequest = new User();
        userRequest.setProfilePicture(Constants.EMAIL);
        userRequest.setName(Constants.NAME);
        userRequest.setLocation(Constants.LOCATION);
        userRequest.setEmail(Constants.EMAIL);
        return userRequest;
    }
}
