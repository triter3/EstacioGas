/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estaciogas.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eloi
 */
public class dbController {
    private static final String url = "jdbc:postgresql://localhost/ASDTgas";
    private static final String user = "eloi";
    private static final String password = "hola";
    
    Connection c = null;
    Statement stmt = null;
    ResultSet rs = null;
  
    public void connect() {
        try {
            c = DriverManager.getConnection(url, user, password);
            //c.setAutoCommit(false);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    
    public void disconnect() {
        try {
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(dbController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //return user with card_id or null if card_id not exists
    public User getUser(String card_id) throws SQLException {
        User auxUser = null;
        stmt = c.createStatement();
        String query = "SELECT name, id, card_id, monthly_payament, hangar_number,"+
        " hangar_position FROM plane p, member m WHERE p.registration =" + 
        " m.plane_registration AND m.card_id = '" + card_id + "';";

        rs = stmt.executeQuery( query );
        if (rs.next())  {
           auxUser = new User();
           auxUser.setName(rs.getString("name"));
           auxUser.setId(rs.getInt("id"));
           auxUser.setCard_id(rs.getString("card_id"));
           //auxUser.setPayment_method(rs.getString("telephone"));
           auxUser.setHangar_name(rs.getString("hangar_number"));
           auxUser.setHangar_place(rs.getString("hangar_position"));
           //mirar si se li  permet treure o no            
        }
        rs.close();
        stmt.close();
        return auxUser;
    }
    
    public void saveRefuel(Refuel refuel) throws SQLException {
       stmt = c.createStatement();
       String query = "INSERT INTO refuel (member_id, liters, price, price_liters) VALUES (" 
               + Integer.toString(refuel.getMember_id()) + ", " + Float.toString(refuel.getLiters())
               + ", " + Float.toString(refuel.getPrice()) + ", "
               + Float.toString(refuel.getPrice_liters()) + ");";
        stmt.executeUpdate(query);
        stmt.close();
    }
    
    public float getFuelPrice(boolean partner) throws SQLException {
        stmt = c.createStatement();
        String query;
        
        if (partner) {
            query = "SELECT * FROM gasoline_member_price;";
        }
        else {
            query =  "SELECT * FROM gasoline_price;";        
        }
        
        rs = stmt.executeQuery( query );
        if(!rs.next()){
            System.out.println("no hi ha cap preu");
            return -1;
        }
        else {
            float aux = rs.getFloat("price");
            rs.close();
            stmt.close();
            return aux;
        }
    }
            
}
