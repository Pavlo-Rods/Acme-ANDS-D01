<%--
- form.jsp
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
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.employer.form.label.area" path="area"/>
	<acme:input-textbox code="authenticated.employer.form.label.sector" path="sector"/>
	<acme:input-textbox code="authenticated.employer.form.label.credit-card" path="creditCard"/>
	
	<acme:submit test="${_command == 'create'}" code="authenticated.employer.form.button.create" action="/authenticated/employer/create"/>
	<jstl:if test="${_command == 'update'}">
		<acme:submit code="authenticated.employer.form.button.update" action="/authenticated/employer/update"/>
		<acme:button code="authenticated.employer.form.button.contractors" action="/employer/works-for/list"/>
	</jstl:if>	
</acme:form>
