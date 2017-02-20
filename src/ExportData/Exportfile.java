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
		for(int i=0;i<ExportContain.size();i++)//��ѡ�е���������ͳһ����ID CAption �Է��ظ�
		{
			EditorRepeatDataStreamID editorRepeatDataStreamID=new EditorRepeatDataStreamID();
			editorRepeatDataStreamID.FormatWithID(i,ExportContain.get(i));
		}

		EditorRepeatDataStreamID editorRepeatDataStreamID=new EditorRepeatDataStreamID();
		editorRepeatDataStreamID.RexSwitchformula(ExportContain);//����Switch�Ĺ�ʽ
		
		//��ÿ����������ͬһ����Ϣͳһ��һ�������У�׼��д���ļ�
		int index=0;
		for (DataStructXml dataStructXml : ExportContain) {
			exportDataTemp.CaptionBuffer.append(dataStructXml.CaptionList);
			exportDataTemp.dataStreamBuffer.append(dataStructXml.DataStream);
			if((dataStructXml.switchStr!=null)&&(dataStructXml.switchStr.length()>5))
			{
				exportDataTemp.SwitchBuffer.append(dataStructXml.switchStr);
			}
			
			
			exportDataTemp.CommandBuffer.append(dataStructXml.command_xml);
			
			//����������Э��ʹ��
			exportDataTemp.protocolCommand.append("REQ"+((index<10)?"0"+index:index)+":"+dataStructXml.command.replaceAll(",{0,1}0[Xx]{1}", " ")+"\n");
			exportDataTemp.protocolstream.append(AlignDataStreamName.AlignData(index,dataStructXml));
			index++;
		}
		//��ʼд���ļ�
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
		for(int i=0;i<ExportContain.size();i++)//��ѡ�е���������ͳһ����ID CAption �Է��ظ�
		{
			EditorRepeatDataStreamID editorRepeatDataStreamID=new EditorRepeatDataStreamID();
			editorRepeatDataStreamID.FormatWithID(i,ExportContain.get(i));
		}

		EditorRepeatDataStreamID editorRepeatDataStreamID=new EditorRepeatDataStreamID();
		editorRepeatDataStreamID.RexSwitchformula(ExportContain);//����Switch�Ĺ�ʽ
		
		//��ÿ����������ͬһ����Ϣͳһ��һ�������У�׼��д���ļ�
		int index=0;
		for (DataStructXml dataStructXml : ExportContain) {
			exportDataTemp.CaptionBuffer.append(dataStructXml.CaptionList);
			exportDataTemp.dataStreamBuffer.append(dataStructXml.DataStream);
			if((dataStructXml.switchStr!=null)&&(dataStructXml.switchStr.length()>5))
			{
				exportDataTemp.SwitchBuffer.append(dataStructXml.switchStr);
			}
			
			
			exportDataTemp.CommandBuffer.append(dataStructXml.command_xml);
			
			//����������Э��ʹ��
			exportDataTemp.protocolCommand.append("REQ"+((index<10)?"0"+index:index)+":"+dataStructXml.command.replaceAll(",{0,1}0[Xx]{1}", " ")+"\n");
			exportDataTemp.protocolstream.append(AlignDataStreamName.AlignData(index,dataStructXml));
			index++;
		}
		//��ʼд���ļ�
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


//��������ʱ��Ļ���
class ExportDataTemp
{
	 StringBuffer dataStreamBuffer=new StringBuffer();
	 StringBuffer CaptionBuffer=new StringBuffer();
	 StringBuffer SwitchBuffer=new StringBuffer();
	 StringBuffer CommandBuffer=new StringBuffer();
	 StringBuffer protocolCommand=new StringBuffer();//����Э���ʱ�򱣴�����
	 StringBuffer protocolstream=new StringBuffer();//����Э���ʱ�򱣴���������Ϣ
}


//��������������
class  AlignDataStreamName{
	
	
	static public String AlignData(int DSindex,DataStructXml DSstr)
	{
		int byteoffset=Integer.valueOf(DSstr.byteoffset).intValue();
		int bytenum=Integer.valueOf(DSstr.bytenum).intValue()-1;
		int byteend=byteoffset+bytenum;
		StringBuffer stringTemp=new StringBuffer();
		
		stringTemp.append(((DSindex<10)?"0"+DSindex:DSindex)+".");//������������
		stringTemp.append(AddSpase(50,DSstr.datastreamname));//�������������
		String str1="ANS"+((DSindex<10)?"0"+DSindex:DSindex)+".byte"+DSstr.byteoffset+((bytenum>0)?"-"+byteend:"");
		
		String str2=DSstr.formula.replace(" ","");
		if((str2.indexOf("&0x01")>0)||(str2.indexOf("&0x1")>0)||(str2.indexOf("&1")>0))
		{
			
			stringTemp.append(AddSpase(20,str1+TransFormal((str2))));
		}
		else {
			stringTemp.append(AddSpase(20,str1));
			//�滻��A B��ռλ��
			
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
	 * ����  switch�Ĺ�ʽ��ת��bit����Ϣ
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
	 * ��ʽ���ַ���,���볤�ȣ�������ַ���
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

