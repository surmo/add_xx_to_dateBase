文件说明:
controllayor
	ResolutionXml    解析xml文件等数据流，按照固定的格式保存到数据库
	
Driverlayor       底层相关的各种函数，文件读写，数据库连接等
	CUDBtoDateBase  数据库的增删改查
	DataStructXm    一个全局的类，相当于数据流的结构体，和数据库字段相对应
	IOfile          文件的读写操作，目录遍历等
	CreateCon				数据库的连接信息等

ExportData    导出数据
	ConstString   一些固定的字符串
	EditorRepeatDataStreamID   caption去重，命令去重等
  Exportfile   根据选择的数据导出文件
  
GUIEnter   界面显示
	domain  整个程序的入口文件   显示主界面
	RexPathInfo   增加车型 ，这里只写了一半，需要进入这个页面，单独执行main里边的添加函数添加车型
	SelectSearchResul   数据流筛选界面
	
Searchlayor   搜索中间层
	SearchDatabyCMD   根据GUI界面的输入信息搜索数据库
	
	
数据库结构说明:

一个车系对应一个数据表，当在某个数据表上追加数据的时候，每次都会调用去重函数

CarList 这个表记录了所有的车型信息，里边包含车型名称和对应的数据表，当删除某个车型的时候，要记得删除对应的数据

数据库的样本见protocol_car.sql

