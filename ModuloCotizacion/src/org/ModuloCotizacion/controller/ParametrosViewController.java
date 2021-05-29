package org.ModuloCotizacion.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.ModuloCotizacion.bean.Animations;
import org.ModuloCotizacion.bean.AutoCompleteComboBoxListener;
import org.ModuloCotizacion.bean.CambioScene;
import org.ModuloCotizacion.bean.FactorVenta;
import org.ModuloCotizacion.bean.ValidarStyle;
import org.ModuloCotizacion.db.Conexion;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author davis
 */
public class ParametrosViewController implements Initializable {

    @FXML
    private Pane regresarbtn;
    @FXML
    private Pane regresarpane;
    @FXML
    private AnchorPane anchor1;
    @FXML
    private JFXTextField txtCodigoFactor;
    @FXML
    private JFXTextField descuentoFactor;
    @FXML
    private JFXButton btnAgregarFactor;
    @FXML
    private JFXButton btnEliminarFactor;
    @FXML
    private JFXButton btnEditarFactor;
    @FXML
    private TextArea descFactor;
    @FXML
    private AnchorPane anchor2;
    @FXML
    private TableView<FactorVenta> tblFactor;
    @FXML
    private TableColumn<FactorVenta, Integer> colCodigoFactor;
    @FXML
    private TableColumn<FactorVenta, String> colDescFactor;
    @FXML
    private TableColumn<FactorVenta, Double> colDescuentoFactor;
    @FXML
    private JFXButton btnBuscarFactor;
    @FXML
    private ComboBox<String> cmbFiltroDesc;
    @FXML
    private ComboBox<String> cmbBuscarFactor;
    @FXML
    private AnchorPane anchor;
    
    
    CambioScene cambioScene = new CambioScene();
    Image imgError = new Image("org/ModuloCotizacion/img/error.png");
    Image imgCorrecto= new Image("org/ModuloCotizacion/img/correcto.png");
    MenuPrincipalContoller menu = new MenuPrincipalContoller();
    ValidarStyle validar = new ValidarStyle();
    Animations animacion = new Animations();
    ObservableList<FactorVenta>listaFactor;
    ObservableList<String>listaCombo;
    ObservableList<String>listaFiltro;
    
    public enum OperacionFactor{AGREGAR,GUARDAR,ELIMINAR,BUSCAR,ACTUALIZAR,CANCELAR,NINGUNO};
    public OperacionFactor tipoOperacionFactor= OperacionFactor.NINGUNO;
    int codigo=0;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        ArrayList<String> lista = new ArrayList();
        cmbFiltroDesc.setValue("");
        lista.add(0,"CÓDIGO");
        lista.add(1,"NIT");
        listaFiltro = FXCollections.observableList(lista);
        cmbBuscarFactor.setItems(listaFiltro);
        animacion.animacion(anchor1, anchor2);
    }    
    
    
    public void disableCamposFactor(){
        txtCodigoFactor.setDisable(true);
        descFactor.setDisable(true);
        descuentoFactor.setDisable(true);
    }
    
    public void limpiarText(){
        txtCodigoFactor.setText("");
        descFactor.setText("");
        descuentoFactor.setText("");
    }
    
    public void disableButtons(){
        btnAgregarFactor.setDisable(true);
        btnEliminarFactor.setDisable(true);
        btnEditarFactor.setDisable(true);
    }
     public void activateButtons(){
        btnAgregarFactor.setDisable(false);
        btnEliminarFactor.setDisable(false);
        btnEditarFactor.setDisable(false);
    }
    public void activarText(){
        txtCodigoFactor.setDisable(false);
        descFactor.setDisable(false);
        descuentoFactor.setDisable(false);
    }
    
    public ObservableList getFactorVenta(){
        ArrayList<FactorVenta> lista = new ArrayList();
        String sql = "{call Sp_ListFactorVenta()}";
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                lista.add(new FactorVenta(
                        rs.getInt("factorVentaId"),
                        rs.getString("factorVentaDesc"),
                        rs.getDouble("factorVentaDescuento")
                
                ));
            }
           
        }catch(SQLException ex){
             Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR");
            noti.text("NO SE HA PODIDO CARGAR LA DB "+ex);
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
        }
        
        return listaFactor = FXCollections.observableList(lista);
    }
    public void cargarDatos(){
        tblFactor.setItems(getFactorVenta());
        colCodigoFactor.setCellValueFactory(new PropertyValueFactory("factorVentaId"));
        colDescFactor.setCellValueFactory(new PropertyValueFactory("factorVentadesc"));
        colDescuentoFactor.setCellValueFactory(new PropertyValueFactory("factorVentaDescuento"));
    }
    
    
    
    @FXML
    private void regresar(MouseEvent event) throws IOException {
         String menu = "org/ModuloCotizacion/view/menuPrincipal.fxml";
        cambioScene.Cambio(menu,(Stage) anchor.getScene().getWindow());
    }
    
    public void accionFactor(){
        switch(tipoOperacionFactor){
            case AGREGAR:
                tipoOperacionFactor = OperacionFactor.GUARDAR;
                disableButtons();
                btnAgregarFactor.setText("GUARDAR");
                btnEliminarFactor.setText("CANCELAR");
                btnEliminarFactor.setDisable(false);
                activarText();
                limpiarText();
                btnBuscarFactor.setDisable(true);
                break;
            case CANCELAR:
                tipoOperacionFactor = OperacionFactor.NINGUNO;
                disableButtons();
                disableCamposFactor();
                
                btnAgregarFactor.setText("AGREGAR");
                btnEliminarFactor.setText("ELIMINAR");
                limpiarText();
                btnBuscarFactor.setDisable(true);
                break;
        }
    }
    
    public void accionFactor(String sql){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        PreparedStatement ps;
        ResultSet rs;
        Notifications noti = Notifications.create();
        ButtonType buttonTypeSi = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");
        
        switch(tipoOperacionFactor){
            case GUARDAR:
                 alert.setTitle("AGREGAR REGISTRO");
                alert.setHeaderText("AGREGAR REGISTRO");
                alert.setContentText("¿Está seguro que desea guardar este registro?");
                
                alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);
                
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == buttonTypeSi ){
                    try {
                        ps = Conexion.getIntance().getConexion().prepareCall(sql);
                        ps.execute();
                        
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("OPERACIÓN EXITOSA");
                        noti.text("SE HA AGREGADO EXITOSAMENTE EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionFactor = OperacionFactor.CANCELAR;
                        accionFactor();
                        cargarDatos();
                        
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL AGREGAR");
                        noti.text("HA OCURRIDO UN ERROR AL GUARDAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionFactor = OperacionFactor.CANCELAR;
                        accionFactor();
                    }
                }else{
                    
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA AGREGADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionFactor = OperacionFactor.CANCELAR;
                    accionFactor();
                }
                break;
            case ELIMINAR:
                 alert.setTitle("ELIMINAR REGISTRO");
                alert.setHeaderText("ELIMINAR REGISTRO");
                alert.setContentText("¿Está seguro que desea Eliminar este registro?");
               
                alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);
                
                Optional<ButtonType> resultEliminar = alert.showAndWait();
                
                if(resultEliminar.get() == buttonTypeSi){
                    try {
                        ps = Conexion.getIntance().getConexion().prepareCall(sql);
                        ps.execute();
                        
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("OPERACIÓN EXITOSA");
                        noti.text("SE HA ELIMINADO EXITOSAMENTE EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        cargarDatos();
                        tipoOperacionFactor = OperacionFactor.CANCELAR;
                        accionFactor();
                        
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        
                        
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ELIMINAR");
                        noti.text("HA OCURRIDO UN ERROR AL ELIMINAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionFactor = OperacionFactor.CANCELAR;
                        accionFactor();
                    }
                }else{
                     noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA ELIMINADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionFactor = OperacionFactor.CANCELAR;
                    accionFactor();
                }
                break;
            case ACTUALIZAR:
                  alert.setTitle("ACTUALIZAR REGISTRO");
                alert.setHeaderText("ACTUALIZAR REGISTRO");
                alert.setContentText("¿Está seguro que desea Actualizar este registro?");
               
                alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);
                
                Optional<ButtonType> resultactualizar = alert.showAndWait();
                if(resultactualizar.get() == buttonTypeSi ){
                    try {
                        ps = Conexion.getIntance().getConexion().prepareCall(sql);
                        ps.execute();
                        
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("OPERACIÓN EXITOSA");
                        noti.text("SE HA ACTUALIZADO EXITOSAMENTE EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionFactor = OperacionFactor.CANCELAR;
                        accionFactor();
                        cargarDatos();
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ACTUALIZAR");
                        noti.text("HA OCURRIDO UN ERROR AL ACTUALIZAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionFactor = OperacionFactor.CANCELAR;
                        accionFactor();
                    }
                }
                break;
            case BUSCAR:
                 try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    rs = ps.executeQuery();
                    int codigo=0;
                    while(rs.next()){
                        txtCodigoFactor.setText(rs.getString("factorVentaId"));
                        descFactor.setText(rs.getString("factorVentaDesc"));
                        descuentoFactor.setText(rs.getString("factorVentaDescuento"));
                        codigo=rs.getInt("factorVentaId");
                    }                    
                    if(rs.first()){
                        for(int i=0; i<tblFactor.getItems().size(); i++){
                            if(colCodigoFactor.getCellData(i) == codigo){
                                tblFactor.getSelectionModel().select(i);
                                break;
                            }
                        }
                        activateButtons();
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("OPERACIÓN EXITOSA");
                        noti.text("SU OPERACIÓN SE HA REALIZADO CON EXITO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        activarText();
                        tipoOperacionFactor = OperacionFactor.NINGUNO;
                        
                    }else{
                         
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL BUSCAR");
                        noti.text("NO SE HA ENCONTRADO EN LA BASE DE DATOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionFactor = OperacionFactor.CANCELAR;
                        accionFactor();
                    }
                }catch(SQLException ex){
                    ex.printStackTrace();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR AL BUSCAR");
                    noti.text("HA OCURRIDO UN ERROR EN LA BASE DE DATOS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionFactor = OperacionFactor.CANCELAR;
                    accionFactor();
                }
                break;
        }
        
    
    }
    


    @FXML
    private void btnAgregarFactor(MouseEvent event) {
         if(tipoOperacionFactor == OperacionFactor.GUARDAR){
            if(descFactor.getText().isEmpty() || descuentoFactor.getText().isEmpty()){
                 Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("HAY CAMPOS VACÍOS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
            }else{
                String sql="";
                if(descFactor.getText().length() > 150){
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("CAMPOS FUERA DE RANGO, DESCRIPCIÓN NO DEBE PASAR LOS 150 CARACTERES");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                }else{
                    FactorVenta factorVenta = new FactorVenta();
                    factorVenta.setFactorVentaDesc(descFactor.getText());
                    factorVenta.setFactorVentaDescuento(Double.parseDouble(descuentoFactor.getText()));
                    
                    sql = "{call Sp_AddFactorVenta('"+factorVenta.getFactorVentaDesc()+"','"+factorVenta.getFactorVentaDescuento()+"')}";                        
                    
                    
                    tipoOperacionFactor = OperacionFactor.GUARDAR;
                    accionFactor(sql);
                }
            }
        }else{
            tipoOperacionFactor = OperacionFactor.AGREGAR;
            accionFactor();
        }
    }

    @FXML
    private void btnEliminarFactor(MouseEvent event) {
        if(tipoOperacionFactor == OperacionFactor.GUARDAR){
            tipoOperacionFactor = OperacionFactor.CANCELAR;
            accionFactor();
        }else{
             
            String sql = "{call Sp_DeleteFactorVenta('"+codigo+"')}";
            tipoOperacionFactor = OperacionFactor.ELIMINAR;
            accionFactor(sql);
        }
    }

    
    @FXML
    private void btnEditarFactor(MouseEvent event) {
        if(descFactor.getText().isEmpty() || descuentoFactor.getText().isEmpty()){
            Notifications noti = Notifications.create();
               noti.graphic(new ImageView(imgError));
               noti.title("ERROR");
               noti.text("HAY CAMPOS VACÍOS");
               noti.position(Pos.BOTTOM_RIGHT);
               noti.hideAfter(Duration.seconds(4));
               noti.darkStyle();   
               noti.show();
       }else{
           if(descFactor.getText().length() > 25 || descuentoFactor.getText().length() > 9){
               Notifications noti = Notifications.create();
               noti.graphic(new ImageView(imgError));
               noti.title("ERROR");
               noti.text("CAMPOS FUERA DE RANGO, NOMBRE NO DEBE PASAR LOS 25 CARACTERES Y EL NIT NO DEBE PASAR LOS 9 CARACTERES");
               noti.position(Pos.BOTTOM_RIGHT);
               noti.hideAfter(Duration.seconds(4));
               noti.darkStyle();   
               noti.show();
           }else{
               FactorVenta factorVenta = new FactorVenta();
               factorVenta.setFactorVentaDesc(descFactor.getText());
               factorVenta.setFactorVentaDescuento(Double.parseDouble(descuentoFactor.getText()));
               String sql = "{call Sp_UpdateFactorVenta('"+codigo+"','"+factorVenta.getFactorVentaDesc()+"','"+factorVenta.getFactorVentaDescuento()+"')}";
               tipoOperacionFactor = OperacionFactor.ACTUALIZAR;
               accionFactor(sql);
           }
       }
    }
    
    
    @FXML
    private void comboFiltroFactor(ActionEvent event) {
        cmbBuscarFactor.setDisable(false);
        btnBuscarFactor.setDisable(false);
        ArrayList<String> lista = new ArrayList();
        String sql = "{call Sp_ListFactorVenta()}";
        if(cmbBuscarFactor.getValue().equals("CÓDIGO")){
            try{
                
                PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                ResultSet rs = ps.executeQuery();
                
                while(rs.next()){
                    lista.add(rs.getString("clienteId"));
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            if(cmbBuscarFactor.getValue().equals("DESCRIPCIÓN")){
                try{
                
                    PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    ResultSet rs = ps.executeQuery();

                    while(rs.next()){
                        lista.add(rs.getString("clienteNit"));
                    }
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
            
        }
        
        
        listaCombo = FXCollections.observableList(lista);
        cmbBuscarFactor.setItems(listaCombo);
        new AutoCompleteComboBoxListener(cmbBuscarFactor);
        
    }
    
    
    @FXML
    private void btnBuscarFactor(MouseEvent event) {
        String sql = "";
        tipoOperacionFactor = OperacionFactor.BUSCAR;
        if(cmbBuscarFactor.getValue().equals("")){
            Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE BUSQUEDA ESTA VACÍO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
        }else{
            if(cmbBuscarFactor.getValue().equals("CÓDIGO")){
                
                sql = "{call Sp_SearchFactorVenta('"+cmbBuscarFactor.getValue()+"')}";

            }else{
                sql = "{call Sp_SearchName('"+cmbBuscarFactor.getValue()+"')}";
            }
            
            accionFactor(sql);
        } 
    }

    
    
    @FXML
    private void cmbBuscarFactor(ActionEvent event) {
        String sql = "";
        tipoOperacionFactor = OperacionFactor.BUSCAR;
        if(cmbBuscarFactor.getValue().equals("")){
            Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE BUSQUEDA ESTA VACÍO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
        }else{
            if(cmbBuscarFactor.getValue().equals("CÓDIGO")){
                
                sql = "{call Sp_SearchFactorVenta('"+cmbBuscarFactor.getValue()+"')}";

            }else{
                sql = "{call Sp_SearchName('"+cmbBuscarFactor.getValue()+"')}";
            }
            
            accionFactor(sql);
        }
    }
    
    
    
    @FXML
    private void validarDescuento(KeyEvent event) {
        
    }
    
    
    //=============================================================================== CAMPOS ESPECIALES =========================================================
    
    
    
    
    
    
    @FXML
    private JFXTextField txtCodigoEspecial;
    @FXML
    private JFXTextField txtPrecioCampos;
    @FXML
    private JFXButton btnAgregarCampos;
    @FXML
    private JFXButton btnEliminarCampos;
    @FXML
    private JFXButton btnEditarCampos;
    @FXML
    private JFXTextField txtNombreCampo;
    @FXML
    private TableView<?> tblCampos;
    @FXML
    private TableColumn<?, ?> colCodigoCampos;
    @FXML
    private TableColumn<?, ?> colNombreCampos;
    @FXML
    private TableColumn<?, ?> colDescuentoCampos;
    @FXML
    private JFXButton btnBuscarCampos;
    @FXML
    private ComboBox<?> cmbFiltroCampos;
    @FXML
    private ComboBox<?> cmbBuscar;
    @FXML
    private JFXTextField txtCodigoProveedores;
    @FXML
    private JFXTextField txtNitProveedores;
    @FXML
    private JFXButton btnAgregar;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXTextField txtNitProveedores1;
    @FXML
    private TableView<?> tblProveedores;
    @FXML
    private TableColumn<?, ?> colCodigoProveedores;
    @FXML
    private TableColumn<?, ?> colNombreProveedores;
    @FXML
    private TableColumn<?, ?> colNitProveedores;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private ComboBox<?> cmbFiltroProveedores;

    
 

    @FXML
    private void comboFiltro(ActionEvent event) {
    }

    

    @FXML
    private void atajosProveedores(KeyEvent event) {
    }

    @FXML
    private void validarCodigoEspecial(KeyEvent event) {
    }

    @FXML
    private void validarTelefono(KeyEvent event) {
    }

    @FXML
    private void btnAgregar(MouseEvent event) {
    }

    @FXML
    private void btnEliminar(MouseEvent event) {
    }

    @FXML
    private void btnEditar(MouseEvent event) {
    }

    @FXML
    private void btnBuscarCampos(MouseEvent event) {
    }

    @FXML
    private void comboFiltroCampos(ActionEvent event) {
    }

    @FXML
    private void cmbBuscar(ActionEvent event) {
    }

    @FXML
    private void validarNumeroProveedores(KeyEvent event) {
    }

    @FXML
    private void btnBuscar(MouseEvent event) {
    }
    
    @FXML
    private void codigoBuscadoProveedores(MouseEvent event) {
    }

    @FXML
    private void seleccionarElementos(MouseEvent event) {
    }
    
}