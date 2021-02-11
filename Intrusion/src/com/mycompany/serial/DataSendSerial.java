package com.mycompany.serial;

import java.io.Serializable;
import java.util.TreeMap;
import java.util.Vector;

import javax.crypto.SealedObject;
import javax.crypto.SecretKey;

public class DataSendSerial implements Serializable {
	public String mode, algorithmType, sourceAddress, destinationAddress,
			seqCount, packetContent, message;
	public SecretKey key;
	public SealedObject sealedObject;
	public int totPacket;
	public String timeline;
	public Vector<Integer> randomColl;
	public String ranPackets;
	public String randomExpression;
	public int packetSize;
	public DataSendSerial(String mode, String algorithmType,
			String sourceAddress, String destinationAddress, String seqCount,
			String packetContent, String message, SecretKey key,
			SealedObject sealedObject, int totPacket,int packetSize) {
		this.packetSize=packetSize;
		this.mode = mode;
		this.algorithmType = algorithmType;
		this.sourceAddress = sourceAddress;
		this.destinationAddress = destinationAddress;
		this.seqCount = seqCount;
		this.packetContent = packetContent;
		this.message = message;
		this.key = key;
		this.sealedObject = sealedObject;
		this.totPacket = totPacket;
	}
	
	public DataSendSerial(String mode, String algorithmType,
			String sourceAddress, String destinationAddress, String seqCount,
			String packetContent, String message, SecretKey key,
			SealedObject sealedObject, int totPacket,Vector<Integer> randomColl, String ranPackets,int packetSize) {
		this.packetSize=packetSize;
		this.randomColl=randomColl;
		this.ranPackets=ranPackets;
		this.mode = mode;
		this.algorithmType = algorithmType;
		this.sourceAddress = sourceAddress;
		this.destinationAddress = destinationAddress;
		this.seqCount = seqCount;
		this.packetContent = packetContent;
		this.message = message;
		this.key = key;
		this.sealedObject = sealedObject;
		this.totPacket = totPacket;
	}
	
	public DataSendSerial(String mode, String algorithmType,
			String sourceAddress, String destinationAddress, String seqCount,
			String packetContent, String expression, SecretKey key,
			SealedObject sealedObject, int totPacket, String timeline,String randomExpression,int packetSize) {
		this.packetSize=packetSize;
		this.randomExpression=randomExpression;
		this.mode = mode;
		this.algorithmType = algorithmType;
		this.sourceAddress = sourceAddress;
		this.destinationAddress = destinationAddress;
		this.seqCount = seqCount;
		this.packetContent = packetContent;
		this.message = expression;
		this.key = key;
		this.sealedObject = sealedObject;
		this.totPacket = totPacket;
		this.timeline = timeline;
	}
}
