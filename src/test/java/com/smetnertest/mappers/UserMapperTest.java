package com.smetnertest.mappers;

import com.smetnertest.dto.DtoUser;
import com.smetnertest.model.Contact;
import com.smetnertest.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static com.smetnertest.TestHelper.getTestDtoUser;
import static com.smetnertest.TestHelper.getTestUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class UserMapperTest {
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private User user;
    private DtoUser dtoUser;

    @Before
    public void setUp() {
        user = getTestUser();
        dtoUser = getTestDtoUser();
    }

    @Test
    public void fromUserToDtoUser() throws Exception {
        dtoUser = userMapper.fromUserToDtoUser(user);
        assertNotNull(dtoUser);
        Contact contact = user.getContact();
        assertEquals(user.getId(), dtoUser.getId().longValue());
        assertEquals(user.getFirstName(), dtoUser.getFirstName());
        assertEquals(user.getMiddleName(), dtoUser.getMiddleName());
        assertEquals(user.getLastName(), dtoUser.getLastName());
        assertEquals(contact.getId(), dtoUser.getContactId().longValue());
        assertEquals(contact.getComment(), dtoUser.getComment());
        assertEquals(contact.getNumber(), dtoUser.getPhoneNumber());
        assertEquals(contact.getType(), dtoUser.getPhoneType());

    }

    @Test
    public void fromDtoUserToUser() throws Exception {
        user = userMapper.fromDtoUserToUser(dtoUser);
        assertNotNull(user);
        Contact contact = user.getContact();
        assertEquals(user.getId(), dtoUser.getId().longValue());
        assertEquals(user.getFirstName(), dtoUser.getFirstName());
        assertEquals(user.getMiddleName(), dtoUser.getMiddleName());
        assertEquals(user.getLastName(), dtoUser.getLastName());
        assertEquals(contact.getId(), dtoUser.getContactId().longValue());
        assertEquals(contact.getComment(), dtoUser.getComment());
        assertEquals(contact.getNumber(), dtoUser.getPhoneNumber());
        assertEquals(contact.getType(), dtoUser.getPhoneType());
    }

}