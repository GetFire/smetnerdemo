package com.smetnertest.controller;

import com.smetnertest.dto.DtoUser;
import com.smetnertest.model.User;
import com.smetnertest.service.ContactService;
import com.smetnertest.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.smetnertest.TestHelper.getTestDtoUser;
import static com.smetnertest.TestHelper.getTestUser;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private ContactService contactService;

    @InjectMocks
    private UserController controller;
    private MockMvc mvc;
    private User user;
    private DtoUser dtoUser;


    @Before
    public void setUp() {
        mvc = standaloneSetup(controller).build();
        user = getTestUser();
        dtoUser = getTestDtoUser();
    }

    @Test
    public void getAllUser() throws Exception {
        mvc.perform(get("/api/users"))
                .andExpect(status().isOk());
        verify(userService, times(1)).getAllUsers();
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void getAllUsersShouldReturnEmptyListAndNoContentStatus() throws Exception {
        when(userService.getAllUsers()).thenReturn(null);
        mvc.perform(get("/api/users"))
                .andExpect(status().isNoContent());
        verify(userService, times(1)).getAllUsers();
        verifyNoMoreInteractions(userService);

    }

    @Test
    public void updateUserContact_UserSuccessfullyUpdatedAndSuccessfullyPassValidation_ShouldReturnUpdatedUser() throws Exception {
        dtoUser.setPhoneNumber("+380930865783");
        dtoUser.setPhoneType("Work");
    }

    @Test
    public void updateAllUsers() throws Exception {
    }

    @Test
    public void getUserById() throws Exception {
    }

}