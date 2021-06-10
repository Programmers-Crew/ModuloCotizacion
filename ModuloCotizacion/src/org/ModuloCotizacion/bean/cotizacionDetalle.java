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
public class cotizacionDetalle {
    int detalleId;
    double cotizacionCantida;
    String cotizacionDesc;
    double cotizacionAlto;
    double cotizacionLargo;
    double cotizacionAncho;
    double cotizacionPrecioU;
    double cotizacionTotalParcial;

    public cotizacionDetalle() {
    }

    public cotizacionDetalle(int detalleId, double cotizacionCantida, String cotizacionDesc, double cotizacionAlto, double cotizacionLargo, double cotizacionAncho, double cotizacionPrecioU, double cotizacionTotalParcial) {
        this.detalleId = detalleId;
        this.cotizacionCantida = cotizacionCantida;
        this.cotizacionDesc = cotizacionDesc;
        this.cotizacionAlto = cotizacionAlto;
        this.cotizacionLargo = cotizacionLargo;
        this.cotizacionAncho = cotizacionAncho;
        this.cotizacionPrecioU = cotizacionPrecioU;
        this.cotizacionTotalParcial = cotizacionTotalParcial;
    }

    public int getDetalleId() {
        return detalleId;
    }

    public void setDetalleId(int detalleId) {
        this.detalleId = detalleId;
    }

    public double getCotizacionCantida() {
        return cotizacionCantida;
    }

    public void setCotizacionCantida(double cotizacionCantida) {
        this.cotizacionCantida = cotizacionCantida;
    }

    public String getCotizacionDesc() {
        return cotizacionDesc;
    }

    public void setCotizacionDesc(String cotizacionDesc) {
        this.cotizacionDesc = cotizacionDesc;
    }

    public double getCotizacionAlto() {
        return cotizacionAlto;
    }

    public void setCotizacionAlto(double cotizacionAlto) {
        this.cotizacionAlto = cotizacionAlto;
    }

    public double getCotizacionLargo() {
        return cotizacionLargo;
    }

    public void setCotizacionLargo(double cotizacionLargo) {
        this.cotizacionLargo = cotizacionLargo;
    }

    public double getCotizacionAncho() {
        return cotizacionAncho;
    }

    public void setCotizacionAncho(double cotizacionAncho) {
        this.cotizacionAncho = cotizacionAncho;
    }

    public double getCotizacionPrecioU() {
        return cotizacionPrecioU;
    }

    public void setCotizacionPrecioU(double cotizacionPrecioU) {
        this.cotizacionPrecioU = cotizacionPrecioU;
    }

    public double getCotizacionTotalParcial() {
        return cotizacionTotalParcial;
    }

    public void setCotizacionTotalParcial(double cotizacionTotalParcial) {
        this.cotizacionTotalParcial = cotizacionTotalParcial;
    }

    @Override
    public String toString() {
        return "cotizacionDetalle{" + "detalleId=" + detalleId + ", cotizacionCantida=" + cotizacionCantida + ", cotizacionDesc=" + cotizacionDesc + ", cotizacionAlto=" + cotizacionAlto + ", cotizacionLargo=" + cotizacionLargo + ", cotizacionAncho=" + cotizacionAncho + ", cotizacionPrecioU=" + cotizacionPrecioU + ", cotizacionTotalParcial=" + cotizacionTotalParcial + '}';
    }
    
    
           
}
