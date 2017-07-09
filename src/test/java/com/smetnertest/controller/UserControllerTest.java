package com.smetnertest.controller;

import com.smetnertest.dto.DtoUser;
import com.smetnertest.model.ListWrapper;
import com.smetnertest.model.Contact;
import com.smetnertest.service.ContactService;
import com.smetnertest.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.smetnertest.TestHelper.asJsonString;
import static com.smetnertest.TestHelper.getTestContact;
import static com.smetnertest.TestHelper.getTestDtoUser;
import static com.smetnertest.TestHelper.getTestList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private static final long ID = 1L;
    private static final String URL = "/api/users";
    private static final String URL_WITH_ID = "/api/users/{id}";
    private static final String URL_ALL = "/api/users/all";

    @Mock
    private UserService userService;
    @Mock
    private ContactService contactService;

    @InjectMocks
    private UserController controller;
    private MockMvc mvc;
    private DtoUser dtoUser;
    private Contact contact;
    private ListWrapper list;


    @Before
    public void setUp() {
        mvc = standaloneSetup(controller).build();
        dtoUser = getTestDtoUser();
        contact = getTestContact();
        list = getTestList();
    }

    @Test
    public void getAllUser() throws Exception {
        mvc.perform(get(URL))
                .andExpect(status().isOk());
        verify(userService, times(1)).getAllUsers();
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void getAllUsersShouldReturnEmptyListAndNoContentStatus() throws Exception {
        when(userService.getAllUsers()).thenReturn(null);
        mvc.perform(get(URL))
                .andExpect(status().isNoContent());
        verify(userService, times(1)).getAllUsers();
        verifyNoMoreInteractions(userService);

    }

    @Test
    public void updateUserContact_ContactSuccessfullyUpdatedAndSuccessfullyPassValidation_ShouldReturnUpdatedUser() throws Exception {
        dtoUser.setPhoneNumber("+380930865783");
        dtoUser.setPhoneType("Unknown");
        when(userService.findOne(ID)).thenReturn(dtoUser);
        when(contactService.updateContact(dtoUser))
                .thenReturn(contact);
        mvc.perform(put(URL_WITH_ID, dtoUser.getId()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(dtoUser)))
                .andExpect(status().isOk());
        verify(userService, times(1)).findOne(ID);
        verify(contactService, times(1)).updateContact(dtoUser);
        verifyNoMoreInteractions(userService);
        verifyNoMoreInteractions(contactService);
    }

    @Test
    public void updateUserContact_withWrongId_ShouldReturnBadGateway() throws Exception {
        dtoUser.setId(100L);
        mvc.perform(put(URL_WITH_ID, dtoUser.getId()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(dtoUser))).andExpect(status().isBadGateway());
        verify(userService, times(1)).findOne(dtoUser.getId());
        verifyNoMoreInteractions(contactService);

    }

    @Test
    public void updateUserContact_FailValidation_WrongNumber() throws Exception {
        dtoUser.setPhoneNumber("+88");
        dtoUser.setPhoneType(null);
        mvc.perform(put(URL_WITH_ID, dtoUser.getId()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(dtoUser))).andExpect(status().isBadRequest());
        verifyNoMoreInteractions(userService);
        verifyNoMoreInteractions(contactService);
    }

    @Test
    public void updateUserContact_FailValidation_WrongPhoneType() throws Exception {
        dtoUser.setPhoneType(null);
        mvc.perform(put(URL_WITH_ID, dtoUser.getId()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(dtoUser))).andExpect(status().isBadRequest());
        verifyNoMoreInteractions(userService);
        verifyNoMoreInteractions(contactService);
    }

    @Test
    public void updateUserContact_FailValidation_WrongCredentials() throws Exception {
        dtoUser.setFirstName(null);
        dtoUser.setLastName(null);
        mvc.perform(put(URL_WITH_ID, dtoUser.getId()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(dtoUser))).andExpect(status().isBadRequest());
        verifyNoMoreInteractions(userService);
        verifyNoMoreInteractions(contactService);
    }


    @Test
    public void updateAllUsers() throws Exception {
        mvc.perform(put(URL_ALL).contentType(MediaType.APPLICATION_JSON).content(asJsonString(list)))
                .andExpect(status().isOk());
        verify(userService, times(2)).getAllUsers();
        verify(contactService, times(list.getUsers().size())).updateContact(dtoUser);
        verifyNoMoreInteractions(contactService);
        verifyNoMoreInteractions(userService);
    }
    @Test
    public void updateAllUsers_WithWrongList_ShouldReturnBadRequest() throws Exception {
        list.setUsers(null);
        mvc.perform(put(URL_ALL).contentType(MediaType.APPLICATION_JSON).content(asJsonString(list)))
                .andExpect(status().isBadRequest());
        verifyNoMoreInteractions(contactService);
        verifyNoMoreInteractions(userService);
    }
    @Test
    public void updateAllUsers_WithWrongInvalidEntity_ShouldReturnBadRequest() throws Exception {
        dtoUser.setPhoneNumber("+35");
        list.getUsers().add(dtoUser);
        mvc.perform(put(URL_ALL).contentType(MediaType.APPLICATION_JSON).content(asJsonString(list)))
                .andExpect(status().isBadRequest());
        verifyNoMoreInteractions(contactService);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void getUserById_ShouldReturnUser() throws Exception {
        when(userService.findOne(ID)).thenReturn(dtoUser);
        mvc.perform(get(URL_WITH_ID, dtoUser.getId())).andExpect(status().isOk());
        verify(userService, times(1)).findOne(ID);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void getUserById_NotFound() throws Exception {
        when(userService.findOne(ID)).thenReturn(null);
        mvc.perform(get(URL_WITH_ID, dtoUser.getId())).andExpect(status().isNotFound());
        verify(userService, times(1)).findOne(dtoUser.getId());
        verifyNoMoreInteractions(userService);
    }


}