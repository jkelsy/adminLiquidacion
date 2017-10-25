/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.Type;

/**
 *
 * @author jk
 */
@Entity
public class Consulta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Basic
    private String Nombre;
    
    @Basic
    private String Descripcion;
    
    @Basic
    @Lob
    @Type(type = "org.hibernate.type.TextType")
   
    private String textoSql;
    
    @ManyToOne(targetEntity = NombreDatasource.class)
    @JoinColumn(name = "NOMBRE_DATASOURCE_ID")
    private NombreDatasource nombreDatasource;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getTextoSql() {
        return textoSql;
    }

    public void setTextoSql(String textoSql) {
        this.textoSql = textoSql;
    }

    public NombreDatasource getNombreDatasource() {
        return nombreDatasource;
    }

    public void setNombreDatasource(NombreDatasource nombreDatasource) {
        this.nombreDatasource = nombreDatasource;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {return false;}
        if (!java.util.Objects.equals(getClass(), obj.getClass())) {return false;}
        final Consulta other = (Consulta) obj;
        if (!java.util.Objects.equals(this.getId(), other.getId())) {return false;        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Consulta{" + " id=" + id + '}';
    }
    
}
