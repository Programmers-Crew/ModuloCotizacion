package org.ModuloCotizacion.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.ModuloCotizacion.bean.Animations;
import org.ModuloCotizacion.bean.CambioScene;
import org.ModuloCotizacion.bean.Cotizaciones;
import org.ModuloCotizacion.bean.ValidarStyle;
import org.ModuloCotizacion.db.Conexion;
import org.controlsfx.control.Notifications;

public class CotizacionesViewController implements Initializable {

    @FXML
    private AnchorPane anchor;



 

    
    
    //Variables
    public enum Operacion{AGREGAR,GUARDAR,ELIMINAR,BUSCAR,ACTUALIZAR,CANCELAR,NINGUNO, SUMAR, RESTAR};
    public Operacion cancelar = Operacion.NINGUNO;
    public Operacion tipoOperacion = Operacion.NINGUNO;

    
    CambioScene cambioScene = new CambioScene();
    Image imgError = new Image("org/ModuloCotizacion/img/error.png");
    Image imgCorrecto= new Image("org/ModuloCotizacion/img/correcto.png");
    Image imgWarning = new Image("org/ModuloCotizacion/img/warning.png");
    
    MenuPrincipalContoller menu = new MenuPrincipalContoller();
    ValidarStyle validar = new ValidarStyle();
    
    Animations animacion = new Animations();
    
    ObservableList<Cotizaciones> listaCotizaciones;
    ObservableList<String> listaNit;
    ObservableList<String> listaTipoCliente;
    ObservableList<String> listaVendedor;
    ObservableList<String> listaFactorVenta;
    ObservableList<String> listaMolduraRef;
    ObservableList<String> listaCodigoCotizaciones;
     ObservableList<String> listaProducto;
    
    Integer codigo = 0;
    String tipoClienteDescuento = "";
    
    @FXML
    private AnchorPane anchor1;
    @FXML
    private AnchorPane anchor2;
    
    //TextField
    @FXML
    private JFXTextField txtCodigo;
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
    @FXML
    private TextField txtDescuento;
    
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
    private TableView<Cotizaciones> tblCamposEspeciales;
    @FXML
    private TableColumn<Cotizaciones, Integer> colCodigoCotizacion;
    @FXML
    private TableColumn<Cotizaciones, Date> colFechaCotizacion;
    @FXML
    private TableColumn<Cotizaciones, Double> colCantidadCotizacion;
    @FXML
    private TableColumn<Cotizaciones, Double> colAnchoCotizacion;
    @FXML
    private TableColumn<Cotizaciones, Double> colAltoCotizacion;
    @FXML
    private TableColumn<Cotizaciones, Double> colLargoCotizacion;
    @FXML
    private TableColumn<Cotizaciones, String> colDescCotizacion;
    
    //CamposEspeciales
    @FXML
    private TableView<Cotizaciones> tblCotizacion;
    @FXML
    private TableColumn<Cotizaciones, String> colDescCampoEspecial;
    @FXML
    private TableColumn<Cotizaciones, Double> colPrecioCampoEspecial;
    
    //Totales
    @FXML
    private JFXTextField txtDescuentoCotizacion;
    @FXML
    private JFXTextField txtPrecioUCotizacion;
    @FXML
    private JFXTextField txtTotalCotizacion;
    
    public void limpiarText(){
        txtCodigo.setText("");
        txtNIT.setValue("");
        txtNombre.setText("");
        txtDireccion.setText("");        
        txtTipoCliente.setValue("");
        txtVendedor.setValue("");
        txtMolduraReferencia.setValue("");
        txtAncho.setText("");
        txtLargo.setText("");        
        txtAlto.setText("");
        txtDescripcion.setText("");
        txtCantidad.setText("");
        txtDescuento.setText("");        
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
        txtCodigo.setEditable(false);
        txtNIT.setDisable(true);
        txtNombre.setEditable(false);
        txtDireccion.setEditable(false);    
        txtTipoCliente.setDisable(true);
        txtVendedor.setDisable(true);
        txtMolduraReferencia.setDisable(true);
        txtAncho.setEditable(false);
        txtLargo.setEditable(false);  
        txtAlto.setEditable(false);
        txtDescripcion.setEditable(false);
        txtCantidad.setEditable(false);
        txtDescuento.setEditable(false);
    }
    
    public void activarText(){
        txtCodigo.setEditable(true);
        txtNIT.setDisable(false);
        txtNombre.setEditable(true);
        txtDireccion.setEditable(true);    
        txtTipoCliente.setDisable(false);
        txtVendedor.setDisable(false);
        txtMolduraReferencia.setDisable(false);
        txtAncho.setEditable(true);
        txtLargo.setEditable(true);  
        txtAlto.setEditable(true);
        txtDescripcion.setEditable(true);
        txtCantidad.setEditable(true);
        txtDescuento.setEditable(true);
    }

    public ObservableList<Cotizaciones> getProveedores(){
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
                              rs.getString("cotizacionImg"),
                              rs.getDate("cotizacionFecha"),
                              rs.getDouble("cotizacionCantida"),
                              rs.getString("cotizacionModeloRef"),
                              rs.getString("cotizacionTipoPrecio"),
                              rs.getDouble("cotizacionAlto"),
                              rs.getDouble("cotizacionAncho"),
                              rs.getDouble("cotizacionLargo"),
                              rs.getString("cotizacionDesc"),
                              rs.getDouble("cotizacionDescuentoNeto"),
                              rs.getDouble("cotizacionDescuento"),
                              rs.getDouble("cotizacionPrecioU"),
                              rs.getDouble("cotizacionTotal"),                        
                              rs.getString("clienteNombre"),
                              rs.getString("tipoClienteDesc"),
                              rs.getString("usuarioNombre"),
                              rs.getString("productoDesc"),
                              rs.getString("campoNombre"),
                              rs.getDouble("campoPrecio")                                                
                ));                
                listaCodigo.add(x, rs.getString("cotizacionId;"));
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
    
    
    public void cargarDatosProveedores(){
       tblCotizacion.setItems(getProveedores());
       colCodigoCotizacion.setCellValueFactory(new PropertyValueFactory("cotizacionId"));
       colFechaCotizacion.setCellValueFactory(new PropertyValueFactory("cotizacionFecha"));
       colCantidadCotizacion.setCellValueFactory(new PropertyValueFactory("cotizacionCantida"));
       colAnchoCotizacion.setCellValueFactory(new PropertyValueFactory("cotizacionAncho"));
       colAltoCotizacion.setCellValueFactory(new PropertyValueFactory("cotizacionAlto"));
       colLargoCotizacion.setCellValueFactory(new PropertyValueFactory("cotizacionLargo"));
       colDescCotizacion.setCellValueFactory(new PropertyValueFactory("cotizacionDesc"));
       
       limpiarText();
       desactivarControles();
    }
    
    public void accion(){
        switch(tipoOperacion){
            case AGREGAR:
                tipoOperacion = Operacion.GUARDAR;
                cancelar = Operacion.CANCELAR;
                desactivarControles();
                btnAgregar.setText("GUARDAR");
                btnEliminar.setText("CANCELAR");
                btnEliminar.setDisable(false);
                activarText();
                btnBuscar.setDisable(true);
                limpiarText();
                break;
            case CANCELAR:
                tipoOperacion = Operacion.NINGUNO;
                desactivarControles();
                desactivarText();
                btnAgregar.setText("AGREGAR");
                btnEliminar.setText("ELIMINAR");
                limpiarText();
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
                        ps = Conexion.getIntance().getConexion().prepareCall(sql);
                        ps.execute();
                        
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("OPERACIÓN EXITOSA");
                        noti.text("SE HA AGREGADO EXITOSAMENTE EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacion = Operacion.CANCELAR;
                        accion();
                        cargarDatosProveedores();
                        
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL AGREGAR");
                        noti.text("HA OCURRIDO UN ERROR AL GUARDAR EL REGISTRO");
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
                        cargarDatosProveedores();
                        tipoOperacion = Operacion.CANCELAR;
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
                        cargarDatosProveedores();
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
    
    
    @FXML
    private void btnAgregar(MouseEvent event) {
        if(tipoOperacion == Operacion.GUARDAR){
            if(txtCodigo.getText().isEmpty() || txtNIT.getValue().isEmpty() || txtNombre.getText().isEmpty() || txtDireccion.getText().isEmpty() || txtTipoCliente.getValue().isEmpty() || txtVendedor.getValue().isEmpty() || 
               txtAncho.getText().isEmpty() || txtAlto.getText().isEmpty() || txtLargo.getText().isEmpty() || txtDescripcion.getText().isEmpty() || txtCantidad.getText().isEmpty()){
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR");
                noti.text("HAY CAMPOS VACÍOS");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();   
                noti.show();
            }else{
                    Cotizaciones nuevoCotizaciones = new Cotizaciones();
                    nuevoCotizaciones.setCotizacionId(Integer.parseInt(txtCodigo.getText()));                    
                    nuevoCotizaciones.setClienteNombre(txtNIT.getValue());
                    nuevoCotizaciones.setTipoClienteDesc(txtTipoCliente.getValue());
                    nuevoCotizaciones.setUsuarioNombre(txtVendedor.getValue());
                    nuevoCotizaciones.setCotizacionImg("Tipo Precios");
                    nuevoCotizaciones.setCotizacionFecha(java.sql.Date.valueOf(txtFecha.getValue()));
                    nuevoCotizaciones.setCotizacionCantida(Double.parseDouble(txtCantidad.getText()));
                    nuevoCotizaciones.setCotizacionModeloRef(txtMolduraReferencia.getValue());
                    nuevoCotizaciones.setCotizacionTipoPrecio("Tipo Precios");
                    nuevoCotizaciones.setCotizacionAlto(Double.parseDouble(txtAlto.getText()));
                    nuevoCotizaciones.setCotizacionAncho(Double.parseDouble(txtAncho.getText()));
                    nuevoCotizaciones.setCotizacionLargo(Double.parseDouble(txtLargo.getText()));
                    nuevoCotizaciones.setCotizacionDesc(txtDescripcion.getText());
                    nuevoCotizaciones.setCotizacionDescuento(Double.parseDouble(txtDescuento.getText()));
                    
                    Double descuentoNeto =  Double.parseDouble(txtDescuento.getText());
                    nuevoCotizaciones.setCotizacionDescuentoNeto(descuentoNeto);
                    
                    Double precioUnit = (Double.parseDouble(txtAlto.getText()) * Double.parseDouble(txtAncho.getText()) * Double.parseDouble(txtLargo.getText()))*0.0005;                    
                    nuevoCotizaciones.setCotizacionPrecioU(precioUnit);
                    
                    
                    Double descuentoR = (precioUnit * Double.parseDouble(txtCantidad.getText()))*(Double.parseDouble(txtDescuento.getText())/100);
                    Double TotalC = (precioUnit * Double.parseDouble(txtCantidad.getText())) - descuentoR;
                    nuevoCotizaciones.setCotizacionTotal(TotalC);
                    
                    
                    
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
                            String sql = "{call Sp_AddCotizacion('"+nuevoCotizaciones.getCotizacionId()+"','"+nuevoCotizaciones.getClienteNombre()+"','"+buscarCodigoTipoClienteDos()
                            +"','"+buscarCodigoVendedor()+"','"+nuevoCotizaciones.getCotizacionImg()+"','"+nuevoCotizaciones.getCotizacionFecha()+"','"+nuevoCotizaciones.getCotizacionCantida()+"','"+nuevoCotizaciones.getCotizacionModeloRef()
                            +"','"+nuevoCotizaciones.getCotizacionTipoPrecio()+"','"+nuevoCotizaciones.getCotizacionAlto()+"','"+nuevoCotizaciones.getCotizacionAncho()
                            +"','"+nuevoCotizaciones.getCotizacionLargo()+"','"+nuevoCotizaciones.getCotizacionDesc()+"','"+nuevoCotizaciones.getCotizacionDescuento()+"','"+nuevoCotizaciones.getCotizacionDescuentoNeto()+"','"+nuevoCotizaciones.getCotizacionPrecioU()+"','"+nuevoCotizaciones.getCotizacionTotal()+"')}";                            
                            accionProveedores(sql);
                            
                        }
                    }
                }
        }else{
            tipoOperacion = Operacion.AGREGAR;
            accion();
        }
    }
    
   
        
    public int buscarCodigoTipoClienteDos(){    
        int busqueda = txtTipoCliente.getValue().indexOf("|");
        
        String valor1 = txtTipoCliente.getValue().substring(busqueda+1, txtTipoCliente.getValue().length());
        int codigoTC = Integer.parseInt(valor1);
        return codigoTC;
    }     
    
    
    @FXML
    private void tipoClienteDescuento(ActionEvent event) {
        
        String sql = "{call Sp_ListTipoClienteCC('"+buscarCodigoTipoClienteDos()+"')}";
        
        try {
            
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                double  ancho = Double.parseDouble(txtAncho.getText()),largo = Double.parseDouble(txtLargo.getText()), alto = Double.parseDouble(txtAlto.getText());
                double valor = (ancho * largo * alto * 0.0005)- Double.parseDouble(txtDescuentoCotizacion.getText());
                double precioUnitario1 = valor/Double.parseDouble(txtCantidad.getText());


                txtDescuentoCotizacion.setText(txtDescuento.getText());
                txtPrecioUCotizacion.setText(String.valueOf(precioUnitario1));
                txtTotalCotizacion.setText(String.valueOf(valor));
                
                txtDescuento.setText(rs.getString("tipoClienteDescuento"));
                
                double descuentoTexto = Double.parseDouble(txtDescuento.getText())* Double.parseDouble(txtTotalCotizacion.getText());
                
                txtDescuentoCotizacion.setText(String.valueOf(descuentoTexto));
                
                double precioTotal = Double.parseDouble(txtTotalCotizacion.getText())- descuentoTexto;
                
                double precioUnitario = precioTotal/Double.parseDouble(txtCantidad.getText());
                
                txtPrecioUCotizacion.setText(String.valueOf(precioUnitario));
                
                txtTotalCotizacion.setText(String.valueOf(precioTotal));
                
                txtPrecioUCotizacion.setText(String.valueOf(precioUnitario));
                
            }
            
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
    }    
   
    
    public void llenarComboNit(){
        ArrayList<String> lista = new ArrayList();
        String sql= "{call Sp_ListCliente()}";
            int x =0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x, rs.getString("clienteNit"));
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
    }   

    
        
        
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarComboNit();
        llenarComboVendedor();
        llenarComboTipoC();
        limpiarText();
        llenarMolduras();
        txtDescuentoCotizacion.setText("0.00");
        txtAncho.setText("0.00");
        txtLargo.setText("0.00");
        txtAlto.setText("0.00");
        txtCantidad.setText("1");
        txtDescuento.setText("0.00");
        calcularprecio();
    }    

    public void llenarMolduras(){
        ArrayList<String> lista = new ArrayList();
        try {
            String sql = "{call Sp_ListarCotizaciones()}";
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                lista.add(rs.getString("cotizacionId"));
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
    private void seleccionarElementos(MouseEvent event) {
    }
    
    
    @FXML
    private void cambiarNit(ActionEvent event) {
        
        String sql = "{call Sp_FindClientesNIt('"+txtNIT.getValue()+"')}";
        
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
    
 

    private void calcularprecio() {
        if(txtAncho.getText().isEmpty() || txtLargo.getText().isEmpty() || txtAlto.getText().isEmpty()){
            
        }else{
            double  ancho = Double.parseDouble(txtAncho.getText()),largo = Double.parseDouble(txtLargo.getText()), alto = Double.parseDouble(txtAlto.getText());
            double valor = (ancho * largo * alto * 0.0005)- Double.parseDouble(txtDescuentoCotizacion.getText());
            double precioUnitario = valor/Double.parseDouble(txtCantidad.getText());
            
            
            txtDescuentoCotizacion.setText(txtDescuento.getText());
            txtPrecioUCotizacion.setText(String.valueOf(precioUnitario));
            txtTotalCotizacion.setText(String.valueOf(valor));
            
        }
    }



      @FXML
    private void txtLargoCambio(KeyEvent event) {
         if(txtAncho.getText().isEmpty() || txtLargo.getText().isEmpty() || txtAlto.getText().isEmpty()){
            
        }else{
            double  ancho = Double.parseDouble(txtAncho.getText()),largo = Double.parseDouble(txtLargo.getText()), alto = Double.parseDouble(txtAlto.getText());
            double valor = (ancho * largo * alto * 0.0005)- Double.parseDouble(txtDescuentoCotizacion.getText());
            double precioUnitario = valor/Double.parseDouble(txtCantidad.getText());
            
            
            txtDescuentoCotizacion.setText(txtDescuento.getText());
            txtPrecioUCotizacion.setText(String.valueOf(precioUnitario));
            txtTotalCotizacion.setText(String.valueOf(valor));
            
        }
    }

    @FXML
    private void txtAltoCambio(KeyEvent event) {
         if(txtAncho.getText().isEmpty() || txtLargo.getText().isEmpty() || txtAlto.getText().isEmpty()){
            
        }else{
            double  ancho = Double.parseDouble(txtAncho.getText()),largo = Double.parseDouble(txtLargo.getText()), alto = Double.parseDouble(txtAlto.getText());
            double valor = (ancho * largo * alto * 0.0005)- Double.parseDouble(txtDescuentoCotizacion.getText());
            double precioUnitario = valor/Double.parseDouble(txtCantidad.getText());
            
            
            txtDescuentoCotizacion.setText(txtDescuento.getText());
            txtPrecioUCotizacion.setText(String.valueOf(precioUnitario));
            txtTotalCotizacion.setText(String.valueOf(valor));
            
        }
    }

    @FXML
    private void txtAnchoCambio(KeyEvent event) {
         if(txtAncho.getText().isEmpty() || txtLargo.getText().isEmpty() || txtAlto.getText().isEmpty()){
            
        }else{
            double  ancho = Double.parseDouble(txtAncho.getText()),largo = Double.parseDouble(txtLargo.getText()), alto = Double.parseDouble(txtAlto.getText());
            double valor = (ancho * largo * alto * 0.0005)- Double.parseDouble(txtDescuentoCotizacion.getText());
            double precioUnitario = valor/Double.parseDouble(txtCantidad.getText());
            
            
            txtDescuentoCotizacion.setText(txtDescuento.getText());
            txtPrecioUCotizacion.setText(String.valueOf(precioUnitario));
            txtTotalCotizacion.setText(String.valueOf(valor));
            
        }
    }
    
    
    @FXML
    private void btnImprimir(MouseEvent event) {
    }

    @FXML
    private void txtCantidadCambio(KeyEvent event) {
        double  ancho = Double.parseDouble(txtAncho.getText()),largo = Double.parseDouble(txtLargo.getText()), alto = Double.parseDouble(txtAlto.getText());
        double valor = (ancho * largo * alto * 0.0005* Double.parseDouble(txtCantidad.getText()))- Double.parseDouble(txtDescuentoCotizacion.getText());
       
        txtTotalCotizacion.setText(String.valueOf(valor));
    }

    @FXML
    private void txtDescuentoCambio(KeyEvent event) {
        double  ancho = Double.parseDouble(txtAncho.getText()),largo = Double.parseDouble(txtLargo.getText()), alto = Double.parseDouble(txtAlto.getText());
        double valor = (ancho * largo * alto * 0.0005)- Double.parseDouble(txtDescuentoCotizacion.getText());
        double precioUnitario1 = valor/Double.parseDouble(txtCantidad.getText());

        
        double descuentoTexto = Double.parseDouble(txtDescuento.getText())* Double.parseDouble(txtTotalCotizacion.getText());
        txtDescuentoCotizacion.setText(txtDescuento.getText());
        txtPrecioUCotizacion.setText(String.valueOf(precioUnitario1));
        txtTotalCotizacion.setText(String.valueOf(valor));
                
        txtDescuentoCotizacion.setText(String.valueOf(descuentoTexto));

        double precioTotal = Double.parseDouble(txtTotalCotizacion.getText())- descuentoTexto;

        double precioUnitario = precioTotal/Double.parseDouble(txtCantidad.getText());

        txtPrecioUCotizacion.setText(String.valueOf(precioUnitario));

        txtTotalCotizacion.setText(String.valueOf(precioTotal));

        txtPrecioUCotizacion.setText(String.valueOf(precioUnitario));
    }

  
    
    @FXML
    private void BuscarMoldura(ActionEvent event) {
        String sql = "{call Sp_SearchCotizaciones('"+txtMolduraReferencia.getValue()+"')}";
        
        
        
        try {
            PreparedStatement ps  = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                txtAncho.setText(rs.getString("cotizacionAlto"));
                txtLargo.setText(rs.getString("cotizacionAncho"));
                txtAlto.setText(rs.getString("cotizacionLargo"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR AL BUSCAR COTIZACIÓN");
            noti.text("Error al cargar la base de datos"+ex);
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
        }
        
    }

    
   
}
