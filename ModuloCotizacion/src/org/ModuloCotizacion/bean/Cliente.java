package org.ModuloCotizacion.bean;

public class Cliente {
    String clienteId;
    String clienteNit;
    String clienteNombre;
    String clienteDireccion;
    String clienteTelefono;
    String clienteCorreo;
    
    public Cliente() {
    }

    public Cliente(String clienteId, String clienteNit, String clienteNombre, String clienteDireccion, String clienteTelefono, String clienteCorreo) {
        this.clienteId = clienteId;
        this.clienteNit = clienteNit;
        this.clienteNombre = clienteNombre;
        this.clienteDireccion = clienteDireccion;
        this.clienteTelefono = clienteTelefono;
        this.clienteCorreo = clienteCorreo;
    }


    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNit() {
        return clienteNit;
    }

    public void setClienteNit(String clienteNit) {
        this.clienteNit = clienteNit;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getClienteDireccion() {
        return clienteDireccion;
    }

    public void setClienteDireccion(String clienteDireccion) {
        this.clienteDireccion = clienteDireccion;
    }

    public String getClienteTelefono() {
        return clienteTelefono;
    }

    public void setClienteTelefono(String clienteTelefono) {
        this.clienteTelefono = clienteTelefono;
    }

    public String getClienteCorreo() {
        return clienteCorreo;
    }

    public void setClienteCorreo(String clienteCorreo) {
        this.clienteCorreo = clienteCorreo;
    }
    
    

    @Override
    public String toString() {
        return "Cliente{" + "clienteId=" + clienteId + ", clienteNit=" + clienteNit + ", clienteNombre=" + clienteNombre + ", clienteDireccion=" + clienteDireccion + '}';
    }

     
}
