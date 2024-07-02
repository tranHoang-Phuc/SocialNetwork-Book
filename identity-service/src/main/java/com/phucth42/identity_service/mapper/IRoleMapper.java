package com.phucth42.identity_service.mapper;

import com.phucth42.identity_service.dto.request.RoleRequest;
import com.phucth42.identity_service.dto.response.RoleResponse;
import com.phucth42.identity_service.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IRoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);
    RoleResponse toRoleResponse(Role role);
}
