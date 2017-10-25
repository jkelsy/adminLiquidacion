package fachade;

import db.Estrato;
import db.Rol;
import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class EstratoRepository implements EntityRepository<Estrato, Long> , CriteriaSupport<Estrato> {
    public abstract Estrato findOptionalByAnyoAndEstrato(int anyo, int estrato);
    public abstract List<Estrato> findAllOrderByAnyoDescEstratoAsc();
    public abstract List<Estrato> findByAnyo(int anyo);
    
    
    public void calcular(){
        
    }
}