package fachade;

import db.Liquidacion;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class LiquidacionRepository implements EntityRepository<Liquidacion, Long> , CriteriaSupport<Liquidacion> {
    
    
}