/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estaciogas.view;

import estaciogas.data.FuelDispenser;
import estaciogas.data.Refuel;
import estaciogas.data.Ticket;
import estaciogas.data.User;
import estaciogas.data.dbController;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author eduard
 */
public class InfoPanel extends javax.swing.JPanel implements FuelDispenser.FuelListener {
    User user;
    Refuel refuel;
    FuelDispenser fuelDispenser;
    float fuelPrice, liters, finalPrice;
    private Timer timer;
    private TimerTask task;
    
    @Override
    public void newData(float liters) {
        jLabel5.setText(Float.toString(liters));
        finalPrice = liters*fuelPrice;
        jLabel3.setText(Float.toString(finalPrice));
    }
    
    @Override
    public void finalData(float liters) {
        jLabel5.setText(Float.toString(liters));
        finalPrice = liters*fuelPrice;
        jLabel3.setText(Float.toString(finalPrice));
        //temporitzador
        finishSession();
    }
    
    
    public void setPanelInfo(User user, float fuelPrice) {
        this.user = user;
        this.liters = this.finalPrice = 0;
        this.fuelPrice = fuelPrice;
        if (user != null) {
           String aux = "Benvingut " + user.getName();
           nameTxt.setText(aux); 
        }
        else {
            nameTxt.setText(""); 
        }
        jLabel2.setText(Float.toString(fuelPrice));
        fuelDispenser = new FuelDispenser(this);
        fuelDispenser.start();
    }
    
    private void saveRefuelInfo() {
        refuel.setLiters(liters);
        if (user != null) refuel.setMember_id(user.getId()); 
        else refuel.setMember_id(-1);
        refuel.setPrice(finalPrice);
        refuel.setPrice_liters(fuelPrice);
    }


    public interface EndListener {
        void endPanel();
    }
    
    private EndListener listener;
    
    /**
     * Creates new form InfoPanel
     */
    public InfoPanel(EndListener listener) {
        this.listener = listener;
        this.refuel = new Refuel();
        this.liters = this.finalPrice = 0;
        this.user = null;
        timer = new Timer();
        
        initComponents();
        endBtn.addActionListener((ActionEvent e) -> {
            finishSession();
        });
    }
    
    private void finishSession() {
        if (liters == 0) {
            task.cancel();
            listener.endPanel();
        }
        else {
            Ticket ticket = new Ticket();
            ticket.printTicket(user, refuel);
            saveRefuelInfo();
            dbController db = new dbController();
            db.connect();
            try {
                db.saveRefuel(refuel);
            } catch (SQLException ex) {
                Logger.getLogger(InfoPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            db.disconnect();
            task.cancel();
            listener.endPanel();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleTxt = new javax.swing.JLabel();
        infoTxt1 = new javax.swing.JLabel();
        nameTxt = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        endBtn = new javax.swing.JButton();

        setBackground(new java.awt.Color(133, 187, 251));

        titleTxt.setFont(new java.awt.Font("Samanata", 1, 36)); // NOI18N
        titleTxt.setForeground(new java.awt.Color(22, 20, 20));
        titleTxt.setText("ASDT GAS STATION");

        infoTxt1.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        infoTxt1.setForeground(new java.awt.Color(27, 27, 27));
        infoTxt1.setText("Gasolina (€/l):");

        nameTxt.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(27, 27, 27));
        jLabel1.setText("Preu ( € ):");

        jLabel2.setBackground(new java.awt.Color(251, 250, 248));
        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("0");
        jLabel2.setOpaque(true);

        jLabel3.setBackground(new java.awt.Color(251, 250, 248));
        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("0");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel3.setOpaque(true);

        jLabel4.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(27, 27, 27));
        jLabel4.setText("Litres:");

        jLabel5.setBackground(new java.awt.Color(251, 250, 248));
        jLabel5.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("0");
        jLabel5.setOpaque(true);

        endBtn.setText("Finalitzar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(nameTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(titleTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(infoTxt1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(endBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(20, 20, 20))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(infoTxt1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(nameTxt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addComponent(endBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton endBtn;
    private javax.swing.JLabel infoTxt1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel nameTxt;
    private javax.swing.JLabel titleTxt;
    // End of variables declaration//GEN-END:variables
}
