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
	<acme:input-textbox code="any.job.form.label.ticker" path="ticker"/>
	<acme:input-select code="any.job.form.label.contractor" path="contractor" choices="${contractors}"/>
	<acme:input-textbox code="any.job.form.label.title" path="title"/>
	<acme:input-moment code="any.job.form.label.deadline" path="deadline"/>
	<acme:input-money code="any.job.form.label.salary" path="salary"/>
	<acme:input-double code="any.job.form.label.score" path="score"/>
	<acme:input-url code="any.job.form.label.moreInfo" path="moreInfo"/>
	<acme:input-textarea code="any.job.form.label.description" path="description"/>
	
	<acme:button code="any.job.form.button.duties" action="/any/duty/list?masterId=${id}"/>
</acme:form>
