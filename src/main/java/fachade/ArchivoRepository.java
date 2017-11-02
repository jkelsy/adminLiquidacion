package fachade;

import db.Archivo;
import java.util.List;
import javax.persistence.TypedQuery;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class ArchivoRepository 
        extends AbstractEntityRepository<Archivo, Long> 
        implements EntityRepository<Archivo, Long> , 
                   CriteriaSupport<Archivo> {
    
    public List<Archivo> findAllByPEOPLE_CODE_IDAndAnyoAndSemestre(String PEOPLE_CODE_ID, int anyo, String semestre){
        return typedQuery("select a from Archivo a where a.PEOPLE_CODE_ID = ?1 and a.anyo = ?2 and a.semestre = ?3")
                .setParameter(1, PEOPLE_CODE_ID)
                .setParameter(2, anyo)
                .setParameter(3, semestre)
                .getResultList();
    }
    
}