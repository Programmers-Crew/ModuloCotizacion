package org.ModuloCotizacion.bean;

public class InventarioProductos {
    private String productoId;
    private double inventarioProductoCant;
    private String proveedorNombre;
    private String productoDesc;
    private String estadoProductoDesc;
    private double productoPrecio;
    private double productoPrecio2;
    private double productoPrecio3;
    private double productoPrecio4;
    private String categoriaNombre;
    private double prodLargo;
    private double prodAlto;
    private double prodAncho;
    
    public InventarioProductos() {
    }

    public InventarioProductos(String productoId, double inventarioProductoCant, String proveedorNombre, String productoDesc, String estadoProductoDesc, double productoPrecio, double productoPrecio2, double productoPrecio3, double productoPrecio4, String categoriaNombre, double prodLargo, double prodAlto, double prodAncho) {
        this.productoId = productoId;
        this.inventarioProductoCant = inventarioProductoCant;
        this.proveedorNombre = proveedorNombre;
        this.productoDesc = productoDesc;
        this.estadoProductoDesc = estadoProductoDesc;
        this.productoPrecio = productoPrecio;
        this.productoPrecio2 = productoPrecio2;
        this.productoPrecio3 = productoPrecio3;
        this.productoPrecio4 = productoPrecio4;
        this.categoriaNombre = categoriaNombre;
        this.prodLargo = prodLargo;
        this.prodAlto = prodAlto;
        this.prodAncho = prodAncho;
    }

    
    public String getProductoId() {
        return productoId;
    }

    public void setProductoId(String productoId) {
        this.productoId = productoId;
    }

    public double getInventarioProductoCant() {
        return inventarioProductoCant;
    }

    public void setInventarioProductoCant(double inventarioProductoCant) {
        this.inventarioProductoCant = inventarioProductoCant;
    }

    public String getProveedorNombre() {
        return proveedorNombre;
    }

    public void setProveedorNombre(String proveedorNombre) {
        this.proveedorNombre = proveedorNombre;
    }

    public String getProductoDesc() {
        return productoDesc;
    }

    public void setProductoDesc(String productoDesc) {
        this.productoDesc = productoDesc;
    }

    public String getEstadoProductoDesc() {
        return estadoProductoDesc;
    }

    public void setEstadoProductoDesc(String estadoProductoDesc) {
        this.estadoProductoDesc = estadoProductoDesc;
    }

    public double getProductoPrecio() {
        return productoPrecio;
    }

    public void setProductoPrecio(double productoPrecio) {
        this.productoPrecio = productoPrecio;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    public double getProductoPrecio2() {
        return productoPrecio2;
    }

    public void setProductoPrecio2(double productoPrecio2) {
        this.productoPrecio2 = productoPrecio2;
    }

    public double getProductoPrecio3() {
        return productoPrecio3;
    }

    public void setProductoPrecio3(double productoPrecio3) {
        this.productoPrecio3 = productoPrecio3;
    }

    public double getProductoPrecio4() {
        return productoPrecio4;
    }

    public void setProductoPrecio4(double productoPrecio4) {
        this.productoPrecio4 = productoPrecio4;
    }        

    public double getProdLargo() {
        return prodLargo;
    }

    public void setProdLargo(double prodLargo) {
        this.prodLargo = prodLargo;
    }

    public double getProdAlto() {
        return prodAlto;
    }

    public void setProdAlto(double prodAlto) {
        this.prodAlto = prodAlto;
    }

    public double getProdAncho() {
        return prodAncho;
    }

    public void setProdAncho(double prodAncho) {
        this.prodAncho = prodAncho;
    }
    
    
}
