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
public class WindObj {
    
    Double direction;
    Double speed;
    
    public WindObj(double direction, double speed){
        this.direction = direction;
        this.speed = speed;
    }
    
    public Double getSpeed(){
        return speed;
    }
    
    public Double getDirection(){
        return direction; 
    }
}
