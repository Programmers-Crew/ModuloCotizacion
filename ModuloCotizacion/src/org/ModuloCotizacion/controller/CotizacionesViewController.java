package org.ModuloCotizacion.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.ModuloCotizacion.bean.Animations;
import org.ModuloCotizacion.bean.AutoCompleteComboBoxListener;
import org.ModuloCotizacion.bean.CambioScene;
import org.ModuloCotizacion.bean.CamposEspeciales;
import org.ModuloCotizacion.bean.Cotizaciones;
import org.ModuloCotizacion.bean.ValidarStyle;
import org.ModuloCotizacion.bean.cotizacionBackup;
import org.ModuloCotizacion.bean.generarCotizacion;
import org.ModuloCotizacion.db.Conexion;
import org.controlsfx.control.Notifications;

public class CotizacionesViewController implements Initializable {

    @FXML
    private AnchorPane anchor;
    @FXML
    private JFXButton btnAgregarImagen;
    @FXML
    private Label txtImagen;
    @FXML
    private JFXButton verImagen;
    @FXML
    private JFXButton btnGuardarCotizacion;
    @FXML
    private TextField txtTotalParcial;
    @FXML
    private JFXButton cargar;

    
    //Variables
    public double factorVenta = 0;
    @FXML
    private JFXTextField txtCodigo;
    @FXML
    private ComboBox<String> txtInventario;
    @FXML
    private ComboBox<String> txtPrecioProd;
    @FXML
    private TextField txtAumento;
    
    public enum Operacion{AGREGAR,GUARDAR,ELIMINAR,BUSCAR,ACTUALIZAR,CANCELAR,NINGUNO, SUMAR, RESTAR};
    public Operacion cancelar = Operacion.NINGUNO;
    public Operacion tipoOperacion = Operacion.NINGUNO;
    int codigoCotizacion=0;
    
    CambioScene cambioScene = new CambioScene();
    Image imgError = new Image("org/ModuloCotizacion/img/error.png");
    Image imgCorrecto= new Image("org/ModuloCotizacion/img/correcto.png");
    Image imgWarning = new Image("org/ModuloCotizacion/img/warning.png");
    
    MenuPrincipalContoller menu = new MenuPrincipalContoller();
    ValidarStyle validar = new ValidarStyle();
    
    Animations animacion = new Animations();
    
    ObservableList<Cotizaciones> listaCotizaciones;
    ObservableList<cotizacionBackup> listaCotizacionesBack;
    ObservableList<String> listaNit;
    ObservableList<String> listaTipoCliente;    
    ObservableList<String> listaInventario;
    ObservableList<String> listaPreciosProd;
    ObservableList<String> listaVendedor;
    ObservableList<String> listaEmpleado;
    ObservableList<String> listaFactorVenta;
    ObservableList<String> listaMolduraRef;
    ObservableList<String> listaCodigoCotizaciones;
    ObservableList<String> listaProducto;
    ObservableList<String> listaCamposEspeciales;
    ObservableList<CamposEspeciales>listaCamposEsp2;
    String destino;
    Path dest;
    String origen;
    Path orig;
    Integer codigo = 0;
    String tipoClienteDescuento = "";
    String codigoProducto = "";
    
    @FXML
    private AnchorPane anchor1;
    @FXML
    private AnchorPane anchor2;
    
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtDireccion;
    @FXML
    private ComboBox<String> txtNIT;
    @FXML
    private ComboBox<String> txtVendedor;
    @FXML
    private ComboBox<String> txtTipoCliente;
    @FXML
    private JFXDatePicker txtFecha;
    @FXML
    private JFXTextField txtLargo;
    @FXML
    private JFXTextField txtAlto;
    @FXML
    private JFXTextField txtAncho;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private ComboBox<String> txtMolduraReferencia;
    @FXML
    private TextField txtCantidad;

    
    //Botones
    @FXML
    private JFXButton btnAgregar;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private ComboBox<String> txtBusqueda;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXButton btnAgregarCampoEspecial;
    @FXML
    private JFXButton btnGenerarWord;
    @FXML
    private JFXButton btnMandarProduccion;
    
    
    // Columnas
    
    @FXML
    private TableView<cotizacionBackup> tblCotizacionDetalle;
    @FXML
    private TableColumn<cotizacionBackup, Integer> colCodigoDetalle;
    @FXML
    private TableColumn<cotizacionBackup, Double> colCantidadDetalle;
    @FXML
    private TableColumn<cotizacionBackup, String> colDetalleDescripcion;
    @FXML
    private TableColumn<cotizacionBackup, Double> colAnchoDetalle;
    @FXML
    private TableColumn<cotizacionBackup, Double> colAltoDetalle;
    @FXML
    private TableColumn<cotizacionBackup, Double> colLargoDetalle;
    @FXML
    private TableColumn<cotizacionBackup, Double> colPrecioDetalle;
    @FXML
    private TableColumn<cotizacionBackup, Double> colTotalDetalle;
    
    
    @FXML
    private TableView<Cotizaciones> tblCotizacion;
    @FXML
    private TableColumn<Cotizaciones, Double> colDescuentoCotizacion;
    @FXML
    private TableColumn<Cotizaciones, Double> colNetoCotizacion;
    @FXML
    private TableColumn<Cotizaciones, Double> colTotalCotizacion;
    @FXML
    private TableColumn<Cotizaciones, Integer> colCodigoCotizacion;
    @FXML
    private TableColumn<Cotizaciones, Date> colFechaCotizacion;
    
    @FXML
    private TableColumn<Cotizaciones, String> colClienteCotizacion;


    //CamposEspeciales
     @FXML
    private TableView<Cotizaciones> tblCamposEspeciales;
    @FXML
    private TableColumn<Cotizaciones, String> colDescCampoEspecial;
    @FXML
    private TableColumn<Cotizaciones, Double> colPrecioCampoEspecial;
    @FXML
    private TableColumn<Cotizaciones, Integer> colCodigoCampoEspecial;

    @FXML
    private TextField txtPrecioUCotizacion;
    @FXML
    private JFXTextField txtTotalCotizacion;
   
    public void limpiarText(){
        txtNIT.setValue("");
        txtNombre.setText("");
        txtDireccion.setText("");        
        txtTipoCliente.setValue("");
        txtVendedor.setValue("");
        txtMolduraReferencia.setValue("");
        txtAncho.setText("0.00");
        txtImagen.setText("Seleccione el archivo...");
        txtLargo.setText("0.00");        
        txtAlto.setText("0.00");
        txtPrecioUCotizacion.setText("0.00");
        txtTotalCotizacion.setText("0.00");
        txtDescripcion.setText("");
        txtCantidad.setText("1");         
    }
    
    public void limpiarTextBack(){
        txtMolduraReferencia.setValue("");
        txtAncho.setText("0.00");
        txtLargo.setText("0.00");        
        txtAlto.setText("0.00");
        txtDescripcion.setText("");
        txtCantidad.setText("1");      
    }
        
    public void desactivarControles(){    
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);
        
    }
    
    public void activarControles(){    
        btnEditar.setDisable(false);
        btnEliminar.setDisable(false);
    }
    
    public void disableButtonsSeleccion(){
        btnAgregarCampoEspecial.setDisable(true);
        btnMandarProduccion.setDisable(true);
        btnGenerarWord.setDisable(true);
        verImagen.setDisable(true);
    }
    
    public void activateButtonsSeleccion(){
        btnAgregarCampoEspecial.setDisable(false);
        btnMandarProduccion.setDisable(false);
        btnGenerarWord.setDisable(false);
        verImagen.setDisable(false);
    }
    
    public void desactivarText(){
    }
    
    public void desactivarTextBack(){
        txtMolduraReferencia.setDisable(true);
        txtAncho.setEditable(false);
        txtLargo.setEditable(false);        
        txtAlto.setEditable(false);
        txtDescripcion.setEditable(false);
        txtCantidad.setEditable(false);
        txtTotalParcial.setEditable(false);
        txtPrecioUCotizacion.setEditable(false);
    }
    
    public void activarText(){
        txtMolduraReferencia.setDisable(false);
        txtAncho.setEditable(true);
        txtLargo.setEditable(true);        
        txtAlto.setEditable(true);
        txtDescripcion.setEditable(true);
        txtCantidad.setEditable(true);
        txtTotalParcial.setEditable(true);
        txtPrecioUCotizacion.setEditable(true);
    }

    public ObservableList<Cotizaciones> getCotizacion(){
        ArrayList<Cotizaciones> lista = new ArrayList();
        ArrayList<String> listaCodigo = new ArrayList();
        String sql= "{call Sp_ListarCotizaciones()}";
        int x =0;
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new Cotizaciones(
                    rs.getInt("cotizacionId"),
                    rs.getString("clienteId")+"| "+rs.getString("clienteNit")+" - "+rs.getString("clienteNombre"),
                    rs.getString("tipoClienteDesc"),
                    rs.getString("cotizacionImg"),
                    rs.getString("usuarioNombre"),
                    rs.getDate("cotizacionFecha"),
                    rs.getDouble("cotizacionDescuento"),
                    rs.getDouble("cotizacionDescuentoNeto"),
                    rs.getDouble("cotizacionTotal")
                ));                
                listaCodigo.add(x, rs.getString("cotizacionId"));
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
        
        listaCodigoCotizaciones = FXCollections.observableList(listaCodigo);
        return listaCotizaciones = FXCollections.observableList(lista);
    }
    
    
    public void cargarDatosCotizaciones(){        
        tblCotizacion.setItems(getCotizacion());
        colCodigoCotizacion.setCellValueFactory(new PropertyValueFactory("cotizacionId"));
        colFechaCotizacion.setCellValueFactory(new PropertyValueFactory("cotizacionFecha"));
        colClienteCotizacion.setCellValueFactory(new PropertyValueFactory("nit"));
        colDescuentoCotizacion.setCellValueFactory(new PropertyValueFactory("cotizacionDescuento"));
        colNetoCotizacion.setCellValueFactory(new PropertyValueFactory("cotizacionDescuentoNeto"));
        colTotalCotizacion.setCellValueFactory(new PropertyValueFactory("cotizacionTotal"));
        desactivarControles();
    }
    
    
    public void getInventario(){
        ArrayList<String> lista = new ArrayList();
        ArrayList<String> comboCodigoFiltro = new ArrayList();
        String sql = "{call Sp_ListInventarioProducto()}";
        int x=0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x,rs.getString("productoId")+ "| "+ rs.getString("productoDesc"));
                x++;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        listaInventario = FXCollections.observableList(lista);
        txtInventario.setItems(listaInventario);
        new AutoCompleteComboBoxListener(txtInventario);

    } 
    
    
    public void getPrecios(String id){
        ArrayList<String> lista = new ArrayList();
        ArrayList<String> comboCodigoFiltro = new ArrayList();
        String sql = "{call SpBuscarProductosFac('"+id+"')}";
        System.out.println(sql);
        System.out.println(id);
        int x=0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x,rs.getString("productoPrecio"));
                lista.add(x,rs.getString("productoPrecio2"));
                lista.add(x,rs.getString("productoPrecio3"));
                lista.add(x,rs.getString("productoPrecio4"));
                x++;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        listaPreciosProd = FXCollections.observableList(lista);
        txtPrecioProd.setItems(listaPreciosProd);
        new AutoCompleteComboBoxListener(txtPrecioProd);

    } 
    
    public ObservableList<Cotizaciones> getCotizacionName(int codigo){
        ArrayList<Cotizaciones> lista = new ArrayList();
        ArrayList<String> listaCodigo = new ArrayList();
        String sql= "{call Sp_SearchCotizaciones('"+codigo+"')}";
        int x =0;
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new Cotizaciones(
                    rs.getInt("cotizacionId"),
                     rs.getString("clienteId")+"| "+rs.getString("clienteNit")+" - "+rs.getString("clienteNombre"),
                    rs.getString("tipoClienteDesc"),
                    rs.getString("cotizacionImg"),
                    rs.getString("usuarioNombre"),
                    rs.getDate("cotizacionFecha"),
                    rs.getDouble("cotizacionDescuento"),
                    rs.getDouble("cotizacionDescuentoNeto"),
                    rs.getDouble("cotizacionTotal")
                ));                
                listaCodigo.add(x, rs.getString("cotizacionId"));
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
        listaCodigoCotizaciones = FXCollections.observableList(listaCodigo);
        return listaCotizaciones = FXCollections.observableList(lista);
    }
    
    
    public void cargarDatosCotizacionesBuscada(int codigo){        
        tblCotizacion.setItems(getCotizacionName(codigo));
        colCodigoCotizacion.setCellValueFactory(new PropertyValueFactory("cotizacionId"));
        colFechaCotizacion.setCellValueFactory(new PropertyValueFactory("cotizacionFecha"));
        colClienteCotizacion.setCellValueFactory(new PropertyValueFactory("nit"));
        colDescuentoCotizacion.setCellValueFactory(new PropertyValueFactory("cotizacionDescuento"));
        colNetoCotizacion.setCellValueFactory(new PropertyValueFactory("cotizacionDescuentoNeto"));
        colTotalCotizacion.setCellValueFactory(new PropertyValueFactory("cotizacionTotal"));
        desactivarControles();
    }
    
    @FXML
    private void changeInventario(ActionEvent event) {                    
        int busqueda = txtInventario.getValue().indexOf("|");
        
        String valor = txtInventario.getValue().substring(0,busqueda);
        String sql = "{ call Sp_FindInventarioProducto('"+valor+"')}";
        System.out.println(valor);
        getPrecios(valor);
         try{
             
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
              txtAncho.setText(rs.getString("prodLargo"));              
              txtLargo.setText(rs.getString("prodAncho"));
              txtAlto.setText(rs.getString("prodAlto"));
              txtDescripcion.setText(txtInventario.getValue());
            }  
            
            calcularprecio();
        }catch(SQLException ex){
            Notifications noti = Notifications.create();
            ex.printStackTrace();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR AL BUSCAR");
            noti.text("HA OCURRIDO UN ERROR EN LA BASE DE DATOS");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
        }
    }
    
    
    public ObservableList<cotizacionBackup> getCotizacionBack(){
        ArrayList<cotizacionBackup> lista = new ArrayList();
        String sql= "{call Sp_ListCotizacionBackUp()}";
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new cotizacionBackup(
                    rs.getInt("backupId"),
                    rs.getDouble("cotizacionCantida"),
                    rs.getString("cotizacionDesc"),
                    rs.getDouble("cotizacionAlto"),
                    rs.getDouble("cotizacionLargo"),
                    rs.getDouble("cotizacionAncho"),
                    rs.getDouble("cotizacionPrecioU"),
                    rs.getDouble("cotizacionTotalParcial")
                ));       
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR AL CARGAR DATOS BACKUP");
            noti.text("Error al cargar la base de datos"+ex);
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
        }        
        return listaCotizacionesBack = FXCollections.observableList(lista);
    }
    
    
    public void cargarDatosCotizacionesBack(){
        tblCotizacionDetalle.getItems().clear();
       tblCotizacionDetalle.setItems(getCotizacionBack());
       colCodigoDetalle.setCellValueFactory(new PropertyValueFactory("backupId"));
       colCantidadDetalle.setCellValueFactory(new PropertyValueFactory("cotizacionCantida"));
       colDetalleDescripcion.setCellValueFactory(new PropertyValueFactory("cotizacionDesc"));
       colAnchoDetalle.setCellValueFactory(new PropertyValueFactory("cotizacionAncho"));
       colAltoDetalle.setCellValueFactory(new PropertyValueFactory("cotizacionAlto"));
       colLargoDetalle.setCellValueFactory(new PropertyValueFactory("cotizacionLargo"));
       
       colPrecioDetalle.setCellValueFactory(new PropertyValueFactory("cotizacionPrecioU"));
       colTotalDetalle.setCellValueFactory(new PropertyValueFactory("cotizacionTotalParcial"));
       desactivarControles();
    }
    
    public void accion(){
        switch(tipoOperacion){
            case AGREGAR:
                codigoCotizacion = 0;
                disableButtonsSeleccion();
                tipoOperacion = Operacion.GUARDAR;
                cancelar = Operacion.CANCELAR;
                desactivarControles();
                btnAgregar.setText("GUARDAR");
                btnEliminar.setText("CANCELAR");
                btnEliminar.setDisable(false);
                activarText();
                btnBuscar.setDisable(true);
                limpiarTextBack();
                
                break;
            case CANCELAR:
                tipoOperacion = Operacion.NINGUNO;
                desactivarControles();
                desactivarTextBack();
                btnAgregar.setText("AGREGAR");
                btnEliminar.setText("ELIMINAR");
                limpiarTextBack();
                btnBuscar.setDisable(false);
                cancelar = Operacion.NINGUNO;
                break;
        }
    }
    
    public void accionProveedores(String sql){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        PreparedStatement ps;
        ResultSet rs;
        Notifications noti = Notifications.create();
        ButtonType buttonTypeSi = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");
        switch(tipoOperacion){
            
            case GUARDAR:
                alert.setTitle("AGREGAR REGISTRO");
                alert.setHeaderText("AGREGAR REGISTRO");
                alert.setContentText("¿Está seguro que desea guardar este registro?");
                
                alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);
                
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == buttonTypeSi ){
                    try {
                        
                        double total = Double.parseDouble(txtTotalCotizacion.getText());
                        
                        
                        total = total+ (Double.parseDouble(txtTotalParcial.getText()));
                        
                       
                        cargar.setDisable(true);
                        txtTotalCotizacion.setText(String.valueOf(total));
                        
                        
                        
                        ps = Conexion.getIntance().getConexion().prepareCall(sql);
                        ps.execute();
                        
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("OPERACIÓN EXITOSA");
                        noti.text("SE HA AGREGADO EXITOSAMENTE EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacion = Operacion.NINGUNO;
                        limpiarTextBack();
                        desactivarTextBack();
                        cargarDatosCotizacionesBack();
                        
                        btnEliminar.setText("ELIMINAR");
                        btnBuscar.setDisable(false);
                        cancelar = Operacion.NINGUNO;
                        txtInventario.setValue("");                                               
                        txtPrecioProd.setValue("");
                    }catch (SQLException ex) {                            
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL AGREGAR");
                        noti.text("HA OCURRIDO UN ERROR AL GUARDAR EL REGISTRO"+ex);
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                    }
                }else{
                    
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA AGREGADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacion = Operacion.CANCELAR;
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
                        cargarDatosCotizaciones();
                        tipoOperacion = Operacion.CANCELAR;
                        accion();
                        
                        txtPrecioProd.setValue("");
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        
                        
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ELIMINAR");
                        noti.text("HA OCURRIDO UN ERROR AL ELIMINAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacion = Operacion.CANCELAR;
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
                    tipoOperacion = Operacion.CANCELAR;
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
                        tipoOperacion = Operacion.CANCELAR;
                        accion();
                        cargarDatosCotizaciones();
                        txtPrecioProd.setValue("");
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ACTUALIZAR");
                        noti.text("HA OCURRIDO UN ERROR AL ACTUALIZAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacion = Operacion.CANCELAR;
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
                    tipoOperacion = Operacion.CANCELAR;
                    accion();
                }
                break;
            case BUSCAR:
                 try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    rs = ps.executeQuery();
                    
                    while(rs.next()){
                      //  txtCodigoProveedores.setText(rs.getString("proveedorId"));
                        // txtNombreProveedores.setText(rs.getString("proveedorNombre"));
                        // txtTelefonoProveedores.setText(rs.getString("proveedorTelefono"));
                        // txtNitProveedores .setText(rs.getString("proveedorNit"));
                        // codigo = rs.getString("proveedorId");
                    }                    
                    if(rs.first()){
                        for(int i=0; i<tblCotizacion.getItems().size(); i++){
                            if(colCodigoCotizacion.getCellData(i) == codigo){
                                tblCotizacion.getSelectionModel().select(i);
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
                        btnEditar.setDisable(false);
                        btnEliminar.setDisable(false);
                        activarText();
                    }else{
                         
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL BUSCAR");
                        noti.text("NO SE HA ENCONTRADO EN LA BASE DE DATOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacion = Operacion.CANCELAR;
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
                    tipoOperacion = Operacion.CANCELAR;
                    accion();
                }
                break;
        }
    }
    


        
    public int buscarCodigoTipoClienteDos(){    
        int codigoTC;
        if(!txtTipoCliente.getValue().isEmpty()){
            int busqueda = txtTipoCliente.getValue().indexOf("|");

            String valor1 = txtTipoCliente.getValue().substring(busqueda+1, txtTipoCliente.getValue().length());
             codigoTC = Integer.parseInt(valor1);
        }else{
            codigoTC = 1;
        }
        return codigoTC;
    }     
    
    
    @FXML
    private void tipoClienteDescuento(ActionEvent event) {
        String sql = "{call Sp_ListTipoClienteCC('"+buscarCodigoTipoClienteDos()+"')}";
        
        try {
            
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                factorVenta = rs.getDouble("tipoClienteDescuento");
            }
            calcularprecio();
        } catch (SQLException ex) {
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR AL CARGAR DATOS CMB");
            noti.text("Error al cargar el descuento"+ex);
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
        }
        
        
    }

    
    
    public int buscarCodigoVendedor(){    
        int busqueda = txtVendedor.getValue().indexOf("|");
        
        String valor1 = txtVendedor.getValue().substring(busqueda+1, txtVendedor.getValue().length());
        
        int codigoVendedor = Integer.parseInt(valor1);
        return codigoVendedor;
    }    
    
    public void llenarComboTipoC(){
        ArrayList<String> lista = new ArrayList();
        String sql= "{call Sp_ListTipoCliente()}";
            int x =0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x, rs.getString("tipoClienteDesc")+ " |" +rs.getString("tipoClienteId"));
                x++;
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
            
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR AL CARGAR DATOS CMB");
            noti.text("Error al cargar la base de datos"+ex);
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
        }
        listaTipoCliente = FXCollections.observableList(lista);
        txtTipoCliente.setItems(listaTipoCliente);
        
       
        new AutoCompleteComboBoxListener(txtTipoCliente);
    }    
       
         
    public void llenarComboVendedor(){
        ArrayList<String> lista = new ArrayList();
        String sql= "{call Sp_ListUsuarioVendedor()}";
            int x =0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x, rs.getString("usuarioNombre")+ " |"+rs.getString("usuarioId"));
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
        listaVendedor = FXCollections.observableList(lista);
        txtVendedor.setItems(listaVendedor);
        new AutoCompleteComboBoxListener(txtVendedor);
    }    
   
    
    public void llenarComboNit(){
        ArrayList<String> lista = new ArrayList();
        String sql= "{call Sp_ListCliente()}";
            int x =0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x, rs.getString("clienteId")+ "| " + rs.getString("clienteNit")+ " - "+rs.getString("clienteNombre"));
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
        listaNit = FXCollections.observableList(lista);
        txtNIT.setItems(listaNit);
        
        new AutoCompleteComboBoxListener(txtNIT);
    }   


  
    public void validarTabla(){
        txtTotalCotizacion.setText("0.00");
        for(int x=0; x<listaCotizacionesBack.size(); x++){
            double valor = Double.parseDouble(txtTotalCotizacion.getText())+ listaCotizacionesBack.get(x).getCotizacionTotalParcial();
            txtTotalCotizacion.setText(String.valueOf(valor));
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarComboNit();
        llenarComboVendedor();
        desactivarTextBack();
        llenarComboTipoC();
        limpiarText();
        disableButtonsSeleccion();
        cargarDatosCotizaciones();
        getInventario();
        llenarMolduras();
        cargarDatosCotizacionesBack();
        txtAncho.setText("0.00");
        txtLargo.setText("0.00");
        txtAlto.setText("0.00");
        txtCantidad.setText("1");
        calcularprecio();
        validarTabla();
        txtPrecioProd.setValue("");    
        txtAumento.setText("0");
    }    

    public void llenarMolduras(){
        ArrayList<String> lista = new ArrayList();
        try {
            String sql = "{call Sp_ListCotizacionDetalleS()}";
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                lista.add(rs.getString("detalleId")+"| "+rs.getString("cotizacionid")+" | "+rs.getString("cotizacionDesc"));
            }
        
        listaMolduraRef = FXCollections.observableList(lista);
        
        txtMolduraReferencia.setItems(listaMolduraRef);
            
        } catch (SQLException ex) {
             ex.printStackTrace();
            
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR AL CARGAR DATOS CMB MOLDURA");
            noti.text("Error al cargar la base de datos"+ex);
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
        }
       
        
        
    }
    
    @FXML
    private void regresar(MouseEvent event) throws IOException {
         String menu = "org/ModuloCotizacion/view/menuPrincipal.fxml";
        cambioScene.Cambio(menu,(Stage) anchor.getScene().getWindow());
    }

    
        @FXML
    private void addCliente(MouseEvent event) throws IOException {
        btnAgregarCliente();
    }

    
    
    @FXML
    private void cambiarNit(ActionEvent event) {
       
       if(!txtNIT.getValue().equals("") || txtNIT.getValue()!=null){
            int busqueda = txtNIT.getValue().indexOf("|");

            String valor = txtNIT.getValue().substring(0,busqueda);
            String sql = "{call SpBuscarClientes('"+valor+"')}";

            try {
                PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                       System.out.println("datos");
                       txtNombre.setText(rs.getString("clienteNombre"));
                       txtDireccion.setText(rs.getString("clienteDireccion"));
                }
                if(rs.first()){

                }else{
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgWarning));
                    noti.title("ALERTA CLIENTE");
                    noti.text("Deberá ingresar el campo de nombre y dirección");
                    txtNombre.setEditable(true);
                    txtDireccion.setEditable(true);
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR AL CARGAR DATOS DE CLIENTE");
                noti.text("Error al cargar la base de datos"+ex);
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
            }
       } 
    }
    
 

    private void calcularprecio() {
        double ancho = 0.0;
        double largo = 0.0;
        double alto = 0.0;
        double procentaje = 0.0;
        double aumento = 0.0;
        double valor = 0.0;
        double precioUnitario = 0.0;
                
        if(txtAncho.getText().isEmpty() || txtLargo.getText().isEmpty() || txtAlto.getText().isEmpty() || txtTipoCliente.getValue().isEmpty()){
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR");
            noti.text("CAMPOS VACIOS");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
        }else{            
            if(txtPrecioProd.getValue().isEmpty()){                                
                ancho = Double.parseDouble(txtAncho.getText());
                largo = Double.parseDouble(txtLargo.getText());
                alto = Double.parseDouble(txtAlto.getText());
                
                System.out.println("Numeros");
                System.out.println(txtAumento.getText());
                procentaje = Double.parseDouble(txtAumento.getText()) / 100;
                System.out.println("aumento");
                System.out.println(procentaje);
                                                        
                aumento = ((((ancho * largo * alto )* factorVenta)) * procentaje);
                System.out.println("aumento");
                System.out.println(aumento);
                    
                valor = (((ancho * largo * alto )* factorVenta)) + aumento;
                
                precioUnitario = valor/Double.parseDouble(txtCantidad.getText());

                txtPrecioUCotizacion.setText(String.valueOf(precioUnitario));
                txtTotalParcial.setText(String.valueOf(valor));               
           }else{                
                double unit = Double.parseDouble(txtPrecioProd.getValue());
                double total =  unit * Double.parseDouble(txtCantidad.getText());

                txtPrecioUCotizacion.setText(String.valueOf(unit));
                txtTotalParcial.setText(String.valueOf(total));
           }            
            
        }
         
    }    



    @FXML
    private void txtLargoCambio(KeyEvent event) {
         if(txtAncho.getText().isEmpty() || txtLargo.getText().isEmpty() || txtAlto.getText().isEmpty()){            
            double unit = Double.parseDouble(txtPrecioProd.getValue());
            double total =  unit * Double.parseDouble(txtCantidad.getText());
            
            txtPrecioUCotizacion.setText(String.valueOf(unit));
            txtTotalParcial.setText(String.valueOf(total));
        }else{
            calcularprecio();
            
        }
    }

    @FXML
    private void txtAltoCambio(KeyEvent event) {
         if(txtAncho.getText().isEmpty() || txtLargo.getText().isEmpty() || txtAlto.getText().isEmpty()){
            double unit = Double.parseDouble(txtPrecioProd.getValue());
            double total =  unit * Double.parseDouble(txtCantidad.getText());
            
            txtPrecioUCotizacion.setText(String.valueOf(unit));
            txtTotalParcial.setText(String.valueOf(total));
        }else{
            calcularprecio();
        }
    }

    @FXML
    private void txtAnchoCambio(KeyEvent event) {
         if(txtAncho.getText().isEmpty() || txtLargo.getText().isEmpty() || txtAlto.getText().isEmpty()){
            double unit = Double.parseDouble(txtPrecioProd.getValue());
            double total =  unit * Double.parseDouble(txtCantidad.getText());
            
            txtPrecioUCotizacion.setText(String.valueOf(unit));
            txtTotalParcial.setText(String.valueOf(total));
        }else{
            calcularprecio();
        }
    }
    
    @FXML
    private void txtAumento(KeyEvent event) {                 
        calcularprecio();                    
    }
    
    @FXML
    private void btnImprimir(MouseEvent event) {
    }

    @FXML
    private void txtCantidadCambio(KeyEvent event) {
         
       double valor = Double.parseDouble(txtPrecioUCotizacion.getText())*Double.parseDouble(txtCantidad.getText());
        txtTotalParcial.setText(String.valueOf(valor));
    }

   

  
    @FXML
    private void BuscarMoldura(ActionEvent event) {
        
        int busqueda = txtMolduraReferencia.getValue().indexOf("|");
        String valor1 = txtMolduraReferencia.getValue().substring(0,busqueda);              
        String sql = "{call Sp_SearchCotizacionDetalle('"+valor1+"')}";        
        
        try {
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);            
            ResultSet rs= ps.executeQuery();
            
            while(rs.next()){                
                double alto = rs.getDouble("cotizacionAlto");
                double ancho = rs.getDouble("cotizacionAncho");
                double largo = rs.getDouble("cotizacionLargo");
                double valor= (alto*ancho*largo*factorVenta);
                String desc = rs.getString("cotizacionDesc");
                int lenght = desc.length();
                String descripcion = desc.substring(9, lenght);
   
                
                txtAncho.setText(String.valueOf(ancho));
                txtLargo.setText(String.valueOf(largo));
                txtAlto.setText(String.valueOf(alto));
                txtAlto.setText(String.valueOf(alto));
                txtDescripcion.setText(descripcion);
                txtPrecioUCotizacion.setText(String.valueOf(valor));
                txtTotalParcial.setText(String.valueOf(valor));
            }
        } catch (SQLException ex) {
             Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR CMB MOLDURA");
            noti.text("NO SE HA PODIDO CARGAR LA DB "+ex);
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
        }
    }

    
    @FXML
    private void validarNumero(KeyEvent event) {
        char letra = event.getCharacter().charAt(0);
        
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
   
    public ObservableList getCamposEspeciales(){
        ArrayList<CamposEspeciales> lista = new ArrayList();
        String sql = "{call Sp_SearchCamposEspecialescotizacion('"+codigoCotizacion+"')}";
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
        
        return listaCamposEsp2 = FXCollections.observableList(lista);
        
    }
    
    
    public void cargarDatosCamposEspeciales(){        
        tblCamposEspeciales.setItems(getCamposEspeciales());
        colDescCampoEspecial.setCellValueFactory(new PropertyValueFactory("campoNombre"));
        colPrecioCampoEspecial.setCellValueFactory(new PropertyValueFactory("campoPrecio"));
        colCodigoCampoEspecial.setCellValueFactory(new PropertyValueFactory("campoId"));
    }
    
    public void listCotizacion(){
        String sql = "{call Sp_SearchCotizaciones('"+codigoCotizacion+"')}";
        
        PreparedStatement ps;
        try {
            ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                
                txtPrecioUCotizacion.setText(rs.getString("cotizacionPrecioU"));
                txtTotalCotizacion.setText(rs.getString("cotizacionTotal"));
                txtVendedor.setValue(rs.getString("usuarioNombre")+" |"+ rs.getString("usuarioId"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR EN LA BASE DE DATOS");
            noti.text("NO SE HA REALIZADO LA BUSQUEDA DE COTIZACIÓN"+ex);
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
        }
        
        
        
        
    }
    
    public void activatTextBackup(){
        txtAncho.setEditable(true);
        txtLargo.setEditable(true);
        txtAlto.setEditable(true);
        txtDescripcion.setEditable(true);
        txtCantidad.setEditable(true);
        txtMolduraReferencia.setDisable(false);
        
        btnEditar.setDisable(false);
        btnEliminar.setDisable(false);
    }
    
    
     @FXML
    private void seleccionarElementosDetalle(MouseEvent event) {

            txtTotalParcial.setText("0");
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            ButtonType buttonTypeSi = new ButtonType("Si");
            ButtonType buttonTypeNo = new ButtonType("No");

            alert.setTitle("WARNING");
            alert.setHeaderText("EDITAR/ELIMINAR REGISTRO DE FACTURA");
            alert.setContentText("¿Está seguro que desea editar/eliminar este registro?");

            alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == buttonTypeSi){
                try{     
                    btnAgregar.setDisable(true);
                    int index = tblCotizacionDetalle.getSelectionModel().getSelectedIndex();
                    txtAncho.setText(colAnchoDetalle.getCellData(index).toString());
                    txtLargo.setText(colLargoDetalle.getCellData(index).toString());
                    txtAlto.setText(colAltoDetalle.getCellData(index).toString());
                    txtDescripcion.setText(colDetalleDescripcion.getCellData(index));
                    txtCantidad.setText(colCantidadDetalle.getCellData(index).toString());
                    txtPrecioUCotizacion.setText(colPrecioDetalle.getCellData(index).toString());
                    
                    activatTextBackup();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else{
                tblCotizacionDetalle.getSelectionModel().clearSelection();
            } 

        
        
    }
    
    
    @FXML
    private void seleccionarElementosCotizacion(MouseEvent event) {
        activateButtonsSeleccion();
        int index = tblCotizacion.getSelectionModel().getSelectedIndex();
        txtCodigo.setText(colCodigoCotizacion.getCellData(index).toString());
        txtFecha.setValue(LocalDate.parse(colFechaCotizacion.getCellData(index).toString()));
        txtNIT.setValue(colClienteCotizacion.getCellData(index));
        txtTotalCotizacion.setText(colTotalCotizacion.getCellData(index).toString());
        codigoCotizacion =colCodigoCotizacion.getCellData(index);
        cargarMore();
        cargarDatosCamposEspeciales();
        cargarDatosCotizacionesDetalle();
    }
    
    
    @FXML
    private void seleccionCampoEspecial(MouseEvent event){
        int index = tblCamposEspeciales.getSelectionModel().getSelectedIndex();
        
        String codigo = colCodigoCampoEspecial.getCellData(index).toString();
        String desc  = colDescCampoEspecial.getCellData(index).toString();
        String price  = colPrecioCampoEspecial.getCellData(index).toString();
        
        System.out.println("Campo Especial");
        System.out.println(codigo);

        Dialog dialog = new Dialog();
        dialog.setTitle("OPERACIONES CAMPOS ESPECIALES");
        dialog.setHeaderText("Seleccione la opcion a realizar...");
        dialog.setResizable(true);
                                                                        
        ButtonType buttonTypeEdit = new ButtonType("Editar", ButtonData.OK_DONE);
        ButtonType buttonTypeDelete = new ButtonType("Eliminar", ButtonData.CANCEL_CLOSE);
        
        dialog.getDialogPane().getButtonTypes().add(buttonTypeEdit);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeDelete);
        
        Optional<ButtonType> result = dialog.showAndWait();

        
        if(result.get() == buttonTypeEdit){                                                
            System.out.println("Opcion editar");                     

            Dialog dialogEdit = new Dialog();
            dialogEdit.setTitle("EDITAR CAMPO ESPECIAL");
            dialogEdit.setHeaderText("Ingresa los datos a editar");
            dialogEdit.setResizable(true);

            Label label1 = new Label("DESCRIPCIÓN: ");
            Label label2 = new Label("PRECIO: ");


            TextField descripcion = new TextField();
            TextField precio = new TextField();
            GridPane grid = new GridPane();
            
            descripcion.setText(desc);
            precio.setText(price);

            grid.add(label1, 1, 1);
            grid.add(descripcion, 2, 1);

            grid.add(label2, 1, 4);
            grid.add(precio, 2, 4);

            dialogEdit.getDialogPane().setContent(grid);

            ButtonType buttonTypeOk = new ButtonType("Editar", ButtonData.OK_DONE);
            ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);

            dialogEdit.getDialogPane().getButtonTypes().add(buttonTypeOk);
            dialogEdit.getDialogPane().getButtonTypes().add(buttonTypeCancel);

            Optional<ButtonType> resultEdit = dialogEdit.showAndWait();
            
            if(resultEdit.get() == buttonTypeOk){
                
                String sql = "{call Sp_UpdateCamposEspeciales('"+codigo+"','"+descripcion.getText()+"','"+precio.getText()+"')}";            
                
                try{
                    PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    ps.execute();
                    
                    double valor = (Double.parseDouble(txtTotalCotizacion.getText()) - Double.parseDouble(price));
                    System.out.println(valor);
                    valor = valor + Double.parseDouble(precio.getText());
                    System.out.println(valor);
                    
                    String sql1 = "{call Sp_UpdateCotizacionTotal('"+codigoCotizacion+"','"+valor+"','"+0+"')}";
                    PreparedStatement ps1 = Conexion.getIntance().getConexion().prepareCall(sql1);
                    ps1.execute();
                    
                    cargarDatosCamposEspeciales();
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgCorrecto));
                    noti.title("EDITADO CORRECTAMENTE");
                    noti.text("Campo especial editado correctamente");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    cargarDatosCotizaciones();
                    txtTotalCotizacion.setText(String.valueOf(valor));
                }catch (SQLException ex) {
                    ex.printStackTrace();
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR AL EDITAR CAMPO ESPECIAL");
                    noti.text("Error al editar campo especial");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                }               
            }else{
                
            }
            
        }else{            
            String sql="{call Sp_DeleteCamposEspeciales('"+codigo+"')}";
            try {
                PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                ps.executeQuery();                  
                cargarDatosCamposEspeciales();
                
                double valor = (Double.parseDouble(txtTotalCotizacion.getText()) - Double.parseDouble(price));

                String sql1 = "{call Sp_UpdateCotizacionTotal('"+codigoCotizacion+"','"+valor+"','"+0+"')}";
                PreparedStatement ps1 = Conexion.getIntance().getConexion().prepareCall(sql1);
                ps1.execute();
                
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgCorrecto));
                noti.title("ELIMINADO CORRECTAMENTE");
                noti.text("Campo especial eliminado correctamente");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
                cargarDatosCotizaciones();
                txtTotalCotizacion.setText(String.valueOf(valor));
            } catch (SQLException ex) {
                ex.printStackTrace();
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR AL ELIMINAR CAMPO ESPECIAL");
                noti.text("Error al eliminar campo especial");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
            }
        }
    }
    
    public void cargarMore(){
          String sql="{call Sp_SearchCotizaciones('"+codigoCotizacion+"')}";
        try {
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            FileTransfer fileTransfer = new FileTransfer();
            
            while(rs.next()){
                fileTransfer.getResource(rs.getString("cotizacionImg"));
                txtImagen.setText(rs.getString("cotizacionImg"));
                txtVendedor.setValue(rs.getString("usuarioNombre")+ " |"+rs.getString("usuarioId"));
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();            
        }
    }
    
    public ObservableList<cotizacionBackup> getCotizaciondetalle(){
        ArrayList<cotizacionBackup> lista = new ArrayList();
        String sql= "{call Sp_ListCotizacionDetalle('"+codigoCotizacion+"')}";
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new cotizacionBackup(
                    rs.getInt("detalleId"),
                    rs.getDouble("cotizacionCantida"),
                    rs.getString("cotizacionDesc"),
                    rs.getDouble("cotizacionAlto"),
                    rs.getDouble("cotizacionLargo"),
                    rs.getDouble("cotizacionAncho"),
                    rs.getDouble("cotizacionPrecioU"),
                    rs.getDouble("cotizacionTotalParcial")
                ));       
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR AL CARGAR DATOS BACKUP");
            noti.text("Error al cargar la base de datos"+ex);
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
        }
        
        return listaCotizacionesBack = FXCollections.observableList(lista);
    }
    
    
    public void cargarDatosCotizacionesDetalle(){
       tblCotizacionDetalle.setItems(getCotizaciondetalle());
       colCodigoDetalle.setCellValueFactory(new PropertyValueFactory("backupId"));
       colCantidadDetalle.setCellValueFactory(new PropertyValueFactory("cotizacionCantida"));
       colDetalleDescripcion.setCellValueFactory(new PropertyValueFactory("cotizacionDesc"));
       colAnchoDetalle.setCellValueFactory(new PropertyValueFactory("cotizacionAncho"));
       colAltoDetalle.setCellValueFactory(new PropertyValueFactory("cotizacionAlto"));
       colLargoDetalle.setCellValueFactory(new PropertyValueFactory("cotizacionLargo"));
       
       colPrecioDetalle.setCellValueFactory(new PropertyValueFactory("cotizacionPrecioU"));
       colTotalDetalle.setCellValueFactory(new PropertyValueFactory("cotizacionTotalParcial"));
       desactivarControles();
    }

     @FXML
    private void btnAgregarCampoEspecial(MouseEvent event) {
        Dialog dialog = new Dialog();
        dialog.setTitle("AGREGAR CAMPO ESPECIAL");
        dialog.setHeaderText("Ingrese el campo especial a agregar");
        dialog.setResizable(true);

        Label label1 = new Label("DESCRIPCIÓN: ");
        Label label2 = new Label("PRECIO: ");
        
        
        TextField descripcion = new TextField();
        TextField precio = new TextField();
        GridPane grid = new GridPane();
        
        grid.add(label1, 1, 1);
        grid.add(descripcion, 2, 1);
        
        grid.add(label2, 1, 4);
        grid.add(precio, 2, 4);
        
        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Guardar", ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
        
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);
        
        Optional<ButtonType> result = dialog.showAndWait();
        
        if(result.get() == buttonTypeOk){
            String sql = "{call Sp_AddCamposEspecialesCotizacion('"+descripcion.getText()+"','"+precio.getText()+"','"+codigoCotizacion+"')}";                        
            
                try{
                    PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    ps.execute();
                    double valor = (Double.parseDouble(txtTotalCotizacion.getText()) +Double.parseDouble(precio.getText()) );
                    String sql1 = "{call Sp_UpdateCotizacionTotal('"+codigoCotizacion+"','"+valor+"','"+0+"')}";
                    PreparedStatement ps1 = Conexion.getIntance().getConexion().prepareCall(sql1);
                    ps1.execute();
                    cargarDatosCamposEspeciales();
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgCorrecto));
                    noti.title("REGISTRO GUARDADO");
                    noti.text("SE HA GUARDADO EL CAMPO ESPECIAL");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                    cargarDatosCotizaciones();
                    txtTotalCotizacion.setText(String.valueOf(valor));
                }catch(SQLException ex){
                    ex.printStackTrace();
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR EN LA BASE DE DATOS");
                    noti.text("HUBO UN ERROR AL GUARDAR EL REGISTRO"+ex);
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
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
    private void btnAgregarImagen(MouseEvent event) {
        Stage stage = (Stage) anchor.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Buscar Imagen");

        // Agregar filtros para facilitar la busqueda
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("All Images", "*.*"),
            new FileChooser.ExtensionFilter("JPG", "*.jpg"),
            new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        // Obtener la imagen seleccionada
        File imgFile = fileChooser.showOpenDialog(stage);

        // Mostar la imagen
        if (imgFile != null) {
            FileTransfer filetransfer = new FileTransfer();
            filetransfer.putResource(imgFile);
            
            destino =  "C:/Program Files (x86)/ModuloCotizacion/img/"+imgFile.getName();
            dest = Paths.get(destino);
            origen =  imgFile.getPath();
            orig = Paths.get(origen);
            
            txtImagen.setText(imgFile.getName());


        }

    }
    
    

    @FXML
    private void verImagen(MouseEvent event) {
       // All of our necessary variables
        File imageFile;
        Image image;
        ImageView imageView;
        BorderPane pane;
        Scene scene;
        Stage stage;
        
        String sql="{call Sp_SearchCotizaciones('"+codigoCotizacion+"')}";
        
        try {
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            String file="";
            FileTransfer fileTransfer = new FileTransfer();
            
            while(rs.next()){                
                fileTransfer.getResource(rs.getString("cotizacionImg"));
                file = "C:/Program Files (x86)/ModuloCotizacion/ModuloCotizacion/img/"+rs.getString("cotizacionImg");
            }
            imageFile = new File(file);
            image = new Image(imageFile.toURI().toString());
            imageView = new ImageView(image);
            imageView.setFitWidth(850);
            imageView.setFitHeight(850);
            // Our image will sit in the middle of our popup.
            pane = new BorderPane();
            pane.setCenter(imageView);
            scene = new Scene(pane);

            // Create the actual window and display it.
            stage = new Stage();
            stage.setScene(scene);

            stage.showAndWait();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        
        
    }

    
    public void transferir(int codigo){
        String sql = "{call SpTransferirBackupCotizacion('"+codigo+"')}";
        
        try {
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ps.execute();
        } catch (SQLException ex) {
             ex.printStackTrace();
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR EN LA BASE DE DATOS");
            noti.text("NO SE HA GUARDADO EL REGISTRO BACKUP, INTENTELO DE NUEVO"+ex);
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
        }
        
    }
    
    
    @FXML
    private void btnGuardarCotizacion(MouseEvent event) {
        if(txtNIT.getValue().isEmpty() || txtNombre.getText().isEmpty() || txtDireccion.getText().isEmpty() || 
            txtVendedor.getValue().isEmpty()){
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR HAY CAMPOS VACÍOS");
            noti.text("LLENE TODOS LOS DATOS");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
        }else if(txtFecha.getValue() == null){
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR HAY CAMPOS VACÍOS");
            noti.text("CAMPO FECHA ESTA VACIO");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
        }else{            
            if(txtTipoCliente.getValue().equals("")){
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR HAY CAMPOS VACÍOS");
                noti.text("Por favor, selecciona un factor de venta");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();   
                noti.show();
            }else{                        
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                ButtonType buttonTypeSi = new ButtonType("Si");
                ButtonType buttonTypeNo = new ButtonType("No");
                alert.setTitle("AGREGAR REGISTRO");
                alert.setHeaderText("AGREGAR REGISTRO");
                alert.setContentText("¿Está seguro que desea guardar este registro?");

                alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);

                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == buttonTypeSi ){
                    Cotizaciones cotizacion = new Cotizaciones();

                    cotizacion.setNit(txtNIT.getValue());
                    int codigoTipo = buscarCodigoTipoClienteDos();

                    if(txtImagen.getText().equals("Seleccione el archivo...")){
                        cotizacion.setCotizacionImg("default.png");

                    }else{
                        cotizacion.setCotizacionImg(txtImagen.getText());

                    }

                    int codigoUsuario = buscarCodigoVendedor();

                    cotizacion.setCotizacionFecha(java.sql.Date.valueOf(txtFecha.getValue()));
                    Double TotalC =  Double.parseDouble(txtTotalCotizacion.getText());            
                    cotizacion.setCotizacionTotal(TotalC);
                      int busqueda = txtNIT.getValue().indexOf("|");

                    String valor = txtNIT.getValue().substring(0,busqueda);
                    String sql = "insert into Cotizacion(cotizacionCliente, cotizacionTipoClienteId, cotizacionMensajero, cotizacionImg, cotizacionFecha,   cotizacionDescuento, cotizacionDescuentoNeto, cotizacionTotal )\n" +
"				values('"+valor+"','"+codigoTipo+"','"+codigoUsuario+"','"+cotizacion.getCotizacionImg()+"','"+cotizacion.getCotizacionFecha()+"','"+0+"','"+0+"','"+cotizacion.getCotizacionTotal()+"');";
                    System.out.println(sql);
                    try{

                        Statement ps = Conexion.getIntance().getConexion().createStatement();
                         ps.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
                            
                        ResultSet rs = ps.getGeneratedKeys();
                        int rest=0;
                        
                        if (rs.next()){
                            rest=rs.getInt(1);

                        }
                        
                        System.out.println("VALOR DE EL NÚMERO DE ID "+rest);

                        transferir(rest);
                        System.out.println(txtImagen.getText());
                        

                        tblCotizacionDetalle.getItems().clear();
                        Notifications noti = Notifications.create();
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("REGISTRO GUARDADO");
                        noti.text("SE HA GUARDADO LA COTIZACIÓN CON ÉXITO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();   
                        noti.show();
                        cargarDatosCotizaciones();
                        cargarDatosCotizacionesBuscada(rest);
                        limpiarText();
                        limpiarTextBack();
                        cargar.setDisable(false);
                    }catch(SQLException ex){
                        ex.printStackTrace();
                        Notifications noti = Notifications.create();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR EN LA BASE DE DATOS");
                        noti.text("NO SE HA GUARDADO EL REGISTRO, INTENTELO DE NUEVO"+ex);
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();   
                        noti.show();
                    } 
                }else{
                    Notifications noti = Notifications.create();
                        noti.graphic(new ImageView(imgError));
                        noti.title("OPERACIÓN CANCELADA");
                        noti.text("SE HA CANCELADO LA OPERACIÓN");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();   
                        noti.show();
                }
            }
        }
        
    }

    


      @FXML
    private void btnGenerarWord(MouseEvent event) {
        generarCotizacion generar = new generarCotizacion();
        
        generar.generar(listaCamposEsp2, listaCotizacionesBack,0, Double.parseDouble(txtTotalCotizacion.getText()), txtImagen.getText(), anchor, Integer.parseInt(txtCodigo.getText()), txtNIT.getValue(), txtNombre.getText(), txtDireccion.getText(), 0, Double.parseDouble(txtTotalCotizacion.getText()), txtFecha.getValue().toString());
        
    }
    
    //BTN EDITAR
          @FXML
    private void btnEditar(MouseEvent event) {
        if(txtAncho.getText().equals("") || txtLargo.getText().isEmpty() || txtAlto.getText().isEmpty() ||  txtCantidad.getText().isEmpty()){
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR");
            noti.text("HAY UN CAMPO VACÍO");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
        }else{
            int index = tblCotizacionDetalle.getSelectionModel().getSelectedIndex();
            cotizacionBackup nuevoDetalle = new cotizacionBackup();
            nuevoDetalle.setBackupId(colCodigoDetalle.getCellData(index));
            
            nuevoDetalle.setCotizacionAncho(Double.parseDouble(txtAncho.getText()));            
            nuevoDetalle.setCotizacionLargo(Double.parseDouble(txtLargo.getText()));
            nuevoDetalle.setCotizacionAlto(Double.parseDouble(txtAlto.getText()));            
            nuevoDetalle.setCotizacionCantida(Double.parseDouble(txtCantidad.getText()));
            nuevoDetalle.setCotizacionDesc(txtDescripcion.getText());
            nuevoDetalle.setCotizacionPrecioU(Double.parseDouble(txtPrecioUCotizacion.getText()));
            nuevoDetalle.setCotizacionTotalParcial(Double.parseDouble(txtCantidad.getText())*Double.parseDouble(txtPrecioUCotizacion.getText()));                                    
            String sql="";
            if(codigoCotizacion != 0 || codigoCotizacion >0){
                sql = "{call Sp_UpdateDetalleCotizacion('"+nuevoDetalle.getBackupId()+"','"+nuevoDetalle.getCotizacionCantida()+"','"+nuevoDetalle.getCotizacionDesc()+"','"+nuevoDetalle.getCotizacionAlto()+"','"+nuevoDetalle.getCotizacionLargo()+"','"+nuevoDetalle.getCotizacionAncho()+"','"+nuevoDetalle.getCotizacionPrecioU()+"','"+nuevoDetalle.getCotizacionTotalParcial()+"','"+txtCodigo.getText()+"')}";
            }else{
                sql = "{call Sp_UpdateBackUpCotizacion('"+nuevoDetalle.getBackupId()+"','"+nuevoDetalle.getCotizacionCantida()+"','"+nuevoDetalle.getCotizacionDesc()+"','"+nuevoDetalle.getCotizacionAlto()+"','"+nuevoDetalle.getCotizacionLargo()+"','"+nuevoDetalle.getCotizacionAncho()+"','"+nuevoDetalle.getCotizacionPrecioU()+"','"+nuevoDetalle.getCotizacionTotalParcial()+"')}";
            }   
            System.out.println(sql);
           try{
               PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
               ps.execute();
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgCorrecto));
                noti.title("OPERACIÓN EXITOSA");
                noti.text("SE HA EDITADO EXITOSAMENTE EL REGISTRO");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
                tipoOperacion = Operacion.NINGUNO;
                txtTotalParcial.setText("0");
                cargarDatosCotizaciones();
                cargarDatosCotizacionesBack();
                btnEditar.setDisable(true);
                btnEliminar.setDisable(true);
           }catch(SQLException ex){
               ex.printStackTrace();
                 Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR");
                noti.text("NO SE HA PODIDO ACTUALIZAR EL CAMPO");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
           }
        }
    }
    
    @FXML
    public void btnEliminar(){
        if(tipoOperacion == Operacion.GUARDAR){
               tipoOperacion = Operacion.CANCELAR;
               accion();
        }else{
            System.out.println(tipoOperacion);
            int index = tblCotizacionDetalle.getSelectionModel().getSelectedIndex();
            cotizacionBackup nuevoDetalle = new cotizacionBackup();
            nuevoDetalle.setBackupId(colCodigoDetalle.getCellData(index));
            
            String sql="";
            if(codigoCotizacion != 0 || codigoCotizacion >0){
                sql = "{call SpEliminarDetalleBack('"+nuevoDetalle.getBackupId()+"')}";
            }else{
                sql = "{call SpEliminarBackuCo('"+nuevoDetalle.getBackupId()+"')}";
            }   
            
            
            try{
                PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                ps.execute();
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgCorrecto));
                noti.title("OPERACIÓN EXITOSA");
                noti.text("SE HA EDITADO EXITOSAMENTE EL REGISTRO");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
                tipoOperacion = Operacion.NINGUNO;
                cargarDatosCotizacionesBack();
                btnEditar.setDisable(true);
                btnEliminar.setDisable(true);
            }catch(Exception e){
                e.printStackTrace();
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR");
                noti.text("NO SE HA PODIDO ELIMINAR EL CAMPO");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
            }
        }
    }
    
    
             
    public ObservableList<String> llenarEmpleado(){
        ArrayList<String> lista = new ArrayList();
        String sql= "{call Sp_ListUsuarioEmpleado()}";
            int x =0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x, rs.getString("usuarioNombre")+ " |"+rs.getString("usuarioId"));
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
        return listaEmpleado = FXCollections.observableList(lista);
    }    
    
   
    
    @FXML
    public void btnProduccion(){
    Dialog dialog = new Dialog();
        dialog.setTitle("TRANSFERIR A PRODUCCION");
        dialog.setHeaderText("Ingrese los campos para transferir a produccion.");
        dialog.setResizable(true);
        Label label1 = new Label("Fecha de final: ");
        
        JFXDatePicker fechaFinal= new JFXDatePicker();
        GridPane grid = new GridPane();
        
        
        Label label2 = new Label("Operador:");
        ComboBox<String> text = new ComboBox();
        
        text.setItems(llenarEmpleado());
        
        
        new AutoCompleteComboBoxListener(text);
        
        
        
        grid.add(label1, 1, 1);
        grid.add(fechaFinal, 2, 1);
        
        grid.add(label2, 1, 4);
        grid.add(text, 2, 4);
        
        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Guardar", ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
        
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);
        
        Optional<ButtonType> result = dialog.showAndWait();
        
        LocalDate date1 = txtFecha.getValue();
        LocalDate date2 = fechaFinal.getValue();
        
        int codigoC = Integer.parseInt(txtCodigo.getText());
        int estado = 1;
        
        
        int codigo1=0;   
        if(result.get() == buttonTypeOk){
            if(!text.getValue().isEmpty()){
                 int busqueda = text.getValue().indexOf("|");
                String valor1 = text.getValue().substring(busqueda+1, text.getValue().length());
                codigo1 = Integer.parseInt(valor1);
                String sql = "{call Sp_TransferirCotizacion('"+codigoC+"','"+estado+"','"+date1+"','"+date2+"','"+codigo1+"')}";
                try {
                    PreparedStatement psProdccion = Conexion.getIntance().getConexion().prepareCall(sql);

                    psProdccion.execute();

                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgCorrecto));
                    noti.title("TRANSFERENCIA GUARDADA");
                    noti.text("Se ha trasnferido la cotización exitosamente");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                    limpiarText();
                    limpiarTextBack();
                } catch (SQLException ex) {

                    if(ex.getErrorCode() == 1062){
                        Notifications noti = Notifications.create();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR COTIZACIÓN DUPLICADA");
                        noti.text("Ya existe esta cotización en producción");
                        ex.printStackTrace();
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(10));
                        noti.darkStyle();   
                        noti.show();
                    }else{
                        Notifications noti = Notifications.create();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR");
                        noti.text("hubo un error en la base de datos"+ex);
                        ex.printStackTrace();
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(10));
                        noti.darkStyle();   
                        noti.show();
                    }


                }
        
            }
        }else{
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("TRANSFERENCIA NO GUARDADA");
            noti.text("No se ha podido transferir cotización a producción");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
        }
            
        
    }
    
  
    
    @FXML
    private void btnAgregar(MouseEvent event) {
        if(tipoOperacion == Operacion.GUARDAR){
            if( txtNIT.getValue().isEmpty() || txtNombre.getText().isEmpty() || txtDireccion.getText().isEmpty() || txtVendedor.getValue().isEmpty() || 
               txtAncho.getText().isEmpty() || txtAlto.getText().isEmpty() || txtLargo.getText().isEmpty() || txtDescripcion.getText().isEmpty() || txtCantidad.getText().isEmpty()){
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR");
                noti.text("HAY CAMPOS VACÍOS");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();   
                noti.show();
                System.out.println(txtFecha.getValue());
            }else{
                
                    cotizacionBackup cotBack = new cotizacionBackup();                    
                    
                    cotBack.setCotizacionAlto(Double.parseDouble(txtAlto.getText()));
                    cotBack.setCotizacionAncho(Double.parseDouble(txtAncho.getText()));
                    cotBack.setCotizacionLargo(Double.parseDouble(txtLargo.getText()));
                    cotBack.setCotizacionDesc(txtDescripcion.getText());
                    cotBack.setCotizacionCantida(Double.parseDouble(txtCantidad.getText()));
                    Double precioUnit = Double.parseDouble(txtPrecioUCotizacion.getText());
                    cotBack.setCotizacionPrecioU(precioUnit);
                    
                    
                    Double TotalC = Double.parseDouble(txtCantidad.getText())*precioUnit;
                    
                    cotBack.setCotizacionTotalParcial(TotalC);
                    
                    
                    
                    if(tipoOperacion == Operacion.GUARDAR){            
                        if(txtDescripcion.getText().length() > 100){
                            Notifications noti = Notifications.create();
                            noti.graphic(new ImageView(imgError));
                            noti.title("ERROR");
                            noti.text("El CAMPO DE CÓDIGO NO PUEDE SER MAYOR A 100 CARACTERES");
                            noti.position(Pos.BOTTOM_RIGHT);
                            noti.hideAfter(Duration.seconds(4));
                            noti.darkStyle();   
                            noti.show();
                            tipoOperacion = Operacion.GUARDAR;
                        }else{
                            String sql = "{call Sp_AddCotizacionBackup('"+cotBack.getCotizacionCantida()+"','"+cotBack.getCotizacionAlto()+"','"+cotBack.getCotizacionAncho()+"','"+cotBack.getCotizacionLargo()+"','"+cotBack.getCotizacionDesc()+"', '"+cotBack.getCotizacionPrecioU()+"','"+cotBack.getCotizacionTotalParcial()+"')}";
                            
                            accionProveedores(sql);
                            
                        }
                    }
                }
        }else{
            System.out.println("agregar");
            tipoOperacion = Operacion.AGREGAR;
            accion();
        }
    }
    
    
    //Agregar cliente
    public void btnAgregarCliente(){
    Dialog dialog = new Dialog();
        dialog.setTitle("Agregar Cliente");
        dialog.setHeaderText("Ingrese los campos necesarios.");
        dialog.setResizable(true);
        
        Label label1 = new Label("NIT: ");        
        JFXTextField nit = new JFXTextField();
        GridPane grid = new GridPane();        
        
        Label label2 = new Label("Nombre:");
        JFXTextField nombre = new JFXTextField();
        
        Label label3 = new Label("Direccion:");
        JFXTextField direccion = new JFXTextField();
        
        Label label4 = new Label("Telefono:");
        JFXTextField telefono = new JFXTextField();
        
        Label label5 = new Label("Correo:");
        JFXTextField correo = new JFXTextField();
                        
        
        grid.add(label1, 1, 1);
        grid.add(nit, 2, 1);
        
        grid.add(label2, 1, 4);
        grid.add(nombre, 2, 4);
        
        grid.add(label3, 1, 8);
        grid.add(direccion, 2, 8);
        
        grid.add(label4, 1, 12);
        grid.add(telefono, 2, 12);
        
        grid.add(label5, 1, 16);
        grid.add(correo, 2, 16);
        
        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Guardar", ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
        
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);
        
        Optional<ButtonType> result = dialog.showAndWait();

        
        if(result.get() == buttonTypeOk){
            if(!nit.getText().isEmpty() || !nombre.getText().isEmpty() || !direccion.getText().isEmpty() || !telefono.getText().isEmpty() || !correo.getText().isEmpty()){
                
                                
                String sql = "{call SpAgregarClientes('"+nit.getText()+"','"+nombre.getText()+"','"+direccion.getText()+"','"+telefono.getText()+"','"+correo.getText()+"')}";
                try {
                    PreparedStatement psProdccion = Conexion.getIntance().getConexion().prepareCall(sql);

                    psProdccion.execute();

                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgCorrecto));
                    noti.title("CLIENTE GUARDADO");
                    noti.text("Se ha guardado exitosamente");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();                    
                    llenarComboNit();
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
                }        
            }
        }else{
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("OPCION CANCELADA");
            noti.text("Se ha cancelado el proceso");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
        }                    
    }
    
    
    @FXML
    private void cargar(MouseEvent event) {
        llenarComboNit();
        llenarComboVendedor();
        desactivarTextBack();
        llenarComboTipoC();
        limpiarText();
        disableButtonsSeleccion();
        cargarDatosCotizaciones();
        llenarMolduras();
        cargarDatosCotizacionesBack();
        txtAncho.setText("0.00");
        txtLargo.setText("0.00");
        txtAlto.setText("0.00");
        txtCantidad.setText("1");
        calcularprecio();
        validarTabla();
        codigoCotizacion = 0;
    }
}

