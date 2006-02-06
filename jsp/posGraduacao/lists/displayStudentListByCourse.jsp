<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert page="/layout/fenixLayout_posGrad.jsp" flush="true">
  <tiles:put name="title" value="Secretaria de P�s-Gradua��o" />
  <tiles:put name="serviceName" value="Secretaria de P�s-Gradua��o" />
  <tiles:put name="navLocal" value="/posGraduacao/lists/listsMenu.jsp" />
  <tiles:put name="navGeral" value="/posGraduacao/commonNavGeralPosGraduacao.jsp" />
  <tiles:put name="body-context" value="/commons/blank.jsp"/>  
  <tiles:put name="body" value="/posGraduacao/lists/displayStudentListByCourse_bd.jsp" />
  <tiles:put name="footer" value="/copyright.jsp" />
</tiles:insert>