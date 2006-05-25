<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import="net.sourceforge.fenixedu.presentationTier.Action.sop.utils.SessionConstants" %>
<%@ page import="net.sourceforge.fenixedu.domain.degree.DegreeType" %>
<%@ page import="net.sourceforge.fenixedu.dataTransferObject.InfoExecutionDegree"%>

<logic:present name="<%= SessionConstants.MASTER_DEGREE %>"  >

	<bean:define id="infoExecutionDegree" name="<%= SessionConstants.MASTER_DEGREE %>" scope="session"/>
		<p><em class="infoop4">
		<bean:message bundle="ENUMERATION_RESOURCES" key="<%=((InfoExecutionDegree)infoExecutionDegree).getInfoDegreeCurricularPlan().getInfoDegree().getTipoCurso().toString()%>" />
		&nbsp;
		<bean:message bundle="GLOBAL_RESOURCES" key="in"/>
		&nbsp;
		<bean:write name="infoExecutionDegree" property="infoDegreeCurricularPlan.infoDegree.nome" />&nbsp;&nbsp;>&nbsp;&nbsp;
		<bean:write name="infoExecutionDegree" property="infoDegreeCurricularPlan.name" />
		</em></p>

	<logic:equal name="infoExecutionDegree" property="infoDegreeCurricularPlan.infoDegree.tipoCurso" value="<%= DegreeType.MASTER_DEGREE.toString() %>">
		<bean:define id="candidates" name="<%= SessionConstants.MASTER_DEGREE_CANDIDATE_AMMOUNT %>" scope="session"/>
		<strong><bean:message key="label.masterDegree.coordinator.candidates"/></strong>
		<bean:write name="candidates" />
	</logic:equal>

</logic:present>
