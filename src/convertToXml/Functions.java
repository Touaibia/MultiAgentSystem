package convertToXml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class Functions {


	  BufferedReader inVar;
	  BufferedReader inDom;
	  BufferedReader inCtr;
	  StreamResult out;
	  
	  static String PATH_TO_DATA = "C:\\Users\\Adel  Touaibia\\Desktop\\Cours\\Multi-Agent-System\\FullRLFAP\\FullRLFAP\\CELAR\\";
      static String FOLDER = "scen09\\";
	  static String PATH_TO_SAVE_DATA = "C:/Users/Adel  Touaibia/Desktop/Cours/Multi-Agent-System/FullRLFAP/FullRLFAP/CELAR/";
	  static String FOLDER_TO_SAVE_FILE = "scen09";
	  TransformerHandler th;
	  AttributesImpl atts;
	  
	  List Predicates = new ArrayList();

	  
	  void GetXml() throws SAXException, IOException {
		  System.out.println("Begin !");
		  getAgents();
		  getDomains();
		  getVariables();
		  getContraints();
		  getPredicates();
		  closeDocument();
		  System.out.println("End !!");

	  }
	  
	  private void getPredicates() {
		   
		    try {
		    	
		    	 atts = new AttributesImpl();
		    	 atts.addAttribute("", "nbPredicates", "nbPredicates", "nbPredicates",Predicates.size()+"" );
				 th.startElement("","","predicates",atts);
				
				 
				 
				 for(int i=0;i<Predicates.size();i++) {
					 atts = new AttributesImpl();
					 atts.addAttribute("", "name", "name", "name", Predicates.get(i)+"");
					 th.startElement("","","predicate",atts);
					
					 th.startElement("","","parameters",null);
					 th.characters(("int X1 int X2").toCharArray(),0,("int X1 int X2").length());
					 th.endElement("","","parameters");
					 
					 th.startElement("","","expression",null);
					 th.startElement("","","functional",null);
					
					 th.characters((Predicates.get(i)+"(X1, X2)").toCharArray(),0,(Predicates.get(i)+"(X1, X2)").length());

					 th.endElement("","","fuctional");
					 th.endElement("","","expression");
					 
					 th.endElement("","","predicate");
	
				  }
			     
				 th.endElement("","","predicates");
				 th.characters("\n".toCharArray(),0,1);
		    
		    } catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		
	}

	private void getContraints() throws SAXException, IOException {
		
		   try {
			   inCtr = new BufferedReader(new FileReader(PATH_TO_DATA+FOLDER+"ctr.txt"));
		       
			   	atts = new AttributesImpl();
			    atts.addAttribute("", "nbConstraints", "nbConstraints", "nbConstraints", numberOfLines("ctr.txt"));
			    th.startElement("","","constraints",atts); 
			    
			    String line;
			    
			    while ((line = inCtr.readLine()) != null) {
					  readContraints(line);
				      }
			      inCtr.close();
			      th.endElement("","","constraints");
			      th.characters("\n".toCharArray(),0,1);
		       
		   } catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}

	}

	private void readContraints(String line) throws SAXException {
		
		 String [] elements = line.trim().split("\\s+");
		  atts.clear();

		  atts.addAttribute("", "name", "name", "name", "var_"+elements[0].trim()+"_AND_"+"var_"+elements[1].trim());
		  atts.addAttribute("", "airity", "airity", "airity", "2");
		  atts.addAttribute("", "scope", "scope", "scope", "var_"+elements[0].trim()+" "+"var_"+elements[1].trim());
		  atts.addAttribute("", "reference", "reference", "reference", elements[2]);

		  if(!Predicates.contains(elements[2]))
			  Predicates.add(elements[2]);
		  
		  th.startElement("","","contraint",atts);
		  th.startElement("","","parameters",null);
		  
		  th.characters(("var_"+elements[0]).toCharArray(),0,("var_"+elements[0]).length());
		  th.characters(" ".toCharArray(),0,1);
		  th.characters(("var_"+elements[1]).toCharArray(),0,("var_"+elements[1]).length());

		  th.endElement("","","parameters");
		  th.endElement("","","contraint");
		  
	}

	private void getVariables()  {
		
			try {
				
				   inVar = new BufferedReader(new FileReader(PATH_TO_DATA+FOLDER+"var.txt"));
					
				    atts = new AttributesImpl();
				    atts.addAttribute("", "nbVariables", "nbVariables", "nbVariables", numberOfLines("var.txt"));
				    th.startElement("","","variables",atts); 
				    
				    
				    String line;
				    
				    while ((line = inVar.readLine()) != null) {
						  readVariables(line);
					      }
				      inVar.close();
				      th.endElement("","","variables");
				      th.characters("\n".toCharArray(),0,1);
		    
			}catch(Exception e) {
				  e.printStackTrace();
			  }
	}

	private void readVariables(String line) throws SAXException {
		  String [] elements = line.split("   ");
		  atts.clear();
		  atts.addAttribute("", "name", "name", "name", "var_"+elements[0].trim());
		  atts.addAttribute("", "domain", "domain", "domain", "domain_"+elements[1].trim());
		  atts.addAttribute("", "agent", "agent", "agent", "agent_"+elements[0].trim());

		  th.startElement("","","variable",atts);
		  th.endElement("","","variable");
		
	}

	void getDomains() {
		  try {
				// Open the dom.xml file to build domains
				inDom = new BufferedReader(new FileReader(PATH_TO_DATA+FOLDER+"dom.txt"));
						
				String line;
				 
				    atts = new AttributesImpl();
				    atts.addAttribute("", "nbDomains", "nbDomains", "nbDomains", numberOfLines("dom.txt"));
				    th.startElement("","","domains",atts); 
				    
				  while ((line = inDom.readLine()) != null) {
					  readDomain(line);
				      }
				  inDom.close();
			      th.endElement("","","domains");
			      th.characters("\n".toCharArray(),0,1);
			      
		  }catch(Exception e) {
			  e.printStackTrace();
		  }
	  }
	  void getAgents() {
		  try {
				// Open the var.xml file to build agents
				inVar = new BufferedReader(new FileReader(PATH_TO_DATA+FOLDER+"\\var.txt"));
				// The output XML file
				out = new StreamResult(new File(PATH_TO_SAVE_DATA+FOLDER_TO_SAVE_FILE+"/"+FOLDER_TO_SAVE_FILE+".xml"));
	            // System.out.println(out.toString());
				 init();
				 String line;
				 
			      while ((line = inVar.readLine()) != null) {
			         readAgent(line);
			      }
			      inVar.close();
			      th.endElement("","","agents");
			      th.characters("\n".toCharArray(),0,1);
			     
			      
				
			}catch(Exception e) {
				 e.printStackTrace();
			}
	  }
	  
	private void readAgent(String line) throws SAXException {
		 String [] elements = line.split("   ");
		  atts.clear();
		  atts.addAttribute("", "name", "name", "name", "agent_"+elements[0].trim());
		  th.startElement("","","agent",atts);
		  th.endElement("","","agent");
		
	}

	private void readDomain(String line) throws SAXException {
		 String [] elements = line.trim().split("\\s+");
		  atts.clear();
		  atts.addAttribute("", "name", "name", "name", "domain_"+elements[0].trim());
		  atts.addAttribute("", "nbValues", "nbValues", "nbValues", (elements.length-1)+"");
		  th.startElement("","","domain",atts);
		  for(int i=1;i<elements.length;i++) {
			  th.characters(elements[i].toCharArray(),0,elements[i].length());
			  th.characters(" ".toCharArray(),0,1);
		  }
		  th.endElement("","","domain");
		
	}


	private void init() throws ParserConfigurationException,
    TransformerConfigurationException, SAXException, IOException  {
		
		 SAXTransformerFactory SAXTransformer = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
		    th = SAXTransformer.newTransformerHandler();
		    
		    Transformer serializer = th.getTransformer();
		    //Encodage of the XML File
		    serializer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
		    // Indent the XML File
		    serializer.setOutputProperty
	        ("{http://xml.apache.org/xslt}indent-amount", "4");
		    serializer.setOutputProperty(OutputKeys.INDENT,"yes");
		    serializer.setOutputProperty(OutputKeys.STANDALONE, "yes"); 

		    
		    th.setResult(out);
		    th.startDocument();
		    atts = new AttributesImpl();
		    atts.addAttribute("", "nbAgents", "nbAgents", "nbAgents", numberOfLines("var.txt"));
		    th.startElement("","","agents",atts);    

	}

	public static String numberOfLines(String file) throws IOException {
		  BufferedReader  inn = new BufferedReader(new FileReader(PATH_TO_DATA+FOLDER+file));
		 String line;
		 int nbLines=0;
	      while ((line = inn.readLine()) != null) {
	         nbLines++;
	      }
	      inn.close();
	      return ""+nbLines;
	}
	

	 public void closeDocument() throws SAXException {
		    th.endDocument(); 
		}
}
