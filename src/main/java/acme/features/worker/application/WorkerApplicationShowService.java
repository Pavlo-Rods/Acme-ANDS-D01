/*
 * WorkerApplicationShowService.java
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

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.jobs.Application;
import acme.realms.Worker;

@GuiService
public class WorkerApplicationShowService extends AbstractGuiService<Worker, Application> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private WorkerApplicationRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int applicationId;
		Application application;

		applicationId = super.getRequest().getData("id", int.class);
		application = this.repository.findApplicationById(applicationId);
		status = application != null && super.getRequest().getPrincipal().hasRealm(application.getWorker());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Application object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findApplicationById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Application object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbindObject(object, "ticker", "moment", "status", "statement", "skills", "qualifications");
		dataset.put("jobId", object.getId());

		super.getResponse().addData(dataset);
	}

}
