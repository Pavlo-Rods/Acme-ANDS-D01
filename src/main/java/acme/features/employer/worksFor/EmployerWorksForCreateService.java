/*
 * EmployerWorksForCreateService.java
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
public class EmployerWorksForCreateService extends AbstractGuiService<Employer, WorksFor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private EmployerWorksForRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Employer proxy;
		WorksFor object;

		proxy = (Employer) super.getRequest().getPrincipal().getActiveRealm();

		object = new WorksFor();
		object.setRoles("");
		object.setContractor(null);
		object.setProxy(proxy);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final WorksFor object) {
		assert object != null;

		int contractorId;
		Employer proxy;
		Company contractor;

		super.bindObject(object, "roles");

		proxy = (Employer) super.getRequest().getPrincipal().getActiveRealm();
		object.setProxy(proxy);

		contractorId = super.getRequest().getData("contractor", int.class);
		contractor = this.repository.findContractorById(contractorId);
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

		int proxyId;
		Collection<Company> companies;
		SelectChoices choices;
		Dataset dataset;

		proxyId = object.getProxy().getId();
		companies = this.repository.findAvailableContractorsByProxyId(proxyId);
		choices = SelectChoices.from(companies, "name", object.getContractor());

		dataset = super.unbindObject(object, "roles");
		dataset.put("contractor", choices.getSelected().getKey());
		dataset.put("contractors", choices);

		super.getResponse().addData(dataset);
	}

}
