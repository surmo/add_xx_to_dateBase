package GUIllayor;

import java.awt.*;
import javax.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import sun.net.www.content.text.plain;

public class inpudata {

	
	public  void FrameDemo(){
		JFrame faram=new JFrame("Frame Demo");
		JTextArea textArea=new JTextArea(3,20);
		textArea.setLineWrap(true);
		Color bg=new Color(1234);
		JScrollPane pane=new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		JLabel label=new JLabel("多行文本");
		JPanel jPanel=new JPanel();
		
		label.setBounds(10, 10, 30, 30);
		jPanel.add(label);
		faram.setLayout(new GridLayout(2,1));
		
		faram.add(jPanel);
		faram.add(pane);
		
		//textArea.setBackground(bg);
		faram.setSize(500,150);
		faram.setLocation(100,200);
		
//		Button button=new Button("login");
//		button.setSize(50,50);
//		
//	
//		
//		JPanel panel1=new JPanel();
//		panel1.setSize(200,50);
//		
//		faram.add(panel1);
//		
//		panel1.add(button);
//		
		
	
		faram.setVisible(true);
		faram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String args[])
	{
		inpudata temp=new inpudata();
		temp.FrameDemo();
		
	}

}
