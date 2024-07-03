package fpt.phucth42.profile_service.controller;

import fpt.phucth42.profile_service.dto.request.ProfileCreationRequest;
import fpt.phucth42.profile_service.dto.response.ApiResponse;
import fpt.phucth42.profile_service.dto.response.UserProfileResponse;
import fpt.phucth42.profile_service.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("users")
public class UserProfileController {
    UserProfileService userProfileService;

    @PostMapping()
    ApiResponse<UserProfileResponse> createUserProfile(@RequestBody ProfileCreationRequest request) {
        UserProfileResponse response = userProfileService.createUserProfile(request);
        return ApiResponse.<UserProfileResponse>builder()
                .code(1000)
                .message("User profile created successfully")
                .result(response)
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<UserProfileResponse> getUserProfile(@PathVariable String id) {
        UserProfileResponse response = userProfileService.getUserProfile(id);
        return ApiResponse.<UserProfileResponse>builder()
                .code(1000)
                .message("User profile retrieved successfully")
                .result(response)
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<UserProfileResponse> updateUserProfile(@RequestBody ProfileCreationRequest request, @PathVariable String id) {
        UserProfileResponse response = userProfileService.updateUserProfile(request, id);
        return ApiResponse.<UserProfileResponse>builder()
                .code(1000)
                .message("User profile updated successfully")
                .result(response)
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteUserProfile(@PathVariable String id) {
        userProfileService.deleteUserProfile(id);
        return ApiResponse.<String>builder()
                .code(1000)
                .message("User profile deleted successfully")
                .build();
    }
}
