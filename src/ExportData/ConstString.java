package ExportData;

public class ConstString {
	//添加数据流其他部分
	public String AddDataStreamInfo(String DataStream,String Switchs)
	{
		String datastreamtop=
"<root version=\"1.0\">"
+ "<function ID=\"ID_MENU_DATA_DS\">"
+ "<show label=\"\" type=\"data_stream_select\">"
+ "<param type=\"function\" value=\"ID_FUNCTION_READ_DATASTREAM\"/>"
+ "<param label=\"data_stream_id\" type=\"ui_data_buffer\" value=\"\"/>"
+ "<param label=\"GROUP\"  type=\"string\" value=\"0\"/>"
+ "</show>"
+ "</function>"
+ ""
+ "<function ID=\"ID_FUNCTION_READ_DATASTREAM\">"
		+ "<call_protocol label=\"data_stream\" function=\"read_data_stream\" >"
				+ "<param  type=\"ui_data_buffer\" value=\"data_stream_id\"/>"
		+ "</call_protocol>"
		+ "<judge_tip_jump use_label=\"data_stream\" />"
				+ "<show label=\"\" type=\"read_data_stream\" >"
						+ "<param type=\"protocol_data\" value=\"data_stream\"/>"
								+ "</show>"
								+ "</function>"
								+ "<data_stream_lib>"
								+ "<group ID=\"0\" caption=\"ID_STR_DS_GROUP_0\">"
								
							+DataStream
		+" </group>"
		+ " </data_stream_lib>"
		+ "<switchs>"
		+ " "
		+Switchs
		+"</switchs>"
	+ "</root>";

		return datastreamtop;
	}
	//组合数据称command.xml文件
	public String AddCommandInfo(String command)
	{
		String commandTemp=""
				+ "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>"
				+ "<root version=\"1.0\">"
				+ "<commands>"
				+ command
				+ "</commands>"
				+ "</root>";
		return commandTemp;
	}
	//组合文件称main.xml文件
	public String AddmaninInfo(String mainstr)
	{
		String maintemp=""
				+ "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>"
				+ "<root>"
				+ "<include file=\"Command.xml\" />"
				+ "<include file=\"ECUInformation.xml\" />"
				+ "<include file=\"DataStream.xml\" />"
				+ "<include file=\"ActuatorTest.xml\" />"
				+ "<main_menu>"
				+ "<menu ID=\"ID_MENU_DATA_STREAM\" caption=\"ID_STR_DATA_STREAM\" function=\"ID_MENU_DATA_DS\" permission=\"0\" />"
				+ "</main_menu>"
				+ "<function ID=\"quit_function\">"
				+ "<call_protocol label=\"\" function=\"quit_system\">"
				+ "<param type=\"command\" value=\"EXIT_CMD\" />"
				+ "</call_protocol>"
				+ "</function>"
				+ "</root>";
			return maintemp;
	}

	public static void main(String args[])
	{
		ConstString constString =new ConstString();
		//System.out.print(constString.DataStreamTop());
	
	}
}
