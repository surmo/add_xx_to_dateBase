package Driverlayor;

import java.sql.*;
import java.util.Vector;


 
public class create_db_test {
 
    // JDBC �����������ݿ� URL
  static  private  String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
  static  private String DB_URL = "jdbc:mysql://localhost:3306/protocol_car";
 
    // ���ݿ���û��������룬��Ҫ�����Լ�������
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
            // ִ�в�ѯ
            System.out.println(" ʵ����Statement��...");
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
            
            // չ����������ݿ�
//            while(rs.next()){
//                // ͨ���ֶμ���
//                int id  = rs.getInt("ID");
//                String name = rs.getString("HEX_FLAG");
//                String url = rs.getString("DTC_Mean");
//    
//                // �������
//                System.out.print("ID: " + id);
//                System.out.print(", վ������: " + name);
//                System.out.print(", վ�� URL: " + url);
//                System.out.print("\n");
//               
//            }
//             ��ɺ�ر�
//            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            // ���� JDBC ����
            se.printStackTrace();
        }catch(Exception e){
            // ���� Class.forName ����
            e.printStackTrace();
        }finally{
            // �ر���Դ
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// ʲô������
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
           // ע�� JDBC ����
           Class.forName("com.mysql.jdbc.Driver");
       
           // ������
           System.out.println("�������ݿ�...");
           conn = DriverManager.getConnection(DB_URL,USER,PASS);
           
       }catch(SQLException se){
           // ���� JDBC ����
           se.printStackTrace();
       }catch(Exception e){
           // ���� Class.forName ����
           e.printStackTrace();
       }
       return conn;
   }
}
