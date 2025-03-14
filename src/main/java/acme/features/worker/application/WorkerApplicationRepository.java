/*
 * WorkerApplicationRepository.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.worker.application;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.jobs.Application;
import acme.entities.jobs.Job;
import acme.realms.Worker;

@Repository
public interface WorkerApplicationRepository extends AbstractRepository {

	@Query("select a from Application a where a.id = :id")
	Application findApplicationById(int id);

	@Query("select j from Job j where j.id = :id")
	Job findJobById(int id);

	@Query("select w from Worker w where w.id = :id")
	Worker findWorkerById(int id);

	@Query("select a from Application a where a.worker.id = :id")
	Collection<Application> findApplicationsByWorkerId(int id);

}
