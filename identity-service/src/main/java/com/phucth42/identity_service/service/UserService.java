package com.phucth42.identity_service.service;

import com.phucth42.identity_service.constant.PredefinedRole;
import com.phucth42.identity_service.dto.request.UserCreationRequest;
import com.phucth42.identity_service.dto.request.UserUpdateRequest;
import com.phucth42.identity_service.dto.response.ApiResponse;
import com.phucth42.identity_service.dto.response.UserResponse;
import com.phucth42.identity_service.entity.Role;
import com.phucth42.identity_service.entity.User;
import com.phucth42.identity_service.exception.AppException;
import com.phucth42.identity_service.exception.ErrorCode;
import com.phucth42.identity_service.mapper.IUserMapper;
import com.phucth42.identity_service.mapper.ProfileMapper;
import com.phucth42.identity_service.repository.IRoleRepository;
import com.phucth42.identity_service.repository.IUserRepository;
import com.phucth42.identity_service.repository.httpclient.ProfileClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    IUserRepository userRepository;
    IUserMapper userMapper;
    PasswordEncoder passwordEncoder;
    IRoleRepository roleRepository;
    ProfileClient profileClient;
    ProfileMapper profileMapper;

    public ApiResponse<UserResponse> createUser(UserCreationRequest request) {
        User checkedUser = userRepository.findByUsername(request.getUsername()).orElse(null);

        if (checkedUser != null) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toDomainModel(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Set<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);
        user.setRoles(roles);
        userRepository.save(user);
        var profileRequest = profileMapper.toProfileCreationRequest(request);
        profileRequest.setUserId(user.getId());
        profileClient.createProfile(profileRequest);
        UserResponse userResponse = userMapper
                .toUserResponse(userRepository
                        .findByUsername(request.getUsername())
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .code(1000)
                .result(userResponse)
                .build();
        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        return userMapper.toUsersResponse(userRepository.findAll());
    }
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<UserResponse> updateUser(String username, UserUpdateRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        User checkedUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userMapper.updateUser(user, request);
        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        userRepository.save(user);

        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .code(1000)
                .message("Update successfully")
                .result(userMapper.toUserResponse(user))
                .build();
        return response;
    }
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> deleteUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userRepository.delete(user);
        return ApiResponse.<String>builder()
                .code(1000)
                .message("Delete successfully")
                .build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<UserResponse> getUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if (user == null)
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .result(userMapper.toUserResponse(user))
                .build();
    }
}
