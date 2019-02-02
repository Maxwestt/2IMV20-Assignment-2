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
import java.text.DateFormat;
import java.text.DecimalFormat;
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
import org.jfree.chart.annotations.XYPointerAnnotation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.TickUnits;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.event.ChartProgressEvent;
import org.jfree.chart.labels.CrosshairLabelGenerator;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;
import org.jfree.ui.RectangleEdge;


public class VisLineChartEx extends JPanel implements ChartMouseListener{
    ChartPanel chartPanel;
    private Crosshair xCrosshair;
    private Crosshair yCrosshair;
    ArrayList<Double> datax;
    ArrayList<Double> datay;
    int year;
    double minVal;
    double maxVal;
    boolean alldata;
    TimeSeriesDataItem item;
    String title;
    
    public VisLineChartEx(String title, ArrayList<Double> datax, ArrayList<Double> datay, int year) { 
        this.datax = datax;
        this.datay = datay;
        this.year = year;
        this.title = title;
        minVal = 0;
        maxVal = 1.0;
        
        initUI();
        // default size
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        this.setLayout(new BorderLayout());
        this.add(chartPanel, BorderLayout.NORTH);
    }

    private void initUI() {
        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        chartPanel.setBackground(Color.white);
        
        CrosshairOverlay crosshairOverlay = new CrosshairOverlay();
        xCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(1f));
        xCrosshair.setLabelVisible(true);
        yCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(1f));
        yCrosshair.setLabelVisible(true);
        xCrosshair.setLabelOutlineVisible(false);
        yCrosshair.setLabelOutlineVisible(false);
        crosshairOverlay.addDomainCrosshair(xCrosshair);
        crosshairOverlay.addRangeCrosshair(yCrosshair);
        xCrosshair.setLabelGenerator(new CrosshairLabelGenerator() {
            @Override
            public String generateLabel(Crosshair crshr) {
                String converted = new SimpleDateFormat("dd MMM").format(crshr.getValue());   
                return " "+converted+" ";
            }
        });
        
         yCrosshair.setLabelGenerator(new CrosshairLabelGenerator() {
            @Override
            public String generateLabel(Crosshair crshr) {
                String converted = new DecimalFormat("0.0").format(crshr.getValue());   
                return " "+converted+" ";
            }
        }); 
        chartPanel.addOverlay(crosshairOverlay);
        chartPanel.addChartMouseListener(this);
        
 
            
    }

    private XYDataset createDataset() {

        TimeSeries series = new TimeSeries(title);
        //System.out.println(datax.size());
        int begin;
        int end;
        if(year <0 ){
            begin = 0;
            end = datax.size();
        }
        else{
            begin = findApproxBeginIndex(year, datax);
            end = Math.min((begin+467),datax.size());
        }
        
        for(int i = begin; i < end ; i++){
            
                try {
                    Date temp = new SimpleDateFormat("yyyyMMdd").parse(String.format("%.0f", datax.get(i)));
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(temp);
                    //if (year > 0){
                        if(calendar.get(Calendar.YEAR) == year || year<0){
                            Day day = new Day(temp);
                            //System.out.println(temp.);
                            series.add(day, datay.get(i));
                            minVal  = datay.get(i) < minVal? datay.get(i):minVal;
                            maxVal  = datay.get(i) > maxVal? datay.get(i):maxVal;
                        }
//                    } else {
//                        Day day = new Day(temp);
//                        series.add(day, datay.get(i));
//                    }
                    
                } catch (ParseException ex) {
                    Logger.getLogger(VisLineChartEx.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);
        //item = series.getDataItem(series.getItemCount() - 1);
        return dataset;
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
                title, 
                "", 
                "", 
                dataset, 
                //PlotOrientation.VERTICAL,
                false, 
                false, 
                false 
        );
        XYPlot plot = chart.getXYPlot();
        ((DateAxis) plot.getDomainAxis()).setDateFormatOverride(new SimpleDateFormat("MMM-yy"));
//        if(!alldata){
//            ((DateAxis) plot.getDomainAxis()).setTickUnit(new DateTickUnit(DateTickUnit.MONTH, 1));
//        }
        
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(.3f));
        
        plot.getRangeAxis().setRange(minVal,maxVal);
        renderer.setSeriesStroke(0, new BasicStroke(1f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);
        plot.setDomainCrosshairVisible(false);
        plot.setRangeCrosshairVisible(false);
        plot.setRangeGridlinesVisible(true);
        
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(false    );
        plot.setDomainGridlinePaint(Color.BLACK);
        
        Rectangle rect = new Rectangle(0,0);
        renderer.setSeriesShape(0, rect);
        //chart.getLegend().setFrame(BlockBorder.NONE);
        
  
        chart.setTitle(new TextTitle(title,  
                        new Font("SansSerif", java.awt.Font.PLAIN, 12)
                )
        );
        return chart;
    }

    
    @Override
    public void chartMouseMoved(ChartMouseEvent cme) {
        Rectangle2D dataArea = chartPanel.getScreenDataArea();
        JFreeChart chart = cme.getChart();
        XYPlot plot = (XYPlot) chart.getPlot();
        ValueAxis xAxis = plot.getDomainAxis();
        double x = xAxis.java2DToValue(cme.getTrigger().getX(), dataArea,
                RectangleEdge.BOTTOM);
        
        double y = DatasetUtilities.findYValue(plot.getDataset(), 0, x);
        this.xCrosshair.setLabelBackgroundPaint(new Color(1f, 1f, 1f, 1f));
        this.xCrosshair.setLabelFont(yCrosshair.getLabelFont().deriveFont(20f));
        this.yCrosshair.setLabelBackgroundPaint(new Color(1f, 1f, 1f, 1f));
        this.yCrosshair.setLabelFont(yCrosshair.getLabelFont().deriveFont(20f));
        
        this.xCrosshair.setValue(x);
        this.yCrosshair.setValue(y);

        
    }

    @Override
    public void chartMouseClicked(ChartMouseEvent cme) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

        
    
   private int findApproxBeginIndex(int year, ArrayList<Double> dates){   
       for(int i = 0; i<dates.size();i=i+100){
           Date temp;
           try {
               temp = new SimpleDateFormat("yyyyMMdd").parse(String.format("%.0f", dates.get(i))); 
               Calendar calendar = new GregorianCalendar();
               calendar.setTime(temp);
               if(calendar.get(Calendar.YEAR) == year){
                   return Math.max(0, i-100);
               }
           } catch (ParseException ex) {
               Logger.getLogger(PolarLineChartEx.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       return 0;
   }

}

