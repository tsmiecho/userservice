package online.smiechowicz.service;

import lombok.experimental.UtilityClass;
import online.smiechowicz.domain.CreateUserRequest;
import online.smiechowicz.domain.User;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@UtilityClass
public class UserMapper {

    public static List<User> toDto(List<online.smiechowicz.model.User> users) {
        return users.stream()
                .map(user -> User.builder()
                        .id(user.getUserId())
                        .email(user.getEmail())
                        .name(user.getName())
                        .build())
                .collect(Collectors.toList());
    }

    public static User toDto(online.smiechowicz.model.User user) {
        return User.builder()
                .id(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public static online.smiechowicz.model.User toModel(CreateUserRequest request) {
        return online.smiechowicz.model.User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .userId(UUID.randomUUID().toString())
                .build();
    }
}
