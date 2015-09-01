package com.alfresco.api.example;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.alfresco.webservice.repository.RepositoryServiceSoapBindingStub;
import org.alfresco.webservice.types.Reference;
import org.alfresco.webservice.util.WebServiceFactory;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.OperationContext;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.runtime.OperationContextImpl;
import org.apache.chemistry.opencmis.commons.data.PropertyData;

import com.helper.Floder;

/**
 * An extremely basic CMIS query example. This is a port of the "Execute a
 * Query" example that ships with the Groovy console in the Workbench plus an
 * OperationContext to show how to limit the number of results returned.
 * 
 * @author jpotts
 */
public class CmisBasicQuery extends BaseOnPremExample {
	public static void main(String[] args) {
		CmisBasicQuery sce = new CmisBasicQuery();
		sce.doQuery();

		String value = "/CMISDocuments/Bizruntime";
		String replacedValue = value.replaceAll("/", "/cm:");
		System.out.println(replacedValue);
	}

	public void doExample() {
		// doQuery("SELECT cmis:objectId, cmis:name, cmis:contentStreamLength FROM cmis:document",
		// 10);

		// doQuery("SELECT * FROM cmis:folder WHERE CONTAINS('PATH:\"/app:company_home/*\"')",30);
	}

	public ArrayList<Floder> doDocumentQuery() {

		Session cmisSession = getCmisSession();

		String cql = "SELECT * FROM cmis:document WHERE CONTAINS('PATH:\"/app:company_home/*\"')";
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

					System.out.println(prop.getQueryName() + ":"
							+ prop.getFirstValue());

				}
				System.out.println(floder.getFileName());
				boolean duplicateFile = false;
				int j=0;
				for (Iterator iterator = floderList.iterator(); iterator
						.hasNext();) {
					Floder floder1 = (Floder) iterator.next();
					try {
						if (floder1.getFileName().equalsIgnoreCase(
								floder.getFileName())) {
							System.out.println("ducplicate file exist");
							duplicateFile = true;
							
							++j;
							
						}

					} catch (Exception e) {
						// TODO: handle exception
					}
				}

				if (!duplicateFile) {
					
					floderList.add(floder);
				}

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

	public ArrayList<Floder> doQuery() {
		Session cmisSession = getCmisSession();
		String cql = "SELECT * FROM cmis:folder WHERE CONTAINS('PATH:\"/app:company_home/*\"')";
		int maxItems = 30;
		OperationContext oc = new OperationContextImpl();
		oc.setMaxItemsPerPage(maxItems);
		ArrayList<Floder> floderList = new ArrayList<Floder>();

		String rootFolderid = cmisSession.getRootFolder().getId();
		try {
			ItemIterable<QueryResult> results = cmisSession.query(cql, false,
					oc);

			for (QueryResult result : results) {
				int i = 0;
				Floder floder = new Floder();

				floder.setRootFloder(rootFolderid);

				for (PropertyData<?> prop : result.getProperties()) {
					++i;

					switch (i) {
					case 1:

						floder.setFloderId(prop.getFirstValue().toString());
						break;

					case 3:
						floder.setFloderPath(prop.getFirstValue().toString());

						break;
					case 14:
						floder.setFloderName(prop.getFirstValue().toString());

						break;

					default:
						break;
					}
					/*
					 * System.out.println(prop.getFirstValue());
					 */

					System.out.println(prop.getQueryName() + ":-"
							+ prop.getFirstValue());

				}

				System.out.println("---------------root floder--------------->"
						+ floder.getRootFloder());

				floderList.add(floder);

				System.out.println("--------------------------------------");
			}

			ArrayList<Floder> documentList = doDocumentQuery();
			for (Floder floder : documentList) {
				floderList.add(floder);
			}
			System.out.println("Total number: " + results.getTotalNumItems()
					+ "data");
			System.out.println("Has more: " + results.getHasMoreItems());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return floderList;
	}

	public ArrayList<Floder> doQuerySpcificFlodeer(String path,
			String rootfloderid) {
		Session cmisSession = getCmisSession();
		String cql = "SELECT * FROM cmis:folder WHERE CONTAINS('PATH:\"/app:company_home"
				+ path + "/*\"')";
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
				floder.setRootFloder(rootfloderid);

				for (PropertyData<?> prop : result.getProperties()) {
					++i;
					switch (i) {
					case 1:

						floder.setFloderId(prop.getFirstValue().toString());
						break;

					case 3:
						floder.setFloderPath(prop.getFirstValue().toString());

						break;
					case 14:
						floder.setFloderName(prop.getFirstValue().toString());

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
				floderList.add(floder);

			}

			ArrayList<Floder> documentList = doQuerySpcificFlodeerDocument(path);
			for (Floder floder : documentList) {
				floderList.add(floder);
			}
			
			System.out.println("Total number: " + results.getTotalNumItems()
					+ "data");
			System.out.println("Has more: " + results.getHasMoreItems());

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return floderList;
	}

	public ArrayList<Floder> doQuerySpcificFlodeerDocument(String path) {
		Session cmisSession = getCmisSession();
		String cql = "SELECT * FROM cmis:document WHERE CONTAINS('PATH:\"/app:company_home"
				+ path + "/*\"')";
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
				List<Document> docuem=	doc.getAllVersions();
				for (Iterator iterator = docuem.iterator(); iterator.hasNext();) {
					Document document = (Document) iterator.next();
					System.out.println(document.getVersionLabel());
			System.out.println(document.getId());

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

}
