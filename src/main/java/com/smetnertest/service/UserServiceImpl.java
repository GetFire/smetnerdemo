package com.smetnertest.service;

import com.smetnertest.dao.UserDao;
import com.smetnertest.dto.DtoUser;
import com.smetnertest.mappers.UserMapper;
import com.smetnertest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Business logic layer for {@link com.smetnertest.model.User}.
 */

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDao userDao;

    @Override
    public DtoUser getUser(String lastName) {
        return userMapper.fromUserToDtoUser(userDao.findByLastName(lastName));
    }

    @Override
    public DtoUser updateUser(DtoUser dtoUser, long id) {
        User currentUser = userDao.findOne(id);
        if (currentUser != null && id == currentUser.getId()) {
            User entity = userDao.findOne(userMapper.fromDtoUserToUser(dtoUser).getId());
            entity.setContact(userMapper.fromDtoUserToUser(dtoUser).getContact());
            userDao.save(entity);
            return userMapper.fromUserToDtoUser(entity);
        }
        return null;
    }

    @Override
    public List<DtoUser> getAllUsers() {
        List<DtoUser> users = new ArrayList<>();
        userDao.findAll().forEach(user -> users.add(userMapper.fromUserToDtoUser(user)));
        return users;
    }

    @Override
    public long saveUser(DtoUser dto) {
        User user = userMapper.fromDtoUserToUser(dto);
        return userDao.save(user).getId();
    }

    public DtoUser findOne(long id) {
        return userMapper.fromUserToDtoUser(userDao.findOne(id));
    }

}
