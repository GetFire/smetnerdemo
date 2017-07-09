package com.smetnertest.dao;

import com.smetnertest.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * A Spring Data supported interface to handle DAO operations on {@link com.smetnertest.model.User}.
 */

@Repository
public interface UserDao extends PagingAndSortingRepository<User, Long> {

}
