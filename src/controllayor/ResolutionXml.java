package controllayor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextArea;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import Driverlayor.CUDBtoDateBase;
import Driverlayor.DataStructXml;
import Driverlayor.IOfile;



public class ResolutionXml {  
	 
	JTextArea showinfo;//����һ��ȫ�ֵ��ı���
	
	
	public void showInit(JTextArea data)//���ı������ֵ
	{
		showinfo=data;
	}
	
    public static void main(String[] args) {  
    	
        
    	List<String> mainpath=IOfile.GetAllMainxmlPath("C:/Users/Sumo/Desktop/we","Main.xml");
    	System.out.println("һ��"+mainpath.size()+"��ϵͳ��");
    	for(int i=0;i<mainpath.size();i++)
    	{
    		System.out.println("��ʼ������"+i+"��ϵͳ��");
    		Resolutionxmlbydir("GreatWall",mainpath.get(i));
    	}
    	

    }   
    
    /*
     * �����ļ���·������XML
     */
    /**
     * ����·������xml�ĵ�    �������Ŀ¼��ߵ�������Ŀ¼����main.xml��Ϊ�жϱ�׼
     * @param CarTable   �ó��͵����ݱ����ƣ����������ݻᱣ�������ݱ���
     * @param pathdir    �ó��͵��ļ�·��
     */
    public void Resolutionxmlbypath(String CarTable,String pathdir){
    	pathdir=pathdir.replace("\\", "/");//�滻·����б��
    	System.out.println("����ͳ��ϵͳ������...");
    	showinfo.append("����ͳ��ϵͳ������...\n");//��ʾ��UI�ı���
    	//��ȡ���� Main.xml ���ļ��е�·������������
    	List<String> mainpath=IOfile.GetAllMainxmlPath(pathdir,"Main.xml");
    	System.out.println("һ��"+mainpath.size()+"��ϵͳ��");
    	showinfo.append("һ��"+mainpath.size()+"��ϵͳ��\n");
    	//��ʼ����·������ϵͳ����������ϵͳΪ��λ���н����ˣ���Ϊÿ��ϵͳֻ��һ��main.xml
    	for(int i=0;i<mainpath.size();i++)
    	{
    		System.out.println("��ʼ������"+i+"��ϵͳ��");
    		showinfo.append("��ʼ������"+i+"��ϵͳ��\n");
    		Resolutionxmlbydir(CarTable,mainpath.get(i));//����
    	}
    	showinfo.append("������ɣ�");
    }
    
    /*
     * ����main.xml����ϵͳ������·��ֻ��ָ������Ŀ¼��Ҳ���ǰ���
     * main.xml
     *command.xml
     *���ļ���Ŀ¼
     */
    /**
     * ����һ��ϵͳ�����ļ������ǰ���main.xml���ļ���
     * ����֮��ƥ��StrTable��ߵ�����
     * ����֮��ƥ��command��ߵ�����
     * @param CarTable  �ó��͵����ݱ����ƣ����������ݻᱣ�������ݱ���
     * @param sysdir   ϵͳ��·��
     */
 static   public void Resolutionxmlbydir(String CarTable,String sysdir)
    {
	 	CUDBtoDateBase dbtemp=new CUDBtoDateBase();//��ȡ���ݿ�����
	 	System.out.println("��ʼ����:"+sysdir);
	 	
    	if(StartResolutionXml(sysdir+"/DataStream.xml"))//��ʼ����,�����������ݱ�����ȫ�ֱ�����   �����ɹ��᷵��true
    	{
    		MatchChinaStr(sysdir+"/StrTable.txt");//ƥ��StrTable�ļ�����ȡ��Ӧ����������
    		MatchCommand(sysdir+"/Command.xml");//ƥ��command.xml�ļ�  ��ȡ��Ӧ������
    		
    		dbtemp.AddXMlListdataStream(CarTable,C_global_data.DatastreamList);//�������ݿ�
    		
    		System.out.println("������ɣ�");
    	
    		System.out.println();
    	}
    	else//����ʧ��
    	{
    		System.out.println("����ʧ��!"+"    ϵͳ:"+sysdir);
    		System.out.println();
    	}
    	/*
    	 * ��ϵͳΪ��λ���н���
    	 * ������һ��ϵͳ֮��
    	 * Ҫ�����Щ�õ���ȫ�ֱ���
    	 */
    	C_global_data.CommandList.clear();
    	C_global_data.DatastreamList.clear();
    	C_global_data.StrTableList.clear();
    	C_global_data.captionList.clear();
    	
    	System.gc();//�����������ջ���
    	
    	
    }
    
   
 /**
  * ����һ��ϵͳ����ȡ����������Ϣ���������õ���command��ID��caption  ���ﲢû�п�ʼƥ��
  * @param filepath  ϵͳ��·��
  * @return  ִ�����
  */
    private  static boolean StartResolutionXml(String filepath) {
    	int streamindex=0;
    	boolean RunningState=true;
    	try{
    		
    		SAXReader reader1=new SAXReader();
    		
    		FileInputStream in=new FileInputStream(new File(filepath));
            Document doc = reader1.read(in); 
            Element root =doc.getRootElement();//��ȡXML�ļ��ĸ��ڵ�
          
            
            Element e=root.element("data_stream_lib");//�ҵ�data_stream_lib�ڵ�
          
            if(e!=null)//�ж��Ƿ�����������ǩ
            {	
            	Iterator<Element> it=e.elementIterator(); //��ȡdata_stream_lib����

            	while(it.hasNext())//����data_stream_lib��ߵ����ݣ�Ҳ���Ǳ���group
            	{
            		e=it.next();
            		if(e.getName().equals("group"))//data_stream_lib��ǩ��ߵı�����group,�����Ǵ��
            		{
            			Iterator<Element> it1=e.elementIterator();//��ȡgroup ��ߵļ���
            			while(it1.hasNext())//����data_stream
            			{
            				e=it1.next();
            				//System.out.println(e.getName()+"  "+e.attributeValue("ID"));
            				savenode(e,streamindex,filepath);//������������ǩ
            				//System.out.println("BF"+streamindex);
            				streamindex++;//������־
            			}
            		}
            		else 
            		{
            			System.out.println("data_stream_lib  ��߲���group��ǩ");
            		}
            
            	}
            
            }
            else //����������ļ���û��data_stream_lib��ǩ
            {
				System.out.println("·��:"+filepath+"������������ǩ");
				RunningState=false;
			}
    	}
    	catch(DocumentException e)
    	{
    		System.out.println(e);
    		RunningState=false;
    	}catch(IOException c)
    	{
    		System.out.println(c);
    		RunningState=false;
    	}
		
    	return RunningState;
	}
    
   
    
   
   /**
    * ����  data_stream�ڵ㣬�����������ָ��data_stream
    * @param node ָ���������Ľڵ�
    * @param datastreamindex  Ҫ�����������������,ÿһ����������������Ψһ�ģ��������� caption��ֵ  
    * @param filepath  ������������ϵͳ��·��
    */
    private static void savenode(Element node,int datastreamindex,String filepath) {
    	 DataStructXml listtemp=new DataStructXml();//�������������͵Ļ���
    	 Sys_caption captiontemp=new Sys_caption();  //���������������ʱ����Switch��Caption��Ϣ����������Ӧ������
    	try
    	{
    	 String switchID;  //Switch��ID
    	
    	 Iterator<Element> collet=node.elementIterator();//data_stream�ڵ�ļ���
    	 
    	 listtemp.DataStream=node.asXML();//������data_stream��������������Ҫ����xml��ʱ��ֱ��ʹ��
    	 //��ȡ��������������
    	 listtemp.datastreamname=node.attributeValue("caption");
    	 //�����������Ƶ�captionֵ��������
    	 captiontemp.caption=node.attributeValue("caption");//���������������Ƶ�Captionֵ
    	 captiontemp.datastreamid=datastreamindex;//Caption��Ӧ������������
    	 
    	// System.out.println(node.getName()+"->"+node.attributeValue("caption"));
    	 
    	 Element e=collet.next();//command
    	 listtemp.command_id=e.getText();
    	// System.out.println(e.getName()+"->"+e.getText());
    	 
    	 e=collet.next();  //valid_byte_offset
    	 listtemp.byteoffset=e.getText();
    	 //System.out.println(e.getName()+"->"+e.getText());
    	 
    	 e=collet.next(); //valid_byte_number
    	 listtemp.bytenum=e.getText();
    	 //System.out.println(e.getName()+"->"+e.getText());
    	
    	 e=collet.next();
    	 Element c=e.element("formula");
    	 listtemp.formula=c.getText();
    
    	 //System.out.println("ADD"+datastreamindex);
    	
    	 C_global_data.DatastreamList.add(datastreamindex,listtemp);//���������
    	 C_global_data.captionList.add(captiontemp);//�������������ƣ�����ͳһ���л�ȡ
    	 
    	 if(c.getText().startsWith("switch"))//�����ʽ�� ����Switch
    	 {
    		switchID=c.getText().substring(c.getText().indexOf('(')+1, c.getText().indexOf(','));//������switch��ID
    		MatchSwitch(switchID,datastreamindex,filepath);//����Switch��IDѰ�Ҹ�Switch������Caption��Ϣ
    	 }
    	 }
    	catch(Exception e)
    	{
    		 C_global_data.DatastreamList.add(datastreamindex,listtemp);//�����ȡ����ĳ���ڵ������������֮��Ҳ����Ҫ�洢��
        	 C_global_data.captionList.add(captiontemp);//�������������ƣ�����ͳһ���л�ȡ
    		
        	 e.printStackTrace();
    		System.out.println(e);
    	}
	}
    
    
    /**
     * ����StrTable�ļ���ƥ������������������
     * @param filepath
     */
    private  static void  MatchChinaStr(String filepath)
     {
    	 try {
    		 String Strtable=IOfile.Readfile(filepath);
    		 String pattern="((.+)(\\t)(.+)(\\t)(.+)[\\r\\n]{1,2})";
    			Pattern patt=Pattern.compile(pattern);
    			Matcher result=patt.matcher(Strtable);
    			//��ȡ��StrTable���е�����
    			while(result.find())
    			{
    				StrTableStruct temp=new StrTableStruct();
    				temp.AllStr=result.group(0);
    				temp.ChinaStr=result.group(4);
    				temp.EnlishStr=result.group(6);
    				temp.Caption=result.group(2);
    				C_global_data.StrTableList.add(temp); //��ӵ�һ���ṹ������
//    				for(int i=0;i<result.groupCount();i++)
//    				{
//    					System.out.print(i+"->"+result.group(i));
//    				}
//    				
    			}
    			
    			//ƥ�����������Ʋ��ұ��浽����������
    			for(int i=0;i<C_global_data.DatastreamList.size();i++)
    			{
    				for(int j=0;j<C_global_data.StrTableList.size();j++)
    				{
    					if(C_global_data.DatastreamList.get(i).datastreamname.equals(C_global_data.StrTableList.get(j).Caption))
    					{
    						C_global_data.DatastreamList.get(i).datastreamname=C_global_data.StrTableList.get(j).ChinaStr;
    						break;
    					}

    				}
    			}
    			/*
    			 * ƥ��ȫ����Caption���ݣ�����������ƥ��
    			 * ��������Strtable
    			 */
    			for(int i=0;i<C_global_data.captionList.size();i++)//���е�Caption����
    			{
    				
    				for(int j=0;j<C_global_data.StrTableList.size();j++)//Strtable����������
    				{
    					//�����������ߵ�Captionֵ��Strtable��ߵ�Caption���
    					if(C_global_data.captionList.get(i).caption.equals(C_global_data.StrTableList.get(j).Caption))
    					{
    						int dataindex=C_global_data.captionList.get(i).datastreamid;//��ȡCaption������һ��������
    						String strtemp=C_global_data.StrTableList.get(j).AllStr;//��ȡ���Caption��Ӧ�������ݣ��������з�
    						//System.out.println("tt"+dataindex);
    						C_global_data.DatastreamList.get(dataindex).CaptionList.append(strtemp);
    						break;
    					}
    				}
    			}
    		 
//    			for (DataStructXml dataStructXml : C_global_data.DatastreamList) {
//    				System.out.println("CDM:"+dataStructXml.command_id+"Name:"+dataStructXml.datastreamname);
// 					
// 				}
    			
    			
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
    	 
    			 
    			 
     }
    
    
    
    
    /**
     * ƥ��command�������
     * @param commandpath
     */

    private  static void MatchCommand(String commandpath){
    	try {
			String commandStr=IOfile.Readfile(commandpath);
			//System.out.println(commandStr);
			String pattern="<command\\s+ID=\"([0-9A-Za-z_]+)\".*>(0x.+)</command>";
			Pattern patt=Pattern.compile(pattern);
			Matcher result=patt.matcher(commandStr);
			
			while(result.find())
			{
				command_Data commandtemp=new command_Data();
				commandtemp.commandString=result.group(0);
				commandtemp.Command_id=result.group(1);
				commandtemp.command=result.group(2);
//				for(int i=0;i<result.groupCount();i++)
//   				{
//    					System.out.println(i+"->"+result.group(i));
//   				}
				C_global_data.CommandList.add(commandtemp);
			}
			
			for(int i=0;i<C_global_data.DatastreamList.size();i++)
			{
				for(int j=0;j<C_global_data.CommandList.size();j++)
				{
					if(C_global_data.DatastreamList.get(i).command_id.equals(C_global_data.CommandList.get(j).Command_id))
					{
						C_global_data.DatastreamList.get(i).command=C_global_data.CommandList.get(j).command;
						C_global_data.DatastreamList.get(i).command_no_0x=cut0xfromcommand(C_global_data.CommandList.get(j).command);
						break;
					}
				}
			}
		 
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
    //ȥ�������е�0x  ����Ա�
    private  static String cut0xfromcommand(String command ){
    	String temp;
    	temp=command.replace("0x","");
    	temp=temp.replace("0X", "");
    	temp=temp.replace(",", "");
    	
    	return temp;
    }
    
    
    
    
    
    
    
    //����switch
    /*
     *����switch��ID�͵�ǰ�������ݵ������� 
     * �ҵ���Ӧ��switch�󣬽�����switch���뵱ǰ������
     * �����Ե�ǰ������������Ϊ��־����������������õ�������
     * caption����
     */
    /**
     * Ѱ��Switch����Ϣ��������Switch�������������ҽ�switch���case �õ���caption��ȡ����
     * @param ID  switch��ID
     * @param datastreamindex  ��ǰ��������Ψһ������
     * @param filepath  
     */
    private static void MatchSwitch(String ID,int datastreamindex,String filepath){
    	boolean findswitch=false;
    	try{
    		SAXReader reader1=new SAXReader();
    		FileInputStream in=new FileInputStream(new File(filepath));
            Document doc = reader1.read(in); 
            Element root =doc.getRootElement();
            Element e = root.element("switchs");
           if(e==null)
           {
        	   System.out.println("�Ҳ���Switch��Ϣ����ȷ��Switch��Ϣ��DataStream.xml��");
        	   return;
           }
            Iterator<Element> it=e.elementIterator();  //��ȡ����Switch�ļ���
            
            while(it.hasNext())
            	{
            		e=it.next();
            		if(ID.equals(e.attributeValue("ID")))  //�ҵ�switch ��ID
            			{
            				C_global_data.DatastreamList.get(datastreamindex).switchStr=e.asXML();
            				findswitch=true;
            				break;
            			}
            		
            	}
            
            //�����������������Switch��Ϣ
            if(findswitch)
            {
            	it=e.elementIterator();//��ȡ���е�case
            	while(it.hasNext())
            	{
            		Sys_caption temp3=new Sys_caption();
            		e=it.next();
            		temp3.datastreamid=datastreamindex;
            		temp3.caption=e.getText();//��ȡcase��ߵ�caption
            		//System.out.println(datastreamID);
            		//System.out.println(e.getName()+"->"+e.getText());
            		//System.out.println();
            		C_global_data.captionList.add(temp3);//��������
            	}
            	
            }
            
    		in.close();
    	}
    	catch(DocumentException e)
    	{
    		e.printStackTrace(); 
    	}catch(IOException c)
    	{
    		
    		c.printStackTrace();
    	}
    	
    }
     
     
}  
 //����Strtable�ı��Ľṹ
class StrTableStruct{	
	String ChinaStr;
	String Caption;
	String EnlishStr;
	String AllStr;
}

class command_Data{
	String commandString;  //��������
	String Command_id;   //�����ID
	String command;     //��������
	
}
//һ���������У��漰��������caption
class Sys_caption{
	int datastreamid;//������������
	String caption;//caption
	
}

class C_global_data{
	static	List<DataStructXml> DatastreamList=new ArrayList<DataStructXml>();//������
	static  List<StrTableStruct> StrTableList=new ArrayList<StrTableStruct>();
	static  List<command_Data> CommandList=new ArrayList<command_Data>();
	static  List<Sys_caption>  captionList=new ArrayList<Sys_caption>();
}



