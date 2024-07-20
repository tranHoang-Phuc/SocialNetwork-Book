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
@RequestMapping("/internal/users")
public class InternalUserProfileController {
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


}
