/*
 * Job.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.entities.jobs;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoney;
import acme.client.components.validation.ValidScore;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MomentHelper;
import acme.client.helpers.SpringHelper;
import acme.constraints.ValidHeader;
import acme.constraints.ValidJob;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.entities.companies.Company;
import acme.realms.Employer;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidJob
@Table(indexes = {
	@Index(columnList = "draftMode, deadline"), //
	@Index(columnList = "employer_id, id")
})
public class Job extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidTicker
	@Column(unique = true)
	private String				ticker;

	@Mandatory
	@ValidHeader
	@Automapped
	private String				title;

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	private Date				deadline;

	@Mandatory
	@ValidMoney
	@Automapped
	private Money				salary;

	@Mandatory
	@ValidScore
	@Automapped
	private double				score;

	@Mandatory
	@ValidText
	@Automapped
	private String				description;

	@Optional
	@ValidUrl
	@Automapped
	private String				moreInfo;

	// HINT: @Mandatory by default.
	// HINT: @Valid by default.
	@Automapped
	private boolean				draftMode;

	// Derived attributes -----------------------------------------------------


	@Transient
	public boolean isAvailable() {
		boolean result;

		result = !this.draftMode && MomentHelper.isFuture(this.deadline);

		return result;
	}

	@Transient
	public double getWorkLoad() {
		double result;
		JobRepository repository;
		Double wrapper;

		repository = SpringHelper.getBean(JobRepository.class);
		wrapper = repository.computeJobWorkload(this.getId());
		result = wrapper == null ? 0 : wrapper.doubleValue();

		return result;
	}

	// Relationships ----------------------------------------------------------


	@Mandatory
	@Valid
	@ManyToOne
	private Employer	employer;

	@Mandatory
	@Valid
	@ManyToOne
	private Company		contractor;

}
