package com.databaseDesign.projectOne.Services;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.databaseDesign.projectOne.Entities.Phone;
import com.databaseDesign.projectOne.Entities.Contact;
import com.databaseDesign.projectOne.Repositories.PhoneRepository;
import com.databaseDesign.projectOne.Repositories.ContactRepository;

@Service
public class PhoneService {
    @Autowired
    PhoneRepository phoneRepository;

    @Autowired
    ContactRepository contactRepository;

    public List<Phone> getAllPhones(Integer contactId) {
        List<Phone> phones = phoneRepository.findByContactId(contactId);
        if (phones.size() > 0) {
            return phones;
        } else {
            return new ArrayList<Phone>();
        }
    }

    public Phone addPhone(Integer contactId, Phone phone) {
        Optional<Contact> contact = contactRepository.findById(contactId);
        if (contact.isPresent()) {
            Contact entity = contact.get();
            entity.addPhone(phone);
            contactRepository.save(entity);
            return phone;
        } else {
            return null;
        }
    }
}