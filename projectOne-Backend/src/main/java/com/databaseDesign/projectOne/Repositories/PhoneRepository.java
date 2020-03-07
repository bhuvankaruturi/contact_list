package com.databaseDesign.projectOne.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.databaseDesign.projectOne.Entities.Phone;

// This will be AUTO IMPLEMENTED by Spring into a Bean called contactRespository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Integer> {
    @Query(value = "SELECT * from Phone p where p.contact_id =:id ", nativeQuery = true)       // using @query
    List<Phone> findByContactId(@Param("id") Integer id);
}