/*
 * AdministratorCompanyListService.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.company;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Administrator;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.companies.Company;

@GuiService
public class AdministratorCompanyListService extends AbstractGuiService<Administrator, Company> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorCompanyRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Company> objects;

		objects = this.repository.findAllCompanies();

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Company object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbindObject(object, "name", "moreInfo");
		super.addPayload(dataset, object, "description");

		super.getResponse().addData(dataset);
	}

}
