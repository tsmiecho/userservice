package online.smiechowicz.controller;

import lombok.extern.slf4j.Slf4j;
import online.smiechowicz.domain.CreateUserRequest;
import online.smiechowicz.domain.PathConstants;
import online.smiechowicz.domain.UpdateUserRequest;
import online.smiechowicz.domain.User;
import online.smiechowicz.service.BackendService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class BackedController {

    private final BackendService backendService;

    public BackedController(BackendService backendService) {
        this.backendService = backendService;
    }

    @GetMapping(value = PathConstants.USERS)
    public List<User> getUsers() {
        log.info("Received get user request");
        return backendService.getUsers();
    }

    @PostMapping(value = PathConstants.USERS)
    public User createUser(@RequestBody @Valid CreateUserRequest request) {
        log.info("Received request to create user {}", request);
        return backendService.createUser(request);
    }

    @GetMapping(value = PathConstants.USER_BY_ID)
    public User getUser(@PathVariable String userId) {
        log.info("Received request to get user by id {}", userId);
        return backendService.getUser(userId);
    }

    @PutMapping(value = PathConstants.USER_BY_ID)
    public User updateUser(@PathVariable String userId, @RequestBody @Valid  UpdateUserRequest request) {
        log.info("Received request {} to update user by id {}", request, userId);
        return backendService.updateUser(userId, request);
    }

    @DeleteMapping(value = PathConstants.USER_BY_ID)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String userId) {
        log.info("Received request to delete user by id {}", userId);
        backendService.deleteUser(userId);
    }
}
