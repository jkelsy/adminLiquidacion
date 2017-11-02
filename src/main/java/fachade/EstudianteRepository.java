package fachade;

import db.Archivo;
import db.Estudiante;
import java.util.List;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class EstudianteRepository 
        extends AbstractEntityRepository<Estudiante, Long> 
        implements EntityRepository<Estudiante, Long> , 
                   CriteriaSupport<Estudiante> {
    
    public Estudiante findByPEOPLE_CODE_IDAndAnyoAndSemestre(String PEOPLE_CODE_ID, int anyo, String semestre){
        
        Estudiante temporal = null;
        
        try {
            temporal = typedQuery("select e from Estudiante e where e.PEOPLE_CODE_ID = ?1 and e.anyoLiquidacion = ?2 and e.semestre = ?3")
                .setParameter(1, PEOPLE_CODE_ID)
                .setParameter(2, anyo)
                .setParameter(3, semestre)
                .getSingleResult();
        } catch (Exception e) {
            return null;
        }
        return temporal;
    }
    
}