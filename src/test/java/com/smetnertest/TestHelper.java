package com.smetnertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smetnertest.dto.DtoUser;
import com.smetnertest.model.ListWrapper;
import com.smetnertest.model.Contact;
import com.smetnertest.model.User;

import java.util.ArrayList;
import java.util.List;


public class TestHelper {
    public static final Long TEST_ID = 1L;
    public static final String TEST_FIRST_NAME = "Anatolij";
    public static final String TEST_MIDDLE_NAME = "Leonidovych";
    public static final String TEST_LAST_NAME = "Tymoshenko";
    public static final String TEST_PHONE_NUMBER = "+380650924685";
    public static final String TEST_COMMENT = "the best number";
    public static final String TEST_TYPE = "Mobile";

    public static User getTestUser() {
        User user = new User();
        user.setId(TEST_ID);
        user.setContact(getTestContact());
        user.setFirstName(TEST_FIRST_NAME);
        user.setMiddleName(TEST_MIDDLE_NAME);
        user.setLastName(TEST_LAST_NAME);
        return user;
    }

    public static Contact getTestContact() {
        Contact contact = new Contact();
        contact.setId(TEST_ID);
        contact.setNumber(TEST_PHONE_NUMBER);
        contact.setComment(TEST_COMMENT);
        contact.setType(TEST_TYPE);
        return contact;
    }

    public static DtoUser getTestDtoUser() {
        DtoUser user = new DtoUser();
        user.setContactId(TEST_ID);
        user.setId(TEST_ID);
        user.setComment(TEST_COMMENT);
        user.setPhoneType(TEST_TYPE);
        user.setPhoneNumber(TEST_PHONE_NUMBER);
        user.setFirstName(TEST_FIRST_NAME);
        user.setMiddleName(TEST_MIDDLE_NAME);
        user.setLastName(TEST_LAST_NAME);
        return user;
    }

    public static ListWrapper getTestList() {
        List<DtoUser> users = new ArrayList<>(5);
        for (int i = 0; i < users.size(); i++) {
            users.add(getTestDtoUser());
        }
        return new ListWrapper(users);
    }

    public static String asJsonString(final Object object) throws Exception {
        return new ObjectMapper().writeValueAsString(object);
    }

}
