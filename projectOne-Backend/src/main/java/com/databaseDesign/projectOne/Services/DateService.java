package com.databaseDesign.projectOne.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.databaseDesign.projectOne.Entities.Contact;
import com.databaseDesign.projectOne.Entities.Date;
import com.databaseDesign.projectOne.Repositories.ContactRepository;
import com.databaseDesign.projectOne.Repositories.DateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DateService {
    @Autowired
    DateRepository dateRepository;

    @Autowired
    ContactRepository contactRepository;

    public List<Date> getAllDates(Integer contactId) {
        List<Date> dates = dateRepository.findByContactId(contactId);
        if (dates.size() > 0) {
            return dates;
        } else {
            return new ArrayList<Date>();
        }
    }

    public Date addDate(Integer contactId, Date date) {
        Optional<Contact> contact = contactRepository.findById(contactId);
        if (contact.isPresent()) {
            Contact entity = contact.get();
            entity.addDate(date);
            contactRepository.save(entity);
            return date;
        } else {
            return null;
        }
    }

    public Date modifyDate(Integer dateId, Date modifiedDate) {
        Optional<Date> date = dateRepository.findById(dateId);
        if (date.isPresent()) {
            Date entity = date.get();
            entity.setDateType(modifiedDate.getDateType());
            entity.setDate(modifiedDate.getDate());
            return dateRepository.save(entity);
        } else return null;
    }
}