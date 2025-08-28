package co.com.powerup.r2dbc.mapper;

import co.com.powerup.model.rol.Rol;
import co.com.powerup.model.users.Users;
import co.com.powerup.r2dbc.entities.UsersEntity;

public class UsserMapperInfra {
  public static UsersEntity toData(Users user) {
    UsersEntity entity = new UsersEntity();
    entity.setId(user.getId());
    entity.setFirstName(user.getFirstName());
    entity.setLastName(user.getLastName());
    entity.setBirthDate(user.getBirthDate());
    entity.setAddress(user.getAddress());
    entity.setPhone(user.getPhone());
    entity.setEmail(user.getEmail());
    entity.setBaseSalary(user.getBaseSalary());
    entity.setIdRole(user.getRole() != null ? user.getRole().getId() : null); // 👈 clave
    entity.setCreatedAt(user.getCreatedAt());
    entity.setUpdatedAt(user.getUpdatedAt());
    return entity;
  }

  public static Users toDomain(UsersEntity entity) {
    return Users.builder()
            .id(entity.getId())
            .firstName(entity.getFirstName())
            .lastName(entity.getLastName())
            .birthDate(entity.getBirthDate())
            .address(entity.getAddress())
            .phone(entity.getPhone())
            .email(entity.getEmail())
            .baseSalary(entity.getBaseSalary())
            .role(Rol.builder().id(entity.getIdRole()).build()) // 👈 clave
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .build();
  }
}
