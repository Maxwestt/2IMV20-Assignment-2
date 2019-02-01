//package MapView;
//
//import java.awt.Dimension;
//import java.util.*;
//
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.PolarChartPanel;
//import org.jfree.chart.plot.PolarPlot;
//import org.jfree.chart.renderer.DefaultPolarItemRenderer;
//import org.jfree.data.xy.XYDataset;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;
//import org.jfree.ui.ApplicationFrame;
//import org.jfree.ui.RefineryUtilities;
//
///**
// * <code>PolarChartDemo</code> demonstrates the capabilities of the {@link PolarPlot}.
// * 
// * @author  Daniel Bridenbecker, Solution Engineering, Inc.
// */
//public class PolarChart extends JPanel {
//    
//    static ArrayList<Double> direction, speed;
//    static ArrayList<WindObj> winds;
//    public PolarChart(final String title, ArrayList<Double> direction, ArrayList<Double> speed) {
//        super(title);
//        this.direction = direction;
//        this.speed = speed;
//        
//        winds = new ArrayList<WindObj>();
//        
//        for (int i = 0; i < direction.size(); i++){
//            WindObj wo = new WindObj(direction.get(i), speed.get(i));
//            winds.add(wo);
//        }
//        
//        Collections.sort(winds, new Comparator<WindObj>() {
//            @Override
//            public int compare(WindObj o1, WindObj o2) {
//                return o1.getDirection().compareTo(o2.getDirection());
//            }
//        });
//        
//        Collections.sort(winds, new WindComparator());
//        System.out.println(winds.get(0).getDirection());
//        System.out.println(winds.get(2).getDirection());
//        System.out.println(winds.get(24).getDirection());
//        
//        final XYDataset dataset = createDataset(winds);
//        final JFreeChart chart = createChart(dataset);
//        final ChartPanel chartPanel = new PolarChartPanel(chart);
//        chartPanel.setPreferredSize(new Dimension(500, 270));
//        chartPanel.setEnforceFileExtensions(false);
//        //setContentPane(chartPanel);
//    }
//    
//    /**
//     * Creates a sample dataset.
//     * 
//     * @return A sample dataset.
//     */
//    private XYDataset createDataset(ArrayList<WindObj> winds) {    
//        final XYSeriesCollection data = new XYSeriesCollection();
//        
//        XYSeries series1 = new XYSeries("Direction and speed");
//        
//        for (int i = 0; i < winds.size(); i++){
//            WindObj wo = winds.get(i);
//            series1.add(wo.getDirection(), wo.getSpeed());
//        }
//        data.addSeries(series1);
//        return data;
//    }
//    
//    /**
//     * Creates a sample chart.
//     * 
//     * @param dataset  the dataset.
//     * 
//     * @return A sample chart.
//     */
//    private JFreeChart createChart(final XYDataset dataset) {
//        final JFreeChart chart = ChartFactory.createPolarChart(
//            "Polar Chart Demo", dataset, true, true, false
//        ); 
//        final PolarPlot plot = (PolarPlot) chart.getPlot();
//        final DefaultPolarItemRenderer renderer = (DefaultPolarItemRenderer) plot.getRenderer();
//        renderer.setSeriesFilled(2, true);
//        return chart;
//    }
//    
//    /**
//     * Main program that creates a thermometer and places it into
//     * a JFrame.
//     *
//     * @param argv Command line arguements - none used.
//     */
//    public static void main(final String[] argv) {
//         
//        final PolarChart chart = new PolarChart("Polar Chart Demo", direction, speed);
////        demo.pack();
////        RefineryUtilities.centerFrameOnScreen(demo);
//        chart.setVisible(true);
//        
//    }   
//       
//}
//
//class WindComparator implements Comparator<WindObj> {
//    
//    public int compare(WindObj o1, WindObj o2) {
//        return o1.getDirection().compareTo(o2.getDirection());
//    }
//}