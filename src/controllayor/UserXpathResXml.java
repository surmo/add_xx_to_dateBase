package controllayor;

import java.io.IOException;  
import java.io.InputStream;  

import javax.xml.parsers.DocumentBuilder;  
import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.ParserConfigurationException;  
import javax.xml.xpath.XPath;  
import javax.xml.xpath.XPathConstants;  
import javax.xml.xpath.XPathExpression;  
import javax.xml.xpath.XPathExpressionException;  
import javax.xml.xpath.XPathFactory;  

import org.w3c.dom.Document;  
import org.w3c.dom.NodeList;  
import org.xml.sax.SAXException;  

public class UserXpathResXml {
	 public static void main(String[] args) {  
	        read();  
	    }  
	      
	    public static void read() {  
	        try {  
	            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
	            DocumentBuilder builder = dbf.newDocumentBuilder();  
	            InputStream in = UserXpathResXml.class.getClassLoader().getResourceAsStream("test.xml");  
	            Document doc = builder.parse(in);  
	            XPathFactory factory = XPathFactory.newInstance();  
	            XPath xpath = factory.newXPath();  
	            // 选取所有class元素的name属性  
	            // XPath语法介绍： http://w3school.com.cn/xpath/  
	            XPathExpression expr = xpath.compile("//data_stream/*");  
	            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);  
	            for (int i = 0; i < nodes.getLength(); i++) {  
	                System.out.println(nodes.item(i).getNodeName()+"->" +nodes.item(i).getTextContent());  
	                    }  
	        } catch (XPathExpressionException e) {  
	            e.printStackTrace();  
	        } catch (ParserConfigurationException e) {  
	            e.printStackTrace();  
	        } catch (SAXException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	    }  
}
