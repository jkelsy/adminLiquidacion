/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import configuracion.Constantes;
import db.EncabezadoLiquidacion;
import db.Liquidacion;
import fachade.EncabezadoLiquidacionRepository;
import fachade.LiquidacionRepository;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import services.NombreDatasourceService;

/**
 *
 * @author jk
 */
@Named(value = "reporteLiquidacion")
@ViewScoped
public class ReportePreliquidacionController implements Serializable {
   
    @Inject private EncabezadoLiquidacionRepository elr;
    @Inject private LiquidacionRepository lr;
    @Inject private NombreDatasourceService datasourceService;
    
    private long encabezadoID; 
    private EncabezadoLiquidacion encabezadoLiquidacion;
    private List<Liquidacion> liquidacionList;
    private List<Liquidacion> sinActualizarList;
    private List<Liquidacion> soportesIncompletosList;

    public List<Liquidacion> getSoportesIncompletosList() {
        return soportesIncompletosList;
    }

    public void setSoportesIncompletosList(List<Liquidacion> soportesIncompletosList) {
        this.soportesIncompletosList = soportesIncompletosList;
    }

    public List<Liquidacion> getSinActualizarList() {
        return sinActualizarList;
    }

    public void setSinActualizarList(List<Liquidacion> sinActualizarList) {
        this.sinActualizarList = sinActualizarList;
    }

    public long getEncabezadoID() {
        return encabezadoID;
    }

    public void setEncabezadoID(long encabezadoID) {
        this.encabezadoID = encabezadoID;
    }    

    public EncabezadoLiquidacion getEncabezadoLiquidacion() {
        return encabezadoLiquidacion;
    }

    public void setEncabezadoLiquidacion(EncabezadoLiquidacion encabezadoLiquidacion) {
        this.encabezadoLiquidacion = encabezadoLiquidacion;
    }

    public List<Liquidacion> getLiquidacionList() {
        return liquidacionList;
    }

    public void setLiquidacionList(List<Liquidacion> liquidacionList) {
        this.liquidacionList = liquidacionList;
    }
    
    public void iniciar() {
        encabezadoLiquidacion = elr.findBy(encabezadoID);
        liquidacionList = lr.findByEncabezadoLiquidacionAndEstado(encabezadoLiquidacion, Constantes.ESTADO_PRE_LIQUIDADO);
        sinActualizarList = lr.findByEncabezadoLiquidacionAndEstado(encabezadoLiquidacion, Constantes.ESTADO_SIN_ACTUALIZAR);        
        soportesIncompletosList = lr.findByEncabezadoLiquidacionAndEstado(encabezadoLiquidacion, Constantes.ESTADO_SOPORTES_INCOMPLETOS);        
        
    }
    
    public void liquidar(){
        for(Liquidacion liquidacion:liquidacionList){
            String primaryFlag = datasourceService.buscarPrimaryFlag(liquidacion);
            System.out.println(primaryFlag);
            
            String refVolante = datasourceService.buscarRefVolante(primaryFlag, liquidacion);
            System.out.println(refVolante);
            
            datasourceService.actualizarDerechoMatricula(refVolante, liquidacion);
            
        }
    }
}
