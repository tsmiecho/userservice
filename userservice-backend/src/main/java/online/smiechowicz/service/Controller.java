package online.smiechowicz.service;

import lombok.extern.slf4j.Slf4j;
import online.smiechowicz.domain.CreateUserRequest;
import online.smiechowicz.domain.PathConstants;
import online.smiechowicz.domain.UpdateUserRequest;
import online.smiechowicz.domain.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class Controller {

    @GetMapping(value = PathConstants.USERS)
    public List<User> getUsers() {
        log.info("get");
        return List.of(User.builder()
                .name("Czarek")
                        .email("czarek@gmail.com")
                        .id(UUID.randomUUID().toString())
                .build());
    }

    @PostMapping(value = PathConstants.USERS)
    public String createUser(@RequestBody CreateUserRequest request) {
        log.info(request.toString());
        return "Hello World";
    }

    @GetMapping(value = PathConstants.USER_BY_ID)
    public String getUser(@PathVariable String userId) {
        log.info(userId);
        return "Hello World";
    }

    @PutMapping(value = PathConstants.USER_BY_ID)
    public String updateUser(@PathVariable String userId, @RequestBody UpdateUserRequest request) {
        log.info(userId);
        log.info(request.toString());
        return "Hello World";
    }

    @DeleteMapping(value = PathConstants.USER_BY_ID)
    public String updateUser(@PathVariable String userId) {
        log.info(userId);
        return "Hello World";
    }
}
