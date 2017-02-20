package ExportData;

import java.util.List;

import com.sun.org.apache.bcel.internal.generic.NEW;

import Driverlayor.DataStructXml;
import Driverlayor.IOfile;
import GUIllayor.inpudata;
import facetest.SelectSearchResul;
import facetest.facetest1.*;


public class Exportfile {
	
	 public boolean Export(List<DataStructXml> ExportContain){
		 ExportDataTemp exportDataTemp=new ExportDataTemp();
		 IOfile iOfile=new IOfile();
		 ConstString constString=new ConstString();
		for(int i=0;i<ExportContain.size();i++)//将选中的数据流，统一整理ID CAption 以防重复
		{
			EditorRepeatDataStreamID editorRepeatDataStreamID=new EditorRepeatDataStreamID();
			editorRepeatDataStreamID.FormatWithID(i,ExportContain.get(i));
		}

		EditorRepeatDataStreamID editorRepeatDataStreamID=new EditorRepeatDataStreamID();
		editorRepeatDataStreamID.RexSwitchformula(ExportContain);//解析Switch的公式
		
		//将每条数据流的同一种信息统一到一个变量中，准备写入文件
		int index=0;
		for (DataStructXml dataStructXml : ExportContain) {
			exportDataTemp.CaptionBuffer.append(dataStructXml.CaptionList);
			exportDataTemp.dataStreamBuffer.append(dataStructXml.DataStream);
			if((dataStructXml.switchStr!=null)&&(dataStructXml.switchStr.length()>5))
			{
				exportDataTemp.SwitchBuffer.append(dataStructXml.switchStr);
			}
			
			
			exportDataTemp.CommandBuffer.append(dataStructXml.command_xml);
			
			//以下是生成协议使用
			exportDataTemp.protocolCommand.append("REQ"+((index<10)?"0"+index:index)+":"+dataStructXml.command.replaceAll(",{0,1}0[Xx]{1}", " ")+"\n");
			exportDataTemp.protocolstream.append(AlignDataStreamName.AlignData(index,dataStructXml));
			index++;
		}
		//开始写入文件
		try {
			iOfile.WriteAXmlFile("DataStream.xml",constString.AddDataStreamInfo(exportDataTemp.dataStreamBuffer.toString(), exportDataTemp.SwitchBuffer.toString()));
			iOfile.WriteAXmlFile("command.xml", constString.AddCommandInfo(exportDataTemp.CommandBuffer.toString()));
			iOfile.WriteAXmlFile("Main.xml",constString.AddmaninInfo(null));
			iOfile.Writefile(exportDataTemp.CaptionBuffer.toString(), "STrTable.txt");
			
			iOfile.Writefile(exportDataTemp.protocolCommand.toString()+exportDataTemp.protocolstream.toString(), "Protocol.asm");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	 
	 
	 
	 public boolean Export(List<DataStructXml> ExportContain,List<String> chinalist){
		 ExportDataTemp exportDataTemp=new ExportDataTemp();
		 IOfile iOfile=new IOfile();
		 ConstString constString=new ConstString();
		for(int i=0;i<ExportContain.size();i++)//将选中的数据流，统一整理ID CAption 以防重复
		{
			EditorRepeatDataStreamID editorRepeatDataStreamID=new EditorRepeatDataStreamID();
			editorRepeatDataStreamID.FormatWithID(i,ExportContain.get(i));
		}

		EditorRepeatDataStreamID editorRepeatDataStreamID=new EditorRepeatDataStreamID();
		editorRepeatDataStreamID.RexSwitchformula(ExportContain);//解析Switch的公式
		
		//将每条数据流的同一种信息统一到一个变量中，准备写入文件
		int index=0;
		for (DataStructXml dataStructXml : ExportContain) {
			exportDataTemp.CaptionBuffer.append(dataStructXml.CaptionList);
			exportDataTemp.dataStreamBuffer.append(dataStructXml.DataStream);
			if((dataStructXml.switchStr!=null)&&(dataStructXml.switchStr.length()>5))
			{
				exportDataTemp.SwitchBuffer.append(dataStructXml.switchStr);
			}
			
			
			exportDataTemp.CommandBuffer.append(dataStructXml.command_xml);
			
			//以下是生成协议使用
			exportDataTemp.protocolCommand.append("REQ"+((index<10)?"0"+index:index)+":"+dataStructXml.command.replaceAll(",{0,1}0[Xx]{1}", " ")+"\n");
			exportDataTemp.protocolstream.append(AlignDataStreamName.AlignData(index,dataStructXml));
			index++;
		}
		//开始写入文件
		try {
			iOfile.WriteAXmlFile("DataStream.xml",constString.AddDataStreamInfo(exportDataTemp.dataStreamBuffer.toString(), exportDataTemp.SwitchBuffer.toString()));
			iOfile.WriteAXmlFile("command.xml", constString.AddCommandInfo(exportDataTemp.CommandBuffer.toString()));
			iOfile.WriteAXmlFile("Main.xml",constString.AddmaninInfo(null));
			iOfile.Writefile(exportDataTemp.CaptionBuffer.toString(), "STrTable.txt");
			
			iOfile.Writefile(exportDataTemp.protocolCommand.toString()+exportDataTemp.protocolstream.toString(), "Protocol.asm");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static void main(String args[])
	{
		String temp="(x>>7)&01";
		if((temp.indexOf("&0x01")!=-1)||(temp.indexOf("&0x1")!=-1)||(temp.indexOf("&1")!=-1))
		{
			System.out.print(temp.indexOf("&0x01"));
		}
	}
	
}


//导出数据时候的缓存
class ExportDataTemp
{
	 StringBuffer dataStreamBuffer=new StringBuffer();
	 StringBuffer CaptionBuffer=new StringBuffer();
	 StringBuffer SwitchBuffer=new StringBuffer();
	 StringBuffer CommandBuffer=new StringBuffer();
	 StringBuffer protocolCommand=new StringBuffer();//生成协议的时候保存命令
	 StringBuffer protocolstream=new StringBuffer();//生成协议的时候保存数据流信息
}


//对齐数据流名称
class  AlignDataStreamName{
	
	
	static public String AlignData(int DSindex,DataStructXml DSstr)
	{
		int byteoffset=Integer.valueOf(DSstr.byteoffset).intValue();
		int bytenum=Integer.valueOf(DSstr.bytenum).intValue()-1;
		int byteend=byteoffset+bytenum;
		StringBuffer stringTemp=new StringBuffer();
		
		stringTemp.append(((DSindex<10)?"0"+DSindex:DSindex)+".");//添加数据流序号
		stringTemp.append(AddSpase(50,DSstr.datastreamname));//添加数据流名称
		String str1="ANS"+((DSindex<10)?"0"+DSindex:DSindex)+".byte"+DSstr.byteoffset+((bytenum>0)?"-"+byteend:"");
		
		String str2=DSstr.formula.replace(" ","");
		if((str2.indexOf("&0x01")>0)||(str2.indexOf("&0x1")>0)||(str2.indexOf("&1")>0))
		{
			
			stringTemp.append(AddSpase(20,str1+TransFormal((str2))));
		}
		else {
			stringTemp.append(AddSpase(20,str1));
			//替换掉A B等占位符
			
			//String str2=DSstr.formula.replace(" ","");
			str2=str2.replace("(A*256+B)","x1x2");
			str2=str2.replace("(B*256+A)","x2x1");
			str2=str2.replace("A","x");
	
			stringTemp.append(AddSpase(20,str2));
		}
		
		
		
		if(DSstr.SwitchFormula!=null)
		{
			stringTemp.append(DSstr.SwitchFormula);
		}
		stringTemp.append("\n");
		return stringTemp.toString();
	}
	
	
	
	/*
	 * 解析  switch的公式，转成bit等信息
	 */
	static public String TransFormal(String swtFormal)
	{
		String temp="";
		if((swtFormal.indexOf("&0x01")!=-1)||(swtFormal.indexOf("&0x1")!=-1)||(swtFormal.indexOf("&1")!=-1))
		{
			temp=".bit"+swtFormal.substring(swtFormal.indexOf(">>")+2,swtFormal.indexOf(">>")+3);
		}
		return temp;
	}
	
	
	
	
	/*
	 * 格式化字符串,输入长度，左对齐字符串
	 */
	
	static public String AddSpase(int DesStrLen,String srcstr) {
 		
 		String temp=null;
 		String Zerotmp="";
 		try {
 			int srcstrlen=srcstr.getBytes("GB2312").length;
 	 		
 	 		
 	 		if(DesStrLen>=srcstrlen)
 	 		{
 	 			for(int i=0;i<DesStrLen-srcstrlen;i++)
 	 	 		{
 	 	 			Zerotmp+=" ";
 	 	 		}
 	 			
 	 			temp=srcstr+Zerotmp;
 	 		}
 	 		
 	 		else {
 				temp=srcstr;
 			}
 	 		
		} catch (Exception e) {
		e.printStackTrace();
		}
 		
 		return temp;
 	}
}

