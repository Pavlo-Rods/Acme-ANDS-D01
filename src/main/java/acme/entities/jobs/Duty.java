/*
 * Duty.java
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

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidScore;
import acme.client.components.validation.ValidUrl;
import acme.constraints.ValidHeader;
import acme.constraints.ValidText;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Duty extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidHeader
	@Automapped
	private String				title;

	@Mandatory
	@ValidText
	@Automapped
	private String				description;

	@Mandatory
	@ValidScore
	@Automapped
	private double				workLoad;

	@Optional
	@ValidUrl
	@Automapped
	private String				moreInfo;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Job					job;

}
