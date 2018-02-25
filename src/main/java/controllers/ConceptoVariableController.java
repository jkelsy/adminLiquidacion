/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import db.ConceptoVariable;
import db.Configuracion;
import db.DatoVariable;
import fachade.ConceptoVariableRepository;
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
@Named(value = "conceptoVariable")
@ViewScoped
public class ConceptoVariableController implements Serializable {   
    
    @Inject private ConceptoVariableRepository conceptoVariableRepository;
    
    private ConceptoVariable conceptoVariableInstance;
    private ConceptoVariable conceptoVariableSelected;
    private List<ConceptoVariable> conceptoVariableList;
    private List<ConceptoVariable> filteredConceptoVariableList;
  
    public void iniciar(){                
        this.conceptoVariableList = conceptoVariableRepository.findAll();    
        this.filteredConceptoVariableList = conceptoVariableList;
        this.conceptoVariableInstance = new ConceptoVariable();        
    }
    
    public void guardar(){
        conceptoVariableRepository.saveAndFlush(conceptoVariableInstance);        
        this.iniciar();
    }
    
    public void cancelar(){                
        conceptoVariableInstance = new ConceptoVariable();
    }
    
    public void eliminar(){
        conceptoVariableRepository.attachAndRemove(conceptoVariableInstance);        
        this.iniciar();
    }
    
    public void onRowSelect(SelectEvent event) {
        conceptoVariableInstance = conceptoVariableSelected;        
    }

    public ConceptoVariable getConceptoVariableInstance() {
        return conceptoVariableInstance;
    }

    public void setConceptoVariableInstance(ConceptoVariable conceptoVariableInstance) {
        this.conceptoVariableInstance = conceptoVariableInstance;
    }

    public ConceptoVariable getConceptoVariableSelected() {
        return conceptoVariableSelected;
    }

    public void setConceptoVariableSelected(ConceptoVariable conceptoVariableSelected) {
        this.conceptoVariableSelected = conceptoVariableSelected;
    }

    public List<ConceptoVariable> getConceptoVariableList() {
        return conceptoVariableList;
    }

    public void setConceptoVariableList(List<ConceptoVariable> conceptoVariableList) {
        this.conceptoVariableList = conceptoVariableList;
    }

    public List<ConceptoVariable> getFilteredConceptoVariableList() {
        return filteredConceptoVariableList;
    }

    public void setFilteredConceptoVariableList(List<ConceptoVariable> filteredConceptoVariableList) {
        this.filteredConceptoVariableList = filteredConceptoVariableList;
    }
}
