package Driverlayor;

public class DataStruct {
 private    String ID;  //ID
 private	String DataLen;  //数据长度
 private	String SID1;   //关键字节 
 private	String SID2;    //命令字节1
 private	String SID3;    //命令字节2
 private	String SID4;    //命令字节2
 private	String ANS_ID;  //ANS信息
 private	String CMD;     //整帧命令的信息
 private    String SID; 	

 public void SetSID(String strd){
	 SID=strd;
 }
 
 public String GetSID(){
	 return SID;
	 
 }
	
 
 public void SetID(String strd){
	 ID=strd;
 }
 
 public String GetID(){
	 return ID;
	 
 }
 
 
 
 public void SetDataLen(String strd){
	 DataLen=strd;
 }
 
 public String GetDataLen(){
	 return DataLen;
	 
 }
 
 
 public void SetSID1(String strd){
	 SID1=strd;
 }
 
 public String GetSID1(){
	 return SID1;
	 
 }
 
 public void SetSID2(String strd){
	 SID2=strd;
 }
 
 public String GetSID2(){
	 return SID2;
	 
 }
 
 
 
 
 public void SetSID3(String strd){
	 SID3=strd;
 }
 
 public String GetSID3(){
	 return SID3;
	 
 }

 
 
 public void SetSID4(String strd){
	 SID4=strd;
 }
 
 public String GetSID4(){
	 return SID4;
	 
 }
 
 
 
 
 public void SetANS_ID(String strd){
	 ANS_ID=strd;
 }
 
 public String GetANS_ID(){
	 return ANS_ID;
	 
 }
 
 
 public void SetCMD(String strd){
	 CMD=strd;
 }
 
 public String GetCMD(){
	 return CMD;
	 
 }
 
 
}
