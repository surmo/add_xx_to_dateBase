package facetest;

import Driverlayor.*;
import ExportData.ConstString;
import ExportData.Exportfile;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Panel;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.mysql.fabric.xmlrpc.base.Array;
import com.sun.java.swing.plaf.windows.resources.windows;

import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToggleButton;
import javax.swing.JComboBox;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class SelectSearchResul {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectSearchResul window = new SelectSearchResul();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SelectSearchResul() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("查询结果筛选");
		frame.setBounds(100, 100, 1100, 616);
		frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u9009\u62E9\u5BF9\u5E94\u7684\u6570\u636E\u6D41\u540D\u79F0");
		lblNewLabel.setBounds(150, 10, 183, 22);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 41, 553, 500);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		
		final JPanel panel_1 = new JPanel();
		
		
		
		JScrollPane scrollPane = new JScrollPane(panel_1);
		panel_1.setLayout(null);
		
		
	
		
		int height=6;//保存多选框当前的位置高度
		int BoxIndex=0;//多选框索引
		//int CMDIndex=0;
		
		/*
		 * 显示根据命令的查询结果
		 */
		for (int i=0;i<G_class_data.Serchresult.size();i++) {
			if(!(G_class_data.Serchresult.get(i).isEmpty()))
			{
				
				JLabel cmdname=new JLabel(i+".   "+G_class_data.Serchresult.get(i).get(0).command);//显示对应的数据流命令
				cmdname.setForeground(Color.RED);
				cmdname.setBounds(6, height=height+25, 600, 23);
				panel_1.add(cmdname);
			}
			for (int j=0;j<G_class_data.Serchresult.get(i).size();j++) {   //该命令对应的数据流名称
				SelectBoxstate OneBoxtemp=new SelectBoxstate();//单个复选框，用来暂存数据
				OneBoxtemp.CMD_index=i;
				OneBoxtemp.Strname_index=j;
				OneBoxtemp.checkBox=new JCheckBox(AddSpase(50,G_class_data.Serchresult.get(i).get(j).datastreamname)+"    偏移量："+AddSpase(10,G_class_data.Serchresult.get(i).get(j).byteoffset)+"字长："+AddSpase(10,G_class_data.Serchresult.get(i).get(j).bytenum));
				OneBoxtemp.checkBox.setBounds(6, height=height+25, 600, 23);
				panel_1.add(OneBoxtemp.checkBox);
				G_data.selectBoxList.add(OneBoxtemp);
			}
		}
		
		//设置容器的高度，撑开滚动框
		panel_1.setPreferredSize(new Dimension(490,height));
		
		scrollPane.setBounds(10, 10, 500, 480);
		panel.add(scrollPane);
		
		
		
		
		/*
		 * 导出当前选中的查询结果
		 */
		JButton button = new JButton("\u5BFC\u51FA\u6570\u636E\u6D41");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<DataStructXml> FainalChoiceResult=new ArrayList<DataStructXml>();
				
				if(G_data.FilebyStrnamelist.isEmpty())//如果通过中文名称筛选的数据流结果为空
				{
					for(int i=0;i<G_data.selectBoxList.size();i++)//那就导出手动选择的结果
					{
						if(G_data.selectBoxList.get(i).checkBox.isSelected())//循环查询复选框是否被选中
						{
							FainalChoiceResult.add(G_class_data.Serchresult.get(G_data.selectBoxList.get(i).CMD_index).get(G_data.selectBoxList.get(i).Strname_index));
						}
					}
				}
				else//通过数据流中文名称加以筛选的结果
				{
					for(int i=0;i<G_data.FilebyStrnamelist.size();i++)
					{
						if(G_data.FilebyStrnamelist.get(i).checkBox.isSelected())//那就导出
						{
						
							FainalChoiceResult.add(G_class_data.Serchresult.get(G_data.FilebyStrnamelist.get(i).CMD_index).get(G_data.FilebyStrnamelist.get(i).Strname_index));
						
						}
					}
				}
				
				//导出文件
				Exportfile exportfile=new Exportfile();
				if(exportfile.Export(FainalChoiceResult))
				{
					JOptionPane.showMessageDialog(null,"文件已经生成");
				}
				
				
			}
		});
		button.setBounds(20, 545, 112, 23);
		frame.getContentPane().add(button);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(573, 41, 501, 500);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		
		
		//输入数据流中文名称加以筛选
		final JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		JScrollPane scrollPane1 = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane1.setBounds(10, 10, 481, 480);
		panel_2.add(scrollPane1);
		
		JLabel label_1 = new JLabel("\u8F93\u5165\u6570\u636E\u6D41\u4E2D\u6587\u540D\u79F0\u52A0\u4EE5\u7B5B\u9009");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		label_1.setBounds(699, 14, 259, 15);
		frame.getContentPane().add(label_1);
		
		//开始筛选
		JButton button_1 = new JButton("\u5F00\u59CB\u7B5B\u9009");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_1.removeAll();
				panel_1.repaint();
				SearchRsultByDataStreamNmae(textArea.getText(),panel_1);//筛选数据流
			}
		});
		button_1.setBounds(583, 545, 93, 23);
		
		frame.getContentPane().add(button_1);
		

		
	}
	
	/*
	 * 根据数据流中文名称再次筛选数据流
	 * 
	 * 
	 */
	static private void SearchRsultByDataStreamNmae(String str,JPanel panel_1){
		//获取输入框的内容
		String inputDataStreamName=str;
		String strtemp;
		//List<String> dataStreamnaemList=new ArrayList<String>();//保存输入的数据流中文名称，存在数组中
		//System.out.print(str);
		try {
			//将string对象转换为字节数组，声明读取方式为UTF-8，否则会出现乱码
			ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(inputDataStreamName.getBytes("UTF-8"));
			InputStreamReader re=new InputStreamReader(tInputStringStream,"UTF-8");
			BufferedReader reader=new BufferedReader(re);
			
			while(reader.ready())
			{//读取每一行数据并且存入数组
				china_datastream_name strtemp1=new china_datastream_name();
				strtemp1.Datastreamname=reader.readLine().replaceAll("[\\s]{0,9}","");//去掉所有的空格，
				strtemp1.IfMatchCMD=false;
				G_data.china_datastream_name_list.add(strtemp1);
			}
			
			
			int height=6;//保存多选框当前的位置高度
			int BoxIndex=0;//多选框索引
			//开始在查找结果中根据数据流中文名称再次筛选
			//System.out.println(G_class_data.Serchresult.size());
			for(int i=0;i<G_class_data.Serchresult.size();i++)
			{
				//System.out.println(i);
				if(!(G_class_data.Serchresult.get(i).isEmpty()))
				{
				JLabel cmdname=new JLabel(i+".   "+G_class_data.Serchresult.get(i).get(0).command);//显示对应的数据流命令
				cmdname.setForeground(Color.RED);
				cmdname.setBounds(6, height=height+25, 600, 23);
				panel_1.add(cmdname);
				for(int j=0;j<G_class_data.Serchresult.get(i).size();j++)
				{
					for(int k=0;k<G_data.china_datastream_name_list.size();k++)
					{
						if(G_data.china_datastream_name_list.get(k).Datastreamname.equals(G_class_data.Serchresult.get(i).get(j).datastreamname))
						{
						
							SelectBoxstate selectTemp=new SelectBoxstate();
							selectTemp.CMD_index=i;
							selectTemp.Strname_index=j;
							selectTemp.checkBox=new JCheckBox(G_class_data.Serchresult.get(i).get(j).datastreamname+"         偏移量："+G_class_data.Serchresult.get(i).get(j).byteoffset+"    字长："+G_class_data.Serchresult.get(i).get(j).bytenum);
							selectTemp.checkBox.setBounds(6, height=height+25, 600, 23);
							panel_1.add(selectTemp.checkBox);
							G_data.FilebyStrnamelist.add(selectTemp);
							G_data.china_datastream_name_list.get(k).IfMatchCMD=true;//打上标记，证明已经找到
							break;
						}
						
					}
				}
				}
			}
			
			panel_1.setPreferredSize(new Dimension(490,height));
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("发生在：数据流中文名称筛选-解析文本框内容时");
		}
		
		
	}
	
	
	
	/*
	 * 格式化字符串
	 */
	
static public String AddSpase(int DesStrLen,String srcstr) {
 		
 		String temp=null;
 		String Zerotmp="";
 		try {
 			int srcstrlen=srcstr.getBytes("UTF-8").length;
 	 		
 	 		
 	 		if(DesStrLen>=srcstrlen)
 	 		{
 	 			for(int i=0;i<DesStrLen-srcstrlen;i++)
 	 	 		{
 	 	 			Zerotmp+=" ";
 	 	 		}
 	 			
 	 			temp=srcstr+Zerotmp;
 	 		}
 	 		
 	 		else {
 				temp=srcstr;
 			}
 	 		
		} catch (Exception e) {
		e.printStackTrace();
		}
 		
 		return temp;
 	}
	
	
}




/*
 * 定义复选框的结构
 * 
 */
class SelectBoxstate
{
	int CMD_index;//记录数组的第一个索引
	int Strname_index;//记录数组的第二个索引,当验证那一个复选框被选中时，会读取这两个索引用来导出数据流
	JCheckBox checkBox;
}

class china_datastream_name// 保存输入的数据流中文名称，用来筛选数据流的
{
	String Datastreamname; //数据流中文名称
	boolean IfMatchCMD; //是否匹配到了数据流，匹配到了为true
}

class G_data
{
	static List<SelectBoxstate> selectBoxList=new ArrayList<SelectBoxstate>();//全部查询结果显示的选择框
	static List<SelectBoxstate> FilebyStrnamelist=new ArrayList<SelectBoxstate>();//经过数据流中文名称筛选后显示的选择框
	static List<china_datastream_name>  china_datastream_name_list=new ArrayList<china_datastream_name>();
}





