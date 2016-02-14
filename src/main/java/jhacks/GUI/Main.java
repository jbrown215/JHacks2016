package jhacks.GUI;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.*;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.Second;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class Main extends ApplicationFrame {

  /*
   * static String stock = null; static JFreeChart chart; static XYDataset data;
   * 
   * static XYSeries series; static Map<String, Chart> chartInfo;
   * 
   * static XYSeriesCollection thisSeries; static int count = 1;
   * 
   * 
   * 
   * 
   * public static void main(String args[]) { createChart("Google",9.0);
   * createChart("Google",8.0); createChart("Google",7.0);
   * createChart("Google",6.0); createChart("Google",5.0);
   * 
   * // final MakeChart demo = new MakeChart("hi", "Google", 9.0); //
   * demo.pack(); // RefineryUtilities.centerFrameOnScreen(demo); //
   * demo.setVisible(true);
   * 
   * //
   * 
   * } static void createChart(String name, Double price){ JFrame frame = new
   * JFrame("Chart"); chartInfo = new TreeMap<String, Chart>();
   * 
   * frame.setSize(500,500);
   * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   * frame.setVisible(true); // seriesCollection = new XYSeriesCollection();
   * 
   * 
   * if(!(chartInfo.containsKey(name))){ System.out.println("stock saved");
   * XYSeries s = new XYSeries("s"); chartInfo.put(name, new Chart(s, count)); }
   * 
   * 
   * chart = upDateChart(name, price);
   * 
   * ChartPanel chartPanel = new ChartPanel(chart); setContentPane(chartPanel);
   * frame.add(chartPanel); frame.pack();
   * 
   * }
   * 
   * 
   * private static void setContentPane(ChartPanel chartPanel) { // TODO
   * Auto-generated method stub
   * 
   * }
   * 
   * 
   * static JFreeChart upDateChart(String name,double price){ series =
   * chartInfo.get(name).getSeries();
   * series.add(chartInfo.get(name).getYvalue(), price);
   * System.out.println(chartInfo.get(name).getYvalue()); int y =
   * chartInfo.get(name).getYvalue() + 1; Chart c = new Chart(series, y);
   * chartInfo.put(name, c);
   * 
   * thisSeries = new XYSeriesCollection(); thisSeries.addSeries(series);
   * JFreeChart chart = ChartFactory.createXYLineChart(stock,"Time", "Price",
   * (XYDataset)thisSeries, PlotOrientation.VERTICAL, true, true, false);
   * chart.setBackgroundPaint(Color.WHITE); XYPlot plot = chart.getXYPlot();
   * plot.setBackgroundPaint(Color.LIGHT_GRAY);
   * plot.setDomainGridlinePaint(Color.white);
   * plot.setRangeGridlinePaint(Color.white);
   * 
   * XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
   * renderer.setSeriesLinesVisible(0, false); plot.setRenderer(renderer);
   * 
   * // NumberAxis rangeAxis return chart; }
   */
  private static final String TITLE = "Stocks";
  private static final String START = "Start";
  private static final String STOP = "Stop";
  private static final float MINMAX = 100;
  private static final int COUNT = 2 * 60;
  private static final int FAST = 100;
  private static final int SLOW = FAST * 5;
  private static final Random random = new Random();
  private Timer timer;
  private static double price;
  private static int numWindow = 5;
  private DynamicTimeSeriesCollection dataset;
  final Queue<Map<String, Double>> dataQueue;
  private static String[] name;

  public Main(final String title, double price) {
    super(title);
    this.dataQueue = new LinkedList<>();
    dataset = new DynamicTimeSeriesCollection(1, COUNT, new Second());
    dataset.setTimeBase(new Second(0, 0, 0, 1, 1, 2011));
    dataset.addSeries(gaussianData(), 0, "Stocks");
    JFreeChart chart = createChart(dataset);
    this.price = price;

    final JButton run = new JButton(STOP);
    run.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (STOP.equals(cmd)) {
          timer.stop();
          run.setText(START);
        } else {
          timer.start();
          run.setText(STOP);
        }
      }
    });

    final JComboBox combo = new JComboBox();
    combo.addItem("Fast");
    combo.addItem("Slow");
    combo.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        if ("Fast".equals(combo.getSelectedItem())) {
          timer.setDelay(FAST);
        } else {
          timer.setDelay(SLOW);
        }
      }
    });

    this.add(new ChartPanel(chart), BorderLayout.CENTER);
    JPanel btnPanel = new JPanel(new FlowLayout());
    btnPanel.add(run);
    btnPanel.add(combo);
    this.add(btnPanel, BorderLayout.SOUTH);

    timer = new Timer(FAST, new ActionListener() {

      float[] newData = new float[1];

      @Override
      public void actionPerformed(ActionEvent e) {
        synchronized (dataQueue) {
          while (!dataQueue.isEmpty()) {
            Map<String, Double> data = dataQueue.poll();
            float val = data.get("price").floatValue();
            newData[0] = val;
            dataset.advanceTime();
            dataset.appendData(newData);
          }
        }
      }
    });
    // updateGraph(price, dataset);
  }

  private void updateGraph(double value, DynamicTimeSeriesCollection dataset) {
    float[] newData = new float[1];
    newData[0] = randomValue();
    dataset.advanceTime();
    dataset.appendData(newData);
  }

  private float randomValue() {
    return (float) price; // (random.nextGaussian() * MINMAX / 3);
  }

  private float[] gaussianData() {
    float[] a = new float[COUNT];
    System.out.println(COUNT);
    for (int i = 0; i < a.length; i++) {
      a[i] = randomValue();
    }
    return a;
  }

  private JFreeChart createChart(final XYDataset dataset) {
    final JFreeChart result = ChartFactory.createTimeSeriesChart(TITLE, "Time", "Price", dataset, true, true, false);
    final XYPlot plot = result.getXYPlot();
    ValueAxis domain = plot.getDomainAxis();
    domain.setAutoRange(true);
    ValueAxis range = plot.getRangeAxis();
    range.setRange(-MINMAX, MINMAX);
    return result;
  }

  public void start() {
    timer.start();
  }

  public static void main(final String[] args) {
    EventQueue.invokeLater(new Runnable() {

      @Override
      public void run() {
        name = new String[5];

        name[0] = "GOOG";
        name[1] = "MOOG";
        name[2] = "FOOD";
        name[3] = "THROOG";
        name[4] = "BOOG";
        Map<String, Queue<Map<String, Double>>> map = new HashMap<>();
        for (int i = 0; i < numWindow; i++) {
          Main demo = new Main(name[i], 20.0);
          demo.pack();
          RefineryUtilities.centerFrameOnScreen(demo);
          demo.setVisible(true);
          demo.start();
          map.put(name[i], demo.dataQueue);
        }
        Thread t = new Thread(new TestDataGenerator(map));
        t.start();

      }
    });
  }
}
