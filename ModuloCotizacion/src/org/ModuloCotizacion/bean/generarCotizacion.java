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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TableRowAlign;
import org.apache.poi.xwpf.usermodel.XWPFAbstractNum;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFNumbering;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTAbstractNum;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTLvl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat;

/**
 *
 * @author davis
 */
public class generarCotizacion {
    public void generar(ObservableList<CamposEspeciales> listaCamposEspeciales, ObservableList<cotizacionBackup> listaDetalle, double descuento, double total,String img, AnchorPane anchor, int cotizacionId, String nit, String nombreCliente, String Direccion, double descuentoNeto, double totalC, String fecha){
        
        
        
        
        String rutaImagen = "C:/Program Files (x86)/ModuloCotizacion/img/grupoAlcon.jpeg";
        
        String encabezado = "COTIZACIÓN "+cotizacionId;
        
        XWPFDocument document = new XWPFDocument();
        
        XWPFParagraph doc = document.createParagraph();    
        XWPFRun run = doc.createRun();
        
        
        doc.setAlignment(ParagraphAlignment.LEFT);
        try {
            FileInputStream  is = new FileInputStream (rutaImagen);
            run.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG,"grupoAlcon.jpeg",Units.toEMU(200), Units.toEMU(100));
            is.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (InvalidFormatException ex) {
            Logger.getLogger(generarCotizacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(generarCotizacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        doc = document.createParagraph();
        
        run = doc.createRun();
        doc.setAlignment(ParagraphAlignment.CENTER);
        
        run.addBreak();
        run.setBold(true);
        doc.setAlignment(ParagraphAlignment.CENTER);
        run.setFontSize(20);
        run.setText(encabezado);
        
       doc = document.createParagraph();
        run = doc.createRun();
        run.setFontSize(14);
        String listarNit = "NIT: "+nit;
        String listarNombre = "NOMBRE: "+nombreCliente;
        String listarFecha = "FECHA: "+fecha;
        
        
        run.setFontSize(12);
        run.setText(listarNit);
        run.addBreak();
        
        run.setText(listarNombre);
        run.addBreak();
        
        run.setText(listarFecha);
        
       if(!img.equals("default.png")){
           doc = document.createParagraph();
            run = doc.createRun();
            doc.setAlignment(ParagraphAlignment.CENTER);
            try {
                FileInputStream  is = new FileInputStream ("C:/Program Files (x86)/ModuloCotizacion/img/"+img);
                run.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG,img,Units.toEMU(100), Units.toEMU(100));
                is.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (InvalidFormatException ex) {
                Logger.getLogger(generarCotizacion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(generarCotizacion.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
        
        
        
        doc = document.createParagraph();
        run = doc.createRun();
        
        run.setBold(true);
        run.setFontSize(15);
        run.setText("DETALLE DE COTIZACIÓN");
        
        
        XWPFTable table = document.createTable();
       
        table.setTableAlignment(TableRowAlign.CENTER);
        table.setWidth(9000);
        table.setCellMargins(0, 200, 0, 0);
       
        
        
        XWPFTableRow tableRowOne = table.getRow(0);
        tableRowOne.getCell(0).setText("CANTIDAD");
        tableRowOne.addNewTableCell().setText("DESCRIPCIÓN");
        tableRowOne.addNewTableCell().setText("ANCHO");
        tableRowOne.addNewTableCell().setText("ALTO");
        tableRowOne.addNewTableCell().setText("LARGO");
        tableRowOne.addNewTableCell().setText("PRECIO UNITARIO");
        tableRowOne.addNewTableCell().setText("TOTAL");
        
        tableRowOne.setHeight(600);
        tableRowOne.getCell(0).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        tableRowOne.getCell(1).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        tableRowOne.getCell(2).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        tableRowOne.getCell(3).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        tableRowOne.getCell(4).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        tableRowOne.getCell(5).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        tableRowOne.getCell(6).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        
   
        

        double totalSuma = 0;
        
        for(int x=0; x<listaDetalle.size(); x++){
            XWPFTableRow tableRow = table.createRow();
            table.setTableAlignment(TableRowAlign.CENTER);
            tableRow.setHeight(600);
            tableRow.getCell(0).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            tableRow.getCell(1).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            tableRow.getCell(2).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            tableRow.getCell(3).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            tableRow.getCell(4).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            tableRow.getCell(5).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            tableRow.getCell(6).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            
            tableRow.getCell(0).setText(String.valueOf(listaDetalle.get(x).getCotizacionCantida()));
            tableRow.getCell(1).setText(String.valueOf(listaDetalle.get(x).getCotizacionDesc()));
            tableRow.getCell(2).setText(String.valueOf(listaDetalle.get(x).getCotizacionAncho()));
            tableRow.getCell(3).setText(String.valueOf(listaDetalle.get(x).getCotizacionAlto()));
            tableRow.getCell(4).setText(String.valueOf(listaDetalle.get(x).getCotizacionLargo()));
            tableRow.getCell(5).setText(String.valueOf(listaDetalle.get(x).getCotizacionPrecioU()));
            tableRow.getCell(6).setText(String.valueOf(listaDetalle.get(x).getCotizacionTotalParcial()));
            
            
            totalSuma = totalSuma+listaDetalle.get(x).getCotizacionTotalParcial();
        }
        
        XWPFTableRow tableRow = table.createRow();
        table.setTableAlignment(TableRowAlign.CENTER);
        tableRow.setHeight(600);
        
        
        
        tableRow.getCell(5).setText("TOTAL: ");
        tableRow.getCell(6).setText(String.valueOf(totalSuma));
        
        
        tableRow.getCell(1).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        tableRow.getCell(2).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        tableRow.getCell(3).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        tableRow.getCell(4).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        tableRow.getCell(5).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        tableRow.getCell(6).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        
        if(listaCamposEspeciales.size()>0){
            doc = document.createParagraph();
            run = doc.createRun();

            run.setBold(true);
            run.setFontSize(15);
            run.setText("DETALLE DE EXTRAS");


            XWPFTable tableCampos = document.createTable();

            tableCampos.setTableAlignment(TableRowAlign.CENTER);
            tableCampos.setWidth(9000);
            tableCampos.setCellMargins(0, 200, 0, 0);


            XWPFTableRow tableRowOneCampos = tableCampos.getRow(0);
            tableRowOneCampos.getCell(0).setText("EXTRA");
            tableRowOneCampos.addNewTableCell().setText("PRECIO");


            tableRowOne.setHeight(600);
            tableRowOneCampos.getCell(0).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            tableRowOneCampos.getCell(1).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);

            double totalSumaCampos = 0;

            for(int x=0; x<listaCamposEspeciales.size(); x++){
                XWPFTableRow tableRowCampos = tableCampos.createRow();
                tableCampos.setTableAlignment(TableRowAlign.CENTER);
                tableRowCampos.setHeight(600);
                tableRowCampos.getCell(0).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                tableRowCampos.getCell(1).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);


                tableRowCampos.getCell(0).setText(String.valueOf(listaCamposEspeciales.get(x).getCampoNombre()));
                tableRowCampos.getCell(1).setText(String.valueOf(listaCamposEspeciales.get(x).getCampoPrecio()));


                totalSumaCampos = totalSuma+listaCamposEspeciales.get(x).getCampoPrecio();
            }

            XWPFTableRow tableRowCampos = tableCampos.createRow();
            tableCampos.setTableAlignment(TableRowAlign.CENTER);
            tableRowCampos.setHeight(600);



            tableRowCampos.getCell(0).setText("TOTAL: ");
            tableRowCampos.getCell(1).setText(String.valueOf(totalSumaCampos));


            tableRowCampos.getCell(0).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            tableRowCampos.getCell(1).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        }
        
        
        
        doc = document.createParagraph();
        run = doc.createRun();
        run.addBreak();
        run.setText("");
        run.addBreak();
        run.setText("");
        run.addBreak();
        run.setBold(true);
        run.setFontSize(15);
        run.setText("CONDICIONES DE COTIZACIÓN");
        
        
        XWPFNumbering numbering = null;
        
        numbering = document.createNumbering();
        
        ArrayList<String> documentList = new ArrayList<String>(
        Arrays.asList(
         new String[] {
          "NIT: 1265209-8     SE REQUIERE EL 50% DE ANTICIPO",
          "ANTICIPO Q.              SALDO Q.        ",
          "COTIZACIÓN VALIDA POR 15 DÍAS",
          "ENVÍOS A TODA LA REPUBLICA",
          "https://www.facebook.com/Duropor.Alcon"
           
         }));
        CTAbstractNum cTAbstractNum = CTAbstractNum.Factory.newInstance();
        
        cTAbstractNum.setAbstractNumId(BigInteger.valueOf(0));

    
        CTLvl cTLvl = cTAbstractNum.addNewLvl();
        cTLvl.addNewNumFmt().setVal(STNumberFormat.DECIMAL);
        cTLvl.addNewLvlText().setVal("%1.");
        cTLvl.addNewStart().setVal(BigInteger.valueOf(1));

        XWPFAbstractNum abstractNum = new XWPFAbstractNum(cTAbstractNum);


        BigInteger abstractNumID = numbering.addAbstractNum(abstractNum);

        BigInteger numID = numbering.addNum(abstractNumID);

        for (String string : documentList) {
         doc = document.createParagraph();
         doc.setNumID(numID);
         run=doc.createRun(); 
         run.setText(string); 
        }
        
        XWPFHeaderFooterPolicy hfPolicy = document.createHeaderFooterPolicy();
        CTP ctP = CTP.Factory.newInstance();
        CTText t = ctP.addNewR().addNewT();
        
        XWPFParagraph[] pars = new XWPFParagraph[1];
		
        t.setStringValue("DISTRIBUIDORA ALCON 18 CALLE 8-29 ZONA 1 TEL. 22211137 / 56465449  E-MAIL: ventas.alcon2013@gmail.com");

        pars[0] = new XWPFParagraph(ctP, document);
        hfPolicy.createFooter(XWPFHeaderFooterPolicy.DEFAULT, pars);
        
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
