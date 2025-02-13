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
	<acme:hidden-data path="jobId"/>

	<acme:input-textbox code="worker.application.form.label.ticker" path="ticker" readonly="true"/>
	<jstl:if test="${_command == 'show'}">
		<acme:input-textbox code="worker.application.form.label.moment" path="moment"/>
		<acme:input-textbox code="worker.application.form.label.status" path="status"/>
	</jstl:if>	
	<acme:input-textarea code="worker.application.form.label.statement" path="statement"/>
	<acme:input-textarea code="worker.application.form.label.qualifications" path="qualifications"/>
	<acme:input-textarea code="worker.application.form.label.skills" path="skills"/>	
			
	<acme:submit test="${_command == 'create'}" code="worker.application.button.create" action="/worker/application/create?jobId=${jobId}"/>		
</acme:form>

