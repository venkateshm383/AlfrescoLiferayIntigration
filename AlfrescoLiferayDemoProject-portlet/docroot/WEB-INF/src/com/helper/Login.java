package com.helper;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.codehaus.jettison.json.JSONObject;

public class Login {

	
	public static void main(String[] args) {
	String ticket=	new Login().login("admin", "admin");
	System.out.println("ticket "+ ticket);
	}
	
static String host="http://52.19.4.0:8080";
	public String login(String uesrname ,String password) {

		
		
		String ticket = null;

		try{
		HttpClient client = new HttpClient();

 
System.out.println("inside  login method" +   host);
		String apiurl = host+"/alfresco/service/api/login";

		PostMethod post = new PostMethod(apiurl);

		try {

			JSONObject login = new JSONObject();

			login.put("username", uesrname);

			login.put("password", password);

 

			post.setDoAuthentication(true);

			post.setRequestHeader("Content-Type", "application/json");

			post.setRequestEntity(new StringRequestEntity(login.toString(),

					"application/json", "UTF-8"));

 

			int status = client.executeMethod(post);

			if (status != HttpStatus.SC_OK) {

				System.err.println("Method failed: " + post.getStatusLine());

			}

			String responseData = post.getResponseBodyAsString();

 

			System.out.println(responseData);

			JSONObject response = new JSONObject(responseData);

			ticket = response.getJSONObject("data").getString("ticket");

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			post.releaseConnection();

		}
		
		}catch(Exception e){
			
		}

		return ticket;

	}


}
