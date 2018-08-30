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
    
    private void changeScreen(ScreenState state) {
        switch(state) {
            case START_SCREEN:
                buttonsPanel.setVisible(false);
                textPanel.setVisible(true);
                titleText.setText("Clica a la pantalla per començar");
                break;
            case SELECT_SCREEN:
                textPanel.setVisible(false);
                buttonsPanel.setVisible(true);
                break;
            case CARD_SCREEN:
                buttonsPanel.setVisible(false);
                textPanel.setVisible(true);
                titleText.setText("<html>Passa la tarjecta per el lector<br>Clica la pantalla per tornar al menú principal</html>");
                break;
            case ERROR_CARD_SCREEN:
                buttonsPanel.setVisible(false);
                textPanel.setVisible(true);
                titleText.setText("Targeta incorrecte");
                {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(StartPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                changeScreen(ScreenState.START_SCREEN);
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
        initComponents();
        changeScreen(ScreenState.START_SCREEN);
        JButton rightBtn = (JButton) splitPanel.getRightComponent();
        JButton leftBtn = (JButton) splitPanel.getLeftComponent();
        rightBtn.setText("Socis");
        leftBtn.setText("No Socis");
        rightBtn.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 30));
        leftBtn.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 30));
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
            System.out.println("entra");
            changeScreen(ScreenState.ERROR_CARD_SCREEN);   
        }
        else {
            try {
                fuelPrice = db.getFuelPrice(true);
            } catch (SQLException ex) {
                Logger.getLogger(StartPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        db.disconnect();
        listener.startSession(user, fuelPrice); 
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
        textPanel = new javax.swing.JPanel();
        titleText = new javax.swing.JLabel();
        buttonsPanel = new javax.swing.JPanel();
        splitPanel = new javax.swing.JSplitPane();

        setBackground(new java.awt.Color(133, 187, 251));

        textPanel.setBackground(new java.awt.Color(133, 187, 251));

        titleText.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        titleText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleText.setText("Clica a la pantalla per començar");
        titleText.setToolTipText("");

        javax.swing.GroupLayout textPanelLayout = new javax.swing.GroupLayout(textPanel);
        textPanel.setLayout(textPanelLayout);
        textPanelLayout.setHorizontalGroup(
            textPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titleText, javax.swing.GroupLayout.DEFAULT_SIZE, 799, Short.MAX_VALUE)
        );
        textPanelLayout.setVerticalGroup(
            textPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titleText, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
        );

        buttonsPanel.setBackground(new java.awt.Color(133, 187, 251));

        splitPanel.setResizeWeight(0.5);
        splitPanel.setName("aa"); // NOI18N

        javax.swing.GroupLayout buttonsPanelLayout = new javax.swing.GroupLayout(buttonsPanel);
        buttonsPanel.setLayout(buttonsPanelLayout);
        buttonsPanelLayout.setHorizontalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 799, Short.MAX_VALUE)
            .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(buttonsPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(splitPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 775, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        buttonsPanelLayout.setVerticalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 432, Short.MAX_VALUE)
            .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(buttonsPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(splitPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jLayeredPane1.setLayer(textPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(buttonsPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(buttonsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(buttonsPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
    private javax.swing.JSplitPane splitPanel;
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
}

