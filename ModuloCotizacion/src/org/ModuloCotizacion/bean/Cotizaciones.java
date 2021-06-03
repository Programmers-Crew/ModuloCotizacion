package org.ModuloCotizacion.bean;

import java.sql.Date;

public class Cotizaciones {
    private Integer cotizacionId;
    private String cotizacionImg;
    private Date cotizacionFecha;
    private Double cotizacionCantida;        
    private String cotizacionModeloRef;        
    private String cotizacionTipoPrecio;        
    private Double cotizacionAlto;        
    private Double cotizacionAncho;        
    private Double cotizacionLargo;        
    private String cotizacionDesc;
    private Double cotizacionDescuentoNeto;
    private Double cotizacionDescuento;
    private Double cotizacionPrecioU;
    private Double cotizacionTotal;
    private String clienteNombre;
    private String tipoClienteDesc;
    private String usuarioNombre;
    private String productoDesc;
    private String campoNombre;
    private Double campoPrecio;

    public Cotizaciones() {
    }

    public Cotizaciones(Integer cotizacionId, String cotizacionImg, Date cotizacionFecha, Double cotizacionCantida, String cotizacionModeloRef, String cotizacionTipoPrecio, Double cotizacionAlto, Double cotizacionAncho, Double cotizacionLargo, String cotizacionDesc, Double cotizacionDescuentoNeto, Double cotizacionDescuento, Double cotizacionPrecioU, Double cotizacionTotal, String clienteNombre, String tipoClienteDesc, String usuarioNombre, String productoDesc, String campoNombre, Double campoPrecio) {
        this.cotizacionId = cotizacionId;
        this.cotizacionImg = cotizacionImg;
        this.cotizacionFecha = cotizacionFecha;
        this.cotizacionCantida = cotizacionCantida;
        this.cotizacionModeloRef = cotizacionModeloRef;
        this.cotizacionTipoPrecio = cotizacionTipoPrecio;
        this.cotizacionAlto = cotizacionAlto;
        this.cotizacionAncho = cotizacionAncho;
        this.cotizacionLargo = cotizacionLargo;
        this.cotizacionDesc = cotizacionDesc;
        this.cotizacionDescuentoNeto = cotizacionDescuentoNeto;
        this.cotizacionDescuento = cotizacionDescuento;
        this.cotizacionPrecioU = cotizacionPrecioU;
        this.cotizacionTotal = cotizacionTotal;
        this.clienteNombre = clienteNombre;
        this.tipoClienteDesc = tipoClienteDesc;
        this.usuarioNombre = usuarioNombre;
        this.productoDesc = productoDesc;
        this.campoNombre = campoNombre;
        this.campoPrecio = campoPrecio;
    }

    public Double getCotizacionDescuentoNeto() {
        return cotizacionDescuentoNeto;
    }

    public void setCotizacionDescuentoNeto(Double cotizacionDescuentoNeto) {
        this.cotizacionDescuentoNeto = cotizacionDescuentoNeto;
    }



    public Integer getCotizacionId() {
        return cotizacionId;
    }

    public void setCotizacionId(Integer cotizacionId) {
        this.cotizacionId = cotizacionId;
    }

    public String getCotizacionImg() {
        return cotizacionImg;
    }

    public void setCotizacionImg(String cotizacionImg) {
        this.cotizacionImg = cotizacionImg;
    }

    public Date getCotizacionFecha() {
        return cotizacionFecha;
    }

    public void setCotizacionFecha(Date cotizacionFecha) {
        this.cotizacionFecha = cotizacionFecha;
    }

    public Double getCotizacionCantida() {
        return cotizacionCantida;
    }

    public void setCotizacionCantida(Double cotizacionCantida) {
        this.cotizacionCantida = cotizacionCantida;
    }

    public String getCotizacionModeloRef() {
        return cotizacionModeloRef;
    }

    public void setCotizacionModeloRef(String cotizacionModeloRef) {
        this.cotizacionModeloRef = cotizacionModeloRef;
    }

    public String getCotizacionTipoPrecio() {
        return cotizacionTipoPrecio;
    }

    public void setCotizacionTipoPrecio(String cotizacionTipoPrecio) {
        this.cotizacionTipoPrecio = cotizacionTipoPrecio;
    }

    public Double getCotizacionAlto() {
        return cotizacionAlto;
    }

    public void setCotizacionAlto(Double cotizacionAlto) {
        this.cotizacionAlto = cotizacionAlto;
    }

    public Double getCotizacionAncho() {
        return cotizacionAncho;
    }

    public void setCotizacionAncho(Double cotizacionAncho) {
        this.cotizacionAncho = cotizacionAncho;
    }

    public Double getCotizacionLargo() {
        return cotizacionLargo;
    }

    public void setCotizacionLargo(Double cotizacionLargo) {
        this.cotizacionLargo = cotizacionLargo;
    }

    public String getCotizacionDesc() {
        return cotizacionDesc;
    }

    public void setCotizacionDesc(String cotizacionDesc) {
        this.cotizacionDesc = cotizacionDesc;
    }

    public Double getCotizacionDescuento() {
        return cotizacionDescuento;
    }

    public void setCotizacionDescuento(Double cotizacionDescuento) {
        this.cotizacionDescuento = cotizacionDescuento;
    }

    public Double getCotizacionPrecioU() {
        return cotizacionPrecioU;
    }

    public void setCotizacionPrecioU(Double cotizacionPrecioU) {
        this.cotizacionPrecioU = cotizacionPrecioU;
    }

    public Double getCotizacionTotal() {
        return cotizacionTotal;
    }

    public void setCotizacionTotal(Double cotizacionTotal) {
        this.cotizacionTotal = cotizacionTotal;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getTipoClienteDesc() {
        return tipoClienteDesc;
    }

    public void setTipoClienteDesc(String tipoClienteDesc) {
        this.tipoClienteDesc = tipoClienteDesc;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public String getProductoDesc() {
        return productoDesc;
    }

    public void setProductoDesc(String productoDesc) {
        this.productoDesc = productoDesc;
    }

    public String getCampoNombre() {
        return campoNombre;
    }

    public void setCampoNombre(String campoNombre) {
        this.campoNombre = campoNombre;
    }

    public Double getCampoPrecio() {
        return campoPrecio;
    }

    public void setCampoPrecio(Double campoPrecio) {
        this.campoPrecio = campoPrecio;
    }
}
