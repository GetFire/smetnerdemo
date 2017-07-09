package com.smetnertest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.smetnertest.dto.DtoUser;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class ListWrapper {
    @Valid
    private List<DtoUser> users;

    @JsonCreator
    public ListWrapper(List<DtoUser> users) {
        this.users = users;
    }

    @JsonValue
    public List<DtoUser> getUsers() {
        return users;
    }
}
