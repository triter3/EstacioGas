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
import java.io.File;
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
      try {        
           pj.print();  
      }
       catch (PrinterException ex) {
      }
    
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
  
  private void drawUserInfo(Graphics2D g2d) {
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
      LocalDateTime now = LocalDateTime.now();
      String aux = now.format(dtf);
      drawEndString(aux, WIDTH, y, g2d); y+=15;
      
  }
 
  private void drawHeader(Graphics2D g2d) {
    BufferedImage img = null;
    try {
        img = ImageIO.read(new File("/home/eloi/proves/lpbt.png"));
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
  
  private void drawRefuelInfo(Graphics g) {
      
  }
  
  @Override
  public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
     int result = NO_SUCH_PAGE;    
        if (pageIndex == 0) {   
            Graphics2D g2d = (Graphics2D) graphics;
            g2d.translate((int) pageFormat.getImageableX()+OFFSET_WIDTH,(int) pageFormat.getImageableY());
            result = PAGE_EXISTS;
            drawHeader(g2d);
            drawUserInfo(g2d);
            drawRefuelInfo(g2d);
        }    
        return result;   
  }
  
}