package MapView;

import java.awt.BasicStroke;
import static java.awt.BasicStroke.CAP_ROUND;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PolarPlot;
import org.jfree.chart.renderer.DefaultPolarItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * @author imssbora
 *
 */
public class PolarLineChartExample extends JPanel {

   private static final long serialVersionUID = 1L;
   private static ArrayList<WindObj> winds;

   ArrayList<Double> dates, direction, speed;
   int year;
    public PolarLineChartExample(ArrayList<Double> dates, ArrayList<Double> direction, ArrayList<Double> speed, int year) {
       
        this.direction = direction;
        this.speed = speed;
        this.dates = dates;
        this.year = year;
        
        winds = new ArrayList<WindObj>();
        
        for(int i = 0; i < dates.size(); i++){
            try {
                    Date temp = new SimpleDateFormat("yyyyMMdd").parse(String.format("%.0f", dates.get(i)));
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(temp);
                        if(calendar.get(Calendar.YEAR) == year || year<0){
                            winds.add(new WindObj(direction.get(i), speed.get(i)));
                        }
            } catch (ParseException ex) {
                Logger.getLogger(VisLineChartEx.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        Collections.sort(winds, new Comparator<WindObj>() {
            @Override
            public int compare(WindObj o1, WindObj o2) {
                return(o1.getDirection().compareTo(o1.getDirection()));
            }
        });
        double maxWind = 0;
        for (WindObj wo: winds){
            if (wo.getSpeed() > maxWind){
                maxWind = wo.getSpeed();
            }
        }
        System.out.println(maxWind);
      
      // Create dataset
      XYDataset dataset = getXYDataset(winds);
    
      // Create chart
      JFreeChart chart = ChartFactory.createPolarChart(
            "Wind Direction and Speed", // Chart title
            dataset,
            true,
            true,
            false
            );

      ChartPanel panel = new ChartPanel(chart);
      panel.setMouseZoomable(true);
      panel.setPreferredSize(new java.awt.Dimension(500, 570));
      panel.setBackground(Color.white);
        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.NORTH);
        DefaultPolarItemRenderer renderer = new DefaultPolarItemRenderer();
        //renderer.setShapesVisible(false);
        renderer.setSeriesFilled(0, false);
        renderer.setSeriesPaint(0, Color.RED);
        Stroke dashedStroke = new BasicStroke(0.0f, BasicStroke.CAP_ROUND, 
        BasicStroke.JOIN_ROUND,0.0f,new float[]{0.0f,1e10f},1.0f);
        
        renderer.setSeriesStroke(0, dashedStroke);
//        Rectangle rect = new Rectangle(0,0);
//        renderer.setSeriesShape(0, rect);
        ((PolarPlot) chart.getPlot()).setRenderer(renderer);
        
        
   }

   private XYDataset getXYDataset(ArrayList<WindObj> winds) {
     
      XYSeriesCollection dataset = new XYSeriesCollection();
      
      XYSeries series1 = new XYSeries("Direction and speed");
        
        for (int i = 0; i < winds.size(); i++){
            WindObj wo = winds.get(i);
            series1.add(wo.getDirection(), wo.getSpeed());
        }
        
      dataset.addSeries(series1);
      
      return dataset;
   }

   public static void main(String[] args) {
//      SwingUtilities.invokeLater(() -> {
//         PolarLineChartExample example = new PolarLineChartExample("Gantt Chart Example");
//         example.setSize(800, 400);
//         example.setVisible(true);
//      });
   }
}