/*
 * AnyShoutFetchService.java
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;

import acme.client.components.datatables.DatatableInput;
import acme.client.components.datatables.DatatableOutput;
import acme.client.components.models.Errors;
import acme.client.components.principals.Any;
import acme.client.helpers.DatatablesHelper;
import acme.client.services.AbstractRestService;
import acme.client.services.RestService;
import acme.entities.shouts.Shout;

@RestService
public class AnyShoutFetchService extends AbstractRestService<Any, DatatableOutput<Shout>> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyShoutRepository repository;

	// AbstractRestService interface -------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Pair<DatatableInput, Errors> pair;
		DatatableInput input;
		PageRequest pageRequest;
		Errors errors;
		Page<Shout> data;
		DatatableOutput<Shout> output;

		pair = super.instantiateObject(DatatableInput.class);
		input = pair.getFirst();
		errors = pair.getSecond();
		assert !errors.hasErrors();
		pageRequest = DatatablesHelper.toPageRequest(input);

		data = this.repository.fetch(pageRequest);
		output = new DatatableOutput<Shout>( //
			input.getDraw(), //
			data.getContent(), //
			data.getTotalElements(), // 
			data.getTotalElements());

		super.getBuffer().addData(output);
	}

	@Override
	public void unbind(final DatatableOutput<Shout> object) {
		super.getResponse().addData(object);
	}

}
