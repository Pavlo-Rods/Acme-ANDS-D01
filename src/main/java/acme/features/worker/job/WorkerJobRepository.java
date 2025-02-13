/*
 * WorkerJobRepository.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.worker.job;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.companies.Company;
import acme.entities.jobs.Job;

@Repository
public interface WorkerJobRepository extends AbstractRepository {

	@Query("select j from Job j where j.id = :id")
	Job findJobById(int id);

	@Query("select j from Job j where j.draftMode = false and j.deadline > :currentMoment")
	Collection<Job> findJobsByAvailability(Date currentMoment);

	@Query("select c from Company c")
	Collection<Company> findAllContractors();

}
