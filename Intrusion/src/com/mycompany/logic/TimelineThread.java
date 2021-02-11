package com.mycompany.logic;

public class TimelineThread extends Thread {
	public boolean flag = true;
	public long timeline;
	public TimelineThread(String time) {
		// TODO Auto-generated constructor stub
		timeline = Long.parseLong(time) * 1000;
	}
	public void CheckTimeline() {
		
		start();
	}

	public void run() {
		try {
			sleep(timeline);
			flag = false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
