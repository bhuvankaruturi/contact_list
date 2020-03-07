package com.databaseDesign.projectOne.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.databaseDesign.projectOne.Entities.Address;

// This will be AUTO IMPLEMENTED by Spring into a Bean called contactRespository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    @Query(value = "SELECT * from Address a where a.contact_id =:id ", nativeQuery = true)       // using @query
    List<Address> findByContactId(@Param("id") Integer id);
}