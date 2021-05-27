package org.moduloFacturacion.bean;

import java.sql.Date;


public class Cardex {
    
    private Date fechaCardex;
    private String noFacCardex;
    private String idTipoDesc;
    private Double saldoCardex;
    private Double entradaCardex;
    private Double totalCardex;
    private String productoDesc;
    private String DescTipoDocumento;
    
    public Cardex() {
    }

    public Cardex(Date fechaCardex, String noFacCardex, String idTipoDesc, Double saldoCardex, Double entradaCardex, Double totalCardex, String productoDesc, String DescTipoDocumento) {
        this.fechaCardex = fechaCardex;
        this.noFacCardex = noFacCardex;
        this.idTipoDesc = idTipoDesc;
        this.saldoCardex = saldoCardex;
        this.entradaCardex = entradaCardex;
        this.totalCardex = totalCardex;
        this.productoDesc = productoDesc;
        this.DescTipoDocumento = DescTipoDocumento;
    }


    public Date getFechaCardex() {
        return fechaCardex;
    }

    public void setFechaCardex(Date fechaCardex) {
        this.fechaCardex = fechaCardex;
    }

    public String getNoFacCardex() {
        return noFacCardex;
    }

    public void setNoFacCardex(String noFacCardex) {
        this.noFacCardex = noFacCardex;
    }

    public String getIdTipoDesc() {
        return idTipoDesc;
    }

    public void setIdTipoDesc(String idTipoDesc) {
        this.idTipoDesc = idTipoDesc;
    }

    public Double getSaldoCardex() {
        return saldoCardex;
    }

    public void setSaldoCardex(Double saldoCardex) {
        this.saldoCardex = saldoCardex;
    }

    public Double getTotalCardex() {
        return totalCardex;
    }

    public void setTotalCardex(Double totalCardex) {
        this.totalCardex = totalCardex;
    }

    public String getProductoDesc() {
        return productoDesc;
    }

    public void setProductoDesc(String productoDesc) {
        this.productoDesc = productoDesc;
    }

    public String getDescTipoDocumento() {
        return DescTipoDocumento;
    }

    public void setDescTipoDocumento(String DescTipoDocumento) {
        this.DescTipoDocumento = DescTipoDocumento;
    }

    public Double getEntradaCardex() {
        return entradaCardex;
    }

    public void setEntradaCardex(Double entradaCardex) {
        this.entradaCardex = entradaCardex;
    }
            
    
    
}
