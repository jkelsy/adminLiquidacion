package fachade;

import db.DatoVariable;
import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class DatoVariableRepository implements EntityRepository<DatoVariable, Long> , CriteriaSupport<DatoVariable> {
    public abstract DatoVariable findOptionalByAnyoAndNombre(int anyo, String nombre);
    public abstract List<DatoVariable> findAllOrderByAnyoDesc();
    public abstract List<DatoVariable> findByAnyo(int anyo);
    
}