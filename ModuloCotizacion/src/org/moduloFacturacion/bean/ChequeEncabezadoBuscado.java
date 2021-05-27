package org.moduloFacturacion.bean;


public class ChequeEncabezadoBuscado {
    
    private String chequeDetalleCuenta;
    private String chequeDetalleDesc;
    private Double chequeDetalleValor;

    public ChequeEncabezadoBuscado() {
    }

    public ChequeEncabezadoBuscado(String chequeDetalleCuenta, String chequeDetalleDesc, Double chequeDetalleValor) {
        this.chequeDetalleCuenta = chequeDetalleCuenta;
        this.chequeDetalleDesc = chequeDetalleDesc;
        this.chequeDetalleValor = chequeDetalleValor;
    }

    public String getChequeDetalleCuenta() {
        return chequeDetalleCuenta;
    }

    public void setChequeDetalleCuenta(String chequeDetalleCuenta) {
        this.chequeDetalleCuenta = chequeDetalleCuenta;
    }

    public String getChequeDetalleDesc() {
        return chequeDetalleDesc;
    }

    public void setChequeDetalleDesc(String chequeDetalleDesc) {
        this.chequeDetalleDesc = chequeDetalleDesc;
    }

    public Double getChequeDetalleValor() {
        return chequeDetalleValor;
    }

    public void setChequeDetalleValor(Double chequeDetalleValor) {
        this.chequeDetalleValor = chequeDetalleValor;
    }
    
    
}
