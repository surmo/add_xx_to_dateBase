package facetest;

import java.util.ArrayList;
import java.util.List;

import Driverlayor.CUDBtoDateBase;
import Driverlayor.DataStructCarList;

/*
 * ��Ҫ�������XMLʱ����Ϣ
 * 1.�����ĸ����ͣ���ѯ���������ݿ����Ƿ��Ѿ�����
 * 2.������Ͳ����ڣ������µ����ݱ�
 * 
 */


public class RexPathInfo {

	/*
	 * ��ȡ�Ѿ����ڵĳ����б�
	 */
 static	public List<DataStructCarList>  GetCarList(){
		
		List<DataStructCarList> CarListList=new ArrayList<DataStructCarList>();
		CUDBtoDateBase cudBtoDateBase=new CUDBtoDateBase();
		CarListList=cudBtoDateBase.GetCarListFromDB();

		
		return CarListList;
	}
 
 	static public boolean AddCarType(String CarName,String CarTable){
 		boolean AddState=true;
 		CUDBtoDateBase cudBtoDateBase=new CUDBtoDateBase();
		
 		AddState=cudBtoDateBase.AddCarType(CarName, CarTable);
 		
 		return AddState;
 		
 	}
 
   public static void main(String args[])
   {
	   
	   if(RexPathInfo.AddCarType("����","HaFei"))
	   {
		  System.out.println("���ӳɹ�");
	   }
	   else {
		   System.out.println("�ó����Ѿ�����");
	}
   }
}
