package com.databaseDesign.projectOne.Services;

import java.util.Optional;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.databaseDesign.projectOne.Entities.Contact;
import com.databaseDesign.projectOne.Repositories.ContactRepository;

@Service
public class ContactService {
    @Autowired
    ContactRepository contactRepository;

    public List<Contact> getAllContacts()  {
        List<Contact> contacts = contactRepository.findAll();
        if (contacts.size() > 0) {
            return contacts;
        } else {
            return new ArrayList<Contact>();
        }
    }

    public Set<Contact> getContactBySearch(String searchString) {
        String[] tokens = searchString.split(" ");
        Set<Contact> result = new HashSet<Contact>();
        for (int i = 0; i < tokens.length; i++) {
            String token = "%" + tokens[i] + "%";
            result.addAll(contactRepository.findByContactToken(token));
        }
        return result;
    }

    public Contact getContactById(Integer id) {
        Optional<Contact> contact = contactRepository.findById(id);
        if (contact.isPresent()) {
            return contact.get();
        } else {
            return null;
        }
    }

    public Contact createContact(Contact entity) {
        return contactRepository.save(entity);
    }

    public Contact modifyContact(Contact modifiedContact) {
        Optional<Contact> contact = contactRepository.findById(modifiedContact.getContactId());
        if (contact.isPresent()) {
            return contactRepository.save(modifiedContact);
        } return null;
    }

    public String deleteContact(Integer id) {
        Optional<Contact> contact = contactRepository.findById(id);
        if(contact.isPresent()) {
            contactRepository.delete(contact.get());
            return "deleted";
        }
        else return "deleted";
    }
}
