package org.moduloFacturacion.bean;

import java.sql.Date;


public class Creditos {
    
    private Date creaditoFechaInicio;
    private Date creditoFechaFinal;
    private Integer creditoDiasRestantes;
    private String creditoDesc;
    private Double creditoMonto;
    private String estadoCreditoDesc;
    private String noFactura;
    
    public Creditos() {
    }

    public Creditos(Date creaditoFechaInicio, Date creditoFechaFinal, Integer creditoDiasRestantes, String creditoDesc, Double creditoMonto, String estadoCreditoDesc, String noFactura) {
        this.creaditoFechaInicio = creaditoFechaInicio;
        this.creditoFechaFinal = creditoFechaFinal;
        this.creditoDiasRestantes = creditoDiasRestantes;
        this.creditoDesc = creditoDesc;
        this.creditoMonto = creditoMonto;
        this.estadoCreditoDesc = estadoCreditoDesc;
        this.noFactura = noFactura;
    }

    
    
    public String getNoFactura() {
        return noFactura;
    }

    public void setNoFactura(String noFactura) {
        this.noFactura = noFactura;
    }
    
    public Integer getCreditoDiasRestantes() {
        return creditoDiasRestantes;
    }

    public void setCreditoDiasRestantes(Integer creditoDiasRestantes) {
        this.creditoDiasRestantes = creditoDiasRestantes;
    }

    public Date getCreaditoFechaInicio() {
        return creaditoFechaInicio;
    }

    public void setCreaditoFechaInicio(Date creaditoFechaInicio) {
        this.creaditoFechaInicio = creaditoFechaInicio;
    }

    public Date getCreditoFechaFinal() {
        return creditoFechaFinal;
    }

    public void setCreditoFechaFinal(Date creditoFechaFinal) {
        this.creditoFechaFinal = creditoFechaFinal;
    }

    public String getCreditoDesc() {
        return creditoDesc;
    }

    public void setCreditoDesc(String creditoDesc) {
        this.creditoDesc = creditoDesc;
    }

    public Double getCreditoMonto() {
        return creditoMonto;
    }

    public void setCreditoMonto(Double creditoMonto) {
        this.creditoMonto = creditoMonto;
    }

    public String getEstadoCreditoDesc() {
        return estadoCreditoDesc;
    }

    public void setEstadoCreditoDesc(String estadoCreditoDesc) {
        this.estadoCreditoDesc = estadoCreditoDesc;
    }
    
}
