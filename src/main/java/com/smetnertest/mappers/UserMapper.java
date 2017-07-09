package com.smetnertest.mappers;

import com.smetnertest.dto.DtoUser;
import com.smetnertest.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Mapstruct provided mapper to convert between {@link com.smetnertest.model.User} and
 * {@link com.smetnertest.dto.DtoUser}.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mappings({
            @Mapping(target = "phoneNumber", source = "contact.number"),
            @Mapping(target = "phoneType", source = "contact.type"),
            @Mapping(target = "comment", source = "contact.comment"),
            @Mapping(target = "contactId", source = "contact.id")})
    DtoUser fromUserToDtoUser(User user);

    @Mappings({
            @Mapping(source = "phoneNumber", target = "contact.number"),
            @Mapping(source = "phoneType", target = "contact.type"),
            @Mapping(source = "comment", target = "contact.comment"),
            @Mapping(source = "contactId", target = "contact.id")})
    User fromDtoUserToUser(DtoUser dtoUser);
}