package Driverlayor;



public class DataStructXml {
public int ID;
public	String DataStream;  //整条数据流
public	String command_id;  //命令的ID
public	String command;     //实际的命令
public  String command_no_canid; //去掉了ID的Can命令
public	String byteoffset;   //字节偏移
public	String bytenum;      //自己个数
public	String formula;      //计算公式
public	String datastreamname;    //数据的中文名称

public String command_no_0x;
public String switchStr;    //switch的数据
public StringBuffer CaptionList=new StringBuffer();//该条数据流用到的全部Caption数据
public String command_xml;//包含XML标签的command命令，导出数据的时候才会给它赋值

public String SwitchFormula;  //保存switch的解析方式，没有的话就留空，导出的时候才会赋值


}
