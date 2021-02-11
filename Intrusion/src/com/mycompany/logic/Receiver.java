package com.mycompany.logic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import com.mycompany.design.NodeDesignForm;
import com.mycompany.support.SocketConnection;
import com.mycompany.support.SplitDetails;

public class Receiver extends Thread {

	public static String serverCache;

	public static TreeMap<Integer, String> transValue = new TreeMap<Integer, String>();;

	public TreeSet<Integer> treeValue = new TreeSet<Integer>();

	int size = 0;

	SocketConnection socketConnection = new SocketConnection();

	SplitDetails splitDetails = new SplitDetails();

	MulticastSocket ms;

	String localHost = "";

	// MultiCastString mstr;

	String MuCst = "", s = null, cur_node;

	String dist;

	Vector myNegh;

	Vector vec = new Vector();

	Vector prop_con = new Vector();

	Vector ack_send = new Vector();

	public TreeSet tree = new TreeSet();

	public static TreeMap<String, String> curd = new TreeMap<String, String>();

	public TreeSet all_nodes = new TreeSet();

	public TreeMap clusterHead = new TreeMap();

	StringTokenizer str_port;

	String nodname;

	String port;

	Iterator ii;

	String sys_port = "", sys_name = "";

	Socket soc;

	ObjectOutputStream oo;

	public TreeMap send_time = new TreeMap();

	Date dd;

	public long tim;

	String nodd;

	int i = 0;

	FileOutputStream fo;

	Properties prop;

	FileInputStream fin;

	String data = "";

	boolean flag = false;

	public int len;

	public TreeSet<String> nei_tree = new TreeSet<String>();

	String region;

	String who;
	NodeDesignForm nodeDesignForm;

	public TreeMap<String, Integer> mobilityName = new TreeMap<String, Integer>();
	public TreeMap<Integer, String> mobilityMap = new TreeMap<Integer, String>();

	public Receiver(String port, String name, String region,
			NodeDesignForm nodeDesignForm, String dis) {
		this.nodeDesignForm = nodeDesignForm;
		this.port = port;
		this.nodd = name;
		this.region = region;
		this.dist = dis;
		// new stop();
		start();
	}

	public void setWho(String whosis) {
		this.who = whosis;
	}

	public void setname(String nod) {
		// System.out.println("cur_node..." + nod);
		cur_node = nod;
	}

	/*
	 * public void setMul(String dis) { dist = dis; }
	 */

	public void run() {
		try {
			while (true) {
				ms = new MulticastSocket(5454);
				InetAddress ia = InetAddress.getByName("228.2.5.11");
				ms.joinGroup(ia);
				byte[] b = new byte[1000];
				DatagramPacket dp = new DatagramPacket(b, b.length);
				ms.receive(dp);
				s = new String(dp.getData()).trim();
				StringTokenizer str = new StringTokenizer(s.trim(), ":");
				// String whosis = str.nextToken(":");
				Random rand = new Random();
				int value = rand.nextInt(10000);
				// System.out.println("SSSSS " + s);

				String reg = str.nextToken(":");
				String dis = str.nextToken(":");
				String nodeName = str.nextToken(":");
				String port = str.nextToken(":");
				String sysName = str.nextToken(":");
				String mobilityScore = str.nextToken(":");
				curd.put(nodeName, port + ":" + sysName);
				//System.out.println(s);
				if (mobilityName.containsKey(nodeName)) {
					int mobilityValue = mobilityName.get(nodeName);
					mobilityMap.remove(mobilityValue);
					mobilityMap.put(Integer.parseInt(mobilityScore), nodeName);
					mobilityName.put(nodeName, Integer.parseInt(mobilityScore));
				} else {
					mobilityMap.put(Integer.parseInt(mobilityScore), nodeName);
					mobilityName.put(nodeName, Integer.parseInt(mobilityScore));
				}

				/*
				 * System.out.println("nodeName :" + nodeName);
				 * System.out.println("region :" + region);
				 * System.out.println("reg :" + reg);
				 */
				if (!nodd.equalsIgnoreCase(nodeName)) {
					if (region.equals("region1")) {
						/*
						 * System.out.println("Node 1 :" + nodeName);
						 * System.out.println("cat :" + cat);
						 * System.out.println("category :" + category);
						 */

						if (!reg.equals("region1") && reg.equals("region2")) {
							if (Integer.parseInt(dis) <= Integer.parseInt(dist)) {
								nei_tree.add(nodeName);
							}
						}
						if (reg.equals("region1")) {
							if (Integer.parseInt(dis) != Integer.parseInt(dist)) {
								nei_tree.add(nodeName);
							}
						}
					}
					if (region.equals("region2")) {
						if (!reg.equals("region2") && reg.equals("region1")) {

							if (Integer.parseInt(dis) >= Integer.parseInt(dist)) {
								nei_tree.add(nodeName);
							}
						}
						if (!reg.equals("region2") && reg.equals("region3")) {
							if (Integer.parseInt(dis) <= Integer.parseInt(dist)) {
								nei_tree.add(nodeName);
							}
						}
						if (reg.equals("region2")) {
							if (Integer.parseInt(dis) != Integer.parseInt(dist)) {
								nei_tree.add(nodeName);
							}
						}
					}
					if (region.equals("region3")) {
						if (!reg.equals("region3") && reg.equals("region2")) {
							if (Integer.parseInt(dis) >= Integer.parseInt(dist)) {
								nei_tree.add(nodeName);
							}
						}
						if (reg.equals("region3")) {
							if (Integer.parseInt(dis) != Integer.parseInt(dist)) {
								nei_tree.add(nodeName);
							}
						}
					}
					/*
					 * System.out.println("Node "+nodd);
					 * System.out.println("Neighbour "+nei_tree);
					 */
					Iterator iter = nei_tree.iterator();
					String text = "";
					while (iter.hasNext()) {
						String temp = iter.next().toString();
						if (!temp.equals(nodd))
							text += temp + " \n";
					}
					nodeDesignForm.jTextAreaNeigh.setText(text);

				}

			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}
}