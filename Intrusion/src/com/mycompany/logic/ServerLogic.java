package com.mycompany.logic;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.swing.JOptionPane;

import com.mycompany.crypt.ObjectEncrypt;
import com.mycompany.design.IntermediateServer;
import com.mycompany.design.NodeDesignForm;
import com.mycompany.design.ServerDesignForm;
import com.mycompany.serial.DataSendSerial;
import com.mycompany.serial.DataToInter;
import com.mycompany.support.Constants;
import com.mycompany.support.SocketConnection;
import com.mycompany.support.SplitDetails;
import com.mycompany.support.SystemProperties;

public class ServerLogic extends Thread {
	private SystemProperties systemProperties = new SystemProperties();

	ServerSocket ss;
	Socket s;
	ObjectInputStream ois;
	private SocketConnection socketConnection;
	String whois;
	int portno;
	Object object;
	String interServer;
	String nodeName;
	int i = 0;
	int j = 0;
	private SplitDetails splitDetails;
	private String interPort;
	public int packetSize;
	IntermediateServer intermediateServer;
	int pop_1 = 0, pop_2 = 0, block_packets;
	String[] initValues = null;
	String receivedMsg = "";

	public ServerLogic(int portno, String whois, Object object,
			String interServer, String interPort, String nodeName) {
		System.out.println("Listening port is :" + portno);
		initValues = systemProperties.ServerValues();
		this.interPort = interPort;
		splitDetails = new SplitDetails();
		this.nodeName = nodeName;
		this.object = object;
		this.interServer = interServer;
		socketConnection = new SocketConnection();
		this.portno = portno;
		this.whois = whois;
		try {
			ss = new ServerSocket(portno);
		} catch (Exception e) {
			e.printStackTrace();
		}
		start();
	}

	public void run() {
		try {
			while (true) {
				s = ss.accept();
				ois = new ObjectInputStream(s.getInputStream());
				Object obj = ois.readObject();
				if (obj.toString().contains("mobility")) {
					SplitDetails splitDetails = new SplitDetails();
					String[] mobilityNodeArr = splitDetails.split(obj
							.toString());
					String mobilityNode = mobilityNodeArr[1];
					// System.out.println(mobilityNode);
					NodeDesignForm nodeDesignForm = (NodeDesignForm) object;
					nodeDesignForm.leaderNode.setText(mobilityNode);

				} else if (obj instanceof DataSendSerial) {
					// System.out.println("i is :" + i);
					if (i == 0)
						JOptionPane.showMessageDialog(null, "Leader Node is :"
								+ nodeName);
					DataSendSerial dataSendSerial = (DataSendSerial) obj;
					/*
					 * DataSendSerial(String mode, String algorithmType, String
					 * sourceAddress, String destinationAddress, String
					 * seqCount, String packetContent, String message, SecretKey
					 * key, SealedObject sealedObject, int totPacket
					 */
					packetSize = dataSendSerial.packetSize;
					String mode = dataSendSerial.mode;
					String algorithmType = dataSendSerial.algorithmType;
					String sourceAddress = dataSendSerial.sourceAddress;
					String destinationAddress = dataSendSerial.destinationAddress;
					String seqCount = dataSendSerial.seqCount;
					String packetContent = dataSendSerial.packetContent;
					String message = dataSendSerial.message;
					SecretKey key = dataSendSerial.key;
					SealedObject sealedObject = dataSendSerial.sealedObject;
					int totPacket = dataSendSerial.totPacket;
					int packetSize = dataSendSerial.packetSize;
					/*
					 * System.out.println("totPacket is :" + totPacket);
					 * System.out.println("i is :" + i);
					 * System.out.println("timeline is :" +
					 * dataSendSerial.timeline);
					 */
					DataToInter dataToInter = null;
					if (algorithmType.equalsIgnoreCase(Constants.shcs)) {
						dataToInter = new DataToInter(mode, algorithmType,
								sourceAddress, destinationAddress, seqCount,
								packetContent, message, key, sealedObject,
								totPacket, packetSize, Constants.INTER, "");
					} else if (algorithmType.equalsIgnoreCase(Constants.crypt)) {
						String timeline = dataSendSerial.timeline;
						dataToInter = new DataToInter(mode, algorithmType,
								sourceAddress, destinationAddress, seqCount,
								packetContent, message, key, sealedObject,
								totPacket, timeline, packetSize,
								Constants.INTER, "");
					} else if (algorithmType.equalsIgnoreCase(Constants.cphs)) {
						// System.out.println("inside :"
						// + dataSendSerial.randomTree);
						dataToInter = new DataToInter(mode, algorithmType,
								sourceAddress, destinationAddress, seqCount,
								packetContent, message, key, sealedObject,
								totPacket, dataSendSerial.randomColl,
								dataSendSerial.ranPackets, packetSize,
								Constants.INTER, "");
					} else if (algorithmType.equalsIgnoreCase("Normal Mode")) {

						dataToInter = new DataToInter(mode, algorithmType,
								sourceAddress, destinationAddress, seqCount,
								packetContent, message, null, null, totPacket,
								packetSize, Constants.INTER, "");
						// packetSize = dataToInter.packetSize;
					}
					/*
					 * dataToInter = new DataToInter(mode, algorithmType,
					 * sourceAddress, destinationAddress, seqCount,
					 * packetContent, message, key, sealedObject, totPacket);
					 */
					// String[] nodeInfoArr = splitDetails.split(interServer);
					int port = Integer.parseInt(interPort);
					String system = initValues[3];
					ObjectOutputStream objOutput = socketConnection.SocketSend(
							String.valueOf(port), system);
					objOutput.writeObject(dataToInter);
					objOutput.close();
					if (i == totPacket) {
						// objOutput.close();
						i = 0;
						System.out.println("inside i..");
					} else {
						++i;
					}

				} else if (obj instanceof DataToInter) {

					DataToInter dataToInter = (DataToInter) obj;
					SealedObject sealedObject = dataToInter.sealedObject;
					SecretKey secretKey = dataToInter.key;
					// System.out.println("dataToInter.message");
					packetSize = dataToInter.packetSize;
					String algorithmType = dataToInter.algorithmType;
					int totPacket = dataToInter.totPacket;
					String selection = dataToInter.selection;

					if (selection.equalsIgnoreCase(Constants.INTER)) {
						int mode;
						intermediateServer = (IntermediateServer) object;
						if (algorithmType.equalsIgnoreCase("Normal Mode")) {
							mode = JOptionPane.showConfirmDialog(null,
									"Continue with the unBlock mode");
							// System.out.println(mode);
							if (mode == 1) {

								intermediateServer.mode = "block";
							} else {
								intermediateServer.mode = "unblock";
							}
						}
						Vector<String> data = new Vector<String>();
						data.add(Inet4Address.getByName(
								dataToInter.sourceAddress).getHostAddress());
						data.add(Inet4Address.getByName(
								dataToInter.destinationAddress)
								.getHostAddress());
						data.add(String.valueOf(Integer.parseInt(dataToInter.seqCount)+1));
						data.add(intermediateServer.mode);

						intermediateServer.model.addRow(data);
						if (algorithmType.equalsIgnoreCase("Normal Mode")
								&& intermediateServer.mode
										.equalsIgnoreCase("block")) {
							/* Update the Table Column */
							if (pop_1 == 0) {
								JOptionPane.showMessageDialog(null,
										"Intruder blocked the Packets");
							}

							if (pop_1 == totPacket) {
								pop_1 = 0;
								// System.out.println("inside i..");
							} else {
								++pop_1;
							}
							DataToInter daToInter = (DataToInter) obj;
							daToInter.selection = Constants.SERVER;
							daToInter.blockorUnblock = intermediateServer.mode;
							ObjectOutputStream objOutput = socketConnection
									.SocketSend(String.valueOf(initValues[5]),
											initValues[3]);
							objOutput.writeObject(daToInter);
							objOutput.close();
						} else if (algorithmType
								.equalsIgnoreCase("Normal Mode")
								&& intermediateServer.mode
										.equalsIgnoreCase("unblock")) {
							DataToInter daToInter = (DataToInter) obj;
							daToInter.selection = Constants.SERVER;
							daToInter.blockorUnblock = intermediateServer.mode;
							ObjectOutputStream objOutput = socketConnection
									.SocketSend(String.valueOf(initValues[5]),
											initValues[3]);
							objOutput.writeObject(daToInter);
							objOutput.close();

						} else if (!algorithmType
								.equalsIgnoreCase("Normal Mode")
								&& intermediateServer.mode
										.equalsIgnoreCase("block")) {
							
							if (pop_2 == 0)
							{
								JOptionPane.showMessageDialog(null,
								"Can't block Encrypted Message");
								DataToInter daToInter = (DataToInter) obj;
								daToInter.selection = Constants.SERVER;
								daToInter.blockorUnblock = "unblock";
								ObjectOutputStream objOutput = socketConnection
										.SocketSend(String.valueOf(initValues[5]),
												initValues[3]);
								objOutput.writeObject(daToInter);
								objOutput.close();
							}
								
							if (pop_2 == totPacket) {
								pop_2 = 0;
								// System.out.println("inside i..");
							} else {
								++pop_2;
							}
						} else {
							DataToInter daToInter = (DataToInter) obj;
							daToInter.selection = Constants.SERVER;
							daToInter.blockorUnblock = intermediateServer.mode;
							ObjectOutputStream objOutput = socketConnection
									.SocketSend(String.valueOf(initValues[5]),
											initValues[3]);
							objOutput.writeObject(daToInter);
							objOutput.close();
						}
					} else if (selection.equalsIgnoreCase(Constants.SERVER)) {
						if (algorithmType.equalsIgnoreCase("Normal Mode")
								&& dataToInter.blockorUnblock
										.equalsIgnoreCase("unblock")) {
							ServerDesignForm serverDesignForm = (ServerDesignForm) object;
							receivedMsg += dataToInter.packetContent;
							if (j == totPacket) {
								j = 0;
								JOptionPane.showMessageDialog(null,
										"Received message");
								// String blockedIp = initValues[2];
								String blockedIp = Inet4Address.getByName(
										initValues[2]).getHostAddress();
								String blockedPacket = dataToInter.seqCount;
								String totalPacket = String
										.valueOf(dataToInter.totPacket + 1);
								Vector<String> data = new Vector<String>();
								data.add(blockedIp);
								data.add(totalPacket);
								data.add(String.valueOf(block_packets));
								
								serverDesignForm.model1.addRow(data);
								serverDesignForm.jtextArea.setText(receivedMsg);
								receivedMsg = "";
								serverDesignForm.jp5.setVisible(false);
								serverDesignForm.jp4.setVisible(true);

							} else {
								j++;
							}
							System.out.println(receivedMsg);
						} else if (algorithmType
								.equalsIgnoreCase("Normal Mode")
								&& dataToInter.blockorUnblock
										.equalsIgnoreCase("block")) {
							ServerDesignForm serverDesignForm = (ServerDesignForm) object;
							if (j == totPacket) {
								String blockedIp = Inet4Address.getByName(
										initValues[2]).getHostAddress();
								String blockedPacket = dataToInter.seqCount;
								String totalPacket = String
										.valueOf(dataToInter.totPacket + 1);
								Vector<String> data = new Vector<String>();
								data.add(blockedIp);
								data.add(totalPacket);
								data.add(String.valueOf(block_packets));
								

								serverDesignForm.model1.addRow(data);
								if (!receivedMsg.equalsIgnoreCase("")) {
									serverDesignForm.jtextArea
											.setText(receivedMsg);
									receivedMsg = "";
									serverDesignForm.jp5.setVisible(false);
									serverDesignForm.jp4.setVisible(true);

								}
								j = 0;

							} else {
								// receivedMsg="";
								block_packets++;
								j++;
							}
						} else {
							/* InterNode */

							/*
							 * if (j == totPacket) { j = 0; } else { if
							 * (algorithmType.equalsIgnoreCase(Constants.shcs)) { }
							 * else if (algorithmType
							 * .equalsIgnoreCase(Constants.crypt)) { } else if
							 * (algorithmType .equalsIgnoreCase(Constants.cphs)) { } }
							 */
							/* Transfer to server Node */
							if (j == totPacket) {

								if (algorithmType
										.equalsIgnoreCase(Constants.shcs)) {
									if (sealedObject != null) {
										/*
										 * model.addColumn("Algorithm Type");
										 * model.addColumn("Secret key");
										 * model.addColumn("No of Packets");
										 * model.addColumn("Secret keypath");
										 */
										/*
										 * Vector<String> tableData = new
										 * Vector<String>(); tableData
										 * .add("SHCS");
										 * tableData.add(dataToInter.message);
										 * tableData .add(String
										 * .valueOf(dataToInter.totPacket));
										 * File f = new File("secret.dat");
										 * 
										 * tableData.add(f.getPath());
										 * tableData.add("NA");
										 * tableData.add("NA");
										 * ServerDesignForm.model
										 * .addRow(tableData);
										 */
										String secretMessage = dataToInter.message;
										String currentMessage = JOptionPane
												.showInputDialog("Enter the Secret Key");
										if (currentMessage
												.equalsIgnoreCase(secretMessage)) {
											Vector<String> tableData = new Vector<String>();
											tableData.add("SHCS");
											tableData.add(dataToInter.message);
											tableData
													.add(String
															.valueOf(dataToInter.totPacket + 1));
											File f = new File("secret.dat");

											tableData.add(f.getPath());
											tableData.add("NA");
											tableData.add("NA");
											tableData.add("NA");
											ServerDesignForm.model
													.addRow(tableData);
											ObjectEncrypt objectEncrypt = new ObjectEncrypt();
											objectEncrypt.writeToFile(
													"secret.dat", secretKey);
											String algorithmName = sealedObject
													.getAlgorithm();
											Cipher cipher = Cipher
													.getInstance(algorithmName);
											cipher.init(Cipher.DECRYPT_MODE,
													secretKey);

											String text = (String) sealedObject
													.getObject(cipher);
											// System.out.println("text" +
											// text);
											System.out
													.println("a algorithm Text :"
															+ text);
											ServerDesignForm serverDesignForm = (ServerDesignForm) object;
											JOptionPane.showMessageDialog(null,
													"Received message");
											serverDesignForm.jtextArea
													.setText(text);
											receivedMsg = "";
											serverDesignForm.jp4
													.setVisible(false);
											serverDesignForm.jp5
													.setVisible(true);

										} else {
											JOptionPane.showMessageDialog(null,
													"Incorrect key");
										}

									}
								} else if (algorithmType
										.equalsIgnoreCase(Constants.crypt)) {
									String expression = dataToInter.message;
									String[] expressionArr = expression
											.split(":");
									ServerDesignForm serverDesignForm = (ServerDesignForm) object;

									String timeline = dataToInter.timeline;
									TimelineThread timelineThread = new TimelineThread(
											timeline);
									timelineThread.CheckTimeline();
									boolean flag = true;
									while (flag) {

										int before = (int) System
												.currentTimeMillis();
										String inputExp = JOptionPane
												.showInputDialog("Expression");
										if (expressionArr[1].equals(inputExp)) {
											int after = (int) System
													.currentTimeMillis();
											serverDesignForm.expressionText
													.setText(expressionArr[1]);
											serverDesignForm.expressionValue
													.setText(expressionArr[0]);
											serverDesignForm.expressionLabel
													.setText(expressionArr[2]);
											flag = timelineThread.flag;
											if (flag == false) {
												JOptionPane.showMessageDialog(
														null,
														"Timeline is expired");
											} else {
												/*
												 * Calendar calendar = Calendar
												 * .getInstance();
												 */

												JOptionPane
														.showMessageDialog(
																null,
																"Puzzle as been sloved in timeline");

												int resolvedTime = (after - before) / 1000;

												/*
												 * if (before <= after) {
												 * resolvedTime = after -
												 * before; } else { resolvedTime =
												 * before - after; }
												 */
												Vector<String> tableData = new Vector<String>();
												tableData.add("CPHS");
												tableData.add(expressionArr[1]);
												tableData
														.add(String
																.valueOf(dataToInter.totPacket + 1));
												File f = new File("secret.dat");

												tableData.add(f.getPath());
												tableData
														.add(timeline + "secs");
												tableData.add(String
														.valueOf(resolvedTime
																+ "secs"));
												tableData.add("NA");
												ServerDesignForm.model
														.addRow(tableData);
												ObjectEncrypt objectEncrypt = new ObjectEncrypt();
												objectEncrypt
														.writeToFile(
																"secret.dat",
																secretKey);

												String algorithmName = sealedObject
														.getAlgorithm();
												Cipher cipher = Cipher
														.getInstance(algorithmName);
												cipher.init(
														Cipher.DECRYPT_MODE,
														secretKey);

												String text = (String) sealedObject
														.getObject(cipher);
												System.out
														.println("b algorithm Text :"
																+ text);
												// ServerDesignForm
												// serverDesignForm =
												// (ServerDesignForm) object;
												JOptionPane.showMessageDialog(
														null,
														"Received message");
												serverDesignForm.jtextArea
														.setText(text);
												receivedMsg = "";
												serverDesignForm.jp4
														.setVisible(false);
												serverDesignForm.jp5
														.setVisible(true);

												flag = false;
											}

										} else {
											flag = timelineThread.flag;
											if (flag == false) {
												JOptionPane.showMessageDialog(
														null,
														"Timeline is expired");
											} else {
												JOptionPane.showMessageDialog(
														null,
														"Incorrect solution");
												flag=false;
											}
										}
									}
								} else if (algorithmType
										.equalsIgnoreCase(Constants.cphs)) {
									// TreeMap<Integer, String> randomTree =
									// dataToInter.randomTree;
									if (sealedObject != null) {

										String secretMessage = dataToInter.message;
										String currentMessage = JOptionPane
												.showInputDialog("Enter the Secret Key");
										if (currentMessage
												.equalsIgnoreCase(secretMessage)) {
											Vector<String> tableData = new Vector<String>();
											/*
											 * tableData
											 * .add(dataToInter.algorithmType);
											 */
											tableData.add("AONT");
											tableData.add(dataToInter.message);
											tableData
													.add(String
															.valueOf(dataToInter.totPacket + 1));
											File f = new File("secret.dat");

											tableData.add(f.getPath());
											tableData.add("NA");
											tableData.add("NA");
											tableData
													.add(dataToInter.ranPackets);
											ServerDesignForm.model
													.addRow(tableData);
											ObjectEncrypt objectEncrypt = new ObjectEncrypt();
											objectEncrypt.writeToFile(
													"secret.dat", secretKey);
											/*
											 * for (int i = 0; i <= randomTree
											 * .size() - 1; i++) { int index =
											 * dataToInter.randomColl .get(i);
											 * objectEncrypt.writeToFile(index +
											 * ".txt", randomTree .get(index)); }
											 */
											String algorithmName = sealedObject
													.getAlgorithm();
											Cipher cipher = Cipher
													.getInstance(algorithmName);
											cipher.init(Cipher.DECRYPT_MODE,
													secretKey);

											String text = (String) sealedObject
													.getObject(cipher);
											// System.out.println("text" +
											// text);
											System.out
													.println("a algorithm Text :"
															+ text);
											JOptionPane.showMessageDialog(null,
													"Received message");
											ServerDesignForm serverDesignForm = (ServerDesignForm) object;
											serverDesignForm.jtextArea
													.setText(text);
											receivedMsg = "";
											serverDesignForm.jp4
													.setVisible(false);
											serverDesignForm.jp5
													.setVisible(true);

										} else {
											JOptionPane.showMessageDialog(null,
													"Incorrect key");
										}

									}
								}
								j = 0;
							} else {
								++j;
							}
						}
					}

					// if()
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
