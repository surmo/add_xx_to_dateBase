package facetest;

import java.util.ArrayList;
import java.util.List;

import Driverlayor.CUDBtoDateBase;
import Driverlayor.DataStructCarList;

/*
 * 主要处理解析XML时的信息
 * 1.解析哪个车型，查询车型在数据库中是否已经存在
 * 2.如果车型不存在，则建立新的数据表
 * 
 */


public class RexPathInfo {

	/*
	 * 获取已经存在的车型列表
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
	   
	   if(RexPathInfo.AddCarType("哈飞","HaFei"))
	   {
		  System.out.println("增加成功");
	   }
	   else {
		   System.out.println("该车型已经存在");
	}
   }
}
