package MapView;

import java.awt.BorderLayout;
import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.BoxAndWhiskerToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.data.statistics.BoxAndWhiskerCategoryDataset;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.jfree.util.Log;
import org.jfree.util.LogContext;

/**
 * Demonstration of a box-and-whisker chart using a {@link CategoryPlot}.
 *
 * @author David Browning
 */
public class BoxAndWhiskerChart extends JPanel {

    /** Access to logging facilities. */
    //private static final LogContext LOGGER = Log.createContext(BoxAndWhiskerChart.class);

    Station st;
    ArrayList<Station> stations;
    int year;
    
    public BoxAndWhiskerChart(String title, Station st, ArrayList<Station> stations, int year) {
        
        this.st = st;
        this.stations = stations;
        this.year = year;
        //stations.remove(st);
        BoxAndWhiskerCategoryDataset dataset = createSampleDataset();

        CategoryAxis xAxis = new CategoryAxis("Type");
        NumberAxis yAxis = new NumberAxis("Value");
        yAxis.setAutoRangeIncludesZero(false);
        BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
        renderer.setFillBox(false);
        //renderer.setToolTipGenerator(new BoxAndWhiskerToolTipGenerator());
        CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);

        JFreeChart chart = new JFreeChart(
            "Box-and-Whisker Demo",
            new Font("SansSerif", Font.BOLD, 14),
            plot,
            true
        );
        ChartPanel panel = new ChartPanel(chart);
        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.NORTH);

    }

    private BoxAndWhiskerCategoryDataset createSampleDataset() {
        
        int seriesCount = 2;
        int categoryCount = 12;
        
        String[] months = new String[12];
        months[0] = "January";
        months[1] = "February";
        months[2] = "March";
        months[3] = "April";
        months[4] = "May";
        months[5] = "June";
        months[6] = "July";
        months[7] = "August";
        months[8] = "September";
        months[9] = "October";
        months[10] = "November";
        months[11] = "December";
        
        DefaultBoxAndWhiskerCategoryDataset dataset 
            = new DefaultBoxAndWhiskerCategoryDataset();
        
        //First, add the dataset for the station
        
        ArrayList<ArrayList<Double>> list1;
        list1 = new ArrayList<>();
        for (int i = 0; i < 12; i++){
            list1.add(new ArrayList<Double>());
        }
        
        //Then, add the dataset for all the stations
        ArrayList<ArrayList<Double>> list2;
        list2 = new ArrayList<>();
        for (int i = 0; i < 12; i++){
                list2.add(new ArrayList<>());
        }
        
        stations.forEach((stat) -> {
            ArrayList<Double> times2 = (ArrayList<Double>) stat.getMap().get("time");
            ArrayList<Double> temps2 = (ArrayList<Double>) stat.getMap().get("tempavg");
            
            int begin2;
            int end2;
            
            if(year<0){
                begin2 = 0;
                end2 = temps2.size();
            }else{
                begin2 = findApproxBeginIndex(year, times2);
                end2 = Math.min((begin2+467),times2.size());
            }
            if (begin2 != -1) {
                for (int j = begin2; j < end2; j++) {
                    try {
                        Date temp = new SimpleDateFormat("yyyyMMdd").parse(Integer.toString(times2.get(j).intValue()));
                        Calendar calendar = new GregorianCalendar();
                        calendar.setTime(temp);
                        if ((calendar.get(Calendar.YEAR) == year) || year < 0) {
                            double curt = temps2.get(j);
                            if (!Double.isNaN(curt)) {
                                int i = calendar.get(Calendar.MONTH);
                                if(stat.getNum() == st.getNum()){
                                    list1.get(i).add(curt);
                                }
                                else{
                                    list2.get(i).add(curt);
                                }
                            }
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(BoxAndWhiskerChart.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        for (int i = 0; i < 12; i++){
            dataset.add((ArrayList<Double>) list1.get(i), "Current Station", months[i]);
            dataset.add((ArrayList<Double>) list2.get(i), "All Stations", months[i]);
        }
        
        
        return dataset;
    }
    
    public int findApproxBeginIndex(int year, ArrayList<Double> dates){
       
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
               //System.out.println(dates.get(i));
//Logger.getLogger(PolarLineChartEx.class.getName()).log(Level.SEVERE, null, ex);
           }
          
       }
       return -1;
   }

    // ****************************************************************************
    // * JFREECHART DEVELOPER GUIDE                                               *
    // * The JFreeChart Developer Guide, written by David Gilbert, is available   *
    // * to purchase from Object Refinery Limited:                                *
    // *                                                                          *
    // * http://www.object-refinery.com/jfreechart/guide.html                     *
    // *                                                                          *
    // * Sales are used to provide funding for the JFreeChart project - please    * 
    // * support us so that we can continue developing free software.             *
    // ****************************************************************************
    
    /**
     * For testing from the command line.
     *
     * @param args  ignored.
     */
    public static void main(final String[] args) {
        //Log.getInstance().addTarget(new PrintStreamLogTarget(System.out));
//        final BoxAndWhiskerDemo demo = new BoxAndWhiskerDemo("Box-and-Whisker Chart Demo");
//        demo.pack();
//        RefineryUtilities.centerFrameOnScreen(demo);
//        demo.setVisible(true);
    }
}