package com.mycompany.support;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Random;
import java.util.Vector;

public class SystemProperties {
	private Random random = new Random();
	private Properties prop = new Properties();
	private Vector<String> symbolsCollection = new Vector<String>();

	public SystemProperties() {
		// TODO Auto-generated constructor stub
		symbolsCollection.add("+");
		symbolsCollection.add("-");
		symbolsCollection.add("*");
		symbolsCollection.add("/");
	}

	public String[] ServerValues() {
		String[] values = new String[7];
		try {

			FileInputStream fin = new FileInputStream("System.properties");
			prop.load(fin);
			String distance = prop.getProperty("distance");
			String region = prop.getProperty("region");
			String intermediateSystem = prop.getProperty("intermediate");
			String serverSystem = prop.getProperty("serversystem");
			String interPort = prop.getProperty("interport");
			String serverPort = prop.getProperty("serverport");
			String packetSize = prop.getProperty("packetsize");
			values[0] = distance;
			values[1] = region;
			values[2] = intermediateSystem;
			values[3] = serverSystem;
			values[4] = interPort;
			values[5] = serverPort;
			values[6] = packetSize;
			prop.load(fin);
			decrementDistance(distance);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return values;
	}

	public void decrementDistance(String distance) {
		try {
			int dist = Integer.parseInt(distance) - 1;
			FileOutputStream fout = new FileOutputStream("System.properties");
			prop.setProperty("distance", String.valueOf(dist));
			prop.store(fout, null);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String RandomName() {
		String appName = "";
		try {
			appName = "Nod " + String.valueOf(random.nextInt(9))
					+ String.valueOf(random.nextInt(9))
					+ String.valueOf(random.nextInt(9));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return appName;
	}

	public int RandomPort() {
		int port = 0;
		try {
			port = Integer.parseInt(random.nextInt(10) + ""
					+ random.nextInt(10) + "" + random.nextInt(10) + ""
					+ random.nextInt(10));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return port;

	}

	public String ExpressionRandom() {
		String expressionRandom = null;
		try {
			expressionRandom = String.valueOf(random.nextInt(10) + 1)
					+" "+SelectSymbol(random.nextInt(4))
					+" "+String.valueOf(random.nextInt(10) + 1)
					+" "+SelectSymbol(random.nextInt(4))
					+" " +String.valueOf(random.nextInt(10) + 1)
					+" "+SelectSymbol(random.nextInt(4))
					+" "+String.valueOf(random.nextInt(10) + 1);
			// System.out.println(expressionRandom);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//ExpressionValue(expressionRandom);
		return expressionRandom;
	}

	public String ExpressionValue(String expressionRandom) {
		int value1 = 0;
		try {
			String[] expressionArray = expressionRandom.split(" ");
			
			int value2=0;
			String symbol="";
			for (int i = 0; i <= (expressionArray.length - 1); i++) {
				// System.out.println(expressionArray[i]);

				if (i == 0) {
					value1 = Integer.parseInt(expressionArray[i]);
				} else if (i % 2 != 0) {
					int index = symbolsCollection.indexOf(expressionArray[i]
							.trim());
					symbol=symbolsCollection.get(index);
					System.out.println("Index 1 is " + i + " " + index);
				} else if (i % 2 == 0 && i != 0) {
					// if
					value2=Integer.parseInt(expressionArray[i]);
					if(symbol.equalsIgnoreCase("+"))
					{
						value1=value1+value2;
					}
					else if(symbol.equalsIgnoreCase("-"))
					{
						value1=value1-value2;
					}
					else if(symbol.equalsIgnoreCase("*"))
					{
						value1=value1*value2;
					}
					else if(symbol.equalsIgnoreCase("/"))
					{
						value1=value1/value2;
					}
				}
				//value2
			}
			System.out.println(value1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(value1);
	}

	public String SelectSymbol(int index) {
		String symbol = "";
		try {
			symbol = symbolsCollection.get(index);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return symbol;
	}

	public static void main(String[] args) {
		SystemProperties systemProperties = new SystemProperties();
		String expressionRandom = systemProperties.ExpressionRandom();
		System.out.println(expressionRandom);
	}
}
