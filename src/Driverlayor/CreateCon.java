package Driverlayor;

import java.sql.*;
public class CreateCon {

	private static final String URL="jdbc:mysql://localhost:3306/protocol_car";
	private static final String NAME="root1";
	private static final String PASSWORD="root";
	
	private static Connection conn=null;
	static{//静态代码，获取数据库驱动
		
		try{
		    // 注册 JDBC 驱动
	           Class.forName("com.mysql.jdbc.Driver");
	           // 打开链接
	           System.out.println("连接数据库...");
	           conn = DriverManager.getConnection(URL,NAME,PASSWORD);
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}catch (SQLException c) {
			c.printStackTrace();
		}
	}
	
	//对外提供一个方法获取数据库连接
	public static Connection Getconnetion() {
		return conn;
	}
	
	public static void main(String[] args) throws Exception
	{
		Statement stmt=conn.createStatement();
		ResultSet rs=stmt.executeQuery("select SID1,DataStream from test1");
		while(rs.next())
		{
			
		}
		
	}
	
	
}
