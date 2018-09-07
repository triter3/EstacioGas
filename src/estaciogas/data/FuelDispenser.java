/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estaciogas.data;
import com.pi4j.io.gpio.*;


/**
 *
 * @author eloi
 */
public class FuelDispenser extends Thread {
    public interface FuelListener { 
        public void newData(float liters); 
        public void finalData(float liters);
    }
    FuelListener fl;
    
    long antClicks, clicks;
    float antLiters, liters;
    final GpioController gpio;
    GpioPinDigitalInput dispenser;
    long time;
    private boolean running = true;

    
    @Override
    public void run() {
        final GpioController gpio = GpioFactory.getInstance();
        GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00);
        long clics = 0;
        boolean antState = false;
        boolean state = false;
        time = System.nanoTime();
        while(running) {
            state = myButton.isHigh();
            if (myButton.isHigh() && antState == false) {
                antState = true;
                clics++;
                if(clics%50 == 0) System.out.println(clics);
            }
            else if (!myButton.isHigh() && antState == true){
                antState = false;
            }
            
            if (clicks > (antClicks+3)) {
                antClicks = clicks;
                liters = clicksToLiters();
                time = System.nanoTime();
            }
            
            if (liters > (antLiters+0.05)) {
                antLiters = liters;
                fl.newData(liters);
            }
            
            if (((System.nanoTime()-time)/1000000000 )> 1 ) {
                fl.finalData(liters);
            }
        }   
    }
    
    public void close() {
        running = false;
    }
    
    public FuelDispenser(FuelListener listener) {
        fl = listener;
        gpio = GpioFactory.getInstance();
        dispenser = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00);
        this.liters = 0;
        this.antClicks = 0;
        this.clicks = 0;       
    }
    
    private float clicksToLiters() {
        liters = clicks/78;
        return liters;
    }

    
    public float getLiters() {
        
        return liters;
    }
     
}
