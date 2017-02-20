package Driverlayor;

import java.io.*;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.util.ArrayList;


public class IOfile {
	static List<String>  mainxmlPath=new ArrayList<String>();	
	
	//创建一个XML文件
	/*
	 * 参数1：文件名称
	 * 参数2:String对象的文件内容
	 */
	public void WriteAXmlFile(String FileName,String strData)
	{
		SAXReader saxReader=new SAXReader();
		Document document=null;
		try {
			
			document = saxReader.read(new ByteArrayInputStream(strData.getBytes("UTF-8")));
			FileOutputStream outStream = new FileOutputStream(FileName);
			OutputFormat outputFormat=OutputFormat.createPrettyPrint();
			outputFormat.setEncoding("UTF-8");
			outputFormat.setNewlines(true);
			XMLWriter xmlWriter=new XMLWriter(outStream,outputFormat);
		
			xmlWriter.write(document);
			xmlWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static String Readfile(String filename) throws IOException{
	
	File f=new File(filename);
	
		FileInputStream fip=new FileInputStream(f);
		InputStreamReader reader=new InputStreamReader(fip,"UTF-8");
		StringBuffer sb=new StringBuffer();
		while(reader.ready())
		{
			sb.append((char)reader.read());
		}
		
	reader.close();
	fip.close();
	
	return sb.toString();
	
		
	} 
	public static void Writefile(String writestr,String filename)throws IOException {
		File f=new File(filename);
		FileOutputStream fop=new FileOutputStream(f);
		OutputStreamWriter Writer=new OutputStreamWriter(fop,"UTF-8");
		Writer.append(writestr);
		
		Writer.close();
		fop.close();
	}
	
	
	 public static List<String> GetAllMainxmlPath(String path,String limit){
			String mainname=limit;
			
			File file = new File(path);
	        if (file.exists()) {
	            File[] files = file.listFiles();
	            if (files.length == 0) {
	              //  System.out.println("文件夹是空的!");
	               // return mainxmlPath;
	            } else {
	                for (File file2 : files) {
	                    if (file2.isDirectory()) {
	                      //  System.out.println("文件夹:" + file2.getAbsolutePath());
	                        GetAllMainxmlPath(file2.getAbsolutePath(),limit);
	                    } 
	                    else {
	                
	                    	if(mainname.equals(file2.getName()))
	                    	{
	                    		
	                    		String temp1;
	                    		temp1=file2.getAbsolutePath().replace("\\"+limit,"");
	                    		temp1=temp1.replace("\\","/");
	                    		//System.out.println(temp1);
	                    		mainxmlPath.add(temp1);
	                    		
	                    	}
	                    }
	                }
	            }
	        } else {
	            System.out.println("文件不存在!");
	        
	        }
			
			return mainxmlPath;
		}
		
	
	public static void main(String[] args){
		
		List<GetAllFilePathClass> temp=new ArrayList<GetAllFilePathClass>();
		GetAllFilePathClass temp1=new GetAllFilePathClass();
		temp1.GetAllFilePath("test");
		
	}
}

//根据路径遍历该路径下边的所有指定文件
class GetAllMainxmlPathClass{
	
	
	 
	
	
}



class GetAllFilePathClass{
	
	private List<String>  FilexmlPath=new ArrayList<String>();
	 
	 public List<String> GetAllFilePath(String path){
			
			
			File file = new File(path);
	        if (file.exists()) {
	            File[] files = file.listFiles();
	            if (files.length == 0) {
	              //  System.out.println("文件夹是空的!");
	                return FilexmlPath;
	            } else {
	                for (File file2 : files) {
	                    if (file2.isDirectory()) {
	                      //  System.out.println("文件夹:" + file2.getAbsolutePath());
	                    	GetAllFilePath(file2.getAbsolutePath());
	                    } 
	                    else {
	                
	                    	
	                    	//System.out.println("文件:" + file2.getAbsolutePath());
	                    	FilexmlPath.add(file2.getAbsolutePath());
	                    	
	                    }
	                }
	            }
	        } else {
	            System.out.println("文件不存在!");
	        
	        }
			
			return FilexmlPath;
		}
		
	
}
