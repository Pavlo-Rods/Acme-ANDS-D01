/*
 * AdministratorCompanyRepository.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.company;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.companies.Company;

@Repository
public interface AdministratorCompanyRepository extends AbstractRepository {

	@Query("select count(wf) from WorksFor wf where wf.contractor.id = :id")
	int countProxiesByContractorId(int id);

	@Query("select c from Company c where c.id = :id")
	Company findCompanyById(int id);

	@Query("select c from Company c")
	Collection<Company> findAllCompanies();

	@Query("select count(j) from Job j where j.contractor.id = :id")
	int countJobsByContractorId(int id);

}
