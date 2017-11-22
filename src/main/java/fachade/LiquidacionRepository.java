package fachade;

import db.EncabezadoLiquidacion;
import db.Liquidacion;
import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class LiquidacionRepository implements EntityRepository<Liquidacion, Long> , CriteriaSupport<Liquidacion> {
    public abstract List<Liquidacion> findByEncabezadoLiquidacion(EncabezadoLiquidacion encabezado);
    public abstract List<Liquidacion> findByEncabezadoLiquidacionAndEstado(EncabezadoLiquidacion encabezado, String estado);
    
}