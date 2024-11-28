package tech.buildrun.springsecurity.service;

import tech.buildrun.springsecurity.model.dto.CreateUserDto;

public interface UserService {

    CreateUserDto createUser(CreateUserDto createUserDto);
}
