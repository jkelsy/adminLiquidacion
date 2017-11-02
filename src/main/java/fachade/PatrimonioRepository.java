package fachade;

import db.Patrimonio;
import java.util.List;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class PatrimonioRepository 
        extends AbstractEntityRepository<Patrimonio, Long>
        implements EntityRepository<Patrimonio, Long> , 
                   CriteriaSupport<Patrimonio> {
    
    public abstract Patrimonio findOptionalByAnyoAndDesde(int anyo, int desde);
    public abstract List<Patrimonio> findAllOrderByAnyoDescDesdeAsc();
    public abstract List<Patrimonio> findByAnyo(int anyo);
    public abstract Patrimonio findOptionalByDesdeGreaterThanAndHastaLessThanEqualsAndAnyo(int desde, int hasta, int anyo);
      
    public Patrimonio findByEquivalenciaAndAnyo(int equivalencia, int anyo){
        
        Patrimonio temporal = null;
        
        try {
            //select * from patrimonio where 8 between desde and hasta and anyo = 2017
            temporal = typedQuery("select e from Patrimonio e where ?1  between e.desde and e.hasta and anyo = ?2")
                .setParameter(1, equivalencia)
                .setParameter(2, anyo)                
                .getSingleResult();
        } catch (Exception e) {
            return null;
        }
        return temporal;
    }
}