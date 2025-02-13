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
	<acme:input-textbox code="administrator.whine.form.label.header" path="header" readonly="true"/>
	<acme:input-textarea code="administrator.whine.form.label.description" path="description" readonly="true"/>
	<acme:input-textarea code="administrator.whine.form.label.redress" path="redress"/>
		
	<jstl:if test="${redress == 'N/A'}">
		<acme:input-checkbox code="administrator.whine.form.label.confirmation" path="confirmation"/>
		<acme:submit code="administrator.whine.form.button.update" action="/administrator/whine/update"/>
	</jstl:if>		
</acme:form>
