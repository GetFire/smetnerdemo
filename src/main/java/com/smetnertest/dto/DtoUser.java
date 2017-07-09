package com.smetnertest.dto;

import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Data transfer object to transfer user  related data for {@link com.smetnertest.model.User}
 */

@Data
public class DtoUser {
    private Long id;
    @NotNull
    private String firstName;

    private String middleName;

    @NotNull
    private String lastName;

    @NotNull
    @Size(min = 13, max = 13)
    @Pattern(regexp = "^((8|\\+3)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$")
    private String phoneNumber;
    @NotNull
    private String phoneType;

    private String comment;

    @NotNull
    private Long contactId;

    @AssertTrue
    private boolean isRightContent() {
        return phoneType != null && (phoneType.contains("Unknown") || phoneType.contains("Mobile") || phoneType
                .contains("Home"));
    }


}
