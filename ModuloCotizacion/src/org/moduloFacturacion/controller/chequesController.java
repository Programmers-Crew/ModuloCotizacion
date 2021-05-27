package org.moduloFacturacion.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
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
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.moduloFacturacion.bean.Animations;
import org.moduloFacturacion.bean.AutoCompleteComboBoxListener;
import org.moduloFacturacion.bean.CambioScene;
import org.moduloFacturacion.bean.ChequeBuscado;
import org.moduloFacturacion.bean.ChequeEncabezadoBuscado;
import org.moduloFacturacion.bean.Chequedetalle;
import org.moduloFacturacion.bean.Letras;
import org.moduloFacturacion.bean.ValidarStyle;
import org.moduloFacturacion.bean.imprimirCheque;
import org.moduloFacturacion.bean.imprimirCheque2;
import org.moduloFacturacion.db.Conexion;


public class chequesController implements Initializable {

    int detalleEditarId =0;
    Letras letras = new Letras();
    DecimalFormat twoDForm = new DecimalFormat("#.00");
    @FXML
    private JFXButton btnEditarCheque;
    @FXML
    private JFXButton btnEliminarCheque;
    @FXML
    private Label nochequeText;
    @FXML
    private Label pagueseText;
    @FXML
    private Label descText;
    @FXML
    private Label digitosText;
    @FXML
    private Label sumadeText;
    @FXML
    private Label fechaText;
    @FXML
    private AnchorPane comprobanteChequeIma;
    @FXML
    private TextField fechaX;
    @FXML
    private TextField fechaY;
    @FXML
    private TextField ordenX;
    @FXML
    private TextField OrdenY;
    @FXML
    private TextField totalX;
    @FXML
    private TextField totalY;
    @FXML
    private TextField letrasX;
    @FXML
    private TextField letrasY;
    @FXML
    private TextField descX;
    @FXML
    private TextField descY;
    @FXML
    private TextField CNoChequeX;
    @FXML
    private TextField CNoChequeY;
    @FXML
    private TextField CordenX;
    @FXML
    private TextField CordenY;
    @FXML
    private TextField CletrasX;
    @FXML
    private TextField CletrasY;
    @FXML
    private TextField CfechaX;
    @FXML
    private TextField CfechaY;
    @FXML
    private TextField CtotalX;
    @FXML
    private TextField CtotalY;
    @FXML
    private ComboBox<String> creditoCancelar;

   

   

    
    
    
    public enum Operacion{AGREGAR,GUARDAR,ELIMINAR,BUSCAR,ACTUALIZAR,CANCELAR,NINGUNO, VENDER,FILTRAR,CARGAR};
    public Operacion cancelar = Operacion.NINGUNO;
    public Operacion tipoOperacionChequeDetalle = Operacion.NINGUNO; 
    Image imgError = new Image("org/moduloFacturacion/img/error.png");
    Image imgCorrecto= new Image("org/moduloFacturacion/img/correcto.png");
    Image imgWarning = new Image("org/moduloFacturacion/img/warning.png");
    CambioScene cambioScene = new CambioScene();  
    LoginViewController login = new LoginViewController();
    @FXML
    private Pane buttonInicio;
    @FXML
    private AnchorPane anchor1;
    @FXML
    private JFXTextField numeroCheque;
    @FXML
    private JFXTextField chequeFecha;
    @FXML
    private JFXComboBox<String> pagoOrden;
    @FXML
    private TextField sumaLetras;
    @FXML
    private TextArea descripcionPago;
    @FXML
    private AnchorPane anchor2;
   
    @FXML
    private TextField totalValor;
    @FXML
    private Button btnImprimir;
    @FXML
    private AnchorPane anchor3;
    @FXML
    private JFXButton btnBuscarFiltro;
    private AnchorPane anchor4;
    
    /* ENTIDADES DE CONTROL DE CHEQUES*/
    @FXML
    private TableView<ChequeBuscado> tblResultadoCheque;
    @FXML
    private TableColumn<ChequeBuscado, Integer> colNoChequeBuscado;
    @FXML
    private TableColumn<ChequeBuscado, String> colFechaChequeBuscado;
    @FXML
    private TableColumn<ChequeBuscado, Double> colTotalChequeBuscado;
    @FXML
    private TableColumn<ChequeBuscado, String> colReferenteChequeBuscado;
     @FXML
    private TableColumn<ChequeBuscado, String> colDetallerChequeBuscado;
    @FXML
    private TableColumn<ChequeBuscado, String> colUsuarioChequeBuscado;

    @FXML
    private JFXComboBox<String> txtBusquedaCodigoChe;

    
    
    
    double totalChequeDetalle=0;
    ObservableList<Chequedetalle> listaCheque;
    LocalDate fechaActual = LocalDate.now();
    @FXML
    private AnchorPane anchor;
    ValidarStyle validar = new ValidarStyle();
    MenuPrincipalContoller menu = new MenuPrincipalContoller();
    
    
    @FXML
    private void previewEvent(MouseEvent event) {
        
        Stage stage = (Stage) anchor.getScene().getWindow();
        
        if(numeroCheque.getText().isEmpty() || pagoOrden.getValue().isEmpty() || totalValor.getText().isEmpty() || sumaLetras.getText().isEmpty() || descripcionPago.getText().isEmpty()){
             Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR");
            noti.text("HAY CAMPOS VACÍOS");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
        }else{  
                nochequeText.setLayoutY(160);
                nochequeText.setLayoutX(800);
                nochequeText.setText(numeroCheque.getText());
                nochequeText.setFont(new Font("System",20));

                fechaText.setLayoutY(190);
                fechaText.setLayoutX(290);
                fechaText.setText(chequeFecha.getText());
                fechaText.setFont(new Font("System",20));


                pagueseText.setLayoutY(230);
                pagueseText.setLayoutX(220);
                pagueseText.setText(pagoOrden.getValue());
                pagueseText.setFont(new Font("System",20));


                digitosText.setLayoutY(230);
                digitosText.setLayoutX(800);
                digitosText.setText(totalValor.getText());
                digitosText.setFont(new Font("System",20));

                sumadeText.setLayoutY(270);
                sumadeText.setLayoutX(240);
                sumadeText.setText(sumaLetras.getText());
                sumadeText.setFont(new Font("System",20));

                descText.setLayoutY(450);
                descText.setLayoutX(290);
                descText.setText(descripcionPago.getText());
                descText.setFont(new Font("System",20));;
            
            
        }
    
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validar.validarView(menu.prefs.get("dark", "root"), anchor);
        btnEditarCheque.setDisable(true);
        btnEliminarCheque.setDisable(true);
        limpiarTextChequeDetalle();
        descripcionPago.setWrapText(true);
        
        cargarCombo();
        cmbProv();
        new AutoCompleteComboBoxListener(creditoCancelar);
       
    }    
    public void limpiarTextChequeDetalle(){
        numeroCheque.setText("");
        pagoOrden.setValue("");
        descripcionPago.setText("");
    }
    public void limpiarDetalle(){
        descripcionPago.setText("");
    }
   
    
   
    public void accion(String sql){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        PreparedStatement ps;
        ResultSet rs;
        Notifications noti = Notifications.create();
        ButtonType buttonTypeSi = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");
        
        switch(tipoOperacionChequeDetalle){
            case ACTUALIZAR:
                try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    ps.execute();
                    noti.graphic(new ImageView(imgCorrecto));
                    noti.title("OPERACIÓN EXITOSA");
                    noti.text("SE HA ACTUALIZADO EXITOSAMENTE EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionChequeDetalle = Operacion.CANCELAR;
                    btnEditarCheque.setDisable(true);
                    btnEliminarCheque.setDisable(true);
                }catch(SQLException ex){
                    ex.printStackTrace();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR AL ACTUALIZAR");
                    noti.text("HA OCURRIDO UN ERROR AL ACTUALIZAR EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionChequeDetalle = Operacion.NINGUNO;
                }
                break;
            case ELIMINAR:
                try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    ps.execute();
                    noti.graphic(new ImageView(imgCorrecto));
                    noti.title("OPERACIÓN EXITOSA");
                    noti.text("SE HA ELIMINADO EXITOSAMENTE EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionChequeDetalle = Operacion.CANCELAR;
                    btnEditarCheque.setDisable(true);
                    btnEliminarCheque.setDisable(true);
                }catch(SQLException ex){
                    ex.printStackTrace();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR AL ELIMINADO");
                    noti.text("HA OCURRIDO UN ERROR AL ELIMINADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionChequeDetalle = Operacion.NINGUNO;
                }
                break;
        }
    }   
    @FXML
    private void regresar(MouseEvent event) throws IOException {
         String menu = "org/moduloFacturacion/view/menuPrincipal.fxml";
        cambioScene.Cambio(menu,(Stage) anchor.getScene().getWindow());
    }
    
    
    
    
    public void actualizarCredito(String nofac){
        
        String sql = "call SpMarcarPagadocFac('"+nofac+"')";
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ps.execute();
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgCorrecto));
            noti.title("CREDITO ACTUALIZADO");
            noti.text("Se ha pagado el credito de la factura: "+nofac);
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
        }
    }
    
    ObservableList<String> listaProveedoresProductos;
    public ObservableList llenarComboProveedores(){
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
        
        return listaProveedoresProductos;
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
   
    
    public void pagarCredito(){
        String noFac = creditoCancelar.getValue();
        
        double montoFac = 0;
        String sql = "call SpBuscarFacCredito('"+noFac+"')";
        
        try {
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
             while(rs.next()){
                montoFac = rs.getDouble("creditoMonto");
                
            }
            if(rs.first()){
                
                actualizarCredito(noFac);
                
            }else{
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR");
                noti.text("No se ha encontrado creditos");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();   
                noti.show();
            }
        } catch (SQLException ex) {
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR");
            noti.text("hubo un error en la base de datos"+ex);
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
        }
        
        
    }
    
     @FXML
    private void imprimirCheque(MouseEvent event) {
        if(numeroCheque.getText().equals("") || chequeFecha.getText().equals("") || pagoOrden.getValue().equals("")){
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR");
            noti.text("HAY CAMPOS VACÍOS");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
            
        }else{
            pagarCredito();
            imprimirCheque imprimirC = new imprimirCheque();
            imprimirCheque2 imprimirch = new imprimirCheque2();
          
            imprimirch.imprima(chequeFecha.getText(), Double.parseDouble(totalValor.getText()), pagoOrden.getValue(), sumaLetras.getText(), totalValor.getText());
            imprimirC.imprima(numeroCheque.getText(), chequeFecha.getText(), pagoOrden.getValue(), sumaLetras.getText(), totalValor.getText(),descripcionPago.getText());
            guardarCheque();
        }
       
    }
     public void guardarCheque(){
       String sqlCheque = "{call SpAgregarCheque('"+numeroCheque.getText()+"','"+chequeFecha.getText()+"','"+pagoOrden.getValue()+"','"+totalValor.getText()+"','"+getUsuarioId()+"','"+descripcionPago.getText()+"')}";
       try{

           PreparedStatement psCheque = Conexion.getIntance().getConexion().prepareCall(sqlCheque);
           psCheque.execute();
           
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgCorrecto));
            noti.title("OPERACIÓN EXITOSA");
            noti.text("SE HA IMPRESO Y REGISTRADO CON ÉXITO");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
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
       }
    }
     
    public int getUsuarioId(){
     int codigoUsuario=0;
     System.out.println(login.prefsUsuario.get("usuario", "root"));
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
     System.out.println(codigoUsuario);
     return codigoUsuario;
    }
    @FXML
    private void btnEliminarCheque(MouseEvent event) {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType buttonTypeSi = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");
        
        alert.setTitle("WARNING");
        alert.setHeaderText("ELIMINAR REGISTRO DE CHEQUE");
        alert.setContentText("¿Está seguro que desea eliminar este registro?");

        alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == buttonTypeSi){
             String sql = "{call SpEliminarChequeEncabezado('"+detalleEditarId+"')}";
            tipoOperacionChequeDetalle = Operacion.ELIMINAR;
            accion(sql);
        }
        
    }
    
    @FXML
    private void validarNoCheque(KeyEvent event) {
        char letra = event.getCharacter().charAt(0);
        
        if(Character.isDigit(letra)){
            
        }else{
            event.consume();
        }
    }

    
    @FXML
    private void convertirLetras(KeyEvent event) {
        if(!totalValor.getText().equals("")){
            sumaLetras.setText(letras.Convertir(twoDForm.format(Double.parseDouble(totalValor.getText())), true));
        }else{
            totalValor.setText("");
        }
    }
    public void letras(){
        if(!totalValor.getText().equals("")){
            sumaLetras.setText(letras.Convertir(twoDForm.format(Double.parseDouble(totalValor.getText())), true));
        }else{
            totalValor.setText("hola");
        }
    }
    

    
    @FXML
    private void btnEditarChequeDetalle(MouseEvent event) {
        if(numeroCheque.getText().equals("") || chequeFecha.getText().equals("") || pagoOrden.getValue().equals("")|| descripcionPago.getText().equals("")){
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR");
            noti.text("HAY CAMPOS VACÍOS");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
            
        }else{
            Chequedetalle chequeNuevo = new Chequedetalle();
            chequeNuevo.setChequeDetalleDesc(descripcionPago.getText());
            
            String sql = "{call SpEditarChequeEncabezado('"+detalleEditarId+"','"+chequeNuevo.getChequeDetalleCuenta()+"','"+chequeNuevo.getChequeDetalleDesc()+"','"+chequeNuevo.getChequeDetalleValor()+"')}";
            tipoOperacionChequeDetalle = Operacion.ACTUALIZAR;
            accion(sql);
            
        }
    }

   

    @FXML
    private void atajosProductos(KeyEvent event) {
    }
    
    ObservableList<String> listaCombo;

    public void cargarCombo(){
        ArrayList<String> lista = new ArrayList();
        String sql ="{call SpListarCredit()}";
        int x=0;
        try{
              PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
              ResultSet rs = ps.executeQuery();
            while(rs.next()){
                
                lista.add(x, rs.getString("noFactura"));
                 x++;
                 
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
       listaCombo = FXCollections.observableList(lista);
       creditoCancelar.setItems(listaCombo);
    }
    
    
    /* CODIGO CONTROL DE CHEQUES*/
    
    public Operacion tipoOperacionBusquedaCheques = Operacion.NINGUNO; 

    ObservableList<String> listaNumeroCheque;
    ObservableList<ChequeBuscado> listaChequeBuscadas;
    ObservableList<ChequeEncabezadoBuscado> listaEncabezadoBuscado;

     Animations animacion = new Animations();
    
    Integer noCheque = 0;
    
    /*OBTENER DATOS DE TABLA CHEQUES*/
    
    /*listar todos los cheques */
        public ObservableList<ChequeBuscado> getControlCheque(){
        ArrayList<ChequeBuscado> lista = new ArrayList();
        ArrayList<String> comboNumeroCheques = new ArrayList();
        String sql = "{call SpListarCheque()}";
        int x=0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new ChequeBuscado(
                            rs.getInt("chequeNo"),
                            rs.getString("chequeLugarYFecha"),
                            rs.getString("chequePagoAlaOrdenDe"),
                            rs.getDouble("chequeMonto"),
                            rs.getString("chequeDetalleDesc"),
                            rs.getString("usuarioNombre")
                ));
                comboNumeroCheques.add(x, rs.getString("chequeNo"));
                x++;
                
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        listaNumeroCheque = FXCollections.observableList(comboNumeroCheques);
        txtBusquedaCodigoChe.setItems(listaNumeroCheque);
        return listaChequeBuscadas = FXCollections.observableList(lista);
    }
    
        
    @FXML
    public void cargarChequesBuscadas(){
        animacion.animacion(anchor3, anchor4);
        tblResultadoCheque.setItems(getControlCheque());
        colNoChequeBuscado.setCellValueFactory(new PropertyValueFactory("chequeNo"));
        colFechaChequeBuscado.setCellValueFactory(new PropertyValueFactory("chequeLugarYFecha"));  
        colTotalChequeBuscado.setCellValueFactory(new PropertyValueFactory("chequeMonto"));
        colReferenteChequeBuscado.setCellValueFactory(new PropertyValueFactory("chequePagoAlaOrdenDe"));
        
        colDetallerChequeBuscado.setCellValueFactory(new PropertyValueFactory("chequeDetalleDesc"));
        colUsuarioChequeBuscado.setCellValueFactory(new PropertyValueFactory("usuarioNombre"));

        new AutoCompleteComboBoxListener(txtBusquedaCodigoChe);
        txtBusquedaCodigoChe.setValue("");
    }  
    
            
    public void cargarChequesBuscadas2(){
        tblResultadoCheque.setItems(getControlCheque());
        colNoChequeBuscado.setCellValueFactory(new PropertyValueFactory("chequeNo"));
        colFechaChequeBuscado.setCellValueFactory(new PropertyValueFactory("chequeLugarYFecha"));  
        colTotalChequeBuscado.setCellValueFactory(new PropertyValueFactory("chequeMonto"));
        colReferenteChequeBuscado.setCellValueFactory(new PropertyValueFactory("chequePagoAlaOrdenDe"));
        
        colDetallerChequeBuscado.setCellValueFactory(new PropertyValueFactory("chequeDetalleDesc"));
        colUsuarioChequeBuscado.setCellValueFactory(new PropertyValueFactory("usuarioNombre"));

    }  
    
    
    /*listar cheque buscados por id*/
    
        public ObservableList<ChequeBuscado> getChequeBuscadasPorId(){
        ArrayList<ChequeBuscado> lista = new ArrayList();
        String sql = "{call SpBuscarCheque('"+txtBusquedaCodigoChe.getValue()+"')}";
        int x=0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new ChequeBuscado(
                            rs.getInt("chequeNo"),
                            rs.getString("chequeLugarYFecha"),
                            rs.getString("chequePagoAlaOrdenDe"),
                            rs.getDouble("chequeMonto"),
                            rs.getString("chequeDetalleDesc"),
                            rs.getString("usuarioNombre")
                ));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return listaChequeBuscadas = FXCollections.observableList(lista);
    }
        
    public void cargarChequesBuscadasPorId(){
        tblResultadoCheque.setItems(getChequeBuscadasPorId());
        
        colNoChequeBuscado.setCellValueFactory(new PropertyValueFactory("chequeNo"));
        colFechaChequeBuscado.setCellValueFactory(new PropertyValueFactory("chequeLugarYFecha"));  
        colTotalChequeBuscado.setCellValueFactory(new PropertyValueFactory("chequeMonto"));
        colReferenteChequeBuscado.setCellValueFactory(new PropertyValueFactory("chequePagoAlaOrdenDe"));
        
        colDetallerChequeBuscado.setCellValueFactory(new PropertyValueFactory("chequeDetalleDesc"));
        colUsuarioChequeBuscado.setCellValueFactory(new PropertyValueFactory("usuarioNombre"));
        txtBusquedaCodigoChe.setValue("");
    }  
        
     
     public void accionBusqueda(String sql){
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        PreparedStatement ps;
        ResultSet rs;
        Notifications noti = Notifications.create();
        ButtonType buttonTypeSi = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");
        switch(tipoOperacionBusquedaCheques){
            case FILTRAR:
                 try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    rs = ps.executeQuery();
                    int numero=0;
                                     
                    if(rs.first()){
                        for(int i=0; i<tblResultadoCheque.getItems().size(); i++){
                            if(colNoChequeBuscado.getCellData(i) == noCheque){
                                tblResultadoCheque.getSelectionModel().select(i);
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
                        tipoOperacionBusquedaCheques = Operacion.CANCELAR;
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
                    tipoOperacionBusquedaCheques = Operacion.CANCELAR;
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
                        tipoOperacionBusquedaCheques = Operacion.CANCELAR;
                        cargarChequesBuscadas();
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL CARGAR SUS REGISTROS");
                        noti.text("HA OCURRIDO UN ERROR AL MOMENTO DE CARGAR TODOS LOS REGISTROS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionBusquedaCheques = Operacion.CANCELAR;

                    }
                }else{  
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HAN PODIDO CARGAR TODOS LOS REGISTROS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionBusquedaCheques = Operacion.CANCELAR;
                }
                break;
            case BUSCAR:
                try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    rs = ps.executeQuery();
                    int numero=0;
                                        
                    if(rs.first()){
                        for(int i=0; i<tblResultadoCheque.getItems().size(); i++){
                            if(colNoChequeBuscado.getCellData(i) == noCheque){
                                tblResultadoCheque.getSelectionModel().select(i);
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
                        tipoOperacionBusquedaCheques = Operacion.CANCELAR;
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
                    tipoOperacionBusquedaCheques = Operacion.CANCELAR;
                }
                break;     
        }
    }
    
     /* Proceso para buscar cheque id*/
    @FXML
     public void buscarPorId(){
      if(txtBusquedaCodigoChe.getValue().equals("")){
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE BUSQUEDA ESTA VACÍO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
        }else{
            tipoOperacionBusquedaCheques = Operacion.BUSCAR;
            cargarChequesBuscadasPorId();
        }  
    }           
    
        @FXML
    private void seleccionarElementosChequeBuscadas(MouseEvent event) {
        int index = tblResultadoCheque.getSelectionModel().getSelectedIndex();
        try{

            txtBusquedaCodigoChe.setValue(colNoChequeBuscado.getCellData(index).toString());
                        
        }catch(Exception ex){
              ex.printStackTrace();
        }
    }
    
    private void btnBuscarCheque(MouseEvent event) {
        buscarPorId();
    }
    
    @FXML
    private void btnCargarCheque(MouseEvent event) {
        cargarChequesBuscadas();
    }
        
    
    // PREFERENCES PARA MAPEAR DATOS EN IMPRESIÓN.
    
    
     @FXML
    private void preferencesCargar(Event event) {
        fechaX.setText(menu.PfechaX.get("valor1", "root"));
        fechaY.setText(menu.PfechaY.get("valor2", "root"));
        
        ordenX.setText(menu.PordenX.get("valor3", "root"));
        OrdenY.setText(menu.PordenY.get("valor4", "root"));
        
        totalX.setText(menu.PTotalX.get("valor5", "root"));
        totalY.setText(menu.PTotalY.get("valor6", "root"));
        
        letrasX.setText(menu.PLetrasX.get("valor7", "root"));
        letrasY.setText(menu.PLetrasY.get("valor8", "root"));   
        
        CNoChequeX.setText(menu.comprobante.get("nochequeX", "root"));
        CNoChequeY.setText(menu.comprobante.get("nochequeY", "root"));
        
        CfechaX.setText(menu.comprobante.get("fechaX", "root"));
        CfechaY.setText(menu.comprobante.get("fechaY", "root"));
        
        CordenX.setText(menu.comprobante.get("ordenX", "root"));
        CordenY.setText(menu.comprobante.get("ordenY", "root"));
        
        CtotalX.setText(menu.comprobante.get("totalX", "root"));
        CtotalY.setText(menu.comprobante.get("totalY", "root"));
        
        CletrasX.setText(menu.comprobante.get("letrasX","root"));
        CletrasY.setText(menu.comprobante.get("letrasY","root"));
        
        descX.setText(menu.comprobante.get("descX","root"));
        descY.setText(menu.comprobante.get("descY","root"));
        
    }
    
    
     @FXML
    private void guardarPreferences(MouseEvent event) {
        menu.PfechaX.put("valor1", fechaX.getText());
        menu.PfechaY.put("valor2", fechaY.getText());
        
        menu.PordenX.put("valo3", ordenX.getText());
        menu.PordenY.put("valor4", OrdenY.getText());
        
        menu.PTotalX.put("valor5", totalX.getText());
        menu.PTotalY.put("valor6", totalY.getText());
        
        menu.PLetrasX.put("valor7", letrasX.getText());
        menu.PLetrasY.put("valor8", letrasY.getText());
    }

    @FXML
    private void guardarPreferencesComprobante(MouseEvent event) {
    
        menu.comprobante.put("nochequeX",CNoChequeX.getText() );
        menu.comprobante.put("nochequeY",CNoChequeY.getText() );
        
        menu.comprobante.put("fechaX",CfechaX.getText() );
        menu.comprobante.put("fechaY",CfechaY.getText() );
        
         menu.comprobante.put("ordenX",CordenX.getText() );
        menu.comprobante.put("ordenY",CordenY.getText() );
        
         menu.comprobante.put("totalX",CtotalX.getText() );
        menu.comprobante.put("totalY",CtotalY.getText() );
        
         menu.comprobante.put("letrasX",CletrasX.getText() );
        menu.comprobante.put("letrasY",CletrasY.getText() );
        
         menu.comprobante.put("descX",descX.getText() );
        menu.comprobante.put("descY",descY.getText() );
    }
    
            double xOffset = 0;
    double yOffset = 0;
    
        @FXML
    private void eliminarCheque(MouseEvent event) throws IOException {
        
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("org/moduloFacturacion/view/eliminarCheque.fxml"));
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
	hilo.start();
    }
    
    
    Runnable runnable = new Runnable() {
    @Override
	public void run() {
            while (true) {
		try {
                    Thread.sleep(1000);
                    cargarChequesBuscadas2();
		} catch (InterruptedException e) {
                    e.printStackTrace();
					}
				}
			}
        
		};

        ObservableList<String> listaProv;

   @FXML
   public void cmbProv(){
        ArrayList<String> lista = new ArrayList();
        
        String sql ="{call SpListarProveedores()}";      
        int x=0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                     lista.add(x, rs.getString("proveedorNombre"));
                 x++;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
       listaProv = FXCollections.observableList(lista);
       pagoOrden.setItems(listaProv);
       new AutoCompleteComboBoxListener(pagoOrden);
   }
    
    
}
