package fachade;

import db.EncabezadoLiquidacion;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class EncabezadoLiquidacionRepository implements EntityRepository<EncabezadoLiquidacion, Long> , CriteriaSupport<EncabezadoLiquidacion> {
    
    
}