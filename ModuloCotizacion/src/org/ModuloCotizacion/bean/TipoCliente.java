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
public class TipoCliente {
    int tipoClienteId;
    String tipoClienteDesc;
    double tipoClienteDescuento;

    public TipoCliente(int tipoClienteId, String tipoClienteDesc, double tipoClienteDescuento) {
        this.tipoClienteId = tipoClienteId;
        this.tipoClienteDesc = tipoClienteDesc;
        this.tipoClienteDescuento = tipoClienteDescuento;
    }

    public TipoCliente() {
    }

    public int getTipoClienteId() {
        return tipoClienteId;
    }

    public void setTipoClienteId(int tipoClienteId) {
        this.tipoClienteId = tipoClienteId;
    }

    public String getTipoClienteDesc() {
        return tipoClienteDesc;
    }

    public void setTipoClienteDesc(String tipoClienteDesc) {
        this.tipoClienteDesc = tipoClienteDesc;
    }

    public double getTipoClienteDescuento() {
        return tipoClienteDescuento;
    }

    public void setTipoClienteDescuento(double tipoClienteDescuento) {
        this.tipoClienteDescuento = tipoClienteDescuento;
    }

    @Override
    public String toString() {
        return "TipoCliente{" + "tipoClienteId=" + tipoClienteId + ", tipoClienteDesc=" + tipoClienteDesc + ", tipoClienteDescuento=" + tipoClienteDescuento + '}';
    }
    
    
    
}
