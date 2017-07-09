package com.smetnertest.service;

import com.smetnertest.dao.ContactDao;
import com.smetnertest.dto.DtoUser;
import com.smetnertest.model.Contact;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.smetnertest.TestHelper.getTestContact;
import static com.smetnertest.TestHelper.getTestDtoUser;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContactServiceTest {
    @Mock
    private ContactDao contactDao;

    @InjectMocks
    private ContactService contactService;
    private Contact contact;
    private DtoUser dtoUser;

    @Before
    public void setUp() {
        contact = getTestContact();
        dtoUser = getTestDtoUser();
    }

    @Test
    public void updateContact_ShouldReturnContact() throws Exception {
        when(contactDao.findOne(contact.getId())).thenReturn(contact);
        assertEquals(contact, contactService.updateContact(dtoUser));
        verify(contactDao, times(2)).findOne(contact.getId());
        verify(contactDao, times(1)).save(contact);
        verifyNoMoreInteractions(contactDao);
    }

    @Test
    public void updateContact_WithWrongID_ShouldReturnNull() throws Exception {
        when(contactDao.findOne(contact.getId())).thenReturn(null);
        assertEquals(null, contactService.updateContact(dtoUser));
        verify(contactDao, times(1)).findOne(contact.getId());
        verifyNoMoreInteractions(contactDao);
    }

}