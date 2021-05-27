package org.moduloFacturacion.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.moduloFacturacion.bean.Animations;
import org.moduloFacturacion.bean.AutoCompleteComboBoxListener;
import org.moduloFacturacion.bean.CambioScene;
import org.moduloFacturacion.bean.Cardex;
import org.moduloFacturacion.bean.Creditos;
import org.moduloFacturacion.bean.EstadoProductos;
import org.moduloFacturacion.bean.GenerarExcel;
import org.moduloFacturacion.bean.GenerarExcelCardex;
import org.moduloFacturacion.bean.InventarioProductos;
import org.moduloFacturacion.bean.ValidarStyle;
import org.moduloFacturacion.db.Conexion;


public class InventarioViewController implements Initializable {
    
    CambioScene cambioScene = new CambioScene();
    Image imgError = new Image("org/moduloFacturacion/img/error.png");
    Image imgCorrecto= new Image("org/moduloFacturacion/img/correcto.png");
    @FXML
    private Pane btnProveedores;
    @FXML
    private Pane btnProductos;
    @FXML
    private Pane btnInicio;
    
    MenuPrincipalContoller menu = new MenuPrincipalContoller();
    ValidarStyle validar = new ValidarStyle();
    @FXML
    private AnchorPane anchor1;
    @FXML
    private AnchorPane anchor2;
    @FXML
    private AnchorPane anchor3;
    @FXML
    private AnchorPane anchor4;
    
    Animations animacion = new Animations();
    @FXML
    private JFXButton btnAgregarInventario1;
    @FXML
    private JFXButton btnBuscarInventario1;
    @FXML

    private JFXTextField noFactura;
    @FXML
    private JFXButton btnCargarDatos;
    @FXML
    private JFXTextField txtCostoNuevo;
    @FXML
    private JFXButton generarExcel;
    @FXML
    private JFXButton btnCargarCardex;
    @FXML
    private JFXButton generar;



    public enum Operacion{AGREGAR,GUARDAR,ELIMINAR,BUSCAR,ACTUALIZAR,CANCELAR,NINGUNO, SUMAR, RESTAR};
    public Operacion cancelar = Operacion.NINGUNO;
    
    // Variables para Inventario
    public Operacion tipoOperacionInventario= Operacion.NINGUNO;

    ObservableList<InventarioProductos> listaInventarioProductos;
    ObservableList<String> listaEstadoInventario;
    ObservableList<String> listaCodigoProducto;
    ObservableList<String> listaCodigoFiltro;
    ObservableList<String> listaFiltro;
    ObservableList<String> listaBuscar;

    int buscarCodigoEstado = 0;
    String codigoProducto = "";

    //Variables para Estado
    public Operacion tipoOperacionEstado= Operacion.NINGUNO; 
   
    ObservableList<EstadoProductos> listaEstadoProductos;
    ObservableList<String> listaCodigoEstadoProductos;
    String codigoEstado = "";
    
    //Propiedades Inventario
    @FXML
    private AnchorPane anchor;
    @FXML
    private JFXButton btnAgregarInventario;
    @FXML
    private JFXButton btnEditarInventario;
    @FXML
    private JFXButton btnEliminarInventario;
    @FXML
    private ComboBox<String> cmbNombreEstado;
    @FXML
    private JFXTextField txtCantidadInventario;
    @FXML
    private JFXTextField txtProveedorInventario;
    @FXML
    private JFXTextField txtProductoInventario;
    @FXML
    private ComboBox<String> cmbCodigoProductoInventario;
    @FXML
    private TableView<InventarioProductos> tblInventario;
    @FXML
    private TableColumn<InventarioProductos, String> colCodigoProductoInventario;
    @FXML
    private TableColumn<InventarioProductos, Double> colCantidadInventario;
    @FXML
    private TableColumn<InventarioProductos, String> colProveedorInventario;
    @FXML
    private TableColumn<InventarioProductos, String> colProductoInventario;
    @FXML
    private TableColumn<InventarioProductos, String> colEstadoInventario;
    @FXML
    private TableColumn<InventarioProductos, Double> colPrecioInventario;
    @FXML
    private JFXButton btnRestarInventario;
    @FXML
    private TableColumn<InventarioProductos, String> colTipoProducto;
    @FXML
    private JFXButton btnBuscarInventario;
    @FXML
    private ComboBox<String> cmbFiltroCodigo;
    @FXML
    private ComboBox<String> cmbBuscar;

    //Propiedades Estado
    @FXML
    private JFXTextField txtCodigoEstadoProducto;
    @FXML
    private JFXTextField txtDescEstadoProducto;
    @FXML
    private JFXButton btnAgregarEstadoProductos;
    @FXML
    private JFXButton btnEditarEstadoProductos;
    @FXML
    private JFXButton btnEliminarEstadoProductos;
    @FXML
    private TableView<EstadoProductos> tblEstadoProductos;
    @FXML
    private TableColumn<EstadoProductos, String> colCodigoEstadoCodigo;
    @FXML
    private TableColumn<EstadoProductos, String> colDescEstadoProductos;
    @FXML
    private JFXButton btnBuscarEstadoProductos;
    @FXML
    private ComboBox<String> cmbCodigoEstadoProductos;
    

    
    double costoProducto;
    String proveedorId;

    String proveedorName = "";
    String prodProveedor = "";
    String prodProducto = "";

    //========================================== CODIGO PARA VISTA INVENTARIO =============================================================
        
    public void limpiarText(){
        txtProveedorInventario.setText("");
        cmbNombreEstado.setValue("");

    }
    
    
    public void limpiarTextFunciones(){
    txtCostoNuevo.setText("");
    txtCantidadInventario.setText("");
    }
    
    public void desactivarControlesInventario(){    
        btnEditarInventario.setDisable(false);
        btnEliminarInventario.setDisable(false);
    }
    
    public void activarControles(){    
        btnEditarInventario.setDisable(false);
        btnEliminarInventario.setDisable(false);
    }
    
        public void desactivarTextInventario(){
        cmbCodigoProductoInventario.setDisable(true);
        txtProveedorInventario.setEditable(false);
        txtProductoInventario.setEditable(false);
        cmbNombreEstado.setDisable(true);
                btnEditarInventario.setDisable(false);
        btnEliminarInventario.setDisable(false);

    }
    
    public void activarTextInventario(){
        cmbCodigoProductoInventario.setDisable(false);
        txtCantidadInventario.setEditable(true);
        cmbNombreEstado.setDisable(false);
    }
    
    
    @FXML
    private void cargarProductos(Event event) {
        iniciarInventario();
        llenarComboProducto();
        activarControles();
        activarTextInventario();
        animacion.animacion(anchor1, anchor2);
    }
    
    
     public ObservableList<InventarioProductos> getInventario(){
        ArrayList<InventarioProductos> lista = new ArrayList();
        ArrayList<String> comboCodigoFiltro = new ArrayList();
        String sql = "{call SpListarInventarioProductos()}";
        int x=0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new InventarioProductos(
                            rs.getString("productoId"),
                            rs.getDouble("inventarioProductoCant"),
                            rs.getString("proveedorNombre"),
                            rs.getString("productoDesc"),
                            rs.getString("estadoProductoDesc"),
                            rs.getDouble("precioCosto"),
                            rs.getString("tipoProdDesc")                        
                ));
                prodProducto = rs.getString("productoId");
                comboCodigoFiltro.add(x, rs.getString("productoId"));
                x++;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        listaCodigoFiltro = FXCollections.observableList(comboCodigoFiltro);
        
        return listaInventarioProductos = FXCollections.observableList(lista);
    }
     
     public void llenarComboProducto(){
        ArrayList<String> lista = new ArrayList();
        String sql= "{call SpListarProductos()}";
            int x =0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x, rs.getString("productoId"));
                x++;
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
            
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR AL CARGAR DATOS CMB");
            noti.text("Error al cargar la base de datos");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
        }
        listaCodigoProducto = FXCollections.observableList(lista);
        cmbCodigoProductoInventario.setItems(listaCodigoProducto);
        
    }
     
     
     public void cargarDatos(){
        tblInventario.setItems(getInventario());
        activarControles();
        activarTextInventario();
        colCodigoProductoInventario.setCellValueFactory(new PropertyValueFactory("productoId"));
        colCantidadInventario.setCellValueFactory(new PropertyValueFactory("inventarioProductoCant"));
        colProductoInventario.setCellValueFactory(new PropertyValueFactory("productoDesc"));
        colProveedorInventario.setCellValueFactory(new PropertyValueFactory("proveedorNombre"));
        colEstadoInventario.setCellValueFactory(new PropertyValueFactory("estadoProductoDesc"));
        colEstadoInventario.setCellValueFactory(new PropertyValueFactory("estadoProductoDesc"));
        colPrecioInventario.setCellValueFactory(new PropertyValueFactory("precioCosto"));
        colTipoProducto.setCellValueFactory(new PropertyValueFactory("tipoProdDesc"));
        limpiarText();
       
        llenarComboEstado();
        cmbNombreEstado.setValue("");
        new AutoCompleteComboBoxListener(cmbFiltroCodigo);
        new AutoCompleteComboBoxListener(cmbNombreEstado);
        activarControles();
        activarTextInventario();
    }
    
    @FXML
    private void generarExcel(ActionEvent event) throws IOException {
           Stage s = (Stage) anchor.getScene().getWindow();
           s.toBack();
           GenerarExcel gE = new GenerarExcel();
           try{
           gE.generar(listaInventarioProductos);
           }catch(Exception e){
               e.printStackTrace();
           }
           s.toFront();
    }

    public ObservableList<InventarioProductos> getInventarioProveedor(){
        ArrayList<InventarioProductos> lista = new ArrayList();
        ArrayList<String> comboCodigoFiltro = new ArrayList();
            String sql = "{call SpListarInventarioProveedores('"+proveedorName+"')}";
        int x=0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new InventarioProductos(
                            rs.getString("productoId"),
                            rs.getDouble("inventarioProductoCant"),
                            rs.getString("proveedorNombre"),
                            rs.getString("productoDesc"),
                            rs.getString("estadoProductoDesc"),
                            rs.getDouble("precioCosto"),
                            rs.getString("tipoProdDesc")
                ));
                x++;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
                
        return listaInventarioProductos = FXCollections.observableList(lista);
    }
     
    
         public void cargarDatosProveedor(){
        tblInventario.setItems(getInventarioProveedor());
        activarControles();
        activarTextInventario();
        colCodigoProductoInventario.setCellValueFactory(new PropertyValueFactory("productoId"));
        colCantidadInventario.setCellValueFactory(new PropertyValueFactory("inventarioProductoCant"));
        colProductoInventario.setCellValueFactory(new PropertyValueFactory("productoDesc"));
        colProveedorInventario.setCellValueFactory(new PropertyValueFactory("proveedorNombre"));
        colEstadoInventario.setCellValueFactory(new PropertyValueFactory("estadoProductoDesc"));
        colEstadoInventario.setCellValueFactory(new PropertyValueFactory("estadoProductoDesc"));
        colPrecioInventario.setCellValueFactory(new PropertyValueFactory("precioCosto"));
        colTipoProducto.setCellValueFactory(new PropertyValueFactory("tipoProdDesc"));
        limpiarText();
       
        llenarComboEstado();
        cmbNombreEstado.setValue("");
        new AutoCompleteComboBoxListener(cmbFiltroCodigo);
        new AutoCompleteComboBoxListener(cmbNombreEstado);
        activarControles();
        activarTextInventario();
    }
    Double costoAntiguo = 0.00;
         
    @FXML
    private void seleccionarElementosProductos(MouseEvent event) {
        int index = tblInventario.getSelectionModel().getSelectedIndex();
        try{
            
            cmbCodigoProductoInventario.setValue(colCodigoProductoInventario.getCellData(index));
            txtCantidadInventario.setText(colCantidadInventario.getCellData(index).toString());
            txtProveedorInventario.setText(colProveedorInventario.getCellData(index));
            txtProductoInventario.setText(colProductoInventario.getCellData(index));
            cmbNombreEstado.setValue(colEstadoInventario.getCellData(index));
            txtCostoNuevo.setText(colPrecioInventario.getCellData(index).toString());

            codigoProducto = colCodigoProductoInventario.getCellData(index);
            cmbNombreEstado.setDisable(false);
            verificarProducto();
            activarControles();
            activarTextInventario();
            
        }catch(Exception ex){
             ex.printStackTrace();
        }
    }
    
    public void iniciarInventario(){
       animacion.animacion(anchor1, anchor2);
        Tooltip toolInicio = new Tooltip("Volver a Inicio");
        Tooltip.install(btnInicio, toolInicio);
        
        Tooltip toolProveedores = new Tooltip("Abrir Proveedores");
        Tooltip.install(btnProveedores, toolProveedores);
        
        Tooltip toolProductos = new Tooltip("Abrir Proveedores");
        Tooltip.install(btnProveedores, toolProductos);
        
        cargarDatos();
        
        tipoOperacionInventario = Operacion.CANCELAR;
        accionInventario();
    }
        
    public void llenarComboEstado(){
        ArrayList<String> lista = new ArrayList();
        String sql= "{call SpListarEstadoProductos()}";
            int x =0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x, rs.getString("estadoProductoDesc"));
                x++;
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
            
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR AL CARGAR DATOS CMB");
            noti.text("Error al cargar la base de datos");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
        }
        listaEstadoInventario = FXCollections.observableList(lista);
        cmbNombreEstado.setItems(listaEstadoInventario);
    }
    
    
     public void accionInventario(){
        switch(tipoOperacionInventario){
            case AGREGAR:
                tipoOperacionInventario = Operacion.GUARDAR;
                cmbFiltroCodigo.setDisable(true);
                cancelar = Operacion.CANCELAR;
                desactivarControlesInventario();
                btnAgregarInventario.setText("GUARDAR");
                btnEliminarInventario.setText("CANCELAR");
                btnBuscarInventario.setDisable(true);
                btnEliminarInventario.setDisable(false);

                System.out.println(proveedorName);
                activarTextInventario();
                limpiarText();
                break;
            case CANCELAR:
                tipoOperacionInventario = Operacion.NINGUNO;
                desactivarControlesInventario();
                desactivarTextInventario();
                btnBuscarInventario.setDisable(false);
                btnAgregarInventario.setText("AGREGAR");
                btnEliminarInventario.setText("ELIMINAR");
                limpiarText();
                cmbFiltroCodigo.setDisable(false);
                break;
        }
    }
     
     
    public void accion(String sql){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        PreparedStatement ps;
        ResultSet rs;
        Notifications noti = Notifications.create();
        ButtonType buttonTypeSi = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");
        switch(tipoOperacionInventario){
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
                        tipoOperacionInventario = Operacion.CANCELAR;
                        buscarCredito();
                        accionInventario();
                        cargarDatosProveedor();
                        
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL AGREGAR");
                        noti.text("HA OCURRIDO UN ERROR AL GUARDAR EL REGISTRO"+ex);
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionInventario = Operacion.CANCELAR;
                        accionInventario();
                       
                    } 
                }else{
                    
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA AGREGADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionInventario = Operacion.CANCELAR;
                    accionInventario();
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
                        proveedorName= txtProveedorInventario.getText();
                        cargarDatosProveedor();
                        tipoOperacionInventario = Operacion.CANCELAR;
                        accionInventario();
                        
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        
                        
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ELIMINAR");
                        noti.text("HA OCURRIDO UN ERROR AL ELIMINAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionInventario = Operacion.CANCELAR;
                        accionInventario();
                    }
                }else{
                     noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA ELIMINADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionInventario = Operacion.CANCELAR;
                    accionInventario();
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
                        proveedorName= txtProveedorInventario.getText();
                        cargarDatosProveedor();
                        tipoOperacionInventario = Operacion.CANCELAR;
                        accionInventario();
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ACTUALIZAR");
                        noti.text("HA OCURRIDO UN ERROR AL ACTUALIZAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionInventario = Operacion.CANCELAR;
                        accionInventario();
                    }
                }else{
                    
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA ACTUALIZAR EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionInventario = Operacion.CANCELAR;
                    accionInventario();
                }
                break;
            case BUSCAR:
                 try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    rs = ps.executeQuery();
                    int codigo=0;
                    while(rs.next()){
                        cmbCodigoProductoInventario.setValue(rs.getString("productoId"));
                        txtCantidadInventario.setText(rs.getString("inventarioProductoCant"));
                        txtProveedorInventario.setText(rs.getString("proveedorNombre"));
                        txtProductoInventario.setText(rs.getString("productoDesc"));
                        cmbNombreEstado.setValue(rs.getString("estadoProductoDesc"));

                        codigoProducto = rs.getString("productoId");
                        
                    }                    
                    if(rs.first()){
                        for(int i=0; i<tblInventario.getItems().size(); i++){
                            
                            if(colCodigoProductoInventario.getCellData(i) == codigoProducto){
                                tblInventario.getSelectionModel().select(i);
                                break;
                            }
                        }
                        activarControles();
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("OPERACIÓN EXITOSA");
                        noti.text("SU OPERACIÓN SE HA REALIZADO CON EXITO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        cmbNombreEstado.setDisable(false);
                        activarTextInventario();
                        
                    }else{
                         
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL BUSCAR");
                        noti.text("NO SE HA ENCONTRADO EN LA BASE DE DATOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionInventario = Operacion.CANCELAR;
                        accionInventario();
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
                    tipoOperacionInventario = Operacion.CANCELAR;
                    accionInventario();
                }
                break;
                 case SUMAR:
                alert.setTitle("SUMAR PRODUCTO");
                alert.setHeaderText("SUMAR PRODUCTO");
                alert.setContentText("¿Está seguro que desea sumar esta cantidad?");
                
                alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);
                
                Optional<ButtonType> resultSuma = alert.showAndWait();
                if(resultSuma.get() == buttonTypeSi ){
                    try {
                        ps = Conexion.getIntance().getConexion().prepareCall(sql);
                        ps.execute();
                        
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("OPERACIÓN EXITOSA");
                        noti.text("SE HA ACTUALIZADO LA CANTIDAD EXITOSAMENTE");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        proveedorName= txtProveedorInventario.getText();
                        cargarDatosProveedor();
                        tipoOperacionInventario = Operacion.CANCELAR;
                        buscarCredito();
                        accionInventario();
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL SUMAR");
                        noti.text("HA OCURRIDO UN ERROR AL SUMAR");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionInventario = Operacion.CANCELAR;
                        accionInventario();
                    }
                }else{
                    
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA AGREGADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionInventario = Operacion.CANCELAR;
                    accionInventario();
                }
                break;
            case RESTAR:
                alert.setTitle("RESTAR PRODUCTO");
                alert.setHeaderText("RESTAR PRODUCTO");
                alert.setContentText("¿Está seguro que desea restar esta cantidad?");
                
                alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);
                
                Optional<ButtonType> resultResta = alert.showAndWait();
                if(resultResta.get() == buttonTypeSi ){
                    try {
                        ps = Conexion.getIntance().getConexion().prepareCall(sql);
                        ps.execute();
                        
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("OPERACIÓN EXITOSA");
                        noti.text("SE HA ACTUALIZADO LA CANTIDAD EXITOSAMENTE");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        proveedorName= txtProveedorInventario.getText();
                        cargarDatosProveedor();
                        tipoOperacionInventario = Operacion.CANCELAR;
                        accionInventario();
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL SUMAR");
                        noti.text("HA OCURRIDO UN ERROR AL SUMAR");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionInventario = Operacion.CANCELAR;
                        accionInventario();
                    }
                }else{
                    
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA AGREGADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionInventario = Operacion.CANCELAR;
                    accionInventario();
                }
                break;
        }
    }
    
    
  
    
    public int buscarCodigoEstado(String descripcionEstado){    
        try{
            PreparedStatement sp = Conexion.getIntance().getConexion().prepareCall("{call SpBuscarCodigoEstado(?)}");
            sp.setString(1, descripcionEstado);
            ResultSet resultado = sp.executeQuery(); 
            
            while(resultado.next()){
            buscarCodigoEstado = resultado.getInt(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return buscarCodigoEstado;
    }

    
    
    public void actualizarCredito(String nofac, double montoFac){

        double montoTotal =montoFac+Double.parseDouble(txtCantidadInventario.getText())*costoProducto;
        
            Double costoFinal = Double.parseDouble(txtCantidadInventario.getText())*Double.parseDouble(txtCostoNuevo.getText());
            
            String sqlDetalle = "{call SpAgregarCreditoDetalleBackUp('"+cmbCodigoProductoInventario.getValue()+"','"+txtCantidadInventario.getText()+"','"+costoFinal+"')}";
            String sqlTransferirBackup = "{call SpAgregarCreditoDetalle()}";
            String sqlEliminarBackup = "{call SpEliminarBackupCredito()}";

     
            Integer tipo = 1;
            Integer documento = 3;
            Integer idFac = Integer.parseInt(noFactura.getText());                           
            LocalDate date2 = LocalDate.now();
        
            String sql = "call SpActualizarCreditoInventario('"+montoTotal+"','"+nofac+"')";
            String sqlUpdate = "call SpUpdateDetalleCredito('"+nofac+"')";
            String sqlCardex = "{call SpAgregarCardexCreditos('"+date2+"','"+txtProductoInventario.getText()+"','"+idFac+"','"+tipo+"','"+txtCantidadInventario.getText()+"','"+documento+"')}";  
            System.out.println(sqlCardex);
        try{
            
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);            
            PreparedStatement psDetalle = Conexion.getIntance().getConexion().prepareCall(sqlDetalle);
            PreparedStatement psTranferirBackup = Conexion.getIntance().getConexion().prepareCall(sqlTransferirBackup);
            PreparedStatement psUpdate = Conexion.getIntance().getConexion().prepareCall(sqlUpdate);
            PreparedStatement psEliminarBackup = Conexion.getIntance().getConexion().prepareCall(sqlEliminarBackup);
                PreparedStatement psCardex = Conexion.getIntance().getConexion().prepareCall(sqlCardex);
                
                psCardex.execute();
            ps.execute();
            psDetalle.execute();
            psTranferirBackup.execute();
            psUpdate.execute();            
            psEliminarBackup.execute();

            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgCorrecto));
            noti.title("CREDITO ACTUALIZADO");
            noti.text("Se ha actualizado el credito de la factura: "+nofac);
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
        }catch (SQLException ex) {
            ex.printStackTrace();
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR");
            noti.text("hubo un error en la base de datos"+ex);
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
            try{
            PreparedStatement psEliminarBackup = Conexion.getIntance().getConexion().prepareCall(sqlEliminarBackup);
            psEliminarBackup.execute();
            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }
    
    public void agregarCredito(){
        Dialog dialog = new Dialog();
        dialog.setTitle("Agregar Credito");
        dialog.setHeaderText("Ingrese los campos para agregar una nueva factura en creditos.");
        dialog.setResizable(true);
        int codigoEstado1 = 1;
        Label label1 = new Label("Fecha de inicio: ");
        Label label2 = new Label("Fecha Final: ");
        Label label3 = new Label("Descripción:");
        
        JFXDatePicker fechaInicio = new JFXDatePicker();
        JFXDatePicker fechaFinal= new JFXDatePicker();
        TextField desc = new TextField();
        GridPane grid = new GridPane();
        
        grid.add(label1, 1, 1);
        grid.add(fechaInicio, 2, 1);
        
        grid.add(label2, 1, 3);
        grid.add(fechaFinal, 2, 3);
        
        grid.add(label3, 1, 4);
        grid.add(desc, 2, 4);
        
        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Guardar", ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
        
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);
        
        Optional<ButtonType> result = dialog.showAndWait();
        
        Integer idFac = Integer.parseInt(noFactura.getText());                   
        Integer tipo = 1;
        LocalDate date2 = fechaInicio.getValue();
            
        if(result.get() == buttonTypeOk){
            Creditos nuevoCredito = new Creditos();
            nuevoCredito.setCreaditoFechaInicio(java.sql.Date.valueOf( fechaInicio.getValue()));
            nuevoCredito.setCreditoFechaFinal(java.sql.Date.valueOf( fechaFinal.getValue()));
            nuevoCredito.setCreditoDesc(desc.getText());
            double cantidad = Double.parseDouble(txtCantidadInventario.getText());
            nuevoCredito.setCreditoMonto(costoProducto*cantidad);
            nuevoCredito.setNoFactura(noFactura.getText());
            Double costoFinal = Double.parseDouble(txtCantidadInventario.getText())*Double.parseDouble(txtCostoNuevo.getText());
            
            String sqlDetalle = "{call SpAgregarCreditoDetalleBackUp('"+cmbCodigoProductoInventario.getValue()+"','"+txtCantidadInventario.getText()+"','"+costoFinal+"')}";
            String sqlTransferirBackup = "{call SpAgregarCreditoDetalle()}";
            String sql = "{call SpAgregarCredito('"+nuevoCredito.getCreaditoFechaInicio()+"','"+nuevoCredito.getCreditoFechaFinal()+"','"+nuevoCredito.getCreditoDesc()+"','"+nuevoCredito.getCreditoMonto()+"','"+codigoEstado1+"','"+nuevoCredito.getNoFactura()+"')}";
            String sqlEliminarBackup = "{call SpEliminarBackupCredito()}";
            System.out.println(sqlDetalle);
            Integer documento = 3;
            System.out.println(txtProductoInventario.getText());
            String sqlCardex = "{call SpAgregarCardexCreditos('"+date2+"','"+txtProductoInventario.getText()+"','"+idFac+"','"+tipo+"','"+txtCantidadInventario.getText()+"','"+documento+"')}";  
            System.out.println(sqlCardex);
            try {
                PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                PreparedStatement psDetalle = Conexion.getIntance().getConexion().prepareCall(sqlDetalle);
                PreparedStatement psTranferirBackup = Conexion.getIntance().getConexion().prepareCall(sqlTransferirBackup);
                PreparedStatement psEliminarBackup = Conexion.getIntance().getConexion().prepareCall(sqlEliminarBackup);                
                PreparedStatement psCardex = Conexion.getIntance().getConexion().prepareCall(sqlCardex);
                
                psCardex.execute();
                psDetalle.execute();
                psTranferirBackup.execute();
                ps.execute();
                
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgCorrecto));
                noti.title("CREDITO GUARDADO");
                noti.text("Se ha agregado un nuevo credito");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();   
                noti.show();
                psEliminarBackup.execute();
                                        limpiarTextFunciones();

            } catch (SQLException ex) {
                 Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR");
                noti.text("hubo un error en la base de datos"+ex);
                ex.printStackTrace();
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(10));
                noti.darkStyle();   
                noti.show();
                            try{
              PreparedStatement psEliminarBackup = Conexion.getIntance().getConexion().prepareCall(sqlEliminarBackup);
            psEliminarBackup.execute();
            }catch(Exception e){
                e.printStackTrace();
            }
            }
        }else{
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("CREDITO NO GUARDADO");
            noti.text("NO SE HA GUARDADO EL PRODUCTO A CREDITOS");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
        }


        
    }
    
    public void buscarCredito(){
        String noFac = noFactura.getText();
        
        double montoFac = 0;
        String sql = "call SpBuscarFacCredito('"+noFac+"')";
        
        try {
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
             while(rs.next()){
                montoFac = rs.getDouble("creditoMonto");                
            }
            if(rs.first()){                
                actualizarCredito(noFac,montoFac);
                
            }else{
                agregarCredito();
            }
        } catch (SQLException ex) {
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR");
            noti.text("hubo un error en la base de datos"+ex);
            ex.printStackTrace();
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
        }
        
        
    }
     @FXML
    private void btnAgregar(MouseEvent event) {
        if(tipoOperacionInventario == Operacion.GUARDAR){
           if(cmbCodigoProductoInventario.getValue().equals("") || txtCantidadInventario.getText().isEmpty() || cmbNombreEstado.getValue().equals("")){
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR");
                noti.text("HAY CAMPOS VACÍOS");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();   
                noti.show();
           }else{
                InventarioProductos nuevoInventario = new InventarioProductos();
                nuevoInventario.setProductoId(cmbCodigoProductoInventario.getValue());
                nuevoInventario.setInventarioProductoCant(Integer.parseInt(txtCantidadInventario.getText()));
                nuevoInventario.setEstadoProductoDesc(cmbNombreEstado.getValue());
               
                   proveedorName = txtProveedorInventario.getText();
                   System.out.println(proveedorName);
                   String sql = "{call SpAgregarInventarioProductos('"+nuevoInventario.getInventarioProductoCant()+"','"+ nuevoInventario.getProductoId()+"','"+buscarCodigoEstado(nuevoInventario.getEstadoProductoDesc())+"')}";
                  
                   tipoOperacionInventario = Operacion.GUARDAR;
                   accion(sql);     
                   
            }
        }else{
            tipoOperacionInventario = Operacion.AGREGAR;;
            accionInventario();
        }

    }
    
    
         @FXML
    private void btnSumar(MouseEvent event) {
           if(cmbCodigoProductoInventario.getValue().equals("") || txtCantidadInventario.getText().isEmpty() || noFactura.getText().isEmpty()){
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("HAY CAMPOS VACÍOS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
           }else{
                   InventarioProductos nuevoInventario = new InventarioProductos();
                   nuevoInventario.setProductoId(cmbCodigoProductoInventario.getValue());
                   nuevoInventario.setInventarioProductoCant(Double.parseDouble(txtCantidadInventario.getText()));

                   String sql = "{call SpSumaProductos('"+nuevoInventario.getProductoId()+"','"+ nuevoInventario.getInventarioProductoCant()+"')}";
                   tipoOperacionInventario = Operacion.SUMAR;
                   accion(sql);                   
                }
                    accionInventario();
    }
    
    private void btnRestar() {
           if(cmbCodigoProductoInventario.getValue().equals("") || txtCantidadInventario.getText().isEmpty()){
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("HAY CAMPOS VACÍOS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
           }else{
                   InventarioProductos nuevoInventario = new InventarioProductos();
                   nuevoInventario.setProductoId(cmbCodigoProductoInventario.getValue());
                   nuevoInventario.setInventarioProductoCant(Integer.parseInt(txtCantidadInventario.getText()));

                   String sql = "{call SpRestarProductos('"+nuevoInventario.getProductoId()+"','"+ nuevoInventario.getInventarioProductoCant()+"')}";
                   tipoOperacionInventario = Operacion.RESTAR;
                   accion(sql); 

                    Integer documento = 4;
                    Integer idFac = 1111;                   
                    Integer tipo = 2;
                    LocalDate date2 = LocalDate.now();
                    
                    String sqlCardex = "{call SpAgregarCardexFac('"+date2+"','"+txtProductoInventario.getText()+"','"+idFac+"','"+tipo+"','"+txtCantidadInventario.getText()+"','"+documento+"')}";  
                    try{
                        PreparedStatement psCardex = Conexion.getIntance().getConexion().prepareCall(sqlCardex);
                        psCardex.execute();  
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                   accionInventario();
    }
    
    @FXML
    public void buscarProducto(){
        verificarProducto();
    }
    public void verificarProducto(){
        if(cmbCodigoProductoInventario.getValue()!= ""){
                try{
                     PreparedStatement sp = Conexion.getIntance().getConexion().prepareCall("{call SpBuscarProductos(?)}");
                     sp.setString(1, cmbCodigoProductoInventario.getValue());
                     ResultSet resultado = sp.executeQuery(); 
                        while(resultado.next()){
                            txtProductoInventario.setText(resultado.getString("productoDesc"));
                            txtProveedorInventario.setText(resultado.getString("proveedorNombre"));
                            proveedorId = resultado.getString("proveedorId");
                            txtCostoNuevo.setText(resultado.getString("precioCosto"));
                            costoProducto = resultado.getDouble("precioCosto");
                            btnAgregarInventario.setDisable(false);
                        }  
                }catch(Exception e){
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE BUSQUEDA ESTA VACÍO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                    btnAgregarInventario.setDisable(true);
                }
        }
    }
    @FXML
    private void btnEliminar(MouseEvent event) {
         if(tipoOperacionInventario == Operacion.GUARDAR){
            tipoOperacionInventario = Operacion.CANCELAR;
            accionInventario();
        }else{
             
            String sql = "{call SpEliminarInventarioProductos('"+codigoProducto+"')}";
            tipoOperacionInventario = Operacion.ELIMINAR;
            accion(sql);
        }
    }
    
    @FXML
    private void btnEditar(MouseEvent event) throws SQLException {
       InventarioProductos nuevoInventario = new InventarioProductos();
       nuevoInventario.setInventarioProductoCant(Double.parseDouble(txtCantidadInventario.getText()));
       nuevoInventario.setEstadoProductoDesc(cmbNombreEstado.getValue());

       String sql = "{call SpActualizarInventarioProductos('"+codigoProducto+"','"+nuevoInventario.getInventarioProductoCant()+"','"+buscarCodigoEstado(nuevoInventario.getEstadoProductoDesc())+"')}";
       
       if(txtCantidadInventario.getText().equals("0")){                
            String sql3 = "{call SpCostoPromedioSinCantidad('"+codigoProducto+"','"+txtCostoNuevo.getText()+"')}";     
            PreparedStatement ps3 = Conexion.getIntance().getConexion().prepareCall(sql3);
            ResultSet rs2 = ps3.executeQuery();
       }else if(txtCantidadInventario.getText() != "0"){
           String sql2 = "{call SpCostoPromedio('"+codigoProducto+"','"+txtCostoNuevo.getText()+"')}";     
           PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql2);
           ResultSet rs = ps.executeQuery();   
       }
       

       tipoOperacionInventario = Operacion.ACTUALIZAR;
       accion(sql);                   
    }

        public void cargarCombo(){
        ArrayList<String>lista = new ArrayList();
        
        lista.add(0,"CÓDIGO");
        lista.add(1,"NOMBRE");
        lista.add(2,"PROVEEDOR");
        
        listaFiltro = FXCollections.observableList(lista);
        
        cmbFiltroCodigo.setItems(listaFiltro);
    }

    @FXML
    private void comboFiltro(ActionEvent event) {
        btnBuscarInventario.setDisable(false);
        ArrayList<String> lista = new ArrayList();        
        String sql ="";
        
        if(cmbFiltroCodigo.getValue().equals("CÓDIGO") || cmbFiltroCodigo.getValue().equals("NOMBRE")){
            sql = "{call SpListarInventarioProductos()}";
        }else if(cmbFiltroCodigo.getValue().equals("PROVEEDOR")){
            sql = "{call SpListarInventarioProductosProv()}";
        }
        
        int x=0;
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                 if(cmbFiltroCodigo.getValue().equals("CÓDIGO")){
                     lista.add(x, rs.getString("productoId"));
                     
                }else if(cmbFiltroCodigo.getValue().equals("NOMBRE")){                   
                    lista.add(x, rs.getString("productoDesc"));
                }else if(cmbFiltroCodigo.getValue().equals("PROVEEDOR")){
                     System.out.println(sql);
                    lista.add(x, rs.getString("proveedorNombre"));
                }
                 x++;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
       listaBuscar = FXCollections.observableList(lista);
       cmbBuscar.setItems(listaBuscar);
       new AutoCompleteComboBoxListener(cmbBuscar);
    }
    
    public void buscar(){
      if(cmbBuscar.getValue().equals("")){
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE BUSQUEDA ESTA VACÍO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
        }else{
            if(cmbFiltroCodigo.getValue().equals("CÓDIGO")){
                tipoOperacionInventario = Operacion.BUSCAR;
                    String sql = "{ call SpBuscarInventarioProductos('"+cmbBuscar.getValue()+"')}";
                    accion(sql);
            }else if(cmbFiltroCodigo.getValue().equals("NOMBRE")){
                    tipoOperacionInventario = Operacion.BUSCAR;
                    String sql = "{ call SpBuscarInventarioProductosNombre('"+cmbBuscar.getValue()+"')}";
                    accion(sql);
            }else if(cmbFiltroCodigo.getValue().equals("PROVEEDOR")){                
                proveedorName = cmbBuscar.getValue();
                cargarDatosProveedor();
                limpiarText();
            }
        }  
    }
    
    @FXML
    private void cmbBuscar(ActionEvent event) {
        buscar();
    }
    
    @FXML
    private void btnBuscar(MouseEvent event) {
        buscar();
    }
    
        @FXML
    private void atajosInventario(KeyEvent event) {
         if(cmbBuscar.isFocused()){
            if(event.getCode() == KeyCode.ENTER){
                buscar();
            }
        }
    }
    
   @FXML
    private void btnVolverACargar(ActionEvent event) {
           cargarDatos();
    }
    
    //========================================== CODIGO PARA VISTA ESTADO PRODUCTO ========================================================

    public void limpiarTextEstado(){
        txtCodigoEstadoProducto.setText("");
        txtDescEstadoProducto.setText("");
    }
    
    public void desactivarControlesEstado(){    
        btnEditarEstadoProductos.setDisable(true);
        btnEliminarEstadoProductos.setDisable(true);
    }
    
    
    public void desactivarTextEstado(){
        txtCodigoEstadoProducto.setEditable(false);
        txtDescEstadoProducto.setEditable(false);
    }
    
    public void activarTextEstado(){
        txtCodigoEstadoProducto.setEditable(true);
        txtDescEstadoProducto.setEditable(true);
    }
    
    
     public ObservableList<EstadoProductos> getEstado(){
        ArrayList<EstadoProductos> lista = new ArrayList();
        ArrayList<String> comboCodigoFiltro = new ArrayList();
        String sql = "{call SpListarEstadoProductos()}";
        int x=0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new EstadoProductos(
                            rs.getString("estadoProductoId"),
                            rs.getString("estadoProductoDesc")
                ));
                comboCodigoFiltro.add(x, rs.getString("estadoProductoId"));
                x++;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        listaCodigoEstadoProductos = FXCollections.observableList(comboCodigoFiltro);
        cmbCodigoEstadoProductos.setItems(listaCodigoEstadoProductos);
        
        return listaEstadoProductos = FXCollections.observableList(lista);
    }
    
   
    public void cargarDatosEstado(){
        tblEstadoProductos.setItems(getEstado());
        colCodigoEstadoCodigo.setCellValueFactory(new PropertyValueFactory("estadoProductoId"));
        colDescEstadoProductos.setCellValueFactory(new PropertyValueFactory("estadoProductoDesc"));
        limpiarTextEstado();
        new AutoCompleteComboBoxListener(cmbCodigoEstadoProductos);
        desactivarControlesEstado();
        desactivarTextEstado();
        tipoOperacionEstado = Operacion.CANCELAR;
        accionEstado();
    }
    
    
    @FXML
    private void seleccionarElementos(MouseEvent event) {
        int index = tblEstadoProductos.getSelectionModel().getSelectedIndex();
        try{
            txtCodigoEstadoProducto.setText(colCodigoEstadoCodigo.getCellData(index).toString());
            txtDescEstadoProducto.setText(colDescEstadoProductos.getCellData(index));
            
            
            btnEliminarEstadoProductos.setDisable(false);
            btnEditarEstadoProductos.setDisable(false);
            cmbNombreEstado.setDisable(false);
            codigoEstado = colCodigoEstadoCodigo.getCellData(index);
            activarTextEstado();
        }catch(Exception e){
        }
    }
    
    
    public void accionEstado(){
        switch(tipoOperacionEstado){
            case AGREGAR:
                tipoOperacionEstado = Operacion.GUARDAR;
                cancelar = Operacion.CANCELAR;
                desactivarControlesEstado();
                btnAgregarEstadoProductos.setText("GUARDAR");
                btnEliminarEstadoProductos.setText("CANCELAR");
                btnEliminarEstadoProductos.setDisable(false);
                activarTextEstado();
                cmbCodigoEstadoProductos.setDisable(true);
                btnBuscarEstadoProductos.setDisable(true);
                limpiarTextEstado();
                break;
            case CANCELAR:
                tipoOperacionEstado = Operacion.NINGUNO;
                desactivarControlesEstado();
                desactivarTextEstado();
                btnAgregarEstadoProductos.setText("AGREGAR");
                btnEliminarEstadoProductos.setText("ELIMINAR");
                limpiarTextEstado();
                cmbCodigoEstadoProductos.setDisable(false);
                btnBuscarEstadoProductos.setDisable(false);
                cancelar = Operacion.NINGUNO;
                break;
        }
    }
    
    
    public void accionEstado(String sql){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        PreparedStatement ps;
        ResultSet rs;
        Notifications noti = Notifications.create();
        ButtonType buttonTypeSi = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");
        switch(tipoOperacionEstado){
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
                        tipoOperacionEstado = Operacion.CANCELAR;
                        accionEstado();
                        cargarDatosEstado();
                        
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL AGREGAR");
                        noti.text("HA OCURRIDO UN ERROR AL GUARDAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionEstado = Operacion.CANCELAR;
                        accionEstado();
                    }
                }else{
                    
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA AGREGADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionEstado = Operacion.CANCELAR;
                    accionEstado();
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
                        cargarDatosEstado();
                        tipoOperacionEstado = Operacion.CANCELAR;
                        accionEstado();
                        
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        
                        
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ELIMINAR");
                        noti.text("HA OCURRIDO UN ERROR AL ELIMINAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionEstado = Operacion.CANCELAR;
                        accionEstado();
                    }
                }else{
                     noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA ELIMINADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionEstado = Operacion.CANCELAR;
                    accionEstado();
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
                        tipoOperacionEstado = Operacion.CANCELAR;
                        accionEstado();
                        cargarDatosEstado();
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ACTUALIZAR");
                        noti.text("HA OCURRIDO UN ERROR AL ACTUALIZAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionEstado = Operacion.CANCELAR;
                        accionEstado();
                    }
                }else{
                    
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA ACTUALIZAR EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionEstado = Operacion.CANCELAR;
                    accionEstado();
                }
                break;
            case BUSCAR:
                 try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    rs = ps.executeQuery();
                    
                    while(rs.next()){
                        txtCodigoEstadoProducto.setText(rs.getString("estadoProductoId"));
                        txtDescEstadoProducto.setText(rs.getString("estadoProductoDesc"));
                        codigoEstado = rs.getString("estadoProductoId");
                        
                    }                    
                    if(rs.first()){
                        for(int i=0; i<tblEstadoProductos.getItems().size(); i++){
                            if(colCodigoEstadoCodigo.getCellData(i) == codigoEstado){
                                tblEstadoProductos.getSelectionModel().select(i);
                                break;
                            }
                        }
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("OPERACIÓN EXITOSA");
                        noti.text("SU OPERACIÓN SE HA REALIZADO CON EXITO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        btnEditarEstadoProductos.setDisable(false);
                        btnEliminarEstadoProductos.setDisable(false);
                        activarTextEstado();
                    }else{
                         
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL BUSCAR");
                        noti.text("NO SE HA ENCONTRADO EN LA BASE DE DATOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionEstado = Operacion.CANCELAR;
                        accionEstado();
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
                    tipoOperacionEstado = Operacion.CANCELAR;
                    accionEstado();
                }
                break;
        }
    }
    
    
    
    @FXML
    private void btnAgregarEstado(MouseEvent event) {
        if(tipoOperacionEstado == Operacion.GUARDAR){
            if(txtCodigoEstadoProducto.getText().isEmpty() || txtDescEstadoProducto.getText().isEmpty()){
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR");
                noti.text("HAY CAMPOS VACÍOS");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();   
                noti.show();
            
            }else{
                if(txtDescEstadoProducto.getText().length()<50){
                    EstadoProductos nuevaEstado = new EstadoProductos();
                    nuevaEstado.setEstadoProductoDesc(txtDescEstadoProducto.getText());
                    String sql = "{call SpAgregarEstadoProducto('"+nuevaEstado.getEstadoProductoDesc()+"')}";
                    accionEstado(sql);
                }else{
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("NOMBRE DE LA CATEGORÍA NO TIENEN UNA LONGITUD ADECUADA (DEBE SER MENOR DE 50 CARACTERES)");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                }
            }
        
        }else{
             tipoOperacionEstado = Operacion.AGREGAR;
                accionEstado();
        }
    }
    
    
    @FXML
    private void btnEditarEstado(MouseEvent event) {
        EstadoProductos nuevaEstado = new EstadoProductos();
        nuevaEstado.setEstadoProductoId(txtCodigoEstadoProducto.getText());
        nuevaEstado.setEstadoProductoDesc(txtDescEstadoProducto.getText());
        
        tipoOperacionEstado = Operacion.ACTUALIZAR;
        String sql = "{call SpActualizarEstadoProducto('"+codigoEstado+"','"+nuevaEstado.getEstadoProductoDesc()+"')}";
        accionEstado(sql);
    }
    
    
     @FXML
    private void btnEliminarEstado(MouseEvent event) {
        if(tipoOperacionEstado == Operacion.GUARDAR){
            tipoOperacionEstado = Operacion.CANCELAR;
            accionEstado();
        }else{
            String sql = "{call SpEliminarEstadoProductos('"+codigoEstado+"')}";
            tipoOperacionEstado = Operacion.ELIMINAR;
            accionEstado(sql);
        }
    }
    
    
    public void buscarEstado(){
        if(cmbCodigoEstadoProductos.getValue().equals("")){
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR");
            noti.text("El CAMPO DE CÓDIGO ESTA VACÍO");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
        }else{
            tipoOperacionEstado = Operacion.BUSCAR;
            String sql = "{ call SpBuscarEstadoProductos('"+cmbCodigoEstadoProductos.getValue()+"')}";
            accionEstado(sql);
        }
    }
    
    
    @FXML
    private void btnBuscarEstado(MouseEvent event) {
        buscarEstado();
    }
 
    
    
    @FXML
    private void cargarEstado(Event event) {
        cargarDatosEstado();
        animacion.animacion(anchor3, anchor4);
    }
    
    
    @FXML
    private void atajosEstado(KeyEvent event) {
        if(cmbCodigoEstadoProductos.isFocused()){
            if(event.getCode() == KeyCode.ENTER){
                buscarEstado();
            }
        }
    }
    
    
        public void imprimirReporteInventario(){
            try{
                Map parametros = new HashMap();

                String repuesta = "'ejemplo'";
                
                parametros.put("prueba", "'"+repuesta+"'");
                 org.moduloFacturacion.report.GenerarReporte.mostrarReporte("ReporteInventario.jasper", "REPORTE DE INVENTARIO", parametros);
                

                }catch(Exception e){
                    e.printStackTrace();
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("DEBE SELECCIONAR FECHA DE INICIO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                }
    }
    
    @FXML
    public void generarReporteInventario(){
           imprimirReporteInventario(); 
    }
    
    boolean passUser = false;
    @FXML
    public void validarRestar(MouseEvent event){
        Dialog dialog = new Dialog();
        dialog.setTitle("AJUSTE INVENTARIO");
        dialog.setHeaderText("Ingrese los campos para ajustar el inventario.");
        dialog.setResizable(true);

        Label label1 = new Label("USUARIO: ");
        Label label2 = new Label("CONTRASEÑA: ");
        
        TextField user = new TextField();
        JFXPasswordField pass= new JFXPasswordField();
        GridPane grid = new GridPane();
        
        grid.add(label1, 1, 1);
        grid.add(user, 2, 1);
        
        grid.add(label2, 1, 3);
        grid.add(pass, 2, 3);
        
        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Guardar", ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
        
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);
        
        Optional<ButtonType> result = dialog.showAndWait();
        
        if(result.get() == buttonTypeOk){
            ArrayList<String> lista = new ArrayList();
            String sql = "{call SpLoginAdmin('"+user.getText()+"','"+pass.getText()+"')}";                        
            int x=0;
            String usuario = "";
                try{
                    PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    lista.add(x, rs.getString("usuarioNombre"));
                    usuario = rs.getString("usuarioNombre");
                     x++;
                }

                if(usuario.equals("")){

                     Notifications noti = Notifications.create();
                            noti.graphic(new ImageView(imgError));
                            noti.title("ERROR AL VERIFICAR  USUARIO");
                            noti.text("NO SE HA PODIDO VERIFICAR EL USUARIO");
                            noti.position(Pos.BOTTOM_RIGHT);
                            noti.hideAfter(Duration.seconds(4));
                            noti.darkStyle();
                            noti.show();

                }else{
                    btnRestar();
                    passUser = true;
                    
                    Notifications noti = Notifications.create();
                            noti.graphic(new ImageView(imgCorrecto));
                            noti.title("USUARIO VERIFICADO");
                            noti.text("SE HA VERIFICADO CON EXITO");
                            noti.position(Pos.BOTTOM_RIGHT);
                            noti.hideAfter(Duration.seconds(4));
                            noti.darkStyle();
                            noti.show();
                }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        }else{
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("OPERACIÓN CANCELADA");
            noti.text("NO SE HA REALIZADO NINGUNA OPERACIÓN");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
        }
        
      }
    //=========================================================================================================================================
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         validar.validarView(menu.prefs.get("dark", "root"), anchor);
        iniciarInventario();
        cmbCodigoProductoInventario.setValue("");
        cmbNombreEstado.setValue("");
        llenarComboProducto();
        cargarCombo();
        
        String sql = "{call SpDesactivarProd()}";

        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                
            ps.execute();
        }catch(Exception e){
                e.printStackTrace();
        }
        
        new AutoCompleteComboBoxListener(cmbBuscar);
        new AutoCompleteComboBoxListener(cmbFiltroCodigo);
        new AutoCompleteComboBoxListener(cmbCodigoProductoInventario);
    }    

    @FXML
    private void regresar(MouseEvent event) throws IOException {
         String menu = "org/moduloFacturacion/view/menuPrincipal.fxml";
        cambioScene.Cambio(menu,(Stage) anchor.getScene().getWindow());
    }
    
    


    @FXML
    private void btnProveedores(MouseEvent event) throws IOException {
        menu.prefsRegresar.put("regresar", "inventario");
        String menu1 = "org/moduloFacturacion/view/ProveedoresView.fxml";
        cambioScene.Cambio(menu1,(Stage) anchor.getScene().getWindow());
    }
    
    @FXML
    private void btnProductos(MouseEvent event) throws IOException {
        menu.prefsRegresarProductos.put("regresarProducto", "inventario");
          String menu = "org/moduloFacturacion/view/ProductosView.fxml";
        cambioScene.Cambio(menu,(Stage) anchor.getScene().getWindow());
    }
    
        @FXML
    private void validarCodigoInventario(KeyEvent event) {
            System.out.println("hola");
    }
      @FXML
    private void validarCantidadProducto(KeyEvent event) {
         char letra = event.getCharacter().charAt(0);
        if(letra != '.'){
            System.out.println("hola");
        }
        if(!Character.isDigit(letra)){
            
            if(letra != '.'){
                if(!Character.isWhitespace(letra)){
                    event.consume();
                }else{
                    if(Character.isSpaceChar(letra)){
                        event.consume();
                    }
                }
            }
            
        }
    }
    
       
    @FXML
    private void validarCodigoEstado(KeyEvent event) {
          char letra = event.getCharacter().charAt(0);
        
        if(!Character.isDigit(letra)){
            if(!Character.isWhitespace(letra)){
                event.consume();
            }else{
                if(Character.isSpaceChar(letra)){
                    event.consume();
                }
            }
           
        }
    }
    
        
    //CARDEX 
    @FXML
    private AnchorPane anchor41;
    @FXML
    private TableView<Cardex> tblCardex;
    
    @FXML
    private TableColumn<Cardex, Date> colFechaCardex;
    @FXML
    private TableColumn<Cardex, String> colNoCardex;
    @FXML
    private TableColumn<Cardex, String> colMovimientoCardex;
    @FXML
    private TableColumn<Cardex, Integer> colTotalCardex;
    @FXML
    private TableColumn<Cardex, Integer> colSaldoCardex;
    @FXML
    private TableColumn<Cardex, String> colDocumentoCardex;
    @FXML
    private TableColumn<Cardex, Integer> colEntradaCardex;
    @FXML
    private JFXButton btnBuscarCardex;
    @FXML
    private ComboBox<String> cmbFiltroCardex;
    @FXML
    private ComboBox<String> cmbFiltroBuscarCardex;
    @FXML
    private JFXButton btnReporteCardex;
    @FXML
    private JFXDatePicker txtfechaInicioCardex;
    @FXML
    private JFXDatePicker txtfechaFinalCardex;
    @FXML
    private JFXTextField txtProveedorCardex;
    
    ObservableList<String> listaCmbFiltro;
    ObservableList<String> listaCmbFiltroBuscado;
    ObservableList<Cardex> listaCardex;
    ObservableList<String> listaCardexProd;
    ObservableList<Cardex> listaCardexOrden;

    
    @FXML
   public void CargarDatosCardex(){
       llenarCmbFiltro();
       cmbFiltroBuscarCardex.setValue("");
   }
   
   public void llenarCmbFiltro(){
       ArrayList<String>lista = new ArrayList();
        
       lista.add(0,"CÓDIGO");
       lista.add(1,"PRODUCTO");
        
       listaCmbFiltro = FXCollections.observableList(lista);
        
       cmbFiltroCardex.setItems(listaCmbFiltro);
       
   }
   
   @FXML
   public void cargarComboFiltro(){
        ArrayList<String> lista = new ArrayList();
        
        String sql ="{call SpLlenarCmbCardex()}";      
        int x=0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                 if(cmbFiltroCardex.getValue().equals("CÓDIGO")){
                     lista.add(x, rs.getString("productoId"));
                     
                }else if(cmbFiltroCardex.getValue().equals("PRODUCTO")){
                    lista.add(x, rs.getString("productoDesc"));
                }
                 x++;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
       listaCmbFiltroBuscado = FXCollections.observableList(lista);
       cmbFiltroBuscarCardex.setItems(listaCmbFiltroBuscado);
       new AutoCompleteComboBoxListener(cmbFiltroBuscarCardex);
   }
          
   //buscar por id
    public ObservableList<Cardex> getCardex(){
        ArrayList<Cardex> lista = new ArrayList();
        String sql = "{call SpGenerarCardex('"+cmbFiltroBuscarCardex.getValue()+"')}"; 
        
        int x=0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new Cardex(
                            rs.getDate("fechaCardex"),
                            rs.getString("noFacCardex"),
                            rs.getString("idTipoDesc"),
                            rs.getDouble("saldoCardex"),
                            rs.getDouble("entradaCardex"),
                            rs.getDouble("totalCardex"),
                            rs.getString("productoDesc"),
                            rs.getString("DescTipoDocumento")
                ));
                x++;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return listaCardex = FXCollections.observableList(lista);
    }
    
    public void cargarCreditosBuscados(){
        tblCardex.setItems(getCardex());
        colFechaCardex.setCellValueFactory(new PropertyValueFactory("fechaCardex"));
        colNoCardex.setCellValueFactory(new PropertyValueFactory("noFacCardex"));
        colMovimientoCardex.setCellValueFactory(new PropertyValueFactory("idTipoDesc"));        
        colTotalCardex.setCellValueFactory(new PropertyValueFactory("saldoCardex"));
        colEntradaCardex.setCellValueFactory(new PropertyValueFactory("entradaCardex")); 
        colSaldoCardex.setCellValueFactory(new PropertyValueFactory("totalCardex"));  
        colDocumentoCardex.setCellValueFactory(new PropertyValueFactory("DescTipoDocumento"));        
        buscarProveedor();
    }  
   
    //buscar por nombre
    public ObservableList<Cardex> getCardexProd(){
        ArrayList<Cardex> lista = new ArrayList();
        String sql = "{call SpGenerarCardexProd('"+cmbFiltroBuscarCardex.getValue()+"')}"; 
        System.out.println(sql);
        int x=0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new Cardex(
                            rs.getDate("fechaCardex"),
                            rs.getString("noFacCardex"),
                            rs.getString("idTipoDesc"),
                            rs.getDouble("saldoCardex"),
                            rs.getDouble("entradaCardex"),                        
                            rs.getDouble("totalCardex"),
                            rs.getString("productoDesc"),
                            rs.getString("DescTipoDocumento")                                                    

                ));
                x++;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return listaCardex = FXCollections.observableList(lista);
    }
    
    public void cargarCreditosBuscadosProd(){
        tblCardex.setItems(getCardexProd());
        System.out.println("cargar");
        colFechaCardex.setCellValueFactory(new PropertyValueFactory("fechaCardex"));
        colNoCardex.setCellValueFactory(new PropertyValueFactory("noFacCardex"));
        colMovimientoCardex.setCellValueFactory(new PropertyValueFactory("idTipoDesc"));        
        colTotalCardex.setCellValueFactory(new PropertyValueFactory("saldoCardex"));
        colEntradaCardex.setCellValueFactory(new PropertyValueFactory("entradaCardex")); 
        colSaldoCardex.setCellValueFactory(new PropertyValueFactory("totalCardex")); 
        colDocumentoCardex.setCellValueFactory(new PropertyValueFactory("DescTipoDocumento"));        
        buscarProveedor();
    } 
        
    
    //buscar por id y fechas
        public ObservableList<Cardex> getCardexFechas(){
        ArrayList<Cardex> lista = new ArrayList();
         String sql = "{call SpGenerarCardexFecha('"+cmbFiltroBuscarCardex.getValue()+"','"+txtfechaInicioCardex.getValue()+"','"+txtfechaFinalCardex.getValue()+"')}";
        System.out.println(sql);
        int x=0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new Cardex(
                            rs.getDate("fechaCardex"),
                            rs.getString("noFacCardex"),
                            rs.getString("idTipoDesc"),
                            rs.getDouble("saldoCardex"),
                            rs.getDouble("entradaCardex"),                        
                            rs.getDouble("totalCardex"),
                            rs.getString("productoDesc"),
                            rs.getString("DescTipoDocumento")

                ));
                x++;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return listaCardex = FXCollections.observableList(lista);
    }
    
    public void cargarCreditosBuscadosFechas(){
        tblCardex.setItems(getCardexFechas());
        colFechaCardex.setCellValueFactory(new PropertyValueFactory("fechaCardex"));
        colNoCardex.setCellValueFactory(new PropertyValueFactory("noFacCardex"));
        colMovimientoCardex.setCellValueFactory(new PropertyValueFactory("idTipoDesc"));        
        colTotalCardex.setCellValueFactory(new PropertyValueFactory("saldoCardex"));
        colEntradaCardex.setCellValueFactory(new PropertyValueFactory("entradaCardex")); 
        colSaldoCardex.setCellValueFactory(new PropertyValueFactory("totalCardex"));
        colDocumentoCardex.setCellValueFactory(new PropertyValueFactory("DescTipoDocumento"));        
        buscarProveedor();
    } 
    
    
    //buscar por nombre y fechas
        public ObservableList<Cardex> getCardexFechasProd(){
        ArrayList<Cardex> lista = new ArrayList();
         String sql = "{call SpGenerarCardexFechaProd('"+cmbFiltroBuscarCardex.getValue()+"','"+txtfechaInicioCardex.getValue()+"','"+txtfechaFinalCardex.getValue()+"')}";
        System.out.println(sql);
        int x=0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new Cardex(
                            rs.getDate("fechaCardex"),
                            rs.getString("noFacCardex"),
                            rs.getString("idTipoDesc"),
                            rs.getDouble("saldoCardex"),
                            rs.getDouble("entradaCardex"),                        
                            rs.getDouble("totalCardex"),
                            rs.getString("productoDesc"),
                            rs.getString("DescTipoDocumento")

                ));
                x++;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return listaCardex = FXCollections.observableList(lista);
    }
    
    public void cargarCreditosBuscadosFechasProd(){
        tblCardex.setItems(getCardexFechasProd());
        colFechaCardex.setCellValueFactory(new PropertyValueFactory("fechaCardex"));
        colNoCardex.setCellValueFactory(new PropertyValueFactory("noFacCardex"));
        colMovimientoCardex.setCellValueFactory(new PropertyValueFactory("idTipoDesc"));        
        colTotalCardex.setCellValueFactory(new PropertyValueFactory("saldoCardex"));
        colSaldoCardex.setCellValueFactory(new PropertyValueFactory("totalCardex")); 
        colEntradaCardex.setCellValueFactory(new PropertyValueFactory("entradaCardex")); 
        colDocumentoCardex.setCellValueFactory(new PropertyValueFactory("DescTipoDocumento"));
        buscarProveedor();        

    } 
    
    public void buscarProveedor(){
       try{
            PreparedStatement sp = Conexion.getIntance().getConexion().prepareCall("{call SpBuscarProveedorCardex(?)}");
             sp.setString(1, cmbFiltroBuscarCardex.getValue());
             ResultSet resultado = sp.executeQuery(); 
             
            while(resultado.next()){
            txtProveedorCardex.setText(resultado.getString("proveedorNombre"));
        }
       }catch(Exception e){
           e.printStackTrace();
       
       }
 
  

        }
  
    
        
    @FXML
    public void buscarCardex(){
    
        if(cmbFiltroCardex.getValue().equals("CÓDIGO")){
            cargarCreditosBuscados();                
        }else if(cmbFiltroCardex.getValue().equals("PRODUCTO")){
            cargarCreditosBuscadosProd();
        }
        
    }
    
    @FXML
    public void btnBuscarCardex(){
            if(cmbFiltroCardex.getValue().equals("CÓDIGO")){
                cargarCreditosBuscadosFechas();                
            }else if(cmbFiltroCardex.getValue().equals("PRODUCTO")){
                cargarCreditosBuscadosFechasProd();
            }       
    }
    
    @FXML
    public void btnCargarCardex(){
        if(cmbFiltroCardex.getValue().equals("CÓDIGO")){
                System.out.println("aqui 1");
                cargarCreditosBuscados();                
            }else if(cmbFiltroCardex.getValue().equals("PRODUCTO")){
                cargarCreditosBuscadosProd();
        }
    }
    
    public void imprimirCardex(){
            try{
                Map parametros = new HashMap();

                String prodId = cmbFiltroBuscarCardex.getValue().toString();
                System.out.println(prodId);
                parametros.put("prodId", "'"+prodId+"'");

                 org.moduloFacturacion.report.GenerarReporte.mostrarReporte("CardexProd.jasper", "MOVIMIENTO DE INVENTARIO", parametros);
                

                }catch(Exception e){
                    e.printStackTrace();
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("DEBE SELECCIONAR FECHA DE INICIO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                }
    }
    
        public void imprimirCardexFechas(){
            try{
                Map parametros = new HashMap();

                 String prodId = cmbFiltroBuscarCardex.getValue().toString();
                 String inicio = txtfechaInicioCardex.getValue().toString();
                 String finalFecha = txtfechaFinalCardex.getValue().toString();


                
                parametros.put("prodId", "'"+prodId+"'");
                parametros.put("inicio", "'"+inicio+"'");
                parametros.put("finalFecha", "'"+finalFecha+"'");


                 org.moduloFacturacion.report.GenerarReporte.mostrarReporte("CardexProdFechas.jasper", "REPORTE CREDITO", parametros);
                

                }catch(Exception e){
                    e.printStackTrace();
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("DEBE SELECCIONAR FECHA DE INICIO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                }
    }
    
    public void generarReporteCredito(){
        String prueba = txtfechaInicioCardex.getValue().toString();
        
        if(prueba != ""){
            imprimirCardexFechas();
        }else{
        imprimirCardex();
        }
    }
    
    @FXML
    public void generarReporteCardex(MouseEvent event){
        if(txtfechaInicioCardex.getValue().equals("")){
            imprimirCardex();
            
        }else{
            imprimirCardexFechas();
        }
    }
    
    @FXML
    private void btnGenerarCardex(ActionEvent event) throws IOException {
        GenerarExcelCardex ge = new GenerarExcelCardex();
        if(!listaCardex.isEmpty()){
            ge.generar(listaCardex, cmbFiltroBuscarCardex.getValue());
        }
    }


}


