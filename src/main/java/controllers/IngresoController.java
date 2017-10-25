/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import db.Ingreso;
import fachade.IngresoRepository;
import fachade.IngresoRepository;
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
@Named(value = "ingreso")
@ViewScoped
public class IngresoController implements Serializable {   
    
    @Inject
    private IngresoRepository ingresoRepository;
    
    private Ingreso ingresoInstance;
    private Ingreso ingresoSelected;
    private List<Ingreso> ingresoList;
    private List<Ingreso> filteredIngresoList;

    public Ingreso getIngresoSelected() {
        return ingresoSelected;
    }

    public void setIngresoSelected(Ingreso ingresoSelected) {
        this.ingresoSelected = ingresoSelected;
    }

    public Ingreso getIngresoInstance() {
        return ingresoInstance;
    }

    public void setIngresoInstance(Ingreso ingresoInstance) {
        this.ingresoInstance = ingresoInstance;
    }

    public List<Ingreso> getFilteredIngresoList() {
        return filteredIngresoList;
    }

    public void setFilteredIngresoList(List<Ingreso> filteredIngresoList) {
        this.filteredIngresoList = filteredIngresoList;
    }

    public List<Ingreso> getIngresoList() {
        return ingresoList;
    }

    public void setIngresoList(List<Ingreso> ingresoList) {
        this.ingresoList = ingresoList;
    }
    
    public void iniciar(){                
        this.ingresoList = ingresoRepository.findAllOrderByAnyoDescDesdeAsc();    
        this.filteredIngresoList = ingresoList;
        this.ingresoInstance = new Ingreso();
    }
    
    public void guardar(){
        ingresoRepository.saveAndFlush(ingresoInstance);        
        this.iniciar();
    }
    
    public void cancelar(){                
        ingresoInstance = new Ingreso();
    }
    
    public void eliminar(){
        ingresoRepository.attachAndRemove(ingresoInstance);        
        this.iniciar();
    }
    
    public void onRowSelect(SelectEvent event) {
        ingresoInstance = ingresoSelected;
        
    }
    
}
