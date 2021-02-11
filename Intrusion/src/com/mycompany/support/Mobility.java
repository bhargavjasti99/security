package com.mycompany.support;

import java.util.Random;
import java.util.TreeMap;

import com.mycompany.design.NodeDesignForm;
import com.mycompany.valueobject.MobilityValueObject;

public class Mobility extends Thread {
	private Random random = new Random();
	// private int setv;
	private int mob;
	private TreeMap mobilityTree = new TreeMap();
	private String nodeName;
	private NodeDesignForm nodeDesignForm;
	public MobilityValueObject mobilityValueObject;
	public Mobility(String nodeName, NodeDesignForm nodeDesignForm,MobilityValueObject mobilityValueObject) {
		this.nodeDesignForm = nodeDesignForm;
		this.nodeName = nodeName;
		this.mobilityValueObject=mobilityValueObject;
		// TODO Auto-generated constructor stub
		// setv = random.nextInt(100);
		mob = random.nextInt(100);
		start();
	}
	private int i=0;
	public void run() {
		while (true) {
			try {

				prog();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void prog() {
		try {
			Random rr = new Random();
			int ii = rr.nextInt(10);
			// int bb=rr.nextInt(10);
			if (ii <= 5) {
				// System.out.println("%%%%%"+ii);
				if (mob <= 0) {
					mob = mob + rr.nextInt(10);
					// bat = bat + rr.nextInt(10);
				} else {
					mob = mob - 1;
					// bat = bat - 1;
				}
				Thread.sleep(rr.nextInt(10000));
			} else {
				mob = mob + 1;
				// System.out.println("$$$$$$"+ii);
			}
			// valm.setValuem(String.valueOf(mem));
			// setb = String.valueOf(bat);
			if (mobilityTree.size() == 0) {
				//mobilityTree.put(mob, nodeName);
			} else {
				mobilityTree.clear();
				//mobilityTree.put(mob, nodeName);
			}
			mobilityValueObject.setMobilityScore(mob);
			nodeDesignForm.pb.setValue(mob);
			nodeDesignForm.pb.setString(mob + " %");
			if(i!=0)
			Thread.sleep(rr.nextInt(5000));
			++i;
			// i=i+10000;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public TreeMap getMobilityTree() {
		return mobilityTree;
	}

	public void setMobilityTree(TreeMap mobilityTree) {
		this.mobilityTree = mobilityTree;
	}
}
