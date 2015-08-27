package com.helper;

import java.io.File;
import java.io.IOException;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatch;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import com.alfresco.api.example.util.Config;

public class FileUpload {
	
	
	public static void uploadDocument(String authTicket, File fileobj, String filename, String filetype, String description, String destination)
	{
	try
	{
		
	String host=	 Config.getConfig().getProperty("host");
	String urlString = host+"/service/api/upload?alf_ticket="+authTicket;
	System.out.println("The upload url:::"+urlString+" rooot "+ destination);
	HttpClient client = new HttpClient();
	PostMethod mPost = new PostMethod(urlString);
	//File f1 =fileobj;
	Part[] parts = {
	new FilePart("filedata",filename,fileobj,filetype,null),
	new StringPart("filename", filename),
	new StringPart("description", description),
	new StringPart("destination", "workspace://SpacesStore/"+destination),
	

	/*new StringPart("description", description),
	new StringPart("siteid", "bizTestSite"),
	new StringPart("containerid", "documentLibrary"),*/
	new StringPart("uploaddirectory", "/")


	};
	mPost.setRequestEntity(
	new MultipartRequestEntity(parts, mPost.getParams())
	);
	int statusCode1 = client.executeMethod(mPost);
	System.out.println("statusLine>>>" +statusCode1+"......"+ mPost.getStatusLine()+mPost.getResponseBodyAsString());
	mPost.releaseConnection();

	}
	catch (Exception e)
	{
	System.out.println(e);
	}
	}
	
	
	
	public static void upload(String username,String password,File file,String fileName,String root)throws IOException
	{
	// SimpleUpload aw=new SimpleUpload();
	//String Ticket=aw.login();
	//String ticket="TICKET_3e61ccfa8a11690b10e1a2fb0eeee2c5583b0043";
		String host=	 Config.getConfig().getProperty("host");
	String alfrescoTiccketURL = host+"/service/api/login?u="+username+"&pw="+password;




	String ticketURLResponse = invokeWebScriptgetRequest(alfrescoTiccketURL);

	//ticketURLResponse =
	int startindex= ticketURLResponse.indexOf("<ticket>")+8;
	int endindex = ticketURLResponse.indexOf("</ticket>");

	ticketURLResponse = ticketURLResponse.substring(startindex, endindex);



	File f=file;
	// getMagicMatch accepts Files or byte[],
	// which is nice if you want to test streams
	System.out.println("f.exists()"+f.exists());
	MagicMatch match=null;
	try{
		match = Magic.getMagicMatch(f,false);
	}catch(Exception e){
		e.printStackTrace();
	}
	System.out.println("ContentType :"+match.getMimeType()); 
	//FileInputStream is=new FileInputStream(f);
	uploadDocument(ticketURLResponse, f,fileName,match.getMimeType(),"description",root);

	//uploadDocument("TICKET_3ef085c4e24f4e2c53a3fa72b3111e55ee6f0543", f,"47.bmp","image file","application/jpg","workspace://SpacesStore/65a06f8c-0b35-4dae-9835-e38414a99bc1");
	}
	public static void main(String args[])throws IOException, MagicParseException, MagicMatchNotFoundException, MagicException
	{
	// SimpleUpload aw=new SimpleUpload();
	//String Ticket=aw.login();
	//String ticket="TICKET_3e61ccfa8a11690b10e1a2fb0eeee2c5583b0043";

	String alfrescoTiccketURL = "http://52.19.4.0:8080/alfresco"+"/service/api/login?u="+"admin"+"&pw="+"admin";




	String ticketURLResponse = invokeWebScriptgetRequest(alfrescoTiccketURL);

	//ticketURLResponse =
	int startindex= ticketURLResponse.indexOf("<ticket>")+8;
	int endindex = ticketURLResponse.indexOf("</ticket>");

	ticketURLResponse = ticketURLResponse.substring(startindex, endindex);



	File f=new File("/home/venkateshm/Desktop/Referral_Agreement_test_2015-07-07 06_55_18.pdf");

	Magic parser = new Magic() ;
	// getMagicMatch accepts Files or byte[],
	// which is nice if you want to test streams
	System.out.println("f.exists()"+f.exists());
	MagicMatch match = Magic.getMagicMatch(f,false);
	System.out.println("ContentType :"+match.getMimeType()); 
	
	
	//FileInputStream is=new FileInputStream(f);
	//uploadDocument(ticketURLResponse, f,f.getName(),"application/pdf","description","d1ff0daa-4d90-438a-a724-13a290b65ac1");

	//uploadDocument("TICKET_3ef085c4e24f4e2c53a3fa72b3111e55ee6f0543", f,"47.bmp","image file","application/jpg","workspace://SpacesStore/65a06f8c-0b35-4dae-9835-e38414a99bc1");
	}	public static String invokeWebScriptgetRequest(String url){


		// Create an instance of HttpClient.
		HttpClient client = new HttpClient();

		// Create a method instance.
		GetMethod method = new GetMethod(url);
		/* // Provide custom retry handler is necessary
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
		new DefaultHttpMethodRetryHandler(3, false));*/
		String response = null;
		try {
		// Execute the method.
		int statusCode = client.executeMethod(method);

		if (statusCode != HttpStatus.SC_OK) {
		System.err.println("Method failed: " + method.getStatusLine());
		}

		// Read the response body.
		byte[] responseBody = method.getResponseBody();

		// Deal with the response.
		// Use caution: ensure correct character encoding and is not binary data
		response = new String(responseBody);
		System.out.println(response);

		} catch (HttpException e) {
		System.err.println("Fatal protocol violation: " + e.getMessage());
		e.printStackTrace();
		} catch (IOException e) {
		System.err.println("Fatal transport error: " + e.getMessage());
		e.printStackTrace();
		} finally {
		// Release the connection.
		method.releaseConnection();
		}
		return response;

		}

}
