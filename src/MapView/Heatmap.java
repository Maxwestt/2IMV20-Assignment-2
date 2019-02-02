/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapView;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import org.tc33.jheatchart.HeatChart;

/**
 *
 * @author tosku
 */
public class Heatmap {
    double[][] heatMap;
   
    BufferedImage nlimg;
    HashMap<Integer,Point> StationButtons;
    ArrayList<Station> stations;
    Calendar calendar;
    
    
    public Heatmap(BufferedImage img, HashMap<Integer,Point> sb, ArrayList<Station> s){
        this.nlimg = img;
        this.StationButtons = sb;
        this.stations = s;
        calendar = new GregorianCalendar();
        
        heatMap = new double[nlimg.getWidth()/10][nlimg.getHeight()/10];
        
        
    }
    
    //Cast stations to a grid based on their coordinates, and give them a value. 
    // Value is based on the average of all included data.
    public BufferedImage genHeatmap( 
            String heatMapSelected, int selectedMonth, int selectedYear){
        for (double[] emptyheatMap1 : heatMap) {
            for (int j = 0; j < heatMap[0].length; j++) {
                emptyheatMap1[j] = -9999.9;
            }
        }
        
        StationButtons.keySet().forEach((i) -> {
            int xi = StationButtons.get(i).x/10;
            int yi = heatMap[0].length-(StationButtons.get(i).y/10)-1;
            ArrayList<Double> times = (ArrayList<Double>) stations.get(i).getMap().get("time");
            ArrayList<Double> temps = (ArrayList<Double>) stations.get(i).getMap().get(heatMapSelected);
            double runningavg = 0;
            double totalm = 0;
            int begin = findApproxBeginIndex(selectedYear, times);
            for(int j = begin; j < Math.min((begin+467), times.size()); j++){
                try {
                    //System.out.println(Integer.toString(times.get(j).intValue()));
                    Date temp = new SimpleDateFormat("yyyyMMdd").parse(Integer.toString(times.get(j).intValue()));
                    calendar.setTime(temp);                    
                    if(calendar.get(Calendar.MONTH) == selectedMonth-1 &&calendar.get(Calendar.YEAR) == selectedYear){
                        double curt = temps.get(j);
                        if(!Double.isNaN(curt)){
                            runningavg += curt;
                            totalm++;
                        }
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(VisLineChartEx.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (totalm>0) {
                heatMap[xi][yi] = runningavg/totalm;
                populateNeighbours(heatMap, xi, yi, 1.0);
            }
        });
        
        double[][] interpHeatMap = heatMap.clone();
        for (int i = 0; i < heatMap.length; i++) {
            for (int j = 0; j < heatMap[0].length; j++) {
                interpHeatMap[i][j] = interpedval(heatMap, i, j);
            }
        }
        
        BufferedImage heatmapimg = (BufferedImage) buildHeatChart(interpHeatMap, heatMapSelected).getChartImage();
        return rotscaleimg(heatmapimg);
    }
    
    //Use library to build a heatmap from data
    private HeatChart buildHeatChart(double[][] data, String att){
        HeatChart hmap = new HeatChart(data);
        hmap.setAxisThickness(0);
        hmap.setShowXAxisValues(false);
        hmap.setShowYAxisValues(false);
        switch(att){
            case "tempavg":
                //heatMapSelected= "tempavg";
                hmap.setHighValueColour(Color.RED);
                hmap.setLowValueColour(Color.WHITE);
            case "tempmin":
                hmap.setHighValueColour(Color.RED);
                hmap.setLowValueColour(Color.WHITE);
               break;
            case "tempmax":
                hmap.setHighValueColour(Color.RED);
                hmap.setLowValueColour(Color.WHITE);
               break;   
            case "windsp":
                hmap.setHighValueColour(Color.YELLOW);
                hmap.setLowValueColour(Color.GREEN);
               break;
            case "pressure":
                hmap.setHighValueColour(Color.MAGENTA);
                hmap.setLowValueColour(Color.BLACK);
               break;
            case "percipation":
                hmap.setHighValueColour(Color.BLUE);
                hmap.setLowValueColour(Color.CYAN);
               break;   
        }
        hmap.setColourScale(1.3);
        hmap.setChartMargin(0);
        return hmap;
    }
    
    //Populate a cell's neighbours to give each point a "zone" of their value
    public void populateNeighbours(double[][] a, int x, int y,  double str){
        for(int i = -2; i<3;i++){
            for(int j = -2;j<3;j++){
                if(!(Math.abs(i)+Math.abs(j)>3)){
                    addP(a, x+i, y+j, a[x][y]);//*(0.7+0.3/((Math.abs(i)+Math.abs(j)+0.1))));   
                }
            }
        }
    }
    
    //Add a value, averaging if point is already at non-default value.
    private void addP(double[][] a, int x, int y, double d) {
        if(x>=0&&x<a.length&&y>=0&&y<a[0].length){
            if(a[x][y]<-9999){
                a[x][y] = d;
            }
            else{
                a[x][y] = (a[x][y]+d)/2;
            }
        }
    }
    
    
   //Interpolate using square distance as weight function
    public double interpedval(double[][] t, int x, int y) {
        //double[][] temp2 = new double[t.length][t[0].length];
        double total_val = 0.0;
        double total_weight = 0.0;
        if(t[x][y]>-9998.0){
            return t[x][y];
        }
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[0].length; j++) {
                if (!(t[i][j] < -9998)) { 
                    if (x == i && y == j) {
                        return t[i][j];
                    }
                    double d_sqr = ((i - x) * (i - x)) + ((j - y) * (j - y));
                    total_weight = total_weight + (1.0 / (d_sqr));
                    total_val = total_val + (t[i][j] / (d_sqr));
                }
            }
        }
        return total_val / total_weight;
    }

    //The heatchart generated is not rotated and scaled correctly. Specifically, we rotate it
    //270 degrees and then scale it to the size of our map.
    private BufferedImage rotscaleimg(BufferedImage heatmapimg) {
        
        //Rotate heatmap
        BufferedImage rotated = new BufferedImage(heatmapimg.getHeight(), heatmapimg.getWidth(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) rotated.createGraphics();
        AffineTransform xform = new AffineTransform();
        xform.translate(0.5*heatmapimg.getHeight(), 0.5*heatmapimg.getWidth());
        xform.rotate(Math.toRadians(270));
        xform.translate(-0.5*heatmapimg.getWidth(), -0.5*heatmapimg.getHeight());
        g2.drawImage(heatmapimg, xform, null);
        g2.dispose();
        
        //Scale heatmap
        Image resized = rotated.getScaledInstance(nlimg.getWidth(), nlimg.getHeight(), Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(nlimg.getWidth(), nlimg.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(resized, 0, 0, null);
        g2d.dispose();
        
        
        //Overlay heatmap onto map
        BufferedImage combined = new BufferedImage(nlimg.getWidth(), nlimg.getHeight(),nlimg.getType());
        Graphics2D g = combined.createGraphics();
        g.setComposite(AlphaComposite.SrcOver);
        g.drawImage(nlimg, 0,0,null);
        float alpha = .6f;
        g.setComposite(AlphaComposite.SrcOver.derive(alpha));
        g.drawImage(dimg,0,0,null);
        g.dispose();
        
        //Color non-land area blue
        for(int k = 0; k<nlimg.getWidth();k++){
            for(int l = 0; l<nlimg.getHeight();l++){
                if (new Color(nlimg.getRGB(k,l)).equals(Color.WHITE)){
                    combined.setRGB(k, l, new Color(135,206,250).getRGB());
                }
            }
        }
        
        return combined;
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
