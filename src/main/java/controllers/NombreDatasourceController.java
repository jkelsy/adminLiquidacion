/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import services.NombreDatasourceService;

/**
 *
 * @author jk
 */

@ManagedBean(name = "datasources")
public class NombreDatasourceController {
    
    @Inject
    private NombreDatasourceService dsServicio;

    public void mostrarServicio(){
        dsServicio.getDataSources2();
    }
}
