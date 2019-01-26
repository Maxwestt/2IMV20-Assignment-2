/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapView;


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.event.ChartProgressListener;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.experimental.swt.SWTUtils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.shape.Circle;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleEdge;


public class VisLineChartMultiple extends JPanel implements ChartMouseListener{
    ChartPanel chartPanel;
    private Crosshair xCrosshair;
    private Crosshair yCrosshair;
    ArrayList<Double> datax1;
    ArrayList<Double> datay1;
//    ArrayList<Double> datax2;
    ArrayList<Double> datay2;
//    ArrayList<Double> datax3;
    ArrayList<Double> datay3;
    int year;
    double minVal;
    double maxVal;
    
    public VisLineChartMultiple(ArrayList<Double> datax1, 
            ArrayList<Double> datay1, 
//            ArrayList<Double> datax2,
            ArrayList<Double> datay2,
//            ArrayList<Double> datax3,
            ArrayList<Double> datay3, int year) { 
        this.datax1 = datax1;
        this.datay1 = datay1;
//        this.datax2 = datax2;
        this.datay2 = datay2;
//        this.datax3 = datax3;
        this.datay3 = datay3;
        this.year = year;
        minVal = 0;
        maxVal = 1.0;
        
        initUI();
        // default size
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setLayout(new BorderLayout());
        add(chartPanel, BorderLayout.NORTH);
    }

    private void initUI() {
        TimeSeries series1 = createTimeSeries(datax1, datay1, "Average Temperature");
        TimeSeries series2 = createTimeSeries(datax1, datay2, "Minimum Temperature");
        TimeSeries series3 = createTimeSeries(datax1, datay3, "Maximum Temperature");
        
        TimeSeriesCollection collection = new TimeSeriesCollection();
        collection.addSeries(series1);
        collection.addSeries(series2);
        collection.addSeries(series3);
        
        JFreeChart chart = createChart(collection);
        
        chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        chartPanel.setBackground(Color.white);
        
        CrosshairOverlay crosshairOverlay = new CrosshairOverlay();
        this.xCrosshair = new Crosshair(Double.NaN, Color.RED,
                new BasicStroke(0f));
        this.xCrosshair.setLabelVisible(true);
        this.yCrosshair = new Crosshair(Double.NaN, Color.GRAY,
                new BasicStroke(0f));
        this.yCrosshair.setLabelVisible(true);
        crosshairOverlay.addDomainCrosshair(xCrosshair);
        crosshairOverlay.addRangeCrosshair(yCrosshair);
        this.chartPanel.addOverlay(crosshairOverlay);
        add(this.chartPanel);
        
        

//        pack();
//        setTitle("Line chart");
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private TimeSeries createTimeSeries(ArrayList<Double> datax, 
            ArrayList<Double> datay,
            String name) {

        TimeSeries series = new TimeSeries(name);
        //System.out.println(datax.size());
        for(int i = 0; i < datax.size(); i++){
            
                try {
                    Date temp = new SimpleDateFormat("yyyyMMdd").parse(String.format("%.0f", datax.get(i)));
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(temp);
                    if(calendar.get(Calendar.YEAR) == year || year<0){
                        Day day = new Day(temp);
                        series.add(day, datay.get(i));
                        minVal  = datay.get(i) < minVal? datay.get(i):minVal;
                        maxVal  = datay.get(i) > maxVal? datay.get(i):maxVal;
                    }
                    
                } catch (ParseException ex) {
                    Logger.getLogger(VisLineChartEx.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            
        }
        
        return series;
    }

    public double getListMinimum(ArrayList<Double> data){
        double min = 0;
        
        for (int i = 0; i < data.size(); i++){
            if (data.get(i) < min){
                min = data.get(i);
            }
        }
        return min;
    }
    
    public double getListMaximum(ArrayList<Double> data){
        double max = 1;
        
        for (int i = 0; i < data.size(); i++){
            if (data.get(i) > max){
                max = data.get(i);
            }
        }
        return max;
    }
    
    
    
    
    private JFreeChart createChart(XYDataset dataset) {
         
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Temperature", 
                "Date", 
                "Temperature", 
                dataset, 
                //PlotOrientation.VERTICAL,
                true, 
                true, 
                false 
        );
        XYPlot plot = chart.getXYPlot();
        //plot.getRangeAxis().setRange();
//        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
//        renderer.setSeriesPaint(0, Color.RED);
//        renderer.setSeriesStroke(0, new BasicStroke(.3f));
        
        plot.getRangeAxis().setRange(minVal,maxVal);

//        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);
        
        Rectangle rect = new Rectangle(0,0);
//        renderer.setSeriesShape(0, rect);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("Temperature",  
                        new Font("Serif", java.awt.Font.BOLD, 18)
                )
        );

        return chart;

    }

    
    
//    public static void main(String[] args) {
//
//        SwingUtilities.invokeLater(() -> {
//            VisLineChartEx ex = new VisLineChartEx());
//            ex.setVisible(true);
//        });
//    }

    @Override
    public void chartMouseClicked(ChartMouseEvent cme) {
        System.out.println("asdasd");
    }

    @Override
    public void chartMouseMoved(ChartMouseEvent event) {
            Rectangle2D dataArea = chartPanel.getScreenDataArea();
            JFreeChart chart = event.getChart();
            XYPlot plot = (XYPlot) chart.getPlot();
            ValueAxis xAxis = plot.getDomainAxis();
            double x = xAxis.java2DToValue(event.getTrigger().getX(), dataArea, 
                    RectangleEdge.BOTTOM);
            // make the crosshairs disappear if the mouse is out of range
            if (!xAxis.getRange().contains(x)) { 
                x = Double.NaN;                  
            }
            double y = DatasetUtilities.findYValue(plot.getDataset(), 0, x);
            this.xCrosshair.setValue(x);
            this.yCrosshair.setValue(y);
    }
}

