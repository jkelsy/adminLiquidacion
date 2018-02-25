package fachade;

import db.ConceptoVariable;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class ConceptoVariableRepository 
        implements EntityRepository<ConceptoVariable, Long> , 
                   CriteriaSupport<ConceptoVariable> {
    public abstract ConceptoVariable findOptionalByNombre(String nombre);
   
    
}