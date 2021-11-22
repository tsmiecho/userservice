package online.smiechowicz.service;

import online.smiechowicz.Application;
import online.smiechowicz.domain.CreateUserRequest;
import online.smiechowicz.domain.PathConstants;
import online.smiechowicz.domain.UpdateUserRequest;
import online.smiechowicz.domain.User;
import online.smiechowicz.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {Application.class})
public class ApplicationTest {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void shouldAddUser() {

        // given
        final CreateUserRequest request = createUserRequest();

        // when
        ResponseEntity<User> createUserResponse = client.postForEntity(PathConstants.USERS, request, User.class);

        // then
        assertThat(createUserResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createUserResponse.getBody().getEmail()).isEqualTo(request.getEmail());
        assertThat(createUserResponse.getBody().getName()).isEqualTo(request.getName());
        List<User> users = Arrays.asList(client.getForObject(PathConstants.USERS, User[].class));
        assertThat(users.size()).isEqualTo(1);
        assertThat(users.get(0).getEmail()).isEqualTo(request.getEmail());
        assertThat(users.get(0).getName()).isEqualTo(request.getName());
    }

    @Test
    public void shouldGetUserById() {

        // given
        final CreateUserRequest request = createUserRequest();

        // when
        ResponseEntity<User> createUserResponse = client.postForEntity(PathConstants.USERS, request, User.class);

        // then
        assertThat(createUserResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createUserResponse.getBody().getEmail()).isEqualTo(request.getEmail());
        assertThat(createUserResponse.getBody().getName()).isEqualTo(request.getName());

        // when
        final String userId = createUserResponse.getBody().getId();
        User user = client.getForObject(PathConstants.USER_BY_ID, User.class, userId);

        // then
        assertThat(user.getEmail()).isEqualTo(request.getEmail());
        assertThat(user.getName()).isEqualTo(request.getName());
        assertThat(user.getId()).isEqualTo(userId);
    }

    @Test
    public void shouldUpdateUser() {

        // given
        User response = client.postForObject(PathConstants.USERS, createUserRequest(), User.class);
        final UpdateUserRequest userRequest = UpdateUserRequest.builder()
                .email("one@gmail.com")
                .name("two")
                .build();
        final RequestEntity requestEntity = new RequestEntity(userRequest, HttpMethod.PUT, URI.create(PathConstants.USERS + "/" + response.getId()));

        // when
        final ResponseEntity<User> updateResponse = client.exchange(requestEntity, User.class);

        // then
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody().getEmail()).isEqualTo(userRequest.getEmail());
        assertThat(updateResponse.getBody().getName()).isEqualTo(userRequest.getName());
        assertThat(updateResponse.getBody().getId()).isEqualTo(response.getId());
        User user = client.getForObject(PathConstants.USER_BY_ID, User.class, response.getId());
        assertThat(user.getEmail()).isEqualTo(userRequest.getEmail());
        assertThat(user.getName()).isEqualTo(userRequest.getName());
        assertThat(user.getId()).isEqualTo(response.getId());
    }

    @Test
    void shouldDeleteUser() {
        // given
        User user = client.postForObject(PathConstants.USERS, createUserRequest(), User.class);
        final RequestEntity requestEntity = new RequestEntity(HttpMethod.DELETE, URI.create(PathConstants.USERS + "/" + user.getId()));

        // when
        final ResponseEntity<Void> response = client.exchange(requestEntity, Void.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void shouldNotFindNotExistingUser() {

        // given
        String notExistingId = UUID.randomUUID().toString();

        // when
        final ResponseEntity<User> response = client.getForEntity(PathConstants.USER_BY_ID, User.class, notExistingId);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void shouldNotCreateUserWithoutEmail() {
        // given
        final CreateUserRequest request = CreateUserRequest.builder()
                .name("Cezary")
                .build();

        // when
        ResponseEntity<User> createUserResponse = client.postForEntity(PathConstants.USERS, request, User.class);

        // then
        assertThat(createUserResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldNotCreateUserWithoutName() {
        // given
        final CreateUserRequest request = CreateUserRequest.builder()
                .email("email@gmail.com")
                .build();

        // when
        ResponseEntity<User> createUserResponse = client.postForEntity(PathConstants.USERS, request, User.class);

        // then
        assertThat(createUserResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldNotCreateUserWithInvalidEmail() {
        // given
        final CreateUserRequest request = CreateUserRequest.builder()
                .email("invalidEmail")
                .name("Name")
                .build();

        // when
        ResponseEntity<User> createUserResponse = client.postForEntity(PathConstants.USERS, request, User.class);

        // then
        assertThat(createUserResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldNotUpdateUserWithInvalidEmail() {
        // given
        User response = client.postForObject(PathConstants.USERS, createUserRequest(), User.class);
        final UpdateUserRequest userRequest = UpdateUserRequest.builder()
                .email("invalidEmail")
                .name("two")
                .build();
        final RequestEntity requestEntity = new RequestEntity(userRequest, HttpMethod.PUT, URI.create(PathConstants.USERS + "/" + response.getId()));

        // when
        final ResponseEntity<User> updateResponse = client.exchange(requestEntity, User.class);

        // then
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    private CreateUserRequest createUserRequest() {
        return CreateUserRequest.builder()
                .email("email@gmail.com")
                .name("Name")
                .build();
    }
}
