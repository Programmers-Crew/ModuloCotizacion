package org.moduloFacturacion.bean;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javax.swing.JFileChooser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author david
 */
public class GenerarExcelCardex {

    
    public  void generar(ObservableList lista, String nombreProducto) throws FileNotFoundException, IOException {
        // Creamos el archivo donde almacenaremos la hoja
        // de calculo, recuerde usar la extension correcta,
        // en este caso .xlsx
        
        ObservableList<Cardex> listaCardexG;
        
        listaCardexG = lista;
        
        String nombreArchivo = "reporteCardex.xlsx";

        // Creamos el libro de trabajo de Excel formato OOXML
        Workbook workbook = new XSSFWorkbook();

        // La hoja donde pondremos los datos
        Sheet pagina = workbook.createSheet("Reporte de Cardex");

        // Creamos el estilo paga las celdas del encabezado
        CellStyle style = workbook.createCellStyle();
        // Indicamos que tendra un fondo azul aqua
        // con patron solido del color indicado
        style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        String[] titulos = {"FECHA", "NO.",
            "MOVIMIENTO", "DOCUMENTO","ENTRADA","SALIDA","SALDO"};
        
        
        
        // Creamos una fila en la hoja en la posicion 0
        Row fila0 = pagina.createRow(0);
        Cell c1 = fila0.createCell(0);
        c1.setCellValue("PRODUCTO:");
        Cell c = fila0.createCell(1);
        c.setCellValue(nombreProducto);
        
        Row fila = pagina.createRow(1);
        // Creamos el encabezado
        for (int i = 0; i < titulos.length; i++) {
            // Creamos una celda en esa fila, en la posicion 
            // indicada por el contador del ciclo
            Cell celda = fila.createCell(i);
            // Indicamos el estilo que deseamos 
            // usar en la celda, en este caso el unico 
            // que hemos creado
            celda.setCellStyle(style);
            celda.setCellValue(titulos[i]);
            
        }

        for(int x=0; x<listaCardexG.size(); x++){
            fila = pagina.createRow(x+2);
            for (int i = 0; i < titulos.length; i++) {
                // Creamos una celda en esa fila, en la
                // posicion indicada por el contador del ciclo
                Cell celda = fila.createCell(i);
                switch(i){
                    case 0:
                        celda.setCellValue(listaCardexG.get(x).getFechaCardex().toString());
                        break;
                    case 1:
                        celda.setCellValue(listaCardexG.get(x).getNoFacCardex());
                        break;
                    case 2:
                        celda.setCellValue(listaCardexG.get(x).getIdTipoDesc());
                        break;
                    case 3:
                        celda.setCellValue(listaCardexG.get(x).getDescTipoDocumento());
                        break;
                    case 4:
                        celda.setCellValue(listaCardexG.get(x).getEntradaCardex());
                        break;
                    case 5:
                        celda.setCellValue(listaCardexG.get(x).getSaldoCardex());
                        break;
                    case 6:
                        celda.setCellValue(listaCardexG.get(x).getTotalCardex());
                        break;
                }
                
            }
        }
        

        // Y colocamos los datos en esa fila
        

        // Ahora guardaremos el archivo
        JFileChooser fileChooser = new JFileChooser();   
           int option = fileChooser.showSaveDialog(fileChooser);
            if(option == JFileChooser.APPROVE_OPTION){
                if(fileChooser.getSelectedFile()!=null){
                    FileOutputStream salida = new FileOutputStream(fileChooser.getSelectedFile()+nombreArchivo);
                    File ruta = new File(fileChooser.getSelectedFile()+nombreArchivo);
                    workbook.write(salida);

                    workbook.close();

                    try {
                        Desktop.getDesktop().open(ruta);
                    } catch (IOException ex) {
                        Logger.getLogger(GenerarExcel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("si se pudo");
                }
            }
    }

}