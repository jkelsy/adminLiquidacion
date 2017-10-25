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
public class Liquidacion implements Serializable {
    
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Basic private String PEOPLE_CODE_ID;

    @Basic private String Nombres;
    
    @Basic private String Apellidos;

    @Basic private int estrato;

    @Basic private int patrimonio;

    @Basic private int ingreso;
    
    @Basic private int ultimoPago; //pago mensual último anyo secundaria
    
    @Basic private int ultimoAnyoPago; //último año secundaria    
    
    @Basic private int anyoLiquidacion; //último año secundaria
    
    @Temporal(TemporalType.TIMESTAMP) private Date fechaActualizacion;
    
    @Basic private String actualizadoPor;
    
    @Temporal(TemporalType.TIMESTAMP) private Date fechaLiquidacion;
    
    @Basic private String liquidadoPor;

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
    

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActulizacion) {
        this.fechaActualizacion = fechaActulizacion;
    }

    public String getActualizadoPor() {
        return actualizadoPor;
    }

    public void setActualizadoPor(String actualizadoPor) {
        this.actualizadoPor = actualizadoPor;
    }

    public int getUltimoPago() {
        return ultimoPago;
    }

    public void setUltimoPago(int ultimoPago) {
        this.ultimoPago = ultimoPago;
    }

    public int getUltimoAnyoPago() {
        return ultimoAnyoPago;
    }

    public void setUltimoAnyoPago(int ultimoAnyoPago) {
        this.ultimoAnyoPago = ultimoAnyoPago;
    }

    public int getAnyoLiquidacion() {
        return anyoLiquidacion;
    }

    public void setAnyoLiquidacion(int anyoLiquidacion) {
        this.anyoLiquidacion = anyoLiquidacion;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPEOPLE_CODE_ID() {
        return this.PEOPLE_CODE_ID;
    }

    public void setPEOPLE_CODE_ID(String PEOPLE_CODE_ID) {
        this.PEOPLE_CODE_ID = PEOPLE_CODE_ID;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String Nombres) {
        this.Nombres = Nombres;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String Apellidos) {
        this.Apellidos = Apellidos;
    }


    public int getEstrato() {
        return this.estrato;
    }

    public void setEstrato(int estrato) {
        this.estrato = estrato;
    }


    public int getPatrimonio() {
        return this.patrimonio;
    }

    public void setPatrimonio(int patrimonio) {
        this.patrimonio = patrimonio;
    }


    public int getIngreso() {
        return this.ingreso;
    }

    public void setIngreso(int ingreso) {
        this.ingreso = ingreso;
    }
    
}
