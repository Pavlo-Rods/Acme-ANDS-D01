/*
 * EmployerWorksForUpdateService.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.employer.worksFor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.companies.Company;
import acme.entities.companies.WorksFor;
import acme.realms.Employer;

@GuiService
public class EmployerWorksForUpdateService extends AbstractGuiService<Employer, WorksFor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private EmployerWorksForRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		WorksFor worksFor;

		id = super.getRequest().getData("id", int.class);
		worksFor = this.repository.findWorksForById(id);
		status = worksFor != null && super.getRequest().getPrincipal().hasRealm(worksFor.getProxy());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		WorksFor object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findWorksForById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final WorksFor object) {
		assert object != null;

		int contractorId;
		Company contractor;

		contractorId = super.getRequest().getData("contractor", int.class);
		contractor = this.repository.findContractorById(contractorId);

		super.bindObject(object, "roles");
		object.setContractor(contractor);
	}

	@Override
	public void validate(final WorksFor object) {
		assert object != null;
	}

	@Override
	public void perform(final WorksFor object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final WorksFor object) {
		assert object != null;

		Collection<Company> contractors;
		SelectChoices choices;
		Dataset dataset;

		contractors = this.repository.findAllContractors();
		choices = SelectChoices.from(contractors, "name", object.getContractor());

		dataset = super.unbindObject(object, "roles");
		dataset.put("contractor", choices.getSelected().getKey());
		dataset.put("contractors", choices);

		super.getResponse().addData(dataset);
	}

}
