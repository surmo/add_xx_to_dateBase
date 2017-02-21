package GUIEnter;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


import javax.swing.JTextField;

import Driverlayor.CUDBtoDateBase;
import Driverlayor.DataStructCarList;
import Driverlayor.DataStructXml;
import Searchlayor.SearchDatabyCMD;

import javax.swing.JTextArea;
import javax.swing.JLabel;


import controllayor.ResolutionXml;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class domain {

	private JFrame frmXml;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					domain window = new domain();
					window.frmXml.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public domain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
	
		
		
		
		frmXml = new JFrame();
		frmXml.setTitle("XML\u6570\u636E\u6D41\u89E3\u6790\u5DE5\u5177");
		frmXml.setBounds(100, 100, 866, 568);
		frmXml.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmXml.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 35, 573, 33);
		frmXml.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u9009\u62E9\u89E3\u6790\u76EE\u5F55\uFF1A");
		lblNewLabel.setBounds(10, 10, 102, 15);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(98, 7, 271, 21);
		panel.add(textField);
		textField.setColumns(10);
		
		/*
		 * 选择解析目录
		 */
		JButton btnNewButton = new JButton("\u6D4F\u89C8");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser jfc=new JFileChooser();  
		        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  
		        jfc.showDialog(new JLabel(), "选择"); 
		        
		        File file=jfc.getSelectedFile();  
		        if(file.isDirectory()){
		        	textField.setText(file.getAbsolutePath());
		        }
		        else {
		        	
				}

			}
		});
		btnNewButton.setBounds(379, 6, 87, 23);
		panel.add(btnNewButton);
		
		
		
		
		
		//创建车型选择列表
		DefaultComboBoxModel SelectCarType = new DefaultComboBoxModel();

		//从数据库获取车型信息
		RexPathInfo rexPathInfo=new RexPathInfo();
		G_class_data.CarListList=rexPathInfo.GetCarList();
		
		//将车型信息组合成下拉选择元素
		for(int i=0;i<G_class_data.CarListList.size();i++)//添加列表元素
		{
			SelectCarType.addElement(G_class_data.CarListList.get(i).CarName);
		}
		
		//创建下拉选择框
		final JComboBox comboBox_1 = new JComboBox(SelectCarType);
		comboBox_1.setBounds(100, 7, 136, 21);
		frmXml.getContentPane().add(comboBox_1);
		
		

		
		
		//设置查询的方式选择框
		
		DefaultComboBoxModel SearchType = new DefaultComboBoxModel();
		
		//
		SearchType.addElement("根据命令查询");
		SearchType.addElement("根据命令查询(忽略CAN ID)");
		SearchType.addElement("根据数据流名称查询");
		
		final JComboBox comboBox = new JComboBox(SearchType);
		comboBox.setBounds(75,78,200,21);
		frmXml.getContentPane().add(comboBox);
		comboBox.setSelectedIndex(0);
		
		
		
		
		
		
		JPanel panel_1 = new JPanel();
		
		panel_1.setBounds(0, 109, 840, 378);
		frmXml.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel label = new JLabel("\u5728\u4E0B\u9762\u8F93\u5165\u6846\u4E2D\u8F93\u5165\u547D\u4EE4");
		label.setBounds(10, 8, 150, 15);
		panel_1.add(label);
		
		//查询命令输入框
		final JTextArea textArea=new JTextArea(3,20);
		textArea.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 32, 410, 336);
		panel_1.add(scrollPane);
		
		JLabel label_1 = new JLabel("\u67E5\u8BE2\u7ED3\u679C");
		label_1.setBounds(467, 8, 54, 15);
		panel_1.add(label_1);
		
		//查询结果显示框
		final JTextArea  textArea_1 = new JTextArea();
		textArea_1.setLineWrap(true);
		JScrollPane scrollPane_1 = new JScrollPane(textArea_1,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBounds(464, 32, 366, 336);
		panel_1.add(scrollPane_1);
		
		
		//开始解析按钮
		JButton btnNewButton_1 = new JButton("\u5F00\u59CB\u89E3\u6790");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {//开始解析xml文件
				int cartype=0;
				cartype=comboBox_1.getSelectedIndex();//获取当前选中的车型
				String CarTable=G_class_data.CarListList.get(cartype).CarTable;//根据选中索引出该车型对应的数据表
				if(!(textField.getText().equals("")))
				{
					String dirpath=textField.getText();//获取解析目录地址
					//这里创建一个新的线程解析，主线程负责显示输出框的内容
					New_thread rexThread=new New_thread("rexdata", dirpath, CarTable, textArea_1);
					rexThread.start();//启动这个线程
				}
			}
		});
		btnNewButton_1.setBounds(476, 6, 87, 23);
		panel.add(btnNewButton_1);
		
		
		
		
		//开始查询按钮
		JButton button = new JButton("\u5F00\u59CB\u67E5\u8BE2");
		button.addActionListener(new ActionListener() {//开始查询数据流命令
			public void actionPerformed(ActionEvent e) {
				
				int sel=0;
				int cartype=0;
				
				cartype=comboBox_1.getSelectedIndex();//获取当前选择的车型
				String CarTable=G_class_data.CarListList.get(cartype).CarTable;//根据索引选择该车型的数据表
				
				textArea_1.setText("");//清空查询结果显示框
				G_class_data.Serchresult.clear();//清空查询结果缓存数组
				
				String searchType;//查询方式
				String claumName;//数据处理之后要查询的栏位
				
				//获取查询方式
				 if (comboBox.getSelectedIndex() != -1) {                     
					 sel =comboBox.getSelectedIndex();      
		            }              
				switch (sel) {
				case 0://根据数据流命令
					searchType="command";
					claumName="command_no_0x";
					break;
				case 1://根据数据流命令 忽略 CAN ID
					searchType="command_no_canid";
					claumName="command_no_0x";
					break;
				case 2://根据数据中文名称 
					searchType="datastreamname";
					claumName="datastreamname";
					break;
				default:
					searchType="command";
					claumName="command_no_0x";
					break;
				}
				//以上为判断使用什么查询方式////////////////////////////
				String inpudata=textArea.getText();//获取输入的命令
				
				SearchDatabyCMD temp=new SearchDatabyCMD();
				//开始查询数据，一次查询多条
				G_class_data.Serchresult=temp.SearchMoredatabyCMD(CarTable,claumName,inpudata,searchType);
				StringBuffer all_result=new StringBuffer();
				
				//显示查询结果
				for (List<DataStructXml> list : G_class_data.Serchresult) {
					if(!list.isEmpty())//如果结果不为空才再截取数据
					{
						all_result.append(list.get(0).command);
						all_result.append("\n");
						for (DataStructXml dataStructXml : list)
						{
							all_result.append(dataStructXml.datastreamname);
							all_result.append("\n");
						}
					}
					else {
						;
					}
				}
				if(all_result.length()==0)//如果数据长度为0，则认为未匹配到数据
				{
					textArea_1.setText("未匹配到数据");
				}
				else {
					textArea_1.setText(all_result.toString());
				}
				
				
			}
		});
		
		
		button.setBounds(10, 497, 93, 23);
		frmXml.getContentPane().add(button);
		
		/*
		 * 结果筛选按钮
		 */
		JButton button_1 = new JButton("\u7ED3\u679C\u7B5B\u9009");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					G_data.FilebyStrnamelist.clear();//清除通过数据流中文名称查询出来的结果，因为后边要以这个结果判断导出哪一部分数据
					SelectSearchResul window = new SelectSearchResul();
					window.main(null);
				} catch (Exception d) {
					d.printStackTrace();
				}
				
			}
		});
		button_1.setBounds(463, 497, 93, 23);
		frmXml.getContentPane().add(button_1);
		
		
		
		
		JLabel label_2 = new JLabel("\u67E5\u8BE2\u65B9\u5F0F\uFF1A");
		label_2.setBounds(10, 78, 80, 15);
		frmXml.getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("\u9009\u62E9\u89E3\u6790\u8F66\u578B");
		label_3.setBounds(10, 10, 80, 15);
		frmXml.getContentPane().add(label_3);
		
		
		
		//新增车型按钮
		JButton button_2 = new JButton("\u65B0\u589E\u8F66\u578B");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		button_2.setBounds(378, 6, 93, 23);
		frmXml.getContentPane().add(button_2);
		
		
		
	}
}

//保存第一次查询的结果
class G_class_data{
	static List<List<DataStructXml>> Serchresult=new ArrayList<List<DataStructXml>>();//数据流查询结果
	static List<DataStructCarList> CarListList=new ArrayList<DataStructCarList>();//保存从数据库查询出来的车型列表信息
	
	
}

/*
 * 新建一个线程来解析XML数据，主线程负责刷新文本框
 */
class New_thread extends Thread
{
	String dirpath;
	String cartype;
	JTextArea	showinfo;
	//获取要执行的参数
	 New_thread(String Nmaes,String dirpaths,String cartypes,JTextArea showinfos) {
		super(Nmaes);
		dirpath=dirpaths;
		cartype=cartypes;
		showinfo=showinfos;
	}
	 public void run()
	 {
		 	ResolutionXml res=new ResolutionXml();
			res.showInit(showinfo);
			res.Resolutionxmlbypath(cartype,dirpath);
			CUDBtoDateBase.delectmore(cartype);//删除重复信息
	 }
}



