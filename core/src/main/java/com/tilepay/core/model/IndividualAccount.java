package com.tilepay.core.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import static javax.persistence.CascadeType.ALL;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class IndividualAccount extends Account {

	@OneToOne(cascade = ALL)
	@JoinColumn(name = "contact_details_id")
	private ContactDetails contactDetails = new ContactDetails();

	public ContactDetails getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(ContactDetails contactDetails) {
		this.contactDetails = contactDetails;
	}

}