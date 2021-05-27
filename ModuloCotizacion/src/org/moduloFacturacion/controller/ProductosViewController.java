package org.moduloFacturacion.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.ButtonBar;
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
import org.moduloFacturacion.bean.CategoriaProducto;
import org.moduloFacturacion.bean.Creditos;
import org.moduloFacturacion.bean.Productos;
import org.moduloFacturacion.bean.ValidarStyle;
import org.moduloFacturacion.db.Conexion;


public class ProductosViewController implements Initializable {
    CambioScene cambioScene = new CambioScene();
    
    ObservableList<CategoriaProducto> listaCategoria;
    ObservableList<String> listaCodigoCategoria;
    
    ObservableList<Productos> listaProductos;
    ObservableList<String> listaCodigoProductos;
    ObservableList<String> listaCategoriaProductos;
    ObservableList<String> listaProveedoresProductos;
    ObservableList<String> listaFiltro;
    ObservableList<String> listaBuscar;
    ObservableList<String> listaFiltroCategoria;
    ObservableList<String> listaBuscarCategoria;
    ObservableList<String> listaTipoProducto;

    
    double costoProducto;
    Image imgError = new Image("org/moduloFacturacion/img/error.png");
    Image imgCorrecto= new Image("org/moduloFacturacion/img/correcto.png");
    @FXML
    private JFXTextField txtCodigoProducto;
    @FXML
    private JFXTextField txtNombreProducto;
    @FXML
    private ComboBox<String> cmbCategoriaProducto;
    @FXML
    private ComboBox<String> cmbProveedorProducto;
    @FXML
    private ComboBox<String> cmbTipoProducto;
    @FXML
    private JFXButton btnAgregar;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private TableView<Productos> tableProductos;
    @FXML
    private TableColumn<Productos, String> colCodigoProductos;
    @FXML
    private TableColumn<Productos, String> colNombreProductos;
    @FXML
    private TableColumn<Productos, String> colCategoriaProductos;
    @FXML
    private TableColumn<Productos, Double> colPrecioProductos;
    @FXML
    private TableColumn<Productos, String> colTipoProducto;
    @FXML
    private JFXTextField txtCostoProducto;
    @FXML
    private TableColumn<Productos, Double> colCostoProductos;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private TableColumn<Productos, String> colProveedorProductos;
    @FXML
    private JFXTextField txtPrecioProducto;
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
    private Pane regresarbtn;
    @FXML
    private ComboBox<String> cmbFiltroProductos;
    @FXML
    private ComboBox<String> cmbBuscar;
    @FXML
    private ComboBox<String> cmbFiltroCategoriaProducto;
    @FXML
    private ComboBox<String> cmbBuscarCategoria;
    @FXML
    private ComboBox<String> cmbFiltrar;
    @FXML
    private JFXButton btnFiltrar;
    @FXML
    private JFXTextField txtcantidad;
    @FXML
    private JFXButton btnAgregar1;
    @FXML
    private JFXTextField txtFactura;


   
    public enum Operacion{AGREGAR,GUARDAR,ELIMINAR,BUSCAR,ACTUALIZAR,CANCELAR,NINGUNO,FILTRAR};
    
    public Operacion tipoOperacionCategoria= Operacion.NINGUNO;
    public Operacion cancelar = Operacion.NINGUNO;
    
    public Operacion tipoOperacionProducto = Operacion.NINGUNO;
    
    String codigo;
    String codigoProducto;
    
    @FXML
    private AnchorPane anchor;
    @FXML
    private Pane buttonInicio;
    @FXML
    private Pane buttonProveedor;
    @FXML
    private JFXTextField txtNombreCategoria;
    @FXML
    private TableView<CategoriaProducto> tableCategoria;
    @FXML
    private TableColumn<CategoriaProducto, String> colCodigoCategoria;
    @FXML
    private TableColumn<CategoriaProducto, String> colNombreCategoria;
    @FXML
    private JFXButton btnAgregarCategoria;
    @FXML
    private JFXButton btnEditarCategoria;
    @FXML
    private JFXButton btnEliminarCategoria;
    @FXML
    private JFXButton btnBuscarCategoria;
    @FXML
    private JFXTextField txtCodigoCategoria;
    
    MenuPrincipalContoller menu = new MenuPrincipalContoller();
    ValidarStyle validar = new ValidarStyle();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(menu.prefsRegresarProductos.get("regresarProducto", "root"));
        if(menu.prefsRegresarProductos.get("regresarProducto", "root").equals("menu")){
            regresarbtn.setVisible(false);
        }else{
            regresarbtn.setVisible(true);
            buttonInicio.setVisible(false);
        }
        validar.validarView(menu.prefs.get("dark", "root"), anchor);
       iniciarProducto();
        
    }    
    
       
    @FXML
    private void regresar(MouseEvent event) throws IOException {
         String menu1 ="";
         
        if(menu.prefsRegresarProductos.get("regresarProducto", "root").equals("menu")){
            menu1= "org/moduloFacturacion/view/menuPrincipal.fxml";
        }else{
              
            if(menu.prefsRegresarProductos.get("regresarProducto", "root").equals("inventario")){
                menu1 = "org/moduloFacturacion/view/InventarioView.fxml";
            }else{
                if(menu.prefsRegresarProductos.get("regresarProducto", "root").equals("facturacion")){
                    menu1 = "org/moduloFacturacion/view/FacturacionView.fxml";
                }
            }
        }
        cambioScene.Cambio(menu1,(Stage) anchor.getScene().getWindow());
    }

    

    @FXML
    private void buttonProveedor(MouseEvent event) throws IOException {
        menu.prefsRegresar.put("regresar","productos");
        String proveedores = "org/moduloFacturacion/view/ProveedoresView.fxml";
        cambioScene.Cambio(proveedores,(Stage) anchor.getScene().getWindow());
    }
    
    //EVENTOS DE LA VISTA PRODUCTOS --------------------------------------------------------------------------------------------------------------------------------
    public void iniciarProducto(){
         animacion.animacion(anchor1, anchor2);
        Tooltip toolInicio = new Tooltip("Volver a Inicio");
        Tooltip.install(buttonInicio, toolInicio);
        Tooltip toolProveedores = new Tooltip("Abrir Proveedores");
        Tooltip.install(buttonProveedor, toolProveedores);
        cargarDatos();
       
        tipoOperacionProducto = Operacion.CANCELAR;
        accion();
        cargarCombo();
        new AutoCompleteComboBoxListener(cmbBuscar);
        new AutoCompleteComboBoxListener(cmbFiltroProductos);
    }
    
       @FXML
    private void cargarProductos(Event event) {
        iniciarProducto();
    }
    
     public void limpiarText(){
         txtCodigoProducto.setText("");
         txtNombreProducto.setText("");
         cmbCategoriaProducto.setValue("");
         cmbProveedorProducto.setValue("");
         txtPrecioProducto.setText("");
         txtCostoProducto.setText("");
         cmbFiltroProductos.setValue("");
         cmbBuscar.setValue("");
         cmbTipoProducto.setValue("");
         txtcantidad.setText("");
    }
     
    public void desactivarControles(){
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);
    }
    
    public void activarControles(){
        btnEditar.setDisable(false);
        btnEliminar.setDisable(false);
    }
    
    public void desactivarText(){
        txtCodigoProducto.setEditable(false);
        txtNombreProducto.setEditable(false);
        txtPrecioProducto.setEditable(false);
        txtCostoProducto.setEditable(false);
        cmbCategoriaProducto.setDisable(true);
        cmbProveedorProducto.setDisable(true);
        cmbTipoProducto.setDisable(true);
        
    }
    
    public void activarText(){
        txtPrecioProducto.setEditable(true);
        txtCostoProducto.setEditable(true);
        txtCodigoProducto.setEditable(true);
        txtNombreProducto.setEditable(true);
        cmbCategoriaProducto.setDisable(false);
        cmbProveedorProducto.setDisable(false);
        cmbTipoProducto.setDisable(false);
    }
    
    String proveedor = "nO CARGO DATOS";
    
    public ObservableList<Productos> getProductos(){
        ArrayList<Productos> lista = new ArrayList();
        ArrayList<String> comboCodigo = new ArrayList();
        String sql = "{call SpListarProductos()}";
        int x=0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new Productos(
                            rs.getString("productoId"),
                            rs.getString("productoDesc"),
                            rs.getString("proveedorNombre"),
                            rs.getString("categoriaNombre"),
                            rs.getDouble("precioCosto"),
                            rs.getDouble("productoPrecio"),
                            rs.getString("tipoProdDesc")
                ));
                comboCodigo.add(x, rs.getString("productoId"));
                x++;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        listaCodigoProductos = FXCollections.observableList(comboCodigo);
        return listaProductos = FXCollections.observableList(lista);
    }
    
    public void cargarDatos(){
        tableProductos.setItems(getProductos());
        colCodigoProductos.setCellValueFactory(new PropertyValueFactory("productoId"));
        colNombreProductos.setCellValueFactory(new PropertyValueFactory("productoDesc"));
        colCategoriaProductos.setCellValueFactory(new PropertyValueFactory("categoriaNombre"));
        colProveedorProductos.setCellValueFactory(new PropertyValueFactory("proveedorNombre"));
        colCostoProductos.setCellValueFactory(new PropertyValueFactory("precioCosto"));
        colPrecioProductos.setCellValueFactory(new PropertyValueFactory("productoPrecio"));
        colTipoProducto.setCellValueFactory(new PropertyValueFactory("tipoProdDesc"));
        limpiarText();
        desactivarControles();
        desactivarText();
        llenarComboCategoria();
        llenarComboProveedores();
        llenarComboTipoProductos();
        
        cmbCategoriaProducto.setValue("");
        cmbProveedorProducto.setValue("");
        new AutoCompleteComboBoxListener(cmbCategoriaProducto);
        new AutoCompleteComboBoxListener(cmbProveedorProducto);
    }
    
    public ObservableList<Productos> getProductosPorProveedor(){
        ArrayList<Productos> lista = new ArrayList();
        ArrayList<String> comboCodigo = new ArrayList();
        String sql = "{call SpListarProductosPorProveedor('"+cmbFiltrar.getValue()+"')}";
        int x=0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new Productos(
                            rs.getString("productoId"),
                            rs.getString("productoDesc"),
                            rs.getString("proveedorNombre"),
                            rs.getString("categoriaNombre"),
                            rs.getDouble("precioCosto"),
                            rs.getDouble("productoPrecio"),
                            rs.getString("tipoProdDesc")
                ));
                comboCodigo.add(x, rs.getString("productoId"));
                x++;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        listaCodigoProductos = FXCollections.observableList(comboCodigo);
        return listaProductos = FXCollections.observableList(lista);
    }
        
        public void cargarDatosPorProveedor(){
        tableProductos.setItems(getProductosPorProveedor());
        colCodigoProductos.setCellValueFactory(new PropertyValueFactory("productoId"));
        colNombreProductos.setCellValueFactory(new PropertyValueFactory("productoDesc"));
        colCategoriaProductos.setCellValueFactory(new PropertyValueFactory("categoriaNombre"));
        colProveedorProductos.setCellValueFactory(new PropertyValueFactory("proveedorNombre"));
        colCostoProductos.setCellValueFactory(new PropertyValueFactory("precioCosto"));
        colPrecioProductos.setCellValueFactory(new PropertyValueFactory("productoPrecio"));
        colTipoProducto.setCellValueFactory(new PropertyValueFactory("tipoProdDesc"));
        limpiarText();
        desactivarControles();
        desactivarText();
        llenarComboCategoria();
        llenarComboProveedores();
        llenarComboTipoProductos();
        cmbCategoriaProducto.setValue("");
        cmbProveedorProducto.setValue("");
        new AutoCompleteComboBoxListener(cmbCategoriaProducto);
        new AutoCompleteComboBoxListener(cmbProveedorProducto);
    }
        
          public ObservableList<Productos> getProductosPorProveedor2(){
        ArrayList<Productos> lista = new ArrayList();
        ArrayList<String> comboCodigo = new ArrayList();
        String sql = "{call SpListarProductosPorProveedor('"+proveedor+"')}";
        int x=0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new Productos(
                            rs.getString("productoId"),
                            rs.getString("productoDesc"),
                            rs.getString("proveedorNombre"),
                            rs.getString("categoriaNombre"),
                            rs.getDouble("precioCosto"),
                            rs.getDouble("productoPrecio"),
                            rs.getString("tipoProdDesc")
                ));
                comboCodigo.add(x, rs.getString("productoId"));
                x++;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        listaCodigoProductos = FXCollections.observableList(comboCodigo);
        return listaProductos = FXCollections.observableList(lista);
    }
        
        public void cargarDatosPorProveedor2(){
        tableProductos.setItems(getProductosPorProveedor2());
        colCodigoProductos.setCellValueFactory(new PropertyValueFactory("productoId"));
        colNombreProductos.setCellValueFactory(new PropertyValueFactory("productoDesc"));
        colCategoriaProductos.setCellValueFactory(new PropertyValueFactory("categoriaNombre"));
        colProveedorProductos.setCellValueFactory(new PropertyValueFactory("proveedorNombre"));
        colCostoProductos.setCellValueFactory(new PropertyValueFactory("precioCosto"));
        colPrecioProductos.setCellValueFactory(new PropertyValueFactory("productoPrecio"));
        colTipoProducto.setCellValueFactory(new PropertyValueFactory("tipoProdDesc"));
        limpiarText();
        desactivarControles();
        desactivarText();
        llenarComboCategoria();
        llenarComboProveedores();
        llenarComboTipoProductos();
        cmbCategoriaProducto.setValue("");
        cmbProveedorProducto.setValue("");
        new AutoCompleteComboBoxListener(cmbCategoriaProducto);
        new AutoCompleteComboBoxListener(cmbProveedorProducto);
    }
        public void buscarProducto(){

            String sql = "{call SpListarProductosPorProveedor('"+cmbFiltrar.getValue()+"')}";
            accion(sql);
            PreparedStatement ps;
            ResultSet rs;
            try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    rs = ps.executeQuery();
                    int numero=0;
                    while(rs.next()){
                        txtCodigoProducto.setText(rs.getString("productoId"));
                        txtNombreProducto.setText(rs.getString("productoDesc"));
                        cmbCategoriaProducto.setValue(rs.getString("categoriaNombre"));
                        cmbProveedorProducto.setValue(rs.getString("proveedorNombre"));
                        txtCostoProducto.setText(rs.getString("precioCosto"));
                        txtPrecioProducto.setText(rs.getString("productoPrecio"));
                        codigoProducto = rs.getString("productoId");
                        cmbTipoProducto.setValue("tipoProdDesc");
                        
                    }    
                    cargarDatosPorProveedor();
                    if(rs.first()){
                        for(int i=0; i<tableProductos.getItems().size(); i++){
                            if(colCodigoProductos.getCellData(i) == codigoProducto){
                                tableProductos.getSelectionModel().select(i);
                                break;
                            }
                        }
                    }else{
                        Notifications noti = Notifications.create();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL BUSCAR");
                        noti.text("NO SE HA ENCONTRADO EN LA BASE DE DATOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionProducto = Operacion.CANCELAR;
                    }
                }catch(SQLException ex){
                    ex.printStackTrace();
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR AL BUSCAR");
                    noti.text("HA OCURRIDO UN ERROR EN LA BASE DE DATOS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionProducto = Operacion.CANCELAR;
                }
        } 
        
     @FXML
    private void seleccionarElementosProductos(MouseEvent event) {
        int index = tableProductos.getSelectionModel().getSelectedIndex();
        try{
            txtCodigoProducto.setText(colCodigoProductos.getCellData(index).toString());
            txtNombreProducto.setText(colNombreProductos.getCellData(index));
            cmbCategoriaProducto.setValue(colCategoriaProductos.getCellData(index));
            cmbProveedorProducto.setValue(colProveedorProductos.getCellData(index));
            txtCostoProducto.setText(colCostoProductos.getCellData(index).toString());
            txtPrecioProducto.setText(colPrecioProductos.getCellData(index).toString());
            cmbTipoProducto.setValue(colTipoProducto.getCellData(index).toString());
            codigoProducto = colCodigoProductos.getCellData(index);
            activarControles();
             activarText();
            cmbCategoriaProducto.setDisable(true);
            cmbProveedorProducto.setDisable(true);
        }catch(Exception ex){
            ex.printStackTrace();
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR AL CARGAR DATOS");
            noti.text("Error al cargar la base de datos");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
           
        }
    }
    
    public void llenarComboCategoria(){
        ArrayList<String> lista = new ArrayList();
        String sql= "{call SpListarCategoriaProductos()}";
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
        listaCategoriaProductos = FXCollections.observableList(lista);
        cmbCategoriaProducto.setItems(listaCategoriaProductos);
        new AutoCompleteComboBoxListener(cmbCategoriaProducto);

    }
    
    public void llenarComboProveedores(){
        ArrayList<String> lista = new ArrayList();
        String sql= "{call SpListarProveedores()}";
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
        listaProveedoresProductos = FXCollections.observableList(lista);
        cmbProveedorProducto.setItems(listaProveedoresProductos);
        cmbFiltrar.setItems(listaProveedoresProductos);
        new AutoCompleteComboBoxListener(cmbProveedorProducto);
        new AutoCompleteComboBoxListener(cmbFiltrar);
    }

    
        
    public void llenarComboTipoProductos(){
        ArrayList<String> lista = new ArrayList();
        String sql= "{call SpListarTipoProductos()}";
            int x =0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x, rs.getString("tipoProdDesc"));
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
        listaTipoProducto = FXCollections.observableList(lista);
        cmbTipoProducto.setItems(listaTipoProducto);
        new AutoCompleteComboBoxListener(cmbTipoProducto);
        
    }
    
    
    public void accion(){
        switch(tipoOperacionProducto){
            case AGREGAR:
                tipoOperacionProducto = Operacion.GUARDAR;
                cmbFiltroProductos.setDisable(true);
                cmbBuscar.setDisable(true);
                cancelar = Operacion.CANCELAR;
                desactivarControles();
                btnAgregar.setText("GUARDAR");
                btnEliminar.setText("CANCELAR");
                btnBuscar.setDisable(true);
                btnEliminar.setDisable(false);
                activarText();
                limpiarText();
                break;
            case CANCELAR:
                tipoOperacionProducto = Operacion.NINGUNO;
                desactivarControles();
                desactivarText();
                btnBuscar.setDisable(false);
                btnAgregar.setText("AGREGAR");
                btnEliminar.setText("ELIMINAR");
                limpiarText();
                cmbFiltroProductos.setDisable(false);
                cmbBuscar.setDisable(false);
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
        switch(tipoOperacionProducto){
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
                        String sql1 = "{call SpAgregarInventarioProductos('"+txtcantidad.getText()+"','"+ txtCodigoProducto.getText()+"','"+1+"')}";
                        PreparedStatement ps1 = Conexion.getIntance().getConexion().prepareCall(sql1);
                        ps1.execute();
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("OPERACIÓN EXITOSA");
                        noti.text("SE HA AGREGADO EXITOSAMENTE EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        buscarCredito();
                        proveedor = cmbProveedorProducto.getValue();
                        cargarDatosPorProveedor2();
                        tipoOperacionProducto = Operacion.CANCELAR;
                        accion();
                        
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL AGREGAR");
                        noti.text("HA OCURRIDO UN ERROR AL GUARDAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionProducto = Operacion.CANCELAR;
                        accion();
                    }
                }else{
                    
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA AGREGADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionProducto = Operacion.CANCELAR;
                    accion();
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
                        proveedor = cmbProveedorProducto.getValue();
                        cargarDatosPorProveedor2();
                        tipoOperacionProducto = Operacion.CANCELAR;
                        accion();
                        
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        
                        
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ELIMINAR");
                        noti.text("HA OCURRIDO UN ERROR AL ELIMINAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionProducto = Operacion.CANCELAR;
                        accion();
                    }
                }else{
                     noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA ELIMINADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionProducto = Operacion.CANCELAR;
                    accion();
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
                        proveedor = cmbProveedorProducto.getValue();
                        cargarDatosPorProveedor2();
                        tipoOperacionProducto = Operacion.CANCELAR;
                        accion();
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ACTUALIZAR");
                        noti.text("HA OCURRIDO UN ERROR AL ACTUALIZAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionProducto = Operacion.CANCELAR;
                        accion();
                    }
                }else{
                    
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA ACTUALIZAR EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionProducto = Operacion.CANCELAR;
                    accion();
                }
                break;
            case BUSCAR:
                 try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    rs = ps.executeQuery();
                    int codigo=0;
                    while(rs.next()){
                        txtCodigoProducto.setText(rs.getString("productoId"));
                        txtNombreProducto.setText(rs.getString("productoDesc"));
                        cmbCategoriaProducto.setValue(rs.getString("categoriaNombre"));
                        cmbProveedorProducto.setValue(rs.getString("proveedorNombre"));
                        txtCostoProducto.setText(rs.getString("precioCosto"));
                        txtPrecioProducto.setText(rs.getString("productoPrecio"));
                        cmbTipoProducto.setValue(rs.getString("tipoProdDesc"));
                        codigoProducto = rs.getString("productoId");
                        
                    }                    
                    if(rs.first()){
                        for(int i=0; i<tableProductos.getItems().size(); i++){
                            if(colCodigoProductos.getCellData(i) == codigoProducto){
                                tableProductos.getSelectionModel().select(i);
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
                        activarText();
                        cmbCategoriaProducto.setDisable(true);
                        cmbProveedorProducto.setDisable(true);
                    }else{
                         
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL BUSCAR");
                        noti.text("NO SE HA ENCONTRADO EN LA BASE DE DATOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionProducto = Operacion.CANCELAR;
                        accion();
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
                    tipoOperacionProducto = Operacion.CANCELAR;
                    accion();
                }
                break;
            case FILTRAR:
                 try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    rs = ps.executeQuery();
                    int numero=0;
                                     
                    if(rs.first()){
                       for(int i=0; i<tableProductos.getItems().size(); i++){
                            if(colCodigoProductos.getCellData(i) == codigoProducto){
                                tableProductos.getSelectionModel().select(i);
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
                    }else{
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL BUSCAR");
                        noti.text("NO SE HA ENCONTRADO EN LA BASE DE DATOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionProducto = Operacion.CANCELAR;
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
                    tipoOperacionProducto = Operacion.CANCELAR;
                }
                break; 
        }
    }
    
        @FXML
    public void buscarPorProveedor(){
        try{
            if(cmbFiltrar.getValue() == null){
                          Notifications noti = Notifications.create();
                          noti.graphic(new ImageView(imgError));
                          noti.title("ERROR");
                          noti.text("SELECCIONE UN PROVEEDOR PARA PODER FILTRAR");
                          noti.position(Pos.BOTTOM_RIGHT);
                          noti.hideAfter(Duration.seconds(4));
                          noti.darkStyle();   
                          noti.show();
              }else{
                  tipoOperacionProducto = Operacion.FILTRAR;
                  cargarDatosPorProveedor();

                }  
            }catch(Exception e){
               e.printStackTrace();
            }
    }
    
    private void validarCodigoProducto(KeyEvent event) {
        if(tipoOperacionProducto == Operacion.GUARDAR){
            
                if(txtCodigoProducto.getText().matches(".*[a-z].*") || txtCodigoProducto.getText().matches(".*[A-Z].*")){
                    btnAgregar.setDisable(true);
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE CÓDIGO NO PUEDE LLEVAR LETRAS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                }else{
                    btnAgregar.setDisable(false);
                }
              
        }else{
            
                if(txtCodigoProducto.getText().matches(".*[a-z].*") || txtCodigoProducto.getText().matches(".*[A-Z].*")){
                    btnEditar.setDisable(true);
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE CÓDIGO NO PUEDE LLEVAR LETRAS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                }else{
                    btnEditar.setDisable(false);
                    
                }
            
        }
    }

    
    
     @FXML
    private void validarPrecioProducto(KeyEvent event) {
        if(tipoOperacionProducto == Operacion.GUARDAR){
            
                if(txtPrecioProducto.getText().matches(".*[a-z].*") || txtPrecioProducto.getText().matches(".*[A-Z].*") ){
                    btnAgregar.setDisable(true);
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE CÓDIGO NO PUEDE LLEVAR LETRAS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                }else{
                    btnAgregar.setDisable(false);
                }
              
        }else{
            
                if(txtPrecioProducto.getText().matches(".*[a-z].*")|| txtPrecioProducto.getText().matches(".*[A-Z].*")){
                    btnEditar.setDisable(true);
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE CÓDIGO NO PUEDE LLEVAR LETRAS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                }else{
                    btnEditar.setDisable(false);
                    
                }
            
        }
    }
    
      @FXML
  public void validarNumeroProductos(KeyEvent event){
              ArrayList<String> lista = new ArrayList();
        String sql = "{call SpValidarProducto('"+txtCodigoProducto.getText()+"')}";                        
        int x=0;
        String id = "";
            try{
                PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x, rs.getString("productoId"));
                id = rs.getString("productoId");
                 x++;
            }
            
            if(id != ""){
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("NUMERO DE PRODUCTO INVALIDO");
                noti.text("Por favor verifica que el numero de producto sea correcto");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
  }

    public String verficarCategoria(String categoria){
        String sql = "{call spVerificarCategoria('"+categoria+"')}";
        String codigoCategoria="";
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                codigoCategoria = rs.getString("categoriaId");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return codigoCategoria;
    }
    
    public String verificarProveedores(String proveedor){
        
        String sql = "{call spVerificarProveedores('"+proveedor+"')}";
        String codigoProveedor="";
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                codigoProveedor = rs.getString("proveedorId");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return codigoProveedor;
        
    }
    public void buscarCredito(){
        String noFac = txtFactura.getText();
        
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
    
       
    public void actualizarCredito(String nofac, double montoFac){

            double montoTotal =montoFac+Double.parseDouble(txtcantidad.getText())*Double.parseDouble(txtCostoProducto.getText());
        
            double costoFinal = Double.parseDouble(txtcantidad.getText())*Double.parseDouble(txtCostoProducto.getText());
            
            String sqlDetalle = "{call SpAgregarCreditoDetalleBackUp('"+txtCodigoProducto.getText()+"','"+txtcantidad.getText()+"','"+costoFinal+"')}";
            String sqlTransferirBackup = "{call SpAgregarCreditoDetalle()}";
            String sqlEliminarBackup = "{call SpEliminarBackupCredito()}";

     
            Integer tipo = 1;
            Integer documento = 3;
            String sql = "call SpActualizarCreditoInventario('"+montoTotal+"','"+nofac+"')";
            String sqlUpdate = "call SpUpdateDetalleCredito('"+nofac+"')";
            String sqlCardex = "{call SpAgregarCardexFacUpdate('"+txtNombreProducto.getText()+"','"+tipo+"','"+txtcantidad.getText()+"','"+txtFactura.getText()+"','"+documento+"')}";  
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

        ButtonType buttonTypeOk = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);
        
        Optional<ButtonType> result = dialog.showAndWait();
        
        Integer idFac = Integer.parseInt(txtFactura.getText());                   
        Integer tipo = 1;
        LocalDate date2 = fechaInicio.getValue();
            
        if(result.get() == buttonTypeOk){
            Creditos nuevoCredito = new Creditos();
            nuevoCredito.setCreaditoFechaInicio(java.sql.Date.valueOf( fechaInicio.getValue()));
            nuevoCredito.setCreditoFechaFinal(java.sql.Date.valueOf( fechaFinal.getValue()));
            nuevoCredito.setCreditoDesc(desc.getText());
            double cantidad = Double.parseDouble(txtcantidad.getText());
            nuevoCredito.setCreditoMonto(Double.parseDouble(txtCostoProducto.getText())*cantidad);
            nuevoCredito.setNoFactura(txtFactura.getText());
            Double costoFinal = Double.parseDouble(txtcantidad.getText())*Double.parseDouble(txtCostoProducto.getText());
            
            String sqlDetalle = "{call SpAgregarCreditoDetalleBackUp('"+txtCodigoProducto.getText()+"','"+txtcantidad.getText()+"','"+costoFinal+"')}";
            String sqlTransferirBackup = "{call SpAgregarCreditoDetalle()}";
            String sql = "{call SpAgregarCredito('"+nuevoCredito.getCreaditoFechaInicio()+"','"+nuevoCredito.getCreditoFechaFinal()+"','"+nuevoCredito.getCreditoDesc()+"','"+nuevoCredito.getCreditoMonto()+"','"+codigoEstado1+"','"+nuevoCredito.getNoFactura()+"')}";
            String sqlEliminarBackup = "{call SpEliminarBackupCredito()}";
            System.out.println(sqlDetalle);
            Integer documento = 3;
            
            String sqlCardex = "{call SpAgregarCardexCreditos('"+date2+"','"+txtNombreProducto.getText()+"','"+idFac+"','"+tipo+"','"+txtcantidad.getText()+"','"+documento+"')}";  
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
    
    
     @FXML
    private void btnAgregar(MouseEvent event) {
        if(tipoOperacionProducto == Operacion.GUARDAR){
           if(txtCodigoProducto.getText().isEmpty() || txtFactura.getText().isEmpty() ||txtcantidad.getText().isEmpty() || txtNombreProducto.getText().isEmpty() || cmbCategoriaProducto.getValue().equals("") || cmbProveedorProducto.getValue().equals("") || txtPrecioProducto.getText().isEmpty() || cmbTipoProducto.getValue().equals("")){
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("HAY CAMPOS VACÍOS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
           }else{
               if(txtNombreProducto.getText().length()>50){
                   Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("NOMBRE DEL PRODUCTO ES MAYOR A 50 CARACTERES");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
               }else{
                   if(txtCodigoProducto.getText().length()<7){
                        Notifications noti = Notifications.create();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR");
                        noti.text("El CAMPO DE CÓDIGO NO PUEDE SER MENOR A 7 DÍGITOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();   
                        noti.show();
                        tipoOperacionProducto = Operacion.GUARDAR;
                   }else if(txtCodigoProducto.getText().length()>7){
                        Notifications noti = Notifications.create();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR");
                        noti.text("El CAMPO DE CÓDIGO NO PUEDE SER MAYOR A 7 DÍGITOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();   
                        noti.show();
                        tipoOperacionProducto = Operacion.GUARDAR;
                   }else{
                   Productos nuevoProducto = new Productos();
                   nuevoProducto.setProductoId(txtCodigoProducto.getText());
                   nuevoProducto.setProductoDesc(txtNombreProducto.getText());
                   nuevoProducto.setCategoriaNombre(cmbCategoriaProducto.getValue());
                   nuevoProducto.setProveedorNombre(cmbProveedorProducto.getValue());
                   nuevoProducto.setProductoPrecio(Double.parseDouble(txtPrecioProducto.getText()));
                   nuevoProducto.setPrecioCosto(Double.parseDouble(txtCostoProducto.getText()));
                   
                   int tipoProdId = 0;
                   
                   if(cmbTipoProducto.getValue().equals("BIEN")){
                       tipoProdId = 1;
                   }else{
                        tipoProdId = 2;
                   }
                   
                   String sql = "{call SpAgregarProductos('"+nuevoProducto.getProductoId()+"','"+nuevoProducto.getProductoDesc()+"','"+verificarProveedores(nuevoProducto.getProveedorNombre())+"'"+ ",'"+verficarCategoria(nuevoProducto.getCategoriaNombre())+"','"+nuevoProducto.getPrecioCosto()+"','"+nuevoProducto.getProductoPrecio()+"','"+tipoProdId+"')}";
                   tipoOperacionProducto = Operacion.GUARDAR;
                   accion(sql);   
                   }
               }
           }
        }else{
            tipoOperacionProducto = Operacion.AGREGAR;
            accion();
        }
    }

    @FXML
    private void btnEliminar(MouseEvent event) {
         if(tipoOperacionProducto == Operacion.GUARDAR){
            tipoOperacionProducto = Operacion.CANCELAR;
            accion();
        }else{
             
            String sql = "{call SpEliminarProductos('"+codigoProducto+"')}";
            tipoOperacionProducto = Operacion.ELIMINAR;
            accion(sql);
        }
    }

    @FXML
    private void btnEditar(MouseEvent event) {
        
        if(txtCodigoProducto.getText().length()<7){
                        Notifications noti = Notifications.create();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR");
                        noti.text("El CAMPO DE CÓDIGO NO PUEDE SER MENOR A 7 DÍGITOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();   
                        noti.show();
                        tipoOperacionProducto = Operacion.GUARDAR;
                   }else if(txtCodigoProducto.getText().length()>7){
                        Notifications noti = Notifications.create();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR");
                        noti.text("El CAMPO DE CÓDIGO NO PUEDE SER MAYOR A 7 DÍGITOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();   
                        noti.show();
                        tipoOperacionProducto = Operacion.GUARDAR;
                   }else{
                        Productos nuevoProducto = new Productos();
                        nuevoProducto.setProductoId(txtCodigoProducto.getText());
                        nuevoProducto.setProductoDesc(txtNombreProducto.getText());
                        nuevoProducto.setProductoPrecio(Double.parseDouble(txtPrecioProducto.getText()));
                        nuevoProducto.setPrecioCosto(Double.parseDouble(txtCostoProducto.getText()));

                        int tipoProdId = 0;
                   
                        if(cmbTipoProducto.getValue().equals("BIEN")){
                            tipoProdId = 1;
                        }else{
                            tipoProdId = 2;
                        }
                        
                   String sql = "{call SpActualizarProductos('"+codigoProducto+"','"+nuevoProducto.getProductoDesc()+"','"+nuevoProducto.getPrecioCosto()+"','"+nuevoProducto.getProductoPrecio()+"')}";
                        tipoOperacionProducto = Operacion.ACTUALIZAR;
                        accion(sql);      
                   }
             
    }
    
        
     @FXML
    private void BtnCargar(MouseEvent event) {
        cargarDatos();
    }
    
    @FXML
    private void atajosProductos(KeyEvent event) {
         if(cmbBuscar.isFocused()){
            if(event.getCode() == KeyCode.ENTER){
                buscar();
            }
        }
    }
    
    
    public void cargarCombo(){
        ArrayList<String>lista = new ArrayList();
        
        lista.add(0,"CÓDIGO");
        lista.add(1,"NOMBRE");        
        listaFiltro = FXCollections.observableList(lista);
        
        cmbFiltroProductos.setItems(listaFiltro);
    }
        
    @FXML
    private void comboFiltro(ActionEvent event) {
        btnBuscar.setDisable(false);
        ArrayList<String> lista = new ArrayList();
        String sql ="{call SpListarProductos()}";
        int x=0;
        try{
              PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
              ResultSet rs = ps.executeQuery();
            while(rs.next()){
                 if(cmbFiltroProductos.getValue().equals("CÓDIGO")){
                     lista.add(x, rs.getString("productoId"));
                     
                }else if(cmbFiltroProductos.getValue().equals("NOMBRE")){
                    lista.add(x, rs.getString("productoDesc"));
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
          System.out.println("hola");
        }else{
            if(cmbFiltroProductos.getValue().equals("CÓDIGO")){
                tipoOperacionProducto = Operacion.BUSCAR;
                    String sql = "{ call SpBuscarProductos('"+cmbBuscar.getValue()+"')}";
                    accion(sql);
            }else if(cmbFiltroProductos.getValue().equals("NOMBRE")){
                    tipoOperacionProducto = Operacion.BUSCAR;
                    String sql = "{ call SpBuscarProductosNombre('"+cmbBuscar.getValue()+"')}";
                    accion(sql);
            }
        }  
    }
    
    @FXML
    private void btnBuscar(MouseEvent event) {
        buscar();
    }
    
    @FXML
    private void cmbBuscar(ActionEvent event) {
        buscar();
    }

    
    //EVENTOS DE LA VISTA DE CATEGORIA-----------------------------------------------------------------------------------------------------------------------
    
    
    public void limpiarTextCategoria(){
          txtCodigoCategoria.setText("");
          txtNombreCategoria.setText("");
          cmbFiltroCategoriaProducto.setValue("");
          cmbBuscarCategoria.setValue("");
    }
    
    public void desactivarControlesCategoria(){
        
        btnEditarCategoria.setDisable(true);
        btnEliminarCategoria.setDisable(true);
    }
    
    public void activarControlesCategoria(){
        
        btnEditarCategoria.setDisable(false);
        btnEliminarCategoria.setDisable(false);
    }
    
    public void desactivarTextCategoria(){
        txtCodigoCategoria.setEditable(false);
        txtNombreCategoria.setEditable(false);
        
    }
    
    public void activarTextCategoria(){
        txtCodigoCategoria.setEditable(true);
        txtNombreCategoria.setEditable(true);
        
    }
    
    
    
    public ObservableList<CategoriaProducto> getCategoria(){
        ArrayList<CategoriaProducto> lista = new ArrayList();
        ArrayList<String> listaCodigo = new ArrayList();
        String sql= "{call SpListarCategoriaProductos()}";
            int x =0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                lista.add(new CategoriaProducto(
                              rs.getString("categoriaId"),
                              rs.getString("categoriaNombre")
                ));
                
                listaCodigo.add(x, rs.getString("categoriaId"));
                x++;
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
            
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR AL CARGAR DATOS");
            noti.text("Error al cargar la base de datos");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
        }
        
        listaCodigoCategoria = FXCollections.observableList(listaCodigo);
        return listaCategoria = FXCollections.observableList(lista);
    
    }
    
    public void cargarDatosCategoria(){
        tableCategoria.setItems(getCategoria());
        colCodigoCategoria.setCellValueFactory(new PropertyValueFactory("categoriaId"));
        colNombreCategoria.setCellValueFactory(new PropertyValueFactory("categoriaNombre"));
        limpiarTextCategoria();
        desactivarControlesCategoria();
        desactivarTextCategoria();
        tipoOperacionCategoria = Operacion.CANCELAR;
        accionCategoria();
    }
    
    
    @FXML
    private void cargarCategoria(Event event) {
         animacion.animacion(anchor3, anchor4);
        cargarDatosCategoria();
        cargarComboCategoria();
        new AutoCompleteComboBoxListener(cmbBuscarCategoria);
        new AutoCompleteComboBoxListener(cmbFiltroCategoriaProducto);
    }
    

    @FXML
    private void seleccionarElementos(MouseEvent event) {
        int index = tableCategoria.getSelectionModel().getSelectedIndex();
        try{
            txtCodigoCategoria.setText(colCodigoCategoria.getCellData(index).toString());
            txtNombreCategoria.setText(colNombreCategoria.getCellData(index));
            btnEliminarCategoria.setDisable(false);
            btnEditarCategoria.setDisable(false);
            
            codigo = colCodigoCategoria.getCellData(index);
            activarTextCategoria();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @FXML
    private void atajosCategoria(KeyEvent event) {
        if(cmbBuscarCategoria.isFocused()){
            if(event.getCode() == KeyCode.ENTER){
                buscarCategoria();
            }
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
                cmbBuscarCategoria.setDisable(true);
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
                cmbBuscarCategoria.setDisable(false);
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
                        txtNombreCategoria.setText(rs.getString("categoriaNombre"));
                        codigo = rs.getString("categoriaId");
                        
                    }                    
                    if(rs.first()){
                        for(int i=0; i<tableCategoria.getItems().size(); i++){
                            if(colCodigoCategoria.getCellData(i) == codigo){
                                tableCategoria.getSelectionModel().select(i);
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
            if(txtCodigoCategoria.getText().isEmpty() || txtNombreCategoria.getText().isEmpty()){
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR");
                noti.text("HAY CAMPOS VACÍOS");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();   
                noti.show();
            }else{
                if(txtNombreCategoria.getText().length()<50){
                    
                    if(txtCodigoCategoria.getText().length()<7){
                        Notifications noti = Notifications.create();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR");
                        noti.text("El CAMPO DE CÓDIGO NO PUEDE SER MENOR A 7 DÍGITOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();   
                        noti.show();
                        tipoOperacionCategoria = Operacion.GUARDAR;
                    }else if(txtCodigoCategoria.getText().length()>7){
                        Notifications noti = Notifications.create();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR");
                        noti.text("El CAMPO DE CÓDIGO NO PUEDE SER MAYOR A 7 DÍGITOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();   
                        noti.show();
                        tipoOperacionCategoria = Operacion.GUARDAR;
                    }else{                  
                    CategoriaProducto nuevaCategoria = new CategoriaProducto();
                    nuevaCategoria.setCategoriaId(txtCodigoCategoria.getText());
                    nuevaCategoria.setCategoriaNombre(txtNombreCategoria.getText());
                    String sql = "{call SpAgregarCategoriaProductos('"+nuevaCategoria.getCategoriaId()+"','"+nuevaCategoria.getCategoriaNombre()+"')}";
                    accionCategoria(sql);
                    }
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
    private void btnEditarCategoria(MouseEvent event) {
        
       if(txtCodigoCategoria.getText().length()<7){
                        Notifications noti = Notifications.create();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR");
                        noti.text("El CAMPO DE CÓDIGO NO PUEDE SER MENOR A 7 DÍGITOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();   
                        noti.show();
                        tipoOperacionCategoria = Operacion.GUARDAR;
                    }else if(txtCodigoCategoria.getText().length()>7){
                        Notifications noti = Notifications.create();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR");
                        noti.text("El CAMPO DE CÓDIGO NO PUEDE SER MAYOR A 7 DÍGITOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();   
                        noti.show();
                        tipoOperacionCategoria = Operacion.GUARDAR;
                    }else{  
                            CategoriaProducto nuevaCategoria = new CategoriaProducto();
                            nuevaCategoria.setCategoriaId(txtCodigoCategoria.getText());
                            nuevaCategoria.setCategoriaNombre(txtNombreCategoria.getText());
                            tipoOperacionCategoria = Operacion.ACTUALIZAR;
                            String sql = "{call SpActualizarCategoriaProductos('"+codigo+"','"+nuevaCategoria.getCategoriaId()+"','"+nuevaCategoria.getCategoriaNombre()+"')}";
                            accionCategoria(sql);
                    }
    }

    @FXML
    private void btnEliminarCategoria(MouseEvent event) {
        if(tipoOperacionCategoria == Operacion.GUARDAR){
            tipoOperacionCategoria = Operacion.CANCELAR;
            accionCategoria();
        }else{
            String sql = "{call SpEliminarCategoriaProductos('"+codigo+"')}";
            tipoOperacionCategoria = Operacion.ELIMINAR;
            accionCategoria(sql);
        }
    }
    
    public void cargarComboCategoria(){
        ArrayList<String>lista = new ArrayList();
        
        lista.add(0,"CÓDIGO");
        lista.add(1,"NOMBRE");
        
        listaFiltroCategoria = FXCollections.observableList(lista);
        cmbFiltroCategoriaProducto.setItems(listaFiltroCategoria);
    }
    
    @FXML
    private void comboFiltroCategoria(ActionEvent event) {
        btnBuscarCategoria.setDisable(false);
        ArrayList<String> lista = new ArrayList();
        String sql ="{call SpListarCategoriaProductos()}";
        int x=0;
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                 if(cmbFiltroCategoriaProducto.getValue().equals("CÓDIGO")){
                     lista.add(x, rs.getString("categoriaId"));
                     
                }else if(cmbFiltroCategoriaProducto.getValue().equals("NOMBRE")){
                    lista.add(x, rs.getString("categoriaNombre"));
                }
                 x++;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
       listaBuscarCategoria = FXCollections.observableList(lista);
       cmbBuscarCategoria.setItems(listaBuscarCategoria);
       new AutoCompleteComboBoxListener(cmbBuscarCategoria);
    }
    
    
    public void buscarCategoria(){
        if(cmbBuscarCategoria.getValue().equals("")){
               System.out.println("hola");
        }else{
            if(cmbFiltroCategoriaProducto.getValue().equals("CÓDIGO")){
                tipoOperacionCategoria = Operacion.BUSCAR;
                    String sql = "{ call SpBuscarCategoriaProductos('"+cmbBuscarCategoria.getValue()+"')}";
                    accionCategoria(sql);
            }else if(cmbFiltroCategoriaProducto.getValue().equals("NOMBRE")){
                    tipoOperacionCategoria = Operacion.BUSCAR;
                    String sql = "{ call SpBuscarCategoriaProductosNombre('"+cmbBuscarCategoria.getValue()+"')}";
                    accionCategoria(sql);
            }
        }
    }
    
    @FXML
    private void cmbBuscarCategoria(ActionEvent event) {
        buscarCategoria();
    }
    @FXML
    private void btnBuscarCategoria(MouseEvent event) {
        buscarCategoria();
    }

    private void codigoBuscadoCategoria(MouseEvent event) {
        limpiarTextCategoria();
        desactivarControlesCategoria();
    }
    
    private void validarCodigoCategoria(KeyEvent event) {
         if(tipoOperacionCategoria == Operacion.GUARDAR){
            
                if(txtCodigoCategoria.getText().matches(".*[a-z].*")){
                    btnAgregarCategoria.setDisable(true);
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE CÓDIGO NO PUEDE LLEVAR LETRAS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                }else{
                    btnAgregarCategoria.setDisable(false);
                }
              
        }else{
            
                if(tipoOperacionCategoria == Operacion.ACTUALIZAR){
                    if(txtCodigoCategoria.getText().matches(".*[a-z].*")){
                    btnEditarCategoria.setDisable(true);
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE CÓDIGO NO PUEDE LLEVAR LETRAS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                }else{
                    btnEditarCategoria.setDisable(false);
                    
                }
                }
            
        }
    }
}
