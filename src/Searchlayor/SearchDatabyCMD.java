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
	 * 查询一条数据
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
	 * 查询多条数据
	 */
	public void SearchMoredatabyCMD(String TableName,String claumName,String filePath){
		
		List<String> cmdlist=new ArrayList<String>();//保存输入的命令
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
	List<List<DataStructXml>> Serchresult=new ArrayList<List<DataStructXml>>();//定义一个二维的数组，用来保存从数据库筛选出来的数据
		List<String> cmdlist=new ArrayList<String>();//保存输入的命令
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
		case "command"://按照数据流命令处理，带CAN ID
			str1=CMDstr.replaceAll(",{0,1}0[Xx]"," ");//去除0x
			
			str1=str1.replaceAll("[0-9a-fA-F]{3}","0$0");//如果有连续三个的字符则表明是CAN的ID帧，所以在前面加上'0'
			
			str1=str1.replaceAll("[\\s]{1,7}","");//去除空格
			
			//System.out.print(str1);
			break;
		case "command_no_canid": //按照数据流命令处理，不带CAN ID
			str1=CMDstr.replaceAll(",{0,1}0[Xx]","");//去除0x
			str1=str1.replaceAll("[0-9a-fA-F]{3}","");//如果有连续三个的字符则表明是CAN的ID帧，直接去掉
			str1=str1.replaceAll("[\\s]{1,7}","");//去除空格
			break;
		case "datastreamname"://按照数据流中文名称查找不做处理
			str1=CMDstr;
			break;
		default:
			str1=CMDstr.replaceAll(",{0,1}0[Xx]","");//去除0x
			
			str1=str1.replaceAll("[0-9a-fA-F]{3}","0$0");//如果有连续三个的字符则表明是CAN的ID帧，所以在前面加上'0'
			
			str1=str1.replaceAll("[\\s]{1,7}","");//去除空格
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
