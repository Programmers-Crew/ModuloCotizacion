package org.ModuloCotizacion.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.ModuloCotizacion.bean.Animations;
import org.ModuloCotizacion.bean.AutoCompleteComboBoxListener;
import org.ModuloCotizacion.bean.CambioScene;
import org.ModuloCotizacion.bean.CategoriaProducto;
import org.ModuloCotizacion.bean.EstadoProductos;
import org.ModuloCotizacion.bean.GenerarExcel;
import org.ModuloCotizacion.bean.InventarioProductos;
import org.ModuloCotizacion.bean.ValidarStyle;
import org.ModuloCotizacion.db.Conexion;


public class InventarioViewController implements Initializable {
    
    CambioScene cambioScene = new CambioScene();
    Image imgError = new Image("org/ModuloCotizacion/img/error.png");
    Image imgCorrecto= new Image("org/ModuloCotizacion/img/correcto.png");
    @FXML
    private Pane btnProveedores;
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
    private JFXTextField noFactura;
    @FXML
    private JFXButton btnCargarDatos;
    @FXML
    private JFXTextField txtCostoNuevo;
    @FXML
    private JFXButton generarExcel;
    @FXML
    private JFXTextField txtCodigoCategoria;
    @FXML
    private JFXTextField txtDescripcionCategoria;
    @FXML
    private JFXButton btnAgregarCategoria;
    @FXML
    private JFXButton btnEliminarCategoria;
    @FXML
    private JFXButton btnEditarCategoria;
    @FXML
    private TableView<CategoriaProducto> tblCategoria;
    @FXML
    private TableColumn<CategoriaProducto, String> colCodigoCategoria;
    @FXML
    private TableColumn<CategoriaProducto, String> colDescCategoria;
    @FXML
    private JFXButton btnBuscarCategoria;
    @FXML
    private ComboBox<String> cmbCodigoCategoria;


    public enum Operacion{AGREGAR,GUARDAR,ELIMINAR,BUSCAR,ACTUALIZAR,CANCELAR,NINGUNO, SUMAR, RESTAR};
    public Operacion cancelar = Operacion.NINGUNO;
    
    // Variables para Inventario
    public Operacion tipoOperacionInventario= Operacion.NINGUNO;

    ObservableList<InventarioProductos> listaInventarioProductos;
    ObservableList<String> listaEstadoInventario;
    ObservableList<String> listaEstadoProveedor;
    ObservableList<String> listaEstadoCategorio;
    ObservableList<String> listaCodigoProducto;
    ObservableList<String> listaCodigoFiltro;
    ObservableList<String> listaFiltro;
    ObservableList<String> listaBuscar;

    int buscarCodigoEstado = 0;
    String buscarCodigoProveedor = "";
    String buscarCodigoCategoria = "";
    String codigoProducto = "";

    //Variables para Estado
    public Operacion tipoOperacionEstado= Operacion.NINGUNO; 
   
    ObservableList<EstadoProductos> listaEstadoProductos;
    ObservableList<CategoriaProducto> listaCategoria;
    ObservableList<String> listaCodigoEstadoProductos;
    ObservableList<String> listaCategoriaProducto;
    
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
    private ComboBox<String> txtProveedorInventario;
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
    private JFXButton btnBuscarInventario;
    @FXML
    private ComboBox<String> cmbFiltroCodigo;
    @FXML
    private ComboBox<String> cmbBuscar;
    
    @FXML
    private TableColumn<InventarioProductos, String> colCategoriaInventario;
    @FXML
    private ComboBox<String> cmbNombreCategoria;

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
    String passAction = "vacio";
    
    
    //Categoria
    public Operacion tipoOperacionCategoria = Operacion.NINGUNO; 
    String codigoCategoria = "";
    //========================================== CODIGO PARA VISTA INVENTARIO =============================================================
        
    public void limpiarText(){
        txtProveedorInventario.setValue("");
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
        txtProveedorInventario.setDisable(true);
        txtProductoInventario.setEditable(false);
        cmbNombreEstado.setDisable(true);
        btnEditarInventario.setDisable(false);
        btnEliminarInventario.setDisable(false);

    }
    
    public void activarTextInventario(){
        cmbCodigoProductoInventario.setDisable(false);
        txtProveedorInventario.setDisable(false);
        txtProductoInventario.setEditable(true);
        txtCostoNuevo.setEditable(true);
        txtCantidadInventario.setEditable(true);
        cmbNombreEstado.setDisable(false);
        cmbNombreCategoria.setDisable(false);        
    }
    
    
    @FXML
    private void cargarProductos(Event event) {
        iniciarInventario();
        activarControles();
        activarTextInventario();
        animacion.animacion(anchor1, anchor2);
    }
    
    
     public ObservableList<InventarioProductos> getInventario(){
        ArrayList<InventarioProductos> lista = new ArrayList();
        ArrayList<String> comboCodigoFiltro = new ArrayList();
        String sql = "{call Sp_ListInventarioProducto()}";
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
                            rs.getDouble("productoPrecio"),
                            rs.getString("categoriaNombre")                        
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
     
     
    public void cargarDatos(){
        tblInventario.setItems(getInventario());
        activarControles();
        activarTextInventario();
        colCodigoProductoInventario.setCellValueFactory(new PropertyValueFactory("productoId"));                
        colCantidadInventario.setCellValueFactory(new PropertyValueFactory("inventarioProductoCant"));        
        colProductoInventario.setCellValueFactory(new PropertyValueFactory("productoDesc"));        
        colCategoriaInventario.setCellValueFactory(new PropertyValueFactory("categoriaNombre"));        
        colPrecioInventario.setCellValueFactory(new PropertyValueFactory("productoPrecio"));        
        colProveedorInventario.setCellValueFactory(new PropertyValueFactory("proveedorNombre"));
        colEstadoInventario.setCellValueFactory(new PropertyValueFactory("estadoProductoDesc"));
        
        limpiarText();
       
        llenarComboEstado();
        llenarComboProveedor();
        llenarComboCategorio();
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
            String sql = "{call Sp_FindInventarioProveedor('"+proveedorName+"')}";
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
                            rs.getDouble("productoPrecio"),
                            rs.getString("categoriaNombre")  
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
        colCategoriaInventario.setCellValueFactory(new PropertyValueFactory("categoriaNombre"));        
        colPrecioInventario.setCellValueFactory(new PropertyValueFactory("productoPrecio"));        
        colProveedorInventario.setCellValueFactory(new PropertyValueFactory("proveedorNombre"));
        colEstadoInventario.setCellValueFactory(new PropertyValueFactory("estadoProductoDesc"));
        limpiarText();
       
                llenarComboEstado();
        llenarComboProveedor();
        llenarComboCategorio();
        cmbNombreEstado.setValue("");
        new AutoCompleteComboBoxListener(cmbFiltroCodigo);
        new AutoCompleteComboBoxListener(cmbNombreEstado);
        activarControles();
        activarTextInventario();
    }
    
         
    @FXML
    private void seleccionarElementosProductos(MouseEvent event) {
        int index = tblInventario.getSelectionModel().getSelectedIndex();
        try{
            
            cmbCodigoProductoInventario.setValue(colCodigoProductoInventario.getCellData(index));            
            txtCantidadInventario.setText(colCantidadInventario.getCellData(index).toString());            
            txtProductoInventario.setText(colProductoInventario.getCellData(index));            
            cmbNombreCategoria.setValue(colCategoriaInventario.getCellData(index));            
            txtCostoNuevo.setText(colPrecioInventario.getCellData(index).toString());            
            txtProveedorInventario.setValue(colProveedorInventario.getCellData(index));            
            cmbNombreEstado.setValue(colEstadoInventario.getCellData(index));                        
            
            
            codigoProducto = colCodigoProductoInventario.getCellData(index);
            cmbNombreEstado.setDisable(false);
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
        String sql= "{call Sp_ListEstadoProducto()}";
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
    
    
    public void llenarComboProveedor(){
        ArrayList<String> lista = new ArrayList();
        String sql= "{call Sp_ListProveedor()}";
            int x =0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x, rs.getString("proveedorNombre"));
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
        listaEstadoProveedor = FXCollections.observableList(lista);
        txtProveedorInventario.setItems(listaEstadoProveedor);
    }
        
    public void llenarComboCategorio(){
        ArrayList<String> lista = new ArrayList();
        String sql= "{call Sp_ListCategoriaProducto()}";
            int x =0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x, rs.getString("categoriaNombre"));
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
        listaEstadoCategorio = FXCollections.observableList(lista);
        cmbNombreCategoria.setItems(listaEstadoCategorio);
    }
        
        
     public void accionInventario(){
        switch(tipoOperacionInventario){
            case AGREGAR:
                tipoOperacionInventario = Operacion.GUARDAR;
                cmbFiltroCodigo.setDisable(true);
                cancelar = Operacion.CANCELAR;
                activarControles();
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
                        proveedorName= txtProveedorInventario.getValue();
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
                        proveedorName= txtProveedorInventario.getValue();
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
                        txtProductoInventario.setText(rs.getString("productoDesc"));
                        cmbNombreCategoria.setValue(rs.getString("categoriaNombre"));
                        txtCostoNuevo.setText(rs.getString("productoPrecio"));                        
                        txtProveedorInventario.setValue(rs.getString("proveedorNombre"));
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
                        proveedorName= txtProveedorInventario.getValue();
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
                        proveedorName= txtProveedorInventario.getValue();
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
            PreparedStatement sp = Conexion.getIntance().getConexion().prepareCall("{call Sp_FindEstadoNombre(?)}");
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
    
    public String buscarCodigoProveedor(String descripcionEstado){    
        try{
            PreparedStatement sp = Conexion.getIntance().getConexion().prepareCall("{call Sp_FindBuscarProveedoresNombre(?)}");
            sp.setString(1, descripcionEstado);
            ResultSet resultado = sp.executeQuery(); 
            
            while(resultado.next()){
            buscarCodigoProveedor = resultado.getString(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return buscarCodigoProveedor;
    }  
   
   
     public String buscarCodigoCategoria(String descripcionEstado){    
        try{
            PreparedStatement sp = Conexion.getIntance().getConexion().prepareCall("{call Sp_FindCategoriaProductosNombre(?)}");
            sp.setString(1, descripcionEstado);
            ResultSet resultado = sp.executeQuery(); 
            
            while(resultado.next()){
            buscarCodigoCategoria = resultado.getString(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return buscarCodigoCategoria;
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
                nuevoInventario.setProductoDesc(txtProductoInventario.getText());                
                nuevoInventario.setProveedorNombre(txtProveedorInventario.getValue());                
                nuevoInventario.setCategoriaNombre(cmbNombreCategoria.getValue());
                nuevoInventario.setProductoPrecio(Double.parseDouble(txtCostoNuevo.getText()));                                
                nuevoInventario.setInventarioProductoCant(Integer.parseInt(txtCantidadInventario.getText()));                
                nuevoInventario.setEstadoProductoDesc(cmbNombreEstado.getValue());

                proveedorName = txtProveedorInventario.getValue();
                   
                String sql = "{call Sp_AddInventario('"+nuevoInventario.getProductoId()+"','"+nuevoInventario.getProductoDesc()+"','"+buscarCodigoProveedor(nuevoInventario.getProveedorNombre())+"','"+buscarCodigoCategoria(nuevoInventario.getCategoriaNombre())+"','"+nuevoInventario.getProductoPrecio()+"','"+nuevoInventario.getInventarioProductoCant()+"','"+buscarCodigoEstado(nuevoInventario.getEstadoProductoDesc())+"')}";
                   System.out.println(sql);
                tipoOperacionInventario = Operacion.GUARDAR;
                accion(sql);     
                   
            }
        }else{
            tipoOperacionInventario = Operacion.AGREGAR;;
            accionInventario();
        }

    }
    
    
  
    private void btnSumar() {
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
                   nuevoInventario.setInventarioProductoCant(Double.parseDouble(txtCantidadInventario.getText()));

                   String sql = "{call Sp_SumarInventar('"+nuevoInventario.getProductoId()+"','"+ nuevoInventario.getInventarioProductoCant()+"')}";
                   tipoOperacionInventario = Operacion.SUMAR;
                   accion(sql);               
                   passAction = "Sumar";
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

                   String sql = "{call Sp_RestarInventar('"+nuevoInventario.getProductoId()+"','"+ nuevoInventario.getInventarioProductoCant()+"')}";
                   tipoOperacionInventario = Operacion.RESTAR;
                   passAction = "Restar";
                   accion(sql); 

                   accionInventario();
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

       nuevoInventario.setProductoDesc(txtProductoInventario.getText());

       nuevoInventario.setProductoPrecio(Double.parseDouble(txtCostoNuevo.getText()));
       nuevoInventario.setEstadoProductoDesc(cmbNombreEstado.getValue());

       String sql = "{call Sp_UpdateInventarioProducto('"+codigoProducto+"','"+nuevoInventario.getProductoDesc()+"','"+nuevoInventario.getProductoPrecio()+"','"+buscarCodigoEstado(nuevoInventario.getEstadoProductoDesc())+"')}";
       
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
            sql = "{call Sp_ListInventarioProducto()}";
        }else if(cmbFiltroCodigo.getValue().equals("PROVEEDOR")){
            sql = "{call Sp_ListInventarioProducto()}";
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
                    String sql = "{ call Sp_FindInventarioProducto('"+cmbBuscar.getValue()+"')}";
                    accion(sql);
            }else if(cmbFiltroCodigo.getValue().equals("NOMBRE")){
                    tipoOperacionInventario = Operacion.BUSCAR;
                    String sql = "{ call Sp_FindInventarioProductoNombre('"+cmbBuscar.getValue()+"')}";
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
        String sql = "{call Sp_ListEstadoProducto()}";
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
                    String sql = "{call Sp_AddEstadoProducto('"+nuevaEstado.getEstadoProductoDesc()+"')}";
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
        String sql = "{call Sp_UpdateEstadoProducto('"+codigoEstado+"','"+nuevaEstado.getEstadoProductoDesc()+"')}";
        accionEstado(sql);
    }
    
    
     @FXML
    private void btnEliminarEstado(MouseEvent event) {
        if(tipoOperacionEstado == Operacion.GUARDAR){
            tipoOperacionEstado = Operacion.CANCELAR;
            accionEstado();
        }else{
            String sql = "{call Sp_EliminarEstadoProducto('"+codigoEstado+"')}";
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
            String sql = "{ call Sp_FindEstadoNombre('"+cmbCodigoEstadoProductos.getValue()+"')}";
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
    
    //REPORTES
        public void imprimirReporteInventario(){
            try{
                Map parametros = new HashMap();

                String repuesta = "'ejemplo'";
                
                parametros.put("prueba", "'"+repuesta+"'");
                 org.ModuloCotizacion.report.GenerarReporte.mostrarReporte("ReporteInventario.jasper", "REPORTE DE INVENTARIO", parametros);
                

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
                    System.out.println(passAction);
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
    
    
    @FXML
    public void validarSuma(MouseEvent event){
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
                         btnSumar();
                 
                   
                    passUser = true;
                    System.out.println(passAction);
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
        cargarCombo();
        llenarComboEstado();
        llenarComboProveedor();
        llenarComboCategorio();
        
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
         String menu = "org/ModuloCotizacion/view/menuPrincipal.fxml";
        cambioScene.Cambio(menu,(Stage) anchor.getScene().getWindow());
    }
    
    


    @FXML
    private void btnProveedores(MouseEvent event) throws IOException {
        menu.prefsRegresar.put("regresar", "inventario");
        String menu1 = "org/ModuloCotizacion/view/ProveedoresView.fxml";
        cambioScene.Cambio(menu1,(Stage) anchor.getScene().getWindow());
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
    
   // ================================================== vista categoría
    
     //========================================== CODIGO PARA VISTA ESTADO PRODUCTO ========================================================

    public void limpiarTextCategoria(){
        txtCodigoCategoria.setText("");
        txtDescripcionCategoria.setText("");
    }
    
    public void desactivarControlesCategoria(){    
        btnEliminarCategoria.setDisable(true);
        btnEditarCategoria.setDisable(true);
    }
    
    
    public void desactivarTextCategoria(){
        txtCodigoCategoria.setEditable(false);
        txtDescripcionCategoria.setEditable(false);
    }
    
    public void activarTextCategoria(){
        txtCodigoCategoria.setEditable(true);
        txtDescripcionCategoria.setEditable(true);
    }
    
    
     public ObservableList<CategoriaProducto> getCategorias(){
        ArrayList<CategoriaProducto> lista = new ArrayList();
        ArrayList<String> comboCodigoFiltro = new ArrayList();
        String sql = "{call Sp_ListCategoriaProducto()}";
        int x=0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new CategoriaProducto(
                            rs.getString("categoriaId"),
                            rs.getString("categoriaNombre")
                ));
                comboCodigoFiltro.add(x, rs.getString("categoriaId"));
                x++;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        listaCategoriaProducto = FXCollections.observableList(comboCodigoFiltro);
        cmbCodigoCategoria.setItems(listaCategoriaProducto);
        
        return listaCategoria = FXCollections.observableList(lista);
    }
    
   
    public void cargarDatosCategoria(){
        tblCategoria.setItems(getCategorias());
        colCodigoCategoria.setCellValueFactory(new PropertyValueFactory("categoriaId"));
        colDescCategoria.setCellValueFactory(new PropertyValueFactory("categoriaNombre"));
        limpiarTextCategoria();
        new AutoCompleteComboBoxListener(cmbCodigoCategoria);
        desactivarControlesCategoria();
        desactivarTextCategoria();
        tipoOperacionCategoria = Operacion.CANCELAR;
        accionCategoria();
    }
        @FXML
    private void seleccionarElementosCategoria(MouseEvent event) {
        int index = tblCategoria.getSelectionModel().getSelectedIndex();
        try{
            txtCodigoCategoria.setText(colCodigoCategoria.getCellData(index));
            txtDescripcionCategoria.setText(colDescCategoria.getCellData(index));
            
            
            btnEliminarCategoria.setDisable(false);
            btnEditarCategoria.setDisable(false);
            
            codigoCategoria = colCodigoCategoria.getCellData(index);
            activarTextCategoria();
        }catch(Exception e){
        }
    }


    
    
    public void accionCategoria(){
        switch(tipoOperacionCategoria){
            case AGREGAR:
                tipoOperacionCategoria = Operacion.GUARDAR;
                cancelar = Operacion.CANCELAR;
                desactivarControlesCategoria();
                btnAgregarCategoria.setText("GUARDAR");
                btnEliminarCategoria.setText("CANCELAR");
                btnEliminarCategoria.setDisable(false);
                activarTextCategoria();
                cmbCodigoCategoria.setDisable(true);
                btnBuscarCategoria.setDisable(true);
                limpiarTextCategoria();
                break;
            case CANCELAR:
                tipoOperacionCategoria = Operacion.NINGUNO;
                desactivarControlesCategoria();
                desactivarTextCategoria();
                btnAgregarCategoria.setText("AGREGAR");
                btnEliminarCategoria.setText("ELIMINAR");
                limpiarTextCategoria();
                cmbCodigoCategoria.setDisable(false);
                btnBuscarCategoria.setDisable(false);
                cancelar = Operacion.NINGUNO;
                break;
        }
    }
    
    
    public void accionCategoria(String sql){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        PreparedStatement ps;
        ResultSet rs;
        Notifications noti = Notifications.create();
        ButtonType buttonTypeSi = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");
        switch(tipoOperacionCategoria){
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
                        tipoOperacionCategoria = Operacion.CANCELAR;
                        accionCategoria();
                        cargarDatosCategoria();
                        
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL AGREGAR");
                        noti.text("HA OCURRIDO UN ERROR AL GUARDAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionCategoria = Operacion.CANCELAR;
                        accionCategoria();
                    }
                }else{
                    
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA AGREGADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionCategoria = Operacion.CANCELAR;
                    accionCategoria();
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
                        cargarDatosCategoria();
                        tipoOperacionCategoria = Operacion.CANCELAR;
                        accionCategoria();
                        
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        
                        
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ELIMINAR");
                        noti.text("HA OCURRIDO UN ERROR AL ELIMINAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionCategoria = Operacion.CANCELAR;
                        accionCategoria();
                    }
                }else{
                     noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA ELIMINADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionCategoria = Operacion.CANCELAR;
                    accionCategoria();
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
                        tipoOperacionCategoria = Operacion.CANCELAR;
                        accionCategoria();
                        cargarDatosCategoria();
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ACTUALIZAR");
                        noti.text("HA OCURRIDO UN ERROR AL ACTUALIZAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionCategoria = Operacion.CANCELAR;
                        accionCategoria();
                    }
                }else{
                    
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA ACTUALIZAR EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionCategoria = Operacion.CANCELAR;
                    accionCategoria();
                }
                break;
            case BUSCAR:
                 try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    rs = ps.executeQuery();
                    
                    while(rs.next()){
                        txtCodigoCategoria.setText(rs.getString("categoriaId"));
                        txtDescripcionCategoria.setText(rs.getString("categoriaNombre"));
                        codigoCategoria = rs.getString("categoriaId");
                        
                    }                    
                    if(rs.first()){
                        for(int i=0; i<tblCategoria.getItems().size(); i++){
                            if(colCodigoCategoria.getCellData(i) == codigoCategoria){
                                tblCategoria.getSelectionModel().select(i);
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
                        btnEditarCategoria.setDisable(false);
                        btnEliminarCategoria.setDisable(false);
                        activarTextCategoria();
                    }else{
                         
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL BUSCAR");
                        noti.text("NO SE HA ENCONTRADO EN LA BASE DE DATOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionCategoria = Operacion.CANCELAR;
                        accionCategoria();
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
                    tipoOperacionCategoria = Operacion.CANCELAR;
                    accionCategoria();
                }
                break;
        }
    }
    
        @FXML
    private void btnAgregarCategoria(MouseEvent event) {
        if(tipoOperacionCategoria == Operacion.GUARDAR){
            if(txtCodigoCategoria.getText().isEmpty() || txtDescripcionCategoria.getText().isEmpty()){
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
                    CategoriaProducto nuevaCategoria = new CategoriaProducto();
                    nuevaCategoria.setCategoriaId(txtCodigoCategoria.getText());
                    nuevaCategoria.setCategoriaNombre(txtDescripcionCategoria.getText());
                    String sql = "{call SpAddCategoriaProducto('"+nuevaCategoria.getCategoriaId()+"','"+nuevaCategoria.getCategoriaNombre()+"')}";
                    accionCategoria(sql);
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
             tipoOperacionCategoria = Operacion.AGREGAR;
                accionCategoria();
        }
    }

    @FXML
    private void btnEliminarCategoria(MouseEvent event) {
        if(tipoOperacionCategoria == Operacion.GUARDAR){
            tipoOperacionCategoria = Operacion.CANCELAR;
            accionCategoria();
        }else{
            String sql = "{call Sp_DeleteCategoriaProducto('"+codigoCategoria+"')}";
            tipoOperacionCategoria = Operacion.ELIMINAR;
            accionCategoria(sql);
        }
    }

    @FXML
    private void btnEditarCategoria(MouseEvent event) {
          CategoriaProducto nuevaCategoria = new CategoriaProducto();
        nuevaCategoria.setCategoriaId(txtCodigoCategoria.getText());
        nuevaCategoria.setCategoriaNombre(txtDescripcionCategoria.getText());
        
        tipoOperacionCategoria = Operacion.ACTUALIZAR;
        String sql = "{call Sp_UpdateCategoriaProducto('"+codigoCategoria+"','"+nuevaCategoria.getCategoriaId()+"','"+nuevaCategoria.getCategoriaNombre()+"')}";
        accionCategoria(sql);
    }


    
    public void buscarCategoria(){
        if(cmbCodigoCategoria.getValue().equals("")){
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR");
            noti.text("El CAMPO DE CÓDIGO ESTA VACÍO");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
        }else{
            tipoOperacionCategoria = Operacion.BUSCAR;
            String sql = "{ call Sp_FindCategoriaProducto('"+cmbCodigoCategoria.getValue()+"')}";
            accionCategoria(sql);
        }
    }
    
    @FXML
    private void btnBuscarCategoria(MouseEvent event) {
        buscarCategoria();
    }

    
    @FXML
    private void cargarCategoria(Event event) {
        cargarDatosCategoria();
        animacion.animacion(anchor3, anchor4);
    }
    
    private void atajosCategoria(KeyEvent event) {
        if(cmbCodigoCategoria.isFocused()){
            if(event.getCode() == KeyCode.ENTER){
                buscarCategoria();
            }
        }
    }
    
    
    
}