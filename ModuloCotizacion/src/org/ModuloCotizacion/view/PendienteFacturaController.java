/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ModuloCotizacion.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Davis
 */
public class PendienteFacturaController implements Initializable {

    @FXML
    private Pane regresarbtn;
    @FXML
    private Pane regresarpane;
    @FXML
    private AnchorPane anchor1;
    @FXML
    private JFXTextField txtCodigoProveedores;
    @FXML
    private AnchorPane anchor2;
    @FXML
    private TableView<?> tblProveedores;
    @FXML
    private TableColumn<?, ?> colCodigoProveedores;
    @FXML
    private TableColumn<?, ?> colNombreProveedores;
    @FXML
    private TableColumn<?, ?> colTellefonoProveedores;
    @FXML
    private TableColumn<?, ?> colNitProveedores;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private ComboBox<?> cmbFiltroProveedores;
    @FXML
    private ComboBox<?> cmbBuscar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void regresar(MouseEvent event) {
    }

    @FXML
    private void validarNumeroProveedores(KeyEvent event) {
    }

    @FXML
    private void seleccionarElementos(MouseEvent event) {
    }

    @FXML
    private void btnBuscar(MouseEvent event) {
    }

    @FXML
    private void codigoBuscadoProveedores(MouseEvent event) {
    }

    @FXML
    private void comboFiltro(ActionEvent event) {
    }

    @FXML
    private void cmbBuscar(ActionEvent event) {
    }

    @FXML
    private void atajosProveedores(KeyEvent event) {
    }
    
}
