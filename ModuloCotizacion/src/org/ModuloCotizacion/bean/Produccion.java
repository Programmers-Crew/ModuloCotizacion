package org.ModuloCotizacion.bean;

import java.sql.Date;


public class Produccion {
    
    private Integer produccionId;
    private Integer produccionCotizacion;
    private Date produccionFechaEntrada;
    private Date produccionFechaSalida;
    private Integer produccionDiasRestantes;    
    private String estadoProduccionDesc;
    private String usuarioNombre;

    public Produccion() {
    }

    public Produccion(Integer produccionId, Integer produccionCotizacion, Date produccionFechaEntrada, Date produccionFechaSalida, Integer produccionDiasRestantes, String estadoProduccionDesc, String usuarioNombre) {
        this.produccionId = produccionId;
        this.produccionCotizacion = produccionCotizacion;
        this.produccionFechaEntrada = produccionFechaEntrada;
        this.produccionFechaSalida = produccionFechaSalida;
        this.produccionDiasRestantes = produccionDiasRestantes;
        this.estadoProduccionDesc = estadoProduccionDesc;
        this.usuarioNombre = usuarioNombre;
    }

    public Produccion(Integer produccionId, Integer produccionCotizacion, Date produccionFechaEntrada, Date produccionFechaSalida, Integer produccionDiasRestantes) {
        this.produccionId = produccionId;
        this.produccionCotizacion = produccionCotizacion;
        this.produccionFechaEntrada = produccionFechaEntrada;
        this.produccionFechaSalida = produccionFechaSalida;
        this.produccionDiasRestantes = produccionDiasRestantes;
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

    public String getEstadoProduccionDesc() {
        return estadoProduccionDesc;
    }

    public void setEstadoProduccionDesc(String estadoProduccionDesc) {
        this.estadoProduccionDesc = estadoProduccionDesc;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }
    
    
    
}
