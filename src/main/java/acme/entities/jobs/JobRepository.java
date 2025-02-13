/*
 * JobRepository.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.entities.jobs;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface JobRepository extends AbstractRepository {

	@Query("select sum(d.workLoad) from Duty d where d.job.id = :jobId")
	Double computeJobWorkload(int jobId);

	@Query("select j from Job j where j.ticker = :ticker")
	Job findJobByTicker(String ticker);

}
