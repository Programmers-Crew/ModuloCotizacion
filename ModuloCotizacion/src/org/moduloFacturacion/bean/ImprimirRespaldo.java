
package org.moduloFacturacion.bean;

import java.awt.Font;
import java.awt.print.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;
import java.time.LocalDate;
import javafx.collections.ObservableList;
import org.moduloFacturacion.controller.MenuPrincipalContoller;

public class ImprimirRespaldo implements Printable{
    PrinterJob printerJob;
    ObservableList<ProductoBuscado> mensaje;   
    String Nit, nombreCliente, direccionCliente,totalFactura;
    LocalDate fecha;
    public ImprimirRespaldo(){
        super();
        printerJob=PrinterJob.getPrinterJob();
        printerJob.setPrintable(this);       
    }
    MenuPrincipalContoller menu = new MenuPrincipalContoller();
    
    float diax = Float.parseFloat(menu.factura.get("diax", "root"));
    float diay = Float.parseFloat(menu.factura.get("diay", "root"));
    
    float mesx = Float.parseFloat(menu.factura.get("mesx", "root"));
    float mesy = Float.parseFloat(menu.factura.get("mesy", "root"));
    
    float añox = Float.parseFloat(menu.factura.get("añox", "root"));
    float añoy = Float.parseFloat(menu.factura.get("añoy", "root"));
    
    float nombrex = Float.parseFloat(menu.factura.get("nombrex", "root"));
    float nombrey = Float.parseFloat(menu.factura.get("nombrey", "root"));
    
    float direccionx = Float.parseFloat(menu.factura.get("direccionx", "root"));
    float direcciony = Float.parseFloat(menu.factura.get("direcciony", "root"));
    
    float nitx = Float.parseFloat(menu.factura.get("nitx", "root"));
    float nity = Float.parseFloat(menu.factura.get("nity", "root"));
    
    float tablax = Float.parseFloat(menu.factura.get("tablax", "root"));
    float tablay = Float.parseFloat(menu.factura.get("tablay", "root"));
    
    float descfacx = Float.parseFloat(menu.factura.get("descfacx", "root"));
    
    float valorx = Float.parseFloat(menu.factura.get("valorx", "root"));
    
    float espaciado = Float.parseFloat(menu.factura.get("espaciado", "root"));
    
    float totalfacx = Float.parseFloat(menu.factura.get("totalfacx", "root"));
    float totalfacy = Float.parseFloat(menu.factura.get("totalfacy", "root"));
    int tamaño = Integer.parseInt(menu.letra.get("tamaño", "root"));

     int tamañoNombre = Integer.parseInt(menu.letra.get("tamañoNombreB", "root"));
    int tamañoDireccion = Integer.parseInt(menu.letra.get("tamañoDirecB", "root"));
    
    int longitudProducto = Integer.parseInt(menu.letra.get("longitudProducto","root"));
    int longitudDireccion = Integer.parseInt(menu.letra.get("longitudDireccion","root"));
    int longitudNombre = Integer.parseInt(menu.letra.get("longitudNombre","root"));
    
    
    
    public void imprima(ObservableList<ProductoBuscado> mensaje, String Nit, String nombreCliente, String direccionCliente,LocalDate fecha, String totalFactura){
    this.mensaje=mensaje;
    this.Nit = Nit;
    this.nombreCliente = nombreCliente;
    this.direccionCliente = direccionCliente;
    this.fecha = fecha;
    this.totalFactura = totalFactura;
     if(printerJob.printDialog()){
        try{
            printerJob.print();
        }catch(Exception PrinterException){
            PrinterException.printStackTrace();
        }        
        } 
    }
    public void imprimir(Graphics2D g2d,PageFormat pf,int pagina){   
        
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(-270),0, 0);
        Font f1 = new Font(g2d.getFont().getFontName(),Font.PLAIN, tamaño);
        Font f1Rotated = f1.deriveFont(affineTransform);
        
        g2d.setFont(f1Rotated);
        
        g2d.drawString(String.valueOf(fecha.getDayOfMonth()), diay,diax);
        g2d.drawString(String.valueOf(fecha.getMonthValue()),mesy,mesx);
        g2d.drawString(String.valueOf(fecha.getYear()),añoy,añox);
        
            float ancho =tablay;
            float largo = tablax;
            float anchoDesc = descfacx;
            
        DecimalFormat df = new DecimalFormat("###,###.00");
            float anchoValor = valorx;
            float anchofor = ancho+espaciado;
            
        
        
        
        g2d.drawString(Nit,nity, nitx);
        
        for(int x=0; x< mensaje.size();x++){
            String precio = String.valueOf(df.format(mensaje.get(x).getProductoPrecio()));
            String totalp = String.valueOf(df.format(mensaje.get(x).getCantidad()*mensaje.get(x).getProductoPrecio()));

            anchofor = anchofor-espaciado;
            g2d.drawString(String.valueOf(mensaje.get(x).getCantidad()),anchofor, largo);
            
            if(mensaje.get(x).getProductoDesc().length()>longitudProducto){
                g2d.drawString(mensaje.get(x).getProductoDesc().substring(0, longitudProducto), anchofor , anchoDesc);
            }else{
                g2d.drawString(mensaje.get(x).getProductoDesc(), anchofor , anchoDesc);
            }
            
            int tamañoTotal = totalp.length();
            switch(tamañoTotal)  {
                case 4:
                    g2d.drawString(totalp, anchofor, anchoValor+16);
                    break;
                case 5:
                    g2d.drawString(totalp, anchofor, anchoValor+11);
                    break;
                case 6:
                    g2d.drawString(totalp, anchofor, anchoValor+6);
                    break;
                case 8:
                    g2d.drawString(totalp, anchofor, anchoValor-1);
                    break;
                case 9:
                    g2d.drawString(totalp, anchofor, anchoValor-7);
                    break;
                case 10:
                    g2d.drawString(totalp, anchofor, anchoValor-12);
                    break;
            }
        }
         
        String totalF = df.format(Double.parseDouble(totalFactura));
        int tamañoFacE = totalF.length();
        
         switch(tamañoFacE)  {
                case 4:
                    g2d.drawString(totalF, totalfacy, totalfacx+20);
                    break;
                case 5:
                    g2d.drawString(totalF, totalfacy, totalfacx+15);
                    break;
                case 6:
                    g2d.drawString(totalF, totalfacy, totalfacx+10);
                    break;
                case 8:
                    g2d.drawString(totalF, totalfacy, totalfacx+2);
                    break;
                case 9:
                    g2d.drawString(totalF, totalfacy, totalfacx-7);
                    break;
                case 10:
                    g2d.drawString(totalF, totalfacy, totalfacx-9);
                    break;
            }
         
        Font fNombre = new Font(g2d.getFont().getFontName(),Font.PLAIN, tamañoNombre);
        Font fNombreRotated = fNombre.deriveFont(affineTransform);
        g2d.setFont(fNombreRotated);
        
        if(nombreCliente.length()> longitudNombre){
            g2d.drawString(nombreCliente.substring(0, longitudNombre), nombrey, nombrex);
        }else{
            g2d.drawString(nombreCliente, nombrey, nombrex);
        }
        
        
        Font fDireccion = new Font(g2d.getFont().getFontName(),Font.PLAIN, tamañoDireccion);
        Font fDireccionRotated = fDireccion.deriveFont(affineTransform);
        g2d.setFont(fDireccionRotated);
        
        if(direccionCliente.length() > longitudDireccion){
            g2d.drawString(direccionCliente.substring(0, longitudDireccion),direcciony, direccionx); 
        }else{
            g2d.drawString(direccionCliente,direcciony, direccionx); 
        }
        
        menu.letra.put("tamañoNombreB",menu.letra.get("tamaño", "root") );
        menu.letra.put("tamañoNombreA",menu.letra.get("tamaño1", "root") );
        
        menu.letra.put("tamañoDirec",menu.letra.get("tamaño", "root") );
        menu.letra.put("tamañoDirecA",menu.letra.get("tamaño1", "root") );
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
