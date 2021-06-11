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
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import org.ModuloCotizacion.bean.CamposEspeciales;
import org.ModuloCotizacion.bean.FactorVenta;
import org.ModuloCotizacion.bean.ModosPago;
import org.ModuloCotizacion.bean.TipoCliente;
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
    private Pane regresarpane;
    @FXML
    private AnchorPane anchor1;
    @FXML
    private AnchorPane anchor2;
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

    @FXML
    private void validarNumeroProveedores(KeyEvent event) {
    }

    @FXML
    private void validarTelefono(KeyEvent event) {
    }

    @FXML
    private void codigoBuscadoProveedores(MouseEvent event) {
    }

    @FXML
    private void atajosProveedores(KeyEvent event) {
    }

  
    
    public enum OperacionFactor{AGREGAR,GUARDAR,ELIMINAR,BUSCAR,ACTUALIZAR,CANCELAR,NINGUNO};
    public OperacionFactor tipoOperacionFactor= OperacionFactor.NINGUNO;
    int codigo=0;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<String> lista = new ArrayList();
        lista.add(0,"CÓDIGO");
        lista.add(1,"DESCRIPCIÓN");
        listaFiltro = FXCollections.observableList(lista);
        animacion.animacion(anchor1, anchor2);
    }    
    
    
    
    
   
    
    
  
    
    
    
    @FXML
    private void regresar(MouseEvent event) throws IOException {
      String menu = "org/ModuloCotizacion/view/menuPrincipal.fxml";
    cambioScene.Cambio(menu,(Stage) anchor.getScene().getWindow());
    }
    

    
   
    
    
    
    
    
    // ===================================================== CAMPOS ESPECIALES ==================================================================
    
    
    @FXML
    private JFXTextField txtCodigoEspecial;
    @FXML
    private JFXTextField txtPrecioEspecial;
    @FXML
    private JFXButton btnAgregarEspecial;
    @FXML
    private JFXButton btnEliminarEspecial;
    @FXML
    private JFXButton btnEditarEspecial;
    @FXML
    private JFXTextField txtNombreEspecial;
    @FXML
    private TableView<CamposEspeciales> tblCamposEspecial;
    @FXML
    private TableColumn<CamposEspeciales, Integer> colCodigoEspecial;
    @FXML
    private TableColumn<CamposEspeciales, String> colNombreEspecial;
    @FXML
    private TableColumn<CamposEspeciales, Double> colDescuentoEspecial;
    @FXML
    private JFXButton btnBuscarEspecial;
    @FXML
    private ComboBox<String> cmbFiltroEspecial;
    @FXML
    private ComboBox<String> cmbBuscarEspecial;
    
    int codigoEspecial=0;
    
    ObservableList<CamposEspeciales>listaCamposEspeciales;
    ObservableList<String>listaComboEspeciales;
    ObservableList<String>listaFiltroEspeciales;
    
    public enum OperacionCamposEspeciales{AGREGAR,GUARDAR,ELIMINAR,BUSCAR,ACTUALIZAR,CANCELAR,NINGUNO};
    public OperacionCamposEspeciales tipoOperacionCamposEspeciales = OperacionCamposEspeciales.NINGUNO;
    
    public void disableCamposEspeciales(){
        txtCodigoEspecial.setDisable(true);
        txtNombreEspecial.setDisable(true);
        txtPrecioEspecial.setDisable(true);
    }
    
    public void limpiarCamposEspeciales(){
        txtCodigoEspecial.setText("");
        txtNombreEspecial.setText("");
        txtPrecioEspecial.setText("");
    }
    
    public void disableButtonsCamposEspeciales(){
        btnAgregarEspecial.setDisable(true);
        btnEliminarEspecial.setDisable(true);
        btnEditarEspecial.setDisable(true);
    }
    
    public void activateButtonsCamposEspeciales(){
        btnAgregarEspecial.setDisable(false);
        btnEliminarEspecial.setDisable(false);
        btnEditarEspecial.setDisable(false);
    }
     
    public void activarTextCamposEspeciales(){
        txtCodigoEspecial.setDisable(false);
        txtNombreEspecial.setDisable(false);
        txtPrecioEspecial.setDisable(false);
        txtCodigoEspecial.setEditable(true);
        txtNombreEspecial.setEditable(true);
        txtPrecioEspecial.setEditable(true);
    }
   
    public ObservableList getCamposEspeciales(){
        ArrayList<CamposEspeciales> lista = new ArrayList();
        String sql = "{call Sp_ListCamposEspeciales()}";
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                lista.add(new CamposEspeciales(
                        rs.getInt("campoId"),
                        rs.getString("campoNombre"),
                        rs.getDouble("campoPrecio")
                
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
        
        return listaCamposEspeciales = FXCollections.observableList(lista);
        
    }
    
    
    public void cargarDatosCamposEspeciales(){
        tblCamposEspecial.setItems(getCamposEspeciales());
        colCodigoEspecial.setCellValueFactory(new PropertyValueFactory("campoId"));
        colNombreEspecial.setCellValueFactory(new PropertyValueFactory("campoNombre"));
        colDescuentoEspecial.setCellValueFactory(new PropertyValueFactory("campoPrecio"));
    }
    
    @FXML
    private void camposEspecialesInitialize(Event event) {
        cargarDatosCamposEspeciales();
        ArrayList<String> lista = new ArrayList();
        cmbFiltroEspecial.setValue("");
        lista.add(0,"CÓDIGO");
        lista.add(1,"NOMBRE");
        disableCamposEspeciales();
        listaFiltroEspeciales = FXCollections.observableList(lista);
        cmbFiltroEspecial.setItems(listaFiltroEspeciales);
        animacion.animacion(anchor1, anchor2);
    }
  
    public void accionCamposEspeciales(){
        switch(tipoOperacionCamposEspeciales){
            case AGREGAR:
                tipoOperacionCamposEspeciales = OperacionCamposEspeciales.GUARDAR;
                disableButtonsCamposEspeciales();
                btnAgregarEspecial.setText("GUARDAR");
                btnEliminarEspecial.setText("CANCELAR");
                btnEliminarEspecial.setDisable(false);
                activarTextCamposEspeciales();
                limpiarCamposEspeciales();
                btnAgregarEspecial.setDisable(false);
                btnBuscarEspecial.setDisable(true);
                break;
            case CANCELAR:
                tipoOperacionCamposEspeciales = OperacionCamposEspeciales.NINGUNO;
                disableButtonsCamposEspeciales();
                disableCamposEspeciales();
                
                btnAgregarEspecial.setText("AGREGAR");
                btnEliminarEspecial.setText("ELIMINAR");
                limpiarCamposEspeciales();
                btnBuscarEspecial.setDisable(true);
                btnAgregarEspecial.setDisable(false);
                break;
        }
    }
    
    public void accionCamposEspeciales(String sql){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        PreparedStatement ps;
        ResultSet rs;
        Notifications noti = Notifications.create();
        ButtonType buttonTypeSi = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");
        
        switch(tipoOperacionCamposEspeciales){
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
                        tipoOperacionCamposEspeciales = OperacionCamposEspeciales.CANCELAR;
                        accionCamposEspeciales();
                        cargarDatosCamposEspeciales();
                        
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL AGREGAR");
                        noti.text("HA OCURRIDO UN ERROR AL GUARDAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionCamposEspeciales = OperacionCamposEspeciales.CANCELAR;
                        accionCamposEspeciales();
                    }
                }else{
                    
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA AGREGADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionCamposEspeciales = OperacionCamposEspeciales.CANCELAR;
                    accionCamposEspeciales();
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
                        cargarDatosCamposEspeciales();
                        tipoOperacionCamposEspeciales = OperacionCamposEspeciales.CANCELAR;
                        accionCamposEspeciales();
                        
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        
                        
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ELIMINAR");
                        noti.text("HA OCURRIDO UN ERROR AL ELIMINAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionCamposEspeciales = OperacionCamposEspeciales.CANCELAR;
                        accionCamposEspeciales();
                    }
                }else{
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA ELIMINADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionCamposEspeciales = OperacionCamposEspeciales.CANCELAR;
                    accionCamposEspeciales();
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
                        tipoOperacionCamposEspeciales = OperacionCamposEspeciales.CANCELAR;
                        accionCamposEspeciales();
                        cargarDatosCamposEspeciales();
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ACTUALIZAR");
                        noti.text("HA OCURRIDO UN ERROR AL ACTUALIZAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionCamposEspeciales = OperacionCamposEspeciales.CANCELAR;
                        accionCamposEspeciales();
                    }
                }
                break;
            case BUSCAR:
                 try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    rs = ps.executeQuery();
                    while(rs.next()){
                        txtCodigoEspecial.setText(rs.getString("campoId"));
                        txtNombreEspecial.setText(rs.getString("campoNombre"));
                        txtPrecioEspecial.setText(rs.getString("campoPrecio"));
                        codigoEspecial = rs.getInt("campoId");
                    }                    
                    if(rs.first()){
                        for(int i=0; i<tblCamposEspecial.getItems().size(); i++){
                            if(colCodigoEspecial.getCellData(i) == codigoEspecial){
                                tblCamposEspecial.getSelectionModel().select(i);
                                break;
                            }
                        }
                        activateButtonsCamposEspeciales();
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("OPERACIÓN EXITOSA");
                        noti.text("SU OPERACIÓN SE HA REALIZADO CON EXITO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        activarTextCamposEspeciales();
                        tipoOperacionCamposEspeciales = OperacionCamposEspeciales.NINGUNO;
                        
                    }else{
                         
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL BUSCAR");
                        noti.text("NO SE HA ENCONTRADO EN LA BASE DE DATOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionCamposEspeciales = OperacionCamposEspeciales.CANCELAR;
                        accionCamposEspeciales();
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
                    tipoOperacionCamposEspeciales = OperacionCamposEspeciales.CANCELAR;
                    accionCamposEspeciales();
                }
                break;
        }
        
    
    }
    
    
    
    @FXML
    private void btnAgregarEspecial(MouseEvent event) {
         if(tipoOperacionCamposEspeciales == OperacionCamposEspeciales.GUARDAR){
            if(txtNombreEspecial.getText().isEmpty() || txtPrecioEspecial.getText().isEmpty()){
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
                if(txtNombreEspecial.getText().length() > 150){
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("CAMPOS FUERA DE RANGO, DESCRIPCIÓN NO DEBE PASAR LOS 150 CARACTERES");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                }else{
                    System.out.println(txtPrecioEspecial.getText()+"holaaaaaaaaaaaa");
                    CamposEspeciales camposEspeciales = new CamposEspeciales();
                    camposEspeciales.setCampoNombre(txtNombreEspecial.getText());
                    camposEspeciales.setCampoPrecio(Double.parseDouble(txtPrecioEspecial.getText()));
                    
                    sql = "{call Sp_AddCamposEspeciales('"+camposEspeciales.getCampoNombre()+"','"+camposEspeciales.getCampoPrecio()+"')}";                        
                    
                    
                    tipoOperacionFactor = OperacionFactor.GUARDAR;
                    accionCamposEspeciales(sql);
                }
            }
        }else{
            tipoOperacionCamposEspeciales = OperacionCamposEspeciales.AGREGAR;
            accionCamposEspeciales();
        }
    
    }
    
    
    
    @FXML
    private void btnEliminarEspecial(MouseEvent event) {
         if(tipoOperacionCamposEspeciales == OperacionCamposEspeciales.GUARDAR){
            tipoOperacionCamposEspeciales = OperacionCamposEspeciales.CANCELAR;
            accionCamposEspeciales();
        }else{
             
            String sql = "{call Sp_DeleteCamposEspeciales('"+codigoEspecial+"')}";
            tipoOperacionCamposEspeciales = OperacionCamposEspeciales.ELIMINAR;
            accionCamposEspeciales(sql);
        }
    }
    
    
    
    @FXML
    private void btnEditarEspecial(MouseEvent event) {
        if(txtNombreEspecial.getText().isEmpty() || txtPrecioEspecial.getText().isEmpty()){
            Notifications noti = Notifications.create();
               noti.graphic(new ImageView(imgError));
               noti.title("ERROR");
               noti.text("HAY CAMPOS VACÍOS");
               noti.position(Pos.BOTTOM_RIGHT);
               noti.hideAfter(Duration.seconds(4));
               noti.darkStyle();   
               noti.show();
       }else{
           if(txtNombreEspecial.getText().length() > 25 || txtPrecioEspecial.getText().length() > 9){
               Notifications noti = Notifications.create();
               noti.graphic(new ImageView(imgError));
               noti.title("ERROR");
               noti.text("CAMPOS FUERA DE RANGO, NOMBRE NO DEBE PASAR LOS 25 CARACTERES Y EL NIT NO DEBE PASAR LOS 9 CARACTERES");
               noti.position(Pos.BOTTOM_RIGHT);
               noti.hideAfter(Duration.seconds(4));
               noti.darkStyle();   
               noti.show();
           }else{
               CamposEspeciales camposEspeciales = new CamposEspeciales();
                camposEspeciales.setCampoNombre(txtNombreEspecial.getText());
                camposEspeciales.setCampoPrecio(Double.parseDouble(txtPrecioEspecial.getText()));
               String sql = "{call Sp_UpdateCamposEspeciales('"+codigoEspecial+"','"+camposEspeciales.getCampoNombre()+"','"+camposEspeciales.getCampoPrecio()+"')}";
               tipoOperacionCamposEspeciales = OperacionCamposEspeciales.ACTUALIZAR;
               accionCamposEspeciales(sql);
           }
       }
    }

    @FXML
    private void btnBuscarEspecial(MouseEvent event) {
        String sql = "";
        tipoOperacionCamposEspeciales = OperacionCamposEspeciales.BUSCAR;
        if(cmbFiltroEspecial.getValue().equals("")){
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR");
            noti.text("El CAMPO DE BUSQUEDA ESTA VACÍO");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
        }else{
            if(cmbFiltroEspecial.getValue().equals("CÓDIGO")){
                
                sql = "{call Sp_SearchCamposEspeciales('"+cmbBuscarEspecial.getValue()+"')}";
                
            }else{
                
                sql = "{call Sp_SearchNameCamposEspeciales('"+cmbBuscarEspecial.getValue()+"')}";
            }
            
            accionCamposEspeciales(sql);
        } 
    }
    
    
    
    @FXML
    private void comboFiltroEspecial(ActionEvent event) {
        cmbBuscarEspecial.setDisable(false);
        btnBuscarEspecial.setDisable(false);
        ArrayList<String> lista = new ArrayList();
        String sql = "{call Sp_ListCamposEspeciales()}";
        if(cmbFiltroEspecial.getValue().equals("CÓDIGO")){
            try{
                
                PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                ResultSet rs = ps.executeQuery();
                
                while(rs.next()){
                    
                    lista.add(rs.getString("campoId"));
                    
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            if(cmbFiltroEspecial.getValue().equals("NOMBRE")){
                try{
                
                    PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    ResultSet rs = ps.executeQuery();

                    while(rs.next()){
                        
                        lista.add(rs.getString("campoNombre"));
                        
                    }
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
            
        }
        
        
        listaComboEspeciales = FXCollections.observableList(lista);
        cmbBuscarEspecial.setItems(listaComboEspeciales);
        new AutoCompleteComboBoxListener(cmbFiltroEspecial);
    }
    
    
    
    @FXML
    private void cmbBuscarEspecial(ActionEvent event) {
         String sql = "";
        tipoOperacionCamposEspeciales = OperacionCamposEspeciales.BUSCAR;
        if(cmbFiltroEspecial.getValue().equals("")){
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR");
            noti.text("El CAMPO DE BUSQUEDA ESTA VACÍO");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
        }else{
            if(cmbFiltroEspecial.getValue().equals("CÓDIGO")){
                
                sql = "{call Sp_SearchCamposEspeciales('"+cmbBuscarEspecial.getValue()+"')}";
                
            }else{
                
                sql = "{call Sp_SearchNameCamposEspeciales('"+cmbBuscarEspecial.getValue()+"')}";
            }
            
            accionCamposEspeciales(sql);
            
        } 
    }

    
    
    
    @FXML
    private void seleccionarElementosCamposEspecial(MouseEvent event) {
        
        int index = tblCamposEspecial.getSelectionModel().getSelectedIndex();
        
        
        try{
            
            txtCodigoEspecial.setText(String.valueOf(colCodigoEspecial.getCellData(index)));
            txtNombreEspecial.setText(colNombreEspecial.getCellData(index));
            txtPrecioEspecial.setText(String.valueOf(colDescuentoEspecial.getCellData(index)));
            codigoEspecial  = colCodigoEspecial.getCellData(index);
            activateButtonsCamposEspeciales();
            activarTextCamposEspeciales();
            
        }catch(Exception e){
        
        }
    }

   // ===================================================== TIPO CLIENTE ======================================================================================
    
    @FXML
    private JFXTextField txtCodigoTipoCliente;
    @FXML
    private JFXTextField txtDescuentoTipoCliente;
    @FXML
    private JFXButton btnAgregarTipoCliente;
    @FXML
    private JFXButton btnEliminarTipoCliente;
    @FXML
    private JFXButton btnEditarTipoCliente;
    @FXML
    private JFXTextField txtDescripciónTipoCliente;
    @FXML
    private TableView<TipoCliente> tblTipoCliente;
    @FXML
    private TableColumn<TipoCliente, Integer> colCodigoTipoCliente;
    @FXML
    private TableColumn<TipoCliente, String> colDescripcionTipoCliente;
    @FXML
    private TableColumn<TipoCliente, Double> colDescuentoTipoCliente;
    @FXML
    private JFXButton btnBuscarTipoCliente;
    @FXML
    private ComboBox<String> cmbFiltroTipoCliente;
    @FXML
    private ComboBox<String> cmbBuscarTipoCliente;

 
    
    int codigoTipoCliente=0;
    
    ObservableList<TipoCliente>listaTipoCliente;
    ObservableList<String>listaComboTipoCliente;
    ObservableList<String>listaFiltroTipoCliente;
    
    public enum OperacionTipoCliente{AGREGAR,GUARDAR,ELIMINAR,BUSCAR,ACTUALIZAR,CANCELAR,NINGUNO};
    public OperacionTipoCliente tipoOperacionTipoCliente = OperacionTipoCliente.NINGUNO;
    
    
      public void disableTipoCliente(){
        txtCodigoTipoCliente.setDisable(true);
        txtDescripciónTipoCliente.setDisable(true);
        txtDescuentoTipoCliente.setDisable(true);
    }
    
    public void limpiarTipoCliente(){
        txtCodigoTipoCliente.setText("");
        txtDescripciónTipoCliente.setText("");
        txtDescuentoTipoCliente.setText("");
    }
    
    public void disableButtonsTipoCliente(){
        btnAgregarTipoCliente.setDisable(true);
        btnEliminarTipoCliente.setDisable(true);
        btnEditarTipoCliente.setDisable(true);
    }
    
    public void activateButtonsTipoCliente(){
        btnAgregarTipoCliente.setDisable(false);
        btnEliminarTipoCliente.setDisable(false);
        btnEditarTipoCliente.setDisable(false);
    }
     
    public void activarTextTipoCliente(){
        txtCodigoTipoCliente.setDisable(false);
        txtDescripciónTipoCliente.setDisable(false);
        txtDescuentoTipoCliente.setDisable(false);
        txtCodigoTipoCliente.setEditable(true);
        txtDescripciónTipoCliente.setEditable(true);
        txtDescuentoTipoCliente.setEditable(true);
    }
    
     
    public ObservableList getTipoCliente(){
        ArrayList<TipoCliente> lista = new ArrayList();
        String sql = "{call Sp_ListTipoCliente()}";
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                lista.add(new TipoCliente(
                        rs.getInt("tipoClienteId"),
                        rs.getString("tipoClienteDesc"),
                        rs.getDouble("tipoClienteDescuento")
                
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
        
        return listaTipoCliente = FXCollections.observableList(lista);
        
    }
    
    
    public void cargarDatosTipoCliente(){
        tblTipoCliente.setItems(getTipoCliente());
        colCodigoTipoCliente.setCellValueFactory(new PropertyValueFactory("tipoClienteId"));
        colDescripcionTipoCliente.setCellValueFactory(new PropertyValueFactory("tipoClienteDesc"));
        colDescuentoTipoCliente.setCellValueFactory(new PropertyValueFactory("tipoClienteDescuento"));
    }
    
    public void accionTipoCliente(){
        switch(tipoOperacionTipoCliente){
            case AGREGAR:
                tipoOperacionTipoCliente = OperacionTipoCliente.GUARDAR;
                disableButtonsTipoCliente();
                btnAgregarTipoCliente.setText("GUARDAR");
                btnEliminarTipoCliente.setText("CANCELAR");
                btnEliminarTipoCliente.setDisable(false);
                activarTextTipoCliente();
                limpiarTipoCliente();
                btnAgregarTipoCliente.setDisable(false);
                btnBuscarTipoCliente.setDisable(true);
                break;
            case CANCELAR:
                tipoOperacionTipoCliente = OperacionTipoCliente.NINGUNO;
                disableButtonsTipoCliente();
                disableTipoCliente();
                
                btnAgregarTipoCliente.setText("AGREGAR");
                btnEliminarTipoCliente.setText("ELIMINAR");
                limpiarTipoCliente();
                btnBuscarTipoCliente.setDisable(true);
                btnAgregarTipoCliente.setDisable(false);
                break;
        }
    }
    
    public void accionTipoCliente(String sql){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        PreparedStatement ps;
        ResultSet rs;
        Notifications noti = Notifications.create();
        ButtonType buttonTypeSi = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");
        
        switch(tipoOperacionTipoCliente){
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
                        tipoOperacionTipoCliente = OperacionTipoCliente.CANCELAR;
                        accionTipoCliente();
                        cargarDatosTipoCliente();
                        
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL AGREGAR");
                        noti.text("HA OCURRIDO UN ERROR AL GUARDAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionTipoCliente = OperacionTipoCliente.CANCELAR;
                        accionTipoCliente();
                    }
                }else{
                    
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA AGREGADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionTipoCliente = OperacionTipoCliente.CANCELAR;
                    accionTipoCliente();
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
                        cargarDatosTipoCliente();
                        tipoOperacionTipoCliente = OperacionTipoCliente.CANCELAR;
                        accionTipoCliente();
                        
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        
                        
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ELIMINAR");
                        noti.text("HA OCURRIDO UN ERROR AL ELIMINAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionTipoCliente = OperacionTipoCliente.CANCELAR;
                        accionTipoCliente();
                    }
                }else{
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA ELIMINADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionTipoCliente = OperacionTipoCliente.CANCELAR;
                    accionTipoCliente();
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
                        tipoOperacionTipoCliente = OperacionTipoCliente.CANCELAR;
                        accionTipoCliente();
                        cargarDatosTipoCliente();
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ACTUALIZAR");
                        noti.text("HA OCURRIDO UN ERROR AL ACTUALIZAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionTipoCliente = OperacionTipoCliente.CANCELAR;
                        accionTipoCliente();
                    }
                }
                break;
            case BUSCAR:
                 try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    rs = ps.executeQuery();
                    while(rs.next()){
                        txtCodigoTipoCliente.setText(rs.getString("tipoClienteId"));
                        txtDescripciónTipoCliente.setText(rs.getString("tipoClienteDesc"));
                        txtDescuentoTipoCliente.setText(rs.getString("tipoClienteDescuento"));
                        codigoTipoCliente = rs.getInt("tipoClienteId");
                    }                    
                    if(rs.first()){
                        for(int i=0; i<tblTipoCliente.getItems().size(); i++){
                            if(colCodigoTipoCliente.getCellData(i) == codigoTipoCliente){
                                tblTipoCliente.getSelectionModel().select(i);
                                break;
                            }
                        }
                        activateButtonsTipoCliente();
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("OPERACIÓN EXITOSA");
                        noti.text("SU OPERACIÓN SE HA REALIZADO CON EXITO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        activarTextTipoCliente();
                        tipoOperacionTipoCliente = OperacionTipoCliente.NINGUNO;
                        
                    }else{
                         
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL BUSCAR");
                        noti.text("NO SE HA ENCONTRADO EN LA BASE DE DATOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionTipoCliente = OperacionTipoCliente.CANCELAR;
                        accionTipoCliente();
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
                    tipoOperacionTipoCliente = OperacionTipoCliente.CANCELAR;
                    accionTipoCliente();
                }
                break;
        }
        
    
    }
    
    
    
    @FXML
    private void btnAgregarTipoCliente(MouseEvent event) {
         if(tipoOperacionTipoCliente == OperacionTipoCliente.GUARDAR){
            if(txtDescripciónTipoCliente.getText().isEmpty() || txtDescuentoTipoCliente.getText().isEmpty()){
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
                if(txtDescripciónTipoCliente.getText().length() > 150){
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("CAMPOS FUERA DE RANGO, DESCRIPCIÓN NO DEBE PASAR LOS 150 CARACTERES");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                }else{
                    TipoCliente tipoCliente = new TipoCliente();
                    tipoCliente.setTipoClienteDesc(txtDescripciónTipoCliente.getText());
                    tipoCliente.setTipoClienteDescuento(Double.parseDouble(txtDescuentoTipoCliente.getText()));
                    
                    sql = "{call Sp_AddTipoCliente('"+tipoCliente.getTipoClienteDesc()+"','"+tipoCliente.getTipoClienteDescuento()+"')}";                        
                    
                    
                    tipoOperacionTipoCliente = OperacionTipoCliente.GUARDAR;
                    accionTipoCliente(sql);
                }
            }
        }else{
            tipoOperacionTipoCliente = OperacionTipoCliente.AGREGAR;
            accionTipoCliente();
        }
    }

    @FXML
    private void btnEliminarTipoCliente(MouseEvent event) {
         if(tipoOperacionTipoCliente == tipoOperacionTipoCliente.GUARDAR){
            tipoOperacionTipoCliente = OperacionTipoCliente.CANCELAR;
            accionTipoCliente();
        }else{
             
            String sql = "{call Sp_DeleteTipoCliente('"+codigoTipoCliente+"')}";
            tipoOperacionTipoCliente = OperacionTipoCliente.ELIMINAR;
            accionTipoCliente(sql);
        }
    }

    @FXML
    private void btnEditarTipoCliente(MouseEvent event) {
        if(txtDescripciónTipoCliente.getText().isEmpty() || txtDescuentoTipoCliente.getText().isEmpty()){
            Notifications noti = Notifications.create();
               noti.graphic(new ImageView(imgError));
               noti.title("ERROR");
               noti.text("HAY CAMPOS VACÍOS");
               noti.position(Pos.BOTTOM_RIGHT);
               noti.hideAfter(Duration.seconds(4));
               noti.darkStyle();   
               noti.show();
       }else{
           if(txtDescripciónTipoCliente.getText().length() > 150){
               Notifications noti = Notifications.create();
               noti.graphic(new ImageView(imgError));
               noti.title("ERROR");
               noti.text("CAMPOS FUERA DE RANGO, DESCRIPCIÓN NO PUEDE PASAR LOS 150 CARACTERES");
               noti.position(Pos.BOTTOM_RIGHT);
               noti.hideAfter(Duration.seconds(4));
               noti.darkStyle();   
               noti.show();
           }else{
               TipoCliente tipoCliente = new TipoCliente();
                tipoCliente.setTipoClienteDesc(txtDescripciónTipoCliente.getText());
                tipoCliente.setTipoClienteDescuento(Double.parseDouble(txtDescuentoTipoCliente.getText()));
               String sql = "{call Sp_UpdateTipoCliente('"+codigoTipoCliente+"','"+tipoCliente.getTipoClienteDesc()+"','"+tipoCliente.getTipoClienteDescuento()+"')}";
               tipoOperacionTipoCliente = OperacionTipoCliente.ACTUALIZAR;
               accionTipoCliente(sql);
           }
       }
    }

    @FXML
    private void btnBuscarTipoCliente(MouseEvent event) {
         String sql = "";
        tipoOperacionTipoCliente = OperacionTipoCliente.BUSCAR;
        if(cmbFiltroTipoCliente.getValue().equals("")){
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR");
            noti.text("El CAMPO DE BUSQUEDA ESTA VACÍO");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
        }else{
            if(cmbFiltroTipoCliente.getValue().equals("CÓDIGO")){
                
                sql = "{call Sp_SearchTipoCliente('"+cmbBuscarTipoCliente.getValue()+"')}";
                
            }else{
                
                sql = "{call Sp_SearchTipoClienteName('"+cmbBuscarTipoCliente.getValue()+"')}";
            }
            
            accionTipoCliente(sql);
        } 
    }

    @FXML
    private void comboFiltroTipoCliente(ActionEvent event) {
        cmbBuscarTipoCliente.setDisable(false);
        btnBuscarTipoCliente.setDisable(false);
        ArrayList<String> lista = new ArrayList();
        String sql = "{call Sp_ListCamposEspeciales()}";
        if(cmbFiltroTipoCliente.getValue().equals("CÓDIGO")){
            try{
                
                PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                ResultSet rs = ps.executeQuery();
                
                while(rs.next()){
                    
                    lista.add(rs.getString("campoId"));
                    
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            if(cmbFiltroTipoCliente.getValue().equals("DESCRIPCIÓN")){
                try{
                
                    PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    ResultSet rs = ps.executeQuery();

                    while(rs.next()){
                        
                        lista.add(rs.getString("campoNombre"));
                        
                    }
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
            
        }
        
        
        listaComboTipoCliente = FXCollections.observableList(lista);
        cmbBuscarTipoCliente.setItems(listaComboTipoCliente);
        new AutoCompleteComboBoxListener(cmbBuscarTipoCliente);
    }

    @FXML
    private void cmbBuscarTipoCliente(ActionEvent event) {
         String sql = "";
        tipoOperacionTipoCliente = OperacionTipoCliente.BUSCAR;
        if(cmbFiltroTipoCliente.getValue().equals("")){
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR");
            noti.text("El CAMPO DE BUSQUEDA ESTA VACÍO");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
        }else{
            if(cmbFiltroTipoCliente.getValue().equals("CÓDIGO")){
                
                sql = "{call Sp_SearchTipoCliente('"+cmbBuscarTipoCliente.getValue()+"')}";
                
            }else{
                
                sql = "{call Sp_SearchTipoClienteName('"+cmbBuscarTipoCliente.getValue()+"')}";
            }
            
            accionTipoCliente(sql);
        } 
    }

    @FXML
    private void tipoClienteInitialize(Event event) {
        cargarDatosTipoCliente();
        ArrayList<String> lista = new ArrayList();
        cmbFiltroTipoCliente.setValue("");
        lista.add(0,"CÓDIGO");
        lista.add(1,"DESCRIPCIÓN");
        disableTipoCliente();
        listaFiltroTipoCliente = FXCollections.observableList(lista);
        cmbFiltroTipoCliente.setItems(listaFiltroTipoCliente);
        animacion.animacion(anchor1, anchor2);
    }
    
    
    @FXML
    private void seleccionarElementosTipoCliente(MouseEvent event) {
        int index = tblTipoCliente.getSelectionModel().getSelectedIndex();
        try{
            txtCodigoTipoCliente.setText(String.valueOf(colCodigoTipoCliente.getCellData(index)));
            txtDescripciónTipoCliente.setText(colDescripcionTipoCliente.getCellData(index));
            txtDescuentoTipoCliente.setText(String.valueOf(colDescuentoTipoCliente.getCellData(index)));
            codigoTipoCliente = colCodigoTipoCliente.getCellData(index);
            activateButtonsTipoCliente();
            activarTextTipoCliente();
        }catch(Exception e){
        
        }
    }
   
    
    
    
    
    // ================================================= MODOS DE PAGO =================================================================
    
     @FXML
    private JFXTextField txtCodigoModosPagos;
    @FXML
    private JFXButton btnAgregarModosPago;
    @FXML
    private JFXButton btnEliminarModosPago;
    @FXML
    private JFXButton btnEditarModosPago;
    @FXML
    private JFXTextField txtDescModosPago;
    @FXML
    private JFXButton btnBuscarModosPago;
    @FXML
    private ComboBox<String> cmbFiltroModosPagos;
    @FXML
    private TableView<ModosPago> tblModosPago;
    @FXML
    private TableColumn<ModosPago, Integer> colCodigoModosPago;
    @FXML
    private TableColumn<ModosPago, String> colDescModoPago;
    @FXML
    private ComboBox<String> cmbBuscarModosPago;
    
    
     int codigoModoPago=0;
    
    ObservableList<ModosPago>listaModosPago;
    ObservableList<String>listaComboModosPago;
    ObservableList<String>listaFiltroModosPago;
    
    public enum OperacionModoPago{AGREGAR,GUARDAR,ELIMINAR,BUSCAR,ACTUALIZAR,CANCELAR,NINGUNO};
    public OperacionModoPago tipoOperacionModoPago = OperacionModoPago.NINGUNO;
    
    
    public void disableModoPago(){
        txtCodigoModosPagos.setDisable(true);
        txtDescModosPago.setDisable(true);
    }
    
    public void limpiarModoPago(){
        txtCodigoModosPagos.setText("");
        txtDescModosPago.setText("");
    }
    
    public void disableButtonsModoPago(){
        btnAgregarModosPago.setDisable(true);
        btnEliminarModosPago.setDisable(true);
        btnEditarModosPago.setDisable(true);
    }
    
    public void activateButtonsModoPago(){
        btnAgregarModosPago.setDisable(false);
        btnEliminarModosPago.setDisable(false);
        btnEditarModosPago.setDisable(false);
    }
     
    public void activarTextModoPago(){
        txtCodigoModosPagos.setDisable(false);
        txtDescModosPago.setDisable(false);
        txtCodigoModosPagos.setEditable(true);
        txtDescModosPago.setEditable(true);
    }
    
     
    public ObservableList getModoPago(){
        ArrayList<ModosPago> lista = new ArrayList();
        String sql = "{call Sp_ListModoPago()}";
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                lista.add(new ModosPago(
                        rs.getInt("modoPagoId"),
                        rs.getString("modoPagoDesc")
                
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
        
        return listaModosPago = FXCollections.observableList(lista);
        
    }
    
    
    public void cargarDatosModoPago(){
        tblModosPago.setItems(getModoPago());
        colCodigoModosPago.setCellValueFactory(new PropertyValueFactory("modoPagoId"));
        colDescModoPago.setCellValueFactory(new PropertyValueFactory("modoPagoDesc"));
    }
    
    public void accionModoPago(){
        switch(tipoOperacionModoPago){
            case AGREGAR:
                tipoOperacionModoPago = OperacionModoPago.GUARDAR;
                disableButtonsModoPago();
                btnAgregarModosPago.setText("GUARDAR");
                btnEliminarModosPago.setText("CANCELAR");
                btnEliminarModosPago.setDisable(false);
                activarTextModoPago();
                limpiarModoPago();
                btnAgregarModosPago.setDisable(false);
                btnBuscarModosPago.setDisable(true);
                break;
            case CANCELAR:
                tipoOperacionModoPago = OperacionModoPago.NINGUNO;
                disableButtonsModoPago();
                disableModoPago();
                
                btnAgregarModosPago.setText("AGREGAR");
                btnEliminarModosPago.setText("ELIMINAR");
                limpiarModoPago();
                btnBuscarModosPago.setDisable(true);
                btnAgregarModosPago.setDisable(false);
                break;
        }
    }
    
    public void accionModoPago(String sql){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        PreparedStatement ps;
        ResultSet rs;
        Notifications noti = Notifications.create();
        ButtonType buttonTypeSi = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");
        
        switch(tipoOperacionModoPago){
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
                        tipoOperacionModoPago = OperacionModoPago.CANCELAR;
                        accionModoPago();
                        cargarDatosModoPago();
                        
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL AGREGAR");
                        noti.text("HA OCURRIDO UN ERROR AL GUARDAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionModoPago = OperacionModoPago.CANCELAR;
                        accionModoPago();
                    }
                }else{
                    
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA AGREGADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionModoPago = OperacionModoPago.CANCELAR;
                    accionModoPago();
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
                        cargarDatosModoPago();
                        tipoOperacionModoPago = OperacionModoPago.CANCELAR;
                        accionModoPago();
                        
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        
                        
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ELIMINAR");
                        noti.text("HA OCURRIDO UN ERROR AL ELIMINAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionModoPago = OperacionModoPago.CANCELAR;
                        accionModoPago();
                    }
                }else{
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA ELIMINADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionModoPago = OperacionModoPago.CANCELAR;
                    accionModoPago();
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
                        tipoOperacionModoPago = OperacionModoPago.CANCELAR;
                        accionModoPago();
                        cargarDatosModoPago();
                        
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ACTUALIZAR");
                        noti.text("HA OCURRIDO UN ERROR AL ACTUALIZAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionModoPago = OperacionModoPago.CANCELAR;
                        accionModoPago();
                    }
                }
                break;
            case BUSCAR:
                 try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    rs = ps.executeQuery();
                    while(rs.next()){
                        txtCodigoModosPagos.setText(rs.getString("modoPagoId"));
                        txtDescModosPago.setText(rs.getString("modoPagoDesc"));
                        codigoModoPago = rs.getInt("modoPagoId");
                    }                    
                    if(rs.first()){
                        for(int i=0; i<tblModosPago.getItems().size(); i++){
                            if(colCodigoModosPago.getCellData(i) == codigoModoPago){
                                tblModosPago.getSelectionModel().select(i);
                                break;
                            }
                        }
                        activateButtonsModoPago();
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("OPERACIÓN EXITOSA");
                        noti.text("SU OPERACIÓN SE HA REALIZADO CON EXITO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        activarTextModoPago();
                        tipoOperacionModoPago = OperacionModoPago.NINGUNO;
                        
                    }else{
                         
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL BUSCAR");
                        noti.text("NO SE HA ENCONTRADO EN LA BASE DE DATOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionModoPago = OperacionModoPago.CANCELAR;
                        accionModoPago();
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
                    tipoOperacionModoPago = OperacionModoPago.CANCELAR;
                    accionModoPago();
                }
                break;
        }
        
    
    }
    
    
    
    
    
    @FXML
    private void btnAgregarModosPago(MouseEvent event) {
        if(tipoOperacionModoPago == OperacionModoPago.GUARDAR){
            if(txtDescModosPago.getText().isEmpty()){
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
                if(txtDescModosPago.getText().length() > 150){
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("CAMPOS FUERA DE RANGO, DESCRIPCIÓN NO DEBE PASAR LOS 150 CARACTERES");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                }else{
                    ModosPago modoPago = new ModosPago();
                    modoPago.setModoPagoDesc(txtDescModosPago.getText());
                    
                    sql = "{call Sp_AddModoPago('"+modoPago.getModoPagoDesc()+"')}";                        
                    
                    
                    tipoOperacionModoPago = OperacionModoPago.GUARDAR;
                    accionModoPago(sql);
                }
            }
        }else{
            tipoOperacionModoPago = OperacionModoPago.AGREGAR;
            accionModoPago();
        }
    }

    @FXML
    private void btnEliminarModosPago(MouseEvent event) {
         if(tipoOperacionModoPago == tipoOperacionModoPago.GUARDAR){
            tipoOperacionModoPago = OperacionModoPago.CANCELAR;
            accionModoPago();
        }else{
             
            String sql = "{call Sp_DeleteModoPago('"+codigoModoPago+"')}";
            tipoOperacionModoPago = OperacionModoPago.ELIMINAR;
            accionModoPago(sql);
        }
    }

    @FXML
    private void btnEditarModosPago(MouseEvent event) {
        if(txtDescModosPago.getText().isEmpty()){
            Notifications noti = Notifications.create();
               noti.graphic(new ImageView(imgError));
               noti.title("ERROR");
               noti.text("HAY CAMPOS VACÍOS");
               noti.position(Pos.BOTTOM_RIGHT);
               noti.hideAfter(Duration.seconds(4));
               noti.darkStyle();   
               noti.show();
       }else{
           if(txtDescModosPago.getText().length() > 150){
               Notifications noti = Notifications.create();
               noti.graphic(new ImageView(imgError));
               noti.title("ERROR");
               noti.text("CAMPOS FUERA DE RANGO, DESCRIPCIÓN NO PUEDE PASAR LOS 150 CARACTERES");
               noti.position(Pos.BOTTOM_RIGHT);
               noti.hideAfter(Duration.seconds(4));
               noti.darkStyle();   
               noti.show();
           }else{
               ModosPago modoPago = new ModosPago();
               modoPago.setModoPagoDesc(txtDescModosPago.getText());
                
               String sql = "{call Sp_UpdateModoPago('"+codigoModoPago+"','"+modoPago.getModoPagoDesc()+"')}";
               tipoOperacionModoPago = OperacionModoPago.ACTUALIZAR;
               accionModoPago(sql);
           }
       }
    }

    @FXML
    private void btnBuscarModosPago(MouseEvent event) {
            String sql = "";
        tipoOperacionModoPago = OperacionModoPago.BUSCAR;
        if(cmbFiltroModosPagos.getValue().equals("")){
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR");
            noti.text("El CAMPO DE BUSQUEDA ESTA VACÍO");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
        }else{
            if(cmbFiltroModosPagos.getValue().equals("CÓDIGO")){
                
                sql = "{call Sp_SearchModoPago('"+cmbBuscarTipoCliente.getValue()+"')}";
                
            }else{
                
                sql = "{call Sp_SearchModoPagoName('"+cmbBuscarTipoCliente.getValue()+"')}";
            }
            
            accionModoPago(sql);
        } 
    }

    
    @FXML
    private void comboFiltroModoPago(ActionEvent event) {
        cmbBuscarModosPago.setDisable(false);
        btnBuscarModosPago.setDisable(false);
        ArrayList<String> lista = new ArrayList();
        String sql = "{call Sp_ListModoPago()}";
        if(cmbFiltroModosPagos.getValue().equals("CÓDIGO")){
            try{
                
                PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                ResultSet rs = ps.executeQuery();
                
                while(rs.next()){
                    
                    lista.add(rs.getString("modoPagoId"));
                    
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            if(cmbFiltroModosPagos.getValue().equals("DESCRIPCIÓN")){
                try{
                
                    PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    ResultSet rs = ps.executeQuery();

                    while(rs.next()){
                        
                        lista.add(rs.getString("modoPagoDesc"));
                        
                    }
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void seleccionarElementosModos(MouseEvent event) {
        int index = tblModosPago.getSelectionModel().getSelectedIndex();
        try{
            txtCodigoModosPagos.setText(String.valueOf(colCodigoModosPago.getCellData(index)));
            txtDescModosPago.setText(colDescModoPago.getCellData(index));
            codigoModoPago = colCodigoModosPago.getCellData(index);
            activateButtonsModoPago();
            activarTextModoPago();
        }catch(Exception e){
        
        }
    }

    @FXML
    private void cmbBuscarModoPago(ActionEvent event) {
            String sql = "";
        tipoOperacionModoPago = OperacionModoPago.BUSCAR;
        if(cmbFiltroModosPagos.getValue().equals("")){
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR");
            noti.text("El CAMPO DE BUSQUEDA ESTA VACÍO");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
        }else{
            if(cmbFiltroModosPagos.getValue().equals("CÓDIGO")){
                
                sql = "{call Sp_SearchModoPago('"+cmbBuscarTipoCliente.getValue()+"')}";
                
            }else{
                
                sql = "{call Sp_SearchModoPagoName('"+cmbBuscarTipoCliente.getValue()+"')}";
            }
            
            accionModoPago(sql);
        } 
    }
    
      @FXML
    private void ModosPagoInitialize(Event event) {
         cargarDatosModoPago();
        ArrayList<String> lista = new ArrayList();
        cmbFiltroModosPagos.setValue("");
        lista.add(0,"CÓDIGO");
        lista.add(1,"DESCRIPCIÓN");
        disableTipoCliente();
        listaFiltroModosPago = FXCollections.observableList(lista);
        cmbFiltroModosPagos.setItems(listaFiltroModosPago);
        animacion.animacion(anchor1, anchor2);
    }

  
    
}