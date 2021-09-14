package org.ModuloCotizacion.system;


import javafx.application.Application; 
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.ModuloCotizacion.controller.ActivationViewController;
import org.ModuloCotizacion.controller.LoginViewController;
import org.ModuloCotizacion.controller.MenuPrincipalContoller;
import org.ModuloCotizacion.db.Conexion;

public class MainApp extends Application {
    MenuPrincipalContoller menu = new MenuPrincipalContoller();
    LoginViewController login = new LoginViewController();
    ActivationViewController pc = new ActivationViewController();
   
    
    
    @Override
    public void start(Stage stage) throws Exception {
        Conexion c = new Conexion();
        if(c.getConexion() != null){
            System.out.println("CONEXIO CORRECTA 1");
        }else{
            System.out.println("CONEXION INCORRECTA");
            Stage st = new Stage();
            Parent root1 = FXMLLoader.load(getClass().getClassLoader().getResource("org/ModuloCotizacion/view/ERRORCONEXION.fxml"));
            Scene scene1 = new Scene(root1);
            st.setTitle("PROGRAMMERS BILLING");
            st.getIcons().add(new Image(getClass().getResource("/org/ModuloCotizacion/img/grupoAlcon.png").toExternalForm()));
            st.setWidth(500);
            st.setHeight(300);
            st.setScene(scene1);
            st.setAlwaysOnTop(true);
            
            st.show();
        }        
        Parent root;
       if(pc.prefsValidacion.get("program", "root").equals("true")){
           if(menu.prefsUsuario1.get("validar", "root").equals("recordar")){    
            
            root = FXMLLoader.load(getClass().getClassLoader().getResource("org/ModuloCotizacion/view/menuPrincipal.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("PROGRAMMERS BILLING");
            stage.getIcons().add(new Image(getClass().getResource("/org/ModuloCotizacion/img/grupoAlcon.jpeg").toExternalForm()));
            int primaryMon=0;
            Screen primary = Screen.getPrimary();
            for(int i = 0; i < Screen.getScreens().size(); i++){
                if(Screen.getScreens().get(i).equals(primary)){
                    primaryMon = i;
                    System.out.println("primary: " + i);
                    break;
                }
            }
            stage.setMinWidth(1500);
            stage.setMinHeight(800);
            stage.setScene(scene);
            stage.show();
            
          
        }else{
            root = FXMLLoader.load(getClass().getClassLoader().getResource("org/ModuloCotizacion/view/LoginView.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("LOGIN PROGRAMMERS BILLING");
            stage.getIcons().add(new Image(getClass().getResource("/org/ModuloCotizacion/img/grupoAlcon.jpeg").toExternalForm()));
            stage.setWidth(668);
            stage.setHeight(520);
            stage.setScene(scene);     
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        }
       }else{
            root = FXMLLoader.load(getClass().getClassLoader().getResource("org/ModuloCotizacion/view/ActivationView.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("ACTIVACIÓN PROGRAMMERS BILLING");
            stage.getIcons().add(new Image(getClass().getResource("/org/ModuloCotizacion/img/grupoAlcon.jpeg").toExternalForm()));
            stage.setWidth(600);
            stage.setHeight(420);
            stage.setScene(scene);     
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
       }
       
    }
    
     public static void main(String[] args) {
        launch(args);
       

    }
    
}
