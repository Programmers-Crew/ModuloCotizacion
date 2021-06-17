package org.ModuloCotizacion.bean;

public class EstadoProduccion {
    
    private Integer estadoProduccionId;
    private String estadoProduccionDesc;

    public EstadoProduccion() {
    }

    public EstadoProduccion(Integer estadoProduccionId, String estadoProduccionDesc) {
        this.estadoProduccionId = estadoProduccionId;
        this.estadoProduccionDesc = estadoProduccionDesc;
    }

    public Integer getEstadoProduccionId() {
        return estadoProduccionId;
    }

    public void setEstadoProduccionId(Integer estadoProduccionId) {
        this.estadoProduccionId = estadoProduccionId;
    }

    public String getEstadoProduccionDesc() {
        return estadoProduccionDesc;
    }

    public void setEstadoProduccionDesc(String estadoProduccionDesc) {
        this.estadoProduccionDesc = estadoProduccionDesc;
    }


    
    
}
