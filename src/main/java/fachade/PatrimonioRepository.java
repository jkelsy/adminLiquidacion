package fachade;

import db.Estrato;
import db.Patrimonio;
import db.Rol;
import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class PatrimonioRepository implements EntityRepository<Patrimonio, Long> , CriteriaSupport<Patrimonio> {
    public abstract Patrimonio findOptionalByAnyoAndDesde(int anyo, int desde);
    public abstract List<Patrimonio> findAllOrderByAnyoDescDesdeAsc();
    public abstract List<Patrimonio> findByAnyo(int anyo);
    public abstract Patrimonio findOptionalByDesdeGreaterThanAndHastaLessThanEqualsAndAnyo(int desde, int hasta, int anyo);
            
}