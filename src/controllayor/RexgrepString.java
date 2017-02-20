package controllayor;
import java.io.IOException;
import java.util.regex.*;



import Driverlayor.*;
public class RexgrepString {
	//识别读入文档需要什么方式解析
	private int RecongnitionStringType(String rexfile)
	{
		int resultcount=0;
		int fileType=0;
		try {
			String strin=IOfile.Readfile(rexfile);
			String pattern="([REQreq]{3}[0-9]{1,3}).*(2[0-5]\\s\\w{2}).*([ANSans]{3}[0-9]{1,3})";
			Pattern patt=Pattern.compile(pattern);
			Matcher result=patt.matcher(strin);
			
			while(result.find())
			{
				resultcount++;
				if(resultcount>3)
					{
						fileType=StringType.ANS_BYTE;
						break;
					}
			}
			if(fileType==0)//证明前面的判断不符合条件
			{
				resultcount=0;
				pattern="##(.*)##";
				patt=Pattern.compile(pattern);
				result=patt.matcher(strin);
				while(result.find())
				{
					resultcount++;
					if(resultcount>3)
					{
						fileType=StringType.DIY_TYPE;
						break;
					}
				}
				
				
			}
			else if(fileType==0)
			{
				resultcount=0;
				pattern="##(.*)##";
				patt=Pattern.compile(pattern);
				result=patt.matcher(strin);
				while(result.find())
				{
					resultcount++;
					if(resultcount>3)
					{
						fileType=StringType.DIY_TYPE;
						break;
					}
				}
				
			}
			
			
		} catch (IOException e) {
			System.err.println(e);
		}

		return fileType;
	}
	
	
	public  void RexgrepStr(String rexfile)
	{
		int filetype=0;
		filetype=RecongnitionStringType(rexfile);
		
		
		switch (filetype) {
		case 1: //通过ANS匹配数据和命令
			RexFileByANS_BYTE.AddRegDataToDB(rexfile);
			break;

		case 2://通过关键字节匹配命令和数据流
			break;
			
		case 3://通过xml文件匹配数据
			
			break;
		default:
			break;
		}
		
		
		
	}
	
	public static void  main(String args[]) {
		
		RexgrepString temp=new RexgrepString();
		temp.RexgrepStr("filetest.txt");
		
		
	}

}

class StringType
{
	public static int ANS_BYTE=1;
	public static int ONLY_BYTE=2;
	public static int XML_STR=3;
	public static int DIY_TYPE=4;
}
