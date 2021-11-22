package online.smiechowicz.service;

import online.smiechowicz.domain.CreateUserRequest;
import online.smiechowicz.domain.UpdateUserRequest;
import online.smiechowicz.domain.User;
import online.smiechowicz.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class BackendService {
    private final UserRepository userRepository;

    public BackendService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return UserMapper.toDto(userRepository.findAll());
    }

    public User createUser(CreateUserRequest request) {
        return UserMapper.toDto(userRepository.save(UserMapper.toModel(request)));
    }

    public User getUser(String userId) {
        return userRepository.findByUserId(userId)
                .map(UserMapper::toDto)
                .orElseThrow(NotFoundException::new);
    }

    public User updateUser(String userId, UpdateUserRequest request) {
        return userRepository.findByUserId(userId)
                .map(user -> {
                    user.setEmail(request.getEmail());
                    user.setName(request.getName());
                    return userRepository.save(user);
                })
                .map(UserMapper::toDto)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public void deleteUser(String userId) {
        userRepository.deleteByUserId(userId);
    }
}
