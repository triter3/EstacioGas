/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estaciogas.data;
import com.pi4j.io.gpio.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eloi
 */
public class FuelDispenser extends Thread{
    float liters;
    long clicks;
    final GpioController gpio;
    GpioPinDigitalInput dispenser;
    
    @Override
    public void run() {
        
    }
    
    public FuelDispenser() {
        gpio = GpioFactory.getInstance();
        dispenser = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00);
    }
    
    private float clicksToLiters() {
        liters = clicks/77;
        return liters;
    }
    
    public void actualizeLiters() {
        
    }
    
    public float getLiters() {
        
        return liters;
    }
    
    public void startRepostage() {
        liters = 0;
        clicks = 0;
    }
    
    public void endRespostage() {
        liters = 0;
        clicks = 0;
    }
}
