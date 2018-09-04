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
        Statement stmtUser = c.createStatement();
        Statement stmtPlane = c.createStatement();
        String queryUser = "SELECT name, id, card_id, monthly_payament, plane_registration " + 
        "FROM member WHERE card_id = '" + card_id + "';";
        ResultSet rsUser = stmtUser.executeQuery( queryUser );
        
        if (rsUser.next())  {
           auxUser = new User();
           auxUser.setName(rsUser.getString("name"));
           auxUser.setId(rsUser.getInt("id"));
           auxUser.setCard_id(rsUser.getString("card_id"));
           //auxUser.setPayment_method(rs.getString("monthly_payment"));
           //mirar si se li  permet treure o no  
           String matricula = rsUser.getString("plane_registration");
           if (matricula != null) {
               String queryPlane = "SELECT *  FROM plane WHERE registration = '" + matricula +"';";
               ResultSet rsPlane = stmtPlane.executeQuery( queryPlane );
               if (rsPlane.next()) {
                   System.out.println("entra");
                auxUser.setHangar_name(rsPlane.getString("hangar_number"));
                auxUser.setHangar_place(rsPlane.getString("hangar_position"));
               }
               rsPlane.close();
               stmtPlane.close();
           }
        }
        rsUser.close();
        stmtUser.close();
        return auxUser;
    }
    
    public void saveRefuel(Refuel refuel) throws SQLException {
       Statement stmt = c.createStatement();
       String auxMemberId; 
       if (refuel.getMember_id()< 0)auxMemberId="null";
       else auxMemberId = Integer.toString(refuel.getMember_id());
       String query = "INSERT INTO refuel (member_id, liters, price, price_liters) VALUES (" 
               + auxMemberId + ", " + Float.toString(refuel.getLiters())
               + ", " + Float.toString(refuel.getPrice()) + ", "
               + Float.toString(refuel.getPrice_liters()) + ");";
        stmt.executeUpdate(query);
        stmt.close();
    }
    
    public float getFuelPrice(boolean partner) throws SQLException {
        Statement stmt = c.createStatement();
        String query;
        
        if (partner) {
            query = "SELECT * FROM gasoline_member_price;";
        }
        else {
            query =  "SELECT * FROM gasoline_price;";        
        }
        
        ResultSet rs = stmt.executeQuery( query );
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
