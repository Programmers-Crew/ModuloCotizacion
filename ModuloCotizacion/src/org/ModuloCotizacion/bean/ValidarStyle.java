package org.ModuloCotizacion.bean;

import javafx.scene.layout.AnchorPane;

public class ValidarStyle {
    String urlMenuDark = "org/ModuloCotizacion/resource/MenuPrincipalStyleOscuro.css";
    String urlMenu = "org/ModuloCotizacion/resource/MenuPrincipalStyle.css";
    
    String urlViewDark = "org/ModuloCotizacion/resource/moduloStyleOscuro.css";
    String urlView = "org/ModuloCotizacion/resource/moduloStyle.css";
    
    public void validarMenu(String valor, AnchorPane anchor){
        
        if(valor.equals("oscuro")){
            anchor.getStylesheets().clear();
            
            anchor.getStylesheets().add(urlMenuDark);
        }else{
            anchor.getStylesheets().clear();
            anchor.getStylesheets().add(urlMenu);
        }
    
    }
    
    public void validarView(String valor, AnchorPane anchor){
        
        if(valor.equals("oscuro")){
            anchor.getStylesheets().clear();
            
            anchor.getStylesheets().add(urlViewDark);
        }else{
            anchor.getStylesheets().clear();
            anchor.getStylesheets().add(urlView);
        }
    
    }
}
