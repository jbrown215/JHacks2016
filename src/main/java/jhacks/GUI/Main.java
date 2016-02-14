package jhacks.GUI;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;

public class Main {
	static String stock = null;		
	static XYDataset data;
	static int count = 0;
//	static XYSeries currentData;
//	static XYSeries newData;
	static XYSeries series;
	String name;



	public static void main(){
		JFrame frame = new JFrame("Chart");
		
		frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		String name = null;
		double price = 0;
		setChartName(name);
		setStock(name, price);
		data = createDataset();
		JFreeChart chart = createChart(data);
		ChartPanel chartPanel = new ChartPanel(chart);

		
		
	}
	static void setChartName(String name){
	}
	
	static XYDataset createDataset(){
		series = new XYSeries("price");
//		setStock();
		
		
		return null;
		
	}
	
	
	static void setStock(String name, double price){
//		for (int i = 0; i < count; i++){
//			XYSeries series = new XYSeries("price");
//			series.add(i, currentData.getY(i));
//		}
		series.add(count, price);
		count++;
	}
	
	static JFreeChart createChart(XYDataset dataSet){
		JFreeChart chart = ChartFactory.createXYLineChart(stock,"Time", "Price", 
				data, PlotOrientation.VERTICAL, true, true, false);
		return chart;
	}
}
