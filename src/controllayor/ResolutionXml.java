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
	 
	JTextArea showinfo;//保存一个全局的文本框
	
	
	public void showInit(JTextArea data)//给文本框对象赋值
	{
		showinfo=data;
	}
	
    public static void main(String[] args) {  
    	
        
    	List<String> mainpath=IOfile.GetAllMainxmlPath("C:/Users/Sumo/Desktop/we","Main.xml");
    	System.out.println("一共"+mainpath.size()+"个系统。");
    	for(int i=0;i<mainpath.size();i++)
    	{
    		System.out.println("开始解析第"+i+"个系统。");
    		Resolutionxmlbydir("GreatWall",mainpath.get(i));
    	}
    	

    }   
    
    /*
     * 根据文件夹路径解析XML
     */
    /**
     * 根据路径解析xml文档    会检索该目录里边的所有子目录，以main.xml作为判断标准
     * @param CarTable   该车型的数据表名称，解析的数据会保存在数据表中
     * @param pathdir    该车型的文件路径
     */
    public void Resolutionxmlbypath(String CarTable,String pathdir){
    	pathdir=pathdir.replace("\\", "/");//替换路径的斜杠
    	System.out.println("正在统计系统总数量...");
    	showinfo.append("正在统计系统总数量...\n");//显示到UI文本框
    	//获取包含 Main.xml 的文件夹的路径，存入数组
    	List<String> mainpath=IOfile.GetAllMainxmlPath(pathdir,"Main.xml");
    	System.out.println("一共"+mainpath.size()+"个系统。");
    	showinfo.append("一共"+mainpath.size()+"个系统。\n");
    	//开始根据路径解析系统，这里是以系统为单位进行解析了，因为每个系统只有一个main.xml
    	for(int i=0;i<mainpath.size();i++)
    	{
    		System.out.println("开始解析第"+i+"个系统。");
    		showinfo.append("开始解析第"+i+"个系统。\n");
    		Resolutionxmlbydir(CarTable,mainpath.get(i));//解析
    	}
    	showinfo.append("解析完成！");
    }
    
    /*
     * 根据main.xml解析系统，这里路径只能指向最终目录，也就是包含
     * main.xml
     *command.xml
     *等文件的目录
     */
    /**
     * 解析一个系统，该文件必须是包含main.xml的文件夹
     * 解析之后匹配StrTable里边的名称
     * 解析之后匹配command里边的命令
     * @param CarTable  该车型的数据表名称，解析的数据会保存在数据表中
     * @param sysdir   系统的路径
     */
 static   public void Resolutionxmlbydir(String CarTable,String sysdir)
    {
	 	CUDBtoDateBase dbtemp=new CUDBtoDateBase();//获取数据库连接
	 	System.out.println("开始解析:"+sysdir);
	 	
    	if(StartResolutionXml(sysdir+"/DataStream.xml"))//开始解析,解析到的数据保存在全局变量中   解析成功会返回true
    	{
    		MatchChinaStr(sysdir+"/StrTable.txt");//匹配StrTable文件，获取对应的中文名称
    		MatchCommand(sysdir+"/Command.xml");//匹配command.xml文件  获取对应的命令
    		
    		dbtemp.AddXMlListdataStream(CarTable,C_global_data.DatastreamList);//加入数据库
    		
    		System.out.println("解析完成！");
    	
    		System.out.println();
    	}
    	else//解析失败
    	{
    		System.out.println("解析失败!"+"    系统:"+sysdir);
    		System.out.println();
    	}
    	/*
    	 * 以系统为单位进行解析
    	 * 解析完一个系统之后
    	 * 要清空这些用到的全局变量
    	 */
    	C_global_data.CommandList.clear();
    	C_global_data.DatastreamList.clear();
    	C_global_data.StrTableList.clear();
    	C_global_data.captionList.clear();
    	
    	System.gc();//启动垃圾回收机制
    	
    	
    }
    
   
 /**
  * 解析一个系统，提取出数据流信息，数据流用到的command的ID，caption  这里并没有开始匹配
  * @param filepath  系统的路径
  * @return  执行情况
  */
    private  static boolean StartResolutionXml(String filepath) {
    	int streamindex=0;
    	boolean RunningState=true;
    	try{
    		
    		SAXReader reader1=new SAXReader();
    		
    		FileInputStream in=new FileInputStream(new File(filepath));
            Document doc = reader1.read(in); 
            Element root =doc.getRootElement();//获取XML文件的根节点
          
            
            Element e=root.element("data_stream_lib");//找到data_stream_lib节点
          
            if(e!=null)//判断是否有数据流标签
            {	
            	Iterator<Element> it=e.elementIterator(); //获取data_stream_lib集合

            	while(it.hasNext())//遍历data_stream_lib里边的数据，也就是遍历group
            	{
            		e=it.next();
            		if(e.getName().equals("group"))//data_stream_lib标签里边的必须是group,否则是错的
            		{
            			Iterator<Element> it1=e.elementIterator();//获取group 里边的集合
            			while(it1.hasNext())//遍历data_stream
            			{
            				e=it1.next();
            				//System.out.println(e.getName()+"  "+e.attributeValue("ID"));
            				savenode(e,streamindex,filepath);//解析数据流标签
            				//System.out.println("BF"+streamindex);
            				streamindex++;//索引标志
            			}
            		}
            		else 
            		{
            			System.out.println("data_stream_lib  里边不是group标签");
            		}
            
            	}
            
            }
            else //如果数据流文件中没有data_stream_lib标签
            {
				System.out.println("路径:"+filepath+"中无数据流标签");
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
    * 遍历  data_stream节点，传入参数必须指向data_stream
    * @param node 指向数据流的节点
    * @param datastreamindex  要保存的数据流的索引,每一条数据流的索引是唯一的，用来关联 caption的值  
    * @param filepath  现在所解析的系统额路径
    */
    private static void savenode(Element node,int datastreamindex,String filepath) {
    	 DataStructXml listtemp=new DataStructXml();//定义数据流类型的缓存
    	 Sys_caption captiontemp=new Sys_caption();  //定义变量，用来暂时保存Switch的Caption信息和数据流对应的索引
    	try
    	{
    	 String switchID;  //Switch的ID
    	
    	 Iterator<Element> collet=node.elementIterator();//data_stream节点的集合
    	 
    	 listtemp.DataStream=node.asXML();//将整个data_stream保存起来，后续要导出xml的时候直接使用
    	 //获取数据流名称属性
    	 listtemp.datastreamname=node.attributeValue("caption");
    	 //将数据流名称的caption值保存起来
    	 captiontemp.caption=node.attributeValue("caption");//保存数据流的名称的Caption值
    	 captiontemp.datastreamid=datastreamindex;//Caption对应的数据流索引
    	 
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
    	
    	 C_global_data.DatastreamList.add(datastreamindex,listtemp);//添加数据流
    	 C_global_data.captionList.add(captiontemp);//保存数据流名称，后面统一进行获取
    	 
    	 if(c.getText().startsWith("switch"))//如果公式中 含有Switch
    	 {
    		switchID=c.getText().substring(c.getText().indexOf('(')+1, c.getText().indexOf(','));//解析出switch的ID
    		MatchSwitch(switchID,datastreamindex,filepath);//根据Switch的ID寻找该Switch包含的Caption信息
    	 }
    	 }
    	catch(Exception e)
    	{
    		 C_global_data.DatastreamList.add(datastreamindex,listtemp);//如果读取到了某个节点出错跳到这里之后，也是需要存储的
        	 C_global_data.captionList.add(captiontemp);//保存数据流名称，后面统一进行获取
    		
        	 e.printStackTrace();
    		System.out.println(e);
    	}
	}
    
    
    /**
     * 解析StrTable文件，匹配数据流的中文名称
     * @param filepath
     */
    private  static void  MatchChinaStr(String filepath)
     {
    	 try {
    		 String Strtable=IOfile.Readfile(filepath);
    		 String pattern="((.+)(\\t)(.+)(\\t)(.+)[\\r\\n]{1,2})";
    			Pattern patt=Pattern.compile(pattern);
    			Matcher result=patt.matcher(Strtable);
    			//截取出StrTable所有的行数
    			while(result.find())
    			{
    				StrTableStruct temp=new StrTableStruct();
    				temp.AllStr=result.group(0);
    				temp.ChinaStr=result.group(4);
    				temp.EnlishStr=result.group(6);
    				temp.Caption=result.group(2);
    				C_global_data.StrTableList.add(temp); //添加到一个结构体数组
//    				for(int i=0;i<result.groupCount();i++)
//    				{
//    					System.out.print(i+"->"+result.group(i));
//    				}
//    				
    			}
    			
    			//匹配数据流名称并且保存到单条数据流
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
    			 * 匹配全部的Caption数据，这里是整条匹配
    			 * 用来生成Strtable
    			 */
    			for(int i=0;i<C_global_data.captionList.size();i++)//所有的Caption链表
    			{
    				
    				for(int j=0;j<C_global_data.StrTableList.size();j++)//Strtable的所有数据
    				{
    					//如果数据流里边的Caption值与Strtable里边的Caption相等
    					if(C_global_data.captionList.get(i).caption.equals(C_global_data.StrTableList.get(j).Caption))
    					{
    						int dataindex=C_global_data.captionList.get(i).datastreamid;//获取Caption属于哪一条数据流
    						String strtemp=C_global_data.StrTableList.get(j).AllStr;//获取这个Caption对应整条数据，包括换行符
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
     * 匹配command表的命令
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
    
    //去掉命令中的0x  方便对比
    private  static String cut0xfromcommand(String command ){
    	String temp;
    	temp=command.replace("0x","");
    	temp=temp.replace("0X", "");
    	temp=temp.replace(",", "");
    	
    	return temp;
    }
    
    
    
    
    
    
    
    //解析switch
    /*
     *传入switch的ID和当前解析数据的索引号 
     * 找到对应的switch后，将整个switch存入当前数据流
     * 并且以当前数据流索引作为标志，保存该条数据流用到的所有
     * caption数据
     */
    /**
     * 寻找Switch的信息，将整个Switch保存起来，并且将switch里边case 用到的caption提取出来
     * @param ID  switch的ID
     * @param datastreamindex  当前数据流的唯一索引号
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
        	   System.out.println("找不到Switch信息，请确认Switch信息在DataStream.xml中");
        	   return;
           }
            Iterator<Element> it=e.elementIterator();  //获取所有Switch的集合
            
            while(it.hasNext())
            	{
            		e=it.next();
            		if(ID.equals(e.attributeValue("ID")))  //找到switch 的ID
            			{
            				C_global_data.DatastreamList.get(datastreamindex).switchStr=e.asXML();
            				findswitch=true;
            				break;
            			}
            		
            	}
            
            //如果单条数据流中有Switch信息
            if(findswitch)
            {
            	it=e.elementIterator();//获取所有的case
            	while(it.hasNext())
            	{
            		Sys_caption temp3=new Sys_caption();
            		e=it.next();
            		temp3.datastreamid=datastreamindex;
            		temp3.caption=e.getText();//获取case里边的caption
            		//System.out.println(datastreamID);
            		//System.out.println(e.getName()+"->"+e.getText());
            		//System.out.println();
            		C_global_data.captionList.add(temp3);//保存起来
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
 //定义Strtable文本的结构
class StrTableStruct{	
	String ChinaStr;
	String Caption;
	String EnlishStr;
	String AllStr;
}

class command_Data{
	String commandString;  //整条命令
	String Command_id;   //命令的ID
	String command;     //命令内容
	
}
//一条数据流中，涉及到的所有caption
class Sys_caption{
	int datastreamid;//数据流的索引
	String caption;//caption
	
}

class C_global_data{
	static	List<DataStructXml> DatastreamList=new ArrayList<DataStructXml>();//数据流
	static  List<StrTableStruct> StrTableList=new ArrayList<StrTableStruct>();
	static  List<command_Data> CommandList=new ArrayList<command_Data>();
	static  List<Sys_caption>  captionList=new ArrayList<Sys_caption>();
}



