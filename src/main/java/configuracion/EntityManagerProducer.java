package configuracion;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Producer for injectable EntityManager
 *
 */
public class EntityManagerProducer {
    @Produces
    @PersistenceContext(unitName = "LiquidacionPU")
    private EntityManager em;
}