package com.mycompany.serial;

import java.io.Serializable;
import java.util.TreeMap;
import java.util.Vector;

import javax.crypto.SealedObject;
import javax.crypto.SecretKey;

public class DataToInter implements Serializable {
	public String mode, algorithmType, sourceAddress, destinationAddress,
			seqCount, packetContent, message;
	public SecretKey key;
	public SealedObject sealedObject;
	public int totPacket;
	public String timeline;
	public Vector<Integer> randomColl;
	public String ranPackets;
	public int packetSize;
	public String selection;
	public String blockorUnblock;
	public DataToInter(String mode, String algorithmType, String sourceAddress,
			String destinationAddress, String seqCount, String packetContent,
			String message, SecretKey key, SealedObject sealedObject,
			int totPacket,int packetSize,String selection,String blockorUnblock) {
		this.selection=selection;
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

	public DataToInter(String mode, String algorithmType, String sourceAddress,
			String destinationAddress, String seqCount, String packetContent,
			String message, SecretKey key, SealedObject sealedObject,
			int totPacket, Vector<Integer> randomColl,
			String ranPackets,int packetSize,String selection,String blockorUnblock) {
		this.packetSize=packetSize;
		this.randomColl = randomColl;
		this.ranPackets = ranPackets;
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
		this.selection=selection;
	}

	public DataToInter(String mode, String algorithmType, String sourceAddress,
			String destinationAddress, String seqCount, String packetContent,
			String message, SecretKey key, SealedObject sealedObject,
			int totPacket, String timeline,int packetSize,String selection,String blockorUnblock) {
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
		this.timeline = timeline;
		this.selection=selection;
	}
}
