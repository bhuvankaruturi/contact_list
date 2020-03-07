package com.databaseDesign.projectOne.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Date {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer dataId;

    private String dateType;

    @JsonFormat(pattern="MM/dd/yyyy")
    private java.util.Date date;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name="contact_id")
    private Contact contact;

    public Date() {

    }

    public Date(String dateType, java.util.Date date) {
        this.setDateType(dateType);
        this.setDate(date);
    }

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}