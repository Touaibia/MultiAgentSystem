package convertToXml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import convertToXml.Functions;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class MainClass {

	

	  public static void main (String args[]) throws IOException, SAXException  {
	      new Functions().GetXml();
	    
	  }

	
	
}
