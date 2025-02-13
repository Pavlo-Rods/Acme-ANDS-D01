/*
 * CustomerWhineShowService.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.customer.whine;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.whines.Whine;
import acme.realms.Customer;

@GuiService
public class CustomerWhineShowService extends AbstractGuiService<Customer, Whine> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private CustomerWhineRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int whineId;
		Whine whine;

		whineId = super.getRequest().getData("id", int.class);
		whine = this.repository.findWhineById(whineId);
		status = super.getRequest().getPrincipal().hasRealm(whine.getCustomer());

		super.getResponse().setAuthorised(status);
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
