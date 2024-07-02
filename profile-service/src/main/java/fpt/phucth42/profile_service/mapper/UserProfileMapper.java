package fpt.phucth42.profile_service.mapper;

import fpt.phucth42.profile_service.dto.request.ProfileCreationRequest;
import fpt.phucth42.profile_service.dto.response.UserProfileResponse;
import fpt.phucth42.profile_service.entity.UserProfile;
import org.mapstruct.Mapper;
import org.springframework.web.bind.annotation.Mapping;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(ProfileCreationRequest request);
    UserProfileResponse toUserProfileResponse(UserProfile userProfile);
}
