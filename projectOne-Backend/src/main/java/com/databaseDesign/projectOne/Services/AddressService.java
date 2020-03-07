package com.databaseDesign.projectOne.Services;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.databaseDesign.projectOne.Entities.Address;
import com.databaseDesign.projectOne.Entities.Contact;
import com.databaseDesign.projectOne.Repositories.AddressRepository;
import com.databaseDesign.projectOne.Repositories.ContactRepository;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;

    @Autowired
    ContactRepository contactRepository;
    public List<Address> getAllAddresses(Integer contactId) {
        List<Address> addresses = addressRepository.findByContactId(contactId);
        if (addresses.size() > 0) {
            return addresses;
        } else {
            return new ArrayList<Address>();
        }
    }

    public Address addAddress(Integer contactId, Address address) {
        Optional<Contact> contact = contactRepository.findById(contactId);
        if (contact.isPresent()) {
            Contact entity = contact.get();
            entity.addAddress(address);
            contactRepository.save(entity);
            return address;
        } else {
            return null;
        }
    }
}