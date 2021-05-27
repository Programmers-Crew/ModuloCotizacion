package org.moduloFacturacion.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
import org.moduloFacturacion.bean.FacturasBuscadas;
import org.moduloFacturacion.bean.ProductoBuscado;
import org.moduloFacturacion.bean.ValidarStyle;
import org.moduloFacturacion.db.Conexion;

public class EliminarFacturaController implements Initializable {

    @FXML
    private AnchorPane anchor;
    @FXML
    private JFXButton btnCerrar;
    @FXML
    private Button btnEliminar;
    @FXML
    private JFXTextField txtNumeroFactura;
    @FXML
    private JFXTextField txtAdministrador;
    @FXML
    private JFXPasswordField txtContrasena;


    Image imgError = new Image("org/moduloFacturacion/img/error.png");
    Image imgCorrecto= new Image("org/moduloFacturacion/img/correcto.png");
    
    MenuPrincipalContoller menu = new MenuPrincipalContoller();
    ValidarStyle validar = new ValidarStyle();
    
    FacturacionViewController fac = new FacturacionViewController();
    
        ObservableList<String> listaNumeroFactura;
        ObservableList<FacturasBuscadas> listaFacturasBuscadas;
        ObservableList<ProductoBuscado> listaProductoBuscado;
    @FXML
    private JFXTextField txtSerieFactura;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validar.validarMenu(menu.prefs.get("dark", "root"), anchor);
        
        txtNumeroFactura.setText(fac.codigoFactura.toString());
        txtAdministrador.setEditable(true);
        txtContrasena.setEditable(true);
        txtNumeroFactura.setEditable(true);
        txtSerieFactura.setEditable(true);
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
                        noti.title("ERROR AL ELIMINAR");
                        noti.text("NO SE HA PODIDO ELIMINAR LA FACTURA");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();

            }else{
                String sql1 = "{call Sp_DevolucionProductos('"+txtSerieFactura.getText()+"','"+txtNumeroFactura.getText()+"')}";
                PreparedStatement ps1 = Conexion.getIntance().getConexion().prepareCall(sql1);
                ps1.execute();
                String sql2 = "{call SpEliminarFac('"+txtSerieFactura.getText()+"','"+txtNumeroFactura.getText()+"')}";
                ps = Conexion.getIntance().getConexion().prepareCall(sql2);
                rs = ps.executeQuery();
                
                 Notifications noti = Notifications.create();
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("FACTURA ELIMINADA");
                        noti.text("SE HA ELIMINADO CON EXITO");
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
    
    @FXML
    private void btnCerrar(MouseEvent event) {
        Stage stage = (Stage) anchor.getScene().getWindow();
        stage.close();
    }


}
