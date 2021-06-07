package org.ModuloCotizacion.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JFileChooser;
import org.ModuloCotizacion.bean.Animations;
import org.ModuloCotizacion.bean.CambioScene;
import org.ModuloCotizacion.bean.CamposEspeciales;
import org.ModuloCotizacion.bean.Cotizaciones;
import org.ModuloCotizacion.bean.ValidarStyle;
import org.ModuloCotizacion.db.Conexion;
import org.controlsfx.control.Notifications;

public class CotizacionesViewController implements Initializable {

    @FXML
    private AnchorPane anchor;
    @FXML
    private JFXButton btnAgregarImagen;

   

    
    //Variables
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
    ObservableList<String> listaNit;
    ObservableList<String> listaTipoCliente;
    ObservableList<String> listaVendedor;
    ObservableList<String> listaFactorVenta;
    ObservableList<String> listaMolduraRef;
    ObservableList<String> listaCodigoCotizaciones;
    ObservableList<String> listaProducto;
    ObservableList<String> listaCamposEspeciales;
    
    ObservableList<CamposEspeciales>listaCamposEsp2;
    
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
    @FXML
    private TableColumn<Cotizaciones, String> nit;


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
        txtAncho.setText("0.00");
        txtLargo.setText("0.00");        
        txtAlto.setText("0.00");
        txtDescuentoCotizacion.setText("0.00");
        txtPrecioUCotizacion.setText("0.00");
        txtTotalCotizacion.setText("0.00");
        txtDescripcion.setText("");
        txtCantidad.setText("1");
        txtDescuento.setText("0.00");        
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
    }
    
    public void activateButtonsSeleccion(){
        btnAgregarCampoEspecial.setDisable(false);
        btnMandarProduccion.setDisable(false);
        btnGenerarWord.setDisable(false);
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
                    rs.getString("cotizacionImg"),
                    rs.getDate("cotizacionFecha"),
                    rs.getDouble("cotizacionCantida"),
                    rs.getString("cotizacionModeloRef"),
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
                    rs.getString("clienteNombre")
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
       colCantidadCotizacion.setCellValueFactory(new PropertyValueFactory("cotizacionCantida"));
       colAnchoCotizacion.setCellValueFactory(new PropertyValueFactory("cotizacionAncho"));
       colAltoCotizacion.setCellValueFactory(new PropertyValueFactory("cotizacionAlto"));
       colLargoCotizacion.setCellValueFactory(new PropertyValueFactory("cotizacionLargo"));
       colDescCotizacion.setCellValueFactory(new PropertyValueFactory("cotizacionDesc"));
       nit.setCellValueFactory(new PropertyValueFactory("clienteNombre"));
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
                        cargarDatosCotizaciones();
                        
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL AGREGAR");
                        noti.text("HA OCURRIDO UN ERROR AL GUARDAR EL REGISTRO");
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
            if(txtCodigo.getText().isEmpty() || txtNIT.getValue().isEmpty() || txtNombre.getText().isEmpty() || txtDireccion.getText().isEmpty() || txtVendedor.getValue().isEmpty() || 
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
                    nuevoCotizaciones.setCotizacionAlto(Double.parseDouble(txtAlto.getText()));
                    nuevoCotizaciones.setCotizacionAncho(Double.parseDouble(txtAncho.getText()));
                    nuevoCotizaciones.setCotizacionLargo(Double.parseDouble(txtLargo.getText()));
                    nuevoCotizaciones.setCotizacionDesc(txtDescripcion.getText());
                    nuevoCotizaciones.setCotizacionDescuento(Double.parseDouble(txtDescuento.getText()));
                    
                    Double descuentoNeto =  Double.parseDouble(txtDescuento.getText());
                    nuevoCotizaciones.setCotizacionDescuentoNeto(descuentoNeto);
                    
                    Double precioUnit = Double.parseDouble(txtPrecioUCotizacion.getText());
                    nuevoCotizaciones.setCotizacionPrecioU(precioUnit);
                    
                    
                    Double TotalC =  Double.parseDouble(txtTotalCotizacion.getText());
                    
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
                            +"','"+nuevoCotizaciones.getCotizacionAlto()+"','"+nuevoCotizaciones.getCotizacionAncho()
                            +"','"+nuevoCotizaciones.getCotizacionLargo()+"','"+nuevoCotizaciones.getCotizacionDesc()+"','"+nuevoCotizaciones.getCotizacionDescuento()+"','"+nuevoCotizaciones.getCotizacionDescuentoNeto()+"','"+nuevoCotizaciones.getCotizacionPrecioU()+"','"+nuevoCotizaciones.getCotizacionTotal()+"')}";                            
                            System.out.println(sql);
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
                double  ancho = Double.parseDouble(txtAncho.getText()),largo = Double.parseDouble(txtLargo.getText()), alto = Double.parseDouble(txtAlto.getText());
                
                txtDescuento.setText(rs.getString("tipoClienteDescuento"));
                
                double cantidad = Double.parseDouble(txtCantidad.getText());
        
        
                double valor = (ancho * largo * alto * 0.0005 * cantidad);
              
                double descuentoTexto = Double.parseDouble(txtDescuento.getText())*valor;
                
                valor = valor-descuentoTexto;
                double precioUnitario1 = valor/Double.parseDouble(txtCantidad.getText());
        
                txtPrecioUCotizacion.setText(String.valueOf(precioUnitario1));
                txtTotalCotizacion.setText(String.valueOf(valor));
                txtDescuentoCotizacion.setText(String.valueOf(descuentoTexto));
                
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
        desactivarText();
        llenarComboTipoC();
        limpiarText();
        disableButtonsSeleccion();
        cargarDatosCotizaciones();
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
            double descuentoTexto = Double.parseDouble(txtDescuento.getText())*((ancho * largo * alto )* 0.0005);
            
            txtDescuentoCotizacion.setText(String.valueOf(descuentoTexto));
            
            
            double valor = ((ancho * largo * alto )* 0.0005)- Double.parseDouble(txtDescuentoCotizacion.getText());
            double precioUnitario = valor/Double.parseDouble(txtCantidad.getText());
            
            txtPrecioUCotizacion.setText(String.valueOf(precioUnitario));
            txtTotalCotizacion.setText(String.valueOf(valor));
            
        }
    }



    @FXML
    private void txtLargoCambio(KeyEvent event) {
         if(txtAncho.getText().isEmpty() || txtLargo.getText().isEmpty() || txtAlto.getText().isEmpty()){
            
        }else{
            calcularprecio();
            
        }
    }

    @FXML
    private void txtAltoCambio(KeyEvent event) {
         if(txtAncho.getText().isEmpty() || txtLargo.getText().isEmpty() || txtAlto.getText().isEmpty()){
            
        }else{
            calcularprecio();
        }
    }

    @FXML
    private void txtAnchoCambio(KeyEvent event) {
         if(txtAncho.getText().isEmpty() || txtLargo.getText().isEmpty() || txtAlto.getText().isEmpty()){
            
        }else{
            calcularprecio();
        }
    }
    
    
    @FXML
    private void btnImprimir(MouseEvent event) {
    }

    @FXML
    private void txtCantidadCambio(KeyEvent event) {
         
        double  ancho = Double.parseDouble(txtAncho.getText()),largo = Double.parseDouble(txtLargo.getText()), alto = Double.parseDouble(txtAlto.getText());
        double valor = (ancho * largo * alto * 0.0005* Double.parseDouble(txtCantidad.getText()));
        double descuentoTexto = Double.parseDouble(txtDescuento.getText())*valor;
       valor = valor- descuentoTexto;
        txtTotalCotizacion.setText(String.valueOf(valor));
    }

    @FXML
    private void txtDescuentoCambio(KeyEvent event) {
        double cantidad = Double.parseDouble(txtCantidad.getText());
        
        double  ancho = Double.parseDouble(txtAncho.getText()),largo = Double.parseDouble(txtLargo.getText()), alto = Double.parseDouble(txtAlto.getText());
        double valor = (ancho * largo * alto * 0.0005 * cantidad)- Double.parseDouble(txtDescuentoCotizacion.getText());
        double precioUnitario1 = valor/Double.parseDouble(txtCantidad.getText());

        
        double descuentoTexto = Double.parseDouble(txtDescuento.getText())*valor;
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
    }
    
    public void listCotizacion(){
        String sql = "{call Sp_SearchCotizaciones('"+codigoCotizacion+"')}";
        
        PreparedStatement ps;
        try {
            ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                txtDescuentoCotizacion.setText(rs.getString("cotizacionDescuentoNeto"));
                txtPrecioUCotizacion.setText(rs.getString("cotizacionPrecioU"));
                txtTotalCotizacion.setText(rs.getString("cotizacionTotal"));
                txtDescuento.setText(rs.getString("cotizacionDescuento"));
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
    
    @FXML
    private void seleccionarElementosCotizacion(MouseEvent event) {
        activateButtonsSeleccion();
        int index = tblCotizacion.getSelectionModel().getSelectedIndex();
        try{
            
            txtCodigo.setText(colCodigoCotizacion.getCellData(index).toString());            
            txtNIT.setValue(nit.getCellData(index));            
            txtFecha.setValue(colFechaCotizacion.getCellData(index).toLocalDate());    
           
            txtAncho.setText(colAnchoCotizacion.getCellData(index).toString());                        
            txtLargo.setText(colAltoCotizacion.getCellData(index).toString());                        
            txtAlto.setText(colLargoCotizacion.getCellData(index).toString());    
            
            txtDescripcion.setText(colDescCotizacion.getCellData(index));                        
            txtCantidad.setText(colCantidadCotizacion.getCellData(index).toString());      
            
            codigoCotizacion = colCodigoCotizacion.getCellData(index);
            
            activarControles();
            activarText();
            cargarDatosCamposEspeciales();
            listCotizacion();
        }catch(Exception ex){
            
        }
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
        
        grid.add(label2, 1, 3);
        grid.add(precio, 2, 3);
        
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
                    
                    String sql1 = "{call Sp_UpdateCotizacionTotal('"+codigoCotizacion+"','"+precio.getText()+"')}";
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
       JFileChooser fileChooser = new JFileChooser();   
           int option = fileChooser.showOpenDialog(fileChooser);
            if(option == JFileChooser.APPROVE_OPTION){
                if(fileChooser.getSelectedFile()!=null){
                    try {
                        File archivo = fileChooser.getSelectedFile();
                        
                        String Dest = System.getProperty("user.dir")+"../img"+archivo.getName();
                        Path Destino = Paths.get(Dest);
                        
                        String Orig = archivo.getPath();
                        Path Origen = Paths.get(Orig);
                        
                        Files.copy(Origen, Destino, REPLACE_EXISTING);
                        
                        Notifications noti = Notifications.create();
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("IMAGEN CARGADA");
                        noti.text("LA IMAGEN SE HA CARGADO EXITOSAMENTE");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();   
                        noti.show();
                    } catch (IOException ex) {
                       Notifications noti = Notifications.create();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR EN IMAGEN");
                        noti.text("NO SE HA PODIDO CARGAR LA IMAGEN"+ex);
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();   
                        noti.show();
                    }
                    
                    
                    
                }
                    
                }
            }

}
