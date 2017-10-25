package fachade;

import db.Rol;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class RolRepository implements EntityRepository<Rol, Long> , CriteriaSupport<Rol> {
    public abstract Rol findOptionalByNombre(String nombre);
}