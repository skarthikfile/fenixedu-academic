<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert page="/layout/fenixLayout_2col.jsp" flush="true">
  <tiles:put name="title" value="Coordenador" />
  <tiles:put name="serviceName" value="Portal do Coordenador" />
  <tiles:put name="navLocal" value="/coordinator/coordinatorMainMenu.jsp" />
  <tiles:put name="navGeral" value="/coordinator/commonNavGeralCoordinator.jsp" />
  <tiles:put name="body-context" value="/coordinator/context.jsp"/>  
  <tiles:put name="body" value="/coordinator/welcomeScreen.jsp" />
  <tiles:put name="footer" value="/copyright.jsp" />
</tiles:insert>

