/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estaciogas.view;

import estaciogas.data.User;
import estaciogas.data.dbController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import java.util.Timer;
import java.util.TimerTask;


/**
 *
 * @author eduard
 */
public class StartPanel extends javax.swing.JPanel implements java.awt.event.KeyListener {
    
    public interface PanelListener {
        void startSession(User user, float gasolinePrice);
    }
    
    private enum ScreenState {
       START_SCREEN,
       SELECT_SCREEN,
       CARD_SCREEN,
       ERROR_CARD_SCREEN
    }
    
    public void startScreen() {
        changeScreen(ScreenState.START_SCREEN);
    }
    
    private PanelListener listener;
    private ScreenState state;
    private Timer timer; 
    private TimerTask task;
    
    private void changeScreen(ScreenState state) {
        switch(state) {
            case START_SCREEN:
                buttonsPanel.setVisible(false);
                textPanel.setVisible(true);
                titleText.setText("Clica a la pantalla per començar");
                if(task != null) {
                    task.cancel();
                }
                task = null;
                break;
            case SELECT_SCREEN:
                textPanel.setVisible(false);
                buttonsPanel.setVisible(true);
                if(task != null) task.cancel();
                timer.schedule(task = new StateTimer(), 15000);
                break;
            case CARD_SCREEN:
                buttonsPanel.setVisible(false);
                textPanel.setVisible(true);
                titleText.setText("<html><div style='text-align: center;'>Passa la tarjecta per el lector<br><br>"
                        + "<div><font size=-1>Clica la pantalla per tornar al menú principal </div> </div></html>");
                if(task != null) task.cancel();
                timer.schedule(task = new StateTimer(), 15000);
                break;
            case ERROR_CARD_SCREEN: 
                buttonsPanel.setVisible(false);
                textPanel.setVisible(true);
                titleText.setText("<html><font color='red'> Targeta  incorrecte </html>");
                if(task != null) task.cancel();
                timer.schedule(task = new StateTimer(), 3000);
                break;
        }
        this.state = state;
    }
    
    /**
     * Creates new form StartPanel
     */
    public StartPanel(PanelListener listener) {
        buffer = new char[10];
        this.listener = listener;
        timer = new Timer();
        initComponents();
        changeScreen(ScreenState.START_SCREEN);
        titleText.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //canvi a els botons
                if(state == ScreenState.START_SCREEN) {
                    changeScreen(ScreenState.SELECT_SCREEN);
                } else {
                    changeScreen(ScreenState.START_SCREEN);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
   
        });
        
        rightBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeScreen(ScreenState.CARD_SCREEN);
            }
        });
        
        leftBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                * Agafar gasolina no soci
                */
                dbController db = new dbController();
                db.connect();
                float fuelPrice = 0;             
                try {
                    fuelPrice = db.getFuelPrice(false);
                } catch (SQLException ex) {
                    Logger.getLogger(StartPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                db.disconnect();        
                if(task != null) task.cancel();
                listener.startSession(null, fuelPrice);
            }
        });
        
    }
    
    private void cardDetected(String code) {
         /*
        * Agafar Usuari i gasolina 
        * si l'usuari no es correcte cridar 
        */
        float fuelPrice = 0;
        dbController db = new dbController();
        db.connect();
        User user = new User();
        try {
            user = db.getUser(code);
        } catch (SQLException ex) {
            Logger.getLogger(StartPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (user == null) {
            changeScreen(ScreenState.ERROR_CARD_SCREEN);
        } else {
            try {
                fuelPrice = db.getFuelPrice(true);
                if(task != null) task.cancel();
                listener.startSession(user, fuelPrice); 
            } catch (SQLException ex) {
                Logger.getLogger(StartPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        db.disconnect();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        buttonsPanel = new javax.swing.JPanel();
        leftBtn = new javax.swing.JButton();
        rightBtn = new javax.swing.JButton();
        textPanel = new javax.swing.JPanel();
        titleText = new javax.swing.JLabel();

        setBackground(new java.awt.Color(133, 187, 251));

        buttonsPanel.setBackground(new java.awt.Color(133, 187, 251));
        buttonsPanel.setLayout(new java.awt.GridLayout(1, 0, 20, 0));

        leftBtn.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        leftBtn.setText("No Soci");
        buttonsPanel.add(leftBtn);

        rightBtn.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        rightBtn.setText("Soci");
        buttonsPanel.add(rightBtn);

        textPanel.setBackground(new java.awt.Color(133, 187, 251));

        titleText.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        titleText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleText.setText("Clica a la pantalla per començar");
        titleText.setToolTipText("");

        javax.swing.GroupLayout textPanelLayout = new javax.swing.GroupLayout(textPanel);
        textPanel.setLayout(textPanelLayout);
        textPanelLayout.setHorizontalGroup(
            textPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textPanelLayout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addComponent(titleText, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
                .addGap(96, 96, 96))
        );
        textPanelLayout.setVerticalGroup(
            textPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titleText, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
        );

        jLayeredPane1.setLayer(buttonsPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(textPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(buttonsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 775, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(buttonsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JButton leftBtn;
    private javax.swing.JButton rightBtn;
    private javax.swing.JPanel textPanel;
    private javax.swing.JLabel titleText;
    // End of variables declaration//GEN-END:variables
    
        //Card detection
    private char[] buffer;
    private int pointer = 0;
    
    @Override
    public void keyTyped(KeyEvent e) {
       if(pointer <= 9 && e.getKeyChar() >= '0' && e.getKeyChar() <= '9') {
           buffer[pointer] = e.getKeyChar();
           pointer++;
       } else {
           if(pointer == 10 && e.getKeyChar() == '\n') {
               cardDetected(String.valueOf(buffer));
           } 
           pointer = 0;
       }
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
       
    }
    
    private class StateTimer extends TimerTask {
        
        @Override
        public void run() {
            if(state != ScreenState.START_SCREEN) {
                changeScreen(ScreenState.START_SCREEN);
            }
        }
    
    }
}

