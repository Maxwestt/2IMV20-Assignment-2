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
    private static final LogContext LOGGER = Log.createContext(BoxAndWhiskerChart.class);

    Station st;
    ArrayList<Station> stations;
    public BoxAndWhiskerChart(String title, Station st, ArrayList<Station> stations) {
        
        this.st = st;
        this.stations = stations;
        stations.remove(st);
        BoxAndWhiskerCategoryDataset dataset = createSampleDataset();

        CategoryAxis xAxis = new CategoryAxis("Type");
        NumberAxis yAxis = new NumberAxis("Value");
        yAxis.setAutoRangeIncludesZero(false);
        BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
        renderer.setFillBox(false);
        renderer.setToolTipGenerator(new BoxAndWhiskerToolTipGenerator());
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
        
        List list = new ArrayList();
        for (int i = 0; i < 12; i++){
            list.add(new ArrayList<Double>());
        }
        
        ArrayList<Double> times = (ArrayList<Double>) st.getMap().get("time");
        ArrayList<Double> temps = (ArrayList<Double>) st.getMap().get("tempavg");
        
        for (int i = 0; i < 12; i++){
            for(int j = 0; j<times.size(); j++){
                try {
                    Date temp = new SimpleDateFormat("yyyyMMdd").parse(Integer.toString(times.get(j).intValue()));
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(temp);
                        if(calendar.get(Calendar.MONTH) == i){
//                            System.out.println("year:" +calendar.get(Calendar.YEAR) + " and month: " + calendar.get(Calendar.MONTH));   
                            double curt = temps.get(j);
                            if(!Double.isNaN(curt)){
                                ((ArrayList<Double>) (list.get(i))).add(curt);
                            }
                        }
                } catch (ParseException ex) {
                    Logger.getLogger(BoxAndWhiskerChart.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        for (int i = 0; i < 12; i++){
            dataset.add((ArrayList<Double>) list.get(i), "Single Station", months[i]);
        }
                
        //Then, add the dataset for all the stations
        List list2 = new ArrayList();
        for (int i = 0; i < 12; i++){
                list2.add(new ArrayList<Double>());
            }
        for (Station stat: stations){
            ArrayList<Double> times2 = (ArrayList<Double>) stat.getMap().get("time");
            ArrayList<Double> temps2 = (ArrayList<Double>) stat.getMap().get("tempavg");
        
            for (int i = 0; i < 12; i++){
                for(int j = 0; j<times2.size(); j++){
                    try {
                        Date temp = new SimpleDateFormat("yyyyMMdd").parse(Integer.toString(times2.get(j).intValue()));
                        Calendar calendar = new GregorianCalendar();
                        calendar.setTime(temp);
                        if(calendar.get(Calendar.MONTH) == i){   
                            double curt = temps2.get(j);
                            if(!Double.isNaN(curt)){
                                ((ArrayList<Double>) (list2.get(i))).add(curt);
                            }
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(BoxAndWhiskerChart.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        
        for (int i = 0; i < 12; i++){
            dataset.add((ArrayList<Double>) list2.get(i), "All Stations", months[i]);
        }
        return dataset;
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