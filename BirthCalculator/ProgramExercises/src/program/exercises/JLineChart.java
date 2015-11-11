package program.exercises;
import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class JLineChart extends ApplicationFrame{

	private XYDataset dataset;
	public JLineChart(final String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}
	
	public JLineChart(final String title, int[] data){
		super(title);
		XYSeries series = new XYSeries("Set");
		for(int i = 0; i < data.length; i++){
			series.add(1900 + i, data[i]);
		}
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);
		this.dataset = dataset;
		final JFreeChart chart = createChart(this.dataset);
		final ChartPanel chartPanel = new ChartPanel(chart);
		setContentPane(chartPanel);
		
	}
	
	private JFreeChart createChart(final XYDataset dataset){
		final JFreeChart chart = ChartFactory.createXYLineChart("Total alive", "X", "Y", dataset, PlotOrientation.VERTICAL, true, true, false);
		
		chart.setBackgroundPaint(Color.WHITE);
		final XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.LIGHT_GRAY);
		
		plot.setDomainGridlinePaint(Color.WHITE);
		plot.setRangeGridlinePaint(Color.WHITE);
		
		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesLinesVisible(0, true);
		renderer.setSeriesShapesVisible(0, true);
		plot.setRenderer(renderer);
		
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		return chart;
		
	}
}
