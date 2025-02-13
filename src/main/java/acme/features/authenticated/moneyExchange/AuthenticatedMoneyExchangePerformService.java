/*
 * AuthenticatedMoneyExchangePerformService.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.moneyExchange;

import java.util.Date;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import acme.client.components.datatypes.Money;
import acme.client.components.models.Dataset;
import acme.client.components.principals.Authenticated;
import acme.client.helpers.MomentHelper;
import acme.client.helpers.StringHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.components.ExchangeRate;
import acme.forms.MoneyExchange;

@GuiService
public class AuthenticatedMoneyExchangePerformService extends AbstractGuiService<Authenticated, MoneyExchange> {

	// AbstractGuiService interface -------------------------------------------

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		MoneyExchange object;

		object = new MoneyExchange();

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final MoneyExchange object) {
		assert object != null;

		super.bindObject(object, "source", "targetCurrency");
	}

	@Override
	public void validate(final MoneyExchange object) {
		assert object != null;
	}

	@Override
	public void perform(final MoneyExchange object) {
		assert object != null;

		Money source;
		String targetCurrency;
		MoneyExchange exchange;

		source = super.getRequest().getData("source", Money.class);
		targetCurrency = super.getRequest().getData("targetCurrency", String.class);
		exchange = this.computeMoneyExchange(source, targetCurrency);
		super.state(exchange.getOops() == null, "*", "authenticated.money-exchange.form.label.api-error");
		if (exchange.getOops() != null) {
			object.setTarget(null);
			object.setMoment(null);
		} else {
			object.setMoment(exchange.getMoment());
			object.setTarget(exchange.getTarget());
		}
	}

	@Override
	public void unbind(final MoneyExchange object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbindObject(object, "source", "targetCurrency", "moment", "target");

		super.getResponse().addData(dataset);
	}

	// Ancillary methods ------------------------------------------------------

	public MoneyExchange computeMoneyExchange(final Money source, final String targetCurrency) {
		assert source != null;
		assert !StringHelper.isBlank(targetCurrency);

		MoneyExchange result;
		RestTemplate api;
		HttpHeaders headers;
		HttpEntity<String> parameters;
		ResponseEntity<ExchangeRate> response;
		ExchangeRate record;
		String sourceCurrency;
		Double sourceAmount, targetAmount, rate;
		Money target;
		Date moment;

		try {
			sourceCurrency = source.getCurrency();
			sourceAmount = source.getAmount();

			headers = new HttpHeaders();
			headers.add("apikey", "Q8Bzt7rnuqtZiRHLQ2joPothdJaUSuX0");
			parameters = new HttpEntity<String>("parameters", headers);
			api = new RestTemplate();
			response = api.exchange( //				
				"https://api.apilayer.com/exchangerates_data/latest?base={0}&symbols={1}", //
				HttpMethod.GET, //
				parameters, //
				ExchangeRate.class, //
				sourceCurrency, //
				targetCurrency //
			);
			assert response != null && response.getBody() != null;
			record = response.getBody();
			assert record != null && record.getRates().containsKey(targetCurrency);
			rate = record.getRates().get(targetCurrency);

			targetAmount = rate * sourceAmount;

			target = new Money();
			target.setAmount(targetAmount);
			target.setCurrency(targetCurrency);

			moment = record.getDate();

			result = new MoneyExchange();
			result.setSource(source);
			result.setTargetCurrency(targetCurrency);
			result.setMoment(moment);
			result.setTarget(target);

			MomentHelper.sleep(1000); // HINT: need to pause the requests to the API to prevent banning!
		} catch (final Throwable oops) {
			result = new MoneyExchange();
			result.setOops(oops);
		}

		return result;
	}

}
