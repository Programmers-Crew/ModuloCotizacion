/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moduloFacturacion.bean;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.text.DecimalFormat;
import java.time.LocalDate;
import javafx.collections.ObservableList;
import org.moduloFacturacion.controller.MenuPrincipalContoller;

/**
 *
 * @author davis
 */
public class ImprimirOrdenRespaldo implements Printable{
     PrinterJob printerJob;
    ObservableList<ProductoBuscado> mensaje;   
    String Nit, nombreCliente, direccionCliente, totalLetras,totalFactura;
    LocalDate fecha;
    public ImprimirOrdenRespaldo(){
        super();
        printerJob=PrinterJob.getPrinterJob();
        printerJob.setPrintable(this);       
    }
    
    public void imprima(ObservableList<ProductoBuscado> mensaje, String Nit, String nombreCliente, String direccionCliente,LocalDate fecha,String totalLetras, String totalFactura){
    this.mensaje=mensaje;
    this.Nit = Nit;
    this.nombreCliente = nombreCliente;
    this.direccionCliente = direccionCliente;
    this.fecha = fecha;
    this.totalLetras = totalLetras;
    this.totalFactura = totalFactura;
     if(printerJob.printDialog()){
        try{
            printerJob.print();
        }catch(Exception PrinterException){
            PrinterException.printStackTrace();
        }        
        } 
    }
    
    MenuPrincipalContoller menu = new MenuPrincipalContoller();
    float diax = Float.parseFloat(menu.orden.get("diaxorden", "root"));
    float diay = Float.parseFloat(menu.orden.get("diayorden", "root"));
    
    float mesx = Float.parseFloat(menu.orden.get("mesxorden", "root"));
    float mesy = Float.parseFloat(menu.orden.get("mesyorden", "root"));
    
    float añox = Float.parseFloat(menu.orden.get("añoxorden", "root"));
    float añoy = Float.parseFloat(menu.orden.get("añoyorden", "root"));
    
    float nombrex = Float.parseFloat(menu.orden.get("nombrexorden", "root"));
    float nombrey = Float.parseFloat(menu.orden.get("nombreyorden", "root"));
    
    float direccionx = Float.parseFloat(menu.orden.get("direccionxorden", "root"));
    float direcciony = Float.parseFloat(menu.orden.get("direccionyorden", "root"));
    
    float tablax = Float.parseFloat(menu.orden.get("tablaxorden", "root"));
    float tablay = Float.parseFloat(menu.orden.get("tablayorden", "root"));
    
    float descfacx = Float.parseFloat(menu.orden.get("descfacxorden", "root"));
    
    float valorx = Float.parseFloat(menu.orden.get("valorxorden", "root"));
    
    float espaciado = Float.parseFloat(menu.orden.get("espaciadoorden", "root"));
    
    float totalfacx = Float.parseFloat(menu.orden.get("totalfacxorden", "root"));
    float totalfacy = Float.parseFloat(menu.orden.get("totalfacyorden", "root"));
    int tamaño = Integer.parseInt(menu.letra.get("tamaño", "root"));
    
    public void imprimir(Graphics2D g2d,PageFormat pf,int pagina){   
        Font font = new Font(null, Font.PLAIN, 0);    
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(-270),0, 0);
        Font rotatedFont = font.deriveFont(affineTransform);
        g2d.setFont(rotatedFont);
        g2d.drawString(String.valueOf(fecha.getDayOfMonth()), diay,diax);
        g2d.drawString(String.valueOf(fecha.getMonthValue()),mesy,mesx);
        g2d.drawString(String.valueOf(fecha.getYear()),añoy,añox);
//        g2d.drawString(Nit,362 , 215);
            float ancho =tablay;
            float largo = tablax;
            float anchoDesc = descfacx;
        DecimalFormat df = new DecimalFormat("###,###.00");
        Font f1 = new Font(g2d.getFont().getFontName(),Font.PLAIN, tamaño);
        Font f1Rotated = f1.deriveFont(affineTransform);
        g2d.setFont(f1Rotated);
        g2d.drawString(direccionCliente,direcciony, direccionx);
        
        g2d.drawString(nombreCliente, nombrey, nombrex);
            float anchoValor = valorx;
            float anchofor = ancho+espaciado;
          for(int x=0; x< mensaje.size();x++){
              anchofor = anchofor-18;
              String totalp = String.valueOf(df.format(mensaje.get(x).getCantidad()*mensaje.get(x).getProductoPrecio()));
              String precio = String.valueOf(df.format(mensaje.get(x).getProductoPrecio()));
              g2d.drawString(String.valueOf(mensaje.get(x).getCantidad()),anchofor, largo);
              g2d.drawString(mensaje.get(x).getProductoDesc(), anchofor , anchoDesc);
              g2d.drawString(String.valueOf(totalp), anchofor, anchoValor);
              
          }
        g2d.drawString(df.format(Double.parseDouble(totalFactura)), totalfacy, totalfacx);
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
