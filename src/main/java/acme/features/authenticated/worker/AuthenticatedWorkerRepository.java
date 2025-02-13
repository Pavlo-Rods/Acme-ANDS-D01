/*
 * AuthenticatedWorkerRepository.java
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

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.realms.Customer;
import acme.realms.Worker;

@Repository
public interface AuthenticatedWorkerRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findUserAccountById(int id);

	@Query("select w from Worker w where w.userAccount.id = :id")
	Worker findWorkerByUserAccountId(int id);

	@Query("select c from Customer c where c.userAccount.id = :id")
	Customer findCustomerByUserAccountId(int id);

}
