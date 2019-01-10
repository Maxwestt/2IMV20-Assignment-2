/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapView;
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

/**
 *
 * @author tosku
 */
public class MapViewTest extends javax.swing.JFrame {

    //String[][] stations = new String[5][4];
    ArrayList<Station> stations = new ArrayList<Station>();
//    String[][] stations = {
//                    {"NO","","","",""},
//                    {"209","4.518","52.465","0.00","IJMOND"},
//                    {"210","4.430","52.171","-0.20","VALKENBURG"},
//                    {"215","4.437","52.141","-1.10","VOORSCHOTEN"},
//                    {"225","4.555","52.463","4.40","IJMUIDEN"},};
    
    //String stationNum, stationName, longitude, latitude, height;
    int stationNum;
    String stationName;
    float longitude, latitude, height;
    
    public MapViewTest() {
        initComponents();
        loadStations();
    }
    
    public void loadStations(){
        try{
            //File f = new File("MapView/Stations.csv");
            BufferedReader br = new BufferedReader(new FileReader("src/MapView/Stations.csv"));
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
    
    public static String removeLastChar(String s) {
    return (s == null || s.length() == 0)
      ? null
      : (s.substring(0, s.length() - 1));
}
    
    private static Station createStation(String[] meta){
        //System.out.println(meta[4]);
        meta[0] = meta[0].substring(1);
        int num = Integer.parseInt(meta[0]);
        String name = removeLastChar(meta[4].trim());
        float longi = Float.parseFloat(meta[1]);
        float lat = Float.parseFloat(meta[2]);
        float height = Float.parseFloat(meta[3]);
        
        return new Station(num, name, longi, lat, height);
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
    }
    
    public int findStationNr(int nr){
        for(int i = 0; i < stations.size(); i++){
            System.out.println("BEEP: "+ stations.get(i).getNum());
            if (nr == stations.get(i).getNum()){
                System.out.println(i + " was found");
                return i;
            }
        }
        System.out.println("NO");
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
        jPanel1 = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jStation209 = new javax.swing.JRadioButton();
        jStation210 = new javax.swing.JRadioButton();
        jNoStation = new javax.swing.JRadioButton();
        jStation215 = new javax.swing.JRadioButton();
        jStation251 = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jStation252 = new javax.swing.JRadioButton();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        jStation210.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation210);
        jStation210.setToolTipText("Valkenburg");
        jStation210.setOpaque(false);
        jStation210.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation210ActionPerformed(evt);
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

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MapView/Images/Map Netherlands v2.0.png"))); // NOI18N

        jStation252.setBackground(new java.awt.Color(0, 0, 0));
        LocationChoice.add(jStation252);
        jStation252.setToolTipText("Hoorn (Terschelling)");
        jStation252.setOpaque(false);
        jStation252.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStation252ActionPerformed(evt);
            }
        });

        jLayeredPane1.setLayer(jStation209, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation210, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jNoStation, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation215, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation251, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jStation252, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jNoStation))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(188, 188, 188)
                        .addComponent(jStation209))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(282, 282, 282)
                        .addComponent(jStation251))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(211, 211, 211)
                        .addComponent(jStation252))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(147, 147, 147)
                        .addComponent(jStation210)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jStation215)))
                .addGap(221, 304, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addContainerGap(666, Short.MAX_VALUE)
                        .addComponent(jNoStation))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jLayeredPane1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jStation209)
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addComponent(jStation251)
                                .addGap(91, 91, 91)
                                .addComponent(jStation252)
                                .addGap(87, 87, 87)))
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addComponent(jStation210))
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(jStation215)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(32, 32, 32))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(54, 54, 54)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextLat, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                                    .addComponent(jTextLong)
                                    .addComponent(jTextHeight)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(31, 31, 31)
                                        .addComponent(jTextNum, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextName, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 13, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jTextName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jTextLong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(jTextLat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(jTextHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jStation209ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation209ActionPerformed
        int n = findStationNr(209);
        updateInfo(n);
    }//GEN-LAST:event_jStation209ActionPerformed

    private void jStation210ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation210ActionPerformed
        int n = findStationNr(210);
        updateInfo(n);
    }//GEN-LAST:event_jStation210ActionPerformed

    private void jNoStationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNoStationActionPerformed
        jTextNum.setText("");
        jTextName.setText("");
        jTextLong.setText("");
        jTextLat.setText("");
        jTextHeight.setText("");
    }//GEN-LAST:event_jNoStationActionPerformed

    private void jTextNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextNameActionPerformed

    private void jStation215ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation215ActionPerformed
        int n = findStationNr(215);
        updateInfo(n);
    }//GEN-LAST:event_jStation215ActionPerformed

    private void jStation251ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation251ActionPerformed
        int n = findStationNr(251);
        updateInfo(n);
    }//GEN-LAST:event_jStation251ActionPerformed

    private void jStation252ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStation252ActionPerformed
        int n = findStationNr(252);
        updateInfo(n);
    }//GEN-LAST:event_jStation252ActionPerformed

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
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JRadioButton jNoStation;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jStation209;
    private javax.swing.JRadioButton jStation210;
    private javax.swing.JRadioButton jStation215;
    private javax.swing.JRadioButton jStation251;
    private javax.swing.JRadioButton jStation252;
    private javax.swing.JTextField jTextHeight;
    private javax.swing.JTextField jTextLat;
    private javax.swing.JTextField jTextLong;
    private javax.swing.JTextField jTextName;
    private javax.swing.JTextField jTextNum;
    // End of variables declaration//GEN-END:variables
}



class Station{
    private int stationNum;
    private String stationName; 
    private float longitude; 
    private float latitude; 
    private float height; 
    
    public Station(int stationNum, String stationName, float longitude, float latitude, float height){
        this.stationNum = stationNum;
        this.stationName = stationName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.height = height;
    }
    
    public int getNum(){
        return stationNum;
    }
    public String getName(){
        return stationName;
    }
    public float getLong(){
        return longitude;
    } 
    public float getLat(){
        return latitude;
    }
    public float getHeight(){
        return height;
    }
}
