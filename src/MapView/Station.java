/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapView;

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
    
    public Station(int stationNum, String stationName, float longitude, float latitude, float height, String opening){
        this.stationNum = stationNum;
        this.stationName = stationName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.height = height;
        this.opening = opening;
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
}
