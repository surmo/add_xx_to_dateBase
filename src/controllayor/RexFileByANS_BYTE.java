package controllayor;
import Driverlayor.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RexFileByANS_BYTE {
	
	public static List<CmdAndChinaStr> RegData(String Str)
	{
		List<CmdAndChinaStr> CmdData=new ArrayList<CmdAndChinaStr>();//用来存储解析到的命令数据，只是命令
		List<CmdAndChinaStr> CmdDataStr=new ArrayList<CmdAndChinaStr>();
		int AllCmdNum=0;
		String pattern="([0-9a-fA-F]{3})\\s{1,5}(0[0-7])\\s(([2][0-5]{1})\\s([0-9a-fA-F]{2})\\s([0-9a-fA-F]{2}))\\s([0-9a-fA-F]{2}\\s)+\\s+"
				+"[ANSans]{3}\\D*([0-9]{1,3}).*([0-9a-fA-f]{3}\\s{1,5}).*";
		Pattern patt=Pattern.compile(pattern);
		Matcher result=patt.matcher(Str);
		
		while(result.find())
		{
//			for(int i=0;i<result.groupCount();i++)
//			{
//				System.out.println(i+"->"+result.group(i));
//			}
			CmdAndChinaStr temp=new CmdAndChinaStr();
			temp.SetCMD(result.group(0));
			temp.SetID(result.group(1));
			temp.SetDataLen(result.group(2));
			temp.SetSID(result.group(3));
			temp.SetSID1(result.group(4));
			temp.SetSID2(result.group(5));
			temp.SetSID3(result.group(6));
			temp.SetANS_ID(result.group(8));
			CmdData.add(temp);
			
		}
		AllCmdNum=CmdData.size();
		pattern="([0-9]{1,3}\\.?|\\s{0,4})([\u4e00-\u9fa5]+).*[ANSans]{3}\\D*([0-9A-Fa-f]{1,3})\\D*([BYTEbyte]{4}).*";
		patt=Pattern.compile(pattern);
		result=patt.matcher(Str);
		
		while(result.find())
		 {
			
			 for(int i=0;i<AllCmdNum;i++)
			 {
				 if(result.group(3).equals(CmdData.get(i).GetANS_ID()))
				 {
					 
					 CmdAndChinaStr temp=new CmdAndChinaStr();
					 temp=CmdData.get(i);
					 temp.SetdataString(result.group(0));
					 CmdDataStr.add(temp);
					
				 }
			 }
			
		 }
		
		
		return CmdDataStr;
	}
	
	public static void AddRegDataToDB(String filepath){
		
		try
		{
			String temp=IOfile.Readfile("filetest.txt");
			List<CmdAndChinaStr> str=RegData(temp);
			
			for(int i=0;i<str.size();i++){
				CUDBtoDateBase.Add("test1",str.get(i));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}catch (IOException c) {
			System.out.println(c);
		}
		
		
	}
	
	public static void main(String args[])throws IOException
	{
		//String temp=IOfile.Readfile("filetest.txt");
		//AddRegDataToDB(RegData(temp));
		
	}
}
