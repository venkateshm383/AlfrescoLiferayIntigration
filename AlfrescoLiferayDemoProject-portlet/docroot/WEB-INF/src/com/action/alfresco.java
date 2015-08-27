package com.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.alfresco.api.example.BaseOnPremExample;
import com.alfresco.api.example.CmisBasicQuery;
import com.helper.FileUpload;
import com.helper.Floder;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class alfresco
 */
public class alfresco extends MVCPortlet {
 
	
	 protected String viewJSP;

		
		public void init() {
	        viewJSP = getInitParameter("view-template");
	    }
			
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws PortletException, IOException {
		// TODO Auto-generated method stub
		String currentViewPage=renderRequest.getParameter("jspPage");
		System.out.println("inside jsp page");
		
		if(currentViewPage!=null&&!currentViewPage.equals("")){
			include(currentViewPage, renderRequest, renderResponse);
		}else{
			include(viewJSP, renderRequest, renderResponse);
		}
	}	

	
	
	public void home(ActionRequest request, ActionResponse response)
			throws PortletException, IOException{
		
		User user=(User)request.getAttribute(WebKeys.USER);

		System.out.println("userName "+user.getScreenName() +"userpassword"+user.getPassword() );
		BaseOnPremExample.setUserPassword(user.getScreenName() , user.getPassword());
		/*String ticket=new Login().login(user.getScreenName().trim(),user.getPassword().trim());
		System.out.println("ticket "+ticket);
		
		if(ticket!=null){
		request.setAttribute("ticket", ticket);
		}else{*/
		
		
		
		ArrayList<Floder> floderList=new CmisBasicQuery().doQuery();
			request.setAttribute("floderList", floderList);

	/*	}*/
		
		
		response.setRenderParameter("jspPage","/html/alfresco/Home.jsp");
	
		
	}
			
	public void	getSubfloder(ActionRequest request, ActionResponse response)
			throws PortletException, IOException{
		
		String path=request.getParameter("path");
		String floderId=request.getParameter("floderid");
		System.out.println("path------------------------------------------------------------- "+path  +"---floderid--------------"+floderId);
		User user=(User)request.getAttribute(WebKeys.USER);

		System.out.println("userName "+user.getScreenName() +"userpassword"+user.getPassword() );
		BaseOnPremExample.setUserPassword(user.getScreenName() , user.getPassword());
		
		ArrayList<Floder> floderList=new CmisBasicQuery().doQuerySpcificFlodeer(path,floderId);
		request.setAttribute("floderList", floderList);
		request.setAttribute("floderId", floderId);
		response.setRenderParameter("jspPage","/html/alfresco/Home.jsp");

	}
	
	public void	fileView(ActionRequest request, ActionResponse response)
			throws PortletException, IOException{
		
		String fileName=request.getParameter("fileName");
		String fileId=request.getParameter("fileId");
String ticket="";
		request.setAttribute("fileName", fileName);
		request.setAttribute("file", "http://52.19.4.0:8080/share/proxy/alfresco/slingshot/node/content/workspace/SpacesStore/"+fileId+"/"+fileName+"?a=true&alf_ticket="+ticket);
		
		response.setRenderParameter("jspPage","/html/alfresco/fileview.jsp");

		
	}
		
	
	 public void uploadCase(ActionRequest actionRequest,
			          ActionResponse actionRresponse) throws PortletException,
			             IOException {
			User user=(User)actionRequest.getAttribute(WebKeys.USER);
			System.out.println("userName "+user.getScreenName() +"userpassword ----"+user.getPassword() );

			
			  String rootFloder=actionRequest.getParameter("node");
			         String folder = getInitParameter("uploadFolder");
			     String    realPath = getPortletContext().getRealPath("/");
			  
			         System.out.println("RealPath" + realPath + " UploadFolder :" + folder);
			         try {
			        	 System.out.println("Siamo nel try");
			            UploadPortletRequest uploadRequest = PortalUtil
			                    .getUploadPortletRequest(actionRequest);
			        System.out.println("Size: "+uploadRequest.getSize("fileName"));
			 
			       if (uploadRequest.getSize("fileName")==0) {
			            SessionErrors.add(actionRequest, "error");
			        }
			 
			            String sourceFileName = uploadRequest.getFileName("fileName");
			            File file = uploadRequest.getFile("fileName");
			          
			      
			            new FileUpload().upload(user.getScreenName().toString(), user.getPassword().toString(),file,uploadRequest.getFileName("fileName"), rootFloder);
			            System.out.println("Nome file:" + uploadRequest.getFileName("fileName"));
			            File newFile = null;
			            newFile = new File(folder + sourceFileName);
			             System.out.println("New file name: " + newFile.getName());
			             System.out.println("New file path: " + newFile.getPath());
			 
			            InputStream in = new BufferedInputStream(uploadRequest.getFileAsStream("fileName"));
			            FileInputStream fis = new FileInputStream(file);
			            FileOutputStream fos = new FileOutputStream(newFile);
			 
			            byte[] bytes_ = FileUtil.getBytes(in);
			            int i = fis.read(bytes_);
			 
			            while (i != -1) {
			                fos.write(bytes_, 0, i);
			                i = fis.read(bytes_);
			            }
			            fis.close();
			            fos.close();
			            Float size = (float) newFile.length();
			            System.out.println("file size bytes:" + size);
			            System.out.println("file size Mb:" + size / 1048576);
			 
			             System.out.println("File created: " + newFile.getName());
			            SessionMessages.add(actionRequest, "success");
			 
			        } catch (FileNotFoundException e) {
			            System.out.println("File Not Found.");
			            e.printStackTrace();
			            SessionMessages.add(actionRequest, "error");
			        } catch (NullPointerException e) {
			            System.out.println("File Not Found");
			            e.printStackTrace();
			            SessionMessages.add(actionRequest, "error");
			        }
			 
			        catch (IOException e1) {
			            System.out.println("Error Reading The File.");
			            SessionMessages.add(actionRequest, "error");
			            e1.printStackTrace();
			        }
			         
			         BaseOnPremExample.setUserPassword(user.getScreenName() , user.getPassword());
			 		
			 		/*ArrayList<Floder> floderList=new CmisBasicQuery().doQuerySpcificFlodeer(path);
			 		request.setAttribute("floderList", floderList);

			 		response.setRenderParameter("jspPage","/html/alfresco/Home.jsp");*/
			 
			    }
	
	
	
	
	protected void include(
	        String path, RenderRequest renderRequest,
	        RenderResponse renderResponse)
	    throws IOException, PortletException {

	    PortletRequestDispatcher portletRequestDispatcher =
	        getPortletContext().getRequestDispatcher(path);

	    if (portletRequestDispatcher == null) {
	        System.out.println("not valide include");
	    }
	    else {
	        portletRequestDispatcher.include(renderRequest, renderResponse);
	    }
	}
}
