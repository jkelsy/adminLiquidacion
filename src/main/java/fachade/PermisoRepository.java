package fachade;

import db.Permiso;
import db.Rol;
import db.Usuario;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class PermisoRepository implements EntityRepository<Permiso, Long> , CriteriaSupport<Permiso> {
    public abstract Permiso findOptionalByUsuarioAndRol(
            Usuario usuario, 
            Rol rol
    );
}