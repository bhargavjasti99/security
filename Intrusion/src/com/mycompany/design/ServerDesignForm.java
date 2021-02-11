package com.mycompany.design;

import java.awt.Color;
import org.jvnet.substance.SubstanceLookAndFeel;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import org.jvnet.substance.SubstanceLookAndFeel;

import com.mycompany.logic.ServerLogic;
import com.mycompany.support.SystemProperties;

public class ServerDesignForm extends JFrame {
	public JLabel expressionLabel;
	private ServerLogic serverLogic;
	public JTextArea jtextArea;
	public static DefaultTableModel model;
	public static JTable table;
	public static JScrollPane jScrollPane ;
	private SystemProperties systemProperties = new SystemProperties();
	private String[] initValues = systemProperties.ServerValues();
	
	public static DefaultTableModel model1 ;
	public static JTable table1 ;
	public static JScrollPane jScrollPane1 ;
	public JPanel jp4;
	public JPanel jp5;
	public JTextField expressionText;
	public JTextField expressionValue;
	
	static
	{
		try
		{
			SubstanceLookAndFeel
			.setCurrentWatermark("org.jvnet.substance.watermark.SubstanceBinaryWatermark");
	SubstanceLookAndFeel
			.setCurrentTheme("org.jvnet.substance.theme.SubstanceInvertedTheme");
	SubstanceLookAndFeel
			.setCurrentGradientPainter("org.jvnet.substance.painter.SpecularGradientPainter");
	SubstanceLookAndFeel
			.setCurrentButtonShaper("org.jvnet.substance.button.ClassicButtonShaper");
	UIManager.setLookAndFeel(new SubstanceLookAndFeel());
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public void Design() {
		try {
			model = new DefaultTableModel();
			table = new JTable(model);
			jScrollPane = new JScrollPane(table);
			
			model1 = new DefaultTableModel();
			table1 = new JTable(model1);
			jScrollPane1 = new JScrollPane(table1);
			
			serverLogic = new ServerLogic(Integer.parseInt(initValues[5]), "whois", this, "initValues[2]",
					"initValues[4]", "nodeName");
			setTitle("Server");
			JPanel jp1 = new JPanel();
			JPanel jp2 = new JPanel();
			// JPanel jp3 = new JPanel();
			jp1.setLayout(null);
			jp2.setLayout(null);

			JPanel jp3 = Expression();
			// jp1.setBackground(Color.GRAY);
			JLabel jLabel = new JLabel("Received Message :");
			jLabel.setBounds(30, 10, 150, 50);
			jp1.add(jLabel);
			jp3.setBounds(0, 350, 300, 300);
			// jp3.setBackground(Color.BLUE);
			// jp3.add(jLabel);
			jp1.add(jp3);
			JLabel jLabel1 = new JLabel("Technical Details :");
			jLabel1.setBounds(30, 10, 150, 50);
			jp2.add(jLabel1);
			
			jp4 = NormalDesign();
			jp5 = AlgorithmDesign();
			//jp4.setBackground(Color.BLUE);
			 
			jp5.setLayout(null);
			jp5.setBounds(50, 60, 900, 300);
			jp2.add(jp5); 
			
			jp5.setVisible(false);
			 
			jp4.setLayout(null);
			jp4.setBounds(50, 60, 500, 300);
			jp2.add(jp4);
			
			jp4.setVisible(false);
			// jp1.setSize(100, 100);
			jtextArea = new JTextArea();
			jtextArea.setEditable(false);
			JScrollPane scrollPane = new JScrollPane(jtextArea,
					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollPane.setBounds(20, 60, 300, 240);
			jp1.add(scrollPane);

			JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
					false, jp1, jp2);
			splitPane.setOneTouchExpandable(true);
			splitPane.setDividerLocation(350);
			add(splitPane);

			setSize(1200, 700);
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension size = getSize();
			screenSize.height = screenSize.height / 2;
			screenSize.width = screenSize.width / 2;
			size.height = size.height / 2;
			size.width = size.width / 2;
			int y = screenSize.height - size.height;
			int x = screenSize.width - size.width;
			setLocation(x, y);

			setVisible(true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public JPanel Expression() {
		JPanel jp3 = new JPanel();
		try {
			jp3.setLayout(null);
			JLabel jLabel = new JLabel("Puzzle value's : ");
			jLabel.setBounds(20, 20, 200, 50);
			jp3.add(jLabel);
			JLabel jLabel1 = new JLabel("Generated value : ");
			jLabel1.setBounds(20, 80, 200, 50);
			jp3.add(jLabel1);
			expressionLabel = new JLabel();
			expressionLabel.setBounds(170, 30, 100, 30);
			jp3.add(expressionLabel);
			expressionText = new JTextField();
			expressionText.setBounds(170, 90, 100, 30);
			expressionText.setEditable(false);
			jp3.add(expressionText);
			JLabel jLabelResult = new JLabel("Generated Result : ");
			jLabelResult.setBounds(20, 130, 200, 50);
			jp3.add(jLabelResult);
			expressionValue = new JTextField();
			expressionValue.setBounds(170, 140, 100, 30);
			jp3.add(expressionValue);
			expressionValue.setEditable(false);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return jp3;
	}

	public JPanel AlgorithmDesign() {
		JPanel jp4 = new JPanel();
		try {
			
			jp4.setLayout(null);
			model.addColumn("Algorithm Type");
			model.addColumn("Secret key");
			model.addColumn("No of Packets");
			model.addColumn("Secret keypath");
			model.addColumn("Issued Time");
			model.addColumn("Resolved Time");
			model.addColumn("Packet's order");
			jScrollPane.setBounds(0, 50, 700, 250);
			jp4.add(jScrollPane);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jp4;
	}

	public JPanel NormalDesign() {
		JPanel jp4 = new JPanel();
		try {
			
			jp4.setLayout(null);
			model1.addColumn("Blocked IP");
			model1.addColumn("No of Packets");
			model1.addColumn("Blocked Packets");
			jScrollPane1.setBounds(0, 50, 400, 250);
			jp4.add(jScrollPane1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jp4;
	}
	public static void main(String[] args) {
		ServerDesignForm serverDesignForm = new ServerDesignForm();
		serverDesignForm.Design();
	}
}
