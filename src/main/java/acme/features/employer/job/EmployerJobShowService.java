/*
 * EmployerJobShowService.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.employer.job;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.companies.Company;
import acme.entities.jobs.Job;
import acme.realms.Employer;

@GuiService
public class EmployerJobShowService extends AbstractGuiService<Employer, Job> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private EmployerJobRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Job job;
		Employer employer;
		Date currentMoment;

		masterId = super.getRequest().getData("id", int.class);
		job = this.repository.findJobById(masterId);
		employer = job == null ? null : job.getEmployer();
		currentMoment = MomentHelper.getCurrentMoment();
		status = super.getRequest().getPrincipal().hasRealm(employer) || job != null && !job.isDraftMode() && MomentHelper.isAfter(job.getDeadline(), currentMoment);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Job object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findJobById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Job object) {
		assert object != null;

		int employerId;
		Collection<Company> contractors;
		SelectChoices choices;
		Dataset dataset;

		if (!object.isDraftMode())
			contractors = this.repository.findAllContractors();
		else {
			employerId = super.getRequest().getPrincipal().getActiveRealm().getId();
			contractors = this.repository.findContractorsByEmployerId(employerId);
		}
		choices = SelectChoices.from(contractors, "name", object.getContractor());

		dataset = super.unbindObject(object, "ticker", "title", "deadline", "salary", "score", "moreInfo", "description", "draftMode");
		dataset.put("contractor", choices.getSelected().getKey());
		dataset.put("contractors", choices);

		super.getResponse().addData(dataset);
	}

}
