/*
 * JobValidator.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.constraints;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.jobs.Job;
import acme.entities.jobs.JobRepository;

@Validator
public class JobValidator extends AbstractValidator<ValidJob, Job> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private JobRepository repository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidJob annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Job job, final ConstraintValidatorContext context) {
		// HINT: job can be null
		assert context != null;

		boolean result;

		if (job == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");
		else {
			{
				boolean uniqueJob;
				Job existingJob;

				existingJob = this.repository.findJobByTicker(job.getTicker());
				uniqueJob = existingJob == null || existingJob.equals(job);

				super.state(context, uniqueJob, "ticker", "acme.validation.job.duplicated-ticker.message");
			}
			{
				boolean correctWorkload;

				correctWorkload = job.isDraftMode() || job.getWorkLoad() == 100.00;

				super.state(context, correctWorkload, "*", "acme.validation.job.workload.message");
			}
			{
				Date minimumDeadline;
				boolean correctDeadline;

				if (job.getDeadline() != null) {
					minimumDeadline = MomentHelper.deltaFromCurrentMoment(5, ChronoUnit.DAYS);
					correctDeadline = MomentHelper.isAfterOrEqual(job.getDeadline(), minimumDeadline);

					super.state(context, correctDeadline, "deadline", "acme.validation.job.deadline.message");
				}
			}
		}

		result = !super.hasErrors(context);

		return result;
	}

}
