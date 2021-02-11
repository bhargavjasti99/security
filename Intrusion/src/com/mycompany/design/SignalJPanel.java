package com.mycompany.design;

import java.awt.BorderLayout;
import java.util.Random;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import com.mycompany.logic.ServerLogic;
import com.mycompany.support.SystemProperties;

public class SignalJPanel extends JPanel implements Runnable {

	private TimeSeries series;

	private SystemProperties systemProperties = new SystemProperties();
	private String[] initValues = systemProperties.ServerValues();
	
	private double lastValue = 0.0;

	public static double Packet = 0.0;

	ValueAxis axis;

	ServerLogic serverLogic;

	IntermediateServer intermediateServer;
	
	Random random=new Random();
	public SignalJPanel(ServerLogic serverLogic,
			IntermediateServer intermediateServer) {
		this.serverLogic=serverLogic;
		// this = new JPanel(new BorderLayout());
		this.intermediateServer=intermediateServer;
		this.series = new TimeSeries("Signal", Millisecond.class);
		final TimeSeriesCollection dataset = new TimeSeriesCollection(
				this.series);
		final JFreeChart chart = createChart(dataset);

		final ChartPanel chartPanel = new ChartPanel(chart);

		this.add(chartPanel);
		// content.add(button, BorderLayout.SOUTH);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 220));
		Thread t = new Thread(this);
		t.start();
	}

	private JFreeChart createChart(final XYDataset dataset) {
		final JFreeChart result = ChartFactory.createTimeSeriesChart(
				"Traffic Signal", "Time in Seconds", "S(n) Value", dataset,
				true, true, false);

		final XYPlot plot = result.getXYPlot();
		axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		axis.setFixedAutoRange(900000.0); // 60 seconds
		axis = plot.getRangeAxis();
		axis.setRange(-10.0, Integer.parseInt(initValues[6])+20);
		return result;
	}

	public void run() {

		synchronized (this) {
			for (;;) {
				int value = 0;
				try {
					//System.out.println("intermediateServer.mode");
					if (intermediateServer.mode.equalsIgnoreCase("unblock")) {
					//	System.out.println(serverLogic.packetSize);
						value=serverLogic.packetSize;
					} 
					/*else
					{
						value=random.nextInt(13);
					}*/
					final double factor = 0.90 + (-0.2) * Math.random();
					this.lastValue = (factor * this.Packet);
					// axis.setRange(-this.Packet - 1, this.Packet + 2);

					// System.out.println(this.lastValue);
					final Millisecond now = new Millisecond();
					final Second now1 = new Second();
					this.series.addOrUpdate(new Millisecond(), value);
					// System.out.println("$$%$%$%"+now);
					// System.out.println("&&&&&&"+lastValue);
					// this.Packet = 0.0+0.90 + 0.2 * Math.random();
					this.wait(1000);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}
}
