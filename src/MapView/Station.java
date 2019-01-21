/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapView;
import java.util.*;

/**
 *
 * @author s141452
 */
public class Station{
    private int stationNum;
    private String stationName; 
    private float longitude; 
    private float latitude; 
    private float height; 
    private String opening;
    
    private String[] attributts;
    
    private ArrayList<Integer> time;
    private ArrayList<Double> tempavg, tempmin, 
            tempmax, windsp, winddir, percipation, pressure;
    
    private HashMap A;
    
    public Station(int stationNum, String stationName, float longitude, float latitude, float height, String opening){
        this.stationNum = stationNum;
        this.stationName = stationName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.height = height;
        this.opening = opening;
        
        attributts = new String[8];
        attributts[0] = "time";
        attributts[1] = "winddir";
        attributts[2] = "windsp";
        attributts[3] = "tempavg";
        attributts[4] = "tempmin";
        attributts[5] = "tempmax";
        attributts[6] = "percipation";
        attributts[7] = "pressure";
        
        A = new HashMap();
        for (String i: attributts){
            A.put(i, new ArrayList<Double>());
        }
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
    public String getOpening(){
        return opening;
    }
    public HashMap getMap(){
        return A;
    }
}
