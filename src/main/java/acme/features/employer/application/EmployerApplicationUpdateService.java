/*
 * EmployerApplicationUpdateService.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.employer.application;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.jobs.Application;
import acme.entities.jobs.ApplicationStatus;
import acme.realms.Employer;

@GuiService
public class EmployerApplicationUpdateService extends AbstractGuiService<Employer, Application> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private EmployerApplicationRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int applicationId;
		Application application;

		applicationId = super.getRequest().getData("id", int.class);
		application = this.repository.findApplicationById(applicationId);
		status = application != null && //
			application.getStatus().equals(ApplicationStatus.PENDING) && //
			super.getRequest().getPrincipal().hasRealm(application.getJob().getEmployer());

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
	public void bind(final Application object) {
		assert object != null;

		super.bindObject(object, "status");
	}

	@Override
	public void validate(final Application object) {
		assert object != null;
	}

	@Override
	public void perform(final Application object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Application object) {
		assert object != null;

		SelectChoices choices;
		Dataset dataset;

		choices = SelectChoices.from(ApplicationStatus.class, object.getStatus());

		dataset = super.unbindObject(object, "ticker", "moment", "status", "statement", "skills", "qualifications");
		dataset.put("masterId", object.getJob().getId());
		dataset.put("statuses", choices);

		super.getResponse().addData(dataset);
	}

}
