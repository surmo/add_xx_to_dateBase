package Driverlayor;

import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.*;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.awt.List;
import java.io.*;

public class rexg_documend  {
	
	public static void main(String[] args)  throws IOException{
		
		ReggrepData(Readfile());
	}
	
	public static String Readfile() throws IOException{
		
	File f=new File("filetest.txt");//名字暂时定死
	
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
	
	public static Vector<MatchCmdAndStr> ReggrepData(String Str){
		
		Vector<RexCmdResult> rex_cmd_resul=new Vector<RexCmdResult>();
		Vector<MatchCmdAndStr> match_cmd_and_str_resul=new Vector<MatchCmdAndStr>();
		int all_data_len=0;
		
		
		
		String pattern="([REQreq]{3}[0-9]{1,3}).*(2[0-5]\\s\\w{2}).*([ANSans]{3}[0-9]{1,3})";				
		
		
		Pattern patt=Pattern.compile(pattern);
		Matcher result=patt.matcher(Str);

		if(result.find())//以帧表示的数据流
		{
//			pattern="([0-9a-fA-F]{3})\\s{1,5}(0[0-7])\\s([2][0-5]{1})\\s([0-9a-fA-F]{2})\\s([0-9a-fA-F]{2})\\s([0-9a-fA-F]{2}\\s)+\\s+"
//					+"([0-9a-fA-f]{3}\\s{1,5})";
			pattern="([0-9a-fA-F]{3})\\s{1,5}(0[0-7])\\s([2][0-5]{1})\\s([0-9a-fA-F]{2})\\s([0-9a-fA-F]{2})\\s([0-9a-fA-F]{2}\\s)+\\s+"
			+"[ANSans]{3}\\D*([0-9]{1,3}).*([0-9a-fA-f]{3}\\s{1,5}).*";
		
			patt=Pattern.compile(pattern);
			result=patt.matcher(Str);
			
			while(result.find())
			{
//				for(int i=0;i<result.groupCount()+1;i++)
//				{
//					System.out.println(i+"->"+result.group(i));
//				}
//				
				RexCmdResult temp=new RexCmdResult();
				temp.CMD=result.group(0);
				temp.ID=result.group(1);
				temp.DataLen=result.group(2);
				temp.SID1=result.group(3);
				temp.SID2=result.group(4);
				temp.SID3=result.group(5);
				temp.ANS_ID=result.group(7);
				rex_cmd_resul.addElement(temp);
				
			}
			//for(int i=0;i<all_data_resul.size();i++)
			//	  System.out.println(i+"->"+all_data_resul.get(i).ANS_ID);  	
				all_data_len=rex_cmd_resul.size();	
				
		
		
		//解析数据流的信息
		pattern="([0-9]{1,3}\\.?|\\s{0,4})([\u4e00-\u9fa5]+).*[ANSans]{3}\\D*([0-9A-Fa-f]{2})\\D*([BYTEbyte]{4}).*";
			patt=Pattern.compile(pattern);
			result=patt.matcher(Str);
	
		 while(result.find())
		 {
			
			 for(int i=0;i<all_data_len;i++)
			 {
				 if(result.group(3).equals(rex_cmd_resul.get(i).ANS_ID))
				 {
					 
					 MatchCmdAndStr temp=new MatchCmdAndStr();
					 temp.copy_father_data(rex_cmd_resul.get(i));
					 temp.DataStream=result.group(0);
					 match_cmd_and_str_resul.addElement(temp);
					
				 }
			 }
			
		 }
		
//		for(int i=0;i<match_cmd_and_str_resul.size();i++)
//		{
//			System.out.println(match_cmd_and_str_resul.get(i).CMD); 
//			System.out.println(match_cmd_and_str_resul.get(i).DataStream);
//			System.out.println();
//				
//		} 
		}
		else//以关键帧表示的数据流
		{
			
			;
		}

		
		return match_cmd_and_str_resul;
	}
  
	

	
}
//定义解析CMD命令的结构的类
class RexCmdResult{
	String ID;  //ID
	String DataLen;  //数据长度
	String SID1;   //关键字节 
	String SID2;    //命令字节1
	String SID3;    //命令字节2
	String SID4;    //命令字节2
	String ANS_ID;  //ANS信息
	String CMD;     //整帧命令的信息
	
	
	int in_ID;
	int in_DataLen;
	int in_SID1;
	int in_SID2;
	int in_SID3;
	int in_SID4;
	
	
	public void Str2int(){
		in_ID=Integer.parseInt(ID,16);
		in_DataLen=Integer.parseInt(DataLen,16);
		in_SID1=Integer.parseInt(SID1,16);
		in_SID2=Integer.parseInt(SID2,16);
		in_SID3=Integer.parseInt(SID3,16);
		in_SID4=Integer.parseInt(SID4,16);
		
	}
	
}
//该类继承了命令，匹配之后的数据流名称和命令会放在里边
class MatchCmdAndStr extends RexCmdResult{
	
	  
	String DataStream;
	
	public void copy_father_data(RexCmdResult FD) {
		 ID=FD.ID;  //ID
		 DataLen=FD.DataLen;  //数据长度
		 SID1=FD.SID1;   //关键字节 
		 SID2=FD.SID2;    //命令字节1
		 SID3=FD.SID3;    //命令字节2
		 SID4=FD.SID4;    //命令字节2
		 ANS_ID=FD.ANS_ID;  //ANS信息
		 CMD=FD.CMD;     //整帧命令的信息
		
	}
	
}


