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
		frame = new JFrame("��ѯ���ɸѡ");
		frame.setBounds(100, 100, 1100, 616);
		frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u9009\u62E9\u5BF9\u5E94\u7684\u6570\u636E\u6D41\u540D\u79F0");
		lblNewLabel.setBounds(150, 10, 183, 22);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 41, 553, 500);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		
		final JPanel panel_1 = new JPanel();
		
		
		
		JScrollPane scrollPane = new JScrollPane(panel_1);
		panel_1.setLayout(null);
		
		
	
		
		int height=6;//�����ѡ��ǰ��λ�ø߶�
		int BoxIndex=0;//��ѡ������
		//int CMDIndex=0;
		
		/*
		 * ��ʾ��������Ĳ�ѯ���
		 */
		for (int i=0;i<G_class_data.Serchresult.size();i++) {
			if(!(G_class_data.Serchresult.get(i).isEmpty()))
			{
				
				JLabel cmdname=new JLabel(i+".   "+G_class_data.Serchresult.get(i).get(0).command);//��ʾ��Ӧ������������
				cmdname.setForeground(Color.RED);
				cmdname.setBounds(6, height=height+25, 600, 23);
				panel_1.add(cmdname);
			}
			for (int j=0;j<G_class_data.Serchresult.get(i).size();j++) {   //�������Ӧ������������
				SelectBoxstate OneBoxtemp=new SelectBoxstate();//������ѡ�������ݴ�����
				OneBoxtemp.CMD_index=i;
				OneBoxtemp.Strname_index=j;
				OneBoxtemp.checkBox=new JCheckBox(AddSpase(50,G_class_data.Serchresult.get(i).get(j).datastreamname)+"    ƫ������"+AddSpase(10,G_class_data.Serchresult.get(i).get(j).byteoffset)+"�ֳ���"+AddSpase(10,G_class_data.Serchresult.get(i).get(j).bytenum));
				OneBoxtemp.checkBox.setBounds(6, height=height+25, 600, 23);
				panel_1.add(OneBoxtemp.checkBox);
				G_data.selectBoxList.add(OneBoxtemp);
			}
		}
		
		//���������ĸ߶ȣ��ſ�������
		panel_1.setPreferredSize(new Dimension(490,height));
		
		scrollPane.setBounds(10, 10, 500, 480);
		panel.add(scrollPane);
		
		
		
		
		/*
		 * ������ǰѡ�еĲ�ѯ���
		 */
		JButton button = new JButton("\u5BFC\u51FA\u6570\u636E\u6D41");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<DataStructXml> FainalChoiceResult=new ArrayList<DataStructXml>();
				
				if(G_data.FilebyStrnamelist.isEmpty())//���ͨ����������ɸѡ�����������Ϊ��
				{
					for(int i=0;i<G_data.selectBoxList.size();i++)//�Ǿ͵����ֶ�ѡ��Ľ��
					{
						if(G_data.selectBoxList.get(i).checkBox.isSelected())//ѭ����ѯ��ѡ���Ƿ�ѡ��
						{
							FainalChoiceResult.add(G_class_data.Serchresult.get(G_data.selectBoxList.get(i).CMD_index).get(G_data.selectBoxList.get(i).Strname_index));
						}
					}
				}
				else//ͨ���������������Ƽ���ɸѡ�Ľ��
				{
					for(int i=0;i<G_data.FilebyStrnamelist.size();i++)
					{
						if(G_data.FilebyStrnamelist.get(i).checkBox.isSelected())//�Ǿ͵���
						{
						
							FainalChoiceResult.add(G_class_data.Serchresult.get(G_data.FilebyStrnamelist.get(i).CMD_index).get(G_data.FilebyStrnamelist.get(i).Strname_index));
						
						}
					}
				}
				
				//�����ļ�
				Exportfile exportfile=new Exportfile();
				if(exportfile.Export(FainalChoiceResult))
				{
					JOptionPane.showMessageDialog(null,"�ļ��Ѿ�����");
				}
				
				
			}
		});
		button.setBounds(20, 545, 112, 23);
		frame.getContentPane().add(button);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(573, 41, 501, 500);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		
		
		//�����������������Ƽ���ɸѡ
		final JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		JScrollPane scrollPane1 = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane1.setBounds(10, 10, 481, 480);
		panel_2.add(scrollPane1);
		
		JLabel label_1 = new JLabel("\u8F93\u5165\u6570\u636E\u6D41\u4E2D\u6587\u540D\u79F0\u52A0\u4EE5\u7B5B\u9009");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		label_1.setBounds(699, 14, 259, 15);
		frame.getContentPane().add(label_1);
		
		//��ʼɸѡ
		JButton button_1 = new JButton("\u5F00\u59CB\u7B5B\u9009");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_1.removeAll();
				panel_1.repaint();
				SearchRsultByDataStreamNmae(textArea.getText(),panel_1);//ɸѡ������
			}
		});
		button_1.setBounds(583, 545, 93, 23);
		
		frame.getContentPane().add(button_1);
		

		
	}
	
	/*
	 * �������������������ٴ�ɸѡ������
	 * 
	 * 
	 */
	static private void SearchRsultByDataStreamNmae(String str,JPanel panel_1){
		//��ȡ����������
		String inputDataStreamName=str;
		String strtemp;
		//List<String> dataStreamnaemList=new ArrayList<String>();//����������������������ƣ�����������
		//System.out.print(str);
		try {
			//��string����ת��Ϊ�ֽ����飬������ȡ��ʽΪUTF-8��������������
			ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(inputDataStreamName.getBytes("UTF-8"));
			InputStreamReader re=new InputStreamReader(tInputStringStream,"UTF-8");
			BufferedReader reader=new BufferedReader(re);
			
			while(reader.ready())
			{//��ȡÿһ�����ݲ��Ҵ�������
				china_datastream_name strtemp1=new china_datastream_name();
				strtemp1.Datastreamname=reader.readLine().replaceAll("[\\s]{0,9}","");//ȥ�����еĿո�
				strtemp1.IfMatchCMD=false;
				G_data.china_datastream_name_list.add(strtemp1);
			}
			
			
			int height=6;//�����ѡ��ǰ��λ�ø߶�
			int BoxIndex=0;//��ѡ������
			//��ʼ�ڲ��ҽ���и������������������ٴ�ɸѡ
			//System.out.println(G_class_data.Serchresult.size());
			for(int i=0;i<G_class_data.Serchresult.size();i++)
			{
				//System.out.println(i);
				if(!(G_class_data.Serchresult.get(i).isEmpty()))
				{
				JLabel cmdname=new JLabel(i+".   "+G_class_data.Serchresult.get(i).get(0).command);//��ʾ��Ӧ������������
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
							selectTemp.checkBox=new JCheckBox(G_class_data.Serchresult.get(i).get(j).datastreamname+"         ƫ������"+G_class_data.Serchresult.get(i).get(j).byteoffset+"    �ֳ���"+G_class_data.Serchresult.get(i).get(j).bytenum);
							selectTemp.checkBox.setBounds(6, height=height+25, 600, 23);
							panel_1.add(selectTemp.checkBox);
							G_data.FilebyStrnamelist.add(selectTemp);
							G_data.china_datastream_name_list.get(k).IfMatchCMD=true;//���ϱ�ǣ�֤���Ѿ��ҵ�
							break;
						}
						
					}
				}
				}
			}
			
			panel_1.setPreferredSize(new Dimension(490,height));
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("�����ڣ���������������ɸѡ-�����ı�������ʱ");
		}
		
		
	}
	
	
	
	/*
	 * ��ʽ���ַ���
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
 * ���帴ѡ��Ľṹ
 * 
 */
class SelectBoxstate
{
	int CMD_index;//��¼����ĵ�һ������
	int Strname_index;//��¼����ĵڶ�������,����֤��һ����ѡ��ѡ��ʱ�����ȡ������������������������
	JCheckBox checkBox;
}

class china_datastream_name// ����������������������ƣ�����ɸѡ��������
{
	String Datastreamname; //��������������
	boolean IfMatchCMD; //�Ƿ�ƥ�䵽����������ƥ�䵽��Ϊtrue
}

class G_data
{
	static List<SelectBoxstate> selectBoxList=new ArrayList<SelectBoxstate>();//ȫ����ѯ�����ʾ��ѡ���
	static List<SelectBoxstate> FilebyStrnamelist=new ArrayList<SelectBoxstate>();//������������������ɸѡ����ʾ��ѡ���
	static List<china_datastream_name>  china_datastream_name_list=new ArrayList<china_datastream_name>();
}





