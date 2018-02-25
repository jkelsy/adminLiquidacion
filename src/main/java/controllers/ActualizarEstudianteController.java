package controllers;

import db.Configuracion;
import db.Estudiante;
import fachade.ConfiguracionRepository;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import services.NombreDatasourceService;
import services.SessionUtils;

@Named(value = "actualizarEstudiante")
@ViewScoped
public class ActualizarEstudianteController implements Serializable {
    
    @Inject private NombreDatasourceService nds; 
    @Inject private ConfiguracionRepository configuracionRepository;
    
    private Configuracion configuracion;    
    private List<Estudiante> estudianteList;
    
    private String identificacion;
    private String nombres;
    private String apellidos;

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public List<Estudiante> getEstudianteList() {
        return estudianteList;
    }

    public void setEstudianteList(List<Estudiante> estudianteList) {
        this.estudianteList = estudianteList;
    }
   
    public void iniciar() {              
        estudianteList = new ArrayList<>();
        this.configuracion = configuracionRepository.findBy(1L);
    }
    
    public void buscar(){        
        estudianteList = nds.buscarEstudiantes(
                identificacion, apellidos, nombres, 
                configuracion.getAnyo(), configuracion.getSemestre());
    }
    
    public String soportes(Estudiante item){
        HttpSession session = SessionUtils.getSession();
        session.setAttribute("estudiante", item);        
        return "/pages/soportes?faces-redirect=true";
    }
}
