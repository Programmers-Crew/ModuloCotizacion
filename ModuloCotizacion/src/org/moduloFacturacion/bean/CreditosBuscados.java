package org.moduloFacturacion.bean;


public class CreditosBuscados {
    
    private String productoDesc;
    private Double cantidadDetalle;
    private Double totalParcialDetalle;

    public CreditosBuscados() {
    }

    public CreditosBuscados(String productoDesc, Double cantidadDetalle, Double totalParcialDetalle) {
        this.productoDesc = productoDesc;
        this.cantidadDetalle = cantidadDetalle;
        this.totalParcialDetalle = totalParcialDetalle;
    }

    public String getProductoDesc() {
        return productoDesc;
    }

    public void setProductoDesc(String productoDesc) {
        this.productoDesc = productoDesc;
    }

    public Double getCantidadDetalle() {
        return cantidadDetalle;
    }

    public void setCantidadDetalle(Double cantidadDetalle) {
        this.cantidadDetalle = cantidadDetalle;
    }

    public Double getTotalParcialDetalle() {
        return totalParcialDetalle;
    }

    public void setTotalParcialDetalle(Double totalParcialDetalle) {
        this.totalParcialDetalle = totalParcialDetalle;
    }

    
}
