package com.smetnertest.service;

import com.smetnertest.dao.ContactDao;
import com.smetnertest.dto.DtoUser;
import com.smetnertest.model.Contact;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Business logic layer for {@link com.smetnertest.model.Contact}.
 */

@Service
@Transactional
public class ContactService {
    private ContactDao contactDao;

    public ContactService(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    public Contact updateContact(DtoUser user) {
        Contact currentContact = contactDao.findOne(user.getContactId());
        if (currentContact != null) {
            Contact entity = contactDao.findOne(user.getContactId());
            entity.setComment(user.getComment());
            entity.setNumber(user.getPhoneNumber());
            entity.setType(user.getPhoneType());
            contactDao.save(entity);
            return entity;
        }
        return null;
    }
}
