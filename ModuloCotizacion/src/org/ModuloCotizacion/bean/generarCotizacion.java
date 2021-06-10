/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ModuloCotizacion.bean;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 *
 * @author davis
 */
public class generarCotizacion {
    public void generar(ObservableList<CamposEspeciales> listaCamposEspeciales, ObservableList<cotizacionBackup> listaDetalle, double descuento, double total,String img, AnchorPane anchor, int cotizacionId, String nit, String nombreCliente, String Direccion, double descuentoNeto, double totalC){
        
        
        
        
        String rutaImagen = "C:/Program Files (x86)/ModuloCotizacion/img/grupoAlcon.jpeg";
        
        String encabezado = "COTIZACIÓN "+cotizacionId;
        
        XWPFDocument document = new XWPFDocument();
        
        XWPFParagraph doc = document.createParagraph();    
        XWPFRun run = doc.createRun();
        
        
        try {
            FileInputStream  is = new FileInputStream (rutaImagen);
            run.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG,"grupoAlcon.jpeg",Units.toEMU(200), Units.toEMU(200));
            is.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (InvalidFormatException ex) {
            Logger.getLogger(generarCotizacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(generarCotizacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        run.addBreak();
        run.setText("");
        run.addBreak();
        run.setBold(true);
        doc.setAlignment(ParagraphAlignment.CENTER);
        run.setText(encabezado);
        
        
        XWPFTable table = document.createTable();
        
        XWPFTableRow tableRowOne = table.getRow(0);
        tableRowOne.getCell(0).setText("CANTIDAD");
        tableRowOne.addNewTableCell().setText("DESCRIPCIÓN");
        tableRowOne.addNewTableCell().setText("ANCHO");
        tableRowOne.addNewTableCell().setText("ALTO");
        tableRowOne.addNewTableCell().setText("LARGO");
        tableRowOne.addNewTableCell().setText("PRECIO UNITARIO");
        tableRowOne.addNewTableCell().setText("TOTAL");
        
        
        for(int x=0; x<listaDetalle.size(); x++){
            XWPFTableRow tableRow = table.createRow();
            tableRow.getCell(0).setText(String.valueOf(listaDetalle.get(x).getCotizacionCantida()));
            tableRow.getCell(1).setText(String.valueOf(listaDetalle.get(x).getCotizacionAncho()));
            tableRow.getCell(2).setText(String.valueOf(listaDetalle.get(x).getCotizacionAlto()));
            tableRow.getCell(3).setText(String.valueOf(listaDetalle.get(x).getCotizacionLargo()));
            tableRow.getCell(4).setText(String.valueOf(listaDetalle.get(x).getCotizacionPrecioU()));
            tableRow.getCell(5).setText(String.valueOf(listaDetalle.get(x).getCotizacionTotalParcial()));
            
        }
        
        
        Stage stage = (Stage) anchor.getScene().getWindow();
        
        
        try{
            FileChooser fileChooser = new FileChooser();   
            
            fileChooser.setTitle("Guardar Cotización");
            File option = fileChooser.showSaveDialog(null);
           
            if(!option.exists()){
                FileOutputStream word = new FileOutputStream(option.getAbsolutePath()+".docx");
                
                document.write(word);

                document.close();
                File ruta = new File(option.getAbsolutePath()+".docx");
                Desktop.getDesktop().open(ruta);
                System.out.println("si se pudo");
            }
            
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
}
