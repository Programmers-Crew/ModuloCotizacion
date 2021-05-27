
package org.moduloFacturacion.bean;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterJob;
import java.text.DecimalFormat;
import org.moduloFacturacion.controller.MenuPrincipalContoller;

/**
 *
 * @author davis
 */
public class imprimirCheque2 implements Printable{
     PrinterJob printerJob;
    String lugarYfecha,ordenDe,suma,valorTotal;
    double cantidad;
    MenuPrincipalContoller menu = new MenuPrincipalContoller();
    float fechaX = Float.parseFloat(menu.PfechaX.get("valor1", "root"));
    float fechaY = Float.parseFloat(menu.PfechaY.get("valor2", "root"));
    
    float ordenX = Float.parseFloat(menu.PordenX.get("valor3", "root"));
    float ordenY = Float.parseFloat(menu.PordenY.get("valor4", "root"));
    
    float totalX = Float.parseFloat(menu.PTotalX.get("valor5", "root"));
    float totalY = Float.parseFloat(menu.PTotalY.get("valor6", "root"));
    
    float letrasX = Float.parseFloat(menu.PLetrasX.get("valor7", "root"));
    float letrasY = Float.parseFloat(menu.PLetrasY.get("valor8", "root"));
    
    public imprimirCheque2(){
        super();
        printerJob=PrinterJob.getPrinterJob();
        printerJob.setPrintable(this);       
    }
    // Método que lanza el cuadro de diálogos de la impresora e imprime
    // imagen
    public void imprima(String lugarYfecha,double cantidad, String ordenDe, String suma, String valorTotal){
        this.lugarYfecha = lugarYfecha;
        this.cantidad = cantidad;
        this.ordenDe = ordenDe;
        this.suma = suma;
        this.valorTotal = valorTotal;
        if(printerJob.printDialog()){
            try{
                printerJob.print();
            }catch(Exception PrinterException){
                PrinterException.printStackTrace();
            }        
        } 
    }
    // Método que traza la imagen para imprimir
    public void imprimir(Graphics2D g2d,PageFormat pf,int pagina){ 
        
         
        DecimalFormat formatea = new DecimalFormat("###,###.00");
        String vt = String.valueOf(formatea.format(Double.parseDouble(valorTotal)));
        pf.setOrientation(PageFormat.LANDSCAPE);
         Font font = new Font(null, Font.PLAIN, 0);    
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(-270),0, 0);
        Font rotatedFont = font.deriveFont(affineTransform);
        g2d.setFont(rotatedFont);
        g2d.drawString(lugarYfecha, fechaY,fechaX);
        
        g2d.drawString(ordenDe,ordenY, ordenX);
        g2d.drawString(String.valueOf(suma),totalY,totalX);
        g2d.drawString(vt,letrasY,letrasX);
        
    }
    
    
    
    public int print(Graphics g,PageFormat pf,int pagina){
      Graphics2D g2d=(Graphics2D)g;
      g2d.translate(pf.getImageableX(),pf.getImageableY());     
            switch(pagina){
                case 0:
                imprimir(g2d,pf,pagina);
                return (PAGE_EXISTS);            
                default:
                return (NO_SUCH_PAGE);
            }   
    }  
    
}
