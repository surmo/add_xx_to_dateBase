package Driverlayor;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.crypto.Data;





public class CUDBtoDateBase {
	//根据ID以及表名删除一条数据
	public static void  delect(String DBtableName,Integer id) throws SQLException
	{
		Connection conn=CreateCon.Getconnetion();
		String sql="delete from "+DBtableName+" where id=?";
		PreparedStatement psmt = conn.prepareStatement(sql);
		psmt.setInt(1, id);
		psmt.execute();
		
		psmt.close();
		conn.close();
		
	}
	
	//增加一条数据
	public static void Add(String DBname,CmdAndChinaStr addstr) throws SQLException{
		Connection conn=CreateCon.Getconnetion();
		
		String sql="insert into "+DBname+" (CANID,Datalen,SID1,SID2,SID3,SID4,ANS_ID,CMD,DataStream,SID)"+
		"values("+
		"?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement psmt=conn.prepareStatement(sql);
		psmt.setString(1, addstr.GetID());
		psmt.setString(2, addstr.GetDataLen());
		psmt.setString(3, addstr.GetSID1());
		psmt.setString(4, addstr.GetSID2());
		psmt.setString(5, addstr.GetSID3());
		psmt.setString(6, addstr.GetSID4());
		psmt.setString(7, addstr.GetANS_ID());
		psmt.setString(8, addstr.GetCMD());
		psmt.setString(9, addstr.GetdataString());
		psmt.setString(10,addstr.GetSID());
		
		psmt.execute();
		psmt.close();
		///conn.close();
		
	}
	
	//根据表名和总的字段获取记录集合
	public static List<CmdAndChinaStr> select(String DBname,String SID) throws SQLException{
	
		List<CmdAndChinaStr> result=new ArrayList<CmdAndChinaStr>();
		Connection conn=CreateCon.Getconnetion();
		
		String sql="select * from "+DBname+" where CANID=?";
		PreparedStatement psmt=conn.prepareStatement(sql);
		psmt.setString(1, SID);
		ResultSet rs=psmt.executeQuery();
		CmdAndChinaStr temp=null;
		while(rs.next())
		{
			temp=new CmdAndChinaStr();
			temp.SetCID(rs.getInt("id"));
			temp.SetID(rs.getString("CANID"));
			temp.SetCMD(rs.getString("CMD"));
			temp.SetDataLen(rs.getString("Datalen"));
			temp.SetSID(rs.getString("SID"));
			temp.SetSID1(rs.getString("SID1"));
			temp.SetSID2(rs.getString("SID2"));
			temp.SetSID3(rs.getString("SID3"));
			temp.SetSID4(rs.getString("SID4"));
			temp.SetdataString(rs.getString("DataStream"));
			
			result.add(temp);
		}
		
		psmt.close();
		conn.close();
		return result;
		
	} 
	
	
	
	
	
	//将解析到的XMl数据流信息存入数据库
	/*
	 * 传入的是一条数据流，包含了所有的信息，将单条数据存入指定数据库
	 */
	public static void AddXMldataStream(String Tablename,DataStructXml DSstr){
		
		try {
			Connection conn=CreateCon.Getconnetion();
			
			String sql="insert into "+Tablename+"(DataStream,command_id,command,byteoffset,bytenum,formula,datastreamname,command_no_0x,"
					+ "switchStr,CaptionList)"+
			"values("+
			"?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement psmt=conn.prepareStatement(sql);
			psmt.setString(1,DSstr.DataStream);
			psmt.setString(2,DSstr.command_id);
			psmt.setString(3,DSstr.command);
			psmt.setString(4,DSstr.byteoffset);
			psmt.setString(5,DSstr.bytenum);
			psmt.setString(6,DSstr.formula);
			psmt.setString(7,DSstr.datastreamname);
			psmt.setString(8,DSstr.command_no_0x);
			psmt.setString(9,DSstr.switchStr);
			psmt.setString(10,DSstr.CaptionList.toString());
			
			psmt.execute();
			psmt.close();
			//conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e);
		}
		
		
	}
	/*
	 * 添加多条从XML资源解析出来的数据流
	 */
	public void  AddXMlListdataStream(String Tablename,List<DataStructXml> DataStreamList) {
		
		for(int i=0;i<DataStreamList.size();i++)
		{
			AddXMldataStream(Tablename,DataStreamList.get(i));
		}
		
	}
	
	
	//修改一条数据
	
	public static void updata(String DBname){
		
	}
	
	
	
	public List<DataStructXml>  C_Getdata(String TableName,String claumName,String CMD){
		Getdata temp=new Getdata();
		return temp.SearchDatabyCMD(TableName, claumName, CMD);
	}
	
	
	/*
	 * 获取已经建立的车型列表信息
	 * 
	 * 返回车型信息的集合
	 */
	public List<DataStructCarList> GetCarListFromDB(){
		List<DataStructCarList> CarListList=new ArrayList<DataStructCarList>();
		try {
			
			Connection conn=CreateCon.Getconnetion();
			String sql="select * from CarList";
			PreparedStatement psmt=conn.prepareStatement(sql);
			
			ResultSet rs=psmt.executeQuery();
			DataStructCarList temp=null;
			while(rs.next())
			{
				temp=new DataStructCarList();
				temp.id=rs.getInt("id");
				temp.CarTable=rs.getString("Car_Table");
				temp.CarName=rs.getString("Car_Name");
				temp.Create_time=rs.getString("Create_time");
				temp.Last_add_time=rs.getString("Last_add_time");
				
				CarListList.add(temp);
			}
			
			psmt.close();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return CarListList;
	}
	
	
	/*
	 * 增加一个车型
	 * 参数1:车型名称
	 * 参数2：车型表名
	 * 
	 * 如果车型已经存在返回失败
	 * 
	 * 成功返回true
	 */
	 public boolean AddCarType(String CarName,String CarTable)
	{
		
		 boolean AddState=true;
		if(ChectTableisHave(CarTable)==false)
		{
			
			try {
				Connection conn=CreateCon.Getconnetion();
				String nowtime=new Date().toString();
				
				//增加CarList里边的信息
				String sql="insert into CarList(Car_Table,Car_Name,Create_time,Last_add_time)"+
				"values("+
				"?,?,?,?)";
				PreparedStatement psmt=conn.prepareStatement(sql);
				
				psmt.setString(1,CarTable);
				psmt.setString(2,CarName);
				psmt.setString(3,nowtime);
				psmt.setString(4,null);
				
				psmt.execute();
				psmt.close();
				//建立车型数据表
				
				CreateDBTable createDBTable=new CreateDBTable();
				if (createDBTable.CreateOneTable(CarTable)==false) {//如果创建数据表失败
					AddState=false;
				}
				
				
				
				
				
				
				
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
		}
		else {
			
			AddState=false;
		}
		
		return AddState;
		
	}
	
	
	/*
	 * 查询某个表在数据路Protocol_car中是否存在
	 */
	private boolean ChectTableisHave(String TableName)
	{
		boolean flag=false;
		try {
			
			Connection conn=CreateCon.Getconnetion();
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet set ;
			set = meta.getTables(null, null, TableName, null);
			while (set.next()) {
			
				flag=true;
				//System.out.print("have");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return flag;
	}	
	
	
	
	
	
	
public static void main(String args[]) throws SQLException {
		
		//CUDBtoDateBase.delectmore();;
		//Date temp=new Date();
		//System.out.print(temp.toString());
		
	}
	
	/*
	 * delete from sqmg a
where (a.command,a.byteoffset) in (select command,byteoffset from vitae group by command,byteoffset having count(*) > 1)
and rowid not in (select min(rowid) from vitae group by command,byteoffset having count(*)>1) 
	 */

public static void delectmore(String CarType){
	try {
		Connection conn=CreateCon.Getconnetion();
		String sql="delete from "+CarType+" where id not in (select * from (select max(id) from "+CarType+" group by command,byteoffset,bytenum,formula,datastreamname) a);";
		PreparedStatement psmt = conn.prepareStatement(sql);
		//psmt.setInt(1, 1);
		psmt.execute();
		
		psmt.close();
		conn.close();
	} catch (SQLException e) {
		// TODO: handle exception
	}
	
}
	
	
	
}
/*
 * 建立数据据表
 */
class CreateDBTable
{
	public boolean CreateOneTable(String Tablename){
		boolean Tablestate=false;
		try {
			System.out.println("正在建立数据表: "+Tablename);
			Connection conn=CreateCon.Getconnetion();
			Statement stat=conn.createStatement();
			String Sql="create table "+Tablename+"(id int primary key auto_increment,"
					+ "DataStream varchar(2000),"
					+ "command_id varchar(200),"
					+ "command varchar(100),"
					+ "byteoffset varchar(10),"
					+ "bytenum varchar(10),"
					+ "formula varchar(500),"
					+ "datastreamname varchar(400),"
					+ "command_no_0x varchar(50),"
					+ "switchStr varchar(5000),"
					+ "CaptionList varchar(3000)"
					+ ")";
			PreparedStatement pstm=conn.prepareStatement(Sql);

			pstm.executeUpdate();

			stat.close();
			conn.close();
			System.out.println("数据表 "+Tablename+" 建立成功！");
			Tablestate=true;
			return Tablestate;
			//stat.executeQuery(sql);

		} catch (SQLException e) {
			System.out.println(e);
			return Tablestate;
		}




	}

}


 

class Getdata{
	
	public List<DataStructXml>  SearchDatabyCMD(String TableName,String claumName,String CMD)
	{
		List<DataStructXml> result=new ArrayList<DataStructXml>();
		try {
			
			Connection conn=CreateCon.Getconnetion();
			
			String sql="select * from "+TableName+" where "+claumName+"=? order by datastreamname,byteoffset";
			PreparedStatement psmt=conn.prepareStatement(sql);
			psmt.setString(1, CMD);
			ResultSet rs=psmt.executeQuery();
			DataStructXml temp=null;
			while(rs.next())
			{
				temp=new DataStructXml();
				temp.ID=rs.getInt("id");
				
				temp.DataStream=rs.getString("DataStream");
				temp.command_id=rs.getString("command_id");
				temp.command=rs.getString("command");
				temp.byteoffset=rs.getString("byteoffset");
				temp.bytenum=rs.getString("bytenum");
				temp.formula=rs.getString("formula");
				temp.datastreamname=rs.getString("datastreamname");
				temp.command_no_0x=rs.getString("command_no_0x");
				temp.switchStr=rs.getString("switchStr");
				temp.CaptionList.append(rs.getString("CaptionList"));
				
				result.add(temp);
				
		}
		
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
		
}
	
	
}
