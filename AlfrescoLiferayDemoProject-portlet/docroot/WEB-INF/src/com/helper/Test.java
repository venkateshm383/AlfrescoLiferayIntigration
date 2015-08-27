package com.helper;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.OperationContext;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.OperationContextImpl;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;
 
 
public class Test {
 
	public static void main(String[] args) throws UnsupportedEncodingException {
		Map<String, String> sessionParameters = new HashMap<String, String>();
		sessionParameters.put(SessionParameter.USER, "admin");
		sessionParameters.put(SessionParameter.PASSWORD, "admin");
		sessionParameters.put(SessionParameter.ATOMPUB_URL, "http://52.19.4.0:8080/alfresco/api/-default-/public/cmis/versions/1.1/atom");
		sessionParameters.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
		SessionFactory sessionFactory = SessionFactoryImpl.newInstance();
		Session lSession = sessionFactory.getRepositories(sessionParameters).get(0).createSession();
		OperationContext oc = new OperationContextImpl();

		Folder root = lSession.getRootFolder();
		System.out.println(root.getId());
               /* Map<String, Object> folderProperties = new HashMap<String, Object>();
                folderProperties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
                folderProperties.put(PropertyIds.NAME, "test");
                Folder newFolder = root.createFolder(folderProperties);
                
                System.out.println("newfloder "+newFolder);
		Map<String, Object> lProperties = new HashMap<String, Object>();
		String name = "testdocument.txt";
		lProperties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
		lProperties.put(PropertyIds.NAME, name);
	

		byte[] content = "CMIS Testdata One".getBytes();
                InputStream stream = new ByteArrayInputStream(content);
                ContentStream contentStream = new ContentStreamImpl(name, new BigInteger(content), "text/plain", stream);
                Document newContent1 =  newFolder.createDocument(lProperties, contentStream, null);
		System.out.println("Document created: " + newContent1.getId());*/
	}
}