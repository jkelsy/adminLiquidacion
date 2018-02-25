package db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author jkelsy
 */
@Entity(name = "Archivo")
@Table(name = "archivo")
@NamedQueries({
    @NamedQuery(
        name = "Archivo.findByTipoAndCode", 
        query = "Select e from Archivo e where e.tipoSoporte = :TIPO and e.PEOPLE_CODE_ID = :PEOPLE_CODE_ID"
    ),
    @NamedQuery(
        name = "Archivo.findByEstudiante", 
        query = "Select e from Archivo e where e.PEOPLE_CODE_ID = :PEOPLE_CODE_ID"
    )
})
public class Archivo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic private String nombre;
    
    @Basic private String nombreOrigen;
    
    @Basic private String extension;
    
    @Basic private String rutaFisica;
    
    @Basic private String rutaWeb;
    
    @Basic private String tipoSoporte; //identificacion, declaracion_renta, recibo 
    
    @Basic private String PEOPLE_CODE_ID;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getExtension() {
        return this.extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getRutaFisica() {
        return rutaFisica;
    }

    public void setRutaFisica(String rutaFisica) {
        this.rutaFisica = rutaFisica;
    }

    public String getNombreOrigen() {
        return nombreOrigen;
    }

    public void setNombreOrigen(String nombreOrigen) {
        this.nombreOrigen = nombreOrigen;
    }

    public String getRutaWeb() {
        return rutaWeb;
    }

    public void setRutaWeb(String rutaWeb) {
        this.rutaWeb = rutaWeb;
    }

    public String getTipoSoporte() {
        return tipoSoporte;
    }

    public void setTipoSoporte(String tipoSoporte) {
        this.tipoSoporte = tipoSoporte;
    }   

    public String getPEOPLE_CODE_ID() {
        return PEOPLE_CODE_ID;
    }

    public void setPEOPLE_CODE_ID(String PEOPLE_CODE_ID) {
        this.PEOPLE_CODE_ID = PEOPLE_CODE_ID;
    }   
}
