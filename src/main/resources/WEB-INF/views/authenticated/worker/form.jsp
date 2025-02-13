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
	<acme:input-textarea code="authenticated.worker.form.label.qualifications" path="qualifications"/>
	<acme:input-textarea code="authenticated.worker.form.label.skills" path="skills"/>
	<acme:input-textbox code="authenticated.worker.form.label.credit-card" path="creditCard"/>
	
	<acme:submit test="${_command == 'create'}" code="authenticated.worker.form.button.create" action="/authenticated/worker/create"/>
	<acme:submit test="${_command == 'update'}" code="authenticated.worker.form.button.update" action="/authenticated/worker/update"/>
</acme:form>
