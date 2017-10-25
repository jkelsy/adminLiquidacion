package fachade;

import db.Consulta;
import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class ConsultaRepository implements EntityRepository<Consulta, Long> , CriteriaSupport<Consulta> {
    
 
}