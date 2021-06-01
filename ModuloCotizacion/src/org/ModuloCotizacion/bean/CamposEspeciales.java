/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ModuloCotizacion.bean;

/**
 *
 * @author davis
 */
public class CamposEspeciales {
    int campoId;
    String campoNombre;
    double campoPrecio;

    public CamposEspeciales(int campoId, String campoNombre, double campoPrecio) {
        this.campoId = campoId;
        this.campoNombre = campoNombre;
        this.campoPrecio = campoPrecio;
    }

    public int getCampoId() {
        return campoId;
    }

    public void setCampoId(int campoId) {
        this.campoId = campoId;
    }

    public String getCampoNombre() {
        return campoNombre;
    }

    public void setCampoNombre(String campoNombre) {
        this.campoNombre = campoNombre;
    }

    public double getCampoPrecio() {
        return campoPrecio;
    }

    public void setCampoPrecio(double campoPrecio) {
        this.campoPrecio = campoPrecio;
    }

    public CamposEspeciales() {
    }

    @Override
    public String toString() {
        return "CamposEspeciales{" + "campoId=" + campoId + ", campoNombre=" + campoNombre + ", campoPrecio=" + campoPrecio + '}';
    }
    
    
    
    
}
