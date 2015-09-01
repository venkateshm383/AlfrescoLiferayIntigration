<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.model.User"%>
<%@page import="com.helper.Login"%>
<%@page import="org.apache.chemistry.opencmis.client.api.Session"%>
<%@page import="com.alfresco.api.example.BaseOnPremExample"%>

<%@page import="java.util.Set"%>
<%@page import="com.helper.Floder"%>
<%@page import="org.alfresco.webservice.authentication.AuthenticationFault"%>
<%@page import="org.alfresco.webservice.util.AuthenticationUtils"%>
<%@page import="org.alfresco.webservice.util.WebServiceFactory"%>




<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<div>
<%String file="";
String fileName="";
Floder floder=null;
try{
	file=(String)request.getAttribute("file");
	fileName=(String)request.getAttribute("fileName");
	floder=(Floder)request.getAttribute("filelist");
	if(floder==null){
		floder=new Floder();
	}
}catch(Exception e){
	
}

%>
<font style="color:blue;size: 6"><%=fileName %></font>
<%
String ticket="";
try{
User user=(User)request.getAttribute(WebKeys.USER);

Login login=new Login();
 ticket=login.login(user.getScreenName(), user.getPassword());
}catch(Exception e){
	e.printStackTrace();
}
%>
 
<a href="<%=file+""+ticket%>">Download File</a><br>

<h5>Properties</h5><br>
<h4>Name :</h4><%=floder.getFileName() %>
<h4>Created By :</h4><%=floder.getFileCreatedBy()%>
<h4>Created Date :</h4><%=floder.getDateFileCreated() %>
<h4>Modified Date :</h4><%=floder.getDateOfLastModefied() %>
<h4>LastModifiedBy :</h4><%=floder.getFilelLastModefiedBy() %>

<h4>Type :</h4><%=floder.getFileType() %>
<h4>Version Label :</h4><%=floder.getVersion() %>
<h4>Version List :</h4><select name="version">


</select>
</div>
</body>
</html>