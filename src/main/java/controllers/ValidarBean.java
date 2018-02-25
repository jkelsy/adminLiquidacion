/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import db.ConceptoVariable;
import db.Estudiante;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author jk
 */
@SessionScoped
public class ValidarBean implements Serializable{
    
    private List<Estudiante> estudiantesSinSoportes;
    private List<Estudiante> estudiantesALiquidar;
    private List<ConceptoVariable> conceptosLiquidar;

    public List<ConceptoVariable> getConceptosLiquidar() {
        return conceptosLiquidar;
    }

    public void setConceptosLiquidar(List<ConceptoVariable> conceptosLiquidar) {
        this.conceptosLiquidar = conceptosLiquidar;
    }
    
    public List<Estudiante> getEstudiantesALiquidar() {
        return estudiantesALiquidar;
    }

    public void setEstudiantesALiquidar(List<Estudiante> estudiantesALiquidar) {
        this.estudiantesALiquidar = estudiantesALiquidar;
    }

    public List<Estudiante> getEstudiantesSinSoportes() {
        return estudiantesSinSoportes;
    }

    public void setEstudiantesSinSoportes(List<Estudiante> estudiantesSinSoportes) {
        this.estudiantesSinSoportes = estudiantesSinSoportes;
    }
    
    public void addEstudianteSinSoporte(Estudiante estudiante){
        estudiantesSinSoportes.add(estudiante);
    }
    
    public void addEstudianteALiquidar(Estudiante estudiante){
        estudiantesALiquidar.add(estudiante);
    }
    
    public void addConceptoLiquidar(ConceptoVariable concepto){
        conceptosLiquidar.add(concepto);
    }
    
    @PostConstruct
    public void iniciar(){
        estudiantesSinSoportes = new ArrayList<>();
        estudiantesALiquidar = new ArrayList<>();
        conceptosLiquidar = new ArrayList<>();
    }
    
    public void iniciarEstudiantes(){
        estudiantesSinSoportes = new ArrayList<>();
        estudiantesALiquidar = new ArrayList<>();
    }

}
