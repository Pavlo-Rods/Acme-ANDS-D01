/*
 * EmployerWorksForRepository.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.employer.worksFor;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.companies.Company;
import acme.entities.companies.WorksFor;
import acme.realms.Employer;

@Repository
public interface EmployerWorksForRepository extends AbstractRepository {

	@Query("select e from Employer e where e.id = :id")
	Employer findProxyById(int id);

	@Query("select c from Company c where c.id = :id")
	Company findContractorById(int id);

	@Query("select c from Company c")
	Collection<Company> findAllContractors();

	@Query("select wf from WorksFor wf where wf.id = :id")
	WorksFor findWorksForById(int id);

	@Query("select wf from WorksFor wf where wf.proxy.id = :proxyId")
	Collection<WorksFor> findWorksForByProxyId(int proxyId);

	@Query("select c from Company c where c.id not in (select wf.contractor.id from WorksFor wf where wf.proxy.id = :proxyId)")
	Collection<Company> findAvailableContractorsByProxyId(int proxyId);

}
