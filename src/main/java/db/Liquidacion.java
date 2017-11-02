package db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Liquidacion implements Serializable {
    
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Basic private String PEOPLE_CODE_ID;    
    @Basic private String codigoPrograma;  
    @Basic private int estrato;
    @Basic private int patrimonio;
    @Basic private int ingreso;    
    @Basic private int ultimoPago;    
    @Basic private int ultimoAnyoPago;    
    @Basic private int anyoLiquidacion;    
    @Basic private double valorMatricula;    
    @Temporal(TemporalType.TIMESTAMP) private Date fecha;    
    @Basic private String usuario;
    
    @ManyToOne(targetEntity = EncabezadoLiquidacion.class)
    @JoinColumn(name="ENCABEZADO_ID")
    private EncabezadoLiquidacion encabezadoLiquidacion;

    public String getCodigoPrograma() {
        return codigoPrograma;
    }

    public void setCodigoPrograma(String codigoPrograma) {
        this.codigoPrograma = codigoPrograma;
    }

    public double getValorMatricula() {
        return valorMatricula;
    }

    public void setValorMatricula(double valorMatricula) {
        this.valorMatricula = valorMatricula;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
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

    public EncabezadoLiquidacion getEncabezadoLiquidacion() {
        return encabezadoLiquidacion;
    }

    public void setEncabezadoLiquidacion(EncabezadoLiquidacion encabezadoLiquidacion) {
        this.encabezadoLiquidacion = encabezadoLiquidacion;
    }
    
}
