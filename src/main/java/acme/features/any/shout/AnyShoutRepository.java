/*
 * AnyShoutRepository.java
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

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.shouts.Shout;

@Repository
public interface AnyShoutRepository extends AbstractRepository {

	@Query("select s from Shout s")
	Collection<Shout> findAllShouts();

	@Query("select s from Shout s")
	Page<Shout> fetch(final PageRequest pageRequest);

}
