package org.moduloFacturacion.bean;

public class ProductoBuscado {
    
    private String productoDesc;
    private double cantidad;
    private double productoPrecio;

    public ProductoBuscado() {
    }

    public ProductoBuscado(String productoDesc, double cantidad, double productoPrecio) {
        this.productoDesc = productoDesc;
        this.cantidad = cantidad;
        this.productoPrecio = productoPrecio;
    }

    public String getProductoDesc() {
        return productoDesc;
    }

    public void setProductoDesc(String productoDesc) {
        this.productoDesc = productoDesc;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getProductoPrecio() {
        return productoPrecio;
    }

    public void setProductoPrecio(double productoPrecio) {
        this.productoPrecio = productoPrecio;
    }
    
    
    
}
