package org.moduloFacturacion.bean;


public class Proveedores {
        private String proveedorId;
        private String proveedorNombre;
        private String proveedorTelefono;
        private String proveedorNit;

    public Proveedores() {
    }

    public Proveedores(String proveedorId, String proveedorNombre, String proveedorTelefono, String proveedorNit) {
        this.proveedorId = proveedorId;
        this.proveedorNombre = proveedorNombre;
        this.proveedorTelefono = proveedorTelefono;
        this.proveedorNit = proveedorNit;
    }

    public String getProveedorNit() {
        return proveedorNit;
    }

    public void setProveedorNit(String proveedorNit) {
        this.proveedorNit = proveedorNit;
    }

    public String getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(String proveedorId) {
        this.proveedorId = proveedorId;
    }

    public String getProveedorNombre() {
        return proveedorNombre;
    }

    public void setProveedorNombre(String proveedorNombre) {
        this.proveedorNombre = proveedorNombre;
    }

    public String getProveedorTelefono() {
        return proveedorTelefono;
    }

    public void setProveedorTelefono(String proveedorTelefono) {
        this.proveedorTelefono = proveedorTelefono;
    }
    
    
    
}
