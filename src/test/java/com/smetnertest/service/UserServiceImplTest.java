package com.smetnertest.service;

import com.smetnertest.dao.UserDao;
import com.smetnertest.dto.DtoUser;
import com.smetnertest.mappers.UserMapper;
import com.smetnertest.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static com.smetnertest.TestHelper.getTestDtoUser;
import static com.smetnertest.TestHelper.getTestUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final long ID = 1L;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;
    private User user;
    private DtoUser dtoUser;

    @Before
    public void setUp() {
        user = getTestUser();
        dtoUser = getTestDtoUser();
    }

    @Test
    public void updateUser_ShouldReturnUser() {
        when(userDao.findOne(user.getId())).thenReturn(user);
        when(userMapper.fromDtoUserToUser(dtoUser)).thenReturn(user);
        when(userMapper.fromUserToDtoUser(user)).thenReturn(dtoUser);
        assertEquals(dtoUser, userService.updateUser(dtoUser, user.getId()));
        verify(userDao, times(2)).findOne(user.getId());
        verify(userMapper, times(2)).fromDtoUserToUser(dtoUser);
        verify(userMapper, times(1)).fromUserToDtoUser(user);
        verify(userDao, times(1)).save(user);
        verifyNoMoreInteractions(userDao);
        verifyNoMoreInteractions(userMapper);
    }

    @Test
    public void updateUser_WithWrongId_ShouldReturnNull() {
        when(userDao.findOne(100L)).thenReturn(null);
        assertEquals(null, userService.updateUser(dtoUser, 100L));
        verify(userDao, times(1)).findOne(100L);
        verifyNoMoreInteractions(userDao);
        verifyNoMoreInteractions(userMapper);
    }

    @Test
    public void getAllUsers_ShouldInvokeFindAll() {
        List<DtoUser> dtoUsers = new ArrayList<>();
        dtoUsers.add(dtoUser);
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userDao.findAll()).thenReturn(users);
        when(userMapper.fromUserToDtoUser(user)).thenReturn(dtoUser);
        assertTrue(dtoUsers.containsAll(userService.getAllUsers()));
        verify(userMapper, times(users.size())).fromUserToDtoUser(user);
        verify(userDao, times(1)).findAll();
        verifyNoMoreInteractions(userMapper);
        verifyNoMoreInteractions(userDao);


    }

    @Test
    public void findOne_ShouldReturnUser() {
        when(userDao.findOne(ID)).thenReturn(user);
        when(userMapper.fromUserToDtoUser(user)).thenReturn(dtoUser);
        assertEquals(dtoUser,userService.findOne(ID));
        verify(userDao,times(1)).findOne(ID);
        verify(userMapper, times(1)).fromUserToDtoUser(user);
    }

}