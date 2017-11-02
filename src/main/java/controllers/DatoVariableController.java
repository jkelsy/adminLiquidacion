/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import db.Configuracion;
import db.DatoVariable;
import fachade.ConfiguracionRepository;
import fachade.DatoVariableRepository;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author jk
 */
@Named(value = "datoVariable")
@ViewScoped
public class DatoVariableController implements Serializable {   
    
    @Inject private DatoVariableRepository datoVariableRepository;
    @Inject private ConfiguracionRepository configuracionRepository;
    
    private DatoVariable datoVariableInstance;
    private DatoVariable datoVariableSelected;
    private List<DatoVariable> datoVariableList;
    private List<DatoVariable> filteredDatoVariableList;
    
    private Configuracion configuracion;

    public DatoVariable getDatoVariableSelected() {
        return datoVariableSelected;
    }

    public void setDatoVariableSelected(DatoVariable datoVariableSelected) {
        this.datoVariableSelected = datoVariableSelected;
    }

    public DatoVariable getDatoVariableInstance() {
        return datoVariableInstance;
    }

    public void setDatoVariableInstance(DatoVariable datoVariableInstance) {
        this.datoVariableInstance = datoVariableInstance;
    }

    public List<DatoVariable> getFilteredDatoVariableList() {
        return filteredDatoVariableList;
    }

    public void setFilteredDatoVariableList(List<DatoVariable> filteredDatoVariableList) {
        this.filteredDatoVariableList = filteredDatoVariableList;
    }

    public List<DatoVariable> getDatoVariableList() {
        return datoVariableList;
    }

    public void setDatoVariableList(List<DatoVariable> datoVariableList) {
        this.datoVariableList = datoVariableList;
    }
    
    public void iniciar(){                
        this.datoVariableList = datoVariableRepository.findAllOrderByAnyoDesc();    
        this.filteredDatoVariableList = datoVariableList;
        this.datoVariableInstance = new DatoVariable();
        this.configuracion = configuracionRepository.findBy(1L);
    }
    
    public void guardar(){
        datoVariableRepository.saveAndFlush(datoVariableInstance);        
        this.iniciar();
    }
    
    public void guardarConfiguracion(){
        this.configuracion = configuracionRepository.saveAndFlush(configuracion);
    }
    
    public void cancelar(){                
        datoVariableInstance = new DatoVariable();
    }
    
    public void eliminar(){
        datoVariableRepository.attachAndRemove(datoVariableInstance);        
        this.iniciar();
    }
    
    public void onRowSelect(SelectEvent event) {
        datoVariableInstance = datoVariableSelected;        
    }

    public Configuracion getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(Configuracion configuracion) {
        this.configuracion = configuracion;
    }
    
}
