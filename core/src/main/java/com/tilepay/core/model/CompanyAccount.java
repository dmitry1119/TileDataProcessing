package com.tilepay.core.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import static javax.persistence.CascadeType.ALL;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class CompanyAccount extends Account {

	@OneToOne(cascade = ALL)
	@JoinColumn(name = "company_contact_details_id")
	private CompanyContactDetails companyContactDetails = new CompanyContactDetails();

	public CompanyContactDetails getCompanyContactDetails() {
		return companyContactDetails;
	}

	public void setCompanyContactDetails(CompanyContactDetails companyContactDetails) {
		this.companyContactDetails = companyContactDetails;
	}
}
