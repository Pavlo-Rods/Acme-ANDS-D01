/*
 * AuthenticatedEmployerCreateService.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.employer;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Authenticated;
import acme.client.components.principals.UserAccount;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.realms.Customer;
import acme.realms.Employer;

@GuiService
public class AuthenticatedEmployerCreateService extends AbstractGuiService<Authenticated, Employer> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedEmployerRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = !super.getRequest().getPrincipal().hasRealmOfType(Employer.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Employer object;
		int userAccountId;
		UserAccount userAccount;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		userAccount = this.repository.findOneUserAccountById(userAccountId);

		object = new Employer();
		object.setUserAccount(userAccount);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Employer object) {
		assert object != null;

		super.bindObject(object, "area", "sector", "creditCard");
	}

	@Override
	public void validate(final Employer object) {
		assert object != null;
	}

	@Override
	public void perform(final Employer object) {
		assert object != null;

		Customer customer;

		this.repository.save(object);

		customer = this.repository.findOneCustomerByUserAccountId(object.getUserAccount().getId());
		if (customer == null) {
			customer = new Customer();
			customer.setUserAccount(object.getUserAccount());
			this.repository.save(customer);
		}
	}

	@Override
	public void unbind(final Employer object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbindObject(object, "area", "sector", "creditCard");

		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}
