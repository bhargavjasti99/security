package com.mycompany.logic;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Properties;

import com.mycompany.valueobject.MobilityValueObject;

public class Multicst implements Runnable {
	MulticastSocket ms;

	String mysys;

	String memo;

	String sysname;

	public static MultiCastString mstr = new MultiCastString();;

	// HelloReceiver hell;
	public String dis;

	String sysip;

	byte[] sendbyte;

	public boolean status = true;

	Thread t;

	public String s;

	public String port;

	Properties p = new Properties();

	String region;

	String whois;

	MobilityValueObject mobilityValueObject;

	public Multicst(String port, String di, String region, String whois,
			MobilityValueObject mobilityValueObject) {
		this.mobilityValueObject = mobilityValueObject;
		this.whois = whois;
		this.region = region;
		t = new Thread(this);
		try {
			dis = di;
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.port = port;
		t.start();
	}

	/*
	 * public void MulticastTrans(String port, String system) { try { int i = 0;
	 * 
	 * while (i == 0) { ms = new MulticastSocket(5454); InetAddress ia =
	 * InetAddress.getByName("228.2.5.1"); ms.joinGroup(ia); String send =
	 * "sample:" + port + ":" + system + ":" + attributes + ":" + region;
	 * System.out.println("Attributes :" + attributes); byte[] sendbyte =
	 * send.getBytes(); DatagramPacket dp = new DatagramPacket(sendbyte,
	 * sendbyte.length, ia, 5454); ms.send(dp); ++i; } } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */

	public void setsys(String mysys) {

		this.mysys = mysys;
	}

	public String getsys() {
		return mysys;
	}

	public void run() {

		try {
			ms = new MulticastSocket(5454);
			InetAddress ia = InetAddress.getByName("228.2.5.11");
			ms.joinGroup(ia);
			Thread.sleep(15000);
			while (status) {
				sysip = InetAddress.getLocalHost().getHostName();
				if (mstr.multiStr.equals("")) {
					mstr.multiStr = InetAddress.getLocalHost().getHostName()
							+ " ";
					// System.out.println("Inside if"+mstr.multiStr);
				} else {
					String[] ss = mstr.multiStr.split(" ");
					boolean flag = true;
					for (int jj = 0; jj < ss.length; jj++) {
						if (ss[jj].equals(InetAddress.getLocalHost()
								.getHostName())) {
							flag = false;
							// System.out.println("Inside second
							// if"+mstr.multiStr);
						}

					}
					if (flag) {

						mstr.multiStr = mstr.multiStr
								+ InetAddress.getLocalHost().getHostName()
								+ " ";
						// System.out.println("Inside third if"+mstr.multiStr);
					}
				}
				sysname = mysys + ";" + dis;
				String j = "Hello..........";
				// System.out.println("Multicst............."+mstr.multiStr);
				// String
				// sendmsg="node"+":"+mstr.multiStr+":"+sysip+":"+sysname+":"+j+":"+port+":"+region;
				String sendmsg = region
						+ ":"
						+ dis
						+ ":"
						+ mysys
						+ ":"
						+ port
						+ ":"
						+ InetAddress.getLocalHost().getHostName()
						+ ":"
						+ String
								.valueOf(mobilityValueObject.getMobilityScore());
				// System.out.println("Multicst.........send"+mysys+"....."+sendmsg);
				sendbyte = sendmsg.getBytes();
				DatagramPacket dp = new DatagramPacket(sendbyte,
						sendbyte.length, ia, 5454);
				ms.send(dp);
				Thread.sleep(1200);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}