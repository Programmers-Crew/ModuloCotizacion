package org.moduloFacturacion.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.moduloFacturacion.bean.AutoCompleteComboBoxListener;
import org.moduloFacturacion.bean.ValidarStyle;
import org.moduloFacturacion.db.Conexion;


public class PromedioProveedoresController implements Initializable {

    @FXML
    private AnchorPane anchor;
    @FXML
    private JFXButton btnCerrar;
    @FXML
    private Button brnPromedio;
    MenuPrincipalContoller menu = new MenuPrincipalContoller();
    ValidarStyle validar = new ValidarStyle();
    @FXML
    private JFXComboBox<String> txtProveedorDos;
    @FXML
    private JFXComboBox<String> txtProveedoresUno;
    @FXML
    private JFXTextField txtPrecioDos;
    @FXML
    private JFXTextField txtPrecioUno;
    @FXML
    private JFXTextField txtPromedio;
    @FXML
    private JFXComboBox<String> txtProductoDos;
    @FXML
    private JFXComboBox<String> txtProductoUno;

    
    ObservableList<String> listaProveedores;
    ObservableList<String> listaProdUno;
    ObservableList<String> listaProdDos;


    Image imgError = new Image("org/moduloFacturacion/img/error.png");
    Image imgCorrecto= new Image("org/moduloFacturacion/img/correcto.png");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarComboProeedores();
        txtProductoDos.setEditable(true);
        txtProductoUno.setEditable(true);
        validar.validarView(menu.prefs.get("dark", "root"), anchor);
        txtProveedoresUno.setEditable(true);
        txtProveedorDos.setEditable(true);
    }    

    @FXML
    private void btnCerrar(MouseEvent event) {
        Stage stage = (Stage) anchor.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void btnPromedio(MouseEvent event) {
        
        try{
            
        Double precioUno = 0.00;
        Double precioDos = 0.00;
        Double promedio = 0.00;
        
        precioUno = Double.parseDouble(txtPrecioUno.getText());
        precioDos = Double.parseDouble(txtPrecioDos.getText());
        
        promedio = (precioUno+precioDos)/2;
        
        txtPromedio.setText(promedio.toString());
        }catch(Exception e){
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR");
            noti.text("El CAMPO DE BUSQUEDA ESTA VACÍO");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
        }      
    }
    
    
    public void llenarComboProeedores(){
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
        listaProveedores = FXCollections.observableList(lista);
        txtProveedoresUno.setItems(listaProveedores);
        txtProveedorDos.setItems(listaProveedores);
        new AutoCompleteComboBoxListener(txtProveedorDos);
        new AutoCompleteComboBoxListener(txtProveedoresUno);
    }
                
    @FXML
    public void buscarProductoUno(){
            ArrayList<String> lista = new ArrayList();
            int x =0;

                try{
                     PreparedStatement sp = Conexion.getIntance().getConexion().prepareCall("{call SpBuscarProductoProv(?)}");
                     sp.setString(1, txtProveedoresUno.getValue());
                     ResultSet resultado = sp.executeQuery(); 
                        while(resultado.next()){
                            lista.add(x, resultado.getString("productoDesc"));
                            x++;
                        }  
                }catch(Exception e){
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE BUSQUEDA ESTA VACÍO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                }
        listaProdUno = FXCollections.observableList(lista);
        txtProductoUno.setItems(listaProdUno);
        new AutoCompleteComboBoxListener(txtProductoUno);

    }
      
    @FXML
    public void buscarProductoDos(){
            ArrayList<String> lista = new ArrayList();
            int x =0;

                try{
                     PreparedStatement sp = Conexion.getIntance().getConexion().prepareCall("{call SpBuscarProductoProv(?)}");
                     sp.setString(1, txtProveedorDos.getValue());
                     ResultSet resultado = sp.executeQuery(); 
                        while(resultado.next()){
                            lista.add(x, resultado.getString("productoDesc"));
                            x++;
                        }  
                }catch(Exception e){
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE BUSQUEDA ESTA VACÍO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                }
        listaProdDos = FXCollections.observableList(lista);
        txtProductoDos.setItems(listaProdDos);
        new AutoCompleteComboBoxListener(txtProductoDos);
    }
    
    
    @FXML
    public void buscarPrecio(){
                try{
                     PreparedStatement sp = Conexion.getIntance().getConexion().prepareCall("{call SpBuscarPrecioProd(?)}");
                     sp.setString(1, txtProductoUno.getValue());
                     ResultSet resultado = sp.executeQuery(); 
                        while(resultado.next()){
                            txtPrecioUno.setText(resultado.getString("productoPrecio"));
                        }  
                }catch(Exception e){
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE BUSQUEDA ESTA VACÍO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
            }
    }
    
        @FXML
    public void buscarPrecioDos(){
                try{
                     PreparedStatement sp = Conexion.getIntance().getConexion().prepareCall("{call SpBuscarPrecioProd(?)}");
                     sp.setString(1, txtProductoDos.getValue());
                     ResultSet resultado = sp.executeQuery(); 
                        while(resultado.next()){
                            txtPrecioDos.setText(resultado.getString("productoPrecio"));
                        }  
                }catch(Exception e){
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE BUSQUEDA ESTA VACÍO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
            }
    }
    
}
