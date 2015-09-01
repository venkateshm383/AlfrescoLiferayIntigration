package com.alfresco.api.example;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.OperationContext;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.runtime.OperationContextImpl;
import org.apache.chemistry.opencmis.commons.data.PropertyData;

import com.helper.Floder;

/**
 * An example of how to do a join with a CMIS query to select properties
 * defined in an aspect.
 * 
 * @author jpotts
 */
public class CmisAspectQuery extends CmisBasicQuery {

	public static void main(String[] args) {
		CmisAspectQuery caqe = new CmisAspectQuery();
		//caqe.doExample();/CMISDocuments/Bizruntime
		caqe.doQuerySpcificFlodeerDocument("");
	}


		
		
		public ArrayList<Floder> doQuerySpcificFlodeerDocument(String path) {
			Session cmisSession = getCmisSession();
			String cql = "SELECT * FROM cmis:document WHERE CONTAINS('PATH:\" /app:company_home/cm:CMISDocuments/cm:portal-ext.properties\"')";
			int maxItems = 30;
			OperationContext oc = new OperationContextImpl();
			oc.setMaxItemsPerPage(maxItems);
			ArrayList<Floder> floderList = new ArrayList<Floder>();

			try {
				ItemIterable<QueryResult> results = cmisSession.query(cql, false,
						oc);

				for (QueryResult result : results) {
					int i = 0;
					Floder floder = new Floder();

					for (PropertyData<?> prop : result.getProperties()) {
						++i;
						switch (i) {

						case 11:

							floder.setFileName(prop.getFirstValue().toString());
							break;

						case 25:
							floder.setFileId(prop.getFirstValue().toString());
							break;
						case 3:
							floder.setVersion(prop.getFirstValue().toString());
							break;

						case 6:
							floder.setFileCreatedBy(prop.getFirstValue().toString());
							break;
						case 8:
							GregorianCalendar calender = (GregorianCalendar) prop
									.getFirstValue();
							floder.setDateFileCreated(calender.getTime());

							break;
						
						case 13:
							GregorianCalendar calender1 = (GregorianCalendar) prop
									.getFirstValue();
							floder.setDateOfLastModefied(calender1.getTime());
							
						case 16:
							floder.setFilelLastModefiedBy(prop.getFirstValue().toString());
							break;
							
						case 19:
							floder.setFileType(prop.getFirstValue().toString());
							break;
						default:
							break;
						}
						
				
						/*
						 * System.out.println(prop.getFirstValue());
						 */

						System.out
								.println(prop.getQueryName()+":" + prop.getFirstValue());

					}
					
					
					Document doc = (Document) cmisSession.getObject(floder.getFileId());
					List<Document> docuem=	doc.getAllVersions(oc);
					for (Iterator iterator = docuem.iterator(); iterator.hasNext();) {
						Document document = (Document) iterator.next();
						System.out.println(document.getVersionLabel());
				System.out.println(document.getVersionSeriesId());
//System.out.println("dbject Id"+((Document)cmisSession.getObject(document.getVersionSeriesId())).getObjectOfLatestVersion(false).);
					}
					
					
					floderList.add(floder);

					System.out.println("--------------------------------------");

				}
				
			
				

				System.out.println("Total number: " + results.getTotalNumItems()
						+ "data");
				System.out.println("Has more: " + results.getHasMoreItems());

			
			} catch (Exception e) {
	e.printStackTrace();
			}
			return floderList;
		}
	/*	doQuery("SELECT D.cmis:name, T.cm:title, T.cm:description FROM cmis:document" +
					 " as D join cm:titled as T on D.cmis:objectId = T.cmis:objectId" +
					 " where D.cmis:name like '%test%'", 5);*/
	
}
