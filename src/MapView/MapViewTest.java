/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapView;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
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
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author tosku
 */
public class MapViewTest extends javax.swing.JFrame {

    ArrayList<Station> stations = new ArrayList<Station>();
    
    int stationNum;
    String stationName, opening;
    float longitude, latitude, height;
    
    public MapViewTest() {
        initComponents();
        loadStations();
        
        setChart1();
    }
    
    public void loadStations(){
        try{
            //File f = new File("MapView/Stations.csv");
            BufferedReader br = new BufferedReader(new FileReader("src/MapView/data/Stations.csv"));
            String line = br.readLine();
            
            while (line != null){
                //System.out.println("HOI");
                String[] attributes = line.split(",");
                //System.out.println("HOI2");
                Station station = createStation(attributes);
                //System.out.println("HO3");
                stations.add(station);
                //System.out.println("HO4");
                line = br.readLine();
                //System.out.println("HOI5");
                //System.out.println(line);
            }
        } catch (IOException ioe){
            ioe.printStackTrace();
        } 
    }
  
    
    
    public void setChart1(){
        VisLineChart b = new VisLineChart("", "");
        PieChartA a = new PieChartA("", "");
        VisLineChartEx c = new VisLineChartEx();
        //setLayout(new BorderLayout());
        //this.jSplitPane1.setBottomComponent(a);
        Icon icon = jLabel1.getIcon();
        BufferedImage bi = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(),BufferedImage.TYPE_INT_RGB);
        
        BufferedImage bi2 = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(),BufferedImage.TYPE_INT_RGB);
                
        Graphics2D g2d = bi.createGraphics();
        g2d.setColor(Color.RED);
        g2d.fillRect(0, 0, bi.getWidth(), bi.getHeight());
        g2d.dispose();
        
        
        Graphics2D g = bi2.createGraphics();
        icon.paintIcon(null, g, 0,0);
      
        float alpha = 1.0f;
        int compositeRule = AlphaComposite.SRC_OVER;
        AlphaComposite ac;
        ac = AlphaComposite.getInstance(compositeRule, alpha);
        g.setComposite(ac);
        g.drawImage(bi2,0,0,null);
        ac = AlphaComposite.getInstance(compositeRule, 0.4f);
        g.setComposite(ac);
        g.drawImage(bi,0,0,null);
        g.dispose();
        
        
        
        jLabel1.setIcon(new ImageIcon(bi2));
        
        
        this.jSplitPane3.setTopComponent(b);
        this.jSplitPane4.setTopComponent(a);
        this.jSplitPane4.setBottomComponent(c);
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
        //System.out.println(meta[4]);
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
    }
    
    public int findStationNrIndex(int nr){
        for(int i = 0; i < stations.size(); i++){
            //System.out.println("BEEP: "+ stations.get(i).getNum());
            if (nr == stations.get(i).getNum()){
                //System.out.println(i + " was found");
                return i;
            }
        }
        //System.out.println("NO");
        return 0;
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
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jStation209 = new javax.swing.JRadioButton();
        jNoStation = new javax.swing.JRadioButton();
        jStation215 = new javax.swing.JRadioButton();
        jStation251 = new javax.swing.JRadioButton();
        jStation235 = new javax.swing.JRadioButton();
        jStation240 = new javax.swing.JRadioButton();
        jStation248 = new javax.swing.JRadioButton();
        jStation259 = new javax.swing.JRadioButton();
        jStation270 = new javax.swing.JRadioButton();
        jStation278 = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
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
        jStation381 = new javax.swing.JRadioButton();
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
        jSplitPane2 = new javax.swing.JSplitPane();
        jSplitPane3 = new javax.swing.JSplitPane();
        jSplitPane4 = new javax.swing.JSplitPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPane1.setDividerSize(2);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jStation209.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation209);
        jStation209.setToolTipText("IJmond");
        jStation209.setOpaque(false);
        jStation209.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation209ActionPerformed(evt);
            }
        });

        jNoStation.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jNoStation);
        jNoStation.setText("None");
        jNoStation.setToolTipText("None");
        jNoStation.setOpaque(false);
        jNoStation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNoStationActionPerformed(evt);
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

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MapView/Images/Map Netherlands v2.0.png"))); // NOI18N

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
        jStation274.setOpaque(false);
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

        jStation381.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation381);
        jStation381.setToolTipText("Arcen");
        jStation381.setOpaque(false);
        jStation381.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation381ActionPerformed(evt);
            }
        });

        jLayeredPane1.setLayer(jStation209, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jNoStation, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation215, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation251, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation235, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation240, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation248, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation259, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation270, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation278, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
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
        jLayeredPane1.setLayer(jStation381, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(333, 333, 333)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(jStation290)
                        .addGap(79, 79, 79))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(jStation375)
                        .addGap(253, 253, 253)))
                .addGap(0, 0, Short.MAX_VALUE))
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
                        .addComponent(jStation381))
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
                        .addGap(23, 23, 23)
                        .addComponent(jNoStation))
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
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                                .addComponent(jStation381))))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(543, 543, 543)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jStation377)
                            .addComponent(jStation319))))
                .addGap(58, 58, 58)
                .addComponent(jStation380)
                .addGap(27, 27, 27)
                .addComponent(jNoStation)
                .addContainerGap())
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1)
                    .addContainerGap(76, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addGap(238, 238, 238)
                    .addComponent(jStation259)
                    .addContainerGap(482, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addGap(214, 214, 214)
                    .addComponent(jStation274)
                    .addContainerGap(506, Short.MAX_VALUE)))
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLayeredPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextName, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                            .addComponent(jTextNum)))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addGap(61, 61, 61)
                            .addComponent(jTextOpening))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(54, 54, 54)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jTextLat, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                        .addComponent(jTextHeight)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextLong, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(56, 56, 56))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLayeredPane1)
                .addGap(106, 106, 106))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
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
                    .addComponent(jTextLong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
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
                    .addComponent(jTextOpening, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 419, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(162, 162, 162))
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
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 810, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextNameActionPerformed

    private void jStation381ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation381ActionPerformed
        int n = findStationNrIndex(381);
        updateInfo(n);
    }//GEN-LAST:event_jStation381ActionPerformed

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

    private void jNoStationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNoStationActionPerformed
        jTextNum.setText("");
        jTextName.setText("");
        jTextLong.setText("");
        jTextLat.setText("");
        jTextHeight.setText("");
    }//GEN-LAST:event_jNoStationActionPerformed

    private void jStation209ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation209ActionPerformed
        int n = findStationNrIndex(209);
        updateInfo(n);
    }//GEN-LAST:event_jStation209ActionPerformed

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
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JRadioButton jNoStation;
    private javax.swing.JPanel jPanel1;
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
    private javax.swing.JRadioButton jStation381;
    private javax.swing.JTextField jTextHeight;
    private javax.swing.JTextField jTextLat;
    private javax.swing.JTextField jTextLong;
    private javax.swing.JTextField jTextName;
    private javax.swing.JTextField jTextNum;
    private javax.swing.JTextField jTextOpening;
    // End of variables declaration//GEN-END:variables
}


