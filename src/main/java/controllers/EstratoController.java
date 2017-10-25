/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import db.Estrato;
import fachade.EstratoRepository;
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
@Named(value = "estrato")
@ViewScoped
public class EstratoController implements Serializable {   
    
    @Inject
    private EstratoRepository estratoRepository;
    
    private Estrato estratoInstance;
    private Estrato estratoSelected;
    private List<Estrato> estratoList;
    private List<Estrato> filteredEstratoList;

    public Estrato getEstratoSelected() {
        return estratoSelected;
    }

    public void setEstratoSelected(Estrato estratoSelected) {
        this.estratoSelected = estratoSelected;
    }

    public Estrato getEstratoInstance() {
        return estratoInstance;
    }

    public void setEstratoInstance(Estrato estratoInstance) {
        this.estratoInstance = estratoInstance;
    }

    public List<Estrato> getFilteredEstratoList() {
        return filteredEstratoList;
    }

    public void setFilteredEstratoList(List<Estrato> filteredEstratoList) {
        this.filteredEstratoList = filteredEstratoList;
    }

    public List<Estrato> getEstratoList() {
        return estratoList;
    }

    public void setEstratoList(List<Estrato> estratoList) {
        this.estratoList = estratoList;
    }
    
    public void iniciar(){                
        this.estratoList = estratoRepository.findAllOrderByAnyoDescEstratoAsc();    
        this.filteredEstratoList = estratoList;
        this.estratoInstance = new Estrato();
    }
    
    public void guardar(){
        estratoRepository.saveAndFlush(estratoInstance);        
        this.iniciar();
    }
    
    public void cancelar(){                
        estratoInstance = new Estrato();
    }
    
    public void eliminar(){
        estratoRepository.attachAndRemove(estratoInstance);        
        this.iniciar();
    }
    
    public void onRowSelect(SelectEvent event) {
        estratoInstance = estratoSelected;
        
    }
    
}
