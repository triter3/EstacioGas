package estaciogas.data;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author eloi
 */
public class User {
    
    int id; //Si es -1 no es soci
    String name, card_id, payment_method, hangar_name, hangar_place;
    boolean disabled;
    
    public User() {
        id = -1;
        card_id = payment_method = hangar_name = hangar_place = null;
        disabled = false;
    }

    public User(int id, String name, String surname, String card_id, String email, 
                String telephone, String payment_method, String plane_registration, 
                String hangar_name, String hangar_place, boolean disabled) {
        this.id = id;
        this.card_id = card_id;
        this.payment_method = payment_method;
        this.hangar_name = hangar_name;
        this.hangar_place = hangar_place;
        this.disabled = disabled;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getHangar_name() {
        return hangar_name;
    }

    public void setHangar_name(String hangar_name) {
        this.hangar_name = hangar_name;
    }

    public String getHangar_place() {
        return hangar_place;
    }

    public void setHangar_place(String hangar_place) {
        this.hangar_place = hangar_place;
    }
  
}
