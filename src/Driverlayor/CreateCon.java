package Driverlayor;

import java.sql.*;
public class CreateCon {

	private static final String URL="jdbc:mysql://localhost:3306/protocol_car";
	private static final String NAME="root1";
	private static final String PASSWORD="root";
	
	private static Connection conn=null;
	static{//��̬���룬��ȡ���ݿ�����
		
		try{
		    // ע�� JDBC ����
	           Class.forName("com.mysql.jdbc.Driver");
	           // ������
	           System.out.println("�������ݿ�...");
	           conn = DriverManager.getConnection(URL,NAME,PASSWORD);
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}catch (SQLException c) {
			c.printStackTrace();
		}
	}
	
	//�����ṩһ��������ȡ���ݿ�����
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
