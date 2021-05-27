package org.moduloFacturacion.bean;

import java.sql.Date;


public class ChequeBuscado {
    
    private int chequeNo;
    private String chequeLugarYFecha;
    private String chequePagoAlaOrdenDe ;
    private Double chequeMonto;
    private String  chequeDetalleDesc;
    private String usuarioNombre;

    public ChequeBuscado() {
    }

    public ChequeBuscado(int chequeNo, String chequeLugarYFecha, String chequePagoAlaOrdenDe, Double chequeMonto, String chequeDetalleDesc, String usuarioNombre) {
        this.chequeNo = chequeNo;
        this.chequeLugarYFecha = chequeLugarYFecha;
        this.chequePagoAlaOrdenDe = chequePagoAlaOrdenDe;
        this.chequeMonto = chequeMonto;
        this.chequeDetalleDesc = chequeDetalleDesc;
        this.usuarioNombre = usuarioNombre;
    }

    

    public String getChequePagoAlaOrdenDe() {
        return chequePagoAlaOrdenDe;
    }

    public void setChequePagoAlaOrdenDe(String chequePagoAlaOrdenDe) {
        this.chequePagoAlaOrdenDe = chequePagoAlaOrdenDe;
    }

    public int getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(int chequeNo) {
        this.chequeNo = chequeNo;
    }

    public String getChequeLugarYFecha() {
        return chequeLugarYFecha;
    }

    public void setChequeLugarYFecha(String chequeLugarYFecha) {
        this.chequeLugarYFecha = chequeLugarYFecha;
    }

    

    public Double getChequeMonto() {
        return chequeMonto;
    }

    public void setChequeMonto(Double chequeMonto) {
        this.chequeMonto = chequeMonto;
    }

    public String getChequeDetalleDesc() {
        return chequeDetalleDesc;
    }

    public void setChequeDetalleDesc(String chequeDetalleDesc) {
        this.chequeDetalleDesc = chequeDetalleDesc;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }
 
    
    
    
    
}
