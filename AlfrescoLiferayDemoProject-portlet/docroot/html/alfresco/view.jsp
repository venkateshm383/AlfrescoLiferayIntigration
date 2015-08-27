<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.model.User"%>
<portlet:defineObjects />

<portlet:renderURL  var="home1" windowState="MAXIMIZED">
<portlet:param name="jspPage" value="/html/alfresco/Home.jsp"/>
</portlet:renderURL>

<portlet:renderURL  var="file" windowState="MAXIMIZED">
<portlet:param name="jspPage" value="/html/alfresco/fileview.jsp"/>
</portlet:renderURL>



This is the <b>Alfresco</b> portlet in View mode.


<portlet:actionURL var="home"  name="home">
</portlet:actionURL>

<a href="<%=home.toString()%>"><button>Home</button></a>
