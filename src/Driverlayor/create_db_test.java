package Driverlayor;

import java.sql.*;
import java.util.Vector;


 
public class create_db_test {
 
    // JDBC 驱动名及数据库 URL
  static  private  String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
  static  private String DB_URL = "jdbc:mysql://localhost:3306/protocol_car";
 
    // 数据库的用户名与密码，需要根据自己的设置
  static  private String USER = "root";
  static  private String PASS = "root";
 
    public void SetDB_URL(String url){
    	DB_URL="jdbc:mysql://localhost:3306/"+url;
    }
    public void SetDB_USER(String user){
    	USER=user;
    }
    public void SetDB_PASS(String pass){
    	PASS=pass;
    }
    
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        Vector<MatchCmdAndStr> match_result=new Vector<MatchCmdAndStr>();
        
       
        try{
        	match_result=rexg_documend.ReggrepData(rexg_documend.Readfile());
        	conn=OpenDB();
            // 执行查询
            System.out.println(" 实例化Statement对...");
            stmt = conn.createStatement();
            
            String sql;
//          for(int i=0;i<match_result.size();i++)
//          { 
//        	  sql = "INSERT INTO test1(CANID,Datalen,SID1,SID2,SID3,SID4,ANS_ID,CMD,DataStream)"+
//            		"VALUES  ('"+
//            		match_result.get(i).ID+"','"+
//            		match_result.get(i).DataLen+"','"+
//            		match_result.get(i).SID1+"','"+
//            		match_result.get(i).SID2+"','"+
//            		match_result.get(i).SID3+"','"+
//            		match_result.get(i).SID4+"','"+
//            		match_result.get(i).ANS_ID+"','"+
//            		match_result.get(i).CMD+"','"+
//            		match_result.get(i).DataStream+
//            		"')";
//        	stmt.executeUpdate(sql);
//          }
            
            // 展开结果集数据库
//            while(rs.next()){
//                // 通过字段检索
//                int id  = rs.getInt("ID");
//                String name = rs.getString("HEX_FLAG");
//                String url = rs.getString("DTC_Mean");
//    
//                // 输出数据
//                System.out.print("ID: " + id);
//                System.out.print(", 站点名称: " + name);
//                System.out.print(", 站点 URL: " + url);
//                System.out.print("\n");
//               
//            }
//             完成后关闭
//            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }
    
 static  public Connection OpenDB()
   {
	   Connection conn = null;
      
       try{
           // 注册 JDBC 驱动
           Class.forName("com.mysql.jdbc.Driver");
       
           // 打开链接
           System.out.println("连接数据库...");
           conn = DriverManager.getConnection(DB_URL,USER,PASS);
           
       }catch(SQLException se){
           // 处理 JDBC 错误
           se.printStackTrace();
       }catch(Exception e){
           // 处理 Class.forName 错误
           e.printStackTrace();
       }
       return conn;
   }
}
