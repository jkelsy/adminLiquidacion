/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import db.Archivo;
import db.Consulta;
import db.Estudiante;
import db.NombreDatasource;
import fachade.ArchivoRepository;
import fachade.ConsultaRepository;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
@Named(value = "cargar")
@ViewScoped
public class CargarController implements Serializable {
    
    @Inject
    private NombreDatasourceService datasourceService;
    
    @Inject private ConsultaRepository consultaRepository;
    
    @Inject private ArchivoRepository archivoRepository;
    
    private Consulta consulta;   
    private List<Consulta> consultaList;
    
    private List<Estudiante> listadoEstudiantes;
    
    private List<Integer> anyoList;

    public List<Integer> getAnyoList() {
        return anyoList;
    }

    public void setAnyoList(List<Integer> anyoList) {
        this.anyoList = anyoList;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public List<Consulta> getConsultaList() {
        return consultaList;
    }

    public void setConsultaList(List<Consulta> consultaList) {
        this.consultaList = consultaList;
    }

    public List<Estudiante> getListadoEstudiantes() {
        return listadoEstudiantes;
    }

    public void setListadoEstudiantes(List<Estudiante> listadoEstudiantes) {
        this.listadoEstudiantes = listadoEstudiantes;
    }
    
    public void iniciar(){        
        this.listadoEstudiantes = new ArrayList<>();
        this.consultaList = consultaRepository.findAll();
        this.anyoList = new ArrayList<>();
        for (int i = -10; i < 10; i++) {
            this.anyoList.add(Calendar.getInstance().get(Calendar.YEAR)+i );
        }
    }
    
    public void cargar(){        
        listadoEstudiantes = datasourceService.cargarEstudiantes(consulta.getNombreDatasource().getNombre(), consulta.getTextoSql());        
    }
    
    public List<Archivo> soportes(String PEOPLE_CODE_ID){
        return archivoRepository.findAllByPEOPLE_CODE_ID("P000065670");
    }
    
    public void liquidar(){
        
    }
}
