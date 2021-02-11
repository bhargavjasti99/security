package com.mycompany.design;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import org.jvnet.substance.SubstanceLookAndFeel;

import com.mycompany.logic.ServerLogic;
import com.mycompany.support.SystemProperties;

public class IntermediateServer extends JFrame implements ActionListener {
	public static DefaultTableModel model;
	public static JTable table;
	public static JScrollPane jScrollPane ;
	private SystemProperties systemProperties = new SystemProperties();
	private String[] initValues = systemProperties.ServerValues();
	private ServerLogic serverLogic;
	private String whois;
	private String nodeName = systemProperties.RandomName();
	int port;
	private SignalJPanel signalJPanel;
	private JRadioButton jRadioButtonBlock;
	private JRadioButton jRadioButtonUnBlock;
	public String mode = "UnBlock";

	static {
		try {
			SubstanceLookAndFeel
					.setCurrentWatermark("org.jvnet.substance.watermark.SubstanceBinaryWatermark");
			SubstanceLookAndFeel
					.setCurrentTheme("org.jvnet.substance.theme.SubstanceInvertedTheme");
			SubstanceLookAndFeel
					.setCurrentGradientPainter("org.jvnet.substance.painter.SpecularGradientPainter");
			SubstanceLookAndFeel
					.setCurrentButtonShaper("org.jvnet.substance.button.ClassicButtonShaper");
			UIManager.setLookAndFeel(new SubstanceLookAndFeel());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public IntermediateServer() {
		// TODO Auto-generated constructor stub
		try {
			model = new DefaultTableModel();
			table = new JTable(model);
			jScrollPane = new JScrollPane(table);
			whois = Inet4Address.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		port = Integer.parseInt(initValues[4]);
		// systemProperties = new SystemProperties();
		initValues = systemProperties.ServerValues();
		serverLogic = new ServerLogic(port, whois, this, initValues[2],
				initValues[4], nodeName);
		try {
			signalJPanel = new SignalJPanel(serverLogic, this);
			Thread t = new Thread(signalJPanel);
			t.start();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void Main() {
		try {
			JFrame frame = new JFrame("Intermediate Server");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JTabbedPane tab = new JTabbedPane();
			frame.add(tab, BorderLayout.CENTER);
			JButton button = new JButton("1");
			tab.add("Packet Details", Design());
			button = new JButton("2");
			tab.add("Traffic Signal", signalJPanel);
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension size = getSize();
			screenSize.height = screenSize.height / 2;
			screenSize.width = screenSize.width / 2;
			size.height = size.height / 2;
			size.width = size.width / 2;
			int y = screenSize.height - size.height;
			int x = screenSize.width - size.width;
			frame.setLocation(x - 300, y - 300);
			frame.setSize(600, 600);
			frame.setVisible(true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public JPanel Design() {
		JPanel jPanel = new JPanel();
		try {

			jPanel.setLayout(null);
			jRadioButtonBlock = new JRadioButton("Block");
			jRadioButtonUnBlock = new JRadioButton("UnBlock", true);
			// setSize(600, 600);
			model.addColumn("Source IP");
			model.addColumn("Destination IP");
			model.addColumn("Seq No");
			model.addColumn("Status");
			jScrollPane.setBounds(80, 50, 450, 100);
			jRadioButtonBlock.setBounds(80, 200, 100, 30);
			jRadioButtonUnBlock.setBounds(80, 230, 100, 30);
			jPanel.add(jRadioButtonBlock);
			jPanel.add(jRadioButtonUnBlock);
			jRadioButtonBlock.addActionListener(this);
			jRadioButtonUnBlock.addActionListener(this);
			jPanel.add(jScrollPane);
			jPanel.setVisible(true);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return jPanel;
	}

	public static void main(String[] args) {
		IntermediateServer intermediateServer = new IntermediateServer();
		intermediateServer.Main();
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == jRadioButtonBlock) {
			mode = jRadioButtonBlock.getText();
			jRadioButtonUnBlock.setSelected(false);
		} else if (e.getSource() == jRadioButtonUnBlock) {
			mode = jRadioButtonUnBlock.getText();
			jRadioButtonBlock.setSelected(false);
			//jRadioButtonBlock.setSelected(false);
		}

	}
}
