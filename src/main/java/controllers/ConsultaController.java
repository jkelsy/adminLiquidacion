/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import db.Consulta;
import db.Estudiante;
import fachade.ConsultaRepository;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import services.NombreDatasourceService;

/**
 *
 * @author jk
 */
@Named(value = "consulta")
@ViewScoped
public class ConsultaController implements Serializable {   
    
    @Inject
    private ConsultaRepository consultaRepository;
    
    @Inject
    private NombreDatasourceService nombreDatasourceService;
    
    private Consulta consultaInstance;
    private Consulta consultaSelected;
    private List<Consulta> consultaList;
    private List<Consulta> filteredConsultaList;
    private List<Estudiante> consultaEstudiantes;

    public Consulta getConsultaSelected() {
        return consultaSelected;
    }

    public void setConsultaSelected(Consulta consultaSelected) {
        this.consultaSelected = consultaSelected;
    }

    public Consulta getConsultaInstance() {
        return consultaInstance;
    }

    public void setConsultaInstance(Consulta consultaInstance) {
        this.consultaInstance = consultaInstance;
    }

    public List<Consulta> getFilteredConsultaList() {
        return filteredConsultaList;
    }

    public void setFilteredConsultaList(List<Consulta> filteredConsultaList) {
        this.filteredConsultaList = filteredConsultaList;
    }

    public List<Consulta> getConsultaList() {
        return consultaList;
    }

    public void setConsultaList(List<Consulta> consultaList) {
        this.consultaList = consultaList;
    }

    public List<Estudiante> getConsultaEstudiantes() {
        return consultaEstudiantes;
    }

    public void setConsultaEstudiantes(List<Estudiante> consultaEstudiantes) {
        this.consultaEstudiantes = consultaEstudiantes;
    }
    
    public void iniciar(){                
        this.consultaList = consultaRepository.findAll();    
        this.filteredConsultaList = consultaList;
        this.consultaInstance = new Consulta();
        
    }
    
    public void guardar(){
        consultaRepository.saveAndFlush(consultaInstance);        
        this.iniciar();
    }
    
    public void cancelar(){                
        consultaInstance = new Consulta();
    }
    
    public void eliminar(){
        consultaRepository.attachAndRemove(consultaInstance);        
        this.iniciar();
    }
    
    public void onRowSelect(SelectEvent event) {
        consultaInstance = consultaSelected;
        
    }
    
    public void resultado(){
        this.consultaEstudiantes = nombreDatasourceService.cargarEstudiantes(consultaInstance.getNombreDatasource().getNombre(), consultaInstance.getTextoSql());
    }
    
}
