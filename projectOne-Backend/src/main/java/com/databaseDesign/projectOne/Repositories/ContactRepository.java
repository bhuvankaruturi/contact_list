package com.databaseDesign.projectOne.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import com.databaseDesign.projectOne.Entities.Contact;

// This will be AUTO IMPLEMENTED by Spring into a Bean called contactRespository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
    static final String baseSearchQuery = "SELECT * FROM contact c where c.f_name like :token OR c.m_name like :token OR c.l_name like :token";
    static final String addressSearchQuery = " OR c.contact_id in (SELECT a.contact_id from address a where a.address like :token)";
    static final String phoneSearchQuery = " OR c.contact_id in (SELECT p.contact_id from phone p where p.number = :token)";
    static final String searchQuery = baseSearchQuery + addressSearchQuery + phoneSearchQuery;
    @Query(value=searchQuery, nativeQuery=true)
    List<Contact> findByContactToken(@Param("token") String token);

    // @Query(value=addressSearchQuery, nativeQuery=true)
    // List<Contact> findByAddressToken(@Param("token") String token);

    // @Query(value=phoneSearchQuery, nativeQuery=true)
    // List<Contact> findByPhoneToken(@Param("token") String token);

}