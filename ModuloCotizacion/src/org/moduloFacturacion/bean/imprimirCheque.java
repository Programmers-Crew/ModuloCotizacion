/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moduloFacturacion.bean;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterJob;
import java.text.DecimalFormat;
import java.time.LocalDate;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.moduloFacturacion.controller.MenuPrincipalContoller;

    

/**
 *
 * @author davis
 */
public class imprimirCheque implements Printable{
     PrinterJob printerJob;
    String chequeNo;
    String lugarYfecha,ordenDe,suma,valorTotal,desc;
    Image img= new Image("org/moduloFacturacion/img/comprobantecheque.png");
    
    BufferedImage image = SwingFXUtils.fromFXImage(img, null);
    LocalDate fechaActual = LocalDate.now();
    
    MenuPrincipalContoller menu = new MenuPrincipalContoller();
    
    float chequeNoX = Float.parseFloat(menu.comprobante.get("nochequeX", "root"));
    float chequeNoY = Float.parseFloat(menu.comprobante.get("nochequeY", "root"));
    
    
    float fechaX = Float.parseFloat(menu.comprobante.get("fechaX", "root"));
    float fechaY = Float.parseFloat(menu.comprobante.get("fechaY", "root"));
    
    float ordenX = Float.parseFloat(menu.comprobante.get("ordenX", "root"));
    float ordenY = Float.parseFloat(menu.comprobante.get("ordenY", "root"));
    
    float totalX = Float.parseFloat(menu.comprobante.get("totalX", "root"));
    float totalY = Float.parseFloat(menu.comprobante.get("totalY", "root"));
    
    float letrasX = Float.parseFloat(menu.comprobante.get("letrasX", "root"));
    float letrasY = Float.parseFloat(menu.comprobante.get("letrasY", "root"));
    
    
    float descX = Float.parseFloat(menu.comprobante.get("descX", "root"));
    float descY = Float.parseFloat(menu.comprobante.get("descY", "root"));
    
    
    public imprimirCheque(){
        super();
        printerJob=PrinterJob.getPrinterJob();
        printerJob.setPrintable(this);       
    }
    // Método que lanza el cuadro de diálogos de la impresora e imprime
    // imagen
    public void imprima(String chequeNo,String lugarYfecha,String ordenDe, String suma, String valorTotal, String desc){
        this.chequeNo = chequeNo;
        this.lugarYfecha = lugarYfecha;
        this.ordenDe = ordenDe;
        this.suma = suma;
        this.valorTotal = valorTotal;
        this.desc = desc;
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
        g2d.drawImage(image, 25,25,500, 500,null);
        g2d.drawString(chequeNo,chequeNoX,chequeNoY);
        g2d.drawString(lugarYfecha,fechaX, fechaY);
        g2d.drawString(ordenDe,ordenX,ordenY);
        g2d.drawString(String.valueOf(suma),totalX,totalY);     
        g2d.drawString(vt, letrasX, letrasY);
        g2d.drawString(String.valueOf(fechaActual), 170, 495);
         String[] arreglo = desc.split("\\n");
         float x= descX;
         float y = 290;
         for (String arreglo1 : arreglo) {
             g2d.drawString(arreglo1, x, y);
             y=y+12;
             
         }
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
