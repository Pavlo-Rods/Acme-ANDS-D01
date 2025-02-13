/*
 * EmployerDutyCreateService.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.employer.duty;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.jobs.Duty;
import acme.entities.jobs.Job;
import acme.realms.Employer;

@GuiService
public class EmployerDutyCreateService extends AbstractGuiService<Employer, Duty> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private EmployerDutyRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Job job;

		masterId = super.getRequest().getData("masterId", int.class);
		job = this.repository.findJobById(masterId);
		status = job != null && job.isDraftMode() && super.getRequest().getPrincipal().hasRealm(job.getEmployer());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Duty object;
		int masterId;
		Job job;

		masterId = super.getRequest().getData("masterId", int.class);
		job = this.repository.findJobById(masterId);

		object = new Duty();
		object.setTitle("");
		object.setDescription("");
		object.setWorkLoad(0.00);
		object.setJob(job);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Duty object) {
		assert object != null;

		super.bindObject(object, "title", "description", "workLoad", "moreInfo");
	}

	@Override
	public void validate(final Duty object) {
		assert object != null;
	}

	@Override
	public void perform(final Duty object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Duty object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbindObject(object, "title", "description", "workLoad", "moreInfo");
		dataset.put("masterId", super.getRequest().getData("masterId", int.class));
		dataset.put("draftMode", object.getJob().isDraftMode());

		super.getResponse().addData(dataset);
	}

}
