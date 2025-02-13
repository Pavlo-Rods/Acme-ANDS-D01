/*
 * AuthenticatedWorkerUpdateService.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.worker;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Authenticated;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.realms.Worker;

@GuiService
public class AuthenticatedWorkerUpdateService extends AbstractGuiService<Authenticated, Worker> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedWorkerRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRealmOfType(Worker.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Worker object;
		int userAccountId;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		object = this.repository.findWorkerByUserAccountId(userAccountId);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Worker object) {
		assert object != null;

		super.bindObject(object, "qualifications", "skills", "creditCard");
	}

	@Override
	public void validate(final Worker object) {
		assert object != null;
	}

	@Override
	public void perform(final Worker object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Worker object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbindObject(object, "qualifications", "skills", "creditCard");

		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}
