package db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class EncabezadoLiquidacion implements Serializable {
    
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP) private Date fechaLiquidacion;
    
    @Basic private String liquidadoPor;
    
    @Basic private int anyoLiquidacion;
    
    @Basic private String semestre;
    
    @Basic private String programa;

    public int getAnyoLiquidacion() {
        return anyoLiquidacion;
    }

    public void setAnyoLiquidacion(int anyoLiquidacion) {
        this.anyoLiquidacion = anyoLiquidacion;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    public String getLiquidadoPor() {
        return liquidadoPor;
    }

    public void setLiquidadoPor(String liquidadoPor) {
        this.liquidadoPor = liquidadoPor;
    }
    
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
