package org.moduloFacturacion.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
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
import org.moduloFacturacion.db.Conexion;


public class RestarInvetarioController implements Initializable {

    @FXML
    private AnchorPane anchor;
    @FXML
    private JFXButton btnCerrar;
    @FXML
    private Button btnEliminar;
    @FXML
    private JFXTextField txtAdministrador;
    @FXML
    private JFXPasswordField txtContrasena;
    @FXML
    private JFXTextField txtCantidad;
    @FXML
    private JFXComboBox<String> cmbProductos;

    
    ObservableList<String> listapProd;
    Image imgError = new Image("org/moduloFacturacion/img/error.png");
    Image imgCorrecto= new Image("org/moduloFacturacion/img/correcto.png");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarComboCategoria();
        txtAdministrador.setText("");
        txtContrasena.setText("");
        cmbProductos.setValue("");
        txtCantidad.setText("");
    }    

    @FXML
    private void btnCerrar(MouseEvent event) {
        Stage stage = (Stage) anchor.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void btnEliminar(MouseEvent event) {
        ArrayList<String> lista = new ArrayList();
        String sql = "{call SpLoginAdmin('"+txtAdministrador.getText()+"','"+txtContrasena.getText()+"')}";                        
        int x=0;
        String usuario = "";
            try{
                PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x, rs.getString("usuarioNombre"));
                usuario = rs.getString("usuarioNombre");
                 x++;
            }
            
            if(usuario.equals("")){
                
                 Notifications noti = Notifications.create();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL RESTAR INVENTARIO");
                        noti.text("NO SE HA PODIDO RESTAR");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();

            }else{
                String sqlRestar = "{call SpRestarProductos('"+cmbProductos.getValue()+"','"+ txtCantidad.getText()+"')}";               
                PreparedStatement psRestar = Conexion.getIntance().getConexion().prepareCall(sqlRestar);
                psRestar.execute();

                
                 Notifications noti = Notifications.create();
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("AJUSTE INVENTARIO");
                        noti.text("SE HA AJUSTADO EL INVENTARIO CON EXITO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        Stage stage = (Stage) anchor.getScene().getWindow();
                        stage.close();
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    
        public void llenarComboCategoria(){
        ArrayList<String> lista = new ArrayList();
        String sql= "{call SpListarProductos()}";
            int x =0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x, rs.getString("productoDesc"));
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
        listapProd = FXCollections.observableList(lista);
        cmbProductos.setItems(listapProd);
        new AutoCompleteComboBoxListener(cmbProductos);

    }
    
}
