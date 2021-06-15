package org.ModuloCotizacion.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.ModuloCotizacion.bean.Animations;
import org.ModuloCotizacion.bean.AutoCompleteComboBoxListener;
import org.ModuloCotizacion.bean.CambioScene;
import org.ModuloCotizacion.bean.CamposEspeciales;
import org.ModuloCotizacion.bean.Cotizaciones;
import org.ModuloCotizacion.bean.Produccion;
import org.ModuloCotizacion.bean.ValidarStyle;
import org.ModuloCotizacion.bean.cotizacionBackup;
import org.ModuloCotizacion.db.Conexion;
import org.controlsfx.control.Notifications;


public class ProduccionesViewController implements Initializable {

    @FXML
    private JFXButton btnEditar;
    @FXML
    private AnchorPane anchor;
    @FXML
    private ComboBox<String> cmbEstadoProduccion;
    @FXML
    private JFXTextField txtTotalCotizacion;
    



    //Variables
    public enum Operacion{AGREGAR,GUARDAR,ELIMINAR,BUSCAR,ACTUALIZAR,CANCELAR,NINGUNO, SUMAR, RESTAR};
    public Operacion cancelar = Operacion.NINGUNO;
    public Operacion tipoOperacion = Operacion.NINGUNO;
    

    ObservableList<String> listaTipoCliente;
    ObservableList<String> listaEstadoProduccion;
    ObservableList<String> listaOperador;
    ObservableList<String> listaCodigoCotizaciones;
    int codigoCotizacion=0;
    int codigoProduccion=0;
    ObservableList<Cotizaciones> listaCotizaciones;
    ObservableList<cotizacionBackup> listaDetalle;
    ObservableList<CamposEspeciales> listaCamposEspeciales;
    ObservableList<Produccion> listaProduccion;

    
    CambioScene cambioScene = new CambioScene();
    Image imgError = new Image("org/ModuloCotizacion/img/error.png");
    Image imgCorrecto= new Image("org/ModuloCotizacion/img/correcto.png");
    Image imgWarning = new Image("org/ModuloCotizacion/img/warning.png");
    double totalProduccion=0;
    MenuPrincipalContoller menu = new MenuPrincipalContoller();
    ValidarStyle validar = new ValidarStyle();
    
    Animations animacion = new Animations();
    
    int codigo=0;
    
    @FXML
    private AnchorPane anchor1;
    @FXML
    private AnchorPane anchor2;
    @FXML
    private AnchorPane anchor11;
    
    //TBL PRODUCCION
    @FXML
    private TableView<Produccion> tblProduccion;
    @FXML
    private TableColumn<Produccion, Integer> colCodigoPr;
    @FXML
    private TableColumn<Produccion, Integer> colCodigoC;
    @FXML
    private TableColumn<Produccion, Date> colSalida;
    @FXML
    private TableColumn<Produccion, Date> colEntrada;
    @FXML
    private TableColumn<Produccion, Integer> colDiasRestantes;
    @FXML
    private TableColumn<Produccion, String> colEstado;
    @FXML
    private TableColumn<Produccion, String> colOperador;
    @FXML
    private TableColumn<Produccion, Double> colTotal;
    //TABLE DETALLE
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
    
    //TABLE CAMPOS ESPECIALES
    @FXML
    private TableView<CamposEspeciales> tblCamposEspeciales;
    @FXML
    private TableColumn<CamposEspeciales, String> colCampoName;
    @FXML
    private TableColumn<CamposEspeciales, Double> colCampoPrecio;
    
    
    //TEXT FIELD
    @FXML
    private JFXTextField txtCodigoPr;
    @FXML
    private ComboBox<String> cmbCotizacion;
    @FXML
    private JFXTextField txtNit;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtDireccion;
    @FXML
    private ComboBox<String> cmbOperador;
    @FXML
    private JFXTextField txtDiasRestantes;
    @FXML
    private JFXDatePicker txtfinal;
    @FXML
    private JFXDatePicker txtInicio;    
    
    private JFXButton btnAgregar;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXButton bntEditar;
    
    
    public void listOperador(){
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
        listaOperador = FXCollections.observableList(lista);
        cmbOperador.setItems(listaOperador);
        new AutoCompleteComboBoxListener(cmbOperador);
    }
    
    public void listEstado(){
        ArrayList<String> lista = new ArrayList();
        String sql= "{call Sp_ListEstadoProduccion()}";
            int x =0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x, rs.getString("estadoProduccionDesc")+ " |"+rs.getString("estadoProduccionId"));
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
        listaEstadoProduccion = FXCollections.observableList(lista);
        cmbEstadoProduccion.setItems(listaEstadoProduccion);
        new AutoCompleteComboBoxListener(cmbEstadoProduccion);
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatosCotizaciones();
        listOperador();
        listEstado();
        
    }    

    @FXML
    private void regresar(MouseEvent event) throws IOException {
         String menu = "org/ModuloCotizacion/view/menuPrincipal.fxml";
        cambioScene.Cambio(menu,(Stage) anchor.getScene().getWindow());
    }

    @FXML
    private void facturacion(Event event) {
    }

    
    public void activarText(){
        txtCodigoPr.setEditable(true);
        cmbCotizacion.setDisable(false);
        txtNit.setEditable(true);
        txtNombre.setEditable(true);
        txtNombre.setEditable(true);
        cmbEstadoProduccion.setDisable(false); 
        cmbOperador.setDisable(false);
        txtInicio.setDisable(false);        
        txtfinal.setDisable(false);   
        txtDiasRestantes.setEditable(true);
    }
    
    public void DesactivarText(){
        txtCodigoPr.setEditable(false);
        cmbCotizacion.setDisable(true);
        txtNit.setEditable(false);
        txtNombre.setEditable(false);
        txtNombre.setEditable(false);
        cmbEstadoProduccion.setDisable(true); 
        cmbOperador.setDisable(true);
        txtInicio.setDisable(true);        
        txtfinal.setDisable(true);   
        txtDiasRestantes.setEditable(false);
    }
    
    public void limpiarText(){
        txtCodigoPr.setText("");
        cmbCotizacion.setValue("");
        txtNit.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
        cmbEstadoProduccion.setValue("");
        cmbOperador.setValue("");
        txtDiasRestantes.setText("");
    }
    
    public void activarBtn(){
      btnEliminar.setDisable(false);    
      bntEditar.setDisable(false);
    }
    
    public void desactivarBtn(){    
     btnEliminar.setDisable(true);    
     bntEditar.setDisable(true);
    }
    
        public ObservableList<Produccion> getProduccion(){
        ArrayList<Produccion> listaP = new ArrayList();
        ArrayList<String> listaCodigo = new ArrayList();
        String sql= "{call Sp_ListProduccion()}";
        int x =0;
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Date fechaEntrada = rs.getDate("produccionFechaEntrada");
                Date fechaSalida = rs.getDate("produccionFechaSalida");
                int resta = fechaSalida.getDate()-fechaEntrada.getDate();
                listaP.add(new Produccion(
                    rs.getInt("produccionId"),
                    rs.getInt("produccionCotizacion"),
                    rs.getDate("produccionFechaEntrada"),
                    rs.getDate("produccionFechaSalida"),
                    resta,
                    rs.getString("usuarioNombre"),
                    rs.getString("estadoProduccionDesc"),
                    rs.getDouble("cotizacionTotal")
                ));                
                listaCodigo.add(x, rs.getString("produccionId"));
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
        return listaProduccion = FXCollections.observableList(listaP);
    }
        
    public void cargarDatosCotizaciones(){
       tblProduccion.setItems(getProduccion());
       
       colCodigoPr.setCellValueFactory(new PropertyValueFactory("produccionId"));
       colCodigoC.setCellValueFactory(new PropertyValueFactory("produccionCotizacion"));
       colSalida.setCellValueFactory(new PropertyValueFactory("produccionFechaSalida"));
       colEntrada.setCellValueFactory(new PropertyValueFactory("produccionFechaEntrada"));
       colDiasRestantes.setCellValueFactory(new PropertyValueFactory("produccionDiasRestantes"));
       colEstado.setCellValueFactory(new PropertyValueFactory("produccionEstado"));
       colOperador.setCellValueFactory(new PropertyValueFactory("usuarioNombre"));
       colTotal.setCellValueFactory(new PropertyValueFactory("cotizacionTotal"));
       limpiarText();
       desactivarBtn();
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
        
        return listaDetalle = FXCollections.observableList(lista);
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
        
        return listaCamposEspeciales = FXCollections.observableList(lista);       
    }
    
    public void cargarDatosCamposEspeciales(){
        tblCamposEspeciales.setItems(getCamposEspeciales());
        colCampoName.setCellValueFactory(new PropertyValueFactory("campoNombre"));
        colCampoPrecio.setCellValueFactory(new PropertyValueFactory("campoPrecio"));
    }
    
    public void accion(){
        switch(tipoOperacion){
            
            case CANCELAR:
                tipoOperacion = Operacion.NINGUNO;
                desactivarBtn();
                DesactivarText();
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
                        for(int i=0; i<tblProduccion.getItems().size(); i++){
                            if(colCodigoC.getCellData(i) == codigo){
                                tblProduccion.getSelectionModel().select(i);
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
                        bntEditar.setDisable(false);
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
                    noti.text("HA OCURRIDO UN ERROR EN LA BASE DE DATOS"+ex);
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
    
    public void buscarCotizacion(){
        String sql = "{call Sp_SearchCotizaciones('"+codigoCotizacion+"')}";
        
        try {
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                txtNit.setText(rs.getString("cotizacionCliente"));
                txtNombre.setText(rs.getString("clienteNombre"));
                txtDireccion.setText(rs.getString("clienteDireccion"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR AL BUSCAR COTIZACIÓN");
            noti.text("HA OCURRIDO UN ERROR EN LA BASE DE DATOS"+ex);
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
        }
        
        
    }
    
    
    public void buscarCodigo(String name){
        String sql = "{call Sp_SearchEstadoProduccionName('"+name+"')}";
        
        try {
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs =  ps.executeQuery();
            
            while(rs.next()){
                cmbEstadoProduccion.setValue(rs.getString("estadoProduccionDesc")+" |"+rs.getString("estadoProduccionId"));
            }
        } catch (SQLException ex) {
             ex.printStackTrace();
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR AL BUSCAR ESTADO");
            noti.text("HA OCURRIDO UN ERROR EN LA BASE DE DATOS"+ex);
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
        }
        
        
    }
    
    
       public void buscarOperador(String name){
        String sql = "{call Sp_ListUsuarioC('"+name+"')}";
        
        try {
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs =  ps.executeQuery();
            
            while(rs.next()){
                cmbOperador.setValue(rs.getString("usuarioNombre")+" |"+rs.getString("usuarioId"));
            }
        } catch (SQLException ex) {
             ex.printStackTrace();
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR AL BUSCAR ESTADO");
            noti.text("HA OCURRIDO UN ERROR EN LA BASE DE DATOS"+ex);
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
        }
        
        
    }
    
    @FXML
    private void seleccionarProduccion(MouseEvent event) {
        int item = tblProduccion.getSelectionModel().getSelectedIndex();
        codigoCotizacion = colCodigoC.getCellData(item);
        txtCodigoPr.setText(colCodigoPr.getCellData(item).toString());
        cmbCotizacion.setValue(colCodigoC.getCellData(item).toString());
        
        buscarCodigo(colEstado.getCellData(item));
        buscarOperador(colOperador.getCellData(item));
        txtInicio.setValue(LocalDate.parse( colEntrada.getCellData(item).toString()));
        txtfinal.setValue(LocalDate.parse( colSalida.getCellData(item).toString()));
        txtDiasRestantes.setText(colDiasRestantes.getCellData(item).toString());
        cargarDatosCotizacionesDetalle();
        cargarDatosCamposEspeciales();
        buscarCotizacion();
        txtTotalCotizacion.setText(colTotal.getCellData(item).toString());
        codigoProduccion = colCodigoPr.getCellData(item);
        bntEditar.setDisable(false);
        btnEliminar.setDisable(false);
    }
    
    
        
    public int buscarCodigoEstado(){    
        int codigoTC=0;
        if(!cmbEstadoProduccion.getValue().isEmpty()){
            int busqueda = cmbEstadoProduccion.getValue().indexOf("|");

            String valor1 = cmbEstadoProduccion.getValue().substring(busqueda+1, cmbEstadoProduccion.getValue().length());
             codigoTC = Integer.parseInt(valor1);
        }
        return codigoTC;
    }     
    
    public int buscarCodigoOperador(){    
        int codigoTC=0;
        if(!cmbOperador.getValue().isEmpty()){
            int busqueda = cmbOperador.getValue().indexOf("|");

            String valor1 = cmbOperador.getValue().substring(busqueda+1, cmbOperador.getValue().length());
             codigoTC = Integer.parseInt(valor1);
        }
        return codigoTC;
    } 
    
    @FXML
    private void btnEditar(MouseEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType buttonTypeSi = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");
        alert.setTitle("AGREGAR REGISTRO");
        alert.setHeaderText("AGREGAR REGISTRO");
        alert.setContentText("¿Está seguro que desea guardar este registro?");

        alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == buttonTypeSi ){
            
            Produccion nueva = new Produccion();
            nueva.setProduccionFechaEntrada(Date.valueOf(txtInicio.getValue()));
            nueva.setProduccionFechaSalida(Date.valueOf(txtfinal.getValue()));
            nueva.setProduccionDiasRestantes(Integer.parseInt(txtDiasRestantes.getText()));
            String sql = "{call Sp_UpdateProduccion('"+codigoProduccion+"','"+buscarCodigoEstado()+"','"+nueva.getProduccionFechaEntrada()+"','"+nueva.getProduccionFechaSalida()+"','"+nueva.getProduccionDiasRestantes()+"'"
                    + ",'"+buscarCodigoOperador()+"')}";

            try {
                PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                ps.execute();
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgCorrecto));
                noti.title("OPERACIÓN EXITOSA");
                noti.text("SU OPERACIÓN SE HA REALIZADO CON EXITO");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
                limpiarText();
                cargarDatosCotizaciones();
            } catch (SQLException ex) {
                ex.printStackTrace();
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR AL EDITAR PRODUCCIÓN");
                noti.text("HA OCURRIDO UN ERROR EN LA BASE DE DATOS"+ex);
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
            }
            
        }else{
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("OPERACIÓN CANCELADA");
            noti.text("No se ha editado la producción");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
        
        }
        
        
        
        
    }

    @FXML
    private void btnEliminar(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType buttonTypeSi = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");
        alert.setTitle("AGREGAR REGISTRO");
        alert.setHeaderText("AGREGAR REGISTRO");
        alert.setContentText("¿Está seguro que desea guardar este registro?");

        alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == buttonTypeSi ){
            String sql = "{call Sp_DeleteProduccion('"+codigoProduccion+"')}";
            try {
                PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                ps.execute();
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgCorrecto));
                noti.title("OPERACIÓN EXITOSA");
                noti.text("SU OPERACIÓN SE HA REALIZADO CON EXITO");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
                limpiarText();
                cargarDatosCotizaciones();
            } catch (SQLException ex) {
                  ex.printStackTrace();
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR AL ELIMINAR PRODUCCIÓN");
                noti.text("HA OCURRIDO UN ERROR EN LA BASE DE DATOS"+ex);
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
            }
        }else{
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("OPERACIÓN CANCELADA");
            noti.text("NO SE HA EDITADO LA PRODUCCIÓN");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
            
        } 
        
        
        
    
    }
    

    
}
