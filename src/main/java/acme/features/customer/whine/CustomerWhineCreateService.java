/*
 * CustomerWhineCreateService.java
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

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.whines.Whine;
import acme.realms.Customer;

@GuiService
public class CustomerWhineCreateService extends AbstractGuiService<Customer, Whine> {

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
		Date moment;
		Customer customer;
		Whine object;

		moment = MomentHelper.getCurrentMoment();
		customer = (Customer) super.getRequest().getPrincipal().getActiveRealm();

		object = new Whine();
		object.setMoment(moment);
		object.setHeader("");
		object.setDescription("");
		object.setRedress("N/A");
		object.setCustomer(customer);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Whine object) {
		assert object != null;

		super.bindObject(object, "header", "description");
	}

	@Override
	public void validate(final Whine object) {
		assert object != null;

		boolean confirmation;

		confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "acme.validation.confirmation.message");
	}

	@Override
	public void perform(final Whine object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Whine object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbindObject(object, "header", "description");

		super.getResponse().addData(dataset);

	}

}
