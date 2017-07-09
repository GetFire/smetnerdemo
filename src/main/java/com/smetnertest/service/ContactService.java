package com.smetnertest.service;

import com.smetnertest.dto.DtoUser;
import com.smetnertest.model.Contact;

public interface ContactService {

     Contact updateContact(DtoUser user);
}
