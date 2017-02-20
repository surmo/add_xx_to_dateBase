package ExportData;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.regexp.internal.recompile;

import Driverlayor.DataStructXml;
import Driverlayor.IOfile;

/*
 * 
 * ȥ���ظ���������ID caption
 * 
 */



public class EditorRepeatDataStreamID {
	
	/*
	 *��һ��������ߵ�ID Caption����Ϣ������Flag��Ϣ����֤��Ψһ��
	 *��������ID����flag��ֵ
	 */
  public void FormatWithID(int FlagID,DataStructXml DataStream)
	{
		int commandLen=0;//���������
		List<String> captionList=new ArrayList<String>();
		//�滻������ID
		DataStream.DataStream=DataStream.DataStream.replaceAll("ID=\"([0-9]{1,4})\"", "ID=\""+FlagID+"\"");
		//�滻������command����
		DataStream.DataStream=DataStream.DataStream.replaceAll("<command_id>.*</command_id>", "<command_id>CMD_DATASTREAM_"+FlagID+"</command_id>");
		//�滻command����
		commandLen=(DataStream.command.length()+1)/5;
		DataStream.command_xml="<command ID=\"CMD_DATASTREAM_"+FlagID+"\" buffer_offset=\"0\" reserved=\"0x00\" length=\""+commandLen+"\">"+DataStream.command+"</command>";
		
		try{
			//String file=IOfile.Readfile("StrTable.txt");
			ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(DataStream.CaptionList.toString().getBytes("UTF-8"));
			InputStreamReader re=new InputStreamReader(tInputStringStream,"UTF-8");
			BufferedReader reader=new BufferedReader(re);
			
			while(reader.ready())
			{//��ȡÿһ�����ݲ��Ҵ�������
				String strtemp;
				strtemp=reader.readLine();
				captionList.add(strtemp);
			}
			if(DataStream.CaptionList.length()>0)
			DataStream.CaptionList.delete(0,DataStream.CaptionList.length()-1);//�������ߵ�����
			//System.out.println("ok");
			for(int i=0;i<captionList.size();i++)
			{
				
				String Captiontemp=captionList.get(i).split("\t")[0];//���Ʊ���ָ��ȡCaption������
				
				DataStream.DataStream=DataStream.DataStream.replace(Captiontemp, "ID_STR_DSSU_"+i+"_"+FlagID);//�滻�������е�caption
				if((DataStream.switchStr!=null)&&(DataStream.switchStr.length()>10))
				DataStream.switchStr=DataStream.switchStr.replace(Captiontemp, "ID_STR_DSSU_"+i+"_"+FlagID);//�滻Switch�е�caption
				
				DataStream.CaptionList.append((captionList.get(i).replace(Captiontemp, "ID_STR_DSSU_"+i+"_"+FlagID)).toCharArray());//�滻StrTable�е�Caption
				DataStream.CaptionList.append("\n");
				//System.out.println(captionList.get(i));
			}
			
			tInputStringStream.close();
			re.close();
			reader.close();
		}catch(IOException e)
		{
			e.printStackTrace();
			System.out.print("��ͳһ������Caption-��ȡCaptionת���������ʱ�����");
		}finally{
			
			
		}
		
		
	}

	
  
  public void RexSwitchformula(List<DataStructXml> datastream)
  {
	  int i=0;
	  StringBuffer stringBuffer=new StringBuffer();
	  while(i<datastream.size())//ѭ��ִ�ж�Ӧ��������
	  {
		  if(datastream.get(i).switchStr!=null)//�����switch����
		  {
			  String temp=datastream.get(i).formula.substring(datastream.get(i).formula.indexOf(",")+1);
			  datastream.get(i).formula=temp.substring(0,temp.length()-1);
			  List<String> captionList=new ArrayList<String>();
			  try {
				  	
				  	ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(datastream.get(i).CaptionList.toString().getBytes("UTF-8"));
					InputStreamReader re=new InputStreamReader(tInputStringStream,"UTF-8");
					BufferedReader reader=new BufferedReader(re);
					
					while(reader.ready())
					{
						//��ȡÿһ�����ݲ��Ҵ�������
						String strtemp=reader.readLine();
						captionList.add(strtemp);
					}
					
			} catch (Exception e) {
				
				e.printStackTrace();
				System.err.print("��ȡcaptionLIst��ʱ�����");
			}
			  String Switchtemp=datastream.get(i).switchStr.replace("\n", "");//ȥ�����з�
			 
			  String temp2=Switchtemp.replaceAll("<switch ID=\"[0-9]{1,5}\">(.*)</switch>", "$1");
			  String temp3=temp2.replaceAll("<case value=\"([0-9a-fA-FxX]{1,4})\">([A-Za-z_0-9]{3,35})</case>","$1:$2   ");
			  String temp4=temp3.replaceAll("<default>(.*)</default>", "����:$1");
			  
			  //��switchת���������ĸ�ʽ  ��  0x00��ID_STR_DS_SWITCH_01  0x01��ID_STR_DS_SWITCH_05
			 
			  //��ID_STR_DS_SWITCH_01  �滻������Ӧ������
			  for(int j=0;j<captionList.size();j++)
			  {
				
				  String Captiontemp[]=captionList.get(j).split("\t");
				  
				  if(Captiontemp.length>1)//�����жϣ���Ȼ�п��л����
			      temp4=temp4.replace(Captiontemp[0], Captiontemp[1]);
			  }
			  
				datastream.get(i).SwitchFormula=temp4;
		  }
		  i++;
	  }
	  
  }
  
  
  
  
	public static void  main(String args[]) {
		
		String temp="<switch ID=\"2\">"+
			"<case value=\"0x00\">ID_STR_DS_SWITCH_01</case>"+
			"<case value=\"0x01\">ID_STR_DS_SWITCH_05</case>"+
			"<case value=\"0x02\">ID_STR_DS_SWITCH_06</case>"+
			"<case value=\"0x03\">ID_STR_DS_SWITCH_07</case>"+
			"<default>ID_STR_DS_SWITCH_00</default>"+
		    "</switch>";
		
		
		String temp1="ID_STR_DSSU_1_4	δ����	SUBARU";
		
		String temp2=temp.replaceAll("<switch ID=\"[0-9]{1,5}\">(.*)</switch>", "$1");
		String temp3=temp2.replaceAll("<case value=\"([0-9a-fA-Fx]{1,4})\">([A-Za-z_0-9]{3,35})</case>","$1:$2   ");
		String temp4=temp3.replaceAll("<default>(.*)</default>", "����:$1");
		
		String temp5[]=temp1.split("\t");
		System.out.println(temp5);
	
	}
}
