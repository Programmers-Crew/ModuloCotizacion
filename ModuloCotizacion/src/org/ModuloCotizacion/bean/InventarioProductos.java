package org.ModuloCotizacion.bean;

public class InventarioProductos {
    private String productoId;
    private double inventarioProductoCant;
    private String proveedorNombre;
    private String productoDesc;
    private String estadoProductoDesc;
    private double productoPrecio;
    private String categoriaNombre;
    
    public InventarioProductos() {
    }

    public InventarioProductos(String productoId, double inventarioProductoCant, String proveedorNombre, String productoDesc, String estadoProductoDesc, double productoPrecio, String categoriaNombre) {
        this.productoId = productoId;
        this.inventarioProductoCant = inventarioProductoCant;
        this.proveedorNombre = proveedorNombre;
        this.productoDesc = productoDesc;
        this.estadoProductoDesc = estadoProductoDesc;
        this.productoPrecio = productoPrecio;
        this.categoriaNombre = categoriaNombre;
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

    
    
}
