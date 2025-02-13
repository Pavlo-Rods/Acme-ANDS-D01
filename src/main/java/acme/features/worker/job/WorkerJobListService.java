/*
 * WorkerJobListService.java
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

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.jobs.Job;
import acme.realms.Worker;

@GuiService
public class WorkerJobListService extends AbstractGuiService<Worker, Job> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private WorkerJobRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Job> object;
		Date currentMoment;

		currentMoment = MomentHelper.getCurrentMoment();
		object = this.repository.findJobsByAvailability(currentMoment);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Job object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbindObject(object, "ticker", "title", "deadline");
		super.addPayload(dataset, object, //
			"description", "contractor.name", "contractor.description", //
			"employer.identity.fullName", "employer.area", "employer.sector");
		super.getResponse().addData(dataset);
	}

}
