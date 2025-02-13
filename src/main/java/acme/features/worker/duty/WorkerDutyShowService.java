/*
 * WorkerDutyShowService.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.worker.duty;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.jobs.Duty;
import acme.entities.jobs.Job;
import acme.realms.Worker;

@GuiService
public class WorkerDutyShowService extends AbstractGuiService<Worker, Duty> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private WorkerDutyRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int dutyId;
		Job job;

		dutyId = super.getRequest().getData("id", int.class);
		job = this.repository.findJobByDutyId(dutyId);
		status = job != null && !job.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Duty object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findDutyById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Duty object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbindObject(object, "title", "description", "workLoad", "moreInfo");

		super.getResponse().addData(dataset);
	}

}
