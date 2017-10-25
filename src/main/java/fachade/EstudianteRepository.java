package fachade;

import db.Estudiante;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class EstudianteRepository implements EntityRepository<Estudiante, Long> , CriteriaSupport<Estudiante> {
    
    
}