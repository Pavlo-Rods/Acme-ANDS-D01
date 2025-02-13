<%--
- menu.jsp
-
- Copyright (C) 2012-2025 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:menu-bar>
	<acme:menu-left>
		<acme:menu-option code="master.menu.anonymous" access="isAnonymous()">
			<acme:menu-suboption code="master.menu.anonymous.list-jobs" action="/any/job/list" />
			<acme:menu-suboption code="master.menu.anonymous.list-shouts" action="/any/shout/list" />
		</acme:menu-option>

		<acme:menu-option code="master.menu.authenticated" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.authenticated.list-jobs" action="/any/job/list" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.authenticated.list-shouts" action="/any/shout/list" />
			<acme:menu-suboption code="master.menu.authenticated.list-announcements" action="/authenticated/announcement/list" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.authenticated.money-exchage" action="/authenticated/money-exchange/perform" />
		</acme:menu-option>
				
		<acme:menu-option code="master.menu.administrator" access="hasRealm('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.list-announcements" action="/administrator/announcement/list" />
			<acme:menu-suboption code="master.menu.administrator.list-companies" action="/administrator/company/list" />
			<acme:menu-suboption code="master.menu.administrator.list-whines" action="/administrator/whine/list" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.administrator.show-dashboard" action="/administrator/dashboard/show" />
			<acme:menu-suboption code="master.menu.administrator.list-user-accounts" action="/administrator/user-account/list" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.administrator.populate-db-initial" action="/administrator/system/populate-initial" />
			<acme:menu-suboption code="master.menu.administrator.populate-db-sample" action="/administrator/system/populate-sample" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.administrator.shut-system-down" action="/administrator/system/shut-down" />
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.employer" access="hasRealm('Employer')">
			<acme:menu-suboption code="master.menu.employer.list-my-jobs" action="/employer/job/list" />			
			<acme:menu-suboption code="master.menu.employer.list-my-applications" action="/employer/application/list" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.employer.list-whines" action="/customer/whine/list" />
		</acme:menu-option>

		<acme:menu-option code="master.menu.worker" access="hasRealm('Worker')">
			<acme:menu-suboption code="master.menu.worker.list-prospective-jobs" action="/worker/job/list" />
			<acme:menu-suboption code="master.menu.worker.list-my-applications" action="/worker/application/list" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.worker.list-whines" action="/customer/whine/list" />
		</acme:menu-option>
	</acme:menu-left>

	<acme:menu-right>		
		<acme:menu-option code="master.menu.user-account" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.user-account.general-profile" action="/authenticated/user-account/update" />
			<acme:menu-suboption code="master.menu.user-account.become-employer" action="/authenticated/employer/create" access="!hasRealm('Employer')" />
			<acme:menu-suboption code="master.menu.user-account.employer-profile" action="/authenticated/employer/update" access="hasRealm('Employer')" />
			<acme:menu-suboption code="master.menu.user-account.become-worker" action="/authenticated/worker/create" access="!hasRealm('Worker')" />
			<acme:menu-suboption code="master.menu.user-account.worker-profile" action="/authenticated/worker/update" access="hasRealm('Worker')" />
		</acme:menu-option>
	</acme:menu-right>
</acme:menu-bar>

