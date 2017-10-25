/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import db.Patrimonio;
import fachade.PatrimonioRepository;
import fachade.PatrimonioRepository;
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
@Named(value = "patrimonio")
@ViewScoped
public class PatrimonioController implements Serializable {   
    
    @Inject
    private PatrimonioRepository patrimonioRepository;
    
    private Patrimonio patrimonioInstance;
    private Patrimonio patrimonioSelected;
    private List<Patrimonio> patrimonioList;
    private List<Patrimonio> filteredPatrimonioList;

    public Patrimonio getPatrimonioSelected() {
        return patrimonioSelected;
    }

    public void setPatrimonioSelected(Patrimonio patrimonioSelected) {
        this.patrimonioSelected = patrimonioSelected;
    }

    public Patrimonio getPatrimonioInstance() {
        return patrimonioInstance;
    }

    public void setPatrimonioInstance(Patrimonio patrimonioInstance) {
        this.patrimonioInstance = patrimonioInstance;
    }

    public List<Patrimonio> getFilteredPatrimonioList() {
        return filteredPatrimonioList;
    }

    public void setFilteredPatrimonioList(List<Patrimonio> filteredPatrimonioList) {
        this.filteredPatrimonioList = filteredPatrimonioList;
    }

    public List<Patrimonio> getPatrimonioList() {
        return patrimonioList;
    }

    public void setPatrimonioList(List<Patrimonio> patrimonioList) {
        this.patrimonioList = patrimonioList;
    }
    
    public void iniciar(){                
        this.patrimonioList = patrimonioRepository.findAllOrderByAnyoDescDesdeAsc();    
        this.filteredPatrimonioList = patrimonioList;
        this.patrimonioInstance = new Patrimonio();
    }
    
    public void guardar(){
        patrimonioRepository.saveAndFlush(patrimonioInstance);        
        this.iniciar();
    }
    
    public void cancelar(){                
        patrimonioInstance = new Patrimonio();
    }
    
    public void eliminar(){
        patrimonioRepository.attachAndRemove(patrimonioInstance);        
        this.iniciar();
    }
    
    public void onRowSelect(SelectEvent event) {
        patrimonioInstance = patrimonioSelected;
        
    }
    
}
