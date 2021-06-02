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
public class ModosPago {
    private int modoPagoId;
    private String modoPagoDesc;

    public ModosPago(int modoPagoId, String modoPagoDesc) {
        this.modoPagoId = modoPagoId;
        this.modoPagoDesc = modoPagoDesc;
    }

    public ModosPago() {
    }
    
    

    public int getModoPagoId() {
        return modoPagoId;
    }

    public void setModoPagoId(int modoPagoId) {
        this.modoPagoId = modoPagoId;
    }

    public String getModoPagoDesc() {
        return modoPagoDesc;
    }

    public void setModoPagoDesc(String modoPagoDesc) {
        this.modoPagoDesc = modoPagoDesc;
    }

    @Override
    public String toString() {
        return "ModosPago{" + "modoPagoId=" + modoPagoId + ", modoPagoDesc=" + modoPagoDesc + '}';
    }
    
    
    
    
    
}
