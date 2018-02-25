package configuracion;

import db.ConceptoVariable;
import db.Configuracion;
import db.DatoVariable;
import db.Estrato;
import db.Ingreso;
import db.NombreDatasource;
import db.Patrimonio;
import db.Permiso;
import db.Rol;
import db.Usuario;
import fachade.ConceptoVariableRepository;
import fachade.ConfiguracionRepository;
import fachade.DatoVariableRepository;
import fachade.EstratoRepository;
import fachade.IngresoRepository;
import fachade.NombreDatasourceRepository;
import fachade.PatrimonioRepository;
import fachade.PermisoRepository;
import fachade.RolRepository;
import fachade.UsuarioRepository;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.script.ScriptException;
import services.ConceptoVariableService;

@Startup
@Singleton
public class Inicio {
    @Inject private UsuarioRepository usuarioRepository;
    @Inject private RolRepository rolRepository;
    @Inject private PermisoRepository permisoRepository;    
    @Inject private NombreDatasourceRepository nombreRepository;    
    @Inject private EstratoRepository estratoRepository;    
    @Inject private PatrimonioRepository patrimonioRepository;    
    @Inject private IngresoRepository ingresoRepository;    
    @Inject private DatoVariableRepository datoVariableRepository;    
    @Inject private ConfiguracionRepository configuracionRepository;
    @Inject private ConceptoVariableRepository conceptoVariableRepository;
    @Inject private ConceptoVariableService cvs;

    private void iniciarSeguridad() {

        String[] roles = {
            Constantes.ROLE_ADMIN,
            Constantes.ROLE_USER
        };

        Rol rolEntity;

        for (String rol : roles) {
            rolEntity = rolRepository.findOptionalByNombre(rol);
            if (rolEntity == null) {
                rolEntity = new Rol();
                rolEntity.setNombre(rol);
                rolRepository.save(rolEntity);
            }
        }

        Usuario usuario = usuarioRepository.findOptionalByLogin("admin");

        if (usuario == null) {
            usuario = new Usuario();
            usuario.setLogin("admin");
            MessageDigest md;
            String _password = "admin";
            try {
                md = MessageDigest.getInstance("SHA-256");
                byte[] passwordBytes = _password.getBytes();
                byte[] hash = md.digest(passwordBytes);
                _password = Base64.getEncoder().encodeToString(hash);
            } catch (NoSuchAlgorithmException e) {
                System.err.println(e.getMessage());
            }
            usuario.setPassword(_password);
            usuario.setActivo(Boolean.TRUE);
            usuario = usuarioRepository.save(usuario);
        }

        Rol rolAdmin = rolRepository.findOptionalByNombre(Constantes.ROLE_ADMIN);
        Permiso permiso = permisoRepository.findOptionalByUsuarioAndRol(usuario, rolAdmin);
        if (permiso == null) {
            permiso = new Permiso();
            permiso.setUsuario(usuario);
            permiso.setRol(rolAdmin);
            permisoRepository.save(permiso);
        }
    } 
    
    public void iniciarDatasources(){
        NombreDatasource nombreDs = nombreRepository.findOptionalByNombre("AgendaDS");
        if(nombreDs == null){
            nombreDs = new NombreDatasource();
            nombreDs.setNombre("AgendaDS");
            nombreRepository.save(nombreDs);
        } 
        
        nombreDs = nombreRepository.findOptionalByNombre("SQLServerUnicorDS");
        if(nombreDs == null){
            nombreDs = new NombreDatasource();
            nombreDs.setNombre("SQLServerUnicorDS");
            nombreRepository.save(nombreDs);
        } 
        
    }
    
    public void iniciarEstratos(){
        double[] valores = {0.20, 0.25, 0.30, 0.50, 1.5, 2.00};
        
        Estrato estrato;
        for (int i = 0; i < valores.length; i++) {
            estrato = estratoRepository.findOptionalByAnyoAndEstrato(2017, i+1);
            if(estrato == null){
                estrato = new Estrato();
                estrato.setAnyo(2017);
                estrato.setEstrato(i+1);
                estrato.setValorSMLV(valores[i]);
                estratoRepository.save(estrato);
            }
        }
    }
    
    public void iniciarPatrimonios(){
        int[] desde = {0, 100, 200, 400, 600};
        int[] hasta = {100, 200, 400, 600, Integer.MAX_VALUE};        
        double[] valores = {0.10, 0.15, 0.20, 0.70, 2.00};
        
        Patrimonio patrimonio;
        
        for (int i = 0; i < valores.length; i++) {
            patrimonio = patrimonioRepository.findOptionalByAnyoAndDesde(2017, desde[i]);
                        
            if(patrimonio == null){
                patrimonio = new Patrimonio();
                patrimonio.setAnyo(2017);
                patrimonio.setDesde(desde[i]);
                patrimonio.setHasta(hasta[i]);
                patrimonio.setValorSMLV(valores[i]);
                patrimonioRepository.save(patrimonio);
            }
        }
    }
    
    public void iniciarIngresos(){
        int[] desde = {0, 24, 48, 72, 96, 120};
        int[] hasta = {24, 48, 72, 96, 120, Integer.MAX_VALUE};        
        double[] valores = {0.20, 0.25, 0.3, 0.40, 0.50, 1.00};
        
        Ingreso ingreso;
        
        for (int i = 0; i < valores.length; i++) {
            ingreso = ingresoRepository.findOptionalByAnyoAndDesde(2017, desde[i]);
                        
            if(ingreso == null){
                ingreso = new Ingreso();
                ingreso.setAnyo(2017);
                ingreso.setDesde(desde[i]);
                ingreso.setHasta(hasta[i]);
                ingreso.setValorSMLV(valores[i]);
                ingresoRepository.save(ingreso);
            }
        }
    }
    
    public void iniciarIPC(){
        double[] ipc = {0.2585, 0.2636, 0.2403, 0.1664, 0.1828, 0.2245, 0.2095, 
                        0.2402, 0.2812, 0.2612, 0.3236, 0.2682, 0.2513, 0.226, 
                        0.2259, 0.1946, 0.2163, 0.1768, 0.167, 0.092317, 
                        0.087482, 0.076464, 0.069928, 0.064906, 0.054975, 
                        0.048549, 0.044779, 0.056941, 0.076748, 0.02001808, 
                        0.03171222, 0.03725787, 0.02435345, 0.01937815, 
                        0.0365768, 0.06769089, 0.05747408};
        
        int anyo = 1980;
        DatoVariable datoVariable;
        
        for (int i = 0; i < ipc.length; i++) {
            datoVariable = datoVariableRepository.findOptionalByAnyoAndNombre(anyo, "IPC");            
            if (datoVariable == null){
                datoVariable = new DatoVariable();
                datoVariable.setAnyo(anyo);
                datoVariable.setNombre("IPC");
                datoVariable.setValor(ipc[i]);
                datoVariableRepository.save(datoVariable);
            }
            anyo++;            
        }
    }
    
    public void iniciarConfiguracion(){
        Configuracion configuracion = configuracionRepository.findBy(1L);       
        
        if(configuracion == null){
            configuracion = new Configuracion();
            configuracion.setAnyo(2018);
            configuracion.setSemestre("SEM1");
            configuracionRepository.save(configuracion);
        }
                
    }
    
    public void iniciarConceptos(){
        
        ConceptoVariable conceptoVariable = conceptoVariableRepository.findOptionalByNombre("MATRICULA");
        
        if(conceptoVariable == null){
            conceptoVariable = new ConceptoVariable();
            conceptoVariable.setNombre("MATRICULA");
            conceptoVariable.setFormula("MATRICULA");
            conceptoVariable.setTipo("MATRICULA");
            conceptoVariableRepository.save(conceptoVariable);
        }
        
    }

    @PostConstruct
    public void iniciar() { 
        
        System.err.println("Iniciar Prueba Evaluación");        
        ConceptoVariable cv = new ConceptoVariable();
        String formula = "4 * BECADO";
        try {
            System.err.println(cvs.evaluar(formula));
        } catch (ScriptException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        iniciarConceptos();
        iniciarIPC();
        iniciarIngresos();
        iniciarPatrimonios();
        iniciarEstratos();
        iniciarDatasources();
        iniciarSeguridad();   
        iniciarConfiguracion();
    }
}
