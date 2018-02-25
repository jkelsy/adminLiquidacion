/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import configuracion.Constantes;
import db.Archivo;
import db.Configuracion;
import db.Estudiante;
import fachade.ArchivoFacade;
import fachade.ConfiguracionRepository;
import fachade.EstudianteRepository;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import services.ArchivoService;
import services.SessionUtils;

/**
 *
 * @author jk
 */
@Named(value = "soporteController")
@ViewScoped
public class SoporteController implements Serializable {

    private Configuracion configuracion;
    private Estudiante estudiante;    
    private List<Integer> anyoList;
    
    //soportes   
    private Archivo archivoIdentificacion;
    private Archivo archivoEstrato;    
    private Archivo archivoMensualidad;
    
    //soportes para patrimonio
    private Archivo archivoInstrumentosPublicos;
    private Archivo archivoDeclaracionRenta;
    private Archivo archivoBalance;    
    
    //soportes para ingresos
    private Archivo archivoIngresosRetenciones;
    private Archivo archivoPerdidasGanancias;
    
    @Inject private ConfiguracionRepository configuracionRepository;
    @Inject private ArchivoService archivoService;
    @Inject private ArchivoFacade archivoFacade;
    @Inject private EstudianteRepository er;

    public SoporteController() {
        this.estudiante = new Estudiante();
        this.archivoIdentificacion = new Archivo();  
        this.archivoEstrato = new Archivo();
        
        this.archivoInstrumentosPublicos = new Archivo();
        this.archivoDeclaracionRenta = new Archivo();
        this.archivoBalance = new Archivo();
        
        this.archivoIngresosRetenciones = new Archivo();
        this.archivoPerdidasGanancias = new Archivo();
    }

    public Archivo getArchivoIngresosRetenciones() {
        return archivoIngresosRetenciones;
    }

    public void setArchivoIngresosRetenciones(Archivo archivoIngresosRetenciones) {
        this.archivoIngresosRetenciones = archivoIngresosRetenciones;
    }

    public Archivo getArchivoPerdidasGanancias() {
        return archivoPerdidasGanancias;
    }

    public void setArchivoPerdidasGanancias(Archivo archivoPerdidasGanancias) {
        this.archivoPerdidasGanancias = archivoPerdidasGanancias;
    }

    public Archivo getArchivoInstrumentosPublicos() {
        return archivoInstrumentosPublicos;
    }

    public void setArchivoInstrumentosPublicos(Archivo archivoInstrumentosPublicos) {
        this.archivoInstrumentosPublicos = archivoInstrumentosPublicos;
    }

    public Archivo getArchivoDeclaracionRenta() {
        return archivoDeclaracionRenta;
    }

    public void setArchivoDeclaracionRenta(Archivo archivoDeclaracionRenta) {
        this.archivoDeclaracionRenta = archivoDeclaracionRenta;
    }

    public Archivo getArchivoBalance() {
        return archivoBalance;
    }

    public void setArchivoBalance(Archivo archivoBalance) {
        this.archivoBalance = archivoBalance;
    }

    public Archivo getArchivoIdentificacion() {
        return archivoIdentificacion;
    }

    public void setArchivoIdentificacion(Archivo archivoIdentificacion) {
        this.archivoIdentificacion = archivoIdentificacion;
    }

    public Archivo getArchivoEstrato() {
        return archivoEstrato;
    }

    public void setArchivoEstrato(Archivo archivoEstrato) {
        this.archivoEstrato = archivoEstrato;
    }

    public Archivo getArchivoMensualidad() {
        return archivoMensualidad;
    }

    public void setArchivoMensualidad(Archivo archivoMensualidad) {
        this.archivoMensualidad = archivoMensualidad;
    }    

    public void inicio() {
        this.configuracion = configuracionRepository.findBy(1L);
        HttpSession session = SessionUtils.getSession();
        this.estudiante = ((Estudiante) session.getAttribute("estudiante"));
        
        Estudiante temporal = er.findByPEOPLE_CODE_ID(
                estudiante.getPEOPLE_CODE_ID());
        
        if(temporal != null ){
            this.estudiante = temporal;
        }
        
        this.archivoIdentificacion = archivoFacade.findByTipoAndPEOPLE_CODE_ID(
                Constantes.TIPO_IDENTIFICACION, this.estudiante.getPEOPLE_CODE_ID());
        
        this.archivoEstrato = archivoFacade.findByTipoAndPEOPLE_CODE_ID(
                Constantes.TIPO_ESTRATO, this.estudiante.getPEOPLE_CODE_ID());
        
        this.archivoMensualidad = archivoFacade.findByTipoAndPEOPLE_CODE_ID(
                Constantes.TIPO_MENSUALIDAD, this.estudiante.getPEOPLE_CODE_ID());
        
        this.archivoInstrumentosPublicos = archivoFacade.findByTipoAndPEOPLE_CODE_ID(
                Constantes.TIPO_INSTRUMENTOS, this.estudiante.getPEOPLE_CODE_ID());
        
        this.archivoDeclaracionRenta = archivoFacade.findByTipoAndPEOPLE_CODE_ID(
                Constantes.TIPO_DECLARACION, this.estudiante.getPEOPLE_CODE_ID());
        
        this.archivoBalance = archivoFacade.findByTipoAndPEOPLE_CODE_ID(
                Constantes.TIPO_BALANCE, this.estudiante.getPEOPLE_CODE_ID());
        
        this.archivoIngresosRetenciones = archivoFacade.findByTipoAndPEOPLE_CODE_ID(
                Constantes.TIPO_INGRESO_RETENCION, this.estudiante.getPEOPLE_CODE_ID());
        
        this.archivoPerdidasGanancias = archivoFacade.findByTipoAndPEOPLE_CODE_ID(
                Constantes.TIPO_PERDIDA_GANANCIA, this.estudiante.getPEOPLE_CODE_ID());

        
        this.anyoList = new ArrayList<>();
        for (int i = -15; i < 1; i++) {
            this.anyoList.add(Calendar.getInstance().get(Calendar.YEAR)+i );
        }
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public void cargarSoporteIdentificacion(FileUploadEvent event) {
        
        this.archivoIdentificacion = archivoService.upload(Constantes.TIPO_IDENTIFICACION, 
                estudiante.getPEOPLE_CODE_ID(),                               
                event.getFile());
    }
    
    public void cargarSoporteEstrato(FileUploadEvent event) {
        this.archivoEstrato = archivoService.upload(Constantes.TIPO_ESTRATO, 
                estudiante.getPEOPLE_CODE_ID(),                          
                event.getFile());
    }
    
    public void cargarSoporteMensualidad(FileUploadEvent event) {
        this.archivoMensualidad = archivoService.upload(Constantes.TIPO_MENSUALIDAD, 
                estudiante.getPEOPLE_CODE_ID(),                              
                event.getFile());
    }
    
    public void cargarSoporteInstrumentos(FileUploadEvent event) {
        this.archivoInstrumentosPublicos = archivoService.upload(Constantes.TIPO_INSTRUMENTOS, 
                estudiante.getPEOPLE_CODE_ID(),                             
                event.getFile());
    }
    
    public void cargarSoporteDeclaracion(FileUploadEvent event) {
        this.archivoDeclaracionRenta = archivoService.upload(Constantes.TIPO_DECLARACION, 
                estudiante.getPEOPLE_CODE_ID(),                                
                event.getFile());
    }
    
    public void cargarSoporteBalance(FileUploadEvent event) {
        this.archivoBalance = archivoService.upload(Constantes.TIPO_BALANCE, 
                estudiante.getPEOPLE_CODE_ID(),                                
                event.getFile());
    }
    
    public void cargarSoporteIngreso(FileUploadEvent event) {
        this.archivoIngresosRetenciones = archivoService.upload(Constantes.TIPO_INGRESO_RETENCION, 
                estudiante.getPEOPLE_CODE_ID(),                              
                event.getFile());
    }
    
    public void cargarSoportePerdida(FileUploadEvent event) {
        this.archivoPerdidasGanancias = archivoService.upload(Constantes.TIPO_PERDIDA_GANANCIA, 
                estudiante.getPEOPLE_CODE_ID(),                         
                event.getFile());
    }

    public List<Integer> getAnyoList() {
        return anyoList;
    }

    public void setAnyoList(List<Integer> anyoList) {
        this.anyoList = anyoList;
    }
    
    public void guardar(){        
        
        
        
        estudiante.setFechaActualizacion(new Date());
        estudiante.setActualizadoPor(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName());
        
        estudiante = er.saveAndFlush(estudiante);
        
        FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Soportes del estudiante actualizados",
                            "Soportes del estudiante actualizados")
            );
    }
    
}
