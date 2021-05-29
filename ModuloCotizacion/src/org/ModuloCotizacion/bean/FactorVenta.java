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
public class FactorVenta {
    private int factorVentaId;
    private String factorVentaDesc;
    private double factorVentaDescuento;

    public FactorVenta(int factorVentaId, String factorVentaDesc, double factorVentaDescuento) {
        this.factorVentaId = factorVentaId;
        this.factorVentaDesc = factorVentaDesc;
        this.factorVentaDescuento = factorVentaDescuento;
    }

    public FactorVenta() {
    }
    
    public int getFactorVentaId() {
        return factorVentaId;
    }

    public void setFactorVentaId(int factorVentaId) {
        this.factorVentaId = factorVentaId;
    }

    public String getFactorVentaDesc() {
        return factorVentaDesc;
    }

    public void setFactorVentaDesc(String factorVentaDesc) {
        this.factorVentaDesc = factorVentaDesc;
    }

    public double getFactorVentaDescuento() {
        return factorVentaDescuento;
    }

    public void setFactorVentaDescuento(double factorVentaDescuento) {
        this.factorVentaDescuento = factorVentaDescuento;
    }

    @Override
    public String toString() {
        return "FactorVenta{" + "factorVentaId=" + factorVentaId + ", factorVentaDesc=" + factorVentaDesc + ", factorVentaDescuento=" + factorVentaDescuento + '}';
    }
    
    
    
    
    
}
