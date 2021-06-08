/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ModuloCotizacion.bean;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author davis
 */
public class generarCotizacion {
    public void generar(ObservableList listaCamposEspeciales, ObservableList listaCotizacion, double descuento, double total, String nombre, String direccion,String img, AnchorPane anchor){
        
        Stage stage = (Stage) anchor.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Cotización");
        
        File file = new File(fileChooser.showSaveDialog(stage).getPath()+".docx");
        
        if(!file.exists()){
            try {
                file.createNewFile();
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
    }
}
