package org.moduloFacturacion.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.awt.print.PrinterJob;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.moduloFacturacion.bean.Animations;
import org.moduloFacturacion.bean.AutoCompleteComboBoxListener;
import org.moduloFacturacion.bean.CambioScene;

import org.moduloFacturacion.bean.FacturacionDetalleBackup;
import org.moduloFacturacion.bean.FacturasBuscadas;
import org.moduloFacturacion.bean.Imprimir;
import org.moduloFacturacion.bean.ImprimirFacA;
import org.moduloFacturacion.bean.ImprimirOrdenDeCompra;
import org.moduloFacturacion.bean.ImprimirOrdenRespaldo;
import org.moduloFacturacion.bean.ImprimirRespaldo;
import org.moduloFacturacion.bean.ImprimirRespaldoA;
import org.moduloFacturacion.bean.Letras;
import org.moduloFacturacion.bean.ProductoBuscado;

import org.moduloFacturacion.bean.ValidarStyle;

import org.moduloFacturacion.db.Conexion;
import org.moduloFacturacion.report.GenerarReporte;



public class FacturacionViewController implements Initializable {
    CambioScene cambioScene = new CambioScene();    
     LoginViewController login = new LoginViewController();
     Image imgError = new Image("org/moduloFacturacion/img/error.png");
    Image imgCorrecto= new Image("org/moduloFacturacion/img/correcto.png");
    Image imgWarning = new Image("org/moduloFacturacion/img/warning.png");
    @FXML
     JFXTextField txtFacturaId;
    @FXML
     JFXTextField txtTotalFactura;
    @FXML
     JFXButton btnEditar;
    @FXML
     JFXTextField txtEfectivo;
    @FXML
     JFXTextField txtCambio;
    @FXML
     JFXButton btnVender;
    @FXML
     TableColumn<FacturacionDetalleBackup, Integer> colCodigoFactura;
    @FXML
     AnchorPane anchor1;
    @FXML
     AnchorPane anchor2;
    
    Animations animacion = new Animations();
    @FXML
     AnchorPane anchor3;
    @FXML
     AnchorPane anchor4;
    @FXML
     JFXTextField txtDireccionCliente;
    @FXML
     JFXTextField txtLetrasPrecio;
    @FXML
     JFXButton btnReporteVentas;
    @FXML
     ComboBox<String> cmbTipoFactura;
    @FXML
     JFXButton btnMarcarDevolucion;
    @FXML
     JFXButton btnImprimirRespaldo;
    @FXML
    private JFXTextField txtTotalFac;
    @FXML
    private TextField diafacx;
    @FXML
    private TextField diafacy;
    @FXML
    private TextField mesfacx;
    @FXML
    private TextField añofacx;
    @FXML
    private TextField añofacy;
    @FXML
    private TextField nombrefacx;
    @FXML
    private TextField nombrefacy;
     @FXML
    private TextField direccionfacx;
    @FXML
    private TextField direccionfacy;
    @FXML
    private TextField nitfacx;
    @FXML
    private TextField nitfacy;
    @FXML
    private TextField tablafacx;
    @FXML
    private TextField tablafacy;
    @FXML
    private TextField descfacx;
    @FXML
    private TextField valorfacx;
    @FXML
    private TextField mesfacy;
    @FXML
    private TextField totalordenx;
    @FXML
    private TextField totalordeny;
    @FXML
    private TextField descordenx;
    @FXML
    private TextField valorordenx;
    @FXML
    private TextField espaciadoorden;
    @FXML
    private TextField direccionordenx;
    @FXML
    private TextField direccionordeny;
    @FXML
    private TextField tablaordenx;
     @FXML
    private TextField tablaordeny;
      @FXML
    private TextField nombreordeny;
    @FXML
    private TextField nombreordenx;
    @FXML
    private TextField añoordenx;
    @FXML
    private TextField añoordeny;
    @FXML
    private TextField diaordeny;
    @FXML
    private TextField mesordenx;
    @FXML
    private TextField mesordeny;
    @FXML
    private TextField diaordenx;
    @FXML
    private TextField totalfacy;
    @FXML
    private TextField totalfacx;
    @FXML
    private TextField espaciadofac;
    @FXML
    private JFXButton btnCorteDeCaja1;
    @FXML
    private JFXTextField txtResultadodDireccion;
    @FXML
    private TextField size;
    @FXML
    private JFXButton btneliminar;
    @FXML
    private JFXTextField txtSerieIdBuscado;
    @FXML
    private TextField diafacx1;
    @FXML
    private TextField diafacy1;
    @FXML
    private TextField mesfacx1;
    @FXML
    private TextField mesfacy1;
    @FXML
    private TextField añofacx1;
    @FXML
    private TextField añofacy1;
    @FXML
    private TextField nombrefacx1;
    @FXML
    private TextField nombrefacy1;
    @FXML
    private TextField direccionfacx1;
    @FXML
    private TextField direccionfacy1;
    @FXML
    private TextField nitfacx1;
    @FXML
    private TextField nitfacy1;
    @FXML
    private TextField tablafacx1;
    @FXML
    private TextField tablafacy1;
    @FXML
    private TextField descfacx1;
    @FXML
    private TextField valorfacx1;
    @FXML
    private TextField espaciadofac1;
    @FXML
    private TextField totalfacx1;
    @FXML
    private TextField totalfacy1;
    @FXML
    private TextField size1;
    @FXML
    private JFXTextField txtExistencias;
    @FXML
    private JFXTextField txtProveedor;
    @FXML
    private TextField cuatroB;
    @FXML
    private Button cifrasB;
    @FXML
    private TextField cincoB;
    @FXML
    private TextField seisB;
    @FXML
    private TextField ochoB;
    @FXML
    private TextField nueveB;
    @FXML
    private TextField diezB;
    @FXML
    private TextField cuatroBT;
    @FXML
    private TextField cincoBT;
    @FXML
    private TextField seisBT;
    @FXML
    private TextField ochoBT;
    @FXML
    private TextField nueveBT;
    @FXML
    private TextField diezBT;
    @FXML
    private TextField cuatroA;
    @FXML
    private Button cifrasA;
    @FXML
    private TextField cincoA;
    @FXML
    private TextField seisA;
    @FXML
    private TextField ochoA;
    @FXML
    private TextField nueveA;
    @FXML
    private TextField diezA;
    @FXML
    private TextField cuatroAT;
    @FXML
    private TextField cincoAT;
    @FXML
    private TextField seisAT;
    @FXML
    private TextField ochoAT;
    @FXML
    private TextField nueveAT;
    @FXML
    private TextField diezAT;
    @FXML
    private TextField cuatroO;
    @FXML
    private Button cifrasOrden;
    @FXML
    private TextField cincoO;
    @FXML
    private TextField seisO;
    @FXML
    private TextField ochoO;
    @FXML
    private TextField nueveO;
    @FXML
    private TextField diezO;
    @FXML
    private TextField cuatroOT;
    @FXML
    private TextField cincoOT;
    @FXML
    private TextField seisOT;
    @FXML
    private TextField ochoOT;
    @FXML
    private TextField nueveOT;
    @FXML
    private TextField diezOT;
    @FXML
    private TextField longitudProducto;
    @FXML
    private TextField logintudDireccion;
    @FXML
    private TextField logintudNombre;
    @FXML
    private JFXButton btnActualizar;


    
    private void cargarEstado(Event event) {
        animacion.animacion(anchor3, anchor4);
    }

    @FXML
    private void facturacion(Event event) {
         animacion.animacion(anchor1, anchor2);
    }
    
    Letras letras = new Letras();

    PrinterJob impresion = PrinterJob.getPrinterJob();

   

    @FXML
    private void vistaProducto(MouseEvent event) throws IOException {
        menu.prefsRegresarProductos.put("regresarProducto", "facturacion");
          String menu1 = "org/moduloFacturacion/view/ProductosView.fxml";
        cambioScene.Cambio(menu1,(Stage) anchor.getScene().getWindow());
    }

   


    public enum Operacion{AGREGAR,GUARDAR,ELIMINAR,BUSCAR,ACTUALIZAR,CANCELAR,NINGUNO, VENDER,FILTRAR,CARGAR, DEVOLUCION};




    public Operacion cancelar = Operacion.NINGUNO;
    
    String numeroFac = "";
    String serieFac = "";
    String numeroFacB = "";
    
    String nitCliente = "";
    String nombreCliente = "";
    String direccionCliente = "";
    
    String nombreProducto = "";
    String precioProducto = "";
    String existenciasProducto = "";
    String proveedorProducto = "";
    String cantidadProducto = "";
    // ==================== VARIABLES DE FACTURACION
    public Operacion tipoOperacionFacturacion = Operacion.NINGUNO; 

    ObservableList<String> listaComboCliente;
    ObservableList<String> listaComboProductos;
    ObservableList<String> listaComboTipo;
    ObservableList<FacturacionDetalleBackup> listaBackUp;

    boolean comprobarCliente = false;
    Notifications noti = Notifications.create();
    String codigoProducto;
    
    
    
    // ================= VARIABLES PRA BUSCAR FACTURAS
        public Operacion tipoOperacionBusquedaFacturas = Operacion.NINGUNO; 

        ObservableList<String> listaNumeroFactura;
        ObservableList<FacturasBuscadas> listaFacturasBuscadas;
        ObservableList<ProductoBuscado> listaProductoBuscado;

    String codigoFacturas;
    String clienteNit;


    
    
    // ======================= PROPIEDADES VISTA FACTURACION 
    @FXML
    private AnchorPane anchor;
    @FXML
    private JFXTextField txtNombreCliente;
    @FXML
    private ComboBox<String> txtNitCliente;
    @FXML
    private JFXTextField txtSerieId;
    @FXML
    private JFXTextField txtCantidadProducto;
    @FXML
    private JFXTextField txtPrecioProducto;
    @FXML
    private ComboBox<String> cmbNombreProducto;

    @FXML
    private JFXButton btnImprimir;
    
    LocalDate date2;
    
    MenuPrincipalContoller menu = new MenuPrincipalContoller();
    
    ValidarStyle validar = new ValidarStyle();

    @FXML
    private TableColumn<FacturacionDetalleBackup, String> colDesProductoBackUp;
    @FXML
    private TableColumn<FacturacionDetalleBackup, Double> colCantidadProductoBackUp;
    @FXML
    private TableColumn<FacturacionDetalleBackup, Double> colPrecioProductoBackUp;
    @FXML
    private TableColumn<FacturacionDetalleBackup, Double> colTotalParcialBackUp;
    
    @FXML
    private TableColumn<FacturacionDetalleBackup, String> codigoProductoColumn;
    
    @FXML
    private TableView<FacturacionDetalleBackup> tblBackUp;
    

    double totalFactura=0;
    
    // ============================ PROPIEDADES DE BUSQUEDA DE FACTURAS 
   @FXML
    private TableColumn<FacturasBuscadas, String> colSerieFacturaBuscada;
    @FXML
     TableView<FacturasBuscadas> tblResultadoFactura;
    @FXML
     TableColumn<FacturasBuscadas, String> colNumeroFacBuscado;
    @FXML
     TableColumn<FacturasBuscadas, Double> colTotlalNeto;
    @FXML
     TableColumn<FacturasBuscadas, Double> colTotalIva;
    @FXML
     TableColumn<FacturasBuscadas, Double> colTotalBuscado;
    @FXML
     TableColumn<FacturasBuscadas, Date> colFechaBuscada;
    @FXML
     TableColumn<FacturasBuscadas, String> colTipoFactura;
    @FXML
     JFXComboBox<String> txtBusquedaCodigoFac;
    @FXML
     JFXButton btnBuscarFactura;
    @FXML
     JFXButton btnFiltrarFactura;
    @FXML
     JFXButton btnCargarFacturas;
    @FXML
     JFXDatePicker txtFechaInicio;
    @FXML
     JFXDatePicker txtFechaFinal;
    @FXML
     JFXButton btnCorteDeCaja;
    @FXML
     TableView<ProductoBuscado> tblResultadoProducto;
    @FXML
     TableColumn<ProductoBuscado, String> colProductoBuscado;
    @FXML
     TableColumn<ProductoBuscado, Double> colCantidadBuscada;
    @FXML
     TableColumn<ProductoBuscado, Double> colPrecioUnitBuscado;
    @FXML
     JFXTextField txtResultadoNit;
    @FXML
     JFXTextField txtResultadoNombre;
    DecimalFormat twoDForm = new DecimalFormat("#.00");
    
    
    
    
    public void valorTotalFactura(){
        String sql = "{call SpSumarBackup()}";
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                totalFactura =  rs.getDouble("sum(totalParcialBackup)");
            }
            
            txtTotalFactura.setText(String.valueOf(twoDForm.format(totalFactura)));
            txtLetrasPrecio.setText(letras.Convertir(String.valueOf(twoDForm.format(totalFactura)), true));
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         validar.validarView(menu.prefs.get("dark", "root"), anchor);
        llenarComboNit();
        llenarComboProdcutos();
        llenarComboTipoFactura();
        cargarDatos();
        btnEditar.setDisable(true);
        btneliminar.setDisable(true);
        valorTotalFactura();
        animacion.animacion(anchor1, anchor2);
        txtNitCliente.setValue("");
        txtSerieId.setText("");
        txtSerieId.setEditable(true);
        date2 = LocalDate.now();
        cargarBackUpF();
        cargarBackUpC();
        cargarBackUpP();
        SetDatosBackUp();
    }    

    @FXML
    private void regresar(MouseEvent event) throws IOException {
                try{
            llenarBackup();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
         String menu = "org/moduloFacturacion/view/menuPrincipal.fxml";
        cambioScene.Cambio(menu,(Stage) anchor.getScene().getWindow());
    }
    
    JFXDatePicker fechaInicio = new JFXDatePicker();
   

    
    @FXML
    private void nuevaFecha(MouseEvent event) {
         GridPane grid = new GridPane();
        Dialog dialog = new Dialog();
        dialog.setTitle("Cambiar fecha");
        dialog.setHeaderText("Por favor seleccionar la fecha");
        dialog.setResizable(true);
        
        Label label1 = new Label("Fecha de nueva: ");

        grid.add(label1, 1, 1);
        grid.add(fechaInicio, 2, 1);

         
        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);
        
        Optional<ButtonType> result = dialog.showAndWait();

        if(result.get() == buttonTypeOk){             
            date2 = fechaInicio.getValue();
            System.out.println(date2);
        }else if(result.get() == buttonTypeCancel){
            date2 = LocalDate.now();
            System.out.println(date2);
        }else{
         Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgCorrecto));
                noti.title("ERRRO EN LA BASE DE DATOS");
                noti.text("Por favor intenta de nuevo");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();   
                noti.show();
        }
    }
    
    // =================== CODIGO FACTURACION
    public void limpiarTextFacturacion(){
        cmbNombreProducto.setValue("");
        txtPrecioProducto.setText("");
        txtCantidadProducto.setText("");
        txtExistencias.setText("");
        txtProveedor.setText("");
    }
    
    public void limpiarTextCliente(){
        txtFacturaId.setText("");
        txtSerieId.setText("");
        txtNitCliente.setValue("");
        txtNombreCliente.setText("");
        txtDireccionCliente.setText("");
            cmbTipoFactura.setValue("");
    }
    public void limpiarTextEfectivo(){
        txtNombreCliente.setText("");
        txtEfectivo.setText("");
        txtCambio.setText("");
        txtTotalFactura.setText("");
    }
    
        @FXML
    private void buscarCliente(ActionEvent event) {
        buscarClienteMetodo();
    }


    
    @FXML
    private void AtajoCliente(KeyEvent event) {
        if(txtNitCliente.isFocused()){
            if(event.getCode() == KeyCode.ENTER){
                buscarClienteMetodo();
            }
        }
    }
    @FXML
    private void atajoProducto(KeyEvent event) {
        if(cmbNombreProducto.isFocused()){
            if(event.getCode() == KeyCode.ENTER){
                buscarPrecioMetodo();
                System.out.println("hola");
            }
        }
    }

    public void llenarComboNit(){
        ArrayList<String> lista = new ArrayList();
        String sql= "{call SpListarClientes()}";
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

        listaComboCliente = FXCollections.observableList(lista);
        txtNitCliente.setItems(listaComboCliente);
        new AutoCompleteComboBoxListener(txtNitCliente);
    }

    
    
    public void buscarClienteMetodo(){
        if(txtNitCliente.getValue().equals("")){
            
        }else{
            
              String sql = "{call SpBuscarClientesNit('"+txtNitCliente.getValue()+"')}";
            try{
                PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                        txtNombreCliente.setText(rs.getString("clienteNombre"));
                        txtDireccionCliente.setText(rs.getString("clienteDireccion"));
                    }
                if(rs.first()){
                    txtNombreCliente.setEditable(true);
                    txtDireccionCliente.setEditable(true);
                }else{
                    comprobarCliente=true;
                    txtNombreCliente.setText("");
                    txtNombreCliente.setEditable(true);
                    txtDireccionCliente.setEditable(true);
                    noti.graphic(new ImageView(imgWarning));
                    noti.title("USUARIO NO EXISTE");
                    noti.text("DEBERÁ INGRESAR EL CAMPO NOMBRE");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();

                }
            }catch(SQLException ex){
                System.out.println(ex);
            }
        }
       
        
    }
         @FXML
    private void buscarPrecio(ActionEvent event) {
        
        buscarPrecioMetodo();
        llenarBackup();
    }

    public void buscarPrecioMetodo(){
        if(cmbNombreProducto.getValue()!= ""){
               
                try{
                     PreparedStatement sp = Conexion.getIntance().getConexion().prepareCall("{call SpBuscarProductosFac(?)}");
                    sp.setString(1, buscarCodigoProducto(cmbNombreProducto.getValue()));
                    System.out.println(buscarCodigoProducto(cmbNombreProducto.getValue()));
                    System.out.println("codigo aqui");
                     ResultSet resultado = sp.executeQuery(); 
                        while(resultado.next()){
                            txtPrecioProducto.setText(resultado.getString("productoPrecio"));
                            txtExistencias.setText(resultado.getString("inventarioProductoCant"));
                            txtProveedor.setText(resultado.getString("proveedorNombre"));
                        }  
                        if(resultado.first()){
                            txtPrecioProducto.setEditable(false);
                            if(tipoOperacionFacturacion == Operacion.ACTUALIZAR){
                                btnVender.setDisable(true);
                            }else{
                                 btnVender.setDisable(false);
                            }
                           
                        }else{
                            txtPrecioProducto.setText("");
                            btnVender.setDisable(true);
                            noti.graphic(new ImageView(imgError));
                            noti.title("ESTE PRODUCTO NO EXISTE");
                            noti.text("DEBERÁ AGREGARLO EN EL MODULO DE PRODUCTOS");
                            noti.position(Pos.BOTTOM_RIGHT);
                            noti.hideAfter(Duration.seconds(4));
                            noti.darkStyle();
                            noti.show();
                        }
                }catch(Exception e){
                    e.printStackTrace();
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR AL BUSCAR EL PRODUCTO");
                    noti.text("HA OCURRIDO UN ERROR EN LA BASE DE DATOS"+e);
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();

                }
            }
    }
    
    public void comprobarClienteExistente(){
        if(comprobarCliente == false){
            System.out.println("hay cliente");
        }else{
            if(txtNitCliente.getValue().equals("") || txtNombreCliente.getText().isEmpty()){
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR AL AGREGAR");
                noti.text("CAMPOS VACÍOS EN EL AREA DE CLIENTES");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
                
            }else{
                String sql = "";
                 
                if(txtDireccionCliente.getText().isEmpty()){
                         sql = "{call SpAgregarClientesSinDireccion('"+txtNitCliente.getValue()+"','"+txtNombreCliente.getText()+"')}";                        
                    }else{
                        sql = "{call SpAgregarClientes('"+txtNitCliente.getValue()+"','"+txtNombreCliente.getText()+"','"+txtDireccionCliente.getText()+"')}";
                    }
                    
                try{
                    PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    ps.execute();
                    llenarComboNit();
                }catch(SQLException ex){
                    ex.printStackTrace();
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR AL AGREGAR");
                    noti.text("HA OCURRIDO UN ERROR AL GUARDAR EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                }
            }
        }
    }
    
    
    
    
    public void llenarComboTipoFactura(){
        ArrayList<String> lista = new ArrayList();
        String sql= "{call Sp_ListarTipo()}";
            int x =0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x, rs.getString("tipoFacturaDesc"));
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

        listaComboTipo = FXCollections.observableList(lista);
        cmbTipoFactura.setItems(listaComboTipo);
        new AutoCompleteComboBoxListener(cmbTipoFactura);
    }

    public String buscarCodigoProducto(String precioProductos){    
        
        int busqueda = cmbNombreProducto.getValue().indexOf("|");
        
        String valor = cmbNombreProducto.getValue().substring(busqueda+1, cmbNombreProducto.getValue().length());
        
        
        codigoProducto = valor;
        return codigoProducto;
    }
    
    public String buscarNombreProducto(String precioProductos){    
        
        int busqueda = cmbNombreProducto.getValue().indexOf("|");
        
        String valor = cmbNombreProducto.getValue().substring(0, busqueda-1);
        
        
        codigoProducto = valor;
        return codigoProducto;
    }    
    
    public void llenarComboProdcutos(){
        ArrayList<String> lista = new ArrayList();
        String sql= "{call SpListarProductosFac()}";
            int x =0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x, rs.getString("productoDesc")+" |"+rs.getString("productoId"));
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

        listaComboProductos = FXCollections.observableList(lista);
        cmbNombreProducto.setItems(listaComboProductos);
        new AutoCompleteComboBoxListener(cmbNombreProducto);
    }
    
    
    public ObservableList<FacturacionDetalleBackup> getBackUp(){
        String user = login.prefsUsuario.get("usuario", "root");
        ArrayList<FacturacionDetalleBackup> lista = new ArrayList();
        String sql = "{call SpListarBackup('"+user+"')}";
        int x=0;
        double totalParcial=0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new FacturacionDetalleBackup(
                    rs.getInt("facturaDetalleIdBackup"),
                    rs.getString("productoDesc"),
                    rs.getDouble("cantidadBackup"),
                    rs.getDouble("productoPrecio"),
                    rs.getDouble("totalParcialBackup"),
                    rs.getString("productoId")
                ));
                totalParcial = rs.getDouble("totalParcialBackup");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        } 
            totalFactura = totalFactura+totalParcial;
            txtTotalFactura.setText(String.valueOf(totalFactura));
            listaBackUp = FXCollections.observableList(lista);
                return listaBackUp;
    }
     
    public void cargarDatos(){
        tblBackUp.setItems(getBackUp());
        colCodigoFactura.setCellValueFactory(new PropertyValueFactory("facturaDetalleIdBackup"));
        colDesProductoBackUp.setCellValueFactory(new PropertyValueFactory("productoDesc"));
        colCantidadProductoBackUp.setCellValueFactory(new PropertyValueFactory("cantidadBackup"));  
        colPrecioProductoBackUp.setCellValueFactory(new PropertyValueFactory("productoPrecio"));
        colTotalParcialBackUp.setCellValueFactory(new PropertyValueFactory("totalParcialBackup"));
        codigoProductoColumn.setCellValueFactory(new PropertyValueFactory("productoId"));
        cmbNombreProducto.setValue("");
        limpiarTextFacturacion();
        
    }  
    
  public void accionEstado(String sql){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        PreparedStatement ps;
        ResultSet rs;
        Notifications noti = Notifications.create();
        ButtonType buttonTypeSi = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");
        
        
        switch(tipoOperacionFacturacion){
            case AGREGAR:
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
                        tipoOperacionFacturacion = Operacion.CANCELAR;
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
                        tipoOperacionFacturacion = Operacion.CANCELAR;
                    }                
                break;
        }
    }
    
  public int validarEstadoProducto(String estado){
      int codigo = 0;
      String sql = "{call SpBuscarEstadoNombre('"+estado+"')}";
      
      try{
          PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
          ResultSet rs = ps.executeQuery();
          while(rs.next()){
              codigo = rs.getInt("estadoProductoId");
          }
          
      }catch(SQLException ex){
          ex.printStackTrace();
      }
      return codigo;
  }
  
  public boolean validarProducto(){
       int busqueda = cmbNombreProducto.getValue().indexOf("|");
        
        String valor1 = cmbNombreProducto.getValue().substring(busqueda+1, cmbNombreProducto.getValue().length());
        
   
       String sql = "{call SpBuscarInventarioProductos('"+valor1+"')}";
       double cantidad=0;
       boolean valor=false;
       String estado="";
       try{
           PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
           ResultSet rs = ps.executeQuery();
           
           while(rs.next()){
               cantidad = rs.getInt("inventarioProductoCant");
               estado = rs.getString("estadoProductoDesc");
           }
       }catch(SQLException ex){
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR");
                noti.text(ex.toString());
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();   
                noti.show();
       }
       
       String sql1="";
       if(estado.equalsIgnoreCase("AGOTADO")){
           valor=false;
       }else{
           double total = cantidad - Double.parseDouble(txtCantidadProducto.getText());
           
           if(total<0){
               valor = false;
               Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR");
                noti.text("ESTE PRODUCTO NO POSEE EXISTENCIAS, ACTUALICE EL INVENTARIO");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();   
                noti.show();
           }else{
               if(total==0){
                    noti.graphic(new ImageView(imgError));
                    noti.title("ATENCIÓN");
                    noti.text("ESTE PRODUCTO SE HA AGOTADO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                    valor = true;
                    System.out.println(total);
                    sql1="{call SpActualizarInventarioProductosFacturacion('"+valor1+"','"+total+"','"+validarEstadoProducto("AGOTADO")+"')}";
               }else{
                   sql1="{call SpActualizarInventarioProductosFacturacion('"+valor1+"','"+total+"','"+validarEstadoProducto(estado)+"')}";
                   
                   valor = true;
               }
               try{
                   PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql1);
                   ps.execute();
               }catch(SQLException ex){
                   ex.printStackTrace();
               }
              }
           }
       
    return valor;
  }
  
  @FXML
  public void validarNumeroFactura(KeyEvent event){
    ArrayList<String> lista = new ArrayList();
    
    System.out.println(txtFacturaId.getText());
    Integer idFac = Integer.parseInt(txtFacturaId.getText());
    String sql = "{call SpvalidarFactura('"+txtSerieId.getText()+"','"+idFac+"')}";        
      System.out.println(sql);
    int x=0;
    String id = "";
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println("aqui 1");
            
        while(rs.next()){
            lista.add(x, rs.getString("facturaId"));
            id = rs.getString("facturaId");
             x++;
             System.out.println(id);
             System.out.println("adentro");
        }

        if(id != ""){
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("NUMERO DE FACTURA INVALIDO");
            noti.text("Por favor verifica que el numero de factura sea correcto");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
        }
    }catch(SQLException ex){
        ex.printStackTrace();
    } 
  }

  public void ValidacionFecha(){
      Notifications noti = Notifications.create();
        
      if(fechaInicio.getValue() != null){
                noti.graphic(new ImageView(imgCorrecto));
                noti.title("OPERACIÓN EXITOSA");
                noti.text("Fecha seleccionada correctamente");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
       }else{
                noti.graphic(new ImageView(imgError));
                noti.title("DEBE SELECCIONAR FECHA");
                noti.text("Por favor verifica que haya seleccionado la fecha correctamente");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
       }
  
  }
  
  @FXML
    private void btnAgregarFacturaBackUp(MouseEvent event) {
        ValidacionFecha();
            Integer idFac = Integer.parseInt(txtFacturaId.getText());                   
            Integer tipo = 2;
            Integer documento = 0;
        if(listaBackUp.size()>13){
             Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR");
            noti.text("NO PUEDE LLENAR MÁS DE 13 CAMPOS A SU FACTURA");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
        }else{
                if(cmbNombreProducto.getValue().equals("")|| txtPrecioProducto.getText().isEmpty() || txtCantidadProducto.getText().isEmpty() || txtNitCliente.getValue().equals("") || txtNombreCliente.getText().isEmpty() || txtFacturaId.getText().isEmpty() ){
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR");
                noti.text("HAY CAMPOS VACÍOS");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();   
                noti.show();
            
            }else{
                    
                if(validarProducto() == true){
                    FacturacionDetalleBackup nuevoBackUp = new FacturacionDetalleBackup();
                   nuevoBackUp.setProductoDesc(cmbNombreProducto.getValue());
                   nuevoBackUp.setCantidadBackup(Double.parseDouble(txtCantidadProducto.getText()));
                   nuevoBackUp.setTotalParcialBackup(Double.parseDouble(txtPrecioProducto.getText())*Double.parseDouble(txtCantidadProducto.getText()));
                   
                   if(cmbTipoFactura.getValue().equals("FACTURA")){
                       documento = 1;
                   }else if(cmbTipoFactura.getValue().equals("ORDEN DE COMPRA")){
                        documento = 2;
                   }
                   
                    String sqlCardex = "{call SpAgregarCardexFac('"+date2+"','"+buscarNombreProducto(cmbNombreProducto.getValue())+"','"+idFac+"','"+tipo+"','"+txtCantidadProducto.getText()+"','"+documento+"')}";  
                    System.out.println(sqlCardex);
                    
                   String sql = "{call SpAgregarBackup('"+buscarCodigoProducto(nuevoBackUp.getProductoDesc())+"','"+ nuevoBackUp.getCantidadBackup()+"','"+nuevoBackUp.getTotalParcialBackup()+"')}";
                   tipoOperacionFacturacion = Operacion.AGREGAR;
                   accionEstado(sql);  
                   txtLetrasPrecio.setText(letras.Convertir(twoDForm.format(Double.parseDouble(txtTotalFactura.getText())), true));
                   

                   

                   try{
                        PreparedStatement psCardex = Conexion.getIntance().getConexion().prepareCall(sqlCardex);
                        psCardex.execute();
                   }catch(Exception e){
                       e.printStackTrace();
                   }

                   
                }else{
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("ESTE PRODUCTO NO POSEE EXISTENCIAS, ACTUALICE EL INVENTARIO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                }
                   
            }
            
        }
        
    }
    
    public void actualizarCliente(){
        ArrayList<String> lista = new ArrayList();
        String sql = "{call SpBuscarCliente('"+txtNitCliente.getValue()+"')}";
        int x=0;
        String usuario = "";
            try{
                PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x, rs.getString("clienteNit"));
                usuario = rs.getString("clienteNit");
                 x++;
            }
            
            if(usuario != ""){;
                String sql1 = "{call SpUpdateClientes('"+txtNitCliente.getValue()+"','"+txtNombreCliente.getText()+"','"+txtDireccionCliente.getText()+"')}";
                PreparedStatement ps1 = Conexion.getIntance().getConexion().prepareCall(sql1);
                ResultSet rs1 = ps1.executeQuery();
            }  
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
        
        
    public int getClienteId(){
        int codigoCliente=0;
            
        String sql = "{call SpBuscarClientesNit('"+txtNitCliente.getValue()+"')}";
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                codigoCliente = rs.getInt("clienteId");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
            
            
        return codigoCliente;
    }
    
    public int getUsuarioId(){
        int codigoUsuario=0;
        String sql = "{call SpBuscarUsuarioId('"+login.prefsUsuario.get("usuario", "root")+"')}";
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                codigoUsuario = rs.getInt("usuarioId");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return codigoUsuario;
    }
    

    public boolean guardarFactura(){
        double totalNeto = Double.parseDouble(txtTotalFactura.getText())/1.12;
       double totalIva = totalNeto*0.12;
       int tipo = 0;
       boolean validacion= false;
       if(cmbTipoFactura.getValue().equals("FACTURA")){
           tipo = 1;
       }else{
             tipo = 2;
        }
       
       String sql = "{call SpTransferirBackup()}";
       String sqlEliminar = "{call SpEliminarBackup()}";
       int tipoFactura=0;
       if(cmbTipoFactura.getValue().equals("FACTURA")){
           tipoFactura=1;
       }else{
           tipoFactura=2;
       } 
       
       String sqlFactura = "{call SpAgregarFactura('"+txtSerieId.getText()+"','"+txtFacturaId.getText()+"','"+getClienteId()+"','"+date2+"','"+getUsuarioId()+"','"+totalNeto+"','"+totalIva+"','"+txtTotalFactura.getText()+"','"+tipoFactura+"')}";
       String sqlTipo = "{call SpAgregarTipoDocumento('"+txtFacturaId.getText()+"','"+tipo+"')}";
       actualizarCliente();
       try{
           
           PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
           ps.execute();
           
           PreparedStatement psFactura = Conexion.getIntance().getConexion().prepareCall(sqlFactura);
               psFactura.execute();

           PreparedStatement psEliminar = Conexion.getIntance().getConexion().prepareCall(sqlEliminar);

           PreparedStatement psTipo = Conexion.getIntance().getConexion().prepareCall(sqlTipo);
               psTipo.execute();

               
               validacion = true;
           psEliminar.execute();
           
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgCorrecto));
            noti.title("OPERACIÓN EXITOSA");
            noti.text("SE HA IMPRESO Y REGISTRADO CON ÉXITO");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
           cargarDatos();
           txtTotalFactura.setText("");
       }catch(SQLException ex){
           ex.printStackTrace();
           Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR");
            noti.text("HUBO UN ERROR AL REGISTRAR EN LA BASE DE DATOS");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
            validacion= false;
       }
       return validacion;
    }
    
    
    public void imprimir(){
        Imprimir imprimir = new Imprimir();
        ImprimirOrdenDeCompra imprimirOrden = new ImprimirOrdenDeCompra();
        
        ImprimirFacA impA = new ImprimirFacA();
        DecimalFormat df = new DecimalFormat("#.00");
        double total = Double.parseDouble( txtTotalFactura.getText());
        String st =String.valueOf(df.format(total));
        if(cmbTipoFactura.getValue().equals("FACTURA")){
            if(txtSerieId.getText().charAt(0) == 'A' || txtSerieId.getText().charAt(0) == 'a'){
                impA.imprima(listaBackUp, txtNitCliente.getValue(), txtNombreCliente.getText(), txtDireccionCliente.getText(), date2,txtLetrasPrecio.getText(), st);
                impA.imprima(listaBackUp, txtNitCliente.getValue(), txtNombreCliente.getText(), txtDireccionCliente.getText(), date2,txtLetrasPrecio.getText(), st);
            }else{
                
                imprimir.imprima(listaBackUp, txtNitCliente.getValue(), txtNombreCliente.getText(), txtDireccionCliente.getText(), date2,txtLetrasPrecio.getText(), st);
                imprimir.imprima(listaBackUp, txtNitCliente.getValue(), txtNombreCliente.getText(), txtDireccionCliente.getText(), date2,txtLetrasPrecio.getText(), st);
            }
            
        }else{
            imprimirOrden.imprima(listaBackUp, txtNitCliente.getValue(), txtNombreCliente.getText(), txtDireccionCliente.getText(), date2,txtLetrasPrecio.getText(),st);
        }
        
    }
    
    
        @FXML
    private void btnImprimir(MouseEvent event) {
       if(txtNitCliente.getValue().equals("") || txtNombreCliente.getText().isEmpty() || txtFacturaId.getText().isEmpty()){
             Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR");
            noti.text("HAY CAMPOS VACÍOS");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
       } else{
           if(txtEfectivo.getText().isEmpty()){
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR");
                noti.text("EL CAMPO DE EFECTIVO ESTA VACÍO");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
           }else{
                comprobarClienteExistente();
                txtLetrasPrecio.setText("");
                 imprimir();
                String sql = "{call SpEliminarBackup()}"; 
                String sqlBackUp = "{call SpElimarBackFCP()}"; 
                if(guardarFactura()==true){
                    limpiarTextCliente();
                   limpiarTextEfectivo();
                   totalFactura = 0;
                }
                
                 try{
                    PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);            
                    ps.execute();
                    
                    PreparedStatement psBackUpFCP = Conexion.getIntance().getConexion().prepareCall(sqlBackUp);            
                    psBackUpFCP.execute();
                 
                 }catch(Exception e){
                     e.printStackTrace();
                 }
                
           }
            
       }
       
    }
    

      @FXML
    private void btnEditar(MouseEvent event) {
        if(cmbNombreProducto.getValue().equals("") || txtPrecioProducto.getText().isEmpty() || txtCantidadProducto.getText().isEmpty()){
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR");
            noti.text("HAY UN CAMPO VACÍO");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
        }else{
            int index = tblBackUp.getSelectionModel().getSelectedIndex();
            FacturacionDetalleBackup nuevaFactura = new FacturacionDetalleBackup();
            nuevaFactura.setFacturaDetalleIdBackup(colCodigoFactura.getCellData(index));
            nuevaFactura.setProductoDesc(cmbNombreProducto.getValue());
            
            nuevaFactura.setCantidadBackup(Double.parseDouble(txtCantidadProducto.getText()));
            nuevaFactura.setTotalParcialBackup(Double.parseDouble(txtPrecioProducto.getText())*Double.parseDouble(txtCantidadProducto.getText()));
            
            
           String sql = "{call spEditarBackup('"+nuevaFactura.getFacturaDetalleIdBackup()+"','"+buscarCodigoProducto(nuevaFactura.getProductoDesc())+"','"+nuevaFactura.getCantidadBackup()+"','"+nuevaFactura.getTotalParcialBackup()+"')}";
           try{
               PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
               ps.execute();
               
                noti.graphic(new ImageView(imgCorrecto));
                noti.title("OPERACIÓN EXITOSA");
                noti.text("SE HA EDITADO EXITOSAMENTE EL REGISTRO");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
                tipoOperacionFacturacion = Operacion.NINGUNO;
                cargarDatos();
                btnEditar.setDisable(true);
                btneliminar.setDisable(true);
                valorTotalFactura();
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
    public void sumarInventario(){
         String codigoProducto1 = buscarCodigoProducto(cmbNombreProducto.getValue());
       String sql = "{call SpBuscarInventarioProductos('"+codigoProducto1+"')}";
       double cantidad=0;
       boolean valor=false;
       String estado="";
       try{
           PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
           ResultSet rs = ps.executeQuery();
           
           while(rs.next()){
               cantidad = rs.getDouble("inventarioProductoCant");
               estado = rs.getString("estadoProductoDesc");
           }
       }catch(SQLException ex){
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR");
                noti.text(ex.toString());
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();   
                noti.show();
       }
       
       String sql1="";
       double total = cantidad + Double.parseDouble(txtCantidadProducto.getText());
       if(estado.equalsIgnoreCase("AGOTADO")){
           
       }else{
           
            if(total==0){
                 noti.graphic(new ImageView(imgError));
                 noti.title("ATENCIÓN");
                 noti.text("ESTE PRODUCTO SE HA CAMBIADO DE ESTADO");
                 noti.position(Pos.BOTTOM_RIGHT);
                 noti.hideAfter(Duration.seconds(4));
                 noti.darkStyle();   
                 noti.show();
                 sql1="{call SpActualizarInventarioProductosFacturacion('"+codigoProducto1+"','"+total+"','"+1+"')}";
            }else{
                sql1="{call SpActualizarInventarioProductosFacturacion('"+codigoProducto1+"','"+total+"','"+validarEstadoProducto(estado)+"')}";

            }
            try{
                PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql1);
                ps.execute();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
    
    
@FXML
    private void btnEliminar(MouseEvent event) {

            int index = tblBackUp.getSelectionModel().getSelectedIndex();
            FacturacionDetalleBackup nuevaFactura = new FacturacionDetalleBackup();
            nuevaFactura.setFacturaDetalleIdBackup(colCodigoFactura.getCellData(index));
            nuevaFactura.setProductoDesc(cmbNombreProducto.getValue());            
            
           String sql = "{call SpBtnEliminar('"+nuevaFactura.getFacturaDetalleIdBackup()+"','"+buscarCodigoProducto(nuevaFactura.getProductoDesc())+"')}";
           try{
               PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
               ps.execute();
               sumarInventario();
                noti.graphic(new ImageView(imgCorrecto));
                noti.title("OPERACIÓN EXITOSA");
                noti.text("SE HA EDITADO EXITOSAMENTE EL REGISTRO");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
                tipoOperacionFacturacion = Operacion.NINGUNO;
                cargarDatos();
                btnEditar.setDisable(true);
                btneliminar.setDisable(true);
                valorTotalFactura();
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
        
    @FXML
    private void seleccionarElementos(MouseEvent event) {
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
                int index  = tblBackUp.getSelectionModel().getSelectedIndex();
                cmbNombreProducto.setValue(colDesProductoBackUp.getCellData(index)+" |"+codigoProductoColumn.getCellData(index));
                txtCantidadProducto.setText(colCantidadProductoBackUp.getCellData(index).toString());
                
                btnEditar.setDisable(false);
                btneliminar.setDisable(false);
                btnVender.setDisable(true);
                btnImprimir.setDisable(true);
                tipoOperacionFacturacion = Operacion.ACTUALIZAR;
            }catch(Exception e){
            
            }
        }else{
            tblBackUp.getSelectionModel().clearSelection();
        }
    }
    
    
    
    
    @FXML
    private void validarLetras(KeyEvent event) {
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
        }else{
        
        }
        
    }
    
    @FXML
    private void validarEfectivo(KeyEvent event) {
        
          char letra = event.getCharacter().charAt(0);
          
        if(!Character.isDigit(letra)){
            if(letra != '.'){
                event.consume();
            }
            
            
        }else{
        
        }   
    }
  

    @FXML
    private void cambio(KeyEvent event) {
        double  totalFactura = Double.parseDouble(txtTotalFactura.getText());
        double efectivo = Double.parseDouble(txtEfectivo.getText());
        
        double total = efectivo -totalFactura;
        if(total<0){
            btnImprimir.setDisable(true);
        }else{
            btnImprimir.setDisable(false);
        }
        txtCambio.setText(String.valueOf(total));
    }
    
    
    //BackUp de facturación, productos y clientes
    
 
  public void llenarBackup(){
        String sql="{call AgregarBackupFacturacionF('"+txtFacturaId.getText()+"','"+txtSerieId.getText()+"','"+cmbTipoFactura.getValue()+"')}";
        String sqlCliente="{call SpAgregarBackupFacturacionC('"+txtNitCliente.getValue()+"','"+txtNombreCliente.getText()+"','"+txtDireccionCliente.getText()+"')}";
        String sqlProducto="{call SpAgregarBackupFacturacionP('"+cmbNombreProducto.getValue()+"','"+txtPrecioProducto.getText()+"','"+txtExistencias.getText()+"','"+txtProveedor.getText()+"','"+txtCantidadProducto.getText()+"')}";
      
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ps.execute();
            
            PreparedStatement psClientes = Conexion.getIntance().getConexion().prepareCall(sqlCliente);
            psClientes.execute();

            PreparedStatement psProductos = Conexion.getIntance().getConexion().prepareCall(sqlProducto);
            psProductos.execute();
            
            System.out.println(sqlCliente);
        }catch(Exception e){
            e.printStackTrace();
        }
  }

  public void llenarBackupMetodo(KeyEvent event){
      llenarBackup();
  }
  
    private void cargarBackUpF() {    
        ArrayList<String> lista = new ArrayList();
        String sql = "{call ListarBackupFacturacionF()}";                        
        int x=0;
            try{
                PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x, rs.getString("numeroFac")
                );
                lista.add(x, rs.getString("serieFac")
                );
                lista.add(x, rs.getString("tipoFac")
                );                
                numeroFac = rs.getString("numeroFac");
                serieFac = rs.getString("serieFac");
                numeroFacB = rs.getString("tipoFac");
                 x++;
            }
            
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    private void cargarBackUpC() {    
        ArrayList<String> lista = new ArrayList();
        String sql = "{call SpListarBackupFacturacionC()}";                        
        int x=0;
            try{
                PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x, rs.getString("nitCliente")
                );
                lista.add(x, rs.getString("nombreCliente")
                );
                lista.add(x, rs.getString("direccionCliente")
                );
                
                nitCliente = rs.getString("nitCliente");
                nombreCliente = rs.getString("nombreCliente");
                direccionCliente = rs.getString("direccionCliente");
                 x++;
            }
            
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    private void cargarBackUpP() {    
        ArrayList<String> lista = new ArrayList();
        String sql = "{call SpListarBackupFacturacionP()}";                        
        int x=0;
            try{
                PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x, rs.getString("nombreProducto")
                );
                lista.add(x, rs.getString("precioProducto")
                );
                lista.add(x, rs.getString("existenciasProducto")
                );
                lista.add(x, rs.getString("proveedorProducto")
                );
                lista.add(x, rs.getString("cantidadProducto")
                );
                
                nombreProducto = rs.getString("nombreProducto");
                precioProducto = rs.getString("precioProducto");
                existenciasProducto = rs.getString("existenciasProducto");
                proveedorProducto = rs.getString("proveedorProducto");
                cantidadProducto = rs.getString("cantidadProducto");

                x++;
            }
            
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    public void SetDatosBackUp(){    
        txtFacturaId.setText(numeroFac);
        txtSerieId.setText(serieFac);
        cmbTipoFactura.setValue(numeroFacB);
        
        //Clientes
        txtNitCliente.setValue(nitCliente);
        txtNombreCliente.setText(nombreCliente);
        txtDireccionCliente.setText(direccionCliente);
        
        //Productos
        cmbNombreProducto.setValue(nombreProducto);
        txtPrecioProducto.setText(precioProducto);
        txtExistencias.setText(existenciasProducto);
        txtProveedor.setText(proveedorProducto);
        txtCantidadProducto.setText(cantidadProducto);
    }
    
    
    @FXML
    private void actualizarDatos(MouseEvent event) throws IOException {
       
        txtFacturaId.setText("");
        txtSerieId.setText("");
        cmbTipoFactura.setValue("");
        txtNitCliente.setValue("");
        txtNombreCliente.setText("");
        txtDireccionCliente.setText("");
        cmbNombreProducto.setValue("");
        txtPrecioProducto.setText("");
        txtExistencias.setText("");
        txtProveedor.setText("");
        txtCantidadProducto.setText("");
        txtTotalFactura.setText("");
        txtEfectivo.setText("");
        txtCambio.setText("");
        txtLetrasPrecio.setText("");
    }
    
// ================================ CODIGO BUSQUEDA FACTURAS
    
    public ObservableList<FacturasBuscadas> getFacturasBuscadas(){
        ArrayList<FacturasBuscadas> lista = new ArrayList();
        ArrayList<String> comboNumeroFacturas = new ArrayList();
        String sql = "{call SpListarBusquedasFacturas()}";
        int x=0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new FacturasBuscadas(
                            rs.getString("facturaSerie"),
                            rs.getString("facturaId"),
                            rs.getDouble("facturaTotalNeto"),
                            rs.getDouble("facturaTotalIva"),
                            rs.getDouble("facturaTotal"),
                            rs.getDate("facturaFecha"),
                            rs.getString("tipoFacturaDesc")
                ));
                comboNumeroFacturas.add(x, rs.getString("facturaId"));
                x++;
                

            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        listaNumeroFactura = FXCollections.observableList(comboNumeroFacturas);
        txtBusquedaCodigoFac.setItems(listaNumeroFactura);
        return listaFacturasBuscadas = FXCollections.observableList(lista);
    }
    
    @FXML
    public void cargarFacturasBuscadas(){
        animacion.animacion(anchor3, anchor4);
        tblResultadoFactura.setItems(getFacturasBuscadas());
        colSerieFacturaBuscada.setCellValueFactory(new PropertyValueFactory("facturaSerie") );      
        colNumeroFacBuscado.setCellValueFactory(new PropertyValueFactory("facturaId") );
        colTotlalNeto.setCellValueFactory(new PropertyValueFactory("facturaTotalNeto"));  
        colTotalIva.setCellValueFactory(new PropertyValueFactory("facturaTotalIva"));
        colTotalBuscado.setCellValueFactory(new PropertyValueFactory("facturaTotal"));
        colFechaBuscada.setCellValueFactory(new PropertyValueFactory("facturaFecha"));
        colTipoFactura.setCellValueFactory(new PropertyValueFactory("tipoFacturaDesc"));
        new AutoCompleteComboBoxListener(txtBusquedaCodigoFac);
        txtBusquedaCodigoFac.setValue("");
        txtFechaInicio.setValue(null);
        txtFechaFinal.setValue(null);
        tblResultadoProducto.setItems(null);
        txtResultadoNit.setText("");
        txtResultadoNombre.setText("");
        txtResultadodDireccion.setText("");
        btnCorteDeCaja.setDisable(true);
        btnReporteVentas.setDisable(true);
        btnImprimirRespaldo.setDisable(true);
    }  
    
    public void cargarFacturasBuscadasELiminar(){
        tblResultadoFactura.setItems(getFacturasBuscadas());
        colSerieFacturaBuscada.setCellValueFactory(new PropertyValueFactory("facturaSerie") );      
        colNumeroFacBuscado.setCellValueFactory(new PropertyValueFactory("facturaId"));
        colTotlalNeto.setCellValueFactory(new PropertyValueFactory("facturaTotalNeto"));  
        colTotalIva.setCellValueFactory(new PropertyValueFactory("facturaTotalIva"));
        colTotalBuscado.setCellValueFactory(new PropertyValueFactory("facturaTotal"));
        colFechaBuscada.setCellValueFactory(new PropertyValueFactory("facturaFecha"));
        colTipoFactura.setCellValueFactory(new PropertyValueFactory("tipoFacturaDesc"));
        new AutoCompleteComboBoxListener(txtBusquedaCodigoFac);
    }  
        
    public ObservableList<FacturasBuscadas> getFacturasBuscadasPorId(){
        ArrayList<FacturasBuscadas> lista = new ArrayList();
        ArrayList<String> comboNumeroFacturas = new ArrayList();
        String sql = "{call SpListarBusquedasFacturasPorId('"+txtSerieIdBuscado.getText()+"','"+txtBusquedaCodigoFac.getValue()+"')}";
        int x=0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new FacturasBuscadas(
                            rs.getString("facturaSerie"),                        
                            rs.getString("facturaId"),
                            rs.getDouble("facturaTotalNeto"),
                            rs.getDouble("facturaTotalIva"),
                            rs.getDouble("facturaTotal"),
                            rs.getDate("facturaFecha"),
                            rs.getString("tipoFacturaDesc")
                ));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return listaFacturasBuscadas = FXCollections.observableList(lista);
    }
    
    public void cargarFacturasBuscadasPorId(){
        tblResultadoFactura.setItems(getFacturasBuscadasPorId());
        colSerieFacturaBuscada.setCellValueFactory(new PropertyValueFactory("facturaSerie") );      
        colNumeroFacBuscado.setCellValueFactory(new PropertyValueFactory("facturaId"));
        colTotlalNeto.setCellValueFactory(new PropertyValueFactory("facturaTotalNeto"));  
        colTotalIva.setCellValueFactory(new PropertyValueFactory("facturaTotalIva"));
        colTotalBuscado.setCellValueFactory(new PropertyValueFactory("facturaTotal"));
        colFechaBuscada.setCellValueFactory(new PropertyValueFactory("facturaFecha"));
        colTipoFactura.setCellValueFactory(new PropertyValueFactory("tipoFacturaDesc"));
        buscarProducto();
        txtBusquedaCodigoFac.setValue("");
    }  
    
    
        public ObservableList<FacturasBuscadas> getFacturasBuscadasPorFecha(){
        ArrayList<FacturasBuscadas> lista = new ArrayList();
        ArrayList<String> comboNumeroFacturas = new ArrayList();
        String sql = "{call SpBuscarDetalleFacturasFecha('"+txtFechaInicio.getValue()+"','"+txtFechaFinal.getValue()+"')}";
        int x=0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new FacturasBuscadas(
                            rs.getString("facturaSerie"),                        
                            rs.getString("facturaId"),
                            rs.getDouble("facturaTotalNeto"),
                            rs.getDouble("facturaTotalIva"),
                            rs.getDouble("facturaTotal"),
                            rs.getDate("facturaFecha"),
                            rs.getString("tipoFacturaDesc")
                ));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return listaFacturasBuscadas = FXCollections.observableList(lista);
    }
        
    public void cargarFacturasBuscadasPorFecha(){
        tblResultadoFactura.setItems(getFacturasBuscadasPorFecha());
        colSerieFacturaBuscada.setCellValueFactory(new PropertyValueFactory("facturaSerie") );      
        colNumeroFacBuscado.setCellValueFactory(new PropertyValueFactory("facturaId"));
        colTotlalNeto.setCellValueFactory(new PropertyValueFactory("facturaTotalNeto"));  
        colTotalIva.setCellValueFactory(new PropertyValueFactory("facturaTotalIva"));
        colTotalBuscado.setCellValueFactory(new PropertyValueFactory("facturaTotal"));
        colFechaBuscada.setCellValueFactory(new PropertyValueFactory("facturaFecha"));
        colTipoFactura.setCellValueFactory(new PropertyValueFactory("tipoFacturaDesc"));
        
        txtBusquedaCodigoFac.setValue("");
    }   
        
     @FXML
    private void btnImprimirRespaldo(MouseEvent event) {
        int index = tblResultadoFactura.getSelectionModel().getSelectedIndex();
        String tipoFac = colTipoFactura.getCellData(index);
        ImprimirRespaldo imprimir = new ImprimirRespaldo();
        ImprimirRespaldoA imprimirA = new ImprimirRespaldoA();
        ImprimirOrdenRespaldo imprimirOrden = new ImprimirOrdenRespaldo();
        DecimalFormat df = new DecimalFormat("#.00");
        double totalT = Double.parseDouble(txtTotalFac.getText());
        String tot = String.valueOf(df.format(totalT));
        if(tipoFac.equals("FACTURA")){
            if(date2 == null){
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR AL IMPRIMIR");
                noti.text("NO SE HA SELECCIONADO FECHA");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
            }else{
                if(txtSerieIdBuscado.getText().charAt(0) == 'A' || txtSerieIdBuscado.getText().charAt(0) == 'a'){
                    imprimirA.imprima(listaProductoBuscado,txtResultadoNit.getText(), txtResultadoNombre.getText(), txtResultadodDireccion.getText(), date2,tot);
                }else{
                    imprimir.imprima(listaProductoBuscado,txtResultadoNit.getText(), txtResultadoNombre.getText(), txtResultadodDireccion.getText(), date2,tot);
                    
                }
                
                
            }
                
            
           
        } else{
            imprimirOrden.imprima(listaProductoBuscado, txtResultadoNit.getText(), txtResultadoNombre.getText(), txtResultadodDireccion.getText(), date2,txtLetrasPrecio.getText(), tot);
        }
        
    }

    public ObservableList<ProductoBuscado> getProductoBuscado(){
        ArrayList<ProductoBuscado> listaProducto = new ArrayList();
        
        String sql = "{call SpBuscarClienteFacturaFecha('"+txtSerieIdBuscado.getText()+"','"+txtBusquedaCodigoFac.getValue()+"')}";
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                listaProducto.add(new ProductoBuscado(
                            rs.getString("productoDesc"),
                            rs.getDouble("cantidad"),
                            rs.getDouble("productoPrecio")
                ));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return listaProductoBuscado = FXCollections.observableList(listaProducto);
    }
 
    
    public void cargarProductosBuscados(){
        tblResultadoProducto.setItems(getProductoBuscado());
        
        colProductoBuscado.setCellValueFactory(new PropertyValueFactory("productoDesc"));
        colCantidadBuscada.setCellValueFactory(new PropertyValueFactory("cantidad"));  
        colPrecioUnitBuscado.setCellValueFactory(new PropertyValueFactory("productoPrecio"));
    }  
    


    
    public void accion(String sql){
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        PreparedStatement ps;
        ResultSet rs;
        Notifications noti = Notifications.create();
        ButtonType buttonTypeSi = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");
        switch(tipoOperacionBusquedaFacturas){
            case FILTRAR:
                 try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    rs = ps.executeQuery();
                    int numero=0;
                                     
                    if(rs.first()){
                        for(int i=0; i<tblResultadoFactura.getItems().size(); i++){
                            if(colNumeroFacBuscado.getCellData(i) == codigoFacturas){
                                tblResultadoFactura.getSelectionModel().select(i);
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
                        tipoOperacionBusquedaFacturas = Operacion.CANCELAR;
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
                    tipoOperacionBusquedaFacturas = Operacion.CANCELAR;
                }
                break;
            case CARGAR:
                 alert.setTitle("VOLVER A CARGAR DATOS");
                alert.setHeaderText("VOLVER A CARGAR DATOS");
                alert.setContentText("¿Está seguro que desea cargar todos los datos de nuevo?");
               
                alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);
                
                Optional<ButtonType> resultactualizar = alert.showAndWait();
                if(resultactualizar.get() == buttonTypeSi ){
                    try {
                        ps = Conexion.getIntance().getConexion().prepareCall(sql);
                        ps.execute();
                        
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("OPERACIÓN EXITOSA");
                        noti.text("SE HAN CARGADO EXITOSAMENTE SUS REGISTROS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionBusquedaFacturas = Operacion.CANCELAR;
                        cargarFacturasBuscadas();
                        txtFechaInicio.setValue(null);
                        txtFechaFinal.setValue(null);
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL CARGAR SUS REGISTROS");
                        noti.text("HA OCURRIDO UN ERROR AL MOMENTO DE CARGAR TODOS LOS REGISTROS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionBusquedaFacturas = Operacion.CANCELAR;

                    }
                }else{  
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HAN PODIDO CARGAR TODOS LOS REGISTROS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionBusquedaFacturas = Operacion.CANCELAR;
                }
                break;
            case BUSCAR:
                try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    rs = ps.executeQuery();
                    int numero=0;
                                        
                    if(rs.first()){
                        for(int i=0; i<tblResultadoFactura.getItems().size(); i++){
                            if(colNumeroFacBuscado.getCellData(i) == codigoFacturas){
                                tblResultadoFactura.getSelectionModel().select(i);
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
                        tipoOperacionBusquedaFacturas = Operacion.CANCELAR;
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
                    tipoOperacionBusquedaFacturas = Operacion.CANCELAR;
                }
                        txtFechaInicio.setValue(null);
                        txtFechaFinal.setValue(null);
                break; 
            case DEVOLUCION:
                alert.setTitle("CANCELAR DOCUMENTO");
                alert.setHeaderText("CANCELAR DOCUMENTO");
                alert.setContentText("¿Está seguro que desea canelar este documento?");
               
                alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);
                
                Optional<ButtonType> resultEliminar = alert.showAndWait();
                
                if(resultEliminar.get() == buttonTypeSi){
                    try {
                        ps = Conexion.getIntance().getConexion().prepareCall(sql);
                        ps.execute();
                        
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("OPERACIÓN EXITOSA");
                        noti.text("SE HA CANCELADO EXITOSAMENTE EL DOCUMENTO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        cargarDatos();
                        tipoOperacionBusquedaFacturas = Operacion.CANCELAR;                        
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ELIMINAR");
                        noti.text("HA OCURRIDO UN ERROR AL CANCELAR EL DOCUMENTO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionBusquedaFacturas = Operacion.CANCELAR;
                    }
                }else{
                     noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA CANCELADO EL DOCUMENTO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionBusquedaFacturas = Operacion.CANCELAR;
                }
                break;
                
        }
    }
    
    @FXML
     public void buscarFactura(){
      if(txtBusquedaCodigoFac.getValue().equals("")){
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE BUSQUEDA ESTA VACÍO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
        }else{
            tipoOperacionBusquedaFacturas = Operacion.BUSCAR;
            cargarFacturasBuscadasPorId();
            
        }  
    }
     
    @FXML
    public void buscarPorFechas(){
        try{
            if(txtFechaInicio.getValue() == null || txtFechaFinal.getValue()==null){
                          Notifications noti = Notifications.create();
                          noti.graphic(new ImageView(imgError));
                          noti.title("ERROR");
                          noti.text("SELECCIONE FECHAS PARA PODER FILTRAR");
                          noti.position(Pos.BOTTOM_RIGHT);
                          noti.hideAfter(Duration.seconds(4));
                          noti.darkStyle();   
                          noti.show();
              }else{
                  tipoOperacionBusquedaFacturas = Operacion.FILTRAR;
                  cargarFacturasBuscadasPorFecha();

                }  
            }catch(Exception e){
               e.printStackTrace();
            }
    }

        @FXML
    private void btnDevolucion(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType buttonTypeSi = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");
        
        
        if(txtBusquedaCodigoFac.getValue().equals("")){
                        Notifications noti = Notifications.create();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR");
                        noti.text("SELECCIONE UNA REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();   
                        noti.show();       
                
                   }else{
                    alert.setTitle("PRODUCTO DEFECTUOSO");
                    alert.setHeaderText("PRODUCTO DEFECTUOSO");
                    alert.setContentText("¿El producto esta defectuoso?");

                    alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);

                    Optional<ButtonType> resultEliminar = alert.showAndWait();    
                    
                    if(resultEliminar.get() == buttonTypeSi){
                        int codigoFac = Integer.parseInt(txtBusquedaCodigoFac.getValue());
                        String sql = "{call Sp_DevolucionProductosProd('"+txtSerieIdBuscado.getText()+"','"+txtBusquedaCodigoFac.getValue()+"')}";
                        String sql2 = "{call Sp_CancelarFac('"+txtSerieIdBuscado.getText()+"','"+txtBusquedaCodigoFac.getValue()+"')}";
                        tipoOperacionBusquedaFacturas = Operacion.DEVOLUCION;
                        accion(sql); 
                        accion(sql2);
                    }else{
                        int codigoFac = Integer.parseInt(txtBusquedaCodigoFac.getValue());
                        String sql = "{call Sp_DevolucionProductos('"+txtSerieIdBuscado.getText()+"','"+txtBusquedaCodigoFac.getValue()+"')}";
                        String sql2 = "{call Sp_CancelarFac('"+txtSerieIdBuscado.getText()+"','"+txtBusquedaCodigoFac.getValue()+"')}";
                        tipoOperacionBusquedaFacturas = Operacion.DEVOLUCION;
                        accion(sql); 
                        accion(sql2);   
                    }
                   }
    }
    
    
    public void buscarProducto(){
            String sql = "{call SpBuscarClienteFacturaFecha('"+txtSerieIdBuscado.getText()+"','"+txtBusquedaCodigoFac.getValue()+"')}";     
            accion(sql);
            
            PreparedStatement ps;
            ResultSet rs;
            
            try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    rs = ps.executeQuery();
                    int numero=0;
                    
                    while(rs.next()){
                        txtResultadoNit.setText(rs.getString("clienteNit"));
                        txtResultadoNombre.setText(rs.getString("clienteNombre"));
                        txtResultadodDireccion.setText(rs.getString("clienteDireccion"));

                        clienteNit = rs.getString("clienteNit");
                        
                    }
                    cargarProductosBuscados();
                    
                    if(rs.first()){
                        for(int i=0; i<tblResultadoProducto.getItems().size(); i++){
                            if(colNumeroFacBuscado.getCellData(i) == codigoFacturas){
                                tblResultadoProducto.getSelectionModel().select(i);
                                break;
                            }
                        }
                       
                    }else{
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL BUSCAR");
                        noti.text("NO SE HA ENCONTRADO EN LA BASE DE DATOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionBusquedaFacturas = Operacion.CANCELAR;
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
                    tipoOperacionBusquedaFacturas = Operacion.CANCELAR;
                }
        }  

    public Integer codigoFactura = 0;
    
    @FXML
    private void seleccionarElementosFacturasBuscadas(MouseEvent event) {
        seleccionarFacturasBuscadas2();
    }
    
    public void seleccionarFacturasBuscadas2(){
        int index = tblResultadoFactura.getSelectionModel().getSelectedIndex();
        try{
            
            txtSerieIdBuscado.setText(colSerieFacturaBuscada.getCellData(index).toString());
            txtBusquedaCodigoFac.setValue(colNumeroFacBuscado.getCellData(index).toString());
            
            txtTotalFac.setText(colTotalBuscado.getCellData(index).toString());
            buscarProducto();
            btnImprimirRespaldo.setDisable(false);
            
            codigoFactura = Integer.parseInt(colNumeroFacBuscado.getCellData(index).toString());
            txtSerieId.setText(colSerieFacturaBuscada.getCellData(index).toString());
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void btnBuscarFactura(MouseEvent event) {
        buscarFactura();
    }
    
    
    private void btnFiltrarFactura(MouseEvent event) {
        buscarPorFechas();
    }
    
    @FXML
    private void btnCargarFacturas(MouseEvent event) {
        
        cargarFacturasBuscadas();
      
    }
    
    public void imprimirReporteVentas(){
            try{
                Map parametros = new HashMap();

                 String FechaCorte = txtFechaInicio.getValue().toString();
                String repuesta = "'"+FechaCorte+"'";
                
                parametros.put("FechaCorte", "'"+FechaCorte+"'");
                 GenerarReporte.mostrarReporte("CorteDeCaja.jasper", "REPORTE DE VENTAS", parametros);
                
                 txtFechaInicio.setValue(null);
                  btnReporteVentas.setDisable(true);
                 btnCorteDeCaja.setDisable(true);
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
    public void generarReporteVentas(){
       if(txtFechaInicio.getValue()== null){
            Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("DEBE SELECCIONAR FECHA DE INICIO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    
       }else{
           imprimirReporteVentas();
       }
        
           
    }
    
    public void imprimirCierreDeCaja(){
        try{
            Map parametros = new HashMap();

             String FechaCorte = txtFechaInicio.getValue().toString();
            String repuesta = "'"+FechaCorte+"'";

            parametros.put("FechaCorte", "'"+FechaCorte+"'");
             GenerarReporte.mostrarReporte("CierreDeCaja.jasper", "CIERRE DE CAJA", parametros);

             txtFechaInicio.setValue(null);
             btnReporteVentas.setDisable(true);
             btnCorteDeCaja.setDisable(true);
        }catch(Exception e){
            e.printStackTrace();

        }
    }
    
    @FXML
    public void generarReporteCierreCaja(){
         if(txtFechaInicio.getValue()==null){
              Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("DEBE SELECCIONAR FECHA DE INICIO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
         }else{  
            imprimirCierreDeCaja();
         }
              
    }
    
        @FXML
    private void fechaInicio(ActionEvent event) {
        btnReporteVentas.setDisable(false);
        btnCorteDeCaja.setDisable(false);
    }
    
        double xOffset = 0;
    double yOffset = 0;
    
    
    @FXML
    private void eliminarFactura(MouseEvent event) throws IOException {
        
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("org/moduloFacturacion/view/eliminarFactura.fxml"));
        Scene scene = new Scene(root);
         
        
       root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
                
                
            }
        });
        stage.setHeight(510);
        stage.setWidth(599);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("ELIMINAR FACTURA");
        stage.setScene(scene);
        stage.show();
        
        Thread hilo = new Thread(runnable);
       // hilo.start();
    }
    
    
    Runnable runnable = new Runnable() {
    @Override
	public void run() {
            while (true) {
		try {
                    Thread.sleep(1000);
                    cargarFacturasBuscadasELiminar();
		} catch (InterruptedException e) {
                    e.printStackTrace();
					}
				}
			}
        
		};

    
    
    @FXML
    private void guardarPreferencesFacturaA(MouseEvent event) {
        menu.facA.put("diax1", diafacx1.getText()); 
        menu.facA.put("diay1", diafacy1.getText()); 
         
        menu.facA.put("mesx1", mesfacx1.getText()); 
        menu.facA.put("mesy1", mesfacy1.getText()); 
         
        menu.facA.put("añox1", añofacx1.getText()); 
        menu.facA.put("añoy1", añofacy1.getText()); 
         
        menu.facA.put("nombrex1", nombrefacx1.getText()); 
        menu.facA.put("nombrey1", nombrefacy1.getText()); 
         
        menu.facA.put("direccionx1", direccionfacx1.getText()); 
        menu.facA.put("direcciony1", direccionfacy1.getText()); 
         
        menu.facA.put("nitx1", nitfacx1.getText()); 
        menu.facA.put("nity1", nitfacy1.getText()); 
         
        menu.facA.put("tablax1", tablafacx1.getText()); 
        menu.facA.put("tablay1", tablafacy1.getText()); 
         
        menu.facA.put("descfacx1", descfacx1.getText()); 
         
         
        menu.facA.put("valorx1", valorfacx1.getText()); 
         
         
        menu.facA.put("espaciado1", espaciadofac1.getText()); 
         
        menu.facA.put("totalfacx1", totalfacx1.getText()); 
        menu.facA.put("totalfacy1", totalfacy1.getText()); 
        
        menu.facA.put("tamaño1", size1.getText());
    
    }

    
@FXML 
    private void guardarPreferencesFactura(MouseEvent event) { 
        menu.factura.put("diax", diafacx.getText()); 
        menu.factura.put("diay", diafacy.getText()); 
         
        menu.factura.put("mesx", mesfacx.getText()); 
        menu.factura.put("mesy", mesfacy.getText()); 
         
        menu.factura.put("añox", añofacx.getText()); 
        menu.factura.put("añoy", añofacy.getText()); 
         
        menu.factura.put("nombrex", nombrefacx.getText()); 
        menu.factura.put("nombrey", nombrefacy.getText()); 
         
        menu.factura.put("direccionx", direccionfacx.getText()); 
        menu.factura.put("direcciony", direccionfacy.getText()); 
         
        menu.factura.put("nitx", nitfacx.getText()); 
        menu.factura.put("nity", nitfacy.getText()); 
         
        menu.factura.put("tablax", tablafacx.getText()); 
        menu.factura.put("tablay", tablafacy.getText()); 
         
        menu.factura.put("descfacx", descfacx.getText()); 
         
         
        menu.factura.put("valorx", valorfacx.getText()); 
         
         
        menu.factura.put("espaciado", espaciadofac.getText()); 
         
        menu.factura.put("totalfacx", totalfacx.getText()); 
        menu.factura.put("totalfacy", totalfacy.getText()); 
        
        menu.letra.put("tamaño", size.getText());
         
    } 
     
    @FXML 
    private void guardarPreferencesOrden(MouseEvent event) { 
        menu.factura.put("diaxorden", diaordenx.getText()); 
        menu.factura.put("diayorden", diaordeny.getText()); 
         
        menu.factura.put("mesxorden", mesordenx.getText()); 
        menu.factura.put("mesyorden", mesordeny.getText()); 
         
        menu.factura.put("añoxorden", añoordenx.getText()); 
        menu.factura.put("añoyorden", añoordeny.getText()); 
         
        menu.factura.put("nombrexorden", nombreordenx.getText()); 
        menu.factura.put("nombreyorden", nombreordeny.getText()); 
         
        menu.factura.put("direccionxorden", direccionordenx.getText()); 
        menu.factura.put("direccionyorden", direccionordeny.getText()); 
         
         
        menu.factura.put("tablaxorden", tablaordenx.getText()); 
        menu.factura.put("tablayorden", tablaordeny.getText()); 
         
        menu.factura.put("descfacxorden", descordenx.getText()); 
         
         
        menu.factura.put("valorxorden", valorordenx.getText()); 
         
         
        menu.factura.put("espaciadoorden", espaciadoorden.getText()); 
         
        menu.factura.put("totalfacxorden", totalordenx.getText()); 
        menu.factura.put("totalfacyorden", totalordeny.getText()); 
        
    } 
     
    @FXML 
    private void preferencesCargar(Event event) { 
        // longitudes
        longitudProducto.setText(menu.letra.get("longitudProducto", "root"));
        logintudDireccion.setText(menu.letra.get("longitudDireccion", "root"));
        logintudNombre.setText(menu.letra.get("longitudNombre", "root"));
        
        //cifras B
        cuatroB.setText(menu.cifras.get("4", "root"));
        cincoB.setText(menu.cifras.get("5", "root"));
        seisB.setText(menu.cifras.get("6", "root"));
        ochoB.setText(menu.cifras.get("8", "root"));
        nueveB.setText(menu.cifras.get("9", "root"));
        diezB.setText(menu.cifras.get("10", "root"));
        
        cuatroBT.setText(menu.cifras.get("4T", "root"));
        cincoBT.setText(menu.cifras.get("5T", "root"));
        seisBT.setText(menu.cifras.get("6T", "root"));
        ochoBT.setText(menu.cifras.get("8T", "root"));
        nueveBT.setText(menu.cifras.get("9T", "root"));
        diezBT.setText(menu.cifras.get("10T", "root"));
        
        //cifras A
        cuatroA.setText(menu.cifras.get("4A", "root"));
        cincoA.setText(menu.cifras.get("5A", "root"));
        seisA.setText(menu.cifras.get("6A", "root"));
        ochoA.setText(menu.cifras.get("8A", "root"));
        nueveA.setText(menu.cifras.get("9A", "root"));
        diezA.setText(menu.cifras.get("10A", "root"));
        
        cuatroAT.setText(menu.cifras.get("4AT", "root"));
        cincoAT.setText(menu.cifras.get("5AT", "root"));
        seisAT.setText(menu.cifras.get("6AT", "root"));
        ochoAT.setText(menu.cifras.get("8AT", "root"));
        nueveAT.setText(menu.cifras.get("9AT", "root"));
        diezAT.setText(menu.cifras.get("10AT", "root"));
        
        
        //orden de Compra
//        cuatroO.setText(menu.cifras.get("4O", "root"));
//        cincoO.setText(menu.cifras.get("5O", "root"));
//        seisO.setText(menu.cifras.get("6O", "root"));
//        ochoO.setText(menu.cifras.get("8O", "root"));
//        nueveO.setText(menu.cifras.get("9O", "root"));
//        diezO.setText(menu.cifras.get("10O", "root"));
//        
//        cuatroOT.setText(menu.cifras.get("4OT", "root"));
//        cincoOT.setText(menu.cifras.get("5OT", "root"));
//        seisOT.setText(menu.cifras.get("6OT", "root"));
//        ochoOT.setText(menu.cifras.get("8OT", "root"));
//        nueveOT.setText(menu.cifras.get("9OT", "root"));
//        diezOT.setText(menu.cifras.get("10OT", "root"));
        
        
        //fac b
        diafacx.setText(menu.factura.get("diax", "root")); 
        diafacy.setText(menu.factura.get("diay", "root")); 
         
        mesfacx.setText(menu.factura.get("mesx", "root")); 
        mesfacy.setText(menu.factura.get("mesy", "root")); 
         
         
        añofacx.setText(menu.factura.get("añox", "root")); 
        añofacy.setText(menu.factura.get("añoy", "root")); 
         
        nombrefacx.setText(menu.factura.get("nombrex", "root")); 
        nombrefacy.setText(menu.factura.get("nombrey", "root")); 
         
        direccionfacx.setText(menu.factura.get("direccionx", "root")); 
        direccionfacy.setText(menu.factura.get("direcciony", "root")); 
         
        nitfacx.setText(menu.factura.get("nitx", "root")); 
        nitfacy.setText(menu.factura.get("nity", "root")); 
         
        tablafacx.setText(menu.factura.get("tablax", "root")); 
        tablafacy.setText(menu.factura.get("tablay", "root")); 
         
        descfacx.setText(menu.factura.get("descfacx", "root")); 
         
        valorfacx.setText(menu.factura.get("valorx", "root")); 
         
        espaciadofac.setText(menu.factura.get("espaciado", "root")); 
         
        totalfacx.setText(menu.factura.get("totalfacx", "root")); 
        totalfacy.setText(menu.factura.get("totalfacy", "root")); 
         
                 
        //fac A
        diafacx1.setText(menu.facA.get("diax1", "root")); 
        diafacy1.setText(menu.facA.get("diay1", "root")); 
         
        mesfacx1.setText(menu.facA.get("mesx1", "root")); 
        mesfacy1.setText(menu.facA.get("mesy1", "root")); 
         
         
        añofacx1.setText(menu.facA.get("añox1", "root")); 
        añofacy1.setText(menu.facA.get("añoy1", "root")); 
         
        nombrefacx1.setText(menu.facA.get("nombrex1", "root")); 
        nombrefacy1.setText(menu.facA.get("nombrey1", "root")); 
         
        direccionfacx1.setText(menu.facA.get("direccionx1", "root")); 
        direccionfacy1.setText(menu.facA.get("direcciony1", "root")); 
         
        nitfacx1.setText(menu.facA.get("nitx1", "root")); 
        nitfacy1.setText(menu.facA.get("nity1", "root")); 
         
        tablafacx1.setText(menu.facA.get("tablax1", "root")); 
        tablafacy1.setText(menu.facA.get("tablay1", "root")); 
         
        descfacx1.setText(menu.facA.get("descfacx1", "root")); 
         
        valorfacx1.setText(menu.facA.get("valorx1", "root")); 
         
        espaciadofac1.setText(menu.facA.get("espaciado1", "root")); 
         
        totalfacx1.setText(menu.facA.get("totalfacx1", "root")); 
        totalfacy1.setText(menu.facA.get("totalfacy1", "root")); 
        size1.setText(menu.facA.get("tamaño1", "root"));
        
        //orden
        
        diaordenx.setText(menu.orden.get("diaxorden", "root")); 
        diaordeny.setText(menu.orden.get("diayorden", "root")); 
         
        mesordenx.setText(menu.orden.get("mesxorden", "root")); 
        mesordeny.setText(menu.orden.get("mesyorden", "root")); 
         
         
        añoordenx.setText(menu.orden.get("añoxorden", "root")); 
        añoordeny.setText(menu.orden.get("añoyorden", "root")); 
         
        nombreordenx.setText(menu.orden.get("nombrexorden", "root")); 
        nombreordeny.setText(menu.orden.get("nombreyorden", "root")); 
         
        direccionordenx.setText(menu.orden.get("direccionxorden", "root")); 
        direccionordeny.setText(menu.orden.get("direccionyorden", "root")); 
         
         
        tablaordenx.setText(menu.orden.get("tablaxorden", "root")); 
        tablaordeny.setText(menu.orden.get("tablayorden", "root")); 
        
        
        descordenx.setText(menu.orden.get("descfacxorden", "root")); 
         
        valorordenx.setText(menu.orden.get("valorxorden", "root")); 
         
        espaciadoorden.setText(menu.orden.get("espaciadoorden", "root")); 
         
        totalordenx.setText(menu.orden.get("totalfacxorden", "root")); 
        totalordeny.setText(menu.orden.get("totalfacyorden", "root")); 
        
        size.setText(menu.letra.get("tamaño", "root"));
    } 
    
    @FXML
    private void cifrasB(MouseEvent event) {
        menu.cifras.put("4", cuatroB.getText());
        menu.cifras.put("5", cincoB.getText());
        menu.cifras.put("6", seisB.getText());
        menu.cifras.put("8", ochoB.getText());
        menu.cifras.put("9", nueveB.getText());
        menu.cifras.put("10", diezB.getText());
        
        menu.cifras.put("4T", cuatroBT.getText());
        menu.cifras.put("5T", cincoBT.getText());
        menu.cifras.put("6T", seisBT.getText());
        menu.cifras.put("8T", ochoBT.getText());
        menu.cifras.put("9T", nueveBT.getText());
        menu.cifras.put("10T", diezBT.getText());
        
        
        
        
    }

    @FXML
    private void cifrasA(MouseEvent event) {
        menu.cifras.put("4A", cuatroA.getText());
        menu.cifras.put("5A", cincoA.getText());
        menu.cifras.put("6A", seisA.getText());
        menu.cifras.put("8A", ochoA.getText());
        menu.cifras.put("9A", nueveA.getText());
        menu.cifras.put("10A", diezA.getText());
        
        menu.cifras.put("4AT", cuatroAT.getText());
        menu.cifras.put("5AT", cincoAT.getText());
        menu.cifras.put("6AT", seisAT.getText());
        menu.cifras.put("8AT", ochoAT.getText());
        menu.cifras.put("9AT", nueveAT.getText());
        menu.cifras.put("10AT", diezAT.getText());
        
        
        
    }

    @FXML
    private void cifrasOrden(MouseEvent event) {
    }

   
    @FXML
    private void tamañosLetra(MouseEvent event) {
        if(txtSerieId.getText().isEmpty()){
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("CAMPO VACÍO");
            noti.text("DEBE DE INGRESAR EL CAMPO SERIE");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
        }else{
            GridPane grid = new GridPane();
            Dialog dialog = new Dialog();
            dialog.setTitle("Cambiar Tamaño de Letra");
            dialog.setHeaderText("Por favor seleccionar nuevo tamaño");
            dialog.setResizable(true);

            Label label1 = new Label("Tamaño de Nombre:");
            JFXTextField tamañoNombre = new JFXTextField();
            Label label2 = new Label("Tamaño de Direccion:");
            JFXTextField tamañoDireccion = new JFXTextField();
            if(txtSerieId.getText().charAt(0) == 'A' ||txtSerieId.getText().charAt(0) == 'a'){
                tamañoNombre.setText(menu.letra.get("tamañoNombreA", "root"));
                tamañoDireccion.setText(menu.letra.get("tamañoDirecA", "root"));
            }else{
                tamañoNombre.setText(menu.letra.get("tamañoNombreB", "root"));
                tamañoDireccion.setText(menu.letra.get("tamañoDirecA", "root"));
            }

            grid.add(label1, 1, 1);
            grid.add(tamañoNombre, 2, 1);

            grid.add(label2, 1, 3);
            grid.add(tamañoDireccion, 2, 3);


            dialog.getDialogPane().setContent(grid);

            ButtonType buttonTypeOk = new ButtonType("GUARDAR", ButtonBar.ButtonData.OK_DONE);
            ButtonType buttonTypeCancel = new ButtonType("CANCELAR", ButtonBar.ButtonData.CANCEL_CLOSE);

            dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
            dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);

            Optional<ButtonType> result = dialog.showAndWait();

            if(result.get() == buttonTypeOk){             
                menu.letra.put("tamañoNombreA", tamañoNombre.getText());
                menu.letra.put("tamañoNombreB", tamañoNombre.getText());
                menu.letra.put("tamañoDirecA", tamañoDireccion.getText());
                menu.letra.put("tamañoDirecB", tamañoDireccion.getText());
            }else if(result.get() == buttonTypeCancel){
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgWarning));
                noti.title("TAMAÑO");
                noti.text("TAMAÑO SE HA ESTABLECIDO EL POR DEFECTO");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();   
                noti.show();
            }
        }
        
        
    }

    @FXML
    private void logintudes(MouseEvent event) {
        menu.letra.put("longitudProducto", longitudProducto.getText());
        menu.letra.put("longitudDireccion", logintudDireccion.getText());
        menu.letra.put("longitudNombre", logintudNombre.getText());
        
        
    }

}