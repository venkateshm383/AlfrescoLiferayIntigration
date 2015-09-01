<%@page import="org.alfresco.webservice.util.AuthenticationUtils"%>
<%@page import="org.alfresco.webservice.util.WebServiceFactory"%>
<%@page import="com.helper.Floder"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<portlet:defineObjects />

<body>

<%ArrayList<Floder> floderList=(ArrayList<Floder>)request.getAttribute("floderList");
String rootfloder="";
String flooderRootid="";
System.out.println("floder size---------------------->"+floderList.size());
try{
	
	flooderRootid=(String)request.getAttribute("floderId");
	
}catch(Exception e){
	
}
%>
<table class="table table-hover" style="width: 100%">
<tr><th>Weh</th></tr>

<% for(int i=0;i<floderList.size();i++){
	Floder floder=floderList.get(i);

	String value="";
	String replacedValue="";

	try{
	 value=floder.getFloderPath();
	 replacedValue=value.replaceAll("/","/cm:");
	}catch(Exception e){
		
	}
	
%>
<%if(floder.getFileName()==null){
	rootfloder=floder.getRootFloder();
System.out.print("root"+rootfloder);
%>
<portlet:actionURL var="path" name="getSubfloder">


	        <portlet:param name="floderid" value="<%=floder.getFloderId()%>"/>
	

        <portlet:param name="path" value="<%=replacedValue%>"/>
    </portlet:actionURL>
<tr>

<td><a href="<%=path%>">
<% 	
	out.println(floder.getFloderName());%>
	</a>
	</td>
	</tr>
<%}else{
request.setAttribute("fileDetails", floder);
%>
<tr><td>
<portlet:actionURL var="file" name="fileView">
 <portlet:param name="createdBy" value="<%=floder.getFileCreatedBy()%>"/>
 <portlet:param name="modefiedBy" value="<%=floder.getFilelLastModefiedBy()%>"/>
	 <portlet:param name="createdDate" value="<%=floder.getDateFileCreated().toString()%>"/>
	 
	 	 <portlet:param name="modefiedDate" value="<%=floder.getDateFileCreated().toString()%>"/>
	 	 	 <portlet:param name="type" value="<%=floder.getFileType()%>"/>
	 	 	 <portlet:param name="version" value="<%=floder.getVersion()%>"/>
	 
 <portlet:param name="fileName" value="<%=floder.getFileName()%>"/>
        <portlet:param name="fileName" value="<%=floder.getFileName()%>"/>
                <portlet:param name="fileId" value="<%=floder.getFileId()%>"/>
        
    </portlet:actionURL>
    
 
    
    <a href="<%=file%>">
<% out.println(floder.getFileName());%></a>
</td></tr>

<% }}


%>




</table>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
 <%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
 <%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
 <%@ page import="com.liferay.portal.kernel.util.ParamUtil"%>
 <%@ page import="com.liferay.portal.kernel.util.Validator"%>
 <%@ page import="javax.portlet.PortletPreferences"%>
 <%@ page import="com.liferay.util.PwdGenerator"%>
 <portlet:defineObjects />
<%
String uploadProgressId = PwdGenerator.getPassword(PwdGenerator.KEY3, 4);
    PortletPreferences prefs = renderRequest.getPreferences();

%>

    

<portlet:actionURL var="editCaseURL" name="uploadCase">
<%if(!rootfloder.isEmpty()) {

	System.out.println("root floder origainl "+rootfloder);
%>


                  <portlet:param name="node" value="<%=rootfloder%>"/>
  <%}else{ 
	  System.out.println("root floder fasil "+flooderRootid);
  %>
   <portlet:param name="node" value="<%=flooderRootid%>"/>
   <%} %>
</portlet:actionURL>

<aui:form action="<%= editCaseURL %>" enctype="multipart/form-data" method="post" >
<aui:input type="file" name="fileName" size="75" />

<aui:input type="hidden" name="root" size="75"/>
<input type="submit" value="<liferay-ui:message key="upload" />" onClick="<%= uploadProgressId %>.startProgress(); return true;"/>
<!--  aui:button type="submit" value="Save" /-->
</aui:form>


<br />
<br />
<br />
<br />



</body>
</html>