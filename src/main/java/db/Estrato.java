package db;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Estrato implements Serializable { 

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Basic
    private int anyo;

    @Basic
    private int estrato;

    @Basic
    private Double valorSMLV;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAnyo() {
        return this.anyo;
    }

    public void setAnyo(int anyo) {
        this.anyo = anyo;
    }

    public int getEstrato() {
        return this.estrato;
    }

    public void setEstrato(int estrato) {
        this.estrato = estrato;
    }

    public Double getValorSMLV() {
        return this.valorSMLV;
    }

    public void setValorSMLV(Double valorSMLV) {
        this.valorSMLV = valorSMLV;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Estrato other = (Estrato) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    

}
