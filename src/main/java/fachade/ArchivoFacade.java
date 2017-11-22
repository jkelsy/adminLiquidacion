/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fachade;

import db.Archivo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jk
 */
@Stateless
public class ArchivoFacade extends AbstractFacade<Archivo> {

    @PersistenceContext(unitName = "LiquidacionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ArchivoFacade() {
        super(Archivo.class);
    }
    
    public Archivo findByTipoAndPEOPLE_CODE_ID(String tipo, String PEOPLE_CODE_ID){
        List<Archivo> temporal;       
        try{
            temporal = em.createNamedQuery("Archivo.findByTipoAndCode", Archivo.class)
                    .setParameter("TIPO", tipo)
                    .setParameter("PEOPLE_CODE_ID", PEOPLE_CODE_ID)
                    .getResultList();
            if(temporal.isEmpty()){
                return null;
            }else{
                return temporal.get(0);
            }
        }catch(Exception e){            
            return null;
        }
    }
    
}
