/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estaciogas.data;

/**
 *
 * @author eloi
 */
public class Refuel {
    int member_id;
    float liters, price, price_liters;
    
    public Refuel() {
        member_id = -1;
    }
    
    public Refuel(int id) {
         member_id = id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public float getLiters() {
        return liters;
    }

    public void setLiters(float liters) {
        this.liters = liters;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice_liters() {
        return price_liters;
    }

    public void setPrice_liters(float price_liters) {
        this.price_liters = price_liters;
    }
}
