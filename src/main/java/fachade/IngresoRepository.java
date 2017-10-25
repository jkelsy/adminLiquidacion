package fachade;

import db.Ingreso;
import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class IngresoRepository implements EntityRepository<Ingreso, Long> , CriteriaSupport<Ingreso> {
    public abstract Ingreso findOptionalByAnyoAndDesde(int anyo, int desde);
    public abstract List<Ingreso> findAllOrderByAnyoDescDesdeAsc();
    public abstract List<Ingreso> findByAnyo(int anyo);
    public abstract Ingreso findOptionalByDesdeGreaterThanAndHastaLessThanEqualsAndAnyo(int desde, int hasta, int anyo);
   
}