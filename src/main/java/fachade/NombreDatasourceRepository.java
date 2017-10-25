package fachade;


import db.NombreDatasource;
import db.Usuario;
import java.io.Serializable;
import java.util.List;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class NombreDatasourceRepository extends AbstractEntityRepository<NombreDatasource, Long> implements EntityRepository<NombreDatasource, Long> , CriteriaSupport<Usuario>, Serializable {
   
   public abstract NombreDatasource findOptionalByNombre(String nombre); 
    
    
}