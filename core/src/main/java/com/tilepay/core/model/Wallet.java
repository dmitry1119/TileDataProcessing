package com.tilepay.core.model;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Entity
public class Wallet {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

//    @OneToMany(cascade = ALL, fetch = EAGER, mappedBy = "wallet")
//    private Set<Address> addresses = new HashSet<>();

    @OneToOne(cascade = ALL, fetch = EAGER, mappedBy = "wallet")
    private Address address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Set<Address> getAddresses() {
//        return addresses;
//    }
//
//    public void setAddresses(Set<Address> addresses) {
//        this.addresses = addresses;
//    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
