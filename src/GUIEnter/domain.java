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
		 * ѡ�����Ŀ¼
		 */
		JButton btnNewButton = new JButton("\u6D4F\u89C8");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser jfc=new JFileChooser();  
		        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  
		        jfc.showDialog(new JLabel(), "ѡ��"); 
		        
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
		
		
		
		
		
		//��������ѡ���б�
		DefaultComboBoxModel SelectCarType = new DefaultComboBoxModel();

		//�����ݿ��ȡ������Ϣ
		RexPathInfo rexPathInfo=new RexPathInfo();
		G_class_data.CarListList=rexPathInfo.GetCarList();
		
		//��������Ϣ��ϳ�����ѡ��Ԫ��
		for(int i=0;i<G_class_data.CarListList.size();i++)//����б�Ԫ��
		{
			SelectCarType.addElement(G_class_data.CarListList.get(i).CarName);
		}
		
		//��������ѡ���
		final JComboBox comboBox_1 = new JComboBox(SelectCarType);
		comboBox_1.setBounds(100, 7, 136, 21);
		frmXml.getContentPane().add(comboBox_1);
		
		

		
		
		//���ò�ѯ�ķ�ʽѡ���
		
		DefaultComboBoxModel SearchType = new DefaultComboBoxModel();
		
		//
		SearchType.addElement("���������ѯ");
		SearchType.addElement("���������ѯ(����CAN ID)");
		SearchType.addElement("�������������Ʋ�ѯ");
		
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
		
		//��ѯ���������
		final JTextArea textArea=new JTextArea(3,20);
		textArea.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 32, 410, 336);
		panel_1.add(scrollPane);
		
		JLabel label_1 = new JLabel("\u67E5\u8BE2\u7ED3\u679C");
		label_1.setBounds(467, 8, 54, 15);
		panel_1.add(label_1);
		
		//��ѯ�����ʾ��
		final JTextArea  textArea_1 = new JTextArea();
		textArea_1.setLineWrap(true);
		JScrollPane scrollPane_1 = new JScrollPane(textArea_1,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBounds(464, 32, 366, 336);
		panel_1.add(scrollPane_1);
		
		
		//��ʼ������ť
		JButton btnNewButton_1 = new JButton("\u5F00\u59CB\u89E3\u6790");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {//��ʼ����xml�ļ�
				int cartype=0;
				cartype=comboBox_1.getSelectedIndex();//��ȡ��ǰѡ�еĳ���
				String CarTable=G_class_data.CarListList.get(cartype).CarTable;//����ѡ���������ó��Ͷ�Ӧ�����ݱ�
				if(!(textField.getText().equals("")))
				{
					String dirpath=textField.getText();//��ȡ����Ŀ¼��ַ
					//���ﴴ��һ���µ��߳̽��������̸߳�����ʾ����������
					New_thread rexThread=new New_thread("rexdata", dirpath, CarTable, textArea_1);
					rexThread.start();//��������߳�
				}
			}
		});
		btnNewButton_1.setBounds(476, 6, 87, 23);
		panel.add(btnNewButton_1);
		
		
		
		
		//��ʼ��ѯ��ť
		JButton button = new JButton("\u5F00\u59CB\u67E5\u8BE2");
		button.addActionListener(new ActionListener() {//��ʼ��ѯ����������
			public void actionPerformed(ActionEvent e) {
				
				int sel=0;
				int cartype=0;
				
				cartype=comboBox_1.getSelectedIndex();//��ȡ��ǰѡ��ĳ���
				String CarTable=G_class_data.CarListList.get(cartype).CarTable;//��������ѡ��ó��͵����ݱ�
				
				textArea_1.setText("");//��ղ�ѯ�����ʾ��
				G_class_data.Serchresult.clear();//��ղ�ѯ�����������
				
				String searchType;//��ѯ��ʽ
				String claumName;//���ݴ���֮��Ҫ��ѯ����λ
				
				//��ȡ��ѯ��ʽ
				 if (comboBox.getSelectedIndex() != -1) {                     
					 sel =comboBox.getSelectedIndex();      
		            }              
				switch (sel) {
				case 0://��������������
					searchType="command";
					claumName="command_no_0x";
					break;
				case 1://�������������� ���� CAN ID
					searchType="command_no_canid";
					claumName="command_no_0x";
					break;
				case 2://���������������� 
					searchType="datastreamname";
					claumName="datastreamname";
					break;
				default:
					searchType="command";
					claumName="command_no_0x";
					break;
				}
				//����Ϊ�ж�ʹ��ʲô��ѯ��ʽ////////////////////////////
				String inpudata=textArea.getText();//��ȡ���������
				
				SearchDatabyCMD temp=new SearchDatabyCMD();
				//��ʼ��ѯ���ݣ�һ�β�ѯ����
				G_class_data.Serchresult=temp.SearchMoredatabyCMD(CarTable,claumName,inpudata,searchType);
				StringBuffer all_result=new StringBuffer();
				
				//��ʾ��ѯ���
				for (List<DataStructXml> list : G_class_data.Serchresult) {
					if(!list.isEmpty())//��������Ϊ�ղ��ٽ�ȡ����
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
				if(all_result.length()==0)//������ݳ���Ϊ0������Ϊδƥ�䵽����
				{
					textArea_1.setText("δƥ�䵽����");
				}
				else {
					textArea_1.setText(all_result.toString());
				}
				
				
			}
		});
		
		
		button.setBounds(10, 497, 93, 23);
		frmXml.getContentPane().add(button);
		
		/*
		 * ���ɸѡ��ť
		 */
		JButton button_1 = new JButton("\u7ED3\u679C\u7B5B\u9009");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					G_data.FilebyStrnamelist.clear();//���ͨ���������������Ʋ�ѯ�����Ľ������Ϊ���Ҫ���������жϵ�����һ��������
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
		
		
		
		//�������Ͱ�ť
		JButton button_2 = new JButton("\u65B0\u589E\u8F66\u578B");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		button_2.setBounds(378, 6, 93, 23);
		frmXml.getContentPane().add(button_2);
		
		
		
	}
}

//�����һ�β�ѯ�Ľ��
class G_class_data{
	static List<List<DataStructXml>> Serchresult=new ArrayList<List<DataStructXml>>();//��������ѯ���
	static List<DataStructCarList> CarListList=new ArrayList<DataStructCarList>();//��������ݿ��ѯ�����ĳ����б���Ϣ
	
	
}

/*
 * �½�һ���߳�������XML���ݣ����̸߳���ˢ���ı���
 */
class New_thread extends Thread
{
	String dirpath;
	String cartype;
	JTextArea	showinfo;
	//��ȡҪִ�еĲ���
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
			CUDBtoDateBase.delectmore(cartype);//ɾ���ظ���Ϣ
	 }
}



