/*
 * CustomerWhineListService.java
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

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.whines.Whine;
import acme.realms.Customer;

@GuiService
public class CustomerWhineListService extends AbstractGuiService<Customer, Whine> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private CustomerWhineRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Whine> objects;
		int customerId;

		customerId = super.getRequest().getPrincipal().getActiveRealm().getId();
		objects = this.repository.findWhineByCustomerId(customerId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Whine object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbindObject(object, "moment", "header");
		super.getResponse().addData(dataset);
	}

}
