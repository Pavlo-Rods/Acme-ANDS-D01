/*
 * EmployerJobController.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.employer.job;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.controllers.AbstractGuiController;
import acme.client.controllers.GuiController;
import acme.entities.jobs.Job;
import acme.realms.Employer;

@GuiController
public class EmployerJobController extends AbstractGuiController<Employer, Job> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private EmployerJobListService		listService;

	@Autowired
	private EmployerJobShowService		showService;

	@Autowired
	private EmployerJobCreateService	createService;

	@Autowired
	private EmployerJobUpdateService	updateService;

	@Autowired
	private EmployerJobDeleteService	deleteService;

	@Autowired
	private EmployerJobPublishService	publishService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);

		super.addCustomCommand("publish", "update", this.publishService);
	}

}
