/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapView;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
//import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import org.jfree.data.time.Day;
import org.tc33.jheatchart.HeatChart;

/**
 *
 * @author tosku
 */
public class MapViewTest extends javax.swing.JFrame {

    ArrayList<Station> stations = new ArrayList<Station>();
    
    int stationNum;
    String stationName, opening;
    float longitude, latitude, height;
    HashMap<Integer,Station> H;
    ArrayList<Integer> stationnumbers = new ArrayList<Integer>();
    HashMap<Integer,Point> StationButtons;
    boolean timeYear;
    int selectedYear;
    int selectedMonth;
    ImageIcon nlmap;
    BufferedImage nlimg; 
    double[][] heatMap;
    String heatMapSelected;
    
    public MapViewTest() {
        H = new HashMap<>();
        initComponents();
        loadData();
        stationNum = stations.get(0).getNum();
        setChart1();
        jYearBox.removeAllItems();
        timeYear = false;
        selectedYear = 2018;
        selectedMonth = 1;
        nlmap = (ImageIcon)jLabel1.getIcon();
        heatMapSelected = "tempavg";
        //BufferedImage nlmap;
    }
    
    public void loadData(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("src/MapView/data/Stations.csv"));
            String line = br.readLine();
            
            while (line != null){
                String[] attributes = line.split(",");
                stationnumbers.add(Integer.parseInt(attributes[0].substring(1)));
                Station station = createStation(attributes);
                stations.add(station);
                line = br.readLine();
            }
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
        
       
        for (int snum: stationnumbers){
            //System.out.println(stations.get(stationnumbers.indexOf(snum)));
            H.put(snum, stations.get(stationnumbers.indexOf(snum)));
            //System.out.println(H.get(snum));
        }
        
        System.out.println(H.keySet());
        
        String[] stringAtts = new String[8];
        stringAtts[0] = "time";
        stringAtts[1] = "winddir";
        stringAtts[2] = "windsp";
        stringAtts[3] = "tempavg";
        stringAtts[4] = "tempmin";
        stringAtts[5] = "tempmax";
        stringAtts[6] = "percipation";
        stringAtts[7] = "pressure";
        
        try{
            BufferedReader br = new BufferedReader(new FileReader("src/MapView/data/KNMI_reduced.txt"));
            String line;
            
            while ( (line = br.readLine()) != null ){
                if (!line.startsWith("#")){
                    String[] attributes = line.split(",");
                    int snum = Integer.parseInt(attributes[0].substring(2));
                    
                    for (int i = 1; i < attributes.length; i++){
                        //System.out.println(H.get(snum).getClass());
                        ArrayList<Double> al = (ArrayList<Double>) (H.get(snum).getMap().get(stringAtts[i-1]));
                        if (attributes[i].replaceAll("\\s+","").isEmpty()){
                            al.add(Double.NaN);
                        } else {
                            if (i > 2){
                                al.add(Double.parseDouble(attributes[i])/10.0);
                            }
                            else{
                                al.add(Double.parseDouble(attributes[i]));
                            }
                        }
                    }
                }
            }
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
        
        setUpHeatmap();
    }
    
    public void setChart1(){
        
        VisLineChart b = new VisLineChart("", "");
        ArrayList<Double> dirData1 = ((ArrayList<Double>) (H.get(stationNum).getMap().get("winddir")));
        ArrayList<Double> speedData1 = ((ArrayList<Double>) (H.get(stationNum).getMap().get("windsp")));
        ArrayList<Double> xdata = ((ArrayList<Double>) (H.get(stationNum).getMap().get("time")));
        ArrayList<Double> ydata = ((ArrayList<Double>) (H.get(stationNum).getMap().get("tempavg")));
        
        PolarLineChartExample a; 
        if (timeYear){
            a = new PolarLineChartExample(xdata, dirData1, speedData1, selectedYear);
        } else {
            a = new PolarLineChartExample(xdata, dirData1, speedData1, -1);
        }
        
        
        VisLineChartEx c;
        if (timeYear){
            c = new VisLineChartEx(xdata, ydata, selectedYear);
        } else {
            c = new VisLineChartEx(xdata, ydata, -1);
        }
        
        VisLineChartMultiple d;
        
        ArrayList<Double> xdata1 = ((ArrayList<Double>) (H.get(stationNum).getMap().get("time")));
        ArrayList<Double> ydata1 = ((ArrayList<Double>) (H.get(stationNum).getMap().get("tempavg")));
        ArrayList<Double> ydata2 = ((ArrayList<Double>) (H.get(stationNum).getMap().get("tempmin")));
        ArrayList<Double> ydata3 = ((ArrayList<Double>) (H.get(stationNum).getMap().get("tempmax")));
        
        
        if (timeYear){
            d = new VisLineChartMultiple(xdata1, ydata1, ydata2, ydata3, selectedYear);
        } else {
            d = new VisLineChartMultiple(xdata1, ydata1, ydata2, ydata3, -1);
        }
        
        
        
        this.jSplitPane3.setTopComponent(b);
        this.jSplitPane4.setTopComponent(a);
        this.jSplitPane4.setBottomComponent(c);
        this.jSplitPane3.setBottomComponent(d);
        //JFrame frame = new JFrame();
        //this.add(a);
        //this.pack();
        //JFrame frame = new JFrame();
        //frame.add(a);
        pack();
        setVisible(true);
    }
    
    public static String removeLastChar(String s) {
    return (s == null || s.length() == 0)
      ? null
      : (s.substring(0, s.length() - 1));
}
    
    private static Station createStation(String[] meta){
        meta[0] = meta[0].substring(1);
        int num = Integer.parseInt(meta[0]);
        String name = removeLastChar(meta[5].trim());
        float longi = Float.parseFloat(meta[1]);
        float lat = Float.parseFloat(meta[2]);
        float height = Float.parseFloat(meta[3]);
        String opening = meta[4];
        
        return new Station(num, name, longi, lat, height, opening);
    }
    
    
    public void updateInfo(int i){
        Station stat = stations.get(i);
        stationNum = stat.getNum();
        jTextNum.setText(String.valueOf(stationNum));
        stationName = stat.getName();
        jTextName.setText(stationName);
        longitude = stat.getLong();
        jTextLong.setText(String.valueOf(longitude));
        latitude = stat.getLat();
        jTextLat.setText(String.valueOf(latitude));
        height = stat.getHeight();
        jTextHeight.setText(String.valueOf(height));
        opening = stat.getOpening();
        jTextOpening.setText(opening);
        
        jYearBox.removeAllItems();
        
        int startyear = Integer.parseInt(opening.substring(0, 4));
        int endyear = 2018;
        for (int y = startyear; y <= endyear; y++){
            jYearBox.addItem(Integer.toString(y));
        }
        
        setChart1();
    }
    
    public int findStationNrIndex(int nr){
        for(int i = 0; i < stations.size(); i++){
            if (nr == stations.get(i).getNum()){
                return i;
            }
        }
        return 0; //"none" is selected
    }
    
    public void setUpHeatmap(){
        StationButtons = new HashMap<>();
        StationButtons.put(findStationNrIndex(209), jStation209.getLocation());
        StationButtons.put(findStationNrIndex(215), jStation215.getLocation());
        StationButtons.put(findStationNrIndex(235), jStation235.getLocation());
        StationButtons.put(findStationNrIndex(240), jStation240.getLocation());
        StationButtons.put(findStationNrIndex(248), jStation248.getLocation());
        StationButtons.put(findStationNrIndex(251), jStation251.getLocation());
        StationButtons.put(findStationNrIndex(259), jStation259.getLocation());
        StationButtons.put(findStationNrIndex(270), jStation270.getLocation());
        StationButtons.put(findStationNrIndex(274), jStation274.getLocation());
        StationButtons.put(findStationNrIndex(275), jStation275.getLocation());
        StationButtons.put(findStationNrIndex(278), jStation278.getLocation());
        StationButtons.put(findStationNrIndex(280), jStation280.getLocation());
        StationButtons.put(findStationNrIndex(283), jStation283.getLocation());
        StationButtons.put(findStationNrIndex(286), jStation286.getLocation());
        StationButtons.put(findStationNrIndex(290), jStation290.getLocation());
        StationButtons.put(findStationNrIndex(308), jStation308.getLocation());
        StationButtons.put(findStationNrIndex(310), jStation310.getLocation());
        StationButtons.put(findStationNrIndex(315), jStation315.getLocation());
        StationButtons.put(findStationNrIndex(319), jStation319.getLocation());
        StationButtons.put(findStationNrIndex(330), jStation330.getLocation());
        StationButtons.put(findStationNrIndex(343), jStation343.getLocation());
        StationButtons.put(findStationNrIndex(348), jStation348.getLocation());
        StationButtons.put(findStationNrIndex(356), jStation356.getLocation());
        StationButtons.put(findStationNrIndex(370), jStation370.getLocation());
        StationButtons.put(findStationNrIndex(375), jStation375.getLocation());
        StationButtons.put(findStationNrIndex(377), jStation377.getLocation());
        StationButtons.put(findStationNrIndex(380), jStation380.getLocation());
        StationButtons.put(findStationNrIndex(391), jStation391.getLocation());
        
        try {
            nlimg = ImageIO.read(new File("src/MapView/images/Map Netherlands v2.0.png"));
        } catch (IOException ex) {
            Logger.getLogger(MapViewTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
    
    


    
    public void genHeatmap(){
        
        heatMap = new double[nlimg.getWidth()/10][nlimg.getHeight()/10];
        for (int i = 0; i < heatMap.length; i++) {
            for (int j = 0; j < heatMap[0].length; j++) {
                heatMap[i][j] = -9999.9;
            }
        }
        //setLayout(new BorderLayout());
        //this.jSplitPane1.setBottomComponent(a);
        for(int i: StationButtons.keySet()){
            int xi = StationButtons.get(i).x/10;
            int yi = heatMap[0].length-(StationButtons.get(i).y/10)-1;
            ArrayList<Double> times = (ArrayList<Double>) stations.get(i).getMap().get("time");
            ArrayList<Double> temps = (ArrayList<Double>) stations.get(i).getMap().get(heatMapSelected);
            double runningavg = 0;
            double totalm = 0;
            for(int j = 0; j<times.size(); j++){
                try {
                    //System.out.println(Integer.toString(times.get(j).intValue()));
                    Date temp = new SimpleDateFormat("yyyyMMdd").parse(Integer.toString(times.get(j).intValue()));
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(temp);
                    //if (year > 0){
                    
                        if(calendar.get(Calendar.MONTH) == selectedMonth-1 &&calendar.get(Calendar.YEAR) == selectedYear){
                            //System.out.println("year:" +calendar.get(Calendar.YEAR));
                                    
                            //System.out.println(calendar.get(Calendar.MONTH));
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
            if(totalm>0){
                heatMap[xi][yi] = runningavg/totalm;
                populateNeighbours(heatMap, xi, yi, 1.0);
                
//                heatMap[xi+1][yi+1] = runningavg/totalm;
//                heatMap[xi][yi+1] = runningavg/totalm;
////                heatMap[xi-1][yi] = runningavg/totalm;
//                heatMap[xi][yi-1] = runningavg/totalm;
//                heatMap[xi-1][yi-1] = runningavg/totalm;
//                heatMap[xi-1][yi+1] = runningavg/totalm;
//                heatMap[xi+1][yi-1] = runningavg/totalm;
                //heatMap[xi+2][yi] = runningavg/totalm;
                //heatMap[xi+2][yi+1] = runningavg/totalm;
                
                //heatMap[xi][yi+2] = runningavg/totalm;
                //heatMap[xi+2][yi] = runningavg/totalm;
                //heatMap[xi][yi-2] = runningavg/totalm;
//                heatMap[xi-2][yi] = runningavg/totalm;
            }
            //System.out.println(StationButtons.get(i));
        }
        double[][] interpHeatMap = heatMap.clone();

        for (int i = 0; i < heatMap.length; i++) {
            for (int j = 0; j < heatMap[0].length; j++) {
                interpHeatMap[i][j] = interpedval(heatMap, i, j);
            }
        }
        
        DecimalFormat df = new DecimalFormat("0.00");
        for (int i = 0; i < interpHeatMap.length; i++) {
            for (int j = 0; j < interpHeatMap[0].length; j++) {
                System.out.print(df.format(interpHeatMap[i][j]) + " | ");
            }
            System.out.println("");
        }
        
        HeatChart hmap = new HeatChart(interpHeatMap);
        hmap.setAxisThickness(0);
        hmap.setShowXAxisValues(false);
        hmap.setShowYAxisValues(false);
        if(heatMapSelected.equals("tempavg")){
            hmap.setHighValueColour(Color.RED);
            hmap.setLowValueColour(Color.WHITE);

        }
        if(heatMapSelected.equals("windsp")){
            hmap.setHighValueColour(Color.YELLOW);
            hmap.setLowValueColour(Color.GREEN);

        }
        
        hmap.setColourScale(1.3);
        hmap.setChartMargin(0);
        //hmap.setCellSize(new Dimension(nlimg.getHeight()/20, nlimg.getWidth()/20));
        BufferedImage heatmapimg = (BufferedImage) hmap.getChartImage();
       
        
        BufferedImage rotated = new BufferedImage(heatmapimg.getHeight(), heatmapimg.getWidth(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) rotated.createGraphics();

        AffineTransform xform = new AffineTransform();
        xform.translate(0.5*heatmapimg.getHeight(), 0.5*heatmapimg.getWidth());
        xform.rotate(Math.toRadians(270));
        xform.translate(-0.5*heatmapimg.getWidth(), -0.5*heatmapimg.getHeight());
        g2.drawImage(heatmapimg, xform, null);
        g2.dispose();
        
        
        Image resized = rotated.getScaledInstance(nlimg.getWidth(), nlimg.getHeight(), Image.SCALE_SMOOTH);
        System.out.println(nlimg.getWidth());
        System.out.println(nlmap.getIconHeight());
        BufferedImage dimg = new BufferedImage(nlimg.getWidth(), nlimg.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(resized, 0, 0, null);
        g2d.dispose();
        
        
        
        BufferedImage combined = new BufferedImage(nlimg.getWidth(), nlimg.getHeight(),nlimg.getType());
        Graphics2D g = combined.createGraphics();
        g.setComposite(AlphaComposite.SrcOver);
        g.drawImage(nlimg, 0,0,null);
        float alpha = .6f;
        g.setComposite(AlphaComposite.SrcOver.derive(alpha));
        g.drawImage(dimg,0,0,null);
        
        g.dispose();
        for(int k = 0; k<nlimg.getWidth();k++){
            for(int l = 0; l<nlimg.getHeight();l++){
                if (new Color(nlimg.getRGB(k,l)).equals(Color.WHITE)){
                    combined.setRGB(k, l, new Color(135,206,250).getRGB());
                }
            }
        }
        
        jLabel1.setIcon(new ImageIcon(combined));
        
        //System.out.println(((ArrayList<Double>)stations.get(findStationNrIndex(275)).getMap().get("windsp")).get(1000));
    }
    
    public void populateNeighbours(double[][] a, int x, int y,  double str){
        for(int i = -2; i<3;i++){
            for(int j = -2;j<3;j++){
                if(!(Math.abs(i)+Math.abs(j)>3)){
                    addP(a, x+i, y+j, a[x][y]);//*(0.7+0.3/((Math.abs(i)+Math.abs(j)+0.1))));   
                }
            }
        }
    }
    
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
    
    public double interpedval(double[][] t, int x, int y) {
        double[][] temp2 = new double[t.length][t[0].length];
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LocationChoice = new javax.swing.ButtonGroup();
        TimeScaleChoice = new javax.swing.ButtonGroup();
        jSlider1 = new javax.swing.JSlider();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jStation209 = new javax.swing.JRadioButton();
        jStation215 = new javax.swing.JRadioButton();
        jStation251 = new javax.swing.JRadioButton();
        jStation235 = new javax.swing.JRadioButton();
        jStation240 = new javax.swing.JRadioButton();
        jStation248 = new javax.swing.JRadioButton();
        jStation259 = new javax.swing.JRadioButton();
        jStation270 = new javax.swing.JRadioButton();
        jStation278 = new javax.swing.JRadioButton();
        jStation275 = new javax.swing.JRadioButton();
        jStation274 = new javax.swing.JRadioButton();
        jStation280 = new javax.swing.JRadioButton();
        jStation283 = new javax.swing.JRadioButton();
        jStation286 = new javax.swing.JRadioButton();
        jStation290 = new javax.swing.JRadioButton();
        jStation308 = new javax.swing.JRadioButton();
        jStation310 = new javax.swing.JRadioButton();
        jStation315 = new javax.swing.JRadioButton();
        jStation319 = new javax.swing.JRadioButton();
        jStation330 = new javax.swing.JRadioButton();
        jStation343 = new javax.swing.JRadioButton();
        jStation348 = new javax.swing.JRadioButton();
        jStation356 = new javax.swing.JRadioButton();
        jStation370 = new javax.swing.JRadioButton();
        jStation375 = new javax.swing.JRadioButton();
        jStation377 = new javax.swing.JRadioButton();
        jStation380 = new javax.swing.JRadioButton();
        jStation391 = new javax.swing.JRadioButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextNum = new javax.swing.JTextField();
        jTextName = new javax.swing.JTextField();
        jTextLong = new javax.swing.JTextField();
        jTextLat = new javax.swing.JTextField();
        jTextHeight = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jTextOpening = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTimeWhole = new javax.swing.JRadioButton();
        jTimeYear = new javax.swing.JRadioButton();
        jYearBox = new javax.swing.JComboBox<>();
        jSlider2 = new javax.swing.JSlider();
        jSplitPane2 = new javax.swing.JSplitPane();
        jSplitPane3 = new javax.swing.JSplitPane();
        jSplitPane4 = new javax.swing.JSplitPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPane1.setDividerSize(2);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jStation209.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation209);
        jStation209.setToolTipText("IJmond");
        jStation209.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation209ActionPerformed(evt);
            }
        });

        jStation215.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation215);
        jStation215.setToolTipText("Valkenburg");
        jStation215.setOpaque(false);
        jStation215.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation215ActionPerformed(evt);
            }
        });

        jStation251.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation251);
        jStation251.setToolTipText("Hoorn (Terschelling)");
        jStation251.setOpaque(false);
        jStation251.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation251ActionPerformed(evt);
            }
        });

        jStation235.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation235);
        jStation235.setToolTipText("De Kooy (Den Helder)");
        jStation235.setOpaque(false);
        jStation235.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation235ActionPerformed(evt);
            }
        });

        jStation240.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation240);
        jStation240.setToolTipText("Schiphol");
        jStation240.setOpaque(false);
        jStation240.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation240ActionPerformed(evt);
            }
        });

        jStation248.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation248);
        jStation248.setToolTipText("Wijdenes");
        jStation248.setOpaque(false);
        jStation248.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation248ActionPerformed(evt);
            }
        });

        jStation259.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation259);
        jStation259.setToolTipText("Houtribdijk");
        jStation259.setOpaque(false);
        jStation259.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation259ActionPerformed(evt);
            }
        });

        jStation270.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation270);
        jStation270.setToolTipText("Leeuwarden");
        jStation270.setOpaque(false);
        jStation270.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation270ActionPerformed(evt);
            }
        });

        jStation278.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation278);
        jStation278.setToolTipText("Heino");
        jStation278.setOpaque(false);
        jStation278.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation278ActionPerformed(evt);
            }
        });

        jStation275.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation275);
        jStation275.setToolTipText("Deelen");
        jStation275.setOpaque(false);
        jStation275.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation275ActionPerformed(evt);
            }
        });

        jStation274.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation274);
        jStation274.setToolTipText("Marknesse");
        jStation274.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation274ActionPerformed(evt);
            }
        });

        jStation280.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation280);
        jStation280.setToolTipText("Eelde");
        jStation280.setOpaque(false);
        jStation280.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation280ActionPerformed(evt);
            }
        });

        jStation283.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation283);
        jStation283.setToolTipText("Hupsel");
        jStation283.setOpaque(false);
        jStation283.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation283ActionPerformed(evt);
            }
        });

        jStation286.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation286);
        jStation286.setToolTipText("Nieuw Beerta");
        jStation286.setOpaque(false);
        jStation286.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation286ActionPerformed(evt);
            }
        });

        jStation290.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation290);
        jStation290.setToolTipText("Twente");
        jStation290.setOpaque(false);
        jStation290.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation290ActionPerformed(evt);
            }
        });

        jStation308.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation308);
        jStation308.setToolTipText("Cadzand");
        jStation308.setOpaque(false);
        jStation308.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation308ActionPerformed(evt);
            }
        });

        jStation310.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation310);
        jStation310.setToolTipText("Vlissingen");
        jStation310.setOpaque(false);
        jStation310.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation310ActionPerformed(evt);
            }
        });

        jStation315.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation315);
        jStation315.setToolTipText("Hansweert");
        jStation315.setOpaque(false);
        jStation315.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation315ActionPerformed(evt);
            }
        });

        jStation319.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation319);
        jStation319.setToolTipText("Westdorpe");
        jStation319.setOpaque(false);
        jStation319.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation319ActionPerformed(evt);
            }
        });

        jStation330.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation330);
        jStation330.setToolTipText("Hoek van Holland");
        jStation330.setOpaque(false);
        jStation330.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation330ActionPerformed(evt);
            }
        });

        jStation343.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation343);
        jStation343.setToolTipText("Rotterdam Geulhaven");
        jStation343.setOpaque(false);
        jStation343.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation343ActionPerformed(evt);
            }
        });

        jStation348.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation348);
        jStation348.setToolTipText("Cabauw");
        jStation348.setOpaque(false);
        jStation348.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation348ActionPerformed(evt);
            }
        });

        jStation356.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation356);
        jStation356.setToolTipText("Herwijnen");
        jStation356.setOpaque(false);
        jStation356.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation356ActionPerformed(evt);
            }
        });

        jStation370.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation370);
        jStation370.setToolTipText("Eindhoven");
        jStation370.setOpaque(false);
        jStation370.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation370ActionPerformed(evt);
            }
        });

        jStation375.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation375);
        jStation375.setToolTipText("Volkel");
        jStation375.setOpaque(false);
        jStation375.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation375ActionPerformed(evt);
            }
        });

        jStation377.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation377);
        jStation377.setToolTipText("Ell");
        jStation377.setOpaque(false);
        jStation377.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation377ActionPerformed(evt);
            }
        });

        jStation380.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation380);
        jStation380.setToolTipText("Maastricht");
        jStation380.setOpaque(false);
        jStation380.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation380ActionPerformed(evt);
            }
        });

        jStation391.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation391);
        jStation391.setToolTipText("Arcen");
        jStation391.setOpaque(false);
        jStation391.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation391ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Temperature", "Wind Speed" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MapView/Images/Map Netherlands v2.0.png"))); // NOI18N

        jLayeredPane1.setLayer(jStation209, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation215, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation251, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation235, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation240, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation248, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation259, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation270, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation278, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation275, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation274, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation280, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation283, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation286, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation290, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation308, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation310, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation315, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation319, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation330, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation343, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation348, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation356, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation370, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation375, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation377, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation380, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation391, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jComboBox1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                        .addGap(73, 73, 73)
                                        .addComponent(jStation319))
                                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                        .addGap(15, 15, 15)
                                        .addComponent(jStation308)))
                                .addGap(1, 1, 1)
                                .addComponent(jStation315))
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jStation310)))
                        .addGap(203, 203, 203)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jStation380)
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addComponent(jStation370)
                                .addGap(21, 21, 21))
                            .addComponent(jStation377))
                        .addGap(37, 37, 37)
                        .addComponent(jStation391))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(188, 188, 188)
                        .addComponent(jStation209)
                        .addGap(214, 214, 214)
                        .addComponent(jStation278))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(jStation330)
                        .addGap(93, 93, 93)
                        .addComponent(jStation348)
                        .addGap(130, 130, 130)
                        .addComponent(jStation275))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(211, 211, 211)
                        .addComponent(jStation235))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addComponent(jStation215))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(282, 282, 282)
                        .addComponent(jStation251))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(159, 159, 159)
                        .addComponent(jStation343)
                        .addGap(82, 82, 82)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jStation356)
                            .addComponent(jStation248)))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(202, 202, 202)
                        .addComponent(jStation240))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(357, 357, 357)
                        .addComponent(jStation270)
                        .addGap(94, 94, 94)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jStation283)
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addComponent(jStation280)
                                .addGap(44, 44, 44)
                                .addComponent(jStation286)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(333, 333, 333)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                                .addComponent(jStation290)
                                .addGap(79, 79, 79))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                                .addComponent(jStation375)
                                .addGap(253, 253, 253))))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1)
                    .addContainerGap(44, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addGap(295, 295, 295)
                    .addComponent(jStation259)
                    .addContainerGap(291, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addGap(380, 380, 380)
                    .addComponent(jStation274)
                    .addContainerGap(206, Short.MAX_VALUE)))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                        .addGap(38, 38, 38)
                                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jStation209)
                                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                                .addComponent(jStation251)
                                                .addGap(20, 20, 20)
                                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jStation270)
                                                    .addComponent(jStation286))
                                                .addGap(50, 50, 50)
                                                .addComponent(jStation235)
                                                .addGap(44, 44, 44)
                                                .addComponent(jStation248)
                                                .addGap(22, 22, 22)))
                                        .addGap(18, 18, 18)
                                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                                .addComponent(jStation240)
                                                .addGap(41, 41, 41)
                                                .addComponent(jStation215))
                                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                                .addComponent(jStation290)
                                                .addGap(19, 19, 19)
                                                .addComponent(jStation283))))
                                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                        .addGap(71, 71, 71)
                                        .addComponent(jStation280)
                                        .addGap(155, 155, 155)
                                        .addComponent(jStation278)))
                                .addGap(13, 13, 13)
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jStation275)
                                    .addComponent(jStation330))
                                .addGap(5, 5, 5))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                                .addComponent(jStation348)
                                .addGap(14, 14, 14)))
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addGap(98, 98, 98)
                                .addComponent(jStation370))
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jStation343)
                                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(jStation356)))
                                .addGap(13, 13, 13)
                                .addComponent(jStation375)
                                .addGap(24, 24, 24)
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jStation310)
                                    .addComponent(jStation315))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jStation308))
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addGap(78, 78, 78)
                                .addComponent(jStation391))))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(512, 512, 512)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jStation377)
                            .addComponent(jStation319))))
                .addGap(58, 58, 58)
                .addComponent(jStation380)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1)
                    .addContainerGap(35, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addGap(238, 238, 238)
                    .addComponent(jStation259)
                    .addContainerGap(441, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addGap(214, 214, 214)
                    .addComponent(jStation274)
                    .addContainerGap(465, Short.MAX_VALUE)))
        );

        jLabel2.setText("Stationnumber");

        jLabel3.setText("Location");

        jLabel4.setText("Longitude");

        jLabel5.setText("Latitude");

        jLabel6.setText("Height");

        jTextName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextNameActionPerformed(evt);
            }
        });

        jButton1.setText("Exit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel7.setText("Opening");

        jLabel8.setText("To View:");

        TimeScaleChoice.add(jTimeWhole);
        jTimeWhole.setSelected(true);
        jTimeWhole.setText("Entire Dataset");
        jTimeWhole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTimeWholeActionPerformed(evt);
            }
        });

        TimeScaleChoice.add(jTimeYear);
        jTimeYear.setText("Specific Year");
        jTimeYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTimeYearActionPerformed(evt);
            }
        });

        jYearBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jYearBox.setEnabled(false);
        jYearBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jYearBoxActionPerformed(evt);
            }
        });

        jSlider2.setMajorTickSpacing(1);
        jSlider2.setMaximum(12);
        jSlider2.setMinimum(1);
        jSlider2.setOrientation(javax.swing.JSlider.VERTICAL);
        jSlider2.setPaintLabels(true);
        jSlider2.setPaintTicks(true);
        jSlider2.setSnapToTicks(true);
        jSlider2.setToolTipText("");
        jSlider2.setValue(1);
        jSlider2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider2StateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 565, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSlider2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(159, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(61, 61, 61)
                                .addComponent(jTextOpening))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(54, 54, 54)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextLat)
                                            .addComponent(jTextHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextLong, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextNum, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextName, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(77, 77, 77)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(127, 127, 127)
                                .addComponent(jYearBox, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTimeWhole)
                                            .addComponent(jTimeYear)))
                                    .addComponent(jLabel8))
                                .addGap(90, 90, 90)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 666, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jSlider2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jTextNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jTextName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(jTextLong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTimeWhole)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTimeYear)
                                    .addComponent(jYearBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextLat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jTextOpening, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jSplitPane1.setLeftComponent(jPanel1);

        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setTopComponent(jSplitPane3);
        jSplitPane2.setRightComponent(jSplitPane4);

        jSplitPane1.setRightComponent(jSplitPane2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1673, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 874, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jYearBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jYearBoxActionPerformed
        if (jYearBox.getSelectedItem()!=null){
            selectedYear = Integer.parseInt((String) jYearBox.getSelectedItem());
        }
        System.out.println("Selected Year: " + selectedYear);
        setChart1();
    }//GEN-LAST:event_jYearBoxActionPerformed

    private void jTimeYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTimeYearActionPerformed
        jYearBox.setEnabled(true);
        timeYear = true;
        setChart1();
    }//GEN-LAST:event_jTimeYearActionPerformed

    private void jTimeWholeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTimeWholeActionPerformed
        jYearBox.setEnabled(false);
        timeYear = false;
        setChart1();
    }//GEN-LAST:event_jTimeWholeActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextNameActionPerformed

    private void jStation391ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation391ActionPerformed
        int n = findStationNrIndex(391);
        updateInfo(n);
    }//GEN-LAST:event_jStation391ActionPerformed

    private void jStation380ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation380ActionPerformed
        int n = findStationNrIndex(380);
        updateInfo(n);
    }//GEN-LAST:event_jStation380ActionPerformed

    private void jStation377ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation377ActionPerformed
        int n = findStationNrIndex(377);
        updateInfo(n);
    }//GEN-LAST:event_jStation377ActionPerformed

    private void jStation375ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation375ActionPerformed
        int n = findStationNrIndex(375);
        updateInfo(n);
    }//GEN-LAST:event_jStation375ActionPerformed

    private void jStation370ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation370ActionPerformed
        int n = findStationNrIndex(370);
        updateInfo(n);
    }//GEN-LAST:event_jStation370ActionPerformed

    private void jStation356ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation356ActionPerformed
        int n = findStationNrIndex(356);
        updateInfo(n);
    }//GEN-LAST:event_jStation356ActionPerformed

    private void jStation348ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation348ActionPerformed
        int n = findStationNrIndex(348);
        updateInfo(n);
    }//GEN-LAST:event_jStation348ActionPerformed

    private void jStation343ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation343ActionPerformed
        int n = findStationNrIndex(343);
        updateInfo(n);
    }//GEN-LAST:event_jStation343ActionPerformed

    private void jStation330ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation330ActionPerformed
        int n = findStationNrIndex(330);
        updateInfo(n);
    }//GEN-LAST:event_jStation330ActionPerformed

    private void jStation319ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation319ActionPerformed
        int n = findStationNrIndex(319);
        updateInfo(n);
    }//GEN-LAST:event_jStation319ActionPerformed

    private void jStation315ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation315ActionPerformed
        int n = findStationNrIndex(315);
        updateInfo(n);
    }//GEN-LAST:event_jStation315ActionPerformed

    private void jStation310ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation310ActionPerformed
        int n = findStationNrIndex(310);
        updateInfo(n);
    }//GEN-LAST:event_jStation310ActionPerformed

    private void jStation308ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation308ActionPerformed
        int n = findStationNrIndex(308);
        updateInfo(n);
    }//GEN-LAST:event_jStation308ActionPerformed

    private void jStation290ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation290ActionPerformed
        int n = findStationNrIndex(290);
        updateInfo(n);
    }//GEN-LAST:event_jStation290ActionPerformed

    private void jStation286ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation286ActionPerformed
        int n = findStationNrIndex(286);
        updateInfo(n);
    }//GEN-LAST:event_jStation286ActionPerformed

    private void jStation283ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation283ActionPerformed
        int n = findStationNrIndex(283);
        updateInfo(n);
    }//GEN-LAST:event_jStation283ActionPerformed

    private void jStation280ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation280ActionPerformed
        int n = findStationNrIndex(280);
        updateInfo(n);
    }//GEN-LAST:event_jStation280ActionPerformed

    private void jStation274ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation274ActionPerformed
        int n = findStationNrIndex(274);
        updateInfo(n);
    }//GEN-LAST:event_jStation274ActionPerformed

    private void jStation275ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation275ActionPerformed
        int n = findStationNrIndex(275);
        updateInfo(n);
    }//GEN-LAST:event_jStation275ActionPerformed

    private void jStation278ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation278ActionPerformed
        int n = findStationNrIndex(278);
        updateInfo(n);
    }//GEN-LAST:event_jStation278ActionPerformed

    private void jStation270ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation270ActionPerformed
        int n = findStationNrIndex(270);
        updateInfo(n);
    }//GEN-LAST:event_jStation270ActionPerformed

    private void jStation259ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation259ActionPerformed
        int n = findStationNrIndex(259);
        updateInfo(n);
    }//GEN-LAST:event_jStation259ActionPerformed

    private void jStation248ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation248ActionPerformed
        int n = findStationNrIndex(248);
        updateInfo(n);
    }//GEN-LAST:event_jStation248ActionPerformed

    private void jStation240ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation240ActionPerformed
        int n = findStationNrIndex(240);
        updateInfo(n);
    }//GEN-LAST:event_jStation240ActionPerformed

    private void jStation235ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation235ActionPerformed
        int n = findStationNrIndex(235);
        updateInfo(n);
    }//GEN-LAST:event_jStation235ActionPerformed

    private void jStation251ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation251ActionPerformed
        int n = findStationNrIndex(251);
        updateInfo(n);
    }//GEN-LAST:event_jStation251ActionPerformed

    private void jStation215ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation215ActionPerformed
        int n = findStationNrIndex(215);
        updateInfo(n);
    }//GEN-LAST:event_jStation215ActionPerformed

    private void jStation209ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation209ActionPerformed
        int n = findStationNrIndex(209);
        updateInfo(n);
    }//GEN-LAST:event_jStation209ActionPerformed

    private void jSlider2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider2StateChanged
        // TODO add your handling code here:
        JSlider source = (JSlider)evt.getSource();
        if (!source.getValueIsAdjusting()) {
            selectedMonth = (int)source.getValue();
            genHeatmap();

        }
    }//GEN-LAST:event_jSlider2StateChanged

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        switch(jComboBox1.getSelectedIndex()){
            case 0:
                heatMapSelected = "tempavg";
                break;
            case 1:
                heatMapSelected= "windsp";
               break;
        }
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MapViewTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MapViewTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MapViewTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MapViewTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MapViewTest().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.ButtonGroup LocationChoice;
    private javax.swing.ButtonGroup TimeScaleChoice;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JSlider jSlider2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JSplitPane jSplitPane4;
    private javax.swing.JRadioButton jStation209;
    private javax.swing.JRadioButton jStation215;
    private javax.swing.JRadioButton jStation235;
    private javax.swing.JRadioButton jStation240;
    private javax.swing.JRadioButton jStation248;
    private javax.swing.JRadioButton jStation251;
    private javax.swing.JRadioButton jStation259;
    private javax.swing.JRadioButton jStation270;
    private javax.swing.JRadioButton jStation274;
    private javax.swing.JRadioButton jStation275;
    private javax.swing.JRadioButton jStation278;
    private javax.swing.JRadioButton jStation280;
    private javax.swing.JRadioButton jStation283;
    private javax.swing.JRadioButton jStation286;
    private javax.swing.JRadioButton jStation290;
    private javax.swing.JRadioButton jStation308;
    private javax.swing.JRadioButton jStation310;
    private javax.swing.JRadioButton jStation315;
    private javax.swing.JRadioButton jStation319;
    private javax.swing.JRadioButton jStation330;
    private javax.swing.JRadioButton jStation343;
    private javax.swing.JRadioButton jStation348;
    private javax.swing.JRadioButton jStation356;
    private javax.swing.JRadioButton jStation370;
    private javax.swing.JRadioButton jStation375;
    private javax.swing.JRadioButton jStation377;
    private javax.swing.JRadioButton jStation380;
    private javax.swing.JRadioButton jStation391;
    private javax.swing.JTextField jTextHeight;
    private javax.swing.JTextField jTextLat;
    private javax.swing.JTextField jTextLong;
    private javax.swing.JTextField jTextName;
    private javax.swing.JTextField jTextNum;
    private javax.swing.JTextField jTextOpening;
    private javax.swing.JRadioButton jTimeWhole;
    private javax.swing.JRadioButton jTimeYear;
    private javax.swing.JComboBox<String> jYearBox;
    // End of variables declaration//GEN-END:variables


}


