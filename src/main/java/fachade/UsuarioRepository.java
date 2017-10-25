package fachade;


import db.Usuario;
import java.io.Serializable;
import java.util.List;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class UsuarioRepository extends AbstractEntityRepository<Usuario, Long> implements EntityRepository<Usuario, Long> , CriteriaSupport<Usuario>, Serializable {
    public abstract Usuario findOptionalByLogin(String login);
    
    public Boolean esAdmin(Usuario usuario){
        List<Usuario> lista = typedQuery(
                "Select e.usuario from Permiso e WHERE e.usuario = ?1 and e.rol.nombre = 'ADMIN'"
        ).setParameter(1, usuario).getResultList();
        
        return !(lista == null || lista.isEmpty());
    }
    
}