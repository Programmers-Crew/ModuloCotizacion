package org.ModuloCotizacion.bean;

import java.sql.Date;


public class Produccion {
    
    private Integer produccionId;
    private Integer produccionCotizacion;
    private Date produccionFechaEntrada;
    private Date produccionFechaSalida;
    private Integer produccionDiasRestantes;    
    private String usuarioNombre;
    private String  produccionEstado;
    private double cotizacionTotal;
    
    public Produccion() {
    }

    public Produccion(Integer produccionId, Integer produccionCotizacion, Date produccionFechaEntrada, Date produccionFechaSalida, Integer produccionDiasRestantes, String usuarioNombre, String produccionEstado, double cotizacionTotal) {
        this.produccionId = produccionId;
        this.produccionCotizacion = produccionCotizacion;
        this.produccionFechaEntrada = produccionFechaEntrada;
        this.produccionFechaSalida = produccionFechaSalida;
        this.produccionDiasRestantes = produccionDiasRestantes;
        this.usuarioNombre = usuarioNombre;
        this.produccionEstado = produccionEstado;
        this.cotizacionTotal = cotizacionTotal;
    }
    

    public Integer getProduccionId() {
        return produccionId;
    }

    public void setProduccionId(Integer produccionId) {
        this.produccionId = produccionId;
    }

    public Integer getProduccionCotizacion() {
        return produccionCotizacion;
    }

    public void setProduccionCotizacion(Integer produccionCotizacion) {
        this.produccionCotizacion = produccionCotizacion;
    }

    public Date getProduccionFechaEntrada() {
        return produccionFechaEntrada;
    }

    public void setProduccionFechaEntrada(Date produccionFechaEntrada) {
        this.produccionFechaEntrada = produccionFechaEntrada;
    }

    public Date getProduccionFechaSalida() {
        return produccionFechaSalida;
    }

    public void setProduccionFechaSalida(Date produccionFechaSalida) {
        this.produccionFechaSalida = produccionFechaSalida;
    }

    public Integer getProduccionDiasRestantes() {
        return produccionDiasRestantes;
    }

    public void setProduccionDiasRestantes(Integer produccionDiasRestantes) {
        this.produccionDiasRestantes = produccionDiasRestantes;
    }

    

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public String getProduccionEstado() {
        return produccionEstado;
    }

    public void setProduccionEstado(String produccionEstado) {
        this.produccionEstado = produccionEstado;
    }

    public double getCotizacionTotal() {
        return cotizacionTotal;
    }

    public void setCotizacionTotal(double cotizacionTotal) {
        this.cotizacionTotal = cotizacionTotal;
    }

    @Override
    public String toString() {
        return "Produccion{" + "produccionId=" + produccionId + ", produccionCotizacion=" + produccionCotizacion + ", produccionFechaEntrada=" + produccionFechaEntrada + ", produccionFechaSalida=" + produccionFechaSalida + ", produccionDiasRestantes=" + produccionDiasRestantes + ", usuarioNombre=" + usuarioNombre + ", produccionEstado=" + produccionEstado + ", cotizacionTotal=" + cotizacionTotal + '}';
    }
    
    
    
    
    
    
}
