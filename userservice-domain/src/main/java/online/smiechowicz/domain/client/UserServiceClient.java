package online.smiechowicz.domain.client;

import online.smiechowicz.domain.CreateUserRequest;
import online.smiechowicz.domain.UpdateUserRequest;
import online.smiechowicz.domain.User;

import java.util.List;

public interface UserServiceClient {
    List<User> getUsers();
    User createUser(CreateUserRequest request);
    User getUser(String userId);
    User updateUser(String userId, UpdateUserRequest request);
    void deleteUser(String userId);
}
