/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estaciogas.data;

import java.time.LocalDateTime;    
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author eloi
 */
public class Ticket implements Printable{
   User user;
   Refuel refuel;
    
   public Ticket() {
      user = null;
      refuel = null;
   }
    
  private static final int OFFSET_WIDTH = 14;
  private static final int WIDTH = 132;
  private static final int CENTER = 132/2;
  private static final int RIGHT_OFFSET = 5;
  int y; //variable que representa a l'alçada on es troba el cursor

 
  protected static double convert_CM_To_PPI(double cm) {            
      return toPPI(cm * 0.393700787);            
  }
 
  protected static double toPPI(double inch) {            
      return inch * 72d;            
  }
  
  public PageFormat getPageFormat(PrinterJob pj) {
    PageFormat pf = pj.defaultPage();
    Paper paper = pf.getPaper();    
               
    double width = convert_CM_To_PPI(8);      //printer know only point per inch.default value is 72ppi
    double height = convert_CM_To_PPI(10); 
    paper.setSize(width, height);
    paper.setImageableArea(                    
        10,
        10,
        180,            
        height - convert_CM_To_PPI(1)
    );   //define boarder size    after that print area width is about 180 points
            
    pf.setOrientation(PageFormat.PORTRAIT);           //select orientation portrait or landscape but for this time portrait
    pf.setPaper(paper);    

    return pf;
}
    
  public void printTicket(User user, Refuel refuel) 
  {
      this.user = user;
      this.refuel = refuel;
      PrinterJob pj = PrinterJob.getPrinterJob();        
      pj.setPrintable(this,getPageFormat(pj));
      
      /*try {     
           pj.print();  
      }
       catch (PrinterException ex) {
      }*/
    
  }
  
  private void drawCenteredString(String s, int w, int h, Graphics g) {
    FontMetrics fm = g.getFontMetrics();
    int x = w - (fm.stringWidth(s)/2);
    g.drawString(s, x, h);
  }
  
  private void drawEndString(String s, int w, int h, Graphics g) {
    FontMetrics fm = g.getFontMetrics();
    int x = w - (fm.stringWidth(s));
    g.drawString(s, x, h);
  }
  
  private String printPartnerNumber(int number) {
      String retornar = null;
      String aux = Integer.toString(number);
      int mida = aux.length();
      if (mida == 1) retornar = "000"+aux;
      else if (mida == 2) retornar = "00"+aux;
      else if (mida == 2) retornar = "0"+aux;
      else retornar = aux;
      return retornar;
  }
  
  private void drawPartnerInfo(Graphics2D g2d) {
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
      LocalDateTime now = LocalDateTime.now();
      String aux = now.format(dtf);
      drawEndString(aux, WIDTH, y, g2d); y+=15;
      g2d.setFont(new Font("Arial",Font.PLAIN,8));
      String aux1, aux2, auxFinal;
      aux1 = "Núm. Soci: ";
      aux2 = printPartnerNumber(user.getId());
      auxFinal = aux1+aux2;
      g2d.drawString(auxFinal,RIGHT_OFFSET, y); y+=10;
      aux1 = "Hangar: ";
      if (user.getHangar_name() != null) 
          aux2 = "H" + user.getHangar_name() + "  " + user.getHangar_place(); 
      else aux2 = "No registrat";
      auxFinal = aux1+aux2;
      g2d.drawString(auxFinal,RIGHT_OFFSET, y); y+=10;
      aux1 = "Pagament: ";
      aux2 = user.getPayment_method();
      auxFinal = aux1+aux2;
      g2d.drawString(auxFinal,RIGHT_OFFSET, y); y+=15;
      g2d.setFont(new Font("Arial",Font.PLAIN,7));
      drawCenteredString("- - - - - - - - - - - - - - - - - - - -", 66, y, g2d); y+= 15;      
  }
  private void drawNoPartnerInfo(Graphics2D g2d) {
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
      LocalDateTime now = LocalDateTime.now();
      String aux = now.format(dtf);
      drawEndString(aux, WIDTH, y, g2d); y+=20;
      g2d.setFont(new Font("Arial",Font.PLAIN,8));
      g2d.drawString("L'usuari no és soci.", 0, y); y+=15;
      g2d.setFont(new Font("Arial",Font.PLAIN,7));
      drawCenteredString("- - - - - - - - - - - - - - - - - - - -", 66, y, g2d); y+= 15;
      
  }
 
  private void drawHeader(Graphics2D g2d) {
    BufferedImage img = null;
    try {
        img = ImageIO.read(getClass().getResourceAsStream("/res/lpbt130.png"));
    } catch (IOException ex) {
        Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
    }
    g2d.drawImage(img, null, 0,0);
    y = img.getHeight()+10;
    g2d.setFont(new Font("Arial",Font.PLAIN,7));
    drawCenteredString("Carretera C16 Km 59", CENTER, y, g2d); y+= 10;
    drawCenteredString("Aeròdrom", CENTER, y, g2d); y+= 10;
    drawCenteredString("CP 08650 Sallent", CENTER, y, g2d); y+= 10;
    drawCenteredString("www.pladebages.cat", CENTER, y, g2d); y+= 15;
    drawCenteredString("- - - - - - - - - - - - - - - - - - - -", 66, y, g2d); y+= 15;
  }
  
  private void drawRefuelInfo(Graphics2D g2d) {
      
      BufferedImage img = null;
    try {
        img = ImageIO.read(getClass().getResourceAsStream("/res/HOLA2.png"));
    } catch (IOException ex) {
        Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
    }
    g2d.setFont(new Font("Arial",Font.PLAIN,8));
    drawCenteredString("Sense Plom 95",CENTER, y, g2d); y+=5;
    g2d.drawImage(img, null, 56,y);y+=30;
    String auxFinal = "Litres: " + Float.toString(refuel.getLiters());
    g2d.drawString(auxFinal,RIGHT_OFFSET, y); y+=10;
    auxFinal = "Preu: " + Float.toString(refuel.getPrice());
    g2d.drawString(auxFinal,RIGHT_OFFSET, y); y+=15;  
    g2d.setFont(new Font("Arial",Font.PLAIN,6));
    drawCenteredString("Gràcies per la seva visita, bon vol!", CENTER, y, g2d);    
  }
  
  @Override
  public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
     int result = NO_SUCH_PAGE;    
        if (pageIndex == 0) {   
            System.out.println("Pinta be1");
            Graphics2D g2d = (Graphics2D) graphics;
            g2d.translate((int) pageFormat.getImageableX()+OFFSET_WIDTH,(int) pageFormat.getImageableY());
            result = PAGE_EXISTS;
            drawHeader(g2d);
            if (user == null) drawNoPartnerInfo(g2d);
            else drawPartnerInfo(g2d);
            drawRefuelInfo(g2d);
            System.out.println("Pinta be2");
        }    
        return result;   
  }
  
}