package com.mycompany.design;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.StringBufferInputStream;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.jvnet.substance.SubstanceLookAndFeel;

import com.mycompany.logic.Multicst;
import com.mycompany.logic.Receiver;
import com.mycompany.logic.ServerLogic;
import com.mycompany.serial.DataSendSerial;
import com.mycompany.support.Mobility;
import com.mycompany.support.Constants;
import com.mycompany.support.SocketConnection;
import com.mycompany.support.SplitDetails;
import com.mycompany.support.SystemProperties;
import com.mycompany.valueobject.MobilityValueObject;

public class NodeDesignForm extends JFrame implements ActionListener {
	public JTextArea jTextAreaNeigh = new JTextArea();
	private SystemProperties systemProperties = new SystemProperties();
	String[] initValues = systemProperties.ServerValues();
	private int distance = Integer.parseInt(initValues[0]);
	private String region = initValues[1];
	private int port = systemProperties.RandomPort();
	private String whois;
	private String nodeName = systemProperties.RandomName();
	private JLabel jLabelDistance = new JLabel();
	public JProgressBar pb = new JProgressBar();
	private Mobility mobility;
	private JButton electionJButton;
	private JButton sendJButton;
	private MobilityValueObject mobilityValueObject;
	private Receiver receiver;
	private Multicst multicast;
	private SplitDetails splitDetails;
	private SocketConnection socketConnection;
	private ServerLogic serverLogic;
	public JLabel leaderNode;
	private SecretKey key;
	private SealedObject sealedObject;
	private String mobilityNode = "";
	JButton openButton;
	final JFileChooser fc = new JFileChooser();
	private JTextField jTextField;
	private JTextArea jtextArea;
	private JLabel expressionLabel;
	private JButton addButton;
	private JButton subButton;
	private JButton mulButton;
	private JButton divButton;
	private JButton expressionButton;
	private JTextField expressionText;
	private JTextField expressionValue;
	private int incre;
	String expressionRandom = "";
	String expressionDerive = "";
	String[] expressionArr;
	int expValue;
	private JRadioButton radioButtonshcs;
	private JRadioButton radioButtoncrypt;
	private JRadioButton radioButtoncphs;
	private JRadioButton radioButtonNormalMode;
	String algorithmType = "";
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

	public NodeDesignForm() {
		// TODO Auto-generated constructor stub
		try {
			whois = Inet4Address.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		socketConnection = new SocketConnection();
		splitDetails = new SplitDetails();
		mobilityValueObject = new MobilityValueObject();
		mobility = new Mobility(nodeName, this, mobilityValueObject);
		serverLogic = new ServerLogic(port, whois, this, initValues[2],
				initValues[4], nodeName);
	}

	public void Design() {
		try {
			// setLayout(null);
			setTitle(nodeName);
			/* Neighbor */
			JPanel jp1 = new JPanel();
			JPanel jp2 = new JPanel();
			// JPanel jp3 = new JPanel();
			jp1.setLayout(null);
			jp2.setLayout(null);
			// jp3.setLayout(null);
			// jp3.add(Expression());
			JLabel jLabel = new JLabel("File Name :");
			jLabel.setBounds(30, 10, 100, 50);
			jp1.add(jLabel);
			// jp3.add(jLabel);
			jTextField = new JTextField();
			jTextField.setBounds(110, 20, 100, 30);
			jp1.add(jTextField);
			jTextField.setEditable(false);

			openButton = new JButton("Open Dialog");
			openButton.setBounds(80, 90, 120, 30);
			jp1.add(openButton);
			openButton.addActionListener(this);

			jtextArea = new JTextArea();

			JScrollPane scrollPane = new JScrollPane(jtextArea,
					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollPane.setBounds(20, 170, 400, 250);
			jp1.add(scrollPane);

			JLabel jLabelNeigh = new JLabel("Neighbour Node's :", JLabel.LEFT);
			jLabelNeigh.setBounds(600, 10, 150, 60);
			jp2.add(jLabelNeigh);
			// jsp1.add(j1);
			// jp1.add(jLabelNeigh);
			// jp1.show();
			// jp1.setSize(350, 400);
			JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
					false, jp1, jp2);
			splitPane.setOneTouchExpandable(true);
			splitPane.setDividerLocation(450);
			add(splitPane);
			JSplitPane splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
					false, splitPane, Expression());
			splitPane1.setOneTouchExpandable(true);
			splitPane1.setDividerLocation(450);
			add(splitPane1);
			jLabelDistance.setText("Distance : " + distance);
			jLabelDistance.setBounds(40, 10, 100, 50);
			jp2.add(jLabelDistance);

			JScrollPane jScrollPane = new JScrollPane(jTextAreaNeigh);
			jScrollPane.setBounds(600, 70, 200, 200);
			jp2.add(jScrollPane);

			pb = new JProgressBar(); // pb.setValue(0);
			pb.setStringPainted(true);
			pb.setBounds(300, 35, 180, 30);
			jp2.add(pb);

			electionJButton = new JButton("Leader Election");
			electionJButton.setBounds(30, 120, 130, 30);

			jp2.add(electionJButton);
			electionJButton.addActionListener(this);

			sendJButton = new JButton("Send");
			sendJButton.setBounds(200, 120, 130, 30);
			jp2.add(sendJButton);
			sendJButton.addActionListener(this);

			leaderNode = new JLabel();
			leaderNode.setBounds(10, 70, 130, 30);
			jp2.add(leaderNode);

			/*
			 * JLabel jLabelNeigh = new JLabel("Neighbour Node's :",
			 * JLabel.LEFT); jLabelNeigh.setBounds(350, 10, 150, 60);
			 * jp1.add(jLabelNeigh);
			 * 
			 * JScrollPane jScrollPane = new JScrollPane(jTextAreaNeigh);
			 * jScrollPane.setBounds(350, 70, 200, 200); add(jScrollPane);
			 * 
			 * Distance
			 * 
			 * jLabelDistance.setText("Distance : " + distance);
			 * jLabelDistance.setBounds(10, 10, 100, 50); add(jLabelDistance);
			 * 
			 * Jprogressbar
			 * 
			 * pb = new JProgressBar(); // pb.setValue(0);
			 * pb.setStringPainted(true); pb.setBounds(170, 35, 130, 30);
			 * add(pb);
			 * 
			 * Electleader button electionJButton = new JButton("Leader
			 * Election"); electionJButton.setBounds(10, 120, 130, 30);
			 * add(electionJButton); electionJButton.addActionListener(this);
			 * 
			 * Send button
			 * 
			 * sendJButton = new JButton("Send"); sendJButton.setBounds(100,
			 * 120, 130, 30); add(sendJButton);
			 * sendJButton.addActionListener(this);
			 * 
			 * Leader Node leaderNode = new JLabel(); leaderNode.setBounds(10,
			 * 70, 130, 30); add(leaderNode);
			 */

			radioButtoncphs = new JRadioButton(Constants.cphs);
			radioButtoncrypt = new JRadioButton(Constants.crypt);
			radioButtonshcs = new JRadioButton(Constants.shcs);
			radioButtonNormalMode = new JRadioButton("Normal Mode");
			radioButtoncphs.setBounds(30, 200, 400, 30);
			radioButtoncrypt.setBounds(30, 240, 400, 30);
			radioButtonshcs.setBounds(30, 280, 400, 30);
			radioButtonNormalMode.setBounds(30, 320, 400, 30);
			radioButtonshcs.addActionListener(this);
			radioButtoncrypt.addActionListener(this);
			radioButtoncphs.addActionListener(this);
			radioButtonNormalMode.addActionListener(this);
			jp2.add(radioButtonNormalMode);
			jp2.add(radioButtoncphs);
			jp2.add(radioButtoncrypt);
			jp2.add(radioButtonshcs);

			setSize(1300, 700);
			setVisible(true);
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension size = getSize();
			screenSize.height = screenSize.height / 2;
			screenSize.width = screenSize.width / 2;
			size.height = size.height / 2;
			size.width = size.width / 2;
			int y = screenSize.height - size.height;
			int x = screenSize.width - size.width;
			setLocation(x, y);
			MultiCastCall();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void MultiCastCall() {
		try {

			multicast = new Multicst(String.valueOf(port), String
					.valueOf(distance), region, whois, mobilityValueObject);
			multicast.setsys(nodeName);
			receiver = new Receiver(String.valueOf(port), nodeName, region,
					this, String.valueOf(distance));
			receiver.setname(nodeName);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public JPanel Expression() {
		JPanel p = null;
		try {
			// expressionRandom = systemProperties.ExpressionRandom();
			// expressionArr = expressionRandom.split(" ");
			p = new JPanel();
			p.setLayout(null);
			// GridBagConstraints c = new GridBagConstraints();
			JPanel buttons = new JPanel();
			buttons.setLayout(new GridLayout(0, 2));
			addButton = new JButton("+");
			mulButton = new JButton("*");
			divButton = new JButton("/");
			subButton = new JButton("-");
			expressionButton = new JButton("=");
			buttons.add(addButton);
			buttons.add(mulButton);
			buttons.add(divButton);
			buttons.add(subButton);
			buttons.add(expressionButton);
			addButton.addActionListener(this);
			mulButton.addActionListener(this);
			divButton.addActionListener(this);
			subButton.addActionListener(this);
			expressionButton.addActionListener(this);
			buttons.setBounds(500, 20, 130, 150);
			//p.add(buttons);
			// p.setSize(400, 100);
			JLabel jLabel = new JLabel("Puzzle value's : ");
			jLabel.setBounds(100, 20, 200, 50);
			p.add(jLabel);
			JLabel jLabel1 = new JLabel("Generated value : ");
			jLabel1.setBounds(100, 80, 200, 50);
			p.add(jLabel1);
			// expressionLabel = new JLabel(expressionRandom);
			expressionLabel = new JLabel();
			expressionLabel.setBounds(220, 30, 100, 30);
			p.add(expressionLabel);
			expressionText = new JTextField();
			expressionText.setBounds(220, 90, 100, 30);
			p.add(expressionText);
			JLabel jLabelResult = new JLabel("Generated Result : ");
			jLabelResult.setBounds(100, 130, 200, 50);
			p.add(jLabelResult);
			expressionValue = new JTextField();
			expressionValue.setBounds(220, 140, 100, 30);
			p.add(expressionValue);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;

	}

	public static void main(String[] args) {
		NodeDesignForm nodeDesignForm = new NodeDesignForm();
		nodeDesignForm.Design();
		// nodeDesignForm.
	}

	public void DataSend(String fileContent, String mode, String algorithmType) {
		String secretKey = null;
		String expression = null;
		String timeline = null;
		String ranPackets = "";
		int packetSize;
		try {

			key = KeyGenerator.getInstance("DES").generateKey();
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			sealedObject = new SealedObject(fileContent, cipher);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Vector<String> randomPackets = new Vector<String>();
		Vector<String> sendingpackets = new Vector<String>();
		Vector<Integer> randomColl = new Vector<Integer>();
		TreeMap<Integer, String> randomTree = new TreeMap<Integer, String>();
		Random random = new Random();
		packetSize = Integer.parseInt(initValues[6]);
		int nop;
		StringBufferInputStream sbi = new StringBufferInputStream(fileContent);
		int len;
		len = sbi.available();
		nop = len / packetSize;
		int rem = len % packetSize;
		if (rem != 0) {
			nop = nop + 1;
		}
		int count = 0;
		for (int i = 0; i < nop; i++) {
			if (i == nop - 1 && rem != 0) {
				byte[] b = new byte[rem];
				try {
					sbi.read(b);
				} catch (IOException ex) {
					ex.printStackTrace();
					// Logger.getLogger(data_send.class.getName()).log(Level.SEVERE,
					// null, ex);
				}
				String sa = new String(b);
				sendingpackets.add(sa);
			} else {
				byte[] b = new byte[packetSize];
				try {
					sbi.read(b);
				} catch (IOException ex) {
					ex.printStackTrace();
					// Logger.getLogger(data_send.class.getName()).log(Level.SEVERE,
					// null, ex);
				}
				String sb = new String(b);
				sendingpackets.add(sb);
			}

		}
		// System.out.println("Sending.." + sendingpackets);
		if (algorithmType.equalsIgnoreCase(Constants.cphs)) {
			for (int i = 0; i <= sendingpackets.size() - 1; i++) {
				int index = random.nextInt(sendingpackets.size());
				while (true) {
					if (!randomColl.contains(index)) {
						randomColl.add(index);
						// ranPackets = ranPackets + "," + index;
						break;
					} else {
						index = random.nextInt(randomColl.size());
						// ranPackets = index + "";
					}
				}
				ranPackets += String.valueOf(index);
			//	randomTree.put(index, sendingpackets.get(index));
			//	randomPackets.add(sendingpackets.get(index));
			}
		}
		System.out.println("Sending Packets :"+sendingpackets.size());
		for (int offSet = 0; offSet <= (sendingpackets.size() - 1); offSet++) {
			String sourceAddress = null;
			try {
				sourceAddress = Inet4Address.getByName(whois).getHostAddress();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String destinationAddress = null;
			try {
				destinationAddress = Inet4Address.getByName(initValues[2])
						.getHostAddress();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String content = "";
			if (randomPackets.size() == 0) {
				content = sendingpackets.get(offSet);
			} else {
				content = randomPackets.get(offSet);
			}
			System.out.println("offset :" + offSet);
			System.out.println("content :" + content);
			DataSendSerial dataSendSerial = null;
			if (offSet == 0) {
				if (algorithmType.equalsIgnoreCase(Constants.crypt)) {
					// expression = JOptionPane
					// .showInputDialog("Enter the Puzzle");
					expression = String.valueOf(expValue);
					timeline = JOptionPane
							.showInputDialog("Enter the Timeline in secs");
				}

			}
			if (offSet == (sendingpackets.size() - 1)) {
				/* File Contents */
				if (algorithmType.equalsIgnoreCase(Constants.shcs)) {
					secretKey = JOptionPane
							.showInputDialog("Enter the SecretKey");
					dataSendSerial = new DataSendSerial(mode, algorithmType,
							sourceAddress, destinationAddress, String
									.valueOf(offSet), content, secretKey, key,
							sealedObject, (nop - 1), packetSize);
				} else if (algorithmType.equalsIgnoreCase(Constants.crypt)) {

					boolean flagExp = false;

					dataSendSerial = new DataSendSerial(mode, algorithmType,
							sourceAddress, destinationAddress, String
									.valueOf(offSet), content, expression + ":"
									+ expressionText.getText() + ":"
									+ expressionRandom, key, sealedObject,
							(nop - 1), timeline, expressionRandom, packetSize);
				} /*
					 * else if (algorithmType.equalsIgnoreCase("c")) { for (int
					 * i = 0; i <= sendingpackets.size() - 1; i++) { int index =
					 * random.nextInt(sendingpackets.size()); while (true) { if
					 * (!randomColl.contains(index)) { randomColl.add(index);
					 * break; } else { index =
					 * random.nextInt(randomColl.size()); } }
					 * randomPackets.add(sendingpackets.get(index)); } }
					 */
				else if (algorithmType.equalsIgnoreCase(Constants.cphs)) {
					secretKey = JOptionPane
							.showInputDialog("Enter the SecretKey");
					dataSendSerial = new DataSendSerial(mode, algorithmType,
							sourceAddress, destinationAddress, String
									.valueOf(offSet), content, secretKey, key,
							sealedObject, (nop - 1), randomColl, ranPackets,
							packetSize);
				} else if (algorithmType.equalsIgnoreCase("Normal Mode")) {
					dataSendSerial = new DataSendSerial(mode, algorithmType,
							sourceAddress, destinationAddress, String
									.valueOf(offSet), content, fileContent,
							null, null, (nop - 1), packetSize);
				}
			} else {
				// System.out.println(expression+:-"+expressionText.getText());
				if (algorithmType.equalsIgnoreCase(Constants.shcs)) {
					dataSendSerial = new DataSendSerial(mode, algorithmType,
							sourceAddress, destinationAddress, String
									.valueOf(offSet), content, secretKey, null,
							null, (nop - 1), packetSize);
				} else if (algorithmType.equalsIgnoreCase(Constants.crypt)) {

					dataSendSerial = new DataSendSerial(mode, algorithmType,
							sourceAddress, destinationAddress, String
									.valueOf(offSet), content, expression + ":"
									+ expressionText.getText() + ":"
									+ expressionRandom, key, sealedObject,
							(nop - 1), timeline, expressionRandom, packetSize);
				} else if (algorithmType.equalsIgnoreCase(Constants.cphs)) {
					dataSendSerial = new DataSendSerial(mode, algorithmType,
							sourceAddress, destinationAddress, String
									.valueOf(offSet), content, secretKey, key,
							sealedObject, (nop - 1), randomColl, ranPackets,
							packetSize);
				} else if (algorithmType.equalsIgnoreCase("Normal Mode")) {
					dataSendSerial = new DataSendSerial(mode, algorithmType,
							sourceAddress, destinationAddress, String
									.valueOf(offSet), content, fileContent,
							null, null, (nop - 1), packetSize);
				}

			}
			// System.out.println("Inside is :" + timeline);
			String nodeInfo = receiver.curd.get(leaderNode.getText());
			String[] nodeInfoArr = splitDetails.split(nodeInfo);
			int port = Integer.parseInt(nodeInfoArr[0]);
			String system = nodeInfoArr[1];
			ObjectOutputStream objOutput = socketConnection.SocketSend(String
					.valueOf(port), system);
			try {
				objOutput.writeObject(dataSendSerial);
				objOutput.close();
				//Thread.sleep(1000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				/*
				 * expValue = 0; incre = 0; expressionRandom =
				 * systemProperties.ExpressionRandom(); expressionArr =
				 * expressionRandom.split(" "); System.out.println("Expression
				 * Random :" + expressionRandom);
				 */
				// expressionArr = null;
			}

		}
		expValue = 0;
		incre = 0;
		expressionDerive = "";
		/*
		 * expressionRandom = systemProperties.ExpressionRandom(); expressionArr =
		 * expressionRandom.split(" "); System.out.println("Expression Random :" +
		 * expressionRandom);
		 */

	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == electionJButton) {
			System.out.println("receiver :" + receiver.mobilityMap);
			int lastkey = receiver.mobilityMap.lastKey();
			mobilityNode = receiver.mobilityMap.get(lastkey);
			leaderNode.setText(mobilityNode);
			TreeSet<String> neighbourNode = receiver.nei_tree;
			Iterator iter = neighbourNode.iterator();
			while (iter.hasNext()) {
				String neiNode = iter.next().toString();
				String nodeInfo = receiver.curd.get(neiNode);
				String[] nodeInfoArr = splitDetails.split(nodeInfo);
				int port = Integer.parseInt(nodeInfoArr[0]);
				String system = nodeInfoArr[1];
				ObjectOutputStream objOutput = socketConnection.SocketSend(
						String.valueOf(port), system);
				try {
					objOutput.writeObject("mobility :" + mobilityNode);
					objOutput.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		} else if (e.getSource() == sendJButton) {
			try{
				if (!algorithmType.equals("") && !leaderNode.getText().equals(""))
					DataSend(jtextArea.getText(), "mode", algorithmType);
				else
					JOptionPane.showMessageDialog(null,
							"Please select all the fields");
			}catch (Exception ee) {
				// TODO: handle exception
				ee.printStackTrace();
			}
			
		} else if (e.getSource() == openButton) {
			int retVal = fc.showOpenDialog(this);
			if (retVal == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fc.getSelectedFile();
				File f = null;
				try {
					f = new File(selectedFile.getCanonicalPath());
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				// File[] selectedfiles = fc.getSelectedFiles();
				/*
				 * StringBuilder sb = new StringBuilder(); for (int i = 0; i <
				 * selectedfiles.length; i++) {
				 * sb.append(selectedfiles[i].getName() + "\n"); }
				 */
				jTextField.setText(f.getName());
				// FileInputStream fInputStream=new
				// FileInputStream(sb.toString());
				try {
					BufferedReader bufferReader = new BufferedReader(
							new FileReader(f));
					String append = "";
					String line = bufferReader.readLine();
					while (line != null) {
						append += line + "\n";
						line = bufferReader.readLine();
					}
					// System.out.println(append);
					jtextArea.setText(append);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// JOptionPane.showMessageDialog(frame, sb.toString());
			}

		} else if (e.getSource() == addButton) {
			++incre;
			if (incre <= 3) {
				if (expressionDerive.equals("")) {
					expressionDerive = expressionArr[incre - 1] + "+"
							+ expressionArr[incre];
				} else {
					expressionDerive += "+" + expressionArr[incre];
				}
			}

			if (expValue == 0) {
				expValue = Integer.parseInt(expressionArr[incre - 1])
						+ Integer.parseInt(expressionArr[incre]);
				System.out.println(Integer.parseInt(expressionArr[incre - 1]));
				System.out.println(Integer.parseInt(expressionArr[incre]));
			}

			else if (incre <= 3) {
				expValue += Integer.parseInt(expressionArr[incre]);
				System.out.println(Integer.parseInt(expressionArr[incre]));
			}

			System.out.println(expValue);
		} else if (e.getSource() == subButton) {
			++incre;
			if (incre <= 3)
				if (expressionDerive.equals("")) {
					expressionDerive = expressionArr[incre - 1] + "-"
							+ expressionArr[incre];
				} else {
					expressionDerive += "-" + expressionArr[incre];
				}
			if (expValue == 0)
				expValue = Integer.parseInt(expressionArr[incre - 1])
						- Integer.parseInt(expressionArr[incre]);
			else if (incre <= 3)
				expValue -= Integer.parseInt(expressionArr[incre]);
		} else if (e.getSource() == mulButton) {
			++incre;
			if (incre <= 3)
				if (expressionDerive.equals("")) {
					expressionDerive = expressionArr[incre - 1] + "*"
							+ expressionArr[incre];
				} else {
					expressionDerive += "*" + expressionArr[incre];
				}

			if (expValue == 0)
				expValue = Integer.parseInt(expressionArr[incre - 1])
						* Integer.parseInt(expressionArr[incre]);
			else if (incre <= 3)
				expValue *= Integer.parseInt(expressionArr[incre]);
		} else if (e.getSource() == divButton) {
			++incre;
			if (incre <= 3)
				if (expressionDerive.equals("")) {
					expressionDerive = expressionArr[incre - 1] + "/"
							+ expressionArr[incre];
				} else {
					expressionDerive += "/" + expressionArr[incre];
				}
			if (expValue == 0)
				expValue = Integer.parseInt(expressionArr[incre - 1])
						/ Integer.parseInt(expressionArr[incre]);
			else if (incre <= 3)
				expValue /= Integer.parseInt(expressionArr[incre]);
		} else if (e.getSource() == expressionButton) {
			if (incre == 3) {

				System.out.println(expressionDerive);
				System.out.println(expValue);
				expressionText.setText(expressionDerive);
				expressionValue.setText(String.valueOf(expValue));
				// expressionArr = null;

			} else {
				incre = 0;
				expValue = 0;
				expressionDerive = "";
				JOptionPane.showMessageDialog(null,
						"Exceeds the Expression length");
			}
		} else if (e.getSource() == radioButtoncphs) {
			algorithmType = radioButtoncphs.getText();
			radioButtoncrypt.setSelected(false);
			radioButtonshcs.setSelected(false);
			radioButtonNormalMode.setSelected(false);
			radioButtoncphs.setSelected(true);
			// System.out.println(algorithmType);

		} else if (e.getSource() == radioButtoncrypt) {
			radioButtoncphs.setSelected(false);
			radioButtonshcs.setSelected(false);
			radioButtonNormalMode.setSelected(false);
			algorithmType = radioButtoncrypt.getText();
			//JOptionPane.showMessageDialog(null, "Please derive the Puzzle");
			radioButtoncrypt.setSelected(true);
			/*expressionRandom = systemProperties.ExpressionRandom();
			//expressionArr = expressionRandom.split(" ");
			expressionLabel.setText(expressionRandom);
*/
			expressionRandom = systemProperties.ExpressionRandom();
			expressionText.setText(expressionRandom);
			expressionValue.setText(systemProperties.ExpressionValue(expressionRandom));
		} else if (e.getSource() == radioButtonshcs) {
			radioButtoncrypt.setSelected(false);
			radioButtoncphs.setSelected(false);
			radioButtonNormalMode.setSelected(false);
			radioButtonshcs.setSelected(true);
			algorithmType = radioButtonshcs.getText();

		} else if (e.getSource() == radioButtonNormalMode) {
			radioButtoncrypt.setSelected(false);
			radioButtonshcs.setSelected(false);
			radioButtoncphs.setSelected(false);
			radioButtonNormalMode.setSelected(true);
			algorithmType = radioButtonNormalMode.getText();

		}
	}
}
