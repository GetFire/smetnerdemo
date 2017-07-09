package com.smetnertest.dao;

import com.smetnertest.model.Contact;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * A Spring Data supported interface to handle DAO operations on {@link com.smetnertest.model.Contact}.
 */

@Repository
public interface ContactDao extends PagingAndSortingRepository<Contact, Long> {
}
