package jhacks.GUI;

import javax.swing.SwingUtilities;

import java.awt.Color;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JFrame;
import org.jfree.chart.*;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class Main {
	static String stock = null;	
	static JFreeChart chart;
	static XYDataset data;
	static int key = 0;
	static int Yvalue = 0;
	static XYSeries series;
	static String name;
	static Map<String,Integer[]> chartInfo;
	static Integer[] keyAndYvalue;
	static XYSeriesCollection seriesCollection;
	static XYSeriesCollection thisSeries;




	public static void main(String args[]) {
		
	}
	public static void main(String name, Double price){
		JFrame frame = new JFrame("Chart");
//		this.name = name;
		chartInfo = new TreeMap<String, Integer[]>();
		keyAndYvalue = new Integer[2];

		
		frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		if(!chartInfo.containsKey(name)){
			keyAndYvalue[0] = key;
			keyAndYvalue[1] = 0;
			chartInfo.put(name, keyAndYvalue);
			key++;
		}
		
		
		chart = upDateChart(price);
		
		ChartPanel chartPanel = new ChartPanel(chart);
//		setContentPane(chartPanel);
		
		
	}

	
	static JFreeChart upDateChart(double price){
		int i = chartInfo.get(name)[0]; //key
		if(seriesCollection.getSeries(i) == null){
			series = new XYSeries(name);
			series.add(1,price);

			chartInfo.put(name, keyAndYvalue);
		}else{
		 series = seriesCollection.getSeries(i);
		 keyAndYvalue[0] = chartInfo.get(name)[0];
		 keyAndYvalue[1] = chartInfo.get(name)[1]++;
		 chartInfo.put(name, keyAndYvalue);
		 series.add((double)chartInfo.get(name)[1], price);
		}
		
		seriesCollection.addSeries(series);
		thisSeries = new XYSeriesCollection();
		thisSeries.addSeries(series);
		JFreeChart chart = ChartFactory.createXYLineChart(stock,"Time", "Price", 
				(XYDataset)thisSeries, PlotOrientation.VERTICAL, true, true, false);
		chart.setBackgroundPaint(Color.WHITE);
//		XYPlot plot = chart.getXYPlot();
		
		return chart;
	}
}
