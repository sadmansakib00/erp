package com.brainstation23.erp.service;

import com.brainstation23.erp.exception.custom.custom.NotFoundException;
import com.brainstation23.erp.mapper.UserMapper;
import com.brainstation23.erp.model.domain.User;
import com.brainstation23.erp.model.dto.CreateUserRequest;
import com.brainstation23.erp.model.dto.UpdateUserRequest;
import com.brainstation23.erp.persistence.entity.UserEntity;
import com.brainstation23.erp.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    public static final String USER_NOT_FOUND = "User Not Found";
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public Page<User> getAll(Pageable pageable) {
        var entities = userRepository.findAll(pageable);
        return entities.map(userMapper::entityToDomain);
    }

    public User getOne(UUID id) {
        var entity = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        return userMapper.entityToDomain(entity);
    }

    public UUID createOne(CreateUserRequest createUserRequest) {
        var entity = new UserEntity();
        entity.setId(UUID.randomUUID())
                .setFirstName(createUserRequest.getFirstName())
                .setLastName(createUserRequest.getLastName())
                .setEmail(createUserRequest.getEmail())
                .setAccountBalance(createUserRequest.getAccountBalance())
                .setRole(createUserRequest.getRole())
                .setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        var createdEntity = userRepository.save(entity);
        return createdEntity.getId();
    }

    public void updateOne(UUID id, UpdateUserRequest updateUserRequest) {
        var entity = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        entity.setFirstName(updateUserRequest.getFirstName());
        entity.setLastName(updateUserRequest.getLastName());
        entity.setEmail(updateUserRequest.getEmail());
        entity.setAccountBalance(updateUserRequest.getAccountBalance());
        entity.setRole(updateUserRequest.getRole());
        userRepository.save(entity);
    }

    public void deleteOne(UUID id) {
        userRepository.deleteById(id);
    }

}
