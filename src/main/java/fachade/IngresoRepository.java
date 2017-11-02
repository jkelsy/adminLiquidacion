package fachade;

import db.Ingreso;
import db.Patrimonio;
import java.util.List;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class IngresoRepository 
        extends AbstractEntityRepository<Ingreso, Long>
        implements EntityRepository<Ingreso, Long> , CriteriaSupport<Ingreso> {
    public abstract Ingreso findOptionalByAnyoAndDesde(int anyo, int desde);
    public abstract List<Ingreso> findAllOrderByAnyoDescDesdeAsc();
    public abstract List<Ingreso> findByAnyo(int anyo);
    public abstract Ingreso findOptionalByDesdeGreaterThanAndHastaLessThanEqualsAndAnyo(int desde, int hasta, int anyo);
   
    public Ingreso findByEquivalenciaAndAnyo(int equivalencia, int anyo){
        
        Ingreso temporal = null;
        
        try {
            //select * from ingreso where 8 between desde and hasta and anyo = 2017
            temporal = typedQuery("select e from Ingreso e where ?1  between e.desde and e.hasta and anyo = ?2")
                .setParameter(1, equivalencia)
                .setParameter(2, anyo)                
                .getSingleResult();
        } catch (Exception e) {
            return null;
        }
        return temporal;
    }
}