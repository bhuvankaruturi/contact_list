package com.databaseDesign.projectOne.Entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer contactId;

    private String fName;

    private String mName;

    private String lName;

    @OneToMany(orphanRemoval=true, mappedBy="contact", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<AddressEntity> addresses = new HashSet<AddressEntity>();

    @OneToMany(orphanRemoval = true, mappedBy="contact", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<PhoneEntity> phones = new HashSet<PhoneEntity>();

    @OneToMany(orphanRemoval = true, mappedBy="contact", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<DateEntity> dates = new HashSet<DateEntity>();

    public Contact() {

    }

    public Contact(String fName, String mName, String lName) {
        this.setfName(fName);
        this.setmName(mName);
        this.setlName(lName);
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        if (fName.equals("")) this.fName = "NO_FNAME";
        else this.fName = fName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        if (lName.equals("")) this.lName = "NO_LNAME";
        else this.lName = lName;
    }

    public Set<AddressEntity> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<AddressEntity> addresses) {
        this.addresses = addresses;
    }

    public void addAddress(AddressEntity address) {
        this.addresses.add(address);
        address.setContact(this);
    }

	public Set<PhoneEntity> getPhones() {
		return phones;
	}

	public void setPhones(Set<PhoneEntity> phones) {
		this.phones = phones;
    }
    
    public void addPhone(PhoneEntity phone) {
        this.phones.add(phone);
        phone.setContact(this);
    }

    public Set<DateEntity> getDates() {
        return dates;
    }

    public void setDates(Set<DateEntity> dates) {
        this.dates = dates;
    }

    public void addDate(DateEntity date) {
        this.dates.add(date);
        date.setContact(this);
    }
}