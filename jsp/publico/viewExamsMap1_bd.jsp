<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %><%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %><%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %><%@ taglib uri="/WEB-INF/app.tld" prefix="app" %><%@ page import="ServidorApresentacao.Action.sop.utils.SessionConstants" %><%@ page import="Util.TipoCurso" %><logic:present name="infoDegreeCurricularPlan" ><bean:define id="degreeCurricularPlanID" name="infoDegreeCurricularPlan" property="idInternal" /><div class="breadcumbs"><a href="http://www.ist.utl.pt/index.shtml"><bean:message key="label.school" /></a>	<bean:define id="degreeType" name="infoDegreeCurricularPlan" property="infoDegree.tipoCurso" />		&nbsp;&gt;&nbsp;<a href="http://www.ist.utl.pt/html/ensino/index.shtml"><bean:message key="label.education" /></a>		&nbsp;&gt;&nbsp;	<html:link page="<%= "/showDegreeSite.do?method=showDescription&amp;executionPeriodOID=" + request.getAttribute(SessionConstants.EXECUTION_PERIOD_OID) + "&amp;degreeID=" + request.getAttribute("degreeID").toString()  %>">		<bean:write name="infoDegreeCurricularPlan" property="infoDegree.sigla" />	</html:link>	&nbsp;&gt;&nbsp;	<html:link page="<%= "/showDegreeSite.do?method=showCurricularPlan&amp;degreeID=" + request.getAttribute("degreeID") + "&amp;degreeCurricularPlanID=" + pageContext.findAttribute("degreeCurricularPlanID").toString() + "&amp;executionPeriodOID=" + request.getAttribute(SessionConstants.EXECUTION_PERIOD_OID)  %>" >		<bean:message key="label.curricularPlan"/>	</html:link>	&nbsp;&gt;&nbsp;<bean:message key="label.exams"/> </div>	<%--<!-- PÁ�GINA EM INGLÊ�S -->	<div class="version">		<span class="px10">			<html:link page="<%= "/showDegreeSite.do?method=showCurricularPlan&amp;inEnglish=true&amp;executionPeriodOID=" + request.getAttribute(SessionConstants.EXECUTION_PERIOD_OID) + "&amp;degreeID=" +  request.getAttribute("degreeID") %>" >				<bean:message key="label.version.english" />			</html:link>			<img src="<%= request.getContextPath() %>/images/icon_uk.gif" alt="Icon: English version!" width="16" height="12" />	</span>		</div> --%>	<h1>	<bean:write name="infoDegreeCurricularPlan" property="infoDegree.tipoCurso" />		<bean:message key="label.in" />	<bean:write name="infoDegreeCurricularPlan" property="infoDegree.nome" /></h1><h2 class="greytxt">	<bean:message key="label.curricularPlan"/>	<bean:message key="label.of" />	<bean:define id="initialDate" name="infoDegreeCurricularPlan" property="initialDate" />			<%= initialDate.toString().substring(initialDate.toString().lastIndexOf(" ")+1) %>	<logic:notEmpty name="infoDegreeCurricularPlan" property="endDate">		<bean:define id="endDate" name="infoDegreeCurricularPlan" property="endDate" />			-<%= endDate.toString().substring(endDate.toString().lastIndexOf(" ")) %>	</logic:notEmpty></h2><logic:present name="lista" scope="request">	<bean:define id="listaNew" name="lista" />	<html:form action="/chooseExamsMapContextDANew.do">		<html:hidden property="<%SessionConstants.EXECUTION_PERIOD_OID%>" value="<%= ""+request.getAttribute(SessionConstants.EXECUTION_PERIOD_OID)%>" />		<html:hidden property="page" value="1"/>		<html:hidden property="method" value="choose"/>		<html:hidden property="degreeID" value="<%= ""+request.getAttribute("degreeID")%>" />		<html:hidden property="degreeCurricularPlanID" value="<%= pageContext.findAttribute("degreeCurricularPlanID").toString()%>" />		<html:hidden property="lista" value="<%= pageContext.findAttribute("listaNew").toString()%>" />		 		<table border="0" cellspacing="0" cellpadding="0">			<tr>			    <td width="100px"><bean:message key="property.executionPeriod"/>:</td>			    <td>					<html:select property="indice" size="1" onchange='this.form.submit();'>						<html:options property="value" labelProperty="label" collection="lista"/>					</html:select>			    </td>			</tr>		</table>	</html:form></logic:present><div><app:generateNewExamsMap name="<%= SessionConstants.INFO_EXAMS_MAP %>" user="public" mapType=" "/></div></logic:present>