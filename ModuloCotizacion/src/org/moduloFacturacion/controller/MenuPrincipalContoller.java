package org.moduloFacturacion.controller;

import VO.ArchivosVO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.ByteArrayInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import org.moduloFacturacion.report.GenerarReporte;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.moduloFacturacion.bean.CuadroImagen;
import org.moduloFacturacion.bean.MiVisorPDF;

import org.moduloFacturacion.bean.AutoCompleteComboBoxListener;
import org.moduloFacturacion.bean.CambioScene;
import org.moduloFacturacion.bean.Usuario;
import org.moduloFacturacion.bean.ValidarStyle;
import org.moduloFacturacion.db.Conexion;


public class MenuPrincipalContoller implements Initializable {
    double xOffset = 0;
    double yOffset = 0;
    LoginViewController login = new LoginViewController();
    CambioScene cambioScene = new CambioScene();
    @FXML
    private Button off;
    @FXML
    private Button on;
    @FXML
    private CheckBox checkBox;
    @FXML
    private AnchorPane cajaConsulta;
    @FXML
    private ScrollPane scroll;
    @FXML
    private JFXButton btnAtras;
    @FXML
    private JFXButton btnSiguiente;

    

    public enum Operacion{AGREGAR,GUARDAR,ELIMINAR,BUSCAR,ACTUALIZAR,CANCELAR,NINGUNO};
    public Operacion tipoOperacion= Operacion.NINGUNO;
    public Operacion cancelar = Operacion.NINGUNO;
    Image imgError= new Image("org/moduloFacturacion/img/error.png");
    Image imgCorrecto= new Image("org/moduloFacturacion/img/correcto.png");
    Image warning= new Image("org/moduloFacturacion/img/warning.png");
    private ObservableList<Usuario>listaUsuario;
    private ObservableList<String>listaCombo;
    private ObservableList<String>listaComboCodigo;
    
    @FXML
    private Label labelUsuario;
    @FXML
    private AnchorPane cajaInventario;
    @FXML
    private Tab tabAjustes;
    @FXML
    private Tab tabInformacion;
    @FXML
    private Pane paneBienvenida;
    @FXML
    private AnchorPane cajaClientes;
    @FXML
    private AnchorPane cajaFactura;
    @FXML
    private AnchorPane paneUsuario;
    @FXML
    private AnchorPane paneTabla;
    @FXML
    private MenuItem itemInventario;
    @FXML
    private AnchorPane anchor;
    @FXML
    private JFXTextField txtUsuario;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXButton btnAgregar;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private TableView<Usuario> tableUsuario;
    @FXML
    private TableColumn<Usuario, String> colCodigoUsuario;
    
    @FXML
    private TableColumn<Usuario, String> colPasswordUsuario;
    @FXML
    private TableColumn<Usuario, String> colTipoUsuario;
    @FXML
    private TableColumn<Usuario, String> colNombreUsuario;
    @FXML
    private ComboBox<String> cmbCodigoUsuario;
    @FXML
    private ComboBox<String> cmbTipoUsuario;
    
    public Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
    public Preferences prefsUsuario1 = Preferences.userRoot().node(this.getClass().getName());
    public Preferences prefsRegresar = Preferences.userRoot().node(this.getClass().getName());
    public Preferences prefsRegresarProductos = Preferences.userRoot().node(this.getClass().getName());
    //preferences impresión cheques
    
    
    public Preferences PfechaX = Preferences.userRoot().node(this.getClass().getName());
    public Preferences PfechaY = Preferences.userRoot().node(this.getClass().getName());
    
    public Preferences PordenX = Preferences.userRoot().node(this.getClass().getName());
    public Preferences PordenY = Preferences.userRoot().node(this.getClass().getName());
    
    public Preferences PTotalX = Preferences.userRoot().node(this.getClass().getName());
    public Preferences PTotalY = Preferences.userRoot().node(this.getClass().getName());
    
    public Preferences PLetrasX = Preferences.userRoot().node(this.getClass().getName());
    public Preferences PLetrasY = Preferences.userRoot().node(this.getClass().getName());
    
    public Preferences comprobante = Preferences.userRoot().node(this.getClass().getName());
    public Preferences facA = Preferences.userRoot().node(this.getClass().getName());
    public Preferences factura = Preferences.userRoot().node(this.getClass().getName());
    public Preferences orden = Preferences.userRoot().node(this.getClass().getName());
    public Preferences letra = Preferences.userRoot().node(this.getClass().getName());
    
    
    public Preferences cifras = Preferences.userRoot().node(this.getClass().getName());
    ValidarStyle validar = new ValidarStyle();
    
    
    @FXML
    private void recordarContraseña(ActionEvent event) {
        if(checkBox.isSelected()){
            prefsUsuario1.put("validar", "recordar");
        }else{
            prefsUsuario1.put("validar", "no recordar");
        }
    }
    public void limpiarText(){
        txtUsuario.setText("");
        txtPassword.setText("");
        cmbTipoUsuario.setValue("");
        cmbCodigoUsuario.setValue("");
        cmbTipoUsuario.setPromptText("Seleccione un tipo de Usuario");
    }
    public void desactivarText(){
        txtUsuario.setEditable(false);
        txtPassword.setEditable(false);
        cmbTipoUsuario.setDisable(true); 
    }
    public void activarText(){
        txtUsuario.setEditable(true);
        txtPassword.setEditable(true);
        cmbTipoUsuario.setDisable(false); 
    }
    public void desactivarControles(){
        btnEliminar.setDisable(true);
        btnEditar.setDisable(true);
        
    }
    public void activarControles(){
        btnEliminar.setDisable(false);
        btnEditar.setDisable(false);
        
    }
    
     @FXML
    private void validarUsuario(KeyEvent event) {
        char letra = event.getCharacter().charAt(0);
        
        if(!Character.isLetterOrDigit(letra)){
            event.consume();
        }else{
        
        }
    }

    @FXML
    private void validarcontraseña(KeyEvent event) {
        char letra = event.getCharacter().charAt(0);
        
        if(!Character.isLetterOrDigit(letra)){
            event.consume();
        }else{
        
        }
    }


    public ObservableList<Usuario> getUsuario(){
        ArrayList<Usuario> lista = new ArrayList();
        ArrayList<String> listaCodigo = new ArrayList();
        String sql = "{call spListarUsuario()}";
        int x=0;
        try {
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new Usuario(
                        rs.getString("usuarioId"),
                         rs.getString("usuarioNombre"),
                        rs.getString("usuarioPassword"),
                        rs.getString("tipoUsuario")
                ));
              listaCodigo.add(x,rs.getString("usuarioId"));
              x++;
            }
            listaComboCodigo = FXCollections.observableList(listaCodigo);
            cmbCodigoUsuario.setItems(listaComboCodigo);
        } catch (SQLException ex) {
            ex.printStackTrace();
            Image imgError = new Image("org/moduloFacturacion/img/error.png");
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR AL CARGAR DATOS");
            noti.text("Error al cargar la base de datos");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
        }
        
        return listaUsuario = FXCollections.observableList(lista);
    }
    
    
    public void cargarDatos(){
        
        tableUsuario.setItems(getUsuario());
        
        colCodigoUsuario.setCellValueFactory(new PropertyValueFactory("usuarioId"));
        colTipoUsuario.setCellValueFactory(new PropertyValueFactory("tipoUsuario"));
        colNombreUsuario.setCellValueFactory(new PropertyValueFactory("usuarioNombre"));
        colPasswordUsuario.setCellValueFactory(new PropertyValueFactory("usuarioPassword"));
        new AutoCompleteComboBoxListener<>(cmbCodigoUsuario);
        desactivarControles();
        desactivarText();
        llenarComboBox();
        limpiarText();   
    }
    
    
    public void llenarComboBox(){
        String sql = "{call spListarTipoUsuario()}";
        int x = 0;
        ArrayList<String>lista= new ArrayList<>();
        try {
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x,rs.getString("tipoUsuario"));
                x++;
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        listaCombo = FXCollections.observableList(lista);
        cmbTipoUsuario.setItems(listaCombo);
        
    }
    
    public void resetarCamposCheque(){
        PfechaX.put("valor1", "55");
        PfechaY.put("valor2", "115");
        
        PordenX.put("valor3", "64");
        PordenY.put("valor4", "97");
        
        PTotalX.put("valor5", "50");
        PTotalY.put("valor6", "79");
        
        PLetrasX.put("valor7", "378");
        PLetrasY.put("valor8", "115");
    }
    public void resetarCamposComprobante(){
        comprobante.put("nochequeX", "340");
        comprobante.put("nochequeY", "130");
        
        comprobante.put("fechaX", "136");
        comprobante.put("fechaY", "148");
        
        comprobante.put("ordenX", "130");
        comprobante.put("ordenY", "175");
        
        comprobante.put("totalX", "130");
        comprobante.put("totalY", "200");
        
        comprobante.put("letrasX", "340");
        comprobante.put("letrasY", "175");
        
        comprobante.put("descX", "136");
        comprobante.put("descY", "290");
    }
     public void resetearCamposFactura(){
        factura.put("diax", "28");
        factura.put("diay", "395");
        
        factura.put("mesx", "68");
        factura.put("mesy", "395");
        
        factura.put("añox", "110");
        factura.put("añoy", "395");
        
        factura.put("nombrex", "70");
        factura.put("nombrey", "370");
        
        factura.put("direccionx", "65");
        factura.put("direcciony", "360");
        
        factura.put("nitx", "218");
        factura.put("nity", "360");
        
        factura.put("tablax", "25");
        factura.put("tablay", "320");
        
        factura.put("descfacx", "70");
        
        factura.put("valorx", "227");
        
        factura.put("espaciado", "18");
        
        factura.put("totalfacx", "232");
        factura.put("totalfacy", "55");
    }
     
    public void resetearCamposFacturaA(){
        facA.put("diax1", "28");
        facA.put("diay1", "395");
        
        facA.put("mesx1", "68");
        facA.put("mesy1", "395");
        
        facA.put("añox1", "110");
        facA.put("añoy1", "395");
        
        facA.put("nombrex1", "70");
        facA.put("nombrey1", "370");
        
        facA.put("direccionx1", "65");
        facA.put("direcciony1", "360");
        
        facA.put("nitx1", "218");
        facA.put("nity1", "360");
        
        facA.put("tablax1", "25");
        facA.put("tablay1", "320");
        
        facA.put("descfacx1", "70");
        
        facA.put("valorx1", "227");
        
        facA.put("espaciado1", "18");
        
        facA.put("totalfacx1", "232");
        facA.put("totalfacy1", "55");
        
    }
    
    
    
    public void llenarCifrasPFac(){
        cifras.put("4","16");
        cifras.put("5","11");
        
        cifras.put("6","6");
        cifras.put("8","1");
        
        cifras.put("9","7");
        cifras.put("10","12");
        
    }
    
    public void llenarCifrasTFac(){
        cifras.put("4T", "20");
        cifras.put("5T", "15");
        cifras.put("6T", "10");
        cifras.put("8T", "2");
        cifras.put("9T", "7");
        cifras.put("10T","9");
    }
    public void llenarCifrasPFacA(){
        cifras.put("4A","16");
        cifras.put("5A","11");
        
        cifras.put("6A","6");
        cifras.put("8A","1");
        
        cifras.put("9A","7");
        cifras.put("10A","12");
        
    }
    
    public void llenarCifrasTFacA(){
        cifras.put("4AT", "25");
        cifras.put("5AT", "20");
        cifras.put("6AT", "16");
        cifras.put("8AT", "8");
        cifras.put("9AT", "2");
        cifras.put("10AT","2");
    }
    
    
     public void resetearCamposOrden(){
        orden.put("diaxorden", "28");
        orden.put("diayorden", "320");
        
        orden.put("mesxorden", "68");
        orden.put("mesyorden", "320");
        
        orden.put("añoxorden", "110");
        orden.put("añoyorden", "320");
        
        orden.put("nombrexorden", "68");
        orden.put("nombreyorden", "297");
        
        orden.put("direccionxorden", "68");
        orden.put("direccionyorden", "279");
        
        
        orden.put("tablaxorden", "28");
        orden.put("tablayorden", "242");
        
        orden.put("descfacxorden", "68");
        
        orden.put("valorxorden", "237");
        
        orden.put("espaciadoorden", "18");
        
        orden.put("totalfacxorden", "240");
        orden.put("totalfacyorden", "40");
    }
    public void resetarLetra(){
        letra.put("tamaño", "9");
    }
    public void resetarLetraA(){
        facA.put("tamaño1", "9");
       
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(PfechaX.get("valor1","root").equals("root") || PfechaY.get("valor2","root").equals("root") || PordenX.get("valor3","root").equals("root") || PordenY.get("valor4","root").equals("root")
                || PTotalX.get("valor5","root").equals("root") || PTotalY.get("valor6","root").equals("root") || PLetrasX.get("valor7","root").equals("root") ||
                 PLetrasY.get("valor8","root").equals("root") ){
            resetarCamposCheque();
        }
        
        if(comprobante.get("nochequeX","root").equals("root") || comprobante.get("nochequeY","root").equals("root") || comprobante.get("fechaX","root").equals("root") || comprobante.get("fechaY","root").equals("root")
                || comprobante.get("ordenX","root").equals("root") || comprobante.get("ordenY","root").equals("root") || comprobante.get("totalX","root").equals("root") ||
                 comprobante.get("totalY","root").equals("root") || comprobante.get("letrasX","root").equals("root") || comprobante.get("letrasY","root").equals("root") || 
                comprobante.get("descX","root").equals("root") || comprobante.get("descY","root").equals("root")){
            resetarCamposComprobante();
            
        }
        if(cifras.get("4", "root").equals("root")){
            llenarCifrasTFac();
            llenarCifrasPFac();
        }
        if(cifras.get("4A", "root").equals("root")){
            llenarCifrasTFacA();
            llenarCifrasPFacA();
        }
        if(factura.get("totalfacy","root").equals("root")){
            resetearCamposFactura();
        }
        
        if(facA.get("totalfacy1","root").equals("root")){
            resetearCamposFacturaA();
        }
         
        if(orden.get("totalfacyorden","root").equals("root")){
            resetearCamposOrden();
        }
        if(letra.get("tamaño","root").equals("root")){
            resetarLetra();
        }
        if(facA.get("tamaño1","root").equals("root")){
            resetarLetraA();
        }
        letra.put("tamañoNombreB",letra.get("tamaño", "root") );
        letra.put("tamañoNombreA",letra.get("tamaño1", "root") );
        
        letra.put("tamañoDirec",letra.get("tamaño", "root") );
        letra.put("tamañoDirecA",letra.get("tamaño1", "root") );
        if(letra.get("longitudProducto","root").equals("root")){
            letra.put("longitudProducto", "25");
            letra.put("longitudNombre", "45");
            letra.put("longitudDireccion", "35");
        }
        prefsRegresar.put("regresar", "menu");
        prefsRegresarProductos.put("regresarProducto", "menu");
         validar.validarMenu(prefs.get("dark", "root"), anchor);
        labelUsuario.setText("¡BIENVENIDO "+login.prefsUsuario.get("usuario","root").toUpperCase()+"!");
        cmbCodigoUsuario.setValue("");
        if(login.prefsLogin.get("tipo","root").equals("empleado")){
            cajaInventario.setDisable(true);
            tabAjustes.setDisable(true);
            itemInventario.setDisable(true);
        }
        
        if(prefsUsuario1.get("validar", "root").equals("recordar")){
            checkBox.setSelected(true);
        }else{
                checkBox.setSelected(false);
        }
       
        
        // caja de bienvenida
         FadeTransition ft = new FadeTransition();
       ft.setFromValue(0);
       ft.setToValue(1);
       ft.setDuration(Duration.seconds(1));
       ft.setNode(paneBienvenida);
       ft.setCycleCount(1);
       ft.play();
       
       
       TranslateTransition tt = new TranslateTransition();
       tt.setFromY(-20);
       tt.setToY(1);
       tt.setDuration(Duration.seconds(1.5));
       tt.setNode(paneBienvenida);
       tt.setCycleCount(1);
       tt.play();
       
       
       //caja de clientes
       
       FadeTransition ftCliente = new FadeTransition();
       ftCliente.setFromValue(0);
       ftCliente.setToValue(1);
       ftCliente.setDuration(Duration.seconds(2));
       ftCliente.setNode(cajaClientes);
       ftCliente.setCycleCount(1);
       ftCliente.play();
       
       
       TranslateTransition ttCliente = new TranslateTransition();
       ttCliente.setFromY(-40);
       ttCliente.setToY(0);
       ttCliente.setDuration(Duration.seconds(2.0));
       ttCliente.setNode(cajaClientes);
       ttCliente.setCycleCount(1);
       ttCliente.play();
       
       // caja de inventario
       
        FadeTransition ftInventario = new FadeTransition();
       ftInventario.setFromValue(0);
       ftInventario.setToValue(1);
       ftInventario.setDuration(Duration.seconds(2));
       ftInventario.setNode(cajaInventario);
       ftInventario.setCycleCount(1);
       ftInventario.play();
       
       
       TranslateTransition tInventario = new TranslateTransition();
       tInventario.setFromY(-60);
       tInventario.setToY(0);
       tInventario.setDuration(Duration.seconds(2.5));
       tInventario.setNode(cajaInventario);
       tInventario.setCycleCount(1);
       tInventario.play();
       
       //CAJA DE PRECIOS
          FadeTransition ftPrecios = new FadeTransition();
       ftPrecios.setFromValue(0);
       ftPrecios.setToValue(1);
       ftPrecios.setDuration(Duration.seconds(2.5));
       ftPrecios.setNode(cajaConsulta);
       ftPrecios.setCycleCount(1);
       ftPrecios.play();
       
       
       TranslateTransition ttPrecios = new TranslateTransition();
       ttPrecios.setFromY(-80);
       ttPrecios.setToY(1);
       ttPrecios.setDuration(Duration.seconds(3));
       ttPrecios.setNode(cajaConsulta);
       ttPrecios.setCycleCount(1);
       ttPrecios.play();
       
       
       // caja de facturas
       FadeTransition ftFacturas = new FadeTransition();
       ftFacturas.setFromValue(0);
       ftFacturas.setToValue(1);
       ftFacturas.setDuration(Duration.seconds(3));
       ftFacturas.setNode(cajaFactura);
       ftFacturas.setCycleCount(1);
       ftFacturas.play();
       
       
       TranslateTransition ttFacturas = new TranslateTransition();
       ttFacturas.setFromY(-80);
       ttFacturas.setToY(0);
       ttFacturas.setDuration(Duration.seconds(3.5));
       ttFacturas.setNode(cajaFactura);
       ttFacturas.setCycleCount(1);
       ttFacturas.play();
       
    }    

        //pestaña de ajustes
    @FXML
    private void tabAjustesEvent(Event event) {
        // caja de usuario
       FadeTransition ftUsuario = new FadeTransition();
       ftUsuario.setFromValue(0);
       ftUsuario.setToValue(1);
       ftUsuario.setDuration(Duration.seconds(2));
       ftUsuario.setNode(paneUsuario);
       ftUsuario.setCycleCount(1);
       ftUsuario.play();
       
       
       TranslateTransition ttUsuario = new TranslateTransition();
       ttUsuario.setFromY(-80);
       ttUsuario.setToY(0);
       ttUsuario.setDuration(Duration.seconds(1.0));
       ttUsuario.setNode(paneUsuario);
       ttUsuario.setCycleCount(1);
       ttUsuario.play();
        
       //caja de tabla
       FadeTransition ftTablaUsuario = new FadeTransition();
       ftTablaUsuario.setFromValue(0);
       ftTablaUsuario.setToValue(1);
       ftTablaUsuario.setDuration(Duration.seconds(2));
       ftTablaUsuario.setNode(paneTabla);
       ftTablaUsuario.setCycleCount(1);
       ftTablaUsuario.play();
       
       
       TranslateTransition ttTabla = new TranslateTransition();
       ttUsuario.setFromY(-80);
       ttUsuario.setToY(0);
       ttUsuario.setDuration(Duration.seconds(2.0));
       ttUsuario.setNode(paneTabla);
       ttUsuario.setCycleCount(1);
       ttUsuario.play();
       cargarDatos();
    }
    

    @FXML
    private void tabBienvenida(Event event) {
         // caja de bienvenida
         FadeTransition ft = new FadeTransition();
       ft.setFromValue(0);
       ft.setToValue(1);
       ft.setDuration(Duration.seconds(1));
       ft.setNode(paneBienvenida);
       ft.setCycleCount(1);
       ft.play();
       
       
       TranslateTransition tt = new TranslateTransition();
       tt.setFromY(-20);
       tt.setToY(1);
       tt.setDuration(Duration.seconds(1.5));
       tt.setNode(paneBienvenida);
       tt.setCycleCount(1);
       tt.play();
        
        
       //caja de clientes
       
         FadeTransition ftCliente = new FadeTransition();
       ftCliente.setFromValue(0);
       ftCliente.setToValue(1);
       ftCliente.setDuration(Duration.seconds(2));
       ftCliente.setNode(cajaClientes);
       ftCliente.setCycleCount(1);
       ftCliente.play();
       
       
       TranslateTransition ttCliente = new TranslateTransition();
       ttCliente.setFromY(-40);
       ttCliente.setToY(0);
       ttCliente.setDuration(Duration.seconds(2.0));
       ttCliente.setNode(cajaClientes);
       ttCliente.setCycleCount(1);
       ttCliente.play();
       
       // caja de inventario
       
        FadeTransition ftInventario = new FadeTransition();
       ftInventario.setFromValue(0);
       ftInventario.setToValue(1);
       ftInventario.setDuration(Duration.seconds(2));
       ftInventario.setNode(cajaInventario);
       ftInventario.setCycleCount(1);
       ftInventario.play();
       
       
       TranslateTransition tInventario = new TranslateTransition();
       tInventario.setFromY(-60);
       tInventario.setToY(0);
       tInventario.setDuration(Duration.seconds(2.5));
       tInventario.setNode(cajaInventario);
       tInventario.setCycleCount(1);
       tInventario.play();
       
         
       //CAJA DE PRECIOS
          FadeTransition ftPrecios = new FadeTransition();
       ftPrecios.setFromValue(0);
       ftPrecios.setToValue(1);
       ftPrecios.setDuration(Duration.seconds(2.5));
       ftPrecios.setNode(cajaConsulta);
       ftPrecios.setCycleCount(1);
       ftPrecios.play();
       
       
       TranslateTransition ttPrecios = new TranslateTransition();
       ttPrecios.setFromY(-80);
       ttPrecios.setToY(1);
       ttPrecios.setDuration(Duration.seconds(3));
       ttPrecios.setNode(cajaConsulta);
       ttPrecios.setCycleCount(1);
       ttPrecios.play();
       
       // caja de facturas
       FadeTransition ftFacturas = new FadeTransition();
       ftFacturas.setFromValue(0);
       ftFacturas.setToValue(1);
       ftFacturas.setDuration(Duration.seconds(2));
       ftFacturas.setNode(cajaFactura);
       ftFacturas.setCycleCount(1);
       ftFacturas.play();
       
       
       TranslateTransition ttFacturas = new TranslateTransition();
       ttFacturas.setFromY(-80);
       ttFacturas.setToY(0);
       ttFacturas.setDuration(Duration.seconds(3.0));
       ttFacturas.setNode(cajaFactura);
       ttFacturas.setCycleCount(1);
       ttFacturas.play();
    }
    

    /* CHEQUE*/
    @FXML
    private void chequesView() throws IOException {
        String chequesUrl = "org/moduloFacturacion/view/chequesView.fxml";
        cambioScene.Cambio(chequesUrl,(Stage) anchor.getScene().getWindow());
    }
    
    private void chequeAtajo(ActionEvent event) throws IOException {
        chequesView();
    }
    
    /* CREDITOS*/
    @FXML
    private void credito() throws IOException {
        String chequesUrl = "org/moduloFacturacion/view/creditosView.fxml";
        cambioScene.Cambio(chequesUrl,(Stage) anchor.getScene().getWindow());
    }
    
    private void creditoView(ActionEvent event) throws IOException {
        credito();
    }
    
    
    /* INVENTARIO*/
        @FXML
    private void inventarioView(ActionEvent event) throws IOException {
        inventario();
    }
    
    public void inventario() throws IOException{
        String inventarioUrl = "org/moduloFacturacion/view/InventarioView.fxml";
        cambioScene.Cambio(inventarioUrl,(Stage) anchor.getScene().getWindow());
    }
    
     @FXML
    private void inventarioAtajo(MouseEvent event) throws IOException {
        inventario();
    }
    
   
    /*FACTURA */
    public void factura() throws IOException{
        String facturaUrl = "org/moduloFacturacion/view/FacturacionView.fxml";
        cambioScene.Cambio(facturaUrl,(Stage) anchor.getScene().getWindow());
    }
    
    @FXML
    private void facturasView(ActionEvent event) throws IOException {
        factura();
    }
    @FXML
    private void facturaAtajo(MouseEvent event) throws IOException {
        factura();
    }
    
    
    /* CLIENTES*/
    public void clientes() throws IOException{
        String clienteUrl = "org/moduloFacturacion/view/ClienteView.fxml";
        cambioScene.Cambio(clienteUrl,(Stage) anchor.getScene().getWindow());
    }
    
    @FXML
    private void Clientesview(ActionEvent event) throws IOException {
        clientes();
    }
      @FXML
    private void ClientesAtajo(MouseEvent event) throws IOException {
        clientes();
    }
    
    
    /* PRODUCTOS*/
    public void productos() throws IOException{
        String inventarioUrl = "org/moduloFacturacion/view/ProductosView.fxml";
        cambioScene.Cambio(inventarioUrl,(Stage) anchor.getScene().getWindow());
    }
    
    @FXML
    private void productosView(ActionEvent event) throws IOException {
        productos();
    }

    
    /* PROVEEDOR*/
    public void proveedores() throws IOException{
        String inventarioUrl = "org/moduloFacturacion/view/ProveedoresView.fxml";
        cambioScene.Cambio(inventarioUrl,(Stage) anchor.getScene().getWindow());
    }
    
    @FXML
    private void proveedoresView(ActionEvent event) throws IOException {
        proveedores();
    }
    
   

    
     //atajos de menu de bienvenida
    @FXML
    private void AtajosInicio(KeyEvent event) {
    }
    
    //atajos de configuracion
    @FXML
    private void AtajosConfiguracion(KeyEvent event) {
        if(cmbCodigoUsuario.isFocused()){
                if(event.getCode() == KeyCode.ENTER){
                    buscar();
                }
        }
    }

    //atajos de vista en general
    @FXML
    private void AtajosVista(KeyEvent event) throws IOException {
        //modulos
       if(event.getCode() == KeyCode.F1){
           factura();
       }else{
           if(event.getCode() == KeyCode.F2){
               productos();
           }else{
               if(event.getCode() == KeyCode.F3){
                   inventario();
               }else{
                   if(event.getCode() == KeyCode.F4){
                       clientes();
                   }else{
                       if(event.getCode() == KeyCode.F5){
                           proveedores();
                       }else{
                           if(event.getCode() == KeyCode.F6){
                               chequesView();
                           }else{
                               if(event.getCode() == KeyCode.F7){
                                   credito();
                               }else{
                                   if(event.getCode() == KeyCode.F8){
                                      promedioProveedores();
                                   }
                               }
                           }
                       }
                   }
               }
           }
       }
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
                cmbCodigoUsuario.setDisable(true);
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
                cmbCodigoUsuario.setDisable(false);
                btnBuscar.setDisable(false);
                cancelar = Operacion.NINGUNO;
                break;
        }
    }
    
    public void accion(String sql){
         Alert alert = new Alert(AlertType.CONFIRMATION);
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
                        cargarDatos();
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
                    String codigo="";
                    while(rs.next()){
                        cmbCodigoUsuario.setValue(rs.getString("usuarioId"));
                        txtUsuario.setText(rs.getString("usuarioNombre"));
                        txtPassword.setText(rs.getString("usuarioPassword"));
                        cmbTipoUsuario.setValue(rs.getString("tipoUsuario"));
                        codigo = rs.getString("usuarioId");
                        
                    }                    
                    if(rs.first()){
                        for(int i=0; i<tableUsuario.getItems().size(); i++){
                            if(colCodigoUsuario.getCellData(i) == codigo){
                                tableUsuario.getSelectionModel().select(i);
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
                if(txtUsuario.getText().isEmpty() || txtPassword.getText().isEmpty() || cmbTipoUsuario.getValue() == ""){
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("HAY CAMPOS VACÍOS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                    
                }else{
                    if((txtUsuario.getText().length() >= 6 && txtUsuario.getText().length() < 30 ) &&(txtPassword.getText().length() >= 6 && txtPassword.getText().length() < 30)){
                        int tipoUsuario;
                        Usuario nuevoUsuario = new Usuario();
                        nuevoUsuario.setUsuarioNombre(txtUsuario.getText());
                        nuevoUsuario.setUsuarioPassword(txtPassword.getText());
                        if(cmbTipoUsuario.getValue().equals("Administrador")){
                               tipoUsuario = 1;
                        }else{
                            tipoUsuario = 2;
                        }

                        String sql = "{call spAgregarUsuario('"+nuevoUsuario.getUsuarioNombre()+"','"+nuevoUsuario.getUsuarioPassword()+"','"+tipoUsuario+"')}";
                        accion(sql);
                    }else{
                        Notifications noti = Notifications.create();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR");
                        noti.text("USUARIO Y/O CONTRASEÑA NO TIENEN UNA LONGITUD ADECUADA (DEBEN ESTAR ENTRE 6 Y 30 CARACTERES)");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();   
                        noti.show();
                    }
                    
                }
            }else{
                tipoOperacion = Operacion.AGREGAR;
                accion();
            }
    }

    @FXML
    private void btnEditar(MouseEvent event) {
        int tipoUsuario;
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsuarioId(cmbCodigoUsuario.getValue());
        nuevoUsuario.setUsuarioNombre(txtUsuario.getText());
        nuevoUsuario.setUsuarioPassword(txtPassword.getText());
        if(cmbTipoUsuario.getValue().equals("Administrador")){
               tipoUsuario = 1;
        }else{
            tipoUsuario = 2;
        }
        tipoOperacion = Operacion.ACTUALIZAR;
        String sql = "{call SpActualizarUsuario('"+nuevoUsuario.getUsuarioId()+"','"+nuevoUsuario.getUsuarioNombre()+"','"+nuevoUsuario.getUsuarioPassword()+"','"+tipoUsuario+"')}";
        accion(sql);
}
    
    
    @FXML
    private void btnEliminar(MouseEvent event) {
        
        if(tipoOperacion == Operacion.GUARDAR){
            tipoOperacion = Operacion.CANCELAR;
            accion();
        }else{
            String sql = "{call SpEliminarUsuarios('"+cmbCodigoUsuario.getValue()+"')}";
            tipoOperacion = Operacion.ELIMINAR;
            accion(sql);
        }
    }

       public void buscar(){
            if(cmbCodigoUsuario.getValue().equals("")){
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE CÓDIGO ESTA VACÍO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
        }else{
            tipoOperacion = Operacion.BUSCAR;
            String sql = "{call spBuscarUsuario('"+cmbCodigoUsuario.getValue()+"')}";
            accion(sql);
        }
       }
    @FXML
    private void btnBuscar(MouseEvent event) {
       
      buscar();
    }

    @FXML
    private void seleccionarElementos(MouseEvent event) {
        int index = tableUsuario.getSelectionModel().getSelectedIndex();
        try{
            activarText();
            cmbCodigoUsuario.setValue(colCodigoUsuario.getCellData(index).toString());
            txtUsuario.setText(colNombreUsuario.getCellData(index));
            txtPassword.setText(colPasswordUsuario.getCellData(index));
            cmbTipoUsuario.setValue(colTipoUsuario.getCellData(index));
            btnEditar.setDisable(false);
            btnEliminar.setDisable(false);
            cmbTipoUsuario.setDisable(false);
        }catch(Exception e){
            
        }
        
    }
    
     @FXML
    private void off(MouseEvent event) {
         prefs.put("dark", "oscuro");
        validar.validarMenu(prefs.get("dark", "root"), anchor);
    }

    @FXML
    private void on(MouseEvent event) {
        prefs.put("dark", "claro");
        validar.validarMenu(prefs.get("dark", "root"), anchor);
    }
        @FXML
    private void consultaPrecios(MouseEvent event) throws IOException {
        
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("org/moduloFacturacion/view/ConsultaPrecios.fxml"));
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
        stage.setWidth(599);
        stage.setHeight(510);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("CONSULTA DE PRECIOS");
        stage.setScene(scene);
        stage.show();
    }
    
    
            @FXML
    private void promedioProveedores() throws IOException {
        
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("org/moduloFacturacion/view/promedioProveedores.fxml"));
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
        stage.setWidth(611);
        stage.setHeight(639);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("CONSULTA DE PRECIOS");
        stage.setScene(scene);
        stage.show();
    }
    // tab de informacion donde irá el manual de usuario
    
    
    
   

  
    
    int numImg;
    private ArrayList<ArchivosVO> ListaComponente;
    MiVisorPDF pn = new MiVisorPDF();
    ArchivosVO pl = new ArchivosVO();
    
    CuadroImagen img= new CuadroImagen();
    int paginas = 0;
    private int totalp = -1;

    @FXML
    private void tabInformacion(Event event) {
        //Es considerado pagina 1
        numImg = 0;
        //Lee la pagina 1
        
        ListaComponente = pn.leerPDF();
        
        //Guardamos todas las paginas en el ArrayList
        
        
            pl = ListaComponente.get(paginas);
            InputStream targetStream = new ByteArrayInputStream(pl.getArchivos());

            img.setImagen(targetStream);
            
        
       
        
        totalp = ListaComponente.size();
        //Mostramos la primera pagina
        ArchivosVO pi = new ArchivosVO();
        pi = ListaComponente.get(0);
        ImageView img1 = new ImageView();
        img1.setImage(img.imagen);
        
        img1.fitWidthProperty().bind(scroll.widthProperty());
        scroll.setCenterShape(true);
        scroll.setContent(img1);
        
    }
    
     @FXML
    private void btnAtras(ActionEvent event) {
         try{
                paginas--;
                pl = ListaComponente.get(paginas);
                InputStream targetStream = new ByteArrayInputStream(pl.getArchivos());

                img.setImagen(targetStream);
                
                


                totalp = ListaComponente.size();
                //Mostramos la primera pagina
                ArchivosVO pi = new ArchivosVO();
                pi = ListaComponente.get(0);
                ImageView img1 = new ImageView();
                img1.setImage(img.imagen);

                img1.fitWidthProperty().bind(scroll.widthProperty());
                scroll.setCenterShape(true);
                scroll.setContent(img1);
             }catch(Exception ex){
                  Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("YA NO SE ECONTRARON  MÁS PÁGINAS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                    paginas = -1;
             }
    }
    
      @FXML
    private void btnSiguiente(ActionEvent event) {
             try{
                paginas++;
                 pl = ListaComponente.get(paginas);
                InputStream targetStream = new ByteArrayInputStream(pl.getArchivos());

                img.setImagen(targetStream);
                
                 


                totalp = ListaComponente.size();
                //Mostramos la primera pagina
                ArchivosVO pi = new ArchivosVO();
                pi = ListaComponente.get(0);
                ImageView img1 = new ImageView();
                img1.setImage(img.imagen);

                img1.fitWidthProperty().bind(scroll.widthProperty());
                scroll.setCenterShape(true);
                scroll.setContent(img1);
             }catch(Exception ex){
                  Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("YA NO SE ECONTRARON  MÁS PÁGINAS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                    paginas = -1;
             }
    }
    
    private void cierreCaja(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("CIERRE DE CAJA");
        dialog.setHeaderText("Ingrese la fecha: ");
        dialog.setContentText("año/mes/día");
        Optional<String>  result = dialog.showAndWait();
        
        if(result.isPresent()){
            try{
            
                Map parametros = new HashMap();

                 String FechaCorte = result.get();
                String repuesta = "'"+FechaCorte+"'";
                
                parametros.put("FechaCorte", "'"+FechaCorte+"'");
                System.out.println(repuesta);
                 GenerarReporte.mostrarReporte("CierreDeCaja.jasper", "CIERRE DE CAJAS", parametros);
                 
                }catch(Exception e){
                    e.printStackTrace();
                }
        }
        
    }

    private void reporteVentas(ActionEvent event) {
         TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("REPORTE DE VENTAS");
        dialog.setHeaderText("Ingrese la fecha: ");
        dialog.setContentText("año/mes/día");
        Optional<String>  result = dialog.showAndWait();
        if(result.isPresent()){
            try{
                Map parametros = new HashMap();

                String FechaCorte = result.get();
                 
                String repuesta = "'"+FechaCorte+"'";
                
                parametros.put("FechaCorte", "'"+FechaCorte+"'");
                 GenerarReporte.mostrarReporte("CorteDeCaja.jasper", "REPORTE DE VENTAS", parametros);
                
                
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
    }

    
 }

