package Searchlayor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import Driverlayor.*;

public class SearchDatabyCMD {
	
	/*
	 * ��ѯһ������
	 */
	public List<DataStructXml> SearchOneDatabyCMD(String TableName,String claumName,String CMD){
		List<DataStructXml> OneCMDSearchresult=new ArrayList<DataStructXml>();
		CUDBtoDateBase cudb= new  CUDBtoDateBase();
		OneCMDSearchresult=cudb.C_Getdata(TableName, claumName, CMD);
		
		///System.out.println(CMD);
//		for(int i=0;i<OneCMDSearchresult.size();i++)
//		{
//			System.out.println(OneCMDSearchresult.get(i).datastreamname+"   "+OneCMDSearchresult.get(i).byteoffset+"    "+OneCMDSearchresult.get(i).formula);
//		}
		//System.out.println();
		
		return OneCMDSearchresult;
	}
	/*
	 * ��ѯ��������
	 */
	public void SearchMoredatabyCMD(String TableName,String claumName,String filePath){
		
		List<String> cmdlist=new ArrayList<String>();//�������������
		String strtemp,strtemp1;
		try{
			
			File f1=new File(filePath);
			FileInputStream fip=new FileInputStream(f1);
			InputStreamReader re=new InputStreamReader(fip,"UTF-8");
			BufferedReader reader=new BufferedReader(re);
			
			while(reader.ready())
			{
				strtemp=reader.readLine();
				
				cmdlist.add(strtemp);
			}
		List<List<DataStructXml>> Serchresult=new ArrayList<List<DataStructXml>>();
		for(int i=0;i<cmdlist.size();i++){
			
			Serchresult.add(SearchOneDatabyCMD(TableName,claumName,cmdlist.get(i)));
			//SearchOneDatabyCMD(TableName,claumName,cmdlist.get(i));
			
		}
		
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		
		
	}
	
public List<List<DataStructXml>> SearchMoredatabyCMD(String TableName,String claumName,String searchstr,String SearchType){
	List<List<DataStructXml>> Serchresult=new ArrayList<List<DataStructXml>>();//����һ����ά�����飬������������ݿ�ɸѡ����������
		List<String> cmdlist=new ArrayList<String>();//�������������
		String strtemp;
		if(searchstr!=null&&!(searchstr.equals("")))
		try{
			ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(searchstr.getBytes("UTF-8"));
			//File f1=new File(filePath);
			//FileInputStream fip=new FileInputStream(f1);
			InputStreamReader re=new InputStreamReader(tInputStringStream,"UTF-8");
			BufferedReader reader=new BufferedReader(re);
			
			while(reader.ready())
			{
				strtemp=HandleInputCmd(SearchType,reader.readLine());
				cmdlist.add(strtemp);
			}
		
		for(int i=0;i<cmdlist.size();i++){
			
			Serchresult.add(SearchOneDatabyCMD(TableName,claumName,cmdlist.get(i)));
			//SearchOneDatabyCMD(TableName,claumName,cmdlist.get(i));
			
		}
		
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		
		return Serchresult;
	}

	private static String HandleInputCmd(String SearchType,String CMDstr)
	{
		String str1,str2;
		switch (SearchType) {
		case "command"://�����������������CAN ID
			str1=CMDstr.replaceAll(",{0,1}0[Xx]"," ");//ȥ��0x
			
			str1=str1.replaceAll("[0-9a-fA-F]{3}","0$0");//����������������ַ��������CAN��ID֡��������ǰ�����'0'
			
			str1=str1.replaceAll("[\\s]{1,7}","");//ȥ���ո�
			
			//System.out.print(str1);
			break;
		case "command_no_canid": //�������������������CAN ID
			str1=CMDstr.replaceAll(",{0,1}0[Xx]","");//ȥ��0x
			str1=str1.replaceAll("[0-9a-fA-F]{3}","");//����������������ַ��������CAN��ID֡��ֱ��ȥ��
			str1=str1.replaceAll("[\\s]{1,7}","");//ȥ���ո�
			break;
		case "datastreamname"://�����������������Ʋ��Ҳ�������
			str1=CMDstr;
			break;
		default:
			str1=CMDstr.replaceAll(",{0,1}0[Xx]","");//ȥ��0x
			
			str1=str1.replaceAll("[0-9a-fA-F]{3}","0$0");//����������������ַ��������CAN��ID֡��������ǰ�����'0'
			
			str1=str1.replaceAll("[\\s]{1,7}","");//ȥ���ո�
			break;
		}
		return str1;
	}


	
	public static void main(String args[])
	{
		SearchDatabyCMD temp=new SearchDatabyCMD();
//		temp.SearchOneDatabyCMD("greatwall","command_no_0x","8031F7022100CB");
		temp.HandleInputCmd("command", "0x07,0x80,0x88,0x76,0x57,0x66");
	}
}
