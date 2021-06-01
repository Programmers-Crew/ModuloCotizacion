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
import org.ModuloCotizacion.bean.CamposEspeciales;
import org.ModuloCotizacion.bean.FactorVenta;
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
        lista.add(1,"DESCRIPCIÓN");
        listaFiltro = FXCollections.observableList(lista);
        cmbFiltroDesc.setItems(listaFiltro);
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
        txtCodigoFactor.setEditable(true);
        descFactor.setEditable(true);
        descuentoFactor.setEditable(true);
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
        colDescFactor.setCellValueFactory(new PropertyValueFactory("factorVentaDesc"));
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
                btnAgregarFactor.setDisable(false);
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
                btnAgregarFactor.setDisable(false);
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
    private void seleccionarElementosParametros(MouseEvent event) {
        int index = tblFactor.getSelectionModel().getSelectedIndex();
        try{
            txtCodigoFactor.setText(String.valueOf(colCodigoFactor.getCellData(index)));
            descFactor.setText(colDescFactor.getCellData(index));
            descuentoFactor.setText(String.valueOf(colDescuentoFactor.getCellData(index)));
            codigo = colCodigoFactor.getCellData(index);
            activateButtons();
            activarText();
        }catch(Exception e){
        
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
        if(cmbFiltroDesc.getValue().equals("CÓDIGO")){
            try{
                
                PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                ResultSet rs = ps.executeQuery();
                
                while(rs.next()){
                    lista.add(rs.getString("factorVentaId"));
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            if(cmbFiltroDesc.getValue().equals("DESCRIPCIÓN")){
                try{
                
                    PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    ResultSet rs = ps.executeQuery();

                    while(rs.next()){
                        lista.add(rs.getString("factorVentaDesc"));
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
            
            System.out.println(sql);
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
            if(cmbFiltroDesc.getValue().equals("CÓDIGO")){
                
                sql = "{call Sp_SearchFactorVenta('"+cmbBuscarFactor.getValue()+"')}";

            }else{
                sql = "{call Sp_SearchName('"+cmbBuscarFactor.getValue()+"')}";
            }
            
            accionFactor(sql);
        }
    }
    
    
    
    @FXML
    private ComboBox<?> cmbBuscar;
    @FXML
    private JFXTextField txtCodigoProveedores;
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
    private TableView<?> tblCamposEspecial;
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
                limpiarText();
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
                        for(int i=0; i<tblFactor.getItems().size(); i++){
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
        tblTipoCliente.setItems(getCamposEspeciales());
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
                        activateButtonsCamposEspeciales();
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
           if(txtDescripciónTipoCliente.getText().length() > 25 || txtDescuentoTipoCliente.getText().length() > 9){
               Notifications noti = Notifications.create();
               noti.graphic(new ImageView(imgError));
               noti.title("ERROR");
               noti.text("CAMPOS FUERA DE RANGO, NOMBRE NO DEBE PASAR LOS 25 CARACTERES Y EL NIT NO DEBE PASAR LOS 9 CARACTERES");
               noti.position(Pos.BOTTOM_RIGHT);
               noti.hideAfter(Duration.seconds(4));
               noti.darkStyle();   
               noti.show();
           }else{
               TipoCliente tipoCliente = new TipoCliente();
                tipoCliente.setTipoClienteDesc(txtDescripciónTipoCliente.getText());
                tipoCliente.setTipoClienteDescuento(Double.parseDouble(txtDescuentoTipoCliente.getText()));
               String sql = "{call Sp_UpdateTipoCliente('"+codigoEspecial+"','"+tipoCliente.getTipoClienteDesc()+"','"+tipoCliente.getTipoClienteDescuento()+"')}";
               tipoOperacionTipoCliente = OperacionTipoCliente.ACTUALIZAR;
               accionTipoCliente(sql);
           }
       }
    }

    @FXML
    private void btnBuscarTipoCliente(MouseEvent event) {
         String sql = "";
        tipoOperacionTipoCliente = OperacionTipoCliente.BUSCAR;
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
            if(cmbFiltroTipoCliente.getValue().equals("CÓDIGO")){
                
                sql = "{call Sp_SearchCamposEspeciales('"+cmbBuscarTipoCliente.getValue()+"')}";
                
            }else{
                
                sql = "{call Sp_SearchNameCamposEspeciales('"+cmbBuscarTipoCliente.getValue()+"')}";
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
                
                sql = "{call Sp_SearchCamposEspeciales('"+cmbBuscarEspecial.getValue()+"')}";
                
            }else{
                
                sql = "{call Sp_SearchNameCamposEspeciales('"+cmbBuscarEspecial.getValue()+"')}";
            }
            
            accionTipoCliente(sql);
        } 
    }

    @FXML
    private void tipoClienteInitialize(Event event) {
    }

}