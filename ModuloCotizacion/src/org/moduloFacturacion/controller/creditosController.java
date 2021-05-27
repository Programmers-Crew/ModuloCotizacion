package org.moduloFacturacion.controller;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.moduloFacturacion.bean.AutoCompleteComboBoxListener;
import org.moduloFacturacion.bean.CambioScene;
import org.moduloFacturacion.bean.Creditos;
import org.moduloFacturacion.bean.CreditosBuscados;
import org.moduloFacturacion.bean.ValidarStyle;
import org.moduloFacturacion.db.Conexion;

public class creditosController implements Initializable {
    
    Image warning= new Image("org/moduloFacturacion/img/warning.png");
    @FXML
    private Pane buttonInicio;
    @FXML
    private AnchorPane anchor2;
    @FXML
    private TableView<Creditos> tableProductos;
    @FXML
    private TableColumn<Creditos, Date > colFechaInicio;
    @FXML
    private TableColumn<Creditos, Date> colFechaFinal;
    @FXML
    private TableColumn<Creditos, Integer> colDiasRestantes;
    @FXML
    private TableColumn<Creditos, String> colDescripcion;
    @FXML
    private TableColumn<Creditos, Double> colMonto;
    @FXML
    private TableColumn<Creditos, String> colEstadoCredito;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private ComboBox<String> cmbFiltroCredito;
    @FXML
    private ComboBox<String> cmbBuscar;
    @FXML
    private JFXButton btnPagado;
    private AnchorPane anchor;


    Image imgError = new Image("org/moduloFacturacion/img/error.png");
    Image imgCorrecto= new Image("org/moduloFacturacion/img/correcto.png");
    
    CambioScene cambioScene = new CambioScene();
    
    String codigo = "";
    
    @FXML
    private AnchorPane anchorCreditos;
    @FXML
    private TableColumn<Creditos, String> noFacColumn;
    
    MenuPrincipalContoller menu = new MenuPrincipalContoller();
    ValidarStyle validar = new ValidarStyle();
    //BUSQUEDA CREDITOS
    @FXML
    private TableView<CreditosBuscados> tableProductosBuscados;
    @FXML
    private TableColumn<CreditosBuscados, String> colProducto;
    @FXML
    private TableColumn<CreditosBuscados, Double> colCantidad;
    @FXML
    private TableColumn<CreditosBuscados, Double> colParcial;
    @FXML
    private JFXTextField txtProveedor;
    @FXML
    private JFXTextField colNitProveedor;
    @FXML
    private JFXButton btnCargarDatos;
    @FXML
    private JFXButton btnEliminarCredito;
    
    public enum Operacion{AGREGAR,GUARDAR,ELIMINAR,BUSCAR,ACTUALIZAR,CANCELAR,FILTRAR,NINGUNO};
    public Operacion tipoOperacion= Operacion.NINGUNO;
    public Operacion cancelar = Operacion.NINGUNO;

    ObservableList<String> listaFiltroCredito;
    ObservableList<Creditos> listaCreditos;
    ObservableList<CreditosBuscados> listaCreditosBuscados;
    ObservableList<String> listaCmbCodigoCreditos;
    ObservableList<String> listaCmbBuscar;
    ObservableList<String> listaCmbFiltro;
    ObservableList<String> listaCmbFechaFinal;

    Integer FacBuscadaNo = 0;

    @FXML
    private JFXDatePicker txtFechaInicio;
    @FXML
    private JFXDatePicker txtFechaFinal;
    @FXML
    private JFXButton btnFiltrar;
    
     //EVENTOS DE LA VISTA DE PROVEEDORES

    public ObservableList<Creditos> getCreditos(){
        ArrayList<Creditos> lista = new ArrayList();
        ArrayList<String> comboCodigo = new ArrayList();
        String sql = "{call SpListarCreditos()}";
        int x=0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new Creditos(
                            rs.getDate("creaditoFechaInicio"),
                            rs.getDate("creditoFechaFinal"),
                            rs.getInt("creditoDiasRestantes"),
                            rs.getString("creditoDesc"),
                            rs.getDouble("creditoMonto"),
                            rs.getString("estadoCreditoDesc"),
                            rs.getString("noFactura")
                ));
                comboCodigo.add(x, rs.getString("noFactura"));
                x++;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        listaCmbCodigoCreditos = FXCollections.observableList(comboCodigo);
        cmbBuscar.setItems(listaCmbCodigoCreditos);

        return listaCreditos = FXCollections.observableList(lista);
    }

    public void cargarCreditos(){
        tableProductos.setItems(getCreditos());
        
        noFacColumn.setCellValueFactory(new PropertyValueFactory("noFactura"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory("creaditoFechaInicio"));
        colFechaFinal.setCellValueFactory(new PropertyValueFactory("creditoFechaFinal"));  
        colDiasRestantes.setCellValueFactory(new PropertyValueFactory("creditoDiasRestantes"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory("creditoDesc"));
        colMonto.setCellValueFactory(new PropertyValueFactory("creditoMonto"));
        colEstadoCredito.setCellValueFactory(new PropertyValueFactory("estadoCreditoDesc"));
        new AutoCompleteComboBoxListener(cmbBuscar);
    }  

        public ObservableList<CreditosBuscados> getCreditosBuscados(){
        ArrayList<CreditosBuscados> lista = new ArrayList();
        String sql = "{call SpListarCreditoDetalle('"+FacBuscadaNo+"')}"; 
        int x=0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new CreditosBuscados(
                            rs.getString("productoDesc"),
                            rs.getDouble("cantidadDetalle"),
                            rs.getDouble("totalParcialDetalle")
                ));
                x++;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return listaCreditosBuscados = FXCollections.observableList(lista);
    }
    
        public void cargarCreditosBuscados(){
        tableProductosBuscados.setItems(getCreditosBuscados());
        
        colProducto.setCellValueFactory(new PropertyValueFactory("productoDesc"));
        colCantidad.setCellValueFactory(new PropertyValueFactory("cantidadDetalle"));
        colParcial.setCellValueFactory(new PropertyValueFactory("totalParcialDetalle"));
        
    }  
        
         public void buscarProducto(){
           String sql = "{call SpListarCreditoDetalle('"+FacBuscadaNo+"')}"; 
            PreparedStatement ps;
            ResultSet rs;
            
            try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    rs = ps.executeQuery();
                    int numero=0;
                    
                    while(rs.next()){
                        txtProveedor.setText(rs.getString("proveedorNombre"));
                        colNitProveedor.setText(rs.getString("proveedorNit"));
                        
                    }
                    cargarCreditosBuscados();

                }catch(SQLException ex){
                    ex.printStackTrace();
                }
        } 
         
    /* FILTRAR POR FECHAS*/
        public ObservableList<Creditos> getFiltroCreditos(){
        ArrayList<Creditos> lista = new ArrayList();
        ArrayList<String> comboCodigo = new ArrayList();
        
        String  sql = "{call SpFiltrarCredito('"+txtFechaInicio.getValue()+"','"+txtFechaFinal.getValue()+"')}";
                    int x=0;
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new Creditos(
                            rs.getDate("creaditoFechaInicio"),
                            rs.getDate("creditoFechaFinal"),
                            rs.getInt("creditoDiasRestantes"),
                            rs.getString("creditoDesc"),
                            rs.getDouble("creditoMonto"),
                            rs.getString("estadoCreditoDesc"),
                            rs.getString("noFactura")
                ));
                comboCodigo.add(x, rs.getString("idCredito"));
                x++;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        listaCmbCodigoCreditos = FXCollections.observableList(comboCodigo);
        cmbBuscar.setItems(listaCmbCodigoCreditos);
            System.out.println("FILTRO");
            System.out.println(sql);

        return listaCreditos = FXCollections.observableList(lista);
    }
      
    public void cargarCreditosFiltrados(){
        tableProductos.setItems(getFiltroCreditos());
        
        colFechaInicio.setCellValueFactory(new PropertyValueFactory("creaditoFechaInicio"));
        colFechaFinal.setCellValueFactory(new PropertyValueFactory("creditoFechaFinal"));  
        colDiasRestantes.setCellValueFactory(new PropertyValueFactory("creditoDiasRestantes"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory("creditoDesc"));
        colMonto.setCellValueFactory(new PropertyValueFactory("creditoMonto"));
        colEstadoCredito.setCellValueFactory(new PropertyValueFactory("estadoCreditoDesc"));
        noFacColumn.setCellValueFactory(new PropertyValueFactory("noFactura"));
    }  
    
    
    /* FILTRO DE  PROVEEDORES*/
    public ObservableList<Creditos> getFiltroCreditospProveedor(){
        ArrayList<Creditos> lista = new ArrayList();
        
         String sql = "{call SpFiltrarCreditoEmpresa('"+txtFechaInicio.getValue()+"','"+txtFechaFinal.getValue()+"','"+cmbBuscar.getValue()+"')}";
                    int x=0;  
            try{
                PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    lista.add(new Creditos(
                                rs.getDate("creditoFechaFinal"),
                                rs.getDate("creaditoFechaInicio"),
                                rs.getInt("creditoDiasRestantes"),
                                rs.getString("creditoDesc"),
                                rs.getDouble("creditoMonto"),
                                rs.getString("estadoCreditoDesc"),
                                rs.getString("noFactura")
                    ));
                    x++;
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
            System.out.println("FILTRO EMPRESA");
            System.out.println(sql);
        

        return listaCreditos = FXCollections.observableList(lista);
    }
    
        public void cargarCreditosFiltradosProveedores(){
        tableProductos.setItems(getFiltroCreditospProveedor());
        
        colFechaInicio.setCellValueFactory(new PropertyValueFactory("creaditoFechaInicio"));
        colFechaFinal.setCellValueFactory(new PropertyValueFactory("creditoFechaFinal"));  
        colDiasRestantes.setCellValueFactory(new PropertyValueFactory("creditoDiasRestantes"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory("creditoDesc"));
        colMonto.setCellValueFactory(new PropertyValueFactory("creditoMonto"));
        colEstadoCredito.setCellValueFactory(new PropertyValueFactory("estadoCreditoDesc"));
        

        new AutoCompleteComboBoxListener(cmbBuscar);
    }  
    
    public void cargarCombo(){
        ArrayList<String>lista = new ArrayList();
        
        lista.add(0,"CÓDIGO");
        lista.add(1,"PROVEEDOR");
        
        listaCmbFiltro = FXCollections.observableList(lista);
        
        cmbFiltroCredito.setItems(listaCmbFiltro);
    }
    
    @FXML
    public void buscarCreditosFiltrados(){
        try{
            if(cmbBuscar.getValue().equals("")){
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
                    tipoOperacion = Operacion.FILTRAR;
                    cargarCreditosFiltrados();
                    System.out.println("BUSCAR VACIO");
                }  
            }else{
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
                      tipoOperacion = Operacion.FILTRAR;
                      cargarCreditosFiltradosProveedores();
                      System.out.println("BUSCAR LLENO");
                }  
            }          
        try{
        }catch(Exception e){
            e.printStackTrace();
        }
        }catch(NullPointerException ex){
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void limpiarBuscar(MouseEvent event) {
    }
    
    @FXML
    private void comboFiltro(ActionEvent event) {
        btnBuscar.setDisable(false);
        ArrayList<String> lista = new ArrayList();
        
        String sql = "";
        if(cmbFiltroCredito.getValue().equals("CÓDIGO")){
              sql ="{call SpListarComboFiltro()}";
            }else if(cmbFiltroCredito.getValue().equals("PROVEEDOR")){
              sql ="{call SpListarComboFiltroProveedor()}";      
        }
        System.out.println(sql);
        int x=0;
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                 if(cmbFiltroCredito.getValue().equals("CÓDIGO")){
                     lista.add(x, rs.getString("noFactura"));
                     
                }else if(cmbFiltroCredito.getValue().equals("PROVEEDOR")){
                    lista.add(x, rs.getString("proveedorNombre"));
                }
                 x++;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
       listaCmbBuscar = FXCollections.observableList(lista);
       cmbBuscar.setItems(listaCmbBuscar);
       new AutoCompleteComboBoxListener(cmbBuscar);
    }
    
    @FXML
    private void seleccionarElementos(MouseEvent event) {
        int index = tableProductos.getSelectionModel().getSelectedIndex();

        FacBuscadaNo = Integer.parseInt(noFacColumn.getCellData(index).toString());
        buscarProducto();
    }
    
      @FXML
    private void regresar(MouseEvent event) throws IOException {
        String menu1 = "org/moduloFacturacion/view/menuPrincipal.fxml";
        cambioScene.Cambio(menu1, (Stage) anchorCreditos.getScene().getWindow());
        
    }
     Runnable runnable = new Runnable() {
         
    @Override 
	public void run() {
                    LocalDate fechaActual = LocalDate.now();

            String sql3="{call SpRestarDias2('"+fechaActual+"')}";
            while (true) { 
		try { 
                    Thread.sleep(1000); 
                    try{
                    PreparedStatement ps3 = Conexion.getIntance().getConexion().prepareCall(sql3);
                    ps3.execute();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    
                   
		} catch (InterruptedException e) { 
                    e.printStackTrace(); 
					} 
				} 
			} 
         
		}; 
     
    public void anuncio(){
        LocalDate fechaActual = LocalDate.now();
        
        String sql3="{call SpRestarDias2('"+fechaActual+"')}";
        
        Integer dias = 0;
        Integer estado = 0;

        try{
            PreparedStatement ps3 = Conexion.getIntance().getConexion().prepareCall(sql3);
            ps3.execute();
            
            
                estado = 3;
                String sql2="{call SpValidarCredito('"+estado+"')}";
                PreparedStatement ps2 = Conexion.getIntance().getConexion().prepareCall(sql2);
                ps2.execute();
                
                
                
        ArrayList<String> lista = new ArrayList();
        String sql = "{call SpBucarCreditosVencidos()}";                        
        int x=0;
        String id = "";
            try{
                PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x, rs.getString("idCredito"));
                id = rs.getString("idCredito");
                 x++;
            }
            
            if(id != ""){
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("CREDITOS PENDIENTES");
                noti.text("Por favor verifica tus creditos");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
                
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       cargarCreditos();
       cargarCombo();
       validar.validarView(menu.prefs.get("dark", "root"), anchorCreditos);
       cmbFiltroCredito.setValue("");
       cmbBuscar.setValue("");
       
    }    
    

        public void accionCreditos(String sql){
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
                        cargarCreditos();
                        tipoOperacion = Operacion.CANCELAR;
                        
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
                }
                break;

                
            case FILTRAR:
                 try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    rs = ps.executeQuery();
                    int numero=0;
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
                }
                break;
        }
    }
    
    
    public void buscar(){
        if(cmbBuscar.getValue().equals("")){
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR");
            noti.text("El CAMPO DE CÓDIGO ESTA VACÍO");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
        }else{
            if(cmbFiltroCredito.getValue().equals("CÓDIGO")){
                tipoOperacion = Operacion.BUSCAR;
                    String sql = "{ call SpBuscarCredito('"+cmbBuscar.getValue()+"')}";
                    accionCreditos(sql);
            }else if(cmbFiltroCredito.getValue().equals("PROVEEDOR")){
                    tipoOperacion = Operacion.BUSCAR;
                    String sql = "{ call SpBuscarCreditoProveedor('"+cmbBuscar.getValue()+"')}";
                    accionCreditos(sql);
            }
        }
    }
    
    public void marcarPagado(){
        String sql = "";
        int index = tableProductos.getSelectionModel().getSelectedIndex();
        String txtCodigoCredito = listaCreditos.get(index).getNoFactura().toString();
         
        sql = "{call SpMarcarPagado('"+txtCodigoCredito+"')}";
        System.out.println(sql);
        Notifications noti = Notifications.create();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType buttonTypeSi = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");
        
        alert.setTitle("MARCAR COMO PAGADO");
        alert.setHeaderText("MARCAR COMO PAGADO");
        alert.setContentText("¿Está seguro que desea marcar como pagado este credito?");
                
        alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.get() == buttonTypeSi ){
            try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
              noti.graphic(new ImageView(imgCorrecto));
              noti.title("OPERACIÓN EXITOSA");
              noti.text("SE HA MARCADO COMO PAGADO");
              noti.position(Pos.BOTTOM_RIGHT);
              noti.hideAfter(Duration.seconds(4));
              noti.darkStyle();
              noti.show();
              cargarCreditos();
            }catch(SQLException ex){
                ex.printStackTrace();
               noti.graphic(new ImageView(imgCorrecto));
              noti.title("OPERACIÓN EXITOSA");
              noti.text("ERROR AL MARCAR, INTENTA DE NUEVO");
              noti.position(Pos.BOTTOM_RIGHT);
              noti.hideAfter(Duration.seconds(4));
              noti.darkStyle();
              noti.show();
            }
        }else{
            noti.graphic(new ImageView(imgError));
            noti.title("OPERACIÓN CANCELADA");
            noti.text("NO SE HA MARCARCADO COMO PAGADO");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
        }
    }
    
    @FXML
    private void btnMarcarPagado(MouseEvent event) {
        marcarPagado();
    }
    
@FXML
    private void btnEliminar(MouseEvent event) {
         if(tipoOperacion == Operacion.GUARDAR){
            tipoOperacion = Operacion.CANCELAR;
        }else{
            Creditos eliminarCredito = new Creditos();

            int index = tableProductos.getSelectionModel().getSelectedIndex();
             String txtCodigoCredito = listaCreditos.get(index).getNoFactura().toString();
            
            String sql = "{call SpEliminatCreditos('"+txtCodigoCredito+"')}";
            tipoOperacion = Operacion.ELIMINAR;
            accionCreditos(sql);
        }
    }
    
    
    @FXML
    private void btnBuscar(MouseEvent event) {
        buscar();
    }

    @FXML
    private void cmbBuscar(ActionEvent event) {
        
            if(cmbFiltroCredito.getValue().equals("CÓDIGO")){
                cargarCreditosPorCodigo();
            }else if(cmbFiltroCredito.getValue().equals("PROVEEDOR")){
                cargarCreditosPorProveedor();
            }
    }

    public ObservableList<Creditos> getCreditosPorCodigo(){
        ArrayList<Creditos> lista = new ArrayList();
        ArrayList<String> comboCodigo = new ArrayList();
        String sql = "{call SpBuscarCredito('"+cmbBuscar.getValue()+"')}";
        int x=0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new Creditos(
                            rs.getDate("creaditoFechaInicio"),
                            rs.getDate("creditoFechaFinal"),
                            rs.getInt("creditoDiasRestantes"),
                            rs.getString("creditoDesc"),
                            rs.getDouble("creditoMonto"),
                            rs.getString("estadoCreditoDesc"),
                            rs.getString("noFactura")
                ));
                comboCodigo.add(x, rs.getString("noFactura"));
                x++;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        listaCmbCodigoCreditos = FXCollections.observableList(comboCodigo);
        cmbBuscar.setItems(listaCmbCodigoCreditos);

        return listaCreditos = FXCollections.observableList(lista);
    }

    public void cargarCreditosPorCodigo(){
        tableProductos.setItems(getCreditosPorCodigo());
        
        noFacColumn.setCellValueFactory(new PropertyValueFactory("noFactura"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory("creaditoFechaInicio"));
        colFechaFinal.setCellValueFactory(new PropertyValueFactory("creditoFechaFinal"));  
        colDiasRestantes.setCellValueFactory(new PropertyValueFactory("creditoDiasRestantes"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory("creditoDesc"));
        colMonto.setCellValueFactory(new PropertyValueFactory("creditoMonto"));
        colEstadoCredito.setCellValueFactory(new PropertyValueFactory("estadoCreditoDesc"));
    }  
    
    public ObservableList<Creditos> getCreditosPorProveedor(){
        ArrayList<Creditos> lista = new ArrayList();
        ArrayList<String> comboCodigo = new ArrayList();
        String sql = "{call SpBuscarCreditoProveedor('"+cmbBuscar.getValue()+"')}";
        int x=0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new Creditos(
                            rs.getDate("creaditoFechaInicio"),
                            rs.getDate("creditoFechaFinal"),
                            rs.getInt("creditoDiasRestantes"),
                            rs.getString("creditoDesc"),
                            rs.getDouble("creditoMonto"),
                            rs.getString("estadoCreditoDesc"),
                            rs.getString("noFactura")
                ));
                comboCodigo.add(x, rs.getString("noFactura"));
                x++;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        listaCmbCodigoCreditos = FXCollections.observableList(comboCodigo);
        cmbBuscar.setItems(listaCmbCodigoCreditos);

        return listaCreditos = FXCollections.observableList(lista);
    }

    public void cargarCreditosPorProveedor(){
        tableProductos.setItems(getCreditosPorProveedor());
        
        noFacColumn.setCellValueFactory(new PropertyValueFactory("noFactura"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory("creaditoFechaInicio"));
        colFechaFinal.setCellValueFactory(new PropertyValueFactory("creditoFechaFinal"));  
        colDiasRestantes.setCellValueFactory(new PropertyValueFactory("creditoDiasRestantes"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory("creditoDesc"));
        colMonto.setCellValueFactory(new PropertyValueFactory("creditoMonto"));
        colEstadoCredito.setCellValueFactory(new PropertyValueFactory("estadoCreditoDesc"));
    }  
    
    @FXML
    private void atajosProductos(KeyEvent event) {
    }


  public void imprimirReporteCreditos(){
            try{
                Map parametros = new HashMap();

                 String fechaInicio = txtFechaInicio.getValue().toString();
                 String fechaFinal = txtFechaFinal.getValue().toString();

                
                parametros.put("fechaInicio", "'"+fechaInicio+"'");
                parametros.put("fechaFinal", "'"+fechaFinal+"'");

                 org.moduloFacturacion.report.GenerarReporte.mostrarReporte("ReporteCredito.jasper", "REPORTE CREDITO", parametros);
                

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
  
    public void imprimirReporteCreditosEmpresas(){
            try{
                Map parametros = new HashMap();

                 String fechaInicio = txtFechaInicio.getValue().toString();
                 String fechaFinal = txtFechaFinal.getValue().toString();
                 String proveedor = cmbBuscar.getValue();


                
                parametros.put("fechaInicio", "'"+fechaInicio+"'");
                parametros.put("fechaFinal", "'"+fechaFinal+"'");
                parametros.put("proveedor", "'"+proveedor+"'");


                 org.moduloFacturacion.report.GenerarReporte.mostrarReporte("ReporteCreditoEmpresa.jasper", "REPORTE CREDITO", parametros);
                

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
    public void generarReporteCredito(){
        

            if(cmbBuscar.getValue().equals("")){
                       imprimirReporteCreditos(); 
            }else{
                imprimirReporteCreditosEmpresas();
            
        }
    }
    
    
    @FXML
    private void cmbCargar(ActionEvent event) {
        cargarCreditos();
    }
    
}
