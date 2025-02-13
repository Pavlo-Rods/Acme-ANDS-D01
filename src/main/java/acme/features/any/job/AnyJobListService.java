/*
 * AnyJobListService.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.any.job;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Any;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.jobs.Job;

@GuiService
public class AnyJobListService extends AbstractGuiService<Any, Job> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyJobRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Job> objects;
		Date currentMoment;

		currentMoment = MomentHelper.getCurrentMoment();
		objects = this.repository.findJobsByAvailability(currentMoment);

		super.getBuffer().addData(objects);
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
