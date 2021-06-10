package org.ModuloCotizacion.bean;

import java.sql.Date;

public class Cotizaciones {
    private Integer cotizacionId;
    private String nit;
    private String tipoClienteDesc;
    private String cotizacionImg;
    private String usuarioNombre;
    private Date cotizacionFecha;    
    private Double cotizacionDescuento;
    private Double cotizacionDescuentoNeto;
    private Double cotizacionTotal;
    
    

    public Cotizaciones() {
    }

    public Cotizaciones(Integer cotizacionId, String nit, String tipoClienteDesc, String cotizacionImg, String usuarioNombre, Date cotizacionFecha, Double cotizacionDescuento, Double cotizacionDescuentoNeto, Double cotizacionTotal) {
        this.cotizacionId = cotizacionId;
        this.nit = nit;
        this.tipoClienteDesc = tipoClienteDesc;
        this.cotizacionImg = cotizacionImg;
        this.usuarioNombre = usuarioNombre;
        this.cotizacionFecha = cotizacionFecha;
        this.cotizacionDescuento = cotizacionDescuento;
        this.cotizacionDescuentoNeto = cotizacionDescuentoNeto;
        this.cotizacionTotal = cotizacionTotal;
    }

    public Integer getCotizacionId() {
        return cotizacionId;
    }

    public void setCotizacionId(Integer cotizacionId) {
        this.cotizacionId = cotizacionId;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getTipoClienteDesc() {
        return tipoClienteDesc;
    }

    public void setTipoClienteDesc(String tipoClienteDesc) {
        this.tipoClienteDesc = tipoClienteDesc;
    }

    public String getCotizacionImg() {
        return cotizacionImg;
    }

    public void setCotizacionImg(String cotizacionImg) {
        this.cotizacionImg = cotizacionImg;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public Date getCotizacionFecha() {
        return cotizacionFecha;
    }

    public void setCotizacionFecha(Date cotizacionFecha) {
        this.cotizacionFecha = cotizacionFecha;
    }

    public Double getCotizacionDescuento() {
        return cotizacionDescuento;
    }

    public void setCotizacionDescuento(Double cotizacionDescuento) {
        this.cotizacionDescuento = cotizacionDescuento;
    }

    public Double getCotizacionDescuentoNeto() {
        return cotizacionDescuentoNeto;
    }

    public void setCotizacionDescuentoNeto(Double cotizacionDescuentoNeto) {
        this.cotizacionDescuentoNeto = cotizacionDescuentoNeto;
    }

    public Double getCotizacionTotal() {
        return cotizacionTotal;
    }

    public void setCotizacionTotal(Double cotizacionTotal) {
        this.cotizacionTotal = cotizacionTotal;
    }
    


}
