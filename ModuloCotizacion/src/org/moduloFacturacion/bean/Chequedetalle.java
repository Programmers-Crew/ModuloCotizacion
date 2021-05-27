package org.moduloFacturacion.bean;

public class Chequedetalle {
    int chequeDetalleNo;
    String chequeDetalleCuenta;
    String chequeDetalleDesc;
    double chequeDetalleValor;



    public Chequedetalle() {
    }

    public String getChequeDetalleCuenta() {
        return chequeDetalleCuenta;
    }

    public Chequedetalle(int chequeDetalleNo, String chequeDetalleCuenta, String chequeDetalleDesc, double chequeDetalleValor) {
        this.chequeDetalleNo = chequeDetalleNo;
        this.chequeDetalleCuenta = chequeDetalleCuenta;
        this.chequeDetalleDesc = chequeDetalleDesc;
        this.chequeDetalleValor = chequeDetalleValor;
    }

    public void setChequeDetalleCuenta(String chequeDetalleCuenta) {
        this.chequeDetalleCuenta = chequeDetalleCuenta;
    }

    public int getChequeDetalleNo() {
        return chequeDetalleNo;
    }

    public void setChequeDetalleNo(int chequeDetalleNo) {
        this.chequeDetalleNo = chequeDetalleNo;
    }

    
    public String getChequeDetalleDesc() {
        return chequeDetalleDesc;
    }

    public void setChequeDetalleDesc(String chequeDetalleDesc) {
        this.chequeDetalleDesc = chequeDetalleDesc;
    }

    public double getChequeDetalleValor() {
        return chequeDetalleValor;
    }

    public void setChequeDetalleValor(double chequeDetalleValor) {
        this.chequeDetalleValor = chequeDetalleValor;
    }

    @Override
    public String toString() {
        return "Chequedetalle{" + "chequeDetalleCuenta=" + chequeDetalleCuenta + ", chequeDetalleDesc=" + chequeDetalleDesc + ", chequeDetalleValor=" + chequeDetalleValor + '}';
    }
    
}
