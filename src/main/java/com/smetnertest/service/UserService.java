package com.smetnertest.service;

import com.smetnertest.dto.DtoUser;

import java.util.List;

public interface UserService {

    DtoUser updateUser(DtoUser dtoUser, long id);

    List<DtoUser> getAllUsers();

    DtoUser findOne(long id);

    long saveUser(DtoUser user);

    DtoUser getUser(String lastName);
}
