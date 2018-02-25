package fachade;

import db.Archivo;
import java.util.List;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class ArchivoRepository 
        extends AbstractEntityRepository<Archivo, Long> 
        implements EntityRepository<Archivo, Long> , 
                   CriteriaSupport<Archivo> {
    
    public List<Archivo> findAllByTipoAndPEOPLE_CODE_ID(String tipo, String PEOPLE_CODE_ID){
        return typedQuery("select a from Archivo a where a.tipoSoporte = ?1 and a.PEOPLE_CODE_ID = ?2 ")
                .setParameter(1, tipo)
                .setParameter(2, PEOPLE_CODE_ID)
                .getResultList();
    }
    
    public Archivo findByTipoAndPEOPLE_CODE_ID(String tipo, String PEOPLE_CODE_ID){
        Archivo tempo;
        try {
           tempo = typedQuery("select a from Archivo a where a.tipoSoporte = ?1 and a.PEOPLE_CODE_ID = ?2 ")
                .setParameter(1, tipo)
                .setParameter(2, PEOPLE_CODE_ID)
                .getSingleResult(); 
        }catch(Exception e){
            return null;
        }        
        return tempo;
    }
    
    public List<Archivo> findAllByPEOPLE_CODE_ID(String PEOPLE_CODE_ID){
        return typedQuery("select a from Archivo a where a.PEOPLE_CODE_ID = ?2 ")  
                .setParameter(2, PEOPLE_CODE_ID)
                .getResultList();
    }
    
}