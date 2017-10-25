/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import db.Estudiante;
import db.NombreDatasource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.bean.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import services.NombreDatasourceService;

/**
 *
 * @author jk
 */
@Named(value = "consultas")
@ViewScoped
public class ConsultasController implements Serializable {
    
    @Inject
    private NombreDatasourceService datasourceService;
    
    private String sql;
    
    private NombreDatasource nombreDatasource;
    
    List<Estudiante> listadoEstudiantes;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
    
    public NombreDatasource getNombreDatasource() {
        return nombreDatasource;
    }

    public void setNombreDatasource(NombreDatasource nombreDatasource) {
        this.nombreDatasource = nombreDatasource;
    }

    public List<Estudiante> getListadoEstudiantes() {
        return listadoEstudiantes;
    }

    public void setListadoEstudiantes(List<Estudiante> listadoEstudiantes) {
        this.listadoEstudiantes = listadoEstudiantes;
    }
    
    public void iniciar(){
        this.sql = "select tar_nombre PEOPLE_CODE, tar_descripcion PEOPLE_ID, CREADOR_ID PEOPLE_CODE_ID, ESTADO_ID UserName from tarea;";
        this.nombreDatasource = new NombreDatasource();
        this.listadoEstudiantes = new ArrayList<>();
    }
    
    public void cargar(){        
        listadoEstudiantes = datasourceService.cargarEstudiantes(nombreDatasource.getNombre(), sql);
        
    }
}
