/*
 * AnyShoutCreateService.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.any.shout;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Any;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.shouts.Shout;

@GuiService
public class AnyShoutCreateService extends AbstractGuiService<Any, Shout> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyShoutRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Shout object;

		object = new Shout();

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Shout object) {
		assert object != null;

		Date moment;

		moment = MomentHelper.getCurrentMoment();
		super.bindObject(object, "author", "text", "moreInfo");
		object.setMoment(moment);
	}

	@Override
	public void validate(final Shout object) {
		assert object != null;
	}

	@Override
	public void perform(final Shout object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Shout object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbindObject(object, "author", "text", "moreInfo");

		super.getResponse().addData(dataset);

	}

}
