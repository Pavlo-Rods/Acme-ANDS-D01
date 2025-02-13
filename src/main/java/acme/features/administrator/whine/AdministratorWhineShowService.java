/*
 * AdministratorWhineShowService.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.whine;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Administrator;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.whines.Whine;

@GuiService
public class AdministratorWhineShowService extends AbstractGuiService<Administrator, Whine> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorWhineRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int whineId;
		Whine whine;

		whineId = super.getRequest().getData("id", int.class);
		whine = this.repository.findWhineById(whineId);

		super.getBuffer().addData(whine);
	}

	@Override
	public void unbind(final Whine object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbindObject(object, "header", "description", "redress");

		super.getResponse().addData(dataset);
	}

}
