package com.tilepay.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tilepay.core.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("select a from Address a where a.address = ?")
    Address findOneByAddress(String address);
}
