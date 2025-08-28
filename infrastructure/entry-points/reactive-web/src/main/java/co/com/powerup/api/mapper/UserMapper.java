package co.com.powerup.api.mapper;

import co.com.powerup.api.dto.UserRequestDTO;
import co.com.powerup.api.dto.UserResponseDTO;
import co.com.powerup.model.users.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface  UserMapper {

  @Mapping(source = "idRole", target = "role.id")
  Users toDomain(UserRequestDTO request);

  @Mapping(source = "role.id", target = "idRole")
  UserResponseDTO toResponse(Users user);

}
