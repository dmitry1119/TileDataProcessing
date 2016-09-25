package com.tilepay.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tilepay.core.model.Address;
import com.tilepay.core.model.AssetTransaction;

public interface AssetTransactionRepository extends JpaRepository<AssetTransaction, Long> {

    @Query("select a from AssetTransaction a where a.address = ? and a.currency = ?")
    List<AssetTransaction> findAllWhereAddressAndCurrency(Address address, String currency);

    @Modifying
    @Query("delete from AssetTransaction where hash = ?")
    void deleteOneByHash(String hash);

}
